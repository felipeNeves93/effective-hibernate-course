The state **managed**, hibernate keeps track of the changes made in the entity.

In the state **detached**, hibernate ignores the changes made to the entity

The 'merge()' operation has the objective to make the state of the entity that you are passing the same
as the one that is saved in the database.
An extra select will be executed when utilizing the merge() operation