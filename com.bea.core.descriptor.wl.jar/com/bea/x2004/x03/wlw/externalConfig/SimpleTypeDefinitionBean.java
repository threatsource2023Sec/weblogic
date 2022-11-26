package com.bea.x2004.x03.wlw.externalConfig;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface SimpleTypeDefinitionBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleTypeDefinitionBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("simpletypedefinitionbean3bc2type");

   BaseType.Enum getBaseType();

   BaseType xgetBaseType();

   void setBaseType(BaseType.Enum var1);

   void xsetBaseType(BaseType var1);

   MemberConstraintBean getConstraint();

   boolean isSetConstraint();

   void setConstraint(MemberConstraintBean var1);

   MemberConstraintBean addNewConstraint();

   void unsetConstraint();

   boolean getRequiresEncryption();

   XmlBoolean xgetRequiresEncryption();

   boolean isSetRequiresEncryption();

   void setRequiresEncryption(boolean var1);

   void xsetRequiresEncryption(XmlBoolean var1);

   void unsetRequiresEncryption();

   String[] getDefaultValueArray();

   String getDefaultValueArray(int var1);

   XmlString[] xgetDefaultValueArray();

   XmlString xgetDefaultValueArray(int var1);

   int sizeOfDefaultValueArray();

   void setDefaultValueArray(String[] var1);

   void setDefaultValueArray(int var1, String var2);

   void xsetDefaultValueArray(XmlString[] var1);

   void xsetDefaultValueArray(int var1, XmlString var2);

   void insertDefaultValue(int var1, String var2);

   void addDefaultValue(String var1);

   XmlString insertNewDefaultValue(int var1);

   XmlString addNewDefaultValue();

   void removeDefaultValue(int var1);

   public static final class Factory {
      public static SimpleTypeDefinitionBean newInstance() {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().newInstance(SimpleTypeDefinitionBean.type, (XmlOptions)null);
      }

      public static SimpleTypeDefinitionBean newInstance(XmlOptions options) {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().newInstance(SimpleTypeDefinitionBean.type, options);
      }

      public static SimpleTypeDefinitionBean parse(String xmlAsString) throws XmlException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleTypeDefinitionBean.type, (XmlOptions)null);
      }

      public static SimpleTypeDefinitionBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleTypeDefinitionBean.type, options);
      }

      public static SimpleTypeDefinitionBean parse(File file) throws XmlException, IOException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(file, SimpleTypeDefinitionBean.type, (XmlOptions)null);
      }

      public static SimpleTypeDefinitionBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(file, SimpleTypeDefinitionBean.type, options);
      }

      public static SimpleTypeDefinitionBean parse(URL u) throws XmlException, IOException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(u, SimpleTypeDefinitionBean.type, (XmlOptions)null);
      }

      public static SimpleTypeDefinitionBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(u, SimpleTypeDefinitionBean.type, options);
      }

      public static SimpleTypeDefinitionBean parse(InputStream is) throws XmlException, IOException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(is, SimpleTypeDefinitionBean.type, (XmlOptions)null);
      }

      public static SimpleTypeDefinitionBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(is, SimpleTypeDefinitionBean.type, options);
      }

      public static SimpleTypeDefinitionBean parse(Reader r) throws XmlException, IOException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(r, SimpleTypeDefinitionBean.type, (XmlOptions)null);
      }

      public static SimpleTypeDefinitionBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(r, SimpleTypeDefinitionBean.type, options);
      }

      public static SimpleTypeDefinitionBean parse(XMLStreamReader sr) throws XmlException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(sr, SimpleTypeDefinitionBean.type, (XmlOptions)null);
      }

      public static SimpleTypeDefinitionBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(sr, SimpleTypeDefinitionBean.type, options);
      }

      public static SimpleTypeDefinitionBean parse(Node node) throws XmlException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(node, SimpleTypeDefinitionBean.type, (XmlOptions)null);
      }

      public static SimpleTypeDefinitionBean parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(node, SimpleTypeDefinitionBean.type, options);
      }

      /** @deprecated */
      public static SimpleTypeDefinitionBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(xis, SimpleTypeDefinitionBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleTypeDefinitionBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleTypeDefinitionBean)XmlBeans.getContextTypeLoader().parse(xis, SimpleTypeDefinitionBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleTypeDefinitionBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleTypeDefinitionBean.type, options);
      }

      private Factory() {
      }
   }

   public interface BaseType extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BaseType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("basetype0be0elemtype");
      Enum BYTE = SimpleTypeDefinitionBean.BaseType.Enum.forString("BYTE");
      Enum SHORT = SimpleTypeDefinitionBean.BaseType.Enum.forString("SHORT");
      Enum INT = SimpleTypeDefinitionBean.BaseType.Enum.forString("INT");
      Enum LONG = SimpleTypeDefinitionBean.BaseType.Enum.forString("LONG");
      Enum DOUBLE = SimpleTypeDefinitionBean.BaseType.Enum.forString("DOUBLE");
      Enum FLOAT = SimpleTypeDefinitionBean.BaseType.Enum.forString("FLOAT");
      Enum CHAR = SimpleTypeDefinitionBean.BaseType.Enum.forString("CHAR");
      Enum BOOLEAN = SimpleTypeDefinitionBean.BaseType.Enum.forString("BOOLEAN");
      Enum STRING = SimpleTypeDefinitionBean.BaseType.Enum.forString("STRING");
      Enum ENUM = SimpleTypeDefinitionBean.BaseType.Enum.forString("ENUM");
      Enum CLASS = SimpleTypeDefinitionBean.BaseType.Enum.forString("CLASS");
      int INT_BYTE = 1;
      int INT_SHORT = 2;
      int INT_INT = 3;
      int INT_LONG = 4;
      int INT_DOUBLE = 5;
      int INT_FLOAT = 6;
      int INT_CHAR = 7;
      int INT_BOOLEAN = 8;
      int INT_STRING = 9;
      int INT_ENUM = 10;
      int INT_CLASS = 11;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static BaseType newValue(Object obj) {
            return (BaseType)SimpleTypeDefinitionBean.BaseType.type.newValue(obj);
         }

         public static BaseType newInstance() {
            return (BaseType)XmlBeans.getContextTypeLoader().newInstance(SimpleTypeDefinitionBean.BaseType.type, (XmlOptions)null);
         }

         public static BaseType newInstance(XmlOptions options) {
            return (BaseType)XmlBeans.getContextTypeLoader().newInstance(SimpleTypeDefinitionBean.BaseType.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_BYTE = 1;
         static final int INT_SHORT = 2;
         static final int INT_INT = 3;
         static final int INT_LONG = 4;
         static final int INT_DOUBLE = 5;
         static final int INT_FLOAT = 6;
         static final int INT_CHAR = 7;
         static final int INT_BOOLEAN = 8;
         static final int INT_STRING = 9;
         static final int INT_ENUM = 10;
         static final int INT_CLASS = 11;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("BYTE", 1), new Enum("SHORT", 2), new Enum("INT", 3), new Enum("LONG", 4), new Enum("DOUBLE", 5), new Enum("FLOAT", 6), new Enum("CHAR", 7), new Enum("BOOLEAN", 8), new Enum("STRING", 9), new Enum("ENUM", 10), new Enum("CLASS", 11)});
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
