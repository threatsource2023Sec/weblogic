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

public interface ForeignKeyColumnType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ForeignKeyColumnType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("foreignkeycolumntype268ctype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ForeignKeyColumnType newInstance() {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().newInstance(ForeignKeyColumnType.type, (XmlOptions)null);
      }

      public static ForeignKeyColumnType newInstance(XmlOptions options) {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().newInstance(ForeignKeyColumnType.type, options);
      }

      public static ForeignKeyColumnType parse(String xmlAsString) throws XmlException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignKeyColumnType.type, (XmlOptions)null);
      }

      public static ForeignKeyColumnType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignKeyColumnType.type, options);
      }

      public static ForeignKeyColumnType parse(File file) throws XmlException, IOException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(file, ForeignKeyColumnType.type, (XmlOptions)null);
      }

      public static ForeignKeyColumnType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(file, ForeignKeyColumnType.type, options);
      }

      public static ForeignKeyColumnType parse(URL u) throws XmlException, IOException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(u, ForeignKeyColumnType.type, (XmlOptions)null);
      }

      public static ForeignKeyColumnType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(u, ForeignKeyColumnType.type, options);
      }

      public static ForeignKeyColumnType parse(InputStream is) throws XmlException, IOException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(is, ForeignKeyColumnType.type, (XmlOptions)null);
      }

      public static ForeignKeyColumnType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(is, ForeignKeyColumnType.type, options);
      }

      public static ForeignKeyColumnType parse(Reader r) throws XmlException, IOException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(r, ForeignKeyColumnType.type, (XmlOptions)null);
      }

      public static ForeignKeyColumnType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(r, ForeignKeyColumnType.type, options);
      }

      public static ForeignKeyColumnType parse(XMLStreamReader sr) throws XmlException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(sr, ForeignKeyColumnType.type, (XmlOptions)null);
      }

      public static ForeignKeyColumnType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(sr, ForeignKeyColumnType.type, options);
      }

      public static ForeignKeyColumnType parse(Node node) throws XmlException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(node, ForeignKeyColumnType.type, (XmlOptions)null);
      }

      public static ForeignKeyColumnType parse(Node node, XmlOptions options) throws XmlException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(node, ForeignKeyColumnType.type, options);
      }

      /** @deprecated */
      public static ForeignKeyColumnType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(xis, ForeignKeyColumnType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ForeignKeyColumnType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ForeignKeyColumnType)XmlBeans.getContextTypeLoader().parse(xis, ForeignKeyColumnType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignKeyColumnType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignKeyColumnType.type, options);
      }

      private Factory() {
      }
   }
}
