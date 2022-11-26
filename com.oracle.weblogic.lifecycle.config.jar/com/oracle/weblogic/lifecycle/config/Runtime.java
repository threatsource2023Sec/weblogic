package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PropertyBagBeanCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.RuntimeCustomizer;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({RuntimeCustomizer.class, PropertyBagBeanCustomizer.class, AuditableCustomizer.class})
public interface Runtime extends Named, PropertyBagBean, Auditable {
   @XmlAttribute
   String getHostname();

   void setHostname(String var1);

   @XmlAttribute
   String getPort();

   void setPort(String var1);

   @XmlAttribute
   String getType();

   void setType(String var1);

   @XmlAttribute
   String getProtocol();

   void setProtocol(String var1);

   @XmlElement(
      name = "partition"
   )
   @Valid List getPartitions();

   void setPartitions(List var1);

   Partition lookupPartition(String var1);

   Partition addPartition(Partition var1);

   Partition removePartition(Partition var1);

   @Customize
   void update(Map var1);

   @Customize
   Partition getPartitionById(String var1);

   @Customize
   Partition getPartitionByName(String var1);

   @Customize
   Partition createPartition(Map var1);

   @Customize
   Partition deletePartition(Partition var1);
}
