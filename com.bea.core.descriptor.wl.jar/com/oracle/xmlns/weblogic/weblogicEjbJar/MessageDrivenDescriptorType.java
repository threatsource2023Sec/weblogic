package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface MessageDrivenDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessageDrivenDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("messagedrivendescriptortype85f6type");

   PoolType getPool();

   boolean isSetPool();

   void setPool(PoolType var1);

   PoolType addNewPool();

   void unsetPool();

   TimerDescriptorType getTimerDescriptor();

   boolean isSetTimerDescriptor();

   void setTimerDescriptor(TimerDescriptorType var1);

   TimerDescriptorType addNewTimerDescriptor();

   void unsetTimerDescriptor();

   ResourceAdapterJndiNameType getResourceAdapterJndiName();

   boolean isSetResourceAdapterJndiName();

   void setResourceAdapterJndiName(ResourceAdapterJndiNameType var1);

   ResourceAdapterJndiNameType addNewResourceAdapterJndiName();

   void unsetResourceAdapterJndiName();

   DestinationJndiNameType getDestinationJndiName();

   boolean isSetDestinationJndiName();

   void setDestinationJndiName(DestinationJndiNameType var1);

   DestinationJndiNameType addNewDestinationJndiName();

   void unsetDestinationJndiName();

   InitialContextFactoryType getInitialContextFactory();

   boolean isSetInitialContextFactory();

   void setInitialContextFactory(InitialContextFactoryType var1);

   InitialContextFactoryType addNewInitialContextFactory();

   void unsetInitialContextFactory();

   ProviderUrlType getProviderUrl();

   boolean isSetProviderUrl();

   void setProviderUrl(ProviderUrlType var1);

   ProviderUrlType addNewProviderUrl();

   void unsetProviderUrl();

   ConnectionFactoryJndiNameType getConnectionFactoryJndiName();

   boolean isSetConnectionFactoryJndiName();

   void setConnectionFactoryJndiName(ConnectionFactoryJndiNameType var1);

   ConnectionFactoryJndiNameType addNewConnectionFactoryJndiName();

   void unsetConnectionFactoryJndiName();

   String getDestinationResourceLink();

   boolean isSetDestinationResourceLink();

   void setDestinationResourceLink(String var1);

   String addNewDestinationResourceLink();

   void unsetDestinationResourceLink();

   String getConnectionFactoryResourceLink();

   boolean isSetConnectionFactoryResourceLink();

   void setConnectionFactoryResourceLink(String var1);

   String addNewConnectionFactoryResourceLink();

   void unsetConnectionFactoryResourceLink();

   XsdNonNegativeIntegerType getJmsPollingIntervalSeconds();

   boolean isSetJmsPollingIntervalSeconds();

   void setJmsPollingIntervalSeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewJmsPollingIntervalSeconds();

   void unsetJmsPollingIntervalSeconds();

   JmsClientIdType getJmsClientId();

   boolean isSetJmsClientId();

   void setJmsClientId(JmsClientIdType var1);

   JmsClientIdType addNewJmsClientId();

   void unsetJmsClientId();

   TrueFalseType getGenerateUniqueJmsClientId();

   boolean isSetGenerateUniqueJmsClientId();

   void setGenerateUniqueJmsClientId(TrueFalseType var1);

   TrueFalseType addNewGenerateUniqueJmsClientId();

   void unsetGenerateUniqueJmsClientId();

   TrueFalseType getDurableSubscriptionDeletion();

   boolean isSetDurableSubscriptionDeletion();

   void setDurableSubscriptionDeletion(TrueFalseType var1);

   TrueFalseType addNewDurableSubscriptionDeletion();

   void unsetDurableSubscriptionDeletion();

   XsdNonNegativeIntegerType getMaxMessagesInTransaction();

   boolean isSetMaxMessagesInTransaction();

   void setMaxMessagesInTransaction(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewMaxMessagesInTransaction();

   void unsetMaxMessagesInTransaction();

   DistributedDestinationConnectionType getDistributedDestinationConnection();

   boolean isSetDistributedDestinationConnection();

   void setDistributedDestinationConnection(DistributedDestinationConnectionType var1);

   DistributedDestinationConnectionType addNewDistributedDestinationConnection();

   void unsetDistributedDestinationConnection();

   TrueFalseType getUse81StylePolling();

   boolean isSetUse81StylePolling();

   void setUse81StylePolling(TrueFalseType var1);

   TrueFalseType addNewUse81StylePolling();

   void unsetUse81StylePolling();

   XsdNonNegativeIntegerType getInitSuspendSeconds();

   boolean isSetInitSuspendSeconds();

   void setInitSuspendSeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewInitSuspendSeconds();

   void unsetInitSuspendSeconds();

   XsdNonNegativeIntegerType getMaxSuspendSeconds();

   boolean isSetMaxSuspendSeconds();

   void setMaxSuspendSeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewMaxSuspendSeconds();

   void unsetMaxSuspendSeconds();

   SecurityPluginType getSecurityPlugin();

   boolean isSetSecurityPlugin();

   void setSecurityPlugin(SecurityPluginType var1);

   SecurityPluginType addNewSecurityPlugin();

   void unsetSecurityPlugin();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MessageDrivenDescriptorType newInstance() {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().newInstance(MessageDrivenDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDrivenDescriptorType newInstance(XmlOptions options) {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().newInstance(MessageDrivenDescriptorType.type, options);
      }

      public static MessageDrivenDescriptorType parse(java.lang.String xmlAsString) throws XmlException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDrivenDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDrivenDescriptorType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDrivenDescriptorType.type, options);
      }

      public static MessageDrivenDescriptorType parse(File file) throws XmlException, IOException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(file, MessageDrivenDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDrivenDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(file, MessageDrivenDescriptorType.type, options);
      }

      public static MessageDrivenDescriptorType parse(URL u) throws XmlException, IOException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(u, MessageDrivenDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDrivenDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(u, MessageDrivenDescriptorType.type, options);
      }

      public static MessageDrivenDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(is, MessageDrivenDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDrivenDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(is, MessageDrivenDescriptorType.type, options);
      }

      public static MessageDrivenDescriptorType parse(Reader r) throws XmlException, IOException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(r, MessageDrivenDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDrivenDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(r, MessageDrivenDescriptorType.type, options);
      }

      public static MessageDrivenDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, MessageDrivenDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDrivenDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, MessageDrivenDescriptorType.type, options);
      }

      public static MessageDrivenDescriptorType parse(Node node) throws XmlException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(node, MessageDrivenDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDrivenDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(node, MessageDrivenDescriptorType.type, options);
      }

      /** @deprecated */
      public static MessageDrivenDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, MessageDrivenDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessageDrivenDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessageDrivenDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, MessageDrivenDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDrivenDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDrivenDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
