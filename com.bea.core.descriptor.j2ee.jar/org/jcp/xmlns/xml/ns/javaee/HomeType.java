package org.jcp.xmlns.xml.ns.javaee;

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

public interface HomeType extends FullyQualifiedClassType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(HomeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("hometype234ctype");

   public static final class Factory {
      public static HomeType newInstance() {
         return (HomeType)XmlBeans.getContextTypeLoader().newInstance(HomeType.type, (XmlOptions)null);
      }

      public static HomeType newInstance(XmlOptions options) {
         return (HomeType)XmlBeans.getContextTypeLoader().newInstance(HomeType.type, options);
      }

      public static HomeType parse(java.lang.String xmlAsString) throws XmlException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HomeType.type, (XmlOptions)null);
      }

      public static HomeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HomeType.type, options);
      }

      public static HomeType parse(File file) throws XmlException, IOException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(file, HomeType.type, (XmlOptions)null);
      }

      public static HomeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(file, HomeType.type, options);
      }

      public static HomeType parse(URL u) throws XmlException, IOException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(u, HomeType.type, (XmlOptions)null);
      }

      public static HomeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(u, HomeType.type, options);
      }

      public static HomeType parse(InputStream is) throws XmlException, IOException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(is, HomeType.type, (XmlOptions)null);
      }

      public static HomeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(is, HomeType.type, options);
      }

      public static HomeType parse(Reader r) throws XmlException, IOException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(r, HomeType.type, (XmlOptions)null);
      }

      public static HomeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(r, HomeType.type, options);
      }

      public static HomeType parse(XMLStreamReader sr) throws XmlException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(sr, HomeType.type, (XmlOptions)null);
      }

      public static HomeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(sr, HomeType.type, options);
      }

      public static HomeType parse(Node node) throws XmlException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(node, HomeType.type, (XmlOptions)null);
      }

      public static HomeType parse(Node node, XmlOptions options) throws XmlException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(node, HomeType.type, options);
      }

      /** @deprecated */
      public static HomeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(xis, HomeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static HomeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (HomeType)XmlBeans.getContextTypeLoader().parse(xis, HomeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HomeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HomeType.type, options);
      }

      private Factory() {
      }
   }
}
