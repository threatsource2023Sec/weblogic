package org.apache.openjpa.meta;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.ClassArgParser;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.MetaDataException;

public class MetaDataTool implements MetaDataModes {
   public static final String ACTION_ADD = "add";
   public static final String ACTION_DROP = "drop";
   public static final String[] ACTIONS = new String[]{"add", "drop"};
   private static Localizer _loc = Localizer.forPackage(MetaDataTool.class);
   private final OpenJPAConfiguration _conf;
   private final String _action;
   private final Set _drop;
   private MetaDataRepository _repos = null;
   private File _file = null;
   private Writer _writer = null;
   private boolean _flush = false;

   public MetaDataTool(OpenJPAConfiguration conf, String action) {
      this._conf = conf;
      this._action = action == null ? "add" : action;
      if ("drop".equals(this._action)) {
         this._drop = new HashSet();
      } else {
         this._drop = null;
      }

   }

   public String getAction() {
      return this._action;
   }

   public File getFile() {
      return this._file;
   }

   public void setFile(File file) {
      this._file = file;
   }

   public Writer getWriter() {
      return this._writer;
   }

   public void setWriter(Writer writer) {
      this._writer = writer;
   }

   public MetaDataRepository getRepository() {
      if (this._repos == null) {
         this._repos = this.newRepository();
         this._repos.setResolve(2, false);
         MetaDataFactory factory = this._repos.getMetaDataFactory();
         factory.getDefaults().setIgnoreNonPersistent(false);
         factory.setStoreMode(2);
      }

      return this._repos;
   }

   protected MetaDataRepository newRepository() {
      return this._conf.newMetaDataRepositoryInstance();
   }

   public void setRepository(MetaDataRepository repos) {
      this._repos = repos;
   }

   public void clear() {
      this._repos = null;
      if (this._drop != null) {
         this._drop.clear();
      }

      this._flush = false;
   }

   public void run(Class cls) {
      if (cls != null) {
         if ("drop".equals(this._action)) {
            this._drop.add(cls);
         } else {
            if (!"add".equals(this._action)) {
               throw new IllegalArgumentException("action == " + this._action);
            }

            this.add(cls);
         }

      }
   }

   private void add(Class cls) {
      ClassMetaData meta = this.getRepository().addMetaData(cls);
      FieldMetaData[] fmds = meta.getDeclaredFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].getDeclaredTypeCode() == 8 && fmds[i].getDeclaredType() != Object.class) {
            fmds[i].setDeclaredTypeCode(15);
         }
      }

      meta.setSource(this._file, meta.getSourceType());
      this._flush = true;
   }

   public void record() {
      MetaDataRepository repos = this.getRepository();
      MetaDataFactory mdf = repos.getMetaDataFactory();

      try {
         if (this._drop != null && !this._drop.isEmpty() && !mdf.drop((Class[])((Class[])this._drop.toArray(new Class[this._drop.size()])), 7, (ClassLoader)null)) {
            Log log = this._conf.getLog("openjpa.MetaData");
            if (log.isWarnEnabled()) {
               log.warn(_loc.get("bad-drop", (Object)this._drop));
            }
         }

         if (this._flush) {
            ClassMetaData[] metas = repos.getMetaDatas();
            Map output = null;
            if (this._writer != null) {
               output = new HashMap();
               File tmp = new File("openjpatmp");

               for(int i = 0; i < metas.length; ++i) {
                  metas[i].setSource(tmp, metas[i].getSourceType());
               }
            }

            if (!mdf.store(metas, new QueryMetaData[0], new SequenceMetaData[0], 1, output)) {
               throw new MetaDataException(_loc.get("bad-store"));
            }

            if (this._writer == null) {
               return;
            }

            PrintWriter out = new PrintWriter(this._writer);
            Iterator itr = output.values().iterator();

            while(itr.hasNext()) {
               out.println((String)itr.next());
            }

            out.flush();
            return;
         }
      } finally {
         this.clear();
      }

   }

   public static void main(String[] args) throws IOException {
      Options opts = new Options();
      final String[] arguments = opts.setFromCmdLine(args);
      boolean ret = args.length > 0 && Configurations.runAgainstAllAnchors(opts, new Configurations.Runnable() {
         public boolean run(Options opts) throws Exception {
            OpenJPAConfiguration conf = new OpenJPAConfigurationImpl();

            boolean var3;
            try {
               var3 = MetaDataTool.run(conf, arguments, opts);
            } finally {
               conf.close();
            }

            return var3;
         }
      });
      if (!ret) {
         System.err.println(_loc.get("tool-usage"));
      }

   }

   public static boolean run(OpenJPAConfiguration conf, String[] args, Options opts) throws IOException {
      Flags flags = new Flags();
      flags.action = opts.removeProperty("action", "a", flags.action);
      String fileName = opts.removeProperty("file", "f", (String)null);
      if ("stdout".equals(fileName)) {
         flags.writer = new PrintWriter(System.out);
         fileName = null;
      } else if ("stderr".equals(fileName)) {
         flags.writer = new PrintWriter(System.err);
         fileName = null;
      }

      Configurations.populateConfiguration(conf, opts);
      ClassLoader loader = conf.getClassResolverInstance().getClassLoader(MetaDataTool.class, (ClassLoader)null);
      if (fileName != null) {
         flags.file = Files.getFile(fileName, loader);
      }

      return run(conf, args, flags, (MetaDataRepository)null, loader);
   }

   public static boolean run(OpenJPAConfiguration conf, String[] args, Flags flags, MetaDataRepository repos, ClassLoader loader) throws IOException {
      if (args.length == 0) {
         return false;
      } else {
         if (flags.action == null) {
            flags.action = "add";
         }

         MetaDataTool tool = new MetaDataTool(conf, flags.action);
         if (repos != null) {
            MetaDataFactory factory = repos.getMetaDataFactory();
            factory.getDefaults().setIgnoreNonPersistent(false);
            factory.setStoreMode(2);
            tool.setRepository(repos);
         }

         if (flags.file != null) {
            tool.setFile(flags.file);
         }

         if (flags.writer != null) {
            tool.setWriter(flags.writer);
         }

         Log log = conf.getLog("openjpa.Tool");
         ClassArgParser cap = conf.getMetaDataRepositoryInstance().getMetaDataFactory().newClassArgParser();
         cap.setClassLoader(loader);

         for(int i = 0; i < args.length; ++i) {
            Class[] classes = cap.parseTypes(args[i]);

            for(int j = 0; j < classes.length; ++j) {
               log.info(_loc.get("tool-running", classes[j], flags.action));

               try {
                  tool.run(classes[j]);
               } catch (IllegalArgumentException var12) {
                  return false;
               }
            }
         }

         log.info(_loc.get("tool-record"));
         tool.record();
         return true;
      }
   }

   public static class Flags {
      public String action = "add";
      public File file = null;
      public Writer writer = null;
   }
}
