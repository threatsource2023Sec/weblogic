package com.oracle.xmlns.weblogic.resourceDeploymentPlan;

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

public interface ConfigResourceOverrideType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConfigResourceOverrideType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("configresourceoverridetype3920type");

   String getResourceName();

   XmlString xgetResourceName();

   void setResourceName(String var1);

   void xsetResourceName(XmlString var1);

   String getResourceType();

   XmlString xgetResourceType();

   void setResourceType(String var1);

   void xsetResourceType(XmlString var1);

   VariableAssignmentType[] getVariableAssignmentArray();

   VariableAssignmentType getVariableAssignmentArray(int var1);

   int sizeOfVariableAssignmentArray();

   void setVariableAssignmentArray(VariableAssignmentType[] var1);

   void setVariableAssignmentArray(int var1, VariableAssignmentType var2);

   VariableAssignmentType insertNewVariableAssignment(int var1);

   VariableAssignmentType addNewVariableAssignment();

   void removeVariableAssignment(int var1);

   public static final class Factory {
      public static ConfigResourceOverrideType newInstance() {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().newInstance(ConfigResourceOverrideType.type, (XmlOptions)null);
      }

      public static ConfigResourceOverrideType newInstance(XmlOptions options) {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().newInstance(ConfigResourceOverrideType.type, options);
      }

      public static ConfigResourceOverrideType parse(String xmlAsString) throws XmlException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigResourceOverrideType.type, (XmlOptions)null);
      }

      public static ConfigResourceOverrideType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigResourceOverrideType.type, options);
      }

      public static ConfigResourceOverrideType parse(File file) throws XmlException, IOException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(file, ConfigResourceOverrideType.type, (XmlOptions)null);
      }

      public static ConfigResourceOverrideType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(file, ConfigResourceOverrideType.type, options);
      }

      public static ConfigResourceOverrideType parse(URL u) throws XmlException, IOException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(u, ConfigResourceOverrideType.type, (XmlOptions)null);
      }

      public static ConfigResourceOverrideType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(u, ConfigResourceOverrideType.type, options);
      }

      public static ConfigResourceOverrideType parse(InputStream is) throws XmlException, IOException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(is, ConfigResourceOverrideType.type, (XmlOptions)null);
      }

      public static ConfigResourceOverrideType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(is, ConfigResourceOverrideType.type, options);
      }

      public static ConfigResourceOverrideType parse(Reader r) throws XmlException, IOException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(r, ConfigResourceOverrideType.type, (XmlOptions)null);
      }

      public static ConfigResourceOverrideType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(r, ConfigResourceOverrideType.type, options);
      }

      public static ConfigResourceOverrideType parse(XMLStreamReader sr) throws XmlException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(sr, ConfigResourceOverrideType.type, (XmlOptions)null);
      }

      public static ConfigResourceOverrideType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(sr, ConfigResourceOverrideType.type, options);
      }

      public static ConfigResourceOverrideType parse(Node node) throws XmlException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(node, ConfigResourceOverrideType.type, (XmlOptions)null);
      }

      public static ConfigResourceOverrideType parse(Node node, XmlOptions options) throws XmlException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(node, ConfigResourceOverrideType.type, options);
      }

      /** @deprecated */
      public static ConfigResourceOverrideType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(xis, ConfigResourceOverrideType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConfigResourceOverrideType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConfigResourceOverrideType)XmlBeans.getContextTypeLoader().parse(xis, ConfigResourceOverrideType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigResourceOverrideType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigResourceOverrideType.type, options);
      }

      private Factory() {
      }
   }
}
