package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface EncodingType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EncodingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("encodingtype3cd8type");

   public static final class Factory {
      public static EncodingType newValue(Object obj) {
         return (EncodingType)EncodingType.type.newValue(obj);
      }

      public static EncodingType newInstance() {
         return (EncodingType)XmlBeans.getContextTypeLoader().newInstance(EncodingType.type, (XmlOptions)null);
      }

      public static EncodingType newInstance(XmlOptions options) {
         return (EncodingType)XmlBeans.getContextTypeLoader().newInstance(EncodingType.type, options);
      }

      public static EncodingType parse(java.lang.String xmlAsString) throws XmlException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EncodingType.type, (XmlOptions)null);
      }

      public static EncodingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EncodingType.type, options);
      }

      public static EncodingType parse(File file) throws XmlException, IOException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(file, EncodingType.type, (XmlOptions)null);
      }

      public static EncodingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(file, EncodingType.type, options);
      }

      public static EncodingType parse(URL u) throws XmlException, IOException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(u, EncodingType.type, (XmlOptions)null);
      }

      public static EncodingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(u, EncodingType.type, options);
      }

      public static EncodingType parse(InputStream is) throws XmlException, IOException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(is, EncodingType.type, (XmlOptions)null);
      }

      public static EncodingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(is, EncodingType.type, options);
      }

      public static EncodingType parse(Reader r) throws XmlException, IOException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(r, EncodingType.type, (XmlOptions)null);
      }

      public static EncodingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(r, EncodingType.type, options);
      }

      public static EncodingType parse(XMLStreamReader sr) throws XmlException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(sr, EncodingType.type, (XmlOptions)null);
      }

      public static EncodingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(sr, EncodingType.type, options);
      }

      public static EncodingType parse(Node node) throws XmlException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(node, EncodingType.type, (XmlOptions)null);
      }

      public static EncodingType parse(Node node, XmlOptions options) throws XmlException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(node, EncodingType.type, options);
      }

      /** @deprecated */
      public static EncodingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(xis, EncodingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EncodingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EncodingType)XmlBeans.getContextTypeLoader().parse(xis, EncodingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EncodingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EncodingType.type, options);
      }

      private Factory() {
      }
   }
}
