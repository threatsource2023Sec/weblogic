package weblogic.management.runtime;

import java.util.Properties;

/** @deprecated */
@Deprecated
public interface JaxRsResourceConfigTypeRuntimeMBean extends RuntimeMBean {
   String getClassName();

   Properties getProperties();
}
