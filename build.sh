echo "Compilation ..." 
mvn clean install -DskipTests 
echo "Creating build ..." 
docker build -t vikason/user-service:latest . 
echo "Push to docker"
docker push vikason/user-service:latest
echo "Deploying .."
kubectl rollout restart deployment/user-service -n saas-control
echo "finished" 

