package weblogic.jdbc.common.internal;

import java.util.Properties;
import weblogic.common.resourcepool.PooledResourceInfo;

public class PropertiesConnectionInfo implements PooledResourceInfo {
   Properties additionalProperties;

   PropertiesConnectionInfo(Properties additionalProperties) {
      this.additionalProperties = additionalProperties;
   }

   Properties getAdditionalProperties() {
      return this.additionalProperties;
   }

   public boolean equals(PooledResourceInfo pri) {
      return false;
   }
}
