package org.python.indexer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.python.indexer.ast.NName;
import org.python.indexer.ast.NNode;
import org.python.indexer.ast.NUrl;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnionType;

public class Scope {
   private static Set looked = new HashSet();
   private Map table;
   private Scope parent;
   private List supers;
   private Set globalNames;
   private Type scopeType;
   private String path = "";
   private int lambdaCounter = 0;
   private boolean isBindingPhase = false;

   public Scope(Scope parent, Type type) {
      if (type == null) {
         throw new IllegalArgumentException("'type' param cannot be null");
      } else {
         this.setParent(parent);
         this.setScopeType(type);
      }
   }

   public void setTable(Map table) {
      this.table = table;
   }

   public Map getTable() {
      if (this.table != null) {
         return Collections.unmodifiableMap(this.table);
      } else {
         Map map = Collections.emptyMap();
         return map;
      }
   }

   public void setParent(Scope parent) {
      this.parent = parent;
   }

   public Scope getParent() {
      return this.parent;
   }

   public void addSuper(Scope sup) {
      if (this.supers == null) {
         this.supers = new ArrayList();
      }

      this.supers.add(sup);
   }

   public void setSupers(List supers) {
      this.supers = supers;
   }

   public List getSupers() {
      if (this.supers != null) {
         return Collections.unmodifiableList(this.supers);
      } else {
         List list = Collections.emptyList();
         return list;
      }
   }

   public void setScopeType(Type type) {
      this.scopeType = type;
   }

   public Type getScopeType() {
      return this.scopeType;
   }

   public boolean isFunctionScope() {
      return this.scopeType == Scope.Type.FUNCTION;
   }

   public void addGlobalName(String name) {
      if (name != null) {
         if (this.globalNames == null) {
            this.globalNames = new HashSet();
         }

         this.globalNames.add(name);
      }
   }

   public boolean isGlobalName(String name) {
      if (this.globalNames != null) {
         return this.globalNames.contains(name);
      } else {
         return this.parent == null ? false : this.parent.isGlobalName(name);
      }
   }

   public void put(String id, NBinding b) {
      this.putBinding(id, b);
   }

   public NBinding put(String id, NNode loc, NType type, NBinding.Kind kind) {
      if (type == null) {
         throw new IllegalArgumentException("Null type: id=" + id + ", loc=" + loc);
      } else {
         NBinding b = this.lookupScope(id);
         return this.insertOrUpdate(b, id, loc, type, kind);
      }
   }

   public NBinding putAttr(String id, NNode loc, NType type, NBinding.Kind kind) {
      if (type == null) {
         throw new IllegalArgumentException("Null type: id=" + id + ", loc=" + loc);
      } else if ("".equals(this.path)) {
         Indexer.idx.reportFailedAssertion("Attempting to set attr '" + id + "' at location " + loc + (loc != null ? loc.getFile() : "") + " in scope with no path (qname) set: " + this.toShortString());
         return null;
      } else {
         NBinding b = this.lookupAttr(id);
         return this.insertOrUpdate(b, id, loc, type, kind);
      }
   }

   private NBinding insertOrUpdate(NBinding b, String id, NNode loc, NType t, NBinding.Kind k) {
      if (b == null) {
         b = this.insertBinding(new NBinding(id, loc, t, k));
      } else {
         this.updateType(b, loc, t, k);
      }

      return b;
   }

   public NBinding update(String id, NNode loc, NType type, NBinding.Kind kind) {
      if (type == null) {
         throw new IllegalArgumentException("Null type: id=" + id + ", loc=" + loc);
      } else {
         return this.update(id, new Def(loc), type, kind);
      }
   }

   public NBinding update(String id, Def loc, NType type, NBinding.Kind kind) {
      if (type == null) {
         throw new IllegalArgumentException("Null type: id=" + id + ", loc=" + loc);
      } else {
         NBinding b = this.lookupScope(id);
         if (b == null) {
            return this.insertBinding(new NBinding(id, loc, type, kind));
         } else {
            b.getDefs().clear();
            b.addDef(loc);
            b.setType(type);
            if (b.getType().isUnknownType()) {
               b.setKind(kind);
            }

            return b;
         }
      }
   }

   private NBinding insertBinding(NBinding b) {
      switch (b.getKind()) {
         case MODULE:
            b.setQname(b.getType().getTable().path);
            break;
         case PARAMETER:
            b.setQname(this.extendPathForParam(b.getName()));
            break;
         default:
            b.setQname(this.extendPath(b.getName()));
      }

      b = Indexer.idx.putBinding(b);
      this.putBinding(b.getName(), b);
      return b;
   }

   private void putBinding(String id, NBinding b) {
      this.ensureTable();
      this.table.put(id, b);
   }

   private void updateType(NBinding b, NNode loc, NType type, NBinding.Kind kind) {
      NType curType = b.followType();
      if (!this.isNewType(curType, type)) {
         if (loc != null && !(loc instanceof NUrl) && !b.getDefs().contains(loc)) {
            Indexer.idx.putLocation(loc, b);
         }

      } else {
         if (loc != null && !b.getRefs().contains(loc)) {
            b.addDef(loc);
            b.setProvisional(false);
         }

         NType btype = b.getType();
         NType t1;
         NType t2;
         if (btype.isUnknownType() && !btype.getTable().isEmpty()) {
            t1 = type;
            t2 = btype;
         } else {
            t1 = btype;
            t2 = type;
         }

         NType newType = NUnionType.union(t1, t2);
         b.setType(newType);
         if (curType.isUnknownType()) {
            b.setKind(kind);
         }

         this.retargetReferences(b, curType);
      }
   }

   private void retargetReferences(NBinding b, NType curType) {
      Scope newScope = b.followType().getTable();
      Iterator var4 = curType.getTable().entrySet().iterator();

      while(true) {
         NBinding oldBinding;
         NBinding newBinding;
         do {
            String attr;
            do {
               if (!var4.hasNext()) {
                  return;
               }

               Map.Entry e = (Map.Entry)var4.next();
               attr = (String)e.getKey();
               oldBinding = (NBinding)e.getValue();
            } while(!oldBinding.isProvisional());

            Indexer.idx.removeBinding(oldBinding);
            newBinding = newScope.lookupAttr(attr);
         } while(newBinding == null);

         List refs = new ArrayList();
         refs.addAll(oldBinding.getRefs());
         Iterator var10 = refs.iterator();

         while(var10.hasNext()) {
            Ref ref = (Ref)var10.next();
            Indexer.idx.updateLocation(ref, newBinding);
         }
      }
   }

   private boolean isNewType(NType curType, NType type) {
      if (this.isBindingPhase) {
         return false;
      } else if (curType.isUnionType()) {
         return !curType.asUnionType().contains(type);
      } else {
         return curType != type;
      }
   }

   public void remove(String id) {
      if (this.table != null) {
         this.table.remove(id);
      }

   }

   public Scope copy(Type tableType) {
      Scope ret = new Scope((Scope)null, tableType);
      if (this.table != null) {
         ret.ensureTable();
         ret.table.putAll(this.table);
      }

      return ret;
   }

   public void setPath(String path) {
      if (path == null) {
         throw new IllegalArgumentException("'path' param cannot be null");
      } else {
         this.path = path;
      }
   }

   public String getPath() {
      return this.path;
   }

   public void setPath(String a, String b) {
      NBinding b1 = this.lookup(a);
      NBinding b2 = this.lookup(b);
      if (b1 != null && b2 != null) {
         b1.setQname(b2.getQname());
      }

   }

   public NBinding lookup(String name) {
      NBinding b = this.getModuleBindingIfGlobal(name);
      if (b != null) {
         return b;
      } else {
         if (this.table != null) {
            NBinding ent = (NBinding)this.table.get(name);
            if (ent != null) {
               return ent;
            }
         }

         return this.getParent() == null ? null : this.getParent().lookup(name);
      }
   }

   public NBinding lookup(NNode n) {
      return n instanceof NName ? this.lookup(((NName)n).id) : null;
   }

   public NBinding lookupLocal(String name) {
      NBinding b = this.getModuleBindingIfGlobal(name);
      if (b != null) {
         return b;
      } else {
         return this.table == null ? null : (NBinding)this.table.get(name);
      }
   }

   public NBinding lookupAttr(String name, boolean supersOnly) {
      if (looked.contains(this)) {
         return null;
      } else {
         NBinding b;
         if (this.table != null && !supersOnly) {
            b = (NBinding)this.table.get(name);
            if (b != null) {
               return b;
            }
         }

         if (this.supers != null && !this.supers.isEmpty()) {
            looked.add(this);

            NBinding var6;
            try {
               Iterator var10 = this.supers.iterator();

               NBinding b;
               do {
                  if (!var10.hasNext()) {
                     b = null;
                     return b;
                  }

                  Scope p = (Scope)var10.next();
                  b = p.lookupAttr(name);
               } while(b == null);

               var6 = b;
            } finally {
               looked.remove(this);
            }

            return var6;
         } else {
            return null;
         }
      }
   }

   public NBinding lookupAttr(String name) {
      return this.lookupAttr(name, false);
   }

   public NType lookupType(String name) {
      return this.lookupType(name, false);
   }

   public NType lookupType(String name, boolean localOnly) {
      NBinding b = localOnly ? this.lookupLocal(name) : this.lookup(name);
      if (b == null) {
         return null;
      } else {
         NType ret = b.followType();
         if (this == Indexer.idx.moduleTable) {
            if (ret.isModuleType()) {
               return ret;
            } else {
               if (ret.isUnionType()) {
                  Iterator var5 = ret.asUnionType().getTypes().iterator();

                  while(var5.hasNext()) {
                     NType t = (NType)var5.next();
                     NType realType = t.follow();
                     if (realType.isModuleType()) {
                        return realType;
                     }
                  }
               }

               Indexer.idx.warn("Found non-module type in module table: " + b);
               return null;
            }
         } else {
            return ret;
         }
      }
   }

   public NType lookupTypeAttr(String name) {
      NBinding b = this.lookupAttr(name);
      return b != null ? b.followType() : null;
   }

   public NBinding lookupBounded(String name, Type typebound) {
      if (this.scopeType == typebound) {
         return this.table == null ? null : (NBinding)this.table.get(name);
      } else {
         return this.getParent() == null ? null : this.getParent().lookupBounded(name, typebound);
      }
   }

   public boolean isScope() {
      switch (this.scopeType) {
         case CLASS:
         case INSTANCE:
         case FUNCTION:
         case MODULE:
         case GLOBAL:
            return true;
         default:
            return false;
      }
   }

   public Scope getScopeSymtab() {
      if (this.isScope()) {
         return this;
      } else if (this.getParent() == null) {
         Indexer.idx.reportFailedAssertion("No binding scope found for " + this.toShortString());
         return this;
      } else {
         return this.getParent().getScopeSymtab();
      }
   }

   public NBinding lookupScope(String name) {
      NBinding b = this.getModuleBindingIfGlobal(name);
      if (b != null) {
         return b;
      } else {
         Scope st = this.getScopeSymtab();
         return st != null ? st.lookupLocal(name) : null;
      }
   }

   public Scope getSymtabOfType(Type type) {
      if (this.scopeType == type) {
         return this;
      } else {
         return this.parent == null ? null : this.parent.getSymtabOfType(type);
      }
   }

   public Scope getGlobalTable() {
      Scope result = this.getSymtabOfType(Scope.Type.MODULE);
      if (result == null) {
         Indexer.idx.reportFailedAssertion("No module table found for " + this);
         result = this;
      }

      return result;
   }

   public Scope getEnclosingLexicalScope() {
      if (this.scopeType != Scope.Type.FUNCTION && this.scopeType != Scope.Type.MODULE) {
         if (this.parent == null) {
            Indexer.idx.reportFailedAssertion("No lexical scope found for " + this);
            return this;
         } else {
            return this.parent.getEnclosingLexicalScope();
         }
      } else {
         return this;
      }
   }

   private NBinding getModuleBindingIfGlobal(String name) {
      if (this.isGlobalName(name)) {
         Scope module = this.getGlobalTable();
         if (module != null && module != this) {
            return module.lookupLocal(name);
         }
      }

      return null;
   }

   public boolean isNameBindingPhase() {
      return this.isBindingPhase;
   }

   public void setNameBindingPhase(boolean isBindingPhase) {
      this.isBindingPhase = isBindingPhase;
   }

   public void merge(Scope other) {
      this.ensureTable();
      this.table.putAll(other.table);
   }

   public Set keySet() {
      if (this.table != null) {
         return this.table.keySet();
      } else {
         Set result = Collections.emptySet();
         return result;
      }
   }

   public Collection values() {
      if (this.table != null) {
         return this.table.values();
      } else {
         Collection result = Collections.emptySet();
         return result;
      }
   }

   public Set entrySet() {
      if (this.table != null) {
         return this.table.entrySet();
      } else {
         Set result = Collections.emptySet();
         return result;
      }
   }

   public boolean isEmpty() {
      return this.table == null ? true : this.table.isEmpty();
   }

   public void clear() {
      if (this.table != null) {
         this.table.clear();
         this.table = null;
      }

      this.parent = null;
      if (this.supers != null) {
         this.supers.clear();
         this.supers = null;
      }

      if (this.globalNames != null) {
         this.globalNames.clear();
         this.globalNames = null;
      }

   }

   public String newLambdaName() {
      return "lambda%" + ++this.lambdaCounter;
   }

   public String extendPathForParam(String name) {
      if (this.path.equals("")) {
         throw new IllegalStateException("Not inside a function");
      } else {
         return this.path + "@" + name;
      }
   }

   public String extendPath(String name) {
      if (name.endsWith(".py")) {
         name = Util.moduleNameFor(name);
      }

      if (this.path.equals("")) {
         return name;
      } else {
         String sep = null;
         switch (this.scopeType) {
            case CLASS:
            case INSTANCE:
            case MODULE:
            case SCOPE:
               sep = ".";
               break;
            case FUNCTION:
               sep = "&";
               break;
            case GLOBAL:
            default:
               System.err.println("unsupported context for extendPath: " + this.scopeType);
               return this.path;
         }

         return this.path + sep + name;
      }
   }

   private void ensureTable() {
      if (this.table == null) {
         this.table = new LinkedHashMap();
      }

   }

   public String toString() {
      return "<Scope:" + this.getScopeType() + ":" + this.path + ":" + (this.table == null ? "{}" : this.table.keySet()) + ">";
   }

   public String toShortString() {
      return "<Scope:" + this.getScopeType() + ":" + this.path + ">";
   }

   public static enum Type {
      CLASS,
      INSTANCE,
      FUNCTION,
      MODULE,
      GLOBAL,
      SCOPE;
   }
}
