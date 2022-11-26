package javax.enterprise.inject.spi;

import javax.enterprise.inject.spi.configurator.BeanAttributesConfigurator;

public interface ProcessBeanAttributes {
   Annotated getAnnotated();

   BeanAttributes getBeanAttributes();

   void setBeanAttributes(BeanAttributes var1);

   BeanAttributesConfigurator configureBeanAttributes();

   void addDefinitionError(Throwable var1);

   void veto();

   void ignoreFinalMethods();
}
