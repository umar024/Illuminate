<?php

include('dbconnection.php');
if($_GET['action']=="signup"){

    $username = $_GET['username'];
    $password = $_GET['password'];
    $email = $_GET['email'];

    if($username == '' || $password == '' || $email ==''){
        echo "please fill all fields";
    }else{
        $sql  = "Select * from users where username='$username' OR email='$email'";
        $check = mysqli_fetch_array(mysqli_query($conn, $sql));
        if($check>0){
            echo "username or email already exists";
        }else{
            $sql = "insert into users (username, password, email) VALUES('$username', '$password', '$email')";
            if(mysqli_query($conn, $sql)){
                echo "Sign Up Successful!";
            }else{
                echo "Failed to Sign Up";
            }
        }
    }
}elseif($_GET['action'] == "login"){
    $username = $_GET['username'];
    $password = $_GET['password'];
    if($username == '' || $password == ''){
        echo "please enter username and password";
    }else{
        $sql = "Select username, password from users where username='$username' and password='$password'";
        $check = mysqli_fetch_array(mysqli_query($conn, $sql));
        if($check==0){
            echo "wrong username or password";
        }else{
            echo "login successful";
        }
    }
}
?>