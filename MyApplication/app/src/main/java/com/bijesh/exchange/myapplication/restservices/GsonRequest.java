package com.bijesh.exchange.myapplication.restservices;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

//import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by laurentmeyer on 25/07/15.
 */
public class GsonRequest<T> extends JsonRequest<T> {
    private static final long serialVersionUID = 8161610343375187070L;

    private static final String TAG = GsonRequest.class.getSimpleName();
    private static final int RETRY_TIMEOUT = 80 * 1000;
    private final Gson gson = new Gson();
    private Class<T> clazz = null;
    private final Map<String, String> headers;
    // Used for request which do not return anything from the server
    private boolean muteRequest = false;
    private Type classType;
private boolean retry = true;
    /*private Response.Listener<T> mListener;
    private final String mRequestBody;

    protected static final String PROTOCOL_CHARSET = "utf-8";

    /*//** Content type for request. *//**//*
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);*/

    /**
     * Basically, this is the constructor which is called by the others.
     * It allows you to send an object of type A to the server and expect a JSON representing a object of type B.
     * The problem with the #JsonObjectRequest is that you expect a JSON at the end.
     * We can do better than that, we can directly receive our POJO.
     * That's what this class does.
     *
     * @param method:        HTTP Method
     * @param classtype:     Classtype to parse the JSON coming from the server
     * @param url:           url to be called
     * @param requestBody:   The body being sent
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param headers:       Added headers
     */
    private GsonRequest(int method, Class<T> classtype, String url, String requestBody,
                        Response.Listener<T> listener, Response.ErrorListener errorListener, Map<String, String> headers) {
        super(method, url, requestBody, listener,
                errorListener);
        clazz = classtype;
        this.headers = headers;
        /*mRequestBody = requestBody;
        mListener = listener;
        configureRequest();*/
    }

    /**
     * Method to be called if you want to send some objects to your server via body in JSON of the request (with headers and not muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param classtype:     Classtype to parse the JSON returned from the server
     * @param toBeSent:      Object which will be transformed in JSON via Gson and sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param headers:       Added headers
     */
    public GsonRequest(int method, String url, Class<T> classtype, Object toBeSent,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, Map<String, String> headers) {
        this(method, classtype, url, new Gson().toJson(toBeSent), listener,
                errorListener, headers);
        if(this.retry) {
            setRetryPolicy(new DefaultRetryPolicy(
                    RETRY_TIMEOUT,
                    1000,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public GsonRequest(int method, String url, Class<T> classtype, JSONObject toBeSent,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, Map<String, String> headers) {
        this(method, classtype, url, toBeSent.toString(), listener,
                errorListener, headers);

    }

    /**
     *
     * @param method
     * @param url
     * @param classtype
     * @param toBeSent
     * @param listener
     * @param errorListener
     * @param headers
     * @param flag
     */
    //TODO: REMOVE THIS METHOD ONCE REST CHANGES ARE DONE, this constructor is created to address search merchant API only
    public GsonRequest(int method, String url, Class<T> classtype, Object toBeSent,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, Map<String, String> headers, String flag) {
        this(method, classtype, url, "\"" + toBeSent.toString() + "\"", listener,
                errorListener, headers);

    }
    /**
     * Method to be called if you want to send some objects to your server via body in JSON of the request (without header and not muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param classtype:     Classtype to parse the JSON returned from the server
     * @param toBeSent:      Object which will be transformed in JSON via Gson and sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     */
    public GsonRequest(int method, String url, Class<T> classtype, Object toBeSent,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, classtype, url, new Gson().toJson(toBeSent), listener,
                errorListener, new HashMap<String, String>());
    }

    /**
     * Method to be called if you want to send something to the server but not with a JSON, just with a defined String (without header and not muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param classtype:     Classtype to parse the JSON returned from the server
     * @param requestBody:   String to be sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     */
//    public GsonRequest(int method, String url, Class<T> classtype, String requestBody,
//                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
//        this(method, classtype, url, getEncodedString(requestBody), listener,
//                errorListener, new HashMap<String, String>());
//    }

    /**
     * Method to be called if you want to GET something from the server and receive the POJO directly after the call (no JSON). (Without header)
     *
     * @param url:           URL to be called
     * @param classtype:     Classtype to parse the JSON returned from the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     */
    public GsonRequest(int method, String url, Class<T> classtype, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, url, classtype, "", listener, errorListener);
    }

    /**
     * Method to be called if you want to GET something from the server and receive the POJO directly after the call (no JSON). (With headers)
     *
     * @param url:           URL to be called
     * @param classtype:     Classtype to parse the JSON returned from the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param headers:       Added headers
     */
    public GsonRequest(int method, String url, Class<T> classtype, Response.Listener<T> listener, Response.ErrorListener errorListener, Map<String, String> headers) {
        this(method, classtype, url, "", listener, errorListener, headers);
    }

    /**
     * Method to be called if you want to send some objects to your server via body in JSON of the request (with headers and muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param classtype:     Classtype to parse the JSON returned from the server
     * @param toBeSent:      Object which will be transformed in JSON via Gson and sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param headers:       Added headers
     * @param mute:          Muted (put it to true, to make sense)
     */
    public GsonRequest(int method, String url, Class<T> classtype, Object toBeSent,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, Map<String, String> headers, boolean mute) {
        this(method, classtype, url, new Gson().toJson(toBeSent), listener,
                errorListener, headers);
        this.muteRequest = mute;
        setRetryPolicy(new DefaultRetryPolicy(
                RETRY_TIMEOUT,
                1000,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    /**
     * Method to be called if you want to send some objects to your server via body in JSON of the request (with headers and muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param classtype:     Classtype to parse the JSON returned from the server
     * @param toBeSent:      Object which will be transformed in JSON via Gson and sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param headers:       Added headers
     * @param mute:          Muted (put it to true, to make sense)
     */
    public GsonRequest(int method, String url, Class<T> classtype, Object toBeSent,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, Map<String, String> headers, boolean mute, boolean retry) {
        this(method, classtype, url, new Gson().toJson(toBeSent), listener,
                errorListener, headers);
        this.muteRequest = mute;
        this.retry = retry;
    }

    /**
     * Method to be called if you want to send some objects to your server via body in JSON of the request (without header and muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param classtype:     Classtype to parse the JSON returned from the server
     * @param toBeSent:      Object which will be transformed in JSON via Gson and sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param mute:          Muted (put it to true, to make sense)
     */
    public GsonRequest(int method, String url, Class<T> classtype, Object toBeSent,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, boolean mute) {
        this(method, classtype, url, new Gson().toJson(toBeSent), listener,
                errorListener, new HashMap<String, String>());
        this.muteRequest = mute;

    }

    /**
     * Method to be called if you want to send something to the server but not with a JSON, just with a defined String (without header and not muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param classtype:     Classtype to parse the JSON returned from the server
     * @param requestBody:   String to be sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param mute:          Muted (put it to true, to make sense)
     */
    public GsonRequest(int method, String url, Class<T> classtype, String requestBody,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, boolean mute) {
        this(method, classtype, url, requestBody, listener,
                errorListener, new HashMap<String, String>());
        this.muteRequest = mute;

    }

    public GsonRequest(int method, String url, Type classtype, Object toBeSent,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, Map<String, String> headers) {
        super(method, url, new Gson().toJson(toBeSent), listener,
                errorListener);
        classType = classtype;
        this.headers = headers;
        if(this.retry) {
            setRetryPolicy(new DefaultRetryPolicy(
                    RETRY_TIMEOUT,
                    1000,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public GsonRequest(int method, String url, Type classtype,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, Map<String, String> headers) {
        super(method, url, "", listener,errorListener);
        classType = classtype;
        this.headers = headers;
    }

    public GsonRequest(int method, String url,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, Map<String, String> headers, boolean mute) {
        super(method, url, "", listener,errorListener);
        this.muteRequest = mute;
        this.headers = headers;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {

        return super.parseNetworkError(volleyError);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        // The magic of the mute request happens here
        if (muteRequest) {
            if (response.statusCode >= 200 && response.statusCode <= 299) {
                // If the status is correct, we return a success but with a null object, because the server didn't return anything

                return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
            }
        } else {
            try {
                // If it's not muted; we just need to create our POJO from the returned JSON and handle correctly the errors
                String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                Log.d(TAG,"Response:: "+json);
//                json = forTempPurpose(json);
//                Log.d(TAG,"Response:: "+json);
                T parsedObject= null;
                if(clazz != null) {
                    parsedObject = gson.fromJson(json, clazz);
                }
                else if(classType != null){
                    Gson gson = new Gson();
                    List list = (List) gson.fromJson(json, classType);
                    parsedObject = (T) list;
                  //  BPCustomLog.d("String Json", "222 :" + list.get(0).toString());
                }
                return Response.success(parsedObject, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JsonSyntaxException e) {
                return Response.error(new ParseError(e));
            }
        }
        return null;
    }

//    private String forTempPurpose(String json){
//        if(json.contains("\\")){
//            return json.split("\\\\")[1];
//        }
//        return json;
//    }

//    private static String getEncodedString(String str){
//        String stringNew=str;
//        try {
//            stringNew= new StringEntity(str,"UTF-8").toString();
//        } catch (Exception e) {
//            stringNew=str;
//        }
//
//        return stringNew;
//    }
//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        BPCustomLog.d("Header Payee", "Header Payee : " + headers.get("Authorization"));
//        this.headers.put(BPMessageConstants.BP_HEADER_USER_AGENT, BPMessageConstants.BP_MOBILE_DEVICE_ANDROID + BPMessageConstants.BP_STRING_SPACE
//                + android.os.Build.VERSION.RELEASE + BPMessageConstants.BP_STRING_SPACE + android.os.Build.BRAND + BPMessageConstants.BP_STRING_SPACE + android.os.Build.DEVICE +
//                BPMessageConstants.BP_STRING_SPACE + BPMessageConstants.BP_APPLICATION_NAME + BPMessageConstants.BP_STRING_FORWARDSLASH + BPMessageConstants.BP_APPLICATION_VER + BPMessageConstants.BP_STRING_SPACE
//                + BPMessageConstants.BP_TIS_PLUGIN_VER);
//        BPCustomLog.d("Header Payee", "Header Payee : " + headers.get(BPMessageConstants.BP_HEADER_USER_AGENT));
//       this.headers.put(BPMessageConstants.BP_HEADER_CORRELATION_ID,getRandomString());
//        BPCustomLog.d("Header Payee", "Header Payee : " + headers.get(BPMessageConstants.BP_HEADER_CORRELATION_ID));
//        return headers;
//    }

    public static String getRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 32;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (new Random().nextFloat() * (rightLimit - leftLimit));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }
}