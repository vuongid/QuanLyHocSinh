<?php
require "dbConnect.php";

$query = "SELECT * FROM lop";
$data =mysqli_query($connect,$query);

/**
 * 
 */
class Lop 
{
	
	function Lop($id, $tenlop)
	{
		$this->ID = $id;
		$this->TENLOP = $tenlop;
	}
}
$mangLop = array();
while ($row = mysqli_fetch_assoc($data)) {
	array_push($mangLop, new Lop(
		$row['id'],
		$row['tenlop'],
	));
}
echo json_encode($mangLop);
?>