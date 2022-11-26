package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface ModuleRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ModuleRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("modulereftypea14dtype");

   String getModuleUri();

   XmlString xgetModuleUri();

   void setModuleUri(String var1);

   void xsetModuleUri(XmlString var1);

   public static final class Factory {
      public static ModuleRefType newInstance() {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().newInstance(ModuleRefType.type, (XmlOptions)null);
      }

      public static ModuleRefType newInstance(XmlOptions options) {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().newInstance(ModuleRefType.type, options);
      }

      public static ModuleRefType parse(String xmlAsString) throws XmlException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleRefType.type, (XmlOptions)null);
      }

      public static ModuleRefType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleRefType.type, options);
      }

      public static ModuleRefType parse(File file) throws XmlException, IOException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(file, ModuleRefType.type, (XmlOptions)null);
      }

      public static ModuleRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(file, ModuleRefType.type, options);
      }

      public static ModuleRefType parse(URL u) throws XmlException, IOException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(u, ModuleRefType.type, (XmlOptions)null);
      }

      public static ModuleRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(u, ModuleRefType.type, options);
      }

      public static ModuleRefType parse(InputStream is) throws XmlException, IOException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(is, ModuleRefType.type, (XmlOptions)null);
      }

      public static ModuleRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(is, ModuleRefType.type, options);
      }

      public static ModuleRefType parse(Reader r) throws XmlException, IOException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(r, ModuleRefType.type, (XmlOptions)null);
      }

      public static ModuleRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(r, ModuleRefType.type, options);
      }

      public static ModuleRefType parse(XMLStreamReader sr) throws XmlException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(sr, ModuleRefType.type, (XmlOptions)null);
      }

      public static ModuleRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(sr, ModuleRefType.type, options);
      }

      public static ModuleRefType parse(Node node) throws XmlException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(node, ModuleRefType.type, (XmlOptions)null);
      }

      public static ModuleRefType parse(Node node, XmlOptions options) throws XmlException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(node, ModuleRefType.type, options);
      }

      /** @deprecated */
      public static ModuleRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(xis, ModuleRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ModuleRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ModuleRefType)XmlBeans.getContextTypeLoader().parse(xis, ModuleRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleRefType.type, options);
      }

      private Factory() {
      }
   }
}
