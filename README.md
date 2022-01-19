# How to setup the coindesk project
1. Create a folder in your computer with a short path without spaces, such as ```C:\y```
2. Copy the repository URL from GitHub
3. Open the **Terminal/Git Bash**, navigate to ```C:\y``` and run ```git clone```
4. Navigate to root folder of the project and run ```git checkout dev``` to
   checkout to the ```dev``` branch of the project to get all the latest code in your local repository
5. Run ```mvn installl``` to install all required dependencies.
  
# How to execute the project and initialize database
1. Execute ```mvn spring-boot:run``` in **Terminal/Git Bash** to run the server
2. Before establish the coin and chinese name relationship, you can run following SQL to import coin name locale data. :
  ```
  Insert into COIN_NAME_LOCALE (COIN_NAME_ID, CHINESE_NAME, NAME) Values 
  (1, '美金', 'United States Dollar'), 
  (2, '英鎊', 'British Pound Sterling'), 
  (3, '歐元', 'Euro');
  ```

# How to check data in H2 and create SQL table
1. Open browser and you can access the H2 console trough: http://localhost:8080/h2-console
2. Enter following configuration and press **connect**
  
   JDBC URL :```jdbc:h2:mem:mydb```
  
   User name:```raphael```
  
   Password :```123456``` 
3. To create ```coin_info``` table. you can run following SQL:
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
4. To create ```coin_name_locale``` table. you can run following SQL:
  ```
  Create table coin_name_locale (
      coin_name_id int(255),
      chinese_name varchar(255),
      name varchar(255)
  );
  ```
# How to test the API
1. Download the Postman
2. import ```Coindesk API.postman_collection.json``` file into Postman
3. Start to test the APIs in the collection

# How to run the unit tests
1. Make sure the coin_name_locale data is already exist in database.
2. run each test in CoindeskApplicationTests sequencely
