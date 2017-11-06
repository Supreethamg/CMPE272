/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytwitterapp;

import java.io.IOException;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import twitter4j.JSONException;
import twitter4j.TwitterException;


/**
 *
 * @author Supreetha
 */
public class MyTwitterApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws TwitterException, IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, JSONException {
        // TODO code application logic here
      
        
        String CONSUMER_KEY="your key";
        String CONSUMER_KEY_SECRET="your key";
        String ACCESS_TOKEN="your key";
        String ACCESS_TOKEN_SECRET="your key";
        
        
      OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_KEY_SECRET);
				
		oAuthConsumer.setTokenWithSecret(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);

		HttpGet request = new HttpGet("https://api.twitter.com/1.1/statuses/user_timeline.json");
                oAuthConsumer.sign(request);
 
       	         HttpClient client = new DefaultHttpClient();
	         HttpResponse response = client.execute(request);

 
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode + ":" + response.getStatusLine().getReasonPhrase());
        System.out.println(IOUtils.toString(response.getEntity().getContent()));
        
        
//        JSONArray temp = new JSONArray(EntityUtils.toString(response.getEntity()));
//        
//        for(int i=0; i< temp.length();i++){
//            System.out.println(((String)((JSONObject) temp.get(i)).get("created_at")));
//            System.out.println(((String)((JSONObject) temp.get(i)).get("text")));
//            
//        }
	}
    
}


