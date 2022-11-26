package weblogic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.i18n.tools.Config;
import weblogic.i18n.tools.FixDates;
import weblogic.i18n.tools.GenException;
import weblogic.i18n.tools.MessageCatalog;
import weblogic.i18n.tools.MessageCatalogWriter;
import weblogic.i18n.tools.MethodPropGen;
import weblogic.i18n.tools.PropGen;
import weblogic.i18n.tools.codegen.AntFormatter;
import weblogic.i18n.tools.codegen.MsgCat2Java;
import weblogic.utils.compiler.CompilerInvoker;
import weblogic.utils.compiler.Tool;

public class i18ngen extends Tool {
   private MsgCat2Java generator;
   private CompilerInvoker compiler;
   private boolean debug = false;
   private boolean noexit = false;
   private boolean dates = false;
   private boolean methodProps = false;

   public void prepare() {
      this.generator = new MsgCat2Java(this.opts);
   }

   public void runBody() throws Exception {
      String[] sources;
      try {
         sources = this.generator.generate(this.opts.args());
      } catch (NullPointerException var15) {
         throw new GenException();
      }

      if (this.opts.hasOption("generateantdependency")) {
         AntFormatter.generateBuildMsgcat(this.generator, this.opts.getOption("generateantdependency"));
      }

      boolean server = this.opts.hasOption("server");
      boolean ignore = this.opts.hasOption("ignore");
      this.debug = this.opts.hasOption("debug");
      this.noexit = this.opts.hasOption("noexit");
      boolean verbose = this.opts.hasOption("verbose");
      this.dates = this.opts.hasOption("dates");
      this.methodProps = this.opts.hasOption("methodprops");
      if (sources == null) {
         throw new GenException();
      } else {
         List jSources = new ArrayList();

         for(int i = 0; i < sources.length; ++i) {
            try {
               File fis = new File(sources[i]);
               if (verbose) {
                  System.out.print("Creating: ");
                  System.out.println(fis.getCanonicalPath());
               }
            } catch (Exception var20) {
               if (verbose) {
                  System.out.print("Failure to create: ");
               }

               System.out.println(sources[i]);
            }

            if (sources[i].endsWith(".java")) {
               jSources.add(sources[i]);
            }
         }

         if (!this.opts.hasOption("nobuild") && !this.opts.hasOption("nowrite")) {
            if (this.opts.hasOption("compile") && !jSources.isEmpty()) {
               this.opts.removeOption("debug");
               this.opts.removeOption("compile");
               this.opts.removeOption("verbose");
               this.compiler = new CompilerInvoker(this.opts);
               this.compiler.compile(jSources);
            }

            Config cfg = new Config();
            cfg.setServer(server);
            cfg.setDebug(this.debug);
            cfg.setVerbose(verbose);
            cfg.setStrictPackageCheckingEnabled(this.opts.hasOption("strict-package-checking"));
            Iterator catalogs = this.generator.getCatalogs().iterator();
            String targetDir = this.opts.getOption("d", ".");
            File tFile;
            int propCtr;
            MessageCatalog msgcat;
            if (catalogs.hasNext()) {
               tFile = new File(targetDir);
               if (!tFile.exists()) {
                  tFile.mkdirs();
               }

               PropGen pg = new PropGen(targetDir, cfg);
               pg.loadProp();
               propCtr = 0;

               label149:
               while(true) {
                  do {
                     if (!catalogs.hasNext()) {
                        try {
                           if (propCtr > 0) {
                              pg.unload();
                           }
                           break label149;
                        } catch (GenException var18) {
                           if (ignore) {
                              break label149;
                           }

                           throw var18;
                        }
                     }

                     msgcat = (MessageCatalog)catalogs.next();
                  } while(msgcat.getCatType() != 2);

                  try {
                     pg.gen(msgcat);
                     ++propCtr;
                  } catch (GenException var19) {
                     if (!ignore) {
                        throw var19;
                     }
                  }
               }
            }

            if (this.dates) {
               catalogs = this.generator.getCatalogs().iterator();

               while(catalogs.hasNext()) {
                  MessageCatalog msgcat = (MessageCatalog)catalogs.next();
                  String src = msgcat.getFullName();
                  if (src != null) {
                     File srcFile = new File(src);
                     if (srcFile.isFile() && srcFile.canWrite() && FixDates.fixDates(msgcat, srcFile)) {
                        try {
                           cfg.inform("Updating catalog, " + src + ", with new timestamps");
                           MessageCatalogWriter.writeCatalog(srcFile.getCanonicalPath(), msgcat);
                        } catch (IOException var14) {
                           System.out.println("Unable to open " + srcFile.getCanonicalPath() + " for output.");
                        }
                     }
                  }
               }
            }

            if (this.methodProps) {
               catalogs = this.generator.getCatalogs().iterator();
               targetDir = this.opts.getOption("d", ".");
               if (catalogs.hasNext()) {
                  tFile = new File(targetDir);
                  if (!tFile.exists()) {
                     tFile.mkdirs();
                  }

                  MethodPropGen pg = new MethodPropGen(targetDir, cfg);
                  pg.loadProp();
                  propCtr = 0;

                  while(true) {
                     do {
                        if (!catalogs.hasNext()) {
                           try {
                              if (propCtr > 0) {
                                 pg.unload();
                              }

                              return;
                           } catch (GenException var16) {
                              if (ignore) {
                                 return;
                              }

                              throw var16;
                           }
                        }

                        msgcat = (MessageCatalog)catalogs.next();
                     } while(msgcat.getCatType() != 2);

                     try {
                        cfg.inform("method props for " + msgcat.getFileName());
                        pg.gen(msgcat);
                        ++propCtr;
                     } catch (GenException var17) {
                        if (!ignore) {
                           throw var17;
                        }
                     }
                  }
               }
            }
         }

      }
   }

   public static String[] getOutputFileNames(String[] args) {
      i18ngen gen = new i18ngen(args);

      try {
         gen.run();
         return gen.generator.getOutputFileNames();
      } catch (Exception var3) {
         if (gen.debug) {
            var3.printStackTrace();
         }

         return null;
      }
   }

   private i18ngen(String[] args) {
      super(args);
   }

   public static void main(String[] args) throws Exception {
      i18ngen task = new i18ngen(args);

      try {
         task.run();
      } catch (Exception var3) {
         if (task.debug) {
            var3.printStackTrace();
         }

         if (task.noexit) {
            throw var3;
         }

         System.exit(1);
      }

   }
}
