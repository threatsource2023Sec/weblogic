package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface JmsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JmsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("jmstype5358type");

   int getVersion();

   XmlInt xgetVersion();

   boolean isSetVersion();

   void setVersion(int var1);

   void xsetVersion(XmlInt var1);

   void unsetVersion();

   String getNotes();

   XmlString xgetNotes();

   boolean isNilNotes();

   boolean isSetNotes();

   void setNotes(String var1);

   void xsetNotes(XmlString var1);

   void setNilNotes();

   void unsetNotes();

   QuotaType[] getQuotaArray();

   QuotaType getQuotaArray(int var1);

   int sizeOfQuotaArray();

   void setQuotaArray(QuotaType[] var1);

   void setQuotaArray(int var1, QuotaType var2);

   QuotaType insertNewQuota(int var1);

   QuotaType addNewQuota();

   void removeQuota(int var1);

   TemplateType[] getTemplateArray();

   TemplateType getTemplateArray(int var1);

   int sizeOfTemplateArray();

   void setTemplateArray(TemplateType[] var1);

   void setTemplateArray(int var1, TemplateType var2);

   TemplateType insertNewTemplate(int var1);

   TemplateType addNewTemplate();

   void removeTemplate(int var1);

   DestinationKeyType[] getDestinationKeyArray();

   DestinationKeyType getDestinationKeyArray(int var1);

   int sizeOfDestinationKeyArray();

   void setDestinationKeyArray(DestinationKeyType[] var1);

   void setDestinationKeyArray(int var1, DestinationKeyType var2);

   DestinationKeyType insertNewDestinationKey(int var1);

   DestinationKeyType addNewDestinationKey();

   void removeDestinationKey(int var1);

   JmsConnectionFactoryType[] getConnectionFactoryArray();

   JmsConnectionFactoryType getConnectionFactoryArray(int var1);

   int sizeOfConnectionFactoryArray();

   void setConnectionFactoryArray(JmsConnectionFactoryType[] var1);

   void setConnectionFactoryArray(int var1, JmsConnectionFactoryType var2);

   JmsConnectionFactoryType insertNewConnectionFactory(int var1);

   JmsConnectionFactoryType addNewConnectionFactory();

   void removeConnectionFactory(int var1);

   ForeignServerType[] getForeignServerArray();

   ForeignServerType getForeignServerArray(int var1);

   int sizeOfForeignServerArray();

   void setForeignServerArray(ForeignServerType[] var1);

   void setForeignServerArray(int var1, ForeignServerType var2);

   ForeignServerType insertNewForeignServer(int var1);

   ForeignServerType addNewForeignServer();

   void removeForeignServer(int var1);

   QueueType[] getQueueArray();

   QueueType getQueueArray(int var1);

   int sizeOfQueueArray();

   void setQueueArray(QueueType[] var1);

   void setQueueArray(int var1, QueueType var2);

   QueueType insertNewQueue(int var1);

   QueueType addNewQueue();

   void removeQueue(int var1);

   TopicType[] getTopicArray();

   TopicType getTopicArray(int var1);

   int sizeOfTopicArray();

   void setTopicArray(TopicType[] var1);

   void setTopicArray(int var1, TopicType var2);

   TopicType insertNewTopic(int var1);

   TopicType addNewTopic();

   void removeTopic(int var1);

   DistributedQueueType[] getDistributedQueueArray();

   DistributedQueueType getDistributedQueueArray(int var1);

   int sizeOfDistributedQueueArray();

   void setDistributedQueueArray(DistributedQueueType[] var1);

   void setDistributedQueueArray(int var1, DistributedQueueType var2);

   DistributedQueueType insertNewDistributedQueue(int var1);

   DistributedQueueType addNewDistributedQueue();

   void removeDistributedQueue(int var1);

   DistributedTopicType[] getDistributedTopicArray();

   DistributedTopicType getDistributedTopicArray(int var1);

   int sizeOfDistributedTopicArray();

   void setDistributedTopicArray(DistributedTopicType[] var1);

   void setDistributedTopicArray(int var1, DistributedTopicType var2);

   DistributedTopicType insertNewDistributedTopic(int var1);

   DistributedTopicType addNewDistributedTopic();

   void removeDistributedTopic(int var1);

   UniformDistributedQueueType[] getUniformDistributedQueueArray();

   UniformDistributedQueueType getUniformDistributedQueueArray(int var1);

   int sizeOfUniformDistributedQueueArray();

   void setUniformDistributedQueueArray(UniformDistributedQueueType[] var1);

   void setUniformDistributedQueueArray(int var1, UniformDistributedQueueType var2);

   UniformDistributedQueueType insertNewUniformDistributedQueue(int var1);

   UniformDistributedQueueType addNewUniformDistributedQueue();

   void removeUniformDistributedQueue(int var1);

   UniformDistributedTopicType[] getUniformDistributedTopicArray();

   UniformDistributedTopicType getUniformDistributedTopicArray(int var1);

   int sizeOfUniformDistributedTopicArray();

   void setUniformDistributedTopicArray(UniformDistributedTopicType[] var1);

   void setUniformDistributedTopicArray(int var1, UniformDistributedTopicType var2);

   UniformDistributedTopicType insertNewUniformDistributedTopic(int var1);

   UniformDistributedTopicType addNewUniformDistributedTopic();

   void removeUniformDistributedTopic(int var1);

   SafImportedDestinationsType[] getSafImportedDestinationsArray();

   SafImportedDestinationsType getSafImportedDestinationsArray(int var1);

   int sizeOfSafImportedDestinationsArray();

   void setSafImportedDestinationsArray(SafImportedDestinationsType[] var1);

   void setSafImportedDestinationsArray(int var1, SafImportedDestinationsType var2);

   SafImportedDestinationsType insertNewSafImportedDestinations(int var1);

   SafImportedDestinationsType addNewSafImportedDestinations();

   void removeSafImportedDestinations(int var1);

   SafRemoteContextType[] getSafRemoteContextArray();

   SafRemoteContextType getSafRemoteContextArray(int var1);

   int sizeOfSafRemoteContextArray();

   void setSafRemoteContextArray(SafRemoteContextType[] var1);

   void setSafRemoteContextArray(int var1, SafRemoteContextType var2);

   SafRemoteContextType insertNewSafRemoteContext(int var1);

   SafRemoteContextType addNewSafRemoteContext();

   void removeSafRemoteContext(int var1);

   SafErrorHandlingType[] getSafErrorHandlingArray();

   SafErrorHandlingType getSafErrorHandlingArray(int var1);

   int sizeOfSafErrorHandlingArray();

   void setSafErrorHandlingArray(SafErrorHandlingType[] var1);

   void setSafErrorHandlingArray(int var1, SafErrorHandlingType var2);

   SafErrorHandlingType insertNewSafErrorHandling(int var1);

   SafErrorHandlingType addNewSafErrorHandling();

   void removeSafErrorHandling(int var1);

   public static final class Factory {
      public static JmsType newInstance() {
         return (JmsType)XmlBeans.getContextTypeLoader().newInstance(JmsType.type, (XmlOptions)null);
      }

      public static JmsType newInstance(XmlOptions options) {
         return (JmsType)XmlBeans.getContextTypeLoader().newInstance(JmsType.type, options);
      }

      public static JmsType parse(String xmlAsString) throws XmlException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsType.type, (XmlOptions)null);
      }

      public static JmsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsType.type, options);
      }

      public static JmsType parse(File file) throws XmlException, IOException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(file, JmsType.type, (XmlOptions)null);
      }

      public static JmsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(file, JmsType.type, options);
      }

      public static JmsType parse(URL u) throws XmlException, IOException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(u, JmsType.type, (XmlOptions)null);
      }

      public static JmsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(u, JmsType.type, options);
      }

      public static JmsType parse(InputStream is) throws XmlException, IOException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(is, JmsType.type, (XmlOptions)null);
      }

      public static JmsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(is, JmsType.type, options);
      }

      public static JmsType parse(Reader r) throws XmlException, IOException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(r, JmsType.type, (XmlOptions)null);
      }

      public static JmsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(r, JmsType.type, options);
      }

      public static JmsType parse(XMLStreamReader sr) throws XmlException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(sr, JmsType.type, (XmlOptions)null);
      }

      public static JmsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(sr, JmsType.type, options);
      }

      public static JmsType parse(Node node) throws XmlException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(node, JmsType.type, (XmlOptions)null);
      }

      public static JmsType parse(Node node, XmlOptions options) throws XmlException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(node, JmsType.type, options);
      }

      /** @deprecated */
      public static JmsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(xis, JmsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JmsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JmsType)XmlBeans.getContextTypeLoader().parse(xis, JmsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsType.type, options);
      }

      private Factory() {
      }
   }
}
