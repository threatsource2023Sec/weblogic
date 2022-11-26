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

public interface OptimisticColumnType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OptimisticColumnType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("optimisticcolumntypee5b3type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static OptimisticColumnType newInstance() {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().newInstance(OptimisticColumnType.type, (XmlOptions)null);
      }

      public static OptimisticColumnType newInstance(XmlOptions options) {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().newInstance(OptimisticColumnType.type, options);
      }

      public static OptimisticColumnType parse(String xmlAsString) throws XmlException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OptimisticColumnType.type, (XmlOptions)null);
      }

      public static OptimisticColumnType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OptimisticColumnType.type, options);
      }

      public static OptimisticColumnType parse(File file) throws XmlException, IOException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(file, OptimisticColumnType.type, (XmlOptions)null);
      }

      public static OptimisticColumnType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(file, OptimisticColumnType.type, options);
      }

      public static OptimisticColumnType parse(URL u) throws XmlException, IOException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(u, OptimisticColumnType.type, (XmlOptions)null);
      }

      public static OptimisticColumnType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(u, OptimisticColumnType.type, options);
      }

      public static OptimisticColumnType parse(InputStream is) throws XmlException, IOException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(is, OptimisticColumnType.type, (XmlOptions)null);
      }

      public static OptimisticColumnType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(is, OptimisticColumnType.type, options);
      }

      public static OptimisticColumnType parse(Reader r) throws XmlException, IOException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(r, OptimisticColumnType.type, (XmlOptions)null);
      }

      public static OptimisticColumnType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(r, OptimisticColumnType.type, options);
      }

      public static OptimisticColumnType parse(XMLStreamReader sr) throws XmlException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(sr, OptimisticColumnType.type, (XmlOptions)null);
      }

      public static OptimisticColumnType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(sr, OptimisticColumnType.type, options);
      }

      public static OptimisticColumnType parse(Node node) throws XmlException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(node, OptimisticColumnType.type, (XmlOptions)null);
      }

      public static OptimisticColumnType parse(Node node, XmlOptions options) throws XmlException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(node, OptimisticColumnType.type, options);
      }

      /** @deprecated */
      public static OptimisticColumnType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(xis, OptimisticColumnType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OptimisticColumnType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OptimisticColumnType)XmlBeans.getContextTypeLoader().parse(xis, OptimisticColumnType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OptimisticColumnType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OptimisticColumnType.type, options);
      }

      private Factory() {
      }
   }
}
