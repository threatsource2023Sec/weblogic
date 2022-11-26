package weblogic.ejb.container.internal;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.ejb.container.interfaces.BaseEJBLocalObjectIntf;
import weblogic.ejb.container.interfaces.Ejb3LocalHome;
import weblogic.ejb.container.interfaces.Ejb3SessionHome;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.spi.SessionBeanReference;
import weblogic.ejb20.interfaces.LocalHomeHandle;

public class SingletonEJBLocalHomeImpl extends BaseEJBLocalHome implements Ejb3SessionHome, Ejb3LocalHome {
   private final Map viewToImpl = new HashMap();

   public SingletonEJBLocalHomeImpl() {
      super((Class)null);
   }

   public void prepare() {
      SessionBeanInfo sbi = this.getBeanInfo();
      Iterator var2 = sbi.getBusinessLocals().iterator();

      while(var2.hasNext()) {
         Class iface = (Class)var2.next();
         this.viewToImpl.put(iface.getName(), this.allocateBI(sbi.getGeneratedLocalBusinessImplClass(iface)));
      }

      if (sbi.hasNoIntfView()) {
         this.viewToImpl.put(sbi.getBeanClassName(), this.allocateBI(sbi.getGeneratedNoIntfViewImplClass()));
      }

   }

   private Object allocateBI(Class implClass) {
      try {
         SingletonLocalObject slo = new SingletonLocalObject();
         slo.setBeanManager(this.getBeanManager());
         slo.setBeanInfo(this.getBeanInfo());
         Constructor ctr;
         if (this.getBeanInfo().hasAsyncMethods()) {
            ctr = implClass.getConstructor(SingletonLocalObject.class, AsyncInvocationManager.class);
            return ctr.newInstance(slo, this.getBeanInfo().getAsyncInvocationManager());
         } else {
            ctr = implClass.getConstructor(SingletonLocalObject.class);
            return ctr.newInstance(slo);
         }
      } catch (Exception var4) {
         throw new AssertionError(var4);
      }
   }

   public SessionBeanInfo getBeanInfo() {
      return (SessionBeanInfo)super.getBeanInfo();
   }

   public Object getBindableImpl(String ifaceName) {
      return this.viewToImpl.get(ifaceName);
   }

   public Object getBusinessImpl(Object pk, Class iface) {
      return this.viewToImpl.get(iface.getName());
   }

   public SessionBeanReference getSessionBeanReference() {
      return new SessionBeanReferenceImpl(this.getBeanInfo(), this);
   }

   public LocalHomeHandle getLocalHomeHandle() {
      throw new AssertionError("Should not be invoked on Singleton");
   }

   public void remove(Object pk) {
      throw new AssertionError("Should not be invoked on Singleton");
   }

   public void remove(MethodDescriptor md, Object pk) {
      throw new AssertionError("Should not be invoked on Singleton");
   }

   public BaseEJBLocalObjectIntf allocateELO(Object pk) {
      throw new AssertionError("Should not be invoked on Singleton");
   }

   public BaseEJBLocalObjectIntf allocateELO() {
      throw new AssertionError("Should not be invoked on Singleton");
   }
}
