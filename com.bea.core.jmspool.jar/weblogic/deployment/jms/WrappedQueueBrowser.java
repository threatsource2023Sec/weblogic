package weblogic.deployment.jms;

import javax.jms.JMSException;
import javax.jms.QueueBrowser;
import weblogic.utils.wrapper.Wrapper;

public class WrappedQueueBrowser implements Wrapper {
   protected QueueBrowser vendorBrowser;
   protected Object vendorObj;
   protected WrappedSession parentSession = null;
   protected WrappedSecondaryContext parentContext = null;

   protected void init(WrappedSession parent, QueueBrowser browser) throws JMSException {
      this.parentSession = parent;
      this.vendorObj = browser;
      this.vendorBrowser = browser;
   }

   public void init(WrappedSecondaryContext parent, QueueBrowser browser) {
      this.parentContext = parent;
      this.vendorObj = browser;
      this.vendorBrowser = browser;
   }

   public void close() throws JMSException {
      if (this.parentSession != null) {
         this.parentSession.browserClosed(this);
      } else {
         this.parentContext.browserClosed(this);
      }

   }

   protected void closeProviderBrowser() throws JMSException {
      this.vendorBrowser.close();
   }

   public void setVendorObj(Object o) {
      this.vendorBrowser = (QueueBrowser)o;
      this.vendorObj = o;
   }

   public Object getVendorObj() {
      return this.vendorObj;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws Exception {
      return null;
   }

   public void preInvocationHandler(String methodName, Object[] params) {
   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) {
      return ret;
   }
}
