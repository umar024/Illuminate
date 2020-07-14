<?php

include('dbconnection.php');


if($_GET['request']==1)
$category =1;
else if ($_GET['request'] == 2)
$category = 2;
else
$category =3;
	
    //getting all variables sent from python scrapping file
       
	$title = filter_var(
       $_POST['title'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
	$headerImage = filter_var(
       $_POST['headerImage'], 
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
       
    $mininstalls = filter_var(
       $_POST['minInstalls'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
      
    $package = filter_var(
       $_POST['package'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
       
       $sql = "SELECT * FROM appsdata WHERE title = '$title'";  
       $result = mysqli_query($conn, $sql);
       if(mysqli_fetch_array($result)==0){      // checking if data doesn`t exist already-- then insert
       
       $sql = "INSERT INTO appsdata (id,title,description, installs, score, ratings, reviews,price,genreid,icon,size,url,released,version,summary,headerImage,mininstalls, category,package)
VALUES ('','$title','$description','$installs','$score','$ratings','$reviews','$price','$genreId','$icon','$size','$url','$released','$version','$summary','$headerImage','$mininstalls', $category,'$package')";
$result = mysqli_query($conn, $sql);


$lengthofarray = filter_var(
       $_POST['lengthofarray'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
    $sql3 = "SELECT * FROM appsdata WHERE title = '$title'";
       $result3 = mysqli_query($conn, $sql3);
       while($row3 = mysqli_fetch_array($result3)){
            $j=0;
            for ($i=0;$i<$lengthofarray;$i++)
            {
                $myid = $row3['id'];
                $j=$j+1;
                $ss = filter_var(
                $_POST["".$j], 
                FILTER_SANITIZE_SPECIAL_CHARS);
                
                
                $sql4 = "INSERT INTO screenshots (id,appid,screenshot) VALUES ('','$myid','$ss')";  //inserting screenshots of application into screenshot table
                $result4 = mysqli_query($conn, $sql4);
            }
           
       }
       
       echo  json_encode("record inserted");
}
else{ 
    // updating data if data already exists in the table
    
    $sql = "UPDATE appsdata SET description='$description', installs='$installs', score='$score', ratings='$ratings', 
    reviews='$reviews',price='$price',genreid='$genreId',icon='$icon',size='$size',url='$url',released='$released',version='$version',summary='$summary',
    headerImage='$headerImage',mininstalls='$mininstalls', package ='$package' WHERE title='$title'";
    
    $result = mysqli_query($conn, $sql);
    
    $lengthofarray = filter_var(
       $_POST['lengthofarray'], 
       FILTER_SANITIZE_SPECIAL_CHARS);
    $sql2 = "SELECT * FROM appsdata WHERE title = '$title'";
       $result2 = mysqli_query($conn, $sql2);
    while($row2 = mysqli_fetch_array($result2)){
            $j=0;
            for ($i=0;$i<$lengthofarray;$i++)
            {
                $myid = $row2['id'];
                $j=$j+1;
                $ss = filter_var(
                $_POST["".$j], 
                FILTER_SANITIZE_SPECIAL_CHARS);
                
                
                $sql3 = "SELECT * FROM screenshots WHERE appid = '$myid' AND screenshot = '$ss'";
                $result3 = mysqli_query($conn, $sql3);
                if(mysqli_fetch_array($result3)==0){
                    $sql4 = "INSERT INTO screenshots (id,appid,screenshot) VALUES ('','$myid','$ss')";      //inserting new Screenshots
                    $result4 = mysqli_query($conn, $sql4);
                }
            }
           
       }
    echo  json_encode("record updated successfully");
}



?>