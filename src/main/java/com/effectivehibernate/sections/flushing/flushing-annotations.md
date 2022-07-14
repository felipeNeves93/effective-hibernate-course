**Flushing**

- Its the proccess of propagating the changes of your entities to the database.
- Its up to the jpa provider to do this
- When you commit a transaction, you are utilizing the flush
- Equalize the transaction context with the persistence context
- All the transactions run in the automatic flush mode

**JPA Flush Modes**

- **JPA Auto Mode:** The default mode. All the pending changes should be visible by an executed query.
  All the changes must be flushed before executing an native query. There are three modes 
  of how the auto flush works:
  - *Auto Flush with JPQL Query with same entity*: In this mode, we have the following example:
     * We insert a *PersonEntity* in the database using the *persist()* method
     * We create an select query to fetch data from *PersonEntity's* table
     * When hibernate detects that we are calling the *PersonEntity* table, it will flush automatically
  - *Auto Flush with JPQL Query with different entity*: In this mode, we have the following example:
    * We insert a *PersonEntity* in the database using the *persist()* method
    * We create an select query to fetch data from *AnotherPersonEntity* table
    * The Insert will happen only at the end of the transaction, opposing to the previous example,
      because we are working with different entities, *PersonEntity* and *AnotherPersonEntity*
  - *Auto Flush with Native Query*: In this mode, we have the following example:
    * We insert a *PersonEntity* using the *persist()* method
    * We create an native query to fetch the data from the *PersonEntity* Table
    * The *PersistenceContext* will be flushed before the select native query is executed
    * This will happen because when we use native queries, hibernate will ensure that the
      the query will have access to the most recent state of the data
  
- **JPA Commit Mode:** This mode ensures that the changes will be flushed only
    when commiting the transaction. This rule will always be respected, even in situations
    like the examples of the previous mode.

**Hibernate Flush Modes**

  - *Always Flush Mode*: 
     * A flush will always happens before the query
     * Before a query is executed, all the changes that are peding are going to be sent to the database.
  - *Manual Flash Mode*:
     * The flush will happens only if triggered by the developer
     * You have to manually call the method *entityManager.flush()*