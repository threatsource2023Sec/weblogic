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

public interface PersistenceContextTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceContextTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("persistencecontexttypetype1bc5type");
   Enum TRANSACTION = PersistenceContextTypeType.Enum.forString("Transaction");
   Enum EXTENDED = PersistenceContextTypeType.Enum.forString("Extended");
   int INT_TRANSACTION = 1;
   int INT_EXTENDED = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static PersistenceContextTypeType newInstance() {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().newInstance(PersistenceContextTypeType.type, (XmlOptions)null);
      }

      public static PersistenceContextTypeType newInstance(XmlOptions options) {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().newInstance(PersistenceContextTypeType.type, options);
      }

      public static PersistenceContextTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceContextTypeType.type, (XmlOptions)null);
      }

      public static PersistenceContextTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceContextTypeType.type, options);
      }

      public static PersistenceContextTypeType parse(File file) throws XmlException, IOException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(file, PersistenceContextTypeType.type, (XmlOptions)null);
      }

      public static PersistenceContextTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(file, PersistenceContextTypeType.type, options);
      }

      public static PersistenceContextTypeType parse(URL u) throws XmlException, IOException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(u, PersistenceContextTypeType.type, (XmlOptions)null);
      }

      public static PersistenceContextTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(u, PersistenceContextTypeType.type, options);
      }

      public static PersistenceContextTypeType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(is, PersistenceContextTypeType.type, (XmlOptions)null);
      }

      public static PersistenceContextTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(is, PersistenceContextTypeType.type, options);
      }

      public static PersistenceContextTypeType parse(Reader r) throws XmlException, IOException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(r, PersistenceContextTypeType.type, (XmlOptions)null);
      }

      public static PersistenceContextTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(r, PersistenceContextTypeType.type, options);
      }

      public static PersistenceContextTypeType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceContextTypeType.type, (XmlOptions)null);
      }

      public static PersistenceContextTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceContextTypeType.type, options);
      }

      public static PersistenceContextTypeType parse(Node node) throws XmlException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(node, PersistenceContextTypeType.type, (XmlOptions)null);
      }

      public static PersistenceContextTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(node, PersistenceContextTypeType.type, options);
      }

      /** @deprecated */
      public static PersistenceContextTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceContextTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceContextTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceContextTypeType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceContextTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceContextTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceContextTypeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_TRANSACTION = 1;
      static final int INT_EXTENDED = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Transaction", 1), new Enum("Extended", 2)});
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
