<?php

$conn = mysqli_connect('localhost','user','password','db');
if(mysqli_connect_error($con))
{
	echo 'Connect to Failed' .mysqli_connect_error();
}
?>