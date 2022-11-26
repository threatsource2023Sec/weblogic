package weblogic.j2ee.descriptor.wl;

import java.util.ArrayList;
import java.util.List;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.JDBCUtil;

public class JDBCValidator {
   static boolean badUrl = false;

   public static void validateJDBCDataSource(JDBCDataSourceBean bean) throws IllegalArgumentException {
      badUrl = false;
      if (bean.getName() == null) {
         throw new IllegalArgumentException(JDBCUtil.getTextFormatter().nameNull());
      } else {
         JDBCDriverParamsBean d = bean.getJDBCDriverParams();
         boolean hasDRCP = false;
         boolean hasConnectionClass = false;
         boolean hasOracleCache = false;
         boolean isOracle = false;
         boolean hasReplayDriver = false;
         boolean hasXAReplayDriver = false;
         String type = JDBCUtil.getDataSourceType(bean);
         JDBCOracleParamsBean op = bean.getJDBCOracleParams();
         String s;
         if (d != null) {
            if (d.getUrl() != null) {
               isOracle = d.getUrl().startsWith("jdbc:oracle:");
            }

            List pdbProxyProps = new ArrayList();
            if (isOracle) {
               hasDRCP = d.getUrl().toUpperCase().matches("(.*\\(SERVER=POOLED\\).*)|(.*:POOLED)");
               if (d.getDriverName() != null && d.getDriverName().startsWith("oracle.jdbc.replay")) {
                  hasReplayDriver = true;
                  if (d.getDriverName().startsWith("oracle.jdbc.replay.OracleXA")) {
                     hasXAReplayDriver = true;
                  }
               }

               int i;
               if (d.getProperties() != null) {
                  JDBCPropertyBean[] p = d.getProperties().getProperties();

                  for(i = 0; i < p.length; ++i) {
                     if (p[i].getName().toLowerCase().startsWith("oracle.jdbc.drcpconnectionclass")) {
                        hasConnectionClass = true;
                     } else if (p[i].getName().toLowerCase().equals("oracle.jdbc.implicitstatementcachesize")) {
                        hasOracleCache = true;
                     } else if (p[i].getName().equalsIgnoreCase("weblogic.jdbc.drainTimeout")) {
                        int j = true;

                        int j;
                        try {
                           j = Integer.parseInt(p[i].getValue());
                        } catch (Exception var15) {
                           j = -1;
                        }

                        if (j < 0) {
                           throw new IllegalArgumentException("weblogic.jdbc.drainTimeout must be a non-negative numeric value");
                        }
                     } else if (p[i].getName().startsWith("weblogic.jdbc.pdbProxy.")) {
                        pdbProxyProps.add(p[i].getName());
                     }
                  }
               }

               if (type.equals("AGL")) {
                  s = d.getUrl().toUpperCase();
                  if (s.indexOf("SERVICE_NAME") == -1) {
                     badUrl = true;
                     if (s.indexOf("@LDAP") == -1) {
                        i = s.lastIndexOf("@");
                        if (i != -1) {
                           String s = s.substring(i);
                           if (s.indexOf(47) == -1 && s.indexOf(58) == -1) {
                              badUrl = false;
                           }
                        }

                        if (badUrl) {
                           JDBCLogger.logAglUrl(bean.getName(), d.getUrl());
                        }
                     }
                  }
               }
            }

            if (pdbProxyProps.size() > 1) {
               throw new IllegalArgumentException("Only one PDB proxy user driver property allowed: " + pdbProxyProps);
            }
         }

         if (hasDRCP != hasConnectionClass) {
            JDBCLogger.logDrcpError();
            throw new IllegalArgumentException(JDBCUtil.getTextFormatter().badDRCP());
         } else {
            JDBCConnectionPoolParamsBean cp;
            if (hasReplayDriver || hasOracleCache) {
               cp = bean.getJDBCConnectionPoolParams();
               if (cp.getStatementCacheSize() != 0) {
                  cp.setStatementCacheSize(0);
                  JDBCLogger.logNoCache();
               }
            }

            cp = bean.getJDBCConnectionPoolParams();
            if (op != null && op.isOracleProxySession()) {
               if (d.getUrl().toLowerCase().indexOf("oracle") == -1) {
                  throw new IllegalArgumentException(JDBCUtil.getTextFormatter().badProxy());
               }

               if (cp != null && cp.isIdentityBasedConnectionPoolingEnabled()) {
                  throw new IllegalArgumentException(JDBCUtil.getTextFormatter().badProxy2());
               }
            }

            if (cp != null && cp.isIdentityBasedConnectionPoolingEnabled() && cp.isPinnedToThread()) {
               throw new IllegalArgumentException(JDBCUtil.getTextFormatter().badPinned());
            } else if (cp != null && cp.isPinnedToThread() && JDBCUtil.usesSharedPool(bean)) {
               throw new IllegalArgumentException("Pinned-to-thread not supported with shared pooling");
            } else if (cp != null && (hasConnectionClass || hasDRCP) && JDBCUtil.isSharedPool(bean)) {
               throw new IllegalArgumentException("DRCP not supported with shared pooling");
            } else {
               if (cp != null && hasXAReplayDriver) {
                  s = cp.getTestTableName();
                  if (s != null) {
                     s = s.toUpperCase();
                     if (s.startsWith("SQL ")) {
                        s = s.substring(4).trim();
                        if (!s.equals("ISVALID") && !s.equals("PINGDATABASE")) {
                           if (!s.startsWith("SELECT")) {
                              throw new IllegalArgumentException(JDBCUtil.getTextFormatter().badXAReplayTestTable());
                           }

                           cp.setTestTableName("SQL ISVALID");
                        }
                     }
                  }
               }

               if (hasReplayDriver && cp != null && !cp.isInvokeBeginEndRequest()) {
                  JDBCLogger.logReplayRequiresBeginEndRequest(bean.getName());
               }

               if (type != null) {
                  JDBCDataSourceParamsBean dataSourceParamsBean = bean.getJDBCDataSourceParams();
                  if (dataSourceParamsBean != null) {
                     String dataSourceList = dataSourceParamsBean.getDataSourceList();
                     if (dataSourceList != null && !type.equals("MDS")) {
                        throw new IllegalArgumentException(JDBCUtil.getTextFormatter().dataSourceListWithNonMDSType());
                     }
                  }
               }

            }
         }
      }
   }

   public static boolean isBadUrl() {
      return badUrl;
   }

   public static void validateConnectionHarvestMaxCount(JDBCConnectionPoolParamsBean bean, int newval) throws IllegalArgumentException {
      if (newval != Integer.MAX_VALUE && newval != 1) {
         int i = bean.getMaxCapacity();
         if (newval < 1 || newval > i) {
            throw new IllegalArgumentException(JDBCUtil.getTextFormatter().invalidHarvestMaxCount(i));
         }
      }
   }

   public static void validateConnectionHarvestTriggerCount(JDBCConnectionPoolParamsBean bean, int newval) throws IllegalArgumentException {
      if (newval != -1 && newval != 0) {
         int i = bean.getMaxCapacity();
         if (newval < -1 || newval > i) {
            throw new IllegalArgumentException(JDBCUtil.getTextFormatter().invalidTriggerCount(newval, i));
         }
      }
   }
}
