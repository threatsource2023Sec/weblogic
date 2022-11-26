package weblogic.server;

import java.util.HashMap;
import java.util.Map;

public class ShutdownParametersBean {
   private static final ShutdownParametersBean INSTANCE = new ShutdownParametersBean();
   private int shutdownTimeout = 0;
   private int lifecycleTimeout = 0;
   private final Map shutdownDirectives = new HashMap();

   private ShutdownParametersBean() {
   }

   public static ShutdownParametersBean getInstance() {
      return INSTANCE;
   }

   public void start() throws ServiceFailureException {
   }

   public boolean isGraceful() {
      Boolean graceful = (Boolean)this.shutdownDirectives.get("Graceful");
      return graceful == null ? false : graceful;
   }

   public boolean ignoreSessions() {
      Boolean ignore = (Boolean)this.shutdownDirectives.get("ignore.sessions");
      return ignore == null ? true : ignore;
   }

   public boolean waitForAllSessions() {
      Boolean waitForAllSessions = (Boolean)this.shutdownDirectives.get("WaitForAllSessions");
      return waitForAllSessions == null ? false : waitForAllSessions;
   }

   public void setShutdownDirectives(Map shutdownDirectives) {
      this.shutdownDirectives.putAll(shutdownDirectives);
   }

   public Map getShutdownDirectives() {
      return this.shutdownDirectives;
   }

   public void setShutdownTimeout(Integer shutdownTimeout) {
      this.shutdownTimeout = shutdownTimeout;
   }

   public int getShutdownTimeout() {
      return this.shutdownTimeout;
   }

   public void setLifecycleTimeout(Integer lifecycleTimeout) {
      this.lifecycleTimeout = lifecycleTimeout;
   }

   public int getLifecycleTimeout() {
      return this.lifecycleTimeout;
   }
}
