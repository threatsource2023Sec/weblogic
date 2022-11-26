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

public interface DerivationSet extends XmlAnySimpleType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DerivationSet.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("derivationset037atype");

   Object getObjectValue();

   void setObjectValue(Object var1);

   /** @deprecated */
   Object objectValue();

   /** @deprecated */
   void objectSet(Object var1);

   SchemaType instanceType();

   public static final class Factory {
      public static DerivationSet newValue(Object obj) {
         return (DerivationSet)DerivationSet.type.newValue(obj);
      }

      public static DerivationSet newInstance() {
         return (DerivationSet)XmlBeans.getContextTypeLoader().newInstance(DerivationSet.type, (XmlOptions)null);
      }

      public static DerivationSet newInstance(XmlOptions options) {
         return (DerivationSet)XmlBeans.getContextTypeLoader().newInstance(DerivationSet.type, options);
      }

      public static DerivationSet parse(String xmlAsString) throws XmlException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, DerivationSet.type, (XmlOptions)null);
      }

      public static DerivationSet parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse(xmlAsString, DerivationSet.type, options);
      }

      public static DerivationSet parse(File file) throws XmlException, IOException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse((File)file, DerivationSet.type, (XmlOptions)null);
      }

      public static DerivationSet parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse(file, DerivationSet.type, options);
      }

      public static DerivationSet parse(URL u) throws XmlException, IOException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse((URL)u, DerivationSet.type, (XmlOptions)null);
      }

      public static DerivationSet parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse(u, DerivationSet.type, options);
      }

      public static DerivationSet parse(InputStream is) throws XmlException, IOException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse((InputStream)is, DerivationSet.type, (XmlOptions)null);
      }

      public static DerivationSet parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse(is, DerivationSet.type, options);
      }

      public static DerivationSet parse(Reader r) throws XmlException, IOException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse((Reader)r, DerivationSet.type, (XmlOptions)null);
      }

      public static DerivationSet parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse(r, DerivationSet.type, options);
      }

      public static DerivationSet parse(XMLStreamReader sr) throws XmlException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, DerivationSet.type, (XmlOptions)null);
      }

      public static DerivationSet parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse(sr, DerivationSet.type, options);
      }

      public static DerivationSet parse(Node node) throws XmlException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse((Node)node, DerivationSet.type, (XmlOptions)null);
      }

      public static DerivationSet parse(Node node, XmlOptions options) throws XmlException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse(node, DerivationSet.type, options);
      }

      /** @deprecated */
      public static DerivationSet parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, DerivationSet.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DerivationSet parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DerivationSet)XmlBeans.getContextTypeLoader().parse(xis, DerivationSet.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DerivationSet.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DerivationSet.type, options);
      }

      private Factory() {
      }
   }

   public interface Member2 extends XmlAnySimpleType {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Member2.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("anon9394type");

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
            return (Member2)DerivationSet.Member2.type.newValue(obj);
         }

         public static Member2 newInstance() {
            return (Member2)XmlBeans.getContextTypeLoader().newInstance(DerivationSet.Member2.type, (XmlOptions)null);
         }

         public static Member2 newInstance(XmlOptions options) {
            return (Member2)XmlBeans.getContextTypeLoader().newInstance(DerivationSet.Member2.type, options);
         }

         private Factory() {
         }
      }
   }

   public interface Member extends XmlToken {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Member.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("anoned75type");
      Enum ALL = DerivationSet.Member.Enum.forString("#all");
      int INT_ALL = 1;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Member newValue(Object obj) {
            return (Member)DerivationSet.Member.type.newValue(obj);
         }

         public static Member newInstance() {
            return (Member)XmlBeans.getContextTypeLoader().newInstance(DerivationSet.Member.type, (XmlOptions)null);
         }

         public static Member newInstance(XmlOptions options) {
            return (Member)XmlBeans.getContextTypeLoader().newInstance(DerivationSet.Member.type, options);
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
