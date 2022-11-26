package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface ParameterType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ParameterType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("parametertype3792type");

   String getDescription();

   XmlString xgetDescription();

   boolean isSetDescription();

   void setDescription(String var1);

   void xsetDescription(XmlString var1);

   void unsetDescription();

   String getParamName();

   XmlString xgetParamName();

   void setParamName(String var1);

   void xsetParamName(XmlString var1);

   String getParamValue();

   XmlString xgetParamValue();

   void setParamValue(String var1);

   void xsetParamValue(XmlString var1);

   public static final class Factory {
      public static ParameterType newInstance() {
         return (ParameterType)XmlBeans.getContextTypeLoader().newInstance(ParameterType.type, (XmlOptions)null);
      }

      public static ParameterType newInstance(XmlOptions options) {
         return (ParameterType)XmlBeans.getContextTypeLoader().newInstance(ParameterType.type, options);
      }

      public static ParameterType parse(String xmlAsString) throws XmlException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParameterType.type, (XmlOptions)null);
      }

      public static ParameterType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParameterType.type, options);
      }

      public static ParameterType parse(File file) throws XmlException, IOException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(file, ParameterType.type, (XmlOptions)null);
      }

      public static ParameterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(file, ParameterType.type, options);
      }

      public static ParameterType parse(URL u) throws XmlException, IOException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(u, ParameterType.type, (XmlOptions)null);
      }

      public static ParameterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(u, ParameterType.type, options);
      }

      public static ParameterType parse(InputStream is) throws XmlException, IOException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(is, ParameterType.type, (XmlOptions)null);
      }

      public static ParameterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(is, ParameterType.type, options);
      }

      public static ParameterType parse(Reader r) throws XmlException, IOException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(r, ParameterType.type, (XmlOptions)null);
      }

      public static ParameterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(r, ParameterType.type, options);
      }

      public static ParameterType parse(XMLStreamReader sr) throws XmlException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(sr, ParameterType.type, (XmlOptions)null);
      }

      public static ParameterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(sr, ParameterType.type, options);
      }

      public static ParameterType parse(Node node) throws XmlException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(node, ParameterType.type, (XmlOptions)null);
      }

      public static ParameterType parse(Node node, XmlOptions options) throws XmlException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(node, ParameterType.type, options);
      }

      /** @deprecated */
      public static ParameterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(xis, ParameterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ParameterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ParameterType)XmlBeans.getContextTypeLoader().parse(xis, ParameterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParameterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParameterType.type, options);
      }

      private Factory() {
      }
   }
}
