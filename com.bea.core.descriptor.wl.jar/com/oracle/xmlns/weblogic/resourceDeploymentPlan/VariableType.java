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

public interface VariableType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(VariableType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("variabletype13bctype");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getValue();

   XmlString xgetValue();

   boolean isNilValue();

   boolean isSetValue();

   void setValue(String var1);

   void xsetValue(XmlString var1);

   void setNilValue();

   void unsetValue();

   String getDescription();

   XmlString xgetDescription();

   boolean isSetDescription();

   void setDescription(String var1);

   void xsetDescription(XmlString var1);

   void unsetDescription();

   public static final class Factory {
      public static VariableType newInstance() {
         return (VariableType)XmlBeans.getContextTypeLoader().newInstance(VariableType.type, (XmlOptions)null);
      }

      public static VariableType newInstance(XmlOptions options) {
         return (VariableType)XmlBeans.getContextTypeLoader().newInstance(VariableType.type, options);
      }

      public static VariableType parse(String xmlAsString) throws XmlException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VariableType.type, (XmlOptions)null);
      }

      public static VariableType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VariableType.type, options);
      }

      public static VariableType parse(File file) throws XmlException, IOException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(file, VariableType.type, (XmlOptions)null);
      }

      public static VariableType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(file, VariableType.type, options);
      }

      public static VariableType parse(URL u) throws XmlException, IOException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(u, VariableType.type, (XmlOptions)null);
      }

      public static VariableType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(u, VariableType.type, options);
      }

      public static VariableType parse(InputStream is) throws XmlException, IOException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(is, VariableType.type, (XmlOptions)null);
      }

      public static VariableType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(is, VariableType.type, options);
      }

      public static VariableType parse(Reader r) throws XmlException, IOException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(r, VariableType.type, (XmlOptions)null);
      }

      public static VariableType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(r, VariableType.type, options);
      }

      public static VariableType parse(XMLStreamReader sr) throws XmlException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(sr, VariableType.type, (XmlOptions)null);
      }

      public static VariableType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(sr, VariableType.type, options);
      }

      public static VariableType parse(Node node) throws XmlException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(node, VariableType.type, (XmlOptions)null);
      }

      public static VariableType parse(Node node, XmlOptions options) throws XmlException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(node, VariableType.type, options);
      }

      /** @deprecated */
      public static VariableType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(xis, VariableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static VariableType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (VariableType)XmlBeans.getContextTypeLoader().parse(xis, VariableType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VariableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VariableType.type, options);
      }

      private Factory() {
      }
   }
}
