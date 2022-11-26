package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface XMLMBean extends XMLElementMBean {
   ParserFactoryMBean getParserFactory();

   void setParserFactory(ParserFactoryMBean var1);

   EntityMappingMBean[] getEntityMappings();

   void setEntityMappings(EntityMappingMBean[] var1);

   void addEntityMapping(EntityMappingMBean var1);

   void removeEntityMapping(EntityMappingMBean var1);
}
