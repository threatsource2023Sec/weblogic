package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.bea.core.repackaged.springframework.core.env.ConfigurableEnvironment;
import com.bea.core.repackaged.springframework.core.io.ClassPathResource;
import com.bea.core.repackaged.springframework.core.io.Resource;

public class GenericXmlApplicationContext extends GenericApplicationContext {
   private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);

   public GenericXmlApplicationContext() {
   }

   public GenericXmlApplicationContext(Resource... resources) {
      this.load(resources);
      this.refresh();
   }

   public GenericXmlApplicationContext(String... resourceLocations) {
      this.load(resourceLocations);
      this.refresh();
   }

   public GenericXmlApplicationContext(Class relativeClass, String... resourceNames) {
      this.load(relativeClass, resourceNames);
      this.refresh();
   }

   public final XmlBeanDefinitionReader getReader() {
      return this.reader;
   }

   public void setValidating(boolean validating) {
      this.reader.setValidating(validating);
   }

   public void setEnvironment(ConfigurableEnvironment environment) {
      super.setEnvironment(environment);
      this.reader.setEnvironment(this.getEnvironment());
   }

   public void load(Resource... resources) {
      this.reader.loadBeanDefinitions((Resource[])resources);
   }

   public void load(String... resourceLocations) {
      this.reader.loadBeanDefinitions((String[])resourceLocations);
   }

   public void load(Class relativeClass, String... resourceNames) {
      Resource[] resources = new Resource[resourceNames.length];

      for(int i = 0; i < resourceNames.length; ++i) {
         resources[i] = new ClassPathResource(resourceNames[i], relativeClass);
      }

      this.load(resources);
   }
}
