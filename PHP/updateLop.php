<?php
require "dbConnect.php";

$id = $_POST['Id'];
$tenlop = $_POST['TenLop'];
$query = "UPDATE lop
		  SET tenlop = '$tenlop'
		  WHERE id = '$id'";
if (mysqli_query($connect, $query)) {
	echo "success";
} else {
	echo "failed";
}
?>