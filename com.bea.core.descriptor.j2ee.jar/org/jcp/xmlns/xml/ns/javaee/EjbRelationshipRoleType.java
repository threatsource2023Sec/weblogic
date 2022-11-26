package org.jcp.xmlns.xml.ns.javaee;

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

public interface EjbRelationshipRoleType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbRelationshipRoleType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("ejbrelationshiproletype3822type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   String getEjbRelationshipRoleName();

   boolean isSetEjbRelationshipRoleName();

   void setEjbRelationshipRoleName(String var1);

   String addNewEjbRelationshipRoleName();

   void unsetEjbRelationshipRoleName();

   MultiplicityType getMultiplicity();

   void setMultiplicity(MultiplicityType var1);

   MultiplicityType addNewMultiplicity();

   EmptyType getCascadeDelete();

   boolean isSetCascadeDelete();

   void setCascadeDelete(EmptyType var1);

   EmptyType addNewCascadeDelete();

   void unsetCascadeDelete();

   RelationshipRoleSourceType getRelationshipRoleSource();

   void setRelationshipRoleSource(RelationshipRoleSourceType var1);

   RelationshipRoleSourceType addNewRelationshipRoleSource();

   CmrFieldType getCmrField();

   boolean isSetCmrField();

   void setCmrField(CmrFieldType var1);

   CmrFieldType addNewCmrField();

   void unsetCmrField();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EjbRelationshipRoleType newInstance() {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().newInstance(EjbRelationshipRoleType.type, (XmlOptions)null);
      }

      public static EjbRelationshipRoleType newInstance(XmlOptions options) {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().newInstance(EjbRelationshipRoleType.type, options);
      }

      public static EjbRelationshipRoleType parse(java.lang.String xmlAsString) throws XmlException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbRelationshipRoleType.type, (XmlOptions)null);
      }

      public static EjbRelationshipRoleType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbRelationshipRoleType.type, options);
      }

      public static EjbRelationshipRoleType parse(File file) throws XmlException, IOException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(file, EjbRelationshipRoleType.type, (XmlOptions)null);
      }

      public static EjbRelationshipRoleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(file, EjbRelationshipRoleType.type, options);
      }

      public static EjbRelationshipRoleType parse(URL u) throws XmlException, IOException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(u, EjbRelationshipRoleType.type, (XmlOptions)null);
      }

      public static EjbRelationshipRoleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(u, EjbRelationshipRoleType.type, options);
      }

      public static EjbRelationshipRoleType parse(InputStream is) throws XmlException, IOException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(is, EjbRelationshipRoleType.type, (XmlOptions)null);
      }

      public static EjbRelationshipRoleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(is, EjbRelationshipRoleType.type, options);
      }

      public static EjbRelationshipRoleType parse(Reader r) throws XmlException, IOException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(r, EjbRelationshipRoleType.type, (XmlOptions)null);
      }

      public static EjbRelationshipRoleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(r, EjbRelationshipRoleType.type, options);
      }

      public static EjbRelationshipRoleType parse(XMLStreamReader sr) throws XmlException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(sr, EjbRelationshipRoleType.type, (XmlOptions)null);
      }

      public static EjbRelationshipRoleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(sr, EjbRelationshipRoleType.type, options);
      }

      public static EjbRelationshipRoleType parse(Node node) throws XmlException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(node, EjbRelationshipRoleType.type, (XmlOptions)null);
      }

      public static EjbRelationshipRoleType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(node, EjbRelationshipRoleType.type, options);
      }

      /** @deprecated */
      public static EjbRelationshipRoleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(xis, EjbRelationshipRoleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbRelationshipRoleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(xis, EjbRelationshipRoleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbRelationshipRoleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbRelationshipRoleType.type, options);
      }

      private Factory() {
      }
   }
}
