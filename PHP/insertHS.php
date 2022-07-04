<?php
require "dbConnect.php";

$idlop = $_POST['IdLop'];
$hoten = $_POST['HoTen'];
$gioitinh = $_POST['GioiTinh'];
$ngaysinh = $_POST['NgaySinh'];
$diachi = $_POST['DiaChi'];
$toan = $_POST['Toan'];
$van = $_POST['Van'];
$anh = $_POST['Anh'];
$query = "INSERT INTO hocsinh VALUES(
	null,
	'$idlop',
	'$hoten',
	'$gioitinh',
	'$ngaysinh',
	'$diachi',
	'$toan',
	'$van',
	'$anh')";
if (mysqli_query($connect,$query)) {
	echo "success";
} else {
	echo "failed";
}
?>