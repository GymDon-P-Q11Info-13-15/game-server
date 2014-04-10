<?php
header('Access-Control-Allow-Origin: *');
$resp = new Response();
header('Content-Type: application/json; charset=UTF-8', true, $resp->http_status);
echo json_encode($resp);

class Response {
	public $http_status = 200;
	public $success = true;
	public $servertime;
	public $serve_time;
	
	function __construct() {
		try{
		$this->success = true;
		$start = microtime(true);
		$this->servertime = time();
		$this->serve_time = microtime(true) - $start;
		}catch(Exception $e) {
			$this->success = false;
			$this->http_status = 400;
		}
	}
}

class Result {
	
	function get() {
		return $this;
	}
}
?>