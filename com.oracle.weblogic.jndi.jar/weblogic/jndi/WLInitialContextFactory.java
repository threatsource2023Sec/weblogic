package weblogic.jndi;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import weblogic.utils.LocatorUtilities;

public class WLInitialContextFactory implements InitialContextFactory {
   public final Context getInitialContext(Hashtable env) throws NamingException {
      Environment e = (Environment)LocatorUtilities.getService(Environment.class);
      if (e == null) {
         throw new NamingException("The GlobalServiceLocator was not initialized properly or an Environment could not be created");
      } else {
         e.setInitialProperties(env);
         return e.getContext((String)null);
      }
   }
}
