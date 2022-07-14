The main objective of projections is to limit the data of the object that
is fetched from the database to the minimum possible, loading only the required
fields.

Is it possible to use projections with 3 different forms:

**JPQL (Very cumbersome)**

- You have to create an object with the fields that you want to fetch
- The syntax of the query is SELECT NEW <FULLY QUALYFIED NAME OF THE OBJECT 
  CREATED> passing the constructor parameters of the fields you want

**Criteria API (Little less Cumbersome)**

- You also have to create an object containing only the fields you want
- The type of the criteriaQuery will be your newly created class
- When calling the select, the trick is to use the construct method to 
  pass the class you ve created, and all the fields you want within from.get()
  calls

**Spring Data (Easiest way)**

- Utilizes repository
- You need to create the object with the fields you want to return
- The return type of the methods in the repository interface will be the created object

**Benefits**

- Select only the fields that are relevant for your query
- Less bytes loaded from database

**Covering Index**

- Select only the fields within indexes, eliminating the need to go to the disc
- Get all the needed data from the index
- Decrease the cost (%CPU) of the query
