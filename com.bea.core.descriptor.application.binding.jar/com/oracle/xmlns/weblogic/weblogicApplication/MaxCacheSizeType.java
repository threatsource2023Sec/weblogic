package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlObject;
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

public interface MaxCacheSizeType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MaxCacheSizeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("maxcachesizetype70d0type");

   int getBytes();

   XmlInt xgetBytes();

   boolean isSetBytes();

   void setBytes(int var1);

   void xsetBytes(XmlInt var1);

   void unsetBytes();

   int getMegabytes();

   XmlInt xgetMegabytes();

   boolean isSetMegabytes();

   void setMegabytes(int var1);

   void xsetMegabytes(XmlInt var1);

   void unsetMegabytes();

   public static final class Factory {
      public static MaxCacheSizeType newInstance() {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().newInstance(MaxCacheSizeType.type, (XmlOptions)null);
      }

      public static MaxCacheSizeType newInstance(XmlOptions options) {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().newInstance(MaxCacheSizeType.type, options);
      }

      public static MaxCacheSizeType parse(String xmlAsString) throws XmlException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MaxCacheSizeType.type, (XmlOptions)null);
      }

      public static MaxCacheSizeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MaxCacheSizeType.type, options);
      }

      public static MaxCacheSizeType parse(File file) throws XmlException, IOException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(file, MaxCacheSizeType.type, (XmlOptions)null);
      }

      public static MaxCacheSizeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(file, MaxCacheSizeType.type, options);
      }

      public static MaxCacheSizeType parse(URL u) throws XmlException, IOException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(u, MaxCacheSizeType.type, (XmlOptions)null);
      }

      public static MaxCacheSizeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(u, MaxCacheSizeType.type, options);
      }

      public static MaxCacheSizeType parse(InputStream is) throws XmlException, IOException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(is, MaxCacheSizeType.type, (XmlOptions)null);
      }

      public static MaxCacheSizeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(is, MaxCacheSizeType.type, options);
      }

      public static MaxCacheSizeType parse(Reader r) throws XmlException, IOException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(r, MaxCacheSizeType.type, (XmlOptions)null);
      }

      public static MaxCacheSizeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(r, MaxCacheSizeType.type, options);
      }

      public static MaxCacheSizeType parse(XMLStreamReader sr) throws XmlException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(sr, MaxCacheSizeType.type, (XmlOptions)null);
      }

      public static MaxCacheSizeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(sr, MaxCacheSizeType.type, options);
      }

      public static MaxCacheSizeType parse(Node node) throws XmlException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(node, MaxCacheSizeType.type, (XmlOptions)null);
      }

      public static MaxCacheSizeType parse(Node node, XmlOptions options) throws XmlException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(node, MaxCacheSizeType.type, options);
      }

      /** @deprecated */
      public static MaxCacheSizeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(xis, MaxCacheSizeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MaxCacheSizeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MaxCacheSizeType)XmlBeans.getContextTypeLoader().parse(xis, MaxCacheSizeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MaxCacheSizeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MaxCacheSizeType.type, options);
      }

      private Factory() {
      }
   }
}
