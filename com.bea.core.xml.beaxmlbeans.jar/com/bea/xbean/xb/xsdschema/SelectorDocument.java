package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface SelectorDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SelectorDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("selectorcb44doctype");

   Selector getSelector();

   void setSelector(Selector var1);

   Selector addNewSelector();

   public static final class Factory {
      public static SelectorDocument newInstance() {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().newInstance(SelectorDocument.type, (XmlOptions)null);
      }

      public static SelectorDocument newInstance(XmlOptions options) {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().newInstance(SelectorDocument.type, options);
      }

      public static SelectorDocument parse(String xmlAsString) throws XmlException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, SelectorDocument.type, (XmlOptions)null);
      }

      public static SelectorDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, SelectorDocument.type, options);
      }

      public static SelectorDocument parse(File file) throws XmlException, IOException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse((File)file, SelectorDocument.type, (XmlOptions)null);
      }

      public static SelectorDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse(file, SelectorDocument.type, options);
      }

      public static SelectorDocument parse(URL u) throws XmlException, IOException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse((URL)u, SelectorDocument.type, (XmlOptions)null);
      }

      public static SelectorDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse(u, SelectorDocument.type, options);
      }

      public static SelectorDocument parse(InputStream is) throws XmlException, IOException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, SelectorDocument.type, (XmlOptions)null);
      }

      public static SelectorDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse(is, SelectorDocument.type, options);
      }

      public static SelectorDocument parse(Reader r) throws XmlException, IOException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, SelectorDocument.type, (XmlOptions)null);
      }

      public static SelectorDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse(r, SelectorDocument.type, options);
      }

      public static SelectorDocument parse(XMLStreamReader sr) throws XmlException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, SelectorDocument.type, (XmlOptions)null);
      }

      public static SelectorDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse(sr, SelectorDocument.type, options);
      }

      public static SelectorDocument parse(Node node) throws XmlException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse((Node)node, SelectorDocument.type, (XmlOptions)null);
      }

      public static SelectorDocument parse(Node node, XmlOptions options) throws XmlException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse(node, SelectorDocument.type, options);
      }

      /** @deprecated */
      public static SelectorDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, SelectorDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SelectorDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SelectorDocument)XmlBeans.getContextTypeLoader().parse(xis, SelectorDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SelectorDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SelectorDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Selector extends Annotated {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Selector.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("selector233felemtype");

      String getXpath();

      Xpath xgetXpath();

      void setXpath(String var1);

      void xsetXpath(Xpath var1);

      public static final class Factory {
         public static Selector newInstance() {
            return (Selector)XmlBeans.getContextTypeLoader().newInstance(SelectorDocument.Selector.type, (XmlOptions)null);
         }

         public static Selector newInstance(XmlOptions options) {
            return (Selector)XmlBeans.getContextTypeLoader().newInstance(SelectorDocument.Selector.type, options);
         }

         private Factory() {
         }
      }

      public interface Xpath extends XmlToken {
         SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Xpath.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("xpath6f9aattrtype");

         public static final class Factory {
            public static Xpath newValue(Object obj) {
               return (Xpath)SelectorDocument.Selector.Xpath.type.newValue(obj);
            }

            public static Xpath newInstance() {
               return (Xpath)XmlBeans.getContextTypeLoader().newInstance(SelectorDocument.Selector.Xpath.type, (XmlOptions)null);
            }

            public static Xpath newInstance(XmlOptions options) {
               return (Xpath)XmlBeans.getContextTypeLoader().newInstance(SelectorDocument.Selector.Xpath.type, options);
            }

            private Factory() {
            }
         }
      }
   }
}
