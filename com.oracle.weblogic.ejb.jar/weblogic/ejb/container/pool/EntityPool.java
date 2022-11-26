package weblogic.ejb.container.pool;

import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.EntityBean;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.internal.EntityEJBHome;
import weblogic.ejb.container.internal.EntityEJBLocalHome;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.management.runtime.EJBPoolRuntimeMBean;
import weblogic.utils.StackTraceUtils;

public final class EntityPool extends Pool {
   private static final Object DUMMY_PK = new Object();
   private final BaseEntityManager beanManager;
   private final EJBObject eo;
   private final EJBLocalObject elo;

   public EntityPool(EntityEJBHome remoteHome, EntityEJBLocalHome localHome, BaseEntityManager beanManager, BeanInfo info, EJBPoolRuntimeMBean mbean) {
      super(beanManager, info, mbean);
      this.beanClass = ((EntityBeanInfo)this.beanInfo).getGeneratedBeanClass();
      this.beanManager = beanManager;
      this.eo = remoteHome != null ? remoteHome.allocateEO(DUMMY_PK) : null;
      this.elo = localHome != null ? localHome.allocateELO(DUMMY_PK) : null;
      if (debugLogger.isDebugEnabled()) {
         debug("created: '" + this + "'");
      }

   }

   public EntityBean getBean() throws InternalException {
      EntityBean result = (EntityBean)super.getBean();
      if (null == result) {
         result = this.createBean();
         if (debugLogger.isDebugEnabled()) {
            debug("Allocate new: '" + result + "'");
         }
      }

      this.getPoolRuntime().incrementBeansInUseCount();
      return result;
   }

   public Object getBean(long timeout) {
      throw new AssertionError("getBean() with timeout not supported for Entity beans");
   }

   protected void removeBean(Object eb) {
      try {
         ((EntityBean)eb).unsetEntityContext();
      } catch (Throwable var3) {
         EJBLogger.logExceptionDuringEJBUnsetEntityContext(this.beanInfo.getEJBName(), StackTraceUtils.throwable2StackTrace(var3));
      }

   }

   public EntityBean createBean() throws InternalException {
      return this.beanManager.createBean(this.eo, this.elo);
   }

   public void reset() {
      this.beanClass = ((EntityBeanInfo)this.beanInfo).getGeneratedBeanClass();
      this.cleanup();
   }

   private static void debug(String s) {
      debugLogger.debug("[EntityPool] " + s);
   }
}
