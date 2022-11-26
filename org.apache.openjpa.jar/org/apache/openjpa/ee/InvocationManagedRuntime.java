package org.apache.openjpa.ee;

import java.lang.reflect.Method;
import javax.transaction.TransactionManager;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;

public class InvocationManagedRuntime extends AbstractManagedRuntime implements ManagedRuntime, Configurable {
   private String _methodName = null;
   private String _clazz = null;
   private transient Method _method = null;
   private OpenJPAConfiguration _conf = null;

   public String getTransactionManagerMethod() {
      return this._methodName;
   }

   public void setTransactionManagerMethod(String methodName) {
      this._clazz = methodName.substring(0, methodName.lastIndexOf(46));
      this._methodName = methodName.substring(methodName.lastIndexOf(46) + 1);
      this._method = null;
   }

   public TransactionManager getTransactionManager() throws Exception {
      if (this._method == null) {
         ClassLoader loader = this._conf.getClassResolverInstance().getClassLoader(this.getClass(), (ClassLoader)null);
         this._method = Class.forName(this._clazz, true, loader).getMethod(this._methodName, (Class[])null);
      }

      return (TransactionManager)this._method.invoke((Object)null, (Object[])null);
   }

   public void setConfiguration(Configuration conf) {
      this._conf = (OpenJPAConfiguration)conf;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public void setRollbackOnly(Throwable cause) throws Exception {
      this.getTransactionManager().getTransaction().setRollbackOnly();
   }

   public Throwable getRollbackCause() throws Exception {
      return null;
   }
}
