package org.python.indexer.demos;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.python.indexer.Def;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Ref;
import org.python.indexer.StyleRun;
import org.python.indexer.Util;
import org.python.indexer.types.NModuleType;

class Linker {
   private static final Pattern CONSTANT = Pattern.compile("[A-Z_][A-Z0-9_]*");
   private Map fileStyles = new HashMap();
   private File outDir;
   private String rootPath;

   public Linker(String root, File outdir) {
      this.rootPath = root;
      this.outDir = outdir;
   }

   public void findLinks(Indexer indexer) {
      Iterator var2 = indexer.getBindings().values().iterator();

      while(var2.hasNext()) {
         NBinding nb = (NBinding)var2.next();
         this.addSemanticStyles(nb);
         this.processDefs(nb);
         this.processRefs(nb);
      }

   }

   public List getStyles(String path) {
      return this.stylesForFile(path);
   }

   private List stylesForFile(String path) {
      List styles = (List)this.fileStyles.get(path);
      if (styles == null) {
         styles = new ArrayList();
         this.fileStyles.put(path, styles);
      }

      return (List)styles;
   }

   private void addFileStyle(String path, StyleRun style) {
      this.stylesForFile(path).add(style);
   }

   private void addSemanticStyles(NBinding nb) {
      Def def = nb.getSignatureNode();
      if (def != null && def.isName()) {
         boolean isConst = CONSTANT.matcher(def.getName()).matches();
         switch (nb.getKind()) {
            case SCOPE:
               if (isConst) {
                  this.addSemanticStyle(def, StyleRun.Type.CONSTANT);
               }
               break;
            case VARIABLE:
               this.addSemanticStyle(def, isConst ? StyleRun.Type.CONSTANT : StyleRun.Type.IDENTIFIER);
               break;
            case PARAMETER:
               this.addSemanticStyle(def, StyleRun.Type.PARAMETER);
               break;
            case CLASS:
               this.addSemanticStyle(def, StyleRun.Type.TYPE_NAME);
         }

      }
   }

   private void addSemanticStyle(Def def, StyleRun.Type type) {
      String path = def.getFile();
      if (path != null) {
         this.addFileStyle(path, new StyleRun(type, def.start(), def.length()));
      }

   }

   private void processDefs(NBinding nb) {
      Def def = nb.getSignatureNode();
      if (def != null && !def.isURL()) {
         StyleRun style = new StyleRun(StyleRun.Type.ANCHOR, def.start(), def.length());
         style.message = nb.getQname();
         style.url = nb.getQname();
         this.addFileStyle(def.getFile(), style);
      }
   }

   private void processRefs(NBinding nb) {
      if (nb.hasRefs()) {
         Iterator var2 = nb.getRefs().iterator();

         while(var2.hasNext()) {
            Ref ref = (Ref)var2.next();
            this.processRef(ref, nb);
         }
      }

   }

   void processRef(Ref ref, NBinding nb) {
      String path = ref.getFile();
      StyleRun link = new StyleRun(StyleRun.Type.LINK, ref.start(), ref.length());
      link.message = nb.getQname();
      link.url = this.toURL(nb, path);
      if (link.url != null) {
         this.addFileStyle(path, link);
      }

   }

   private String toURL(NBinding nb, String path) {
      Def def = nb.getSignatureNode();
      if (def == null) {
         return null;
      } else if (nb.isBuiltin()) {
         return def.getURL();
      } else if (def.isModule()) {
         return this.toModuleUrl(nb);
      } else {
         String anchor = "#" + nb.getQname();
         if (nb.getFirstFile().equals(path)) {
            return anchor;
         } else {
            String destPath = def.getFile();

            try {
               String relpath = destPath.substring(this.rootPath.length());
               return Util.joinPath(this.outDir.getAbsolutePath(), relpath) + ".html" + anchor;
            } catch (Exception var7) {
               System.err.println("path problem:  dest=" + destPath + ", root=" + this.rootPath + ": " + var7);
               return null;
            }
         }
      }
   }

   private String toModuleUrl(NBinding nb) {
      NModuleType mtype = nb.getType().follow().asModuleType();
      String path = mtype.getFile();
      if (!path.startsWith(this.rootPath)) {
         return "file://" + path;
      } else {
         String relpath = path.substring(this.rootPath.length());
         return Util.joinPath(this.outDir.getAbsolutePath(), relpath) + ".html";
      }
   }
}
