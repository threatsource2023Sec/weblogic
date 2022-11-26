package weblogic.management.configuration;

import java.util.Set;

public interface TargetMBean extends ConfigurationMBean {
   Set getServerNames();
}
