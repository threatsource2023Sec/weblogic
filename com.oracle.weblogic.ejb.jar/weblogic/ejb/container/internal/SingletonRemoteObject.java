package weblogic.ejb.container.internal;

import java.rmi.RemoteException;
import javax.ejb.ConcurrentAccessException;
import javax.ejb.EJBTransactionRolledbackException;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.ejb.container.interfaces.Ejb3RemoteHome;
import weblogic.ejb.spi.BusinessHandle;
import weblogic.ejb.spi.BusinessObject;
import weblogic.security.service.ContextHandler;

@Service
public class SingletonRemoteObject extends BaseRemoteObject implements FastThreadLocalMarker {
   public boolean postInvoke1(int txRetryCount, InvocationWrapper wrap, Throwable ee) throws Exception {
      boolean retryTX = super.postInvoke1(txRetryCount, wrap, ee);
      if (retryTX) {
         this.getBeanManager().releaseBean(wrap);
      }

      return retryTX;
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
         InvocationWrapper iw = (InvocationWrapper)currentInvocationWrapper.pop();
         if (!iw.hasSystemExceptionOccured() && iw.getBean() != null) {
            this.getBeanManager().releaseBean(iw);
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

   protected void __WL_preInvoke(InvocationWrapper wrap, ContextHandler ch) throws RemoteException {
      try {
         super.__WL_preInvoke(wrap, ch);
      } catch (EJBTransactionRolledbackException var5) {
         Throwable eCause = var5.getCause();
         if (eCause != null && eCause instanceof ConcurrentAccessException) {
            throw EJBRuntimeUtils.asRemoteException("EJB Exception: ", eCause);
         } else {
            throw var5;
         }
      }
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }
}
