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

public interface RunAsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RunAsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("runastype5617type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   RoleNameType getRoleName();

   void setRoleName(RoleNameType var1);

   RoleNameType addNewRoleName();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RunAsType newInstance() {
         return (RunAsType)XmlBeans.getContextTypeLoader().newInstance(RunAsType.type, (XmlOptions)null);
      }

      public static RunAsType newInstance(XmlOptions options) {
         return (RunAsType)XmlBeans.getContextTypeLoader().newInstance(RunAsType.type, options);
      }

      public static RunAsType parse(java.lang.String xmlAsString) throws XmlException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RunAsType.type, (XmlOptions)null);
      }

      public static RunAsType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RunAsType.type, options);
      }

      public static RunAsType parse(File file) throws XmlException, IOException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(file, RunAsType.type, (XmlOptions)null);
      }

      public static RunAsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(file, RunAsType.type, options);
      }

      public static RunAsType parse(URL u) throws XmlException, IOException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(u, RunAsType.type, (XmlOptions)null);
      }

      public static RunAsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(u, RunAsType.type, options);
      }

      public static RunAsType parse(InputStream is) throws XmlException, IOException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(is, RunAsType.type, (XmlOptions)null);
      }

      public static RunAsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(is, RunAsType.type, options);
      }

      public static RunAsType parse(Reader r) throws XmlException, IOException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(r, RunAsType.type, (XmlOptions)null);
      }

      public static RunAsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(r, RunAsType.type, options);
      }

      public static RunAsType parse(XMLStreamReader sr) throws XmlException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(sr, RunAsType.type, (XmlOptions)null);
      }

      public static RunAsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(sr, RunAsType.type, options);
      }

      public static RunAsType parse(Node node) throws XmlException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(node, RunAsType.type, (XmlOptions)null);
      }

      public static RunAsType parse(Node node, XmlOptions options) throws XmlException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(node, RunAsType.type, options);
      }

      /** @deprecated */
      public static RunAsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(xis, RunAsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RunAsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RunAsType)XmlBeans.getContextTypeLoader().parse(xis, RunAsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RunAsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RunAsType.type, options);
      }

      private Factory() {
      }
   }
}
