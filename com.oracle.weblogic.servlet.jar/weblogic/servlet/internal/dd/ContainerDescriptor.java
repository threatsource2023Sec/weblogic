package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.ContainerDescriptorMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class ContainerDescriptor extends BaseServletDescriptor implements ContainerDescriptorMBean {
   private static final String CHECK_AUTH_ON_FORWARD = "check-auth-on-forward";
   private static final String REDIRECT_WITH_ABSOLUTE_URL = "redirect-with-absolute-url";
   private static final String INDEX_DIRECTORY_ENABLED = "index-directory-enabled";
   private static final String FILTER_DISPATCHED_REQUESTS_ENABLED = "filter-dispatched-requests-enabled";
   private static final String SERVLET_RELOAD_CHECK_SECS = "servlet-reload-check-secs";
   private static final String SINGLE_THREADED_MODEL_POOLSIZE = "single-threaded-servlet-pool-size";
   private static final String SESSION_MONITORING_ENABLED = "session-monitoring-enabled";
   private static final String PREFER_WEBINF_CLASSES = "prefer-web-inf-classes";
   private static final String INDEX_DIRECTORY_SORT_BY = "index-directory-sort-by";
   private static final String SAVE_SESSIONS_ENABLED = "save-sessions-enabled";
   private static final String DEFAULT_MIME_TYPE = "default-mime-type";
   private static final String SEND_PERMANENT_REDIRECTS = "send-permanent-redirects";
   private boolean checkAuthOnForward = false;
   private boolean redirectWithAbsoluteURL = true;
   private boolean preferWebInfClasses = false;
   private boolean filterDispatchedRequestsEnabled = true;
   private boolean indexDirectoryEnabled = false;
   private String indexDirectorySortBy = null;
   private boolean sessionMonitoringEnabled = false;
   private boolean saveSessionsEnabled = false;
   private int servletReloadCheckSecs = 1;
   private boolean sendPermanentRedirects = false;
   private int singleThreadedServletPoolSize = 5;
   private boolean filterDispatchedRequestsEnabledSet = false;
   private boolean indexDirectoryEnabledSet = false;
   private boolean sessionMonitoringEnabledSet = false;
   private boolean saveSessionsEnabledSet = false;
   private boolean servletReloadCheckSecsSet = false;
   private boolean sendPermanentRedirectsSet = false;
   private boolean singleThreadedServletPoolSizeSet = false;
   private boolean preferWebInfClassesSet = false;
   private String defaultMimeType = null;

   public ContainerDescriptor() {
   }

   public ContainerDescriptor(Element parentElement) throws DOMProcessingException {
      String str = DOMUtils.getOptionalValueByTagName(parentElement, "check-auth-on-forward");
      if (str != null && (str.equals("") || str.equalsIgnoreCase("true"))) {
         this.checkAuthOnForward = true;
      }

      str = DOMUtils.getOptionalValueByTagName(parentElement, "redirect-with-absolute-url");
      if (str != null && str.equalsIgnoreCase("false")) {
         this.redirectWithAbsoluteURL = false;
      }

      str = DOMUtils.getOptionalValueByTagName(parentElement, "prefer-web-inf-classes");
      if (str != null) {
         this.preferWebInfClasses = str.equalsIgnoreCase("true");
         this.preferWebInfClassesSet = true;
      }

      str = DOMUtils.getOptionalValueByTagName(parentElement, "filter-dispatched-requests-enabled");
      if (str != null) {
         this.filterDispatchedRequestsEnabledSet = true;
         this.filterDispatchedRequestsEnabled = str.equalsIgnoreCase("true");
      }

      str = DOMUtils.getOptionalValueByTagName(parentElement, "index-directory-enabled");
      if (str != null) {
         this.indexDirectoryEnabledSet = true;
         this.indexDirectoryEnabled = str.equalsIgnoreCase("true");
      }

      str = DOMUtils.getOptionalValueByTagName(parentElement, "save-sessions-enabled");
      if (str != null) {
         this.saveSessionsEnabledSet = true;
         this.saveSessionsEnabled = str.equalsIgnoreCase("true");
      }

      this.indexDirectorySortBy = DOMUtils.getOptionalValueByTagName(parentElement, "index-directory-sort-by");
      if (this.indexDirectorySortBy != null) {
         if (this.indexDirectorySortBy.equalsIgnoreCase("NAME")) {
            this.indexDirectorySortBy = "NAME";
         } else if (this.indexDirectorySortBy.equalsIgnoreCase("LAST_MODIFIED")) {
            this.indexDirectorySortBy = "LAST_MODIFIED";
         } else {
            if (!this.indexDirectorySortBy.equalsIgnoreCase("SIZE")) {
               HTTPLogger.logInvalidIndexDirectorySortBy(this.indexDirectorySortBy);
               throw new DOMProcessingException("Invalid value assigned for element index-directory-sort-by in weblogic.xml: " + this.indexDirectorySortBy);
            }

            this.indexDirectorySortBy = "SIZE";
         }
      }

      str = DOMUtils.getOptionalValueByTagName(parentElement, "session-monitoring-enabled");
      if (str != null) {
         this.sessionMonitoringEnabledSet = true;
         this.sessionMonitoringEnabled = str.equalsIgnoreCase("true");
      }

      str = DOMUtils.getOptionalValueByTagName(parentElement, "servlet-reload-check-secs");
      if (str != null) {
         try {
            this.servletReloadCheckSecs = Integer.parseInt(str);
            this.servletReloadCheckSecsSet = true;
         } catch (NumberFormatException var5) {
            this.servletReloadCheckSecs = 1;
         }
      }

      str = DOMUtils.getOptionalValueByTagName(parentElement, "send-permanent-redirects");
      if (str != null) {
         this.sendPermanentRedirects = str.equalsIgnoreCase("true");
         this.sendPermanentRedirectsSet = true;
      }

      str = DOMUtils.getOptionalValueByTagName(parentElement, "single-threaded-servlet-pool-size");
      if (str != null) {
         try {
            this.singleThreadedServletPoolSize = Integer.parseInt(str);
            this.singleThreadedServletPoolSizeSet = true;
         } catch (NumberFormatException var4) {
            this.singleThreadedServletPoolSize = 5;
         }
      }

      this.defaultMimeType = DOMUtils.getOptionalValueByTagName(parentElement, "default-mime-type");
   }

   public boolean isCheckAuthOnForwardEnabled() {
      return this.checkAuthOnForward;
   }

   public void setCheckAuthOnForwardEnabled(boolean b) {
      boolean old = this.checkAuthOnForward;
      this.checkAuthOnForward = b;
      if (old != b) {
         this.firePropertyChange("checkAuthOnForwardEnabled", new Boolean(!b), new Boolean(b));
      }

   }

   public String getRedirectContentType() {
      return null;
   }

   public void setRedirectContentType(String t) {
   }

   public String getRedirectContent() {
      return null;
   }

   public void setRedirectContent(String c) {
   }

   public boolean isRedirectWithAbsoluteURLEnabled() {
      return this.redirectWithAbsoluteURL;
   }

   public void setRedirectWithAbsoluteURLEnabled(boolean b) {
      boolean old = this.redirectWithAbsoluteURL;
      this.redirectWithAbsoluteURL = b;
      if (old != b) {
         this.firePropertyChange("redirectWithAbsoluteURLEnabled", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isFilterDispatchedRequestsEnabled() {
      return this.filterDispatchedRequestsEnabled;
   }

   public void setFilterDispatchedRequestsEnabled(boolean b) {
      boolean old = this.filterDispatchedRequestsEnabled;
      this.filterDispatchedRequestsEnabled = b;
      if (old != b) {
         this.firePropertyChange("filterDispatchedRequestsEnabled", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isIndexDirectoryEnabled() {
      return this.indexDirectoryEnabled;
   }

   public void setIndexDirectoryEnabled(boolean b) {
      boolean old = this.indexDirectoryEnabled;
      this.indexDirectoryEnabled = b;
      if (old != b) {
         this.firePropertyChange("indexDirectoryEnabled", new Boolean(!b), new Boolean(b));
      }

   }

   public String getIndexDirectorySortBy() {
      return this.indexDirectorySortBy;
   }

   public void setIndexDirectorySortBy(String str) {
      String old = this.indexDirectorySortBy;
      this.indexDirectorySortBy = str;
      if (!comp(old, str)) {
         this.firePropertyChange("indexDirectorySortBy", old, str);
      }

   }

   public boolean isSessionMonitoringEnabled() {
      return this.sessionMonitoringEnabled;
   }

   public void setSessionMonitoringEnabled(boolean b) {
      boolean old = this.sessionMonitoringEnabled;
      this.sessionMonitoringEnabled = b;
      if (old != b) {
         this.firePropertyChange("sessionMonitoringEnabled", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isSaveSessionsEnabled() {
      return this.saveSessionsEnabled;
   }

   public void setSaveSessionsEnabled(boolean b) {
      boolean old = this.saveSessionsEnabled;
      this.saveSessionsEnabled = b;
      if (old != b) {
         this.firePropertyChange("saveSessionsEnabled", new Boolean(!b), new Boolean(b));
      }

   }

   public int getServletReloadCheckSecs() {
      return this.servletReloadCheckSecs;
   }

   public void setServletReloadCheckSecs(int i) {
      int old = this.servletReloadCheckSecs;
      this.servletReloadCheckSecs = i;
      if (old != i) {
         this.firePropertyChange("servletReloadCheckSecs", new Integer(old), new Integer(i));
      }

   }

   public boolean isSendPermanentRedirectsEnabled() {
      return this.sendPermanentRedirects;
   }

   public void setSendPermanentRedirectsEnabled(boolean b) {
      if (this.sendPermanentRedirects != b) {
         this.firePropertyChange("sendPermanetRedirects", new Boolean(!this.sendPermanentRedirects), new Boolean(b));
      }

   }

   public int getSingleThreadedServletPoolSize() {
      return this.singleThreadedServletPoolSize;
   }

   public void setSingleThreadedServletPoolSize(int i) {
      int old = this.singleThreadedServletPoolSize;
      this.singleThreadedServletPoolSize = i;
      if (old != i) {
         this.firePropertyChange("singleThreadedServletPoolSize", new Integer(old), new Integer(i));
      }

   }

   public boolean isPreferWebInfClasses() {
      return this.preferWebInfClasses;
   }

   public void setPreferWebInfClasses(boolean b) {
      boolean old = this.preferWebInfClasses;
      this.preferWebInfClasses = b;
      if (old != b) {
         this.firePropertyChange("preferWebInfClasses", new Boolean(!b), new Boolean(b));
      }

   }

   public void setDefaultMimeType(String s) {
      String old = this.defaultMimeType;
      this.defaultMimeType = s;
      if (!comp(old, s)) {
         this.firePropertyChange("defaultMimeType", old, s);
      }

   }

   public boolean isFilterDispatchedRequestsEnabledSet() {
      return this.filterDispatchedRequestsEnabledSet;
   }

   public boolean isIndexDirectoryEnabledSet() {
      return this.indexDirectoryEnabledSet;
   }

   public boolean isSaveSessionsEnabledSet() {
      return this.saveSessionsEnabledSet;
   }

   public boolean isSessionMonitoringEnabledSet() {
      return this.sessionMonitoringEnabledSet;
   }

   public boolean isServletReloadCheckSecsSet() {
      return this.servletReloadCheckSecsSet;
   }

   public boolean isSendPermanentRedirectsSet() {
      return this.sendPermanentRedirectsSet;
   }

   public boolean isSingleThreadedServletPoolSizeSet() {
      return this.singleThreadedServletPoolSizeSet;
   }

   public boolean isPreferWebInfClassesSet() {
      return this.preferWebInfClassesSet;
   }

   public String getDefaultMimeType() {
      return this.defaultMimeType;
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
      if (this.indexDirectorySortBy != null && !this.indexDirectorySortBy.equals("NAME") && !this.indexDirectorySortBy.equals("LAST_MODIFIED") && !this.indexDirectorySortBy.equals("SIZE")) {
         HTTPLogger.logInvalidIndexDirectorySortBy(this.indexDirectorySortBy);
         throw new DescriptorValidationException("Invalid value assigned for element index-directory-sort-by in weblogic.xml: " + this.indexDirectorySortBy);
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      boolean needed = false;
      if (this.checkAuthOnForward) {
         result = result + this.indentStr(indent + 2) + "<" + "check-auth-on-forward" + "/>\n";
         needed = true;
      }

      if (!this.filterDispatchedRequestsEnabled) {
         result = result + this.indentStr(indent + 2) + "<" + "filter-dispatched-requests-enabled" + ">" + this.filterDispatchedRequestsEnabled + "</" + "filter-dispatched-requests-enabled" + ">\n";
         needed = true;
      }

      if (!this.redirectWithAbsoluteURL) {
         result = result + this.indentStr(indent + 2) + "<" + "redirect-with-absolute-url" + ">" + this.redirectWithAbsoluteURL + "</" + "redirect-with-absolute-url" + ">\n";
         needed = true;
      }

      if (this.indexDirectoryEnabled) {
         result = result + this.indentStr(indent + 2) + "<" + "index-directory-enabled" + ">" + this.indexDirectoryEnabled + "</" + "index-directory-enabled" + ">\n";
         needed = true;
      }

      if (this.indexDirectorySortBy != null) {
         result = result + this.indentStr(indent + 2) + "<" + "index-directory-sort-by" + ">" + this.indexDirectorySortBy + "</" + "index-directory-sort-by" + ">\n";
         needed = true;
      }

      if (this.servletReloadCheckSecs != 1) {
         result = result + this.indentStr(indent + 2) + "<" + "servlet-reload-check-secs" + ">" + this.servletReloadCheckSecs + "</" + "servlet-reload-check-secs" + ">\n";
         needed = true;
      }

      if (this.sendPermanentRedirects) {
         result = result + this.indentStr(indent + 2) + "<" + "send-permanent-redirects" + ">" + this.sendPermanentRedirects + "</" + "send-permanent-redirects" + ">\n";
         needed = true;
      }

      if (this.singleThreadedServletPoolSize != 5) {
         result = result + this.indentStr(indent + 2) + "<" + "single-threaded-servlet-pool-size" + ">" + this.singleThreadedServletPoolSize + "</" + "single-threaded-servlet-pool-size" + ">\n";
         needed = true;
      }

      if (this.sessionMonitoringEnabled) {
         result = result + this.indentStr(indent + 2) + "<" + "session-monitoring-enabled" + ">" + this.sessionMonitoringEnabled + "</" + "session-monitoring-enabled" + ">\n";
         needed = true;
      }

      if (this.saveSessionsEnabled) {
         result = result + this.indentStr(indent + 2) + "<" + "save-sessions-enabled" + ">" + this.saveSessionsEnabled + "</" + "save-sessions-enabled" + ">\n";
         needed = true;
      }

      if (this.preferWebInfClasses) {
         result = result + this.indentStr(indent + 2) + "<" + "prefer-web-inf-classes" + ">" + this.preferWebInfClasses + "</" + "prefer-web-inf-classes" + ">\n";
         needed = true;
      }

      if (this.defaultMimeType != null) {
         result = result + this.indentStr(indent + 2) + "<" + "default-mime-type" + ">" + this.defaultMimeType + "</" + "default-mime-type" + ">\n";
         needed = true;
      }

      return needed ? this.indentStr(indent) + "<container-descriptor>\n" + result + this.indentStr(indent) + "</container-descriptor>\n" : "";
   }
}
