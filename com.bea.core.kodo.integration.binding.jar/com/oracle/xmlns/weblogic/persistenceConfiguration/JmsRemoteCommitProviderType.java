package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface JmsRemoteCommitProviderType extends RemoteCommitProviderType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JmsRemoteCommitProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("jmsremotecommitprovidertype8ebdtype");

   String getTopic();

   XmlString xgetTopic();

   boolean isNilTopic();

   boolean isSetTopic();

   void setTopic(String var1);

   void xsetTopic(XmlString var1);

   void setNilTopic();

   void unsetTopic();

   int getExceptionReconnectAttempts();

   XmlInt xgetExceptionReconnectAttempts();

   boolean isSetExceptionReconnectAttempts();

   void setExceptionReconnectAttempts(int var1);

   void xsetExceptionReconnectAttempts(XmlInt var1);

   void unsetExceptionReconnectAttempts();

   String getTopicConnectionFactory();

   XmlString xgetTopicConnectionFactory();

   boolean isNilTopicConnectionFactory();

   boolean isSetTopicConnectionFactory();

   void setTopicConnectionFactory(String var1);

   void xsetTopicConnectionFactory(XmlString var1);

   void setNilTopicConnectionFactory();

   void unsetTopicConnectionFactory();

   public static final class Factory {
      public static JmsRemoteCommitProviderType newInstance() {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(JmsRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static JmsRemoteCommitProviderType newInstance(XmlOptions options) {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(JmsRemoteCommitProviderType.type, options);
      }

      public static JmsRemoteCommitProviderType parse(String xmlAsString) throws XmlException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static JmsRemoteCommitProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsRemoteCommitProviderType.type, options);
      }

      public static JmsRemoteCommitProviderType parse(File file) throws XmlException, IOException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, JmsRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static JmsRemoteCommitProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, JmsRemoteCommitProviderType.type, options);
      }

      public static JmsRemoteCommitProviderType parse(URL u) throws XmlException, IOException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, JmsRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static JmsRemoteCommitProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, JmsRemoteCommitProviderType.type, options);
      }

      public static JmsRemoteCommitProviderType parse(InputStream is) throws XmlException, IOException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, JmsRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static JmsRemoteCommitProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, JmsRemoteCommitProviderType.type, options);
      }

      public static JmsRemoteCommitProviderType parse(Reader r) throws XmlException, IOException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, JmsRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static JmsRemoteCommitProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, JmsRemoteCommitProviderType.type, options);
      }

      public static JmsRemoteCommitProviderType parse(XMLStreamReader sr) throws XmlException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, JmsRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static JmsRemoteCommitProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, JmsRemoteCommitProviderType.type, options);
      }

      public static JmsRemoteCommitProviderType parse(Node node) throws XmlException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, JmsRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static JmsRemoteCommitProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, JmsRemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static JmsRemoteCommitProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, JmsRemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JmsRemoteCommitProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JmsRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, JmsRemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsRemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsRemoteCommitProviderType.type, options);
      }

      private Factory() {
      }
   }
}
