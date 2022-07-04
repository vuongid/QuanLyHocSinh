<?php
require "dbConnect.php";

$idlop = $_POST['IDLOP'];
$query = "SELECT * FROM hocsinh WHERE idlop = '$idlop'";
$data = mysqli_query($connect,$query);

/**
 * 
 */
class HocSinh 
{
	
	function HocSinh($id,$idlop,$hoten,$gioitinh,$ngaysinh,$diachi,$toan,$van,$anh)
	{
			$this->ID = $id;
			$this->IDLOP = $idlop;
			$this->HOTEN = $hoten;
			$this->GIOITINH = $gioitinh;
			$this->NGAYSINH =$ngaysinh;
			$this->DIACHI =$diachi;
			$this->TOAN = $toan;
			$this->VAN = $van;
			$this->ANH = $anh;
	}
}

$mangHS = array();
while ($row = mysqli_fetch_assoc($data)) {
	array_push($mangHS, new HocSinh(
		$row['id'],
		$row['idlop'],
		$row['hoten'],
		$row['gioitinh'],
		$row['ngaysinh'],
		$row['diachi'],
		$row['toan'],
		$row['van'],
		$row['anh']
	));
}
echo json_encode($mangHS);
?>