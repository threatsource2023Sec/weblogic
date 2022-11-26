package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.RuntimesCustomizer;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({RuntimesCustomizer.class})
public interface Runtimes {
   @XmlElement(
      name = "runtime"
   )
   @Valid List getRuntimes();

   void setRuntimes(List var1);

   Runtime lookupRuntime(String var1);

   Runtime addRuntime(Runtime var1);

   Runtime removeRuntime(Runtime var1);

   @Customize
   List getRuntimeByType(Class var1);

   @Customize
   Runtime getRuntimeByName(String var1);

   @Customize
   Runtime createRuntime(Map var1);

   @Customize
   Runtime deleteRuntime(Runtime var1);
}
