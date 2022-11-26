package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

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

public interface WeblogicRdbmsRelationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicRdbmsRelationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicrdbmsrelationtype8933type");

   RelationNameType getRelationName();

   void setRelationName(RelationNameType var1);

   RelationNameType addNewRelationName();

   TableNameType getTableName();

   boolean isSetTableName();

   void setTableName(TableNameType var1);

   TableNameType addNewTableName();

   void unsetTableName();

   WeblogicRelationshipRoleType[] getWeblogicRelationshipRoleArray();

   WeblogicRelationshipRoleType getWeblogicRelationshipRoleArray(int var1);

   int sizeOfWeblogicRelationshipRoleArray();

   void setWeblogicRelationshipRoleArray(WeblogicRelationshipRoleType[] var1);

   void setWeblogicRelationshipRoleArray(int var1, WeblogicRelationshipRoleType var2);

   WeblogicRelationshipRoleType insertNewWeblogicRelationshipRole(int var1);

   WeblogicRelationshipRoleType addNewWeblogicRelationshipRole();

   void removeWeblogicRelationshipRole(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WeblogicRdbmsRelationType newInstance() {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().newInstance(WeblogicRdbmsRelationType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsRelationType newInstance(XmlOptions options) {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().newInstance(WeblogicRdbmsRelationType.type, options);
      }

      public static WeblogicRdbmsRelationType parse(String xmlAsString) throws XmlException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRdbmsRelationType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsRelationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRdbmsRelationType.type, options);
      }

      public static WeblogicRdbmsRelationType parse(File file) throws XmlException, IOException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(file, WeblogicRdbmsRelationType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsRelationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(file, WeblogicRdbmsRelationType.type, options);
      }

      public static WeblogicRdbmsRelationType parse(URL u) throws XmlException, IOException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(u, WeblogicRdbmsRelationType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsRelationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(u, WeblogicRdbmsRelationType.type, options);
      }

      public static WeblogicRdbmsRelationType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(is, WeblogicRdbmsRelationType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsRelationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(is, WeblogicRdbmsRelationType.type, options);
      }

      public static WeblogicRdbmsRelationType parse(Reader r) throws XmlException, IOException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(r, WeblogicRdbmsRelationType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsRelationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(r, WeblogicRdbmsRelationType.type, options);
      }

      public static WeblogicRdbmsRelationType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRdbmsRelationType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsRelationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRdbmsRelationType.type, options);
      }

      public static WeblogicRdbmsRelationType parse(Node node) throws XmlException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(node, WeblogicRdbmsRelationType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsRelationType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(node, WeblogicRdbmsRelationType.type, options);
      }

      /** @deprecated */
      public static WeblogicRdbmsRelationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRdbmsRelationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicRdbmsRelationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicRdbmsRelationType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRdbmsRelationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRdbmsRelationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRdbmsRelationType.type, options);
      }

      private Factory() {
      }
   }
}
