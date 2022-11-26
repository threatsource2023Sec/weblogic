package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface TopicType extends DestinationType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TopicType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("topictype9d19type");

   TopicSubscriptionParamsType getTopicSubscriptionParams();

   boolean isSetTopicSubscriptionParams();

   void setTopicSubscriptionParams(TopicSubscriptionParamsType var1);

   TopicSubscriptionParamsType addNewTopicSubscriptionParams();

   void unsetTopicSubscriptionParams();

   ForwardingPolicyType.Enum getForwardingPolicy();

   ForwardingPolicyType xgetForwardingPolicy();

   boolean isSetForwardingPolicy();

   void setForwardingPolicy(ForwardingPolicyType.Enum var1);

   void xsetForwardingPolicy(ForwardingPolicyType var1);

   void unsetForwardingPolicy();

   MulticastParamsType getMulticast();

   boolean isSetMulticast();

   void setMulticast(MulticastParamsType var1);

   MulticastParamsType addNewMulticast();

   void unsetMulticast();

   public static final class Factory {
      public static TopicType newInstance() {
         return (TopicType)XmlBeans.getContextTypeLoader().newInstance(TopicType.type, (XmlOptions)null);
      }

      public static TopicType newInstance(XmlOptions options) {
         return (TopicType)XmlBeans.getContextTypeLoader().newInstance(TopicType.type, options);
      }

      public static TopicType parse(String xmlAsString) throws XmlException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TopicType.type, (XmlOptions)null);
      }

      public static TopicType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TopicType.type, options);
      }

      public static TopicType parse(File file) throws XmlException, IOException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(file, TopicType.type, (XmlOptions)null);
      }

      public static TopicType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(file, TopicType.type, options);
      }

      public static TopicType parse(URL u) throws XmlException, IOException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(u, TopicType.type, (XmlOptions)null);
      }

      public static TopicType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(u, TopicType.type, options);
      }

      public static TopicType parse(InputStream is) throws XmlException, IOException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(is, TopicType.type, (XmlOptions)null);
      }

      public static TopicType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(is, TopicType.type, options);
      }

      public static TopicType parse(Reader r) throws XmlException, IOException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(r, TopicType.type, (XmlOptions)null);
      }

      public static TopicType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(r, TopicType.type, options);
      }

      public static TopicType parse(XMLStreamReader sr) throws XmlException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(sr, TopicType.type, (XmlOptions)null);
      }

      public static TopicType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(sr, TopicType.type, options);
      }

      public static TopicType parse(Node node) throws XmlException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(node, TopicType.type, (XmlOptions)null);
      }

      public static TopicType parse(Node node, XmlOptions options) throws XmlException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(node, TopicType.type, options);
      }

      /** @deprecated */
      public static TopicType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(xis, TopicType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TopicType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TopicType)XmlBeans.getContextTypeLoader().parse(xis, TopicType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TopicType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TopicType.type, options);
      }

      private Factory() {
      }
   }
}
