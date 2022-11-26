package weblogic.jdbc.common.internal;

import weblogic.jdbc.common.rac.RACAffinityContextException;
import weblogic.jdbc.common.rac.RACAffinityContextHelper;
import weblogic.jdbc.common.rac.RACAffinityContextHelperFactory;

public abstract class AbstractAffinityContextHelper implements AffinityContextHelper {
   protected static final String AFFINITY_CONTEXT_KEY_PREFIX = "weblogic.jdbc.affinity.";
   private RACAffinityContextHelper racAffinityContextHelper;

   public AbstractAffinityContextHelper() {
      try {
         this.racAffinityContextHelper = RACAffinityContextHelperFactory.getInstance();
      } catch (RACAffinityContextException var2) {
         throw new RuntimeException(var2);
      }
   }

   public Object createAffinityContext(String databaseName, String serviceName, String instanceName) throws AffinityContextException {
      try {
         return this.racAffinityContextHelper.createAffinityContext(databaseName, serviceName, instanceName);
      } catch (RACAffinityContextException var5) {
         throw new AffinityContextException(var5);
      }
   }

   public String getDatabaseName(Object affinityContext) throws AffinityContextException {
      try {
         return this.racAffinityContextHelper.getDatabaseName(affinityContext);
      } catch (RACAffinityContextException var3) {
         throw new AffinityContextException(var3);
      }
   }

   public String getServiceName(Object affinityContext) throws AffinityContextException {
      try {
         return this.racAffinityContextHelper.getServiceName(affinityContext);
      } catch (RACAffinityContextException var3) {
         throw new AffinityContextException(var3);
      }
   }

   public String getInstanceName(Object affinityContext) throws AffinityContextException {
      try {
         return this.racAffinityContextHelper.getInstanceName(affinityContext);
      } catch (RACAffinityContextException var3) {
         throw new AffinityContextException(var3);
      }
   }

   public String getKey(Object affinityContext) throws AffinityContextException {
      return this.getKey(this.getDatabaseName(affinityContext), this.getServiceName(affinityContext));
   }

   public String getKey(String databaseName, String serviceName) {
      return "weblogic.jdbc.affinity." + databaseName + "." + serviceName;
   }

   protected void debug(String msg) {
      JdbcDebug.JDBCRAC.debug(this.getClass().getCanonicalName() + ": " + msg);
   }
}
