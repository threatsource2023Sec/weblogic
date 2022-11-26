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

public interface UserDataConstraintType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UserDataConstraintType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("userdataconstrainttypecc31type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   TransportGuaranteeType getTransportGuarantee();

   void setTransportGuarantee(TransportGuaranteeType var1);

   TransportGuaranteeType addNewTransportGuarantee();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static UserDataConstraintType newInstance() {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().newInstance(UserDataConstraintType.type, (XmlOptions)null);
      }

      public static UserDataConstraintType newInstance(XmlOptions options) {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().newInstance(UserDataConstraintType.type, options);
      }

      public static UserDataConstraintType parse(java.lang.String xmlAsString) throws XmlException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UserDataConstraintType.type, (XmlOptions)null);
      }

      public static UserDataConstraintType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UserDataConstraintType.type, options);
      }

      public static UserDataConstraintType parse(File file) throws XmlException, IOException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(file, UserDataConstraintType.type, (XmlOptions)null);
      }

      public static UserDataConstraintType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(file, UserDataConstraintType.type, options);
      }

      public static UserDataConstraintType parse(URL u) throws XmlException, IOException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(u, UserDataConstraintType.type, (XmlOptions)null);
      }

      public static UserDataConstraintType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(u, UserDataConstraintType.type, options);
      }

      public static UserDataConstraintType parse(InputStream is) throws XmlException, IOException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(is, UserDataConstraintType.type, (XmlOptions)null);
      }

      public static UserDataConstraintType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(is, UserDataConstraintType.type, options);
      }

      public static UserDataConstraintType parse(Reader r) throws XmlException, IOException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(r, UserDataConstraintType.type, (XmlOptions)null);
      }

      public static UserDataConstraintType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(r, UserDataConstraintType.type, options);
      }

      public static UserDataConstraintType parse(XMLStreamReader sr) throws XmlException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(sr, UserDataConstraintType.type, (XmlOptions)null);
      }

      public static UserDataConstraintType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(sr, UserDataConstraintType.type, options);
      }

      public static UserDataConstraintType parse(Node node) throws XmlException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(node, UserDataConstraintType.type, (XmlOptions)null);
      }

      public static UserDataConstraintType parse(Node node, XmlOptions options) throws XmlException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(node, UserDataConstraintType.type, options);
      }

      /** @deprecated */
      public static UserDataConstraintType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(xis, UserDataConstraintType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UserDataConstraintType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UserDataConstraintType)XmlBeans.getContextTypeLoader().parse(xis, UserDataConstraintType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UserDataConstraintType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UserDataConstraintType.type, options);
      }

      private Factory() {
      }
   }
}
