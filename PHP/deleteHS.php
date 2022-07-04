<?php
require "dbConnect.php";

$id =$_POST['ID'];
$query = "DELETE FROM hocsinh WHERE id = '$id'";
if (mysqli_query($connect,$query)) {
	echo "success";
} else {
	echo "failed";
}
?>