<?php
include('dbconnection.php');



if($_GET['request']==1){
    $userid = $_GET['userid'];
    $sql =  "SELECT * from permissions WHERE userid= '$userid'";
    $result = mysqli_query($conn, $sql);
    $response["permissions"] = array();
    while($row = mysqli_fetch_array($result)){
        $items = array();
        $items['Access_Location'] = $row['Access_Location'];
        $items['Read_Contacts'] = $row['Read_Contacts'];
        $items['Write_Contacts'] = $row['Write_Contacts'];
        $items['Read_Messages'] = $row['Read_Messages'];
        $items['Recieve_SMS'] = $row['Recieve_SMS'];
        $items['Recieve_MMS'] = $row['Recieve_MMS'];
        $items['Read_phone_state'] = $row['Read_phone_state'];
        $items['Intercept_Outgoing_calls'] = $row['Intercept_Outgoing_calls'];
        $items['Modify_phone_state'] = $row['Modify_phone_state'];
        $items['Access_Camera'] = $row['Access_Camera'];
        $items['Record_Audio'] = $row['Record_Audio'];
        $items['Read_Calender_Events'] = $row['Read_Calender_Events'];
        $items['Read_Browser_History'] = $row['Read_Browser_History'];
        array_push($response["permissions"], $items);
    }
    echo json_encode($response);
    
    
}else if($_POST['request']==2){
    
    $userid = $_POST['userid'];
    
    if($_POST['Access_Location']=="true")
        $Access_Location = 1;
    else
        $Access_Location = 0;
        
    if($_POST['Read_Contacts']=="true")
        $Read_Contacts = 1;
    else
        $Read_Contacts = 0;
        
    if($_POST['Write_Contacts']=="true")
        $Write_Contacts = 1;
    else
        $Write_Contacts = 0;
    
    if($_POST['Read_Messages']=="true")
        $Read_Messages = 1;
    else
        $Read_Messages = 0;
        
    if($_POST['Recieve_SMS']=="true")
        $Recieve_SMS = 1;
    else
        $Recieve_SMS = 0;
        
    if($_POST['Recieve_MMS']=="true")
        $Recieve_MMS = 1;
    else
        $Recieve_MMS = 0;
        
    if($_POST['Read_phone_state']=="true")
        $Read_phone_state = 1;
    else
        $Read_phone_state = 0;
        
    if($_POST['Intercept_Outgoing_calls']=="true")
        $Intercept_Outgoing_calls = 1;
    else
        $Intercept_Outgoing_calls = 0;
        
    if($_POST['Modify_phone_state']=="true")
        $Modify_phone_state = 1;
    else
        $Modify_phone_state = 0;
    
    if($_POST['Access_Camera']=="true")
        $Access_Camera = 1;
    else
        $Access_Camera = 0;
        
    if($_POST['Record_Audio']=="true")
        $Record_Audio = 1;
    else
        $Record_Audio = 0;
        
    if($_POST['Read_Calender_Events']=="true")
        $Read_Calender_Events = 1;
    else
        $Read_Calender_Events = 0;
        
    if($_POST['Read_Browser_History']=="true")
        $Read_Browser_History = 1;
    else
        $Read_Browser_History = 0;
    
    
    $sql = "UPDATE permissions SET 
        Access_Location='$Access_Location',
        Read_Contacts='$Read_Contacts',
        Write_Contacts='$Write_Contacts',
        Read_Messages='$Read_Messages',
        Recieve_SMS='$Recieve_SMS',
        Recieve_MMS='$Recieve_MMS',
        Read_phone_state='$Read_phone_state',
        Intercept_Outgoing_calls='$Intercept_Outgoing_calls',
        Modify_phone_state='$Modify_phone_state',
        Access_Camera='$Access_Camera',
        Record_Audio='$Record_Audio',
        Read_Calender_Events='$Read_Calender_Events',
        Read_Browser_History ='$Read_Browser_History' 
        WHERE
        userid='$userid'";
    $result =  mysqli_query($conn, $sql);
    echo ($result);
}


?>