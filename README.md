This project aims to store nominees and winners of the worst picture category of the Golden Raspberry Awards.

**To run the project:**  
1 - Expand the `src/main/java/com/outsera/movies` package.  
2 - Click the right button on `MoviesApplication` (java class).  
3 - On the dialog opened, click the left button on `Run MoviesApplication...main()` option.  
4 - The application must start automatically.  
5 - The initial data must be imported from csv file to the database automatically.  

Steps 5 until 8 were tested using the Chrome browser  
5 - To retrieve all studios pre-inserted, access the browser with the url:  
http://localhost:8080/api/studios

6 - To retrieve all producers pre-inserted, access the browser with the url:  
http://localhost:8080/api/producers

7 - To retrieve all movies pre-inserted, access the browser with the url:  
http://localhost:8080/api/movies

8 - To retrieve the winner producers so minimum interval so maximum interval, access the browser with the url:  
http://localhost:8080/api/producers/winners-with-min-and-max-interval

**To run all the integration testings:**  
1 - Expand the `src/test/java/com/outsera/movies` package.  
2 - Click the right button on the `movies` package.  
3 - On the dialog opened, click the left button on `Run Tests in movies` option.  
4 - The integration testings must run automatically.