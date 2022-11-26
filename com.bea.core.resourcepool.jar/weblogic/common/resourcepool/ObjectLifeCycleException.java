package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public class ObjectLifeCycleException extends ResourceException {
   public ObjectLifeCycleException() {
   }

   public ObjectLifeCycleException(String msg) {
      super(msg);
   }

   public String toString() {
      return "ObjectLifeCycleException: " + super.toString();
   }
}
