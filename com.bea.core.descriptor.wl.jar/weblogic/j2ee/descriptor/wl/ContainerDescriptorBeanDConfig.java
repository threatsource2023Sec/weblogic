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
import weblogic.j2ee.descriptor.EmptyBean;

public class ContainerDescriptorBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ContainerDescriptorBean beanTreeNode;

   public ContainerDescriptorBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ContainerDescriptorBean)btn;
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

   public String getRefererValidation() {
      return this.beanTreeNode.getRefererValidation();
   }

   public void setRefererValidation(String value) {
      this.beanTreeNode.setRefererValidation(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RefererValidation", (Object)null, (Object)null));
      this.setModified(true);
   }

   public EmptyBean getCheckAuthOnForward() {
      return this.beanTreeNode.getCheckAuthOnForward();
   }

   public boolean isFilterDispatchedRequestsEnabled() {
      return this.beanTreeNode.isFilterDispatchedRequestsEnabled();
   }

   public void setFilterDispatchedRequestsEnabled(boolean value) {
      this.beanTreeNode.setFilterDispatchedRequestsEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FilterDispatchedRequestsEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRedirectContentType() {
      return this.beanTreeNode.getRedirectContentType();
   }

   public void setRedirectContentType(String value) {
      this.beanTreeNode.setRedirectContentType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RedirectContentType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRedirectContent() {
      return this.beanTreeNode.getRedirectContent();
   }

   public void setRedirectContent(String value) {
      this.beanTreeNode.setRedirectContent(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RedirectContent", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isRedirectWithAbsoluteUrl() {
      return this.beanTreeNode.isRedirectWithAbsoluteUrl();
   }

   public void setRedirectWithAbsoluteUrl(boolean value) {
      this.beanTreeNode.setRedirectWithAbsoluteUrl(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RedirectWithAbsoluteUrl", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isIndexDirectoryEnabled() {
      return this.beanTreeNode.isIndexDirectoryEnabled();
   }

   public void setIndexDirectoryEnabled(boolean value) {
      this.beanTreeNode.setIndexDirectoryEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IndexDirectoryEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isIndexDirectoryEnabledSet() {
      return this.beanTreeNode.isIndexDirectoryEnabledSet();
   }

   public String getIndexDirectorySortBy() {
      return this.beanTreeNode.getIndexDirectorySortBy();
   }

   public void setIndexDirectorySortBy(String value) {
      this.beanTreeNode.setIndexDirectorySortBy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IndexDirectorySortBy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getServletReloadCheckSecs() {
      return this.beanTreeNode.getServletReloadCheckSecs();
   }

   public void setServletReloadCheckSecs(int value) {
      this.beanTreeNode.setServletReloadCheckSecs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ServletReloadCheckSecs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isServletReloadCheckSecsSet() {
      return this.beanTreeNode.isServletReloadCheckSecsSet();
   }

   public boolean isSendPermanentRedirects() {
      return this.beanTreeNode.isSendPermanentRedirects();
   }

   public void setSendPermanentRedirects(boolean value) {
      this.beanTreeNode.setSendPermanentRedirects(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SendPermanentRedirects", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getResourceReloadCheckSecs() {
      return this.beanTreeNode.getResourceReloadCheckSecs();
   }

   public void setResourceReloadCheckSecs(int value) {
      this.beanTreeNode.setResourceReloadCheckSecs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ResourceReloadCheckSecs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getSingleThreadedServletPoolSize() {
      return this.beanTreeNode.getSingleThreadedServletPoolSize();
   }

   public void setSingleThreadedServletPoolSize(int value) {
      this.beanTreeNode.setSingleThreadedServletPoolSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SingleThreadedServletPoolSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isSessionMonitoringEnabled() {
      return this.beanTreeNode.isSessionMonitoringEnabled();
   }

   public void setSessionMonitoringEnabled(boolean value) {
      this.beanTreeNode.setSessionMonitoringEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SessionMonitoringEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isSaveSessionsEnabled() {
      return this.beanTreeNode.isSaveSessionsEnabled();
   }

   public void setSaveSessionsEnabled(boolean value) {
      this.beanTreeNode.setSaveSessionsEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SaveSessionsEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPreferWebInfClasses() {
      return this.beanTreeNode.isPreferWebInfClasses();
   }

   public void setPreferWebInfClasses(boolean value) {
      this.beanTreeNode.setPreferWebInfClasses(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PreferWebInfClasses", (Object)null, (Object)null));
      this.setModified(true);
   }

   public PreferApplicationPackagesBean getPreferApplicationPackages() {
      return this.beanTreeNode.getPreferApplicationPackages();
   }

   public PreferApplicationResourcesBean getPreferApplicationResources() {
      return this.beanTreeNode.getPreferApplicationResources();
   }

   public String getDefaultMimeType() {
      return this.beanTreeNode.getDefaultMimeType();
   }

   public void setDefaultMimeType(String value) {
      this.beanTreeNode.setDefaultMimeType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultMimeType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isReloginEnabled() {
      return this.beanTreeNode.isReloginEnabled();
   }

   public void setReloginEnabled(boolean value) {
      this.beanTreeNode.setReloginEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ReloginEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isAllowAllRoles() {
      return this.beanTreeNode.isAllowAllRoles();
   }

   public void setAllowAllRoles(boolean value) {
      this.beanTreeNode.setAllowAllRoles(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AllowAllRoles", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isClientCertProxyEnabled() {
      return this.beanTreeNode.isClientCertProxyEnabled();
   }

   public void setClientCertProxyEnabled(boolean value) {
      this.beanTreeNode.setClientCertProxyEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClientCertProxyEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isNativeIOEnabled() {
      return this.beanTreeNode.isNativeIOEnabled();
   }

   public void setNativeIOEnabled(boolean value) {
      this.beanTreeNode.setNativeIOEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NativeIOEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getMinimumNativeFileSize() {
      return this.beanTreeNode.getMinimumNativeFileSize();
   }

   public void setMinimumNativeFileSize(long value) {
      this.beanTreeNode.setMinimumNativeFileSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MinimumNativeFileSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isDisableImplicitServletMappings() {
      return this.beanTreeNode.isDisableImplicitServletMappings();
   }

   public void setDisableImplicitServletMappings(boolean value) {
      this.beanTreeNode.setDisableImplicitServletMappings(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DisableImplicitServletMappings", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTempDir() {
      return this.beanTreeNode.getTempDir();
   }

   public void setTempDir(String value) {
      this.beanTreeNode.setTempDir(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TempDir", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isOptimisticSerialization() {
      return this.beanTreeNode.isOptimisticSerialization();
   }

   public void setOptimisticSerialization(boolean value) {
      this.beanTreeNode.setOptimisticSerialization(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OptimisticSerialization", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isRetainOriginalURL() {
      return this.beanTreeNode.isRetainOriginalURL();
   }

   public void setRetainOriginalURL(boolean value) {
      this.beanTreeNode.setRetainOriginalURL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RetainOriginalURL", (Object)null, (Object)null));
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

   public boolean isShowArchivedRealPathEnabled() {
      return this.beanTreeNode.isShowArchivedRealPathEnabled();
   }

   public void setShowArchivedRealPathEnabled(boolean value) {
      this.beanTreeNode.setShowArchivedRealPathEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ShowArchivedRealPathEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isShowArchivedRealPathEnabledSet() {
      return this.beanTreeNode.isShowArchivedRealPathEnabledSet();
   }

   public boolean isRequireAdminTraffic() {
      return this.beanTreeNode.isRequireAdminTraffic();
   }

   public void setRequireAdminTraffic(boolean value) {
      this.beanTreeNode.setRequireAdminTraffic(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequireAdminTraffic", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isAccessLoggingDisabled() {
      return this.beanTreeNode.isAccessLoggingDisabled();
   }

   public void setAccessLoggingDisabled(boolean value) {
      this.beanTreeNode.setAccessLoggingDisabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AccessLoggingDisabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isAccessLoggingDisabledSet() {
      return this.beanTreeNode.isAccessLoggingDisabledSet();
   }

   public boolean isPreferForwardQueryString() {
      return this.beanTreeNode.isPreferForwardQueryString();
   }

   public void setPreferForwardQueryString(boolean value) {
      this.beanTreeNode.setPreferForwardQueryString(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PreferForwardQueryString", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPreferForwardQueryStringSet() {
      return this.beanTreeNode.isPreferForwardQueryStringSet();
   }

   public boolean getFailDeployOnFilterInitError() {
      return this.beanTreeNode.getFailDeployOnFilterInitError();
   }

   public void setFailDeployOnFilterInitError(boolean value) {
      this.beanTreeNode.setFailDeployOnFilterInitError(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FailDeployOnFilterInitError", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isContainerInitializerEnabled() {
      return this.beanTreeNode.isContainerInitializerEnabled();
   }

   public void setContainerInitializerEnabled(boolean value) {
      this.beanTreeNode.setContainerInitializerEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ContainerInitializerEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isContainerInitializerEnabledSet() {
      return this.beanTreeNode.isContainerInitializerEnabledSet();
   }

   public String getLangtagRevision() {
      return this.beanTreeNode.getLangtagRevision();
   }

   public void setLangtagRevision(String value) {
      this.beanTreeNode.setLangtagRevision(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LangtagRevision", (Object)null, (Object)null));
      this.setModified(true);
   }

   public GzipCompressionBean getGzipCompression() {
      return this.beanTreeNode.getGzipCompression();
   }

   public ClassLoadingBean getClassLoading() {
      return this.beanTreeNode.getClassLoading();
   }
}
