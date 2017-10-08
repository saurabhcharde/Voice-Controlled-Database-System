<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "dvwa";
	$text=$_GET['param1'];


	$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

if($text){
	$sql = "INSERT INTO speech(val) VALUES ('$text')";
	$result=mysqli_query($conn, $sql);
	if (!$result) {
    die("Query to insert fields from table failed");
	}
	echo (string)$result." rows inserted";
}
else
{
echo "error!";
}
mysqli_close($conn);
?>