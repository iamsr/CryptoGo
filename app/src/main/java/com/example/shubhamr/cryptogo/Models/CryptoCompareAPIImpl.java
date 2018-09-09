package com.example.shubhamr.cryptogo.Models;

import android.content.Context;
import android.util.Log;

import com.example.shubhamr.cryptogo.Interface.ApiInterface;
import com.example.shubhamr.cryptogo.Interface.Contract;
import com.example.shubhamr.cryptogo.ModelClasses.Coin;
import com.example.shubhamr.cryptogo.ModelClasses.CoinIndexResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class CryptoCompareAPIImpl implements Contract.CryptoCompareAPI {

    private Context context;


    public CryptoCompareAPIImpl(Context context){
        this.context = context;
    }



    @Override
    public void getCoinIndex(final Contract.OnFinishedCoinIndex listener) {

        // Retrieving retrofit instance
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        // Calling api method to get response of coin index query
        Call<CoinIndexResponse> call = apiService.getCoinList();


        call.enqueue(new Callback<CoinIndexResponse>() {
            @Override
            public void onResponse(Call<CoinIndexResponse> call, Response<CoinIndexResponse> response) {

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

                // Until there is object inside response
                while(iterator.hasNext()) {

                    // Getting name of json object
                    String key = (String) iterator.next();

                    // Retrieving json object using key name retrieved in last step
                    JSONObject issue = issueObj.getJSONObject(key);

                    // Convert josn object into COIN class object using gson
                    Coin model = new Gson().fromJson(issue.toString(), Coin.class);

                    // Adding Coin to coinList
                    coinList.add(model);

                }
                 // Passing back the retrieved coinList to Presenter
                 listener.onFinishedCoinList(coinList);

             }
                catch (Exception e){
                  // Error Found pass null so view can sow error
                  listener.onFinishedCoinList(null);
                 e.printStackTrace();
                }


                }

            @Override
            public void onFailure(Call<CoinIndexResponse> call, Throwable t) {
                // Pass back null so view can show error on screen
                t.printStackTrace();
                listener.onFinishedCoinList(null);
            }
        });


    }


}
