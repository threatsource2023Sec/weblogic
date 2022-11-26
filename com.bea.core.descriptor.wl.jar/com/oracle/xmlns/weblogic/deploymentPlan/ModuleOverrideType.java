package com.oracle.xmlns.weblogic.deploymentPlan;

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

public interface ModuleOverrideType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ModuleOverrideType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("moduleoverridetypee468type");

   String getModuleName();

   XmlString xgetModuleName();

   void setModuleName(String var1);

   void xsetModuleName(XmlString var1);

   String getModuleType();

   XmlString xgetModuleType();

   void setModuleType(String var1);

   void xsetModuleType(XmlString var1);

   ModuleDescriptorType[] getModuleDescriptorArray();

   ModuleDescriptorType getModuleDescriptorArray(int var1);

   int sizeOfModuleDescriptorArray();

   void setModuleDescriptorArray(ModuleDescriptorType[] var1);

   void setModuleDescriptorArray(int var1, ModuleDescriptorType var2);

   ModuleDescriptorType insertNewModuleDescriptor(int var1);

   ModuleDescriptorType addNewModuleDescriptor();

   void removeModuleDescriptor(int var1);

   public static final class Factory {
      public static ModuleOverrideType newInstance() {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().newInstance(ModuleOverrideType.type, (XmlOptions)null);
      }

      public static ModuleOverrideType newInstance(XmlOptions options) {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().newInstance(ModuleOverrideType.type, options);
      }

      public static ModuleOverrideType parse(String xmlAsString) throws XmlException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleOverrideType.type, (XmlOptions)null);
      }

      public static ModuleOverrideType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleOverrideType.type, options);
      }

      public static ModuleOverrideType parse(File file) throws XmlException, IOException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(file, ModuleOverrideType.type, (XmlOptions)null);
      }

      public static ModuleOverrideType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(file, ModuleOverrideType.type, options);
      }

      public static ModuleOverrideType parse(URL u) throws XmlException, IOException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(u, ModuleOverrideType.type, (XmlOptions)null);
      }

      public static ModuleOverrideType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(u, ModuleOverrideType.type, options);
      }

      public static ModuleOverrideType parse(InputStream is) throws XmlException, IOException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(is, ModuleOverrideType.type, (XmlOptions)null);
      }

      public static ModuleOverrideType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(is, ModuleOverrideType.type, options);
      }

      public static ModuleOverrideType parse(Reader r) throws XmlException, IOException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(r, ModuleOverrideType.type, (XmlOptions)null);
      }

      public static ModuleOverrideType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(r, ModuleOverrideType.type, options);
      }

      public static ModuleOverrideType parse(XMLStreamReader sr) throws XmlException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(sr, ModuleOverrideType.type, (XmlOptions)null);
      }

      public static ModuleOverrideType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(sr, ModuleOverrideType.type, options);
      }

      public static ModuleOverrideType parse(Node node) throws XmlException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(node, ModuleOverrideType.type, (XmlOptions)null);
      }

      public static ModuleOverrideType parse(Node node, XmlOptions options) throws XmlException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(node, ModuleOverrideType.type, options);
      }

      /** @deprecated */
      public static ModuleOverrideType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(xis, ModuleOverrideType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ModuleOverrideType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ModuleOverrideType)XmlBeans.getContextTypeLoader().parse(xis, ModuleOverrideType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleOverrideType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleOverrideType.type, options);
      }

      private Factory() {
      }
   }
}
