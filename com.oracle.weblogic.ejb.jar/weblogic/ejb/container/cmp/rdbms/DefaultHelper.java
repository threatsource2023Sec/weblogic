package weblogic.ejb.container.cmp.rdbms;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.EntityBeanQuery;
import weblogic.j2ee.descriptor.wl.CompatibilityBean;
import weblogic.j2ee.descriptor.wl.MethodParamsBean;
import weblogic.j2ee.descriptor.wl.QueryMethodBean;
import weblogic.j2ee.descriptor.wl.WeblogicQueryBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;

public final class DefaultHelper {
   private static final DebugLogger debugLogger;
   private boolean isSet_checkExistsOnMethod = false;
   private Set queriesWithIncludeUpdatesSet;
   private boolean isSet_orderDatabaseOperations = false;
   private boolean isSet_enableBatchOperations = false;
   private float version = 0.0F;

   public void setVersion(float version) {
      this.version = version;
   }

   public float getVersion() {
      return this.version;
   }

   public void setIsSet_checkExistsOnMethod(boolean b, String val) {
      if (debugLogger.isDebugEnabled()) {
         debug("checkExistsOnMethod has been explicitely set in the DD to '" + val + "'");
      }

      this.isSet_checkExistsOnMethod = b;
   }

   public boolean isSet_checkExistsOnMethod() {
      return this.isSet_checkExistsOnMethod;
   }

   public void addQueryWithIncludeUpdates(WeblogicQueryBean query, String val) {
      if (this.queriesWithIncludeUpdatesSet == null) {
         this.queriesWithIncludeUpdatesSet = new HashSet();
      }

      this.queriesWithIncludeUpdatesSet.add(query);
      if (debugLogger.isDebugEnabled()) {
         debug("added WeblogicQueryBean for query '" + query.getQueryMethod().getMethodName() + "' to QueryWithIncludeUpdates List with include-updates set in the DD to '" + val + "'");
      }

   }

   public boolean isSet_IncludeUpdates(WeblogicQueryBean query) {
      return this.queriesWithIncludeUpdatesSet == null ? false : this.queriesWithIncludeUpdatesSet.contains(query);
   }

   public void setIsSet_orderDatabaseOperations(boolean b, String val) {
      if (debugLogger.isDebugEnabled()) {
         debug("orderDatabaseOperations has been explicitely set in the DD to '" + val + "'");
      }

      this.isSet_orderDatabaseOperations = b;
   }

   public boolean isSet_orderDatabaseOperations() {
      return this.isSet_orderDatabaseOperations;
   }

   public void setIsSet_enableBatchOperations(boolean b, String val) {
      if (debugLogger.isDebugEnabled()) {
         debug("enableBatchOperations has been set to true, since delay-database-insert-until has been explicitely set in the DD to '" + val + "'");
      }

      this.isSet_enableBatchOperations = b;
   }

   public boolean isSet_enableBatchOperations() {
      return this.isSet_enableBatchOperations;
   }

   public void adjustDefaults(String primaryKeyClassName, Collection ejbQueries, int concurrencyStrategy, WeblogicRdbmsJarBean cmpJar, WeblogicRdbmsBeanBean rdbmsBean) {
      if (debugLogger.isDebugEnabled()) {
         debug("adjustDefaults: ejbName- " + rdbmsBean.getEjbName());
         debug("version- " + this.getVersion());
      }

      if (this.getVersion() < 8.1F) {
         if (!this.isSet_orderDatabaseOperations()) {
            if (debugLogger.isDebugEnabled()) {
               debug(" order-database-operations not set, setting pre 8.1 default 'False'");
            }

            cmpJar.setOrderDatabaseOperations(false);
         }

         if (!this.isSet_enableBatchOperations()) {
            if (debugLogger.isDebugEnabled()) {
               debug(" enable-batch-operations not set, setting pre 8.1 default 'False'");
            }

            cmpJar.setEnableBatchOperations(false);
         }

         if (!this.isSet_checkExistsOnMethod()) {
            if (debugLogger.isDebugEnabled()) {
               debug(" check-exists-on-method  not set, setting pre Java EE default 'False'");
            }

            rdbmsBean.setCheckExistsOnMethod(false);
         }
      }

      CompatibilityBean compat = cmpJar.getCompatibility();
      if (compat == null) {
         compat = cmpJar.createCompatibility();
      }

      compat.setSerializeCharArrayToBytes(true);
      compat.setDisableStringTrimming(true);
      compat.setFindersReturnNulls(false);
      WeblogicQueryBean[] weblogicQueryBeans = rdbmsBean.getWeblogicQueries();
      Iterator var8 = ejbQueries.iterator();

      while(var8.hasNext()) {
         EntityBeanQuery ejbQuery = (EntityBeanQuery)var8.next();
         this.processIncludeUpdatesForQuery(weblogicQueryBeans, ejbQuery, rdbmsBean);
      }

   }

   private void processIncludeUpdatesForQuery(WeblogicQueryBean[] weblogicQueryBeans, EntityBeanQuery ejbQuery, WeblogicRdbmsBeanBean rdbmsBean) {
      String methodName = ejbQuery.getMethodName();
      String[] methodParams = ejbQuery.getMethodParams();
      if (debugLogger.isDebugEnabled()) {
         debug("processIncludeUpdatesForQuery checking method: " + DDUtils.getMethodSignature(methodName, methodParams));
      }

      WeblogicQueryBean weblogicQueryBean = this.getWLQueryBeanForEntityBeanQuery(ejbQuery, weblogicQueryBeans);
      if (weblogicQueryBean != null) {
         if (this.isSet_IncludeUpdates(weblogicQueryBean)) {
            if (debugLogger.isDebugEnabled()) {
               debug("existing weblogic-query has include-updates set.  leaving setting as is.");
            }

            return;
         }

         if (debugLogger.isDebugEnabled()) {
            debug("existing weblogic-query does NOT have include-updates set.  setting pre Java EE defaults");
         }

         if (this.getVersion() < 8.1F) {
            if (debugLogger.isDebugEnabled()) {
               debug("pre 8.1: setting default include-updates to false");
            }

            weblogicQueryBean.setIncludeUpdates(false);
         }
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("no matching weblogic-query found. setting include-updates to default value. ");
         }

         weblogicQueryBean = rdbmsBean.createWeblogicQuery();
         QueryMethodBean queryMethodBean = weblogicQueryBean.createQueryMethod();
         queryMethodBean.setMethodName(methodName);
         MethodParamsBean methodParamsBean = queryMethodBean.createMethodParams();
         methodParamsBean.setMethodParams(methodParams);
         if (this.getVersion() < 8.1F) {
            if (debugLogger.isDebugEnabled()) {
               debug("pre 8.1: setting default include-updates to false");
            }

            weblogicQueryBean.setIncludeUpdates(false);
         }
      }

   }

   private WeblogicQueryBean getWLQueryBeanForEntityBeanQuery(EntityBeanQuery ejbQuery, WeblogicQueryBean[] weblogicQueryBeans) {
      if (weblogicQueryBeans.length <= 0) {
         return null;
      } else {
         String methodName = ejbQuery.getMethodName();
         String[] methodParams = ejbQuery.getMethodParams();
         WeblogicQueryBean[] var5 = weblogicQueryBeans;
         int var6 = weblogicQueryBeans.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            WeblogicQueryBean wlqBean = var5[var7];
            QueryMethodBean wlqMethod = wlqBean.getQueryMethod();
            if (methodName.equals(wlqMethod.getMethodName())) {
               String[] wlqMethodParams = wlqMethod.getMethodParams().getMethodParams();
               if (methodParams.length == wlqMethodParams.length) {
                  if (methodParams.length == 0 && wlqMethodParams.length == 0) {
                     return wlqBean;
                  }

                  boolean paramMisMatch = false;

                  for(int j = 0; j < methodParams.length; ++j) {
                     String methodParam = methodParams[j];
                     String wlqMethodParam = wlqMethodParams[j];
                     if (!methodParam.equals(wlqMethodParam)) {
                        paramMisMatch = true;
                        break;
                     }
                  }

                  if (!paramMisMatch) {
                     return wlqBean;
                  }
               }
            }
         }

         return null;
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[DefaultHelper] " + s);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
