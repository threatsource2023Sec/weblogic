package org.python.indexer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.python.indexer.ast.NNode;
import org.python.indexer.types.NModuleType;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NBinding implements Comparable {
   private static final int PROVISIONAL = 1;
   private static final int STATIC = 2;
   private static final int SYNTHETIC = 4;
   private static final int READONLY = 8;
   private static final int DEPRECATED = 16;
   private static final int BUILTIN = 32;
   private static final int DEF_SET_INITIAL_CAPACITY = 1;
   private static final int REF_SET_INITIAL_CAPACITY = 8;
   private String name;
   private String qname;
   private NType type;
   Kind kind;
   int modifiers;
   private List defs;
   private Set refs;

   public NBinding(String id, NNode node, NType type, Kind kind) {
      this(id, node != null ? new Def(node) : null, type, kind);
   }

   public NBinding(String id, Def def, NType type, Kind kind) {
      if (id == null) {
         throw new IllegalArgumentException("'id' param cannot be null");
      } else {
         this.qname = this.name = id;
         this.defs = new ArrayList(1);
         this.addDef(def);
         this.type = (NType)(type == null ? new NUnknownType() : type);
         this.kind = kind == null ? NBinding.Kind.SCOPE : kind;
      }
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void setQname(String qname) {
      this.qname = qname;
   }

   public String getQname() {
      return this.qname;
   }

   public void addDef(NNode node) {
      if (node != null) {
         this.addDef(new Def(node));
      }

   }

   public void addDefs(Collection nodes) {
      Iterator var2 = nodes.iterator();

      while(var2.hasNext()) {
         NNode n = (NNode)var2.next();
         this.addDef(n);
      }

   }

   public void addDef(Def def) {
      if (def != null) {
         List defs = this.getDefs();
         if (!defs.contains(def)) {
            defs.add(def);
            def.setBinding(this);
            if (def.isURL()) {
               this.markBuiltin();
            }

         }
      }
   }

   public void addRef(NNode node) {
      this.addRef(new Ref(node));
   }

   public void addRef(Ref ref) {
      this.getRefs().add(ref);
   }

   public void removeRef(Ref node) {
      this.getRefs().remove(node);
   }

   public Def getSignatureNode() {
      return this.getDefs().isEmpty() ? null : (Def)this.getDefs().get(0);
   }

   public void setType(NType type) {
      this.type = type;
   }

   public NType getType() {
      return this.type;
   }

   public NType followType() {
      return NUnknownType.follow(this.type);
   }

   public void setKind(Kind kind) {
      this.kind = kind;
   }

   public Kind getKind() {
      return this.kind;
   }

   public void markStatic() {
      this.modifiers |= 2;
   }

   public boolean isStatic() {
      return (this.modifiers & 2) != 0;
   }

   public void markSynthetic() {
      this.modifiers |= 4;
   }

   public boolean isSynthetic() {
      return (this.modifiers & 4) != 0;
   }

   public void markReadOnly() {
      this.modifiers |= 8;
   }

   public boolean isReadOnly() {
      return (this.modifiers & 8) != 0;
   }

   public boolean isDeprecated() {
      return (this.modifiers & 16) != 0;
   }

   public void markDeprecated() {
      this.modifiers |= 16;
   }

   public boolean isBuiltin() {
      return (this.modifiers & 32) != 0;
   }

   public void markBuiltin() {
      this.modifiers |= 32;
   }

   public void setProvisional(boolean isProvisional) {
      if (isProvisional) {
         this.modifiers |= 1;
      } else {
         this.modifiers &= -2;
      }

   }

   public boolean isProvisional() {
      return (this.modifiers & 1) != 0;
   }

   public int compareTo(Object o) {
      return this.getSignatureNode().start() - ((NBinding)o).getSignatureNode().start();
   }

   public List getDefs() {
      if (this.defs == null) {
         this.defs = new ArrayList(1);
      }

      return this.defs;
   }

   public int getNumDefs() {
      return this.defs == null ? 0 : this.defs.size();
   }

   public boolean hasRefs() {
      return this.refs == null ? false : !this.refs.isEmpty();
   }

   public int getNumRefs() {
      return this.refs == null ? 0 : this.refs.size();
   }

   public Set getRefs() {
      if (this.refs == null) {
         this.refs = new LinkedHashSet(8);
      }

      return this.refs;
   }

   public String getFirstFile() {
      NType bt = this.getType();
      if (bt instanceof NModuleType) {
         String file = bt.asModuleType().getFile();
         return file != null ? file : "<built-in module>";
      } else if (this.defs != null) {
         Iterator var2 = this.defs.iterator();

         String file;
         do {
            if (!var2.hasNext()) {
               return "<built-in binding>";
            }

            Def def = (Def)var2.next();
            file = def.getFile();
         } while(file == null);

         return file;
      } else {
         return "<unknown source>";
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("<Binding:").append(this.qname);
      sb.append(":type=").append(this.type);
      sb.append(":kind=").append(this.kind);
      sb.append(":defs=").append(this.defs);
      sb.append(":refs=");
      if (this.getRefs().size() > 10) {
         sb.append("[");
         sb.append(this.refs.iterator().next());
         sb.append(", ...(");
         sb.append(this.refs.size() - 1);
         sb.append(" more)]");
      } else {
         sb.append(this.refs);
      }

      sb.append(">");
      return sb.toString();
   }

   public static enum Kind {
      ATTRIBUTE,
      CLASS,
      CONSTRUCTOR,
      FUNCTION,
      METHOD,
      MODULE,
      PARAMETER,
      SCOPE,
      VARIABLE;
   }
}
