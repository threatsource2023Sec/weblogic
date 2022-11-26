package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface TopicSubscriptionParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TopicSubscriptionParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("topicsubscriptionparamstype3e90type");

   long getMessagesLimitOverride();

   XmlLong xgetMessagesLimitOverride();

   boolean isSetMessagesLimitOverride();

   void setMessagesLimitOverride(long var1);

   void xsetMessagesLimitOverride(XmlLong var1);

   void unsetMessagesLimitOverride();

   public static final class Factory {
      public static TopicSubscriptionParamsType newInstance() {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().newInstance(TopicSubscriptionParamsType.type, (XmlOptions)null);
      }

      public static TopicSubscriptionParamsType newInstance(XmlOptions options) {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().newInstance(TopicSubscriptionParamsType.type, options);
      }

      public static TopicSubscriptionParamsType parse(String xmlAsString) throws XmlException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TopicSubscriptionParamsType.type, (XmlOptions)null);
      }

      public static TopicSubscriptionParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TopicSubscriptionParamsType.type, options);
      }

      public static TopicSubscriptionParamsType parse(File file) throws XmlException, IOException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(file, TopicSubscriptionParamsType.type, (XmlOptions)null);
      }

      public static TopicSubscriptionParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(file, TopicSubscriptionParamsType.type, options);
      }

      public static TopicSubscriptionParamsType parse(URL u) throws XmlException, IOException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(u, TopicSubscriptionParamsType.type, (XmlOptions)null);
      }

      public static TopicSubscriptionParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(u, TopicSubscriptionParamsType.type, options);
      }

      public static TopicSubscriptionParamsType parse(InputStream is) throws XmlException, IOException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(is, TopicSubscriptionParamsType.type, (XmlOptions)null);
      }

      public static TopicSubscriptionParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(is, TopicSubscriptionParamsType.type, options);
      }

      public static TopicSubscriptionParamsType parse(Reader r) throws XmlException, IOException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(r, TopicSubscriptionParamsType.type, (XmlOptions)null);
      }

      public static TopicSubscriptionParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(r, TopicSubscriptionParamsType.type, options);
      }

      public static TopicSubscriptionParamsType parse(XMLStreamReader sr) throws XmlException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(sr, TopicSubscriptionParamsType.type, (XmlOptions)null);
      }

      public static TopicSubscriptionParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(sr, TopicSubscriptionParamsType.type, options);
      }

      public static TopicSubscriptionParamsType parse(Node node) throws XmlException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(node, TopicSubscriptionParamsType.type, (XmlOptions)null);
      }

      public static TopicSubscriptionParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(node, TopicSubscriptionParamsType.type, options);
      }

      /** @deprecated */
      public static TopicSubscriptionParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(xis, TopicSubscriptionParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TopicSubscriptionParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TopicSubscriptionParamsType)XmlBeans.getContextTypeLoader().parse(xis, TopicSubscriptionParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TopicSubscriptionParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TopicSubscriptionParamsType.type, options);
      }

      private Factory() {
      }
   }
}
