package com.oracle.xmlns.weblogic.resourceDeploymentPlan;

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

public interface ResourceDeploymentPlanType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourceDeploymentPlanType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("resourcedeploymentplantype3232type");

   String getDescription();

   XmlString xgetDescription();

   boolean isNilDescription();

   boolean isSetDescription();

   void setDescription(String var1);

   void xsetDescription(XmlString var1);

   void setNilDescription();

   void unsetDescription();

   VariableDefinitionType getVariableDefinition();

   boolean isSetVariableDefinition();

   void setVariableDefinition(VariableDefinitionType var1);

   VariableDefinitionType addNewVariableDefinition();

   void unsetVariableDefinition();

   ExternalResourceOverrideType[] getExternalResourceOverrideArray();

   ExternalResourceOverrideType getExternalResourceOverrideArray(int var1);

   int sizeOfExternalResourceOverrideArray();

   void setExternalResourceOverrideArray(ExternalResourceOverrideType[] var1);

   void setExternalResourceOverrideArray(int var1, ExternalResourceOverrideType var2);

   ExternalResourceOverrideType insertNewExternalResourceOverride(int var1);

   ExternalResourceOverrideType addNewExternalResourceOverride();

   void removeExternalResourceOverride(int var1);

   ConfigResourceOverrideType[] getConfigResourceOverrideArray();

   ConfigResourceOverrideType getConfigResourceOverrideArray(int var1);

   int sizeOfConfigResourceOverrideArray();

   void setConfigResourceOverrideArray(ConfigResourceOverrideType[] var1);

   void setConfigResourceOverrideArray(int var1, ConfigResourceOverrideType var2);

   ConfigResourceOverrideType insertNewConfigResourceOverride(int var1);

   ConfigResourceOverrideType addNewConfigResourceOverride();

   void removeConfigResourceOverride(int var1);

   boolean getGlobalVariables();

   XmlBoolean xgetGlobalVariables();

   boolean isSetGlobalVariables();

   void setGlobalVariables(boolean var1);

   void xsetGlobalVariables(XmlBoolean var1);

   void unsetGlobalVariables();

   public static final class Factory {
      public static ResourceDeploymentPlanType newInstance() {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().newInstance(ResourceDeploymentPlanType.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanType newInstance(XmlOptions options) {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().newInstance(ResourceDeploymentPlanType.type, options);
      }

      public static ResourceDeploymentPlanType parse(String xmlAsString) throws XmlException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceDeploymentPlanType.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceDeploymentPlanType.type, options);
      }

      public static ResourceDeploymentPlanType parse(File file) throws XmlException, IOException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(file, ResourceDeploymentPlanType.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(file, ResourceDeploymentPlanType.type, options);
      }

      public static ResourceDeploymentPlanType parse(URL u) throws XmlException, IOException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(u, ResourceDeploymentPlanType.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(u, ResourceDeploymentPlanType.type, options);
      }

      public static ResourceDeploymentPlanType parse(InputStream is) throws XmlException, IOException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(is, ResourceDeploymentPlanType.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(is, ResourceDeploymentPlanType.type, options);
      }

      public static ResourceDeploymentPlanType parse(Reader r) throws XmlException, IOException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(r, ResourceDeploymentPlanType.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(r, ResourceDeploymentPlanType.type, options);
      }

      public static ResourceDeploymentPlanType parse(XMLStreamReader sr) throws XmlException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(sr, ResourceDeploymentPlanType.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(sr, ResourceDeploymentPlanType.type, options);
      }

      public static ResourceDeploymentPlanType parse(Node node) throws XmlException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(node, ResourceDeploymentPlanType.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanType parse(Node node, XmlOptions options) throws XmlException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(node, ResourceDeploymentPlanType.type, options);
      }

      /** @deprecated */
      public static ResourceDeploymentPlanType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(xis, ResourceDeploymentPlanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourceDeploymentPlanType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourceDeploymentPlanType)XmlBeans.getContextTypeLoader().parse(xis, ResourceDeploymentPlanType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceDeploymentPlanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceDeploymentPlanType.type, options);
      }

      private Factory() {
      }
   }
}
