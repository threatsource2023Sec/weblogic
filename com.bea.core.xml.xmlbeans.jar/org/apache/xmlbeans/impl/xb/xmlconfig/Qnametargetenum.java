package org.apache.xmlbeans.impl.xb.xmlconfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.StringEnumAbstractBase;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface Qnametargetenum extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Qnametargetenum.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLCONFIG").resolveHandle("qnametargetenum9f8ftype");
   Enum TYPE = Qnametargetenum.Enum.forString("type");
   Enum DOCUMENT_TYPE = Qnametargetenum.Enum.forString("document-type");
   Enum ACCESSOR_ELEMENT = Qnametargetenum.Enum.forString("accessor-element");
   Enum ACCESSOR_ATTRIBUTE = Qnametargetenum.Enum.forString("accessor-attribute");
   int INT_TYPE = 1;
   int INT_DOCUMENT_TYPE = 2;
   int INT_ACCESSOR_ELEMENT = 3;
   int INT_ACCESSOR_ATTRIBUTE = 4;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static Qnametargetenum newValue(Object obj) {
         return (Qnametargetenum)Qnametargetenum.type.newValue(obj);
      }

      public static Qnametargetenum newInstance() {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().newInstance(Qnametargetenum.type, (XmlOptions)null);
      }

      public static Qnametargetenum newInstance(XmlOptions options) {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().newInstance(Qnametargetenum.type, options);
      }

      public static Qnametargetenum parse(String xmlAsString) throws XmlException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, Qnametargetenum.type, (XmlOptions)null);
      }

      public static Qnametargetenum parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse(xmlAsString, Qnametargetenum.type, options);
      }

      public static Qnametargetenum parse(File file) throws XmlException, IOException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse((File)file, Qnametargetenum.type, (XmlOptions)null);
      }

      public static Qnametargetenum parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse(file, Qnametargetenum.type, options);
      }

      public static Qnametargetenum parse(URL u) throws XmlException, IOException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse((URL)u, Qnametargetenum.type, (XmlOptions)null);
      }

      public static Qnametargetenum parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse(u, Qnametargetenum.type, options);
      }

      public static Qnametargetenum parse(InputStream is) throws XmlException, IOException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse((InputStream)is, Qnametargetenum.type, (XmlOptions)null);
      }

      public static Qnametargetenum parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse(is, Qnametargetenum.type, options);
      }

      public static Qnametargetenum parse(Reader r) throws XmlException, IOException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse((Reader)r, Qnametargetenum.type, (XmlOptions)null);
      }

      public static Qnametargetenum parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse(r, Qnametargetenum.type, options);
      }

      public static Qnametargetenum parse(XMLStreamReader sr) throws XmlException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, Qnametargetenum.type, (XmlOptions)null);
      }

      public static Qnametargetenum parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse(sr, Qnametargetenum.type, options);
      }

      public static Qnametargetenum parse(Node node) throws XmlException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse((Node)node, Qnametargetenum.type, (XmlOptions)null);
      }

      public static Qnametargetenum parse(Node node, XmlOptions options) throws XmlException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse(node, Qnametargetenum.type, options);
      }

      /** @deprecated */
      public static Qnametargetenum parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, Qnametargetenum.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Qnametargetenum parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Qnametargetenum)XmlBeans.getContextTypeLoader().parse(xis, Qnametargetenum.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Qnametargetenum.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Qnametargetenum.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_TYPE = 1;
      static final int INT_DOCUMENT_TYPE = 2;
      static final int INT_ACCESSOR_ELEMENT = 3;
      static final int INT_ACCESSOR_ATTRIBUTE = 4;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("type", 1), new Enum("document-type", 2), new Enum("accessor-element", 3), new Enum("accessor-attribute", 4)});
      private static final long serialVersionUID = 1L;

      public static Enum forString(String s) {
         return (Enum)table.forString(s);
      }

      public static Enum forInt(int i) {
         return (Enum)table.forInt(i);
      }

      private Enum(String s, int i) {
         super(s, i);
      }

      private Object readResolve() {
         return forInt(this.intValue());
      }
   }
}
