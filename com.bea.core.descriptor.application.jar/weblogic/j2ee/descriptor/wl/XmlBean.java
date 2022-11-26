package weblogic.j2ee.descriptor.wl;

public interface XmlBean {
   ParserFactoryBean getParserFactory();

   ParserFactoryBean createParserFactory();

   void destroyParserFactory(ParserFactoryBean var1);

   EntityMappingBean[] getEntityMappings();

   EntityMappingBean createEntityMapping();

   void destroyEntityMapping(EntityMappingBean var1);
}
