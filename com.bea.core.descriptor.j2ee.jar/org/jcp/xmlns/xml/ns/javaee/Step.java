package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface Step extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Step.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("stepa7b9type");

   Properties getProperties();

   boolean isSetProperties();

   void setProperties(Properties var1);

   Properties addNewProperties();

   void unsetProperties();

   Listeners getListeners();

   boolean isSetListeners();

   void setListeners(Listeners var1);

   Listeners addNewListeners();

   void unsetListeners();

   Batchlet getBatchlet();

   boolean isSetBatchlet();

   void setBatchlet(Batchlet var1);

   Batchlet addNewBatchlet();

   void unsetBatchlet();

   Chunk getChunk();

   boolean isSetChunk();

   void setChunk(Chunk var1);

   Chunk addNewChunk();

   void unsetChunk();

   Partition getPartition();

   boolean isSetPartition();

   void setPartition(Partition var1);

   Partition addNewPartition();

   void unsetPartition();

   End[] getEndArray();

   End getEndArray(int var1);

   int sizeOfEndArray();

   void setEndArray(End[] var1);

   void setEndArray(int var1, End var2);

   End insertNewEnd(int var1);

   End addNewEnd();

   void removeEnd(int var1);

   Fail[] getFailArray();

   Fail getFailArray(int var1);

   int sizeOfFailArray();

   void setFailArray(Fail[] var1);

   void setFailArray(int var1, Fail var2);

   Fail insertNewFail(int var1);

   Fail addNewFail();

   void removeFail(int var1);

   Next[] getNextArray();

   Next getNextArray(int var1);

   int sizeOfNextArray();

   void setNextArray(Next[] var1);

   void setNextArray(int var1, Next var2);

   Next insertNewNext(int var1);

   Next addNewNext();

   void removeNext(int var1);

   Stop[] getStopArray();

   Stop getStopArray(int var1);

   int sizeOfStopArray();

   void setStopArray(Stop[] var1);

   void setStopArray(int var1, Stop var2);

   Stop insertNewStop(int var1);

   Stop addNewStop();

   void removeStop(int var1);

   java.lang.String getId();

   XmlID xgetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   java.lang.String getStartLimit();

   XmlString xgetStartLimit();

   boolean isSetStartLimit();

   void setStartLimit(java.lang.String var1);

   void xsetStartLimit(XmlString var1);

   void unsetStartLimit();

   java.lang.String getAllowStartIfComplete();

   XmlString xgetAllowStartIfComplete();

   boolean isSetAllowStartIfComplete();

   void setAllowStartIfComplete(java.lang.String var1);

   void xsetAllowStartIfComplete(XmlString var1);

   void unsetAllowStartIfComplete();

   java.lang.String getNext2();

   XmlString xgetNext2();

   boolean isSetNext2();

   void setNext2(java.lang.String var1);

   void xsetNext2(XmlString var1);

   void unsetNext2();

   public static final class Factory {
      public static Step newInstance() {
         return (Step)XmlBeans.getContextTypeLoader().newInstance(Step.type, (XmlOptions)null);
      }

      public static Step newInstance(XmlOptions options) {
         return (Step)XmlBeans.getContextTypeLoader().newInstance(Step.type, options);
      }

      public static Step parse(java.lang.String xmlAsString) throws XmlException {
         return (Step)XmlBeans.getContextTypeLoader().parse(xmlAsString, Step.type, (XmlOptions)null);
      }

      public static Step parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Step)XmlBeans.getContextTypeLoader().parse(xmlAsString, Step.type, options);
      }

      public static Step parse(File file) throws XmlException, IOException {
         return (Step)XmlBeans.getContextTypeLoader().parse(file, Step.type, (XmlOptions)null);
      }

      public static Step parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Step)XmlBeans.getContextTypeLoader().parse(file, Step.type, options);
      }

      public static Step parse(URL u) throws XmlException, IOException {
         return (Step)XmlBeans.getContextTypeLoader().parse(u, Step.type, (XmlOptions)null);
      }

      public static Step parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Step)XmlBeans.getContextTypeLoader().parse(u, Step.type, options);
      }

      public static Step parse(InputStream is) throws XmlException, IOException {
         return (Step)XmlBeans.getContextTypeLoader().parse(is, Step.type, (XmlOptions)null);
      }

      public static Step parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Step)XmlBeans.getContextTypeLoader().parse(is, Step.type, options);
      }

      public static Step parse(Reader r) throws XmlException, IOException {
         return (Step)XmlBeans.getContextTypeLoader().parse(r, Step.type, (XmlOptions)null);
      }

      public static Step parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Step)XmlBeans.getContextTypeLoader().parse(r, Step.type, options);
      }

      public static Step parse(XMLStreamReader sr) throws XmlException {
         return (Step)XmlBeans.getContextTypeLoader().parse(sr, Step.type, (XmlOptions)null);
      }

      public static Step parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Step)XmlBeans.getContextTypeLoader().parse(sr, Step.type, options);
      }

      public static Step parse(Node node) throws XmlException {
         return (Step)XmlBeans.getContextTypeLoader().parse(node, Step.type, (XmlOptions)null);
      }

      public static Step parse(Node node, XmlOptions options) throws XmlException {
         return (Step)XmlBeans.getContextTypeLoader().parse(node, Step.type, options);
      }

      /** @deprecated */
      public static Step parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Step)XmlBeans.getContextTypeLoader().parse(xis, Step.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Step parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Step)XmlBeans.getContextTypeLoader().parse(xis, Step.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Step.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Step.type, options);
      }

      private Factory() {
      }
   }
}
