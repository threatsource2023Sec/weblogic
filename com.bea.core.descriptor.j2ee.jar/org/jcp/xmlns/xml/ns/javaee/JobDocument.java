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

public interface JobDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JobDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("jobf18adoctype");

   Job getJob();

   void setJob(Job var1);

   Job addNewJob();

   public static final class Factory {
      public static JobDocument newInstance() {
         return (JobDocument)XmlBeans.getContextTypeLoader().newInstance(JobDocument.type, (XmlOptions)null);
      }

      public static JobDocument newInstance(XmlOptions options) {
         return (JobDocument)XmlBeans.getContextTypeLoader().newInstance(JobDocument.type, options);
      }

      public static JobDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, JobDocument.type, (XmlOptions)null);
      }

      public static JobDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, JobDocument.type, options);
      }

      public static JobDocument parse(File file) throws XmlException, IOException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(file, JobDocument.type, (XmlOptions)null);
      }

      public static JobDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(file, JobDocument.type, options);
      }

      public static JobDocument parse(URL u) throws XmlException, IOException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(u, JobDocument.type, (XmlOptions)null);
      }

      public static JobDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(u, JobDocument.type, options);
      }

      public static JobDocument parse(InputStream is) throws XmlException, IOException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(is, JobDocument.type, (XmlOptions)null);
      }

      public static JobDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(is, JobDocument.type, options);
      }

      public static JobDocument parse(Reader r) throws XmlException, IOException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(r, JobDocument.type, (XmlOptions)null);
      }

      public static JobDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(r, JobDocument.type, options);
      }

      public static JobDocument parse(XMLStreamReader sr) throws XmlException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(sr, JobDocument.type, (XmlOptions)null);
      }

      public static JobDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(sr, JobDocument.type, options);
      }

      public static JobDocument parse(Node node) throws XmlException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(node, JobDocument.type, (XmlOptions)null);
      }

      public static JobDocument parse(Node node, XmlOptions options) throws XmlException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(node, JobDocument.type, options);
      }

      /** @deprecated */
      public static JobDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(xis, JobDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JobDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JobDocument)XmlBeans.getContextTypeLoader().parse(xis, JobDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JobDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JobDocument.type, options);
      }

      private Factory() {
      }
   }
}
