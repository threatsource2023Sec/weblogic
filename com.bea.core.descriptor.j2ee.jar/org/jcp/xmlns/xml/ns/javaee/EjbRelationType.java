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

public interface EjbRelationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbRelationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("ejbrelationtypecf1ftype");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   String getEjbRelationName();

   boolean isSetEjbRelationName();

   void setEjbRelationName(String var1);

   String addNewEjbRelationName();

   void unsetEjbRelationName();

   EjbRelationshipRoleType[] getEjbRelationshipRoleArray();

   EjbRelationshipRoleType getEjbRelationshipRoleArray(int var1);

   int sizeOfEjbRelationshipRoleArray();

   void setEjbRelationshipRoleArray(EjbRelationshipRoleType[] var1);

   void setEjbRelationshipRoleArray(int var1, EjbRelationshipRoleType var2);

   EjbRelationshipRoleType insertNewEjbRelationshipRole(int var1);

   EjbRelationshipRoleType addNewEjbRelationshipRole();

   void removeEjbRelationshipRole(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EjbRelationType newInstance() {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().newInstance(EjbRelationType.type, (XmlOptions)null);
      }

      public static EjbRelationType newInstance(XmlOptions options) {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().newInstance(EjbRelationType.type, options);
      }

      public static EjbRelationType parse(java.lang.String xmlAsString) throws XmlException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbRelationType.type, (XmlOptions)null);
      }

      public static EjbRelationType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbRelationType.type, options);
      }

      public static EjbRelationType parse(File file) throws XmlException, IOException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(file, EjbRelationType.type, (XmlOptions)null);
      }

      public static EjbRelationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(file, EjbRelationType.type, options);
      }

      public static EjbRelationType parse(URL u) throws XmlException, IOException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(u, EjbRelationType.type, (XmlOptions)null);
      }

      public static EjbRelationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(u, EjbRelationType.type, options);
      }

      public static EjbRelationType parse(InputStream is) throws XmlException, IOException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(is, EjbRelationType.type, (XmlOptions)null);
      }

      public static EjbRelationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(is, EjbRelationType.type, options);
      }

      public static EjbRelationType parse(Reader r) throws XmlException, IOException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(r, EjbRelationType.type, (XmlOptions)null);
      }

      public static EjbRelationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(r, EjbRelationType.type, options);
      }

      public static EjbRelationType parse(XMLStreamReader sr) throws XmlException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(sr, EjbRelationType.type, (XmlOptions)null);
      }

      public static EjbRelationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(sr, EjbRelationType.type, options);
      }

      public static EjbRelationType parse(Node node) throws XmlException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(node, EjbRelationType.type, (XmlOptions)null);
      }

      public static EjbRelationType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(node, EjbRelationType.type, options);
      }

      /** @deprecated */
      public static EjbRelationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(xis, EjbRelationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbRelationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbRelationType)XmlBeans.getContextTypeLoader().parse(xis, EjbRelationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbRelationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbRelationType.type, options);
      }

      private Factory() {
      }
   }
}
