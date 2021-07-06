package pe.bigprime.controller;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthToken implements RequestHandler<AuthRequest, AuthResponse> {

    static final String USER = System.getenv("AWS_ACCESS_KEY");
    static final String PASS = System.getenv("AWS_SECRET_KEY");
    static final String CLIENT_ID = System.getenv("CLIENT_ID");
    static final String GROUP_USER = System.getenv("GROUP_USER");

    @Override
    public AuthResponse handleRequest(AuthRequest authRequest, Context context) {
        System.out.println("Welcome to the Jungle!");
        Gson gson = new Gson();
        String JSON = gson.toJson(authRequest);
        @SuppressWarnings("unchecked")
        Map<String, String> credentials = new Gson().fromJson(JSON,Map.class);

        //BasicAWSCredentials awsCredentials = new BasicAWSCredentials(USER, PASS);
        AWSCognitoIdentityProvider provider = AWSCognitoIdentityProviderClientBuilder.standard().build();
                //.withRegion(Regions.US_EAST_1)
                //.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

        AdminInitiateAuthRequest admin = new AdminInitiateAuthRequest().withClientId(CLIENT_ID)
                .withUserPoolId(GROUP_USER).withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .withAuthParameters(credentials);
        AdminInitiateAuthResult result = provider.adminInitiateAuth(admin);
        AuthResponse authResponse = new AuthResponse();
        if(result != null) {
            String token = result.getAuthenticationResult().getIdToken();
            String accessToken = result.getAuthenticationResult().getAccessToken();
            String refresh = result.getAuthenticationResult().getRefreshToken();

            authResponse.setToken(token);
            authResponse.setAccess(accessToken);
            authResponse.setRefresh(refresh);
        }
        return authResponse;
    }

    public static void main(String[] args) throws JSONException {
        //JSONObject jsonObj = new JSONObject("{ \"USERNAME\":\"USUARIO\",\"PASSWORD\":\"PASSWORD\"}");
        Gson gson = new Gson();
        AuthRequest authRequest = new AuthRequest();
        authRequest.USERNAME = "a2";
        authRequest.PASSWORD = "Acm1pts.";
    /*    String JSON = gson.toJson(authRequest);
        @SuppressWarnings("unchecked")
        Map<String, String> map = new Gson().fromJson(JSON,Map.class);//jsonObj.toString()*/
        AuthResponse token = paralelo(authRequest);//map
        System.out.println("Token : " + token.getToken());
    }

    public static AuthResponse paralelo(AuthRequest authRequest){ // Map<String, String> credentials
        System.out.println("Welcome to the Jungle!");
        Gson gson = new Gson();
        String JSON = gson.toJson(authRequest);
        @SuppressWarnings("unchecked")
        Map<String, String> credentials = new Gson().fromJson(JSON,Map.class);

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(USER, PASS);
        AWSCognitoIdentityProvider provider = AWSCognitoIdentityProviderClientBuilder.standard().withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

        AdminInitiateAuthRequest admin = new AdminInitiateAuthRequest().withClientId(CLIENT_ID)
                .withUserPoolId(GROUP_USER).withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .withAuthParameters(credentials);
        AdminInitiateAuthResult result = provider.adminInitiateAuth(admin);
        AuthResponse authResponse = new AuthResponse();
        if(result != null) {
            String token = result.getAuthenticationResult().getIdToken();
            String accessToken = result.getAuthenticationResult().getAccessToken();
            String refresh = result.getAuthenticationResult().getRefreshToken();

           authResponse.setToken(token);
           authResponse.setAccess(accessToken);
           authResponse.setRefresh(refresh);
        }
        return authResponse;
    }

}
