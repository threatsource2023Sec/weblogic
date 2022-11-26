package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jndi.JndiTemplate;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Properties;
import javax.naming.NamingException;

public class JndiRmiServiceExporter extends RmiBasedExporter implements InitializingBean, DisposableBean {
   @Nullable
   private static Method exportObject;
   @Nullable
   private static Method unexportObject;
   private JndiTemplate jndiTemplate = new JndiTemplate();
   private String jndiName;
   private Remote exportedObject;

   public void setJndiTemplate(JndiTemplate jndiTemplate) {
      this.jndiTemplate = jndiTemplate != null ? jndiTemplate : new JndiTemplate();
   }

   public void setJndiEnvironment(Properties jndiEnvironment) {
      this.jndiTemplate = new JndiTemplate(jndiEnvironment);
   }

   public void setJndiName(String jndiName) {
      this.jndiName = jndiName;
   }

   public void afterPropertiesSet() throws NamingException, RemoteException {
      this.prepare();
   }

   public void prepare() throws NamingException, RemoteException {
      if (this.jndiName == null) {
         throw new IllegalArgumentException("Property 'jndiName' is required");
      } else {
         this.exportedObject = this.getObjectToExport();
         this.invokePortableRemoteObject(exportObject);
         this.rebind();
      }
   }

   public void rebind() throws NamingException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Binding RMI service to JNDI location [" + this.jndiName + "]");
      }

      this.jndiTemplate.rebind(this.jndiName, this.exportedObject);
   }

   public void destroy() throws NamingException, RemoteException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Unbinding RMI service from JNDI location [" + this.jndiName + "]");
      }

      this.jndiTemplate.unbind(this.jndiName);
      this.invokePortableRemoteObject(unexportObject);
   }

   private void invokePortableRemoteObject(@Nullable Method method) throws RemoteException {
      if (method != null) {
         try {
            method.invoke((Object)null, this.exportedObject);
         } catch (InvocationTargetException var4) {
            Throwable targetEx = var4.getTargetException();
            if (targetEx instanceof RemoteException) {
               throw (RemoteException)targetEx;
            }

            ReflectionUtils.rethrowRuntimeException(targetEx);
         } catch (Throwable var5) {
            throw new IllegalStateException("PortableRemoteObject invocation failed", var5);
         }
      }

   }

   static {
      try {
         Class portableRemoteObject = JndiRmiServiceExporter.class.getClassLoader().loadClass("javax.rmi.PortableRemoteObject");
         exportObject = portableRemoteObject.getMethod("exportObject", Remote.class);
         unexportObject = portableRemoteObject.getMethod("unexportObject", Remote.class);
      } catch (Throwable var1) {
         exportObject = null;
         unexportObject = null;
      }

   }
}
