package weblogic.jdbc.common.rac.internal;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.Properties;
import java.util.StringTokenizer;
import oracle.ucp.ConnectionAffinityCallback;
import oracle.ucp.ConnectionAffinityCallback.AffinityPolicy;
import oracle.ucp.jdbc.oracle.FailoverablePooledConnectionHelper;
import weblogic.common.ResourceException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jdbc.common.rac.RACInstance;

public class RACModuleUtils {
   static final DebugLogger JDBCRAC = DebugLogger.getDebugLogger("DebugJDBCRAC");

   static RACInstance createRACInstance(Connection c) throws ResourceException {
      Properties props = FailoverablePooledConnectionHelper.getSessionInfoOnOracleConnection(c);
      if (props == null) {
         throw new ResourceException("Unable to get connection properties on Oracle connection " + c);
      } else {
         return new RACInstanceImpl(props.getProperty("DATABASE_NAME"), props.getProperty("SERVER_HOST"), props.getProperty("INSTANCE_NAME"), props.getProperty("SERVICE_NAME"));
      }
   }

   static RACInstance createRACInstance(oracle.ucp.jdbc.oracle.RACInstance racInstance) {
      return new RACInstanceImpl(racInstance.getDatabase(), racInstance.getHost(), racInstance.getInstance(), racInstance.getService(), racInstance.getPercent(), false);
   }

   static ConnectionAffinityCallback.AffinityPolicy getUCPAffinityPolicy(ConnectionAffinityCallback.AffinityPolicy wlPolicy) {
      switch (wlPolicy) {
         case TRANSACTION_BASED_AFFINITY:
            return AffinityPolicy.TRANSACTION_BASED_AFFINITY;
         case WEBSESSION_BASED_AFFINITY:
            return AffinityPolicy.WEBSESSION_BASED_AFFINITY;
         case DATA_BASED_AFFINITY:
            return AffinityPolicy.DATA_BASED_AFFINITY;
         default:
            return null;
      }
   }

   static ConnectionAffinityCallback.AffinityPolicy getWLAffinityPolicy(ConnectionAffinityCallback.AffinityPolicy ucpPolicy) {
      switch (ucpPolicy) {
         case TRANSACTION_BASED_AFFINITY:
            return AffinityPolicy.TRANSACTION_BASED_AFFINITY;
         case WEBSESSION_BASED_AFFINITY:
            return AffinityPolicy.WEBSESSION_BASED_AFFINITY;
         case DATA_BASED_AFFINITY:
            return AffinityPolicy.DATA_BASED_AFFINITY;
         default:
            return null;
      }
   }

   static boolean isAffSetForInstance(String instance, byte[] lbaBody) throws UnsupportedEncodingException {
      String body = (new String(lbaBody, "UTF-8")).toLowerCase();
      StringTokenizer tokenizer = new StringTokenizer(body, "{}");

      String token;
      do {
         if (!tokenizer.hasMoreTokens()) {
            return false;
         }

         token = tokenizer.nextToken();
      } while(!token.matches(".*instance=" + instance.toLowerCase() + ".*aff=true"));

      return true;
   }
}
