package com.bijesh.exchange.myapplication.restservices.asynctasks;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.bijesh.exchange.myapplication.MainActivity;
import com.bijesh.exchange.myapplication.R;
import com.bijesh.exchange.myapplication.fragments.HomeFragment;
import com.bijesh.exchange.myapplication.models.webservicemodels.Stock;
import com.bijesh.exchange.myapplication.models.webservicemodels.StockData;
import com.bijesh.exchange.myapplication.utils.JsonPojoConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Bijesh on 10/23/2016.
 */

public class DownloadStockDataTask extends AsyncTask<List<String>,Void,List<StockData>> {

    private static final String TAG = DownloadStockDataTask.class.getCanonicalName();
    private List<StockData> mStockList = new ArrayList<>();
    private Context mContext;

    public DownloadStockDataTask(Context context){
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<StockData> doInBackground(List<String>... params) {
        List<String> urls = params[0];
        mStockList = callWebServiceViaHttp(urls);
        return mStockList;
    }

    @Override
    protected void onPostExecute(List<StockData> stockDatas) {
        super.onPostExecute(stockDatas);
//        populateUI(stockDatas);
        notifyUser(stockDatas);
    }

    private void notifyUser(List<StockData> stockDatas){
        for(StockData stockData:stockDatas){
            if(stockData != null) {
                Stock stock = stockData.getStock().get(0);
                if (stock != null){
                    if (Double.parseDouble(stock.getPChange()) < -3 || Double.parseDouble(stock.getPChange()) > 3){
                        initializeNotification(stock);
                    }
                }
            }
        }
    }

    private int getNotificationIcon(String perChange){
        Double percentageChange = Double.parseDouble(perChange);
        if(percentageChange < 0){
            return R.drawable.bear_notify;
        }else
            return R.drawable.bull_notify;
    }

    private void initializeNotification(Stock stock){
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(mContext)
//                        .setSmallIcon(R.drawable.bear_notify)
//                        .setContentTitle(stock.getCompanyName())
//                        .setContentText("Hello World!");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(getNotificationIcon(stock.getPChange()));
        mBuilder.setContentTitle(stock.getCompanyName());
        mBuilder.setContentText("Watch out "+stock.getCompanyName()+" Moving bearish/bullish");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(9999, mBuilder.build());
    }



    private List<StockData> callWebServiceViaHttp(List<String> urls){

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpsURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;
        for(String urlPath:urls) {
            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = null;
                url = new URL(urlPath);
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
                urlConnection.setRequestProperty("Accept", "*/*");
                urlConnection.connect();

                // Read the input stream into a String
                int statusCode = urlConnection.getResponseCode();
                Log.d(TAG, "$$$ response code " + statusCode);
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.

                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
//                return null;
                }
                forecastJsonStr = buffer.toString();
                Log.d(TAG, "$$$ response from the server " + forecastJsonStr);
                StockData stockData = (StockData)JsonPojoConverter.convertJsonToPojo(forecastJsonStr,StockData.class);
                mStockList.add(stockData);
//            synchronized (waitingObject) {
//                waitingObject.notifyAll();
//            }
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
//            return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }
        return mStockList;
    }


}
