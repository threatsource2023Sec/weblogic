package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.StringEnumAbstractBase;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlNCName;
import org.apache.xmlbeans.XmlNMTOKEN;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlQName;
import org.apache.xmlbeans.XmlString;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface Attribute extends Annotated {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Attribute.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("attribute83a9type");

   LocalSimpleType getSimpleType();

   boolean isSetSimpleType();

   void setSimpleType(LocalSimpleType var1);

   LocalSimpleType addNewSimpleType();

   void unsetSimpleType();

   String getName();

   XmlNCName xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlNCName var1);

   void unsetName();

   QName getRef();

   XmlQName xgetRef();

   boolean isSetRef();

   void setRef(QName var1);

   void xsetRef(XmlQName var1);

   void unsetRef();

   QName getType();

   XmlQName xgetType();

   boolean isSetType();

   void setType(QName var1);

   void xsetType(XmlQName var1);

   void unsetType();

   Use.Enum getUse();

   Use xgetUse();

   boolean isSetUse();

   void setUse(Use.Enum var1);

   void xsetUse(Use var1);

   void unsetUse();

   String getDefault();

   XmlString xgetDefault();

   boolean isSetDefault();

   void setDefault(String var1);

   void xsetDefault(XmlString var1);

   void unsetDefault();

   String getFixed();

   XmlString xgetFixed();

   boolean isSetFixed();

   void setFixed(String var1);

   void xsetFixed(XmlString var1);

   void unsetFixed();

   FormChoice.Enum getForm();

   FormChoice xgetForm();

   boolean isSetForm();

   void setForm(FormChoice.Enum var1);

   void xsetForm(FormChoice var1);

   void unsetForm();

   public static final class Factory {
      public static Attribute newInstance() {
         return (Attribute)XmlBeans.getContextTypeLoader().newInstance(Attribute.type, (XmlOptions)null);
      }

      public static Attribute newInstance(XmlOptions options) {
         return (Attribute)XmlBeans.getContextTypeLoader().newInstance(Attribute.type, options);
      }

      public static Attribute parse(String xmlAsString) throws XmlException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, Attribute.type, (XmlOptions)null);
      }

      public static Attribute parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse(xmlAsString, Attribute.type, options);
      }

      public static Attribute parse(File file) throws XmlException, IOException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse((File)file, Attribute.type, (XmlOptions)null);
      }

      public static Attribute parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse(file, Attribute.type, options);
      }

      public static Attribute parse(URL u) throws XmlException, IOException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse((URL)u, Attribute.type, (XmlOptions)null);
      }

      public static Attribute parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse(u, Attribute.type, options);
      }

      public static Attribute parse(InputStream is) throws XmlException, IOException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse((InputStream)is, Attribute.type, (XmlOptions)null);
      }

      public static Attribute parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse(is, Attribute.type, options);
      }

      public static Attribute parse(Reader r) throws XmlException, IOException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse((Reader)r, Attribute.type, (XmlOptions)null);
      }

      public static Attribute parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse(r, Attribute.type, options);
      }

      public static Attribute parse(XMLStreamReader sr) throws XmlException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, Attribute.type, (XmlOptions)null);
      }

      public static Attribute parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse(sr, Attribute.type, options);
      }

      public static Attribute parse(Node node) throws XmlException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse((Node)node, Attribute.type, (XmlOptions)null);
      }

      public static Attribute parse(Node node, XmlOptions options) throws XmlException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse(node, Attribute.type, options);
      }

      /** @deprecated */
      public static Attribute parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, Attribute.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Attribute parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Attribute)XmlBeans.getContextTypeLoader().parse(xis, Attribute.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Attribute.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Attribute.type, options);
      }

      private Factory() {
      }
   }

   public interface Use extends XmlNMTOKEN {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Use.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("usea41aattrtype");
      Enum PROHIBITED = Attribute.Use.Enum.forString("prohibited");
      Enum OPTIONAL = Attribute.Use.Enum.forString("optional");
      Enum REQUIRED = Attribute.Use.Enum.forString("required");
      int INT_PROHIBITED = 1;
      int INT_OPTIONAL = 2;
      int INT_REQUIRED = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Use newValue(Object obj) {
            return (Use)Attribute.Use.type.newValue(obj);
         }

         public static Use newInstance() {
            return (Use)XmlBeans.getContextTypeLoader().newInstance(Attribute.Use.type, (XmlOptions)null);
         }

         public static Use newInstance(XmlOptions options) {
            return (Use)XmlBeans.getContextTypeLoader().newInstance(Attribute.Use.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_PROHIBITED = 1;
         static final int INT_OPTIONAL = 2;
         static final int INT_REQUIRED = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("prohibited", 1), new Enum("optional", 2), new Enum("required", 3)});
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
