<!--Written By Hrishikesh Rendalkar-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Twitter API Test</title>
</head>
<body>
	<h3>Twitter API Calls</h3>
	
	<h5>Get Calls</h5>
	<form action="TwitterAPI" >
		<input type="hidden" name="URL" value="https://api.twitter.com/1.1/statuses/home_timeline.json"/>
		<input type="hidden" name="HTTPMethod" value="GET"/>
		<input type="submit" value="Home Timeline"/>
	</form>
	
	<form action="TwitterAPI" >
		<input type="hidden" name="URL" value="https://api.twitter.com/1.1/statuses/user_timeline.json"/>
		<input type="hidden" name="HTTPMethod" value="GET"/>
		<input type="text" placeholder="Please enter a Screen Name" name="screen_name" value=""/>
		<input type="submit" value="User Timeline"/>
	</form>
	
	<form action="TwitterAPI" >
		<input type="hidden" name="URL" value="https://api.twitter.com/1.1/search/tweets.json"/>
		<input type="hidden" name="HTTPMethod" value="GET"/>
		<input type="text" placeholder="Please enter query term" name="q" value=""/>
		<input type="submit" value="Search"/>
	</form>
	
	<form action="TwitterAPI" >
		<input type="hidden" name="URL" value="https://api.twitter.com/1.1/geo/search.json"/>
		<input type="hidden" name="HTTPMethod" value="GET"/>
		<input type="text" placeholder="Please enter location" name="query" value=""/>
		<input type="submit" value="Geo Search"/>
	</form>
	
	<form action="TwitterAPI" >
		<input type="hidden" name="URL" value="https://api.twitter.com/1.1/trends/place.json"/>
		<input type="hidden" name="HTTPMethod" value="GET"/>
		<input type="text" placeholder="Please enter id" name="id" value=""/>
		<input type="submit" value="Trends"/>
	</form>
	
	<form action="TwitterAPI" >
		<input type="hidden" name="URL" value="https://api.twitter.com/1.1/favorites/list.json"/>
		<input type="hidden" name="HTTPMethod" value="GET"/>
		<input type="text" placeholder="Please enter a Screen Name" name="screen_name" value=""/>
		<input type="submit" value="Favorites"/>
	</form>
	
	<h5>Post Calls</h5>
	
	<form action="TwitterAPI" >
		<input type="hidden" name="URL" value="https://api.twitter.com/1.1/statuses/update.json"/>
		<input type="hidden" name="HTTPMethod" value="POST"/>
		<input type="text" placeholder="Please enter status" name="status" value=""/>
		<input type="submit" value="Post a Status"/>
	</form>
	
	<form action="TwitterAPI" >
		<input type="hidden" name="URL" value="https://api.twitter.com/1.1/favorites/create.json"/>
		<input type="hidden" name="HTTPMethod" value="POST"/>
		<input type="text" placeholder="Please enter id" name="id" value=""/>
		<input type="submit" value="Mark as Favorite"/>
	</form>
	
	
	<script src="http://cdn.jsdelivr.net/vue/1.0.16/vue.js"></script>
</body>
</html>