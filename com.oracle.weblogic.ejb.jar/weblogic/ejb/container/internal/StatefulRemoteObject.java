package weblogic.ejb.container.internal;

import java.rmi.RemoteException;
import weblogic.ejb.container.interfaces.Ejb3RemoteHome;
import weblogic.ejb.container.manager.StatefulSessionManager;
import weblogic.ejb.spi.BusinessHandle;
import weblogic.ejb.spi.BusinessObject;
import weblogic.rmi.extensions.NotificationListener;

public class StatefulRemoteObject extends BaseRemoteObject implements NotificationListener {
   public BusinessHandle __WL_getBusinessObjectHandle(MethodDescriptor md, Class iface, Object pk) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      Object o = ((Ejb3RemoteHome)this.ejbHome).getBusinessImpl(pk, iface);
      return new BusinessHandleImpl(this.ejbHome, (BusinessObject)o, pk);
   }

   protected boolean __WL_postInvokeTxRetry(InvocationWrapper wrap, Throwable ee) throws Exception {
      boolean retry = super.__WL_postInvokeTxRetry(wrap, ee);
      if (!retry && wrap.getMethodDescriptor().isRemoveMethod()) {
         MethodDescriptor md = wrap.getMethodDescriptor();
         if (ee == null || !md.isRetainIfException() && this.deploymentInfo.getExceptionInfo(md.getMethod(), ee).isAppException()) {
            ((StatefulSessionManager)this.getBeanManager()).removeForRemoveAnnotation(wrap);
         }
      }

      return retry;
   }

   public void notifyRemoteCallBegin() {
      currentInvocationWrapper.push(new BaseRemoteObject.ThreadLocalObject(true, this));
   }

   protected void pushInvocationWrapperInThreadLocal(InvocationWrapper wrap) {
      Object o = currentInvocationWrapper.get();
      if (o instanceof BaseRemoteObject.ThreadLocalObject) {
         BaseRemoteObject.ThreadLocalObject tlo = (BaseRemoteObject.ThreadLocalObject)o;
         if (tlo.isRemote() && tlo.getBaseRemoteObject() == this) {
            currentInvocationWrapper.pop();
            wrap.setIsRemoteInvocation();
         }
      }

   }

   protected void popInvocationWrapperInThreadLocalOnError(InvocationWrapper wrap) {
   }

   public void notifyRemoteCallEnd() {
      Object o = currentInvocationWrapper.get();
      if (o != null) {
         if (o instanceof BaseRemoteObject.ThreadLocalObject) {
            BaseRemoteObject.ThreadLocalObject tlo = (BaseRemoteObject.ThreadLocalObject)o;
            if (tlo.isRemote()) {
               currentInvocationWrapper.pop();
            }
         }

      }
   }
}
