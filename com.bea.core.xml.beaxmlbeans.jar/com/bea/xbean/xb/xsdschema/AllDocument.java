package com.bea.xbean.xb.xsdschema;

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

public interface AllDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AllDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("all7214doctype");

   All getAll();

   void setAll(All var1);

   All addNewAll();

   public static final class Factory {
      public static AllDocument newInstance() {
         return (AllDocument)XmlBeans.getContextTypeLoader().newInstance(AllDocument.type, (XmlOptions)null);
      }

      public static AllDocument newInstance(XmlOptions options) {
         return (AllDocument)XmlBeans.getContextTypeLoader().newInstance(AllDocument.type, options);
      }

      public static AllDocument parse(String xmlAsString) throws XmlException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, AllDocument.type, (XmlOptions)null);
      }

      public static AllDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AllDocument.type, options);
      }

      public static AllDocument parse(File file) throws XmlException, IOException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse((File)file, AllDocument.type, (XmlOptions)null);
      }

      public static AllDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse(file, AllDocument.type, options);
      }

      public static AllDocument parse(URL u) throws XmlException, IOException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse((URL)u, AllDocument.type, (XmlOptions)null);
      }

      public static AllDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse(u, AllDocument.type, options);
      }

      public static AllDocument parse(InputStream is) throws XmlException, IOException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, AllDocument.type, (XmlOptions)null);
      }

      public static AllDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse(is, AllDocument.type, options);
      }

      public static AllDocument parse(Reader r) throws XmlException, IOException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, AllDocument.type, (XmlOptions)null);
      }

      public static AllDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse(r, AllDocument.type, options);
      }

      public static AllDocument parse(XMLStreamReader sr) throws XmlException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, AllDocument.type, (XmlOptions)null);
      }

      public static AllDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse(sr, AllDocument.type, options);
      }

      public static AllDocument parse(Node node) throws XmlException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse((Node)node, AllDocument.type, (XmlOptions)null);
      }

      public static AllDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse(node, AllDocument.type, options);
      }

      /** @deprecated */
      public static AllDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, AllDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AllDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AllDocument)XmlBeans.getContextTypeLoader().parse(xis, AllDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AllDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AllDocument.type, options);
      }

      private Factory() {
      }
   }
}
