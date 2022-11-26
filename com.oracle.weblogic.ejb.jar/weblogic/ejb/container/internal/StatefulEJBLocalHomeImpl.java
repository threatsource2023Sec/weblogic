package weblogic.ejb.container.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.Ejb3LocalHome;
import weblogic.ejb.container.interfaces.Ejb3SessionHome;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.manager.StatefulSessionManager;
import weblogic.ejb.spi.StatefulSessionBeanReference;
import weblogic.ejb20.interfaces.LocalHomeHandle;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jndi.ClassTypeOpaqueReference;

public class StatefulEJBLocalHomeImpl extends StatefulEJBLocalHome implements Ejb3LocalHome, Ejb3SessionHome {
   private final Map opaqueRefs = new HashMap();

   public StatefulEJBLocalHomeImpl() {
      super((Class)null);
   }

   public StatefulEJBLocalHomeImpl(Class eloClass) {
      super(eloClass);
   }

   public LocalHomeHandle getLocalHomeHandle() {
      throw new IllegalStateException();
   }

   public void remove(Object pk) throws RemoveException, EJBException {
      throw new IllegalStateException();
   }

   public void prepare() {
      SessionBeanInfo sbi = this.getBeanInfo();
      Iterator var2 = sbi.getBusinessLocals().iterator();

      while(var2.hasNext()) {
         Class c = (Class)var2.next();
         this.opaqueRefs.put(c.getName(), new ORImpl(sbi.getGeneratedLocalBusinessImplClass(c), c));
      }

      if (sbi.hasNoIntfView()) {
         this.opaqueRefs.put(sbi.getBeanClassName(), new ORImpl(sbi.getGeneratedNoIntfViewImplClass(), sbi.getBeanClass()));
      }

   }

   public Object getBindableImpl(String ifaceName) {
      return this.opaqueRefs.get(ifaceName);
   }

   public Object getBusinessImpl(Object pk, Class iface) {
      SessionBeanInfo sbi = this.getBeanInfo();
      Class clz = sbi.getGeneratedLocalBusinessImplClass(iface);
      if (clz == null) {
         if (iface != sbi.getBeanClass() || !sbi.hasNoIntfView()) {
            return null;
         }

         clz = sbi.getGeneratedNoIntfViewImplClass();
      }

      return this.newBusinessImpl(pk, clz);
   }

   protected final Object newBusinessImpl(Object pk, Class implClass) {
      try {
         StatefulLocalObject slo = new StatefulLocalObject();
         slo.setBeanManager(this.getBeanManager());
         slo.setBeanInfo(this.getBeanInfo());
         Constructor ctr;
         if (this.getBeanInfo().hasAsyncMethods()) {
            ctr = implClass.getConstructor(StatefulLocalObject.class, AsyncInvocationManager.class, Object.class);
            return ctr.newInstance(slo, this.getBeanInfo().getAsyncInvocationManager(), pk);
         } else {
            ctr = implClass.getConstructor(StatefulLocalObject.class, Object.class);
            return ctr.newInstance(slo, pk);
         }
      } catch (IllegalAccessException var5) {
         throw new AssertionError(var5);
      } catch (InstantiationException | SecurityException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException var6) {
         throw new AssertionError(var6);
      }
   }

   public SessionBeanInfo getBeanInfo() {
      return (SessionBeanInfo)super.getBeanInfo();
   }

   public StatefulSessionBeanReference getSessionBeanReference() {
      return new StatefulSessionBeanReferenceImpl(this.getBeanInfo(), this);
   }

   private final class ORImpl implements ClassTypeOpaqueReference {
      private final Class type;
      private final Class viewImplClass;
      private final SessionBeanInfo sbi = StatefulEJBLocalHomeImpl.this.getBeanInfo();

      ORImpl(Class implClass, Class type) {
         this.viewImplClass = implClass;
         this.type = type;
      }

      public Object getReferent(Name name, Context ctx) throws NamingException {
         try {
            ManagedInvocationContext mic = this.sbi.setCIC();
            Throwable var19 = null;

            Object var6;
            try {
               Object pk = ((StatefulSessionManager)StatefulEJBLocalHomeImpl.this.beanManager).createBean();
               var6 = StatefulEJBLocalHomeImpl.this.newBusinessImpl(pk, this.viewImplClass);
            } catch (Throwable var16) {
               var19 = var16;
               throw var16;
            } finally {
               if (mic != null) {
                  if (var19 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var15) {
                        var19.addSuppressed(var15);
                     }
                  } else {
                     mic.close();
                  }
               }

            }

            return var6;
         } catch (InternalException var18) {
            EJBException e = new EJBException();
            e.initCause(var18.detail);
            throw e;
         }
      }

      public Class getObjectClass() {
         return this.type;
      }
   }
}
