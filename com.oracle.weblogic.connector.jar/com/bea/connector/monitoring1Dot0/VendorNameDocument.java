package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface VendorNameDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(VendorNameDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("vendornamec490doctype");

   String getVendorName();

   XmlString xgetVendorName();

   void setVendorName(String var1);

   void xsetVendorName(XmlString var1);

   public static final class Factory {
      public static VendorNameDocument newInstance() {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().newInstance(VendorNameDocument.type, (XmlOptions)null);
      }

      public static VendorNameDocument newInstance(XmlOptions options) {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().newInstance(VendorNameDocument.type, options);
      }

      public static VendorNameDocument parse(String xmlAsString) throws XmlException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, VendorNameDocument.type, (XmlOptions)null);
      }

      public static VendorNameDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, VendorNameDocument.type, options);
      }

      public static VendorNameDocument parse(File file) throws XmlException, IOException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(file, VendorNameDocument.type, (XmlOptions)null);
      }

      public static VendorNameDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(file, VendorNameDocument.type, options);
      }

      public static VendorNameDocument parse(URL u) throws XmlException, IOException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(u, VendorNameDocument.type, (XmlOptions)null);
      }

      public static VendorNameDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(u, VendorNameDocument.type, options);
      }

      public static VendorNameDocument parse(InputStream is) throws XmlException, IOException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(is, VendorNameDocument.type, (XmlOptions)null);
      }

      public static VendorNameDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(is, VendorNameDocument.type, options);
      }

      public static VendorNameDocument parse(Reader r) throws XmlException, IOException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(r, VendorNameDocument.type, (XmlOptions)null);
      }

      public static VendorNameDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(r, VendorNameDocument.type, options);
      }

      public static VendorNameDocument parse(XMLStreamReader sr) throws XmlException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(sr, VendorNameDocument.type, (XmlOptions)null);
      }

      public static VendorNameDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(sr, VendorNameDocument.type, options);
      }

      public static VendorNameDocument parse(Node node) throws XmlException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(node, VendorNameDocument.type, (XmlOptions)null);
      }

      public static VendorNameDocument parse(Node node, XmlOptions options) throws XmlException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(node, VendorNameDocument.type, options);
      }

      /** @deprecated */
      public static VendorNameDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(xis, VendorNameDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static VendorNameDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (VendorNameDocument)XmlBeans.getContextTypeLoader().parse(xis, VendorNameDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VendorNameDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VendorNameDocument.type, options);
      }

      private Factory() {
      }
   }
}
