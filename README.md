This project aims to store nominees and winners of the worst picture category of the Golden Raspberry Awards.

**To run the project:**  
1 - Expand the `src/main/java/com/outsera/movies` package.  
2 - Click the right button on `MoviesApplication` (java class).  
3 - Click the left button on `Run MoviesApplication...main()` option.  
4 - The application must start.

Steps 5 until 7 were tested using the Chrome browser  
5 - To retrieve all studios pre-inserted (imported from csv file when the application started), access the browser:  
http://localhost:8080/api/studios

6 - To retrieve all producers pre-inserted (imported from csv file when the application started), access the browser:  
http://localhost:8080/api/producers

7 - To retrieve all movies pre-inserted (imported from csv file when the application started), access the browser:  
http://localhost:8080/api/movies

**To run all the integration testings:**  
1 - Expand the `src/test/java/com/outsera/movies` package.  
2 - Click the right button on the `movies` package.  
3 - Click the left button on `Run Tests in movies` option.  
4 - The integration testings must run.