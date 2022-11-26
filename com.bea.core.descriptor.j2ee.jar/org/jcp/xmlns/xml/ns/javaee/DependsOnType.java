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

public interface DependsOnType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DependsOnType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("dependsontypeca46type");

   EjbLinkType[] getEjbNameArray();

   EjbLinkType getEjbNameArray(int var1);

   int sizeOfEjbNameArray();

   void setEjbNameArray(EjbLinkType[] var1);

   void setEjbNameArray(int var1, EjbLinkType var2);

   EjbLinkType insertNewEjbName(int var1);

   EjbLinkType addNewEjbName();

   void removeEjbName(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DependsOnType newInstance() {
         return (DependsOnType)XmlBeans.getContextTypeLoader().newInstance(DependsOnType.type, (XmlOptions)null);
      }

      public static DependsOnType newInstance(XmlOptions options) {
         return (DependsOnType)XmlBeans.getContextTypeLoader().newInstance(DependsOnType.type, options);
      }

      public static DependsOnType parse(java.lang.String xmlAsString) throws XmlException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DependsOnType.type, (XmlOptions)null);
      }

      public static DependsOnType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DependsOnType.type, options);
      }

      public static DependsOnType parse(File file) throws XmlException, IOException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(file, DependsOnType.type, (XmlOptions)null);
      }

      public static DependsOnType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(file, DependsOnType.type, options);
      }

      public static DependsOnType parse(URL u) throws XmlException, IOException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(u, DependsOnType.type, (XmlOptions)null);
      }

      public static DependsOnType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(u, DependsOnType.type, options);
      }

      public static DependsOnType parse(InputStream is) throws XmlException, IOException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(is, DependsOnType.type, (XmlOptions)null);
      }

      public static DependsOnType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(is, DependsOnType.type, options);
      }

      public static DependsOnType parse(Reader r) throws XmlException, IOException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(r, DependsOnType.type, (XmlOptions)null);
      }

      public static DependsOnType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(r, DependsOnType.type, options);
      }

      public static DependsOnType parse(XMLStreamReader sr) throws XmlException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(sr, DependsOnType.type, (XmlOptions)null);
      }

      public static DependsOnType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(sr, DependsOnType.type, options);
      }

      public static DependsOnType parse(Node node) throws XmlException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(node, DependsOnType.type, (XmlOptions)null);
      }

      public static DependsOnType parse(Node node, XmlOptions options) throws XmlException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(node, DependsOnType.type, options);
      }

      /** @deprecated */
      public static DependsOnType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(xis, DependsOnType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DependsOnType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DependsOnType)XmlBeans.getContextTypeLoader().parse(xis, DependsOnType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DependsOnType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DependsOnType.type, options);
      }

      private Factory() {
      }
   }
}
