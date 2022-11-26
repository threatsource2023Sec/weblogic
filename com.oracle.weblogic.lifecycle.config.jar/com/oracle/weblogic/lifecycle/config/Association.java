package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({AuditableCustomizer.class})
public interface Association extends Auditable {
   @XmlAttribute(
      required = true
   )
   @XmlIDREF
   @NotNull Partition getPartition1();

   void setPartition1(Partition var1);

   @XmlAttribute(
      required = true
   )
   @XmlIDREF
   @NotNull Partition getPartition2();

   void setPartition2(Partition var1);
}
