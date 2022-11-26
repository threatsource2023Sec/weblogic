package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface ProfilingProxyManagerType extends ProxyManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProfilingProxyManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("profilingproxymanagertype6ca5type");

   boolean getAssertAllowedType();

   XmlBoolean xgetAssertAllowedType();

   boolean isSetAssertAllowedType();

   void setAssertAllowedType(boolean var1);

   void xsetAssertAllowedType(XmlBoolean var1);

   void unsetAssertAllowedType();

   boolean getTrackChanges();

   XmlBoolean xgetTrackChanges();

   boolean isSetTrackChanges();

   void setTrackChanges(boolean var1);

   void xsetTrackChanges(XmlBoolean var1);

   void unsetTrackChanges();

   public static final class Factory {
      public static ProfilingProxyManagerType newInstance() {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().newInstance(ProfilingProxyManagerType.type, (XmlOptions)null);
      }

      public static ProfilingProxyManagerType newInstance(XmlOptions options) {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().newInstance(ProfilingProxyManagerType.type, options);
      }

      public static ProfilingProxyManagerType parse(String xmlAsString) throws XmlException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProfilingProxyManagerType.type, (XmlOptions)null);
      }

      public static ProfilingProxyManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProfilingProxyManagerType.type, options);
      }

      public static ProfilingProxyManagerType parse(File file) throws XmlException, IOException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(file, ProfilingProxyManagerType.type, (XmlOptions)null);
      }

      public static ProfilingProxyManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(file, ProfilingProxyManagerType.type, options);
      }

      public static ProfilingProxyManagerType parse(URL u) throws XmlException, IOException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(u, ProfilingProxyManagerType.type, (XmlOptions)null);
      }

      public static ProfilingProxyManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(u, ProfilingProxyManagerType.type, options);
      }

      public static ProfilingProxyManagerType parse(InputStream is) throws XmlException, IOException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(is, ProfilingProxyManagerType.type, (XmlOptions)null);
      }

      public static ProfilingProxyManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(is, ProfilingProxyManagerType.type, options);
      }

      public static ProfilingProxyManagerType parse(Reader r) throws XmlException, IOException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(r, ProfilingProxyManagerType.type, (XmlOptions)null);
      }

      public static ProfilingProxyManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(r, ProfilingProxyManagerType.type, options);
      }

      public static ProfilingProxyManagerType parse(XMLStreamReader sr) throws XmlException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(sr, ProfilingProxyManagerType.type, (XmlOptions)null);
      }

      public static ProfilingProxyManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(sr, ProfilingProxyManagerType.type, options);
      }

      public static ProfilingProxyManagerType parse(Node node) throws XmlException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(node, ProfilingProxyManagerType.type, (XmlOptions)null);
      }

      public static ProfilingProxyManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(node, ProfilingProxyManagerType.type, options);
      }

      /** @deprecated */
      public static ProfilingProxyManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(xis, ProfilingProxyManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProfilingProxyManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProfilingProxyManagerType)XmlBeans.getContextTypeLoader().parse(xis, ProfilingProxyManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProfilingProxyManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProfilingProxyManagerType.type, options);
      }

      private Factory() {
      }
   }
}
