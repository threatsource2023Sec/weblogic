package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.ServiceCustomizer;
import com.oracle.weblogic.lifecycle.config.validators.ReferenceConstraint;
import javax.validation.Payload;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({ServiceCustomizer.class, AuditableCustomizer.class})
public interface Service extends Payload, Auditable {
   @XmlAttribute(
      required = true
   )
   @XmlID
   @NotNull String getId();

   void setId(String var1);

   @XmlAttribute(
      name = "top-level-dir"
   )
   String getTopLevelDir();

   void setTopLevelDir(String var1);

   @XmlAttribute(
      name = "two-task"
   )
   String getTwoTask();

   void setTwoTask(String var1);

   @XmlAttribute(
      required = true
   )
   @NotNull String getName();

   void setName(String var1);

   @XmlAttribute(
      name = "environment-ref"
   )
   @ReferenceConstraint(
      type = Environment.class
   )
   String getEnvironmentRef();

   void setEnvironmentRef(String var1);

   @XmlAttribute(
      name = "service-type"
   )
   String getServiceType();

   void setServiceType(String var1);

   @XmlAttribute(
      name = "identity-domain"
   )
   String getIdentityDomain();

   void setIdentityDomain(String var1);

   @XmlElement(
      name = "pdb"
   )
   @Valid PDB getPdb();

   void setPdb(PDB var1);

   @Customize
   PDB createPDB(String var1, String var2, String var3);

   @Customize
   PDB deletePDB(PDB var1);

   @Customize
   PDB deletePDB(String var1);

   @Customize
   Resources createResourcesIfNotFound();

   @XmlElement(
      name = "resources"
   )
   @Valid Resources getResources();

   void setResources(Resources var1);

   @Customize
   Environment getEnvironment();

   @Customize
   Tenant getTenant();

   @Customize
   void addTopLevelDir(String var1);

   @Customize
   void addTwoTask(String var1);
}
