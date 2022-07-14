**JDBC Level statement Batching**

- Means that we can insert several rows withing one insert statement
- We can utilize the prepared statement and the *addBatch()* method to do this
- After calling the batch method, we can insert any numbers of objects we want
- At the end after adding all the objects, you can call the *executeBatch()* method.
- Only one INSERT command will be sent

**Hibernate Batching**

- Batch size is a very important configuration to the application performance
- It utilizes the configuration *spring.jpa.properties.jdbc.batch_size=?* to set the batch size for the application.
- When there is an insert statement with the number of rows to be created within the range of the configuration, it will only execute one insert for the multiple rows
- Starting on *Hibernate 5.2*, you can set the batch size based on transaction.

**Advanced Hibernate Batching Configuration**

- To utilize the batch size configuration at the transaction level, you should use the *entityManager.unwrap(Session.class).setJdbcBatchSize(number)* method.
- When you utilize this kind of configuration, it will override the global one
- You can enable batching for update commands as well and utilize batch for versioned entities
- You can control wether to enable or disable (enable by default) the batch for versioned entities with the *spring.jpa.properties.hibernate.jdbc.batch_versioned_data=boolean* property
- The insert order and update order can be configured to group statements in hibernate
- When inserting a large number of entities, its a good practice to do regular flushes and clears in the entity manager to send the flushed entity changes from the entity manager to the database
- When doing this, it is possible to control the peaks of memory consuption when inserting a large number of entities in the database