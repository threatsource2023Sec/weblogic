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

public interface ProfilingDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProfilingDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("profiling0570doctype");

   ProfilingType getProfiling();

   void setProfiling(ProfilingType var1);

   ProfilingType addNewProfiling();

   public static final class Factory {
      public static ProfilingDocument newInstance() {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().newInstance(ProfilingDocument.type, (XmlOptions)null);
      }

      public static ProfilingDocument newInstance(XmlOptions options) {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().newInstance(ProfilingDocument.type, options);
      }

      public static ProfilingDocument parse(String xmlAsString) throws XmlException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProfilingDocument.type, (XmlOptions)null);
      }

      public static ProfilingDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProfilingDocument.type, options);
      }

      public static ProfilingDocument parse(File file) throws XmlException, IOException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(file, ProfilingDocument.type, (XmlOptions)null);
      }

      public static ProfilingDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(file, ProfilingDocument.type, options);
      }

      public static ProfilingDocument parse(URL u) throws XmlException, IOException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(u, ProfilingDocument.type, (XmlOptions)null);
      }

      public static ProfilingDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(u, ProfilingDocument.type, options);
      }

      public static ProfilingDocument parse(InputStream is) throws XmlException, IOException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(is, ProfilingDocument.type, (XmlOptions)null);
      }

      public static ProfilingDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(is, ProfilingDocument.type, options);
      }

      public static ProfilingDocument parse(Reader r) throws XmlException, IOException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(r, ProfilingDocument.type, (XmlOptions)null);
      }

      public static ProfilingDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(r, ProfilingDocument.type, options);
      }

      public static ProfilingDocument parse(XMLStreamReader sr) throws XmlException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(sr, ProfilingDocument.type, (XmlOptions)null);
      }

      public static ProfilingDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(sr, ProfilingDocument.type, options);
      }

      public static ProfilingDocument parse(Node node) throws XmlException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(node, ProfilingDocument.type, (XmlOptions)null);
      }

      public static ProfilingDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(node, ProfilingDocument.type, options);
      }

      /** @deprecated */
      public static ProfilingDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(xis, ProfilingDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProfilingDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProfilingDocument)XmlBeans.getContextTypeLoader().parse(xis, ProfilingDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProfilingDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProfilingDocument.type, options);
      }

      private Factory() {
      }
   }
}
