package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface SimpleDerivationSet extends XmlAnySimpleType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleDerivationSet.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("simplederivationsetf70ctype");

   Object getObjectValue();

   void setObjectValue(Object var1);

   /** @deprecated */
   Object objectValue();

   /** @deprecated */
   void objectSet(Object var1);

   SchemaType instanceType();

   public static final class Factory {
      public static SimpleDerivationSet newValue(Object obj) {
         return (SimpleDerivationSet)SimpleDerivationSet.type.newValue(obj);
      }

      public static SimpleDerivationSet newInstance() {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().newInstance(SimpleDerivationSet.type, (XmlOptions)null);
      }

      public static SimpleDerivationSet newInstance(XmlOptions options) {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().newInstance(SimpleDerivationSet.type, options);
      }

      public static SimpleDerivationSet parse(String xmlAsString) throws XmlException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, SimpleDerivationSet.type, (XmlOptions)null);
      }

      public static SimpleDerivationSet parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleDerivationSet.type, options);
      }

      public static SimpleDerivationSet parse(File file) throws XmlException, IOException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse((File)file, SimpleDerivationSet.type, (XmlOptions)null);
      }

      public static SimpleDerivationSet parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse(file, SimpleDerivationSet.type, options);
      }

      public static SimpleDerivationSet parse(URL u) throws XmlException, IOException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse((URL)u, SimpleDerivationSet.type, (XmlOptions)null);
      }

      public static SimpleDerivationSet parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse(u, SimpleDerivationSet.type, options);
      }

      public static SimpleDerivationSet parse(InputStream is) throws XmlException, IOException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse((InputStream)is, SimpleDerivationSet.type, (XmlOptions)null);
      }

      public static SimpleDerivationSet parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse(is, SimpleDerivationSet.type, options);
      }

      public static SimpleDerivationSet parse(Reader r) throws XmlException, IOException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse((Reader)r, SimpleDerivationSet.type, (XmlOptions)null);
      }

      public static SimpleDerivationSet parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse(r, SimpleDerivationSet.type, options);
      }

      public static SimpleDerivationSet parse(XMLStreamReader sr) throws XmlException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, SimpleDerivationSet.type, (XmlOptions)null);
      }

      public static SimpleDerivationSet parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse(sr, SimpleDerivationSet.type, options);
      }

      public static SimpleDerivationSet parse(Node node) throws XmlException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse((Node)node, SimpleDerivationSet.type, (XmlOptions)null);
      }

      public static SimpleDerivationSet parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse(node, SimpleDerivationSet.type, options);
      }

      /** @deprecated */
      public static SimpleDerivationSet parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, SimpleDerivationSet.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleDerivationSet parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleDerivationSet)XmlBeans.getContextTypeLoader().parse(xis, SimpleDerivationSet.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleDerivationSet.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleDerivationSet.type, options);
      }

      private Factory() {
      }
   }

   public interface Member2 extends XmlAnySimpleType {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Member2.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("anon8ba6type");

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
            return (Member2)SimpleDerivationSet.Member2.type.newValue(obj);
         }

         public static Member2 newInstance() {
            return (Member2)XmlBeans.getContextTypeLoader().newInstance(SimpleDerivationSet.Member2.type, (XmlOptions)null);
         }

         public static Member2 newInstance(XmlOptions options) {
            return (Member2)XmlBeans.getContextTypeLoader().newInstance(SimpleDerivationSet.Member2.type, options);
         }

         private Factory() {
         }
      }

      public interface Item extends DerivationControl {
         SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Item.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("anonf38etype");
         DerivationControl.Enum LIST = DerivationControl.LIST;
         DerivationControl.Enum UNION = DerivationControl.UNION;
         DerivationControl.Enum RESTRICTION = DerivationControl.RESTRICTION;
         int INT_LIST = 4;
         int INT_UNION = 5;
         int INT_RESTRICTION = 3;

         public static final class Factory {
            public static Item newValue(Object obj) {
               return (Item)SimpleDerivationSet.Member2.Item.type.newValue(obj);
            }

            public static Item newInstance() {
               return (Item)XmlBeans.getContextTypeLoader().newInstance(SimpleDerivationSet.Member2.Item.type, (XmlOptions)null);
            }

            public static Item newInstance(XmlOptions options) {
               return (Item)XmlBeans.getContextTypeLoader().newInstance(SimpleDerivationSet.Member2.Item.type, options);
            }

            private Factory() {
            }
         }
      }
   }

   public interface Member extends XmlToken {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Member.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("anon38c7type");
      Enum ALL = SimpleDerivationSet.Member.Enum.forString("#all");
      int INT_ALL = 1;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Member newValue(Object obj) {
            return (Member)SimpleDerivationSet.Member.type.newValue(obj);
         }

         public static Member newInstance() {
            return (Member)XmlBeans.getContextTypeLoader().newInstance(SimpleDerivationSet.Member.type, (XmlOptions)null);
         }

         public static Member newInstance(XmlOptions options) {
            return (Member)XmlBeans.getContextTypeLoader().newInstance(SimpleDerivationSet.Member.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_ALL = 1;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("#all", 1)});
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
