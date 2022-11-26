package com.oracle.xmlns.weblogic.weblogicExtension;

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

public interface ModuleProviderType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ModuleProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("moduleprovidertype4b4atype");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getModuleFactoryClassName();

   XmlString xgetModuleFactoryClassName();

   void setModuleFactoryClassName(String var1);

   void xsetModuleFactoryClassName(XmlString var1);

   String getBindingJarUri();

   XmlString xgetBindingJarUri();

   boolean isSetBindingJarUri();

   void setBindingJarUri(String var1);

   void xsetBindingJarUri(XmlString var1);

   void unsetBindingJarUri();

   public static final class Factory {
      public static ModuleProviderType newInstance() {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().newInstance(ModuleProviderType.type, (XmlOptions)null);
      }

      public static ModuleProviderType newInstance(XmlOptions options) {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().newInstance(ModuleProviderType.type, options);
      }

      public static ModuleProviderType parse(String xmlAsString) throws XmlException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleProviderType.type, (XmlOptions)null);
      }

      public static ModuleProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleProviderType.type, options);
      }

      public static ModuleProviderType parse(File file) throws XmlException, IOException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(file, ModuleProviderType.type, (XmlOptions)null);
      }

      public static ModuleProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(file, ModuleProviderType.type, options);
      }

      public static ModuleProviderType parse(URL u) throws XmlException, IOException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(u, ModuleProviderType.type, (XmlOptions)null);
      }

      public static ModuleProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(u, ModuleProviderType.type, options);
      }

      public static ModuleProviderType parse(InputStream is) throws XmlException, IOException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(is, ModuleProviderType.type, (XmlOptions)null);
      }

      public static ModuleProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(is, ModuleProviderType.type, options);
      }

      public static ModuleProviderType parse(Reader r) throws XmlException, IOException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(r, ModuleProviderType.type, (XmlOptions)null);
      }

      public static ModuleProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(r, ModuleProviderType.type, options);
      }

      public static ModuleProviderType parse(XMLStreamReader sr) throws XmlException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(sr, ModuleProviderType.type, (XmlOptions)null);
      }

      public static ModuleProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(sr, ModuleProviderType.type, options);
      }

      public static ModuleProviderType parse(Node node) throws XmlException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(node, ModuleProviderType.type, (XmlOptions)null);
      }

      public static ModuleProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(node, ModuleProviderType.type, options);
      }

      /** @deprecated */
      public static ModuleProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(xis, ModuleProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ModuleProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ModuleProviderType)XmlBeans.getContextTypeLoader().parse(xis, ModuleProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleProviderType.type, options);
      }

      private Factory() {
      }
   }
}
