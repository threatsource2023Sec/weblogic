package org.python.indexer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.python.indexer.types.NModuleType;
import org.python.indexer.types.NType;

public class Outliner {
   public List generate(Indexer idx, String abspath) throws Exception {
      NModuleType mt = idx.getModuleForFile(abspath);
      return (List)(mt == null ? new ArrayList() : this.generate(mt.getTable(), abspath));
   }

   public List generate(Scope scope, String path) {
      List result = new ArrayList();
      Set entries = new TreeSet();
      Iterator var5 = scope.values().iterator();

      NBinding nb;
      while(var5.hasNext()) {
         nb = (NBinding)var5.next();
         if (!nb.isSynthetic() && !nb.isBuiltin() && !nb.getDefs().isEmpty() && path.equals(nb.getSignatureNode().getFile())) {
            entries.add(nb);
         }
      }

      Object kid;
      for(var5 = entries.iterator(); var5.hasNext(); result.add(kid)) {
         nb = (NBinding)var5.next();
         Def signode = nb.getSignatureNode();
         List kids = null;
         if (nb.getKind() == NBinding.Kind.CLASS) {
            NType realType = nb.followType();
            if (realType.isUnionType()) {
               Iterator var10 = realType.asUnionType().getTypes().iterator();

               while(var10.hasNext()) {
                  NType t = (NType)var10.next();
                  if (t.isClassType()) {
                     realType = t;
                     break;
                  }
               }
            }

            kids = this.generate(realType.getTable(), path);
         }

         kid = kids != null ? new Branch() : new Leaf();
         ((Entry)kid).setOffset(signode.start());
         ((Entry)kid).setQname(nb.getQname());
         ((Entry)kid).setKind(nb.getKind());
         if (kids != null) {
            ((Entry)kid).setChildren(kids);
         }
      }

      return result;
   }

   public static class Leaf extends Entry {
      public boolean isLeaf() {
         return true;
      }

      public boolean isBranch() {
         return false;
      }

      public Leaf() {
      }

      public Leaf(String qname, int start, NBinding.Kind kind) {
         super(qname, start, kind);
      }

      public boolean hasChildren() {
         return false;
      }

      public List getChildren() {
         return new ArrayList();
      }

      public void setChildren(List children) {
         throw new UnsupportedOperationException("Leaf nodes cannot have children.");
      }
   }

   public static class Branch extends Entry {
      private List children = new ArrayList();

      public Branch() {
      }

      public Branch(String qname, int start, NBinding.Kind kind) {
         super(qname, start, kind);
      }

      public boolean isLeaf() {
         return false;
      }

      public boolean isBranch() {
         return true;
      }

      public boolean hasChildren() {
         return this.children != null && !this.children.isEmpty();
      }

      public List getChildren() {
         return this.children;
      }

      public void setChildren(List children) {
         this.children = children;
      }
   }

   public abstract static class Entry {
      protected String qname;
      protected int offset;
      protected NBinding.Kind kind;

      public Entry() {
      }

      public Entry(String qname, int offset, NBinding.Kind kind) {
         this.qname = qname;
         this.offset = offset;
         this.kind = kind;
      }

      public abstract boolean isLeaf();

      public Leaf asLeaf() {
         return (Leaf)this;
      }

      public abstract boolean isBranch();

      public Branch asBranch() {
         return (Branch)this;
      }

      public abstract boolean hasChildren();

      public abstract List getChildren();

      public abstract void setChildren(List var1);

      public String getQname() {
         return this.qname;
      }

      public void setQname(String qname) {
         if (qname == null) {
            throw new IllegalArgumentException("qname param cannot be null");
         } else {
            this.qname = qname;
         }
      }

      public int getOffset() {
         return this.offset;
      }

      public void setOffset(int offset) {
         this.offset = offset;
      }

      public NBinding.Kind getKind() {
         return this.kind;
      }

      public void setKind(NBinding.Kind kind) {
         if (kind == null) {
            throw new IllegalArgumentException("kind param cannot be null");
         } else {
            this.kind = kind;
         }
      }

      public String getName() {
         String[] parts = this.qname.split("[.&@%]");
         return parts[parts.length - 1];
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         this.toString(sb, 0);
         return sb.toString().trim();
      }

      public void toString(StringBuilder sb, int depth) {
         for(int i = 0; i < depth; ++i) {
            sb.append("  ");
         }

         sb.append(this.getKind());
         sb.append(" ");
         sb.append(this.getName());
         sb.append("\n");
         if (this.hasChildren()) {
            Iterator var5 = this.getChildren().iterator();

            while(var5.hasNext()) {
               Entry e = (Entry)var5.next();
               e.toString(sb, depth + 1);
            }
         }

      }
   }
}
