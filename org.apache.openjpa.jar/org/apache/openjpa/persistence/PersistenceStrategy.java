package org.apache.openjpa.persistence;

public enum PersistenceStrategy {
   BASIC,
   MANY_ONE,
   ONE_MANY,
   ONE_ONE,
   MANY_MANY,
   EMBEDDED,
   TRANSIENT,
   PERS,
   PERS_COLL,
   PERS_MAP;
}
