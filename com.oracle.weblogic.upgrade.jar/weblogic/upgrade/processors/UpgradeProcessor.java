package weblogic.upgrade.processors;

import weblogic.management.configuration.DomainMBean;
import weblogic.upgrade.UpgradeException;

public interface UpgradeProcessor {
   void upgradeConfiguration(DomainMBean var1, UpgradeProcessorContext var2) throws UpgradeException;
}
