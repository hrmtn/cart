# Cart

This is a simple project where the user can add items to a cart and then checkout.
The user can also remove items from the cart, validate the cart to create an order, check on that order also cancel the order.

### Getting Started
First run `docker-compose up` to start the project.

### Usage
You can go to `localhost:8080/api/v1/products` to get the list of the products.
curt command: `curl localhost:8080/api/v1/products` in the terminal.

Find the product you want to add to your cart and run the following command:
```agsl
curl -X 'POST' \
  'http://localhost:8080/api/v1/cart/add' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "productId": "becd5e12-defd-4238-8739-5ac69fb06b0f",
  "quantity": 2
}'
```
You can also remove items from the cart by running the following command:
```agsl
curl -X 'DELETE' \
  'http://localhost:8080/api/v1/cart/becd5e12-defd-4238-8739-5ac69fb06b0f' \
  -H 'accept: */*'
```

When you're satisfied with your cart, you can checkout by running the following command:
```agsl
curl -X 'POST' \
  'http://localhost:8080/api/v1/orders/validate' \
  -H 'accept: */*'
```
Once you validate your order, the api returns an order id that you can use later to check its status.

You can check the status of your order by running the following command:
```agsl
curl -X 'GET' \
  'http://localhost:8080/api/v1/orders/e56afcad-b4b6-4771-ac2e-f7094161a20f' \
  -H 'accept: */*'
```
You can also run the following command for a minimal output:
```agsl
curl -X 'GET' \
  'http://localhost:8080/api/v1/orders/e56afcad-b4b6-4771-ac2e-f7094161a20f/status' \
  -H 'accept: */*'
```

### Messaging
When you validate you cart, a new order gets created and a message is sent to the `validated-orders` topic.
To see the messages coming in, you can run the following that enables you to listen to Kafka messages:
```
docker exec -ti kafka /opt/kafka/bin/kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic validated-orders \
  --from-beginning
```


### TODO
Tests

Deploy to k8s

Native image

Secure the api
