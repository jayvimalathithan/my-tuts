              # Setting up CA certificate authority
Windows to add extrea slash( //CN=Kafka-Secutiry-CA)
1. openssl req -new -newkey rsa:4096 -days 365 -x509 -subj "//CN=Kafka-Security-CA" -keyout ca-key -out ca-cert -nodes
2. ll
3. cat ca-key
4. cat ca-cert

			 # Setting up keystore

1. export SRVPASS=serversecret
2.  keytool -genkey -keystore kafka.server.keystore.jks -validity 365 -storepass $SRVPASS --keypass $SRVPASS -dname "CN=localhost" -storetype pkcs12
3. ls
   ### to view keystore file
4. keytool -list -v -keystore kafka.server.keystore.jks
   Enter Password: serversecret
   ### Requesting a ca-cert [usually done at user side]
5. keytool -keystore kafka.server.keystore.jks -certreq -file cert-file -storepass $SRVPASS 
   -keypass $SRVPASS
6. ls [Note an extra file named cert-file after requesting certificate]
7. send ca-cert file to admininstrator and get it signed version of certificate
  ### Signing on our own [server side] [we will get new file named cert-signed]
 8.  openssl x509 -req -CA ca-cert -CAkey ca-key -in cert-file -out cert-signed -days 365 -CAcreateserial -passin pass:$SRVPASS
    ### print the cert [file name: cert-signed]
 9. keytool -printcert -v -file cert-signed

 		# Setting up Trust store
1.  keytool -keystore kafka.server.truststore.jks -alias CARoot -import -file ca-cert -storepass $SRVPASS -keypass $SRVPASS -noprompt
    *Certificate was added to keystore*







