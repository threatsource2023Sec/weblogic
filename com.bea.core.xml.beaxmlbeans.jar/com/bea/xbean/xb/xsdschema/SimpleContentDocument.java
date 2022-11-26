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

public interface SimpleContentDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleContentDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("simplecontent8acedoctype");

   SimpleContent getSimpleContent();

   void setSimpleContent(SimpleContent var1);

   SimpleContent addNewSimpleContent();

   public static final class Factory {
      public static SimpleContentDocument newInstance() {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().newInstance(SimpleContentDocument.type, (XmlOptions)null);
      }

      public static SimpleContentDocument newInstance(XmlOptions options) {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().newInstance(SimpleContentDocument.type, options);
      }

      public static SimpleContentDocument parse(String xmlAsString) throws XmlException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, SimpleContentDocument.type, (XmlOptions)null);
      }

      public static SimpleContentDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleContentDocument.type, options);
      }

      public static SimpleContentDocument parse(File file) throws XmlException, IOException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse((File)file, SimpleContentDocument.type, (XmlOptions)null);
      }

      public static SimpleContentDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse(file, SimpleContentDocument.type, options);
      }

      public static SimpleContentDocument parse(URL u) throws XmlException, IOException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse((URL)u, SimpleContentDocument.type, (XmlOptions)null);
      }

      public static SimpleContentDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse(u, SimpleContentDocument.type, options);
      }

      public static SimpleContentDocument parse(InputStream is) throws XmlException, IOException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, SimpleContentDocument.type, (XmlOptions)null);
      }

      public static SimpleContentDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse(is, SimpleContentDocument.type, options);
      }

      public static SimpleContentDocument parse(Reader r) throws XmlException, IOException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, SimpleContentDocument.type, (XmlOptions)null);
      }

      public static SimpleContentDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse(r, SimpleContentDocument.type, options);
      }

      public static SimpleContentDocument parse(XMLStreamReader sr) throws XmlException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, SimpleContentDocument.type, (XmlOptions)null);
      }

      public static SimpleContentDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse(sr, SimpleContentDocument.type, options);
      }

      public static SimpleContentDocument parse(Node node) throws XmlException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse((Node)node, SimpleContentDocument.type, (XmlOptions)null);
      }

      public static SimpleContentDocument parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse(node, SimpleContentDocument.type, options);
      }

      /** @deprecated */
      public static SimpleContentDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, SimpleContentDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleContentDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleContentDocument)XmlBeans.getContextTypeLoader().parse(xis, SimpleContentDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleContentDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleContentDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface SimpleContent extends Annotated {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleContent.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("simplecontent9a5belemtype");

      SimpleRestrictionType getRestriction();

      boolean isSetRestriction();

      void setRestriction(SimpleRestrictionType var1);

      SimpleRestrictionType addNewRestriction();

      void unsetRestriction();

      SimpleExtensionType getExtension();

      boolean isSetExtension();

      void setExtension(SimpleExtensionType var1);

      SimpleExtensionType addNewExtension();

      void unsetExtension();

      public static final class Factory {
         public static SimpleContent newInstance() {
            return (SimpleContent)XmlBeans.getContextTypeLoader().newInstance(SimpleContentDocument.SimpleContent.type, (XmlOptions)null);
         }

         public static SimpleContent newInstance(XmlOptions options) {
            return (SimpleContent)XmlBeans.getContextTypeLoader().newInstance(SimpleContentDocument.SimpleContent.type, options);
         }

         private Factory() {
         }
      }
   }
}
