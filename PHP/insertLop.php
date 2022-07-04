<?php
require "dbConnect.php";

$tenlop = $_POST['TenLop'];
$query = "INSERT INTO lop VALUES(null,'$tenlop')";
if(mysqli_query($connect,$query)){
	echo "success";
} else{
	echo "failed";
}
?>