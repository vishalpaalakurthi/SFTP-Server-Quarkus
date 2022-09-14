# sftp-server-quarkus



# Usage
## Environment Properties
1) sftp_server_port - SFTP Port (default: 22)
2) sftp_server_host - Host (default: localhost) 
3) server_cert_path - p12 certificate path
4) server_cert_password - p12 certificate password
5) sftp_user - user name (default: admin)
6) sftp_password - user password (default: admin)
7) directory_path - SFTP default file system path


# Build command
mvn clean compile package -Dquarkus.package.type=uber-jar -DskipTests

# Run jar
java -Dsftp_server_port=9022  -jar sftp-server-1.0.0-SNAPSHOT-runner.jar
