package weblogic.jms;

import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.security.auth.Subject;
import weblogic.jms.client.JMSConnectionFactory;
import weblogic.security.Security;

public class WrappedInitialContext implements Context {
   private Context ctx;
   private Subject subject;
   private Hashtable env;

   WrappedInitialContext(Context ctx, Subject subject, Hashtable env) {
      try {
         ctx.removeFromEnvironment("osgi.service.jndi.bundleContext");
      } catch (Exception var5) {
      }

      this.ctx = ctx;
      this.subject = subject;
      this.env = env;
   }

   public Object addToEnvironment(final String arg0, final Object arg1) throws NamingException {
      try {
         return Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.addToEnvironment(arg0, arg1);
               return WrappedInitialContext.this.checkResult(result);
            }
         });
      } catch (PrivilegedActionException var5) {
         throw WrappedInitialContextFactory.convertException(var5);
      }
   }

   public void bind(final Name arg0, final Object arg1) throws NamingException {
      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WrappedInitialContext.this.ctx.bind(arg0, arg1);
               return null;
            }
         });
      } catch (PrivilegedActionException var5) {
         throw WrappedInitialContextFactory.convertException(var5);
      }
   }

   public void bind(final String arg0, final Object arg1) throws NamingException {
      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WrappedInitialContext.this.ctx.bind(arg0, arg1);
               return null;
            }
         });
      } catch (PrivilegedActionException var5) {
         throw WrappedInitialContextFactory.convertException(var5);
      }
   }

   public void close() throws NamingException {
      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WrappedInitialContext.this.ctx.close();
               return null;
            }
         });
      } catch (PrivilegedActionException var2) {
         throw WrappedInitialContextFactory.convertException(var2);
      }
   }

   public Name composeName(final Name arg0, final Name arg1) throws NamingException {
      try {
         return (Name)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.composeName(arg0, arg1);
               return result;
            }
         });
      } catch (PrivilegedActionException var5) {
         throw WrappedInitialContextFactory.convertException(var5);
      }
   }

   public String composeName(final String arg0, final String arg1) throws NamingException {
      try {
         return (String)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.composeName(arg0, arg1);
               return result;
            }
         });
      } catch (PrivilegedActionException var5) {
         throw WrappedInitialContextFactory.convertException(var5);
      }
   }

   public Context createSubcontext(final Name arg0) throws NamingException {
      Context rtn = null;

      try {
         return (Context)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               return WrappedInitialContext.this.checkResult(WrappedInitialContext.this.ctx.createSubcontext(arg0));
            }
         });
      } catch (PrivilegedActionException var4) {
         throw WrappedInitialContextFactory.convertException(var4);
      }
   }

   public Context createSubcontext(final String arg0) throws NamingException {
      Context rtn = null;

      try {
         return (Context)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               return WrappedInitialContext.this.checkResult(WrappedInitialContext.this.ctx.createSubcontext(arg0));
            }
         });
      } catch (PrivilegedActionException var4) {
         throw WrappedInitialContextFactory.convertException(var4);
      }
   }

   public void destroySubcontext(final Name arg0) throws NamingException {
      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WrappedInitialContext.this.ctx.destroySubcontext(arg0);
               return null;
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public void destroySubcontext(final String arg0) throws NamingException {
      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WrappedInitialContext.this.ctx.destroySubcontext(arg0);
               return null;
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public Hashtable getEnvironment() throws NamingException {
      try {
         return (Hashtable)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.getEnvironment();
               return result;
            }
         });
      } catch (PrivilegedActionException var2) {
         throw WrappedInitialContextFactory.convertException(var2);
      }
   }

   public String getNameInNamespace() throws NamingException {
      try {
         return (String)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.getNameInNamespace();
               return result;
            }
         });
      } catch (PrivilegedActionException var2) {
         throw WrappedInitialContextFactory.convertException(var2);
      }
   }

   public NameParser getNameParser(final Name arg0) throws NamingException {
      try {
         return (NameParser)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.getNameParser(arg0);
               return result;
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public NameParser getNameParser(final String arg0) throws NamingException {
      try {
         return (NameParser)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.getNameParser(arg0);
               return result;
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public NamingEnumeration list(final Name arg0) throws NamingException {
      try {
         return (NamingEnumeration)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.list(arg0);
               return result;
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public NamingEnumeration list(final String arg0) throws NamingException {
      try {
         return (NamingEnumeration)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.list(arg0);
               return result;
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public NamingEnumeration listBindings(Name arg0) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public NamingEnumeration listBindings(String arg0) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public Object lookup(final Name arg0) throws NamingException {
      try {
         return Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               return WrappedInitialContext.this.checkResult(WrappedInitialContext.this.ctx.lookup(arg0));
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public Object lookup(final String arg0) throws NamingException {
      try {
         return Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               return WrappedInitialContext.this.checkResult(WrappedInitialContext.this.ctx.lookup(arg0));
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public Object lookupLink(final Name arg0) throws NamingException {
      try {
         return Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.lookupLink(arg0);
               return WrappedInitialContext.this.checkResult(result);
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public Object lookupLink(final String arg0) throws NamingException {
      try {
         return Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.lookupLink(arg0);
               return WrappedInitialContext.this.checkResult(result);
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public void rebind(final Name arg0, final Object arg1) throws NamingException {
      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WrappedInitialContext.this.ctx.rebind(arg0, arg1);
               return null;
            }
         });
      } catch (PrivilegedActionException var5) {
         throw WrappedInitialContextFactory.convertException(var5);
      }
   }

   public void rebind(final String arg0, final Object arg1) throws NamingException {
      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WrappedInitialContext.this.ctx.rebind(arg0, arg1);
               return null;
            }
         });
      } catch (PrivilegedActionException var5) {
         throw WrappedInitialContextFactory.convertException(var5);
      }
   }

   public Object removeFromEnvironment(final String arg0) throws NamingException {
      try {
         return Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               Object result = WrappedInitialContext.this.ctx.removeFromEnvironment(arg0);
               return WrappedInitialContext.this.checkResult(result);
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public void rename(final Name arg0, final Name arg1) throws NamingException {
      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WrappedInitialContext.this.ctx.rename(arg0, arg1);
               return null;
            }
         });
      } catch (PrivilegedActionException var5) {
         throw WrappedInitialContextFactory.convertException(var5);
      }
   }

   public void rename(final String arg0, final String arg1) throws NamingException {
      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WrappedInitialContext.this.ctx.rename(arg0, arg1);
               return null;
            }
         });
      } catch (PrivilegedActionException var5) {
         throw WrappedInitialContextFactory.convertException(var5);
      }
   }

   public void unbind(final Name arg0) throws NamingException {
      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WrappedInitialContext.this.ctx.unbind(arg0);
               return null;
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   public void unbind(final String arg0) throws NamingException {
      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WrappedInitialContext.this.ctx.unbind(arg0);
               return null;
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WrappedInitialContextFactory.convertException(var3);
      }
   }

   private Object checkResult(Object result) throws OperationNotSupportedException {
      if (result != null && result instanceof Context) {
         result = new WrappedInitialContext((Context)result, this.subject, this.env);
      } else if (result != null && result instanceof JMSConnectionFactory) {
         JMSConnectionFactory cf = (JMSConnectionFactory)result;
         cf.setRA(true);
         cf.setSubject(this.subject);
         cf.setJNDIEnv(this.env);
      }

      return result;
   }
}
