package weblogic.application.naming;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

public class URLObjectFactory implements ObjectFactory {
   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
      EnvReference ref = (EnvReference)obj;

      try {
         return new URL(ref.getJndiName());
      } catch (MalformedURLException var7) {
         throw new AssertionError("Cannot create URL" + var7);
      }
   }
}
