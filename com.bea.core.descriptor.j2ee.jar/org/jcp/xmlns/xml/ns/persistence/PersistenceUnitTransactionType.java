package org.jcp.xmlns.xml.ns.persistence;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface PersistenceUnitTransactionType extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceUnitTransactionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("persistenceunittransactiontype20aetype");
   Enum JTA = PersistenceUnitTransactionType.Enum.forString("JTA");
   Enum RESOURCE_LOCAL = PersistenceUnitTransactionType.Enum.forString("RESOURCE_LOCAL");
   int INT_JTA = 1;
   int INT_RESOURCE_LOCAL = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static PersistenceUnitTransactionType newValue(Object obj) {
         return (PersistenceUnitTransactionType)PersistenceUnitTransactionType.type.newValue(obj);
      }

      public static PersistenceUnitTransactionType newInstance() {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitTransactionType.type, (XmlOptions)null);
      }

      public static PersistenceUnitTransactionType newInstance(XmlOptions options) {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitTransactionType.type, options);
      }

      public static PersistenceUnitTransactionType parse(String xmlAsString) throws XmlException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitTransactionType.type, (XmlOptions)null);
      }

      public static PersistenceUnitTransactionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitTransactionType.type, options);
      }

      public static PersistenceUnitTransactionType parse(File file) throws XmlException, IOException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitTransactionType.type, (XmlOptions)null);
      }

      public static PersistenceUnitTransactionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitTransactionType.type, options);
      }

      public static PersistenceUnitTransactionType parse(URL u) throws XmlException, IOException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitTransactionType.type, (XmlOptions)null);
      }

      public static PersistenceUnitTransactionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitTransactionType.type, options);
      }

      public static PersistenceUnitTransactionType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitTransactionType.type, (XmlOptions)null);
      }

      public static PersistenceUnitTransactionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitTransactionType.type, options);
      }

      public static PersistenceUnitTransactionType parse(Reader r) throws XmlException, IOException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitTransactionType.type, (XmlOptions)null);
      }

      public static PersistenceUnitTransactionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitTransactionType.type, options);
      }

      public static PersistenceUnitTransactionType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitTransactionType.type, (XmlOptions)null);
      }

      public static PersistenceUnitTransactionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitTransactionType.type, options);
      }

      public static PersistenceUnitTransactionType parse(Node node) throws XmlException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitTransactionType.type, (XmlOptions)null);
      }

      public static PersistenceUnitTransactionType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitTransactionType.type, options);
      }

      /** @deprecated */
      public static PersistenceUnitTransactionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitTransactionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceUnitTransactionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceUnitTransactionType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitTransactionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitTransactionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitTransactionType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_JTA = 1;
      static final int INT_RESOURCE_LOCAL = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("JTA", 1), new Enum("RESOURCE_LOCAL", 2)});
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
