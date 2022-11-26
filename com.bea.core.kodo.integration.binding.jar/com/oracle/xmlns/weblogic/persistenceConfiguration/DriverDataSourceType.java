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

public interface DriverDataSourceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DriverDataSourceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("driverdatasourcetypee9e1type");

   public static final class Factory {
      public static DriverDataSourceType newInstance() {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().newInstance(DriverDataSourceType.type, (XmlOptions)null);
      }

      public static DriverDataSourceType newInstance(XmlOptions options) {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().newInstance(DriverDataSourceType.type, options);
      }

      public static DriverDataSourceType parse(String xmlAsString) throws XmlException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DriverDataSourceType.type, (XmlOptions)null);
      }

      public static DriverDataSourceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DriverDataSourceType.type, options);
      }

      public static DriverDataSourceType parse(File file) throws XmlException, IOException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(file, DriverDataSourceType.type, (XmlOptions)null);
      }

      public static DriverDataSourceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(file, DriverDataSourceType.type, options);
      }

      public static DriverDataSourceType parse(URL u) throws XmlException, IOException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(u, DriverDataSourceType.type, (XmlOptions)null);
      }

      public static DriverDataSourceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(u, DriverDataSourceType.type, options);
      }

      public static DriverDataSourceType parse(InputStream is) throws XmlException, IOException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(is, DriverDataSourceType.type, (XmlOptions)null);
      }

      public static DriverDataSourceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(is, DriverDataSourceType.type, options);
      }

      public static DriverDataSourceType parse(Reader r) throws XmlException, IOException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(r, DriverDataSourceType.type, (XmlOptions)null);
      }

      public static DriverDataSourceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(r, DriverDataSourceType.type, options);
      }

      public static DriverDataSourceType parse(XMLStreamReader sr) throws XmlException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(sr, DriverDataSourceType.type, (XmlOptions)null);
      }

      public static DriverDataSourceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(sr, DriverDataSourceType.type, options);
      }

      public static DriverDataSourceType parse(Node node) throws XmlException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(node, DriverDataSourceType.type, (XmlOptions)null);
      }

      public static DriverDataSourceType parse(Node node, XmlOptions options) throws XmlException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(node, DriverDataSourceType.type, options);
      }

      /** @deprecated */
      public static DriverDataSourceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xis, DriverDataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DriverDataSourceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xis, DriverDataSourceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DriverDataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DriverDataSourceType.type, options);
      }

      private Factory() {
      }
   }
}
