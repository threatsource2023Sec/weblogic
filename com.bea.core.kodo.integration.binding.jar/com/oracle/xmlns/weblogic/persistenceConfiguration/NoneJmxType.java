package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface NoneJmxType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NoneJmxType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("nonejmxtype87batype");

   public static final class Factory {
      public static NoneJmxType newInstance() {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().newInstance(NoneJmxType.type, (XmlOptions)null);
      }

      public static NoneJmxType newInstance(XmlOptions options) {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().newInstance(NoneJmxType.type, options);
      }

      public static NoneJmxType parse(String xmlAsString) throws XmlException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NoneJmxType.type, (XmlOptions)null);
      }

      public static NoneJmxType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NoneJmxType.type, options);
      }

      public static NoneJmxType parse(File file) throws XmlException, IOException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(file, NoneJmxType.type, (XmlOptions)null);
      }

      public static NoneJmxType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(file, NoneJmxType.type, options);
      }

      public static NoneJmxType parse(URL u) throws XmlException, IOException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(u, NoneJmxType.type, (XmlOptions)null);
      }

      public static NoneJmxType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(u, NoneJmxType.type, options);
      }

      public static NoneJmxType parse(InputStream is) throws XmlException, IOException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(is, NoneJmxType.type, (XmlOptions)null);
      }

      public static NoneJmxType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(is, NoneJmxType.type, options);
      }

      public static NoneJmxType parse(Reader r) throws XmlException, IOException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(r, NoneJmxType.type, (XmlOptions)null);
      }

      public static NoneJmxType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(r, NoneJmxType.type, options);
      }

      public static NoneJmxType parse(XMLStreamReader sr) throws XmlException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(sr, NoneJmxType.type, (XmlOptions)null);
      }

      public static NoneJmxType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(sr, NoneJmxType.type, options);
      }

      public static NoneJmxType parse(Node node) throws XmlException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(node, NoneJmxType.type, (XmlOptions)null);
      }

      public static NoneJmxType parse(Node node, XmlOptions options) throws XmlException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(node, NoneJmxType.type, options);
      }

      /** @deprecated */
      public static NoneJmxType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(xis, NoneJmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NoneJmxType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NoneJmxType)XmlBeans.getContextTypeLoader().parse(xis, NoneJmxType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NoneJmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NoneJmxType.type, options);
      }

      private Factory() {
      }
   }
}
