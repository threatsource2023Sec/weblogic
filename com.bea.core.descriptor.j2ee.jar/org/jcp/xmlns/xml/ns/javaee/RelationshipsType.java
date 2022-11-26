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

public interface RelationshipsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RelationshipsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("relationshipstypec162type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   EjbRelationType[] getEjbRelationArray();

   EjbRelationType getEjbRelationArray(int var1);

   int sizeOfEjbRelationArray();

   void setEjbRelationArray(EjbRelationType[] var1);

   void setEjbRelationArray(int var1, EjbRelationType var2);

   EjbRelationType insertNewEjbRelation(int var1);

   EjbRelationType addNewEjbRelation();

   void removeEjbRelation(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RelationshipsType newInstance() {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().newInstance(RelationshipsType.type, (XmlOptions)null);
      }

      public static RelationshipsType newInstance(XmlOptions options) {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().newInstance(RelationshipsType.type, options);
      }

      public static RelationshipsType parse(java.lang.String xmlAsString) throws XmlException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationshipsType.type, (XmlOptions)null);
      }

      public static RelationshipsType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationshipsType.type, options);
      }

      public static RelationshipsType parse(File file) throws XmlException, IOException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(file, RelationshipsType.type, (XmlOptions)null);
      }

      public static RelationshipsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(file, RelationshipsType.type, options);
      }

      public static RelationshipsType parse(URL u) throws XmlException, IOException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(u, RelationshipsType.type, (XmlOptions)null);
      }

      public static RelationshipsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(u, RelationshipsType.type, options);
      }

      public static RelationshipsType parse(InputStream is) throws XmlException, IOException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(is, RelationshipsType.type, (XmlOptions)null);
      }

      public static RelationshipsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(is, RelationshipsType.type, options);
      }

      public static RelationshipsType parse(Reader r) throws XmlException, IOException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(r, RelationshipsType.type, (XmlOptions)null);
      }

      public static RelationshipsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(r, RelationshipsType.type, options);
      }

      public static RelationshipsType parse(XMLStreamReader sr) throws XmlException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(sr, RelationshipsType.type, (XmlOptions)null);
      }

      public static RelationshipsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(sr, RelationshipsType.type, options);
      }

      public static RelationshipsType parse(Node node) throws XmlException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(node, RelationshipsType.type, (XmlOptions)null);
      }

      public static RelationshipsType parse(Node node, XmlOptions options) throws XmlException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(node, RelationshipsType.type, options);
      }

      /** @deprecated */
      public static RelationshipsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(xis, RelationshipsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RelationshipsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RelationshipsType)XmlBeans.getContextTypeLoader().parse(xis, RelationshipsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationshipsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationshipsType.type, options);
      }

      private Factory() {
      }
   }
}
