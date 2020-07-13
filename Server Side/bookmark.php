<?php
$conn = mysqli_connect('localhost','username','password','db');

if($_GET['request']==1){ 
    //request 1 to add to bookmarks table
    
    
       
	$userid = $_GET['userid'];
	$appid = $_GET['appid'];
       
    $sql = "INSERT INTO bookmarks (id, userid, appid) VALUES ('','$userid','$appid')";
    $result = mysqli_query($conn, $sql);
    echo  "added";
	
       
}else if($_GET['request']==2){
    //request 2 to remove from bookmarks table
    
    $userid = $_GET['userid'];
	$appid = $_GET['appid'];
	
	$sql = "DELETE FROM bookmarks WHERE appid = '$appid' AND userid = '$userid'";
	$result = mysqli_query($conn, $sql);
    echo  "deleted";
    
}else if($_GET['request']==3){
    $userid = $_GET['userid'];
    $sql = "SELECT * FROM bookmarks WHERE userid= '$userid'";
    $result = mysqli_query($conn,$sql);
    $response["details"] = array();
    
    
        while($row = mysqli_fetch_array($result)){
            $appid = $row['appid'];
            $sql1 = "SELECT icon, id, title, ratings, reviews, score from appsdata WHERE id = '$appid'";
            $result1 = mysqli_query($conn, $sql1);
            while($row1 = mysqli_fetch_array($result1)){
                $items = array();
                $items['icon'] = $row1['icon'];
                $items['title'] = htmlspecialchars($row1['title']);
                $items['id'] = $row1['id'];
                $items['rating'] = $row1['ratings'];
                $items['reviews'] = $row1['reviews'];
                $items['score'] = $row1['score'];
                array_push($response["details"], $items);
        }
    }
    
    echo json_encode($response);
}
?>