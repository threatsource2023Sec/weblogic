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

public interface PersistenceServerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceServerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("persistenceservertype3a29type");

   public static final class Factory {
      public static PersistenceServerType newInstance() {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().newInstance(PersistenceServerType.type, (XmlOptions)null);
      }

      public static PersistenceServerType newInstance(XmlOptions options) {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().newInstance(PersistenceServerType.type, options);
      }

      public static PersistenceServerType parse(String xmlAsString) throws XmlException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceServerType.type, (XmlOptions)null);
      }

      public static PersistenceServerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceServerType.type, options);
      }

      public static PersistenceServerType parse(File file) throws XmlException, IOException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(file, PersistenceServerType.type, (XmlOptions)null);
      }

      public static PersistenceServerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(file, PersistenceServerType.type, options);
      }

      public static PersistenceServerType parse(URL u) throws XmlException, IOException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(u, PersistenceServerType.type, (XmlOptions)null);
      }

      public static PersistenceServerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(u, PersistenceServerType.type, options);
      }

      public static PersistenceServerType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(is, PersistenceServerType.type, (XmlOptions)null);
      }

      public static PersistenceServerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(is, PersistenceServerType.type, options);
      }

      public static PersistenceServerType parse(Reader r) throws XmlException, IOException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(r, PersistenceServerType.type, (XmlOptions)null);
      }

      public static PersistenceServerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(r, PersistenceServerType.type, options);
      }

      public static PersistenceServerType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceServerType.type, (XmlOptions)null);
      }

      public static PersistenceServerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceServerType.type, options);
      }

      public static PersistenceServerType parse(Node node) throws XmlException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(node, PersistenceServerType.type, (XmlOptions)null);
      }

      public static PersistenceServerType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(node, PersistenceServerType.type, options);
      }

      /** @deprecated */
      public static PersistenceServerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceServerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceServerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceServerType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceServerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceServerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceServerType.type, options);
      }

      private Factory() {
      }
   }
}
