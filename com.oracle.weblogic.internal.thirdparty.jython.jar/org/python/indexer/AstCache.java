package org.python.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.python.antlr.AnalyzingParser;
import org.python.antlr.base.mod;
import org.python.antlr.runtime.ANTLRFileStream;
import org.python.antlr.runtime.ANTLRStringStream;
import org.python.antlr.runtime.CharStream;
import org.python.antlr.runtime.RecognitionException;
import org.python.indexer.ast.NModule;

public class AstCache {
   public static final String CACHE_DIR;
   private static final Logger LOG;
   private Map cache = new HashMap();
   private static AstCache INSTANCE;

   private AstCache() throws Exception {
      File f = new File(CACHE_DIR);
      if (!f.exists()) {
         f.mkdirs();
      }

   }

   public static AstCache get() throws Exception {
      if (INSTANCE == null) {
         INSTANCE = new AstCache();
      }

      return INSTANCE;
   }

   public void clear() {
      this.cache.clear();
   }

   public boolean clearDiskCache() {
      try {
         File dir = new File(CACHE_DIR);
         File[] var2 = dir.listFiles();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            File f = var2[var4];
            if (f.isFile()) {
               f.delete();
            }
         }

         return true;
      } catch (Exception var6) {
         this.severe("Failed to clear disk cache: " + var6);
         return false;
      }
   }

   public NModule getAST(String path) throws Exception {
      if (path == null) {
         throw new IllegalArgumentException("null path");
      } else {
         return this.fetch(path);
      }
   }

   public NModule getAST(String path, String contents) throws Exception {
      if (path == null) {
         throw new IllegalArgumentException("null path");
      } else if (contents == null) {
         throw new IllegalArgumentException("null contents");
      } else if (this.cache.containsKey(path)) {
         return (NModule)this.cache.get(path);
      } else {
         NModule mod = null;

         try {
            mod = this.parse(path, contents);
            if (mod != null) {
               mod.setFileAndMD5(path, Util.getMD5(contents.getBytes("UTF-8")));
            }
         } finally {
            this.cache.put(path, mod);
         }

         return mod;
      }
   }

   private NModule fetch(String path) throws Exception {
      if (this.cache.containsKey(path)) {
         return (NModule)this.cache.get(path);
      } else {
         NModule mod = this.getSerializedModule(path);
         if (mod != null) {
            this.fine("reusing " + path);
            this.cache.put(path, mod);
            return mod;
         } else {
            try {
               mod = this.parse(path);
            } finally {
               this.cache.put(path, mod);
            }

            if (mod != null) {
               this.serialize(mod);
            }

            return mod;
         }
      }
   }

   private NModule parse(String path) throws Exception {
      this.fine("parsing " + path);
      mod ast = this.invokeANTLR(path);
      return this.generateAST(ast, path);
   }

   private NModule parse(String path, String contents) throws Exception {
      this.fine("parsing " + path);
      mod ast = this.invokeANTLR(path, contents);
      return this.generateAST(ast, path);
   }

   private NModule generateAST(mod ast, String path) throws Exception {
      if (ast == null) {
         Indexer.idx.reportFailedAssertion("ANTLR returned NULL for " + path);
         return null;
      } else {
         Object obj = ast.accept(new AstConverter());
         if (!(obj instanceof NModule)) {
            this.warn("\n[warning] converted AST is not a module: " + obj);
            return null;
         } else {
            NModule module = (NModule)obj;
            if ((new File(path)).canRead()) {
               module.setFile(path);
            }

            return module;
         }
      }
   }

   private mod invokeANTLR(String filename) {
      CharStream text = null;

      try {
         text = new ANTLRFileStream(filename);
      } catch (IOException var4) {
         this.fine(filename + ": " + var4);
         return null;
      }

      return this.invokeANTLR((CharStream)text, filename);
   }

   private mod invokeANTLR(String filename, String contents) {
      CharStream text = new ANTLRStringStream(contents);
      return this.invokeANTLR((CharStream)text, filename);
   }

   private mod invokeANTLR(CharStream text, String filename) {
      AnalyzingParser p = new AnalyzingParser(text, filename, (String)null);
      mod ast = null;

      try {
         ast = p.parseModule();
      } catch (Exception var6) {
         this.fine("parse for " + filename + " failed: " + var6);
      }

      this.recordParseErrors(filename, p.getRecognitionErrors());
      return ast;
   }

   private void recordParseErrors(String path, List errs) {
      if (!errs.isEmpty()) {
         List diags = Indexer.idx.getParseErrs(path);
         Iterator var4 = errs.iterator();

         while(var4.hasNext()) {
            RecognitionException rx = (RecognitionException)var4.next();
            String msg = rx.line + ":" + rx.charPositionInLine + ":" + rx;
            diags.add(new Diagnostic(path, Diagnostic.Type.ERROR, -1, -1, msg));
         }

      }
   }

   public String getCachePath(File sourcePath) throws Exception {
      return this.getCachePath(Util.getMD5(sourcePath), sourcePath.getName());
   }

   public String getCachePath(String md5, String name) {
      return CACHE_DIR + name + md5 + ".ast";
   }

   void serialize(NModule ast) throws Exception {
      String path = this.getCachePath(ast.getMD5(), (new File(ast.getFile())).getName());
      ObjectOutputStream oos = null;
      FileOutputStream fos = null;

      try {
         fos = new FileOutputStream(path);
         oos = new ObjectOutputStream(fos);
         oos.writeObject(ast);
      } finally {
         if (oos != null) {
            oos.close();
         } else if (fos != null) {
            fos.close();
         }

      }

   }

   NModule getSerializedModule(String sourcePath) {
      try {
         File sourceFile = new File(sourcePath);
         if (sourceFile != null && sourceFile.canRead()) {
            File cached = new File(this.getCachePath(sourceFile));
            return !cached.canRead() ? null : this.deserialize(sourceFile);
         } else {
            return null;
         }
      } catch (Exception var4) {
         this.severe("Failed to deserialize " + sourcePath + ": " + var4);
         return null;
      }
   }

   NModule deserialize(File sourcePath) throws Exception {
      String cachePath = this.getCachePath(sourcePath);
      FileInputStream fis = null;
      ObjectInputStream ois = null;

      NModule var6;
      try {
         fis = new FileInputStream(cachePath);
         ois = new ObjectInputStream(fis);
         NModule mod = (NModule)ois.readObject();
         mod.setFile(sourcePath);
         var6 = mod;
      } finally {
         if (ois != null) {
            ois.close();
         } else if (fis != null) {
            fis.close();
         }

      }

      return var6;
   }

   private void log(Level level, String msg) {
      if (LOG.isLoggable(level)) {
         LOG.log(level, msg);
      }

   }

   private void severe(String msg) {
      this.log(Level.SEVERE, msg);
   }

   private void warn(String msg) {
      this.log(Level.WARNING, msg);
   }

   private void info(String msg) {
      this.log(Level.INFO, msg);
   }

   private void fine(String msg) {
      this.log(Level.FINE, msg);
   }

   private void finer(String msg) {
      this.log(Level.FINER, msg);
   }

   static {
      CACHE_DIR = (new File(Util.getSystemTempDir(), "jython/ast_cache")).getAbsolutePath() + File.separator;
      LOG = Logger.getLogger(AstCache.class.getCanonicalName());
   }
}
