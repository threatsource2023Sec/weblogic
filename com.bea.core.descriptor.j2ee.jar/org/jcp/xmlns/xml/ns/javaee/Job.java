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

public interface Job extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Job.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("job6b9atype");

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

   Decision[] getDecisionArray();

   Decision getDecisionArray(int var1);

   int sizeOfDecisionArray();

   void setDecisionArray(Decision[] var1);

   void setDecisionArray(int var1, Decision var2);

   Decision insertNewDecision(int var1);

   Decision addNewDecision();

   void removeDecision(int var1);

   Flow[] getFlowArray();

   Flow getFlowArray(int var1);

   int sizeOfFlowArray();

   void setFlowArray(Flow[] var1);

   void setFlowArray(int var1, Flow var2);

   Flow insertNewFlow(int var1);

   Flow addNewFlow();

   void removeFlow(int var1);

   Split[] getSplitArray();

   Split getSplitArray(int var1);

   int sizeOfSplitArray();

   void setSplitArray(Split[] var1);

   void setSplitArray(int var1, Split var2);

   Split insertNewSplit(int var1);

   Split addNewSplit();

   void removeSplit(int var1);

   Step[] getStepArray();

   Step getStepArray(int var1);

   int sizeOfStepArray();

   void setStepArray(Step[] var1);

   void setStepArray(int var1, Step var2);

   Step insertNewStep(int var1);

   Step addNewStep();

   void removeStep(int var1);

   java.lang.String getVersion();

   XmlString xgetVersion();

   void setVersion(java.lang.String var1);

   void xsetVersion(XmlString var1);

   java.lang.String getId();

   XmlID xgetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   java.lang.String getRestartable();

   XmlString xgetRestartable();

   boolean isSetRestartable();

   void setRestartable(java.lang.String var1);

   void xsetRestartable(XmlString var1);

   void unsetRestartable();

   public static final class Factory {
      public static Job newInstance() {
         return (Job)XmlBeans.getContextTypeLoader().newInstance(Job.type, (XmlOptions)null);
      }

      public static Job newInstance(XmlOptions options) {
         return (Job)XmlBeans.getContextTypeLoader().newInstance(Job.type, options);
      }

      public static Job parse(java.lang.String xmlAsString) throws XmlException {
         return (Job)XmlBeans.getContextTypeLoader().parse(xmlAsString, Job.type, (XmlOptions)null);
      }

      public static Job parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Job)XmlBeans.getContextTypeLoader().parse(xmlAsString, Job.type, options);
      }

      public static Job parse(File file) throws XmlException, IOException {
         return (Job)XmlBeans.getContextTypeLoader().parse(file, Job.type, (XmlOptions)null);
      }

      public static Job parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Job)XmlBeans.getContextTypeLoader().parse(file, Job.type, options);
      }

      public static Job parse(URL u) throws XmlException, IOException {
         return (Job)XmlBeans.getContextTypeLoader().parse(u, Job.type, (XmlOptions)null);
      }

      public static Job parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Job)XmlBeans.getContextTypeLoader().parse(u, Job.type, options);
      }

      public static Job parse(InputStream is) throws XmlException, IOException {
         return (Job)XmlBeans.getContextTypeLoader().parse(is, Job.type, (XmlOptions)null);
      }

      public static Job parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Job)XmlBeans.getContextTypeLoader().parse(is, Job.type, options);
      }

      public static Job parse(Reader r) throws XmlException, IOException {
         return (Job)XmlBeans.getContextTypeLoader().parse(r, Job.type, (XmlOptions)null);
      }

      public static Job parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Job)XmlBeans.getContextTypeLoader().parse(r, Job.type, options);
      }

      public static Job parse(XMLStreamReader sr) throws XmlException {
         return (Job)XmlBeans.getContextTypeLoader().parse(sr, Job.type, (XmlOptions)null);
      }

      public static Job parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Job)XmlBeans.getContextTypeLoader().parse(sr, Job.type, options);
      }

      public static Job parse(Node node) throws XmlException {
         return (Job)XmlBeans.getContextTypeLoader().parse(node, Job.type, (XmlOptions)null);
      }

      public static Job parse(Node node, XmlOptions options) throws XmlException {
         return (Job)XmlBeans.getContextTypeLoader().parse(node, Job.type, options);
      }

      /** @deprecated */
      public static Job parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Job)XmlBeans.getContextTypeLoader().parse(xis, Job.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Job parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Job)XmlBeans.getContextTypeLoader().parse(xis, Job.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Job.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Job.type, options);
      }

      private Factory() {
      }
   }
}
