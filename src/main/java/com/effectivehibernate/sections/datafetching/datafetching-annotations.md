Eager loading will always brings all the rows of a relation, for example, if you
load an entity called State with a relation of OneToMany to cities, it will load the entire
list of cities when the entity is fetched from the database

Lazy loading on the other hand, will only load if the set is accessed.
By default the relation OneToMany is lazy loaded if not specified otherwise.
Only one select is done, to fetch state for example, and when you are inside an transaction and
access the lazy loaded list, another select will be called to bring the rows to the list.

When you use the entityManager to load an entity from the database in a method without
the @Transactional annotation, Hibernate will call a Temporary Transaction, but,
if you are loading an entity with lazily loaded lists, you have to have an @Transactional in your method, otherwise an exception will be raised, probably LazyInitializationException

In case you want to utilize lazy loading in a method without @Transactional, you can enable the temporary transaction (not recommended, can quickly starve database connections) in application properties, enabling true in the spring.jpa.properties.enable_lazy_load_no_trans

Hibernate has a method called 'Initialize()' that receives an entity as a parameter
and force it to be loaded (lazy relations)

N+1 problem means that you are utilizing N+1 queries to fetch the data you want,
example. Load a list of entities called Company with a lazily loaded list called products, and count the amount of products for each company.
The first select will bring the list of companies, then, after that, you will need
one more select for each company's list of products, translating the n+1 problem.

To fix it, you should use projections or fetch joins.

In JPQL to use FETCH JOIN you need to invert the order of the words,
using JOIN FETCH. Ex: FROM Person p JOIN FETCH p.jobs

The only difference of using FETCH JOIN and the EAGER fetch strategy is that
in the EAGER Fetch strategy a LEFT OUTER JOIN is utilizend, and in the FETCH JOIN a INNER JOIN is utlized

To use FETCH JOIN with the criteria API, you need to utilize the method fetch() fromt the root entity that you defined. Ex:
var root = cq.from(Person.class)
root.fetch("jobs") // Here we pass the attribute name of the entity that we
want to fetch