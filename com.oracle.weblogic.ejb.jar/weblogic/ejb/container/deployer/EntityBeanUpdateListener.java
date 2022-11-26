package weblogic.ejb.container.deployer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.application.ApplicationContextInternal;
import weblogic.descriptor.DescriptorBean;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.CachingManager;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.PoolIntf;
import weblogic.ejb.container.interfaces.ReadOnlyManager;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.wl.ApplicationEntityCacheBean;
import weblogic.j2ee.descriptor.wl.AutomaticKeyGenerationBean;
import weblogic.j2ee.descriptor.wl.EjbBean;
import weblogic.j2ee.descriptor.wl.EntityCacheBean;
import weblogic.j2ee.descriptor.wl.EntityCacheRefBean;
import weblogic.j2ee.descriptor.wl.EntityDescriptorBean;
import weblogic.j2ee.descriptor.wl.PoolBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;

public class EntityBeanUpdateListener extends BaseBeanUpdateListener {
   private final EntityBeanInfo ebi;

   protected EntityBeanUpdateListener(EntityBeanInfo ebi) {
      super(ebi);
      this.ebi = ebi;
   }

   protected void addBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDesc, ApplicationContextInternal appCtx) {
      super.addBeanUpdateListener(wlBean, ejbDesc);
      EntityDescriptorBean ed = wlBean.getEntityDescriptor();
      Iterator var5;
      DescriptorBean db;
      if (ed.isEntityCacheRefSet()) {
         var5 = this.getCacheRefs(ed, appCtx.getWLApplicationDD()).iterator();

         while(var5.hasNext()) {
            db = (DescriptorBean)var5.next();
            db.addBeanUpdateListener(this);
         }
      } else {
         ((DescriptorBean)ed.getEntityCache()).addBeanUpdateListener(this);
      }

      ((DescriptorBean)ed.getPool()).addBeanUpdateListener(this);
      var5 = this.getAutoKeyGenerators(ejbDesc).iterator();

      while(var5.hasNext()) {
         db = (DescriptorBean)var5.next();
         db.addBeanUpdateListener(this);
      }

   }

   protected void removeBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDesc, ApplicationContextInternal appCtx) {
      super.removeBeanUpdateListener(wlBean, ejbDesc);
      EntityDescriptorBean ed = wlBean.getEntityDescriptor();
      Iterator var5;
      DescriptorBean db;
      if (ed.isEntityCacheRefSet()) {
         var5 = this.getCacheRefs(ed, appCtx.getWLApplicationDD()).iterator();

         while(var5.hasNext()) {
            db = (DescriptorBean)var5.next();
            db.removeBeanUpdateListener(this);
         }
      } else {
         ((DescriptorBean)ed.getEntityCache()).removeBeanUpdateListener(this);
      }

      ((DescriptorBean)ed.getPool()).removeBeanUpdateListener(this);
      var5 = this.getAutoKeyGenerators(ejbDesc).iterator();

      while(var5.hasNext()) {
         db = (DescriptorBean)var5.next();
         db.removeBeanUpdateListener(this);
      }

   }

   protected void handleProperyChange(String propertyName, DescriptorBean newBean) {
      PoolBean pool;
      if (propertyName.equals("MaxBeansInFreePool")) {
         pool = (PoolBean)newBean;
         this.updateMaxBeansInFreePool(pool.getMaxBeansInFreePool());
      } else {
         EntityCacheBean cache;
         if (propertyName.equals("MaxBeansInCache")) {
            if (newBean instanceof EntityCacheBean) {
               cache = (EntityCacheBean)newBean;
               this.updateMaxBeansInCache(cache.getMaxBeansInCache());
            } else {
               ApplicationEntityCacheBean cache = (ApplicationEntityCacheBean)newBean;
               this.updateMaxBeansInCache(cache.getMaxBeansInCache());
            }
         } else {
            EntityCacheRefBean cacheRef;
            if (propertyName.equals("IdleTimeoutSeconds")) {
               if (newBean instanceof EntityCacheBean) {
                  cache = (EntityCacheBean)newBean;
                  this.updateCacheIdleTimeoutSeconds(cache.getIdleTimeoutSeconds());
               } else if (newBean instanceof EntityCacheRefBean) {
                  cacheRef = (EntityCacheRefBean)newBean;
                  this.updateCacheIdleTimeoutSeconds(cacheRef.getIdleTimeoutSeconds());
               } else {
                  pool = (PoolBean)newBean;
                  this.updatePoolIdleTimeoutSeconds(pool.getIdleTimeoutSeconds());
               }
            } else if (propertyName.equals("ReadTimeoutSeconds")) {
               if (newBean instanceof EntityCacheBean) {
                  cache = (EntityCacheBean)newBean;
                  this.updateReadTimeoutSeconds(cache.getReadTimeoutSeconds());
               } else {
                  cacheRef = (EntityCacheRefBean)newBean;
                  this.updateReadTimeoutSeconds(cacheRef.getReadTimeoutSeconds());
               }
            } else {
               if (!propertyName.equals("KeyCacheSize")) {
                  throw new AssertionError("Unexpected propertyName: " + propertyName);
               }

               AutomaticKeyGenerationBean akg = (AutomaticKeyGenerationBean)newBean;
               this.updateKeyCacheSize(akg.getKeyCacheSize());
            }
         }
      }

   }

   private Set getAutoKeyGenerators(EjbDescriptorBean ejbDesc) {
      Set dbs = new HashSet();
      if (!this.ebi.getIsBeanManagedPersistence() && this.ebi.getCMPInfo().uses20CMP()) {
         WeblogicRdbmsJarBean[] rdbmsJars = ejbDesc.getWeblogicRdbmsJarBeans();
         WeblogicRdbmsJarBean[] var4 = rdbmsJars;
         int var5 = rdbmsJars.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            WeblogicRdbmsJarBean rdbmsJar = var4[var6];
            WeblogicRdbmsBeanBean[] rdbmsBeans = rdbmsJar.getWeblogicRdbmsBeans();
            WeblogicRdbmsBeanBean[] var9 = rdbmsBeans;
            int var10 = rdbmsBeans.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               WeblogicRdbmsBeanBean rdbmsBean = var9[var11];
               if (this.ebi.getEJBName().equals(rdbmsBean.getEjbName())) {
                  AutomaticKeyGenerationBean ak = rdbmsBean.getAutomaticKeyGeneration();
                  if (ak != null) {
                     dbs.add((DescriptorBean)ak);
                  }
               }
            }
         }
      }

      return dbs;
   }

   private Set getCacheRefs(EntityDescriptorBean ed, WeblogicApplicationBean wlAppDD) {
      Set dbs = new HashSet();
      EntityCacheRefBean eCacheRef = ed.getEntityCacheRef();
      dbs.add((DescriptorBean)eCacheRef);
      EjbBean ejb = wlAppDD.getEjb();
      ApplicationEntityCacheBean[] appCaches = ejb.getEntityCaches();
      ApplicationEntityCacheBean[] var7 = appCaches;
      int var8 = appCaches.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         ApplicationEntityCacheBean appCache = var7[var9];
         if (eCacheRef.getEntityCacheName().equals(appCache.getEntityCacheName())) {
            dbs.add((DescriptorBean)appCache);
         }
      }

      return dbs;
   }

   private void updateCacheIdleTimeoutSeconds(int idleTimeoutSeconds) {
      this.ebi.getCachingDescriptor().setIdleTimeoutSecondsCache(idleTimeoutSeconds);
      BeanManager bm = this.ebi.getBeanManager();
      if (bm instanceof CachingManager) {
         CachingManager cm = (CachingManager)bm;
         cm.updateIdleTimeoutSecondsCache(idleTimeoutSeconds);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("updated Cache IdleTimeoutSeconds to " + idleTimeoutSeconds + " for EJB " + this.ebi.getDisplayName());
      }

   }

   private void updateMaxBeansInCache(int max) {
      BeanManager bm = this.ebi.getBeanManager();
      if (bm instanceof CachingManager) {
         ((CachingManager)bm).updateMaxBeansInCache(max);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("updated MaxBeansInCache to " + max + " for EJB " + this.ebi.getDisplayName());
      }

   }

   private void updateReadTimeoutSeconds(int seconds) {
      this.ebi.getCachingDescriptor().setReadTimeoutSeconds(seconds);
      BeanManager bm = this.ebi.getBeanManager();
      if (bm instanceof ReadOnlyManager) {
         ((ReadOnlyManager)bm).updateReadTimeoutSeconds(seconds);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("updated ReadTimeoutSeconds to " + seconds + " for EJB " + this.ebi.getDisplayName());
      }

   }

   private void updateKeyCacheSize(int size) {
      BeanManager bm = this.ebi.getBeanManager();
      if (bm instanceof BaseEntityManager) {
         ((BaseEntityManager)bm).updateKeyCacheSize(size);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("updated KeyCacheSize to " + size + " for EJB " + this.ebi.getDisplayName());
      }

   }

   private void updateMaxBeansInFreePool(int max) {
      BeanManager bm = this.ebi.getBeanManager();
      if (bm instanceof BaseEntityManager) {
         PoolIntf pool = ((BaseEntityManager)bm).getPool();
         pool.updateMaxBeansInFreePool(max);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("updated MaxBeansInFreePool to " + max + " for EJB " + this.ebi.getDisplayName());
      }

   }

   private void updatePoolIdleTimeoutSeconds(int seconds) {
      BeanManager bm = this.ebi.getBeanManager();
      if (bm instanceof BaseEntityManager) {
         PoolIntf pool = ((BaseEntityManager)bm).getPool();
         pool.updateIdleTimeoutSeconds(seconds);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("updated Pool IdleTimeoutSeconds to " + seconds + " for EJB " + this.ebi.getDisplayName());
      }

   }

   private static void debug(String s) {
      DEBUG_LOGGER.debug("[EntityBeanUpdateListener] " + s);
   }
}
