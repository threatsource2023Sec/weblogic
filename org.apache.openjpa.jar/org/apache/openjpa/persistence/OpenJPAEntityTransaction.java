package org.apache.openjpa.persistence;

import javax.persistence.EntityTransaction;

public interface OpenJPAEntityTransaction extends EntityTransaction {
   void commitAndResume();

   void rollbackAndResume();

   void setRollbackOnly(Throwable var1);

   Throwable getRollbackCause();
}
