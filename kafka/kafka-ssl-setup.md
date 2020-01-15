              ## Setting up CA certificate authority 
Windows to add extrea slash( //CN=Kafka-Secutiry-CA)
1. openssl req -new -newkey rsa:4096 -days 365 -x509 -subj "//CN=Kafka-Security-CA" -keyout ca-key -out ca-cert -nodes
2. ls 
3. cat ca-key
4. cat ca-cert



