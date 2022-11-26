package weblogic.jdbc.common.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import weblogic.common.ResourceException;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ONSClientRuntimeMBean;
import weblogic.management.runtime.ONSDaemonRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class ONSClientRuntimeImpl extends RuntimeMBeanDelegate implements ONSClientRuntimeMBean {
   JDBCConnectionPool pool;
   JDBCDataSourceBean dsBean;
   String[] onsNodeList;
   List daemons = new ArrayList();

   public ONSClientRuntimeImpl(JDBCConnectionPool pool, String beanName, RuntimeMBean parent) throws ManagementException {
      super(beanName, parent, true);
      this.pool = pool;
      this.dsBean = pool.getJDBCDataSource();
      this.createONSDaemons();
   }

   public ONSDaemonRuntimeMBean[] getONSDaemonRuntimes() {
      if (this.onsNodeList == null && this.daemons.size() == 0) {
         try {
            this.createONSDaemons();
         } catch (NumberFormatException var2) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("error creating ONS runtime MBeans for datasource " + this.pool.getName(), var2);
            }
         } catch (ManagementException var3) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("error creating ONS runtime MBeans for datasource " + this.pool.getName(), var3);
            }
         }
      }

      return (ONSDaemonRuntimeMBean[])((ONSDaemonRuntimeMBean[])this.daemons.toArray(new ONSDaemonRuntimeMBean[this.daemons.size()]));
   }

   private void createONSDaemons() throws NumberFormatException, ManagementException {
      String nodeList = this.dsBean.getJDBCOracleParams().getOnsNodeList();
      if (nodeList != null && nodeList.length() != 0) {
         this.onsNodeList = this.parseStaticONSNodeList(nodeList);
      } else {
         try {
            this.onsNodeList = this.lookupAutoONS();
         } catch (ResourceException var3) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("unable to get connection to determine auto-ONS config for " + this.pool.getName(), var3);
            }
         }
      }

      if (this.onsNodeList != null) {
         this.createONSRuntimes(this.onsNodeList);
      }

   }

   private String[] parseStaticONSNodeList(String nodeList) {
      return ONSConfigurationHelper.parseNodeList(nodeList);
   }

   private void createONSRuntimes(String[] hostPorts) throws NumberFormatException, ManagementException {
      for(int i = 0; i < hostPorts.length; ++i) {
         String[] hostPort = hostPorts[i].split("[\\s:]");
         if (hostPort.length < 2) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("invalid ONS host/port: " + hostPorts[i]);
            }
         } else {
            String dname = this.name + "_" + i;
            ONSDaemonRuntimeMBean daemon = new ONSDaemonRuntimeImpl(hostPort[0], Integer.parseInt(hostPort[1]), this.dsBean.getJDBCOracleParams().getOnsWalletFile(), this.dsBean.getJDBCOracleParams().getOnsWalletPassword(), dname, this);
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("created ONSDaemonRuntimeMBean " + dname);
            }

            this.daemons.add(daemon);
         }
      }

   }

   private String[] parseAutoONSConfig(String config) {
      List hostPorts = new ArrayList();
      String[] tokens = config.split("[\\n]");
      if (tokens != null) {
         for(int i = 0; i < tokens.length; ++i) {
            if (tokens[i].startsWith("nodes")) {
               String[] nodeValue = tokens[i].split("=");
               String[] pairs = nodeValue[1].split(",");
               String[] var7 = pairs;
               int var8 = pairs.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  String pair = var7[var9];
                  hostPorts.add(pair);
               }
            }
         }
      }

      return (String[])hostPorts.toArray(new String[hostPorts.size()]);
   }

   private String[] lookupAutoONS() throws ResourceException {
      ConnectionEnv cc = null;

      Properties props;
      try {
         cc = this.pool.reserveInternal(-1);
         if (cc != null) {
            props = this.pool.getOracleHelper().getConnectionInfo(cc);
            if (props != null) {
               String onsConfig = props.getProperty("AUTH_ONS_CONFIG");
               if (onsConfig != null) {
                  String[] var4 = this.parseAutoONSConfig(onsConfig);
                  return var4;
               }
            }
         }

         props = null;
      } finally {
         if (cc != null) {
            this.pool.release(cc);
         }

      }

      return props;
   }
}
