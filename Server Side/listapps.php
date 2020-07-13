<?php
$conn = mysqli_connect('localhost','username','password','db');

$sql = "Select icon, id, title, ratings, reviews, score from appsdata where category = 1 limit 100";
mysqli_query($conn, "SET title `utf8`");

$result = mysqli_query($conn, $sql);

if(mysqli_fetch_array($result)>0){
    $response["details"] = array();
    while($row = mysqli_fetch_array($result)){
        $items = array();
        $items['icon'] = $row['icon'];
        $items['title'] = htmlspecialchars($row['title']);
        $items['id'] = $row['id'];
        $items['rating'] = $row['ratings'];
        $items['reviews'] = $row['reviews'];
        $items['score'] = $row['score'];
        array_push($response["details"], $items);
    }
}


echo json_encode($response);
?>