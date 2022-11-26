package weblogic.servlet.internal.dd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.ParameterMBean;
import weblogic.management.descriptors.webapp.RunAsMBean;
import weblogic.management.descriptors.webapp.SecurityRoleRefMBean;
import weblogic.management.descriptors.webapp.ServletMBean;
import weblogic.management.descriptors.webapp.UIMBean;
import weblogic.management.descriptors.webappext.ServletDescriptorMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class ServletDescriptor extends BaseServletDescriptor implements ToXML, ServletMBean {
   private static final String SERVLET_NAME = "servlet-name";
   private static final String SERVLET_CLASS = "servlet-class";
   private static final String JSP_FILE = "jsp-file";
   private static final String INIT_PARAM = "init-param";
   private static final String LOAD_ON_STARTUP = "load-on-startup";
   private static final String SECURITY_ROLE_REF = "security-role-ref";
   private static final String RUN_AS = "run-as";
   private String servletName;
   private UIMBean uiData = new UIDescriptor();
   private String servletClass;
   private String jspFile;
   private List initParams;
   private int loadSeq = -1;
   private List secRoleRefs;
   private RunAsMBean runAs;
   private String initAs;
   private String destroyAs;
   private ServletDescriptorMBean servletDescriptor = null;

   public ServletDescriptor() {
   }

   public ServletDescriptor(ServletMBean mbean) {
      this.setServletName(mbean.getServletName());
      this.setServletClass(mbean.getServletClass());
      this.setUIData(mbean.getUIData());
      this.setJspFile(mbean.getJspFile());
      this.setInitParams(mbean.getInitParams());
      this.setLoadOnStartup(mbean.getLoadOnStartup());
      this.setSecurityRoleRefs(mbean.getSecurityRoleRefs());
      this.setRunAs(mbean.getRunAs());
   }

   public ServletDescriptor(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      this.servletName = DOMUtils.getValueByTagName(parentElement, "servlet-name");
      this.servletClass = DOMUtils.getOptionalValueByTagName(parentElement, "servlet-class");
      if (this.servletClass == null) {
         this.jspFile = DOMUtils.getOptionalValueByTagName(parentElement, "jsp-file");
         if (this.jspFile == null) {
            throw new DOMProcessingException("Servlet node does not contain niether servlet-class nor jsp-file nodes");
         }
      }

      this.uiData = new UIDescriptor(parentElement);
      String loadSeqS = DOMUtils.getOptionalValueByTagName(parentElement, "load-on-startup");
      if (loadSeqS != null) {
         try {
            this.loadSeq = Integer.parseInt(loadSeqS);
         } catch (NumberFormatException var7) {
            this.loadSeq = -1;
         }
      }

      List elts = DOMUtils.getOptionalElementsByTagName(parentElement, "init-param");
      Iterator i = elts.iterator();
      this.initParams = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.initParams.add(new ParameterDescriptor((Element)i.next()));
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "security-role-ref");
      i = elts.iterator();
      this.secRoleRefs = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.secRoleRefs.add(new SecurityRoleRefDescriptor(wad, (Element)i.next()));
      }

      Element elt = DOMUtils.getOptionalElementByTagName(parentElement, "run-as");
      if (elt != null) {
         this.runAs = new RunAsDescriptor(elt);
      }

   }

   public String toString() {
      return this.getServletName();
   }

   public String getServletName() {
      return this.servletName != null ? this.servletName : "";
   }

   public void setServletName(String name) {
      String old = this.servletName;
      this.servletName = name;
      this.checkChange("servletName", old, name);
   }

   public UIMBean getUIData() {
      return this.uiData;
   }

   public void setUIData(UIMBean uid) {
      if (uid != null) {
         this.uiData = uid;
      } else {
         this.uiData = new UIDescriptor();
      }

   }

   public String getServlet() {
      return this.servletClass != null ? this.servletClass : this.jspFile;
   }

   public void setServlet(String s) {
      if (s.endsWith(".jsp")) {
         this.setJSPFile(s);
         this.servletClass = null;
      } else {
         this.setServletClass(s);
         this.jspFile = null;
      }

   }

   public void setServletCode(String s) {
      this.setServlet(s);
   }

   public String getServletCode() {
      return this.getServlet();
   }

   public String getServletClass() {
      return this.servletClass;
   }

   public void setServletClass(String cl) {
      String old = this.servletClass;
      this.servletClass = cl;
      if (this.servletClass != null && (this.servletClass = this.servletClass.trim()).length() > 0) {
         this.jspFile = null;
      }

      this.checkChange("servletClass", old, cl);
   }

   public String getJSPFile() {
      return this.jspFile;
   }

   public void setJSPFile(String f) {
      String old = this.jspFile;
      this.jspFile = f;
      if (this.jspFile != null && (this.jspFile = this.jspFile.trim()).length() > 0) {
         this.servletClass = null;
      }

      this.checkChange("jspFile", old, f);
   }

   public ParameterMBean[] getInitParams() {
      if (this.initParams == null) {
         return new ParameterDescriptor[0];
      } else {
         ParameterDescriptor[] ret = new ParameterDescriptor[this.initParams.size()];
         this.initParams.toArray(ret);
         return ret;
      }
   }

   public void setInitParams(ParameterMBean[] x) {
      ParameterMBean[] oldparams = this.getInitParams();
      this.initParams = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.initParams.add(x[i]);
         }

         this.checkChange("initParams", oldparams, x);
      }
   }

   public void addInitParam(ParameterMBean param) {
      if (this.initParams == null) {
         this.initParams = new ArrayList();
      }

      this.initParams.add(param);
   }

   public void removeInitParam(ParameterMBean param) {
      if (this.initParams != null) {
         this.initParams.remove(param);
      }

   }

   public int getLoadSequence() {
      return this.loadSeq;
   }

   public void setLoadSequence(int l) {
      int old = this.loadSeq;
      this.loadSeq = l;
      this.checkChange("loadSequence", old, l);
   }

   public SecurityRoleRefMBean[] getSecurityRoleRefs() {
      if (this.secRoleRefs == null) {
         return new SecurityRoleRefDescriptor[0];
      } else {
         SecurityRoleRefDescriptor[] ret = new SecurityRoleRefDescriptor[this.secRoleRefs.size()];
         this.secRoleRefs.toArray(ret);
         return (SecurityRoleRefMBean[])ret;
      }
   }

   public void setSecurityRoleRefs(SecurityRoleRefMBean[] x) {
      SecurityRoleRefMBean[] oldrefs = this.getSecurityRoleRefs();
      this.secRoleRefs = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.secRoleRefs.add(x[i]);
         }

         this.checkChange("securityRoleRefs", oldrefs, x);
      }
   }

   public void addSecurityRoleRef(SecurityRoleRefMBean x) {
      if (this.secRoleRefs == null) {
         this.secRoleRefs = new ArrayList();
      }

      this.secRoleRefs.add(x);
   }

   public void removeSecurityRoleRef(SecurityRoleRefMBean x) {
      if (this.secRoleRefs != null) {
         this.secRoleRefs.remove(x);
      }
   }

   public String getSmallIconFileName() {
      return this.uiData.getSmallIconFileName();
   }

   public void setSmallIconFileName(String icon) {
      String old = this.getSmallIconFileName();
      this.uiData.setSmallIconFileName(icon);
      this.checkChange("smallIconFileName", old, icon);
   }

   public String getLargeIconFileName() {
      return this.uiData.getLargeIconFileName();
   }

   public void setLargeIconFileName(String icon) {
      String old = this.getLargeIconFileName();
      this.uiData.setLargeIconFileName(icon);
      this.checkChange("largeIconFileName", old, icon);
   }

   public String getDisplayName() {
      return this.uiData.getDisplayName();
   }

   public void setDisplayName(String icon) {
      String old = this.getDisplayName();
      this.uiData.setDisplayName(icon);
      this.checkChange("displayName", old, icon);
   }

   public String getJspFile() {
      return this.jspFile;
   }

   public void setJspFile(String fileName) {
      String old = this.jspFile;
      this.jspFile = fileName;
      this.checkChange("jspFile", old, this.jspFile);
   }

   public String getDescription() {
      return this.uiData.getDescription();
   }

   public void setDescription(String d) {
      String old = this.getDescription();
      this.uiData.setDescription(d);
      this.checkChange("description", old, d);
   }

   public int getLoadOnStartup() {
      return this.loadSeq;
   }

   public void setLoadOnStartup(int loadOnStartup) {
      int old = this.loadSeq;
      this.loadSeq = loadOnStartup;
      this.checkChange("loadOnStartup", old, loadOnStartup);
   }

   public void setRunAs(RunAsMBean ra) {
      RunAsMBean old = this.runAs;
      this.runAs = ra;
      this.checkChange("runAs", old, ra);
   }

   public RunAsMBean getRunAs() {
      return this.runAs;
   }

   public void setInitAs(String principal) {
      String old = this.initAs;
      this.initAs = principal;
      this.checkChange("initAs", old, principal);
   }

   public String getInitAs() {
      return this.initAs;
   }

   public void setServletDescriptor(ServletDescriptorMBean descriptor) {
      this.servletDescriptor = descriptor;
   }

   public ServletDescriptorMBean getServletDescriptor() {
      return this.servletDescriptor;
   }

   public void setDestroyAs(String principal) {
      String old = this.destroyAs;
      this.destroyAs = principal;
      this.checkChange("destroyAs", old, principal);
   }

   public String getDestroyAs() {
      return this.destroyAs;
   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      String s = this.getServletName();
      if (s != null && (s = s.trim()).length() != 0) {
         this.setServletName(s);
      } else {
         this.addDescriptorError("NO_SERVLET_NAME");
         ok = false;
      }

      s = this.getServletClass();
      if (s != null) {
         s = s.trim();
         this.setServletClass(s);
      }

      s = this.getJSPFile();
      if (s != null) {
         s = s.trim();
         this.setJSPFile(s);
      }

      String clazz = this.getServletClass();
      String jsp = this.getJSPFile();
      if (clazz != null && clazz.length() > 0 && jsp != null && jsp.length() > 0) {
         this.addDescriptorError("MULTIPLE_DEFINES_SERVLET_DEF", this.getServletName());
         ok = false;
      }

      if ((clazz == null || clazz.length() == 0) && (jsp == null || jsp.length() == 0)) {
         this.addDescriptorError("NO_SERVLET_DEF", this.getServletName());
         ok = false;
      }

      if (this.getRunAs() != null) {
         this.setRunAs(this.getRunAs());
      }

      if (!ok) {
         throw new DescriptorValidationException();
      }
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<servlet>\n";
      indent += 2;
      String s;
      if (this.uiData != null) {
         s = this.getSmallIconFileName();
         String largeIcon = this.getLargeIconFileName();
         if (s != null || largeIcon != null) {
            result = result + this.indentStr(indent) + "<icon>\n";
            if (s != null) {
               result = result + this.indentStr(indent + 2) + "<small-icon>" + s + "</small-icon>\n";
            }

            if (largeIcon != null) {
               result = result + this.indentStr(indent + 2) + "<large-icon>" + largeIcon + "</large-icon>\n";
            }

            result = result + this.indentStr(indent) + "</icon>\n";
         }
      }

      result = result + this.indentStr(indent) + "<servlet-name>" + this.servletName + "</servlet-name>\n";
      if (this.uiData != null) {
         s = this.getDisplayName();
         if (s != null && (s = s.trim()).length() > 0) {
            result = result + this.indentStr(indent) + "<display-name>" + cdata(s) + "</display-name>\n";
         }

         s = this.getDescription();
         if (s != null && (s = s.trim()).length() > 0) {
            result = result + this.indentStr(indent) + "<description>" + cdata(s) + "</description>\n";
         }
      }

      if (this.servletClass != null) {
         result = result + this.indentStr(indent) + "<servlet-class>" + this.servletClass + "</servlet-class>\n";
      } else {
         result = result + this.indentStr(indent) + "<jsp-file>" + this.jspFile + "</jsp-file>\n";
      }

      Iterator i;
      if (this.initParams != null) {
         for(i = this.initParams.iterator(); i.hasNext(); result = result + this.indentStr(indent) + "</init-param>\n") {
            ParameterDescriptor pd = (ParameterDescriptor)i.next();
            result = result + this.indentStr(indent) + "<init-param>\n";
            indent += 2;
            result = result + this.indentStr(indent) + "<param-name>" + pd.getParamName() + "</param-name>\n";
            result = result + this.indentStr(indent) + "<param-value>" + pd.getParamValue() + "</param-value>\n";
            String d = pd.getDescription();
            if (d != null) {
               result = result + this.indentStr(indent) + "<description>" + d + "</description>\n";
            }

            indent -= 2;
         }
      }

      if (this.runAs != null) {
         result = result + this.runAs.toXML(indent);
      }

      if (this.loadSeq != -1) {
         result = result + this.indentStr(indent) + "<load-on-startup>" + this.loadSeq + "</load-on-startup>\n";
      }

      SecurityRoleRefDescriptor srr;
      if (this.secRoleRefs != null) {
         for(i = this.secRoleRefs.iterator(); i.hasNext(); result = result + srr.toXML(indent)) {
            srr = (SecurityRoleRefDescriptor)i.next();
         }
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</servlet>\n";
      return result;
   }
}
