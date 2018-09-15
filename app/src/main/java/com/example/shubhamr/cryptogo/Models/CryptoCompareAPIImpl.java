package com.example.shubhamr.cryptogo.Models;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.UiThread;

import com.example.shubhamr.cryptogo.Interface.ApiInterface;
import com.example.shubhamr.cryptogo.Interface.Contract;
import com.example.shubhamr.cryptogo.ModelClasses.Coin;
import com.example.shubhamr.cryptogo.ModelClasses.CoinDetail;
import com.example.shubhamr.cryptogo.ModelClasses.CoinIndexResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptoCompareAPIImpl implements Contract.CryptoCompareAPI {

    private Context context;
    private ApiInterface apiService;
    private String currency = "INR";
    private String limit = "6";

    public CryptoCompareAPIImpl(Context context){
        this.context = context;
        // Retrieving retrofit instance
        this.apiService = ApiClient.getClient().create(ApiInterface.class);

    }





    // Retrieve Coin Index
    @Override
    public void getCoinIndex(final Contract.OnFinishedCoinIndex listener) {


        // Calling api method to get response of coin index query
        Call<CoinIndexResponse> call = apiService.getCoinList();


        call.enqueue(new Callback<CoinIndexResponse>() {
            @Override
            public void onResponse(Call<CoinIndexResponse> call, Response<CoinIndexResponse> response) {


                 // doing list retrieving task in async task as it take more time and since ruetrofit run on main thread it can block ui
                        new longTask(listener,response).execute();

                }

            @Override
            public void onFailure(Call<CoinIndexResponse> call, Throwable t) {
                // Pass back null so view can show error on screen
                t.printStackTrace();
                listener.onFinishedCoinList(null);
            }
        });


    }



   // Retrieve detail of coin only selected ones
    @Override
    public void getCoinFullData(Contract.OnFinishedCoinData listener,String symbol) {

        // Calling api method to get response of coin detail
        Call<JsonObject> call = apiService.getCoinData(symbol,currency);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()&&!response.body().isJsonNull()) {
                    JsonElement displayElement = response.body().getAsJsonObject("DISPLAY").getAsJsonObject(symbol).get("INR");
                    String jsonResponse = displayElement.toString();

                    //Converting response from json to CoinDetail Object
                    CoinDetail coinDetail = new Gson().fromJson(jsonResponse, CoinDetail.class);

                    // Call method to get history of coin for past 7 days and complete detail of coin
                    getCoinHistory7Day(listener, coinDetail, symbol);


                }
                else{
                    listener.onFinishedCoinDetail(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                //Passing back null if some error occured
                listener.onFinishedCoinDetail(null);
            }
        });


    }

/*
           ^
           |
           |         Interlinked Methods First one calls second one for complete processing
           |         this one is for only selected detail and for our main screen fragment
           |
           |
           v


 */


    // Retrieve price of coin for past 7 days
    @Override
    public void getCoinHistory7Day(Contract.OnFinishedCoinData listener,CoinDetail coinDetail,String symbol) {

        // Call method of interface for retrieving history of coin
        Call<JsonObject> call = apiService.getCoinWeeklyHistory(symbol,currency,limit);

        // Asynchronous calling
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                try {

                    List<String> historyList = new ArrayList<String>();

                    // Retrieving Data array which contain array of history of past 7 days
                    JsonArray historyArray = response.body().getAsJsonArray("Data");



                    // Accessing objects inside array
                    for (int i = 0; i < historyArray.size(); i++) {

                      // Retrieving close price only of history objects in side array
                      String historyItem =  historyArray.get(i).toString();
                      JSONObject jsonObject = new JSONObject(historyItem);
                      //Accessing only close key
                      String closePrice = jsonObject.getString("close");
                      //Adding to history list
                      historyList.add(closePrice);

                    }

                    // Setting historyList inside coin object
                    coinDetail.setHistoryList(historyList);

                    // FINAL CALL : Passing back coin detail object which cotain complete data about coin
                    listener.onFinishedCoinDetail(coinDetail);


                }catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                //Passing back null if some error happened
                  listener.onFinishedCoinDetail(null);
                  t.printStackTrace();
            }
        });


    }





    // Weekly history of two coins for comparing
    @Override
    public void getTwoCoinHistory(Contract.OnFinishedCoinHistory listener, String coin,String type,String coinNum) {

        Call<JsonObject> call;

        switch (type)
        {

            case "WEEKLY":
                call = apiService.getCoinWeeklyHistory(coin,currency,"6");
                break;

            case "HOURLY" :
                call = apiService.getCoinHourlyHistory(coin,currency,"6");
                break;

            case "MINUTES" :
                call = apiService.getCoinMinuteHistory(coin,currency,"6");
                break;

             default:
                 call = null;

        }



        // Asynchronous calling
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                try {

                    List<String> historyList = new ArrayList<String>();

                    // Retrieving Data array which contain array of history of past 7 days
                    JsonArray historyArray = response.body().getAsJsonArray("Data");



                    // Accessing objects inside array
                    for (int i = 0; i < historyArray.size(); i++) {

                        // Retrieving close price only of history objects in side array
                        String historyItem =  historyArray.get(i).toString();
                        JSONObject jsonObject = new JSONObject(historyItem);
                        //Accessing only close key
                        String closePrice = jsonObject.getString("close");
                        //Adding to history list
                        historyList.add(closePrice);

                    }


                    // Passing back retrieved list
                    listener.onFinishedWeekly(historyList,coinNum);






                }catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                //Passing back null if some error happened
                listener.onFinishedWeekly(null,coinNum);
                t.printStackTrace();
            }
        });





    }




    // Get coin details for compare fragment
    @Override
    public void getBothCoinDetails(Contract.OnFinishedCoinCompare listener, Coin coin, int coinNum) {

        // Calling api method to get response of coin detail
        Call<JsonObject> call = apiService.getCoinData(coin.getSymbol(),currency);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()&&response.body()!=null) {
                    JsonElement displayElement = response.body().getAsJsonObject("DISPLAY").getAsJsonObject(coin.getSymbol()).get("INR");
                    String jsonResponse = displayElement.toString();

                    //Converting response from json to CoinDetail Object
                    CoinDetail coinDetail = new Gson().fromJson(jsonResponse, CoinDetail.class);

                    // Send bac list
                    listener.onFinishedCoinDetails(coinDetail,coinNum);


                }
                else{
                    listener.onFinishedCoinDetails(null,coinNum);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                //Passing back null if some error occurred
                listener.onFinishedCoinDetails(null,coinNum);
            }
        });



    }


}











// Async task class

 class longTask extends AsyncTask<Void,Void,List<Coin>> {

     private Contract.OnFinishedCoinIndex listener;
     private   Response<CoinIndexResponse> response;

     public longTask(Contract.OnFinishedCoinIndex listener, Response<CoinIndexResponse> response) {
         this.listener = listener;
         this.response=response;
     }

     @Override
     protected List<Coin> doInBackground(Void... voids) {

         // Getting Data Json Object which contain another JSON object contains detail of all coins
         JsonElement dataElement = response.body().getCoinList();

         // Converting it to string so we can create JSON Object from it
         String jsonResponse = dataElement.toString();

         // List to store coin index
         List<Coin> coinList= new ArrayList<>();

         try{

             // Json Object created from string
             JSONObject issueObj = new JSONObject(jsonResponse);

             // Iterator to iterate in whole response object using key
             Iterator iterator = issueObj.keys();

             // Taking only 200 Coin For List
             int i =0;


             // Until there is object inside response
             while(iterator.hasNext()&&i<201) {

                 // Getting name of json object
                 String key = (String) iterator.next();

                 // Retrieving json object using key name retrieved in last step
                 JSONObject issue = issueObj.getJSONObject(key);

                 // Convert josn object into COIN class object using GSON
                 Coin model = new Gson().fromJson(issue.toString(), Coin.class);

                 // Adding Coin to coinList
                 coinList.add(model);
                i++;
             }

             //Sorting list on the basis of ranking using Collections
             Collections.sort(coinList,(Coin c1,Coin c2)->{
                 int rank1 = Integer.parseInt(c1.getRanking());
                 int rank2 = Integer.parseInt(c2.getRanking());
                 return rank1-rank2;
             });

             // Final list in order from top till 100 only
             List<Coin> finalList = coinList.subList(0,200);


             // Passing back the retrieved coinList to Presenter
             return finalList;

         }
         catch (Exception e){
             // Error Found pass null so view can sow error
             e.printStackTrace();
             return null;

         }



     }

     @Override
     protected void onPostExecute(List<Coin> finalList) {

         listener.onFinishedCoinList(finalList);

     }


 }










