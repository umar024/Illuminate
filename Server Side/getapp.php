<?php

$conn = mysqli_connect('localhost','username','password','db');

$appid = $_GET['appid'];
$userid = $_GET['userid'];
$isbookmark = false;

$sql0 = "Select * from bookmarks where appid = '$appid' AND userid = '$userid'";
$result0 = mysqli_query($conn, $sql0);
if(mysqli_num_rows ( $result0 )>0){
    $isbookmark = true;
}



$sql = "Select * from appsdata where id = '$appid'";
$result = mysqli_query($conn, $sql);

$response["details"] = array();
    while($row = mysqli_fetch_array($result)){
        $items = array();
        $items['isbookmark'] = $isbookmark;
        $items['id'] = $row['id'];
        $items['title'] = $row['title'];
        $items['installs'] = $row['installs'];
        $items['score'] = $row['score'];
        $items['ratings'] = $row['ratings'];
        $items['description'] = $row['description'];
        $items['reviews'] = $row['reviews'];
        $items['price'] = $row['price'];
        $items['genreid'] = $row['genreid'];
        $items['icon'] = $row['icon'];
        $items['size'] = $row['size'];
        $items['url'] = $row['url'];
        $items['released'] = $row['released'];
        $items['version'] = $row['version'];
        $items['headerImage'] =  $row['headerImage'];
        $items['package'] = $row['package'];
        array_push($response["details"], $items);
    } 



//echo json_encode($response);


$sql = "Select * from screenshots where appid = '$appid'";
$result = mysqli_query($conn, $sql);

$response["detailsSS"] = array();
    while($row = mysqli_fetch_array($result)){
        $items = array();
        $j=$j+1;
        $items['idssapp'] = $row['id'];
        $items['screenshot'] = $row['screenshot'];
        array_push($response["detailsSS"], $items);
    }
    



echo json_encode($response);




?>