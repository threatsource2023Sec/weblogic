package weblogic.management.provider.internal.situationalconfig;

import weblogic.management.utils.situationalconfig.SituationalConfigManager;

public class SituationalConfigManagerFactory {
   private static final SituationalConfigManager SITUATIONAL_CONFIG_MANAGER = new SituationalConfigManagerImpl();

   public static SituationalConfigManager getSituationalConfigManager() {
      return SITUATIONAL_CONFIG_MANAGER;
   }
}
