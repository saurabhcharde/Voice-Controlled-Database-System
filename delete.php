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

$sql = "DELETE FROM speech where val='$text'";
	$result=mysqli_query($conn, $sql);
	if (!$result) {
    die("Query to delete fields from table failed");
}
	echo $result." rows deleted";

}
else
{
echo "error!";
}
mysqli_close($conn);
?>
