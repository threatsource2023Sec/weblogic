package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface PersistenceUseType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceUseType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("persistenceusetype8c5dtype");

   TypeIdentifierType getTypeIdentifier();

   void setTypeIdentifier(TypeIdentifierType var1);

   TypeIdentifierType addNewTypeIdentifier();

   TypeVersionType getTypeVersion();

   void setTypeVersion(TypeVersionType var1);

   TypeVersionType addNewTypeVersion();

   TypeStorageType getTypeStorage();

   void setTypeStorage(TypeStorageType var1);

   TypeStorageType addNewTypeStorage();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PersistenceUseType newInstance() {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUseType.type, (XmlOptions)null);
      }

      public static PersistenceUseType newInstance(XmlOptions options) {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUseType.type, options);
      }

      public static PersistenceUseType parse(String xmlAsString) throws XmlException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUseType.type, (XmlOptions)null);
      }

      public static PersistenceUseType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUseType.type, options);
      }

      public static PersistenceUseType parse(File file) throws XmlException, IOException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUseType.type, (XmlOptions)null);
      }

      public static PersistenceUseType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUseType.type, options);
      }

      public static PersistenceUseType parse(URL u) throws XmlException, IOException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUseType.type, (XmlOptions)null);
      }

      public static PersistenceUseType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUseType.type, options);
      }

      public static PersistenceUseType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUseType.type, (XmlOptions)null);
      }

      public static PersistenceUseType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUseType.type, options);
      }

      public static PersistenceUseType parse(Reader r) throws XmlException, IOException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUseType.type, (XmlOptions)null);
      }

      public static PersistenceUseType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUseType.type, options);
      }

      public static PersistenceUseType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUseType.type, (XmlOptions)null);
      }

      public static PersistenceUseType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUseType.type, options);
      }

      public static PersistenceUseType parse(Node node) throws XmlException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUseType.type, (XmlOptions)null);
      }

      public static PersistenceUseType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUseType.type, options);
      }

      /** @deprecated */
      public static PersistenceUseType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUseType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceUseType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceUseType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUseType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUseType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUseType.type, options);
      }

      private Factory() {
      }
   }
}
