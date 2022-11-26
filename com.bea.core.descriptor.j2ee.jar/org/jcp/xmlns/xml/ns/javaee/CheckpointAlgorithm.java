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

public interface CheckpointAlgorithm extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CheckpointAlgorithm.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("checkpointalgorithmd150type");

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
      public static CheckpointAlgorithm newInstance() {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().newInstance(CheckpointAlgorithm.type, (XmlOptions)null);
      }

      public static CheckpointAlgorithm newInstance(XmlOptions options) {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().newInstance(CheckpointAlgorithm.type, options);
      }

      public static CheckpointAlgorithm parse(java.lang.String xmlAsString) throws XmlException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(xmlAsString, CheckpointAlgorithm.type, (XmlOptions)null);
      }

      public static CheckpointAlgorithm parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(xmlAsString, CheckpointAlgorithm.type, options);
      }

      public static CheckpointAlgorithm parse(File file) throws XmlException, IOException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(file, CheckpointAlgorithm.type, (XmlOptions)null);
      }

      public static CheckpointAlgorithm parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(file, CheckpointAlgorithm.type, options);
      }

      public static CheckpointAlgorithm parse(URL u) throws XmlException, IOException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(u, CheckpointAlgorithm.type, (XmlOptions)null);
      }

      public static CheckpointAlgorithm parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(u, CheckpointAlgorithm.type, options);
      }

      public static CheckpointAlgorithm parse(InputStream is) throws XmlException, IOException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(is, CheckpointAlgorithm.type, (XmlOptions)null);
      }

      public static CheckpointAlgorithm parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(is, CheckpointAlgorithm.type, options);
      }

      public static CheckpointAlgorithm parse(Reader r) throws XmlException, IOException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(r, CheckpointAlgorithm.type, (XmlOptions)null);
      }

      public static CheckpointAlgorithm parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(r, CheckpointAlgorithm.type, options);
      }

      public static CheckpointAlgorithm parse(XMLStreamReader sr) throws XmlException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(sr, CheckpointAlgorithm.type, (XmlOptions)null);
      }

      public static CheckpointAlgorithm parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(sr, CheckpointAlgorithm.type, options);
      }

      public static CheckpointAlgorithm parse(Node node) throws XmlException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(node, CheckpointAlgorithm.type, (XmlOptions)null);
      }

      public static CheckpointAlgorithm parse(Node node, XmlOptions options) throws XmlException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(node, CheckpointAlgorithm.type, options);
      }

      /** @deprecated */
      public static CheckpointAlgorithm parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(xis, CheckpointAlgorithm.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CheckpointAlgorithm parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CheckpointAlgorithm)XmlBeans.getContextTypeLoader().parse(xis, CheckpointAlgorithm.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CheckpointAlgorithm.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CheckpointAlgorithm.type, options);
      }

      private Factory() {
      }
   }
}
