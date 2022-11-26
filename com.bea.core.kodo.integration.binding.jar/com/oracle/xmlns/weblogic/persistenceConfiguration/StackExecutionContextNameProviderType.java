package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface StackExecutionContextNameProviderType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StackExecutionContextNameProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("stackexecutioncontextnameprovidertypee931type");

   Style.Enum getStyle();

   Style xgetStyle();

   boolean isNilStyle();

   boolean isSetStyle();

   void setStyle(Style.Enum var1);

   void xsetStyle(Style var1);

   void setNilStyle();

   void unsetStyle();

   public static final class Factory {
      public static StackExecutionContextNameProviderType newInstance() {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().newInstance(StackExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static StackExecutionContextNameProviderType newInstance(XmlOptions options) {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().newInstance(StackExecutionContextNameProviderType.type, options);
      }

      public static StackExecutionContextNameProviderType parse(String xmlAsString) throws XmlException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StackExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static StackExecutionContextNameProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StackExecutionContextNameProviderType.type, options);
      }

      public static StackExecutionContextNameProviderType parse(File file) throws XmlException, IOException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(file, StackExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static StackExecutionContextNameProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(file, StackExecutionContextNameProviderType.type, options);
      }

      public static StackExecutionContextNameProviderType parse(URL u) throws XmlException, IOException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(u, StackExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static StackExecutionContextNameProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(u, StackExecutionContextNameProviderType.type, options);
      }

      public static StackExecutionContextNameProviderType parse(InputStream is) throws XmlException, IOException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(is, StackExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static StackExecutionContextNameProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(is, StackExecutionContextNameProviderType.type, options);
      }

      public static StackExecutionContextNameProviderType parse(Reader r) throws XmlException, IOException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(r, StackExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static StackExecutionContextNameProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(r, StackExecutionContextNameProviderType.type, options);
      }

      public static StackExecutionContextNameProviderType parse(XMLStreamReader sr) throws XmlException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(sr, StackExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static StackExecutionContextNameProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(sr, StackExecutionContextNameProviderType.type, options);
      }

      public static StackExecutionContextNameProviderType parse(Node node) throws XmlException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(node, StackExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static StackExecutionContextNameProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(node, StackExecutionContextNameProviderType.type, options);
      }

      /** @deprecated */
      public static StackExecutionContextNameProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xis, StackExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StackExecutionContextNameProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StackExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xis, StackExecutionContextNameProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StackExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StackExecutionContextNameProviderType.type, options);
      }

      private Factory() {
      }
   }

   public interface Style extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Style.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("style6674elemtype");
      Enum LINE = StackExecutionContextNameProviderType.Style.Enum.forString("line");
      Enum PARTIAL = StackExecutionContextNameProviderType.Style.Enum.forString("partial");
      Enum FULL = StackExecutionContextNameProviderType.Style.Enum.forString("full");
      int INT_LINE = 1;
      int INT_PARTIAL = 2;
      int INT_FULL = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Style newValue(Object obj) {
            return (Style)StackExecutionContextNameProviderType.Style.type.newValue(obj);
         }

         public static Style newInstance() {
            return (Style)XmlBeans.getContextTypeLoader().newInstance(StackExecutionContextNameProviderType.Style.type, (XmlOptions)null);
         }

         public static Style newInstance(XmlOptions options) {
            return (Style)XmlBeans.getContextTypeLoader().newInstance(StackExecutionContextNameProviderType.Style.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_LINE = 1;
         static final int INT_PARTIAL = 2;
         static final int INT_FULL = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("line", 1), new Enum("partial", 2), new Enum("full", 3)});
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
