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

public interface ModuleNameDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ModuleNameDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("modulenameb4b4doctype");

   String getModuleName();

   XmlString xgetModuleName();

   void setModuleName(String var1);

   void xsetModuleName(XmlString var1);

   public static final class Factory {
      public static ModuleNameDocument newInstance() {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().newInstance(ModuleNameDocument.type, (XmlOptions)null);
      }

      public static ModuleNameDocument newInstance(XmlOptions options) {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().newInstance(ModuleNameDocument.type, options);
      }

      public static ModuleNameDocument parse(String xmlAsString) throws XmlException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleNameDocument.type, (XmlOptions)null);
      }

      public static ModuleNameDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleNameDocument.type, options);
      }

      public static ModuleNameDocument parse(File file) throws XmlException, IOException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(file, ModuleNameDocument.type, (XmlOptions)null);
      }

      public static ModuleNameDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(file, ModuleNameDocument.type, options);
      }

      public static ModuleNameDocument parse(URL u) throws XmlException, IOException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(u, ModuleNameDocument.type, (XmlOptions)null);
      }

      public static ModuleNameDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(u, ModuleNameDocument.type, options);
      }

      public static ModuleNameDocument parse(InputStream is) throws XmlException, IOException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(is, ModuleNameDocument.type, (XmlOptions)null);
      }

      public static ModuleNameDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(is, ModuleNameDocument.type, options);
      }

      public static ModuleNameDocument parse(Reader r) throws XmlException, IOException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(r, ModuleNameDocument.type, (XmlOptions)null);
      }

      public static ModuleNameDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(r, ModuleNameDocument.type, options);
      }

      public static ModuleNameDocument parse(XMLStreamReader sr) throws XmlException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(sr, ModuleNameDocument.type, (XmlOptions)null);
      }

      public static ModuleNameDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(sr, ModuleNameDocument.type, options);
      }

      public static ModuleNameDocument parse(Node node) throws XmlException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(node, ModuleNameDocument.type, (XmlOptions)null);
      }

      public static ModuleNameDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(node, ModuleNameDocument.type, options);
      }

      /** @deprecated */
      public static ModuleNameDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(xis, ModuleNameDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ModuleNameDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ModuleNameDocument)XmlBeans.getContextTypeLoader().parse(xis, ModuleNameDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleNameDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleNameDocument.type, options);
      }

      private Factory() {
      }
   }
}
