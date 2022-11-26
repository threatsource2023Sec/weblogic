package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface String extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(String.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("string46b4type");

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static String newInstance() {
         return (String)XmlBeans.getContextTypeLoader().newInstance(String.type, (XmlOptions)null);
      }

      public static String newInstance(XmlOptions options) {
         return (String)XmlBeans.getContextTypeLoader().newInstance(String.type, options);
      }

      public static String parse(java.lang.String xmlAsString) throws XmlException {
         return (String)XmlBeans.getContextTypeLoader().parse(xmlAsString, String.type, (XmlOptions)null);
      }

      public static String parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (String)XmlBeans.getContextTypeLoader().parse(xmlAsString, String.type, options);
      }

      public static String parse(File file) throws XmlException, IOException {
         return (String)XmlBeans.getContextTypeLoader().parse(file, String.type, (XmlOptions)null);
      }

      public static String parse(File file, XmlOptions options) throws XmlException, IOException {
         return (String)XmlBeans.getContextTypeLoader().parse(file, String.type, options);
      }

      public static String parse(URL u) throws XmlException, IOException {
         return (String)XmlBeans.getContextTypeLoader().parse(u, String.type, (XmlOptions)null);
      }

      public static String parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (String)XmlBeans.getContextTypeLoader().parse(u, String.type, options);
      }

      public static String parse(InputStream is) throws XmlException, IOException {
         return (String)XmlBeans.getContextTypeLoader().parse(is, String.type, (XmlOptions)null);
      }

      public static String parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (String)XmlBeans.getContextTypeLoader().parse(is, String.type, options);
      }

      public static String parse(Reader r) throws XmlException, IOException {
         return (String)XmlBeans.getContextTypeLoader().parse(r, String.type, (XmlOptions)null);
      }

      public static String parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (String)XmlBeans.getContextTypeLoader().parse(r, String.type, options);
      }

      public static String parse(XMLStreamReader sr) throws XmlException {
         return (String)XmlBeans.getContextTypeLoader().parse(sr, String.type, (XmlOptions)null);
      }

      public static String parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (String)XmlBeans.getContextTypeLoader().parse(sr, String.type, options);
      }

      public static String parse(Node node) throws XmlException {
         return (String)XmlBeans.getContextTypeLoader().parse(node, String.type, (XmlOptions)null);
      }

      public static String parse(Node node, XmlOptions options) throws XmlException {
         return (String)XmlBeans.getContextTypeLoader().parse(node, String.type, options);
      }

      /** @deprecated */
      public static String parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (String)XmlBeans.getContextTypeLoader().parse(xis, String.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static String parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (String)XmlBeans.getContextTypeLoader().parse(xis, String.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, String.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, String.type, options);
      }

      private Factory() {
      }
   }
}
