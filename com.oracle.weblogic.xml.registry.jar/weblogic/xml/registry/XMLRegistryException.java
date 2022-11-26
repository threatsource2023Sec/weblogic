package weblogic.xml.registry;

import weblogic.utils.NestedException;

public class XMLRegistryException extends NestedException {
   public XMLRegistryException(String msg) {
      super(msg);
   }

   public XMLRegistryException(Throwable th) {
      super(th);
   }

   public XMLRegistryException(String msg, Throwable th) {
      super(msg, th);
   }
}
