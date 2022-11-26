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

public interface MaxLengthDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MaxLengthDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("maxlengthf8abdoctype");

   NumFacet getMaxLength();

   void setMaxLength(NumFacet var1);

   NumFacet addNewMaxLength();

   public static final class Factory {
      public static MaxLengthDocument newInstance() {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().newInstance(MaxLengthDocument.type, (XmlOptions)null);
      }

      public static MaxLengthDocument newInstance(XmlOptions options) {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().newInstance(MaxLengthDocument.type, options);
      }

      public static MaxLengthDocument parse(String xmlAsString) throws XmlException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, MaxLengthDocument.type, (XmlOptions)null);
      }

      public static MaxLengthDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MaxLengthDocument.type, options);
      }

      public static MaxLengthDocument parse(File file) throws XmlException, IOException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse((File)file, MaxLengthDocument.type, (XmlOptions)null);
      }

      public static MaxLengthDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse(file, MaxLengthDocument.type, options);
      }

      public static MaxLengthDocument parse(URL u) throws XmlException, IOException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse((URL)u, MaxLengthDocument.type, (XmlOptions)null);
      }

      public static MaxLengthDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse(u, MaxLengthDocument.type, options);
      }

      public static MaxLengthDocument parse(InputStream is) throws XmlException, IOException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, MaxLengthDocument.type, (XmlOptions)null);
      }

      public static MaxLengthDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse(is, MaxLengthDocument.type, options);
      }

      public static MaxLengthDocument parse(Reader r) throws XmlException, IOException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, MaxLengthDocument.type, (XmlOptions)null);
      }

      public static MaxLengthDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse(r, MaxLengthDocument.type, options);
      }

      public static MaxLengthDocument parse(XMLStreamReader sr) throws XmlException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, MaxLengthDocument.type, (XmlOptions)null);
      }

      public static MaxLengthDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse(sr, MaxLengthDocument.type, options);
      }

      public static MaxLengthDocument parse(Node node) throws XmlException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse((Node)node, MaxLengthDocument.type, (XmlOptions)null);
      }

      public static MaxLengthDocument parse(Node node, XmlOptions options) throws XmlException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse(node, MaxLengthDocument.type, options);
      }

      /** @deprecated */
      public static MaxLengthDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, MaxLengthDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MaxLengthDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MaxLengthDocument)XmlBeans.getContextTypeLoader().parse(xis, MaxLengthDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MaxLengthDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MaxLengthDocument.type, options);
      }

      private Factory() {
      }
   }
}
