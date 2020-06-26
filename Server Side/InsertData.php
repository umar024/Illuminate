<?php

$conn = mysqli_connect('localhost','db_name','db_pass','username');


if($_GET['request']==1)
$category =1;
else if ($_GET['request'] == 2)
$category = 2;
else
$category =3;
	
	$title = filter_var(
       $_POST['title'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$description = filter_var(
       $_POST['description'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$installs = filter_var(
       $_POST['installs'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$score = filter_var(
       $_POST['score'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$ratings = filter_var(
       $_POST['ratings'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$reviews = filter_var(
       $_POST['reviews'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$price = filter_var(
       $_POST['price'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$genreId = filter_var(
       $_POST['genreId'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$icon = filter_var(
       $_POST['icon'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$size = filter_var(
       $_POST['size'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$url = filter_var(
       $_POST['url'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$released = filter_var(
       $_POST['released'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$version = filter_var(
       $_POST['version'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$summary = filter_var(
       $_POST['summary'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
       $sql = "SELECT * FROM appsdata WHERE title = '$title'";
       $result = mysqli_query($conn, $sql);
       if(mysqli_fetch_array($result)==0){
       
       $sql = "INSERT INTO appsdata (id,title,description, installs, score, ratings, reviews,price,genreid,icon,size,url,released,version,summary, category)
VALUES ('','$title','$description','$installs','$score','$ratings','$reviews','$price','$genreId','$icon','$size','$url','$released','$version','$summary', $category)";
$result = mysqli_query($conn, $sql);


/*
$response["details"] = array();
$items = array();
        $items['title'] = $title;
        $items['description'] = $description;
        $items['installs'] = $installs;
        $items['score'] = $score;
        $items['ratings'] = $ratings;
        $items['reviews'] = $reviews;
        $items['price'] = $price;
        $items['genreId'] = $genreId;
        $items['icon'] = $icon;
        $items['size'] = $size;
        $items['url'] = $url;
        $items['released'] = $released;
        $items['version'] = $version;
        $items['summary'] = $summary;
        array_push($response["details"], $items);
        
        */

	echo json_encode($result); 
	
}
else{
    echo json_encode("record already exists");
}

?>