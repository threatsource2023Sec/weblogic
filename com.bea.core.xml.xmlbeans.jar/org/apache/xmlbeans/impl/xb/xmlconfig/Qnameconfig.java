package org.apache.xmlbeans.impl.xb.xmlconfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlQName;
import org.apache.xmlbeans.XmlString;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface Qnameconfig extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Qnameconfig.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLCONFIG").resolveHandle("qnameconfig463ftype");

   QName getName();

   XmlQName xgetName();

   boolean isSetName();

   void setName(QName var1);

   void xsetName(XmlQName var1);

   void unsetName();

   String getJavaname();

   XmlString xgetJavaname();

   boolean isSetJavaname();

   void setJavaname(String var1);

   void xsetJavaname(XmlString var1);

   void unsetJavaname();

   List getTarget();

   Qnametargetlist xgetTarget();

   boolean isSetTarget();

   void setTarget(List var1);

   void xsetTarget(Qnametargetlist var1);

   void unsetTarget();

   public static final class Factory {
      public static Qnameconfig newInstance() {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().newInstance(Qnameconfig.type, (XmlOptions)null);
      }

      public static Qnameconfig newInstance(XmlOptions options) {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().newInstance(Qnameconfig.type, options);
      }

      public static Qnameconfig parse(String xmlAsString) throws XmlException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, Qnameconfig.type, (XmlOptions)null);
      }

      public static Qnameconfig parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse(xmlAsString, Qnameconfig.type, options);
      }

      public static Qnameconfig parse(File file) throws XmlException, IOException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse((File)file, Qnameconfig.type, (XmlOptions)null);
      }

      public static Qnameconfig parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse(file, Qnameconfig.type, options);
      }

      public static Qnameconfig parse(URL u) throws XmlException, IOException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse((URL)u, Qnameconfig.type, (XmlOptions)null);
      }

      public static Qnameconfig parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse(u, Qnameconfig.type, options);
      }

      public static Qnameconfig parse(InputStream is) throws XmlException, IOException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse((InputStream)is, Qnameconfig.type, (XmlOptions)null);
      }

      public static Qnameconfig parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse(is, Qnameconfig.type, options);
      }

      public static Qnameconfig parse(Reader r) throws XmlException, IOException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse((Reader)r, Qnameconfig.type, (XmlOptions)null);
      }

      public static Qnameconfig parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse(r, Qnameconfig.type, options);
      }

      public static Qnameconfig parse(XMLStreamReader sr) throws XmlException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, Qnameconfig.type, (XmlOptions)null);
      }

      public static Qnameconfig parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse(sr, Qnameconfig.type, options);
      }

      public static Qnameconfig parse(Node node) throws XmlException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse((Node)node, Qnameconfig.type, (XmlOptions)null);
      }

      public static Qnameconfig parse(Node node, XmlOptions options) throws XmlException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse(node, Qnameconfig.type, options);
      }

      /** @deprecated */
      public static Qnameconfig parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, Qnameconfig.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Qnameconfig parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Qnameconfig)XmlBeans.getContextTypeLoader().parse(xis, Qnameconfig.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Qnameconfig.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Qnameconfig.type, options);
      }

      private Factory() {
      }
   }
}
