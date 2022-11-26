package weblogic.servlet.jsp;

import java.util.Set;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.internal.ServletWorkContext;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;

public class JspCompilerContext implements JspCLLManager.IJSPCompilerContext {
   private String name;
   private GenericClassLoader classloader;
   private JspConfig config;
   private String[] sourcePaths;
   private WebAppBean webAppBean;
   private WeblogicWebAppBean wlWebAppBean;
   private boolean verbose;
   private ClassFinder resourceFinder;
   private String[] splitDirectoryJars;

   public JspCompilerContext(String name) {
      this.name = name;
      this.verbose = false;
   }

   public void setClassLoader(GenericClassLoader cl) {
      this.classloader = cl;
   }

   public ClassLoader getClassLoader() {
      return this.classloader;
   }

   public String getClasspath() {
      return this.classloader.getClassPath();
   }

   public void setJspConfig(JspConfig config) {
      this.config = config;
   }

   public JspConfig getJspConfig() {
      return this.config;
   }

   public void setSourcePaths(String[] spaths) {
      this.sourcePaths = spaths;
   }

   public String[] getSourcePaths() {
      return this.sourcePaths;
   }

   public void setWebAppBean(WebAppBean wb) {
      this.webAppBean = wb;
   }

   public WebAppBean getWebAppBean() {
      return this.webAppBean;
   }

   public void setWlWebAppBean(WeblogicWebAppBean wb) {
      this.wlWebAppBean = wb;
   }

   public WeblogicWebAppBean getWlWebAppBean() {
      return this.wlWebAppBean;
   }

   public String getName() {
      return this.name;
   }

   public ServletWorkContext getServletContext() {
      return null;
   }

   public String getContextPath() {
      return "";
   }

   public void say(String jspURI) {
      if (this.verbose) {
         System.out.println(this.name + " Compiling " + jspURI);
      }

   }

   public void sayError(String jspURI, String errorString) {
      System.err.println(this.name + " Error encountered while compiling '" + jspURI + "' \r\n" + errorString);
   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   boolean isVerbose() {
      return this.verbose;
   }

   public void setResourceFinder(ClassFinder finder) {
      this.resourceFinder = finder;
   }

   public Source getSource(String URI) {
      return this.resourceFinder.getSource(URI);
   }

   public Set getAdditionalExtensions() {
      return null;
   }

   public void setSplitDirectoryJars(String[] splitDirectoryJars) {
      this.splitDirectoryJars = splitDirectoryJars;
   }

   public String[] getSplitDirectoryJars() {
      return this.splitDirectoryJars;
   }
}
