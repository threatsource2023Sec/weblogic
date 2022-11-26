package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface ParserFactoryMBean extends XMLElementMBean {
   String getSaxparserFactory();

   void setSaxparserFactory(String var1);

   String getDocumentBuilderFactory();

   void setDocumentBuilderFactory(String var1);

   String getTransformerFactory();

   void setTransformerFactory(String var1);
}
