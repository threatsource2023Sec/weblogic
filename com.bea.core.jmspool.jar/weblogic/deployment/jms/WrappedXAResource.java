package weblogic.deployment.jms;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import weblogic.utils.wrapper.Wrapper;

public class WrappedXAResource implements Wrapper {
   private String poolName;
   private XAResource vendorRes;
   protected Object vendorObj;

   protected void init(String poolName, XAResource res) {
      this.poolName = poolName;
      this.vendorRes = res;
      this.vendorObj = res;
   }

   public void setVendorObj(Object o) {
      this.vendorRes = (XAResource)o;
      this.vendorObj = o;
   }

   public Object getVendorObj() {
      return this.vendorObj;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws Exception {
      return null;
   }

   public void preInvocationHandler(String methodName, Object[] params) {
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("PooledXAResource." + methodName + " called");
      }

   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) {
      return ret;
   }

   public boolean isSameRM(XAResource res) throws XAException {
      if (res instanceof WrappedXAResource) {
         WrappedXAResource pooledRes = (WrappedXAResource)res;
         return this.poolName != null && pooledRes.poolName != null ? pooledRes.poolName.equals(this.poolName) : this.vendorRes.isSameRM(pooledRes.vendorRes);
      } else {
         return false;
      }
   }
}
