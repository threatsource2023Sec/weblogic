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

public interface ExternalResourceOverrideType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExternalResourceOverrideType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("externalresourceoverridetype5969type");

   String getResourceName();

   XmlString xgetResourceName();

   void setResourceName(String var1);

   void xsetResourceName(XmlString var1);

   String getResourceType();

   XmlString xgetResourceType();

   void setResourceType(String var1);

   void xsetResourceType(XmlString var1);

   String getRootElement();

   XmlString xgetRootElement();

   void setRootElement(String var1);

   void xsetRootElement(XmlString var1);

   String getDescriptorFilePath();

   XmlString xgetDescriptorFilePath();

   void setDescriptorFilePath(String var1);

   void xsetDescriptorFilePath(XmlString var1);

   VariableAssignmentType[] getVariableAssignmentArray();

   VariableAssignmentType getVariableAssignmentArray(int var1);

   int sizeOfVariableAssignmentArray();

   void setVariableAssignmentArray(VariableAssignmentType[] var1);

   void setVariableAssignmentArray(int var1, VariableAssignmentType var2);

   VariableAssignmentType insertNewVariableAssignment(int var1);

   VariableAssignmentType addNewVariableAssignment();

   void removeVariableAssignment(int var1);

   public static final class Factory {
      public static ExternalResourceOverrideType newInstance() {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().newInstance(ExternalResourceOverrideType.type, (XmlOptions)null);
      }

      public static ExternalResourceOverrideType newInstance(XmlOptions options) {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().newInstance(ExternalResourceOverrideType.type, options);
      }

      public static ExternalResourceOverrideType parse(String xmlAsString) throws XmlException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExternalResourceOverrideType.type, (XmlOptions)null);
      }

      public static ExternalResourceOverrideType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExternalResourceOverrideType.type, options);
      }

      public static ExternalResourceOverrideType parse(File file) throws XmlException, IOException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(file, ExternalResourceOverrideType.type, (XmlOptions)null);
      }

      public static ExternalResourceOverrideType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(file, ExternalResourceOverrideType.type, options);
      }

      public static ExternalResourceOverrideType parse(URL u) throws XmlException, IOException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(u, ExternalResourceOverrideType.type, (XmlOptions)null);
      }

      public static ExternalResourceOverrideType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(u, ExternalResourceOverrideType.type, options);
      }

      public static ExternalResourceOverrideType parse(InputStream is) throws XmlException, IOException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(is, ExternalResourceOverrideType.type, (XmlOptions)null);
      }

      public static ExternalResourceOverrideType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(is, ExternalResourceOverrideType.type, options);
      }

      public static ExternalResourceOverrideType parse(Reader r) throws XmlException, IOException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(r, ExternalResourceOverrideType.type, (XmlOptions)null);
      }

      public static ExternalResourceOverrideType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(r, ExternalResourceOverrideType.type, options);
      }

      public static ExternalResourceOverrideType parse(XMLStreamReader sr) throws XmlException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(sr, ExternalResourceOverrideType.type, (XmlOptions)null);
      }

      public static ExternalResourceOverrideType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(sr, ExternalResourceOverrideType.type, options);
      }

      public static ExternalResourceOverrideType parse(Node node) throws XmlException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(node, ExternalResourceOverrideType.type, (XmlOptions)null);
      }

      public static ExternalResourceOverrideType parse(Node node, XmlOptions options) throws XmlException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(node, ExternalResourceOverrideType.type, options);
      }

      /** @deprecated */
      public static ExternalResourceOverrideType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(xis, ExternalResourceOverrideType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExternalResourceOverrideType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExternalResourceOverrideType)XmlBeans.getContextTypeLoader().parse(xis, ExternalResourceOverrideType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExternalResourceOverrideType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExternalResourceOverrideType.type, options);
      }

      private Factory() {
      }
   }
}
