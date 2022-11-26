package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface XMLRegistryMBean extends ConfigurationMBean {
   String getDocumentBuilderFactory();

   void setDocumentBuilderFactory(String var1) throws InvalidAttributeValueException;

   String getSAXParserFactory();

   void setSAXParserFactory(String var1) throws InvalidAttributeValueException;

   String getTransformerFactory();

   void setTransformerFactory(String var1) throws InvalidAttributeValueException;

   String getXpathFactory();

   void setXpathFactory(String var1) throws InvalidAttributeValueException;

   String getSchemaFactory();

   void setSchemaFactory(String var1) throws InvalidAttributeValueException;

   String getXMLInputFactory();

   void setXMLInputFactory(String var1) throws InvalidAttributeValueException;

   String getXMLOutputFactory();

   void setXMLOutputFactory(String var1) throws InvalidAttributeValueException;

   String getXMLEventFactory();

   void setXMLEventFactory(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean addRegistryEntry(XMLRegistryEntryMBean var1);

   /** @deprecated */
   @Deprecated
   boolean removeRegistryEntry(XMLRegistryEntryMBean var1);

   /** @deprecated */
   @Deprecated
   XMLRegistryEntryMBean[] getRegistryEntries();

   /** @deprecated */
   @Deprecated
   void setRegistryEntries(XMLRegistryEntryMBean[] var1) throws InvalidAttributeValueException;

   XMLParserSelectRegistryEntryMBean createXMLParserSelectRegistryEntry(String var1);

   void destroyXMLParserSelectRegistryEntry(XMLParserSelectRegistryEntryMBean var1);

   boolean addParserSelectRegistryEntry(XMLParserSelectRegistryEntryMBean var1);

   boolean removeParserSelectRegistryEntry(XMLParserSelectRegistryEntryMBean var1);

   XMLParserSelectRegistryEntryMBean[] getXMLParserSelectRegistryEntries();

   /** @deprecated */
   @Deprecated
   XMLParserSelectRegistryEntryMBean[] getParserSelectRegistryEntries();

   void setParserSelectRegistryEntries(XMLParserSelectRegistryEntryMBean[] var1) throws InvalidAttributeValueException;

   boolean addEntitySpecRegistryEntry(XMLEntitySpecRegistryEntryMBean var1);

   boolean removeEntitySpecRegistryEntry(XMLEntitySpecRegistryEntryMBean var1);

   /** @deprecated */
   @Deprecated
   XMLEntitySpecRegistryEntryMBean[] getEntitySpecRegistryEntries();

   XMLEntitySpecRegistryEntryMBean[] getXMLEntitySpecRegistryEntries();

   XMLEntitySpecRegistryEntryMBean createXMLEntitySpecRegistryEntry(String var1);

   void destroyXMLEntitySpecRegistryEntry(XMLEntitySpecRegistryEntryMBean var1);

   void setEntitySpecRegistryEntries(XMLEntitySpecRegistryEntryMBean[] var1) throws InvalidAttributeValueException;

   String getWhenToCache();

   void setWhenToCache(String var1);

   boolean isHandleEntityInvalidation();

   void setHandleEntityInvalidation(boolean var1);

   XMLParserSelectRegistryEntryMBean findParserSelectMBeanByKey(String var1, String var2, String var3);

   XMLEntitySpecRegistryEntryMBean findEntitySpecMBeanByKey(String var1, String var2);
}
