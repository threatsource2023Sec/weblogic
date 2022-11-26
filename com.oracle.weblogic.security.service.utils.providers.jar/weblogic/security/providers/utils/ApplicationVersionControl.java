package weblogic.security.providers.utils;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.store.service.StoreService;
import java.util.Collection;
import java.util.Iterator;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

public abstract class ApplicationVersionControl {
   private String domain = null;
   private String realm = null;
   private StoreService storeService = null;
   private LoggerSpi logger = null;
   private Class objClass = null;

   public ApplicationVersionControl(String domain, String realm, StoreService storeService, Class objClass, LoggerSpi logger) {
      this.domain = domain;
      this.realm = realm;
      this.storeService = storeService;
      this.logger = logger;
      this.objClass = objClass;
   }

   public void applicationCopy(String sourceAppName, String destAppName) throws Exception {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".applcationCopy(): objClass=" + this.objClass + ", source=" + sourceAppName + ", dest=" + destAppName);
      }

      PersistenceManager pm = this.storeService.getPersistenceManager();
      Query query = null;

      try {
         Transaction transaction = pm.currentTransaction();
         transaction.begin();

         try {
            query = pm.newQuery(this.objClass);
            query.getFetchPlan().addGroup("all");
            String filter = "this.domainName == domain && this.realmName == realm && this.cn.matches(cnPattern) && this.wlsCreatorInfo != 'deploy'";
            String declarations = "String domain, String realm, String cnPattern";
            Object[] parameters = new Object[]{this.domain, this.realm, getCNPatternForApplication(sourceAppName)};
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("application copy: domain=" + this.domain + ", realm=" + this.realm + ", cnPattern=" + parameters[2] + ", wlsCreatorInfo!=deploy");
            }

            query.setFilter(filter);
            query.declareParameters(declarations);
            Collection collection = (Collection)query.executeWithArray(parameters);
            if (collection == null || collection.size() == 0) {
               transaction.commit();
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("application copy: found no matching " + this.objClass);
               }

               return;
            }

            Collection detachedCollection = pm.detachCopyAll(collection);
            Iterator ite = detachedCollection.iterator();

            while(ite.hasNext()) {
               this.businessObjectModify(ite.next(), sourceAppName, destAppName);
            }

            pm.makePersistentAll(detachedCollection);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("application copy done");
            }
         } catch (Exception var16) {
            transaction.rollback();
            throw var16;
         } catch (Error var17) {
            transaction.rollback();
            throw var17;
         }

         transaction.commit();
      } finally {
         if (query != null) {
            query.closeAll();
         }

         pm.close();
      }
   }

   public void applicationDelete(String appName) throws Exception {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("application delete, application id: " + appName);
      }

      PersistenceManager pm = this.storeService.getPersistenceManager();
      Query query = null;

      try {
         Transaction transaction = pm.currentTransaction();
         transaction.begin();

         try {
            query = pm.newQuery(this.objClass);
            String filter = "this.domainName == domain && this.realmName == realm && this.cn.matches(cnPattern)";
            String declarations = "String domain, String realm, String cnPattern";
            Object[] parameters = new Object[]{this.domain, this.realm, getCNPatternForApplication(appName)};
            query.setFilter(filter);
            query.declareParameters(declarations);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("application delete: domain=" + this.domain + ", realm=" + this.realm + ", cnPattern=" + parameters[2]);
            }

            query.deletePersistentAll(parameters);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("application delete done");
            }
         } catch (Exception var12) {
            transaction.rollback();
            throw var12;
         } catch (Error var13) {
            transaction.rollback();
            throw var13;
         }

         transaction.commit();
      } finally {
         if (query != null) {
            query.closeAll();
         }

         pm.close();
      }

   }

   protected String getUpdatedValue(String value, String source, String dest) {
      if (value == null) {
         return null;
      } else {
         int start = value.indexOf(source);
         if (start == -1) {
            return value;
         } else {
            StringBuffer sb = new StringBuffer(value);
            int end = start + source.length();
            sb.replace(start, end, dest);
            return sb.toString();
         }
      }
   }

   public abstract Object businessObjectModify(Object var1, String var2, String var3);

   private static String getCNPatternForApplication(String appName) {
      String escapedAppName = appName;
      int length = appName.length();
      if (length > 0 && (appName.indexOf(46) >= 0 || appName.indexOf(92) >= 0)) {
         StringBuffer buf = new StringBuffer(length + 16);

         for(int i = 0; i < length; ++i) {
            char c = appName.charAt(i);
            if (c == '.' || c == '\\') {
               buf.append('\\');
            }

            buf.append(c);
         }

         escapedAppName = buf.toString();
      }

      return ".*application=" + escapedAppName + ",.*";
   }
}
