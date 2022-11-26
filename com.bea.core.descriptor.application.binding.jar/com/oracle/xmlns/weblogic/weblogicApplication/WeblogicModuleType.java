package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
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

public interface WeblogicModuleType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicModuleType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("weblogicmoduletype2c38type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   Type.Enum getType();

   Type xgetType();

   void setType(Type.Enum var1);

   void xsetType(Type var1);

   String getPath();

   XmlString xgetPath();

   void setPath(String var1);

   void xsetPath(XmlString var1);

   public static final class Factory {
      public static WeblogicModuleType newInstance() {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().newInstance(WeblogicModuleType.type, (XmlOptions)null);
      }

      public static WeblogicModuleType newInstance(XmlOptions options) {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().newInstance(WeblogicModuleType.type, options);
      }

      public static WeblogicModuleType parse(String xmlAsString) throws XmlException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicModuleType.type, (XmlOptions)null);
      }

      public static WeblogicModuleType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicModuleType.type, options);
      }

      public static WeblogicModuleType parse(File file) throws XmlException, IOException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(file, WeblogicModuleType.type, (XmlOptions)null);
      }

      public static WeblogicModuleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(file, WeblogicModuleType.type, options);
      }

      public static WeblogicModuleType parse(URL u) throws XmlException, IOException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(u, WeblogicModuleType.type, (XmlOptions)null);
      }

      public static WeblogicModuleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(u, WeblogicModuleType.type, options);
      }

      public static WeblogicModuleType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(is, WeblogicModuleType.type, (XmlOptions)null);
      }

      public static WeblogicModuleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(is, WeblogicModuleType.type, options);
      }

      public static WeblogicModuleType parse(Reader r) throws XmlException, IOException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(r, WeblogicModuleType.type, (XmlOptions)null);
      }

      public static WeblogicModuleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(r, WeblogicModuleType.type, options);
      }

      public static WeblogicModuleType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicModuleType.type, (XmlOptions)null);
      }

      public static WeblogicModuleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicModuleType.type, options);
      }

      public static WeblogicModuleType parse(Node node) throws XmlException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(node, WeblogicModuleType.type, (XmlOptions)null);
      }

      public static WeblogicModuleType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(node, WeblogicModuleType.type, options);
      }

      /** @deprecated */
      public static WeblogicModuleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicModuleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicModuleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicModuleType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicModuleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicModuleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicModuleType.type, options);
      }

      private Factory() {
      }
   }

   public interface Type extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Type.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("typef70eelemtype");
      Enum JMS = WeblogicModuleType.Type.Enum.forString("JMS");
      Enum JDBC = WeblogicModuleType.Type.Enum.forString("JDBC");
      Enum INTERCEPTION = WeblogicModuleType.Type.Enum.forString("Interception");
      Enum GAR = WeblogicModuleType.Type.Enum.forString("GAR");
      int INT_JMS = 1;
      int INT_JDBC = 2;
      int INT_INTERCEPTION = 3;
      int INT_GAR = 4;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Type newValue(Object obj) {
            return (Type)WeblogicModuleType.Type.type.newValue(obj);
         }

         public static Type newInstance() {
            return (Type)XmlBeans.getContextTypeLoader().newInstance(WeblogicModuleType.Type.type, (XmlOptions)null);
         }

         public static Type newInstance(XmlOptions options) {
            return (Type)XmlBeans.getContextTypeLoader().newInstance(WeblogicModuleType.Type.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_JMS = 1;
         static final int INT_JDBC = 2;
         static final int INT_INTERCEPTION = 3;
         static final int INT_GAR = 4;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("JMS", 1), new Enum("JDBC", 2), new Enum("Interception", 3), new Enum("GAR", 4)});
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
