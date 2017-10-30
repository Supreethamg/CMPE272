package com.sn.pkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.json.JSONObject;


/**
 * Servelet implementation class TwitterAPI
 */
@WebServlet("/TwitterAPI")
public class TwitterAPI extends HttpServlet {
    //**variables
    private static final long serialVersionUID = 1L;
    private static final String Twitter_Consumer_Key="your key";
    private static final String Twitter_Token="your token";
    private static final String Twitter_Consumer_Key_Secret="your consumer key";
    private static final String Twitter_Token_Secret="your token secret";
    private static final String Twitter_OAuth_Signature_Method="HMAC-SHA1";
    private static final String Twitter_OAuth_Version="1.0";
    //private static final String Include_Entities="true";

    //returning status of response dont make it static as it needs to be separate for all objects
    private int responseGlobal;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TwitterAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Code written by Supreetha MG
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpURLConnection Res1=mSendRequest(request);
		InputStream Res=null;
		
		if(Res1.getResponseCode()== HttpURLConnection.HTTP_OK)
			Res=Res1.getInputStream();
		response.setStatus(responseGlobal);
		//**return results
		//response.setStatus(Res1.getResponseCode());
		response.setContentType("text/html");
		response.setStatus(responseGlobal);
		
		PrintWriter out = response.getWriter();
		
		if(Res.equals(null))
		{
			out.println("<h3>" + "Sorry! Can't respond right now. Try again. " + "</h3>");
		}
		else
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(Res));
			String inputLine;
			StringBuffer result = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				result.append(inputLine);
			}
			in.close();
			
                    response.setContentType("text/html");    
                    JsonArray jSonArray = ParseStringResponse(request.getQueryString(), result);
                    
                    

                 
                     out.println("<html><body><head color=#2db8eb>Response</head><br><table border=1 bordercolor=white bgcolor=#2db8eb><tr><th>Type</th><th>Value</th></tr>"); 
                    
                     
                   //  jSonArray.ge
                    for(int i=0;i<jSonArray.size();i++)
                    {
                        out.println("<tr>");
                        JsonObject temp=(JsonObject)jSonArray.get(i);
                        System.out.println("temp"+temp);
                        
                        JsonElement str=temp.get("text");
                        System.out.println("JSon"+str);
                       String s1= str.getAsString();
                        out.println("<td>"+"text"+"</td><td>"+s1+"</td>");
                        out.println("<tr>");
                        
                    }
                    out.println("</table></body></html>"); 

                    
                    
			
		}
		
		//**postman implementation
		//SendTwitterGet("https://api.twitter.com/1.1/statuses/home_timeline.json?oauth_consumer_key=hnTQrQlp2bs1W8iP9YMY4MhtP&oauth_token=914266543147556864-DiR29Os6OlFhwlJL0Ze8M1hyFYASvfO&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1507444334&oauth_nonce=s8pd4T&oauth_version=1.0&oauth_signature=4A%2FMBczxymUP6Dip8vJoE2KjImc%3D");
		
		//**actual implementation
		//mSendGetRequest("https://api.twitter.com/1.1/statuses/home_timeline.json");//?oauth_consumer_key=hnTQrQlp2bs1W8iP9YMY4MhtP&oauth_token=914266543147556864-DiR29Os6OlFhwlJL0Ze8M1hyFYASvfO&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1507228553&oauth_nonce=gmeMVf&oauth_version=1.0&oauth_signature=LoMcZ4HX2spx6jnIZp6Fo%2B6268k%3D");
		//mSendGetRequest("https://api.twitter.com/1.1/statuses/user_timeline.json");
		//mSendGetRequest("https://api.twitter.com/1.1/search/tweets.json");//?q=%23freebandnames&since_id=24012619984051000&max_id=250126199840518145&result_type=mixed&count=4
		//https://api.twitter.com/1.1/geo/search.json?query=Toronto
		//GET https://api.twitter.com/1.1/trends/place.json?id=1
		//GET https://api.twitter.com/1.1/favorites/list.json?count=2&screen_name=episod
	    // Actual logic goes here.
		//String s=request.getParameter("method_type");
		//String s1=request.getParameter("screen_name");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//POST https://api.twitter.com/1.1/statuses/update.json?status=Maybe%20he%27ll%20finally%20find%20his%20keys.%20%23peterfalk
		//POST https://api.twitter.com/1.1/favorites/create.json?id=243138128959913986
		//POST https://api.twitter.com/1.1/statuses/retweet/243149503589400576.json
	}
	
        /**
	 * Code written by Sourabh Namilikonda
	 */
	private HttpURLConnection mSendRequest(HttpServletRequest request) throws IOException {
		//**create outh parameter sorted list
		String strTimeStamp=mCreateTimeStamp();
		String strNonce=mCreateNonce();
		Map<String, String> Dictionary = mAddOauthParameters(strNonce, strTimeStamp);
		
		//**separate out url, method type
		String RequestURL="";
		String RequestHTTPMethod="";
		
		//**add remaining parameters in alphabetic order
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName=parameterNames.nextElement();
			if(paramName.equals("URL"))
				RequestURL=request.getParameterValues(paramName)[0];
			else if(paramName.equals("HTTPMethod"))
				RequestHTTPMethod=request.getParameterValues(paramName)[0];
			else
				Dictionary.put(paramName, request.getParameterValues(paramName)[0]);
		}
		
		//**create Signature
		String strSignature = mCreateSignatureFinal(Dictionary, RequestHTTPMethod, RequestURL);
		
		//**create userCreds This is fixed only for oauth params
		String userCredentials = "oauth_consumer_key=\""+Twitter_Consumer_Key+"\",oauth_token=\""+Twitter_Token+"\",oauth_signature_method=\""+Twitter_OAuth_Signature_Method+"\",oauth_timestamp=\""+strTimeStamp+"\",oauth_nonce=\""+strNonce+"\",oauth_version=\""+Twitter_OAuth_Version+"\",oauth_signature=\""+strSignature+"\"";
		
		//**add uRL and other parameters
		String strFinalURL=RequestURL;
		parameterNames = request.getParameterNames();
		boolean bFlag=false;
		while (parameterNames.hasMoreElements()) {
			String paramName=parameterNames.nextElement();
			if(!(paramName.equals("URL")||paramName.equals("HTTPMethod")))
			{
				if(!bFlag)
				{
					strFinalURL=strFinalURL+"?";
					bFlag=true;
				}
				strFinalURL=strFinalURL+paramName+"="+mCreatePercentEncodedString(request.getParameterValues(paramName)[0])+"&";
			}
		}
		
		//remove last &
		if (strFinalURL != null && strFinalURL.length() > 0 && strFinalURL.charAt(strFinalURL.length() - 1) == '&') {
			strFinalURL = strFinalURL.substring(0, strFinalURL.length() - 1);
	    }
		
		//**call the api
		URL obj = new URL(strFinalURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(RequestHTTPMethod);
		String OAuth = "OAuth " + userCredentials;
		con.setRequestProperty ("Authorization", OAuth);
		con.setRequestProperty("Cache-Control", "no-cache");
		
		//**get response code
		int responseCode = con.getResponseCode();
		responseGlobal=responseCode;
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			System.out.println("Request Worked");
		} else {
			System.out.println("Request Failed");
		}
		return con;
	}

	private String mCreateSignatureFinal(Map<String, String> dictionary,String requestHTTPMethod, String requestURL) {
		try{
			//alphabetic by encoded key, append by &
			String BaseSignatureString="";
			for (Map.Entry<String, String> entry : dictionary.entrySet()) {
				//**replacing space character with %20
				BaseSignatureString=BaseSignatureString+entry.getKey()+"="+entry.getValue().replace(" ", "%20")+"&";
			}
			
			//remove last &
			if (BaseSignatureString != null && BaseSignatureString.length() > 0 && BaseSignatureString.charAt(BaseSignatureString.length() - 1) == '&') {
				BaseSignatureString = BaseSignatureString.substring(0, BaseSignatureString.length() - 1);
		    }
			
			//sign key and final base string
			String BaseStringFinal=requestHTTPMethod.toUpperCase()+"&"+mCreatePercentEncodedString(requestURL)+"&"+mCreatePercentEncodedString(BaseSignatureString);
			String SigningKey=mCreatePercentEncodedString(Twitter_Consumer_Key_Secret)+"&"+mCreatePercentEncodedString(Twitter_Token_Secret);
			
			//create and return sign
			return mCreateHMacSign(BaseStringFinal, SigningKey);
		}
		catch (Exception e) {
			return "";
		}		
	}

	private TreeMap<String, String> mAddOauthParameters(String strNonce, String strTimeStamp) throws UnsupportedEncodingException {
		TreeMap<String, String> dic=new TreeMap<String, String>();
		dic.put("oauth_consumer_key", mCreatePercentEncodedString(Twitter_Consumer_Key));
		dic.put("oauth_nonce", mCreatePercentEncodedString(strNonce));
		dic.put("oauth_signature_method", mCreatePercentEncodedString(Twitter_OAuth_Signature_Method));
		dic.put("oauth_timestamp", mCreatePercentEncodedString(strTimeStamp));
		dic.put("oauth_token", mCreatePercentEncodedString(Twitter_Token));
		dic.put("oauth_version", mCreatePercentEncodedString(Twitter_OAuth_Version));
		return dic; 
	}
	
	private String mCreateNonce()
	{
		// random alphanumeric
		String strNonce=UUID.randomUUID().toString();
		strNonce = strNonce.replaceAll("-", "");
		return strNonce;
	}
	
	private String mCreateTimeStamp()
	{
		return Long.toString((System.currentTimeMillis() / 1000));
	}
	
	private String mCreatePercentEncodedString(String Value) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(Value,"UTF-8");
	}
	
	private String mCreateHMacSign(String BaseStringFinal, String SigningKey) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException
	{
		SecretKeySpec signKey = new SecretKeySpec(SigningKey.getBytes(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signKey);		
		return mCreatePercentEncodedString(new String(Base64.encode(mac.doFinal(BaseStringFinal.getBytes()))).trim());
	}

        /**
	 * Code written by Ravali Nagabandi
	 */
	private JsonArray ParseStringResponse(String requestURL, StringBuffer input)
	{
		if(requestURL.contains("home_timeline") || requestURL.contains("user_timeline")|| requestURL.contains("available") || requestURL.contains("list.json"))
		{
		//Parse to Result if REquired.
			//JsonParser parser = new JsonParser();
			//JsonArray jsonArray = (JsonArray) parser.parse(input.toString());
			
			//return jsonArray.toString();
			
			
		JsonParser parser = new JsonParser();
		
		JsonArray jsonArray = (JsonArray) parser.parse(input.toString());
		JsonArray jsonArrayFormatted=new JsonArray();

		for(int a=0;a<jsonArray.size();a++)
		{
			JsonObject temp=(JsonObject)jsonArray.get(a);
			JsonObject newOb=new JsonObject();
			newOb.add("text",temp.get("text"));
			jsonArrayFormatted.add(newOb);
		}
		
		return jsonArrayFormatted;
		
		}
                
                if(requestURL.contains("Fgeo"))
                {
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObj = (JsonObject) parser.parse(input.toString());
                    JsonObject json1=(JsonObject)jsonObj.get("result");
                    JsonArray jsonArrayPlaces=json1.getAsJsonArray("places");

                    JsonArray jsonArrayFormatted=new JsonArray();
                    for(int a=0;a<jsonArrayPlaces.size();a++)
                    {
                        JsonObject temp=(JsonObject)jsonArrayPlaces.get(a);
                        JsonObject newOb=new JsonObject();
                        newOb.add("text",temp.get("name"));
                        jsonArrayFormatted.add(newOb);
                    }
                    return jsonArrayFormatted;
                   
                }		
                else if(requestURL.contains("search"))//requestURL.contains("search")
		{
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse(input.toString());
			JsonArray jsonArrayStatues=jsonObject.getAsJsonArray("statuses");
			JsonArray jsonArrayFormatted=new JsonArray();
			for(int a=0;a<jsonArrayStatues.size();a++)
			{
				JsonObject temp=(JsonObject)jsonArrayStatues.get(a);
				JsonObject newOb=new JsonObject();
				newOb.add("text",temp.get("text"));
				jsonArrayFormatted.add(newOb);
			}
			return jsonArrayFormatted;
		}
		else if(requestURL.contains("update.json"))
		{
			JsonParser parser = new JsonParser();
			JsonObject jsonObj = (JsonObject) parser.parse(input.toString());	
			JsonArray jsonArrayFormatted=new JsonArray();
			JsonObject newOb=new JsonObject();
			newOb.add("text",jsonObj.get("text"));
			jsonArrayFormatted.add(newOb);
			return jsonArrayFormatted;
		
		}
                else if(requestURL.contains("favorite"))
                {
                    JsonParser parser = new JsonParser();
			JsonObject jsonObj = (JsonObject) parser.parse(input.toString());	
			JsonArray jsonArrayFormatted=new JsonArray();
			JsonObject newOb=new JsonObject();
			newOb.add("text",jsonObj.get("created_at"));
			jsonArrayFormatted.add(newOb);
			return jsonArrayFormatted;
                }
		else
		{
			/*JsonParser parser = new JsonParser();
			JsonObject jsonObj = (JsonObject) parser.parse(input.toString());			
			return jsonObj;*/
                    JsonParser parser = new JsonParser();
                    JsonArray jsonObj = (JsonArray) parser.parse(input.toString());	
                    JsonObject json1=(JsonObject)jsonObj.get(0);
                    JsonArray jsonArrayTrends=json1.getAsJsonArray("trends");

                    JsonArray jsonArrayFormatted=new JsonArray();
                        
			for(int a=0;a<jsonArrayTrends.size();a++)
                    {
                        JsonObject temp=(JsonObject)jsonArrayTrends.get(a);
                        JsonObject newOb=new JsonObject();
                        newOb.add("text",temp.get("name"));
                        jsonArrayFormatted.add(newOb);
                    }
			return jsonArrayFormatted;
		}
	
	}
	
	
	
	
	
	/*private String mCreateSignature(String HTTPMethod, String URL, String TimeStamp, String Nonce)
	{
		try{
			//alphabetic by encoded key, append by &
			String BaseSignatureString="";
			BaseSignatureString=BaseSignatureString+"count="+mCreatePercentEncodedString("2")+"&";
			BaseSignatureString=BaseSignatureString+"oauth_consumer_key="+mCreatePercentEncodedString(Twitter_Consumer_Key)+"&";
			BaseSignatureString=BaseSignatureString+"oauth_nonce="+mCreatePercentEncodedString(Nonce)+"&";
			BaseSignatureString=BaseSignatureString+"oauth_signature_method="+mCreatePercentEncodedString(Twitter_OAuth_Signature_Method)+"&";
			BaseSignatureString=BaseSignatureString+"oauth_timestamp="+mCreatePercentEncodedString(TimeStamp)+"&";
			BaseSignatureString=BaseSignatureString+"oauth_token="+mCreatePercentEncodedString(Twitter_Token)+"&";
			BaseSignatureString=BaseSignatureString+"oauth_version="+mCreatePercentEncodedString(Twitter_OAuth_Version)+"&";
			BaseSignatureString=BaseSignatureString+"screen_name="+mCreatePercentEncodedString("sachin_rt");
			
			String BaseStringFinal=HTTPMethod.toUpperCase()+"&"+mCreatePercentEncodedString(URL)+"&"+mCreatePercentEncodedString(BaseSignatureString);
			String SigningKey=mCreatePercentEncodedString(Twitter_Consumer_Key_Secret)+"&"+mCreatePercentEncodedString(Twitter_Token_Secret);
			
			return mCreateHMacSign(BaseStringFinal, SigningKey);
		}
		catch (Exception e) {
			// TODO: handle exception
			return "";
		}		
	}
	
	private void mSendGetRequest(String URLValue) throws IOException
	{
		String strTimeStamp=mCreateTimeStamp();
		String strNonce=mCreateNonce();
		String strSignature=mCreateSignature("GET", URLValue, strTimeStamp, strNonce);
		
		String userCredentials = "oauth_consumer_key=\""+Twitter_Consumer_Key+"\",oauth_token=\""+Twitter_Token+"\",oauth_signature_method=\""+Twitter_OAuth_Signature_Method+"\",oauth_timestamp=\""+strTimeStamp+"\",oauth_nonce=\""+strNonce+"\",oauth_version=\""+Twitter_OAuth_Version+"\",oauth_signature=\""+strSignature+"\"";
		
		URL obj = new URL(URLValue+"?screen_name=sachin_rt&count=2");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		
		String OAuth = "OAuth " + userCredentials;
		con.setRequestProperty ("Authorization", OAuth);
		con.setRequestProperty("Cache-Control", "no-cache");
		
		
		 

		
		
		
		
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		//if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		//} else {
			System.out.println("GET request not worked");
	//	}
			
		//**Extra code might be useful
		//String urlvalue=userCredentials.replaceAll("\"", "");
		//urlvalue=urlvalue.replaceAll(",", "&");
		//URL obj = new URL(URLValue+"?"+urlvalue);
	}
	
	private void SendTwitterGet (String strGetURL) throws IOException
	{	
		/*GET /1.1/statuses/home_timeline.json?oauth_consumer_key=hnTQrQlp2bs1W8iP9YMY4MhtP&amp;oauth_token=914266543147556864-DiR29Os6OlFhwlJL0Ze8M1hyFYASvfO&amp;oauth_signature_method=HMAC-SHA1&amp;oauth_timestamp=1507444334&amp;oauth_nonce=s8pd4T&amp;oauth_version=1.0&amp;oauth_signature=4A%2FMBczxymUP6Dip8vJoE2KjImc%3D HTTP/1.1
Host: api.twitter.com
Authorization: OAuth oauth_consumer_key="hnTQrQlp2bs1W8iP9YMY4MhtP",oauth_token="914266543147556864-DiR29Os6OlFhwlJL0Ze8M1hyFYASvfO",oauth_signature_method="HMAC-SHA1",oauth_timestamp="1507098323",oauth_nonce="Rg3kbN",oauth_version="1.0",oauth_signature="kbPV3NDt6%2BhoOWodiCpQfCzlPtI%3D"
Cache-Control: no-cache
Postman-Token: 570a151b-2eda-08d9-e422-9b5081d5a1fc

		*/
	
		/*String userCredentials = "oauth_consumer_key=\"hnTQrQlp2bs1W8iP9YMY4MhtP\",oauth_token=\"914266543147556864-DiR29Os6OlFhwlJL0Ze8M1hyFYASvfO\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1507098323\",oauth_nonce=\"Rg3kbN\",oauth_version=\"1.0\",oauth_signature=\"kbPV3NDt6%2BhoOWodiCpQfCzlPtI%3D\"";
		
		//**no encoding required
		//String OAuth = "OAuth " + new String(com.sun.org.apache.xml.internal.security.utils.Base64.encode(userCredentials.getBytes()));
		
		String OAuth = "OAuth " + userCredentials;
		URL obj = new URL(strGetURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//con.setRequestMethod("GET");
		//con.setRequestProperty ("Authorization", OAuth);
		//con.setRequestProperty("Cache-Control", "no-cache");
		//con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}
	}*/
}

