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

public interface PersistenceContextSynchronizationType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceContextSynchronizationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("persistencecontextsynchronizationtypefea9type");
   Enum SYNCHRONIZED = PersistenceContextSynchronizationType.Enum.forString("Synchronized");
   Enum UNSYNCHRONIZED = PersistenceContextSynchronizationType.Enum.forString("Unsynchronized");
   int INT_SYNCHRONIZED = 1;
   int INT_UNSYNCHRONIZED = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static PersistenceContextSynchronizationType newInstance() {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().newInstance(PersistenceContextSynchronizationType.type, (XmlOptions)null);
      }

      public static PersistenceContextSynchronizationType newInstance(XmlOptions options) {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().newInstance(PersistenceContextSynchronizationType.type, options);
      }

      public static PersistenceContextSynchronizationType parse(java.lang.String xmlAsString) throws XmlException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceContextSynchronizationType.type, (XmlOptions)null);
      }

      public static PersistenceContextSynchronizationType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceContextSynchronizationType.type, options);
      }

      public static PersistenceContextSynchronizationType parse(File file) throws XmlException, IOException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(file, PersistenceContextSynchronizationType.type, (XmlOptions)null);
      }

      public static PersistenceContextSynchronizationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(file, PersistenceContextSynchronizationType.type, options);
      }

      public static PersistenceContextSynchronizationType parse(URL u) throws XmlException, IOException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(u, PersistenceContextSynchronizationType.type, (XmlOptions)null);
      }

      public static PersistenceContextSynchronizationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(u, PersistenceContextSynchronizationType.type, options);
      }

      public static PersistenceContextSynchronizationType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(is, PersistenceContextSynchronizationType.type, (XmlOptions)null);
      }

      public static PersistenceContextSynchronizationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(is, PersistenceContextSynchronizationType.type, options);
      }

      public static PersistenceContextSynchronizationType parse(Reader r) throws XmlException, IOException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(r, PersistenceContextSynchronizationType.type, (XmlOptions)null);
      }

      public static PersistenceContextSynchronizationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(r, PersistenceContextSynchronizationType.type, options);
      }

      public static PersistenceContextSynchronizationType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceContextSynchronizationType.type, (XmlOptions)null);
      }

      public static PersistenceContextSynchronizationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceContextSynchronizationType.type, options);
      }

      public static PersistenceContextSynchronizationType parse(Node node) throws XmlException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(node, PersistenceContextSynchronizationType.type, (XmlOptions)null);
      }

      public static PersistenceContextSynchronizationType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(node, PersistenceContextSynchronizationType.type, options);
      }

      /** @deprecated */
      public static PersistenceContextSynchronizationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceContextSynchronizationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceContextSynchronizationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceContextSynchronizationType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceContextSynchronizationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceContextSynchronizationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceContextSynchronizationType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_SYNCHRONIZED = 1;
      static final int INT_UNSYNCHRONIZED = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Synchronized", 1), new Enum("Unsynchronized", 2)});
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
