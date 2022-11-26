package weblogic.work;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.jndi.factories.java.javaURLContextFactory;

public class WLSWorkEnvironmentImpl extends WorkEnvironment {
   public void javaURLContextFactoryPopContext() {
      javaURLContextFactory.popContext();
   }

   public void javaURLContextFactoryPushContext(Context context) {
      javaURLContextFactory.pushContext(context);
   }

   public Context javaURLContextFactoryCreateContext() throws NamingException {
      javaURLContextFactory contextFactory = new javaURLContextFactory();
      return (Context)contextFactory.getObjectInstance((Object)null, (Name)null, (Context)null, (Hashtable)null);
   }
}
