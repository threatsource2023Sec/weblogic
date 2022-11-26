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
import weblogic.deployment.jms.ForeignJMSServerAware;
import weblogic.jms.client.JMSConnectionFactory;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSOBSHelper;
import weblogic.jms.common.WrappedDestinationImpl;
import weblogic.kernel.KernelStatus;
import weblogic.security.Security;

public class WLInitialContext implements Context {
   private Context ctx;
   private Subject subject;
   private Hashtable env;
   private static boolean isInstallClient;

   WLInitialContext(Context ctx, Subject subject, Hashtable env) {
      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("WLInitialContext:constructor ctx=" + ctx + ", subject=" + subject + ", env=" + JMSOBSHelper.filterProperties(env));
      }

      this.ctx = ctx;
      this.subject = subject;
      this.env = env;
   }

   public Object lookup(final String name) throws NamingException {
      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("WLInitialContext:lookup name=" + name + ", ctx=" + this.ctx + ", subject=" + this.subject);
      }

      try {
         return Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               return WLInitialContext.this.checkResult(WLInitialContext.this.ctx.lookup(name));
            }
         });
      } catch (PrivilegedActionException var3) {
         throw WLInitialContextFactory.convertException(var3);
      }
   }

   private Object checkResult(Object result) throws OperationNotSupportedException {
      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("WLInitialContext:checkResult result=" + result + ", result.getClass()=" + result.getClass().getName());
      }

      if (result instanceof ForeignJMSServerAware && ((ForeignJMSServerAware)result).isReferencedByFS()) {
         throw new OperationNotSupportedException(JMSClientExceptionLogger.logInvalidLookupForForeignServer());
      } else if (result instanceof Context) {
         return new WLInitialContext((Context)result, this.subject, this.env);
      } else if (result instanceof JMSConnectionFactory) {
         JMSConnectionFactory cf = (JMSConnectionFactory)result;
         cf.setOBSIC(true);
         cf.setSubject(this.subject);
         cf.setJNDIEnv(this.env);
         return cf;
      } else if (!(result instanceof WrappedDestinationImpl) && !(result instanceof DestinationImpl)) {
         throw new OperationNotSupportedException(JMSClientExceptionLogger.logInvalidLookupTypeLoggable(result.getClass().getName()).getMessage());
      } else {
         return result;
      }
   }

   public void close() throws NamingException {
      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("WLInitialContext:close ctx=" + this.ctx + ", subject=" + this.subject);
      }

      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               WLInitialContext.this.ctx.close();
               return null;
            }
         });
      } catch (PrivilegedActionException var2) {
         throw WLInitialContextFactory.convertException(var2);
      }
   }

   public Object lookup(Name name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("lookup(Name name)").getMessage());
   }

   public void bind(Name name, Object obj) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("bind(Name name, Object obj)").getMessage());
   }

   public void bind(String name, Object obj) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("bind(String name, Object obj)").getMessage());
   }

   public void rebind(Name name, Object obj) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("rebind(Name name, Object obj)").getMessage());
   }

   public void rebind(String name, Object obj) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("rebind(String name, Object obj)").getMessage());
   }

   public void unbind(Name name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("unbind(Name name)").getMessage());
   }

   public void unbind(String name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("unbind(String name)").getMessage());
   }

   public void rename(Name oldName, Name newName) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("rename(Name oldName, Name newName)").getMessage());
   }

   public void rename(String oldName, String newName) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("rename(String oldName, String newName)").getMessage());
   }

   public NamingEnumeration list(Name name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("list(Name name)").getMessage());
   }

   public NamingEnumeration list(final String name) throws NamingException {
      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("WLInitialContext:list name=" + name + ", ctx=" + this.ctx + ", subject=" + this.subject + ", KernelStatus.isServer()=" + KernelStatus.isServer() + ", isInstallClient=" + isInstallClient);
      }

      if (!KernelStatus.isServer() && !isInstallClient) {
         throw new OperationNotSupportedException(JMSClientExceptionLogger.logRestrictedAPILoggable("list(String name)").getMessage());
      } else {
         try {
            return (NamingEnumeration)Security.runAs(this.subject, new PrivilegedExceptionAction() {
               public NamingEnumeration run() throws NamingException {
                  return WLInitialContext.this.ctx.list(name);
               }
            });
         } catch (PrivilegedActionException var3) {
            throw WLInitialContextFactory.convertException(var3);
         }
      }
   }

   public NamingEnumeration listBindings(Name name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("listBindings(Name name)").getMessage());
   }

   public NamingEnumeration listBindings(String name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("listBindings(String name)").getMessage());
   }

   public void destroySubcontext(Name name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("destroySubcontext(Name name)").getMessage());
   }

   public void destroySubcontext(String name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("destroySubcontext(String name)").getMessage());
   }

   public Context createSubcontext(Name name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("createSubcontext(Name name)").getMessage());
   }

   public Context createSubcontext(String name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("createSubcontext(String name)").getMessage());
   }

   public Object lookupLink(Name name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("lookupLink(Name name)").getMessage());
   }

   public Object lookupLink(String name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("lookupLink(String name)").getMessage());
   }

   public NameParser getNameParser(Name name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("getNameParser(Name name)").getMessage());
   }

   public NameParser getNameParser(String name) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("getNameParser(String name)").getMessage());
   }

   public Name composeName(Name name, Name prefix) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("composeName(Name name, Name prefix)").getMessage());
   }

   public String composeName(String name, String prefix) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("composeName(String name, Name prefix)").getMessage());
   }

   public Object addToEnvironment(String propName, Object propVal) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("addToEnvironment(String propName, Object propVal)").getMessage());
   }

   public Object removeFromEnvironment(String propName) throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("removeFromEnvironment(String propName)").getMessage());
   }

   public Hashtable getEnvironment() throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("getEnvironment()").getMessage());
   }

   public String getNameInNamespace() throws NamingException {
      throw new OperationNotSupportedException(JMSClientExceptionLogger.logUnsupportedAPILoggable("getNameInNamespace()").getMessage());
   }

   static {
      if (KernelStatus.isServer()) {
         isInstallClient = false;
      } else {
         try {
            Class.forName("weblogic.rmi.cluster.ClusterableRemoteObject");
            isInstallClient = true;
         } catch (ClassNotFoundException var1) {
            isInstallClient = false;
         }
      }

   }
}
