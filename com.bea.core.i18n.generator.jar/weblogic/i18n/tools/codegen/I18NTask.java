package weblogic.i18n.tools.codegen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import weblogic.i18n.tools.Config;
import weblogic.i18n.tools.GenException;
import weblogic.i18n.tools.MessageCatalog;
import weblogic.i18n.tools.PropGen;
import weblogic.utils.BadOptionException;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.CompilerInvoker;

public class I18NTask extends MatchingTask {
   private Getopt2 options;
   private MsgCat2Java generator;
   private boolean verbose = false;
   private boolean compile = false;
   private boolean i18n = true;
   private boolean checkDepends = true;
   private String catalog = null;
   private String sourceDir = null;
   private Path classpath;

   public void init() {
      this.options = new Getopt2();
      this.generator = new MsgCat2Java(this.options);
   }

   public void setSourceDir(String sourceCodeDirectory) {
      this.sourceDir = sourceCodeDirectory;
   }

   public void setClasspath(Path classpath) {
      this.classpath = classpath;
   }

   public void setClasspathRef(Reference r) {
      this.classpath = new Path(this.getProject());
      this.classpath.setRefid(r);
   }

   public void setCatalog(String catalog) {
      this.catalog = catalog;
   }

   public void setCheckDepends(boolean check) {
      this.checkDepends = check;
   }

   public void setI18n(boolean value) {
      try {
         this.i18n = true;
         this.options.setOption("i18n", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setL10n(boolean value) {
      try {
         this.options.setOption("l10n", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setVerbose(boolean value) {
      try {
         this.verbose = value;
         this.options.setOption("verbose", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setDebug(boolean value) {
      try {
         this.options.setOption("debug", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setIgnore(boolean value) {
      try {
         this.options.setOption("ignore", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setKeepGenerated(boolean value) {
      try {
         this.options.setOption("keepgenerated", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setNoBuild(boolean value) {
      try {
         this.options.setOption("nobuild", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setBuild(boolean value) {
      try {
         this.options.setOption("build", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setNoWrite(boolean value) {
      try {
         this.options.setOption("nowrite", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setCompile(boolean value) {
      try {
         this.compile = true;
         this.options.setOption("compile", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setDeprecated(boolean value) {
      try {
         this.options.setOption("deprecation", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setDates(boolean value) {
      try {
         this.options.setOption("dates", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setMethodProps(boolean value) {
      try {
         this.options.setOption("methodprops", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setClasses(String value) {
      try {
         this.options.setOption("d", value);
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setGenerated(String value) {
      try {
         this.options.setOption("generated", value);
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setCompiler(String compiler) {
      try {
         this.options.setOption("compiler", compiler);
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setSource(String source) {
      try {
         if (this.verbose) {
            this.log("Setting source option to " + source);
         }

         this.options.setOption("source", source);
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setTarget(String target) {
      try {
         if (this.verbose) {
            this.log("Setting target option to " + target);
         }

         this.options.setOption("target", target);
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setRange(String range) {
      if (!range.equals("client") && !range.equals("server")) {
         throw new RuntimeException("range must be either client or server.  It was " + range);
      } else {
         try {
            this.options.setOption("range", range);
         } catch (BadOptionException var3) {
            var3.printStackTrace();
            throw new RuntimeException(var3);
         }
      }
   }

   public void setServer(boolean value) {
      try {
         this.options.setOption("server", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void setCommentary(boolean value) {
      try {
         if (this.verbose) {
            this.log("Setting commentary option to " + value);
         }

         this.options.setOption("commentary", Boolean.toString(value));
      } catch (BadOptionException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   public void execute() throws BuildException {
      try {
         if (this.checkDepends) {
            this.options.setOption("checkdepends", "true");
         }
      } catch (BadOptionException var10) {
         var10.printStackTrace();
         throw new BuildException(var10);
      }

      if (this.sourceDir != null && this.catalog != null) {
         throw new BuildException("Only one of sourceDir or catalog may be set");
      } else if (this.sourceDir == null && this.catalog == null) {
         throw new BuildException("At least one of sourceDir or catalog may be set");
      } else {
         String[] sources;
         File sourceDirFile;
         if (this.sourceDir != null) {
            if (this.sourceDir.startsWith(".")) {
               sourceDirFile = this.getProject().getBaseDir();
               if (this.sourceDir.length() > 1) {
                  this.sourceDir = sourceDirFile.getAbsolutePath() + this.sourceDir.substring(1);
               } else {
                  this.sourceDir = sourceDirFile.getAbsolutePath();
               }
            }

            if (this.verbose) {
               this.log("Scanning " + this.sourceDir);
            }

            sourceDirFile = new File(this.sourceDir);
            DirectoryScanner ds = this.getDirectoryScanner(sourceDirFile);
            ds.scan();
            sources = ds.getIncludedFiles();

            for(int i = 0; i < sources.length; ++i) {
               String source = sources[i];
               File targetSource = new File(sourceDirFile, source);
               sources[i] = targetSource.getAbsolutePath();
               this.log("Preparing to process message catalog " + source);
            }

            if (sources.length == 0) {
               throw new BuildException("Bean generation failure: No source files matched");
            }
         } else {
            if (this.catalog.startsWith(".")) {
               sourceDirFile = this.getProject().getBaseDir();
               if (this.catalog.length() > 1) {
                  this.catalog = sourceDirFile.getAbsolutePath() + this.catalog.substring(1);
               } else {
                  this.catalog = sourceDirFile.getAbsolutePath();
               }
            }

            sources = new String[]{this.catalog};
         }

         String[] generated;
         try {
            generated = this.generator.generate(sources);
         } catch (Exception var9) {
            throw new BuildException(var9);
         }

         this.generateI18NProperties();
         String[] generatedSources = this.getOnlySources(generated);
         if (generatedSources != null && generatedSources.length != 0 && this.compile && this.i18n) {
            try {
               this.options.removeOption("debug");
               this.options.removeOption("compile");
               this.options.removeOption("verbose");
               this.options.setOption("classpath", this.classpath.toString());
               if (this.verbose) {
                  this.log("compiling generated sources with options: " + this.options);
               }

               CompilerInvoker compiler = new CompilerInvoker(this.options);
               compiler.compile(generatedSources);
            } catch (IOException var7) {
               throw new BuildException(var7);
            } catch (BadOptionException var8) {
               throw new BuildException(var8);
            }
         }

      }
   }

   private void generateI18NProperties() throws BuildException {
      this.log("Building i18n.properties ...");
      Config cfg = new Config();
      boolean server = false;
      if (this.options.hasOption("server")) {
         server = true;
      } else if (this.options.hasOption("range")) {
         server = this.options.getOption("range").equals("server");
      }

      boolean ignore = this.options.hasOption("ignore");
      boolean debug = this.options.hasOption("debug");
      cfg.setServer(server);
      cfg.setDebug(debug);
      cfg.setVerbose(this.verbose);
      Iterator catalogs = this.generator.getCatalogs().iterator();
      String targetDir = this.options.getOption("d", ".");
      if (catalogs.hasNext()) {
         File tFile = new File(targetDir);
         if (!tFile.exists()) {
            tFile.mkdirs();
         }

         PropGen pg = new PropGen(targetDir, cfg);

         try {
            pg.loadProp();
         } catch (IOException var14) {
            if (!ignore) {
               throw new BuildException(var14);
            }
         }

         int propCtr = 0;

         while(true) {
            MessageCatalog msgcat;
            do {
               if (!catalogs.hasNext()) {
                  try {
                     if (propCtr > 0) {
                        pg.unload();
                     }

                     return;
                  } catch (GenException var12) {
                     if (ignore) {
                        return;
                     }

                     throw new BuildException(var12);
                  }
               }

               msgcat = (MessageCatalog)catalogs.next();
            } while(msgcat.getCatType() != 2);

            this.log("Processing catalog " + msgcat.getFileName());

            try {
               pg.gen(msgcat);
               ++propCtr;
            } catch (GenException var13) {
               if (!ignore) {
                  throw new BuildException(var13);
               }
            }
         }
      }
   }

   private String[] getOnlySources(String[] generated) {
      ArrayList sources = new ArrayList();

      for(int i = 0; i < generated.length; ++i) {
         String s = generated[i];
         this.log("Generated " + s);
         if (s.endsWith("java")) {
            if (this.verbose) {
               this.log("Preparing to compile " + s);
            }

            sources.add(s);
         }
      }

      String[] results = new String[sources.size()];
      return (String[])((String[])sources.toArray(results));
   }
}
