package org.python.indexer.types;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.python.indexer.Indexer;

public class NUnionType extends NType {
   private static final int MAX_RECURSION_DEPTH = 15;
   private Set types;

   public NUnionType() {
      this.types = new HashSet();
   }

   public NUnionType(NType... initialTypes) {
      this();
      NType[] var2 = initialTypes;
      int var3 = initialTypes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         NType nt = var2[var4];
         this.addType(nt);
      }

   }

   public void setTypes(Set types) {
      this.types = types;
   }

   public Set getTypes() {
      return this.types;
   }

   public void addType(NType t) {
      if (t == null) {
         throw new IllegalArgumentException("null type");
      } else {
         if (t.isUnionType()) {
            this.types.addAll(t.asUnionType().types);
         } else {
            this.types.add(t);
         }

      }
   }

   public boolean contains(NType t) {
      return this.types.contains(t);
   }

   public static NType union(NType u, NType v) {
      NType wu = NUnknownType.follow(u);
      NType wv = NUnknownType.follow(v);
      if (wu == wv) {
         return u;
      } else if (wu == Indexer.idx.builtins.None) {
         return v;
      } else if (wv == Indexer.idx.builtins.None) {
         return u;
      } else if (wu.isUnknownType() && !occurs(wu, wv, 0)) {
         NUnknownType.point(wu, wv);
         return u;
      } else if (wv.isUnknownType() && !occurs(wv, wu, 0)) {
         NUnknownType.point(wv, wu);
         return v;
      } else if (wu.isTupleType() && wv.isTupleType()) {
         NTupleType tu = (NTupleType)wu;
         NTupleType tv = (NTupleType)wv;
         if (tu.getElementTypes().size() != tv.getElementTypes().size()) {
            return newUnion(wu, wv);
         } else {
            NTupleType ret = new NTupleType();

            for(int i = 0; i < tu.getElementTypes().size(); ++i) {
               ret.add(union((NType)tu.getElementTypes().get(i), (NType)tv.getElementTypes().get(i)));
            }

            return ret;
         }
      } else if (wu.isListType() && wv.isListType()) {
         return new NListType(union(wu.asListType().getElementType(), wv.asListType().getElementType()));
      } else if (wu.isDictType() && wv.isDictType()) {
         NDictType du = (NDictType)wu;
         NDictType dv = (NDictType)wv;
         return new NDictType(union(du.getKeyType(), dv.getKeyType()), union(du.getValueType(), dv.getValueType()));
      } else if (wu.isFuncType() && wv.isFuncType()) {
         return new NFuncType(union(wu.asFuncType().getReturnType(), wv.asFuncType().getReturnType()));
      } else if (wu.isFuncType() && wv.isClassType()) {
         NUnknownType.point(wu.asFuncType().getReturnType(), wv);
         NUnknownType.point(u, wv);
         return u;
      } else if (wu.isClassType() && wv.isFuncType()) {
         NUnknownType.point(wv.asFuncType().getReturnType(), wu);
         NUnknownType.point(v, wu);
         return v;
      } else {
         return newUnion(wu, wv);
      }
   }

   private static boolean occurs(NType u, NType v, int depth) {
      if (depth++ > 15) {
         return true;
      } else {
         u = NUnknownType.follow(u);
         v = NUnknownType.follow(v);
         if (u == v) {
            return true;
         } else {
            Iterator var3;
            NType vv;
            if (v.isTupleType()) {
               var3 = v.asTupleType().getElementTypes().iterator();

               do {
                  if (!var3.hasNext()) {
                     return false;
                  }

                  vv = (NType)var3.next();
               } while(!occurs(u, vv, depth));

               return true;
            } else if (v.isListType()) {
               return occurs(u, v.asListType().getElementType(), depth);
            } else if (!v.isDictType()) {
               if (v.isFuncType()) {
                  NType ret = v.asFuncType().getReturnType();
                  return occurs(v, ret, depth) ? true : occurs(u, ret, depth);
               } else if (v.isUnionType()) {
                  var3 = v.asUnionType().types.iterator();

                  do {
                     if (!var3.hasNext()) {
                        return false;
                     }

                     vv = (NType)var3.next();
                  } while(!occurs(u, vv, depth));

                  return true;
               } else {
                  return false;
               }
            } else {
               return occurs(u, v.asDictType().getKeyType(), depth) || occurs(u, v.asDictType().getValueType(), depth);
            }
         }
      }
   }

   public static NUnionType newUnion(NType... types) {
      NUnionType ret = new NUnionType();
      NType[] var2 = types;
      int var3 = types.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         NType type = var2[var4];
         ret.addType(type);
      }

      return ret;
   }

   public NType firstKnownAlternate() {
      Iterator var1 = this.types.iterator();

      NType type;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         type = (NType)var1.next();
      } while(type.follow().isUnknownType());

      return type;
   }

   public NType firstKnownNonNullAlternate() {
      Iterator var1 = this.types.iterator();

      NType type;
      NType tt;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         type = (NType)var1.next();
         tt = type.follow();
      } while(tt.isUnknownType() || tt == Indexer.idx.builtins.None);

      return type;
   }

   public void printKids(NType.CyclicTypeRecorder ctr, StringBuilder sb) {
      sb.append("[");
      Iterator var3 = this.types.iterator();

      while(var3.hasNext()) {
         NType u = (NType)var3.next();
         u.print(ctr, sb);
         sb.append(",");
      }

      sb.setLength(sb.length() - 1);
      sb.append("]");
   }
}
