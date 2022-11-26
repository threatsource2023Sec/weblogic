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

public interface ExplicitGroup extends Group {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExplicitGroup.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("explicitgroup4efatype");

   public static final class Factory {
      public static ExplicitGroup newInstance() {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().newInstance(ExplicitGroup.type, (XmlOptions)null);
      }

      public static ExplicitGroup newInstance(XmlOptions options) {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().newInstance(ExplicitGroup.type, options);
      }

      public static ExplicitGroup parse(String xmlAsString) throws XmlException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, ExplicitGroup.type, (XmlOptions)null);
      }

      public static ExplicitGroup parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExplicitGroup.type, options);
      }

      public static ExplicitGroup parse(File file) throws XmlException, IOException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse((File)file, ExplicitGroup.type, (XmlOptions)null);
      }

      public static ExplicitGroup parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse(file, ExplicitGroup.type, options);
      }

      public static ExplicitGroup parse(URL u) throws XmlException, IOException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse((URL)u, ExplicitGroup.type, (XmlOptions)null);
      }

      public static ExplicitGroup parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse(u, ExplicitGroup.type, options);
      }

      public static ExplicitGroup parse(InputStream is) throws XmlException, IOException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse((InputStream)is, ExplicitGroup.type, (XmlOptions)null);
      }

      public static ExplicitGroup parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse(is, ExplicitGroup.type, options);
      }

      public static ExplicitGroup parse(Reader r) throws XmlException, IOException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse((Reader)r, ExplicitGroup.type, (XmlOptions)null);
      }

      public static ExplicitGroup parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse(r, ExplicitGroup.type, options);
      }

      public static ExplicitGroup parse(XMLStreamReader sr) throws XmlException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, ExplicitGroup.type, (XmlOptions)null);
      }

      public static ExplicitGroup parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse(sr, ExplicitGroup.type, options);
      }

      public static ExplicitGroup parse(Node node) throws XmlException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse((Node)node, ExplicitGroup.type, (XmlOptions)null);
      }

      public static ExplicitGroup parse(Node node, XmlOptions options) throws XmlException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse(node, ExplicitGroup.type, options);
      }

      /** @deprecated */
      public static ExplicitGroup parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, ExplicitGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExplicitGroup parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExplicitGroup)XmlBeans.getContextTypeLoader().parse(xis, ExplicitGroup.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExplicitGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExplicitGroup.type, options);
      }

      private Factory() {
      }
   }
}
