package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface XMLParserSelectRegistryEntryMBean extends ConfigurationMBean {
   String getPublicId();

   void setPublicId(String var1) throws InvalidAttributeValueException;

   String getSystemId();

   void setSystemId(String var1) throws InvalidAttributeValueException;

   String getRootElementTag();

   void setRootElementTag(String var1) throws InvalidAttributeValueException;

   String getDocumentBuilderFactory();

   void setDocumentBuilderFactory(String var1) throws InvalidAttributeValueException;

   String getSAXParserFactory();

   void setSAXParserFactory(String var1) throws InvalidAttributeValueException;

   String getTransformerFactory();

   void setTransformerFactory(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getParserClassName();

   /** @deprecated */
   @Deprecated
   void setParserClassName(String var1) throws InvalidAttributeValueException;
}
