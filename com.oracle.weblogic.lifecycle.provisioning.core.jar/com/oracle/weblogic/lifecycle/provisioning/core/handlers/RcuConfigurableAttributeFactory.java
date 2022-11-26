package com.oracle.weblogic.lifecycle.provisioning.core.handlers;

import com.oracle.weblogic.lifecycle.provisioning.api.ConfigurableAttributeFactory;
import com.oracle.weblogic.lifecycle.provisioning.api.ConfigurableAttributeLiteral;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;

@Service
@PerLookup
public class RcuConfigurableAttributeFactory implements ConfigurableAttributeFactory {
   public Collection getConfigurableAttributes(@RcuXml ProvisioningEvent provisioningEvent) {
      ArrayList confAttrs = new ArrayList();
      confAttrs.add(new ConfigurableAttributeLiteral("dbUrl"));
      confAttrs.add(new ConfigurableAttributeLiteral("dbHost"));
      confAttrs.add(new ConfigurableAttributeLiteral("dbPort"));
      confAttrs.add(new ConfigurableAttributeLiteral("dbName"));
      confAttrs.add(new ConfigurableAttributeLiteral("schemaPrefix"));
      confAttrs.add(new ConfigurableAttributeLiteral("useSamePasswordForAllSchemas"));
      return Collections.unmodifiableCollection(confAttrs);
   }
}
