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

public interface NumFacet extends Facet {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NumFacet.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("numfacet93a2type");

   public static final class Factory {
      public static NumFacet newInstance() {
         return (NumFacet)XmlBeans.getContextTypeLoader().newInstance(NumFacet.type, (XmlOptions)null);
      }

      public static NumFacet newInstance(XmlOptions options) {
         return (NumFacet)XmlBeans.getContextTypeLoader().newInstance(NumFacet.type, options);
      }

      public static NumFacet parse(String xmlAsString) throws XmlException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, NumFacet.type, (XmlOptions)null);
      }

      public static NumFacet parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse(xmlAsString, NumFacet.type, options);
      }

      public static NumFacet parse(File file) throws XmlException, IOException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse((File)file, NumFacet.type, (XmlOptions)null);
      }

      public static NumFacet parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse(file, NumFacet.type, options);
      }

      public static NumFacet parse(URL u) throws XmlException, IOException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse((URL)u, NumFacet.type, (XmlOptions)null);
      }

      public static NumFacet parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse(u, NumFacet.type, options);
      }

      public static NumFacet parse(InputStream is) throws XmlException, IOException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse((InputStream)is, NumFacet.type, (XmlOptions)null);
      }

      public static NumFacet parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse(is, NumFacet.type, options);
      }

      public static NumFacet parse(Reader r) throws XmlException, IOException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse((Reader)r, NumFacet.type, (XmlOptions)null);
      }

      public static NumFacet parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse(r, NumFacet.type, options);
      }

      public static NumFacet parse(XMLStreamReader sr) throws XmlException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, NumFacet.type, (XmlOptions)null);
      }

      public static NumFacet parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse(sr, NumFacet.type, options);
      }

      public static NumFacet parse(Node node) throws XmlException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse((Node)node, NumFacet.type, (XmlOptions)null);
      }

      public static NumFacet parse(Node node, XmlOptions options) throws XmlException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse(node, NumFacet.type, options);
      }

      /** @deprecated */
      public static NumFacet parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, NumFacet.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NumFacet parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NumFacet)XmlBeans.getContextTypeLoader().parse(xis, NumFacet.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NumFacet.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NumFacet.type, options);
      }

      private Factory() {
      }
   }
}
