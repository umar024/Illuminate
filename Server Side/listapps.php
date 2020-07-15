<?php
include('dbconnection.php');



$category=$_GET['category'];

if(isset($_GET['genreid'])){
    $mygenreid = $_GET['genreid'];
    $genreid = "%".$mygenreid."%";
    $sql = "Select icon, id, title, score, size, installs from appsdata where category = '$category' AND genreid LIKE '$genreid' ORDER BY RAND() limit 100";
}else if(isset($_GET['search'])){
    $mysearch = $_GET['search'];
    $search = "%".$mysearch."%";
    $sql = "Select icon, id, title, score, size, installs from appsdata where title LIKE '$search' LIMIT 1";
}else{
$sql = "Select icon, id, title, score, size, installs from appsdata where category = '$category' AND ratings > 4.0 ORDER BY RAND() limit 100";
    
}
//mysqli_query($conn, "SET title `utf8`");

$result = mysqli_query($conn, $sql);


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



echo json_encode($response);
?>