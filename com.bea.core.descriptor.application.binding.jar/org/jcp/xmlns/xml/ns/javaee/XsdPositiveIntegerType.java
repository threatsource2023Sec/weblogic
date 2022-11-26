package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlPositiveInteger;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface XsdPositiveIntegerType extends XmlPositiveInteger {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XsdPositiveIntegerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("xsdpositiveintegertype844ftype");

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static XsdPositiveIntegerType newInstance() {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().newInstance(XsdPositiveIntegerType.type, (XmlOptions)null);
      }

      public static XsdPositiveIntegerType newInstance(XmlOptions options) {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().newInstance(XsdPositiveIntegerType.type, options);
      }

      public static XsdPositiveIntegerType parse(java.lang.String xmlAsString) throws XmlException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdPositiveIntegerType.type, (XmlOptions)null);
      }

      public static XsdPositiveIntegerType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdPositiveIntegerType.type, options);
      }

      public static XsdPositiveIntegerType parse(File file) throws XmlException, IOException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(file, XsdPositiveIntegerType.type, (XmlOptions)null);
      }

      public static XsdPositiveIntegerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(file, XsdPositiveIntegerType.type, options);
      }

      public static XsdPositiveIntegerType parse(URL u) throws XmlException, IOException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(u, XsdPositiveIntegerType.type, (XmlOptions)null);
      }

      public static XsdPositiveIntegerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(u, XsdPositiveIntegerType.type, options);
      }

      public static XsdPositiveIntegerType parse(InputStream is) throws XmlException, IOException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(is, XsdPositiveIntegerType.type, (XmlOptions)null);
      }

      public static XsdPositiveIntegerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(is, XsdPositiveIntegerType.type, options);
      }

      public static XsdPositiveIntegerType parse(Reader r) throws XmlException, IOException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(r, XsdPositiveIntegerType.type, (XmlOptions)null);
      }

      public static XsdPositiveIntegerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(r, XsdPositiveIntegerType.type, options);
      }

      public static XsdPositiveIntegerType parse(XMLStreamReader sr) throws XmlException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(sr, XsdPositiveIntegerType.type, (XmlOptions)null);
      }

      public static XsdPositiveIntegerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(sr, XsdPositiveIntegerType.type, options);
      }

      public static XsdPositiveIntegerType parse(Node node) throws XmlException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(node, XsdPositiveIntegerType.type, (XmlOptions)null);
      }

      public static XsdPositiveIntegerType parse(Node node, XmlOptions options) throws XmlException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(node, XsdPositiveIntegerType.type, options);
      }

      /** @deprecated */
      public static XsdPositiveIntegerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(xis, XsdPositiveIntegerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XsdPositiveIntegerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XsdPositiveIntegerType)XmlBeans.getContextTypeLoader().parse(xis, XsdPositiveIntegerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdPositiveIntegerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdPositiveIntegerType.type, options);
      }

      private Factory() {
      }
   }
}
