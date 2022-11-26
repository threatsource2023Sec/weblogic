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

public interface UpdateManagerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UpdateManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("updatemanagertype2231type");

   public static final class Factory {
      public static UpdateManagerType newInstance() {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(UpdateManagerType.type, (XmlOptions)null);
      }

      public static UpdateManagerType newInstance(XmlOptions options) {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(UpdateManagerType.type, options);
      }

      public static UpdateManagerType parse(String xmlAsString) throws XmlException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UpdateManagerType.type, (XmlOptions)null);
      }

      public static UpdateManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UpdateManagerType.type, options);
      }

      public static UpdateManagerType parse(File file) throws XmlException, IOException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, UpdateManagerType.type, (XmlOptions)null);
      }

      public static UpdateManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, UpdateManagerType.type, options);
      }

      public static UpdateManagerType parse(URL u) throws XmlException, IOException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, UpdateManagerType.type, (XmlOptions)null);
      }

      public static UpdateManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, UpdateManagerType.type, options);
      }

      public static UpdateManagerType parse(InputStream is) throws XmlException, IOException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, UpdateManagerType.type, (XmlOptions)null);
      }

      public static UpdateManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, UpdateManagerType.type, options);
      }

      public static UpdateManagerType parse(Reader r) throws XmlException, IOException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, UpdateManagerType.type, (XmlOptions)null);
      }

      public static UpdateManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, UpdateManagerType.type, options);
      }

      public static UpdateManagerType parse(XMLStreamReader sr) throws XmlException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, UpdateManagerType.type, (XmlOptions)null);
      }

      public static UpdateManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, UpdateManagerType.type, options);
      }

      public static UpdateManagerType parse(Node node) throws XmlException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, UpdateManagerType.type, (XmlOptions)null);
      }

      public static UpdateManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, UpdateManagerType.type, options);
      }

      /** @deprecated */
      public static UpdateManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, UpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UpdateManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, UpdateManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UpdateManagerType.type, options);
      }

      private Factory() {
      }
   }
}
