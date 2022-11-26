package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface ManagedconnectionfactoryClassDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ManagedconnectionfactoryClassDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("managedconnectionfactoryclass4b96doctype");

   String getManagedconnectionfactoryClass();

   XmlString xgetManagedconnectionfactoryClass();

   void setManagedconnectionfactoryClass(String var1);

   void xsetManagedconnectionfactoryClass(XmlString var1);

   public static final class Factory {
      public static ManagedconnectionfactoryClassDocument newInstance() {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().newInstance(ManagedconnectionfactoryClassDocument.type, (XmlOptions)null);
      }

      public static ManagedconnectionfactoryClassDocument newInstance(XmlOptions options) {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().newInstance(ManagedconnectionfactoryClassDocument.type, options);
      }

      public static ManagedconnectionfactoryClassDocument parse(String xmlAsString) throws XmlException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ManagedconnectionfactoryClassDocument.type, (XmlOptions)null);
      }

      public static ManagedconnectionfactoryClassDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ManagedconnectionfactoryClassDocument.type, options);
      }

      public static ManagedconnectionfactoryClassDocument parse(File file) throws XmlException, IOException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(file, ManagedconnectionfactoryClassDocument.type, (XmlOptions)null);
      }

      public static ManagedconnectionfactoryClassDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(file, ManagedconnectionfactoryClassDocument.type, options);
      }

      public static ManagedconnectionfactoryClassDocument parse(URL u) throws XmlException, IOException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(u, ManagedconnectionfactoryClassDocument.type, (XmlOptions)null);
      }

      public static ManagedconnectionfactoryClassDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(u, ManagedconnectionfactoryClassDocument.type, options);
      }

      public static ManagedconnectionfactoryClassDocument parse(InputStream is) throws XmlException, IOException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(is, ManagedconnectionfactoryClassDocument.type, (XmlOptions)null);
      }

      public static ManagedconnectionfactoryClassDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(is, ManagedconnectionfactoryClassDocument.type, options);
      }

      public static ManagedconnectionfactoryClassDocument parse(Reader r) throws XmlException, IOException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(r, ManagedconnectionfactoryClassDocument.type, (XmlOptions)null);
      }

      public static ManagedconnectionfactoryClassDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(r, ManagedconnectionfactoryClassDocument.type, options);
      }

      public static ManagedconnectionfactoryClassDocument parse(XMLStreamReader sr) throws XmlException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(sr, ManagedconnectionfactoryClassDocument.type, (XmlOptions)null);
      }

      public static ManagedconnectionfactoryClassDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(sr, ManagedconnectionfactoryClassDocument.type, options);
      }

      public static ManagedconnectionfactoryClassDocument parse(Node node) throws XmlException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(node, ManagedconnectionfactoryClassDocument.type, (XmlOptions)null);
      }

      public static ManagedconnectionfactoryClassDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(node, ManagedconnectionfactoryClassDocument.type, options);
      }

      /** @deprecated */
      public static ManagedconnectionfactoryClassDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(xis, ManagedconnectionfactoryClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ManagedconnectionfactoryClassDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ManagedconnectionfactoryClassDocument)XmlBeans.getContextTypeLoader().parse(xis, ManagedconnectionfactoryClassDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ManagedconnectionfactoryClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ManagedconnectionfactoryClassDocument.type, options);
      }

      private Factory() {
      }
   }
}
