package weblogic.application.ddconvert;

import java.io.File;
import java.io.IOException;
import weblogic.application.ApplicationFileManager;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.compiler.ToolsInitializerManager;
import weblogic.utils.FileUtils;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFile;

public final class DDConverter extends Tool {
   private File inputFile;
   private File workingDir;
   private boolean deleteWorkingDir;

   public DDConverter(String[] argv) {
      super(argv);
   }

   private static void p(String s) {
      ConvertCtx.debug(s);
   }

   public void prepare() {
      this.setUsageName("weblogic.DDConverter");
      this.opts.setUsageArgs("<EAR, JAR, RAR or WAR file/directory>");
      this.opts.addOption("d", "dir", "Directory where descriptors are written");
      this.opts.addFlag("verbose", "Turns on additional output for debugging");
      this.opts.addFlag("quiet", "Turns off output except for errors");
      this.opts.markPrivate("version");
   }

   private ConvertCtx setupFiles(String arg) throws ToolFailureException, IOException {
      boolean isVerbose = this.opts.hasOption("verbose");
      boolean isQuiet = this.opts.hasOption("quiet");
      this.inputFile = new File(arg);
      if (!this.inputFile.exists()) {
         throw new ToolFailureException("InputDirectory: " + this.inputFile + " does not exist or could not be read.");
      } else {
         if (isVerbose) {
            p("inputFile " + this.inputFile.getAbsolutePath());
         }

         String outputPath = this.opts.getOption("d");
         if (outputPath == null) {
            throw new ToolFailureException("-d must be specified to indicate where DDConverter should write the descriptors");
         } else {
            File outputDir = new File(outputPath);
            if (isVerbose) {
               p("outputDir " + outputDir.getAbsolutePath());
            }

            if (outputDir.exists()) {
               if (!outputDir.isDirectory()) {
                  throw new ToolFailureException("-d " + outputDir + " is not a directory.  Please specify a directory for output.");
               }
            } else {
               outputDir.mkdirs();
            }

            if (this.inputFile.isDirectory()) {
               this.workingDir = this.inputFile;
               this.deleteWorkingDir = false;
               if (isVerbose) {
                  p("Using exploded dir " + this.workingDir.getAbsolutePath());
               }
            } else {
               this.workingDir = FileUtils.createTempDir(this.inputFile.getName());
               this.deleteWorkingDir = true;
               if (isVerbose) {
                  p("extracting jar to " + this.workingDir.getAbsolutePath());
               }

               JarFileUtils.extract(this.inputFile, this.workingDir);
            }

            ApplicationArchive archive = null;
            ApplicationFileManager afm;
            if (archive != null) {
               afm = ApplicationFileManager.newInstance((ApplicationArchive)archive);
            } else {
               afm = ApplicationFileManager.newInstance(this.workingDir);
            }

            return new ConvertCtx((ApplicationArchive)archive, afm, outputDir, isVerbose, isQuiet);
         }
      }
   }

   private void example() {
      this.opts.usageError("weblogic.DDConverter");
      System.out.println("");
      System.out.println("Example: java weblogic.DDConverter -d tmpdir my.ear");
      System.out.println("");
   }

   public void runBody() throws ToolFailureException, IOException, DDConvertException {
      String[] args = this.opts.args();
      if (args != null && args.length != 0) {
         ConvertCtx ctx = this.setupFiles(args[0]);
         VirtualJarFile vjar = null;

         try {
            label122: {
               ToolsInitializerManager.init();
               vjar = ctx.getAppVJF();
               Converter[] converters = ConverterFactoryManager.instance.findConverters(ctx, vjar);
               if (converters != null && converters.length != 0) {
                  Converter[] var5 = converters;
                  int var6 = converters.length;
                  int var7 = 0;

                  while(true) {
                     if (var7 >= var6) {
                        break label122;
                     }

                     Converter c = var5[var7];
                     if (!ctx.isQuiet()) {
                        c.printStartMessage(this.inputFile.getName());
                     }

                     if (ctx.hasApplicationArchive()) {
                        c.convertDDs(ctx, ctx.getApplicationArchive(), ctx.getOutputDir());
                        c.convertDDs(ctx, vjar, ctx.getOutputDir());
                     } else {
                        c.convertDDs(ctx, vjar, ctx.getOutputDir());
                     }

                     if (!ctx.isQuiet()) {
                        c.printEndMessage(this.inputFile.getName());
                     }

                     ++var7;
                  }
               }

               throw new ToolFailureException("For the application at " + this.inputFile + ", no descriptors were found for processing.\n\n\nFor EAR Files, there was no META-INF/application.xml.\nFor EJB-JAR files, there was no META-INF/ejb-jar.xml\nFor WAR files, there was no WEB-INF/web.xml\nFor RAR files, there was no META-INF/ra.xml");
            }
         } finally {
            if (vjar != null) {
               try {
                  vjar.close();
               } catch (IOException var14) {
               }
            }

         }

         if (this.deleteWorkingDir) {
            FileUtils.remove(this.workingDir);
         }

      } else {
         this.example();
      }
   }

   public static void main(String[] argv) throws Exception {
      (new DDConverter(argv)).run();
   }
}
