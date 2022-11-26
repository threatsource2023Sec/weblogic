package weblogic.servlet.internal.dd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.webapp.EjbRefMBean;
import weblogic.management.descriptors.webapp.EnvEntryMBean;
import weblogic.management.descriptors.webapp.ErrorPageMBean;
import weblogic.management.descriptors.webapp.FilterMBean;
import weblogic.management.descriptors.webapp.FilterMappingMBean;
import weblogic.management.descriptors.webapp.ListenerMBean;
import weblogic.management.descriptors.webapp.LoginConfigMBean;
import weblogic.management.descriptors.webapp.MimeMappingMBean;
import weblogic.management.descriptors.webapp.ParameterMBean;
import weblogic.management.descriptors.webapp.ResourceEnvRefMBean;
import weblogic.management.descriptors.webapp.ResourceRefMBean;
import weblogic.management.descriptors.webapp.SecurityConstraintMBean;
import weblogic.management.descriptors.webapp.SecurityRoleMBean;
import weblogic.management.descriptors.webapp.ServletMBean;
import weblogic.management.descriptors.webapp.ServletMappingMBean;
import weblogic.management.descriptors.webapp.SessionConfigMBean;
import weblogic.management.descriptors.webapp.TagLibMBean;
import weblogic.management.descriptors.webapp.UIMBean;
import weblogic.management.descriptors.webapp.WebAppDescriptorMBean;
import weblogic.management.descriptors.webapp.WelcomeFileListMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class WebAppDescriptor extends BaseServletDescriptor implements ToXML, WebAppDescriptorMBean, DescriptorConstants, Comparator {
   private static final long serialVersionUID = -4056254418170227485L;
   private static final String CONTEXT_PARAM = "context-param";
   private static final String DISTRIBUTABLE = "distributable";
   private static final String SERVLET = "servlet";
   private static final String SERVLET_MAPPING = "servlet-mapping";
   private static final String SESSION_CONFIG = "session-config";
   private static final String MIME_MAPPING = "mime-mapping";
   private static final String WELCOME_FILE_LIST = "welcome-file-list";
   private static final String ERROR_PAGE = "error-page";
   private static final String RESOURCE_REF = "resource-ref";
   private static final String RESOURCE_ENV_REF = "resource-env-ref";
   private static final String ENV_ENTRY = "env-entry";
   private static final String EJB_REF = "ejb-ref";
   private static final String EJB_LOCAL_REF = "ejb-local-ref";
   private static final String TAGLIB = "taglib";
   private static final String TAGLIB_URI = "taglib-uri";
   private static final String TAGLIB_LOCATION = "taglib-location";
   private static final String SECURITY_ROLE = "security-role";
   private static final String SECURITY_CONSTRAINT = "security-constraint";
   private static final String LOGIN_CONFIG = "login-config";
   private static final String EVENT_LISTENER = "listener";
   private static final String FILTER = "filter";
   private static final String FILTER_MAPPING = "filter-mapping";
   private UIMBean uiData;
   private List contextParams;
   private List servlets;
   private List servletMaps;
   private SessionConfigMBean sessionConfig;
   private List mimeMaps;
   private WelcomeFileListMBean welcomeFiles;
   private List errorPages;
   private List resourceRefs;
   private List resourceEnvRefs;
   private List envEntries;
   private List ejbRefs;
   private List ejbLocalRefs;
   private List taglibs;
   private List secRoles;
   private List secCons;
   private List eventListeners;
   private List filters;
   private List filterMappings;
   private LoginConfigMBean login;
   private boolean distributable = false;
   private String root;
   private String deployedName;
   private boolean archived;
   private boolean deployed;
   private String descriptorEncoding = null;
   private String descriptorVersion = null;
   private static final String WL_JSP = "weblogic.jsp.";
   private static final String WL_SESSION = "weblogic.httpd.session.";
   private static final String WL_COOKIE = "weblogic.httpd.session.cookie.";

   public String getEncoding() {
      return this.descriptorEncoding;
   }

   public void setEncoding(String encoding) {
      this.descriptorEncoding = encoding;
   }

   public String getVersion() {
      return this.descriptorVersion;
   }

   public void setVersion(String version) {
      this.descriptorVersion = version;
   }

   public WebAppDescriptor() {
      this.sessionConfig = new SessionDescriptor();
   }

   public WebAppDescriptor(Element parentElement) throws DOMProcessingException {
      this.uiData = new UIDescriptor(parentElement);
      List elts = DOMUtils.getOptionalElementsByTagName(parentElement, "distributable");
      if (elts != null && elts.size() > 0) {
         this.distributable = true;
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "context-param");
      Iterator i = elts.iterator();
      this.contextParams = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.contextParams.add(new ParameterDescriptor((Element)i.next()));
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "listener");
      i = elts.iterator();
      this.eventListeners = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.eventListeners.add(new ListenerDescriptor((Element)i.next()));
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "security-role");
      i = elts.iterator();
      this.secRoles = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.secRoles.add(new SecurityRoleDescriptor((Element)i.next()));
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "servlet");
      i = elts.iterator();
      this.servlets = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.servlets.add(new ServletDescriptor(this, (Element)i.next()));
      }

      ServletMBean[] smbs = new ServletMBean[this.servlets.size()];
      this.servlets.toArray(smbs);
      this.setServlets(smbs);
      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "servlet-mapping");
      i = elts.iterator();
      this.servletMaps = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.servletMaps.add(new ServletMappingDescriptor(this, (Element)i.next()));
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "filter");
      i = elts.iterator();
      this.filters = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.filters.add(new FilterDescriptor((Element)i.next()));
      }

      FilterMBean[] fmbs = new FilterMBean[this.filters.size()];
      this.filters.toArray(fmbs);
      this.setFilters(fmbs);
      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "filter-mapping");
      i = elts.iterator();
      this.filterMappings = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.filterMappings.add(new FilterMappingDescriptor(this, (Element)i.next()));
      }

      Element elt = DOMUtils.getOptionalElementByTagName(parentElement, "session-config");
      if (elt != null) {
         this.sessionConfig = new SessionDescriptor(elt);
      } else {
         this.sessionConfig = new SessionDescriptor();
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "mime-mapping");
      i = elts.iterator();
      this.mimeMaps = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.mimeMaps.add(new MimeMappingDescriptor((Element)i.next()));
      }

      elt = DOMUtils.getOptionalElementByTagName(parentElement, "welcome-file-list");
      if (elt != null) {
         this.welcomeFiles = new WelcomeFilesDescriptor(elt);
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "error-page");
      i = elts.iterator();
      this.errorPages = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.errorPages.add(new ErrorPageDescriptor((Element)i.next()));
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "resource-ref");
      i = elts.iterator();
      this.resourceRefs = new ArrayList(elts.size());

      while(i.hasNext()) {
         ResourceReference r = new ResourceReference((Element)i.next());
         this.resourceRefs.add(r);
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "resource-env-ref");
      i = elts.iterator();
      this.resourceEnvRefs = new ArrayList(elts.size());

      while(i.hasNext()) {
         ResourceEnvRef r = new ResourceEnvRef((Element)i.next());
         this.resourceEnvRefs.add(r);
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "env-entry");
      i = elts.iterator();
      this.envEntries = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.envEntries.add(new EnvironmentEntry((Element)i.next()));
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "ejb-ref");
      i = elts.iterator();
      this.ejbRefs = new ArrayList(elts.size());

      while(i.hasNext()) {
         EJBReference ejb_ref = new EJBReference((Element)i.next());
         this.ejbRefs.add(ejb_ref);
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "ejb-local-ref");
      i = elts.iterator();
      this.ejbLocalRefs = new ArrayList(elts.size());

      while(i.hasNext()) {
         EJBLocalReference ejb_local_ref = new EJBLocalReference((Element)i.next());
         this.ejbLocalRefs.add(ejb_local_ref);
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "taglib");
      i = elts.iterator();
      this.taglibs = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.taglibs.add(new TaglibDescriptor((Element)i.next()));
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "security-constraint");
      i = elts.iterator();
      this.secCons = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.secCons.add(new SecurityConstraint(this, (Element)i.next()));
      }

      elt = DOMUtils.getOptionalElementByTagName(parentElement, "login-config");
      if (elt != null) {
         this.login = new LoginDescriptor(elt);
      }

   }

   public boolean isDeployed() {
      return this.deployed;
   }

   public void setDeployed(boolean b) {
      this.deployed = b;
   }

   public boolean isArchived() {
      return this.archived;
   }

   public void setArchived(boolean b) {
      this.archived = b;
   }

   public String getRoot() {
      return this.root;
   }

   public void setRoot(String s) {
      this.root = s;
   }

   public String getDeployedName() {
      return this.deployedName;
   }

   public void setDeployedName(String s) {
      this.deployedName = s;
   }

   public String getDisplayName() {
      return this.getUIData() != null ? this.getUIData().getDisplayName() : null;
   }

   public void setDisplayName(String s) {
      String old = this.getDisplayName();
      if (this.getUIData() != null) {
         this.getUIData().setDisplayName(s);
      }

      if (!comp(old, s)) {
         this.firePropertyChange("displayName", old, s);
      }

   }

   public UIMBean getUIData() {
      return this.uiData;
   }

   public void setUIData(UIMBean uim) {
      this.uiData = uim;
   }

   public boolean isDistributable() {
      return this.distributable;
   }

   public void setDistributable(boolean b) {
      if (b != this.isDistributable()) {
         this.distributable = b;
         this.firePropertyChange("distributable", new Boolean(!b), new Boolean(b));
      }

   }

   public ParameterMBean[] getContextParams() {
      if (this.contextParams == null) {
         return new ParameterDescriptor[0];
      } else {
         ParameterDescriptor[] ret = new ParameterDescriptor[this.contextParams.size()];
         this.contextParams.toArray(ret);
         return ret;
      }
   }

   public void setContextParams(ParameterMBean[] x) {
      ParameterMBean[] old = this.getContextParams();
      this.contextParams = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.contextParams.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("contextParams", old, x);
         }

      }
   }

   public void addContextParam(ParameterMBean x) {
      ParameterMBean[] prev = this.getContextParams();
      if (prev == null) {
         prev = new ParameterMBean[]{x};
         this.setContextParams(prev);
      } else {
         ParameterMBean[] curr = new ParameterMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setContextParams(curr);
      }
   }

   public void removeContextParam(ParameterMBean x) {
      ParameterMBean[] prev = this.getContextParams();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            ParameterMBean[] curr = new ParameterMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setContextParams(curr);
         }

      }
   }

   public ListenerMBean[] getListeners() {
      if (this.eventListeners == null) {
         return new ListenerDescriptor[0];
      } else {
         ListenerDescriptor[] ret = new ListenerDescriptor[this.eventListeners.size()];
         this.eventListeners.toArray(ret);
         return ret;
      }
   }

   public void setListeners(ListenerMBean[] x) {
      ListenerMBean[] old = this.getListeners();
      this.eventListeners = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.eventListeners.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("listeners", old, x);
         }

      }
   }

   public void addListener(ListenerMBean x) {
      ListenerMBean[] prev = this.getListeners();
      if (prev == null) {
         prev = new ListenerMBean[]{x};
         this.setListeners(prev);
      } else {
         ListenerMBean[] curr = new ListenerMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setListeners(curr);
      }
   }

   public void removeListener(ListenerMBean x) {
      ListenerMBean[] prev = this.getListeners();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            ListenerMBean[] curr = new ListenerMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setListeners(curr);
         }

      }
   }

   public FilterMBean[] getFilters() {
      if (this.filters == null) {
         return new FilterMBean[0];
      } else {
         FilterMBean[] ret = new FilterMBean[this.filters.size()];
         this.filters.toArray(ret);
         return ret;
      }
   }

   public void setFilters(FilterMBean[] x) {
      FilterMBean[] old = this.getFilters();
      this.filters = new ArrayList();
      if (x != null) {
         Arrays.sort(x, this);

         for(int i = 0; i < x.length; ++i) {
            this.filters.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("filters", old, x);
         }

      }
   }

   public void addFilter(FilterMBean x) {
      FilterMBean[] prev = this.getFilters();
      if (prev == null) {
         prev = new FilterMBean[1];
         prev[1] = x;
         this.setFilters(prev);
      } else {
         FilterMBean[] curr = new FilterMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setFilters(curr);
      }
   }

   public void removeFilter(FilterMBean x) {
      FilterMBean[] prev = this.getFilters();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            FilterMBean[] curr = new FilterMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setFilters(curr);
         }

      }
   }

   public FilterMappingMBean[] getFilterMappings() {
      if (this.filterMappings == null) {
         return new FilterMappingDescriptor[0];
      } else {
         FilterMappingDescriptor[] ret = new FilterMappingDescriptor[this.filterMappings.size()];
         this.filterMappings.toArray(ret);
         return ret;
      }
   }

   public void setFilterMappings(FilterMappingMBean[] x) {
      FilterMappingMBean[] old = this.getFilterMappings();
      this.filterMappings = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.filterMappings.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("filterMappings", old, x);
         }

      }
   }

   public void addFilterMapping(FilterMappingMBean x) {
      FilterMappingMBean[] prev = this.getFilterMappings();
      if (prev == null) {
         prev = new FilterMappingMBean[1];
         prev[1] = x;
         this.setFilterMappings(prev);
      } else {
         FilterMappingMBean[] curr = new FilterMappingMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setFilterMappings(curr);
      }
   }

   public void removeFilterMapping(FilterMappingMBean x) {
      FilterMappingMBean[] prev = this.getFilterMappings();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            FilterMappingMBean[] curr = new FilterMappingMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setFilterMappings(curr);
         }

      }
   }

   public ServletMBean[] getServlets() {
      if (this.servlets == null) {
         return new ServletMBean[0];
      } else {
         ServletMBean[] ret = new ServletMBean[this.servlets.size()];
         this.servlets.toArray(ret);
         return ret;
      }
   }

   public void setServlets(ServletMBean[] x) {
      ServletMBean[] old = this.getServlets();
      this.servlets = new ArrayList();
      if (x != null) {
         int i;
         for(i = 0; i < x.length; ++i) {
            if (x[i] == null) {
               throw new NullPointerException("null element " + i);
            }
         }

         Arrays.sort(x, this);

         for(i = 0; i < x.length; ++i) {
            this.servlets.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("servlets", old, x);
         }

      }
   }

   public void addServlet(ServletMBean servlet) {
      if (this.servlets == null) {
         this.servlets = new ArrayList();
      }

      this.servlets.add(servlet);
   }

   public void removeServlet(ServletMBean servlet) {
      if (this.servlets != null) {
         this.servlets.remove(servlet);
      }

   }

   public ServletMBean getServlet(String name) {
      if (name != null && this.servlets != null) {
         for(int i = 0; i < this.servlets.size(); ++i) {
            ServletMBean servlet = (ServletMBean)this.servlets.get(i);
            if (name.equals(servlet.getName())) {
               return servlet;
            }
         }
      }

      return null;
   }

   public String[] getServletNames() {
      ServletDescriptor[] smd = (ServletDescriptor[])((ServletDescriptor[])this.getServlets());
      String[] ret = new String[smd.length];

      for(int i = 0; i < ret.length; ++i) {
         ret[i] = smd[i].getServletName();
      }

      return ret;
   }

   public ServletMappingMBean[] getServletMappings() {
      if (this.servletMaps == null) {
         return new ServletMappingDescriptor[0];
      } else {
         ServletMappingDescriptor[] ret = new ServletMappingDescriptor[this.servletMaps.size()];
         return (ServletMappingDescriptor[])((ServletMappingDescriptor[])this.servletMaps.toArray(ret));
      }
   }

   public void setServletMappings(ServletMappingMBean[] x) {
      ServletMappingMBean[] old = this.getServletMappings();
      this.servletMaps = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.servletMaps.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("servletMappings", old, x);
         }

      }
   }

   public void addServletMapping(ServletMappingMBean servletMapping) {
      if (this.servletMaps == null) {
         this.servletMaps = new ArrayList();
      }

      this.servletMaps.add(servletMapping);
   }

   public void removeServletMapping(ServletMappingMBean servletMapping) {
      if (this.servletMaps != null) {
         this.servletMaps.remove(servletMapping);
      }

   }

   public SessionConfigMBean getSessionConfig() {
      return this.sessionConfig;
   }

   public void setSessionConfig(SessionConfigMBean sc) {
      this.sessionConfig = sc;
   }

   public MimeMappingMBean[] getMimeMappings() {
      if (this.mimeMaps == null) {
         return new MimeMappingDescriptor[0];
      } else {
         MimeMappingDescriptor[] ret = new MimeMappingDescriptor[this.mimeMaps.size()];
         this.mimeMaps.toArray(ret);
         return ret;
      }
   }

   public void setMimeMappings(MimeMappingMBean[] x) {
      MimeMappingMBean[] old = this.getMimeMappings();
      this.mimeMaps = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.mimeMaps.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("mimeMappings", old, x);
         }

      }
   }

   public void addMimeMapping(MimeMappingMBean mimeMapping) {
      if (this.mimeMaps == null) {
         this.mimeMaps = new ArrayList();
      }

      this.mimeMaps.add(mimeMapping);
   }

   public void removeMimeMapping(MimeMappingMBean mimeMapping) {
      if (this.mimeMaps != null) {
         this.mimeMaps.remove(mimeMapping);
      }
   }

   public WelcomeFileListMBean getWelcomeFiles() {
      return this.welcomeFiles;
   }

   public void setWelcomeFiles(WelcomeFileListMBean mbean) {
      this.welcomeFiles = mbean;
   }

   public ErrorPageMBean[] getErrorPages() {
      if (this.errorPages == null) {
         return new ErrorPageDescriptor[0];
      } else {
         ErrorPageDescriptor[] ret = new ErrorPageDescriptor[this.errorPages.size()];
         this.errorPages.toArray(ret);
         return ret;
      }
   }

   public void setErrorPages(ErrorPageMBean[] x) {
      ErrorPageMBean[] old = this.getErrorPages();
      this.errorPages = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.errorPages.add(new ErrorPageDescriptor(x[i]));
         }

         if (!comp(old, x)) {
            this.firePropertyChange("errorPages", old, x);
         }

      }
   }

   public void addErrorPage(ErrorPageMBean errorPage) {
      if (this.errorPages == null) {
         this.errorPages = new ArrayList();
      }

      this.errorPages.add(errorPage);
   }

   public void removeErrorPage(ErrorPageMBean errorPage) {
      this.errorPages.remove(errorPage);
   }

   public EnvEntryMBean[] getEnvironmentEntries() {
      if (this.envEntries == null) {
         return new EnvEntryMBean[0];
      } else {
         EnvironmentEntry[] ret = new EnvironmentEntry[this.envEntries.size()];
         return (EnvEntryMBean[])((EnvEntryMBean[])this.envEntries.toArray(ret));
      }
   }

   public void setEnvironmentEntries(EnvEntryMBean[] x) {
      EnvEntryMBean[] old = this.getEnvironmentEntries();
      this.envEntries = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.envEntries.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("environmentEntries", old, x);
         }

      }
   }

   public void addEnvironmentEntry(EnvEntryMBean envEntry) {
      if (this.envEntries == null) {
         this.envEntries = new ArrayList();
      }

      this.envEntries.add(envEntry);
   }

   public void removeEnvironmentEntry(EnvEntryMBean envEntry) {
      if (this.envEntries != null) {
         this.envEntries.remove(envEntry);
      }
   }

   public ResourceRefMBean[] getResourceReferences() {
      if (this.resourceRefs == null) {
         return new ResourceReference[0];
      } else {
         ResourceReference[] ret = new ResourceReference[this.resourceRefs.size()];
         return (ResourceRefMBean[])((ResourceRefMBean[])this.resourceRefs.toArray(ret));
      }
   }

   public void setResourceReferences(ResourceRefMBean[] x) {
      ResourceRefMBean[] old = this.getResourceReferences();
      this.resourceRefs = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.resourceRefs.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("resourceReferences", old, x);
         }

      }
   }

   public void addResourceReference(ResourceRefMBean resRef) {
      if (this.resourceRefs == null) {
         this.resourceRefs = new ArrayList();
      }

      this.resourceRefs.add(resRef);
   }

   public void removeResourceReference(ResourceRefMBean resRef) {
      if (this.resourceRefs != null) {
         this.resourceRefs.remove(resRef);
      }

   }

   public ResourceReference getResourceReference(String name) {
      if (this.resourceRefs == null) {
         return null;
      } else {
         Iterator i = this.resourceRefs.iterator();

         ResourceReference r;
         do {
            if (!i.hasNext()) {
               return null;
            }

            r = (ResourceReference)i.next();
         } while(!name.equals(r.getRefName()));

         return r;
      }
   }

   public ResourceEnvRefMBean[] getResourceEnvRefs() {
      if (this.resourceEnvRefs == null) {
         return new ResourceEnvRefMBean[0];
      } else {
         ResourceEnvRef[] ret = new ResourceEnvRef[this.resourceEnvRefs.size()];
         return (ResourceEnvRefMBean[])((ResourceEnvRefMBean[])this.resourceEnvRefs.toArray(ret));
      }
   }

   public void setResourceEnvRefs(ResourceEnvRefMBean[] x) {
      ResourceEnvRefMBean[] old = this.getResourceEnvRefs();
      this.resourceEnvRefs = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.resourceEnvRefs.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("resourceEnvRefs", old, x);
         }

      }
   }

   public void addResourceEnvRef(ResourceEnvRefMBean resEnvRef) {
      if (this.resourceEnvRefs == null) {
         this.resourceEnvRefs = new ArrayList();
      }

      this.resourceEnvRefs.add(resEnvRef);
   }

   public void removeResourceEnvRef(ResourceEnvRefMBean resEnvRef) {
      if (this.resourceEnvRefs != null) {
         this.resourceEnvRefs.remove(resEnvRef);
      }
   }

   public EjbRefMBean[] getEJBReferences() {
      if (this.ejbRefs == null) {
         return new EjbRefMBean[0];
      } else {
         EJBReference[] ret = new EJBReference[this.ejbRefs.size()];
         return (EjbRefMBean[])((EjbRefMBean[])this.ejbRefs.toArray(ret));
      }
   }

   public void setEJBReferences(EjbRefMBean[] x) {
      EjbRefMBean[] old = this.getEJBReferences();
      this.ejbRefs = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.ejbRefs.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("ejbReferences", old, x);
         }

      }
   }

   public void addEJBReference(EjbRefMBean ejbRef) {
      if (this.ejbRefs == null) {
         this.ejbRefs = new ArrayList();
      }

      this.ejbRefs.add(ejbRef);
   }

   public void removeEJBReference(EjbRefMBean ejbRef) {
      if (this.ejbRefs != null) {
         this.ejbRefs.remove(ejbRef);
      }

   }

   public EJBReference getEJBReference(String name) {
      if (this.ejbRefs == null) {
         return null;
      } else {
         Iterator i = this.ejbRefs.iterator();

         EJBReference r;
         do {
            if (!i.hasNext()) {
               return null;
            }

            r = (EJBReference)i.next();
         } while(!name.equals(r.getEJBRefName()));

         return r;
      }
   }

   public EjbRefMBean[] getEJBLocalReferences() {
      if (this.ejbLocalRefs == null) {
         return new EjbRefMBean[0];
      } else {
         EjbRefMBean[] ret = new EjbRefMBean[this.ejbLocalRefs.size()];
         return (EjbRefMBean[])((EjbRefMBean[])this.ejbLocalRefs.toArray(ret));
      }
   }

   public void setEJBLocalReferences(EjbRefMBean[] x) {
      EjbRefMBean[] old = this.getEJBLocalReferences();
      this.ejbLocalRefs = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.ejbLocalRefs.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("ejbLocalReferences", old, x);
         }

      }
   }

   public void addEJBLocalReference(EjbRefMBean ejbRef) {
      if (this.ejbLocalRefs == null) {
         this.ejbLocalRefs = new ArrayList();
      }

      this.ejbLocalRefs.add(ejbRef);
   }

   public void removeEJBLocalReference(EjbRefMBean ejbRef) {
      if (this.ejbLocalRefs != null) {
         this.ejbLocalRefs.remove(ejbRef);
      }

   }

   public EJBLocalReference getEJBLocalReference(String name) {
      if (this.ejbLocalRefs == null) {
         return null;
      } else {
         Iterator i = this.ejbLocalRefs.iterator();

         EJBLocalReference r;
         do {
            if (!i.hasNext()) {
               return null;
            }

            r = (EJBLocalReference)i.next();
         } while(!name.equals(r.getEJBRefName()));

         return r;
      }
   }

   public int getSessionTimeout() {
      SessionDescriptor sesconf = (SessionDescriptor)this.getSessionConfig();
      return sesconf != null ? sesconf.getSessionTimeout() : -2;
   }

   public TagLibMBean[] getTagLibs() {
      if (this.taglibs == null) {
         return new TaglibDescriptor[0];
      } else {
         TaglibDescriptor[] ret = new TaglibDescriptor[this.taglibs.size()];
         return (TagLibMBean[])((TagLibMBean[])this.taglibs.toArray(ret));
      }
   }

   public void setTagLibs(TagLibMBean[] x) {
      TagLibMBean[] old = this.getTagLibs();
      this.taglibs = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.taglibs.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("taglibs", old, x);
         }

      }
   }

   public void addTagLib(TagLibMBean taglib) {
      if (this.taglibs == null) {
         this.taglibs = new ArrayList();
      }

      this.taglibs.add(taglib);
   }

   public void removeTagLib(TagLibMBean taglib) {
      if (this.taglibs != null) {
         this.taglibs.remove(taglib);
      }
   }

   public SecurityRoleMBean[] getSecurityRoles() {
      if (this.secRoles == null) {
         return new SecurityRoleMBean[0];
      } else {
         SecurityRoleMBean[] ret = new SecurityRoleMBean[this.secRoles.size()];
         return (SecurityRoleMBean[])((SecurityRoleMBean[])this.secRoles.toArray(ret));
      }
   }

   public void setSecurityRoles(SecurityRoleMBean[] x) {
      SecurityRoleMBean[] old = this.getSecurityRoles();
      this.secRoles = new ArrayList();

      for(int i = 0; i < x.length; ++i) {
         this.secRoles.add(x[i]);
      }

   }

   public void addSecurityRole(SecurityRoleMBean role) {
      if (this.secRoles == null) {
         this.secRoles = new ArrayList();
      }

      this.secRoles.add(role);
   }

   public void removeSecurityRole(SecurityRoleMBean role) {
      if (this.secRoles != null) {
         this.secRoles.remove(role);
      }
   }

   public String[] getSecurityRoleNames() {
      SecurityRoleDescriptor[] roles = (SecurityRoleDescriptor[])((SecurityRoleDescriptor[])this.getSecurityRoles());
      String[] ret = new String[roles.length];

      for(int i = 0; i < ret.length; ++i) {
         ret[i] = roles[i].getRoleName();
      }

      return ret;
   }

   public SecurityConstraintMBean[] getSecurityConstraints() {
      if (this.secCons == null) {
         return new SecurityConstraint[0];
      } else {
         SecurityConstraint[] ret = new SecurityConstraint[this.secCons.size()];
         this.secCons.toArray(ret);
         return ret;
      }
   }

   public void setSecurityConstraints(SecurityConstraintMBean[] x) {
      SecurityConstraintMBean[] old = this.getSecurityConstraints();
      this.secCons = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.secCons.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("securityConstraints", old, x);
         }

      }
   }

   public void addSecurityConstraint(SecurityConstraintMBean constraint) {
      if (this.secCons == null) {
         this.secCons = new ArrayList();
      }

      this.secCons.add(constraint);
   }

   public void removeSecurityConstraint(SecurityConstraintMBean constraint) {
      if (this.secCons != null) {
         this.secCons.remove(constraint);
      }
   }

   public LoginConfigMBean getLoginConfig() {
      return this.login;
   }

   public void setLoginConfig(LoginConfigMBean ld) {
      this.login = ld;
   }

   private ParameterDescriptor getPD(String pname) {
      ParameterDescriptor[] params = (ParameterDescriptor[])((ParameterDescriptor[])this.getContextParams());

      for(int i = 0; i < params.length; ++i) {
         if (pname.equals(params[i].getParamName())) {
            return params[i];
         }
      }

      return null;
   }

   private boolean getBooleanPD(String name, boolean defalt) {
      ParameterDescriptor pd = this.getPD(name);
      return pd == null ? defalt : "true".equalsIgnoreCase(pd.getParamValue());
   }

   private String getStringPD(String name, String defalt) {
      ParameterDescriptor pd = this.getPD(name);
      return pd == null ? defalt : pd.getParamValue();
   }

   private int getIntPD(String name, int defalt) {
      ParameterDescriptor pd = this.getPD(name);
      if (pd == null) {
         return defalt;
      } else {
         String s = pd.getParamValue();
         return s != null && (s = s.trim()).length() != 0 ? Integer.parseInt(s) : defalt;
      }
   }

   private void addPD(String name, String val, String desc) {
      ParameterDescriptor pd = new ParameterDescriptor(name, val, desc);
      ParameterDescriptor[] params = (ParameterDescriptor[])((ParameterDescriptor[])this.getContextParams());
      ParameterDescriptor[] newParams = new ParameterDescriptor[params.length + 1];
      System.arraycopy(params, 0, newParams, 0, params.length);
      newParams[params.length] = pd;
      this.setContextParams(newParams);
   }

   public boolean getWLJSPPrecompile() {
      return this.getBooleanPD("weblogic.jsp.precompile", false);
   }

   public void setWLJSPPrecompile(boolean b) {
      String pname = "weblogic.jsp.precompile";
      ParameterDescriptor pd = this.getPD(pname);
      if (pd == null) {
         this.addPD(pname, "" + b, "Controls whether to precompile all web-app JSP's on server startup");
      } else {
         pd.setParamValue("" + b);
      }

   }

   public String getWLJSPCompileCommand() {
      return this.getStringPD("weblogic.jsp.compileCommand", "javac");
   }

   public void setWLJSPCompileCommand(String s) {
      String pname = "weblogic.jsp.compileCommand";
      ParameterDescriptor pd = this.getPD(pname);
      if (pd == null) {
         this.addPD(pname, s, "java compiler executable used to generate JSP pages");
      } else {
         pd.setParamValue(s);
      }

   }

   public String getWLJSPCompileClass() {
      return this.getStringPD("weblogic.jsp.compilerclass", (String)null);
   }

   public void setWLJSPCompileClass(String s) {
      String pname = "weblogic.jsp.compilerclass";
      ParameterDescriptor pd = this.getPD(pname);
      if (pd == null) {
         this.addPD(pname, s, "java compiler executable used to generate JSP pages");
      } else {
         pd.setParamValue(s);
      }

   }

   public boolean getWLJSPVerbose() {
      return this.getBooleanPD("weblogic.jsp.verbose", true);
   }

   public void setWLJSPVerbose(boolean b) {
      String pname = "weblogic.jsp.verbose";
      ParameterDescriptor pd = this.getPD(pname);
      if (pd == null) {
         this.addPD(pname, "" + b, "Enables browser-friendly JSP error reporting and verbose JSP logging");
      } else {
         pd.setParamValue("" + b);
      }

   }

   public String getWLJSPPackagePrefix() {
      return this.getStringPD("weblogic.jsp.packagePrefix", "jsp_servlet");
   }

   public void setWLJSPPackagePrefix(String s) {
      String pname = "weblogic.jsp.packagePrefix";
      ParameterDescriptor pd = this.getPD(pname);
      if (pd == null) {
         this.addPD(pname, s, "java package prefix for generated JSP code");
      } else {
         pd.setParamValue(s);
      }

   }

   public String getWLJSPEncoding() {
      return this.getStringPD("weblogic.jsp.encoding", "");
   }

   public void setWLJSPEncoding(String s) {
      String pname = "weblogic.jsp.encoding";
      ParameterDescriptor pd = this.getPD(pname);
      if (pd == null) {
         this.addPD(pname, s, "default i18n charset for JSP pages in this web application");
      } else {
         pd.setParamValue(s);
      }

   }

   public boolean getWLJSPKeepgenerated() {
      return this.getBooleanPD("weblogic.jsp.keepgenerated", false);
   }

   public void setWLJSPKeepgenerated(boolean b) {
      String pname = "weblogic.jsp.keepgenerated";
      ParameterDescriptor pd = this.getPD(pname);
      if (pd == null) {
         this.addPD(pname, "" + b, "Enables saving generated JSP sources in the JSP working directory - useful for debugging");
      } else {
         pd.setParamValue("" + b);
      }

   }

   public boolean getWLSessionPersistence() {
      return this.getBooleanPD("weblogic.httpd.session.persistence", false);
   }

   public void setWLSessionPersistence(boolean b) {
      String pname = "weblogic.httpd.session.persistence";
      ParameterDescriptor pd = this.getPD(pname);
      if (pd == null) {
         this.addPD(pname, "" + b, "Enables one of WebLogic's persistent HTTP session stores");
      } else {
         pd.setParamValue("" + b);
      }

   }

   public String getWLSessionPersistenceType() {
      return this.getStringPD("weblogic.httpd.session.persistentStoreType", "file");
   }

   public void setWLSessionPersistenceType(String s) {
      if (!"file".equals(s) && !"replicated".equals(s) && !"replicated_if_clustered".equals(s) && !"jdbc".equals(s) && !"cookie".equals(s)) {
         throw new IllegalArgumentException("persistent store type must be one of: 'memory'|'file'|'replicated'|'replicated_if_clustered'|'jdbc'|'cookie' not '" + s + "'");
      } else {
         String pname = "weblogic.httpd.session.persistentStoreType";
         ParameterDescriptor pd = this.getPD(pname);
         if (pd == null) {
            this.addPD(pname, s, "Selects the type of WebLogic's persistent HTTP session stores");
         } else {
            pd.setParamValue(s);
         }

      }
   }

   public boolean getWLSessionDebug() {
      return this.getBooleanPD("weblogic.httpd.session.debug", false);
   }

   public void setWLSessionDebug(boolean b) {
      String pname = "weblogic.httpd.session.debug";
      ParameterDescriptor pd = this.getPD(pname);
      if (pd == null) {
         this.addPD(pname, "" + b, "Enables verbose logging of session activity for debugging");
      } else {
         pd.setParamValue("" + b);
      }

   }

   public int getWLSessionIDLength() {
      return this.getIntPD("weblogic.httpd.session.IDLength", 52);
   }

   public void setWLSessionIDLength(int i) {
      String pname = "weblogic.httpd.session.IDLength";
      if (i >= 10 && i <= 200) {
         ParameterDescriptor pd = this.getPD(pname);
         if (pd == null) {
            this.addPD(pname, "" + i, "Specifies the length in chars of the random part of generated session IDs");
         } else {
            pd.setParamValue("" + i);
         }

      } else {
         throw new IllegalArgumentException("sessionid length must be > 10 && < 200: not " + i);
      }
   }

   public int getWLSessionInvalidationInterval() {
      return this.getIntPD("weblogic.httpd.session.invalidationIntervalSecs", 60);
   }

   public void setWLSessionInvalidationInterval(int i) {
      String pname = "weblogic.httpd.session.invalidationIntervalSecs";
      if (i < 20) {
         throw new IllegalArgumentException("invalidation interval seconds must be > 20: not " + i);
      } else {
         ParameterDescriptor pd = this.getPD(pname);
         if (pd == null) {
            this.addPD(pname, "" + i, "Specifies the frequency in seconds where stored sessions are checked for expiration");
         } else {
            pd.setParamValue("" + i);
         }

      }
   }

   public int getWLCookieAge() {
      return this.getIntPD("weblogic.httpd.session.cookie.maxAgeSecs", -1);
   }

   public void setWLCookieAge(int i) {
      String pname = "weblogic.httpd.session.cookie.maxAgeSecs";
      if (i <= 0 && i != -1) {
         throw new IllegalArgumentException("cookie timeout must be >0 || == -1: not " + i);
      } else {
         ParameterDescriptor pd = this.getPD(pname);
         if (pd == null) {
            this.addPD(pname, "" + i, "Specifies in seconds the 'expires' field of HTTP cookies used for session tracking");
         } else {
            pd.setParamValue("" + i);
         }

      }
   }

   public String getWLSessionPersistentStoreDir() {
      return this.getStringPD("weblogic.httpd.session.persistentStoreDir", "session_db");
   }

   public void setWLSessionPersistentStoreDir(String s) {
      String pname = "weblogic.httpd.session.persistentStoreDir";
      ParameterDescriptor pd = this.getPD(pname);
      if (pd == null) {
         this.addPD(pname, s, "filesystem path (absolute or relative to server home) to be used for file persistence");
      } else {
         pd.setParamValue(s);
      }

   }

   public String getWLSessionPersistentStoreCookieName() {
      return this.getStringPD("weblogic.httpd.session.persistentStoreCookieName", "WLCOOKIE");
   }

   public void setWLSessionPersistentStoreCookieName(String s) {
      String pname = "weblogic.httpd.session.persistentStoreCookieName";
      ParameterDescriptor pd = this.getPD(pname);
      if (pd == null) {
         this.addPD(pname, s, "Cookie name used to store the attributes for a cookie based session persistence");
      } else {
         pd.setParamValue(s);
      }

   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
      boolean ok = true;
      Iterator i;
      if (this.contextParams != null) {
         for(i = this.contextParams.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.servlets != null) {
         for(i = this.servlets.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.servletMaps != null) {
         for(i = this.servletMaps.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.sessionConfig != null) {
         ok &= this.check(this.sessionConfig);
      }

      if (this.mimeMaps != null) {
         for(i = this.mimeMaps.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.welcomeFiles != null) {
         ok &= this.check(this.welcomeFiles);
      }

      if (this.errorPages != null) {
         for(i = this.errorPages.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.resourceRefs != null) {
         for(i = this.resourceRefs.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.resourceEnvRefs != null) {
         for(i = this.resourceEnvRefs.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.envEntries != null) {
         for(i = this.envEntries.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.ejbRefs != null) {
         for(i = this.ejbRefs.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.taglibs != null) {
         for(i = this.taglibs.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.secRoles != null) {
         for(i = this.secRoles.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.secCons != null) {
         for(i = this.secCons.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.eventListeners != null) {
         for(i = this.eventListeners.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      if (this.filters != null) {
         for(i = this.filters.iterator(); i.hasNext(); ok &= this.check((WebElementMBean)i.next())) {
         }
      }

      WebElementMBean x;
      if (this.filterMappings != null) {
         for(i = this.filterMappings.iterator(); i.hasNext(); ok &= this.check(x)) {
            x = (WebElementMBean)i.next();
         }
      }

      if (this.login != null) {
         ok &= this.check(this.login);
      }

      if (!ok) {
         String[] err = this.getDescriptorErrors();
         throw new DescriptorValidationException(this.arrayToString(err));
      }
   }

   public String toXML(int indent) {
      String result = "";
      String webAppDescriptorEncoding = this.getEncoding();
      if (webAppDescriptorEncoding != null) {
         result = result + "<?xml version=\"1.0\" encoding=\"" + webAppDescriptorEncoding + "\"?>\n";
      }

      result = result + this.indentStr(indent) + "<!DOCTYPE web-app PUBLIC \"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN\" \"http://java.sun.com/dtd/web-app_2_3.dtd\">";
      result = result + "\n" + this.indentStr(indent) + "<web-app>\n";
      indent += 2;
      if (this.uiData != null) {
         result = result + "\n" + this.uiData.toXML(indent);
      }

      if (this.distributable) {
         result = result + "\n" + this.indentStr(indent) + "<distributable/>\n";
      }

      Iterator i;
      ParameterDescriptor pd;
      if (this.contextParams != null) {
         for(i = this.contextParams.iterator(); i.hasNext(); result = result + "\n" + pd.toXML(indent)) {
            pd = (ParameterDescriptor)i.next();
         }
      }

      FilterDescriptor fd;
      if (this.filters != null) {
         for(i = this.filters.iterator(); i.hasNext(); result = result + "\n" + fd.toXML(indent)) {
            fd = (FilterDescriptor)i.next();
         }
      }

      FilterMappingDescriptor fd;
      if (this.filterMappings != null) {
         for(i = this.filterMappings.iterator(); i.hasNext(); result = result + "\n" + fd.toXML(indent)) {
            fd = (FilterMappingDescriptor)i.next();
         }
      }

      ListenerDescriptor ld;
      if (this.eventListeners != null) {
         for(i = this.eventListeners.iterator(); i.hasNext(); result = result + "\n" + ld.toXML(indent)) {
            ld = (ListenerDescriptor)i.next();
         }
      }

      ServletDescriptor sd;
      if (this.servlets != null) {
         for(i = this.servlets.iterator(); i.hasNext(); result = result + "\n" + sd.toXML(indent)) {
            sd = (ServletDescriptor)i.next();
         }
      }

      ServletMappingDescriptor smd;
      if (this.servletMaps != null) {
         for(i = this.servletMaps.iterator(); i.hasNext(); result = result + "\n" + smd.toXML(indent)) {
            smd = (ServletMappingDescriptor)i.next();
         }
      }

      if (this.sessionConfig != null) {
         result = result + "\n" + this.sessionConfig.toXML(indent);
      }

      MimeMappingDescriptor mmd;
      if (this.mimeMaps != null) {
         for(i = this.mimeMaps.iterator(); i.hasNext(); result = result + "\n" + mmd.toXML(indent)) {
            mmd = (MimeMappingDescriptor)i.next();
         }
      }

      if (this.welcomeFiles != null) {
         result = result + "\n" + this.welcomeFiles.toXML(indent);
      }

      ErrorPageDescriptor epd;
      if (this.errorPages != null) {
         for(i = this.errorPages.iterator(); i.hasNext(); result = result + "\n" + epd.toXML(indent)) {
            epd = (ErrorPageDescriptor)i.next();
         }
      }

      TaglibDescriptor tld;
      if (this.taglibs != null) {
         for(i = this.taglibs.iterator(); i.hasNext(); result = result + "\n" + tld.toXML(indent)) {
            tld = (TaglibDescriptor)i.next();
         }
      }

      if (this.resourceEnvRefs != null) {
         i = this.resourceEnvRefs.iterator();

         while(i.hasNext()) {
            ResourceEnvRef rer = (ResourceEnvRef)i.next();

            try {
               rer.validate();
            } catch (DescriptorValidationException var8) {
               HTTPLogger.logDescriptorValidationFailure("web.xml", "resource-env-ref", var8);
               continue;
            }

            result = result + "\n" + rer.toXML(indent);
         }
      }

      if (this.resourceRefs != null) {
         i = this.resourceRefs.iterator();

         while(i.hasNext()) {
            ResourceReference rr = (ResourceReference)i.next();

            try {
               rr.validate();
            } catch (DescriptorValidationException var7) {
               HTTPLogger.logDescriptorValidationFailure("web.xml", "resource-ref", var7);
               continue;
            }

            result = result + "\n" + rr.toXML(indent);
         }
      }

      SecurityConstraint sc;
      if (this.secCons != null) {
         for(i = this.secCons.iterator(); i.hasNext(); result = result + "\n" + sc.toXML(indent)) {
            sc = (SecurityConstraint)i.next();
         }
      }

      if (this.login != null) {
         result = result + "\n" + this.login.toXML(indent);
      }

      SecurityRoleDescriptor rd;
      if (this.secRoles != null) {
         for(i = this.secRoles.iterator(); i.hasNext(); result = result + "\n" + rd.toXML(indent)) {
            rd = (SecurityRoleDescriptor)i.next();
         }
      }

      EnvironmentEntry ee;
      if (this.envEntries != null) {
         for(i = this.envEntries.iterator(); i.hasNext(); result = result + "\n" + ee.toXML(indent)) {
            ee = (EnvironmentEntry)i.next();
         }
      }

      EJBReference ejb;
      if (this.ejbRefs != null) {
         for(i = this.ejbRefs.iterator(); i.hasNext(); result = result + "\n" + ejb.toXML(indent)) {
            ejb = (EJBReference)i.next();
         }
      }

      if (this.ejbLocalRefs != null) {
         for(i = this.ejbLocalRefs.iterator(); i.hasNext(); result = result + "\n" + ejb.toXML(indent)) {
            ejb = (EJBReference)i.next();
         }
      }

      indent -= 2;
      result = result + "\n" + this.indentStr(indent) + "</web-app>";
      return result;
   }

   public int compare(Object o1, Object o2) {
      int ret = 0;
      if (o1 instanceof ServletMBean) {
         ServletMBean s1 = (ServletMBean)o1;
         ServletMBean s2 = (ServletMBean)o2;
         ret = s1.getServletName().compareTo(s2.getServletName());
      } else if (o1 instanceof FilterMBean) {
         FilterMBean f1 = (FilterMBean)o1;
         FilterMBean f2 = (FilterMBean)o2;
         ret = f1.getFilterName().compareTo(f2.getFilterName());
      }

      return ret;
   }

   public boolean equals(Object o) {
      return o == this;
   }
}
