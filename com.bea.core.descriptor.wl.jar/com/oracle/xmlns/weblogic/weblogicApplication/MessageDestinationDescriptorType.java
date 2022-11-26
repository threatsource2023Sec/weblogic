package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface MessageDestinationDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessageDestinationDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("messagedestinationdescriptortype55aftype");

   MessageDestinationNameType getMessageDestinationName();

   void setMessageDestinationName(MessageDestinationNameType var1);

   MessageDestinationNameType addNewMessageDestinationName();

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

   String getDestinationResourceLink();

   boolean isSetDestinationResourceLink();

   void setDestinationResourceLink(String var1);

   String addNewDestinationResourceLink();

   void unsetDestinationResourceLink();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MessageDestinationDescriptorType newInstance() {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDestinationDescriptorType newInstance(XmlOptions options) {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationDescriptorType.type, options);
      }

      public static MessageDestinationDescriptorType parse(java.lang.String xmlAsString) throws XmlException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDestinationDescriptorType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationDescriptorType.type, options);
      }

      public static MessageDestinationDescriptorType parse(File file) throws XmlException, IOException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDestinationDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationDescriptorType.type, options);
      }

      public static MessageDestinationDescriptorType parse(URL u) throws XmlException, IOException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDestinationDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationDescriptorType.type, options);
      }

      public static MessageDestinationDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDestinationDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationDescriptorType.type, options);
      }

      public static MessageDestinationDescriptorType parse(Reader r) throws XmlException, IOException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDestinationDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationDescriptorType.type, options);
      }

      public static MessageDestinationDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDestinationDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationDescriptorType.type, options);
      }

      public static MessageDestinationDescriptorType parse(Node node) throws XmlException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationDescriptorType.type, (XmlOptions)null);
      }

      public static MessageDestinationDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationDescriptorType.type, options);
      }

      /** @deprecated */
      public static MessageDestinationDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessageDestinationDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessageDestinationDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
