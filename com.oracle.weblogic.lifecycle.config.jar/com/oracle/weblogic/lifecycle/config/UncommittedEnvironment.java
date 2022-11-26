package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.UncommittedEnvironmentCustomizer;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({UncommittedEnvironmentCustomizer.class, AuditableCustomizer.class})
public interface UncommittedEnvironment extends Named, Auditable {
   String DELETE_TYPE = "delete";
   String CREATE_TYPE = "create";

   @XmlAttribute(
      required = true
   )
   @NotNull String getName();

   void setName(String var1);

   @XmlAttribute
   String getType();

   void setType(String var1);

   @XmlElement(
      name = "uncommitted-association"
   )
   @Valid List getUncommittedAssociations();

   void setUncommittedAssociations(List var1);

   UncommittedAssociation addUncommittedAssociation(UncommittedAssociation var1);

   UncommittedAssociation removeUncommittedAssociation(UncommittedAssociation var1);

   @Customize
   UncommittedAssociation createAssociation(String var1, String var2, String var3, String var4);

   @Customize
   boolean isDeleteType();
}
