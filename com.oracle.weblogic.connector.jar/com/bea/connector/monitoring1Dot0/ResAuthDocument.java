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

public interface ResAuthDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResAuthDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("resauth6199doctype");

   String getResAuth();

   XmlString xgetResAuth();

   void setResAuth(String var1);

   void xsetResAuth(XmlString var1);

   public static final class Factory {
      public static ResAuthDocument newInstance() {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().newInstance(ResAuthDocument.type, (XmlOptions)null);
      }

      public static ResAuthDocument newInstance(XmlOptions options) {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().newInstance(ResAuthDocument.type, options);
      }

      public static ResAuthDocument parse(String xmlAsString) throws XmlException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResAuthDocument.type, (XmlOptions)null);
      }

      public static ResAuthDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResAuthDocument.type, options);
      }

      public static ResAuthDocument parse(File file) throws XmlException, IOException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(file, ResAuthDocument.type, (XmlOptions)null);
      }

      public static ResAuthDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(file, ResAuthDocument.type, options);
      }

      public static ResAuthDocument parse(URL u) throws XmlException, IOException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(u, ResAuthDocument.type, (XmlOptions)null);
      }

      public static ResAuthDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(u, ResAuthDocument.type, options);
      }

      public static ResAuthDocument parse(InputStream is) throws XmlException, IOException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(is, ResAuthDocument.type, (XmlOptions)null);
      }

      public static ResAuthDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(is, ResAuthDocument.type, options);
      }

      public static ResAuthDocument parse(Reader r) throws XmlException, IOException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(r, ResAuthDocument.type, (XmlOptions)null);
      }

      public static ResAuthDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(r, ResAuthDocument.type, options);
      }

      public static ResAuthDocument parse(XMLStreamReader sr) throws XmlException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(sr, ResAuthDocument.type, (XmlOptions)null);
      }

      public static ResAuthDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(sr, ResAuthDocument.type, options);
      }

      public static ResAuthDocument parse(Node node) throws XmlException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(node, ResAuthDocument.type, (XmlOptions)null);
      }

      public static ResAuthDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(node, ResAuthDocument.type, options);
      }

      /** @deprecated */
      public static ResAuthDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(xis, ResAuthDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResAuthDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResAuthDocument)XmlBeans.getContextTypeLoader().parse(xis, ResAuthDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResAuthDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResAuthDocument.type, options);
      }

      private Factory() {
      }
   }
}
