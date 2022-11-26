package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.EnvironmentsCustomizer;
import java.util.List;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({EnvironmentsCustomizer.class})
public interface Environments {
   @XmlElement(
      name = "environment"
   )
   @Valid List getEnvironments();

   void setEnvironments(List var1);

   Environment lookupEnvironment(String var1);

   Environment addEnvironment(Environment var1);

   Environment removeEnvironment(String var1);

   @Customize
   Environment getOrCreateEnvironment(String var1);

   @Customize
   Environment createEnvironment(String var1);

   @Customize
   Environment getEnvironmentByName(String var1);

   @Customize
   Environment deleteEnvironment(Environment var1);

   @Customize
   PartitionRef getPartitionRef(Partition var1);

   @Customize
   Environment getReferencedEnvironment(Partition var1);
}
