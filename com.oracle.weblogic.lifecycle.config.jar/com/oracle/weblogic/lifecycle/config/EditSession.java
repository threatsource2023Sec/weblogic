package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.EditSessionCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PropertyBagBeanCustomizer;
import java.beans.PropertyVetoException;
import java.util.List;
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
@Customizer({EditSessionCustomizer.class, PropertyBagBeanCustomizer.class, AuditableCustomizer.class})
public interface EditSession extends PropertyBagBean, Auditable {
   @XmlElement(
      name = "uncommitted-environment"
   )
   @Valid List getUncommittedEnvironments();

   void setUncommittedEnvironments(List var1);

   UncommittedEnvironment addUncommittedEnvironment(UncommittedEnvironment var1);

   UncommittedEnvironment removeUncommittedEnvironment(UncommittedEnvironment var1);

   @XmlAttribute(
      required = true
   )
   @XmlID
   @NotNull String getName();

   void setName(String var1) throws PropertyVetoException;

   @XmlAttribute(
      name = "workflow-id",
      required = true
   )
   String getWorkflowId();

   void setWorkflowId(String var1) throws PropertyVetoException;

   @Customize
   UncommittedEnvironment createEnvironment(String var1);

   @Customize
   UncommittedEnvironment deleteEnvironment(String var1);

   @XmlElement(
      name = "partition"
   )
   @Valid List getPartitions();

   void setPartitions(List var1);

   UncommittedPartition addPartition(UncommittedPartition var1);

   UncommittedPartition removePartition(UncommittedPartition var1);

   @Customize
   UncommittedPartition createPartition(String var1, String var2);

   @Customize
   UncommittedEnvironment getEnvironmentByName(String var1);

   @Customize
   UncommittedPartition getPartitionByName(String var1);
}
