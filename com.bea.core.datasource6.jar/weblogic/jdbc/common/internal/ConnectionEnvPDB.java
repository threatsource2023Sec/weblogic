package weblogic.jdbc.common.internal;

import java.lang.reflect.Method;
import java.sql.SQLException;
import oracle.jdbc.internal.PDBChangeEvent;
import oracle.jdbc.internal.PDBChangeEventListener;
import oracle.ucp.ConnectionHarvestingCallback;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertiesBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.utils.wrapper.Wrapper;

public class ConnectionEnvPDB implements PDBChangeEventListener {
   JDBCConnectionPool pool;
   ConnectionEnv ce;

   public ConnectionEnvPDB(JDBCConnectionPool pool, ConnectionEnv ce) {
      this.pool = pool;
      this.ce = ce;

      try {
         Class vendorClass = null;
         if (ce.conn.jconn instanceof Wrapper) {
            vendorClass = ((Wrapper)ce.conn.jconn).getVendorObj().getClass();
         } else {
            vendorClass = ce.conn.jconn.getClass();
         }

         ClassLoader jcl = ce.conn.jconn.getClass().getClassLoader();
         Class ocCls = jcl.loadClass("oracle.jdbc.internal.OracleConnection");
         if (ocCls == null || !ocCls.isAssignableFrom(vendorClass)) {
            return;
         }

         Method setPDBChangeEventListener = ocCls.getMethod("setPDBChangeEventListener", PDBChangeEventListener.class);
         if (setPDBChangeEventListener != null) {
            if (this.isPDBChangeListenerEnabled(pool)) {
               setPDBChangeEventListener.invoke(ce.conn.jconn, this);
            }

            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("Registered PDBChangeEventListener");
            }
         }
      } catch (Throwable var7) {
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("ConnectionEnvPDB constructor got exception " + var7);
         }
      }

   }

   private boolean isPDBChangeListenerEnabled(JDBCConnectionPool pool) {
      JDBCDataSourceBean datasourceBean = pool.getJDBCDataSource();
      if (datasourceBean != null && datasourceBean.getJDBCDriverParams() != null && datasourceBean.getJDBCDriverParams().getProperties() != null) {
         JDBCPropertiesBean propertiesBean = datasourceBean.getJDBCDriverParams().getProperties();
         if (propertiesBean.getProperties() != null) {
            JDBCPropertyBean[] var4 = propertiesBean.getProperties();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               JDBCPropertyBean propertyBean = var4[var6];
               if (propertyBean.getName().equals("disable-pdb-change-listener") && "true".equalsIgnoreCase(propertyBean.getValue())) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   public void pdbChanged(PDBChangeEvent e) {
      if (this.pool.isSharedPool() && !this.ce.processingSwitch.get()) {
         throw new RuntimeException("PDB switching not allowed for shared pool " + this.pool.getName());
      } else {
         String u = this.ce.saveUser;
         char[] p = this.ce.savePassword;
         Object c = this.ce.saveClientId;
         boolean saveJTS = this.ce.isJTS;
         ConnectionHarvestingCallback chc = this.ce.connectionHarvestingCallback;
         this.ce.clearIdentity();
         this.ce.clearCache();
         this.ce.cleanup();
         this.ce.setInUse();
         this.ce.setup();
         this.ce.saveUser = u;
         u = null;
         this.ce.savePassword = p;
         char[] p = null;
         this.ce.saveClientId = c;
         c = null;
         if (saveJTS) {
            this.ce.setJTS();
         }

         this.ce.connectionHarvestingCallback = chc;
         chc = null;
         OracleHelper oracleHelper = this.pool.getOracleHelper();
         if (oracleHelper != null) {
            try {
               oracleHelper.registerConnectionInitializationCallback(this.ce);
               oracleHelper.replayBeginRequest(this.ce, this.pool.getReplayInitiationTimeout());
            } catch (Throwable var10) {
            }
         }

         try {
            this.pool.connectionCallbacks(this.ce);
         } catch (SQLException var9) {
         }

         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("Executed onPDBChangeNotification");
         }

      }
   }
}
