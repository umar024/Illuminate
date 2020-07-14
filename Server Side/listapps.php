<?php
include('dbconnection.php');

$sql = "Select icon, id, title, score, size, installs from appsdata where category = 1 limit 100";
mysqli_query($conn, "SET title `utf8`");

$result = mysqli_query($conn, $sql);

if(mysqli_fetch_array($result)>0){
    $response["details"] = array();
    while($row = mysqli_fetch_array($result)){
        $items = array();
        $items['icon'] = $row['icon'];
        $items['title'] = htmlspecialchars($row['title']);
        $items['id'] = $row['id'];
        $items['score'] = $row['score'];
        $items['size'] = $row['size'];
        $items['installs'] = $row['installs'];
        array_push($response["details"], $items);
    }
}


echo json_encode($response);
?>