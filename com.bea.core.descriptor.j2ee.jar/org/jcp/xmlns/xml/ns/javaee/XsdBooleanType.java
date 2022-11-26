package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface XsdBooleanType extends XmlBoolean {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XsdBooleanType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("xsdbooleantype0bcctype");

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static XsdBooleanType newInstance() {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().newInstance(XsdBooleanType.type, (XmlOptions)null);
      }

      public static XsdBooleanType newInstance(XmlOptions options) {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().newInstance(XsdBooleanType.type, options);
      }

      public static XsdBooleanType parse(java.lang.String xmlAsString) throws XmlException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdBooleanType.type, (XmlOptions)null);
      }

      public static XsdBooleanType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdBooleanType.type, options);
      }

      public static XsdBooleanType parse(File file) throws XmlException, IOException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(file, XsdBooleanType.type, (XmlOptions)null);
      }

      public static XsdBooleanType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(file, XsdBooleanType.type, options);
      }

      public static XsdBooleanType parse(URL u) throws XmlException, IOException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(u, XsdBooleanType.type, (XmlOptions)null);
      }

      public static XsdBooleanType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(u, XsdBooleanType.type, options);
      }

      public static XsdBooleanType parse(InputStream is) throws XmlException, IOException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(is, XsdBooleanType.type, (XmlOptions)null);
      }

      public static XsdBooleanType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(is, XsdBooleanType.type, options);
      }

      public static XsdBooleanType parse(Reader r) throws XmlException, IOException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(r, XsdBooleanType.type, (XmlOptions)null);
      }

      public static XsdBooleanType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(r, XsdBooleanType.type, options);
      }

      public static XsdBooleanType parse(XMLStreamReader sr) throws XmlException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(sr, XsdBooleanType.type, (XmlOptions)null);
      }

      public static XsdBooleanType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(sr, XsdBooleanType.type, options);
      }

      public static XsdBooleanType parse(Node node) throws XmlException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(node, XsdBooleanType.type, (XmlOptions)null);
      }

      public static XsdBooleanType parse(Node node, XmlOptions options) throws XmlException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(node, XsdBooleanType.type, options);
      }

      /** @deprecated */
      public static XsdBooleanType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(xis, XsdBooleanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XsdBooleanType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XsdBooleanType)XmlBeans.getContextTypeLoader().parse(xis, XsdBooleanType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdBooleanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdBooleanType.type, options);
      }

      private Factory() {
      }
   }
}
