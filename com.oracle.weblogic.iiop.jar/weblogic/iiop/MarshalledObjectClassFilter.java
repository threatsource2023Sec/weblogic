package weblogic.iiop;

import java.io.InvalidClassException;
import weblogic.corba.utils.CorbaUtils;
import weblogic.corba.utils.MarshalledObject;
import weblogic.iiop.server.BlacklistedClassException;

class MarshalledObjectClassFilter extends MarshalledObject.Filter {
   public void check(String className) throws InvalidClassException {
      try {
         CorbaUtils.checkLegacyBlacklistIfNeeded(className);
      } catch (BlacklistedClassException var3) {
         throw new InvalidClassException(className, var3.getMessage());
      }
   }
}
