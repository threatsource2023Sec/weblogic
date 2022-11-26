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

public interface ErrorCodeType extends XsdPositiveIntegerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ErrorCodeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("errorcodetype4cf9type");

   public static final class Factory {
      public static ErrorCodeType newInstance() {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().newInstance(ErrorCodeType.type, (XmlOptions)null);
      }

      public static ErrorCodeType newInstance(XmlOptions options) {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().newInstance(ErrorCodeType.type, options);
      }

      public static ErrorCodeType parse(java.lang.String xmlAsString) throws XmlException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ErrorCodeType.type, (XmlOptions)null);
      }

      public static ErrorCodeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ErrorCodeType.type, options);
      }

      public static ErrorCodeType parse(File file) throws XmlException, IOException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(file, ErrorCodeType.type, (XmlOptions)null);
      }

      public static ErrorCodeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(file, ErrorCodeType.type, options);
      }

      public static ErrorCodeType parse(URL u) throws XmlException, IOException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(u, ErrorCodeType.type, (XmlOptions)null);
      }

      public static ErrorCodeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(u, ErrorCodeType.type, options);
      }

      public static ErrorCodeType parse(InputStream is) throws XmlException, IOException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(is, ErrorCodeType.type, (XmlOptions)null);
      }

      public static ErrorCodeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(is, ErrorCodeType.type, options);
      }

      public static ErrorCodeType parse(Reader r) throws XmlException, IOException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(r, ErrorCodeType.type, (XmlOptions)null);
      }

      public static ErrorCodeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(r, ErrorCodeType.type, options);
      }

      public static ErrorCodeType parse(XMLStreamReader sr) throws XmlException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(sr, ErrorCodeType.type, (XmlOptions)null);
      }

      public static ErrorCodeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(sr, ErrorCodeType.type, options);
      }

      public static ErrorCodeType parse(Node node) throws XmlException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(node, ErrorCodeType.type, (XmlOptions)null);
      }

      public static ErrorCodeType parse(Node node, XmlOptions options) throws XmlException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(node, ErrorCodeType.type, options);
      }

      /** @deprecated */
      public static ErrorCodeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(xis, ErrorCodeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ErrorCodeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ErrorCodeType)XmlBeans.getContextTypeLoader().parse(xis, ErrorCodeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ErrorCodeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ErrorCodeType.type, options);
      }

      private Factory() {
      }
   }
}
