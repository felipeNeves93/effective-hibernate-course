There are two ways in Hibernate/JPA to deal with conflicts (when for example two
updates happens at the same time for the same row): **Optimistic Locking** when
you want to detect conflicts, and **Pessimist Locking** when you want to prevent
conflicts, both have advantages and disavantages.

Usually, the **Optimistic Locking** is utilized when conflicts are very rare,
and you just want to react and rollback the transaction. **Pessimist Locking** on the
other hand, is utilized when conflicts are frequent, and when you work with
an specific entity that you want to ensure that nobody has access to while
you are working, means more contention

**Versioned Optimistic Locking**

- One of the most utilized ways of dealing with concurrent transactions
- Tracks the version of the object, a field must be created in the entity called version (int)
  and marked with the **@Version** annotation.
- Forces to work with the current state of the object, otherwise will launch an exception

**Versionless Optimistic Locking for non-overlapping changes**

- It happens when two transactions are executed at the same time, and one transaction wants to update a different field than the other
- To solve this issue we can create two separated entities with two version fields 
  with an one-to-one relation to be able to work concurrently. Not always the best
  solution
- The other way to deal with this issue is to enforce that each transaction queries
  the database with the field that its going to change, for example: 
    - Transaction A wants to update the stockValue field, the current value is 1
    - Transaction B wants to change the name value, the current value is "product-one"
    - Transaction A starts then B starts while A is executing
    - Transaction A filters for the stock value, while B filters for the name value
    - In this cenario, there is no colision or optimistic locking exception, because
      the filtered fields are different
- To do this, you need to use the **@DynamicUpdate** annotation at the entity, meaning, it will generate queries
  on the fly putting only the fields that are changing in the query. (The default behavior is to hibernate to cache the queries at the
  start of the app)
- You also need to utilize the **@OptimisticLocking(type=OptimisticLockType.DIRTY)** annotation, meaning
  that will utilize the dirty attributes *(the ones being modified)* as part of an expanded
  query

**Explicit Optimistic Locking**

 - There are two ways to pass the explicit optimistic locking: Passing the **LockModeType.OPTIMISTIC**
   to the find method in entity manager, or calling the lock() method from the entity manager
 - This type of locking is rarely utilized in real life applications
 - When you utilize **LockModeType.OPTIMISTIC**, you force the hibernate to check the transaction at the
   ending, wether a change happened or not
 - Its only applicable to entities that have the **@Version** annotation
 - There is another excplicit Optimistic Locking mode, the **LockModeType.OPTIMISTIC_FORCE_INCREMENT**
 - This lock mode will always increment the version number of the entity, even if it wasnt changed

**Pessimistic Locking**

- This way of locking prevents conflicts from happening
- Locks are held until the end of the transaction
- Utilizes database level locks
- The are two ways of doing database locks: **Shared Lock** and **Exclusively Lock**
- Shared Lock allows to have multiple readers at the same time
- Exclusively lock blocks everyone else excluding you to access the data
- Use cases are rare because you are creating more contention to your database
- There are three ways to implement Pessimistic Locking in hibernate: **PESSIMISTIC FORCE_INCREMENT**,
  **PESSIMISTIC_READ** and **PESSIMISTIC_WRITE**
- PESSIMISTIC FORCE_INCREMENT while grants an exclusively lock to the data, also increments the version
  number of the entity with any operation, its a combination between both optimistic and pessimistic locking
- PESSIMISTIC_READ is a shared lock. There are multiple readers to the data, but no writer
- H2 Database doesn't support shared locks, only exclusively locks
- PESSIMISTIC_WRITE is a exclusively lock, only allowing one writer