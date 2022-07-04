<?php
require "dbConnect.php";

$id = $_POST['ID'];
$idlop = $_POST['IDLOP'];
$hoten = $_POST['HOTEN'];
$gioitinh = $_POST['GIOITINH'];
$ngaysinh = $_POST['NGAYSINH'];
$diachi = $_POST['DIACHI'];
$toan = $_POST['TOAN'];
$van = $_POST['VAN'];
$anh = $_POST['ANH'];
$query = "UPDATE hocsinh 
		  SET 	idlop = '$idlop',
		  		hoten = '$hoten',
		  		gioitinh = '$gioitinh',
		  		ngaysinh = '$ngaysinh',
		  		diachi = '$diachi',
		  		toan = '$toan',
		  		van = '$van',
		  		anh = '$anh'
		  WHERE id = '$id'";
if (mysqli_query($connect,$query)) {
	echo "success";
} else {
	echo "failed";
}
?>