<?php
header('Access-Control-Allow-Origin: *');
$resp = new Response();
header('Content-Type: application/json; charset=UTF-8', true, $resp->http_status);
echo json_encode($resp);

class NotFoundException extends Exception {}
class NotAuthorizedException extends Exception {}
class WrongAuthorizationException extends Exception {}

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
			if($e instanceof NotFoundException)
				$this->http_status = 404;
			if($e instanceof NotAuthorizedException)
				$this->http_status = 401;
			if($e instanceof WrongAuthorizationException)
				$this->http_status = 403;
		}
	}
}

class Result {
	
	function get() {
		return $this;
	}
}
?>