package weblogic.servlet.internal.dd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.AuthFilterMBean;
import weblogic.management.descriptors.webappext.CharsetParamsMBean;
import weblogic.management.descriptors.webappext.ContainerDescriptorMBean;
import weblogic.management.descriptors.webappext.DestroyAsMBean;
import weblogic.management.descriptors.webappext.InitAsMBean;
import weblogic.management.descriptors.webappext.JspDescriptorMBean;
import weblogic.management.descriptors.webappext.PreprocessorMBean;
import weblogic.management.descriptors.webappext.PreprocessorMappingMBean;
import weblogic.management.descriptors.webappext.ReferenceDescriptorMBean;
import weblogic.management.descriptors.webappext.RunAsRoleAssignmentMBean;
import weblogic.management.descriptors.webappext.SecurityPermissionMBean;
import weblogic.management.descriptors.webappext.SecurityRoleAssignmentMBean;
import weblogic.management.descriptors.webappext.ServletDescriptorMBean;
import weblogic.management.descriptors.webappext.SessionDescriptorMBean;
import weblogic.management.descriptors.webappext.URLMatchMapMBean;
import weblogic.management.descriptors.webappext.VirtualDirectoryMappingMBean;
import weblogic.management.descriptors.webappext.WebAppExtDescriptorMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class WLWebAppDescriptor extends BaseServletDescriptor implements ToXML, WebAppExtDescriptorMBean, DescriptorConstants {
   private static final long serialVersionUID = -7317362224751399947L;
   private static final String CHARSET_PARAMS = "charset-params";
   private static final String CONTAINER_DESCRIPTOR = "container-descriptor";
   private static final String CONTEXT_ROOT = "context-root";
   private static final String DISPATCH_POLICY = "wl-dispatch-policy";
   private static final String DESCRIPTION = "description";
   private static final String VERSION = "weblogic-version";
   private static final String S_R_ASSIGNMENT = "security-role-assignment";
   private static final String REFERENCE = "reference-descriptor";
   private static final String SESSION = "session-descriptor";
   private static final String JSP = "jsp-descriptor";
   private static final String AUTH_FILTER = "auth-filter";
   private static final String VIRTUAL_DIRECTORY_MAPPING = "virtual-directory-mapping";
   private static final String URL_MATCH_MAP = "url-match-map";
   private static final String PREPROCESSOR = "preprocessor";
   private static final String PREPROCESSOR_MAPPING = "preprocessor-mapping";
   private static final String SECURITY_PERMISSION = "security-permission";
   private static final String INIT_AS = "init-as";
   private static final String DESTROY_AS = "destroy-as";
   private static final String SERVLET_DESCRIPTOR = "servlet-descriptor";
   private static final String RUN_AS_ROLE_ASSIGNMENT = "run-as-role-assignment";
   private String wlVersion;
   private String wlDescription;
   private SecurityRoleAssignmentMBean[] securityRoleAssignments;
   private VirtualDirectoryMappingMBean[] virtualDirectories;
   private ReferenceDescriptorMBean refDes;
   private SessionDescriptorMBean sessDes;
   private JspDescriptorMBean jspDes;
   private AuthFilterMBean wlAuthFilter;
   private URLMatchMapMBean urlMatchMap;
   private ContainerDescriptorMBean containerDescriptor;
   private CharsetParamsMBean charsetParams;
   private PreprocessorMBean[] preprocessors;
   private PreprocessorMappingMBean[] preprocessorMaps;
   private SecurityPermissionMBean securityPermission;
   private String contextRoot;
   private String dispatchPolicy;
   private ServletDescriptorMBean[] servletDescriptors;
   private ArrayList migrationServletDescriptors;
   private RunAsRoleAssignmentMBean[] runAsRoleAssignments;
   private String descriptorEncoding = null;
   private String descriptorVersion = null;

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

   public WLWebAppDescriptor() {
      this.jspDes = new WLJspDescriptor();
      this.sessDes = new WLSessionDescriptor();
   }

   public WLWebAppDescriptor(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      this.wlDescription = DOMUtils.getOptionalValueByTagName(parentElement, "description");
      this.wlVersion = DOMUtils.getOptionalValueByTagName(parentElement, "weblogic-version");
      String authFilter = DOMUtils.getOptionalValueByTagName(parentElement, "auth-filter");
      if (authFilter != null && authFilter.trim().length() > 0) {
         this.wlAuthFilter = new AuthFilter(authFilter);
      }

      String eltstr = DOMUtils.getOptionalValueByTagName(parentElement, "url-match-map");
      if (eltstr != null && eltstr.length() != 0) {
         this.urlMatchMap = new URLMatchMapDescriptor(eltstr);
      }

      Element elt = DOMUtils.getOptionalElementByTagName(parentElement, "container-descriptor");
      if (elt != null) {
         this.containerDescriptor = new ContainerDescriptor(elt);
      }

      elt = DOMUtils.getOptionalElementByTagName(parentElement, "security-permission");
      if (elt != null) {
         this.securityPermission = new SecurityPermissionDescriptor(elt);
      }

      elt = DOMUtils.getOptionalElementByTagName(parentElement, "charset-params");
      if (elt != null) {
         this.charsetParams = new CharsetParams(elt);
      }

      List elts = DOMUtils.getOptionalElementsByTagName(parentElement, "security-role-assignment");
      Iterator i = elts.iterator();
      SecurityRoleAssignmentMBean[] srAssignments = new SecurityRoleAssignmentMBean[elts.size()];

      int j;
      for(j = 0; i.hasNext() && j < srAssignments.length; ++j) {
         srAssignments[j] = new SecurityRoleAssignment(wad, (Element)i.next());
      }

      this.securityRoleAssignments = srAssignments;
      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "servlet-descriptor");
      int j;
      if (elts.size() > 0) {
         i = elts.iterator();
         ServletDescriptorMBean[] mbeans = new ServletDescriptorMBean[elts.size()];

         for(j = 0; j < mbeans.length; ++j) {
            mbeans[j] = new WLServletDescriptor(wad, (Element)i.next());
         }

         this.servletDescriptors = mbeans;
      } else {
         this.servletDescriptors = new ServletDescriptorMBean[0];
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "init-as");
      boolean exists;
      int count;
      WLServletDescriptor servletDesc;
      if (elts.size() > 0) {
         i = elts.iterator();

         for(j = 0; j < elts.size(); ++j) {
            InitAsMBean mbean = new InitAsDescriptor(wad, (Element)i.next());
            exists = false;
            if (this.servletDescriptors != null) {
               for(count = 0; count < this.servletDescriptors.length; ++count) {
                  if (this.servletDescriptors[count].getServletName().equals(mbean.getServletName())) {
                     this.servletDescriptors[count].setInitAsPrincipalName(mbean.getPrincipalName());
                     exists = true;
                  }
               }
            }

            if (!exists) {
               servletDesc = new WLServletDescriptor(wad, mbean.getServletName());
               servletDesc.setInitAsPrincipalName(mbean.getPrincipalName());
               if (this.migrationServletDescriptors == null) {
                  this.migrationServletDescriptors = new ArrayList();
               }

               this.migrationServletDescriptors.add(servletDesc);
            }
         }
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "destroy-as");
      if (elts.size() > 0) {
         i = elts.iterator();

         for(j = 0; j < elts.size(); ++j) {
            DestroyAsMBean mbean = new DestroyAsDescriptor(wad, (Element)i.next());
            exists = false;
            if (this.servletDescriptors != null) {
               for(count = 0; count < this.servletDescriptors.length; ++count) {
                  if (this.servletDescriptors[count].getServletName().equals(mbean.getServletName())) {
                     this.servletDescriptors[count].setDestroyAsPrincipalName(mbean.getPrincipalName());
                     exists = true;
                  }
               }
            }

            if (!exists && this.migrationServletDescriptors != null) {
               for(Iterator iter = this.migrationServletDescriptors.iterator(); iter.hasNext(); exists = true) {
                  ServletDescriptorMBean destAs = (ServletDescriptorMBean)iter.next();
                  destAs.setDestroyAsPrincipalName(mbean.getPrincipalName());
               }
            }

            if (!exists) {
               servletDesc = new WLServletDescriptor(wad, mbean.getServletName());
               servletDesc.setDestroyAsPrincipalName(mbean.getPrincipalName());
               if (this.migrationServletDescriptors == null) {
                  this.migrationServletDescriptors = new ArrayList();
               }

               this.migrationServletDescriptors.add(servletDesc);
            }
         }
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "run-as-role-assignment");
      if (elts.size() > 0) {
         i = elts.iterator();
         RunAsRoleAssignmentMBean[] mbeans = new RunAsRoleAssignmentMBean[elts.size()];

         for(j = 0; i.hasNext() && j < mbeans.length; ++j) {
            mbeans[j] = new RunAsRoleAssignment(wad, (Element)i.next());
         }

         this.runAsRoleAssignments = mbeans;
      } else {
         this.runAsRoleAssignments = null;
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "virtual-directory-mapping");
      if (elts.size() > 0) {
         i = elts.iterator();
         VirtualDirectoryMappingMBean[] virtualDirs = new VirtualDirectoryMappingMBean[elts.size()];

         for(j = 0; i.hasNext() && j < virtualDirs.length; ++j) {
            virtualDirs[j] = new VirtualDirectoryMappingDescriptor((Element)i.next());
         }

         this.virtualDirectories = virtualDirs;
      } else {
         this.virtualDirectories = null;
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "preprocessor");
      if (elts.size() > 0) {
         i = elts.iterator();
         PreprocessorMBean[] prep = new PreprocessorMBean[elts.size()];

         for(j = 0; i.hasNext() && j < prep.length; ++j) {
            prep[j] = new PreprocessorDescriptor((Element)i.next());
         }

         this.preprocessors = prep;
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "preprocessor-mapping");
      if (elts.size() > 0) {
         i = elts.iterator();
         PreprocessorMappingMBean[] prepMaps = new PreprocessorMappingMBean[elts.size()];

         for(j = 0; i.hasNext() && j < prepMaps.length; ++j) {
            prepMaps[j] = new PreprocessorMappingDescriptor(this, (Element)i.next());
         }

         if (prepMaps != null) {
            this.preprocessorMaps = prepMaps;
         }
      }

      elt = DOMUtils.getOptionalElementByTagName(parentElement, "reference-descriptor");
      if (elt != null) {
         this.refDes = new ReferenceDescriptor(wad, elt);
      }

      elt = DOMUtils.getOptionalElementByTagName(parentElement, "session-descriptor");
      if (elt != null) {
         this.sessDes = new WLSessionDescriptor(elt);
      }

      elt = DOMUtils.getOptionalElementByTagName(parentElement, "jsp-descriptor");
      if (elt != null) {
         this.jspDes = new WLJspDescriptor(elt);
      }

      this.contextRoot = DOMUtils.getOptionalValueByTagName(parentElement, "context-root");
      this.dispatchPolicy = DOMUtils.getOptionalValueByTagName(parentElement, "wl-dispatch-policy");
      if (this.sessDes == null) {
         this.sessDes = new WLSessionDescriptor();
      }

      if (this.jspDes == null) {
         this.jspDes = new WLJspDescriptor();
      }

   }

   public String getWebLogicVersion() {
      return this.wlVersion;
   }

   public void setWebLogicVersion(String wlVer) {
      String old = this.getWebLogicVersion();
      if (!comp(old, wlVer)) {
         this.wlVersion = wlVer;
         this.firePropertyChange("wlVersion", old, wlVer);
      }

   }

   public AuthFilterMBean getAuthFilter() {
      return this.wlAuthFilter;
   }

   public void setAuthFilter(AuthFilterMBean wlAF) {
      this.wlAuthFilter = wlAF;
   }

   public URLMatchMapMBean getURLMatchMap() {
      return this.urlMatchMap;
   }

   public void setURLMatchMap(URLMatchMapMBean map) {
      this.urlMatchMap = map;
   }

   public String getDescription() {
      return this.wlDescription;
   }

   public void setDescription(String desc) {
      String old = this.getDescription();
      if (!comp(old, desc)) {
         this.wlDescription = desc;
         this.firePropertyChange("description", old, desc);
      }

   }

   public ReferenceDescriptorMBean getReferenceDescriptor() {
      return this.refDes;
   }

   public void setReferenceDescriptor(ReferenceDescriptorMBean rd) {
      this.refDes = rd;
   }

   public SessionDescriptorMBean getSessionDescriptor() {
      return this.sessDes;
   }

   public void setSessionDescriptor(SessionDescriptorMBean sd) {
      this.sessDes = sd;
   }

   public JspDescriptorMBean getJspDescriptor() {
      return this.jspDes;
   }

   public void setJspDescriptor(JspDescriptorMBean jd) {
      this.jspDes = jd;
   }

   public void setSecurityRoleAssignments(SecurityRoleAssignmentMBean[] x) {
      SecurityRoleAssignmentMBean[] old = this.securityRoleAssignments;
      this.securityRoleAssignments = x;
   }

   public RunAsRoleAssignmentMBean[] getRunAsRoleAssignments() {
      if (this.runAsRoleAssignments == null) {
         this.runAsRoleAssignments = new RunAsRoleAssignmentMBean[0];
      }

      return this.runAsRoleAssignments;
   }

   public void setRunAsRoleAssignments(RunAsRoleAssignmentMBean[] x) {
      this.runAsRoleAssignments = x;
   }

   public SecurityRoleAssignmentMBean[] getSecurityRoleAssignments() {
      if (this.securityRoleAssignments == null) {
         this.securityRoleAssignments = new SecurityRoleAssignmentMBean[0];
      }

      return this.securityRoleAssignments;
   }

   public void addSecurityRoleAssignment(SecurityRoleAssignmentMBean x) {
      SecurityRoleAssignmentMBean[] prev = this.getSecurityRoleAssignments();
      if (prev == null) {
         prev = new SecurityRoleAssignmentMBean[]{x};
         this.setSecurityRoleAssignments(prev);
      } else {
         SecurityRoleAssignmentMBean[] curr = new SecurityRoleAssignmentMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setSecurityRoleAssignments(curr);
      }
   }

   public void removeSecurityRoleAssignment(SecurityRoleAssignmentMBean x) {
      SecurityRoleAssignmentMBean[] prev = this.getSecurityRoleAssignments();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            SecurityRoleAssignmentMBean[] curr = new SecurityRoleAssignmentMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setSecurityRoleAssignments(curr);
         }

      }
   }

   public void setVirtualDirectoryMappings(VirtualDirectoryMappingMBean[] x) {
      VirtualDirectoryMappingMBean[] old = this.virtualDirectories;
      this.virtualDirectories = x;
      if (!comp(old, x)) {
         this.firePropertyChange("virtualDirectoryMappings", old, x);
      }

   }

   public VirtualDirectoryMappingMBean[] getVirtualDirectoryMappings() {
      if (this.virtualDirectories == null) {
         this.virtualDirectories = new VirtualDirectoryMappingMBean[0];
      }

      return this.virtualDirectories;
   }

   public void addVirtualDirectoryMapping(VirtualDirectoryMappingMBean x) {
      VirtualDirectoryMappingMBean[] prev = this.getVirtualDirectoryMappings();
      if (prev == null) {
         prev = new VirtualDirectoryMappingMBean[]{x};
         this.setVirtualDirectoryMappings(prev);
      } else {
         VirtualDirectoryMappingMBean[] curr = new VirtualDirectoryMappingMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setVirtualDirectoryMappings(curr);
      }
   }

   public void removeVirtualDirectoryMapping(VirtualDirectoryMappingMBean x) {
      VirtualDirectoryMappingMBean[] prev = this.getVirtualDirectoryMappings();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            VirtualDirectoryMappingMBean[] curr = new VirtualDirectoryMappingMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setVirtualDirectoryMappings(curr);
         }

      }
   }

   public boolean containsServletDescriptor(ServletDescriptorMBean dd) {
      if (this.servletDescriptors == null) {
         return false;
      } else {
         int len = this.servletDescriptors.length;

         for(int i = 0; i < len; ++i) {
            if (dd == this.servletDescriptors[i]) {
               return true;
            }
         }

         return false;
      }
   }

   public void addServletDescriptor(ServletDescriptorMBean dd) {
      if (dd == null) {
         throw new NullPointerException("null arg");
      } else {
         if (this.servletDescriptors == null) {
            this.servletDescriptors = new ServletDescriptorMBean[0];
         }

         int len = this.servletDescriptors.length;
         ServletDescriptorMBean[] tmp = new ServletDescriptorMBean[len + 1];
         System.arraycopy(this.servletDescriptors, 0, tmp, 0, len);
         tmp[len] = dd;
         this.servletDescriptors = tmp;
      }
   }

   public void removeServletDescriptor(ServletDescriptorMBean dd) {
      if (this.servletDescriptors != null && dd != null) {
         List l = new ArrayList();

         for(int i = 0; i < this.servletDescriptors.length; ++i) {
            if (this.servletDescriptors[i] != dd) {
               l.add(this.servletDescriptors[i]);
            }
         }

         ServletDescriptorMBean[] tmp = new ServletDescriptorMBean[l.size()];
         l.toArray(tmp);
         this.servletDescriptors = tmp;
      }
   }

   public void setContextRoot(String root) {
      String old = this.contextRoot;
      this.contextRoot = root;
      if (!comp(old, root)) {
         this.firePropertyChange("contextRoot", old, root);
      }

   }

   public String getContextRoot() {
      return this.contextRoot;
   }

   public void setDispatchPolicy(String policy) {
      String old = this.dispatchPolicy;
      this.dispatchPolicy = policy;
      if (!comp(old, policy)) {
         this.firePropertyChange("dispatchPolicy", old, policy);
      }

   }

   public String getDispatchPolicy() {
      return this.dispatchPolicy;
   }

   public void setContainerDescriptor(ContainerDescriptorMBean cd) {
      this.containerDescriptor = cd;
   }

   public ContainerDescriptorMBean getContainerDescriptor() {
      return this.containerDescriptor;
   }

   public void setCharsetParams(CharsetParamsMBean c) {
      this.charsetParams = c;
   }

   public CharsetParamsMBean getCharsetParams() {
      return this.charsetParams;
   }

   public void setSecurityPermissionMBean(SecurityPermissionMBean mbean) {
      this.securityPermission = mbean;
   }

   public SecurityPermissionMBean getSecurityPermissionMBean() {
      return this.securityPermission;
   }

   public void setPreprocessors(PreprocessorMBean[] preps) {
      PreprocessorMBean[] old = this.preprocessors;
      this.preprocessors = preps;
      if (!comp(old, preps)) {
         this.firePropertyChange("preprocessors", old, preps);
      }

   }

   public PreprocessorMBean[] getPreprocessors() {
      return this.preprocessors;
   }

   public void addPreprocessorMBean(PreprocessorMBean x) {
      PreprocessorMBean[] prev = this.getPreprocessors();
      if (prev == null) {
         prev = new PreprocessorMBean[]{x};
         this.setPreprocessors(prev);
      } else {
         PreprocessorMBean[] curr = new PreprocessorMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setPreprocessors(curr);
      }
   }

   public void removePreprocessorMBean(PreprocessorMBean x) {
      PreprocessorMBean[] prev = this.getPreprocessors();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            PreprocessorMBean[] curr = new PreprocessorMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setPreprocessors(curr);
         }

      }
   }

   public void setPreprocessorMappings(PreprocessorMappingMBean[] x) {
      PreprocessorMappingMBean[] old = this.preprocessorMaps;
      this.preprocessorMaps = x;
      if (!comp(old, x)) {
         this.firePropertyChange("preprocessorMappings", old, x);
      }

   }

   public PreprocessorMappingMBean[] getPreprocessorMappings() {
      return this.preprocessorMaps;
   }

   public void addPreprocessorMappingMBean(PreprocessorMappingMBean x) {
      PreprocessorMappingMBean[] prev = this.getPreprocessorMappings();
      if (prev == null) {
         prev = new PreprocessorMappingMBean[]{x};
         this.setPreprocessorMappings(prev);
      } else {
         PreprocessorMappingMBean[] curr = new PreprocessorMappingMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setPreprocessorMappings(curr);
      }
   }

   public void removePreprocessorMappingMBean(PreprocessorMappingMBean x) {
      PreprocessorMappingMBean[] prev = this.getPreprocessorMappings();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            PreprocessorMappingMBean[] curr = new PreprocessorMappingMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setPreprocessorMappings(curr);
         }

      }
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
      boolean ok = true;
      int i;
      if (this.securityRoleAssignments != null) {
         for(i = 0; i < this.securityRoleAssignments.length; ++i) {
            ok &= this.check(this.securityRoleAssignments[i]);
         }
      }

      if (this.virtualDirectories != null) {
         for(i = 0; i < this.virtualDirectories.length; ++i) {
            ok &= this.check(this.virtualDirectories[i]);
         }
      }

      if (this.refDes != null) {
         ok &= this.check(this.refDes);
      }

      if (this.sessDes != null) {
         ok &= this.check(this.sessDes);
      }

      if (this.jspDes != null) {
         ok &= this.check(this.jspDes);
      }

      if (this.wlAuthFilter != null) {
         ok &= this.check(this.wlAuthFilter);
      }

      if (this.urlMatchMap != null) {
         ok &= this.check(this.urlMatchMap);
      }

      if (this.containerDescriptor != null) {
         ok &= this.check(this.containerDescriptor);
      }

      if (this.charsetParams != null) {
         ok &= this.check(this.charsetParams);
      }

      if (this.preprocessors != null) {
         for(i = 0; i < this.preprocessors.length; ++i) {
            ok &= this.check(this.preprocessors[i]);
         }
      }

      if (this.preprocessorMaps != null) {
         for(i = 0; i < this.preprocessorMaps.length; ++i) {
            ok &= this.check(this.preprocessorMaps[i]);
         }
      }

      if (!ok) {
         String[] err = this.getDescriptorErrors();
         throw new DescriptorValidationException(this.arrayToString(err));
      }
   }

   public String toXML(int indent) {
      String result = "";
      String weblogicWebAppDescriptorEncoding = this.getEncoding();
      if (weblogicWebAppDescriptorEncoding != null) {
         result = result + "<?xml version=\"1.0\" encoding=\"" + weblogicWebAppDescriptorEncoding + "\"?>\n";
      }

      result = result + this.indentStr(indent) + "<!DOCTYPE weblogic-web-app PUBLIC \"-//BEA Systems, Inc.//DTD Web Application 8.1//EN\" \"http://www.bea.com/servers/wls810/dtd/weblogic810-web-jar.dtd\">" + "\n";
      result = result + "\n" + this.indentStr(indent) + "<weblogic-web-app>\n";
      indent += 2;
      if (this.wlDescription != null) {
         result = result + "\n" + this.indentStr(indent) + "<description>" + this.wlDescription + "</description>\n";
      }

      if (this.wlVersion != null) {
         result = result + "\n" + this.indentStr(indent) + "<weblogic-version>" + this.wlVersion + "</weblogic-version>\n";
      }

      int i;
      if (this.securityRoleAssignments != null) {
         for(i = 0; i < this.securityRoleAssignments.length; ++i) {
            SecurityRoleAssignmentMBean sra = this.securityRoleAssignments[i];
            result = result + "\n" + sra.toXML(indent);
         }
      }

      if (this.runAsRoleAssignments != null) {
         for(i = 0; i < this.runAsRoleAssignments.length; ++i) {
            RunAsRoleAssignmentMBean ra = this.runAsRoleAssignments[i];
            String raxml = ra.toXML(indent);
            if (raxml != null && raxml.length() > 0) {
               result = result + '\n' + raxml;
            }
         }
      }

      if (this.refDes != null) {
         result = result + "\n" + this.refDes.toXML(indent);
      }

      if (this.sessDes != null) {
         result = result + "\n" + this.sessDes.toXML(indent);
      }

      if (this.jspDes != null) {
         result = result + "\n" + this.jspDes.toXML(indent);
      }

      if (this.wlAuthFilter != null) {
         result = result + "\n" + this.wlAuthFilter.toXML(indent);
      }

      if (this.containerDescriptor != null) {
         result = result + "\n" + this.containerDescriptor.toXML(indent);
      }

      if (this.charsetParams != null) {
         result = result + "\n" + this.charsetParams.toXML(indent);
      }

      if (this.virtualDirectories != null) {
         for(i = 0; i < this.virtualDirectories.length; ++i) {
            VirtualDirectoryMappingMBean vd = this.virtualDirectories[i];
            result = result + "\n" + vd.toXML(indent);
         }
      }

      if (this.urlMatchMap != null) {
         result = result + "\n" + this.urlMatchMap.toXML(indent);
      }

      if (this.preprocessors != null) {
         for(i = 0; i < this.preprocessors.length; ++i) {
            PreprocessorMBean pd = this.preprocessors[i];
            result = result + "\n" + pd.toXML(indent);
         }
      }

      if (this.preprocessorMaps != null) {
         for(i = 0; i < this.preprocessorMaps.length; ++i) {
            PreprocessorMappingMBean pmd = this.preprocessorMaps[i];
            result = result + "\n" + pmd.toXML(indent);
         }
      }

      if (this.securityPermission != null) {
         result = result + "\n" + this.securityPermission.toXML(indent);
      }

      if (this.contextRoot != null && this.contextRoot.trim().length() > 0) {
         result = result + "\n" + this.indentStr(indent) + "<context-root>" + this.contextRoot + "</context-root>";
      }

      if (this.dispatchPolicy != null && this.dispatchPolicy.trim().length() > 0) {
         result = result + "\n" + this.indentStr(indent) + "<wl-dispatch-policy>" + this.dispatchPolicy + "</wl-dispatch-policy>";
      }

      if (this.servletDescriptors != null) {
         for(i = 0; i < this.servletDescriptors.length; ++i) {
            result = result + "\n" + this.servletDescriptors[i].toXML(indent);
         }
      }

      ServletDescriptorMBean mbean;
      if (this.migrationServletDescriptors != null) {
         for(Iterator iter = this.migrationServletDescriptors.iterator(); iter.hasNext(); result = result + "\n" + mbean.toXML(indent)) {
            mbean = (ServletDescriptorMBean)iter.next();
         }
      }

      indent -= 2;
      result = result + "\n" + this.indentStr(indent) + "</weblogic-web-app>\n";
      return result;
   }
}
