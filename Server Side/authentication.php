<?php

include('dbconnection.php');

if($_POST['request']=="savedata"){
    $userid = $_POST['userid'];
    $totalapps= $_POST['totalapps'];
    $apps = array();
    for($i=0;$i<$totalapps;$i++){
        $apps[$i] = $_POST['appname'.$i];
    }
    $sql0 = "SELECT * FROM userinstalledapps WHERE userid = '$userid'";
    $result0 = mysqli_query($conn, $sql0);
    if(mysqli_num_rows($result0)!=0){
        $sqldelete = "DELETE FROM userinstalledapps WHERE userid = '$userid'";
        $resultdelete = mysqli_query($conn, $sqldelete);
    }
    for($i=0;$i<$totalapps;$i++){
        $appname = "%".$apps[$i]."%";
        $sql = "SELECT * FROM appsdata WHERE title LIKE '$appname' LIMIT 1";
        $result = mysqli_query($conn,$sql);
        if(mysqli_num_rows($result)!=0){
            while($row = mysqli_fetch_array($result)){
                $genreid = $row['genreid'];
                $sql1 = "INSERT into userinstalledapps (id,userid,title,genreid) VALUES ('','$userid','$apps[$i]','$genreid')";
                $result1 = mysqli_query($conn, $sql1);
            }
        }
    }
      

    $sql2 = "SELECT * FROM permissions WHERE userid = '$userid'";
    $result2 = mysqli_query($conn,$sql2);
    if(mysqli_num_rows($result2)==0){
        $sql3="INSERT INTO permissions (id,userid,Access_Location,Read_Contacts, Write_Contacts, Read_Messages, Recieve_SMS,Recieve_MMS,Read_phone_state,Intercept_Outgoing_calls,	Modify_phone_state,	Access_Camera,Record_Audio,Read_Calender_Events,Read_Browser_History)
VALUES ('','$userid','1','1','1','1','1','1','1','1','1','1','1','1','1')";
        $result3 = mysqli_query($conn,$sql3);
            
    }
    echo "Complete";
}

else if($_GET['action']=="signup"){

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

 /*	
id
userid
Access_Location
Read_Contacts
Write_Contacts
Read_Messages
Recieve_SMS
Recieve_MMS
Read_phone_state
Intercept_Outgoing_calls
Modify_phone_state
Access_Camera
Record_Audio
Read_Calender_Events
Read_Browser_History*/


?>