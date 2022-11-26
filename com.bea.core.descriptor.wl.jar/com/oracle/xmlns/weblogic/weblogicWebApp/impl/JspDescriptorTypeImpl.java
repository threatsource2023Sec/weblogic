package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWebApp.JspDescriptorType;
import com.oracle.xmlns.weblogic.weblogicWebApp.TrueFalseType;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import javax.xml.namespace.QName;

public class JspDescriptorTypeImpl extends XmlComplexContentImpl implements JspDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName KEEPGENERATED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "keepgenerated");
   private static final QName PACKAGEPREFIX$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "package-prefix");
   private static final QName SUPERCLASS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "super-class");
   private static final QName PAGECHECKSECONDS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "page-check-seconds");
   private static final QName PRECOMPILE$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "precompile");
   private static final QName PRECOMPILECONTINUE$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "precompile-continue");
   private static final QName VERBOSE$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "verbose");
   private static final QName WORKINGDIR$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "working-dir");
   private static final QName PRINTNULLS$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "print-nulls");
   private static final QName BACKWARDCOMPATIBLE$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "backward-compatible");
   private static final QName ENCODING$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "encoding");
   private static final QName EXACTMAPPING$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "exact-mapping");
   private static final QName DEFAULTFILENAME$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "default-file-name");
   private static final QName RTEXPRVALUEJSPPARAMNAME$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "rtexprvalue-jsp-param-name");
   private static final QName DEBUG$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "debug");
   private static final QName COMPRESSHTMLTEMPLATE$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "compress-html-template");
   private static final QName OPTIMIZEJAVAEXPRESSION$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "optimize-java-expression");
   private static final QName RESOURCEPROVIDERCLASS$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "resource-provider-class");
   private static final QName STRICTSTALECHECK$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "strict-stale-check");
   private static final QName STRICTJSPDOCUMENTVALIDATION$38 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "strict-jsp-document-validation");
   private static final QName EXPRESSIONINTERCEPTOR$40 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "expression-interceptor");
   private static final QName EL22BACKWARDCOMPATIBLE$42 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "el-2.2-backward-compatible");
   private static final QName COMPILERSOURCEVM$44 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "compiler-source-vm");
   private static final QName COMPILERTARGETVM$46 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "compiler-target-vm");

   public JspDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getKeepgenerated() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(KEEPGENERATED$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetKeepgenerated() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KEEPGENERATED$0) != 0;
      }
   }

   public void setKeepgenerated(TrueFalseType keepgenerated) {
      this.generatedSetterHelperImpl(keepgenerated, KEEPGENERATED$0, 0, (short)1);
   }

   public TrueFalseType addNewKeepgenerated() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(KEEPGENERATED$0);
         return target;
      }
   }

   public void unsetKeepgenerated() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KEEPGENERATED$0, 0);
      }
   }

   public String getPackagePrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PACKAGEPREFIX$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPackagePrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PACKAGEPREFIX$2, 0);
         return target;
      }
   }

   public boolean isSetPackagePrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PACKAGEPREFIX$2) != 0;
      }
   }

   public void setPackagePrefix(String packagePrefix) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PACKAGEPREFIX$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PACKAGEPREFIX$2);
         }

         target.setStringValue(packagePrefix);
      }
   }

   public void xsetPackagePrefix(XmlString packagePrefix) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PACKAGEPREFIX$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PACKAGEPREFIX$2);
         }

         target.set(packagePrefix);
      }
   }

   public void unsetPackagePrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PACKAGEPREFIX$2, 0);
      }
   }

   public String getSuperClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPERCLASS$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSuperClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUPERCLASS$4, 0);
         return target;
      }
   }

   public boolean isSetSuperClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPERCLASS$4) != 0;
      }
   }

   public void setSuperClass(String superClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPERCLASS$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPERCLASS$4);
         }

         target.setStringValue(superClass);
      }
   }

   public void xsetSuperClass(XmlString superClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUPERCLASS$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SUPERCLASS$4);
         }

         target.set(superClass);
      }
   }

   public void unsetSuperClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPERCLASS$4, 0);
      }
   }

   public XsdIntegerType getPageCheckSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(PAGECHECKSECONDS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPageCheckSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PAGECHECKSECONDS$6) != 0;
      }
   }

   public void setPageCheckSeconds(XsdIntegerType pageCheckSeconds) {
      this.generatedSetterHelperImpl(pageCheckSeconds, PAGECHECKSECONDS$6, 0, (short)1);
   }

   public XsdIntegerType addNewPageCheckSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(PAGECHECKSECONDS$6);
         return target;
      }
   }

   public void unsetPageCheckSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PAGECHECKSECONDS$6, 0);
      }
   }

   public TrueFalseType getPrecompile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(PRECOMPILE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPrecompile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRECOMPILE$8) != 0;
      }
   }

   public void setPrecompile(TrueFalseType precompile) {
      this.generatedSetterHelperImpl(precompile, PRECOMPILE$8, 0, (short)1);
   }

   public TrueFalseType addNewPrecompile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(PRECOMPILE$8);
         return target;
      }
   }

   public void unsetPrecompile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRECOMPILE$8, 0);
      }
   }

   public TrueFalseType getPrecompileContinue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(PRECOMPILECONTINUE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPrecompileContinue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRECOMPILECONTINUE$10) != 0;
      }
   }

   public void setPrecompileContinue(TrueFalseType precompileContinue) {
      this.generatedSetterHelperImpl(precompileContinue, PRECOMPILECONTINUE$10, 0, (short)1);
   }

   public TrueFalseType addNewPrecompileContinue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(PRECOMPILECONTINUE$10);
         return target;
      }
   }

   public void unsetPrecompileContinue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRECOMPILECONTINUE$10, 0);
      }
   }

   public TrueFalseType getVerbose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(VERBOSE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetVerbose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERBOSE$12) != 0;
      }
   }

   public void setVerbose(TrueFalseType verbose) {
      this.generatedSetterHelperImpl(verbose, VERBOSE$12, 0, (short)1);
   }

   public TrueFalseType addNewVerbose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(VERBOSE$12);
         return target;
      }
   }

   public void unsetVerbose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERBOSE$12, 0);
      }
   }

   public String getWorkingDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WORKINGDIR$14, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetWorkingDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(WORKINGDIR$14, 0);
         return target;
      }
   }

   public boolean isSetWorkingDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WORKINGDIR$14) != 0;
      }
   }

   public void setWorkingDir(String workingDir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WORKINGDIR$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(WORKINGDIR$14);
         }

         target.setStringValue(workingDir);
      }
   }

   public void xsetWorkingDir(XmlString workingDir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(WORKINGDIR$14, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(WORKINGDIR$14);
         }

         target.set(workingDir);
      }
   }

   public void unsetWorkingDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WORKINGDIR$14, 0);
      }
   }

   public TrueFalseType getPrintNulls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(PRINTNULLS$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPrintNulls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRINTNULLS$16) != 0;
      }
   }

   public void setPrintNulls(TrueFalseType printNulls) {
      this.generatedSetterHelperImpl(printNulls, PRINTNULLS$16, 0, (short)1);
   }

   public TrueFalseType addNewPrintNulls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(PRINTNULLS$16);
         return target;
      }
   }

   public void unsetPrintNulls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRINTNULLS$16, 0);
      }
   }

   public TrueFalseType getBackwardCompatible() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(BACKWARDCOMPATIBLE$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetBackwardCompatible() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BACKWARDCOMPATIBLE$18) != 0;
      }
   }

   public void setBackwardCompatible(TrueFalseType backwardCompatible) {
      this.generatedSetterHelperImpl(backwardCompatible, BACKWARDCOMPATIBLE$18, 0, (short)1);
   }

   public TrueFalseType addNewBackwardCompatible() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(BACKWARDCOMPATIBLE$18);
         return target;
      }
   }

   public void unsetBackwardCompatible() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BACKWARDCOMPATIBLE$18, 0);
      }
   }

   public String getEncoding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENCODING$20, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetEncoding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENCODING$20, 0);
         return target;
      }
   }

   public boolean isSetEncoding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENCODING$20) != 0;
      }
   }

   public void setEncoding(String encoding) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENCODING$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENCODING$20);
         }

         target.setStringValue(encoding);
      }
   }

   public void xsetEncoding(XmlString encoding) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENCODING$20, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ENCODING$20);
         }

         target.set(encoding);
      }
   }

   public void unsetEncoding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENCODING$20, 0);
      }
   }

   public TrueFalseType getExactMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(EXACTMAPPING$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetExactMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXACTMAPPING$22) != 0;
      }
   }

   public void setExactMapping(TrueFalseType exactMapping) {
      this.generatedSetterHelperImpl(exactMapping, EXACTMAPPING$22, 0, (short)1);
   }

   public TrueFalseType addNewExactMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(EXACTMAPPING$22);
         return target;
      }
   }

   public void unsetExactMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXACTMAPPING$22, 0);
      }
   }

   public String getDefaultFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTFILENAME$24, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDefaultFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTFILENAME$24, 0);
         return target;
      }
   }

   public boolean isSetDefaultFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTFILENAME$24) != 0;
      }
   }

   public void setDefaultFileName(String defaultFileName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTFILENAME$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTFILENAME$24);
         }

         target.setStringValue(defaultFileName);
      }
   }

   public void xsetDefaultFileName(XmlString defaultFileName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTFILENAME$24, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DEFAULTFILENAME$24);
         }

         target.set(defaultFileName);
      }
   }

   public void unsetDefaultFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTFILENAME$24, 0);
      }
   }

   public TrueFalseType getRtexprvalueJspParamName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(RTEXPRVALUEJSPPARAMNAME$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRtexprvalueJspParamName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RTEXPRVALUEJSPPARAMNAME$26) != 0;
      }
   }

   public void setRtexprvalueJspParamName(TrueFalseType rtexprvalueJspParamName) {
      this.generatedSetterHelperImpl(rtexprvalueJspParamName, RTEXPRVALUEJSPPARAMNAME$26, 0, (short)1);
   }

   public TrueFalseType addNewRtexprvalueJspParamName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(RTEXPRVALUEJSPPARAMNAME$26);
         return target;
      }
   }

   public void unsetRtexprvalueJspParamName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RTEXPRVALUEJSPPARAMNAME$26, 0);
      }
   }

   public TrueFalseType getDebug() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(DEBUG$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDebug() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEBUG$28) != 0;
      }
   }

   public void setDebug(TrueFalseType debug) {
      this.generatedSetterHelperImpl(debug, DEBUG$28, 0, (short)1);
   }

   public TrueFalseType addNewDebug() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(DEBUG$28);
         return target;
      }
   }

   public void unsetDebug() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEBUG$28, 0);
      }
   }

   public TrueFalseType getCompressHtmlTemplate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(COMPRESSHTMLTEMPLATE$30, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCompressHtmlTemplate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPRESSHTMLTEMPLATE$30) != 0;
      }
   }

   public void setCompressHtmlTemplate(TrueFalseType compressHtmlTemplate) {
      this.generatedSetterHelperImpl(compressHtmlTemplate, COMPRESSHTMLTEMPLATE$30, 0, (short)1);
   }

   public TrueFalseType addNewCompressHtmlTemplate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(COMPRESSHTMLTEMPLATE$30);
         return target;
      }
   }

   public void unsetCompressHtmlTemplate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMPRESSHTMLTEMPLATE$30, 0);
      }
   }

   public TrueFalseType getOptimizeJavaExpression() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(OPTIMIZEJAVAEXPRESSION$32, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetOptimizeJavaExpression() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OPTIMIZEJAVAEXPRESSION$32) != 0;
      }
   }

   public void setOptimizeJavaExpression(TrueFalseType optimizeJavaExpression) {
      this.generatedSetterHelperImpl(optimizeJavaExpression, OPTIMIZEJAVAEXPRESSION$32, 0, (short)1);
   }

   public TrueFalseType addNewOptimizeJavaExpression() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(OPTIMIZEJAVAEXPRESSION$32);
         return target;
      }
   }

   public void unsetOptimizeJavaExpression() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OPTIMIZEJAVAEXPRESSION$32, 0);
      }
   }

   public String getResourceProviderClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCEPROVIDERCLASS$34, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetResourceProviderClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCEPROVIDERCLASS$34, 0);
         return target;
      }
   }

   public boolean isSetResourceProviderClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEPROVIDERCLASS$34) != 0;
      }
   }

   public void setResourceProviderClass(String resourceProviderClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCEPROVIDERCLASS$34, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESOURCEPROVIDERCLASS$34);
         }

         target.setStringValue(resourceProviderClass);
      }
   }

   public void xsetResourceProviderClass(XmlString resourceProviderClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCEPROVIDERCLASS$34, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RESOURCEPROVIDERCLASS$34);
         }

         target.set(resourceProviderClass);
      }
   }

   public void unsetResourceProviderClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEPROVIDERCLASS$34, 0);
      }
   }

   public TrueFalseType getStrictStaleCheck() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(STRICTSTALECHECK$36, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStrictStaleCheck() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STRICTSTALECHECK$36) != 0;
      }
   }

   public void setStrictStaleCheck(TrueFalseType strictStaleCheck) {
      this.generatedSetterHelperImpl(strictStaleCheck, STRICTSTALECHECK$36, 0, (short)1);
   }

   public TrueFalseType addNewStrictStaleCheck() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(STRICTSTALECHECK$36);
         return target;
      }
   }

   public void unsetStrictStaleCheck() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STRICTSTALECHECK$36, 0);
      }
   }

   public TrueFalseType getStrictJspDocumentValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(STRICTJSPDOCUMENTVALIDATION$38, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStrictJspDocumentValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STRICTJSPDOCUMENTVALIDATION$38) != 0;
      }
   }

   public void setStrictJspDocumentValidation(TrueFalseType strictJspDocumentValidation) {
      this.generatedSetterHelperImpl(strictJspDocumentValidation, STRICTJSPDOCUMENTVALIDATION$38, 0, (short)1);
   }

   public TrueFalseType addNewStrictJspDocumentValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(STRICTJSPDOCUMENTVALIDATION$38);
         return target;
      }
   }

   public void unsetStrictJspDocumentValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STRICTJSPDOCUMENTVALIDATION$38, 0);
      }
   }

   public String getExpressionInterceptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXPRESSIONINTERCEPTOR$40, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetExpressionInterceptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EXPRESSIONINTERCEPTOR$40, 0);
         return target;
      }
   }

   public boolean isSetExpressionInterceptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXPRESSIONINTERCEPTOR$40) != 0;
      }
   }

   public void setExpressionInterceptor(String expressionInterceptor) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXPRESSIONINTERCEPTOR$40, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EXPRESSIONINTERCEPTOR$40);
         }

         target.setStringValue(expressionInterceptor);
      }
   }

   public void xsetExpressionInterceptor(XmlString expressionInterceptor) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EXPRESSIONINTERCEPTOR$40, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(EXPRESSIONINTERCEPTOR$40);
         }

         target.set(expressionInterceptor);
      }
   }

   public void unsetExpressionInterceptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXPRESSIONINTERCEPTOR$40, 0);
      }
   }

   public TrueFalseType getEl22BackwardCompatible() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(EL22BACKWARDCOMPATIBLE$42, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEl22BackwardCompatible() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EL22BACKWARDCOMPATIBLE$42) != 0;
      }
   }

   public void setEl22BackwardCompatible(TrueFalseType el22BackwardCompatible) {
      this.generatedSetterHelperImpl(el22BackwardCompatible, EL22BACKWARDCOMPATIBLE$42, 0, (short)1);
   }

   public TrueFalseType addNewEl22BackwardCompatible() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(EL22BACKWARDCOMPATIBLE$42);
         return target;
      }
   }

   public void unsetEl22BackwardCompatible() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EL22BACKWARDCOMPATIBLE$42, 0);
      }
   }

   public String getCompilerSourceVm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COMPILERSOURCEVM$44, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCompilerSourceVm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COMPILERSOURCEVM$44, 0);
         return target;
      }
   }

   public boolean isSetCompilerSourceVm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPILERSOURCEVM$44) != 0;
      }
   }

   public void setCompilerSourceVm(String compilerSourceVm) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COMPILERSOURCEVM$44, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COMPILERSOURCEVM$44);
         }

         target.setStringValue(compilerSourceVm);
      }
   }

   public void xsetCompilerSourceVm(XmlString compilerSourceVm) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COMPILERSOURCEVM$44, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COMPILERSOURCEVM$44);
         }

         target.set(compilerSourceVm);
      }
   }

   public void unsetCompilerSourceVm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMPILERSOURCEVM$44, 0);
      }
   }

   public String getCompilerTargetVm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COMPILERTARGETVM$46, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCompilerTargetVm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COMPILERTARGETVM$46, 0);
         return target;
      }
   }

   public boolean isSetCompilerTargetVm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPILERTARGETVM$46) != 0;
      }
   }

   public void setCompilerTargetVm(String compilerTargetVm) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COMPILERTARGETVM$46, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COMPILERTARGETVM$46);
         }

         target.setStringValue(compilerTargetVm);
      }
   }

   public void xsetCompilerTargetVm(XmlString compilerTargetVm) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COMPILERTARGETVM$46, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COMPILERTARGETVM$46);
         }

         target.set(compilerTargetVm);
      }
   }

   public void unsetCompilerTargetVm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMPILERTARGETVM$46, 0);
      }
   }
}
