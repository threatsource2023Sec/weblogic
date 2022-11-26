package weblogic.ejb.container.internal;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import weblogic.ejb.container.interfaces.Ejb3LocalHome;
import weblogic.ejb.container.interfaces.Ejb3SessionHome;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.spi.SessionBeanReference;
import weblogic.ejb20.interfaces.LocalHomeHandle;

public class StatelessEJBLocalHomeImpl extends StatelessEJBLocalHome implements Ejb3SessionHome, Ejb3LocalHome {
   private final Map viewToImpl = new HashMap();

   public StatelessEJBLocalHomeImpl() {
      super((Class)null);
   }

   public StatelessEJBLocalHomeImpl(Class eloClass) {
      super(eloClass);
   }

   public LocalHomeHandle getLocalHomeHandle() {
      throw new IllegalStateException();
   }

   public void remove(Object pk) throws EJBException, RemoveException {
      throw new IllegalStateException();
   }

   public void prepare() {
      SessionBeanInfo sbi = this.getBeanInfo();
      Iterator var2 = sbi.getBusinessLocals().iterator();

      while(var2.hasNext()) {
         Class iface = (Class)var2.next();
         Class busImpl = sbi.getGeneratedLocalBusinessImplClass(iface);
         this.viewToImpl.put(iface.getName(), this.allocateBI(busImpl));
      }

      if (sbi.hasNoIntfView()) {
         Class busImpl = sbi.getGeneratedNoIntfViewImplClass();
         this.viewToImpl.put(sbi.getBeanClassName(), this.allocateBI(busImpl));
      }

   }

   public Object getBindableImpl(String ifaceName) {
      return this.viewToImpl.get(ifaceName);
   }

   public Object getBusinessImpl(Object pk, Class iface) {
      return this.viewToImpl.get(iface.getName());
   }

   private Object allocateBI(Class implClass) {
      try {
         StatelessLocalObject slo = new StatelessLocalObject();
         slo.setBeanManager(this.getBeanManager());
         slo.setBeanInfo(this.getBeanInfo());
         Constructor ctr;
         if (this.getBeanInfo().hasAsyncMethods()) {
            ctr = implClass.getConstructor(StatelessLocalObject.class, AsyncInvocationManager.class);
            return ctr.newInstance(slo, this.getBeanInfo().getAsyncInvocationManager());
         } else {
            ctr = implClass.getConstructor(StatelessLocalObject.class);
            return ctr.newInstance(slo);
         }
      } catch (Exception var4) {
         throw new AssertionError(var4);
      }
   }

   public SessionBeanInfo getBeanInfo() {
      return (SessionBeanInfo)super.getBeanInfo();
   }

   public SessionBeanReference getSessionBeanReference() {
      return new SessionBeanReferenceImpl(this.getBeanInfo(), this);
   }
}
