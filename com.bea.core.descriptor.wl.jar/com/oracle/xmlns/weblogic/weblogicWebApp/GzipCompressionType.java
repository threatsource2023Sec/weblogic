package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlObject;
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

public interface GzipCompressionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GzipCompressionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("gzipcompressiontype04e6type");

   TrueFalseType getEnabled();

   boolean isSetEnabled();

   void setEnabled(TrueFalseType var1);

   TrueFalseType addNewEnabled();

   void unsetEnabled();

   long getMinContentLength();

   XmlLong xgetMinContentLength();

   boolean isSetMinContentLength();

   void setMinContentLength(long var1);

   void xsetMinContentLength(XmlLong var1);

   void unsetMinContentLength();

   String[] getContentTypeArray();

   String getContentTypeArray(int var1);

   XmlString[] xgetContentTypeArray();

   XmlString xgetContentTypeArray(int var1);

   int sizeOfContentTypeArray();

   void setContentTypeArray(String[] var1);

   void setContentTypeArray(int var1, String var2);

   void xsetContentTypeArray(XmlString[] var1);

   void xsetContentTypeArray(int var1, XmlString var2);

   void insertContentType(int var1, String var2);

   void addContentType(String var1);

   XmlString insertNewContentType(int var1);

   XmlString addNewContentType();

   void removeContentType(int var1);

   public static final class Factory {
      public static GzipCompressionType newInstance() {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().newInstance(GzipCompressionType.type, (XmlOptions)null);
      }

      public static GzipCompressionType newInstance(XmlOptions options) {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().newInstance(GzipCompressionType.type, options);
      }

      public static GzipCompressionType parse(String xmlAsString) throws XmlException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GzipCompressionType.type, (XmlOptions)null);
      }

      public static GzipCompressionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GzipCompressionType.type, options);
      }

      public static GzipCompressionType parse(File file) throws XmlException, IOException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(file, GzipCompressionType.type, (XmlOptions)null);
      }

      public static GzipCompressionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(file, GzipCompressionType.type, options);
      }

      public static GzipCompressionType parse(URL u) throws XmlException, IOException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(u, GzipCompressionType.type, (XmlOptions)null);
      }

      public static GzipCompressionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(u, GzipCompressionType.type, options);
      }

      public static GzipCompressionType parse(InputStream is) throws XmlException, IOException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(is, GzipCompressionType.type, (XmlOptions)null);
      }

      public static GzipCompressionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(is, GzipCompressionType.type, options);
      }

      public static GzipCompressionType parse(Reader r) throws XmlException, IOException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(r, GzipCompressionType.type, (XmlOptions)null);
      }

      public static GzipCompressionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(r, GzipCompressionType.type, options);
      }

      public static GzipCompressionType parse(XMLStreamReader sr) throws XmlException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(sr, GzipCompressionType.type, (XmlOptions)null);
      }

      public static GzipCompressionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(sr, GzipCompressionType.type, options);
      }

      public static GzipCompressionType parse(Node node) throws XmlException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(node, GzipCompressionType.type, (XmlOptions)null);
      }

      public static GzipCompressionType parse(Node node, XmlOptions options) throws XmlException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(node, GzipCompressionType.type, options);
      }

      /** @deprecated */
      public static GzipCompressionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(xis, GzipCompressionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GzipCompressionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GzipCompressionType)XmlBeans.getContextTypeLoader().parse(xis, GzipCompressionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GzipCompressionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GzipCompressionType.type, options);
      }

      private Factory() {
      }
   }
}
