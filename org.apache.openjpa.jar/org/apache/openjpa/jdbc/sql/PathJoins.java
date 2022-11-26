package org.apache.openjpa.jdbc.sql;

interface PathJoins extends Joins {
   PathJoins setOuter(boolean var1);

   boolean isDirty();

   StringBuffer path();

   JoinSet joins();

   int joinCount();

   void nullJoins();
}
