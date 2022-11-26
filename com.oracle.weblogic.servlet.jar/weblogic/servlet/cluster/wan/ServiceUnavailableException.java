package weblogic.servlet.cluster.wan;

import java.rmi.NoSuchObjectException;

public class ServiceUnavailableException extends NoSuchObjectException {
   public ServiceUnavailableException(String msg) {
      super(msg);
   }
}
