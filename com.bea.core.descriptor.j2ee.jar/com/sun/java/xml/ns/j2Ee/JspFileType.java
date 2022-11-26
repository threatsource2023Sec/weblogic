package com.sun.java.xml.ns.j2Ee;

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

public interface JspFileType extends PathType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JspFileType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("jspfiletype1515type");

   public static final class Factory {
      public static JspFileType newInstance() {
         return (JspFileType)XmlBeans.getContextTypeLoader().newInstance(JspFileType.type, (XmlOptions)null);
      }

      public static JspFileType newInstance(XmlOptions options) {
         return (JspFileType)XmlBeans.getContextTypeLoader().newInstance(JspFileType.type, options);
      }

      public static JspFileType parse(java.lang.String xmlAsString) throws XmlException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JspFileType.type, (XmlOptions)null);
      }

      public static JspFileType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JspFileType.type, options);
      }

      public static JspFileType parse(File file) throws XmlException, IOException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(file, JspFileType.type, (XmlOptions)null);
      }

      public static JspFileType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(file, JspFileType.type, options);
      }

      public static JspFileType parse(URL u) throws XmlException, IOException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(u, JspFileType.type, (XmlOptions)null);
      }

      public static JspFileType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(u, JspFileType.type, options);
      }

      public static JspFileType parse(InputStream is) throws XmlException, IOException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(is, JspFileType.type, (XmlOptions)null);
      }

      public static JspFileType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(is, JspFileType.type, options);
      }

      public static JspFileType parse(Reader r) throws XmlException, IOException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(r, JspFileType.type, (XmlOptions)null);
      }

      public static JspFileType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(r, JspFileType.type, options);
      }

      public static JspFileType parse(XMLStreamReader sr) throws XmlException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(sr, JspFileType.type, (XmlOptions)null);
      }

      public static JspFileType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(sr, JspFileType.type, options);
      }

      public static JspFileType parse(Node node) throws XmlException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(node, JspFileType.type, (XmlOptions)null);
      }

      public static JspFileType parse(Node node, XmlOptions options) throws XmlException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(node, JspFileType.type, options);
      }

      /** @deprecated */
      public static JspFileType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(xis, JspFileType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JspFileType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JspFileType)XmlBeans.getContextTypeLoader().parse(xis, JspFileType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JspFileType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JspFileType.type, options);
      }

      private Factory() {
      }
   }
}
