package weblogic.ejb20.internal;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb20.interfaces.LocalHandle;

public final class LocalHandleImpl implements LocalHandle, Serializable {
   private static final long serialVersionUID = 3817127104258844908L;
   private transient EJBLocalObject ejbObject = null;
   private EJBLocalHome home = null;
   private Object primaryKey = null;

   public LocalHandleImpl() {
   }

   public LocalHandleImpl(EJBLocalObject eo) {
      this.ejbObject = eo;
      this.home = eo.getEJBLocalHome();
   }

   public LocalHandleImpl(EJBLocalObject eo, Object pk) {
      this.ejbObject = eo;
      this.home = eo.getEJBLocalHome();
      this.primaryKey = pk;
   }

   private EJBLocalObject allocateELO(Class[] param, Object[] arg) {
      Class homeClass = this.home.getClass();

      try {
         Method allocMethod = homeClass.getMethod("allocateELO", param);
         return (EJBLocalObject)allocMethod.invoke(this.home, arg);
      } catch (NoSuchMethodException var6) {
         throw EJBRuntimeUtils.asEJBException("Exception re-establishing handle: " + homeClass + " doesn't define allocatELO() method", var6);
      } catch (InvocationTargetException var7) {
         Throwable t = var7.getTargetException();
         throw EJBRuntimeUtils.asEJBException("Exception re-establishing handle", t);
      } catch (IllegalAccessException var8) {
         throw EJBRuntimeUtils.asEJBException("Exception re-establishing handle", var8);
      }
   }

   public EJBLocalObject getEJBLocalObject() {
      if (this.ejbObject != null) {
         return this.ejbObject;
      } else {
         return this.primaryKey == null ? this.allocateELO((Class[])null, (Object[])null) : this.allocateELO(new Class[]{Object.class}, new Object[]{this.primaryKey});
      }
   }
}
