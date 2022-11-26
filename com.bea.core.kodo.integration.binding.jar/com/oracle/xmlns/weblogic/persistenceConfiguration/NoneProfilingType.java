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

public interface NoneProfilingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NoneProfilingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("noneprofilingtype2409type");

   public static final class Factory {
      public static NoneProfilingType newInstance() {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().newInstance(NoneProfilingType.type, (XmlOptions)null);
      }

      public static NoneProfilingType newInstance(XmlOptions options) {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().newInstance(NoneProfilingType.type, options);
      }

      public static NoneProfilingType parse(String xmlAsString) throws XmlException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NoneProfilingType.type, (XmlOptions)null);
      }

      public static NoneProfilingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NoneProfilingType.type, options);
      }

      public static NoneProfilingType parse(File file) throws XmlException, IOException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(file, NoneProfilingType.type, (XmlOptions)null);
      }

      public static NoneProfilingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(file, NoneProfilingType.type, options);
      }

      public static NoneProfilingType parse(URL u) throws XmlException, IOException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(u, NoneProfilingType.type, (XmlOptions)null);
      }

      public static NoneProfilingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(u, NoneProfilingType.type, options);
      }

      public static NoneProfilingType parse(InputStream is) throws XmlException, IOException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(is, NoneProfilingType.type, (XmlOptions)null);
      }

      public static NoneProfilingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(is, NoneProfilingType.type, options);
      }

      public static NoneProfilingType parse(Reader r) throws XmlException, IOException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(r, NoneProfilingType.type, (XmlOptions)null);
      }

      public static NoneProfilingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(r, NoneProfilingType.type, options);
      }

      public static NoneProfilingType parse(XMLStreamReader sr) throws XmlException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(sr, NoneProfilingType.type, (XmlOptions)null);
      }

      public static NoneProfilingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(sr, NoneProfilingType.type, options);
      }

      public static NoneProfilingType parse(Node node) throws XmlException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(node, NoneProfilingType.type, (XmlOptions)null);
      }

      public static NoneProfilingType parse(Node node, XmlOptions options) throws XmlException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(node, NoneProfilingType.type, options);
      }

      /** @deprecated */
      public static NoneProfilingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(xis, NoneProfilingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NoneProfilingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NoneProfilingType)XmlBeans.getContextTypeLoader().parse(xis, NoneProfilingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NoneProfilingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NoneProfilingType.type, options);
      }

      private Factory() {
      }
   }
}
