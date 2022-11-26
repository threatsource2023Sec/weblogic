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

public interface FullyQualifiedClassType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FullyQualifiedClassType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("fullyqualifiedclasstypeb479type");

   public static final class Factory {
      public static FullyQualifiedClassType newInstance() {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().newInstance(FullyQualifiedClassType.type, (XmlOptions)null);
      }

      public static FullyQualifiedClassType newInstance(XmlOptions options) {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().newInstance(FullyQualifiedClassType.type, options);
      }

      public static FullyQualifiedClassType parse(java.lang.String xmlAsString) throws XmlException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FullyQualifiedClassType.type, (XmlOptions)null);
      }

      public static FullyQualifiedClassType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FullyQualifiedClassType.type, options);
      }

      public static FullyQualifiedClassType parse(File file) throws XmlException, IOException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(file, FullyQualifiedClassType.type, (XmlOptions)null);
      }

      public static FullyQualifiedClassType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(file, FullyQualifiedClassType.type, options);
      }

      public static FullyQualifiedClassType parse(URL u) throws XmlException, IOException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(u, FullyQualifiedClassType.type, (XmlOptions)null);
      }

      public static FullyQualifiedClassType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(u, FullyQualifiedClassType.type, options);
      }

      public static FullyQualifiedClassType parse(InputStream is) throws XmlException, IOException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(is, FullyQualifiedClassType.type, (XmlOptions)null);
      }

      public static FullyQualifiedClassType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(is, FullyQualifiedClassType.type, options);
      }

      public static FullyQualifiedClassType parse(Reader r) throws XmlException, IOException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(r, FullyQualifiedClassType.type, (XmlOptions)null);
      }

      public static FullyQualifiedClassType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(r, FullyQualifiedClassType.type, options);
      }

      public static FullyQualifiedClassType parse(XMLStreamReader sr) throws XmlException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(sr, FullyQualifiedClassType.type, (XmlOptions)null);
      }

      public static FullyQualifiedClassType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(sr, FullyQualifiedClassType.type, options);
      }

      public static FullyQualifiedClassType parse(Node node) throws XmlException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(node, FullyQualifiedClassType.type, (XmlOptions)null);
      }

      public static FullyQualifiedClassType parse(Node node, XmlOptions options) throws XmlException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(node, FullyQualifiedClassType.type, options);
      }

      /** @deprecated */
      public static FullyQualifiedClassType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(xis, FullyQualifiedClassType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FullyQualifiedClassType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FullyQualifiedClassType)XmlBeans.getContextTypeLoader().parse(xis, FullyQualifiedClassType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FullyQualifiedClassType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FullyQualifiedClassType.type, options);
      }

      private Factory() {
      }
   }
}
