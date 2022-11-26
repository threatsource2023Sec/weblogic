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

public interface AddressingResponsesType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AddressingResponsesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("addressingresponsestype76f8type");
   Enum ANONYMOUS = AddressingResponsesType.Enum.forString("ANONYMOUS");
   Enum NON_ANONYMOUS = AddressingResponsesType.Enum.forString("NON_ANONYMOUS");
   Enum ALL = AddressingResponsesType.Enum.forString("ALL");
   int INT_ANONYMOUS = 1;
   int INT_NON_ANONYMOUS = 2;
   int INT_ALL = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static AddressingResponsesType newInstance() {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().newInstance(AddressingResponsesType.type, (XmlOptions)null);
      }

      public static AddressingResponsesType newInstance(XmlOptions options) {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().newInstance(AddressingResponsesType.type, options);
      }

      public static AddressingResponsesType parse(java.lang.String xmlAsString) throws XmlException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AddressingResponsesType.type, (XmlOptions)null);
      }

      public static AddressingResponsesType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AddressingResponsesType.type, options);
      }

      public static AddressingResponsesType parse(File file) throws XmlException, IOException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(file, AddressingResponsesType.type, (XmlOptions)null);
      }

      public static AddressingResponsesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(file, AddressingResponsesType.type, options);
      }

      public static AddressingResponsesType parse(URL u) throws XmlException, IOException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(u, AddressingResponsesType.type, (XmlOptions)null);
      }

      public static AddressingResponsesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(u, AddressingResponsesType.type, options);
      }

      public static AddressingResponsesType parse(InputStream is) throws XmlException, IOException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(is, AddressingResponsesType.type, (XmlOptions)null);
      }

      public static AddressingResponsesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(is, AddressingResponsesType.type, options);
      }

      public static AddressingResponsesType parse(Reader r) throws XmlException, IOException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(r, AddressingResponsesType.type, (XmlOptions)null);
      }

      public static AddressingResponsesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(r, AddressingResponsesType.type, options);
      }

      public static AddressingResponsesType parse(XMLStreamReader sr) throws XmlException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(sr, AddressingResponsesType.type, (XmlOptions)null);
      }

      public static AddressingResponsesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(sr, AddressingResponsesType.type, options);
      }

      public static AddressingResponsesType parse(Node node) throws XmlException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(node, AddressingResponsesType.type, (XmlOptions)null);
      }

      public static AddressingResponsesType parse(Node node, XmlOptions options) throws XmlException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(node, AddressingResponsesType.type, options);
      }

      /** @deprecated */
      public static AddressingResponsesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(xis, AddressingResponsesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AddressingResponsesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AddressingResponsesType)XmlBeans.getContextTypeLoader().parse(xis, AddressingResponsesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AddressingResponsesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AddressingResponsesType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_ANONYMOUS = 1;
      static final int INT_NON_ANONYMOUS = 2;
      static final int INT_ALL = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("ANONYMOUS", 1), new Enum("NON_ANONYMOUS", 2), new Enum("ALL", 3)});
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
