package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.PluginsCustomizer;
import java.util.List;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({PluginsCustomizer.class})
public interface Plugins {
   @XmlElement(
      name = "plugin"
   )
   @Valid List getPlugins();

   void setPlugins(List var1);

   Plugin lookupPlugin(String var1);

   Plugin addPlugin(Plugin var1);

   Plugin removePlugin(Plugin var1);

   @Customize
   Plugin createPlugin(String var1, String var2, String var3);

   @Customize
   Plugin getPluginByName(String var1);

   @Customize
   Plugin deletePlugin(Plugin var1);
}
