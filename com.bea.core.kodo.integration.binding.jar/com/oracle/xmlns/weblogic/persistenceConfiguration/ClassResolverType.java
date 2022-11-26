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

public interface ClassResolverType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClassResolverType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("classresolvertypebddftype");

   public static final class Factory {
      public static ClassResolverType newInstance() {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().newInstance(ClassResolverType.type, (XmlOptions)null);
      }

      public static ClassResolverType newInstance(XmlOptions options) {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().newInstance(ClassResolverType.type, options);
      }

      public static ClassResolverType parse(String xmlAsString) throws XmlException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClassResolverType.type, (XmlOptions)null);
      }

      public static ClassResolverType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClassResolverType.type, options);
      }

      public static ClassResolverType parse(File file) throws XmlException, IOException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(file, ClassResolverType.type, (XmlOptions)null);
      }

      public static ClassResolverType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(file, ClassResolverType.type, options);
      }

      public static ClassResolverType parse(URL u) throws XmlException, IOException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(u, ClassResolverType.type, (XmlOptions)null);
      }

      public static ClassResolverType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(u, ClassResolverType.type, options);
      }

      public static ClassResolverType parse(InputStream is) throws XmlException, IOException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(is, ClassResolverType.type, (XmlOptions)null);
      }

      public static ClassResolverType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(is, ClassResolverType.type, options);
      }

      public static ClassResolverType parse(Reader r) throws XmlException, IOException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(r, ClassResolverType.type, (XmlOptions)null);
      }

      public static ClassResolverType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(r, ClassResolverType.type, options);
      }

      public static ClassResolverType parse(XMLStreamReader sr) throws XmlException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(sr, ClassResolverType.type, (XmlOptions)null);
      }

      public static ClassResolverType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(sr, ClassResolverType.type, options);
      }

      public static ClassResolverType parse(Node node) throws XmlException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(node, ClassResolverType.type, (XmlOptions)null);
      }

      public static ClassResolverType parse(Node node, XmlOptions options) throws XmlException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(node, ClassResolverType.type, options);
      }

      /** @deprecated */
      public static ClassResolverType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(xis, ClassResolverType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClassResolverType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClassResolverType)XmlBeans.getContextTypeLoader().parse(xis, ClassResolverType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClassResolverType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClassResolverType.type, options);
      }

      private Factory() {
      }
   }
}
