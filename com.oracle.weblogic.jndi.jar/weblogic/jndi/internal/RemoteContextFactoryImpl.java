package weblogic.jndi.internal;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import javax.naming.NotContextException;
import javax.naming.spi.InitialContextFactory;

public class RemoteContextFactoryImpl implements RemoteContextFactory {
   public Context getContext(Hashtable env, String subCtxName) throws NamingException {
      Hashtable delegateEnv = null;

      try {
         if (env != null) {
            delegateEnv = (Hashtable)env.get("weblogic.jndi.delegate.environment");
         }
      } catch (ClassCastException var12) {
         throw new NoInitialContextException("Property weblogic.jndi.delegate.environment (" + env.get("weblogic.jndi.delegate.environment") + ") is not an instance of Hashtable");
      }

      Context ctx;
      if (delegateEnv == null) {
         ctx = RootNamingNode.getSingleton().getContext(env);
      } else {
         String factoryName = (String)delegateEnv.get("java.naming.factory.initial");
         if (factoryName == null) {
            throw new NoInitialContextException("Property weblogic.jndi.delegate.environment.java.naming.factory.initial is null");
         }

         InitialContextFactory factory;
         try {
            factory = (InitialContextFactory)Class.forName(factoryName).newInstance();
         } catch (ClassNotFoundException var9) {
            throw new NoInitialContextException("Failed to find weblogic.jndi.delegate.environment.java.naming.factory.initial (" + factoryName + ") on server");
         } catch (ClassCastException var10) {
            throw new NoInitialContextException("Class specified by weblogic.jndi.delegate.environment.java.naming.factory.initial (" + factoryName + ") does not implement InitialContextFactory");
         } catch (Exception var11) {
            NamingException ne = new NoInitialContextException("Failed to instantiate weblogic.jndi.delegate.environment.java.naming.factory.initial (" + factoryName + ") on server");
            ne.setRootCause(var11);
            throw ne;
         }

         ctx = factory.getInitialContext(delegateEnv);
      }

      if (subCtxName != null) {
         Object o = ctx.lookup(subCtxName);
         if (!(o instanceof Context)) {
            throw new NotContextException(subCtxName + " is no a context");
         }

         ctx = (Context)o;
      } else {
         ctx = (Context)WLNamingManager.getTransportableInstance(ctx, (Name)null, (Context)null, env);
      }

      return ctx;
   }

   static void initialize() {
   }
}
