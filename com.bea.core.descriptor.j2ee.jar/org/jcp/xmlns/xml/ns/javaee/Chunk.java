package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface Chunk extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Chunk.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("chunk18aatype");

   ItemReader getReader();

   void setReader(ItemReader var1);

   ItemReader addNewReader();

   ItemProcessor getProcessor();

   boolean isSetProcessor();

   void setProcessor(ItemProcessor var1);

   ItemProcessor addNewProcessor();

   void unsetProcessor();

   ItemWriter getWriter();

   void setWriter(ItemWriter var1);

   ItemWriter addNewWriter();

   CheckpointAlgorithm getCheckpointAlgorithm();

   boolean isSetCheckpointAlgorithm();

   void setCheckpointAlgorithm(CheckpointAlgorithm var1);

   CheckpointAlgorithm addNewCheckpointAlgorithm();

   void unsetCheckpointAlgorithm();

   ExceptionClassFilter getSkippableExceptionClasses();

   boolean isSetSkippableExceptionClasses();

   void setSkippableExceptionClasses(ExceptionClassFilter var1);

   ExceptionClassFilter addNewSkippableExceptionClasses();

   void unsetSkippableExceptionClasses();

   ExceptionClassFilter getRetryableExceptionClasses();

   boolean isSetRetryableExceptionClasses();

   void setRetryableExceptionClasses(ExceptionClassFilter var1);

   ExceptionClassFilter addNewRetryableExceptionClasses();

   void unsetRetryableExceptionClasses();

   ExceptionClassFilter getNoRollbackExceptionClasses();

   boolean isSetNoRollbackExceptionClasses();

   void setNoRollbackExceptionClasses(ExceptionClassFilter var1);

   ExceptionClassFilter addNewNoRollbackExceptionClasses();

   void unsetNoRollbackExceptionClasses();

   java.lang.String getCheckpointPolicy();

   XmlString xgetCheckpointPolicy();

   boolean isSetCheckpointPolicy();

   void setCheckpointPolicy(java.lang.String var1);

   void xsetCheckpointPolicy(XmlString var1);

   void unsetCheckpointPolicy();

   java.lang.String getItemCount();

   XmlString xgetItemCount();

   boolean isSetItemCount();

   void setItemCount(java.lang.String var1);

   void xsetItemCount(XmlString var1);

   void unsetItemCount();

   java.lang.String getTimeLimit();

   XmlString xgetTimeLimit();

   boolean isSetTimeLimit();

   void setTimeLimit(java.lang.String var1);

   void xsetTimeLimit(XmlString var1);

   void unsetTimeLimit();

   java.lang.String getSkipLimit();

   XmlString xgetSkipLimit();

   boolean isSetSkipLimit();

   void setSkipLimit(java.lang.String var1);

   void xsetSkipLimit(XmlString var1);

   void unsetSkipLimit();

   java.lang.String getRetryLimit();

   XmlString xgetRetryLimit();

   boolean isSetRetryLimit();

   void setRetryLimit(java.lang.String var1);

   void xsetRetryLimit(XmlString var1);

   void unsetRetryLimit();

   public static final class Factory {
      public static Chunk newInstance() {
         return (Chunk)XmlBeans.getContextTypeLoader().newInstance(Chunk.type, (XmlOptions)null);
      }

      public static Chunk newInstance(XmlOptions options) {
         return (Chunk)XmlBeans.getContextTypeLoader().newInstance(Chunk.type, options);
      }

      public static Chunk parse(java.lang.String xmlAsString) throws XmlException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(xmlAsString, Chunk.type, (XmlOptions)null);
      }

      public static Chunk parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(xmlAsString, Chunk.type, options);
      }

      public static Chunk parse(File file) throws XmlException, IOException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(file, Chunk.type, (XmlOptions)null);
      }

      public static Chunk parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(file, Chunk.type, options);
      }

      public static Chunk parse(URL u) throws XmlException, IOException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(u, Chunk.type, (XmlOptions)null);
      }

      public static Chunk parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(u, Chunk.type, options);
      }

      public static Chunk parse(InputStream is) throws XmlException, IOException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(is, Chunk.type, (XmlOptions)null);
      }

      public static Chunk parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(is, Chunk.type, options);
      }

      public static Chunk parse(Reader r) throws XmlException, IOException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(r, Chunk.type, (XmlOptions)null);
      }

      public static Chunk parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(r, Chunk.type, options);
      }

      public static Chunk parse(XMLStreamReader sr) throws XmlException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(sr, Chunk.type, (XmlOptions)null);
      }

      public static Chunk parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(sr, Chunk.type, options);
      }

      public static Chunk parse(Node node) throws XmlException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(node, Chunk.type, (XmlOptions)null);
      }

      public static Chunk parse(Node node, XmlOptions options) throws XmlException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(node, Chunk.type, options);
      }

      /** @deprecated */
      public static Chunk parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(xis, Chunk.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Chunk parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Chunk)XmlBeans.getContextTypeLoader().parse(xis, Chunk.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Chunk.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Chunk.type, options);
      }

      private Factory() {
      }
   }
}
