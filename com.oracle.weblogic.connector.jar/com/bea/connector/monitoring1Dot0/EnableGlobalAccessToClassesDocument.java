package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
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

public interface EnableGlobalAccessToClassesDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EnableGlobalAccessToClassesDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("enableglobalaccesstoclasses6ee3doctype");

   boolean getEnableGlobalAccessToClasses();

   XmlBoolean xgetEnableGlobalAccessToClasses();

   void setEnableGlobalAccessToClasses(boolean var1);

   void xsetEnableGlobalAccessToClasses(XmlBoolean var1);

   public static final class Factory {
      public static EnableGlobalAccessToClassesDocument newInstance() {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().newInstance(EnableGlobalAccessToClassesDocument.type, (XmlOptions)null);
      }

      public static EnableGlobalAccessToClassesDocument newInstance(XmlOptions options) {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().newInstance(EnableGlobalAccessToClassesDocument.type, options);
      }

      public static EnableGlobalAccessToClassesDocument parse(String xmlAsString) throws XmlException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnableGlobalAccessToClassesDocument.type, (XmlOptions)null);
      }

      public static EnableGlobalAccessToClassesDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnableGlobalAccessToClassesDocument.type, options);
      }

      public static EnableGlobalAccessToClassesDocument parse(File file) throws XmlException, IOException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(file, EnableGlobalAccessToClassesDocument.type, (XmlOptions)null);
      }

      public static EnableGlobalAccessToClassesDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(file, EnableGlobalAccessToClassesDocument.type, options);
      }

      public static EnableGlobalAccessToClassesDocument parse(URL u) throws XmlException, IOException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(u, EnableGlobalAccessToClassesDocument.type, (XmlOptions)null);
      }

      public static EnableGlobalAccessToClassesDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(u, EnableGlobalAccessToClassesDocument.type, options);
      }

      public static EnableGlobalAccessToClassesDocument parse(InputStream is) throws XmlException, IOException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(is, EnableGlobalAccessToClassesDocument.type, (XmlOptions)null);
      }

      public static EnableGlobalAccessToClassesDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(is, EnableGlobalAccessToClassesDocument.type, options);
      }

      public static EnableGlobalAccessToClassesDocument parse(Reader r) throws XmlException, IOException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(r, EnableGlobalAccessToClassesDocument.type, (XmlOptions)null);
      }

      public static EnableGlobalAccessToClassesDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(r, EnableGlobalAccessToClassesDocument.type, options);
      }

      public static EnableGlobalAccessToClassesDocument parse(XMLStreamReader sr) throws XmlException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(sr, EnableGlobalAccessToClassesDocument.type, (XmlOptions)null);
      }

      public static EnableGlobalAccessToClassesDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(sr, EnableGlobalAccessToClassesDocument.type, options);
      }

      public static EnableGlobalAccessToClassesDocument parse(Node node) throws XmlException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(node, EnableGlobalAccessToClassesDocument.type, (XmlOptions)null);
      }

      public static EnableGlobalAccessToClassesDocument parse(Node node, XmlOptions options) throws XmlException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(node, EnableGlobalAccessToClassesDocument.type, options);
      }

      /** @deprecated */
      public static EnableGlobalAccessToClassesDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(xis, EnableGlobalAccessToClassesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EnableGlobalAccessToClassesDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EnableGlobalAccessToClassesDocument)XmlBeans.getContextTypeLoader().parse(xis, EnableGlobalAccessToClassesDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnableGlobalAccessToClassesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnableGlobalAccessToClassesDocument.type, options);
      }

      private Factory() {
      }
   }
}
