package org.jcp.xmlns.xml.ns.persistence;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface PersistenceDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("persistence9a39doctype");

   PersistenceType getPersistence();

   void setPersistence(PersistenceType var1);

   PersistenceType addNewPersistence();

   public static final class Factory {
      public static PersistenceDocument newInstance() {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().newInstance(PersistenceDocument.type, (XmlOptions)null);
      }

      public static PersistenceDocument newInstance(XmlOptions options) {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().newInstance(PersistenceDocument.type, options);
      }

      public static PersistenceDocument parse(String xmlAsString) throws XmlException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceDocument.type, (XmlOptions)null);
      }

      public static PersistenceDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceDocument.type, options);
      }

      public static PersistenceDocument parse(File file) throws XmlException, IOException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(file, PersistenceDocument.type, (XmlOptions)null);
      }

      public static PersistenceDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(file, PersistenceDocument.type, options);
      }

      public static PersistenceDocument parse(URL u) throws XmlException, IOException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(u, PersistenceDocument.type, (XmlOptions)null);
      }

      public static PersistenceDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(u, PersistenceDocument.type, options);
      }

      public static PersistenceDocument parse(InputStream is) throws XmlException, IOException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(is, PersistenceDocument.type, (XmlOptions)null);
      }

      public static PersistenceDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(is, PersistenceDocument.type, options);
      }

      public static PersistenceDocument parse(Reader r) throws XmlException, IOException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(r, PersistenceDocument.type, (XmlOptions)null);
      }

      public static PersistenceDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(r, PersistenceDocument.type, options);
      }

      public static PersistenceDocument parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(sr, PersistenceDocument.type, (XmlOptions)null);
      }

      public static PersistenceDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(sr, PersistenceDocument.type, options);
      }

      public static PersistenceDocument parse(Node node) throws XmlException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(node, PersistenceDocument.type, (XmlOptions)null);
      }

      public static PersistenceDocument parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(node, PersistenceDocument.type, options);
      }

      /** @deprecated */
      public static PersistenceDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(xis, PersistenceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceDocument)XmlBeans.getContextTypeLoader().parse(xis, PersistenceDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceDocument.type, options);
      }

      private Factory() {
      }
   }
}
