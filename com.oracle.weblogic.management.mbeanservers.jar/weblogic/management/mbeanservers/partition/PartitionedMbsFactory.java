package weblogic.management.mbeanservers.partition;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.util.concurrent.Callable;
import javax.management.MBeanServer;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class PartitionedMbsFactory {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public MBeanServer create(String pName, MBeanServer mbs) {
      InvocationHandler ih = new MbsInvocationHandler(pName, mbs);
      MBeanServer proxyMbs = (MBeanServer)Proxy.newProxyInstance(MBeanServer.class.getClassLoader(), new Class[]{MBeanServer.class, Serializable.class}, ih);
      return proxyMbs;
   }

   private class MbsInvocationHandler implements InvocationHandler {
      private final String pName;
      private transient WeakReference mbs;

      public MbsInvocationHandler(String pName, MBeanServer mbs) {
         this.pName = pName;
         this.mbs = new WeakReference(mbs);
      }

      public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
         switch (method.getName()) {
            case "hashCode":
               return this.pName.hashCode();
            case "equals":
               return proxy == args[0];
            case "toString":
               return this.getMbs() + " for partition " + this.pName;
            default:
               ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().createComponentInvocationContext(this.pName);
               Callable c = new Callable() {
                  public Object call() throws Exception {
                     return method.invoke(MbsInvocationHandler.this.getMbs(), args);
                  }
               };
               return ComponentInvocationContextManager.runAs(PartitionedMbsFactory.kernelId, cic, c);
         }
      }

      private MBeanServer getMbs() {
         return (MBeanServer)this.mbs.get();
      }
   }
}
