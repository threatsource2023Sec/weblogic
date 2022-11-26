package org.apache.xmlbeans.impl.xb.xsdschema;

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
import org.apache.xmlbeans.XmlNMTOKEN;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface WhiteSpaceDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WhiteSpaceDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("whitespaced2c6doctype");

   WhiteSpace getWhiteSpace();

   void setWhiteSpace(WhiteSpace var1);

   WhiteSpace addNewWhiteSpace();

   public static final class Factory {
      public static WhiteSpaceDocument newInstance() {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().newInstance(WhiteSpaceDocument.type, (XmlOptions)null);
      }

      public static WhiteSpaceDocument newInstance(XmlOptions options) {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().newInstance(WhiteSpaceDocument.type, options);
      }

      public static WhiteSpaceDocument parse(String xmlAsString) throws XmlException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, WhiteSpaceDocument.type, (XmlOptions)null);
      }

      public static WhiteSpaceDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WhiteSpaceDocument.type, options);
      }

      public static WhiteSpaceDocument parse(File file) throws XmlException, IOException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse((File)file, WhiteSpaceDocument.type, (XmlOptions)null);
      }

      public static WhiteSpaceDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse(file, WhiteSpaceDocument.type, options);
      }

      public static WhiteSpaceDocument parse(URL u) throws XmlException, IOException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse((URL)u, WhiteSpaceDocument.type, (XmlOptions)null);
      }

      public static WhiteSpaceDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse(u, WhiteSpaceDocument.type, options);
      }

      public static WhiteSpaceDocument parse(InputStream is) throws XmlException, IOException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, WhiteSpaceDocument.type, (XmlOptions)null);
      }

      public static WhiteSpaceDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse(is, WhiteSpaceDocument.type, options);
      }

      public static WhiteSpaceDocument parse(Reader r) throws XmlException, IOException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, WhiteSpaceDocument.type, (XmlOptions)null);
      }

      public static WhiteSpaceDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse(r, WhiteSpaceDocument.type, options);
      }

      public static WhiteSpaceDocument parse(XMLStreamReader sr) throws XmlException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, WhiteSpaceDocument.type, (XmlOptions)null);
      }

      public static WhiteSpaceDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse(sr, WhiteSpaceDocument.type, options);
      }

      public static WhiteSpaceDocument parse(Node node) throws XmlException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse((Node)node, WhiteSpaceDocument.type, (XmlOptions)null);
      }

      public static WhiteSpaceDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse(node, WhiteSpaceDocument.type, options);
      }

      /** @deprecated */
      public static WhiteSpaceDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, WhiteSpaceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WhiteSpaceDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WhiteSpaceDocument)XmlBeans.getContextTypeLoader().parse(xis, WhiteSpaceDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WhiteSpaceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WhiteSpaceDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface WhiteSpace extends Facet {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WhiteSpace.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("whitespace97ffelemtype");

      public static final class Factory {
         public static WhiteSpace newInstance() {
            return (WhiteSpace)XmlBeans.getContextTypeLoader().newInstance(WhiteSpaceDocument.WhiteSpace.type, (XmlOptions)null);
         }

         public static WhiteSpace newInstance(XmlOptions options) {
            return (WhiteSpace)XmlBeans.getContextTypeLoader().newInstance(WhiteSpaceDocument.WhiteSpace.type, options);
         }

         private Factory() {
         }
      }

      public interface Value extends XmlNMTOKEN {
         SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Value.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("value8186attrtype");
         Enum PRESERVE = WhiteSpaceDocument.WhiteSpace.Value.Enum.forString("preserve");
         Enum REPLACE = WhiteSpaceDocument.WhiteSpace.Value.Enum.forString("replace");
         Enum COLLAPSE = WhiteSpaceDocument.WhiteSpace.Value.Enum.forString("collapse");
         int INT_PRESERVE = 1;
         int INT_REPLACE = 2;
         int INT_COLLAPSE = 3;

         StringEnumAbstractBase enumValue();

         void set(StringEnumAbstractBase var1);

         public static final class Factory {
            public static Value newValue(Object obj) {
               return (Value)WhiteSpaceDocument.WhiteSpace.Value.type.newValue(obj);
            }

            public static Value newInstance() {
               return (Value)XmlBeans.getContextTypeLoader().newInstance(WhiteSpaceDocument.WhiteSpace.Value.type, (XmlOptions)null);
            }

            public static Value newInstance(XmlOptions options) {
               return (Value)XmlBeans.getContextTypeLoader().newInstance(WhiteSpaceDocument.WhiteSpace.Value.type, options);
            }

            private Factory() {
            }
         }

         public static final class Enum extends StringEnumAbstractBase {
            static final int INT_PRESERVE = 1;
            static final int INT_REPLACE = 2;
            static final int INT_COLLAPSE = 3;
            public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("preserve", 1), new Enum("replace", 2), new Enum("collapse", 3)});
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
   }
}
