# How to setup the coindesk project
1. Create a folder in your computer with a short path without spaces, such as ```C:\y```
2. Copy the repository URL from GitHub
3. Open the **Terminal/Git Bash**, navigate to ```C:\y``` and run ```git clone```
4. Navigate to root folder of the project and run ```git checkout dev``` to
   checkout to the ```dev``` branch of the project to get all the latest code in your local repository
5. Run ```mvn installl``` to install all required dependencies.
  
# How to execute the project
1. Execute ```mvn spring-boot:run``` in **Terminal/Git Bash** to run the server

# How to check data in H2 and create SQL table
1. Open browser and you can access the H2 console trough: http://localhost:8080/h2-console
2. Enter following configuration and press **connect**
  
   JDBC URL :```jdbc:h2:mem:mydb```
  
   User name:```raphael```
  
   Password :```123456``` 
3. To create coin_info table. you can run following SQL:
  ```
  Create table coin_Info (
      id int(255),
      code varchar(255),
      name varchar(255),
      symbol varchar(255),
      description varchar(255),
      rate varchar(255),
      rate_float float(24),
      updatedTime varchar(255)
  );
  ```
# How to test the API
1. Download the Postman
2. import ```Coindesk API.postman_collection.json``` file into Postman
3. Start to test the APIs in the collection
