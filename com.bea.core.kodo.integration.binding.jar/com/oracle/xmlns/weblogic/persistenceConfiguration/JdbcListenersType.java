package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface JdbcListenersType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JdbcListenersType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("jdbclistenerstype726dtype");

   CustomJdbcListenerType[] getCustomJdbcListenerArray();

   CustomJdbcListenerType getCustomJdbcListenerArray(int var1);

   boolean isNilCustomJdbcListenerArray(int var1);

   int sizeOfCustomJdbcListenerArray();

   void setCustomJdbcListenerArray(CustomJdbcListenerType[] var1);

   void setCustomJdbcListenerArray(int var1, CustomJdbcListenerType var2);

   void setNilCustomJdbcListenerArray(int var1);

   CustomJdbcListenerType insertNewCustomJdbcListener(int var1);

   CustomJdbcListenerType addNewCustomJdbcListener();

   void removeCustomJdbcListener(int var1);

   public static final class Factory {
      public static JdbcListenersType newInstance() {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().newInstance(JdbcListenersType.type, (XmlOptions)null);
      }

      public static JdbcListenersType newInstance(XmlOptions options) {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().newInstance(JdbcListenersType.type, options);
      }

      public static JdbcListenersType parse(String xmlAsString) throws XmlException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdbcListenersType.type, (XmlOptions)null);
      }

      public static JdbcListenersType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdbcListenersType.type, options);
      }

      public static JdbcListenersType parse(File file) throws XmlException, IOException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(file, JdbcListenersType.type, (XmlOptions)null);
      }

      public static JdbcListenersType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(file, JdbcListenersType.type, options);
      }

      public static JdbcListenersType parse(URL u) throws XmlException, IOException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(u, JdbcListenersType.type, (XmlOptions)null);
      }

      public static JdbcListenersType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(u, JdbcListenersType.type, options);
      }

      public static JdbcListenersType parse(InputStream is) throws XmlException, IOException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(is, JdbcListenersType.type, (XmlOptions)null);
      }

      public static JdbcListenersType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(is, JdbcListenersType.type, options);
      }

      public static JdbcListenersType parse(Reader r) throws XmlException, IOException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(r, JdbcListenersType.type, (XmlOptions)null);
      }

      public static JdbcListenersType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(r, JdbcListenersType.type, options);
      }

      public static JdbcListenersType parse(XMLStreamReader sr) throws XmlException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(sr, JdbcListenersType.type, (XmlOptions)null);
      }

      public static JdbcListenersType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(sr, JdbcListenersType.type, options);
      }

      public static JdbcListenersType parse(Node node) throws XmlException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(node, JdbcListenersType.type, (XmlOptions)null);
      }

      public static JdbcListenersType parse(Node node, XmlOptions options) throws XmlException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(node, JdbcListenersType.type, options);
      }

      /** @deprecated */
      public static JdbcListenersType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(xis, JdbcListenersType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JdbcListenersType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JdbcListenersType)XmlBeans.getContextTypeLoader().parse(xis, JdbcListenersType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdbcListenersType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdbcListenersType.type, options);
      }

      private Factory() {
      }
   }
}
