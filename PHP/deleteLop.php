<?php
require "dbConnect.php";

$id = $_POST['Id'];
$query = "DELETE FROM lop WHERE id ='$id'";
if (mysqli_query($connect,$query)) {
	echo "success";
} else {
	echo "failed";
}
?>