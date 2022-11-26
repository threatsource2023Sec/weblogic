package org.jboss.weld.xml;

import javax.enterprise.inject.spi.BeanManager;

public enum XmlSchema {
   CDI10("beans_1_0.xsd", BeanManager.class.getClassLoader()),
   CDI11("beans_1_1.xsd", BeanManager.class.getClassLoader()),
   CDI20("beans_2_0.xsd", BeanManager.class.getClassLoader()),
   WELD11("weld_1_1.xsd", BeansXmlStreamParser.class.getClassLoader());

   static final XmlSchema[] CDI11_SCHEMAS = new XmlSchema[]{CDI10, WELD11, CDI11};
   static final XmlSchema[] CDI20_SCHEMAS = new XmlSchema[]{CDI10, WELD11, CDI20};
   private final String fileName;
   private final transient ClassLoader classLoader;

   private XmlSchema(String fileName, ClassLoader classLoader) {
      this.fileName = fileName;
      this.classLoader = classLoader;
   }

   public String getFileName() {
      return this.fileName;
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }
}
