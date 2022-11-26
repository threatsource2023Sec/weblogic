package weblogic.servlet.provider;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.jndi.factories.java.javaURLContextFactory;
import weblogic.jndi.internal.ApplicationNamingNode;
import weblogic.servlet.spi.JNDIProvider;

public class WlsJNDIProvider implements JNDIProvider {
   public Context lookupInitialContext() throws NamingException {
      Hashtable env = new Hashtable();
      env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      env.put("weblogic.jndi.createIntermediateContexts", "true");
      return new InitialContext(env);
   }

   public void pushContext(Context context) {
      javaURLContextFactory.pushContext(context);
   }

   public void popContext() {
      javaURLContextFactory.popContext();
   }

   public Context createApplicationContext(String name) throws NamingException {
      Hashtable env = new Hashtable(2);
      env.put("weblogic.jndi.createIntermediateContexts", "true");
      env.put("weblogic.jndi.replicateBindings", "false");
      Context appCtx = (new ApplicationNamingNode()).getContext(env);
      appCtx.createSubcontext(name);
      return appCtx;
   }
}
