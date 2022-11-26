package com.oracle.xmlns.weblogic.weblogicCoherence;

import com.bea.xml.SchemaType;
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

public interface CoherencePersistenceParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherencePersistenceParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherencepersistenceparamstypee72ctype");

   String getDefaultPersistenceMode();

   XmlString xgetDefaultPersistenceMode();

   boolean isSetDefaultPersistenceMode();

   void setDefaultPersistenceMode(String var1);

   void xsetDefaultPersistenceMode(XmlString var1);

   void unsetDefaultPersistenceMode();

   String getActiveDirectory();

   XmlString xgetActiveDirectory();

   boolean isNilActiveDirectory();

   boolean isSetActiveDirectory();

   void setActiveDirectory(String var1);

   void xsetActiveDirectory(XmlString var1);

   void setNilActiveDirectory();

   void unsetActiveDirectory();

   String getSnapshotDirectory();

   XmlString xgetSnapshotDirectory();

   boolean isNilSnapshotDirectory();

   boolean isSetSnapshotDirectory();

   void setSnapshotDirectory(String var1);

   void xsetSnapshotDirectory(XmlString var1);

   void setNilSnapshotDirectory();

   void unsetSnapshotDirectory();

   String getTrashDirectory();

   XmlString xgetTrashDirectory();

   boolean isNilTrashDirectory();

   boolean isSetTrashDirectory();

   void setTrashDirectory(String var1);

   void xsetTrashDirectory(XmlString var1);

   void setNilTrashDirectory();

   void unsetTrashDirectory();

   public static final class Factory {
      public static CoherencePersistenceParamsType newInstance() {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().newInstance(CoherencePersistenceParamsType.type, (XmlOptions)null);
      }

      public static CoherencePersistenceParamsType newInstance(XmlOptions options) {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().newInstance(CoherencePersistenceParamsType.type, options);
      }

      public static CoherencePersistenceParamsType parse(String xmlAsString) throws XmlException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherencePersistenceParamsType.type, (XmlOptions)null);
      }

      public static CoherencePersistenceParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherencePersistenceParamsType.type, options);
      }

      public static CoherencePersistenceParamsType parse(File file) throws XmlException, IOException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(file, CoherencePersistenceParamsType.type, (XmlOptions)null);
      }

      public static CoherencePersistenceParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(file, CoherencePersistenceParamsType.type, options);
      }

      public static CoherencePersistenceParamsType parse(URL u) throws XmlException, IOException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(u, CoherencePersistenceParamsType.type, (XmlOptions)null);
      }

      public static CoherencePersistenceParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(u, CoherencePersistenceParamsType.type, options);
      }

      public static CoherencePersistenceParamsType parse(InputStream is) throws XmlException, IOException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(is, CoherencePersistenceParamsType.type, (XmlOptions)null);
      }

      public static CoherencePersistenceParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(is, CoherencePersistenceParamsType.type, options);
      }

      public static CoherencePersistenceParamsType parse(Reader r) throws XmlException, IOException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(r, CoherencePersistenceParamsType.type, (XmlOptions)null);
      }

      public static CoherencePersistenceParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(r, CoherencePersistenceParamsType.type, options);
      }

      public static CoherencePersistenceParamsType parse(XMLStreamReader sr) throws XmlException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(sr, CoherencePersistenceParamsType.type, (XmlOptions)null);
      }

      public static CoherencePersistenceParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(sr, CoherencePersistenceParamsType.type, options);
      }

      public static CoherencePersistenceParamsType parse(Node node) throws XmlException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(node, CoherencePersistenceParamsType.type, (XmlOptions)null);
      }

      public static CoherencePersistenceParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(node, CoherencePersistenceParamsType.type, options);
      }

      /** @deprecated */
      public static CoherencePersistenceParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(xis, CoherencePersistenceParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherencePersistenceParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherencePersistenceParamsType)XmlBeans.getContextTypeLoader().parse(xis, CoherencePersistenceParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherencePersistenceParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherencePersistenceParamsType.type, options);
      }

      private Factory() {
      }
   }
}
