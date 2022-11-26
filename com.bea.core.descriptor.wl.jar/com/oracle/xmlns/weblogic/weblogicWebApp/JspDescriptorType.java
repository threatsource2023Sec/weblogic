package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface JspDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JspDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("jspdescriptortypeb8e8type");

   TrueFalseType getKeepgenerated();

   boolean isSetKeepgenerated();

   void setKeepgenerated(TrueFalseType var1);

   TrueFalseType addNewKeepgenerated();

   void unsetKeepgenerated();

   String getPackagePrefix();

   XmlString xgetPackagePrefix();

   boolean isSetPackagePrefix();

   void setPackagePrefix(String var1);

   void xsetPackagePrefix(XmlString var1);

   void unsetPackagePrefix();

   String getSuperClass();

   XmlString xgetSuperClass();

   boolean isSetSuperClass();

   void setSuperClass(String var1);

   void xsetSuperClass(XmlString var1);

   void unsetSuperClass();

   XsdIntegerType getPageCheckSeconds();

   boolean isSetPageCheckSeconds();

   void setPageCheckSeconds(XsdIntegerType var1);

   XsdIntegerType addNewPageCheckSeconds();

   void unsetPageCheckSeconds();

   TrueFalseType getPrecompile();

   boolean isSetPrecompile();

   void setPrecompile(TrueFalseType var1);

   TrueFalseType addNewPrecompile();

   void unsetPrecompile();

   TrueFalseType getPrecompileContinue();

   boolean isSetPrecompileContinue();

   void setPrecompileContinue(TrueFalseType var1);

   TrueFalseType addNewPrecompileContinue();

   void unsetPrecompileContinue();

   TrueFalseType getVerbose();

   boolean isSetVerbose();

   void setVerbose(TrueFalseType var1);

   TrueFalseType addNewVerbose();

   void unsetVerbose();

   String getWorkingDir();

   XmlString xgetWorkingDir();

   boolean isSetWorkingDir();

   void setWorkingDir(String var1);

   void xsetWorkingDir(XmlString var1);

   void unsetWorkingDir();

   TrueFalseType getPrintNulls();

   boolean isSetPrintNulls();

   void setPrintNulls(TrueFalseType var1);

   TrueFalseType addNewPrintNulls();

   void unsetPrintNulls();

   TrueFalseType getBackwardCompatible();

   boolean isSetBackwardCompatible();

   void setBackwardCompatible(TrueFalseType var1);

   TrueFalseType addNewBackwardCompatible();

   void unsetBackwardCompatible();

   String getEncoding();

   XmlString xgetEncoding();

   boolean isSetEncoding();

   void setEncoding(String var1);

   void xsetEncoding(XmlString var1);

   void unsetEncoding();

   TrueFalseType getExactMapping();

   boolean isSetExactMapping();

   void setExactMapping(TrueFalseType var1);

   TrueFalseType addNewExactMapping();

   void unsetExactMapping();

   String getDefaultFileName();

   XmlString xgetDefaultFileName();

   boolean isSetDefaultFileName();

   void setDefaultFileName(String var1);

   void xsetDefaultFileName(XmlString var1);

   void unsetDefaultFileName();

   TrueFalseType getRtexprvalueJspParamName();

   boolean isSetRtexprvalueJspParamName();

   void setRtexprvalueJspParamName(TrueFalseType var1);

   TrueFalseType addNewRtexprvalueJspParamName();

   void unsetRtexprvalueJspParamName();

   TrueFalseType getDebug();

   boolean isSetDebug();

   void setDebug(TrueFalseType var1);

   TrueFalseType addNewDebug();

   void unsetDebug();

   TrueFalseType getCompressHtmlTemplate();

   boolean isSetCompressHtmlTemplate();

   void setCompressHtmlTemplate(TrueFalseType var1);

   TrueFalseType addNewCompressHtmlTemplate();

   void unsetCompressHtmlTemplate();

   TrueFalseType getOptimizeJavaExpression();

   boolean isSetOptimizeJavaExpression();

   void setOptimizeJavaExpression(TrueFalseType var1);

   TrueFalseType addNewOptimizeJavaExpression();

   void unsetOptimizeJavaExpression();

   String getResourceProviderClass();

   XmlString xgetResourceProviderClass();

   boolean isSetResourceProviderClass();

   void setResourceProviderClass(String var1);

   void xsetResourceProviderClass(XmlString var1);

   void unsetResourceProviderClass();

   TrueFalseType getStrictStaleCheck();

   boolean isSetStrictStaleCheck();

   void setStrictStaleCheck(TrueFalseType var1);

   TrueFalseType addNewStrictStaleCheck();

   void unsetStrictStaleCheck();

   TrueFalseType getStrictJspDocumentValidation();

   boolean isSetStrictJspDocumentValidation();

   void setStrictJspDocumentValidation(TrueFalseType var1);

   TrueFalseType addNewStrictJspDocumentValidation();

   void unsetStrictJspDocumentValidation();

   String getExpressionInterceptor();

   XmlString xgetExpressionInterceptor();

   boolean isSetExpressionInterceptor();

   void setExpressionInterceptor(String var1);

   void xsetExpressionInterceptor(XmlString var1);

   void unsetExpressionInterceptor();

   TrueFalseType getEl22BackwardCompatible();

   boolean isSetEl22BackwardCompatible();

   void setEl22BackwardCompatible(TrueFalseType var1);

   TrueFalseType addNewEl22BackwardCompatible();

   void unsetEl22BackwardCompatible();

   String getCompilerSourceVm();

   XmlString xgetCompilerSourceVm();

   boolean isSetCompilerSourceVm();

   void setCompilerSourceVm(String var1);

   void xsetCompilerSourceVm(XmlString var1);

   void unsetCompilerSourceVm();

   String getCompilerTargetVm();

   XmlString xgetCompilerTargetVm();

   boolean isSetCompilerTargetVm();

   void setCompilerTargetVm(String var1);

   void xsetCompilerTargetVm(XmlString var1);

   void unsetCompilerTargetVm();

   public static final class Factory {
      public static JspDescriptorType newInstance() {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().newInstance(JspDescriptorType.type, (XmlOptions)null);
      }

      public static JspDescriptorType newInstance(XmlOptions options) {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().newInstance(JspDescriptorType.type, options);
      }

      public static JspDescriptorType parse(String xmlAsString) throws XmlException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JspDescriptorType.type, (XmlOptions)null);
      }

      public static JspDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JspDescriptorType.type, options);
      }

      public static JspDescriptorType parse(File file) throws XmlException, IOException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(file, JspDescriptorType.type, (XmlOptions)null);
      }

      public static JspDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(file, JspDescriptorType.type, options);
      }

      public static JspDescriptorType parse(URL u) throws XmlException, IOException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(u, JspDescriptorType.type, (XmlOptions)null);
      }

      public static JspDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(u, JspDescriptorType.type, options);
      }

      public static JspDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(is, JspDescriptorType.type, (XmlOptions)null);
      }

      public static JspDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(is, JspDescriptorType.type, options);
      }

      public static JspDescriptorType parse(Reader r) throws XmlException, IOException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(r, JspDescriptorType.type, (XmlOptions)null);
      }

      public static JspDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(r, JspDescriptorType.type, options);
      }

      public static JspDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, JspDescriptorType.type, (XmlOptions)null);
      }

      public static JspDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, JspDescriptorType.type, options);
      }

      public static JspDescriptorType parse(Node node) throws XmlException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(node, JspDescriptorType.type, (XmlOptions)null);
      }

      public static JspDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(node, JspDescriptorType.type, options);
      }

      /** @deprecated */
      public static JspDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, JspDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JspDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JspDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, JspDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JspDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JspDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
