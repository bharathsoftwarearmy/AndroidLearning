package com.bijesh.exchange.myapplication.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bijesh.exchange.myapplication.BaseApplication;
import com.bijesh.exchange.myapplication.R;
import com.bijesh.exchange.myapplication.adapters.listviewadapters.HomeScreenAdapter;
import com.bijesh.exchange.myapplication.constants.ApplicationConstants;
import com.bijesh.exchange.myapplication.contentproviders.MyApplicationDBHandler;
import com.bijesh.exchange.myapplication.dialogs.ApplicationProgressDialog;
import com.bijesh.exchange.myapplication.models.adaptermodels.StockChange;
import com.bijesh.exchange.myapplication.models.dbmodels.Share;
import com.bijesh.exchange.myapplication.models.webservicemodels.StockData;
import com.bijesh.exchange.myapplication.restservices.GsonRequest;
import com.bijesh.exchange.myapplication.utils.DBUtils;
import com.bijesh.exchange.myapplication.utils.StringUtil;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by bijesh on 5/21/2016.
 */
public class HomeFragment extends BaseFragment implements Response.Listener, Response.ErrorListener, ApplicationConstants{


    private static final String TAG = HomeFragment.class.getSimpleName();
    private List<StockData> mStockList = new ArrayList<>();
    private View mRootView;
    private ListView mStockListView;
//    private ApplicationProgressDialog mProgressDialog;
//    private ProgressDialog mProgressDialog;
    private Object waitingObject;
    private ImageView imgViewAddShare;
    private RelativeLayout relativeLayoutShareInput;
    private Button mBtnAddShare;
    private EditText mEdtTxtShare;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    List<String> mUrls;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        if(mRootView == null){
            waitingObject = new Object();
            mRootView = inflater.inflate(R.layout.home_fragment,container,false);
            initComponents(mRootView);
//            callWebService();
//            callWebService1();
//            callWebServiceViaHttp();
        }
        return mRootView;
    }

    private void addShare(Share share){
        MyApplicationDBHandler dbHandler = BaseApplication.getDBHandler();
        dbHandler.insertShare(share);
//        Share askShare = dbHandler.getShare("ASHOKLEY");
//        Log.d(TAG,"$$$ share retrieved "+askShare);
    }



    private void initComponents(View mRootView){

        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_refresh_layout);

        mStockListView = (ListView) mRootView.findViewById(R.id.stockList);
        imgViewAddShare = (ImageView) mRootView.findViewById(R.id.imgViewAddShare);
        relativeLayoutShareInput = (RelativeLayout) mRootView.findViewById(R.id.inputShareDetailContainer);
        mBtnAddShare = (Button) mRootView.findViewById(R.id.btnAddShare);
        mEdtTxtShare = (EditText) mRootView.findViewById(R.id.edtTxtShareSymbol);




        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadStockData();
            }
        });



        imgViewAddShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relativeLayoutShareInput.getVisibility() == View.VISIBLE){
                    relativeLayoutShareInput.setVisibility(View.GONE);
                    mEdtTxtShare.setText("");
                }else {
                    relativeLayoutShareInput.setVisibility(View.VISIBLE);
                }
            }
        });

        mBtnAddShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share share = new Share();
                share.setShareSymbol(mEdtTxtShare.getText().toString().toUpperCase().trim());
                addShare(share);
                relativeLayoutShareInput.setVisibility(View.GONE);
                mEdtTxtShare.setText("");
                mUrls = StringUtil.getShareUrls(DBUtils.getAllShare());
                mSwipeRefreshLayout.setRefreshing(true);
                downloadStockData();
            }
        });

        mStockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"Item selected",Toast.LENGTH_LONG);
                ShareDetailFragment shareDetailFragment = new ShareDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(BUNDLE_STOCK,mStockList.get(position).getStock().get(0));
                shareDetailFragment.setArguments(bundle);
                fragmentTransaction(R.id.mainContainer,shareDetailFragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mUrls = StringUtil.getShareUrls(DBUtils.getAllShare());
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         mSwipeRefreshLayout.setRefreshing(true);
                                         downloadStockData();
                                     }
                                 }
        );
    }

    private void attachAdapter(List<StockData> stockData){
        HomeScreenAdapter homeScreenAdapter = new HomeScreenAdapter(getContext(),R.layout.stock_list_item,stockData);
        mStockListView.setAdapter(homeScreenAdapter);
    }

    private void downloadStockData(){
        DownloadStockDataTask task = new DownloadStockDataTask();
//        String tempUrl = StringUtil.getShareUrl();
        task.execute(mUrls);
    }

//    private List<String> getUrls(){
//        List<String> urls = new ArrayList<>();
////
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=ASHOKLEY");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=INOXWIND");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=GOKEX");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=MAWANASUG");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=METALFORGE");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=RCOM");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=KCPSUGIND");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=MANDHANA");
//        urls.add( "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=SHRENUJ");
//        return urls;
//    }


    private void callWebService(){
//        public GsonRequest(int method, String url, Class<T> classtype, String requestBody,
//                Response.Listener<T> listener, Response.ErrorListener errorListener, boolean mute)
//        String url = "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=dhfl";
//        String url = "http://finance.google.com/finance/info?client=ig&q=NSE:DHFL";
//
        String url = "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=ASHOKLEY";
        gsonRequest = new GsonRequest(Request.Method.GET,url, null, new String(), this, this, getHeaders());
        requestQueue.add(gsonRequest);
    }

    private Map<String,String> getHeaders(){
        Map<String,String> header = new HashMap<>();
        header.put("User-Agent","Mozilla/5.0 ( compatible ) ");
        header.put("Accept","*/*");
        return header;
    }

    private void callWebService1(){
//        mProgressDialog = new ApplicationProgressDialog(this.getActivity());
//        mProgressDialog.show();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//               callWebServiceViaHttp();
//            }
//        }).start();
//        synchronized (waitingObject) {
//            try {
//                waitingObject.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        populateUI();
    }

    private void populateUI(List<StockData> stockData){
//        if(mProgressDialog != null){
//            mProgressDialog.dismiss();
//        }
        mSwipeRefreshLayout.setRefreshing(false);
        attachAdapter(stockData);
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
                StockData stockData = getStockData(forecastJsonStr);
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

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Object response) {
         if(response != null){
             Log.d(TAG,"$$$ response "+response.toString());
//             Stock stock = (Stock)response;
         }
    }

    private StockData getStockData(String responseJson){
//        T parsedObject= null;
//        if(clazz != null) {
//            parsedObject = gson.fromJson(json, clazz);
//        }
        Gson gson = new Gson();
        StockData stockData = null;
        stockData = gson.fromJson(responseJson,StockData.class);
        Log.d(TAG,"$$$ reponse as object "+stockData);
        return stockData;
    }


    private class DownloadStockDataTask extends AsyncTask<List<String>,Void,List<StockData>>{


//        @Override
//        protected List<StockData> doInBackground(List<String>... params) {
//            return null;
//        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mProgressDialog = new ProgressDialog(HomeFragment.this.getActivity());
//            mProgressDialog.show(); ,...
            mStockList.clear();
            mStockListView.setAdapter(null);
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
            populateUI(stockDatas);
        }


    }



//    private void callValidatePaymentService() {
//        setValidateRequestObject();
//        params.clear();
//        screen = ApplicationProgressDialog.show(getActivity(), "", BPMessageConstants.BP_VALIDATE_PAYMENTS_MESSAGE, true);
//        String url = securePref.getString(BPMessageConstants.BP_HOST_URLPATH) + BPMessageConstants.BP_SENDMONEY_VALIDATE_PAYMENT;
//        gsonRequest = new GsonRequest(Request.Method.POST, VolleyUtil.getUrl(params, securePref, url), BillpayValidatePaymentResponse.class, validatePaymentRequest, this, this, VolleyUtil.getHeaders(securePref));
//        requestQueue.add(gsonRequest);
//    }
}
