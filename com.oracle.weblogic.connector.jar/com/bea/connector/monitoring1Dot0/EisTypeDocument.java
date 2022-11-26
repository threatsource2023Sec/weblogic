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

public interface EisTypeDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EisTypeDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("eistype7b36doctype");

   String getEisType();

   XmlString xgetEisType();

   void setEisType(String var1);

   void xsetEisType(XmlString var1);

   public static final class Factory {
      public static EisTypeDocument newInstance() {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().newInstance(EisTypeDocument.type, (XmlOptions)null);
      }

      public static EisTypeDocument newInstance(XmlOptions options) {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().newInstance(EisTypeDocument.type, options);
      }

      public static EisTypeDocument parse(String xmlAsString) throws XmlException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, EisTypeDocument.type, (XmlOptions)null);
      }

      public static EisTypeDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, EisTypeDocument.type, options);
      }

      public static EisTypeDocument parse(File file) throws XmlException, IOException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(file, EisTypeDocument.type, (XmlOptions)null);
      }

      public static EisTypeDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(file, EisTypeDocument.type, options);
      }

      public static EisTypeDocument parse(URL u) throws XmlException, IOException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(u, EisTypeDocument.type, (XmlOptions)null);
      }

      public static EisTypeDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(u, EisTypeDocument.type, options);
      }

      public static EisTypeDocument parse(InputStream is) throws XmlException, IOException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(is, EisTypeDocument.type, (XmlOptions)null);
      }

      public static EisTypeDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(is, EisTypeDocument.type, options);
      }

      public static EisTypeDocument parse(Reader r) throws XmlException, IOException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(r, EisTypeDocument.type, (XmlOptions)null);
      }

      public static EisTypeDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(r, EisTypeDocument.type, options);
      }

      public static EisTypeDocument parse(XMLStreamReader sr) throws XmlException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(sr, EisTypeDocument.type, (XmlOptions)null);
      }

      public static EisTypeDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(sr, EisTypeDocument.type, options);
      }

      public static EisTypeDocument parse(Node node) throws XmlException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(node, EisTypeDocument.type, (XmlOptions)null);
      }

      public static EisTypeDocument parse(Node node, XmlOptions options) throws XmlException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(node, EisTypeDocument.type, options);
      }

      /** @deprecated */
      public static EisTypeDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(xis, EisTypeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EisTypeDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EisTypeDocument)XmlBeans.getContextTypeLoader().parse(xis, EisTypeDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EisTypeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EisTypeDocument.type, options);
      }

      private Factory() {
      }
   }
}
