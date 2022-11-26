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

public interface WeblogicExtensionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicExtensionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("weblogicextensiontype6986type");

   ModuleProviderType[] getModuleProviderArray();

   ModuleProviderType getModuleProviderArray(int var1);

   int sizeOfModuleProviderArray();

   void setModuleProviderArray(ModuleProviderType[] var1);

   void setModuleProviderArray(int var1, ModuleProviderType var2);

   ModuleProviderType insertNewModuleProvider(int var1);

   ModuleProviderType addNewModuleProvider();

   void removeModuleProvider(int var1);

   CustomModuleType[] getCustomModuleArray();

   CustomModuleType getCustomModuleArray(int var1);

   int sizeOfCustomModuleArray();

   void setCustomModuleArray(CustomModuleType[] var1);

   void setCustomModuleArray(int var1, CustomModuleType var2);

   CustomModuleType insertNewCustomModule(int var1);

   CustomModuleType addNewCustomModule();

   void removeCustomModule(int var1);

   String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static WeblogicExtensionType newInstance() {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().newInstance(WeblogicExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicExtensionType newInstance(XmlOptions options) {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().newInstance(WeblogicExtensionType.type, options);
      }

      public static WeblogicExtensionType parse(String xmlAsString) throws XmlException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicExtensionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicExtensionType.type, options);
      }

      public static WeblogicExtensionType parse(File file) throws XmlException, IOException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(file, WeblogicExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicExtensionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(file, WeblogicExtensionType.type, options);
      }

      public static WeblogicExtensionType parse(URL u) throws XmlException, IOException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(u, WeblogicExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicExtensionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(u, WeblogicExtensionType.type, options);
      }

      public static WeblogicExtensionType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(is, WeblogicExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicExtensionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(is, WeblogicExtensionType.type, options);
      }

      public static WeblogicExtensionType parse(Reader r) throws XmlException, IOException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(r, WeblogicExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicExtensionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(r, WeblogicExtensionType.type, options);
      }

      public static WeblogicExtensionType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicExtensionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicExtensionType.type, options);
      }

      public static WeblogicExtensionType parse(Node node) throws XmlException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(node, WeblogicExtensionType.type, (XmlOptions)null);
      }

      public static WeblogicExtensionType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(node, WeblogicExtensionType.type, options);
      }

      /** @deprecated */
      public static WeblogicExtensionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicExtensionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicExtensionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicExtensionType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicExtensionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicExtensionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicExtensionType.type, options);
      }

      private Factory() {
      }
   }
}
