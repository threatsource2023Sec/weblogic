package weblogic.persistence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.persistence.EntityManager;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextChangeListener;
import weblogic.kernel.ThreadLocalStack;

public final class CICScopedEMProvider implements ComponentInvocationContextChangeListener {
   private static final CICScopedEMProvider INSTANCE = new CICScopedEMProvider();
   private static final ThreadLocalStack THREAD_LOCAL_STACK = new ThreadLocalStack();

   private CICScopedEMProvider() {
   }

   public static CICScopedEMProvider getInstance() {
      return INSTANCE;
   }

   private Map peekEMs() {
      return (Map)THREAD_LOCAL_STACK.peek();
   }

   public EntityManager getEMForCurrentCIC(String puName) {
      return (EntityManager)this.peekEMs().get(puName);
   }

   public void setEMForCurrentCIC(String puName, EntityManager em) {
      this.peekEMs().put(puName, em);
   }

   public void invalidateEMForCurrentCIC(String puName) {
      EntityManager em = (EntityManager)this.peekEMs().remove(puName);
      if (em != null && em.isOpen()) {
         em.close();
      }

   }

   public void componentInvocationContextChanged(ComponentInvocationContext oldContext, ComponentInvocationContext newContext, boolean isPush) {
      if (isPush) {
         THREAD_LOCAL_STACK.push(new HashMap());
      } else {
         Map ems = (Map)THREAD_LOCAL_STACK.pop();
         Iterator var5 = ems.values().iterator();

         while(var5.hasNext()) {
            EntityManager em = (EntityManager)var5.next();
            if (em != null && em.isOpen()) {
               em.close();
            }
         }
      }

   }
}
