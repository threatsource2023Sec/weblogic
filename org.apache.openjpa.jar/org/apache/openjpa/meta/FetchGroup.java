package org.apache.openjpa.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;
import serp.util.Numbers;

public class FetchGroup implements Serializable {
   public static final String NAME_DEFAULT = "default";
   public static final String NAME_ALL = "all";
   public static final int RECURSION_DEPTH_DEFAULT = 1;
   public static final int DEPTH_INFINITE = -1;
   static final FetchGroup DEFAULT = new FetchGroup("default", true);
   static final FetchGroup ALL = new FetchGroup("all", false);
   private static final Localizer _loc = Localizer.forPackage(FetchGroup.class);
   private final String _name;
   private final ClassMetaData _meta;
   private final boolean _readOnly;
   private List _includes;
   private Set _containedBy;
   private Map _depths;
   private Boolean _postLoad;

   FetchGroup(ClassMetaData cm, String name) {
      this._meta = cm;
      this._name = name;
      this._readOnly = false;
   }

   private FetchGroup(String name, boolean postLoad) {
      this._meta = null;
      this._name = name;
      this._postLoad = postLoad ? Boolean.TRUE : Boolean.FALSE;
      this._readOnly = true;
   }

   void copy(FetchGroup fg) {
      if (fg._includes != null) {
         Iterator itr = fg._includes.iterator();

         while(itr.hasNext()) {
            this.addDeclaredInclude((String)itr.next());
         }
      }

      if (fg._containedBy != null) {
         this._containedBy = new HashSet(fg._containedBy);
      }

      if (fg._depths != null) {
         Iterator itr = fg._depths.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            this.setRecursionDepth((FieldMetaData)entry.getKey(), ((Number)entry.getValue()).intValue());
         }
      }

      if (fg._postLoad != null) {
         this._postLoad = fg._postLoad;
      }

   }

   public String getName() {
      return this._name;
   }

   public void addDeclaredInclude(String fgName) {
      if (this._readOnly) {
         throw new UnsupportedOperationException();
      } else if (StringUtils.isEmpty(fgName)) {
         throw new MetaDataException(_loc.get("null-include-fg", (Object)this));
      } else {
         if (this._includes == null) {
            this._includes = new ArrayList();
         }

         if (!this._includes.contains(fgName)) {
            this._includes.add(fgName);
         }

      }
   }

   public boolean includes(String fgName, boolean recurse) {
      if (this._includes != null) {
         if (this._includes.contains(fgName)) {
            return true;
         }

         if (recurse && this._meta != null) {
            Iterator i = this._includes.iterator();

            while(i.hasNext()) {
               FetchGroup fg = this._meta.getFetchGroup((String)i.next());
               if (fg != null && fg.includes(fgName, true)) {
                  return true;
               }
            }
         }
      }

      if (this._meta != null) {
         ClassMetaData sup = this._meta.getPCSuperclassMetaData();
         if (sup != null) {
            FetchGroup supFG = sup.getFetchGroup(this._name);
            if (supFG != null) {
               return supFG.includes(fgName, recurse);
            }
         }
      }

      return false;
   }

   public boolean addContainedBy(FetchGroup parent) {
      parent.addDeclaredInclude(this.getName());
      if (this._containedBy == null) {
         this._containedBy = new HashSet();
      }

      return this._containedBy.add(parent.getName());
   }

   public Set getContainedBy() {
      return this._containedBy == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(this._containedBy);
   }

   public String[] getDeclaredIncludes() {
      return this._includes == null ? new String[0] : (String[])((String[])this._includes.toArray(new String[this._includes.size()]));
   }

   public void setRecursionDepth(FieldMetaData fm, int depth) {
      if (this._readOnly) {
         throw new UnsupportedOperationException();
      } else if (depth < -1) {
         throw new MetaDataException(_loc.get("invalid-fg-depth", this._name, fm, Numbers.valueOf(depth)));
      } else {
         if (this._depths == null) {
            this._depths = new HashMap();
         }

         this._depths.put(fm, Numbers.valueOf(depth));
      }
   }

   public int getRecursionDepth(FieldMetaData fm) {
      Number depth = this.findRecursionDepth(fm);
      return depth == null ? 1 : depth.intValue();
   }

   public int getDeclaredRecursionDepth(FieldMetaData fm) {
      Number depth = this._depths == null ? null : (Number)this._depths.get(fm);
      return depth == null ? 0 : depth.intValue();
   }

   private Number findRecursionDepth(FieldMetaData fm) {
      Number depth = this._depths == null ? null : (Number)this._depths.get(fm);
      if (depth != null) {
         return depth;
      } else {
         Number max = null;
         if (this._meta != null && fm.getDeclaringMetaData() != this._meta) {
            ClassMetaData sup = this._meta.getPCSuperclassMetaData();
            if (sup != null) {
               FetchGroup supFG = sup.getFetchGroup(this._name);
               if (supFG != null) {
                  max = supFG.findRecursionDepth(fm);
               }
            }
         }

         if (this._includes == null) {
            return max;
         } else {
            Iterator itr = this._includes.iterator();

            while(true) {
               do {
                  do {
                     if (!itr.hasNext()) {
                        return max;
                     }

                     FetchGroup fg = this._meta.getFetchGroup((String)itr.next());
                     depth = fg == null ? null : fg.findRecursionDepth(fm);
                  } while(depth == null);
               } while(max != null && depth.intValue() <= max.intValue());

               max = depth;
            }
         }
      }
   }

   public FieldMetaData[] getDeclaredRecursionDepthFields() {
      return this._depths == null ? new FieldMetaData[0] : (FieldMetaData[])((FieldMetaData[])this._depths.keySet().toArray(new FieldMetaData[this._depths.size()]));
   }

   public void setPostLoad(boolean flag) {
      if (this._readOnly && flag != this.isPostLoad()) {
         throw new UnsupportedOperationException();
      } else {
         this._postLoad = flag ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   public boolean isPostLoad() {
      if (this._postLoad != null) {
         return this._postLoad;
      } else {
         if (this._meta != null) {
            ClassMetaData sup = this._meta.getPCSuperclassMetaData();
            if (sup != null) {
               FetchGroup supFG = sup.getFetchGroup(this._name);
               if (supFG != null && supFG.isPostLoad()) {
                  return true;
               }
            }
         }

         if (this._includes == null) {
            return false;
         } else {
            Iterator itr = this._includes.iterator();

            FetchGroup fg;
            do {
               if (!itr.hasNext()) {
                  return false;
               }

               fg = this._meta.getFetchGroup((String)itr.next());
            } while(fg == null || !fg.isPostLoad());

            return true;
         }
      }
   }

   public boolean isPostLoadExplicit() {
      return this._postLoad != null;
   }

   public void resolve() {
      if (this._includes != null) {
         Iterator itr = this._includes.iterator();

         String name;
         FetchGroup fg;
         do {
            if (!itr.hasNext()) {
               return;
            }

            name = (String)itr.next();
            if (name.equals(this._name)) {
               throw new MetaDataException(_loc.get("cyclic-fg", this, name));
            }

            fg = this._meta.getFetchGroup(name);
            if (fg == null) {
               throw new MetaDataException(_loc.get("bad-fg-include", this, name));
            }
         } while(!fg.includes(this._name, true));

         throw new MetaDataException(_loc.get("cyclic-fg", this, name));
      }
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof FetchGroup)) {
         return false;
      } else {
         FetchGroup that = (FetchGroup)other;
         return this._name.equals(that._name) && ObjectUtils.equals(this._meta, that._meta);
      }
   }

   public int hashCode() {
      return this._name.hashCode() + (this._meta == null ? 0 : this._meta.hashCode());
   }

   public String toString() {
      return (this._meta == null ? "Builtin" : this._meta.toString()) + "." + this._name;
   }
}
