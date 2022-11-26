package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface ClusterRemoteCommitProviderType extends RemoteCommitProviderType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClusterRemoteCommitProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("clusterremotecommitprovidertypef627type");

   int getBufferSize();

   XmlInt xgetBufferSize();

   boolean isSetBufferSize();

   void setBufferSize(int var1);

   void xsetBufferSize(XmlInt var1);

   void unsetBufferSize();

   RecoverAction.Enum getRecoverAction();

   RecoverAction xgetRecoverAction();

   boolean isSetRecoverAction();

   void setRecoverAction(RecoverAction.Enum var1);

   void xsetRecoverAction(RecoverAction var1);

   void unsetRecoverAction();

   String getCacheTopics();

   XmlString xgetCacheTopics();

   boolean isNilCacheTopics();

   boolean isSetCacheTopics();

   void setCacheTopics(String var1);

   void xsetCacheTopics(XmlString var1);

   void setNilCacheTopics();

   void unsetCacheTopics();

   public static final class Factory {
      public static ClusterRemoteCommitProviderType newInstance() {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(ClusterRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static ClusterRemoteCommitProviderType newInstance(XmlOptions options) {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(ClusterRemoteCommitProviderType.type, options);
      }

      public static ClusterRemoteCommitProviderType parse(String xmlAsString) throws XmlException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClusterRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static ClusterRemoteCommitProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClusterRemoteCommitProviderType.type, options);
      }

      public static ClusterRemoteCommitProviderType parse(File file) throws XmlException, IOException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, ClusterRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static ClusterRemoteCommitProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, ClusterRemoteCommitProviderType.type, options);
      }

      public static ClusterRemoteCommitProviderType parse(URL u) throws XmlException, IOException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, ClusterRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static ClusterRemoteCommitProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, ClusterRemoteCommitProviderType.type, options);
      }

      public static ClusterRemoteCommitProviderType parse(InputStream is) throws XmlException, IOException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, ClusterRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static ClusterRemoteCommitProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, ClusterRemoteCommitProviderType.type, options);
      }

      public static ClusterRemoteCommitProviderType parse(Reader r) throws XmlException, IOException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, ClusterRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static ClusterRemoteCommitProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, ClusterRemoteCommitProviderType.type, options);
      }

      public static ClusterRemoteCommitProviderType parse(XMLStreamReader sr) throws XmlException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, ClusterRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static ClusterRemoteCommitProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, ClusterRemoteCommitProviderType.type, options);
      }

      public static ClusterRemoteCommitProviderType parse(Node node) throws XmlException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, ClusterRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static ClusterRemoteCommitProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, ClusterRemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static ClusterRemoteCommitProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, ClusterRemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClusterRemoteCommitProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClusterRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, ClusterRemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClusterRemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClusterRemoteCommitProviderType.type, options);
      }

      private Factory() {
      }
   }

   public interface RecoverAction extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RecoverAction.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("recoveraction3382elemtype");
      Enum NONE = ClusterRemoteCommitProviderType.RecoverAction.Enum.forString("none");
      Enum CLEAR = ClusterRemoteCommitProviderType.RecoverAction.Enum.forString("clear");
      int INT_NONE = 1;
      int INT_CLEAR = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static RecoverAction newValue(Object obj) {
            return (RecoverAction)ClusterRemoteCommitProviderType.RecoverAction.type.newValue(obj);
         }

         public static RecoverAction newInstance() {
            return (RecoverAction)XmlBeans.getContextTypeLoader().newInstance(ClusterRemoteCommitProviderType.RecoverAction.type, (XmlOptions)null);
         }

         public static RecoverAction newInstance(XmlOptions options) {
            return (RecoverAction)XmlBeans.getContextTypeLoader().newInstance(ClusterRemoteCommitProviderType.RecoverAction.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_NONE = 1;
         static final int INT_CLEAR = 2;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("none", 1), new Enum("clear", 2)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }
}
