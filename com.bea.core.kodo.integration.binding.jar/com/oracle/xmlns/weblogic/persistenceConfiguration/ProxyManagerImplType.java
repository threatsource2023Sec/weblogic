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

public interface ProxyManagerImplType extends ProxyManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProxyManagerImplType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("proxymanagerimpltype115btype");

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
      public static ProxyManagerImplType newInstance() {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().newInstance(ProxyManagerImplType.type, (XmlOptions)null);
      }

      public static ProxyManagerImplType newInstance(XmlOptions options) {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().newInstance(ProxyManagerImplType.type, options);
      }

      public static ProxyManagerImplType parse(String xmlAsString) throws XmlException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProxyManagerImplType.type, (XmlOptions)null);
      }

      public static ProxyManagerImplType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProxyManagerImplType.type, options);
      }

      public static ProxyManagerImplType parse(File file) throws XmlException, IOException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(file, ProxyManagerImplType.type, (XmlOptions)null);
      }

      public static ProxyManagerImplType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(file, ProxyManagerImplType.type, options);
      }

      public static ProxyManagerImplType parse(URL u) throws XmlException, IOException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(u, ProxyManagerImplType.type, (XmlOptions)null);
      }

      public static ProxyManagerImplType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(u, ProxyManagerImplType.type, options);
      }

      public static ProxyManagerImplType parse(InputStream is) throws XmlException, IOException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(is, ProxyManagerImplType.type, (XmlOptions)null);
      }

      public static ProxyManagerImplType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(is, ProxyManagerImplType.type, options);
      }

      public static ProxyManagerImplType parse(Reader r) throws XmlException, IOException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(r, ProxyManagerImplType.type, (XmlOptions)null);
      }

      public static ProxyManagerImplType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(r, ProxyManagerImplType.type, options);
      }

      public static ProxyManagerImplType parse(XMLStreamReader sr) throws XmlException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(sr, ProxyManagerImplType.type, (XmlOptions)null);
      }

      public static ProxyManagerImplType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(sr, ProxyManagerImplType.type, options);
      }

      public static ProxyManagerImplType parse(Node node) throws XmlException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(node, ProxyManagerImplType.type, (XmlOptions)null);
      }

      public static ProxyManagerImplType parse(Node node, XmlOptions options) throws XmlException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(node, ProxyManagerImplType.type, options);
      }

      /** @deprecated */
      public static ProxyManagerImplType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(xis, ProxyManagerImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProxyManagerImplType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProxyManagerImplType)XmlBeans.getContextTypeLoader().parse(xis, ProxyManagerImplType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProxyManagerImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProxyManagerImplType.type, options);
      }

      private Factory() {
      }
   }
}
