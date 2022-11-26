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

public interface ForeignKeyTableType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ForeignKeyTableType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("foreignkeytabletype4124type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ForeignKeyTableType newInstance() {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().newInstance(ForeignKeyTableType.type, (XmlOptions)null);
      }

      public static ForeignKeyTableType newInstance(XmlOptions options) {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().newInstance(ForeignKeyTableType.type, options);
      }

      public static ForeignKeyTableType parse(String xmlAsString) throws XmlException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignKeyTableType.type, (XmlOptions)null);
      }

      public static ForeignKeyTableType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignKeyTableType.type, options);
      }

      public static ForeignKeyTableType parse(File file) throws XmlException, IOException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(file, ForeignKeyTableType.type, (XmlOptions)null);
      }

      public static ForeignKeyTableType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(file, ForeignKeyTableType.type, options);
      }

      public static ForeignKeyTableType parse(URL u) throws XmlException, IOException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(u, ForeignKeyTableType.type, (XmlOptions)null);
      }

      public static ForeignKeyTableType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(u, ForeignKeyTableType.type, options);
      }

      public static ForeignKeyTableType parse(InputStream is) throws XmlException, IOException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(is, ForeignKeyTableType.type, (XmlOptions)null);
      }

      public static ForeignKeyTableType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(is, ForeignKeyTableType.type, options);
      }

      public static ForeignKeyTableType parse(Reader r) throws XmlException, IOException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(r, ForeignKeyTableType.type, (XmlOptions)null);
      }

      public static ForeignKeyTableType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(r, ForeignKeyTableType.type, options);
      }

      public static ForeignKeyTableType parse(XMLStreamReader sr) throws XmlException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(sr, ForeignKeyTableType.type, (XmlOptions)null);
      }

      public static ForeignKeyTableType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(sr, ForeignKeyTableType.type, options);
      }

      public static ForeignKeyTableType parse(Node node) throws XmlException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(node, ForeignKeyTableType.type, (XmlOptions)null);
      }

      public static ForeignKeyTableType parse(Node node, XmlOptions options) throws XmlException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(node, ForeignKeyTableType.type, options);
      }

      /** @deprecated */
      public static ForeignKeyTableType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(xis, ForeignKeyTableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ForeignKeyTableType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ForeignKeyTableType)XmlBeans.getContextTypeLoader().parse(xis, ForeignKeyTableType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignKeyTableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignKeyTableType.type, options);
      }

      private Factory() {
      }
   }
}
