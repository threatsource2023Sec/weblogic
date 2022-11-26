package weblogic.deploy.internal.targetserver.state;

import java.io.Serializable;

public final class AppTransition implements Serializable {
   private static final long serialVersionUID = 1L;
   private final String xition;
   private final long gentime;
   private final String serverName;

   AppTransition(String x, long time, String s) {
      this.xition = x;
      this.gentime = time;
      this.serverName = s;
   }

   public String getXition() {
      return this.xition;
   }

   public String getServerName() {
      return this.serverName;
   }

   public long getGentime() {
      return this.gentime;
   }
}
