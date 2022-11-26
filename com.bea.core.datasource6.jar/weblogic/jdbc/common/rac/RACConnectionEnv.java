package weblogic.jdbc.common.rac;

import java.sql.Connection;
import java.util.Properties;
import weblogic.jdbc.common.internal.ConnectionEnv;

public class RACConnectionEnv extends ConnectionEnv {
   protected RACPooledConnectionState racState;

   public RACConnectionEnv(Properties poolParams, boolean isXA) {
      super(poolParams, isXA);
   }

   public RACConnectionEnv(Properties poolParams) {
      super(poolParams);
   }

   protected void setRACPooledConnectionState(RACPooledConnectionState state) {
      this.racState = state;
   }

   public RACPooledConnectionState getRACPooledConnectionState() {
      return this.racState;
   }

   protected String getInstance() {
      return this.racState.getRACInstance().getInstance();
   }

   public RACInstance getRACInstance() {
      return this.racState.getRACInstance();
   }

   public RACModulePool getRACModulePool() {
      return (RACModulePool)this.pool;
   }

   public Connection getPooledPhysicalConnection() {
      return this.conn != null ? this.conn.jconn : null;
   }
}
