package weblogic.diagnostics.harvester;

import weblogic.diagnostics.harvester.internal.MetricArchiver;

public class HarvesterLifecycleDelegate {
   private static final MetricArchiver HARVESTER = MetricArchiver.getInstance();

   public static final HarvesterLifecycleDelegate getInstance() {
      return HarvesterLifecycleDelegate.HarvesterLifecycleDelegateInitializer.SINGLETON;
   }

   public boolean isEnabled() {
      return HARVESTER.isEnabled();
   }

   public void initialize() {
   }

   public void enable() {
   }

   public void disable() {
   }

   private static final class HarvesterLifecycleDelegateInitializer {
      private static final HarvesterLifecycleDelegate SINGLETON = new HarvesterLifecycleDelegate();
   }
}
