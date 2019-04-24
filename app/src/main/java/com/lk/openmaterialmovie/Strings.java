package com.lk.openmaterialmovie;

public class Strings {

    public static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static class Log {
        public static final String EXCEPTION = "Exception";
        public static final String PLEASE_PROVIDE_READ_API_ACCESS_TOKEN = "Please provide read api access token";
        public static final String RETROFIT_FAILURE = "Retrofit failure";
        public static final String DEFAULT_CASE_REACHED = "Impossible default case. Something is wrong ";
        public static final String FAIL = "Error occurred ";
        public static final String CALENDAR_RANGE_SELECTION_FAIL = "Incorrect data passed to calendar select range method. Something went wrong!";
        public static final String INVALID_ENUM_VALUE = "Invalid enum value: ";
        public static final String NEW_PUSH_TOKEN = "New push token: ";
        public static final String CAUGHT_EXCEPTION_DURING_LAUNCH_OF_KILLED_APP = "Following exception caught during launch of killed app. That's OK, just for info:\n";

        public static class RestApi {
            public static final String REQUEST_STRING = "RestAPI request is: ";
            public static final String RESPONSE_CODE = "RestAPI response code: ";
            public static final String RESPONSE_BODY = "RestAPI response body: ";
            public static final String REQUEST_BODY = "RestAPI response body: ";
            public static final String RESPONSE_ERROR_BODY = "RestAPI response error body: ";
            public static final String RESPONSE_STATUS = "RestAPI response status is: ";
            public static final String RESPONSE_FAILURE = "RestAPI response onFailure called: ";
            public static final String DEVICE_VALIDATION_RESPONSE = "RestAPI device validation response is: ";
        }
    }
}
