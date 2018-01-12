package org.svishnyakov.bux.bot;

public class ConnectionConfig {

    private static final String API_URL = "https://api.beta.getbux.com/";
    private static final String SUBSCRIPTION_URL = "wss://rtf.beta.getbux.com/subscriptions/me";

    private static final String HEADER = "Bearer " +
            "eyJhbGciOiJIUzI1NiJ9.eyJyZWZyZXNoYWJsZSI6ZmFsc2UsInN1YiI6ImJiMGNkYTJiLWE" +
            "xMGUtNGVkMy1hZDVhLTBmODJiNGMxNTJjNCIsImF1ZCI6ImJldGEuZ2V0YnV4LmNvbSIsInN" +
            "jcCI6WyJhcHA6bG9naW4iLCJydGY6bG9naW4iXSwiZXhwIjoxODIwODQ5Mjc5LCJpYXQiOjE" +
            "1MDU0ODkyNzksImp0aSI6ImI3MzlmYjgwLTM1NzUtNGIwMS04NzUxLTMzZDFhNGRjOGY5MiI" +
            "sImNpZCI6Ijg0NzM2MjI5MzkifQ.M5oANIi2nBtSfIfhyUMqJnex-JYg6Sm92KPYaUL9GKg";

    public static String getApiUrl() {
        return API_URL;
    }


    public static String getSubscriptionUrl() {
        return SUBSCRIPTION_URL;
    }

    public static String getSecuredHeader() {
        return HEADER;
    }
}
