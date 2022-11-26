package weblogic.ejb.container.internal;

import java.rmi.RemoteException;
import weblogic.ejb.container.interfaces.Ejb3RemoteHome;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.spi.BusinessHandle;
import weblogic.ejb.spi.BusinessObject;

public class StatelessRemoteObject extends BaseRemoteObject {
   public boolean postInvoke1(int txRetryCount, InvocationWrapper wrap, Throwable ee) throws Exception {
      boolean retryTx = super.postInvoke1(txRetryCount, wrap, ee);
      if (retryTx) {
         this.getBeanManager().releaseBean(wrap);
      }

      return retryTx;
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
            currentInvocationWrapper.push(wrap);
            wrap.setIsRemoteInvocation();
         } else {
            currentInvocationWrapper.push(new BaseRemoteObject.ThreadLocalObject(false, this));
         }
      } else {
         currentInvocationWrapper.push(new BaseRemoteObject.ThreadLocalObject(false, this));
      }

   }

   protected void popInvocationWrapperInThreadLocalOnError(InvocationWrapper wrap) {
      Object o = currentInvocationWrapper.get();
      if (o instanceof BaseRemoteObject.ThreadLocalObject && !((BaseRemoteObject.ThreadLocalObject)o).isRemote()) {
         currentInvocationWrapper.pop();
      }

   }

   public void notifyRemoteCallEnd() {
      Object o = currentInvocationWrapper.get();
      if (o != null && o instanceof InvocationWrapper) {
         InvocationWrapper invocationWrapper = (InvocationWrapper)currentInvocationWrapper.pop();
         Object eBean = invocationWrapper.getBean();
         Class beanClass = ((SessionBeanInfo)this.ejbHome.getBeanInfo()).getGeneratedBeanClass();
         boolean isBeanEq = this.beanInfo.isEJB30() || eBean != null && eBean.getClass() == beanClass;
         if (!invocationWrapper.hasSystemExceptionOccured() && eBean != null && isBeanEq) {
            this.getBeanManager().releaseBean(invocationWrapper);
         }
      } else if (o instanceof BaseRemoteObject.ThreadLocalObject && ((BaseRemoteObject.ThreadLocalObject)o).isRemote()) {
         currentInvocationWrapper.pop();
      }

   }

   public void __WL_postInvokeCleanup(InvocationWrapper wrap, Throwable ee) throws Exception {
      boolean var9 = false;

      try {
         var9 = true;
         super.__WL_postInvokeCleanup(wrap, ee);
         var9 = false;
      } finally {
         if (var9) {
            Object var6 = currentInvocationWrapper.get();
            if (var6 instanceof BaseRemoteObject.ThreadLocalObject) {
               BaseRemoteObject.ThreadLocalObject tlo = (BaseRemoteObject.ThreadLocalObject)currentInvocationWrapper.pop();
               if (!tlo.isRemote() && !wrap.hasSystemExceptionOccured()) {
                  this.getBeanManager().releaseBean(wrap);
               }
            }

         }
      }

      Object o = currentInvocationWrapper.get();
      if (o instanceof BaseRemoteObject.ThreadLocalObject) {
         BaseRemoteObject.ThreadLocalObject tlo = (BaseRemoteObject.ThreadLocalObject)currentInvocationWrapper.pop();
         if (!tlo.isRemote() && !wrap.hasSystemExceptionOccured()) {
            this.getBeanManager().releaseBean(wrap);
         }
      }

   }

   public BusinessHandle __WL_getBusinessObjectHandle(MethodDescriptor md, Class iface) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      Object o = ((Ejb3RemoteHome)this.ejbHome).getBusinessImpl((Object)null, (Class)iface);
      return new BusinessHandleImpl(this.ejbHome, (BusinessObject)o, iface.getName());
   }
}
