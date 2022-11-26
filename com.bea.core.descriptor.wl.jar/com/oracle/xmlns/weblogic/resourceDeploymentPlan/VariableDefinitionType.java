package com.oracle.xmlns.weblogic.resourceDeploymentPlan;

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

public interface VariableDefinitionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(VariableDefinitionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("variabledefinitiontype6992type");

   VariableType[] getVariableArray();

   VariableType getVariableArray(int var1);

   int sizeOfVariableArray();

   void setVariableArray(VariableType[] var1);

   void setVariableArray(int var1, VariableType var2);

   VariableType insertNewVariable(int var1);

   VariableType addNewVariable();

   void removeVariable(int var1);

   public static final class Factory {
      public static VariableDefinitionType newInstance() {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().newInstance(VariableDefinitionType.type, (XmlOptions)null);
      }

      public static VariableDefinitionType newInstance(XmlOptions options) {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().newInstance(VariableDefinitionType.type, options);
      }

      public static VariableDefinitionType parse(String xmlAsString) throws XmlException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VariableDefinitionType.type, (XmlOptions)null);
      }

      public static VariableDefinitionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VariableDefinitionType.type, options);
      }

      public static VariableDefinitionType parse(File file) throws XmlException, IOException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(file, VariableDefinitionType.type, (XmlOptions)null);
      }

      public static VariableDefinitionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(file, VariableDefinitionType.type, options);
      }

      public static VariableDefinitionType parse(URL u) throws XmlException, IOException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(u, VariableDefinitionType.type, (XmlOptions)null);
      }

      public static VariableDefinitionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(u, VariableDefinitionType.type, options);
      }

      public static VariableDefinitionType parse(InputStream is) throws XmlException, IOException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(is, VariableDefinitionType.type, (XmlOptions)null);
      }

      public static VariableDefinitionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(is, VariableDefinitionType.type, options);
      }

      public static VariableDefinitionType parse(Reader r) throws XmlException, IOException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(r, VariableDefinitionType.type, (XmlOptions)null);
      }

      public static VariableDefinitionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(r, VariableDefinitionType.type, options);
      }

      public static VariableDefinitionType parse(XMLStreamReader sr) throws XmlException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(sr, VariableDefinitionType.type, (XmlOptions)null);
      }

      public static VariableDefinitionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(sr, VariableDefinitionType.type, options);
      }

      public static VariableDefinitionType parse(Node node) throws XmlException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(node, VariableDefinitionType.type, (XmlOptions)null);
      }

      public static VariableDefinitionType parse(Node node, XmlOptions options) throws XmlException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(node, VariableDefinitionType.type, options);
      }

      /** @deprecated */
      public static VariableDefinitionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(xis, VariableDefinitionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static VariableDefinitionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (VariableDefinitionType)XmlBeans.getContextTypeLoader().parse(xis, VariableDefinitionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VariableDefinitionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VariableDefinitionType.type, options);
      }

      private Factory() {
      }
   }
}
