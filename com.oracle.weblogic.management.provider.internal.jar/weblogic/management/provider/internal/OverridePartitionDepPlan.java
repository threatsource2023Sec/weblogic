package weblogic.management.provider.internal;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;

@Contract
public interface OverridePartitionDepPlan {
   void setPartition(PartitionMBean var1) throws IOException, XMLStreamException;

   void applyResourceOverride(ResourceGroupMBean var1, OrderedOrganizer var2) throws Exception;

   void applyResourceOverride(ConfigurationMBean var1, String var2) throws Exception;
}
