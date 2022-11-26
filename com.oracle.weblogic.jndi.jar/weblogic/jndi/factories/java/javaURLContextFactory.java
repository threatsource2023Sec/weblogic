package weblogic.jndi.factories.java;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.jndi.Environment;
import weblogic.jndi.SimpleContext;
import weblogic.jndi.internal.AbstractURLContext;
import weblogic.kernel.KernelStatus;
import weblogic.kernel.ThreadLocalStack;
import weblogic.management.internal.SecurityHelper;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.transaction.TransactionHelper;
import weblogic.workarea.WorkContextHelper;

@Service
public final class javaURLContextFactory implements ObjectFactory, FastThreadLocalMarker {
   private static final Reference IIOP_OBJECT_REFERENCE = new Reference((String)null, "weblogic.iiop.jndi.IiopObjectFactory", (String)null);
   private static Context defaultContext;
   private static Object runtimeMBeanServer = null;
   private static Object domainRuntimeMBeanServer = null;
   private static Object editMBeanServer = null;
   private static ThreadLocalStack threadContext = new ThreadLocalStack(true);

   private static Context doCreateDefaultContext() {
      Context ctx = new SimpleContext();

      try {
         Context c = ctx.createSubcontext("comp");
         c.bind("UserTransaction", new SimpleContext.SimpleReference() {
            public Object get() throws NamingException {
               return TransactionHelper.getTransactionHelper().getUserTransaction();
            }
         });
         c.bind("TransactionSynchronizationRegistry", new SimpleContext.SimpleReference() {
            public Object get() throws NamingException {
               return TransactionHelper.getTransactionHelper().getTransactionManager();
            }
         });
         if (KernelStatus.isServer()) {
            Context jmx = c.createSubcontext("jmx");
            jmx.bind("runtime", new SimpleContext.SimpleReference() {
               public Object get() throws NamingException {
                  return javaURLContextFactory.runtimeMBeanServer;
               }
            });
            jmx.bind("domainRuntime", new SimpleContext.SimpleReference() {
               public Object get() throws NamingException {
                  return javaURLContextFactory.domainRuntimeMBeanServer;
               }
            });
            jmx.bind("edit", new SimpleContext.SimpleReference() {
               public Object get() throws NamingException {
                  return javaURLContextFactory.editMBeanServer;
               }
            });
         }

         WorkContextHelper.bind(c);
         DisconnectMonitorListImpl.bindToJNDI(c);
         c.bind("HandleDelegate", IIOP_OBJECT_REFERENCE);
         c.bind("ORB", IIOP_OBJECT_REFERENCE);
         return ctx;
      } catch (NamingException var3) {
         throw new AssertionError(var3);
      }
   }

   public static void setRuntimeMBeanServer(Object mbs) {
      if (runtimeMBeanServer != null) {
         throw new AssertionError("The RuntimeMBeanServer can only be established once.");
      } else {
         runtimeMBeanServer = mbs;
      }
   }

   public static void setDomainRuntimeMBeanServer(Object mbs) {
      if (domainRuntimeMBeanServer != null) {
         throw new AssertionError("The DomainRuntimeMBeanServer can only be establised once.");
      } else {
         domainRuntimeMBeanServer = mbs;
      }
   }

   public static void setEditMBeanServer(Object mbs) {
      if (editMBeanServer != null) {
         throw new AssertionError("The Edit MBeanServer can only be established once.");
      } else {
         editMBeanServer = mbs;
      }
   }

   public static void pushContext(Context ctx) {
      threadContext.push(ctx);
   }

   public static void popContext() {
      threadContext.pop();
   }

   public static Context peekContext() {
      return (Context)threadContext.peek();
   }

   public static Context getDefaultContext(AuthenticatedSubject subj) {
      SecurityHelper.assertIfNotKernel(subj);
      return getDefaultContext();
   }

   private static Context getDefaultContext() {
      if (defaultContext == null) {
         defaultContext = createDefaultContext();
      }

      return defaultContext;
   }

   private static synchronized Context createDefaultContext() {
      if (defaultContext == null) {
         defaultContext = doCreateDefaultContext();
      }

      return defaultContext;
   }

   public Object getObjectInstance(Object original, Name name, Context ctx, Hashtable env) throws NamingException {
      Context c = (Context)threadContext.peek();
      if (c == null) {
         c = getDefaultContext();
      }

      if (c instanceof JavaURLContext) {
         ((JavaURLContext)c).setEnvironment(env);
         return c;
      } else {
         if (!(c instanceof ReadOnlyContextWrapper)) {
            c = new ReadOnlyContextWrapper((Context)c);
         }

         return new JavaURLContext((Context)c, env);
      }
   }

   public static void main(String[] argv) throws Exception {
      (new javaURLContextFactory()).getObjectInstance((Object)null, (Name)null, (Context)null, (Hashtable)null);
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }

   private static class JavaURLContext extends AbstractURLContext {
      private Context actualCtx;
      private Hashtable env;
      private Context rootCtx;
      private static final String JAVA_GLOBAL = "java:global";

      public JavaURLContext(Context actualCtx, Hashtable env) {
         this.actualCtx = actualCtx;
         this.env = env;
      }

      public void bind(String s, Object obj) throws NamingException {
         if (s.startsWith("java:global")) {
            Context ctx = null;

            try {
               ctx = this.getContext(s);
               ctx.bind(this.removeURL(s), obj);
            } finally {
               if (ctx != null) {
                  try {
                     ctx.close();
                  } catch (NamingException var10) {
                  }
               }

            }
         } else {
            super.bind(s, obj);
         }

      }

      public Context createSubcontext(String s) throws NamingException {
         if (s.startsWith("java:global")) {
            Context ctx = null;

            Context var3;
            try {
               ctx = this.getContext(s);
               var3 = ctx.createSubcontext(this.removeURL(s));
            } finally {
               if (ctx != null) {
                  try {
                     ctx.close();
                  } catch (NamingException var10) {
                  }
               }

            }

            return var3;
         } else {
            return super.createSubcontext(s);
         }
      }

      public void destroySubcontext(String s) throws NamingException {
         if (s.startsWith("java:global")) {
            Context ctx = null;

            try {
               ctx = this.getContext(s);
               ctx.destroySubcontext(this.removeURL(s));
            } finally {
               if (ctx != null) {
                  try {
                     ctx.close();
                  } catch (NamingException var9) {
                  }
               }

            }
         } else {
            super.destroySubcontext(s);
         }

      }

      public NamingEnumeration list(String s) throws NamingException {
         if (s.startsWith("java:global")) {
            Context ctx = null;

            NamingEnumeration var3;
            try {
               ctx = this.getContext(s);
               var3 = ctx.list(this.removeURL(s));
            } finally {
               if (ctx != null) {
                  try {
                     ctx.close();
                  } catch (NamingException var10) {
                  }
               }

            }

            return var3;
         } else {
            return super.list(s);
         }
      }

      public NamingEnumeration listBindings(String s) throws NamingException {
         if (s.startsWith("java:global")) {
            Context ctx = null;

            NamingEnumeration var3;
            try {
               ctx = this.getContext(s);
               var3 = ctx.listBindings(this.removeURL(s));
            } finally {
               if (ctx != null) {
                  try {
                     ctx.close();
                  } catch (NamingException var10) {
                  }
               }

            }

            return var3;
         } else {
            return super.listBindings(s);
         }
      }

      public Object lookup(String s) throws NamingException {
         if (s.startsWith("java:global")) {
            Context ctx = null;

            Object var3;
            try {
               ctx = this.getContext(s);
               var3 = ctx.lookup(this.removeURL(s));
            } finally {
               if (ctx != null) {
                  try {
                     ctx.close();
                  } catch (NamingException var10) {
                  }
               }

            }

            return var3;
         } else {
            return super.lookup(s);
         }
      }

      public Object lookupLink(String s) throws NamingException {
         if (s.startsWith("java:global")) {
            Context ctx = null;

            Object var3;
            try {
               ctx = this.getContext(s);
               var3 = ctx.lookupLink(this.removeURL(s));
            } finally {
               if (ctx != null) {
                  try {
                     ctx.close();
                  } catch (NamingException var10) {
                  }
               }

            }

            return var3;
         } else {
            return super.lookupLink(s);
         }
      }

      public void rebind(String s, Object obj) throws NamingException {
         if (s.startsWith("java:global")) {
            Context ctx = null;

            try {
               ctx = this.getContext(s);
               ctx.rebind(this.removeURL(s), obj);
            } finally {
               if (ctx != null) {
                  try {
                     ctx.close();
                  } catch (NamingException var10) {
                  }
               }

            }
         } else {
            super.rebind(s, obj);
         }

      }

      public void rename(String s, String s1) throws NamingException {
         if (s.startsWith("java:global")) {
            Context ctx = null;

            try {
               ctx = this.getContext(s);
               ctx.rename(this.removeURL(s), s1);
            } finally {
               if (ctx != null) {
                  try {
                     ctx.close();
                  } catch (NamingException var10) {
                  }
               }

            }
         } else {
            super.rename(s, s1);
         }

      }

      public void unbind(String s) throws NamingException {
         if (s.startsWith("java:global")) {
            Context ctx = null;

            try {
               ctx = this.getContext(s);
               ctx.unbind(this.removeURL(s));
            } finally {
               if (ctx != null) {
                  try {
                     ctx.close();
                  } catch (NamingException var9) {
                  }
               }

            }
         } else {
            super.unbind(s);
         }

      }

      protected String removeURL(String name) throws InvalidNameException {
         if (name.startsWith("java:comp/")) {
            return name.substring(5);
         } else {
            return name.startsWith("java:global") ? name : super.removeURL(name);
         }
      }

      protected Context getContext(String s) throws NamingException {
         return s.startsWith("java:global") ? this.getRootCtx() : this.actualCtx;
      }

      private Context getRootCtx() throws NamingException {
         return this.rootCtx = (new Environment(this.env)).getInitialContext();
      }

      private void setEnvironment(Hashtable env) {
         this.env = env;
      }
   }
}
