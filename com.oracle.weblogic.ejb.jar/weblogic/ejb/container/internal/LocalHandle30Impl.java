package weblogic.ejb.container.internal;

import java.io.Serializable;
import java.util.Iterator;
import javax.ejb.EJBException;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.Ejb3LocalHome;
import weblogic.ejb.container.interfaces.LocalHandle30;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.utils.LocatorUtilities;

public final class LocalHandle30Impl implements LocalHandle30, Serializable {
   private static final long serialVersionUID = 3384945695775082609L;
   private String moduleId;
   private String ejbName;
   private Class localIntf;
   private Object primaryKey;
   private transient Object businessLocalObject;
   private String bloClassName;

   public LocalHandle30Impl(BaseLocalObject baseLocalObject, Object businessLocalObject) {
      this(baseLocalObject, businessLocalObject, (Object)null);
   }

   public LocalHandle30Impl(BaseLocalObject baseLocalObject, Object businessLocalObject, Object pk) {
      this.primaryKey = null;
      this.businessLocalObject = null;
      this.bloClassName = null;
      this.primaryKey = pk;
      Class bloClass = businessLocalObject.getClass();
      this.bloClassName = bloClass.getName();
      BeanInfo bi = baseLocalObject.getBeanManager().getBeanInfo();
      this.moduleId = bi.getDeploymentInfo().getModuleId();
      this.ejbName = bi.getEJBName();
      if (bi instanceof SessionBeanInfo) {
         SessionBeanInfo sbi = (SessionBeanInfo)bi;
         Iterator var7 = sbi.getBusinessLocals().iterator();

         while(var7.hasNext()) {
            Class busLocalIntf = (Class)var7.next();
            Class busLocalImpl = sbi.getGeneratedLocalBusinessImplClass(busLocalIntf);
            if (busLocalImpl == bloClass) {
               this.localIntf = busLocalIntf;
            }
         }

         if (this.localIntf == null && sbi.hasNoIntfView()) {
            this.localIntf = sbi.getBeanClass();
         }
      }

   }

   private Object getBLO() {
      ApplicationContextInternal appCtx = ((ApplicationAccess)LocatorUtilities.getService(ApplicationAccess.class)).getCurrentApplicationContext();
      DeploymentInfo di = getDeploymentInfo(appCtx, this.moduleId);
      if (di != null) {
         BeanInfo bi = di.getBeanInfo(this.ejbName);
         if (bi != null && bi instanceof ClientDrivenBeanInfo) {
            BaseEJBLocalHomeIntf localHome = ((ClientDrivenBeanInfo)bi).getLocalHome();
            if (localHome instanceof Ejb3LocalHome) {
               return ((Ejb3LocalHome)localHome).getBusinessImpl(this.primaryKey, this.localIntf);
            }
         }
      }

      throw new EJBException("Failed to get local object for " + this.bloClassName + " with primaryKey=" + this.primaryKey);
   }

   private static DeploymentInfo getDeploymentInfo(ApplicationContextInternal appCtx, String moduleId) {
      ModuleContext moduleCtx = appCtx.getModuleContext(moduleId);
      return moduleCtx != null && moduleCtx.getRegistry() != null ? (DeploymentInfo)moduleCtx.getRegistry().get(weblogic.ejb.spi.DeploymentInfo.class.getName()) : null;
   }

   public synchronized Object getEJB30LocalObject() {
      if (this.businessLocalObject == null) {
         this.businessLocalObject = this.getBLO();
         if (this.businessLocalObject == null) {
            throw new EJBException("Failed to get local object for " + this.bloClassName + " with primaryKey=" + this.primaryKey);
         }
      }

      return this.businessLocalObject;
   }
}
