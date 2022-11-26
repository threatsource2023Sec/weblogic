package com.oracle.weblogic.lifecycle.provisioning.api;

public interface TransactionalHandler {
   void commit();

   void rollback();
}
