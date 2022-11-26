package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface ItemReader extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ItemReader.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("itemreaderf34ftype");

   Properties getProperties();

   boolean isSetProperties();

   void setProperties(Properties var1);

   Properties addNewProperties();

   void unsetProperties();

   java.lang.String getRef();

   ArtifactRef xgetRef();

   void setRef(java.lang.String var1);

   void xsetRef(ArtifactRef var1);

   public static final class Factory {
      public static ItemReader newInstance() {
         return (ItemReader)XmlBeans.getContextTypeLoader().newInstance(ItemReader.type, (XmlOptions)null);
      }

      public static ItemReader newInstance(XmlOptions options) {
         return (ItemReader)XmlBeans.getContextTypeLoader().newInstance(ItemReader.type, options);
      }

      public static ItemReader parse(java.lang.String xmlAsString) throws XmlException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(xmlAsString, ItemReader.type, (XmlOptions)null);
      }

      public static ItemReader parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(xmlAsString, ItemReader.type, options);
      }

      public static ItemReader parse(File file) throws XmlException, IOException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(file, ItemReader.type, (XmlOptions)null);
      }

      public static ItemReader parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(file, ItemReader.type, options);
      }

      public static ItemReader parse(URL u) throws XmlException, IOException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(u, ItemReader.type, (XmlOptions)null);
      }

      public static ItemReader parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(u, ItemReader.type, options);
      }

      public static ItemReader parse(InputStream is) throws XmlException, IOException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(is, ItemReader.type, (XmlOptions)null);
      }

      public static ItemReader parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(is, ItemReader.type, options);
      }

      public static ItemReader parse(Reader r) throws XmlException, IOException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(r, ItemReader.type, (XmlOptions)null);
      }

      public static ItemReader parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(r, ItemReader.type, options);
      }

      public static ItemReader parse(XMLStreamReader sr) throws XmlException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(sr, ItemReader.type, (XmlOptions)null);
      }

      public static ItemReader parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(sr, ItemReader.type, options);
      }

      public static ItemReader parse(Node node) throws XmlException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(node, ItemReader.type, (XmlOptions)null);
      }

      public static ItemReader parse(Node node, XmlOptions options) throws XmlException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(node, ItemReader.type, options);
      }

      /** @deprecated */
      public static ItemReader parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(xis, ItemReader.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ItemReader parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ItemReader)XmlBeans.getContextTypeLoader().parse(xis, ItemReader.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ItemReader.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ItemReader.type, options);
      }

      private Factory() {
      }
   }
}
