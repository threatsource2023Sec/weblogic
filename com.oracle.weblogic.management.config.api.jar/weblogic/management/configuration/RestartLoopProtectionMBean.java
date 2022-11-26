package weblogic.management.configuration;

public interface RestartLoopProtectionMBean extends ConfigurationMBean {
   boolean isRestartLoopProtectionEnabled();

   void setRestartLoopProtectionEnabled(boolean var1);

   long getMaxRestartAllowed();

   void setMaxRestartAllowed(long var1);

   long getMaxRestartAllowedInterval();

   void setMaxRestartAllowedInterval(long var1);

   long getRestartDelay();

   void setRestartDelay(long var1);
}
