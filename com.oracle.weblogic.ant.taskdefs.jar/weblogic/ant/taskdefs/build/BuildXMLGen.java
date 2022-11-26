package weblogic.ant.taskdefs.build;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryInitializer;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.library.LibraryReference;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryUtils;
import weblogic.servlet.utils.WebAppLibraryUtils;
import weblogic.utils.BadOptionException;
import weblogic.utils.Debug;
import weblogic.utils.Getopt2;
import weblogic.utils.StringUtils;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;

public final class BuildXMLGen extends Tool {
   public BuildXMLGen(String[] args) {
      super(args);
      this.setRequireExtraArgs(true);
   }

   public void prepare() {
      this.opts.addOption("projectName", "project name", "name of the ANT project.");
      this.opts.addOption("d", "directory", "directory where build.xml is created.  Default is the current directory.");
      this.opts.addOption("projectName", "project name", "name of the ANT project.");
      this.opts.addOption("file", "build.xml", "name of the generated build file.");
      this.opts.addOption("username", "username", "user name for deploy commands.");
      this.opts.addOption("password", "password", "password for the user.");
      this.opts.addOption("adminurl", "url", "Administatrion Server URL.");
      this.opts.addOption("librarydir", "directories", "Comma-separated list of directories in which to look for libraries.");
      this.opts.addFlag("verbose", "Enable Verbose output.");
      this.opts.setUsageArgs("<source directory>");
   }

   public boolean isVerbose() {
      return "true".equalsIgnoreCase(this.opts.getOption("verbose", "false"));
   }

   public String getUserName() {
      return this.opts.getOption("username");
   }

   public String getPassword() {
      return this.opts.getOption("password");
   }

   public String getBuildFileName() {
      return this.opts.getOption("file", "build.xml");
   }

   private String[] getLibraryDirs() {
      String s = this.opts.getOption("librarydir");
      return s == null ? new String[0] : StringUtils.splitCompletely(s, ",");
   }

   public void runBody() throws ToolFailureException {
      String[] args = this.opts.args();
      File srcDir = new File(args[0]);
      if (!srcDir.exists()) {
         throw new ToolFailureException("Source directory: " + srcDir.getAbsolutePath() + " does not exist");
      } else if (!srcDir.isDirectory()) {
         throw new ToolFailureException("Source directory: " + srcDir.getAbsolutePath() + " is not a directory");
      } else {
         try {
            if (this.opts.getOption("d") == null) {
               this.opts.setOption("d", (new File(".")).getAbsolutePath());
            }
         } catch (BadOptionException var12) {
         }

         LibraryInitializer libraryInitializer = null;

         try {
            String projectName = this.opts.getOption("projectName", srcDir.getCanonicalFile().getName());
            String adminurl = this.opts.getOption("adminurl", "iiop://localhost:7001");
            BuildXMLGenerator gen = new BuildXMLGenerator(this.opts, projectName, srcDir, this.getUserName(), this.getPassword(), this.getBuildFileName(), adminurl);
            String dirName = gen.getRootDirectoryName();
            if (dirName == null) {
               dirName = ".";
            }

            this.errorIfBuildFileExists(dirName);
            libraryInitializer = this.getLibraryInitializer();
            gen.setLibraryManager(this.initLibraryManager(srcDir));
            gen.generate(new Object());
            System.out.println("Generated " + dirName + File.separatorChar + this.getBuildFileName());
         } catch (Exception var13) {
            throw new ToolFailureException("Code Generation Error", var13);
         } finally {
            if (libraryInitializer != null) {
               libraryInitializer.cleanup();
            }

         }

      }
   }

   private LibraryManager initLibraryManager(File srcDir) throws ToolFailureException {
      LibraryManager mgr = new LibraryManager(LibraryUtils.initAppReferencer(), "DOMAIN");
      LibraryReference[] appLibRefs = this.initAppLibRefs(srcDir);
      mgr.lookup(appLibRefs);

      LibraryReference[] optPacks;
      try {
         optPacks = WebAppLibraryUtils.initAllWebLibRefs(srcDir);
         mgr.lookup(optPacks);
      } catch (ToolFailureException var6) {
         if (this.isVerbose()) {
            var6.printStackTrace();
         }

         System.out.println("Problem processing weblogic.xml, ignoring any library references it may contain.");
      }

      try {
         optPacks = LibraryUtils.initAllOptPacks(srcDir);
         mgr.lookup(optPacks);
      } catch (LibraryProcessingException var5) {
         if (this.isVerbose()) {
            var5.printStackTrace();
         }

         System.out.println("Problem processing META-INF/MANIFEST.MF, ignoring any optional packages it may contain.");
      }

      return mgr;
   }

   private void errorIfBuildFileExists(String dirName) throws ToolFailureException {
      File existingBuildFile = new File(dirName, this.getBuildFileName());
      if (existingBuildFile.exists()) {
         throw new ToolFailureException("Build File already exists, use -file to specify alternative build file name");
      }
   }

   private LibraryInitializer getLibraryInitializer() {
      LibraryInitializer rtn = new LibraryInitializer();
      if (this.isVerbose()) {
         rtn.setVerbose();
      } else {
         rtn.setSilent();
      }

      String[] dirs = this.getLibraryDirs();
      File libraryDir = null;

      for(int i = 0; i < dirs.length; ++i) {
         libraryDir = new File(dirs[i]);

         try {
            rtn.registerLibdir(libraryDir.getPath());
         } catch (LoggableLibraryProcessingException var6) {
            var6.getLoggable().log();
         }
      }

      return rtn;
   }

   private LibraryReference[] initAppLibRefs(File srcDir) {
      try {
         return LibraryUtils.initLibRefs(srcDir);
      } catch (LibraryProcessingException var3) {
         if (this.isVerbose()) {
            var3.printStackTrace();
         }

         System.out.println("Problem processing weblogic-application.xml, ignoring any library references it may contain.");
         return null;
      }
   }

   public static void main(String[] argv) throws Exception {
      (new BuildXMLGen(argv)).run();
   }

   public static class BuildXMLGenerator extends CodeGenerator {
      private final String projectName;
      private final File srcDir;
      private final File[] componentDirs;
      private int componentIndex = 0;
      private final String userName;
      private final String password;
      private final String buildFileName;
      private final String adminurl;
      private final Set skipDirs = new HashSet();
      private Library[] libraries = new Library[0];
      private LibraryReference[] unresolvedRefs = new LibraryReference[0];
      private int libraryIndex = 0;
      private int unresolvedRefIndex = 0;
      private Collection allLibraryTargets = new LinkedHashSet();
      private boolean resolvedRef = true;

      public BuildXMLGenerator(Getopt2 opts, String projectName, File srcDir, String userName, String password, String buildFileName, String adminurl) {
         super(opts);
         this.projectName = projectName;
         this.srcDir = srcDir;
         this.userName = userName;
         this.password = password;
         this.buildFileName = buildFileName;
         this.adminurl = adminurl;
         this.componentDirs = srcDir.listFiles(new FileFilter() {
            public boolean accept(File path) {
               return path.isDirectory();
            }
         });
         this.skipDirs.add("META-INF");
         this.skipDirs.add("APP-INF");
      }

      private void setLibraryManager(LibraryManager mgr) {
         Set tmpSet = new HashSet();
         tmpSet.addAll(Arrays.asList(mgr.getReferencedLibraries()));
         this.libraries = (Library[])((Library[])tmpSet.toArray(new Library[tmpSet.size()]));
         this.unresolvedRefs = mgr.getUnresolvedReferences();
      }

      public String srcdir() {
         return this.srcDir.getPath();
      }

      public String adminurl() {
         return this.adminurl;
      }

      public String project_name() {
         return this.projectName;
      }

      public String generator() {
         return "weblogic.BuildXMLGen";
      }

      public String user() {
         return this.userName;
      }

      public String password() {
         return this.password;
      }

      public String build_components() throws CodeGenerationException {
         if (this.componentDirs != null && this.componentDirs.length != 0) {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < this.componentDirs.length; ++i) {
               this.componentIndex = i;
               if (!this.skipDirs.contains(this.component_name())) {
                  sb.append(this.parse(this.getProductionRule("build_component")));
               }
            }

            return sb.toString();
         } else {
            return "";
         }
      }

      public String redeploy_components() throws CodeGenerationException {
         if (this.componentDirs != null && this.componentDirs.length != 0) {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < this.componentDirs.length; ++i) {
               this.componentIndex = i;
               if (!this.skipDirs.contains(this.component_name())) {
                  sb.append(this.parse(this.getProductionRule("redeploy_component")));
               }
            }

            return sb.toString();
         } else {
            return "";
         }
      }

      public String component_name() {
         return this.componentDirs[this.componentIndex].getName();
      }

      public String target_name() {
         StringBuffer sb = new StringBuffer();
         sb.append(this.component_name());
         sb.append("@${servername}");
         return sb.toString();
      }

      private boolean hasLibraries() {
         return this.libraries.length > 0 || this.unresolvedRefs.length > 0;
      }

      public String nested_libraries() throws CodeGenerationException {
         StringBuffer rtn = new StringBuffer();
         if (this.hasLibraries()) {
            rtn.append(">").append(System.getProperty("line.separator"));
         }

         if (this.libraries.length > 0) {
            rtn.append(this.parse(this.getProductionRule("app_libraries_rule")));
         }

         if (this.unresolvedRefs.length > 0) {
            rtn.append(this.parse(this.getProductionRule("unresolved_applib_refs_rule")));
         }

         return rtn.toString();
      }

      public String wlcompile_end() {
         return this.hasLibraries() ? "    </wlcompile>" : " />";
      }

      public String appc_end() {
         return this.hasLibraries() ? "    </wlappc>" : " />";
      }

      public String app_libraries() throws CodeGenerationException {
         if (this.libraries.length == 0) {
            return "";
         } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < this.libraries.length; ++i) {
               this.libraryIndex = i;
               sb.append(this.parse(this.getProductionRule("app_library")));
               this.trimRightHandSide(sb);
               if (i < this.libraries.length - 1) {
                  sb.append(System.getProperty("line.separator"));
               }
            }

            return sb.toString();
         }
      }

      public String unresolved_applib_refs() throws CodeGenerationException {
         if (this.unresolvedRefs.length == 0) {
            return "";
         } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < this.unresolvedRefs.length; ++i) {
               this.unresolvedRefIndex = i;
               sb.append(this.parse(this.getProductionRule("unresolved_applib_ref")));
               this.trimRightHandSide(sb);
               if (i < this.unresolvedRefs.length - 1) {
                  sb.append(System.getProperty("line.separator"));
               }
            }

            return sb.toString();
         }
      }

      public String unresolved_library_ref() {
         LibraryReference ref = this.unresolvedRefs[this.unresolvedRefIndex];
         StringBuffer sb = new StringBuffer(ref.getName());
         if (ref.getSpecificationVersion() != null) {
            sb.append("-").append(ref.getSpecificationVersion());
            if (ref.getImplementationVersion() != null) {
               sb.append("-").append(ref.getImplementationVersion());
            }
         }

         return sb.toString();
      }

      public String library_file() {
         return this.resolvedRef ? this.libraries[this.libraryIndex].getLocation().getPath() : this.unresolvedRefs[this.unresolvedRefIndex].getName();
      }

      public String library_name() {
         return this.resolvedRef ? this.libraries[this.libraryIndex].getName() : this.unresolvedRefs[this.unresolvedRefIndex].getName();
      }

      public String library_target_name() {
         StringBuffer rtn = new StringBuffer();
         rtn.append(this.library_name());
         if (this.getSpec() != null) {
            rtn.append("-").append(this.getSpec());
         }

         if (this.getImpl() != null) {
            rtn.append("-").append(this.getImpl());
         }

         if (this.resolvedRef) {
            this.allLibraryTargets.add(rtn.toString());
         }

         return rtn.toString();
      }

      public String libspecver() {
         return this.getSpec() == null ? "" : " libspecver=\"" + this.getSpec() + "\"";
      }

      public String libimplver() {
         return this.getImpl() == null ? "" : " libimplver=\"" + this.getImpl() + "\"";
      }

      private String getSpec() {
         return this.resolvedRef ? this.libraries[this.libraryIndex].getSpecificationVersion() : this.unresolvedRefs[this.unresolvedRefIndex].getSpecificationVersion();
      }

      private String getImpl() {
         return this.resolvedRef ? this.libraries[this.libraryIndex].getImplementationVersion() : this.unresolvedRefs[this.unresolvedRefIndex].getImplementationVersion();
      }

      public String deploy_libraries() throws CodeGenerationException {
         return this.generate_deployment_target("deploy");
      }

      public String generate_deployment_target(String deployCommand) throws CodeGenerationException {
         if (!this.hasLibraries()) {
            return "";
         } else {
            StringBuffer sb = new StringBuffer();
            int i;
            if (this.libraries.length > 0) {
               this.resolvedRef = true;

               for(i = 0; i < this.libraries.length; ++i) {
                  this.libraryIndex = i;
                  sb.append(this.parse(this.getProductionRule(deployCommand + "_library")));
               }
            }

            if (this.unresolvedRefs.length > 0) {
               this.resolvedRef = false;

               for(i = 0; i < this.unresolvedRefs.length; ++i) {
                  this.unresolvedRefIndex = i;
                  sb.append(this.parse(this.getProductionRule(deployCommand + "_library_unresolved_ref")));
               }
            }

            return sb.toString();
         }
      }

      public String deploy_all_libraries() throws CodeGenerationException {
         return this.allLibraryTargets.isEmpty() ? "" : this.parse(this.getProductionRule("deploy_all_libraries_rule"));
      }

      public String all_libraries_deploy_targets() throws CodeGenerationException {
         if (this.allLibraryTargets.isEmpty()) {
            return "";
         } else {
            StringBuffer sb = new StringBuffer();
            Iterator iter = this.allLibraryTargets.iterator();

            while(iter.hasNext()) {
               sb.append("deploy.lib.").append(iter.next().toString());
               if (iter.hasNext()) {
                  sb.append(",");
               }
            }

            return sb.toString();
         }
      }

      public String undeploy_libraries() throws CodeGenerationException {
         return this.generate_deployment_target("undeploy");
      }

      public String undeploy_all_libraries() throws CodeGenerationException {
         return this.allLibraryTargets.isEmpty() ? "" : this.parse(this.getProductionRule("undeploy_all_libraries_rule"));
      }

      public String all_libraries_undeploy_targets() throws CodeGenerationException {
         if (this.allLibraryTargets.isEmpty()) {
            return "";
         } else {
            StringBuffer sb = new StringBuffer();
            Iterator iter = this.allLibraryTargets.iterator();

            while(iter.hasNext()) {
               sb.append("undeploy.lib.").append(iter.next().toString());
               if (iter.hasNext()) {
                  sb.append(",");
               }
            }

            return sb.toString();
         }
      }

      private void trimRightHandSide(StringBuffer sb) {
         int i;
         for(i = sb.length() - 1; i >= 0 && Character.isWhitespace(sb.charAt(i)); --i) {
         }

         sb.delete(i + 1, sb.length());
      }

      protected Enumeration outputs(List inputs) throws Exception {
         if (this.verboseCodegen) {
            Debug.say("outputs called");
         }

         CodeGenerator.Output o = new BuildXMLGenOutput();
         o.setTemplate("buildxml.j");
         o.setOutputFile(this.buildFileName);
         Vector v = new Vector();
         v.add(o);
         return v.elements();
      }

      public static class BuildXMLGenOutput extends CodeGenerator.Output {
      }
   }
}
