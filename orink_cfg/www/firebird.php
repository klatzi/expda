<?php
class Firebird {
	private static $link = null ;
	
	private static function getLink($tip){
		if (self::$link) return self::$link;

        $driver="firebird";
        $user="SYSDBA";
        $password="masterkey";
        $host="192.168.1.105";
//        $host="192.168.1.248";        
        $dbname_depo="F:\ALFA\TIR\DAT\DEPO12\DEPO12.GDB" ;
        $dbname_orink="F:\ALFA\TIR\DAT\ORINK\ORINKMUNKA.GDB" ;
        $dbname_kende="F:\ALFA\TIR\DAT\KENDE\KENDE.GDB" ;        
//        $dbname_orink="/var/lib/firebird/2.5/data/ORINKMUNKA.GDB" ;
        if ($tip=='kende') $dbname=$dbname_kende;
        if ($tip=='orink') $dbname=$dbname_orink;
        if ($tip=='depo') $dbname=$dbname_depo;
                

		$port="3050";
		
		$dsn = "${driver}:";
		$dsn .= 'dbname='.$host.':'.$dbname;
		$options=array();
		
		self::$link = new PDO($dsn, $user, $password, $options);
		$attributes = array();
		foreach ($attributes as $k => $v)
			self::$link->setAttribute(constant("PDO::{$k}"), constant( "PDO::{$v}" ));
		return self :: $link ;
	}

	/**
	 * Statikus PDO osztályhívások
	 * @param string $name
	 * @param string $args
	 * @return PDO
	 */
	public static function __callStatic($name, $args){
		$callback = array(self::getLink(), $name);
		return call_user_func_array($callback, $args);
	}
	
	public static function prepare($sql,$tip){
		return self::getLink($tip)->prepare($sql);
	}

}
?>