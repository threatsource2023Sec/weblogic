package weblogic.servlet.proxy;

import java.rmi.RemoteException;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.jndi.Environment;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.work.WorkManagerFactory;

public final class HealthCheckTrigger implements NakedTimerListener {
   private static final boolean DEBUG = true;
   private final Environment env;
   private final String host;
   private final String port;
   private final String toStringMsg;
   private Context ctx;
   private HealthCheck healthCheck;
   private int healthCheckInterval;
   private int healthCheckFailureCount = 0;
   private int maxHealthCheckInterval;
   private int maxRetriesCount = 0;
   private int maxRetries = 3;
   private int serverID = -1;
   private int originalHealthCheckInterval;

   public HealthCheckTrigger(String host, String port, int healthCheckInterval, int maxRetries, int maxHealthCheckInterval) {
      this.healthCheckInterval = healthCheckInterval * 1000;
      this.maxHealthCheckInterval = maxHealthCheckInterval * 1000;
      this.host = host;
      this.port = port;
      this.maxRetries = maxRetries;
      this.env = new Environment();
      this.env.setProviderUrl("t3://" + host + ":" + port);
      TimerManagerFactory.getTimerManagerFactory().getTimerManager("HTTPProxy", WorkManagerFactory.getInstance().getSystem()).schedule(this, (long)healthCheckInterval);
      this.originalHealthCheckInterval = this.healthCheckInterval;
      this.toStringMsg = "Trigger for server running at host " + host + " and port " + port;
   }

   synchronized void setHealthCheckInterval(int healthCheckInterval) {
      if (healthCheckInterval < this.maxHealthCheckInterval) {
         this.healthCheckInterval = healthCheckInterval;
      } else {
         this.healthCheckInterval = this.maxHealthCheckInterval;
      }

   }

   public final void timerExpired(Timer t) {
      try {
         if (this.healthCheck == null) {
            this.createConnection();
         }

         this.healthCheck.ping();
         this.reset();
      } catch (NamingException var7) {
         ++this.healthCheckFailureCount;
      } catch (RemoteException var8) {
         ++this.healthCheckFailureCount;
      } finally {
         if (this.healthCheckFailureCount > this.maxRetries) {
            this.healthCheckFailureCount = this.maxRetries;
            if (this.maxHealthCheckInterval != this.healthCheckInterval) {
               this.setHealthCheckInterval(this.maxRetriesCount++ * this.healthCheckInterval / 10 + this.healthCheckInterval);
            }
         } else if (this.healthCheckFailureCount == this.maxRetries && this.serverID != -1) {
            ServerManager.getServerManager().removeServer(this.serverID);
            Debug.say("REMOVED SERVER " + this.serverID);
            this.serverID = -1;
            this.healthCheck = null;
         }

      }

   }

   private void reset() {
      this.healthCheckFailureCount = 0;
      this.maxRetriesCount = 0;
   }

   private void createConnection() throws NamingException, RemoteException {
      this.ctx = this.env.getInitialContext();
      this.healthCheck = (HealthCheck)this.ctx.lookup("HealthCheck");
      this.serverID = this.healthCheck.getServerID();
      ServerManager.getServerManager().addServer(this.serverID, this.host, Integer.parseInt(this.port));
      this.reset();
      this.setHealthCheckInterval(this.originalHealthCheckInterval);
      Debug.say("ADDED SERVER " + this.serverID);
   }

   public String toString() {
      return this.toStringMsg;
   }
}
