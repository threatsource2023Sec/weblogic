package weblogic.management.mbeanservers.edit.internal;

import java.io.Serializable;
import weblogic.management.mbeanservers.edit.ServerStatus;

public class ServerStatusImpl implements Serializable, ServerStatus {
   private String serverName;
   private int serverState;
   private Exception serverException;

   public ServerStatusImpl(String serverName, int serverState, Exception serverException) {
      this.serverName = serverName;
      this.serverState = serverState;
      this.serverException = serverException;
   }

   public String getServerName() {
      return this.serverName;
   }

   public int getServerState() {
      return this.serverState;
   }

   public Exception getServerException() {
      return this.serverException;
   }
}
