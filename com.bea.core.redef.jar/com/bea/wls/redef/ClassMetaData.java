package com.bea.wls.redef;

import com.bea.wls.redef.filter.MetaDataFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import serp.bytecode.NameCache;
import serp.bytecode.lowlevel.ConstantPoolTable;
import weblogic.diagnostics.debug.DebugLogger;

public class ClassMetaData {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugClassRedef");
   private static final String[] EMPTY_STRINGS = new String[0];
   private static final FieldMetaData[] EMPTY_FIELDS = new FieldMetaData[0];
   private static final MethodMetaData[] EMPTY_METHODS = new MethodMetaData[0];
   private static final ConstructorMetaData[] EMPTY_CONSTRUCTORS = new ConstructorMetaData[0];
   public static final String SVUID = "serialVersionUID";
   private final MetaDataRepository _repos;
   private final String _name;
   private final int _baseIndex;
   private String _super;
   private ClassMetaData _superMeta;
   private List _nonredefSupers;
   private int _version = -1;
   private MetaDataFilter _filter;
   private FieldMetaData[] _fields;
   private FieldMetaData[] _nonredefFields;
   private MethodMetaData[] _methods;
   private ConstructorMetaData[] _cons;
   private int _access;
   private String[] _interfaces;
   private boolean _hasStaticInitializer;
   private Long _svuid;
   private boolean _resolved;

   ClassMetaData(MetaDataRepository repos, String name, int baseIndex) {
      this._fields = EMPTY_FIELDS;
      this._nonredefFields = EMPTY_FIELDS;
      this._methods = EMPTY_METHODS;
      this._cons = EMPTY_CONSTRUCTORS;
      this._repos = repos;
      this._name = name;
      this._baseIndex = baseIndex;
   }

   void define(ConstantPoolTable table, NameCache names, MetaDataFilter filter) {
      int idx = table.getEndIndex();
      ++this._version;
      this._filter = filter;
      if (this._version == 0) {
         this._access = table.readUnsignedShort(idx);
      }

      idx += 2;
      idx += 2;
      if (this._version == 0) {
         this._super = this.getClassEntry(table, idx);
      }

      idx += 2;
      int count = table.readUnsignedShort(idx);
      idx += 2;
      if (this._version == 0) {
         this._interfaces = new String[count];

         for(int i = 0; i < count; ++i) {
            this._interfaces[i] = this.getClassEntry(table, idx + 2 * i);
         }
      }

      idx += 2 * count;
      count = table.readUnsignedShort(idx);
      List fields = new ArrayList(count);
      idx += 2;

      int i;
      String name;
      for(int i = 0; i < count; ++i) {
         int access = table.readUnsignedShort(idx);
         i = table.readUnsignedShort(idx + 2);
         String name = table.readString(table.get(i));
         i = table.readUnsignedShort(idx + 4);
         String descriptor = table.readString(table.get(i));
         name = names.getExternalForm(descriptor, false);
         FieldMetaData fieldMeta = new FieldMetaData(this._name, name, descriptor, name, access);
         if (this._version == 0 && name.equals("serialVersionUID") && descriptor.equals("J") && fieldMeta.isStatic()) {
            this._svuid = SerialVersionUID.getExplicitSVUIDValue(table, idx);
         }

         if (filter.eval(fieldMeta)) {
            fields.add(fieldMeta);
         }

         idx += 6;
         idx += this.skipAttributes(table, idx);
      }

      this.mergeFields(fields);
      count = table.readUnsignedShort(idx);
      List methods = new ArrayList(count);
      List cons = new ArrayList(3);
      idx += 2;

      for(i = 0; i < count; ++i) {
         int access = table.readUnsignedShort(idx);
         int utfEntry = table.readUnsignedShort(idx + 2);
         name = table.readString(table.get(utfEntry));
         if ("<clinit>".equals(name)) {
            this._hasStaticInitializer = true;
         } else {
            utfEntry = table.readUnsignedShort(idx + 4);
            String desc = table.readString(table.get(utfEntry));
            String[] params = names.getDescriptorParamNames(desc);

            for(int j = 0; j < params.length; ++j) {
               params[j] = names.getExternalForm(params[j], false);
            }

            if ("<init>".equals(name)) {
               ConstructorMetaData consMeta = new ConstructorMetaData(params, desc, access);
               if (filter.eval(consMeta)) {
                  cons.add(consMeta);
               }
            } else {
               String ret = names.getExternalForm(names.getDescriptorReturnName(desc), false);
               MethodMetaData methodMeta = new MethodMetaData(this, name, ret, params, desc, access);
               if (filter.eval(methodMeta)) {
                  methods.add(methodMeta);
               }
            }
         }

         idx += 6;
         idx += this.skipAttributes(table, idx);
      }

      this.mergeMethods(methods);
      this.mergeConstructors(cons);
      this.getSerialVersionUID();
   }

   private String getClassEntry(ConstantPoolTable table, int idx) {
      int clsEntry = table.readUnsignedShort(idx);
      int utfEntry = table.readUnsignedShort(table.get(clsEntry));
      return table.readString(table.get(utfEntry)).replace('/', '.');
   }

   private int skipAttributes(ConstantPoolTable table, int idx) {
      int attrCount = table.readUnsignedShort(idx);
      int skipped = 2;

      for(int i = 0; i < attrCount; ++i) {
         int len = table.readInt(idx + skipped + 2);
         skipped += 6 + len;
      }

      return skipped;
   }

   private void mergeFields(List fields) {
      FieldMetaData[] var2 = this._fields;
      int var3 = var2.length;

      FieldMetaData existing;
      for(int var4 = 0; var4 < var3; ++var4) {
         existing = var2[var4];
         existing.setCurrent(false);
      }

      List redef = null;
      Iterator itr = fields.iterator();

      while(itr.hasNext()) {
         FieldMetaData field = (FieldMetaData)itr.next();
         existing = this.getField(field.getName(), field.getType(), field.isStatic(), false);
         if (existing != null) {
            existing.setCurrent(true);
         } else {
            if (redef == null) {
               redef = new ArrayList(Arrays.asList(this._fields));
            }

            field.setIndex(this._baseIndex + this._nonredefFields.length + redef.size());
            field.setAdded(this._version > 0);
            field.setCurrent(true);
            redef.add(field);
         }
      }

      if (redef != null) {
         this._fields = (FieldMetaData[])redef.toArray(new FieldMetaData[redef.size()]);
      }

   }

   private void mergeMethods(List methods) {
      MethodMetaData[] var2 = this._methods;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MethodMetaData method = var2[var4];
         method.setCurrent(false);
      }

      List redef = null;
      Iterator itr = methods.iterator();

      while(itr.hasNext()) {
         MethodMetaData method = (MethodMetaData)itr.next();
         AccessType accessType = method.isStatic() ? AccessType.STATIC : (method.isPrivate() ? AccessType.PRIVATE : AccessType.NONPRIVATE);
         MethodMetaData existing = this.getMethod(method.getName(), method.getReturnType(), method.getParameterTypes(), accessType, false);
         if (existing != null) {
            existing.setCurrent(true);
         } else {
            if (redef == null) {
               redef = new ArrayList(Arrays.asList(this._methods));
            }

            method.setIndex(this._repos.getMethodIndex(method));
            method.setAdded(this._version > 0);
            method.setCurrent(true);
            redef.add(method);
         }
      }

      if (redef != null) {
         this._methods = (MethodMetaData[])redef.toArray(new MethodMetaData[redef.size()]);
      }

   }

   private void mergeConstructors(List cons) {
      ConstructorMetaData[] var2 = this._cons;
      int var3 = var2.length;

      ConstructorMetaData existing;
      for(int var4 = 0; var4 < var3; ++var4) {
         existing = var2[var4];
         existing.setCurrent(false);
      }

      List redef = null;
      Iterator itr = cons.iterator();

      while(itr.hasNext()) {
         ConstructorMetaData con = (ConstructorMetaData)itr.next();
         existing = this.getConstructor(con.getParameterTypes());
         if (existing != null) {
            existing.setCurrent(true);
         } else {
            if (redef == null) {
               redef = new ArrayList(Arrays.asList(this._cons));
            }

            con.setIndex(this._baseIndex + redef.size());
            con.setAdded(this._version > 0);
            con.setCurrent(true);
            redef.add(con);
         }
      }

      if (redef != null) {
         this._cons = (ConstructorMetaData[])redef.toArray(new ConstructorMetaData[redef.size()]);
      }

   }

   void resolve(NameCache names, ClassLoader loader) {
      if (!this._resolved) {
         this._resolved = true;
         ClassMetaData sup = this._repos.getMetaData(this._super);
         this._superMeta = sup;
         if (this._super != null && !this.isInterface()) {
            Collection overridable = null;
            ArrayList all;
            if (sup != null) {
               overridable = this.getNewOverridableMethods(sup);
            } else {
               all = new ArrayList(3);
               List fields = new ArrayList(5);

               try {
                  overridable = this.processNonredefinableSuperclass(Class.forName(this._super, false, loader), all, fields, names);
               } catch (Throwable var8) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug(var8.getMessage(), var8);
                  }
               }

               this._nonredefSupers = all;
               this._nonredefFields = (FieldMetaData[])fields.toArray(new FieldMetaData[fields.size()]);
            }

            if (overridable != null) {
               all = new ArrayList(Arrays.asList(this._methods));
               all.addAll((Collection)overridable);
               this._methods = (MethodMetaData[])all.toArray(new MethodMetaData[all.size()]);
            }
         }
      }
   }

   private List getNewOverridableMethods(ClassMetaData sup) {
      List overridable = null;
      MethodMetaData[] var3 = sup.getDeclaredMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MethodMetaData method = var3[var5];
         if (!method.isPrivate() && !method.isStatic() && !method.isAdded()) {
            MethodMetaData existing = this.getMethod(method.getIndex(), false);
            if (existing != null) {
               existing.setOverride(OverrideType.REDEFINABLE);
            } else {
               MethodMetaData clone = new MethodMetaData(this, method.getName(), method.getReturnType(), method.getParameterTypes(), method.getDescriptor(), method.getAccess());
               clone.setIndex(method.getIndex());
               clone.setOverride(OverrideType.REDEFINABLE);
               if (overridable == null) {
                  overridable = new ArrayList();
               }

               overridable.add(clone);
            }
         }
      }

      return overridable;
   }

   private Collection processNonredefinableSuperclass(Class sup, List supers, List fields, NameCache names) throws Throwable {
      Map overridable = null;

      for(Map finalMethods = null; sup != null; sup = sup.getSuperclass()) {
         supers.add(sup.getName());
         Field[] var7 = sup.getDeclaredFields();
         int var8 = var7.length;

         int var9;
         int mods;
         for(var9 = 0; var9 < var8; ++var9) {
            Field f = var7[var9];
            mods = f.getModifiers();
            if (Modifier.isProtected(mods) && (Modifier.isStatic(mods) || !hasMemberField(fields, f.getName()))) {
               String type = f.getType().getName();
               FieldMetaData meta = new FieldMetaData(sup.getName(), f.getName(), names.getInternalForm(type, true), type, mods);
               meta.setCurrent(true);
               meta.setIndex(this._baseIndex + this._fields.length + fields.size());
               fields.add(meta);
            }
         }

         Method[] var18 = sup.getDeclaredMethods();
         var8 = var18.length;

         for(var9 = 0; var9 < var8; ++var9) {
            Method m = var18[var9];
            mods = m.getModifiers();
            if (Modifier.isFinal(mods)) {
               finalMethods = this.markFinalMethod(finalMethods, m);
            }

            OverrideType type = null;
            if (Modifier.isFinal(mods) && Modifier.isProtected(mods) && !Modifier.isStatic(mods)) {
               type = OverrideType.FINALPROTECTED_NONREDEFINABLE;
            } else if (Modifier.isPrivate(mods) || Modifier.isFinal(mods) || Modifier.isNative(mods) || this.isMarkedFinal(finalMethods, m)) {
               continue;
            }

            String ret = m.getReturnType().getName();
            String[] params = getTypeNames(m.getParameterTypes());
            MethodMetaData meta = new MethodMetaData(this, type == OverrideType.FINALPROTECTED_NONREDEFINABLE ? "beaAccessSuperFinalProtected" + m.getName() : m.getName(), ret, params, names.getDescriptor(ret, params), m.getModifiers());
            int idx = this._repos.getMethodIndex(meta);
            MethodMetaData existing = this.getMethod(idx, false);
            if (type == null) {
               type = meta.isStatic() ? OverrideType.STATIC_NONREDEFINABLE : OverrideType.NONREDEFINABLE;
            }

            if (existing != null) {
               existing.setOverride(type);
            } else if (params.length != 0 || !"finalize".equals(m.getName())) {
               meta.setOverride(type);
               meta.setIndex(idx);
               if (overridable == null) {
                  overridable = new HashMap();
               } else if (overridable.containsKey(idx)) {
                  continue;
               }

               overridable.put(idx, meta);
            }
         }
      }

      return overridable == null ? null : overridable.values();
   }

   private Map markFinalMethod(Map map, Method m) {
      if (map == null) {
         map = new HashMap();
      }

      String methodName = m.getName();
      List list = (List)((Map)map).get(methodName);
      if (list == null) {
         list = new ArrayList();
         ((Map)map).put(methodName, list);
      }

      ((List)list).add(m);
      return (Map)map;
   }

   private boolean isMarkedFinal(Map map, Method m) {
      if (map == null) {
         return false;
      } else {
         String methodName = m.getName();
         Class[] paramTypes = m.getParameterTypes();
         List list = (List)map.get(methodName);
         if (list == null) {
            return false;
         } else {
            Iterator var6 = list.iterator();

            Method fm;
            do {
               if (!var6.hasNext()) {
                  return false;
               }

               fm = (Method)var6.next();
            } while(!this.matchedMethods(m, fm));

            return true;
         }
      }
   }

   private boolean matchedMethods(Method m1, Method m2) {
      Class[] paramTypes1 = m1.getParameterTypes();
      Class[] paramTypes2 = m2.getParameterTypes();
      int size1 = paramTypes1 != null ? paramTypes1.length : 0;
      int size2 = paramTypes2 != null ? paramTypes2.length : 0;
      if (size1 != size2) {
         return false;
      } else if (!m1.getReturnType().equals(m2.getReturnType())) {
         return false;
      } else {
         for(int i = 0; i < size1; ++i) {
            if (!paramTypes1[i].equals(paramTypes2[i])) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean hasMemberField(List fields, String name) {
      Iterator var2 = fields.iterator();

      FieldMetaData field;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         field = (FieldMetaData)var2.next();
      } while(field.isStatic() || !field.getName().equals(name));

      return true;
   }

   private static String[] getTypeNames(Class[] types) {
      String[] names = new String[types.length];

      for(int i = 0; i < types.length; ++i) {
         names[i] = types[i].getName();
      }

      return names;
   }

   MetaDataFilter getPreviousFilter() {
      return this._filter;
   }

   public MetaDataRepository getRepository() {
      return this._repos;
   }

   public String getName() {
      return this._name;
   }

   public int getVersion() {
      return this._version;
   }

   public int getBaseIndex() {
      return this._baseIndex;
   }

   public int getFirstDeclaredAddedFieldIndex() {
      FieldMetaData[] var1 = this._fields;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         FieldMetaData field = var1[var3];
         if (field.isAdded()) {
            return field.getIndex();
         }
      }

      return -1;
   }

   public FieldMetaData getField(String name, String type, boolean isStatic) {
      return this.getField(name, type, isStatic, true);
   }

   private FieldMetaData getField(String name, String type, boolean isStatic, boolean sup) {
      FieldMetaData[] var5 = this._fields;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         FieldMetaData field = var5[var7];
         if (field.getName().equals(name) && field.getType().equals(type) && field.isStatic() == isStatic) {
            return field;
         }
      }

      if (!sup) {
         return null;
      } else {
         FieldMetaData field = this.getNonredefinableSuperclassField((String)null, name, type, isStatic);
         if (field != null) {
            return field;
         } else {
            ClassMetaData supMeta = this.getSuperclassMetaData();
            return supMeta == null ? null : supMeta.getField(name, type, isStatic);
         }
      }
   }

   public FieldMetaData[] getDeclaredFields() {
      return this._fields;
   }

   public FieldMetaData[] getNonredefinableSuperclassFields() {
      return this._nonredefFields;
   }

   public FieldMetaData getNonredefinableSuperclassField(String declarer, String name, String type, boolean isStatic) {
      FieldMetaData[] var5 = this._nonredefFields;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         FieldMetaData field = var5[var7];
         if (field.getName().equals(name) && field.getType().equals(type) && field.isStatic() == isStatic) {
            if (declarer == null || declarer.equals(field.getDeclarer())) {
               return field;
            }

            int idx = this._nonredefSupers.indexOf(declarer);
            if (idx == -1) {
               return null;
            }

            if (idx < this._nonredefSupers.indexOf(field.getDeclarer())) {
               return field;
            }
         }
      }

      return null;
   }

   public MethodMetaData getMethod(int idx) {
      return this.getMethod(idx, true);
   }

   private MethodMetaData getMethod(int idx, boolean sup) {
      MethodMetaData[] var3 = this._methods;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MethodMetaData method = var3[var5];
         if (method.getIndex() == idx) {
            return method;
         }
      }

      if (!sup) {
         return null;
      } else {
         ClassMetaData supMeta = this.getSuperclassMetaData();
         return supMeta == null ? null : supMeta.getMethod(idx);
      }
   }

   public MethodMetaData getMethod(String name, String returnType, String[] paramTypes) {
      return this.getMethod(name, returnType, paramTypes, AccessType.ANY, true);
   }

   public MethodMetaData getMethod(String name, String returnType, String[] paramTypes, AccessType accessType) {
      return this.getMethod(name, returnType, paramTypes, accessType, true);
   }

   private MethodMetaData getMethod(String name, String returnType, String[] paramTypes, AccessType accessType, boolean sup) {
      MethodMetaData[] var6 = this._methods;
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         MethodMetaData method = var6[var8];
         switch (accessType) {
            case STATIC:
               if (!method.isStatic()) {
                  continue;
               }
               break;
            case PRIVATE:
               if (method.isStatic() || !method.isPrivate()) {
                  continue;
               }
               break;
            case NONPRIVATE:
               if (method.isStatic() || method.isPrivate()) {
                  continue;
               }
            case ANY:
         }

         if (method.getName().equals(name) && parametersEqual(method.getParameterTypes(), paramTypes) && method.getReturnType().equals(returnType)) {
            return method;
         }
      }

      if (!sup) {
         return null;
      } else {
         ClassMetaData supMeta = this.getSuperclassMetaData();
         return supMeta == null ? null : supMeta.getMethod(name, returnType, paramTypes, accessType);
      }
   }

   private static boolean parametersEqual(String[] p1, String[] p2) {
      if (p1 == null) {
         p1 = EMPTY_STRINGS;
      }

      if (p2 == null) {
         p2 = EMPTY_STRINGS;
      }

      if (p1.length != p2.length) {
         return false;
      } else {
         for(int i = 0; i < p1.length; ++i) {
            if (!p1[i].equals(p2[i])) {
               return false;
            }
         }

         return true;
      }
   }

   public MethodMetaData[] getDeclaredMethods() {
      return this._methods;
   }

   public ConstructorMetaData getConstructor(String[] paramTypes) {
      ConstructorMetaData[] var2 = this._cons;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ConstructorMetaData con = var2[var4];
         if (parametersEqual(con.getParameterTypes(), paramTypes)) {
            return con;
         }
      }

      return null;
   }

   public ConstructorMetaData[] getDeclaredConstructors() {
      return this._cons;
   }

   public int getAccess() {
      return this._access;
   }

   public String getSuperclass() {
      return this._super;
   }

   public ClassMetaData getSuperclassMetaData() {
      return this._resolved ? this._superMeta : this._repos.getMetaData(this._super);
   }

   public boolean hasNonredefinableSuperclass(String clsName) {
      return this._nonredefSupers != null && this._nonredefSupers.contains(clsName);
   }

   public boolean isInterface() {
      return (this._access & 512) == 512;
   }

   public String[] getInterfaces() {
      return this._interfaces;
   }

   public boolean hasStaticInitializer() {
      return this._hasStaticInitializer;
   }

   public long getSerialVersionUID() {
      if (this._svuid == null) {
         this._svuid = new Long(SerialVersionUID.computeSerialVersionUID(this));
      }

      return this._svuid;
   }
}
