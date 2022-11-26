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

public interface GroupRef extends RealGroup {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GroupRef.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("groupref303ftype");

   QName getRef();

   XmlQName xgetRef();

   boolean isSetRef();

   void setRef(QName var1);

   void xsetRef(XmlQName var1);

   void unsetRef();

   public static final class Factory {
      public static GroupRef newInstance() {
         return (GroupRef)XmlBeans.getContextTypeLoader().newInstance(GroupRef.type, (XmlOptions)null);
      }

      public static GroupRef newInstance(XmlOptions options) {
         return (GroupRef)XmlBeans.getContextTypeLoader().newInstance(GroupRef.type, options);
      }

      public static GroupRef parse(String xmlAsString) throws XmlException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, GroupRef.type, (XmlOptions)null);
      }

      public static GroupRef parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse(xmlAsString, GroupRef.type, options);
      }

      public static GroupRef parse(File file) throws XmlException, IOException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse((File)file, GroupRef.type, (XmlOptions)null);
      }

      public static GroupRef parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse(file, GroupRef.type, options);
      }

      public static GroupRef parse(URL u) throws XmlException, IOException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse((URL)u, GroupRef.type, (XmlOptions)null);
      }

      public static GroupRef parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse(u, GroupRef.type, options);
      }

      public static GroupRef parse(InputStream is) throws XmlException, IOException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse((InputStream)is, GroupRef.type, (XmlOptions)null);
      }

      public static GroupRef parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse(is, GroupRef.type, options);
      }

      public static GroupRef parse(Reader r) throws XmlException, IOException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse((Reader)r, GroupRef.type, (XmlOptions)null);
      }

      public static GroupRef parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse(r, GroupRef.type, options);
      }

      public static GroupRef parse(XMLStreamReader sr) throws XmlException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, GroupRef.type, (XmlOptions)null);
      }

      public static GroupRef parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse(sr, GroupRef.type, options);
      }

      public static GroupRef parse(Node node) throws XmlException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse((Node)node, GroupRef.type, (XmlOptions)null);
      }

      public static GroupRef parse(Node node, XmlOptions options) throws XmlException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse(node, GroupRef.type, options);
      }

      /** @deprecated */
      public static GroupRef parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, GroupRef.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GroupRef parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GroupRef)XmlBeans.getContextTypeLoader().parse(xis, GroupRef.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GroupRef.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GroupRef.type, options);
      }

      private Factory() {
      }
   }
}
