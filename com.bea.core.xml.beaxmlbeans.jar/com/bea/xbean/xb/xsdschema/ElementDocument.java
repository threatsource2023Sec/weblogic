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

public interface ElementDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ElementDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("element7f99doctype");

   TopLevelElement getElement();

   void setElement(TopLevelElement var1);

   TopLevelElement addNewElement();

   public static final class Factory {
      public static ElementDocument newInstance() {
         return (ElementDocument)XmlBeans.getContextTypeLoader().newInstance(ElementDocument.type, (XmlOptions)null);
      }

      public static ElementDocument newInstance(XmlOptions options) {
         return (ElementDocument)XmlBeans.getContextTypeLoader().newInstance(ElementDocument.type, options);
      }

      public static ElementDocument parse(String xmlAsString) throws XmlException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, ElementDocument.type, (XmlOptions)null);
      }

      public static ElementDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ElementDocument.type, options);
      }

      public static ElementDocument parse(File file) throws XmlException, IOException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse((File)file, ElementDocument.type, (XmlOptions)null);
      }

      public static ElementDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse(file, ElementDocument.type, options);
      }

      public static ElementDocument parse(URL u) throws XmlException, IOException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse((URL)u, ElementDocument.type, (XmlOptions)null);
      }

      public static ElementDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse(u, ElementDocument.type, options);
      }

      public static ElementDocument parse(InputStream is) throws XmlException, IOException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, ElementDocument.type, (XmlOptions)null);
      }

      public static ElementDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse(is, ElementDocument.type, options);
      }

      public static ElementDocument parse(Reader r) throws XmlException, IOException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, ElementDocument.type, (XmlOptions)null);
      }

      public static ElementDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse(r, ElementDocument.type, options);
      }

      public static ElementDocument parse(XMLStreamReader sr) throws XmlException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, ElementDocument.type, (XmlOptions)null);
      }

      public static ElementDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse(sr, ElementDocument.type, options);
      }

      public static ElementDocument parse(Node node) throws XmlException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse((Node)node, ElementDocument.type, (XmlOptions)null);
      }

      public static ElementDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse(node, ElementDocument.type, options);
      }

      /** @deprecated */
      public static ElementDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, ElementDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ElementDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ElementDocument)XmlBeans.getContextTypeLoader().parse(xis, ElementDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ElementDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ElementDocument.type, options);
      }

      private Factory() {
      }
   }
}
