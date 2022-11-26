package org.apache.openjpa.jdbc.kernel;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.util.Collection;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.OpenJPASavepoint;
import org.apache.openjpa.kernel.SavepointManager;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;

public abstract class AbstractJDBCSavepointManager implements SavepointManager, Configurable {
   private boolean _restore = false;

   public void startConfiguration() {
   }

   public void setConfiguration(Configuration conf) {
      this._restore = ((OpenJPAConfiguration)conf).getRestoreStateConstant() != 0;
   }

   public void endConfiguration() {
   }

   public boolean getRestoreFieldState() {
      return this._restore;
   }

   public void setRestoreFieldState(boolean restore) {
      this._restore = restore;
   }

   public OpenJPASavepoint newSavepoint(String name, Broker broker) {
      OpenJPASavepoint save = new ConnectionSavepoint(broker, name, this._restore);
      broker.flush();
      return save;
   }

   public boolean supportsIncrementalFlush() {
      return true;
   }

   protected abstract void rollbackDataStore(ConnectionSavepoint var1);

   protected abstract void setDataStore(ConnectionSavepoint var1);

   protected class ConnectionSavepoint extends OpenJPASavepoint {
      private Object _savepoint;

      public ConnectionSavepoint(Broker broker, String name, boolean copy) {
         super(broker, name, copy);
      }

      public Object getDataStoreSavepoint() {
         return this._savepoint;
      }

      public void setDataStoreSavepoint(Object savepoint) {
         this._savepoint = savepoint;
      }

      public Connection getConnection() {
         return ((JDBCStoreManager)this.getBroker().getStoreManager().getInnermostDelegate()).getConnection();
      }

      public Collection rollback(Collection previous) {
         AbstractJDBCSavepointManager.this.rollbackDataStore(this);
         return super.rollback(previous);
      }

      public void save(Collection states) {
         AbstractJDBCSavepointManager.this.setDataStore(this);
         super.save(states);
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         throw new NotSerializableException();
      }
   }
}
