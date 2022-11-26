package com.bea.xbean.xb.xsdschema;

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

public interface SimpleExplicitGroup extends ExplicitGroup {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleExplicitGroup.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("simpleexplicitgroup428ctype");

   public static final class Factory {
      public static SimpleExplicitGroup newInstance() {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().newInstance(SimpleExplicitGroup.type, (XmlOptions)null);
      }

      public static SimpleExplicitGroup newInstance(XmlOptions options) {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().newInstance(SimpleExplicitGroup.type, options);
      }

      public static SimpleExplicitGroup parse(String xmlAsString) throws XmlException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, SimpleExplicitGroup.type, (XmlOptions)null);
      }

      public static SimpleExplicitGroup parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleExplicitGroup.type, options);
      }

      public static SimpleExplicitGroup parse(File file) throws XmlException, IOException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse((File)file, SimpleExplicitGroup.type, (XmlOptions)null);
      }

      public static SimpleExplicitGroup parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse(file, SimpleExplicitGroup.type, options);
      }

      public static SimpleExplicitGroup parse(URL u) throws XmlException, IOException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse((URL)u, SimpleExplicitGroup.type, (XmlOptions)null);
      }

      public static SimpleExplicitGroup parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse(u, SimpleExplicitGroup.type, options);
      }

      public static SimpleExplicitGroup parse(InputStream is) throws XmlException, IOException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse((InputStream)is, SimpleExplicitGroup.type, (XmlOptions)null);
      }

      public static SimpleExplicitGroup parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse(is, SimpleExplicitGroup.type, options);
      }

      public static SimpleExplicitGroup parse(Reader r) throws XmlException, IOException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse((Reader)r, SimpleExplicitGroup.type, (XmlOptions)null);
      }

      public static SimpleExplicitGroup parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse(r, SimpleExplicitGroup.type, options);
      }

      public static SimpleExplicitGroup parse(XMLStreamReader sr) throws XmlException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, SimpleExplicitGroup.type, (XmlOptions)null);
      }

      public static SimpleExplicitGroup parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse(sr, SimpleExplicitGroup.type, options);
      }

      public static SimpleExplicitGroup parse(Node node) throws XmlException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse((Node)node, SimpleExplicitGroup.type, (XmlOptions)null);
      }

      public static SimpleExplicitGroup parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse(node, SimpleExplicitGroup.type, options);
      }

      /** @deprecated */
      public static SimpleExplicitGroup parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, SimpleExplicitGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleExplicitGroup parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleExplicitGroup)XmlBeans.getContextTypeLoader().parse(xis, SimpleExplicitGroup.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleExplicitGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleExplicitGroup.type, options);
      }

      private Factory() {
      }
   }
}
