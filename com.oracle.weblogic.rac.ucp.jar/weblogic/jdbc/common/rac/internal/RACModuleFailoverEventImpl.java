package weblogic.jdbc.common.rac.internal;

import java.lang.reflect.Method;
import oracle.ucp.jdbc.oracle.OracleFailoverEvent;
import weblogic.jdbc.common.rac.RACModuleFailoverEvent;

public class RACModuleFailoverEventImpl implements RACModuleFailoverEvent {
   private OracleFailoverEvent oracleFailoverEvent;
   static Method drainMethod = null;

   RACModuleFailoverEventImpl(OracleFailoverEvent ofe) {
      this.oracleFailoverEvent = ofe;
   }

   public String getDbUniqueName() {
      return this.oracleFailoverEvent.getDbUniqueName();
   }

   public String getEventType() {
      return this.oracleFailoverEvent.getEventType();
   }

   public String getHostName() {
      return this.oracleFailoverEvent.getHostName();
   }

   public String getInstanceName() {
      return this.oracleFailoverEvent.getInstanceName();
   }

   public String getReason() {
      return this.oracleFailoverEvent.getReason();
   }

   public String getServiceName() {
      return this.oracleFailoverEvent.getServiceName();
   }

   public String getStatus() {
      return this.oracleFailoverEvent.getStatus();
   }

   public boolean isPlanned() {
      return this.oracleFailoverEvent.getEventType().equals("database/event/service") && this.oracleFailoverEvent.getReason().equals("user");
   }

   public OracleFailoverEvent getOracleFailoverEvent() {
      return this.oracleFailoverEvent;
   }

   public String toString() {
      return this.oracleFailoverEvent.toString();
   }

   public boolean isDownEvent() {
      return this.oracleFailoverEvent.getStatus().equals("down") || this.oracleFailoverEvent.getStatus().equals("nodedown");
   }

   public boolean isNodeEvent() {
      return this.oracleFailoverEvent.getEventType().equals("database/event/host");
   }

   public boolean isServiceEvent() {
      return this.oracleFailoverEvent.getEventType().equals("database/event/service");
   }

   public boolean isUpEvent() {
      return this.oracleFailoverEvent.getStatus().equals("up");
   }

   public int getDrainTimeout() {
      int drainTimeout = 0;
      if (drainMethod != null) {
         try {
            drainTimeout = (Integer)drainMethod.invoke(this.oracleFailoverEvent, (Object[])null);
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

      return drainTimeout;
   }

   static {
      try {
         Class c = Class.forName("oracle.ucp.jdbc.oracle.OracleFailoverEvent");
         drainMethod = c.getMethod("getDrainTimeout", (Class[])null);
      } catch (Exception var2) {
      }

   }
}
