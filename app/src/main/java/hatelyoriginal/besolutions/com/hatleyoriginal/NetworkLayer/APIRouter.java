package hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

/**
 * @desc Java APIRouter Class for Managing Different Types of Network Calls
 */

class APIRouter {

    private Context context;
    private NetworkInterface networkInterface;
    private TinyDB tinyDB;


    APIRouter(Context context, NetworkInterface networkInterface) {
        this.context = context;
        this.networkInterface = networkInterface;

        tinyDB = new TinyDB(context);
    }


    void performRequest(final String URL, final List<String> params, final List<String> values, final int method, final int responseCode) {
        Log.e("url", URL);
        networkInterface.OnStart();

        StringRequest stringRequest = new StringRequest(method, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ResponseModel model = new ResponseModel(responseCode, response);
                        networkInterface.OnResponse(model);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                networkInterface.OnError(error);

            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                if (params != null && values != null) {
                    for (int i = 0; i < params.size(); i++) {
                        hashMap.put(params.get(i), values.get(i));
                    }
                }

                return hashMap;
            }
        };
        stringRequest.setShouldCache(false);
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }


    void makeSimpleRequest(String url) {

        networkInterface.OnStart();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ResponseModel model = new ResponseModel(0, response);

                        networkInterface.OnResponse(model);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        networkInterface.OnError(error);
                    }
                });
        stringRequest.setShouldCache(false);
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }


    public void makeAdvancedRequest(String url, final int method, final List<String> params, final List<String> values, final HashMap<String, String> body) throws JSONException {
        Log.e("url", url);

        networkInterface.OnStart();

        JSONObject object = new JSONObject();
        if (params != null && values != null) {
            for (int index = 0; index < params.size(); index++) {
                object.put(params.get(index), values.get(index));
            }
        }


        JsonObjectRequest sr = new JsonObjectRequest(method, url, object,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ResponseModel model = new ResponseModel(0, response);

                        networkInterface.OnResponse(model);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                networkInterface.OnError(error);

            }

        }) {
            /*  @Override
              protected Map<String, String> getParams(){
                  HashMap<String, String> hashMap = new HashMap<>();
                  if(params != null && values!=null)
                  {
                      for(int i = 0; i<params.size();i++)
                      {
                          Log.e("params",params.get(i)+","+values.get(i));
                          hashMap.put(params.get(i),values.get(i));
                      }
                  }
                  return hashMap;
              }
  */
            @Override
            public Map<String, String> getHeaders() {
                final HashMap<String, String> header = new HashMap<>();
                header.put("Accept", "application/json");
                header.put("Content-Type", "application/json");
               //header.put("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImRiNjU0NzMwM2MwNDVkYTA3NzJlODg3YzkzOGUxZWM4NGVjNWI2NWQ0NWIxZjJjMjM5ZjBiMmZmMjk4ZDVjYTBkMmM0NTY5MjQyMzJkMDRiIn0.eyJhdWQiOiIzIiwianRpIjoiZGI2NTQ3MzAzYzA0NWRhMDc3MmU4ODdjOTM4ZTFlYzg0ZWM1YjY1ZDQ1YjFmMmMyMzlmMGIyZmYyOThkNWNhMGQyYzQ1NjkyNDIzMmQwNGIiLCJpYXQiOjE1NzUyOTI1OTQsIm5iZiI6MTU3NTI5MjU5NCwiZXhwIjoxNjA2OTE0OTk0LCJzdWIiOiI3NCIsInNjb3BlcyI6W119.PaI93yVANWNH8YXBhw0_O8x_YOA0M6_5cdkGEO70gqA5zzqwEQW3-EnRS2s3GDKU9rO4-GRmWnHZZpwBtna1dTfh5kpEyLkRCXGZCPhJdkZgiTP1Gk20gLu6quNQodEmWK1QtRPkqVF0ppct-9w8q9Q3mRl_ptBzx1Y_ewdJ-q1CI91SrkRQgzoKhhowq31AZcUSTPNgQ-a4wQg-WEAafUeLQKVqx1hwMLWOlyBa9Ycu3B9pntws6j6p_jBP3l0H3Xg57uNOQiZWqduPAfe2h2k-GvbAUeDorAZIvI-ftzoZOFYphSrG0VOzYl8xhODxeaM_hyNzBUrJA2r2sx1wG-Ws0OCQJgbHF5AGPlvrdfX0IT57u0G39VL6s2dWtjOGV2PHz6V8uixDSJQDdeB6Pbf3w7-Bvbaw8StKxp_D2d6lPWpTsM1YNn1pKEwXxo9OgN5wYfK3ttQ3WznjkOuEekmkhQZkO8o_7y6bgxKSJFJd3XaLPAWtQl7Sq1sv860ZFz1-HBrFhfzqKtHzWAS03SbCQN9_3gBhAXRPnHGleK0t90azMxUB-CuDFZBP966-_dfkUw6seSR6p8KllBcLtDnYCzESB62spR_6z0Lpu-_FUATYHU-JqgImz1yphhRpX3r-FriikZYU-CzrFZs_zBy-I4unWlXODb1vNfEN6VY");
                header.put("Authorization", "Bearer " + tinyDB.getString("userToken"));
                return header;
            }


        };
        sr.setShouldCache(false);
        sr.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(context).addToRequestQueue(sr);
    }
}
