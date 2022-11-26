package weblogic.ejb.container.internal;

import javax.ejb.EJBException;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.interfaces.WLSessionEJBContext;
import weblogic.ejb.spi.BaseWSObjectIntf;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.utils.Debug;

public abstract class BaseWSLocalObject extends BaseLocalObject implements BaseWSObjectIntf {
   private int state = 1;
   private boolean sessionCtxHasMessageCtx = false;
   private ContextHandler __wsContext = null;
   private Throwable theException = null;
   private InvocationWrapper __wrap_;
   private MethodDescriptor __md_;

   protected final InvocationWrapper __WL_preInvoke(MethodDescriptor md, ContextHandler ejbContext, ContextHandler wsContext) throws EJBException {
      return this.__WL_preInvoke(md, ejbContext, wsContext, (AuthenticatedSubject)null);
   }

   protected final InvocationWrapper __WL_preInvoke(MethodDescriptor md, ContextHandler ejbContext, ContextHandler wsContext, AuthenticatedSubject altRunAs) throws EJBException {
      this.__md_ = md;
      this.__wsContext = wsContext;
      if (debugLogger.isDebugEnabled()) {
         debug("preInvoke with md: " + this.__md_ + " on: " + this);
      }

      this.setNextState(2);
      EJBContextHandler ejbch = (EJBContextHandler)ejbContext;
      if (this.__wsContext != null) {
         Object mch = this.__wsContext.getValue("com.bea.contextelement.wsee.SOAPMessage");
         if (mch instanceof SOAPMessageContext) {
            SOAPMessage soap = ((SOAPMessageContext)mch).getMessage();
            ejbch.setSOAPMessage(soap);
         } else if (debugLogger.isDebugEnabled()) {
            debug("we expected MessageContext.getValue(ContextElementDictionary.WSEE_SOAPMESSAGE) to return a javax.xml.rpc.handler.soap.SOAPMessageContext, but instead we got: '" + mch.getClass().getName() + "'");
         }
      }

      try {
         this.__wrap_ = InvocationWrapper.newInstance(this.__md_);
         this.__wrap_.setAlternativeRunAsSubject(altRunAs);
         super.__WL_preInvoke(this.__wrap_, ejbContext);
      } catch (Throwable var8) {
         this.setNextStateError(8, var8);
         throw EJBRuntimeUtils.asEJBException("service object exception on preInvoke", var8);
      }

      this.setNextState(4);
      return this.__wrap_;
   }

   protected final void __WL_business(MethodDescriptor md) {
      if (debugLogger.isDebugEnabled()) {
         debug("Business method " + md + " on: " + this);
      }

      assert md.getMethod().equals(this.__md_.getMethod()) : " business method called for different method than was called for preInvoke ! preInvoke method: '" + this.__md_.toString() + "', business method: '" + md.toString() + "'";

      this.setNextState(16);
      if (this.__wsContext != null) {
         this.setMessageContext();
      }

   }

   private void setMessageContext() {
      WLEnterpriseBean bean = (WLEnterpriseBean)this.__wrap_.getBean();
      WLSessionEJBContext sctx = (WLSessionEJBContext)bean.__WL_getEJBContext();
      Object ctx = this.__wsContext.getValue("com.bea.contextelement.wsee.SOAPMessage");
      if (ctx == null) {
         ctx = this.__wsContext.getValue("com.bea.contextelement.wsee.SOAPMessage.jaxws");
      }

      if (ctx instanceof MessageContext) {
         sctx.setMessageContext((MessageContext)ctx);
         this.sessionCtxHasMessageCtx = true;
      }

   }

   protected final void __WL_business_success() {
      this.setNextState(32);
   }

   protected final void __WL_business_fail(Throwable th) {
      this.setNextStateError(64, th);
   }

   protected final boolean __WL_postInvokeTxRetry() throws Exception {
      this.setNextState(256);
      if (this.sessionCtxHasMessageCtx) {
         WLEnterpriseBean bean = (WLEnterpriseBean)this.__wrap_.getBean();
         WLSessionEJBContext sctx = (WLSessionEJBContext)bean.__WL_getEJBContext();
         sctx.setMessageContext((MessageContext)null);
         this.sessionCtxHasMessageCtx = false;
      }

      boolean ret = super.__WL_postInvokeTxRetry(this.__wrap_, this.theException);
      if (ret && this.__wsContext != null) {
         this.setMessageContext();
      } else {
         this.__wsContext = null;
      }

      return ret;
   }

   protected final void __WL_wsPostInvoke() throws Exception {
      switch (this.getState()) {
         case 4:
            this.__WL_postInvokeWithoutBusiness();
            return;
         case 256:
            this.__WL_postInvokeCleanup();
            return;
         default:
            Debug.assertion(false, "wsPostInvoke encountered unhandleable state " + this.getState());
      }
   }

   public final void __WL_postInvokeWithoutBusiness() throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debug("postInvokeWithoutBusiness  on: " + this);
      }

      this.setNextState(128);

      try {
         int txRetryCount = 0;
         super.postInvoke1(txRetryCount, this.__wrap_, this.theException);
      } catch (Exception var5) {
         this.theException = var5;
      } finally {
         this.__WL_postInvokeCleanup();
      }

   }

   public final void __WL_postInvokeCleanup() {
      if (debugLogger.isDebugEnabled()) {
         debug("__WL_postInvokeCleanup  on: " + this);
      }

      this.setNextState(512);

      try {
         super.__WL_postInvokeCleanup(this.__wrap_, this.theException);
      } catch (Throwable var2) {
         this.setNextStateError(1024, var2);
         throw EJBRuntimeUtils.asEJBException("service object exception on postInvoke", var2);
      }

      this.setNextState(1024);
   }

   public final void __WL_methodComplete() {
      this.setNextState(1);
      this.sessionCtxHasMessageCtx = false;
      this.theException = null;
      this.__wrap_ = null;
      this.__md_ = null;
      this.__wsContext = null;
   }

   public final boolean __WL_encounteredException() {
      return this.theException != null;
   }

   public final boolean __WL_isApplicationException() {
      Debug.assertion(this.__md_ != null, "isAppException invoked outside of service invoke cycle !");
      Debug.assertion(this.theException != null, "isAppException invoked when there was no exception encountered !");
      return this.deploymentInfo.getExceptionInfo(this.__md_.getMethod(), this.theException).isAppException();
   }

   public final Throwable __WL_getException() {
      return this.theException;
   }

   protected final void __WL_setException(Throwable th) {
      this.theException = th;
   }

   private void setNextState(int newState) {
      if (this.allowedState(newState)) {
         this.setState(newState);
      } else {
         Debug.assertion(false, "attempt at illegal state transaction from " + this.getState() + " to " + newState);
      }

   }

   private void setNextStateError(int newState, Throwable th) {
      this.setNextState(newState);
      this.theException = th;
   }

   private boolean allowedState(int newState) {
      if ((newState & this.getState()) != 0) {
         return true;
      } else {
         int test = false;
         short test;
         switch (newState) {
            case 1:
               test = 1033;
               break;
            case 2:
               test = 9;
               break;
            case 4:
               test = 2;
               break;
            case 8:
               test = 2;
               break;
            case 16:
               test = 260;
               break;
            case 32:
               test = 16;
               break;
            case 64:
               test = 16;
               break;
            case 128:
               test = 4;
               break;
            case 256:
               test = 96;
               break;
            case 512:
               test = 384;
               break;
            case 1024:
               test = 512;
               break;
            default:
               Debug.assertion(false, "unknown state argument to allowedMethod '" + newState + "'");
               return false;
         }

         return (test & this.getState()) != 0;
      }
   }

   private void setState(int i) {
      this.state = i;
   }

   private int getState() {
      return this.state;
   }

   protected final InvocationWrapper __WL_getWrap() {
      return this.__wrap_;
   }

   public String toString() {
      return "[BaseWSLocalObject] ";
   }

   private static void debug(String s) {
      debugLogger.debug("[BaseWSLocalObject] " + s);
   }
}
