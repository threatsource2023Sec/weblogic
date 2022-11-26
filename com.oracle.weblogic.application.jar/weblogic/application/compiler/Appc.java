package weblogic.application.compiler;

import java.util.ArrayList;
import java.util.List;
import weblogic.application.compiler.flow.AppCompilerFlow;
import weblogic.application.compiler.flow.CheckUnusedLibrariesFlow;
import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.application.compiler.flow.InitLibrariesFlow;
import weblogic.application.compiler.flow.InitPlanFlow;
import weblogic.application.compiler.flow.OptionalPackageReferencerFlow;
import weblogic.application.compiler.flow.PrepareInputFlow;
import weblogic.application.compiler.flow.SetupFlow;
import weblogic.application.utils.LibraryUtils;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.BadOptionException;
import weblogic.utils.compiler.ArgfileParser;
import weblogic.utils.compiler.CompilerInvoker;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;

public class Appc extends Tool {
   protected Appc(String[] args) {
      super(args);
   }

   public void prepare() {
      this.setRequireExtraArgs(true);
      this.setUsageName("weblogic.appc");
      this.opts.setUsageArgs("<ear, jar, war or rar file or directory>");
      this.opts.addOption("output", "file", "Specifies an alternate output archive or directory.  If not set, output will be placed in the source archive or directory.");
      this.opts.addOption("plan", "file", "Specifies an optional deployment plan.");
      this.opts.addFlag("forceGeneration", "Force generation of EJB and JSP classes.  Without this flag the classes may not be regenerated if it is determined to be unnecessary.");
      this.opts.addFlag("quiet", "Turns off output except for errors");
      this.opts.addFlag("lineNumbers", "Add JSP line numbers to generated class files to aid in debugging.");
      this.opts.addAdvancedFlag("k", "continue compiling jsp files, even when some fail");
      this.opts.addOption("moduleUri", "uri", "Specify a module in an ear file for compilation.");
      this.opts.addOption("jsps", "jsps", "Comma-separated list of jsp files, specifies jsps that need to be compiled. All jsps of the app will be compiled if the option is not passed into.");
      this.opts.addAdvancedFlag("useByteBuffer", "Generate source codes of jsp files to use NIO ByteBuffer for static contents.");
      this.opts.addFlag("compileAllTagFiles", "Compile all JSP tag files");
      LibraryUtils.addLibraryUsage(this.opts);
      this.opts.addAdvancedFlag("basicClientJar", "Do not include deployment descriptors in client jars generated for EJBs.");
      this.opts.addFlag("disableHotCodeGen", "Generate ejb stub and skel as part of ejbc. Avoid HotCodeGen to have better performance.");
      this.opts.addFlag("enableHotCodeGen", "Do not generate ejb stub and skel as part of ejbc. Stubs and skels will be dynamically generated.");
      this.opts.addFlag("writeInferredDescriptors", "Write out the descriptors with inferred information including annotations.");
      this.opts.addOption("manifest", "file", "Include manifest information from specified manifest file.");
      this.opts.addOption("clientJarOutputDir", "dir", "Specifies a directory to put generated client jars.");
      this.opts.addAdvancedFlag("idl", "Generate idl for EJB remote interfaces");
      this.opts.addAdvancedFlag("idlOverwrite", "Always overwrite existing IDL files");
      this.opts.addAdvancedFlag("idlVerbose", "Display verbose information for IDL generation");
      this.opts.addAdvancedFlag("idlNoValueTypes", "Do not generate valuetypes and methods/attributes that contain them.");
      this.opts.addAdvancedFlag("idlNoAbstractInterfaces", "Do not generate abstract interfaces and methods/attributes that contain them.");
      this.opts.addAdvancedFlag("idlFactories", "Generate factory methods for valuetypes.");
      this.opts.addAdvancedFlag("idlVisibroker", "Generate IDL somewhat compatible with Visibroker 4.5 C++.");
      this.opts.addAdvancedFlag("idlOrbix", "Generate IDL somewhat compatible with Orbix 2000 2.0 C++.");
      this.opts.addAdvancedOption("idlDirectory", "dir", "Specify the directory where IDL files will be created (default : target directory or jar)");
      this.opts.addAdvancedOption("idlMethodSignatures", "signature", "Specify the method signatures used to trigger idl code generation.");
      this.opts.addAdvancedFlag("iiop", "Generate CORBA stubs for EJBs");
      this.opts.addAdvancedFlag("ignorePlanValidation", "Ignore the plan file if it doesn't exist");
      this.opts.addAdvancedOption("iiopDirectory", "dir", "Specify the directory where IIOP stub files will be written (default : target directory or jar)");
      this.opts.addAdvancedOption("altappdd", "file", "Location of the alternate application deployment descriptor. Support application(ear) and single-module deployments.");
      this.opts.addAdvancedOption("altwlsappdd", "file", "Location of the alternate WebLogic application deployment descriptor.");
      this.opts.addAdvancedOption("maxfiles", "int", "Maximum number of generated java files to be compiled at one time.");
      new CompilerInvoker(this.opts);
      this.opts.markAdvanced("verboseJavac");
      this.opts.markAdvanced("normi");
      this.opts.markAdvanced("nowarn");
      this.opts.markAdvanced("deprecation");
      this.opts.markAdvanced("O");
      this.opts.markAdvanced("J");
      this.opts.markAdvanced("g");
      this.opts.markAdvanced("compilerclass");
      this.opts.markAdvanced("compiler");
      this.opts.markPrivate("nowrite");
      this.opts.markPrivate("commentary");
      this.opts.markPrivate("d");
      this.opts.markPrivate("disableHotCodeGen");
      this.opts.markPrivate("enableHotCodeGen");
      this.opts.markPrivate("moduleUri");
      this.opts.markPrivate("jsps");
      this.opts.markPrivate("compileAllTagFiles");
      this.opts.markPrivate("useByteBuffer");
   }

   public void runBody() throws ToolFailureException {
      try {
         if (this.opts.hasOption("enableHotCodeGen")) {
            this.opts.setFlag("disableHotCodeGen", false);
         } else {
            this.opts.setFlag("disableHotCodeGen", true);
         }
      } catch (BadOptionException var5) {
         throw new AssertionError(var5);
      }

      this.opts.removeOption("enableHotCodeGen");
      CompilerCtx ctx = new CompilerCtxImpl();
      ctx.setOpts(this.opts);
      String workingDirName = "appcgen_" + System.currentTimeMillis();
      CompilerFlow[] flow = new CompilerFlow[]{new SetupFlow(ctx, workingDirName), new PrepareInputFlow(ctx), new OptionalPackageReferencerFlow(ctx), new InitPlanFlow(ctx), new InitLibrariesFlow(ctx, false), new AppCompilerFlow(ctx), new CheckUnusedLibrariesFlow(ctx)};

      try {
         (new FlowDriver()).run(flow);
      } catch (ToolFailureException var6) {
         J2EELogger.logAppcFailedWithError();
         if (var6.getCause() != null) {
            var6.getCause().printStackTrace();
         }

         throw var6;
      }
   }

   private List conjoinJspsOption(List args) {
      List result = new ArrayList();
      boolean isJspsOption = false;
      List jspList = new ArrayList();

      for(int i = 0; i < args.size(); ++i) {
         String arg = (String)args.get(i);
         if (i == args.size() - 1) {
            isJspsOption = false;
         }

         if (arg.startsWith("-")) {
            if ("-jsps".equals(arg)) {
               isJspsOption = true;
            } else {
               isJspsOption = false;
            }
         }

         if (isJspsOption && !arg.startsWith("-")) {
            jspList.add(arg);
         } else {
            result.add(arg);
         }
      }

      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < jspList.size(); ++i) {
         sb.append((String)jspList.get(i));
         if (i < jspList.size() - 1) {
            sb.append(",");
         }
      }

      if (result.indexOf("-jsps") >= 0) {
         result.add(result.indexOf("-jsps") + 1, sb.toString());
      }

      return result;
   }

   protected String[] transformArgs(String[] args) throws ToolFailureException {
      List result = new ArrayList();
      String[] var3 = args;
      int var4 = args.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String arg = var3[var5];
         if (arg.startsWith("@")) {
            result.addAll((new ArgfileParser(arg.substring(1))).parse());
         } else {
            result.add(arg);
         }
      }

      List result = this.conjoinJspsOption(result);
      return (String[])result.toArray(new String[result.size()]);
   }

   public static void main(String[] args) throws Exception {
      (new Appc(args)).run();
   }
}
