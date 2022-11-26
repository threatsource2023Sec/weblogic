package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface UnionDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UnionDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("union5866doctype");

   Union getUnion();

   void setUnion(Union var1);

   Union addNewUnion();

   public static final class Factory {
      public static UnionDocument newInstance() {
         return (UnionDocument)XmlBeans.getContextTypeLoader().newInstance(UnionDocument.type, (XmlOptions)null);
      }

      public static UnionDocument newInstance(XmlOptions options) {
         return (UnionDocument)XmlBeans.getContextTypeLoader().newInstance(UnionDocument.type, options);
      }

      public static UnionDocument parse(String xmlAsString) throws XmlException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, UnionDocument.type, (XmlOptions)null);
      }

      public static UnionDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, UnionDocument.type, options);
      }

      public static UnionDocument parse(File file) throws XmlException, IOException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse((File)file, UnionDocument.type, (XmlOptions)null);
      }

      public static UnionDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse(file, UnionDocument.type, options);
      }

      public static UnionDocument parse(URL u) throws XmlException, IOException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse((URL)u, UnionDocument.type, (XmlOptions)null);
      }

      public static UnionDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse(u, UnionDocument.type, options);
      }

      public static UnionDocument parse(InputStream is) throws XmlException, IOException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, UnionDocument.type, (XmlOptions)null);
      }

      public static UnionDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse(is, UnionDocument.type, options);
      }

      public static UnionDocument parse(Reader r) throws XmlException, IOException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, UnionDocument.type, (XmlOptions)null);
      }

      public static UnionDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse(r, UnionDocument.type, options);
      }

      public static UnionDocument parse(XMLStreamReader sr) throws XmlException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, UnionDocument.type, (XmlOptions)null);
      }

      public static UnionDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse(sr, UnionDocument.type, options);
      }

      public static UnionDocument parse(Node node) throws XmlException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse((Node)node, UnionDocument.type, (XmlOptions)null);
      }

      public static UnionDocument parse(Node node, XmlOptions options) throws XmlException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse(node, UnionDocument.type, options);
      }

      /** @deprecated */
      public static UnionDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, UnionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UnionDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UnionDocument)XmlBeans.getContextTypeLoader().parse(xis, UnionDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UnionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UnionDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Union extends Annotated {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Union.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("union498belemtype");

      LocalSimpleType[] getSimpleTypeArray();

      LocalSimpleType getSimpleTypeArray(int var1);

      int sizeOfSimpleTypeArray();

      void setSimpleTypeArray(LocalSimpleType[] var1);

      void setSimpleTypeArray(int var1, LocalSimpleType var2);

      LocalSimpleType insertNewSimpleType(int var1);

      LocalSimpleType addNewSimpleType();

      void removeSimpleType(int var1);

      List getMemberTypes();

      MemberTypes xgetMemberTypes();

      boolean isSetMemberTypes();

      void setMemberTypes(List var1);

      void xsetMemberTypes(MemberTypes var1);

      void unsetMemberTypes();

      public static final class Factory {
         public static Union newInstance() {
            return (Union)XmlBeans.getContextTypeLoader().newInstance(UnionDocument.Union.type, (XmlOptions)null);
         }

         public static Union newInstance(XmlOptions options) {
            return (Union)XmlBeans.getContextTypeLoader().newInstance(UnionDocument.Union.type, options);
         }

         private Factory() {
         }
      }

      public interface MemberTypes extends XmlAnySimpleType {
         SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MemberTypes.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("membertypes2404attrtype");

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
            public static MemberTypes newValue(Object obj) {
               return (MemberTypes)UnionDocument.Union.MemberTypes.type.newValue(obj);
            }

            public static MemberTypes newInstance() {
               return (MemberTypes)XmlBeans.getContextTypeLoader().newInstance(UnionDocument.Union.MemberTypes.type, (XmlOptions)null);
            }

            public static MemberTypes newInstance(XmlOptions options) {
               return (MemberTypes)XmlBeans.getContextTypeLoader().newInstance(UnionDocument.Union.MemberTypes.type, options);
            }

            private Factory() {
            }
         }
      }
   }
}
