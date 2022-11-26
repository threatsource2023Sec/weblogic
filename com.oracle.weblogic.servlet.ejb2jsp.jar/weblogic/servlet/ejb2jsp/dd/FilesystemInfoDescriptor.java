package weblogic.servlet.ejb2jsp.dd;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.servlet.ejb2jsp.Utils;
import weblogic.servlet.internal.dd.ToXML;
import weblogic.utils.io.XMLWriter;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public class FilesystemInfoDescriptor implements ToXML {
   private String javacPath;
   private String javacFlags;
   private String pkgname;
   private String ejbJarFile;
   private String saveAs;
   private String saveJarTmpdir;
   private String saveJarFile;
   private String saveDirClassDir;
   private String saveDirTldFile;
   private String[] compileClasspath;
   private String[] sourcePath;
   private boolean keepgenerated;
   private boolean compile = true;

   public FilesystemInfoDescriptor() {
      this.javacPath = this.javacFlags = this.pkgname = this.ejbJarFile = this.saveAs = this.saveJarTmpdir = this.saveJarFile = this.saveDirClassDir = this.saveDirTldFile = "";
      this.sourcePath = new String[0];
      this.compileClasspath = new String[0];
   }

   public FilesystemInfoDescriptor(Element e) throws DOMProcessingException {
      Element sub = null;
      this.javacPath = DOMUtils.getValueByTagName(e, "javac-path");
      this.javacFlags = DOMUtils.getValueByTagName(e, "javac-flags");
      this.compileClasspath = this.getPathElements(e, "compile-classpath");
      String s = DOMUtils.getValueByTagName(e, "keepgenerated");
      this.keepgenerated = "true".equalsIgnoreCase(s);
      this.sourcePath = this.getPathElements(e, "source-path");
      this.pkgname = DOMUtils.getValueByTagName(e, "package-name");
      this.ejbJarFile = DOMUtils.getValueByTagName(e, "ejb-jar-file");
      this.saveAs = DOMUtils.getValueByTagName(e, "save-as");
      sub = DOMUtils.getElementByTagName(e, "save-taglib-jar");
      this.saveJarTmpdir = DOMUtils.getValueByTagName(sub, "tmpdir");
      this.saveJarFile = DOMUtils.getValueByTagName(sub, "taglib-jar-file");
      sub = DOMUtils.getElementByTagName(e, "save-taglib-directory");
      this.saveDirClassDir = DOMUtils.getValueByTagName(sub, "classes-directory");
      this.saveDirTldFile = DOMUtils.getValueByTagName(sub, "tld-file");
   }

   private String[] getPathElements(Element parent, String tagName) throws DOMProcessingException {
      Element sub = DOMUtils.getElementByTagName(parent, tagName);
      List l = DOMUtils.getOptionalElementsByTagName(sub, "path-element");
      l = DOMUtils.getTextDataValues(l);
      Iterator I = l.iterator();
      List path = new ArrayList();

      while(I.hasNext()) {
         path.add(I.next());
      }

      String[] ret = new String[path.size()];
      path.toArray(ret);
      return ret;
   }

   public String toString() {
      return "Project Build Options";
   }

   private static String trim(String s) {
      return s == null ? "" : s.trim();
   }

   private static boolean isNull(String s) {
      return s == null || s.length() == 0;
   }

   public String[] getErrors() {
      this.javacPath = trim(this.javacPath);
      this.javacFlags = trim(this.javacFlags);
      this.pkgname = trim(this.pkgname);
      this.saveJarTmpdir = trim(this.saveJarTmpdir);
      this.saveJarFile = trim(this.saveJarFile);
      this.saveDirClassDir = trim(this.saveDirClassDir);
      this.saveDirTldFile = trim(this.saveDirTldFile);
      List errs = new ArrayList();
      if (isNull(this.javacPath)) {
         errs.add("A java compiler (like \"javac\") must be specified, e.g., \"my.ejb.jsptags\"");
      }

      if (isNull(this.pkgname)) {
         errs.add("A java package for the generated code must be specified.");
      }

      if (this.saveAsDirectory()) {
         if (isNull(this.saveDirTldFile)) {
            errs.add("A file location for the TLD file must be specified, e.g., WEB-INF/myejb.tld");
         }

         if (isNull(this.saveDirClassDir)) {
            errs.add("A directory must be specified where the tag classes will be compiled, e.g., WEB-INF/classes");
         }
      } else {
         if (isNull(this.saveJarFile)) {
            errs.add("The target taglib jar file must be specified, e.g., WEB-INF/lib/myejb-tags.jar");
         }

         if (isNull(this.saveJarTmpdir)) {
            errs.add("A temporary compilation directory must be specified before packaging the jar file, e.g., C:\\TEMP or /tmp");
         }
      }

      String[] ret = new String[errs.size()];
      errs.toArray(ret);
      return ret;
   }

   public String[] getCompileCommand() {
      List a = new ArrayList();
      if (this.getKeepgenerated()) {
         a.add("-keepgenerated");
      }

      a.add("-d");
      if (this.saveAsDirectory()) {
         a.add(this.getSaveDirClassDir());
      } else {
         a.add(this.getSaveJarTmpdir());
      }

      String[] ret = new String[a.size()];
      a.toArray(ret);
      return ret;
   }

   public String getPackage() {
      return this.pkgname;
   }

   public void setPackage(String s) {
      this.pkgname = s;
      if (this.pkgname.endsWith(".")) {
         this.pkgname = this.pkgname.substring(0, this.pkgname.length() - 2);
      }

   }

   public String getJavacPath() {
      return this.javacPath;
   }

   public void setJavacPath(String s) {
      this.javacPath = s;
   }

   public String getJavacFlags() {
      return this.javacFlags;
   }

   public void setJavacFlags(String s) {
      this.javacFlags = s;
   }

   public String[] getCompileClasspath() {
      if (this.compileClasspath == null) {
         this.compileClasspath = new String[0];
      }

      return (String[])((String[])this.compileClasspath.clone());
   }

   public String[] getBuiltinClasspath() {
      return Utils.splitPath(System.getProperty("java.class.path") + File.pathSeparator + this.getEJBJarFile());
   }

   public void setCompileClasspath(String[] s) {
      if (s == null) {
         this.compileClasspath = new String[0];
      } else {
         this.compileClasspath = (String[])((String[])s.clone());
      }

   }

   public boolean getCompile() {
      return this.compile;
   }

   public void setCompile(boolean b) {
      this.compile = b;
   }

   public boolean getKeepgenerated() {
      return this.keepgenerated;
   }

   public void setKeepgenerated(boolean b) {
      this.keepgenerated = b;
   }

   public String[] getSourcePath() {
      if (this.sourcePath == null) {
         this.sourcePath = new String[0];
      }

      return (String[])((String[])this.sourcePath.clone());
   }

   public void setSourcePath(String[] s) {
      this.sourcePath = (String[])((String[])s.clone());
   }

   public String getEJBJarFile() {
      return this.ejbJarFile;
   }

   public void setEJBJarFile(String s) {
      this.ejbJarFile = s;
   }

   public String getSaveAs() {
      return this.saveAs;
   }

   public void setSaveAs(String s) {
      this.saveAs = s;
   }

   public boolean saveAsDirectory() {
      return !"JAR".equals(this.saveAs);
   }

   public String getSaveJarTmpdir() {
      return this.saveJarTmpdir;
   }

   public void setSaveJarTmpdir(String s) {
      this.saveJarTmpdir = s;
   }

   public String getSaveJarFile() {
      return this.saveJarFile;
   }

   public void setSaveJarFile(String s) {
      this.saveJarFile = s;
   }

   public String getSaveDirClassDir() {
      return this.saveDirClassDir;
   }

   public void setSaveDirClassDir(String s) {
      this.saveDirClassDir = s;
   }

   public String getSaveDirTldFile() {
      return this.saveDirTldFile;
   }

   public void setSaveDirTldFile(String s) {
      this.saveDirTldFile = s;
   }

   public void toXML(XMLWriter x) {
      x.println("<filesystem-info>");
      x.incrIndent();
      x.println("<javac-path>" + this.javacPath + "</javac-path>");
      x.println("<javac-flags>" + this.javacFlags + "</javac-flags>");
      x.println("<compile-classpath>");
      String[] s = this.getCompileClasspath();
      x.incrIndent();

      int i;
      for(i = 0; i < s.length; ++i) {
         x.println("<path-element>" + s[i] + "</path-element>");
      }

      x.decrIndent();
      x.println("</compile-classpath>");
      x.println("<keepgenerated>" + this.keepgenerated + "</keepgenerated>");
      x.println("<source-path>");
      s = this.getSourcePath();
      x.incrIndent();

      for(i = 0; i < s.length; ++i) {
         x.println("<path-element>" + s[i] + "</path-element>");
      }

      x.decrIndent();
      x.println("</source-path>");
      x.println("<package-name>" + this.pkgname + "</package-name>");
      x.println("<ejb-jar-file>" + this.ejbJarFile + "</ejb-jar-file>");
      x.println("<save-as>" + this.saveAs + "</save-as>");
      x.println("<save-taglib-jar>");
      x.incrIndent();
      x.println("<tmpdir>" + this.saveJarTmpdir + "</tmpdir>");
      x.println("<taglib-jar-file>" + this.saveJarFile + "</taglib-jar-file>");
      x.decrIndent();
      x.println("</save-taglib-jar>");
      x.println("<save-taglib-directory>");
      x.incrIndent();
      x.println("<classes-directory>" + this.saveDirClassDir + "</classes-directory>");
      x.println("<tld-file>" + this.saveDirTldFile + "</tld-file>");
      x.decrIndent();
      x.println("</save-taglib-directory>");
      x.decrIndent();
      x.println("</filesystem-info>");
   }
}
