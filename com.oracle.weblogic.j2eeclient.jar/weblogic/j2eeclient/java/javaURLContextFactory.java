package weblogic.j2eeclient.java;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import weblogic.j2eeclient.jndi.Environment;

public final class javaURLContextFactory implements ObjectFactory {
   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
      Context ctx = Environment.instance().getLocalRootCtx();
      return new ClientReadOnlyContextWrapper(ctx);
   }
}
