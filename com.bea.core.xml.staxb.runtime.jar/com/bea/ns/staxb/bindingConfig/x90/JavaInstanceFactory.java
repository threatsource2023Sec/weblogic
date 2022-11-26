package com.bea.ns.staxb.bindingConfig.x90;

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

public interface JavaInstanceFactory extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaInstanceFactory.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("javainstancefactoryf2c8type");

   public static final class Factory {
      /** @deprecated */
      public static JavaInstanceFactory newInstance() {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().newInstance(JavaInstanceFactory.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaInstanceFactory newInstance(XmlOptions options) {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().newInstance(JavaInstanceFactory.type, options);
      }

      public static JavaInstanceFactory parse(String xmlAsString) throws XmlException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaInstanceFactory.type, (XmlOptions)null);
      }

      public static JavaInstanceFactory parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaInstanceFactory.type, options);
      }

      public static JavaInstanceFactory parse(File file) throws XmlException, IOException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(file, JavaInstanceFactory.type, (XmlOptions)null);
      }

      public static JavaInstanceFactory parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(file, JavaInstanceFactory.type, options);
      }

      public static JavaInstanceFactory parse(URL u) throws XmlException, IOException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(u, JavaInstanceFactory.type, (XmlOptions)null);
      }

      public static JavaInstanceFactory parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(u, JavaInstanceFactory.type, options);
      }

      public static JavaInstanceFactory parse(InputStream is) throws XmlException, IOException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(is, JavaInstanceFactory.type, (XmlOptions)null);
      }

      public static JavaInstanceFactory parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(is, JavaInstanceFactory.type, options);
      }

      public static JavaInstanceFactory parse(Reader r) throws XmlException, IOException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(r, JavaInstanceFactory.type, (XmlOptions)null);
      }

      public static JavaInstanceFactory parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(r, JavaInstanceFactory.type, options);
      }

      public static JavaInstanceFactory parse(XMLStreamReader sr) throws XmlException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(sr, JavaInstanceFactory.type, (XmlOptions)null);
      }

      public static JavaInstanceFactory parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(sr, JavaInstanceFactory.type, options);
      }

      public static JavaInstanceFactory parse(Node node) throws XmlException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(node, JavaInstanceFactory.type, (XmlOptions)null);
      }

      public static JavaInstanceFactory parse(Node node, XmlOptions options) throws XmlException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(node, JavaInstanceFactory.type, options);
      }

      /** @deprecated */
      public static JavaInstanceFactory parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(xis, JavaInstanceFactory.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaInstanceFactory parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaInstanceFactory)XmlBeans.getContextTypeLoader().parse(xis, JavaInstanceFactory.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaInstanceFactory.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaInstanceFactory.type, options);
      }

      private Factory() {
      }
   }
}
