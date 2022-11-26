package com.sun.java.xml.ns.j2Ee;

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

public interface RelationshipRoleSourceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RelationshipRoleSourceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("relationshiprolesourcetypee790type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   EjbNameType getEjbName();

   void setEjbName(EjbNameType var1);

   EjbNameType addNewEjbName();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RelationshipRoleSourceType newInstance() {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().newInstance(RelationshipRoleSourceType.type, (XmlOptions)null);
      }

      public static RelationshipRoleSourceType newInstance(XmlOptions options) {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().newInstance(RelationshipRoleSourceType.type, options);
      }

      public static RelationshipRoleSourceType parse(java.lang.String xmlAsString) throws XmlException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationshipRoleSourceType.type, (XmlOptions)null);
      }

      public static RelationshipRoleSourceType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationshipRoleSourceType.type, options);
      }

      public static RelationshipRoleSourceType parse(File file) throws XmlException, IOException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(file, RelationshipRoleSourceType.type, (XmlOptions)null);
      }

      public static RelationshipRoleSourceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(file, RelationshipRoleSourceType.type, options);
      }

      public static RelationshipRoleSourceType parse(URL u) throws XmlException, IOException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(u, RelationshipRoleSourceType.type, (XmlOptions)null);
      }

      public static RelationshipRoleSourceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(u, RelationshipRoleSourceType.type, options);
      }

      public static RelationshipRoleSourceType parse(InputStream is) throws XmlException, IOException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(is, RelationshipRoleSourceType.type, (XmlOptions)null);
      }

      public static RelationshipRoleSourceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(is, RelationshipRoleSourceType.type, options);
      }

      public static RelationshipRoleSourceType parse(Reader r) throws XmlException, IOException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(r, RelationshipRoleSourceType.type, (XmlOptions)null);
      }

      public static RelationshipRoleSourceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(r, RelationshipRoleSourceType.type, options);
      }

      public static RelationshipRoleSourceType parse(XMLStreamReader sr) throws XmlException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(sr, RelationshipRoleSourceType.type, (XmlOptions)null);
      }

      public static RelationshipRoleSourceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(sr, RelationshipRoleSourceType.type, options);
      }

      public static RelationshipRoleSourceType parse(Node node) throws XmlException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(node, RelationshipRoleSourceType.type, (XmlOptions)null);
      }

      public static RelationshipRoleSourceType parse(Node node, XmlOptions options) throws XmlException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(node, RelationshipRoleSourceType.type, options);
      }

      /** @deprecated */
      public static RelationshipRoleSourceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(xis, RelationshipRoleSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RelationshipRoleSourceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RelationshipRoleSourceType)XmlBeans.getContextTypeLoader().parse(xis, RelationshipRoleSourceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationshipRoleSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationshipRoleSourceType.type, options);
      }

      private Factory() {
      }
   }
}
