package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

/** @deprecated */
@Deprecated
public interface XMLRegistryEntryMBean extends ConfigurationMBean {
   String getPublicId();

   void setPublicId(String var1) throws InvalidAttributeValueException;

   String getSystemId();

   void setSystemId(String var1) throws InvalidAttributeValueException;

   String getRootElementTag();

   void setRootElementTag(String var1) throws InvalidAttributeValueException;

   String getEntityPath();

   void setEntityPath(String var1) throws InvalidAttributeValueException;

   String getParserClassName();

   void setParserClassName(String var1) throws InvalidAttributeValueException;

   String getDocumentBuilderFactory();

   void setDocumentBuilderFactory(String var1) throws InvalidAttributeValueException;

   String getSAXParserFactory();

   void setSAXParserFactory(String var1) throws InvalidAttributeValueException;
}
