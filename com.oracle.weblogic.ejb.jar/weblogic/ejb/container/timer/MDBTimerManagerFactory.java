package weblogic.ejb.container.timer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.management.runtime.EJBTimerRuntimeMBean;

public class MDBTimerManagerFactory {
   private final Set bms = new HashSet();
   private boolean tmsetupCalled = false;

   public synchronized TimerManager createEJBTimerManager(BeanManager bm) {
      if (!this.bms.isEmpty()) {
         return ((BeanManager)this.bms.iterator().next()).getTimerManager();
      } else {
         BeanManager bmproxy = (BeanManager)Proxy.newProxyInstance(bm.getClass().getClassLoader(), bm.getClass().getInterfaces(), new InterceptingInvocationHandlerImpl());
         this.bms.add(bm);
         return EJBTimerManagerFactory.createEJBTimerManager(bmproxy, bm.getBeanInfo().isClusteredTimers());
      }
   }

   public synchronized BeanManager getActiveBeanManager() {
      return !this.bms.isEmpty() ? (BeanManager)this.bms.iterator().next() : null;
   }

   public synchronized void setup(BeanManager bm, EJBTimerRuntimeMBean timerRT) throws WLDeploymentException {
      if (!this.tmsetupCalled && this.bms.size() == 1 && bm.getTimerManager() != null) {
         bm.getTimerManager().setup(timerRT);
         this.tmsetupCalled = true;
      }

      this.bms.add(bm);
   }

   public void undeploy(BeanManager bm) {
      boolean undeployTimerManager = false;
      synchronized(this) {
         if (this.bms.size() == 1 && bm.getTimerManager() != null) {
            undeployTimerManager = true;
            this.tmsetupCalled = false;
         }

         this.bms.remove(bm);
      }

      if (undeployTimerManager) {
         bm.getTimerManager().undeploy();
      }

   }

   class InterceptingInvocationHandlerImpl implements InvocationHandler {
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         Object result = null;

         try {
            result = method.invoke(MDBTimerManagerFactory.this.getActiveBeanManager(), args);
            return result;
         } catch (InvocationTargetException var6) {
            throw var6.getCause();
         }
      }
   }
}
