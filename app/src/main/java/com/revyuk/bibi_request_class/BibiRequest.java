package com.revyuk.bibi_request_class;

import android.content.Context;
import android.util.Xml;

import com.loopj.android.http.*;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import java.io.UnsupportedEncodingException;

/**
 * Created by Vitaly Revyuk on 28.03.2015.
 */

public class BibiRequest {
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String PUT_METHOD = "PUT";
    private static final String DELETE_METHOD = "DELETE";

    public static final String DEFAULT_API_URL = "http://bibi.cloudapp.net:8000";
    public static final String COMMENTS = "comments";
    public static final String CUSTOMERS = "customers";
    public static final String FAV_LOCATION = "favorite_locations";
    public static final String FAV_ROUTES = "favorite_routes";
    public static final String ROUTES = "routes";
    public static final String ORDERS = "orders";
    public static final String SHEDULES = "shedules";

    private Context context;
    private Callback callback;
    private String apiUrl;
    private AsyncHttpClient client = new AsyncHttpClient();

    /**
     * Interface of BibiRequest class
     */
    public interface Callback {
        public void bibiResponse(int who, String responseMsg);
        public void bibiResponseFailure(int who, int responseCode, String responseMsg);
    };

    /**
     * PUBLIC CONSTRUCTOR
     * @param context context of call
     */
    public BibiRequest(Context context) {
        this(context, null);
    }

    /**
     * PUBLIC CONSTRUCTOR
     * @param context context of call
     */
    public BibiRequest(Context context, String apiServerRootUrl) {
        this.context = context;
        this.apiUrl = (apiServerRootUrl==null || apiServerRootUrl.isEmpty())?DEFAULT_API_URL:apiServerRootUrl;
        try {
            callback = (Callback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("You have implement BibiRequest.Callback interface.");
        }
    }

    public void postComment(int who, String jsonString) {
        httpRequest(POST_METHOD, composeUrl(COMMENTS), jsonString, new BibiResponseHandler(who));
    }

    public void getCustomers(int who) {
        httpRequest(GET_METHOD, composeUrl(CUSTOMERS), null, new BibiResponseHandler(who));
    }

    public void getCustomer(int who, String customerID) {
        httpRequest(GET_METHOD, composeUrl(CUSTOMERS, customerID), null, new BibiResponseHandler(who));
    }

    public void postCustomer(int who, String jsonString) {
        httpRequest(POST_METHOD, composeUrl(CUSTOMERS), jsonString, new BibiResponseHandler(who));
    }

    public void putCustomer(int who, String customerID, String jsonString) {
        httpRequest(PUT_METHOD, composeUrl(CUSTOMERS, customerID), jsonString, new BibiResponseHandler(who));
    }

    public void putCustomerCar(int who, String customerID, String jsonString) {
        httpRequest(PUT_METHOD, composeUrl(CUSTOMERS, customerID, "car"), jsonString, new BibiResponseHandler(who));
    }

    public void postCustomerFavLocation(int who, String customerID, String jsonString) {
        httpRequest(POST_METHOD, composeUrl(CUSTOMERS, customerID, "favorite_locations"), jsonString, new BibiResponseHandler(who));
    }

    public void postCustomerFavRoutes(int who, String customerID, String jsonString) {
        httpRequest(POST_METHOD, composeUrl(CUSTOMERS, customerID, "favorite_routes"), jsonString, new BibiResponseHandler(who));
    }

    public void getCustomerPosition(int who, String customerID) {
        httpRequest(GET_METHOD, composeUrl(CUSTOMERS, customerID, "position"), null, new BibiResponseHandler(who));
    }

    public void putCustomerPosition(int who, String customerID, String jsonString) {
        httpRequest(PUT_METHOD, composeUrl(CUSTOMERS, customerID, "position"), jsonString, new BibiResponseHandler(who));
    }

    public void getCustomerState(int who, String customerID) {
        httpRequest(GET_METHOD, composeUrl(CUSTOMERS, customerID, "state"), null, new BibiResponseHandler(who));
    }

    public void putCustomerState(int who, String customerID, String jsonString) {
        httpRequest(PUT_METHOD, composeUrl(CUSTOMERS, customerID, "state"), jsonString, new BibiResponseHandler(who));
    }

    public void deleteFavoriteLocation(int who, String locationID) {
        httpRequest(DELETE_METHOD, composeUrl(FAV_LOCATION, locationID), null, new BibiResponseHandler(who));
    }

    public void putFavoriteLocation(int who, String locationID, String jsonString) {
        httpRequest(PUT_METHOD, composeUrl(CUSTOMERS, locationID), jsonString, new BibiResponseHandler(who));
    }

    public void deleteFavoriteRoute(int who, String routeID) {
        httpRequest(DELETE_METHOD, composeUrl(FAV_ROUTES, routeID), null, new BibiResponseHandler(who));
    }

    public void putFavoriteRoute(int who, String routeID, String jsonString) {
        httpRequest(PUT_METHOD, composeUrl(FAV_ROUTES, routeID), jsonString, new BibiResponseHandler(who));
    }

    public void getRoutes(int who, String jsonString) {
        httpRequest(GET_METHOD, composeUrl(ROUTES), jsonString, new BibiResponseHandler(who));
    }

    public void postRoutes(int who, String jsonString) {
        httpRequest(POST_METHOD, composeUrl(ROUTES), jsonString, new BibiResponseHandler(who));
    }

    public void deleteRoutes(int who, String routeID) {
        httpRequest(DELETE_METHOD, composeUrl(ROUTES, routeID), null, new BibiResponseHandler(who));
    }

    public void getRoute(int who, String routeID) {
        httpRequest(GET_METHOD, composeUrl(ROUTES, routeID), null, new BibiResponseHandler(who));
    }

    public void putRoute(int who, String routeID, String jsonString) {
        httpRequest(PUT_METHOD, composeUrl(ROUTES, routeID), jsonString, new BibiResponseHandler(who));
    }

    public void getRoutePassengers(int who, String routeID) {
        httpRequest(GET_METHOD, composeUrl(ROUTES, routeID, "passengers"), null, new BibiResponseHandler(who));
    }

    public void getRouteState(int who, String routeID) {
        httpRequest(GET_METHOD, composeUrl(ROUTES, routeID, "state"), null, new BibiResponseHandler(who));
    }

    public void putRouteState(int who, String routeID, String jsonString) {
        httpRequest(PUT_METHOD, composeUrl(ROUTES, routeID, "state"), jsonString, new BibiResponseHandler(who));
    }

    public void postOrder(int who, String jsonString) {
        httpRequest(POST_METHOD, composeUrl(ORDERS), jsonString, new BibiResponseHandler(who));
    }

    public void getOrder(int who, String orderID) {
        httpRequest(GET_METHOD, composeUrl(ORDERS, orderID), null, new BibiResponseHandler(who));
    }

    public void getOrderState(int who, String orderID) {
        httpRequest(GET_METHOD, composeUrl(ORDERS, orderID, "state"), null, new BibiResponseHandler(who));
    }

    public void putOrderState(int who, String orderID, String jsonString) {
        httpRequest(PUT_METHOD, composeUrl(ORDERS, orderID, "state"), jsonString, new BibiResponseHandler(who));
    }

    public void getShedules(int who) {
        httpRequest(GET_METHOD, composeUrl(SHEDULES), null, new BibiResponseHandler(who));
    }

    public void postShedule(int who, String jsonString) {
        httpRequest(POST_METHOD, composeUrl(SHEDULES), jsonString, new BibiResponseHandler(who));
    }

    public void deleteShedule(int who, String sheduleID) {
        httpRequest(DELETE_METHOD, composeUrl(SHEDULES, sheduleID), null, new BibiResponseHandler(who));
    }

    public void getShedule(int who, String sheduleID) {
        httpRequest(GET_METHOD, composeUrl(SHEDULES, sheduleID), null, new BibiResponseHandler(who));
    }

    public void putShedule(int who, String sheduleID, String jsonString) {
        httpRequest(PUT_METHOD, composeUrl(SHEDULES, sheduleID), jsonString, new BibiResponseHandler(who));
    }


    /**
     * PRIVATE SECTION
     * INNER CLASSES
     *
     */

    private class BibiResponseHandler extends AsyncHttpResponseHandler {
        private int who;

        public BibiResponseHandler(int who) {
            this.who = who;
        }

        public int getWho() { return who; }

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            if(callback != null) callback.bibiResponse(who, new String(bytes));
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            if(callback != null) callback.bibiResponseFailure(who, i, new String(bytes));
        }
    }

    private String composeUrl(String... params) {
        String str = apiUrl;
        for (int i=0; i<params.length; i++) {
            str += "/"+params[i];
        }
        return str;
    }

    private void httpRequest(String method, final String url, String params, final BibiResponseHandler handler) {

        final String _url = (url==null || url.isEmpty())?DEFAULT_API_URL:url;
        final String _method = method==null?GET_METHOD:method;
        final String _params = params==null?"":params;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.setConnectTimeout(CONNECTION_TIMEOUT);
                    client.addHeader("Charset", "UTF-8");
                    client.setResponseTimeout(CONNECTION_TIMEOUT);
                    HttpEntity entity;

                    entity = new StringEntity(_params);

                    switch (_method.toUpperCase()) {
                        case GET_METHOD:
                            client.get(context, _url, handler);
                            break;
                        case POST_METHOD:
                            client.post(context, _url, entity, "application/json", handler);
                            break;
                        case PUT_METHOD:
                            client.put(context, _url, entity, "application/json", handler);
                            break;
                        case DELETE_METHOD:
                            client.delete(context, _url, handler);
                            break;
                        default:
                            break;
                    }
                } catch (UnsupportedEncodingException | NullPointerException e) {
                    if(callback != null) callback.bibiResponseFailure(handler.getWho(), -1, e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
