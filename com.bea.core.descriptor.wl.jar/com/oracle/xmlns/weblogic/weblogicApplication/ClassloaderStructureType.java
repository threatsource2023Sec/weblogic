package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
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

public interface ClassloaderStructureType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClassloaderStructureType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("classloaderstructuretypeed1atype");

   ModuleRefType[] getModuleRefArray();

   ModuleRefType getModuleRefArray(int var1);

   int sizeOfModuleRefArray();

   void setModuleRefArray(ModuleRefType[] var1);

   void setModuleRefArray(int var1, ModuleRefType var2);

   ModuleRefType insertNewModuleRef(int var1);

   ModuleRefType addNewModuleRef();

   void removeModuleRef(int var1);

   ClassloaderStructureType[] getClassloaderStructureArray();

   ClassloaderStructureType getClassloaderStructureArray(int var1);

   int sizeOfClassloaderStructureArray();

   void setClassloaderStructureArray(ClassloaderStructureType[] var1);

   void setClassloaderStructureArray(int var1, ClassloaderStructureType var2);

   ClassloaderStructureType insertNewClassloaderStructure(int var1);

   ClassloaderStructureType addNewClassloaderStructure();

   void removeClassloaderStructure(int var1);

   public static final class Factory {
      public static ClassloaderStructureType newInstance() {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().newInstance(ClassloaderStructureType.type, (XmlOptions)null);
      }

      public static ClassloaderStructureType newInstance(XmlOptions options) {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().newInstance(ClassloaderStructureType.type, options);
      }

      public static ClassloaderStructureType parse(String xmlAsString) throws XmlException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClassloaderStructureType.type, (XmlOptions)null);
      }

      public static ClassloaderStructureType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClassloaderStructureType.type, options);
      }

      public static ClassloaderStructureType parse(File file) throws XmlException, IOException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(file, ClassloaderStructureType.type, (XmlOptions)null);
      }

      public static ClassloaderStructureType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(file, ClassloaderStructureType.type, options);
      }

      public static ClassloaderStructureType parse(URL u) throws XmlException, IOException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(u, ClassloaderStructureType.type, (XmlOptions)null);
      }

      public static ClassloaderStructureType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(u, ClassloaderStructureType.type, options);
      }

      public static ClassloaderStructureType parse(InputStream is) throws XmlException, IOException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(is, ClassloaderStructureType.type, (XmlOptions)null);
      }

      public static ClassloaderStructureType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(is, ClassloaderStructureType.type, options);
      }

      public static ClassloaderStructureType parse(Reader r) throws XmlException, IOException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(r, ClassloaderStructureType.type, (XmlOptions)null);
      }

      public static ClassloaderStructureType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(r, ClassloaderStructureType.type, options);
      }

      public static ClassloaderStructureType parse(XMLStreamReader sr) throws XmlException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(sr, ClassloaderStructureType.type, (XmlOptions)null);
      }

      public static ClassloaderStructureType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(sr, ClassloaderStructureType.type, options);
      }

      public static ClassloaderStructureType parse(Node node) throws XmlException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(node, ClassloaderStructureType.type, (XmlOptions)null);
      }

      public static ClassloaderStructureType parse(Node node, XmlOptions options) throws XmlException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(node, ClassloaderStructureType.type, options);
      }

      /** @deprecated */
      public static ClassloaderStructureType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(xis, ClassloaderStructureType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClassloaderStructureType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClassloaderStructureType)XmlBeans.getContextTypeLoader().parse(xis, ClassloaderStructureType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClassloaderStructureType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClassloaderStructureType.type, options);
      }

      private Factory() {
      }
   }
}
