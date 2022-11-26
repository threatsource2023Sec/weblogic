package weblogic.jdbc.common.rac.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import oracle.ucp.jdbc.oracle.FailoverablePooledConnectionHelper;
import weblogic.common.ResourceException;
import weblogic.jdbc.common.rac.RACConnectionEnv;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.common.rac.RACModulePool;

public class ServiceTopology {
   static final long INIT_TIMEOUT = 5000L;
   private RACModulePool pool;
   private List racInstances = new ArrayList();
   private Set serviceInstanceNames = new HashSet();
   private AtomicBoolean initialized = new AtomicBoolean();
   private AtomicBoolean initializing = new AtomicBoolean();

   public ServiceTopology(RACModulePool pool) {
      this.pool = pool;
   }

   boolean isInitialized() {
      return this.initialized.get();
   }

   void clear() {
      synchronized(this) {
         this.serviceInstanceNames.clear();
         this.racInstances.clear();
         this.initialized.set(false);
      }
   }

   void initialize() throws SQLException {
      synchronized(this) {
         if (this.initialized.get()) {
            return;
         }

         if (this.initializing.get()) {
            try {
               this.wait(5000L);
               if (this.initializing.get()) {
                  throw new SQLException("timeout waiting for service topology initialization");
               }
            } catch (InterruptedException var17) {
               throw new SQLException("error getting service topology: " + var17.getMessage());
            }

            return;
         }

         this.initializing.set(true);
      }

      boolean var13 = false;

      try {
         var13 = true;
         this.initializeFromServiceTopologyQuery();
         this.initialized.set(true);
         var13 = false;
      } finally {
         if (var13) {
            synchronized(this) {
               this.initializing.set(false);
               this.notifyAll();
            }
         }
      }

      synchronized(this) {
         this.initializing.set(false);
         this.notifyAll();
      }
   }

   synchronized List getRACInstances() {
      return new ArrayList(this.racInstances);
   }

   synchronized Set getServiceInstanceNames() {
      return new HashSet(this.serviceInstanceNames);
   }

   synchronized boolean addInstance(String serviceName, String instanceName, String databaseName, String hostName) {
      if (instanceName == null) {
         return false;
      } else {
         RACInstance racInstance = new RACInstanceImpl(databaseName, hostName, instanceName, serviceName);
         this.racInstances.add(racInstance);
         this.serviceInstanceNames.add(instanceName);
         return true;
      }
   }

   synchronized boolean removeInstance(String serviceName, String instanceName, String databaseName, String hostName) {
      if (instanceName == null) {
         return false;
      } else {
         RACInstance racInstance = new RACInstanceImpl(databaseName, hostName, instanceName, serviceName);
         this.racInstances.remove(racInstance);
         return this.serviceInstanceNames.remove(instanceName);
      }
   }

   protected void initializeFromServiceTopologyQuery() throws SQLException {
      RACConnectionEnv ce = null;
      PreparedStatement ps = null;

      try {
         ce = this.pool.getSharedRACModulePool().reserveInternalResource();
         if (ce != null && ce.conn != null && ce.conn.jconn != null) {
            if (ce.getSwitchingContext() != null && ce.getSwitchingContext().getPDBName() != null) {
               this.pool.switchToRootPartition(ce);
            }

            Connection c = ce.conn.jconn;
            Properties props = FailoverablePooledConnectionHelper.getSessionInfoOnOracleConnection(c);
            ps = c.prepareStatement("select dbms_service_prvt.get_topology(?) from dual");
            ps.setString(1, this.pool.getServiceName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               String instances = rs.getString(1);
               if (instances != null && instances.length() > 0) {
                  String[] var7 = instances.split(",");
                  int var8 = var7.length;

                  for(int var9 = 0; var9 < var8; ++var9) {
                     String instance = var7[var9];
                     this.addInstance(this.pool.getServiceName(), instance, props.getProperty("DATABASE_NAME"), props.getProperty("SERVER_HOST"));
                  }
               }
            }
         }
      } catch (ResourceException var21) {
         throw new SQLException("error querying service topology", var21);
      } finally {
         if (ps != null) {
            try {
               ps.close();
            } catch (SQLException var20) {
            }
         }

         if (ce != null) {
            try {
               this.pool.getSharedRACModulePool().release(ce);
            } catch (ResourceException var19) {
            }
         }

      }

   }

   public String toString() {
      return this.serviceInstanceNames.toString();
   }
}
