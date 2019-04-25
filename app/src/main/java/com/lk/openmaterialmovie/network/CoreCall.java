/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.network;

import android.support.annotation.NonNull;

import com.annimon.stream.function.Consumer;
import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.Strings;
import com.lk.openmaterialmovie.enums.NetworkResponse;
import com.lk.openmaterialmovie.helpers.Dialogue;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.log.Logger;
import com.lk.openmaterialmovie.ui.activities.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoreCall<ResponseType> implements Callback<ResponseType> {

    private Consumer<RawResponse<ResponseType>> responseCommand;
    private Request request;

    /**
    Mock constructor only
     */
    public CoreCall() {
    }

    public CoreCall(Request request, Consumer<RawResponse<ResponseType>> responseCommand) {
        showProgress();
        this.responseCommand = responseCommand;
        this.request = request;
    }

    private static void showProgress() {
        Ui.getActivity().progressShow();
    }

    private static void hideProgress() {
        Ui.getActivity().progressHide();
    }

    //TODO: Use this for error stream read later
    public static void onError(BaseActivity activity, HttpURLConnection httpURLConnection) throws IOException {
        hideProgress();
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
        String line;
        StringBuilder output = new StringBuilder();
        while ((line = in.readLine()) != null) {
            output.append(line);
        }
        activity.runOnUiThread(() -> Dialogue.show(Ui.getString(R.string.common_error), output.toString(), null));
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull Throwable t) {
        hideProgress();
        RawResponse rawResponse = new RawResponse();
        boolean showSnackbarMessage = false;
        if (request != null) {
            rawResponse.setRequest(request);
            Logger.d(Strings.Log.RestApi.REQUEST_STRING + request);
        }
        if (t instanceof SocketTimeoutException) {
            rawResponse.setNetworkResponseStatus(NetworkResponse.TIMEOUT);
            //  rawResponse.setErrorString(UiContainer.getBaseContext().getString(R.string.error_request_timed_out));
    /*        UiContainer.getActivity().runOnUiThread(() -> {
                Context context = UiContainer.getBaseContext();
                DialogHelper.showSimplestDialog(UiContainer.getActivity(), context.getString(R.string.error), context.getString(R.string.error_request_timed_out), null);
            });*/
        } else if (t instanceof IOException) {
            if (t instanceof UnknownHostException) {
             /*   UiContainer.getActivity().runOnUiThread(() -> {
                    Context context = UiContainer.getBaseContext();
                    DialogHelper.showSimplestDialog(UiContainer.getActivity(), context.getString(R.string.error), context.getString(R.string.error_request_no_internet_connection), null);
                });*/
                rawResponse.setNetworkResponseStatus(NetworkResponse.NO_CONNECTION);
                //rawResponse.setErrorString(UiContainer.getBaseContext().getString(R.string.error_request_no_internet_connection));
            } else {
                // possibly not only connection problems will get in this case, but full list of connection related exceptions is unclear
                rawResponse.setNetworkResponseStatus(NetworkResponse.NO_CONNECTION);
                showSnackbarMessage = request == null || !request.toString().contains("validateDevice");
            }
        } else {
            rawResponse.setNetworkResponseStatus(NetworkResponse.ERROR_UNEXPECTED);
        }
        if (showSnackbarMessage) {
            //TODO: Show error here
        }
        String message = t.getMessage();
        rawResponse.setErrorString(message);
        Logger.w(Strings.Log.RestApi.RESPONSE_FAILURE + (message != null ? message : t.getClass()));
        responseCommand.accept(rawResponse);
    }

    @Override
    public void onResponse(@NonNull Call<ResponseType> call, @NonNull Response<ResponseType> response) {
        RawResponse rawResponse = new RawResponse<ResponseType>();
        if (request != null) {
            rawResponse.setRequest(request);
            Logger.d(Strings.Log.RestApi.REQUEST_STRING + request);
        }
        Logger.d(Strings.Log.RestApi.RESPONSE_CODE + response.code());
        Logger.d(Strings.Log.RestApi.RESPONSE_BODY + response.body());
        // We need to store error body cause if we read it once it will be cleared
        // And this must be done through call of string() method cause it's closes related resource
        if (response.errorBody() != null) {
            try {
                JSONObject errorObject = new JSONObject(response.errorBody().string());
                String errorField = "error";
                String messageDescription = "message_description";
                if (errorObject.has(errorField)) {
                    rawResponse.setErrorString(errorObject.getString(errorField));
                } else if (errorObject.has(messageDescription)) {
                    rawResponse.setErrorString(errorObject.getString(messageDescription));
                }
            } catch (IOException e) {
                Logger.e(Strings.Log.FAIL);
            } catch (JSONException e) {
                Logger.e(e.getMessage());
            }
        }
        switch (response.code()) {
            // Generic cases
            case HttpURLConnection.HTTP_NO_CONTENT:
            case HttpURLConnection.HTTP_CREATED:
            case HttpURLConnection.HTTP_OK:
                rawResponse.setNetworkResponseStatus(NetworkResponse.SUCCESS);
                if (response.body() != null) {
                    rawResponse.setData(response.body());
                }
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                rawResponse.setNetworkResponseStatus(NetworkResponse.NOT_FOUND);
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
            case HttpURLConnection.HTTP_BAD_GATEWAY:
            case HttpURLConnection.HTTP_UNAVAILABLE:
            case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                rawResponse.setNetworkResponseStatus(NetworkResponse.SERVER_ERROR);
                break;
            // Custom cases
            case HttpURLConnection.HTTP_BAD_REQUEST:
                // Strangely server returns 400 error code if signup request failed, e.g. user already exists
                rawResponse.setNetworkResponseStatus(NetworkResponse.SIGNUP_REQUEST_FAILED);
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                rawResponse.setNetworkResponseStatus(NetworkResponse.AUTH_FAILED);
                break;
            // Default case. Shouldn't be reached
            default:
                Logger.e(Strings.Log.DEFAULT_CASE_REACHED + response.code());
                rawResponse.setNetworkResponseStatus(NetworkResponse.ERROR_UNEXPECTED);
                break;
        }
        if (responseCommand != null) {
            responseCommand.accept(rawResponse);
        }
        hideProgress();
    }
}
