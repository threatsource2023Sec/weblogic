package com.bea.xbean.xb.xmlconfig;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface JavaName extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaName.class.getClassLoader(), "schemacom_bea_xml.system.sXMLCONFIG").resolveHandle("javanamee640type");

   public static final class Factory {
      public static JavaName newValue(Object obj) {
         return (JavaName)JavaName.type.newValue(obj);
      }

      public static JavaName newInstance() {
         return (JavaName)XmlBeans.getContextTypeLoader().newInstance(JavaName.type, (XmlOptions)null);
      }

      public static JavaName newInstance(XmlOptions options) {
         return (JavaName)XmlBeans.getContextTypeLoader().newInstance(JavaName.type, options);
      }

      public static JavaName parse(String xmlAsString) throws XmlException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, JavaName.type, (XmlOptions)null);
      }

      public static JavaName parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaName.type, options);
      }

      public static JavaName parse(File file) throws XmlException, IOException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse((File)file, JavaName.type, (XmlOptions)null);
      }

      public static JavaName parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse(file, JavaName.type, options);
      }

      public static JavaName parse(URL u) throws XmlException, IOException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse((URL)u, JavaName.type, (XmlOptions)null);
      }

      public static JavaName parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse(u, JavaName.type, options);
      }

      public static JavaName parse(InputStream is) throws XmlException, IOException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse((InputStream)is, JavaName.type, (XmlOptions)null);
      }

      public static JavaName parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse(is, JavaName.type, options);
      }

      public static JavaName parse(Reader r) throws XmlException, IOException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse((Reader)r, JavaName.type, (XmlOptions)null);
      }

      public static JavaName parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse(r, JavaName.type, options);
      }

      public static JavaName parse(XMLStreamReader sr) throws XmlException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, JavaName.type, (XmlOptions)null);
      }

      public static JavaName parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse(sr, JavaName.type, options);
      }

      public static JavaName parse(Node node) throws XmlException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse((Node)node, JavaName.type, (XmlOptions)null);
      }

      public static JavaName parse(Node node, XmlOptions options) throws XmlException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse(node, JavaName.type, options);
      }

      /** @deprecated */
      public static JavaName parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, JavaName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaName parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaName)XmlBeans.getContextTypeLoader().parse(xis, JavaName.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaName.type, options);
      }

      private Factory() {
      }
   }
}
