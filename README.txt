# Aplicacion con Spring

	Aplicacion con el uso de Spring security, Mybatis como persistencia y JSF + Primefaces como vista.

## Requisitos
	Java 6 o superior
	productividad, aplicar un mvn install
	Servidor de aplicaciones JBoss.
	
## Base de Datos 
	Conexion a Base de Datos Oracle.
		
## Configuración de Bd en el Proyecto	

	Modificar el archivo: application-config.default.properties

	db.driver=oracle.jdbc.driver.OracleDriver
	db.url=jdbc:oracle:thin:@SERVER-ORACLE:1521/DEV
	db.user=USER
	db.password=PASSWORD
	db.initialize=false
