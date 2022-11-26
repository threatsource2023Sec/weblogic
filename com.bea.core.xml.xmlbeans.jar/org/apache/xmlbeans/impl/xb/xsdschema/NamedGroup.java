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
import org.apache.xmlbeans.XmlNCName;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface NamedGroup extends RealGroup {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NamedGroup.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("namedgroup878dtype");

   String getName();

   XmlNCName xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlNCName var1);

   void unsetName();

   public static final class Factory {
      public static NamedGroup newInstance() {
         return (NamedGroup)XmlBeans.getContextTypeLoader().newInstance(NamedGroup.type, (XmlOptions)null);
      }

      public static NamedGroup newInstance(XmlOptions options) {
         return (NamedGroup)XmlBeans.getContextTypeLoader().newInstance(NamedGroup.type, options);
      }

      public static NamedGroup parse(String xmlAsString) throws XmlException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, NamedGroup.type, (XmlOptions)null);
      }

      public static NamedGroup parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse(xmlAsString, NamedGroup.type, options);
      }

      public static NamedGroup parse(File file) throws XmlException, IOException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse((File)file, NamedGroup.type, (XmlOptions)null);
      }

      public static NamedGroup parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse(file, NamedGroup.type, options);
      }

      public static NamedGroup parse(URL u) throws XmlException, IOException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse((URL)u, NamedGroup.type, (XmlOptions)null);
      }

      public static NamedGroup parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse(u, NamedGroup.type, options);
      }

      public static NamedGroup parse(InputStream is) throws XmlException, IOException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse((InputStream)is, NamedGroup.type, (XmlOptions)null);
      }

      public static NamedGroup parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse(is, NamedGroup.type, options);
      }

      public static NamedGroup parse(Reader r) throws XmlException, IOException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse((Reader)r, NamedGroup.type, (XmlOptions)null);
      }

      public static NamedGroup parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse(r, NamedGroup.type, options);
      }

      public static NamedGroup parse(XMLStreamReader sr) throws XmlException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, NamedGroup.type, (XmlOptions)null);
      }

      public static NamedGroup parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse(sr, NamedGroup.type, options);
      }

      public static NamedGroup parse(Node node) throws XmlException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse((Node)node, NamedGroup.type, (XmlOptions)null);
      }

      public static NamedGroup parse(Node node, XmlOptions options) throws XmlException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse(node, NamedGroup.type, options);
      }

      /** @deprecated */
      public static NamedGroup parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, NamedGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NamedGroup parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NamedGroup)XmlBeans.getContextTypeLoader().parse(xis, NamedGroup.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NamedGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NamedGroup.type, options);
      }

      private Factory() {
      }
   }

   public interface All extends org.apache.xmlbeans.impl.xb.xsdschema.All {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(All.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("all82daelemtype");

      public static final class Factory {
         public static All newInstance() {
            return (All)XmlBeans.getContextTypeLoader().newInstance(NamedGroup.All.type, (XmlOptions)null);
         }

         public static All newInstance(XmlOptions options) {
            return (All)XmlBeans.getContextTypeLoader().newInstance(NamedGroup.All.type, options);
         }

         private Factory() {
         }
      }
   }
}
