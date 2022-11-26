package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlNonNegativeInteger;
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

public interface XsdNonNegativeIntegerType extends XmlNonNegativeInteger {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XsdNonNegativeIntegerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("xsdnonnegativeintegertype80f8type");

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static XsdNonNegativeIntegerType newInstance() {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().newInstance(XsdNonNegativeIntegerType.type, (XmlOptions)null);
      }

      public static XsdNonNegativeIntegerType newInstance(XmlOptions options) {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().newInstance(XsdNonNegativeIntegerType.type, options);
      }

      public static XsdNonNegativeIntegerType parse(java.lang.String xmlAsString) throws XmlException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdNonNegativeIntegerType.type, (XmlOptions)null);
      }

      public static XsdNonNegativeIntegerType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdNonNegativeIntegerType.type, options);
      }

      public static XsdNonNegativeIntegerType parse(File file) throws XmlException, IOException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(file, XsdNonNegativeIntegerType.type, (XmlOptions)null);
      }

      public static XsdNonNegativeIntegerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(file, XsdNonNegativeIntegerType.type, options);
      }

      public static XsdNonNegativeIntegerType parse(URL u) throws XmlException, IOException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(u, XsdNonNegativeIntegerType.type, (XmlOptions)null);
      }

      public static XsdNonNegativeIntegerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(u, XsdNonNegativeIntegerType.type, options);
      }

      public static XsdNonNegativeIntegerType parse(InputStream is) throws XmlException, IOException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(is, XsdNonNegativeIntegerType.type, (XmlOptions)null);
      }

      public static XsdNonNegativeIntegerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(is, XsdNonNegativeIntegerType.type, options);
      }

      public static XsdNonNegativeIntegerType parse(Reader r) throws XmlException, IOException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(r, XsdNonNegativeIntegerType.type, (XmlOptions)null);
      }

      public static XsdNonNegativeIntegerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(r, XsdNonNegativeIntegerType.type, options);
      }

      public static XsdNonNegativeIntegerType parse(XMLStreamReader sr) throws XmlException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(sr, XsdNonNegativeIntegerType.type, (XmlOptions)null);
      }

      public static XsdNonNegativeIntegerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(sr, XsdNonNegativeIntegerType.type, options);
      }

      public static XsdNonNegativeIntegerType parse(Node node) throws XmlException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(node, XsdNonNegativeIntegerType.type, (XmlOptions)null);
      }

      public static XsdNonNegativeIntegerType parse(Node node, XmlOptions options) throws XmlException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(node, XsdNonNegativeIntegerType.type, options);
      }

      /** @deprecated */
      public static XsdNonNegativeIntegerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(xis, XsdNonNegativeIntegerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XsdNonNegativeIntegerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XsdNonNegativeIntegerType)XmlBeans.getContextTypeLoader().parse(xis, XsdNonNegativeIntegerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdNonNegativeIntegerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdNonNegativeIntegerType.type, options);
      }

      private Factory() {
      }
   }
}
