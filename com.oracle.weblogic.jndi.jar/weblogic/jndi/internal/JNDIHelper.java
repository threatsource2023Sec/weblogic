package weblogic.jndi.internal;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.ContextNotEmptyException;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.event.NamespaceChangeListener;
import javax.naming.event.NamingEvent;
import javax.naming.event.NamingExceptionEvent;
import weblogic.jndi.Environment;
import weblogic.jndi.SimpleContext;
import weblogic.jndi.ThreadLocalMap;
import weblogic.jndi.WLContext;
import weblogic.jndi.WLInitialContextFactoryDelegate;
import weblogic.rmi.extensions.server.CBVInputStream;
import weblogic.rmi.extensions.server.CBVOutputStream;
import weblogic.rmi.spi.HostID;

public final class JNDIHelper {
   private JNDIHelper() {
   }

   public static Object copyObject(Object object) throws IOException, ClassNotFoundException {
      CBVOutputStream out = new CBVOutputStream();
      out.writeObject(object);
      out.flush();
      CBVInputStream in = out.makeCBVInputStream();
      Object o = in.readObject();
      out.close();
      in.close();
      return o;
   }

   public static void unbindWithDestroySubcontexts(String name, Context context) throws NamingException {
      if (isReplicateBindings(context)) {
         try {
            unbindWithListenerAndDestroySubcontexts(name, (WLEventContextImpl)context);
         } catch (NamingException var3) {
            debug("+++ unbindWithDestroySubcontexts(" + name + ") unbindWithListenerAndDestroySubcontexts throws NamingException : " + var3 + ". So, fallback to simpleUnbindWithDestroySubcontexts.");
            simpleUnbindWithDestroySubcontexts(name, context);
         }
      } else {
         simpleUnbindWithDestroySubcontexts(name, context);
      }

   }

   public static Class lookupClassType(String nameStr, Context context) throws NamingException {
      Class var5;
      try {
         Hashtable env = new Hashtable();
         env.put("weblogic.jndi.onlyGetClassType", "true");
         ThreadLocalMap.push(env);
         Object obj = context.lookup(nameStr);
         Class ojbectClass;
         if (obj instanceof Class) {
            ojbectClass = (Class)obj;
         } else {
            ojbectClass = obj.getClass();
         }

         var5 = ojbectClass;
      } catch (NamingException var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (Exception var13) {
         throw new NamingException(var13.getMessage());
      } finally {
         ThreadLocalMap.pop();
      }

      return var5;
   }

   public static boolean isBindable(Context context, String name, Object boundObject) throws NamingException {
      try {
         if (context instanceof WLInternalContext) {
            return ((WLInternalContext)context).isBindable(name, boundObject);
         } else {
            throw new OperationNotSupportedException("Only WLInternalContext supports isBindable operation");
         }
      } catch (NamingException var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (Exception var6) {
         throw new NamingException(var6.getMessage());
      }
   }

   public static boolean isBindable(Context context, String name, boolean isAggregatable) throws NamingException {
      try {
         if (context instanceof WLInternalContext) {
            return ((WLInternalContext)context).isBindable(name, isAggregatable);
         } else {
            throw new OperationNotSupportedException("Only WLInternalContext supports isBindable operation");
         }
      } catch (NamingException var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (Exception var6) {
         throw new NamingException(var6.getMessage());
      }
   }

   private static void simpleUnbindWithDestroySubcontexts(String name, Context context) throws NamingException {
      debug("+++ >>> simpleUnbindWithDestroySubcontexts(" + name + ")");
      synchronized(context) {
         context.unbind(name);
      }

      cascadeDestroySubcontext(name, context);
      debug("+++ <<< simpleUnbindWithDestroySubcontexts(" + name + ")");
   }

   private static void unbindWithListenerAndDestroySubcontexts(String name, WLEventContextImpl context) throws NamingException {
      debug("+++ >>> unbindWithListenerAndDestroySubcontexts(" + name + ")");

      try {
         context.getNode().addNamingListener(name, 0, new ObjectRemovedListener(name), context.getEnvironment());
      } catch (Throwable var5) {
         throw ExceptionTranslator.toNamingException(var5);
      }

      synchronized(context) {
         context.unbind(name);
      }

      debug("+++ <<< unbindWithListenerAndDestroySubcontexts(" + name + ")");
   }

   private static boolean isReplicateBindings(Context context) throws NamingException {
      Hashtable env = context.getEnvironment();
      return context instanceof WLEventContextImpl && (env == null || !"false".equals(env.get("weblogic.jndi.replicateBindings")));
   }

   private static void cascadeDestroyFromLeafContext(WLContextImpl context) {
      try {
         String initialNode = context.getNode().getNameInNamespace();
         debug("+++ >>> cascadeDestroyFromLeafContext(" + initialNode + ")");

         while(context.getNode().getParent() != null) {
            Name currentName = context.getNameParser("").parse(context.getNode().getNameInNamespace());
            Name leafNodeName = currentName.getSuffix(currentName.size() - 1);
            WLContextImpl parentContext = (WLContextImpl)context.getNode().getParent().getContext((Hashtable)null);
            debug("+++ cascadeDestroyFromLeafContext(" + initialNode + ") : parent context name : " + parentContext.getNameInNamespace() + ", subcontext for destroy : " + leafNodeName.toString());
            if (!destroyContext(leafNodeName, parentContext)) {
               return;
            }

            context = parentContext;
         }

         debug("+++ <<< cascadeDestroyFromLeafContext(" + initialNode + ")");
      } catch (NamingException var5) {
         debug("+++ cascadeDestroyFromLeafContext caught exception : " + var5);
      } catch (RemoteException var6) {
         debug("+++ cascadeDestroyFromLeafContext() caught exception : " + var6);
      }

   }

   private static void cascadeDestroySubcontext(String name, Context context) {
      try {
         debug("+++ >>> cascadeDestroySubcontext(" + name + ")");
         Name currentName = context.getNameParser("").parse(name);

         while(currentName.size() > 1) {
            currentName = currentName.getPrefix(currentName.size() - 1);
            if (!destroyContext(currentName, context)) {
               return;
            }
         }
      } catch (NamingException var3) {
         debug("+++ cascadeDestroySubcontext(" + name + ") caught exception : " + var3);
      }

   }

   private static boolean destroyContext(Name name, Context context) {
      synchronized(context) {
         try {
            context.destroySubcontext(name);
         } catch (NameNotFoundException var5) {
            debug("+++ destroyContext(" + name + ") caught exception : " + var5);
         } catch (ContextNotEmptyException var6) {
            debug("+++ destroyContext(" + name + ") caught exception : " + var6);
            return false;
         } catch (Exception var7) {
            debug("+++ destroyContext(" + name + ") caught exception : " + var7);
            return false;
         }

         return true;
      }
   }

   private static void debug(String s) {
      if (NamingDebugLogger.isDebugEnabled()) {
         NamingDebugLogger.debug(s);
      }

   }

   public static void bindNonListable(Context ctx, String name, Object obj) throws NamingException {
      ctx.bind(name, new NonListableRef(obj));
   }

   public static void createNonListableSubcontext(Context ctx, String name) throws NamingException {
      if (ctx instanceof SimpleContext) {
         ((SimpleContext)ctx).createNonListableSubcontext(name);
      } else {
         boolean hasNonistableProperty = false;

         try {
            hasNonistableProperty = ctx.getEnvironment().containsKey("weblogic.jndi.createNonListableNode");
            if (!hasNonistableProperty) {
               ctx.addToEnvironment("weblogic.jndi.createNonListableNode", "true");
            }

            ctx.createSubcontext(name);
         } finally {
            if (!hasNonistableProperty) {
               ctx.getEnvironment().remove("weblogic.jndi.createNonListableNode");
            }

         }

      }
   }

   public static final WLContext getInitialWLContext(Hashtable env) throws NamingException {
      Context context = WLInitialContextFactoryDelegate.theOne().getInitialContext(env);
      return (WLContext)context;
   }

   public static final WLContext getInitialWLContext(Environment env, String subCtxName) throws NamingException {
      Context context = WLInitialContextFactoryDelegate.theOne().getInitialContext(env, subCtxName);
      return (WLContext)context;
   }

   public static final WLContext getInitialWLContext(Environment env, String subCtxName, HostID hostID) throws NamingException {
      Context context = WLInitialContextFactoryDelegate.theOne().getInitialContext(env, subCtxName, hostID);
      return (WLContext)context;
   }

   private static class ObjectRemovedListener implements NamespaceChangeListener {
      String name;

      ObjectRemovedListener(String name) {
         this.name = name;
      }

      public void namingExceptionThrown(NamingExceptionEvent evt) {
      }

      public void objectAdded(NamingEvent evt) {
      }

      public void objectRenamed(NamingEvent evt) {
      }

      public void objectRemoved(NamingEvent evt) {
         if (!(evt.getEventContext() instanceof WLEventContextImpl)) {
            JNDIHelper.debug("+++ objectRemoved(" + this.name + ") does nothing because given context is not WLS's.");
         } else {
            JNDIHelper.debug("+++ >>> objectRemoved(" + this.name + ")");
            WLEventContextImpl context = (WLEventContextImpl)evt.getEventContext();
            synchronized(context) {
               try {
                  context.removeNamingListener(this);
               } catch (NamingException var6) {
               }
            }

            JNDIHelper.cascadeDestroyFromLeafContext(context);
            JNDIHelper.debug("+++ <<< objectRemoved(" + this.name + ")");
         }
      }
   }
}
