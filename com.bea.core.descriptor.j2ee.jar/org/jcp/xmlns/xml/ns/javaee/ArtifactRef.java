package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ArtifactRef extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ArtifactRef.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("artifactref4e56type");

   public static final class Factory {
      public static ArtifactRef newValue(Object obj) {
         return (ArtifactRef)ArtifactRef.type.newValue(obj);
      }

      public static ArtifactRef newInstance() {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().newInstance(ArtifactRef.type, (XmlOptions)null);
      }

      public static ArtifactRef newInstance(XmlOptions options) {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().newInstance(ArtifactRef.type, options);
      }

      public static ArtifactRef parse(java.lang.String xmlAsString) throws XmlException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(xmlAsString, ArtifactRef.type, (XmlOptions)null);
      }

      public static ArtifactRef parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(xmlAsString, ArtifactRef.type, options);
      }

      public static ArtifactRef parse(File file) throws XmlException, IOException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(file, ArtifactRef.type, (XmlOptions)null);
      }

      public static ArtifactRef parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(file, ArtifactRef.type, options);
      }

      public static ArtifactRef parse(URL u) throws XmlException, IOException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(u, ArtifactRef.type, (XmlOptions)null);
      }

      public static ArtifactRef parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(u, ArtifactRef.type, options);
      }

      public static ArtifactRef parse(InputStream is) throws XmlException, IOException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(is, ArtifactRef.type, (XmlOptions)null);
      }

      public static ArtifactRef parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(is, ArtifactRef.type, options);
      }

      public static ArtifactRef parse(Reader r) throws XmlException, IOException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(r, ArtifactRef.type, (XmlOptions)null);
      }

      public static ArtifactRef parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(r, ArtifactRef.type, options);
      }

      public static ArtifactRef parse(XMLStreamReader sr) throws XmlException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(sr, ArtifactRef.type, (XmlOptions)null);
      }

      public static ArtifactRef parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(sr, ArtifactRef.type, options);
      }

      public static ArtifactRef parse(Node node) throws XmlException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(node, ArtifactRef.type, (XmlOptions)null);
      }

      public static ArtifactRef parse(Node node, XmlOptions options) throws XmlException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(node, ArtifactRef.type, options);
      }

      /** @deprecated */
      public static ArtifactRef parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(xis, ArtifactRef.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ArtifactRef parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ArtifactRef)XmlBeans.getContextTypeLoader().parse(xis, ArtifactRef.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ArtifactRef.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ArtifactRef.type, options);
      }

      private Factory() {
      }
   }
}
