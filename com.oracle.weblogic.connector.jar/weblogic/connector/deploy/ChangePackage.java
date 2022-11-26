package weblogic.connector.deploy;

import weblogic.connector.exception.RAException;

interface ChangePackage {
   void prepare() throws RAException;

   void activate() throws RAException;

   void rollback() throws RAException;
}
