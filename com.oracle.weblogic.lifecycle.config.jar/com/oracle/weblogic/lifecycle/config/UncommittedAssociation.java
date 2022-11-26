package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import javax.xml.bind.annotation.XmlAttribute;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({AuditableCustomizer.class})
public interface UncommittedAssociation extends Auditable {
   @XmlAttribute(
      name = "partition1-name",
      required = true
   )
   String getPartition1Name();

   void setPartition1Name(String var1);

   @XmlAttribute(
      name = "partition2-name",
      required = true
   )
   String getPartition2Name();

   void setPartition2Name(String var1);

   @XmlAttribute(
      name = "runtime1-name",
      required = true
   )
   String getRuntime1Name();

   void setRuntime1Name(String var1);

   @XmlAttribute(
      name = "runtime2-name",
      required = true
   )
   String getRuntime2Name();

   void setRuntime2Name(String var1);
}
