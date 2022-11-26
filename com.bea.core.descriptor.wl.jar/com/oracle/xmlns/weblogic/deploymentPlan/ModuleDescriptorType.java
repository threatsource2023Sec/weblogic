package com.oracle.xmlns.weblogic.deploymentPlan;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.j2Ee.PathType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ModuleDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ModuleDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("moduledescriptortypebaa5type");

   String getRootElement();

   XmlString xgetRootElement();

   void setRootElement(String var1);

   void xsetRootElement(XmlString var1);

   PathType getUri();

   void setUri(PathType var1);

   PathType addNewUri();

   VariableAssignmentType[] getVariableAssignmentArray();

   VariableAssignmentType getVariableAssignmentArray(int var1);

   int sizeOfVariableAssignmentArray();

   void setVariableAssignmentArray(VariableAssignmentType[] var1);

   void setVariableAssignmentArray(int var1, VariableAssignmentType var2);

   VariableAssignmentType insertNewVariableAssignment(int var1);

   VariableAssignmentType addNewVariableAssignment();

   void removeVariableAssignment(int var1);

   String getHashCode();

   XmlString xgetHashCode();

   boolean isSetHashCode();

   void setHashCode(String var1);

   void xsetHashCode(XmlString var1);

   void unsetHashCode();

   boolean getExternal();

   XmlBoolean xgetExternal();

   boolean isSetExternal();

   void setExternal(boolean var1);

   void xsetExternal(XmlBoolean var1);

   void unsetExternal();

   public static final class Factory {
      public static ModuleDescriptorType newInstance() {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().newInstance(ModuleDescriptorType.type, (XmlOptions)null);
      }

      public static ModuleDescriptorType newInstance(XmlOptions options) {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().newInstance(ModuleDescriptorType.type, options);
      }

      public static ModuleDescriptorType parse(String xmlAsString) throws XmlException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleDescriptorType.type, (XmlOptions)null);
      }

      public static ModuleDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleDescriptorType.type, options);
      }

      public static ModuleDescriptorType parse(File file) throws XmlException, IOException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(file, ModuleDescriptorType.type, (XmlOptions)null);
      }

      public static ModuleDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(file, ModuleDescriptorType.type, options);
      }

      public static ModuleDescriptorType parse(URL u) throws XmlException, IOException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(u, ModuleDescriptorType.type, (XmlOptions)null);
      }

      public static ModuleDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(u, ModuleDescriptorType.type, options);
      }

      public static ModuleDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(is, ModuleDescriptorType.type, (XmlOptions)null);
      }

      public static ModuleDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(is, ModuleDescriptorType.type, options);
      }

      public static ModuleDescriptorType parse(Reader r) throws XmlException, IOException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(r, ModuleDescriptorType.type, (XmlOptions)null);
      }

      public static ModuleDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(r, ModuleDescriptorType.type, options);
      }

      public static ModuleDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, ModuleDescriptorType.type, (XmlOptions)null);
      }

      public static ModuleDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, ModuleDescriptorType.type, options);
      }

      public static ModuleDescriptorType parse(Node node) throws XmlException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(node, ModuleDescriptorType.type, (XmlOptions)null);
      }

      public static ModuleDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(node, ModuleDescriptorType.type, options);
      }

      /** @deprecated */
      public static ModuleDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, ModuleDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ModuleDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ModuleDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, ModuleDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
