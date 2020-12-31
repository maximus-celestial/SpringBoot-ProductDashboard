# SpringBoot-ProductDashboard
SpringBoot Product Dashboard was a question from HackerRank as a part of Coding Interview Test for Kin+Carta

### To install
 - Git Clone the Repository
 - Import the code in Eclise as an existing Maven Project
 - Project -> Mavn -> Update Project 
 - Open the file "com.hackerrank.eshopping.product.dashboard.Application.java" in /src/main/java, Right Click and Run as Java Application
 - Open the browser with url - http://localhost:8080/products - Will give an [] inittially.
 - Open the H2 console - http://localhost:8080/h2-console - give the database as  jdbc:h2:mem:testdb with its default credentails
 - To run the Junit test cases - com.hackerrank.eshopping.product.dashboard.controller.ProductsControllerTest in /src/test.java, right click and rin as Junit test case - this will run all the 17 tests. 
 - To run a specific test, refer http://www.eclipseonetips.com/2014/06/16/run-a-single-junit-test-method-in-eclipse/ 
 
 ### Sample test data in h2 console - espcially for run for Junit test case - Order(15)
 
 ```sql
 
 delete from product;
 
insert into product (id, availability, category, discounted_price, name, retail_price) values(1,TRUE,'Full Body Outfits',251.49,'Dressing Gown',303.00);
insert into product (id, availability, category, discounted_price, name, retail_price) values(2,TRUE,'Footwear',123.00,'Shoes',150.00);
insert into product (id, availability, category, discounted_price, name, retail_price) values(3,TRUE,'Full Body Outfits',254.81,'Nightgown',307.00);
insert into product (id, availability, category, discounted_price, name, retail_price) values(5,TRUE,'Full Body Outfits',272.97,'Ball Gown',337.00);
insert into product (id, availability, category, discounted_price, name, retail_price) values(9,TRUE,'Full Body Outfits',321.64,'Overalls',374.00);
insert into product (id, availability, category, discounted_price, name, retail_price) values(14,TRUE,'Full Body Outfits',140.00,'Dress',175.00);
insert into product (id, availability, category, discounted_price, name, retail_price) values(16,FALSE,'Full Body Outfits',423.90,'Tracksuit',471.00);
insert into product (id, availability, category, discounted_price, name, retail_price) values(17,TRUE,'Full Body Outfits',254.81,'Tailcoat',307.00);
insert into product (id, availability, category, discounted_price, name, retail_price) values(19,FALSE,'Full Body Outfits',100.00,'Suit',125.00);
insert into product (id, availability, category, discounted_price, name, retail_price) values(20,FALSE,'Full Body Outfits',200.00,'Catsuit',250.00);
insert into product (id, availability, category, discounted_price, name, retail_price) values(21,FALSE,'Full Body Outfits',362.71,'Dungarees',437.00); 
 
 select * from product;
 
 ```
 
 ### URIs
 
#### GET Requests
 
 http://localhost:8000/products

http://localhost:8000/products/1

http://localhost:8000/products/s - 404 Error

http://localhost:8000/products?category=Full%20Body%20Outfits&availability=0

http://localhost:8000/products?category=Full%20Body%20Outfits&availability=1

http://localhost:8000/products?category=Full%20Body%20Outfits



#### POST Request in Postman 

POST http://localhost:8000/products

```json
{                                     
    "id": 20,                         
    "name": "Catsuit",                
    "category": "Full Body Outfits",  
    "retail_price": 250.0,            
    "discounted_price": 200.0,       
    "availability": false              
}

```
new id - add products, 201 created

exisiting id - 400 bad request


// PUT Request

PUT http://localhost:8000/products/20

```json
{
    "retail_price": 252.0,            
    "discounted_price": 200.0,       
    "availability": true              
}

```

new id - 400 bad request

exisiting id - save details while retaining other details, 200 OK

 
 
 
 
 
