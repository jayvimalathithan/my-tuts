              ## Setting up CA certificate authority 
Windows to add extrea slash( //CN=Kafka-Secutiry-CA)
1. openssl req -new -newkey rsa:4096 -days 365 -x509 -subj "//CN=Kafka-Security-CA" -keyout ca-key -out ca-cert -nodes
2. ls 
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
    * Certificate was added to keystore 
2. ls
   * ca-cert      ca-key     cert-signed **kafka.server.truststore.jks** ca-cert.srl  cert-file  **kafka.server.keystore.jks**
   ### keystore import ca-cert
3.  keytool -keystore kafka.server.keystore.jks -alias CARoot -import -file ca-cert -storepass $SRVPASS -keypass $SRVPASS -noprompt
   * Certificate was added to keystore
   ### keystore import cert-signed
4.  keytool -keystore kafka.server.keystore.jks -import -file cert-signed -storepass $SRVPASS -keypass $SRVPASS -noprompt
   * Certificate reply was installed in keystore
   ### copy in server.properties
5. 
 listeners=PLAINTEXT://0.0.0.0:9092,SSL://0.0.0.0:9093
 advertised.listeners=PLAINTEXT://##your-public-DNS##:9092,SSL://##your-public-DNS##:9093
 zookeeper.connect=##your-public-DNS##:2181
 ssl.keystore.location=/home/ubuntu/ssl/kafka.server.keystore.jks
 ssl.keystore.password=serversecret
 ssl.key.password=serversecret
 ssl.truststore.location=/home/ubuntu/ssl/kafka.server.truststore.jks
 ssl.truststore.password=serversecret
 
6. restart kafka
7. Open that port 9093[ssl port] in cloud AWS,Azure

# Setting on client side trust store
# Setting left side trust store

1. export CLIPASS=clientpass
2. mkdir client_sll && cd client_sll
3. Download ca-cert file from server
4. keytool -keystore kafka.client.truststore.jks -alias CARoot -import -file ca-cert -storepass $CLIPASS -keypass $CLIPASS -noprompt
   * Certificate was added to keystore
5. ll
  * total 8
  * -rw-r--r-- 1 vvimalat 1049089 1860 Jan 15 14:57 ca-cert
  * -rw-r--r-- 1 vvimalat 1049089 1602 Jan 15 15:01 **kafka.client.truststore.jks**
6. keytool -list -V -keystore kafka.client.truststore.jks
     * Enter keystore password:  clientpass
     ### consumer client properties
7. client.properties[I created under config folder]
  security.protocol=SSL
  ssl.truststore.location=c:\\client_ssl\\kafka.client.truststore.jks
  ssl.truststore.password=clientpass
8. kafka-console-producer --broker-list localhost:9093 --topic topic.vega.do.dev.qc --producer.config %kafka_home%\config\client.properties
9. kafka-console-consumer --bootstrap-server localhost:9093 --topic topic.vega.do.dev.qc --consumer.config %kafka_home%\config\client.properties







