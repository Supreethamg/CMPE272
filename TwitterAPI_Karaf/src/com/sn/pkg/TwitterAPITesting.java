package com.sn.pkg;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Random;

import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author Pratik Dhumal
 */
@RunWith(MockitoJUnitRunner.class)
public class TwitterAPITesting {   
	@Test
    public void methodSuccess() throws ServletException, IOException
    {
         MockHttpServletRequest request = new MockHttpServletRequest();
         MockHttpServletResponse response=new MockHttpServletResponse();
       
         request.setParameter("URL","https://api.twitter.com/1.1/statuses/update.json");
         request.setParameter("HTTPMethod","POST"); 
         request.setParameter("status",getRandomStringInJava(50));
          
       TwitterAPI twitterSuccess = new TwitterAPI();
	                    
       twitterSuccess.doGet(request,response);
        
       int httpResponseCodeActual=0;
               
       httpResponseCodeActual=response.getStatus();
        
       System.out.println("methodSuccess Response is:"+httpResponseCodeActual);
        
       assertEquals(HttpURLConnection.HTTP_OK, httpResponseCodeActual);   
    }
    
    //This test is going to fail because we already have tweet test18
   @Test
    public void methodForForbidden() throws ServletException, IOException
    {
      MockHttpServletRequest request = new MockHttpServletRequest();
      MockHttpServletResponse response=new MockHttpServletResponse();
       
        request.setParameter("URL","https://api.twitter.com/1.1/statuses/update.json");
        request.setParameter("HTTPMethod","POST"); 
        request.setParameter("status","test18");
        
        TwitterAPI twitterFailure = new TwitterAPI();
	    
        try
        {          
        	twitterFailure.doGet(request,response);
        	assertEquals(HttpURLConnection.HTTP_FORBIDDEN, response.getStatus());
        }
        catch(Exception ex)
        {
        	assertEquals(HttpURLConnection.HTTP_FORBIDDEN, response.getStatus());
        }
    }

    @Test
     public void checkMaxTweet() throws ServletException, IOException
    {
      MockHttpServletRequest request = new MockHttpServletRequest();
      MockHttpServletResponse response=new MockHttpServletResponse();
       
        request.setParameter("URL","https://api.twitter.com/1.1/statuses/update.json");
        request.setParameter("HTTPMethod","POST"); 
        request.setParameter("status",getRandomStringInJava(140));
          
        TwitterAPI twitterFailure = new TwitterAPI();
	                    
        twitterFailure.doGet(request,response);
              
        int httpResponseCodeActual=0;
               
        httpResponseCodeActual=response.getStatus();
        
        System.out.println("checkMaxTweet Response is:"+httpResponseCodeActual);
        
        assertEquals(HttpURLConnection.HTTP_OK, httpResponseCodeActual);
    }
    
    @Test
     public void checkMaxTweetCountMinusOne() throws ServletException, IOException
    {
      MockHttpServletRequest request = new MockHttpServletRequest();
      MockHttpServletResponse response=new MockHttpServletResponse();
       
        request.setParameter("URL","https://api.twitter.com/1.1/statuses/update.json");
        request.setParameter("HTTPMethod","POST"); 
        request.setParameter("status",getRandomStringInJava(139));
          
        TwitterAPI twitterFailure = new TwitterAPI();
	                    
        twitterFailure.doGet(request,response);
              
        int httpResponseCodeActual=0;
               
        httpResponseCodeActual=response.getStatus();
        
        System.out.println("methodFailure Response is:"+httpResponseCodeActual);
        
        assertEquals(HttpURLConnection.HTTP_OK, httpResponseCodeActual); 
    }
     
    @Test
     public void checkMinTweetCount() throws ServletException, IOException
    {
      MockHttpServletRequest request = new MockHttpServletRequest();
      MockHttpServletResponse response=new MockHttpServletResponse();
       
        request.setParameter("URL","https://api.twitter.com/1.1/statuses/update.json");
        request.setParameter("HTTPMethod","POST"); 
        request.setParameter("status",getRandomStringInJava(139));
          
        TwitterAPI twitterFailure = new TwitterAPI();
	                    
        try
        {          
        	twitterFailure.doGet(request,response);
        }
        catch(Exception ex)
        {
        	assertEquals(null, response);
        }
    } 
     
      @Test
     public void checkMaxTweetCountPlusOne() throws ServletException, IOException
    {
      MockHttpServletRequest request = new MockHttpServletRequest();
      MockHttpServletResponse response=new MockHttpServletResponse();
       
        request.setParameter("URL","https://api.twitter.com/1.1/statuses/update.json");
        request.setParameter("HTTPMethod","POST"); 
        request.setParameter("status",getRandomStringInJava(150));
          
        TwitterAPI twitterFailure = new TwitterAPI();
	                    
        try
        {          
        	twitterFailure.doGet(request,response);
        }
        catch(Exception ex)
        {
        	assertEquals(HttpURLConnection.HTTP_FORBIDDEN, response.getStatus());
        }
                
    }

      @Test
      public void MintweetCharacter() throws ServletException, IOException
     {
       MockHttpServletRequest request = new MockHttpServletRequest();
       MockHttpServletResponse response=new MockHttpServletResponse();
        
         request.setParameter("URL","https://api.twitter.com/1.1/statuses/update.json");
         request.setParameter("HTTPMethod","POST"); 
         request.setParameter("status",getRandomStringInJava(1));
           
         TwitterAPI twitterFailure = new TwitterAPI();
         try
         {      
         twitterFailure.doGet(request,response);
         
         int httpResponseCodeActual=0;
                
         httpResponseCodeActual=response.getStatus();
         
         System.out.println("MinTweet Response is:"+httpResponseCodeActual);
         
         assertEquals(HttpURLConnection.HTTP_OK, httpResponseCodeActual); 
 	             }
 	              catch(Exception ex)
 	              {
 	            	  String s="";
 	              }
     }

      
    public String getRandomStringInJava(int loop) 
    {
        String RANDOMCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz 123456789";
        StringBuilder randomString = new StringBuilder();
        Random randomNumber = new Random();
        //randomString.append(" ");
        while (randomString.length() < loop) 
        { 
            int index = (int) (randomNumber.nextFloat() * RANDOMCHARS.length());
            randomString.append(RANDOMCHARS.charAt(index));       
        }
        String readyRandomString = randomString.toString();
        
        System.out.println("ReadyRandomString"+readyRandomString.length());
        
        System.out.println("ReadyRandomString"+readyRandomString);
        
        return readyRandomString;

    }
}
