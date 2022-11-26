package weblogic.wtc.gwt;

import java.io.Serializable;

public class DSessConnInfo implements Serializable {
   private String localAccessPointId;
   private String remoteAccessPointId;
   private String connected;
   static final long serialVersionUID = 1247362893152970095L;

   public DSessConnInfo(String localId, String remoteId, String isConn) {
      this.localAccessPointId = localId;
      this.remoteAccessPointId = remoteId;
      this.connected = isConn;
   }

   public DSessConnInfo() {
      this.localAccessPointId = null;
      this.remoteAccessPointId = null;
      this.connected = "false";
   }

   public String getLocalAccessPointId() {
      return this.localAccessPointId;
   }

   public String getRemoteAccessPointId() {
      return this.remoteAccessPointId;
   }

   public String getConnected() {
      return this.connected;
   }

   public boolean isConnected() {
      return "true".equals(this.connected);
   }
}
