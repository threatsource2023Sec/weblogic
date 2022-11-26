package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface PrimaryKeyTableType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PrimaryKeyTableType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("primarykeytabletypecad2type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PrimaryKeyTableType newInstance() {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().newInstance(PrimaryKeyTableType.type, (XmlOptions)null);
      }

      public static PrimaryKeyTableType newInstance(XmlOptions options) {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().newInstance(PrimaryKeyTableType.type, options);
      }

      public static PrimaryKeyTableType parse(String xmlAsString) throws XmlException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PrimaryKeyTableType.type, (XmlOptions)null);
      }

      public static PrimaryKeyTableType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PrimaryKeyTableType.type, options);
      }

      public static PrimaryKeyTableType parse(File file) throws XmlException, IOException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(file, PrimaryKeyTableType.type, (XmlOptions)null);
      }

      public static PrimaryKeyTableType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(file, PrimaryKeyTableType.type, options);
      }

      public static PrimaryKeyTableType parse(URL u) throws XmlException, IOException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(u, PrimaryKeyTableType.type, (XmlOptions)null);
      }

      public static PrimaryKeyTableType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(u, PrimaryKeyTableType.type, options);
      }

      public static PrimaryKeyTableType parse(InputStream is) throws XmlException, IOException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(is, PrimaryKeyTableType.type, (XmlOptions)null);
      }

      public static PrimaryKeyTableType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(is, PrimaryKeyTableType.type, options);
      }

      public static PrimaryKeyTableType parse(Reader r) throws XmlException, IOException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(r, PrimaryKeyTableType.type, (XmlOptions)null);
      }

      public static PrimaryKeyTableType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(r, PrimaryKeyTableType.type, options);
      }

      public static PrimaryKeyTableType parse(XMLStreamReader sr) throws XmlException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(sr, PrimaryKeyTableType.type, (XmlOptions)null);
      }

      public static PrimaryKeyTableType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(sr, PrimaryKeyTableType.type, options);
      }

      public static PrimaryKeyTableType parse(Node node) throws XmlException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(node, PrimaryKeyTableType.type, (XmlOptions)null);
      }

      public static PrimaryKeyTableType parse(Node node, XmlOptions options) throws XmlException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(node, PrimaryKeyTableType.type, options);
      }

      /** @deprecated */
      public static PrimaryKeyTableType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(xis, PrimaryKeyTableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PrimaryKeyTableType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PrimaryKeyTableType)XmlBeans.getContextTypeLoader().parse(xis, PrimaryKeyTableType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PrimaryKeyTableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PrimaryKeyTableType.type, options);
      }

      private Factory() {
      }
   }
}
