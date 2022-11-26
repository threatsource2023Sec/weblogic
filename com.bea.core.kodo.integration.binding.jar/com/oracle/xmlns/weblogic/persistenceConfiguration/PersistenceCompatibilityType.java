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

public interface PersistenceCompatibilityType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceCompatibilityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("persistencecompatibilitytype862atype");

   public static final class Factory {
      public static PersistenceCompatibilityType newInstance() {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().newInstance(PersistenceCompatibilityType.type, (XmlOptions)null);
      }

      public static PersistenceCompatibilityType newInstance(XmlOptions options) {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().newInstance(PersistenceCompatibilityType.type, options);
      }

      public static PersistenceCompatibilityType parse(String xmlAsString) throws XmlException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceCompatibilityType.type, (XmlOptions)null);
      }

      public static PersistenceCompatibilityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceCompatibilityType.type, options);
      }

      public static PersistenceCompatibilityType parse(File file) throws XmlException, IOException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(file, PersistenceCompatibilityType.type, (XmlOptions)null);
      }

      public static PersistenceCompatibilityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(file, PersistenceCompatibilityType.type, options);
      }

      public static PersistenceCompatibilityType parse(URL u) throws XmlException, IOException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(u, PersistenceCompatibilityType.type, (XmlOptions)null);
      }

      public static PersistenceCompatibilityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(u, PersistenceCompatibilityType.type, options);
      }

      public static PersistenceCompatibilityType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(is, PersistenceCompatibilityType.type, (XmlOptions)null);
      }

      public static PersistenceCompatibilityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(is, PersistenceCompatibilityType.type, options);
      }

      public static PersistenceCompatibilityType parse(Reader r) throws XmlException, IOException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(r, PersistenceCompatibilityType.type, (XmlOptions)null);
      }

      public static PersistenceCompatibilityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(r, PersistenceCompatibilityType.type, options);
      }

      public static PersistenceCompatibilityType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceCompatibilityType.type, (XmlOptions)null);
      }

      public static PersistenceCompatibilityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceCompatibilityType.type, options);
      }

      public static PersistenceCompatibilityType parse(Node node) throws XmlException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(node, PersistenceCompatibilityType.type, (XmlOptions)null);
      }

      public static PersistenceCompatibilityType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(node, PersistenceCompatibilityType.type, options);
      }

      /** @deprecated */
      public static PersistenceCompatibilityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceCompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceCompatibilityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceCompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceCompatibilityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceCompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceCompatibilityType.type, options);
      }

      private Factory() {
      }
   }
}
