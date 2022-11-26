package org.python.indexer.ast;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.indexer.Def;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Scope;
import org.python.indexer.Util;
import org.python.indexer.types.NModuleType;
import org.python.indexer.types.NType;

public class NModule extends NNode {
   static final long serialVersionUID = -7737089963380450802L;
   public String name;
   public NBody body;
   private String file;
   private String md5;

   public NModule() {
   }

   public NModule(String name) {
      this.name = name;
   }

   public NModule(NBlock body, int start, int end) {
      super(start, end);
      this.body = new NBody(body);
      this.addChildren(new NNode[]{this.body});
   }

   public void setFile(String file) throws Exception {
      this.file = file;
      this.name = Util.moduleNameFor(file);
      this.md5 = Util.getMD5(new File(file));
   }

   public void setFile(File path) throws Exception {
      this.file = path.getCanonicalPath();
      this.name = Util.moduleNameFor(this.file);
      this.md5 = Util.getMD5(path);
   }

   public void setFileAndMD5(String path, String md5) throws Exception {
      this.file = path;
      this.name = Util.moduleNameFor(this.file);
      this.md5 = md5;
   }

   public String getFile() {
      return this.file;
   }

   public String getMD5() {
      return this.md5;
   }

   public NType resolve(Scope s) throws Exception {
      NBinding mb = Indexer.idx.moduleTable.lookupLocal(this.file);
      if (mb == null) {
         Indexer.idx.reportFailedAssertion("No module for " + this.name + ": " + this.file);
         this.setType(new NModuleType(this.name, this.file, s));
      } else {
         this.setType(mb.getType());
      }

      resolveExpr(this.body, this.getTable());
      this.resolveExportedNames();
      return this.getType();
   }

   private void resolveExportedNames() throws Exception {
      NModuleType mtype = null;
      NType thisType = this.getType();
      if (thisType.isModuleType()) {
         mtype = thisType.asModuleType();
      } else if (thisType.isUnionType()) {
         Iterator var3 = thisType.asUnionType().getTypes().iterator();

         while(var3.hasNext()) {
            NType u = (NType)var3.next();
            if (u.isModuleType()) {
               mtype = u.asModuleType();
               break;
            }
         }
      }

      if (mtype == null) {
         Indexer.idx.reportFailedAssertion("Found non-module type for " + this + " in " + this.getFile() + ": " + thisType);
      } else {
         Scope table = mtype.getTable();
         Iterator var9 = this.getExportedNameNodes().iterator();

         while(var9.hasNext()) {
            NStr nstr = (NStr)var9.next();
            String name = nstr.n.toString();
            NBinding b = table.lookupLocal(name);
            if (b != null) {
               Indexer.idx.putLocation((NNode)nstr, b);
            }
         }

      }
   }

   public List getExportedNames() throws Exception {
      List exports = new ArrayList();
      if (!this.getType().isModuleType()) {
         return exports;
      } else {
         Iterator var2 = this.getExportedNameNodes().iterator();

         while(var2.hasNext()) {
            NStr nstr = (NStr)var2.next();
            exports.add(nstr.n.toString());
         }

         return exports;
      }
   }

   public List getExportedNameNodes() throws Exception {
      List exports = new ArrayList();
      if (!this.getType().isModuleType()) {
         return exports;
      } else {
         NBinding all = this.getTable().lookupLocal("__all__");
         if (all == null) {
            return exports;
         } else {
            Def def = all.getSignatureNode();
            if (def == null) {
               return exports;
            } else {
               NNode __all__ = this.getDeepestNodeAtOffset(def.start());
               if (!(__all__ instanceof NName)) {
                  return exports;
               } else {
                  NNode assign = __all__.getParent();
                  if (!(assign instanceof NAssign)) {
                     return exports;
                  } else {
                     NNode rvalue = ((NAssign)assign).rvalue;
                     if (!(rvalue instanceof NList)) {
                        return exports;
                     } else {
                        Iterator var7 = ((NList)rvalue).elts.iterator();

                        while(var7.hasNext()) {
                           NNode elt = (NNode)var7.next();
                           if (elt instanceof NStr) {
                              NStr nstr = (NStr)elt;
                              if (nstr.n != null) {
                                 exports.add(nstr);
                              }
                           }
                        }

                        return exports;
                     }
                  }
               }
            }
         }
      }
   }

   public String toLongString() {
      return "<Module:" + this.body + ">";
   }

   public String toString() {
      return "<Module:" + this.getFile() + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.body, v);
      }

   }
}
