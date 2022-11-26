package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInteger;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface MultipartConfigType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MultipartConfigType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("multipartconfigtypecaf0type");

   String getLocation();

   boolean isSetLocation();

   void setLocation(String var1);

   String addNewLocation();

   void unsetLocation();

   long getMaxFileSize();

   XmlLong xgetMaxFileSize();

   boolean isSetMaxFileSize();

   void setMaxFileSize(long var1);

   void xsetMaxFileSize(XmlLong var1);

   void unsetMaxFileSize();

   long getMaxRequestSize();

   XmlLong xgetMaxRequestSize();

   boolean isSetMaxRequestSize();

   void setMaxRequestSize(long var1);

   void xsetMaxRequestSize(XmlLong var1);

   void unsetMaxRequestSize();

   BigInteger getFileSizeThreshold();

   XmlInteger xgetFileSizeThreshold();

   boolean isSetFileSizeThreshold();

   void setFileSizeThreshold(BigInteger var1);

   void xsetFileSizeThreshold(XmlInteger var1);

   void unsetFileSizeThreshold();

   public static final class Factory {
      public static MultipartConfigType newInstance() {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().newInstance(MultipartConfigType.type, (XmlOptions)null);
      }

      public static MultipartConfigType newInstance(XmlOptions options) {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().newInstance(MultipartConfigType.type, options);
      }

      public static MultipartConfigType parse(java.lang.String xmlAsString) throws XmlException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MultipartConfigType.type, (XmlOptions)null);
      }

      public static MultipartConfigType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MultipartConfigType.type, options);
      }

      public static MultipartConfigType parse(File file) throws XmlException, IOException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(file, MultipartConfigType.type, (XmlOptions)null);
      }

      public static MultipartConfigType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(file, MultipartConfigType.type, options);
      }

      public static MultipartConfigType parse(URL u) throws XmlException, IOException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(u, MultipartConfigType.type, (XmlOptions)null);
      }

      public static MultipartConfigType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(u, MultipartConfigType.type, options);
      }

      public static MultipartConfigType parse(InputStream is) throws XmlException, IOException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(is, MultipartConfigType.type, (XmlOptions)null);
      }

      public static MultipartConfigType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(is, MultipartConfigType.type, options);
      }

      public static MultipartConfigType parse(Reader r) throws XmlException, IOException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(r, MultipartConfigType.type, (XmlOptions)null);
      }

      public static MultipartConfigType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(r, MultipartConfigType.type, options);
      }

      public static MultipartConfigType parse(XMLStreamReader sr) throws XmlException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(sr, MultipartConfigType.type, (XmlOptions)null);
      }

      public static MultipartConfigType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(sr, MultipartConfigType.type, options);
      }

      public static MultipartConfigType parse(Node node) throws XmlException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(node, MultipartConfigType.type, (XmlOptions)null);
      }

      public static MultipartConfigType parse(Node node, XmlOptions options) throws XmlException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(node, MultipartConfigType.type, options);
      }

      /** @deprecated */
      public static MultipartConfigType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(xis, MultipartConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MultipartConfigType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MultipartConfigType)XmlBeans.getContextTypeLoader().parse(xis, MultipartConfigType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MultipartConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MultipartConfigType.type, options);
      }

      private Factory() {
      }
   }
}
