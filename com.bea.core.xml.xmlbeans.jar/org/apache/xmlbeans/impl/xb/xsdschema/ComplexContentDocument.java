package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlBoolean;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface ComplexContentDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ComplexContentDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("complexcontentc57adoctype");

   ComplexContent getComplexContent();

   void setComplexContent(ComplexContent var1);

   ComplexContent addNewComplexContent();

   public static final class Factory {
      public static ComplexContentDocument newInstance() {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().newInstance(ComplexContentDocument.type, (XmlOptions)null);
      }

      public static ComplexContentDocument newInstance(XmlOptions options) {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().newInstance(ComplexContentDocument.type, options);
      }

      public static ComplexContentDocument parse(String xmlAsString) throws XmlException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, ComplexContentDocument.type, (XmlOptions)null);
      }

      public static ComplexContentDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ComplexContentDocument.type, options);
      }

      public static ComplexContentDocument parse(File file) throws XmlException, IOException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse((File)file, ComplexContentDocument.type, (XmlOptions)null);
      }

      public static ComplexContentDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse(file, ComplexContentDocument.type, options);
      }

      public static ComplexContentDocument parse(URL u) throws XmlException, IOException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse((URL)u, ComplexContentDocument.type, (XmlOptions)null);
      }

      public static ComplexContentDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse(u, ComplexContentDocument.type, options);
      }

      public static ComplexContentDocument parse(InputStream is) throws XmlException, IOException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, ComplexContentDocument.type, (XmlOptions)null);
      }

      public static ComplexContentDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse(is, ComplexContentDocument.type, options);
      }

      public static ComplexContentDocument parse(Reader r) throws XmlException, IOException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, ComplexContentDocument.type, (XmlOptions)null);
      }

      public static ComplexContentDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse(r, ComplexContentDocument.type, options);
      }

      public static ComplexContentDocument parse(XMLStreamReader sr) throws XmlException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, ComplexContentDocument.type, (XmlOptions)null);
      }

      public static ComplexContentDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse(sr, ComplexContentDocument.type, options);
      }

      public static ComplexContentDocument parse(Node node) throws XmlException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse((Node)node, ComplexContentDocument.type, (XmlOptions)null);
      }

      public static ComplexContentDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse(node, ComplexContentDocument.type, options);
      }

      /** @deprecated */
      public static ComplexContentDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, ComplexContentDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ComplexContentDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ComplexContentDocument)XmlBeans.getContextTypeLoader().parse(xis, ComplexContentDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ComplexContentDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ComplexContentDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface ComplexContent extends Annotated {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ComplexContent.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("complexcontentaa7felemtype");

      ComplexRestrictionType getRestriction();

      boolean isSetRestriction();

      void setRestriction(ComplexRestrictionType var1);

      ComplexRestrictionType addNewRestriction();

      void unsetRestriction();

      ExtensionType getExtension();

      boolean isSetExtension();

      void setExtension(ExtensionType var1);

      ExtensionType addNewExtension();

      void unsetExtension();

      boolean getMixed();

      XmlBoolean xgetMixed();

      boolean isSetMixed();

      void setMixed(boolean var1);

      void xsetMixed(XmlBoolean var1);

      void unsetMixed();

      public static final class Factory {
         public static ComplexContent newInstance() {
            return (ComplexContent)XmlBeans.getContextTypeLoader().newInstance(ComplexContentDocument.ComplexContent.type, (XmlOptions)null);
         }

         public static ComplexContent newInstance(XmlOptions options) {
            return (ComplexContent)XmlBeans.getContextTypeLoader().newInstance(ComplexContentDocument.ComplexContent.type, options);
         }

         private Factory() {
         }
      }
   }
}
