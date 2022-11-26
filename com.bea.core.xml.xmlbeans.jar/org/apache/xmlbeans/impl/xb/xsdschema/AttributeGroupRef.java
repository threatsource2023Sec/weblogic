package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlQName;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface AttributeGroupRef extends AttributeGroup {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AttributeGroupRef.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("attributegroupref8375type");

   QName getRef();

   XmlQName xgetRef();

   boolean isSetRef();

   void setRef(QName var1);

   void xsetRef(XmlQName var1);

   void unsetRef();

   public static final class Factory {
      public static AttributeGroupRef newInstance() {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().newInstance(AttributeGroupRef.type, (XmlOptions)null);
      }

      public static AttributeGroupRef newInstance(XmlOptions options) {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().newInstance(AttributeGroupRef.type, options);
      }

      public static AttributeGroupRef parse(String xmlAsString) throws XmlException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, AttributeGroupRef.type, (XmlOptions)null);
      }

      public static AttributeGroupRef parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse(xmlAsString, AttributeGroupRef.type, options);
      }

      public static AttributeGroupRef parse(File file) throws XmlException, IOException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse((File)file, AttributeGroupRef.type, (XmlOptions)null);
      }

      public static AttributeGroupRef parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse(file, AttributeGroupRef.type, options);
      }

      public static AttributeGroupRef parse(URL u) throws XmlException, IOException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse((URL)u, AttributeGroupRef.type, (XmlOptions)null);
      }

      public static AttributeGroupRef parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse(u, AttributeGroupRef.type, options);
      }

      public static AttributeGroupRef parse(InputStream is) throws XmlException, IOException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse((InputStream)is, AttributeGroupRef.type, (XmlOptions)null);
      }

      public static AttributeGroupRef parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse(is, AttributeGroupRef.type, options);
      }

      public static AttributeGroupRef parse(Reader r) throws XmlException, IOException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse((Reader)r, AttributeGroupRef.type, (XmlOptions)null);
      }

      public static AttributeGroupRef parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse(r, AttributeGroupRef.type, options);
      }

      public static AttributeGroupRef parse(XMLStreamReader sr) throws XmlException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, AttributeGroupRef.type, (XmlOptions)null);
      }

      public static AttributeGroupRef parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse(sr, AttributeGroupRef.type, options);
      }

      public static AttributeGroupRef parse(Node node) throws XmlException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse((Node)node, AttributeGroupRef.type, (XmlOptions)null);
      }

      public static AttributeGroupRef parse(Node node, XmlOptions options) throws XmlException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse(node, AttributeGroupRef.type, options);
      }

      /** @deprecated */
      public static AttributeGroupRef parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, AttributeGroupRef.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AttributeGroupRef parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AttributeGroupRef)XmlBeans.getContextTypeLoader().parse(xis, AttributeGroupRef.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AttributeGroupRef.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AttributeGroupRef.type, options);
      }

      private Factory() {
      }
   }
}
