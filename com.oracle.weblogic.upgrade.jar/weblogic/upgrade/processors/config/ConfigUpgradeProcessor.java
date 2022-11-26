package weblogic.upgrade.processors.config;

import weblogic.version;
import weblogic.management.configuration.DomainMBean;
import weblogic.upgrade.UpgradeException;
import weblogic.upgrade.processors.UpgradeProcessor;
import weblogic.upgrade.processors.UpgradeProcessorContext;

public class ConfigUpgradeProcessor implements UpgradeProcessor {
   public void upgradeConfiguration(DomainMBean root, UpgradeProcessorContext ctx) throws UpgradeException {
      String currentVersion = version.getReleaseBuildVersion();
      root.setConfigurationVersion(currentVersion);
      root.setDomainVersion(currentVersion);
   }
}
