package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface EnvEntryTypeValuesType extends FullyQualifiedClassType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EnvEntryTypeValuesType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("enventrytypevaluestype39d5type");

   public static final class Factory {
      public static EnvEntryTypeValuesType newInstance() {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().newInstance(EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType newInstance(XmlOptions options) {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().newInstance(EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(java.lang.String xmlAsString) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(File file) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(file, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(file, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(URL u) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(u, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(u, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(InputStream is) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(is, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(is, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(Reader r) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(r, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(r, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(XMLStreamReader sr) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(sr, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(sr, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(Node node) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(node, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(Node node, XmlOptions options) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(node, EnvEntryTypeValuesType.type, options);
      }

      /** @deprecated */
      public static EnvEntryTypeValuesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(xis, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EnvEntryTypeValuesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(xis, EnvEntryTypeValuesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnvEntryTypeValuesType.type, options);
      }

      private Factory() {
      }
   }
}
