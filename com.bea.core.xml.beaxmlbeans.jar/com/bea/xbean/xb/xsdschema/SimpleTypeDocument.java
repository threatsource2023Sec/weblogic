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

public interface SimpleTypeDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleTypeDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("simpletypedef7doctype");

   TopLevelSimpleType getSimpleType();

   void setSimpleType(TopLevelSimpleType var1);

   TopLevelSimpleType addNewSimpleType();

   public static final class Factory {
      public static SimpleTypeDocument newInstance() {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().newInstance(SimpleTypeDocument.type, (XmlOptions)null);
      }

      public static SimpleTypeDocument newInstance(XmlOptions options) {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().newInstance(SimpleTypeDocument.type, options);
      }

      public static SimpleTypeDocument parse(String xmlAsString) throws XmlException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, SimpleTypeDocument.type, (XmlOptions)null);
      }

      public static SimpleTypeDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleTypeDocument.type, options);
      }

      public static SimpleTypeDocument parse(File file) throws XmlException, IOException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse((File)file, SimpleTypeDocument.type, (XmlOptions)null);
      }

      public static SimpleTypeDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse(file, SimpleTypeDocument.type, options);
      }

      public static SimpleTypeDocument parse(URL u) throws XmlException, IOException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse((URL)u, SimpleTypeDocument.type, (XmlOptions)null);
      }

      public static SimpleTypeDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse(u, SimpleTypeDocument.type, options);
      }

      public static SimpleTypeDocument parse(InputStream is) throws XmlException, IOException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, SimpleTypeDocument.type, (XmlOptions)null);
      }

      public static SimpleTypeDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse(is, SimpleTypeDocument.type, options);
      }

      public static SimpleTypeDocument parse(Reader r) throws XmlException, IOException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, SimpleTypeDocument.type, (XmlOptions)null);
      }

      public static SimpleTypeDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse(r, SimpleTypeDocument.type, options);
      }

      public static SimpleTypeDocument parse(XMLStreamReader sr) throws XmlException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, SimpleTypeDocument.type, (XmlOptions)null);
      }

      public static SimpleTypeDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse(sr, SimpleTypeDocument.type, options);
      }

      public static SimpleTypeDocument parse(Node node) throws XmlException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse((Node)node, SimpleTypeDocument.type, (XmlOptions)null);
      }

      public static SimpleTypeDocument parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse(node, SimpleTypeDocument.type, options);
      }

      /** @deprecated */
      public static SimpleTypeDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, SimpleTypeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleTypeDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleTypeDocument)XmlBeans.getContextTypeLoader().parse(xis, SimpleTypeDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleTypeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleTypeDocument.type, options);
      }

      private Factory() {
      }
   }
}
