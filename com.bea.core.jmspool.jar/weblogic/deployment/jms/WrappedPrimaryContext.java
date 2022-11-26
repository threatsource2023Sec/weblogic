package weblogic.deployment.jms;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import weblogic.utils.wrapper.Wrapper;

public class WrappedPrimaryContext implements Wrapper {
   protected PrimaryContextHelperService primaryContextHelper;
   protected JMSContext vendorPrimaryContext;
   protected Object vendorObj;
   protected int wrapStyle;
   protected WrappedClassManager wrapperManager;
   protected boolean closed;

   protected void init(int wrapStyle, WrappedClassManager wrapperManager, PrimaryContextHelperService helper) {
      JMSPoolDebug.logger.debug("Created a new non-pooled WrappedPrimaryContext");
      this.primaryContextHelper = helper;
      this.vendorPrimaryContext = helper.getPrimaryContext();
      this.vendorObj = this.vendorPrimaryContext;
      this.wrapStyle = wrapStyle;
      this.wrapperManager = wrapperManager;
   }

   protected void init(int wrapStyle, WrappedClassManager wrapperManager, JMSContext vendorPrimaryContext) {
      this.vendorPrimaryContext = vendorPrimaryContext;
      this.vendorObj = vendorPrimaryContext;
      this.wrapStyle = wrapStyle;
      this.wrapperManager = wrapperManager;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws Exception {
      return null;
   }

   public void preInvocationHandler(String methodName, Object[] params) {
      if (!methodName.equals("close")) {
         this.checkClosed();
      }

      if ((this.wrapStyle == 1 || this.wrapStyle == 2) && (methodName.equals("setClientID") || methodName.equals("setExceptionListener") || methodName.equals("stop"))) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logInvalidJ2EEMethodLoggable(methodName));
      }
   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) {
      return ret;
   }

   public JMSContext createContext(int sessionMode) {
      this.checkClosed();
      return (JMSContext)this.doCreateContext(sessionMode);
   }

   public synchronized void close() {
      if (!this.closed) {
         this.vendorPrimaryContext.close();
         this.closed = true;
      }

   }

   protected WrappedSecondaryContext doCreateContext(int requestedSessionMode) {
      int actualSessionMode;
      if (requestedSessionMode != 2 && requestedSessionMode != 0) {
         actualSessionMode = requestedSessionMode;
      } else {
         actualSessionMode = 1;
      }

      try {
         boolean ignoreXA = false;
         SecondaryContextHolder holder = this.primaryContextHelper.getNewSecondaryContext(actualSessionMode, ignoreXA);
         int type = 27;
         WrappedTransactionalSecondaryContext ret = (WrappedTransactionalSecondaryContext)this.wrapperManager.getWrappedInstance(type, holder.getSecondaryContext());
         ret.init(this.primaryContextHelper.getXAResourceName(), holder, this.primaryContextHelper.hasNativeTransactions(), this.wrapperManager);
         ret.setWrapStyle(this.getWrapStyle());
         ret.setRequestedSessionMode(requestedSessionMode);
         ret.setSecondaryContextStarted(true);
         return ret;
      } catch (JMSException var7) {
         throw new JMSRuntimeException(var7.getMessage(), var7.getErrorCode(), var7);
      }
   }

   protected int getWrapStyle() {
      return this.wrapStyle;
   }

   protected synchronized void checkClosed() {
      if (this.closed) {
         throw JMSExceptions.getIllegalStateRuntimeException(JMSPoolLogger.logJMSObjectClosedLoggable());
      }
   }

   public Object getVendorObj() {
      return this.vendorObj;
   }

   public void setVendorObj(Object o) {
      this.vendorPrimaryContext = (JMSContext)o;
      this.vendorObj = o;
   }

   public WrappedClassManager getWrapperManager() {
      return this.wrapperManager;
   }
}
