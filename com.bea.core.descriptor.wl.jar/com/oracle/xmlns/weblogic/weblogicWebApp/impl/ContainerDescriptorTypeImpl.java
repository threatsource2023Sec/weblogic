package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWebApp.ClassLoadingType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ContainerDescriptorType;
import com.oracle.xmlns.weblogic.weblogicWebApp.DefaultMimeTypeType;
import com.oracle.xmlns.weblogic.weblogicWebApp.GzipCompressionType;
import com.oracle.xmlns.weblogic.weblogicWebApp.IndexDirectorySortByType;
import com.oracle.xmlns.weblogic.weblogicWebApp.PreferApplicationPackagesType;
import com.oracle.xmlns.weblogic.weblogicWebApp.PreferApplicationResourcesType;
import com.oracle.xmlns.weblogic.weblogicWebApp.RedirectContentType;
import com.oracle.xmlns.weblogic.weblogicWebApp.RedirectContentTypeType;
import com.oracle.xmlns.weblogic.weblogicWebApp.RefererValidationType;
import com.oracle.xmlns.weblogic.weblogicWebApp.TrueFalseType;
import com.sun.java.xml.ns.javaee.EmptyType;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class ContainerDescriptorTypeImpl extends XmlComplexContentImpl implements ContainerDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName REFERERVALIDATION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "referer-validation");
   private static final QName CHECKAUTHONFORWARD$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "check-auth-on-forward");
   private static final QName FILTERDISPATCHEDREQUESTSENABLED$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "filter-dispatched-requests-enabled");
   private static final QName REDIRECTCONTENTTYPE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "redirect-content-type");
   private static final QName REDIRECTCONTENT$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "redirect-content");
   private static final QName REDIRECTWITHABSOLUTEURL$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "redirect-with-absolute-url");
   private static final QName INDEXDIRECTORYENABLED$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "index-directory-enabled");
   private static final QName INDEXDIRECTORYSORTBY$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "index-directory-sort-by");
   private static final QName SERVLETRELOADCHECKSECS$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "servlet-reload-check-secs");
   private static final QName RESOURCERELOADCHECKSECS$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "resource-reload-check-secs");
   private static final QName SINGLETHREADEDSERVLETPOOLSIZE$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "single-threaded-servlet-pool-size");
   private static final QName SESSIONMONITORINGENABLED$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "session-monitoring-enabled");
   private static final QName SAVESESSIONSENABLED$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "save-sessions-enabled");
   private static final QName PREFERWEBINFCLASSES$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "prefer-web-inf-classes");
   private static final QName PREFERAPPLICATIONPACKAGES$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "prefer-application-packages");
   private static final QName PREFERAPPLICATIONRESOURCES$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "prefer-application-resources");
   private static final QName DEFAULTMIMETYPE$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "default-mime-type");
   private static final QName CLIENTCERTPROXYENABLED$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "client-cert-proxy-enabled");
   private static final QName RELOGINENABLED$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "relogin-enabled");
   private static final QName ALLOWALLROLES$38 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "allow-all-roles");
   private static final QName NATIVEIOENABLED$40 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "native-io-enabled");
   private static final QName MINIMUMNATIVEFILESIZE$42 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "minimum-native-file-size");
   private static final QName DISABLEIMPLICITSERVLETMAPPINGS$44 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "disable-implicit-servlet-mappings");
   private static final QName TEMPDIR$46 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "temp-dir");
   private static final QName OPTIMISTICSERIALIZATION$48 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "optimistic-serialization");
   private static final QName RETAINORIGINALURL$50 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "retain-original-url");
   private static final QName SHOWARCHIVEDREALPATHENABLED$52 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "show-archived-real-path-enabled");
   private static final QName REQUIREADMINTRAFFIC$54 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "require-admin-traffic");
   private static final QName ACCESSLOGGINGDISABLED$56 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "access-logging-disabled");
   private static final QName PREFERFORWARDQUERYSTRING$58 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "prefer-forward-query-string");
   private static final QName FAILDEPLOYONFILTERINITERROR$60 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "fail-deploy-on-filter-init-error");
   private static final QName SENDPERMANENTREDIRECTS$62 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "send-permanent-redirects");
   private static final QName CONTAINERINITIALIZERENABLED$64 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "container-initializer-enabled");
   private static final QName LANGTAGREVISION$66 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "langtag-revision");
   private static final QName GZIPCOMPRESSION$68 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "gzip-compression");
   private static final QName CLASSLOADING$70 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "class-loading");
   private static final QName ID$72 = new QName("", "id");

   public ContainerDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public RefererValidationType.Enum getRefererValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REFERERVALIDATION$0, 0);
         return target == null ? null : (RefererValidationType.Enum)target.getEnumValue();
      }
   }

   public RefererValidationType xgetRefererValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RefererValidationType target = null;
         target = (RefererValidationType)this.get_store().find_element_user(REFERERVALIDATION$0, 0);
         return target;
      }
   }

   public boolean isSetRefererValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REFERERVALIDATION$0) != 0;
      }
   }

   public void setRefererValidation(RefererValidationType.Enum refererValidation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REFERERVALIDATION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REFERERVALIDATION$0);
         }

         target.setEnumValue(refererValidation);
      }
   }

   public void xsetRefererValidation(RefererValidationType refererValidation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RefererValidationType target = null;
         target = (RefererValidationType)this.get_store().find_element_user(REFERERVALIDATION$0, 0);
         if (target == null) {
            target = (RefererValidationType)this.get_store().add_element_user(REFERERVALIDATION$0);
         }

         target.set(refererValidation);
      }
   }

   public void unsetRefererValidation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REFERERVALIDATION$0, 0);
      }
   }

   public EmptyType getCheckAuthOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(CHECKAUTHONFORWARD$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCheckAuthOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHECKAUTHONFORWARD$2) != 0;
      }
   }

   public void setCheckAuthOnForward(EmptyType checkAuthOnForward) {
      this.generatedSetterHelperImpl(checkAuthOnForward, CHECKAUTHONFORWARD$2, 0, (short)1);
   }

   public EmptyType addNewCheckAuthOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(CHECKAUTHONFORWARD$2);
         return target;
      }
   }

   public void unsetCheckAuthOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHECKAUTHONFORWARD$2, 0);
      }
   }

   public TrueFalseType getFilterDispatchedRequestsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(FILTERDISPATCHEDREQUESTSENABLED$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFilterDispatchedRequestsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILTERDISPATCHEDREQUESTSENABLED$4) != 0;
      }
   }

   public void setFilterDispatchedRequestsEnabled(TrueFalseType filterDispatchedRequestsEnabled) {
      this.generatedSetterHelperImpl(filterDispatchedRequestsEnabled, FILTERDISPATCHEDREQUESTSENABLED$4, 0, (short)1);
   }

   public TrueFalseType addNewFilterDispatchedRequestsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(FILTERDISPATCHEDREQUESTSENABLED$4);
         return target;
      }
   }

   public void unsetFilterDispatchedRequestsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILTERDISPATCHEDREQUESTSENABLED$4, 0);
      }
   }

   public RedirectContentTypeType getRedirectContentType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RedirectContentTypeType target = null;
         target = (RedirectContentTypeType)this.get_store().find_element_user(REDIRECTCONTENTTYPE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRedirectContentType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REDIRECTCONTENTTYPE$6) != 0;
      }
   }

   public void setRedirectContentType(RedirectContentTypeType redirectContentType) {
      this.generatedSetterHelperImpl(redirectContentType, REDIRECTCONTENTTYPE$6, 0, (short)1);
   }

   public RedirectContentTypeType addNewRedirectContentType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RedirectContentTypeType target = null;
         target = (RedirectContentTypeType)this.get_store().add_element_user(REDIRECTCONTENTTYPE$6);
         return target;
      }
   }

   public void unsetRedirectContentType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REDIRECTCONTENTTYPE$6, 0);
      }
   }

   public RedirectContentType getRedirectContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RedirectContentType target = null;
         target = (RedirectContentType)this.get_store().find_element_user(REDIRECTCONTENT$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRedirectContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REDIRECTCONTENT$8) != 0;
      }
   }

   public void setRedirectContent(RedirectContentType redirectContent) {
      this.generatedSetterHelperImpl(redirectContent, REDIRECTCONTENT$8, 0, (short)1);
   }

   public RedirectContentType addNewRedirectContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RedirectContentType target = null;
         target = (RedirectContentType)this.get_store().add_element_user(REDIRECTCONTENT$8);
         return target;
      }
   }

   public void unsetRedirectContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REDIRECTCONTENT$8, 0);
      }
   }

   public TrueFalseType getRedirectWithAbsoluteUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(REDIRECTWITHABSOLUTEURL$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRedirectWithAbsoluteUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REDIRECTWITHABSOLUTEURL$10) != 0;
      }
   }

   public void setRedirectWithAbsoluteUrl(TrueFalseType redirectWithAbsoluteUrl) {
      this.generatedSetterHelperImpl(redirectWithAbsoluteUrl, REDIRECTWITHABSOLUTEURL$10, 0, (short)1);
   }

   public TrueFalseType addNewRedirectWithAbsoluteUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(REDIRECTWITHABSOLUTEURL$10);
         return target;
      }
   }

   public void unsetRedirectWithAbsoluteUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REDIRECTWITHABSOLUTEURL$10, 0);
      }
   }

   public TrueFalseType getIndexDirectoryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(INDEXDIRECTORYENABLED$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIndexDirectoryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INDEXDIRECTORYENABLED$12) != 0;
      }
   }

   public void setIndexDirectoryEnabled(TrueFalseType indexDirectoryEnabled) {
      this.generatedSetterHelperImpl(indexDirectoryEnabled, INDEXDIRECTORYENABLED$12, 0, (short)1);
   }

   public TrueFalseType addNewIndexDirectoryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(INDEXDIRECTORYENABLED$12);
         return target;
      }
   }

   public void unsetIndexDirectoryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INDEXDIRECTORYENABLED$12, 0);
      }
   }

   public IndexDirectorySortByType getIndexDirectorySortBy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IndexDirectorySortByType target = null;
         target = (IndexDirectorySortByType)this.get_store().find_element_user(INDEXDIRECTORYSORTBY$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIndexDirectorySortBy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INDEXDIRECTORYSORTBY$14) != 0;
      }
   }

   public void setIndexDirectorySortBy(IndexDirectorySortByType indexDirectorySortBy) {
      this.generatedSetterHelperImpl(indexDirectorySortBy, INDEXDIRECTORYSORTBY$14, 0, (short)1);
   }

   public IndexDirectorySortByType addNewIndexDirectorySortBy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IndexDirectorySortByType target = null;
         target = (IndexDirectorySortByType)this.get_store().add_element_user(INDEXDIRECTORYSORTBY$14);
         return target;
      }
   }

   public void unsetIndexDirectorySortBy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INDEXDIRECTORYSORTBY$14, 0);
      }
   }

   public XsdIntegerType getServletReloadCheckSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(SERVLETRELOADCHECKSECS$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetServletReloadCheckSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVLETRELOADCHECKSECS$16) != 0;
      }
   }

   public void setServletReloadCheckSecs(XsdIntegerType servletReloadCheckSecs) {
      this.generatedSetterHelperImpl(servletReloadCheckSecs, SERVLETRELOADCHECKSECS$16, 0, (short)1);
   }

   public XsdIntegerType addNewServletReloadCheckSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(SERVLETRELOADCHECKSECS$16);
         return target;
      }
   }

   public void unsetServletReloadCheckSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVLETRELOADCHECKSECS$16, 0);
      }
   }

   public XsdIntegerType getResourceReloadCheckSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(RESOURCERELOADCHECKSECS$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResourceReloadCheckSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCERELOADCHECKSECS$18) != 0;
      }
   }

   public void setResourceReloadCheckSecs(XsdIntegerType resourceReloadCheckSecs) {
      this.generatedSetterHelperImpl(resourceReloadCheckSecs, RESOURCERELOADCHECKSECS$18, 0, (short)1);
   }

   public XsdIntegerType addNewResourceReloadCheckSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(RESOURCERELOADCHECKSECS$18);
         return target;
      }
   }

   public void unsetResourceReloadCheckSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCERELOADCHECKSECS$18, 0);
      }
   }

   public XsdNonNegativeIntegerType getSingleThreadedServletPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(SINGLETHREADEDSERVLETPOOLSIZE$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSingleThreadedServletPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SINGLETHREADEDSERVLETPOOLSIZE$20) != 0;
      }
   }

   public void setSingleThreadedServletPoolSize(XsdNonNegativeIntegerType singleThreadedServletPoolSize) {
      this.generatedSetterHelperImpl(singleThreadedServletPoolSize, SINGLETHREADEDSERVLETPOOLSIZE$20, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewSingleThreadedServletPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(SINGLETHREADEDSERVLETPOOLSIZE$20);
         return target;
      }
   }

   public void unsetSingleThreadedServletPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SINGLETHREADEDSERVLETPOOLSIZE$20, 0);
      }
   }

   public TrueFalseType getSessionMonitoringEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SESSIONMONITORINGENABLED$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSessionMonitoringEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SESSIONMONITORINGENABLED$22) != 0;
      }
   }

   public void setSessionMonitoringEnabled(TrueFalseType sessionMonitoringEnabled) {
      this.generatedSetterHelperImpl(sessionMonitoringEnabled, SESSIONMONITORINGENABLED$22, 0, (short)1);
   }

   public TrueFalseType addNewSessionMonitoringEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SESSIONMONITORINGENABLED$22);
         return target;
      }
   }

   public void unsetSessionMonitoringEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SESSIONMONITORINGENABLED$22, 0);
      }
   }

   public TrueFalseType getSaveSessionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SAVESESSIONSENABLED$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSaveSessionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAVESESSIONSENABLED$24) != 0;
      }
   }

   public void setSaveSessionsEnabled(TrueFalseType saveSessionsEnabled) {
      this.generatedSetterHelperImpl(saveSessionsEnabled, SAVESESSIONSENABLED$24, 0, (short)1);
   }

   public TrueFalseType addNewSaveSessionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SAVESESSIONSENABLED$24);
         return target;
      }
   }

   public void unsetSaveSessionsEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAVESESSIONSENABLED$24, 0);
      }
   }

   public TrueFalseType getPreferWebInfClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(PREFERWEBINFCLASSES$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPreferWebInfClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREFERWEBINFCLASSES$26) != 0;
      }
   }

   public void setPreferWebInfClasses(TrueFalseType preferWebInfClasses) {
      this.generatedSetterHelperImpl(preferWebInfClasses, PREFERWEBINFCLASSES$26, 0, (short)1);
   }

   public TrueFalseType addNewPreferWebInfClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(PREFERWEBINFCLASSES$26);
         return target;
      }
   }

   public void unsetPreferWebInfClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREFERWEBINFCLASSES$26, 0);
      }
   }

   public PreferApplicationPackagesType getPreferApplicationPackages() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PreferApplicationPackagesType target = null;
         target = (PreferApplicationPackagesType)this.get_store().find_element_user(PREFERAPPLICATIONPACKAGES$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPreferApplicationPackages() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREFERAPPLICATIONPACKAGES$28) != 0;
      }
   }

   public void setPreferApplicationPackages(PreferApplicationPackagesType preferApplicationPackages) {
      this.generatedSetterHelperImpl(preferApplicationPackages, PREFERAPPLICATIONPACKAGES$28, 0, (short)1);
   }

   public PreferApplicationPackagesType addNewPreferApplicationPackages() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PreferApplicationPackagesType target = null;
         target = (PreferApplicationPackagesType)this.get_store().add_element_user(PREFERAPPLICATIONPACKAGES$28);
         return target;
      }
   }

   public void unsetPreferApplicationPackages() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREFERAPPLICATIONPACKAGES$28, 0);
      }
   }

   public PreferApplicationResourcesType getPreferApplicationResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PreferApplicationResourcesType target = null;
         target = (PreferApplicationResourcesType)this.get_store().find_element_user(PREFERAPPLICATIONRESOURCES$30, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPreferApplicationResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREFERAPPLICATIONRESOURCES$30) != 0;
      }
   }

   public void setPreferApplicationResources(PreferApplicationResourcesType preferApplicationResources) {
      this.generatedSetterHelperImpl(preferApplicationResources, PREFERAPPLICATIONRESOURCES$30, 0, (short)1);
   }

   public PreferApplicationResourcesType addNewPreferApplicationResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PreferApplicationResourcesType target = null;
         target = (PreferApplicationResourcesType)this.get_store().add_element_user(PREFERAPPLICATIONRESOURCES$30);
         return target;
      }
   }

   public void unsetPreferApplicationResources() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREFERAPPLICATIONRESOURCES$30, 0);
      }
   }

   public DefaultMimeTypeType getDefaultMimeType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMimeTypeType target = null;
         target = (DefaultMimeTypeType)this.get_store().find_element_user(DEFAULTMIMETYPE$32, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDefaultMimeType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTMIMETYPE$32) != 0;
      }
   }

   public void setDefaultMimeType(DefaultMimeTypeType defaultMimeType) {
      this.generatedSetterHelperImpl(defaultMimeType, DEFAULTMIMETYPE$32, 0, (short)1);
   }

   public DefaultMimeTypeType addNewDefaultMimeType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMimeTypeType target = null;
         target = (DefaultMimeTypeType)this.get_store().add_element_user(DEFAULTMIMETYPE$32);
         return target;
      }
   }

   public void unsetDefaultMimeType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTMIMETYPE$32, 0);
      }
   }

   public TrueFalseType getClientCertProxyEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CLIENTCERTPROXYENABLED$34, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClientCertProxyEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLIENTCERTPROXYENABLED$34) != 0;
      }
   }

   public void setClientCertProxyEnabled(TrueFalseType clientCertProxyEnabled) {
      this.generatedSetterHelperImpl(clientCertProxyEnabled, CLIENTCERTPROXYENABLED$34, 0, (short)1);
   }

   public TrueFalseType addNewClientCertProxyEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CLIENTCERTPROXYENABLED$34);
         return target;
      }
   }

   public void unsetClientCertProxyEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLIENTCERTPROXYENABLED$34, 0);
      }
   }

   public TrueFalseType getReloginEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(RELOGINENABLED$36, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetReloginEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RELOGINENABLED$36) != 0;
      }
   }

   public void setReloginEnabled(TrueFalseType reloginEnabled) {
      this.generatedSetterHelperImpl(reloginEnabled, RELOGINENABLED$36, 0, (short)1);
   }

   public TrueFalseType addNewReloginEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(RELOGINENABLED$36);
         return target;
      }
   }

   public void unsetReloginEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RELOGINENABLED$36, 0);
      }
   }

   public TrueFalseType getAllowAllRoles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ALLOWALLROLES$38, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAllowAllRoles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALLOWALLROLES$38) != 0;
      }
   }

   public void setAllowAllRoles(TrueFalseType allowAllRoles) {
      this.generatedSetterHelperImpl(allowAllRoles, ALLOWALLROLES$38, 0, (short)1);
   }

   public TrueFalseType addNewAllowAllRoles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ALLOWALLROLES$38);
         return target;
      }
   }

   public void unsetAllowAllRoles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALLOWALLROLES$38, 0);
      }
   }

   public TrueFalseType getNativeIoEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(NATIVEIOENABLED$40, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetNativeIoEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NATIVEIOENABLED$40) != 0;
      }
   }

   public void setNativeIoEnabled(TrueFalseType nativeIoEnabled) {
      this.generatedSetterHelperImpl(nativeIoEnabled, NATIVEIOENABLED$40, 0, (short)1);
   }

   public TrueFalseType addNewNativeIoEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(NATIVEIOENABLED$40);
         return target;
      }
   }

   public void unsetNativeIoEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NATIVEIOENABLED$40, 0);
      }
   }

   public long getMinimumNativeFileSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MINIMUMNATIVEFILESIZE$42, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetMinimumNativeFileSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MINIMUMNATIVEFILESIZE$42, 0);
         return target;
      }
   }

   public boolean isSetMinimumNativeFileSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINIMUMNATIVEFILESIZE$42) != 0;
      }
   }

   public void setMinimumNativeFileSize(long minimumNativeFileSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MINIMUMNATIVEFILESIZE$42, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MINIMUMNATIVEFILESIZE$42);
         }

         target.setLongValue(minimumNativeFileSize);
      }
   }

   public void xsetMinimumNativeFileSize(XmlLong minimumNativeFileSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MINIMUMNATIVEFILESIZE$42, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(MINIMUMNATIVEFILESIZE$42);
         }

         target.set(minimumNativeFileSize);
      }
   }

   public void unsetMinimumNativeFileSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINIMUMNATIVEFILESIZE$42, 0);
      }
   }

   public TrueFalseType getDisableImplicitServletMappings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(DISABLEIMPLICITSERVLETMAPPINGS$44, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDisableImplicitServletMappings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISABLEIMPLICITSERVLETMAPPINGS$44) != 0;
      }
   }

   public void setDisableImplicitServletMappings(TrueFalseType disableImplicitServletMappings) {
      this.generatedSetterHelperImpl(disableImplicitServletMappings, DISABLEIMPLICITSERVLETMAPPINGS$44, 0, (short)1);
   }

   public TrueFalseType addNewDisableImplicitServletMappings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(DISABLEIMPLICITSERVLETMAPPINGS$44);
         return target;
      }
   }

   public void unsetDisableImplicitServletMappings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISABLEIMPLICITSERVLETMAPPINGS$44, 0);
      }
   }

   public String getTempDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TEMPDIR$46, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTempDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TEMPDIR$46, 0);
         return target;
      }
   }

   public boolean isSetTempDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TEMPDIR$46) != 0;
      }
   }

   public void setTempDir(String tempDir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TEMPDIR$46, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TEMPDIR$46);
         }

         target.setStringValue(tempDir);
      }
   }

   public void xsetTempDir(XmlString tempDir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TEMPDIR$46, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TEMPDIR$46);
         }

         target.set(tempDir);
      }
   }

   public void unsetTempDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TEMPDIR$46, 0);
      }
   }

   public TrueFalseType getOptimisticSerialization() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(OPTIMISTICSERIALIZATION$48, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetOptimisticSerialization() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OPTIMISTICSERIALIZATION$48) != 0;
      }
   }

   public void setOptimisticSerialization(TrueFalseType optimisticSerialization) {
      this.generatedSetterHelperImpl(optimisticSerialization, OPTIMISTICSERIALIZATION$48, 0, (short)1);
   }

   public TrueFalseType addNewOptimisticSerialization() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(OPTIMISTICSERIALIZATION$48);
         return target;
      }
   }

   public void unsetOptimisticSerialization() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OPTIMISTICSERIALIZATION$48, 0);
      }
   }

   public TrueFalseType getRetainOriginalUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(RETAINORIGINALURL$50, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRetainOriginalUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RETAINORIGINALURL$50) != 0;
      }
   }

   public void setRetainOriginalUrl(TrueFalseType retainOriginalUrl) {
      this.generatedSetterHelperImpl(retainOriginalUrl, RETAINORIGINALURL$50, 0, (short)1);
   }

   public TrueFalseType addNewRetainOriginalUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(RETAINORIGINALURL$50);
         return target;
      }
   }

   public void unsetRetainOriginalUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RETAINORIGINALURL$50, 0);
      }
   }

   public TrueFalseType getShowArchivedRealPathEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SHOWARCHIVEDREALPATHENABLED$52, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetShowArchivedRealPathEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHOWARCHIVEDREALPATHENABLED$52) != 0;
      }
   }

   public void setShowArchivedRealPathEnabled(TrueFalseType showArchivedRealPathEnabled) {
      this.generatedSetterHelperImpl(showArchivedRealPathEnabled, SHOWARCHIVEDREALPATHENABLED$52, 0, (short)1);
   }

   public TrueFalseType addNewShowArchivedRealPathEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SHOWARCHIVEDREALPATHENABLED$52);
         return target;
      }
   }

   public void unsetShowArchivedRealPathEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHOWARCHIVEDREALPATHENABLED$52, 0);
      }
   }

   public TrueFalseType getRequireAdminTraffic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(REQUIREADMINTRAFFIC$54, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRequireAdminTraffic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUIREADMINTRAFFIC$54) != 0;
      }
   }

   public void setRequireAdminTraffic(TrueFalseType requireAdminTraffic) {
      this.generatedSetterHelperImpl(requireAdminTraffic, REQUIREADMINTRAFFIC$54, 0, (short)1);
   }

   public TrueFalseType addNewRequireAdminTraffic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(REQUIREADMINTRAFFIC$54);
         return target;
      }
   }

   public void unsetRequireAdminTraffic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUIREADMINTRAFFIC$54, 0);
      }
   }

   public TrueFalseType getAccessLoggingDisabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ACCESSLOGGINGDISABLED$56, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAccessLoggingDisabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACCESSLOGGINGDISABLED$56) != 0;
      }
   }

   public void setAccessLoggingDisabled(TrueFalseType accessLoggingDisabled) {
      this.generatedSetterHelperImpl(accessLoggingDisabled, ACCESSLOGGINGDISABLED$56, 0, (short)1);
   }

   public TrueFalseType addNewAccessLoggingDisabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ACCESSLOGGINGDISABLED$56);
         return target;
      }
   }

   public void unsetAccessLoggingDisabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACCESSLOGGINGDISABLED$56, 0);
      }
   }

   public TrueFalseType getPreferForwardQueryString() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(PREFERFORWARDQUERYSTRING$58, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPreferForwardQueryString() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREFERFORWARDQUERYSTRING$58) != 0;
      }
   }

   public void setPreferForwardQueryString(TrueFalseType preferForwardQueryString) {
      this.generatedSetterHelperImpl(preferForwardQueryString, PREFERFORWARDQUERYSTRING$58, 0, (short)1);
   }

   public TrueFalseType addNewPreferForwardQueryString() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(PREFERFORWARDQUERYSTRING$58);
         return target;
      }
   }

   public void unsetPreferForwardQueryString() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREFERFORWARDQUERYSTRING$58, 0);
      }
   }

   public TrueFalseType getFailDeployOnFilterInitError() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(FAILDEPLOYONFILTERINITERROR$60, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFailDeployOnFilterInitError() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FAILDEPLOYONFILTERINITERROR$60) != 0;
      }
   }

   public void setFailDeployOnFilterInitError(TrueFalseType failDeployOnFilterInitError) {
      this.generatedSetterHelperImpl(failDeployOnFilterInitError, FAILDEPLOYONFILTERINITERROR$60, 0, (short)1);
   }

   public TrueFalseType addNewFailDeployOnFilterInitError() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(FAILDEPLOYONFILTERINITERROR$60);
         return target;
      }
   }

   public void unsetFailDeployOnFilterInitError() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FAILDEPLOYONFILTERINITERROR$60, 0);
      }
   }

   public TrueFalseType getSendPermanentRedirects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SENDPERMANENTREDIRECTS$62, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSendPermanentRedirects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SENDPERMANENTREDIRECTS$62) != 0;
      }
   }

   public void setSendPermanentRedirects(TrueFalseType sendPermanentRedirects) {
      this.generatedSetterHelperImpl(sendPermanentRedirects, SENDPERMANENTREDIRECTS$62, 0, (short)1);
   }

   public TrueFalseType addNewSendPermanentRedirects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SENDPERMANENTREDIRECTS$62);
         return target;
      }
   }

   public void unsetSendPermanentRedirects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SENDPERMANENTREDIRECTS$62, 0);
      }
   }

   public TrueFalseType getContainerInitializerEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CONTAINERINITIALIZERENABLED$64, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetContainerInitializerEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONTAINERINITIALIZERENABLED$64) != 0;
      }
   }

   public void setContainerInitializerEnabled(TrueFalseType containerInitializerEnabled) {
      this.generatedSetterHelperImpl(containerInitializerEnabled, CONTAINERINITIALIZERENABLED$64, 0, (short)1);
   }

   public TrueFalseType addNewContainerInitializerEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CONTAINERINITIALIZERENABLED$64);
         return target;
      }
   }

   public void unsetContainerInitializerEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONTAINERINITIALIZERENABLED$64, 0);
      }
   }

   public String getLangtagRevision() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LANGTAGREVISION$66, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLangtagRevision() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LANGTAGREVISION$66, 0);
         return target;
      }
   }

   public boolean isSetLangtagRevision() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LANGTAGREVISION$66) != 0;
      }
   }

   public void setLangtagRevision(String langtagRevision) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LANGTAGREVISION$66, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LANGTAGREVISION$66);
         }

         target.setStringValue(langtagRevision);
      }
   }

   public void xsetLangtagRevision(XmlString langtagRevision) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LANGTAGREVISION$66, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LANGTAGREVISION$66);
         }

         target.set(langtagRevision);
      }
   }

   public void unsetLangtagRevision() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LANGTAGREVISION$66, 0);
      }
   }

   public GzipCompressionType getGzipCompression() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GzipCompressionType target = null;
         target = (GzipCompressionType)this.get_store().find_element_user(GZIPCOMPRESSION$68, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGzipCompression() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GZIPCOMPRESSION$68) != 0;
      }
   }

   public void setGzipCompression(GzipCompressionType gzipCompression) {
      this.generatedSetterHelperImpl(gzipCompression, GZIPCOMPRESSION$68, 0, (short)1);
   }

   public GzipCompressionType addNewGzipCompression() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GzipCompressionType target = null;
         target = (GzipCompressionType)this.get_store().add_element_user(GZIPCOMPRESSION$68);
         return target;
      }
   }

   public void unsetGzipCompression() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GZIPCOMPRESSION$68, 0);
      }
   }

   public ClassLoadingType getClassLoading() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassLoadingType target = null;
         target = (ClassLoadingType)this.get_store().find_element_user(CLASSLOADING$70, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClassLoading() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASSLOADING$70) != 0;
      }
   }

   public void setClassLoading(ClassLoadingType classLoading) {
      this.generatedSetterHelperImpl(classLoading, CLASSLOADING$70, 0, (short)1);
   }

   public ClassLoadingType addNewClassLoading() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassLoadingType target = null;
         target = (ClassLoadingType)this.get_store().add_element_user(CLASSLOADING$70);
         return target;
      }
   }

   public void unsetClassLoading() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASSLOADING$70, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$72);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$72);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$72) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$72);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$72);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$72);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$72);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$72);
      }
   }
}
