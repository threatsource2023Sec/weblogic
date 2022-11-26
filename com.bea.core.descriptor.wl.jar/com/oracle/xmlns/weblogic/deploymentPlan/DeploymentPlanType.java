package com.oracle.xmlns.weblogic.deploymentPlan;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface DeploymentPlanType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeploymentPlanType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("deploymentplantype1fc4type");

   String getDescription();

   XmlString xgetDescription();

   boolean isNilDescription();

   boolean isSetDescription();

   void setDescription(String var1);

   void xsetDescription(XmlString var1);

   void setNilDescription();

   void unsetDescription();

   String getApplicationName();

   XmlString xgetApplicationName();

   void setApplicationName(String var1);

   void xsetApplicationName(XmlString var1);

   String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   VariableDefinitionType getVariableDefinition();

   boolean isSetVariableDefinition();

   void setVariableDefinition(VariableDefinitionType var1);

   VariableDefinitionType addNewVariableDefinition();

   void unsetVariableDefinition();

   ModuleOverrideType[] getModuleOverrideArray();

   ModuleOverrideType getModuleOverrideArray(int var1);

   int sizeOfModuleOverrideArray();

   void setModuleOverrideArray(ModuleOverrideType[] var1);

   void setModuleOverrideArray(int var1, ModuleOverrideType var2);

   ModuleOverrideType insertNewModuleOverride(int var1);

   ModuleOverrideType addNewModuleOverride();

   void removeModuleOverride(int var1);

   String getConfigRoot();

   XmlString xgetConfigRoot();

   boolean isNilConfigRoot();

   boolean isSetConfigRoot();

   void setConfigRoot(String var1);

   void xsetConfigRoot(XmlString var1);

   void setNilConfigRoot();

   void unsetConfigRoot();

   boolean getGlobalVariables();

   XmlBoolean xgetGlobalVariables();

   boolean isSetGlobalVariables();

   void setGlobalVariables(boolean var1);

   void xsetGlobalVariables(XmlBoolean var1);

   void unsetGlobalVariables();

   public static final class Factory {
      public static DeploymentPlanType newInstance() {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().newInstance(DeploymentPlanType.type, (XmlOptions)null);
      }

      public static DeploymentPlanType newInstance(XmlOptions options) {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().newInstance(DeploymentPlanType.type, options);
      }

      public static DeploymentPlanType parse(String xmlAsString) throws XmlException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentPlanType.type, (XmlOptions)null);
      }

      public static DeploymentPlanType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentPlanType.type, options);
      }

      public static DeploymentPlanType parse(File file) throws XmlException, IOException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(file, DeploymentPlanType.type, (XmlOptions)null);
      }

      public static DeploymentPlanType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(file, DeploymentPlanType.type, options);
      }

      public static DeploymentPlanType parse(URL u) throws XmlException, IOException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(u, DeploymentPlanType.type, (XmlOptions)null);
      }

      public static DeploymentPlanType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(u, DeploymentPlanType.type, options);
      }

      public static DeploymentPlanType parse(InputStream is) throws XmlException, IOException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(is, DeploymentPlanType.type, (XmlOptions)null);
      }

      public static DeploymentPlanType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(is, DeploymentPlanType.type, options);
      }

      public static DeploymentPlanType parse(Reader r) throws XmlException, IOException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(r, DeploymentPlanType.type, (XmlOptions)null);
      }

      public static DeploymentPlanType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(r, DeploymentPlanType.type, options);
      }

      public static DeploymentPlanType parse(XMLStreamReader sr) throws XmlException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(sr, DeploymentPlanType.type, (XmlOptions)null);
      }

      public static DeploymentPlanType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(sr, DeploymentPlanType.type, options);
      }

      public static DeploymentPlanType parse(Node node) throws XmlException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(node, DeploymentPlanType.type, (XmlOptions)null);
      }

      public static DeploymentPlanType parse(Node node, XmlOptions options) throws XmlException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(node, DeploymentPlanType.type, options);
      }

      /** @deprecated */
      public static DeploymentPlanType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(xis, DeploymentPlanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeploymentPlanType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeploymentPlanType)XmlBeans.getContextTypeLoader().parse(xis, DeploymentPlanType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentPlanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentPlanType.type, options);
      }

      private Factory() {
      }
   }
}
