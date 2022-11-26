package org.python.indexer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.python.indexer.ast.NModule;
import org.python.indexer.ast.NNode;
import org.python.indexer.ast.NUrl;
import org.python.indexer.types.NModuleType;
import org.python.indexer.types.NType;

public class Indexer {
   public static Indexer idx;
   public Scope moduleTable;
   public Scope globaltable;
   private Map allBindings;
   private Map locations;
   public Map problems;
   public Map parseErrs;
   public String currentFile;
   public String projDir;
   public List path;
   private AstCache astCache;
   public Set failedModules;
   private Map unresolvedModules;
   public Builtins builtins;
   private boolean aggressiveAssertions;
   private int nloc;
   private int nunbound;
   private int nunknown;
   private int nprob;
   private int nparsing;
   private int loadedFiles;
   private Logger logger;

   public Indexer() {
      this.moduleTable = new Scope((Scope)null, Scope.Type.GLOBAL);
      this.globaltable = new Scope((Scope)null, Scope.Type.GLOBAL);
      this.allBindings = new HashMap();
      this.locations = new HashMap();
      this.problems = new HashMap();
      this.parseErrs = new HashMap();
      this.currentFile = null;
      this.projDir = null;
      this.path = new ArrayList();
      this.failedModules = new HashSet();
      this.unresolvedModules = new TreeMap();
      this.nloc = 0;
      this.nunbound = 0;
      this.nunknown = 0;
      this.nprob = 0;
      this.nparsing = 0;
      this.loadedFiles = 0;
      this.logger = Logger.getLogger(Indexer.class.getCanonicalName());
      idx = this;
      this.builtins = new Builtins(this.globaltable, this.moduleTable);
      this.builtins.init();
   }

   public void setLogger(Logger logger) {
      if (logger == null) {
         throw new IllegalArgumentException("null logger param");
      } else {
         this.logger = logger;
      }
   }

   public Logger getLogger() {
      return this.logger;
   }

   public void setProjectDir(String cd) throws IOException {
      this.projDir = Util.canonicalize(cd);
   }

   public void enableAggressiveAssertions(boolean enable) {
      this.aggressiveAssertions = enable;
   }

   public boolean aggressiveAssertionsEnabled() {
      return this.aggressiveAssertions;
   }

   public void handleException(String msg, Throwable cause) {
      if (cause instanceof StackOverflowError) {
         this.logger.log(Level.WARNING, msg, (Throwable)cause);
      } else if (this.aggressiveAssertionsEnabled()) {
         if (msg != null) {
            throw new IndexingException(msg, (Throwable)cause);
         } else {
            throw new IndexingException((Throwable)cause);
         }
      } else {
         if (msg == null) {
            msg = "<null msg>";
         }

         if (cause == null) {
            cause = new Exception();
         }

         this.logger.log(Level.WARNING, msg, (Throwable)cause);
      }
   }

   public void reportFailedAssertion(String msg) {
      if (this.aggressiveAssertionsEnabled()) {
         throw new IndexingException(msg, new Exception());
      }
   }

   public void addPaths(List p) throws IOException {
      Iterator var2 = p.iterator();

      while(var2.hasNext()) {
         String s = (String)var2.next();
         this.addPath(s);
      }

   }

   public void addPath(String p) throws IOException {
      this.path.add(Util.canonicalize(p));
   }

   public void setPath(List path) throws IOException {
      this.path = new ArrayList(path.size());
      this.addPaths(path);
   }

   public List getLoadPath() {
      List loadPath = new ArrayList();
      if (this.projDir != null) {
         loadPath.add(this.projDir);
      }

      loadPath.addAll(this.path);
      return loadPath;
   }

   public boolean isLibFile(String file) {
      if (file.startsWith(File.separator)) {
         return true;
      } else {
         if (this.path != null) {
            Iterator var2 = this.path.iterator();

            while(var2.hasNext()) {
               String p = (String)var2.next();
               if (file.startsWith(p)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public Map getBindings() {
      return this.allBindings;
   }

   public NBinding lookupQname(String qname) {
      return (NBinding)this.allBindings.get(qname);
   }

   public NType lookupQnameType(String qname) {
      NBinding b = this.lookupQname(qname);
      return b != null ? b.followType() : null;
   }

   NModuleType getCachedModule(String file) {
      return (NModuleType)this.moduleTable.lookupType(file);
   }

   public NModuleType getModuleForFile(String file) throws Exception {
      if (this.failedModules.contains(file)) {
         return null;
      } else {
         NModuleType m = this.getCachedModule(file);
         return m != null ? m : this.loadFile(file);
      }
   }

   public List getDiagnosticsForFile(String file) {
      List errs = (List)this.problems.get(file);
      return (List)(errs != null ? errs : new ArrayList());
   }

   public List generateOutline(String file) throws Exception {
      return (new Outliner()).generate(this, file);
   }

   public void putLocation(NNode node, NBinding b) {
      if (node != null) {
         this.putLocation(new Ref(node), b);
      }
   }

   public void putLocation(Ref ref, NBinding b) {
      if (ref != null) {
         List bindings = (List)this.locations.get(ref);
         if (bindings == null) {
            bindings = new ArrayList(1);
            this.locations.put(ref, bindings);
         }

         if (!((List)bindings).contains(b)) {
            ((List)bindings).add(b);
         }

         b.addRef(ref);
      }
   }

   public void updateLocation(Ref node, NBinding b) {
      if (node != null) {
         List bindings = (List)this.locations.get(node);
         if (bindings == null) {
            bindings = new ArrayList(1);
            this.locations.put(node, bindings);
         } else {
            Iterator var4 = ((List)bindings).iterator();

            while(var4.hasNext()) {
               NBinding oldb = (NBinding)var4.next();
               oldb.removeRef(node);
            }

            ((List)bindings).clear();
         }

         if (!((List)bindings).contains(b)) {
            ((List)bindings).add(b);
         }

         b.addRef(node);
      }
   }

   public void removeBinding(NBinding b) {
      this.allBindings.remove(b);
   }

   public NBinding putBinding(NBinding b) {
      if (b == null) {
         throw new IllegalArgumentException("null binding arg");
      } else {
         String qname = b.getQname();
         if (qname != null && qname.length() != 0) {
            NBinding existing = (NBinding)this.allBindings.get(qname);
            if (existing == b) {
               return b;
            } else if (existing != null) {
               this.duplicateBindingFailure(b, existing);
               return b.getKind() == NBinding.Kind.MODULE ? b : existing;
            } else {
               this.allBindings.put(qname, b);
               return b;
            }
         } else {
            throw new IllegalArgumentException("Null/empty qname: " + b);
         }
      }
   }

   private void duplicateBindingFailure(NBinding newb, NBinding oldb) {
   }

   public void putProblem(NNode loc, String msg) {
      String file;
      if (loc != null && (file = loc.getFile()) != null) {
         this.addFileErr(file, loc.start(), loc.end(), msg);
      }

   }

   public void putProblem(String file, int beg, int end, String msg) {
      if (file != null) {
         this.addFileErr(file, beg, end, msg);
      }

   }

   void addFileErr(String file, int beg, int end, String msg) {
      List msgs = this.getFileErrs(file, this.problems);
      msgs.add(new Diagnostic(file, Diagnostic.Type.ERROR, beg, end, msg));
   }

   List getParseErrs(String file) {
      return this.getFileErrs(file, this.parseErrs);
   }

   List getFileErrs(String file, Map map) {
      List msgs = (List)map.get(file);
      if (msgs == null) {
         msgs = new ArrayList();
         map.put(file, msgs);
      }

      return (List)msgs;
   }

   public NModuleType loadFile(String path) throws Exception {
      return this.loadFile(path, false);
   }

   public NModuleType loadString(String path, String contents) throws Exception {
      NModuleType module = this.getCachedModule(path);
      if (module != null) {
         this.finer("\nusing cached module " + path + " [succeeded]");
         return module;
      } else {
         return this.parseAndResolve(path, contents);
      }
   }

   public NModuleType loadFile(String path, boolean skipChain) throws Exception {
      File f = new File(path);
      if (f.isDirectory()) {
         this.finer("\n    loading init file from directory: " + path);
         f = Util.joinPath(path, "__init__.py");
         path = f.getAbsolutePath();
      }

      if (!f.canRead()) {
         this.finer("\nfile not not found or cannot be read: " + path);
         return null;
      } else {
         NModuleType module = this.getCachedModule(path);
         if (module != null) {
            this.finer("\nusing cached module " + path + " [succeeded]");
            return module;
         } else {
            if (!skipChain) {
               this.loadParentPackage(path);
            }

            try {
               return this.parseAndResolve(path);
            } catch (StackOverflowError var6) {
               this.handleException("Error loading " + path, var6);
               return null;
            }
         }
      }
   }

   private void loadParentPackage(String file) throws Exception {
      File f = new File(file);
      File parent = f.getParentFile();
      if (parent != null && !this.isInLoadPath(parent)) {
         if (parent != null && f.isFile() && "__init__.py".equals(f.getName())) {
            parent = parent.getParentFile();
         }

         if (parent != null && !this.isInLoadPath(parent)) {
            File initpy = Util.joinPath(parent, "__init__.py");
            if (initpy.isFile() && initpy.canRead()) {
               this.loadFile(initpy.getPath());
            }
         }
      }
   }

   private boolean isInLoadPath(File dir) {
      Iterator var2 = this.getLoadPath().iterator();

      String s;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         s = (String)var2.next();
      } while(!(new File(s)).equals(dir));

      return true;
   }

   private NModuleType parseAndResolve(String file) throws Exception {
      return this.parseAndResolve(file, (String)null);
   }

   private NModuleType parseAndResolve(String file, String contents) throws Exception {
      NModuleType nmt = (NModuleType)this.moduleTable.lookupType(file);
      if (nmt != null) {
         return nmt;
      } else {
         NModuleType mod = new NModuleType(Util.moduleNameFor(file), file, this.globaltable);
         this.moduleTable.put(file, new NUrl("file://" + file), mod, NBinding.Kind.MODULE);

         try {
            NModule ast = null;
            if (contents != null) {
               ast = this.getAstForFile(file, contents);
            } else {
               ast = this.getAstForFile(file);
            }

            if (ast == null) {
               return null;
            } else {
               this.finer("resolving: " + file);
               ast.resolve(this.globaltable);
               this.finer("[success]");
               ++this.loadedFiles;
               return mod;
            }
         } catch (OutOfMemoryError var6) {
            if (this.astCache != null) {
               this.astCache.clear();
            }

            System.gc();
            return null;
         }
      }
   }

   private AstCache getAstCache() throws Exception {
      if (this.astCache == null) {
         this.astCache = AstCache.get();
      }

      return this.astCache;
   }

   public NModule getAstForFile(String file) throws Exception {
      return this.getAstCache().getAST(file);
   }

   public NModule getAstForFile(String file, String contents) throws Exception {
      return this.getAstCache().getAST(file, contents);
   }

   public NModuleType getBuiltinModule(String qname) throws Exception {
      return this.builtins.get(qname);
   }

   public NModuleType loadModule(String modname) throws Exception {
      if (this.failedModules.contains(modname)) {
         return null;
      } else {
         NModuleType cached = this.getCachedModule(modname);
         if (cached != null) {
            this.finer("\nusing cached module " + modname);
            return cached;
         } else {
            NModuleType mt = this.getBuiltinModule(modname);
            if (mt != null) {
               return mt;
            } else {
               this.finer("looking for module " + modname);
               if (modname.endsWith(".py")) {
                  modname = modname.substring(0, modname.length() - 3);
               }

               String modpath = modname.replace('.', File.separatorChar);
               modpath = modpath.replaceFirst("(/python[23])/([0-9]/)", "$1.$2");
               List loadPath = this.getLoadPath();
               Iterator var6 = loadPath.iterator();

               NModuleType m;
               do {
                  String name;
                  while(true) {
                     if (!var6.hasNext()) {
                        this.finer("failed to find module " + modname + " in load path");
                        this.failedModules.add(modname);
                        return null;
                     }

                     String p = (String)var6.next();
                     String dirname = p + modpath;
                     String pyname = dirname + ".py";
                     String initname = Util.joinPath(dirname, "__init__.py").getAbsolutePath();
                     if (Util.isReadableFile(initname)) {
                        name = initname;
                        break;
                     }

                     if (Util.isReadableFile(pyname)) {
                        name = pyname;
                        break;
                     }
                  }

                  name = Util.canonicalize(name);
                  m = this.loadFile(name);
               } while(m == null);

               this.finer("load of module " + modname + "[succeeded]");
               return m;
            }
         }
      }
   }

   public void loadFileRecursive(String fullname) throws Exception {
      File file_or_dir = new File(fullname);
      if (file_or_dir.isDirectory()) {
         File[] var3 = file_or_dir.listFiles();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File file = var3[var5];
            this.loadFileRecursive(file.getAbsolutePath());
         }
      } else if (file_or_dir.getAbsolutePath().endsWith(".py")) {
         this.loadFile(file_or_dir.getAbsolutePath());
      }

   }

   public void ready() {
      this.fine("Checking for undeclared variables");
      Iterator var1 = this.locations.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry ent = (Map.Entry)var1.next();
         Ref ref = (Ref)ent.getKey();
         List bindings = (List)ent.getValue();
         this.convertCallToNew(ref, bindings);
         if (this.countDefs(bindings) != 0) {
            ++this.nloc;
         }
      }

      this.nprob = this.problems.size();
      this.nparsing = this.parseErrs.size();
      Set removals = new HashSet();
      Iterator var6 = this.allBindings.entrySet().iterator();

      while(true) {
         Map.Entry e;
         NBinding nb;
         do {
            if (!var6.hasNext()) {
               var6 = removals.iterator();

               while(var6.hasNext()) {
                  String s = (String)var6.next();
                  this.allBindings.remove(s);
               }

               this.locations.clear();
               return;
            }

            e = (Map.Entry)var6.next();
            nb = (NBinding)e.getValue();
         } while(!nb.isProvisional() && nb.getNumDefs() != 0);

         removals.add(e.getKey());
      }
   }

   private void convertCallToNew(Ref ref, List bindings) {
      if (!ref.isRef()) {
         if (!bindings.isEmpty()) {
            NBinding nb = (NBinding)bindings.get(0);
            NType t = nb.followType();
            if (t.isUnionType()) {
               t = t.asUnionType().firstKnownNonNullAlternate();
               if (t == null) {
                  return;
               }
            }

            NType tt = t.follow();
            if (!tt.isUnknownType() && !tt.isFuncType()) {
               ref.markAsNew();
            }

         }
      }
   }

   public void clearAstCache() {
      if (this.astCache != null) {
         this.astCache.clear();
      }

   }

   public void clearModuleTable() {
      this.moduleTable.clear();
      this.moduleTable = new Scope((Scope)null, Scope.Type.GLOBAL);
      this.clearAstCache();
   }

   private int countDefs(List bindings) {
      int count = 0;

      NBinding b;
      for(Iterator var3 = bindings.iterator(); var3.hasNext(); count += b.getNumDefs()) {
         b = (NBinding)var3.next();
      }

      return count;
   }

   private String printBindings() {
      StringBuilder sb = new StringBuilder();
      Set sorter = new TreeSet();
      sorter.addAll(this.allBindings.keySet());
      Iterator var3 = sorter.iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         NBinding b = (NBinding)this.allBindings.get(key);
         sb.append(b.toString()).append("\n");
      }

      return sb.toString();
   }

   public void recordUnresolvedModule(String qname, String file) {
      Set importers = (Set)this.unresolvedModules.get(qname);
      if (importers == null) {
         importers = new TreeSet();
         this.unresolvedModules.put(qname, importers);
      }

      ((Set)importers).add(file);
   }

   public String getStatusReport() {
      int total = this.nloc + this.nunbound + this.nunknown;
      if (total == 0) {
         int total = true;
      }

      StringBuilder sb = new StringBuilder();
      sb.append("Summary: \n").append("- modules loaded: ").append(this.loadedFiles).append("\n- unresolved modules: ").append(this.unresolvedModules.size()).append("\n");

      for(Iterator var3 = this.unresolvedModules.keySet().iterator(); var3.hasNext(); sb.append("\n")) {
         String s = (String)var3.next();
         sb.append(s).append(": ");
         Set importers = (Set)this.unresolvedModules.get(s);
         if (importers.size() > 5) {
            sb.append((String)importers.iterator().next());
            sb.append(" and ");
            sb.append(importers.size());
            sb.append(" more");
         } else {
            String files = importers.toString();
            sb.append(files.substring(1, files.length() - 1));
         }
      }

      sb.append("\nsemantics problems: ").append(this.nprob);
      sb.append("\nparsing problems: ").append(this.nparsing);
      return sb.toString();
   }

   private String percent(int num, int total) {
      double pct = (double)num * 1.0 / (double)total;
      pct = (double)Math.round(pct * 10000.0) / 100.0;
      return num + "/" + total + " = " + pct + "%";
   }

   public int numFilesLoaded() {
      return this.loadedFiles;
   }

   public List getLoadedFiles() {
      List files = new ArrayList();
      Iterator var2 = this.moduleTable.keySet().iterator();

      while(var2.hasNext()) {
         String file = (String)var2.next();
         if (file.startsWith(File.separator)) {
            files.add(file);
         }
      }

      return files;
   }

   public void log(Level level, String msg) {
      if (this.logger.isLoggable(level)) {
         this.logger.log(level, msg);
      }

   }

   public void severe(String msg) {
      this.log(Level.SEVERE, msg);
   }

   public void warn(String msg) {
      this.log(Level.WARNING, msg);
   }

   public void info(String msg) {
      this.log(Level.INFO, msg);
   }

   public void fine(String msg) {
      this.log(Level.FINE, msg);
   }

   public void finer(String msg) {
      this.log(Level.FINER, msg);
   }

   public void release() {
      this.moduleTable = this.globaltable = null;
      this.clearAstCache();
      this.astCache = null;
      this.locations = null;
      this.problems.clear();
      this.problems = null;
      this.parseErrs.clear();
      this.parseErrs = null;
      this.path.clear();
      this.path = null;
      this.failedModules.clear();
      this.failedModules = null;
      this.unresolvedModules.clear();
      this.unresolvedModules = null;
      this.builtins = null;
      this.allBindings.clear();
      this.allBindings = null;
      idx = null;
   }

   public String toString() {
      return "<Indexer:locs=" + this.locations.size() + ":unbound=" + this.nunbound + ":probs=" + this.problems.size() + ":files=" + this.loadedFiles + ">";
   }
}
