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

public interface SavepointManagerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SavepointManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("savepointmanagertypec5b9type");

   public static final class Factory {
      public static SavepointManagerType newInstance() {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(SavepointManagerType.type, (XmlOptions)null);
      }

      public static SavepointManagerType newInstance(XmlOptions options) {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(SavepointManagerType.type, options);
      }

      public static SavepointManagerType parse(String xmlAsString) throws XmlException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SavepointManagerType.type, (XmlOptions)null);
      }

      public static SavepointManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SavepointManagerType.type, options);
      }

      public static SavepointManagerType parse(File file) throws XmlException, IOException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, SavepointManagerType.type, (XmlOptions)null);
      }

      public static SavepointManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, SavepointManagerType.type, options);
      }

      public static SavepointManagerType parse(URL u) throws XmlException, IOException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, SavepointManagerType.type, (XmlOptions)null);
      }

      public static SavepointManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, SavepointManagerType.type, options);
      }

      public static SavepointManagerType parse(InputStream is) throws XmlException, IOException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, SavepointManagerType.type, (XmlOptions)null);
      }

      public static SavepointManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, SavepointManagerType.type, options);
      }

      public static SavepointManagerType parse(Reader r) throws XmlException, IOException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, SavepointManagerType.type, (XmlOptions)null);
      }

      public static SavepointManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, SavepointManagerType.type, options);
      }

      public static SavepointManagerType parse(XMLStreamReader sr) throws XmlException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, SavepointManagerType.type, (XmlOptions)null);
      }

      public static SavepointManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, SavepointManagerType.type, options);
      }

      public static SavepointManagerType parse(Node node) throws XmlException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, SavepointManagerType.type, (XmlOptions)null);
      }

      public static SavepointManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, SavepointManagerType.type, options);
      }

      /** @deprecated */
      public static SavepointManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, SavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SavepointManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, SavepointManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SavepointManagerType.type, options);
      }

      private Factory() {
      }
   }
}
