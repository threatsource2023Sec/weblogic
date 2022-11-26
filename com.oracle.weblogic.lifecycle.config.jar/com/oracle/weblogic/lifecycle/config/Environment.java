package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.EnvironmentCustomizer;
import java.util.List;
import java.util.Properties;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({EnvironmentCustomizer.class, AuditableCustomizer.class})
public interface Environment extends Named, Auditable {
   @XmlElement
   @Valid Associations getAssociations();

   void setAssociations(Associations var1);

   @XmlElement(
      name = "partition-ref"
   )
   @Valid List getPartitionRefs();

   void setPartitionRefs(List var1);

   PartitionRef addPartitionRef(PartitionRef var1);

   PartitionRef removePartitionRef(PartitionRef var1);

   PartitionRef lookupPartitionRef(String var1);

   @Customize
   List getPartitions();

   @Customize
   Partition getPartitionById(String var1);

   @Customize
   PartitionRef getPartitionRefById(String var1);

   @Customize
   PartitionRef getPartitionRefByTypeAndName(String var1, String var2);

   @Customize
   PartitionRef createPartitionRef(Partition var1, Properties var2);

   @Customize
   PartitionRef deletePartitionRef(PartitionRef var1);

   @Customize
   Association createAssociation(Partition var1, Partition var2);

   @Customize
   Association createAssociation(PartitionRef var1, PartitionRef var2);

   @Customize
   Association createAssociation(String var1, String var2);

   @Customize
   Association removeAssociation(Partition var1, Partition var2);

   @Customize
   Association removeAssociation(PartitionRef var1, PartitionRef var2);

   @Customize
   Association removeAssociation(String var1, String var2);

   @Customize
   List findAssociations(Partition var1);

   @Customize
   List findAssociations(PartitionRef var1);

   @Customize
   List removeAssociations(Partition var1);

   @Customize
   List removeAssociations(PartitionRef var1);

   @Customize
   List removeAssociations(String var1);
}
