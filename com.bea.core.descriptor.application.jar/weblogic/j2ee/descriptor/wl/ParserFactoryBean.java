package weblogic.j2ee.descriptor.wl;

public interface ParserFactoryBean {
   String getSaxparserFactory();

   void setSaxparserFactory(String var1);

   String getDocumentBuilderFactory();

   void setDocumentBuilderFactory(String var1);

   String getTransformerFactory();

   void setTransformerFactory(String var1);

   String getXpathFactory();

   void setXpathFactory(String var1);

   String getSchemaFactory();

   void setSchemaFactory(String var1);

   String getXMLInputFactory();

   void setXMLInputFactory(String var1);

   String getXMLOutputFactory();

   void setXMLOutputFactory(String var1);

   String getXMLEventFactory();

   void setXMLEventFactory(String var1);
}
