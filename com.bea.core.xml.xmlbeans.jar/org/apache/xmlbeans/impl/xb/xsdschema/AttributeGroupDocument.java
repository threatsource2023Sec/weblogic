package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface AttributeGroupDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AttributeGroupDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("attributegroup4520doctype");

   NamedAttributeGroup getAttributeGroup();

   void setAttributeGroup(NamedAttributeGroup var1);

   NamedAttributeGroup addNewAttributeGroup();

   public static final class Factory {
      public static AttributeGroupDocument newInstance() {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().newInstance(AttributeGroupDocument.type, (XmlOptions)null);
      }

      public static AttributeGroupDocument newInstance(XmlOptions options) {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().newInstance(AttributeGroupDocument.type, options);
      }

      public static AttributeGroupDocument parse(String xmlAsString) throws XmlException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, AttributeGroupDocument.type, (XmlOptions)null);
      }

      public static AttributeGroupDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AttributeGroupDocument.type, options);
      }

      public static AttributeGroupDocument parse(File file) throws XmlException, IOException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse((File)file, AttributeGroupDocument.type, (XmlOptions)null);
      }

      public static AttributeGroupDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse(file, AttributeGroupDocument.type, options);
      }

      public static AttributeGroupDocument parse(URL u) throws XmlException, IOException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse((URL)u, AttributeGroupDocument.type, (XmlOptions)null);
      }

      public static AttributeGroupDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse(u, AttributeGroupDocument.type, options);
      }

      public static AttributeGroupDocument parse(InputStream is) throws XmlException, IOException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, AttributeGroupDocument.type, (XmlOptions)null);
      }

      public static AttributeGroupDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse(is, AttributeGroupDocument.type, options);
      }

      public static AttributeGroupDocument parse(Reader r) throws XmlException, IOException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, AttributeGroupDocument.type, (XmlOptions)null);
      }

      public static AttributeGroupDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse(r, AttributeGroupDocument.type, options);
      }

      public static AttributeGroupDocument parse(XMLStreamReader sr) throws XmlException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, AttributeGroupDocument.type, (XmlOptions)null);
      }

      public static AttributeGroupDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse(sr, AttributeGroupDocument.type, options);
      }

      public static AttributeGroupDocument parse(Node node) throws XmlException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse((Node)node, AttributeGroupDocument.type, (XmlOptions)null);
      }

      public static AttributeGroupDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse(node, AttributeGroupDocument.type, options);
      }

      /** @deprecated */
      public static AttributeGroupDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, AttributeGroupDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AttributeGroupDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AttributeGroupDocument)XmlBeans.getContextTypeLoader().parse(xis, AttributeGroupDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AttributeGroupDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AttributeGroupDocument.type, options);
      }

      private Factory() {
      }
   }
}
