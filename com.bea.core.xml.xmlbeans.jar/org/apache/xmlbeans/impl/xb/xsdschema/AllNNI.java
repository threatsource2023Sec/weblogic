package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.StringEnumAbstractBase;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlNMTOKEN;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface AllNNI extends XmlAnySimpleType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AllNNI.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("allnni78cbtype");

   Object getObjectValue();

   void setObjectValue(Object var1);

   /** @deprecated */
   Object objectValue();

   /** @deprecated */
   void objectSet(Object var1);

   SchemaType instanceType();

   public static final class Factory {
      public static AllNNI newValue(Object obj) {
         return (AllNNI)AllNNI.type.newValue(obj);
      }

      public static AllNNI newInstance() {
         return (AllNNI)XmlBeans.getContextTypeLoader().newInstance(AllNNI.type, (XmlOptions)null);
      }

      public static AllNNI newInstance(XmlOptions options) {
         return (AllNNI)XmlBeans.getContextTypeLoader().newInstance(AllNNI.type, options);
      }

      public static AllNNI parse(String xmlAsString) throws XmlException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, AllNNI.type, (XmlOptions)null);
      }

      public static AllNNI parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse(xmlAsString, AllNNI.type, options);
      }

      public static AllNNI parse(File file) throws XmlException, IOException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse((File)file, AllNNI.type, (XmlOptions)null);
      }

      public static AllNNI parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse(file, AllNNI.type, options);
      }

      public static AllNNI parse(URL u) throws XmlException, IOException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse((URL)u, AllNNI.type, (XmlOptions)null);
      }

      public static AllNNI parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse(u, AllNNI.type, options);
      }

      public static AllNNI parse(InputStream is) throws XmlException, IOException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse((InputStream)is, AllNNI.type, (XmlOptions)null);
      }

      public static AllNNI parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse(is, AllNNI.type, options);
      }

      public static AllNNI parse(Reader r) throws XmlException, IOException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse((Reader)r, AllNNI.type, (XmlOptions)null);
      }

      public static AllNNI parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse(r, AllNNI.type, options);
      }

      public static AllNNI parse(XMLStreamReader sr) throws XmlException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, AllNNI.type, (XmlOptions)null);
      }

      public static AllNNI parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse(sr, AllNNI.type, options);
      }

      public static AllNNI parse(Node node) throws XmlException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse((Node)node, AllNNI.type, (XmlOptions)null);
      }

      public static AllNNI parse(Node node, XmlOptions options) throws XmlException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse(node, AllNNI.type, options);
      }

      /** @deprecated */
      public static AllNNI parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, AllNNI.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AllNNI parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AllNNI)XmlBeans.getContextTypeLoader().parse(xis, AllNNI.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AllNNI.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AllNNI.type, options);
      }

      private Factory() {
      }
   }

   public interface Member extends XmlNMTOKEN {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Member.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("anon0330type");
      Enum UNBOUNDED = AllNNI.Member.Enum.forString("unbounded");
      int INT_UNBOUNDED = 1;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Member newValue(Object obj) {
            return (Member)AllNNI.Member.type.newValue(obj);
         }

         public static Member newInstance() {
            return (Member)XmlBeans.getContextTypeLoader().newInstance(AllNNI.Member.type, (XmlOptions)null);
         }

         public static Member newInstance(XmlOptions options) {
            return (Member)XmlBeans.getContextTypeLoader().newInstance(AllNNI.Member.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_UNBOUNDED = 1;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("unbounded", 1)});
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
