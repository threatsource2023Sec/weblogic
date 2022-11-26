package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.StringEnumAbstractBase;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface NamespaceList extends XmlAnySimpleType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NamespaceList.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("namespacelist10cctype");

   Object getObjectValue();

   void setObjectValue(Object var1);

   /** @deprecated */
   Object objectValue();

   /** @deprecated */
   void objectSet(Object var1);

   SchemaType instanceType();

   public static final class Factory {
      public static NamespaceList newValue(Object obj) {
         return (NamespaceList)NamespaceList.type.newValue(obj);
      }

      public static NamespaceList newInstance() {
         return (NamespaceList)XmlBeans.getContextTypeLoader().newInstance(NamespaceList.type, (XmlOptions)null);
      }

      public static NamespaceList newInstance(XmlOptions options) {
         return (NamespaceList)XmlBeans.getContextTypeLoader().newInstance(NamespaceList.type, options);
      }

      public static NamespaceList parse(String xmlAsString) throws XmlException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, NamespaceList.type, (XmlOptions)null);
      }

      public static NamespaceList parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse(xmlAsString, NamespaceList.type, options);
      }

      public static NamespaceList parse(File file) throws XmlException, IOException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse((File)file, NamespaceList.type, (XmlOptions)null);
      }

      public static NamespaceList parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse(file, NamespaceList.type, options);
      }

      public static NamespaceList parse(URL u) throws XmlException, IOException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse((URL)u, NamespaceList.type, (XmlOptions)null);
      }

      public static NamespaceList parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse(u, NamespaceList.type, options);
      }

      public static NamespaceList parse(InputStream is) throws XmlException, IOException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse((InputStream)is, NamespaceList.type, (XmlOptions)null);
      }

      public static NamespaceList parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse(is, NamespaceList.type, options);
      }

      public static NamespaceList parse(Reader r) throws XmlException, IOException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse((Reader)r, NamespaceList.type, (XmlOptions)null);
      }

      public static NamespaceList parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse(r, NamespaceList.type, options);
      }

      public static NamespaceList parse(XMLStreamReader sr) throws XmlException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, NamespaceList.type, (XmlOptions)null);
      }

      public static NamespaceList parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse(sr, NamespaceList.type, options);
      }

      public static NamespaceList parse(Node node) throws XmlException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse((Node)node, NamespaceList.type, (XmlOptions)null);
      }

      public static NamespaceList parse(Node node, XmlOptions options) throws XmlException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse(node, NamespaceList.type, options);
      }

      /** @deprecated */
      public static NamespaceList parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, NamespaceList.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NamespaceList parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NamespaceList)XmlBeans.getContextTypeLoader().parse(xis, NamespaceList.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NamespaceList.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NamespaceList.type, options);
      }

      private Factory() {
      }
   }

   public interface Member2 extends XmlAnySimpleType {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Member2.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("anona0e6type");

      List getListValue();

      List xgetListValue();

      void setListValue(List var1);

      /** @deprecated */
      List listValue();

      /** @deprecated */
      List xlistValue();

      /** @deprecated */
      void set(List var1);

      public static final class Factory {
         public static Member2 newValue(Object obj) {
            return (Member2)NamespaceList.Member2.type.newValue(obj);
         }

         public static Member2 newInstance() {
            return (Member2)XmlBeans.getContextTypeLoader().newInstance(NamespaceList.Member2.type, (XmlOptions)null);
         }

         public static Member2 newInstance(XmlOptions options) {
            return (Member2)XmlBeans.getContextTypeLoader().newInstance(NamespaceList.Member2.type, options);
         }

         private Factory() {
         }
      }

      public interface Item extends XmlAnySimpleType {
         SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Item.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("anon16cetype");

         Object getObjectValue();

         void setObjectValue(Object var1);

         /** @deprecated */
         Object objectValue();

         /** @deprecated */
         void objectSet(Object var1);

         SchemaType instanceType();

         public static final class Factory {
            public static Item newValue(Object obj) {
               return (Item)NamespaceList.Member2.Item.type.newValue(obj);
            }

            public static Item newInstance() {
               return (Item)XmlBeans.getContextTypeLoader().newInstance(NamespaceList.Member2.Item.type, (XmlOptions)null);
            }

            public static Item newInstance(XmlOptions options) {
               return (Item)XmlBeans.getContextTypeLoader().newInstance(NamespaceList.Member2.Item.type, options);
            }

            private Factory() {
            }
         }

         public interface Member extends XmlToken {
            SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Member.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("anon0c73type");
            Enum TARGET_NAMESPACE = NamespaceList.Member2.Item.Member.Enum.forString("##targetNamespace");
            Enum LOCAL = NamespaceList.Member2.Item.Member.Enum.forString("##local");
            int INT_TARGET_NAMESPACE = 1;
            int INT_LOCAL = 2;

            StringEnumAbstractBase enumValue();

            void set(StringEnumAbstractBase var1);

            public static final class Factory {
               public static Member newValue(Object obj) {
                  return (Member)NamespaceList.Member2.Item.Member.type.newValue(obj);
               }

               public static Member newInstance() {
                  return (Member)XmlBeans.getContextTypeLoader().newInstance(NamespaceList.Member2.Item.Member.type, (XmlOptions)null);
               }

               public static Member newInstance(XmlOptions options) {
                  return (Member)XmlBeans.getContextTypeLoader().newInstance(NamespaceList.Member2.Item.Member.type, options);
               }

               private Factory() {
               }
            }

            public static final class Enum extends StringEnumAbstractBase {
               static final int INT_TARGET_NAMESPACE = 1;
               static final int INT_LOCAL = 2;
               public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("##targetNamespace", 1), new Enum("##local", 2)});
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

   public interface Member extends XmlToken {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Member.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("anonfac7type");
      Enum ANY = NamespaceList.Member.Enum.forString("##any");
      Enum OTHER = NamespaceList.Member.Enum.forString("##other");
      int INT_ANY = 1;
      int INT_OTHER = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Member newValue(Object obj) {
            return (Member)NamespaceList.Member.type.newValue(obj);
         }

         public static Member newInstance() {
            return (Member)XmlBeans.getContextTypeLoader().newInstance(NamespaceList.Member.type, (XmlOptions)null);
         }

         public static Member newInstance(XmlOptions options) {
            return (Member)XmlBeans.getContextTypeLoader().newInstance(NamespaceList.Member.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_ANY = 1;
         static final int INT_OTHER = 2;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("##any", 1), new Enum("##other", 2)});
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
