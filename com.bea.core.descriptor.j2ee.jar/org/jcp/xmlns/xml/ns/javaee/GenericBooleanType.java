package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface GenericBooleanType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GenericBooleanType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("genericbooleantype924btype");
   Enum TRUE = GenericBooleanType.Enum.forString("true");
   Enum FALSE = GenericBooleanType.Enum.forString("false");
   Enum YES = GenericBooleanType.Enum.forString("yes");
   Enum NO = GenericBooleanType.Enum.forString("no");
   int INT_TRUE = 1;
   int INT_FALSE = 2;
   int INT_YES = 3;
   int INT_NO = 4;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static GenericBooleanType newInstance() {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().newInstance(GenericBooleanType.type, (XmlOptions)null);
      }

      public static GenericBooleanType newInstance(XmlOptions options) {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().newInstance(GenericBooleanType.type, options);
      }

      public static GenericBooleanType parse(java.lang.String xmlAsString) throws XmlException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GenericBooleanType.type, (XmlOptions)null);
      }

      public static GenericBooleanType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GenericBooleanType.type, options);
      }

      public static GenericBooleanType parse(File file) throws XmlException, IOException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(file, GenericBooleanType.type, (XmlOptions)null);
      }

      public static GenericBooleanType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(file, GenericBooleanType.type, options);
      }

      public static GenericBooleanType parse(URL u) throws XmlException, IOException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(u, GenericBooleanType.type, (XmlOptions)null);
      }

      public static GenericBooleanType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(u, GenericBooleanType.type, options);
      }

      public static GenericBooleanType parse(InputStream is) throws XmlException, IOException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(is, GenericBooleanType.type, (XmlOptions)null);
      }

      public static GenericBooleanType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(is, GenericBooleanType.type, options);
      }

      public static GenericBooleanType parse(Reader r) throws XmlException, IOException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(r, GenericBooleanType.type, (XmlOptions)null);
      }

      public static GenericBooleanType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(r, GenericBooleanType.type, options);
      }

      public static GenericBooleanType parse(XMLStreamReader sr) throws XmlException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(sr, GenericBooleanType.type, (XmlOptions)null);
      }

      public static GenericBooleanType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(sr, GenericBooleanType.type, options);
      }

      public static GenericBooleanType parse(Node node) throws XmlException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(node, GenericBooleanType.type, (XmlOptions)null);
      }

      public static GenericBooleanType parse(Node node, XmlOptions options) throws XmlException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(node, GenericBooleanType.type, options);
      }

      /** @deprecated */
      public static GenericBooleanType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(xis, GenericBooleanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GenericBooleanType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GenericBooleanType)XmlBeans.getContextTypeLoader().parse(xis, GenericBooleanType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GenericBooleanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GenericBooleanType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_TRUE = 1;
      static final int INT_FALSE = 2;
      static final int INT_YES = 3;
      static final int INT_NO = 4;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("true", 1), new Enum("false", 2), new Enum("yes", 3), new Enum("no", 4)});
      private static final long serialVersionUID = 1L;

      public static Enum forString(java.lang.String s) {
         return (Enum)table.forString(s);
      }

      public static Enum forInt(int i) {
         return (Enum)table.forInt(i);
      }

      private Enum(java.lang.String s, int i) {
         super(s, i);
      }

      private Object readResolve() {
         return forInt(this.intValue());
      }
   }
}
