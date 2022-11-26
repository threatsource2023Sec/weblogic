package com.bea.core.repackaged.springframework.scripting.config;

import com.bea.core.repackaged.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class LangNamespaceHandler extends NamespaceHandlerSupport {
   public void init() {
      this.registerScriptBeanDefinitionParser("groovy", "com.bea.core.repackaged.springframework.scripting.groovy.GroovyScriptFactory");
      this.registerScriptBeanDefinitionParser("bsh", "com.bea.core.repackaged.springframework.scripting.bsh.BshScriptFactory");
      this.registerScriptBeanDefinitionParser("std", "com.bea.core.repackaged.springframework.scripting.support.StandardScriptFactory");
      this.registerBeanDefinitionParser("defaults", new ScriptingDefaultsParser());
   }

   private void registerScriptBeanDefinitionParser(String key, String scriptFactoryClassName) {
      this.registerBeanDefinitionParser(key, new ScriptBeanDefinitionParser(scriptFactoryClassName));
   }
}
