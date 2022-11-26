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

public interface ComplexTypeDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ComplexTypeDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("complextype83cbdoctype");

   TopLevelComplexType getComplexType();

   void setComplexType(TopLevelComplexType var1);

   TopLevelComplexType addNewComplexType();

   public static final class Factory {
      public static ComplexTypeDocument newInstance() {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().newInstance(ComplexTypeDocument.type, (XmlOptions)null);
      }

      public static ComplexTypeDocument newInstance(XmlOptions options) {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().newInstance(ComplexTypeDocument.type, options);
      }

      public static ComplexTypeDocument parse(String xmlAsString) throws XmlException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, ComplexTypeDocument.type, (XmlOptions)null);
      }

      public static ComplexTypeDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ComplexTypeDocument.type, options);
      }

      public static ComplexTypeDocument parse(File file) throws XmlException, IOException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse((File)file, ComplexTypeDocument.type, (XmlOptions)null);
      }

      public static ComplexTypeDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse(file, ComplexTypeDocument.type, options);
      }

      public static ComplexTypeDocument parse(URL u) throws XmlException, IOException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse((URL)u, ComplexTypeDocument.type, (XmlOptions)null);
      }

      public static ComplexTypeDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse(u, ComplexTypeDocument.type, options);
      }

      public static ComplexTypeDocument parse(InputStream is) throws XmlException, IOException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, ComplexTypeDocument.type, (XmlOptions)null);
      }

      public static ComplexTypeDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse(is, ComplexTypeDocument.type, options);
      }

      public static ComplexTypeDocument parse(Reader r) throws XmlException, IOException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, ComplexTypeDocument.type, (XmlOptions)null);
      }

      public static ComplexTypeDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse(r, ComplexTypeDocument.type, options);
      }

      public static ComplexTypeDocument parse(XMLStreamReader sr) throws XmlException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, ComplexTypeDocument.type, (XmlOptions)null);
      }

      public static ComplexTypeDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse(sr, ComplexTypeDocument.type, options);
      }

      public static ComplexTypeDocument parse(Node node) throws XmlException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse((Node)node, ComplexTypeDocument.type, (XmlOptions)null);
      }

      public static ComplexTypeDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse(node, ComplexTypeDocument.type, options);
      }

      /** @deprecated */
      public static ComplexTypeDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, ComplexTypeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ComplexTypeDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ComplexTypeDocument)XmlBeans.getContextTypeLoader().parse(xis, ComplexTypeDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ComplexTypeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ComplexTypeDocument.type, options);
      }

      private Factory() {
      }
   }
}
