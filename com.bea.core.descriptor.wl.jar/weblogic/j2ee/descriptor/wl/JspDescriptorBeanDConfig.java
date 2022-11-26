package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class JspDescriptorBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private JspDescriptorBean beanTreeNode;

   public JspDescriptorBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (JspDescriptorBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      return null;
   }

   public String keyPropertyValue() {
      return null;
   }

   public void initKeyPropertyValue(String value) {
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public boolean isKeepgenerated() {
      return this.beanTreeNode.isKeepgenerated();
   }

   public void setKeepgenerated(boolean value) {
      this.beanTreeNode.setKeepgenerated(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Keepgenerated", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPackagePrefix() {
      return this.beanTreeNode.getPackagePrefix();
   }

   public void setPackagePrefix(String value) {
      this.beanTreeNode.setPackagePrefix(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PackagePrefix", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSuperClass() {
      return this.beanTreeNode.getSuperClass();
   }

   public void setSuperClass(String value) {
      this.beanTreeNode.setSuperClass(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SuperClass", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getPageCheckSeconds() {
      return this.beanTreeNode.getPageCheckSeconds();
   }

   public void setPageCheckSeconds(int value) {
      this.beanTreeNode.setPageCheckSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PageCheckSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPageCheckSecondsSet() {
      return this.beanTreeNode.isPageCheckSecondsSet();
   }

   public boolean isPrecompile() {
      return this.beanTreeNode.isPrecompile();
   }

   public void setPrecompile(boolean value) {
      this.beanTreeNode.setPrecompile(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Precompile", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPrecompileContinue() {
      return this.beanTreeNode.isPrecompileContinue();
   }

   public void setPrecompileContinue(boolean value) {
      this.beanTreeNode.setPrecompileContinue(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PrecompileContinue", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isVerbose() {
      return this.beanTreeNode.isVerbose();
   }

   public void setVerbose(boolean value) {
      this.beanTreeNode.setVerbose(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Verbose", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getWorkingDir() {
      return this.beanTreeNode.getWorkingDir();
   }

   public void setWorkingDir(String value) {
      this.beanTreeNode.setWorkingDir(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WorkingDir", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPrintNulls() {
      return this.beanTreeNode.isPrintNulls();
   }

   public void setPrintNulls(boolean value) {
      this.beanTreeNode.setPrintNulls(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PrintNulls", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isBackwardCompatible() {
      return this.beanTreeNode.isBackwardCompatible();
   }

   public void setBackwardCompatible(boolean value) {
      this.beanTreeNode.setBackwardCompatible(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BackwardCompatible", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getEncoding() {
      return this.beanTreeNode.getEncoding();
   }

   public void setEncoding(String value) {
      this.beanTreeNode.setEncoding(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Encoding", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isExactMapping() {
      return this.beanTreeNode.isExactMapping();
   }

   public void setExactMapping(boolean value) {
      this.beanTreeNode.setExactMapping(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ExactMapping", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDefaultFileName() {
      return this.beanTreeNode.getDefaultFileName();
   }

   public void setDefaultFileName(String value) {
      this.beanTreeNode.setDefaultFileName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultFileName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isRtexprvalueJspParamName() {
      return this.beanTreeNode.isRtexprvalueJspParamName();
   }

   public void setRtexprvalueJspParamName(boolean value) {
      this.beanTreeNode.setRtexprvalueJspParamName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RtexprvalueJspParamName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isDebug() {
      return this.beanTreeNode.isDebug();
   }

   public void setDebug(boolean value) {
      this.beanTreeNode.setDebug(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Debug", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCompressHtmlTemplate() {
      return this.beanTreeNode.isCompressHtmlTemplate();
   }

   public void setCompressHtmlTemplate(boolean value) {
      this.beanTreeNode.setCompressHtmlTemplate(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CompressHtmlTemplate", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isOptimizeJavaExpression() {
      return this.beanTreeNode.isOptimizeJavaExpression();
   }

   public void setOptimizeJavaExpression(boolean value) {
      this.beanTreeNode.setOptimizeJavaExpression(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OptimizeJavaExpression", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getResourceProviderClass() {
      return this.beanTreeNode.getResourceProviderClass();
   }

   public void setResourceProviderClass(String value) {
      this.beanTreeNode.setResourceProviderClass(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ResourceProviderClass", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isStrictStaleCheck() {
      return this.beanTreeNode.isStrictStaleCheck();
   }

   public void setStrictStaleCheck(boolean value) {
      this.beanTreeNode.setStrictStaleCheck(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StrictStaleCheck", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isStrictJspDocumentValidation() {
      return this.beanTreeNode.isStrictJspDocumentValidation();
   }

   public void setStrictJspDocumentValidation(boolean value) {
      this.beanTreeNode.setStrictJspDocumentValidation(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StrictJspDocumentValidation", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getExpressionInterceptor() {
      return this.beanTreeNode.getExpressionInterceptor();
   }

   public void setExpressionInterceptor(String value) {
      this.beanTreeNode.setExpressionInterceptor(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ExpressionInterceptor", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isEL22BackwardCompatible() {
      return this.beanTreeNode.isEL22BackwardCompatible();
   }

   public void setEL22BackwardCompatible(boolean value) {
      this.beanTreeNode.setEL22BackwardCompatible(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EL22BackwardCompatible", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCompilerSourceVM() {
      return this.beanTreeNode.getCompilerSourceVM();
   }

   public void setCompilerSourceVM(String value) {
      this.beanTreeNode.setCompilerSourceVM(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CompilerSourceVM", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCompilerTargetVM() {
      return this.beanTreeNode.getCompilerTargetVM();
   }

   public void setCompilerTargetVM(String value) {
      this.beanTreeNode.setCompilerTargetVM(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CompilerTargetVM", (Object)null, (Object)null));
      this.setModified(true);
   }
}
