package weblogic.servlet.jsp;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.CharsetMappingBean;
import weblogic.j2ee.descriptor.wl.CharsetParamsBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.JspDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.j2ee.validation.ModuleValidationInfo;
import weblogic.servlet.internal.CharsetMap;
import weblogic.servlet.internal.WebAppDescriptor;
import weblogic.servlet.internal.dd.compliance.ComplianceUtils;
import weblogic.servlet.internal.dd.compliance.DeploymentInfo;
import weblogic.servlet.utils.WarUtils;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.Getopt2;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public class JspcInvoker {
   private static final boolean debug = false;
   private static final String JSPC_TEMP_DIR = ".jspcgen_" + System.currentTimeMillis();
   private static final String tmpDirRoot = System.getProperty("java.io.tmpdir");
   final Getopt2 opts;
   private WebAppBean bean;
   private WeblogicWebAppBean wlBean;

   public JspcInvoker(Getopt2 opt) {
      this.opts = opt;
   }

   public void checkCompliance(GenericClassLoader classLoader, VirtualJarFile vjf, File altDD, File config, DeploymentPlanBean plan, ModuleValidationInfo mvi, boolean verbose) throws ErrorCollectionException {
      try {
         String uri = mvi == null ? this.getUriFromPlan(plan) : mvi.getURI();
         this.parseDescriptors(altDD, vjf, config, plan, uri);
      } catch (IOException var9) {
         throw new ErrorCollectionException(var9);
      } catch (XMLStreamException var10) {
         throw new ErrorCollectionException(var10);
      }

      this.checkCompliance(classLoader, mvi, verbose, vjf);
   }

   private String getUriFromPlan(DeploymentPlanBean plan) {
      if (plan == null) {
         return null;
      } else {
         ModuleOverrideBean[] movs = plan.getModuleOverrides();

         for(int i = 0; i < movs.length; ++i) {
            ModuleOverrideBean mov = movs[i];
            if (plan.rootModule(mov.getModuleName())) {
               return mov.getModuleName();
            }
         }

         return null;
      }
   }

   private void checkCompliance(GenericClassLoader classLoader, ModuleValidationInfo mvi, boolean verbose, VirtualJarFile vjf) throws ErrorCollectionException {
      DeploymentInfo info = new DeploymentInfo();
      info.setWebAppBean(this.bean);
      info.setWeblogicWebAppBean(this.wlBean);
      info.setClassLoader(classLoader);
      info.setModuleValidationInfo(mvi);
      info.setVerbose(verbose);
      info.setIsWebServiceModule(vjf.getEntry("WEB-INF/webservices.xml") != null);
      if (verbose) {
         p("Checking web app for compliance.");
      }

      ComplianceUtils.checkCompliance(info);
   }

   public void compile(VirtualJarFile virtualJarFile, WebAppBean bean, WeblogicWebAppBean wlBean, GenericClassLoader classLoader, ClassFinder resourceFinder, ModuleValidationInfo mvi, String[] splitDirectoryJars) throws ErrorCollectionException {
      ErrorCollectionException errorCollection = null;

      try {
         File docRoot = virtualJarFile.getDirectory();
         boolean verbose = this.opts.getBooleanOption("verbose");

         try {
            this.bean = bean;
            this.wlBean = wlBean;
            this.checkCompliance(classLoader, mvi, verbose, virtualJarFile);
         } catch (ErrorCollectionException var13) {
            errorCollection = var13;
         }

         if (errorCollection == null || errorCollection.isEmpty()) {
            String[] args = this.validateToolInputs(docRoot);
            IJspc jspc = new jspc20(args);
            jspc.setWebBean(bean);
            jspc.setWlWebBean(wlBean);
            jspc.setSplitDirectoryJars(splitDirectoryJars);
            jspc.runJspc(classLoader, resourceFinder, virtualJarFile);
         }
      } catch (Exception var14) {
         if (errorCollection == null) {
            errorCollection = new ErrorCollectionException();
         }

         errorCollection.add(var14);
         throw errorCollection;
      }

      if (errorCollection != null && !errorCollection.isEmpty()) {
         throw errorCollection;
      }
   }

   private void parseDescriptors(File altDD, VirtualJarFile vjf, File config, DeploymentPlanBean plan, String uri) throws IOException, XMLStreamException {
      try {
         WebAppDescriptor wad = WarUtils.getWebAppDescriptor(altDD, vjf, config, plan, uri);
         this.bean = wad.getWebAppBean();
         this.wlBean = wad.getWeblogicWebAppBean();
      } catch (FileNotFoundException var7) {
      }

   }

   private String[] validateToolInputs(File webappDir) {
      String targetDir = this.determineTargetDir(webappDir);
      List args = new ArrayList();
      if (this.opts.getBooleanOption("verbose")) {
         args.add("-verboseJspc");
      }

      if (this.opts.getBooleanOption("keepgenerated")) {
         args.add("-keepgenerated");
      }

      if (this.opts.getBooleanOption("forceGeneration")) {
         args.add("-forceGeneration");
      }

      if (this.opts.getBooleanOption("k")) {
         args.add("-k");
      }

      if (this.opts.getBooleanOption("useByteBuffer")) {
         args.add("-useByteBuffer");
      }

      args.add("-d");
      args.add(this.opts.getOption("d", targetDir));
      args.add("-noexit");
      if (this.opts.getBooleanOption("lineNumbers")) {
         args.add("-lineNumbers");
      }

      if (this.opts.hasOption("jsps")) {
         args.add("-jsps");
         args.add(this.opts.getOption("jsps"));
      }

      if (this.opts.getBooleanOption("compileAllTagFiles")) {
         args.add("-compileAllTagFiles");
      }

      if (this.opts.hasOption("compiler")) {
         args.add("-compiler");
         args.add(this.opts.getOption("compiler"));
      }

      if (this.opts.hasOption("source")) {
         args.add("-source");
         args.add(this.opts.getOption("source"));
      }

      if (this.opts.hasOption("target")) {
         args.add("-target");
         args.add(this.opts.getOption("target"));
      }

      if (this.opts.hasOption("maxfiles")) {
         args.add("-maxfiles");
         args.add(this.opts.getOption("maxfiles"));
      }

      return (String[])((String[])args.toArray(new String[0]));
   }

   private String determineTargetDir(File docRoot) {
      File target = new File(docRoot, "WEB-INF" + File.separatorChar + "classes");
      return target.getAbsolutePath();
   }

   public static File getJspcTempDir() {
      File f = new File(tmpDirRoot, JSPC_TEMP_DIR);
      f.mkdirs();
      return f;
   }

   public static String determineWebAppClasspath(VirtualJarFile webapp, Map jartable) {
      StringBuffer classpath = new StringBuffer();
      File[] webDirs = webapp.getRootFiles();

      for(int i = 0; i < webDirs.length; ++i) {
         buildWebAppClasspath(webDirs[i], classpath, jartable);
      }

      return classpath.length() > 0 ? classpath.toString() : null;
   }

   private static void addFileToClasspath(StringBuffer classpath, File file) {
      if (classpath.length() > 0) {
         classpath.append(File.pathSeparator);
      }

      classpath.append(file.getAbsolutePath());
   }

   private static void buildWebAppClasspath(File dir, StringBuffer classpath, Map jarTable) {
      File webinf = new File(dir, "WEB-INF");
      if (webinf.exists() && webinf.isDirectory()) {
         File classesDir = new File(webinf, "classes");
         if (classesDir.exists() && classesDir.isDirectory()) {
            addFileToClasspath(classpath, classesDir);
         }

         File libDir = new File(webinf, "lib");
         if (libDir.exists() && libDir.isDirectory()) {
            File[] jars = libDir.listFiles(new JarFileFilter());
            if (jars != null) {
               for(int i = 0; i < jars.length; ++i) {
                  addFileToClasspath(classpath, jars[i]);
                  jarTable.put(jars[i].getName(), jars[i].getAbsolutePath());
               }
            }
         }

      }
   }

   public static GenericClassLoader getJspClassLoader(ClassLoader parent, ClassFinder cf, String cname) {
      return new JspClassLoader(cf, parent, cname);
   }

   public static Map makeDescriptorMap(WeblogicWebAppBean wlBean, CharsetMap cMap) {
      if (wlBean == null) {
         return null;
      } else {
         Map args = makeDefaultDescriptorMap();
         JspDescriptorBean jspDesc = (JspDescriptorBean)DescriptorUtils.getFirstChildOrDefaultBean(wlBean, wlBean.getJspDescriptors(), "JspDescriptor");
         if (jspDesc != null) {
            setArg(args, "keepgenerated", "" + jspDesc.isKeepgenerated());
            setArg(args, "backwardCompatible", "" + jspDesc.isBackwardCompatible());
            setArg(args, "rtexprvalueJspParamName", "" + jspDesc.isRtexprvalueJspParamName());
            setArg(args, "superclass", jspDesc.getSuperClass());
            setArg(args, "packagePrefix", jspDesc.getPackagePrefix());
            setArg(args, "pageCheckSeconds", "" + jspDesc.getPageCheckSeconds());
            setArg(args, "encoding", jspDesc.getEncoding());
            setArg(args, "printNulls", "" + jspDesc.isPrintNulls());
            setArg(args, "debug", "" + jspDesc.isDebug());
            setArg(args, "compressHtmlTemplate", "" + jspDesc.isCompressHtmlTemplate());
            setArg(args, "optimizeJavaExpression", "" + jspDesc.isOptimizeJavaExpression());
            setArg(args, "strictStaleCheck", "" + jspDesc.isStrictStaleCheck());
            setArg(args, "strictJspDocumentValidation", "" + jspDesc.isStrictJspDocumentValidation());
            if (jspDesc.getCompilerSourceVM() != null && jspDesc.getCompilerSourceVM().length() > 0) {
               setArg(args, "source", jspDesc.getCompilerSourceVM());
            }

            if (jspDesc.getCompilerTargetVM() != null && jspDesc.getCompilerTargetVM().length() > 0) {
               setArg(args, "target", jspDesc.getCompilerTargetVM());
            }
         }

         if (cMap != null) {
            CharsetParamsBean cpBeans = (CharsetParamsBean)DescriptorUtils.getFirstChildOrDefaultBean(wlBean, wlBean.getCharsetParams(), "CharsetParams");
            if (cpBeans != null) {
               CharsetMappingBean[] maps = cpBeans.getCharsetMappings();

               for(int i = 0; i < maps.length; ++i) {
                  cMap.addMapping(maps[i].getIanaCharsetName(), maps[i].getJavaCharsetName());
               }
            }
         }

         return args;
      }
   }

   public static Map makeDefaultDescriptorMap() {
      Map args = new HashMap();
      setArg(args, "defaultFileName", "index.jsp");
      setArg(args, "verbose", "true");
      setArg(args, "keepgenerated", "false");
      setArg(args, "precompileContinue", "false");
      setArg(args, "pageCheckSeconds", "1");
      setArg(args, "packagePrefix", "jsp_servlet");
      setArg(args, "superclass", "weblogic.servlet.jsp.JspBase");
      setArg(args, "exactMapping", "true");
      setArg(args, "backwardCompatible", "false");
      setArg(args, "printNulls", "true");
      setArg(args, "compressHtmlTemplate", "false");
      setArg(args, "optimizeJavaExpression", "false");
      setArg(args, "strictStaleCheck", "true");
      setArg(args, "strictJspDocumentValidation", "false");
      return args;
   }

   static void setArg(Map map, String key, String value) {
      if (value != null) {
         map.put(key, value);
      }

   }

   static void say(String s) {
      System.out.println("[jspc] " + s);
   }

   static void p(String out) {
      System.out.println("[JspcInvoker]" + out);
   }

   static void printAll(Getopt2 opts) {
      p("-d :" + opts.getOption("d"));
      p("-lineNumbers :" + opts.getOption("lineNumbers"));
      p("-k :" + opts.getOption("k"));
      p("-verbose :" + opts.getOption("verbose"));
      p("-keepgenerated :" + opts.getOption("keepgenerated"));
      p("-compiler :" + opts.getOption("compiler"));
      p("-forceGeneration :" + opts.getOption("forceGeneration"));
   }

   public static boolean canFindJavelinClasses() {
      try {
         URL u = (new Object()).getClass().getResource("/weblogic/servlet/jsp/.build.txt");
         if (u == null) {
            return true;
         } else {
            Class.forName("weblogic.jsp.wlw.filesystem.IFileFilter");
            return true;
         }
      } catch (Exception var1) {
         return false;
      }
   }

   public interface IJspc {
      void setWebBean(WebAppBean var1);

      void setWlWebBean(WeblogicWebAppBean var1);

      void setSplitDirectoryJars(String[] var1);

      void runJspc(GenericClassLoader var1, ClassFinder var2, VirtualJarFile var3) throws Exception;
   }

   private static class JarFileFilter implements FileFilter {
      private JarFileFilter() {
      }

      public boolean accept(File f) {
         return !f.isDirectory() && (f.getName().endsWith(".jar") || f.getName().endsWith(".JAR"));
      }

      // $FF: synthetic method
      JarFileFilter(Object x0) {
         this();
      }
   }
}
