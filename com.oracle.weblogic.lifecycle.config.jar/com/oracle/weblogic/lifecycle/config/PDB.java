package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PDBCustomizer;
import javax.validation.Payload;
import javax.xml.bind.annotation.XmlAttribute;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({PDBCustomizer.class, AuditableCustomizer.class})
public interface PDB extends Named, Payload, Auditable {
   @XmlAttribute(
      name = "id"
   )
   String getId();

   void setId(String var1);

   @XmlAttribute(
      name = "pdb-status"
   )
   String getPdbStatus();

   void setPdbStatus(String var1);

   @Customize
   Service getService();
}
