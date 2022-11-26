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

public interface DescriptionDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DescriptionDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("descriptione804doctype");

   String getDescription();

   XmlString xgetDescription();

   void setDescription(String var1);

   void xsetDescription(XmlString var1);

   public static final class Factory {
      public static DescriptionDocument newInstance() {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().newInstance(DescriptionDocument.type, (XmlOptions)null);
      }

      public static DescriptionDocument newInstance(XmlOptions options) {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().newInstance(DescriptionDocument.type, options);
      }

      public static DescriptionDocument parse(String xmlAsString) throws XmlException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DescriptionDocument.type, (XmlOptions)null);
      }

      public static DescriptionDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DescriptionDocument.type, options);
      }

      public static DescriptionDocument parse(File file) throws XmlException, IOException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(file, DescriptionDocument.type, (XmlOptions)null);
      }

      public static DescriptionDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(file, DescriptionDocument.type, options);
      }

      public static DescriptionDocument parse(URL u) throws XmlException, IOException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(u, DescriptionDocument.type, (XmlOptions)null);
      }

      public static DescriptionDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(u, DescriptionDocument.type, options);
      }

      public static DescriptionDocument parse(InputStream is) throws XmlException, IOException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(is, DescriptionDocument.type, (XmlOptions)null);
      }

      public static DescriptionDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(is, DescriptionDocument.type, options);
      }

      public static DescriptionDocument parse(Reader r) throws XmlException, IOException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(r, DescriptionDocument.type, (XmlOptions)null);
      }

      public static DescriptionDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(r, DescriptionDocument.type, options);
      }

      public static DescriptionDocument parse(XMLStreamReader sr) throws XmlException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(sr, DescriptionDocument.type, (XmlOptions)null);
      }

      public static DescriptionDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(sr, DescriptionDocument.type, options);
      }

      public static DescriptionDocument parse(Node node) throws XmlException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(node, DescriptionDocument.type, (XmlOptions)null);
      }

      public static DescriptionDocument parse(Node node, XmlOptions options) throws XmlException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(node, DescriptionDocument.type, options);
      }

      /** @deprecated */
      public static DescriptionDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(xis, DescriptionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DescriptionDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DescriptionDocument)XmlBeans.getContextTypeLoader().parse(xis, DescriptionDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DescriptionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DescriptionDocument.type, options);
      }

      private Factory() {
      }
   }
}
