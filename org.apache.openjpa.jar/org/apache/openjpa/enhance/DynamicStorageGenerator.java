package org.apache.openjpa.enhance;

import java.lang.reflect.Constructor;
import java.security.AccessController;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.util.InternalException;
import serp.bytecode.BCClass;
import serp.bytecode.BCClassLoader;
import serp.bytecode.BCField;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;
import serp.bytecode.Instruction;
import serp.bytecode.JumpInstruction;
import serp.bytecode.LoadInstruction;
import serp.bytecode.Project;
import serp.bytecode.TableSwitchInstruction;

public class DynamicStorageGenerator {
   private static final String PREFIX = "openjpastorage$";
   protected static final int POLICY_EXCEPTION = 0;
   protected static final int POLICY_EMPTY = 1;
   protected static final int POLICY_SILENT = 2;
   private static final Class[][] WRAPPERS;
   private static final int[] TYPES;
   private final Project _project = new Project();
   private final BCClassLoader _loader;

   public DynamicStorageGenerator() {
      this._loader = (BCClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newBCClassLoaderAction(this._project, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(DynamicStorage.class))));
   }

   public DynamicStorage generateStorage(int[] types, Object obj) {
      if (obj == null) {
         return null;
      } else {
         String name = this.getClassName(obj);
         BCClass bc = this._project.loadClass(name);
         this.declareClasses(bc);
         bc.addDefaultConstructor().makePublic();
         int objectCount = this.declareFields(types, bc);
         this.addFactoryMethod(bc);
         this.addFieldCount(bc, types, objectCount);
         this.addSetMethods(bc, types, objectCount);
         this.addGetMethods(bc, types);
         this.addInitialize(bc, objectCount);
         this.decorate(obj, bc, types);
         return this.createFactory(bc);
      }
   }

   protected String getClassName(Object obj) {
      return "openjpastorage$" + obj.toString();
   }

   protected int getFieldAccess() {
      return 2;
   }

   protected String getFieldName(int index) {
      return "field" + index;
   }

   protected int getCreateFieldMethods(int type) {
      return 0;
   }

   protected void decorate(Object obj, BCClass cls, int[] types) {
   }

   protected DynamicStorage createFactory(BCClass bc) {
      try {
         Class cls = Class.forName(bc.getName(), false, this._loader);
         Constructor cons = cls.getConstructor((Class[])null);
         DynamicStorage data = (DynamicStorage)cons.newInstance((Object[])null);
         this._project.clear();
         return data;
      } catch (Throwable var5) {
         throw (new InternalException("cons-access", var5)).setFatal(true);
      }
   }

   protected void declareClasses(BCClass bc) {
      bc.declareInterface(DynamicStorage.class);
   }

   private void addFactoryMethod(BCClass bc) {
      BCMethod method = bc.declareMethod("newInstance", DynamicStorage.class, (Class[])null);
      Code code = method.getCode(true);
      code.anew().setType(bc);
      code.dup();
      code.invokespecial().setMethod(bc.getName(), "<init>", "void", (String[])null);
      code.areturn();
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private void addFieldCount(BCClass bc, int[] types, int objectCount) {
      BCMethod method = bc.declareMethod("getFieldCount", Integer.TYPE, (Class[])null);
      Code code = method.getCode(true);
      code.constant().setValue(types.length);
      code.ireturn();
      code.calculateMaxLocals();
      code.calculateMaxStack();
      method = bc.declareMethod("getObjectCount", Integer.TYPE, (Class[])null);
      code = method.getCode(true);
      code.constant().setValue(objectCount);
      code.ireturn();
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private void addInitialize(BCClass bc, int objectCount) {
      BCMethod meth = bc.declareMethod("initialize", Void.TYPE, (Class[])null);
      Code code = meth.getCode(true);
      JumpInstruction ifins = null;
      if (objectCount > 0) {
         code.aload().setThis();
         code.getfield().setField("objects", Object[].class);
         ifins = code.ifnonnull();
         code.aload().setThis();
         code.constant().setValue(objectCount);
         code.anewarray().setType(Object.class);
         code.putfield().setField("objects", Object[].class);
      }

      Instruction ins = code.vreturn();
      if (ifins != null) {
         ifins.setTarget(ins);
      }

      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private int declareFields(int[] types, BCClass bc) {
      bc.declareField("objects", Object[].class).makePrivate();
      int objectCount = 0;

      for(int i = 0; i < types.length; ++i) {
         Class type = this.forType(types[i]);
         if (type == Object.class) {
            ++objectCount;
         } else {
            BCField field = bc.declareField(this.getFieldName(i), type);
            field.setAccessFlags(this.getFieldAccess());
         }
      }

      return objectCount;
   }

   private void addSetMethods(BCClass bc, int[] types, int totalObjects) {
      for(int i = 0; i < TYPES.length; ++i) {
         this.addSetMethod(TYPES[i], bc, types, totalObjects);
      }

   }

   private void addSetMethod(int typeCode, BCClass bc, int[] types, int totalObjects) {
      int handle = this.getCreateFieldMethods(typeCode);
      if (handle != 1) {
         Class type = this.forType(typeCode);
         String name = Object.class.equals(type) ? "Object" : StringUtils.capitalize(type.getName());
         name = "set" + name;
         BCMethod method = bc.declareMethod(name, Void.TYPE, new Class[]{Integer.TYPE, type});
         method.makePublic();
         Code code = method.getCode(true);
         code.aload().setParam(0);
         TableSwitchInstruction tabins = code.tableswitch();
         tabins.setLow(0);
         tabins.setHigh(types.length - 1);
         Object defaultIns;
         if (handle == 2) {
            defaultIns = code.vreturn();
         } else {
            defaultIns = this.throwException(code, IllegalArgumentException.class);
         }

         tabins.setDefaultTarget((Instruction)defaultIns);
         int objectCount = 0;

         for(int i = 0; i < types.length; ++i) {
            if (!this.isCompatible(types[i], typeCode)) {
               tabins.addTarget(tabins.getDefaultTarget());
            } else {
               tabins.addTarget(code.aload().setThis());
               if (typeCode >= 8) {
                  code.aload().setThis();
                  code.getfield().setField("objects", Object[].class);
                  JumpInstruction ifins = code.ifnonnull();
                  code.aload().setThis();
                  code.constant().setValue(totalObjects);
                  code.anewarray().setType(Object.class);
                  code.putfield().setField("objects", Object[].class);
                  ifins.setTarget(code.aload().setThis());
                  code.getfield().setField("objects", Object[].class);
                  code.constant().setValue(objectCount);
                  code.aload().setParam(1);
                  code.aastore();
                  ++objectCount;
               } else {
                  LoadInstruction load = code.xload();
                  load.setType(type);
                  load.setParam(1);
                  code.putfield().setField("field" + i, type);
               }

               code.vreturn();
            }
         }

         code.calculateMaxLocals();
         code.calculateMaxStack();
      }
   }

   private void addGetMethods(BCClass bc, int[] types) {
      for(int i = 0; i < TYPES.length; ++i) {
         this.addGetMethod(TYPES[i], bc, types);
      }

   }

   private void addGetMethod(int typeCode, BCClass bc, int[] types) {
      int handle = this.getCreateFieldMethods(typeCode);
      if (handle != 1) {
         Class type = this.forType(typeCode);
         String name = Object.class.equals(type) ? "Object" : StringUtils.capitalize(type.getName());
         name = "get" + name;
         BCMethod method = bc.declareMethod(name, type, new Class[]{Integer.TYPE});
         method.makePublic();
         Code code = method.getCode(true);
         code.aload().setParam(0);
         TableSwitchInstruction tabins = code.tableswitch();
         tabins.setLow(0);
         tabins.setHigh(types.length - 1);
         Instruction defaultIns = null;
         if (typeCode == 8 && handle == 2) {
            defaultIns = code.constant().setNull();
            code.areturn();
         } else {
            defaultIns = this.throwException(code, IllegalArgumentException.class);
         }

         tabins.setDefaultTarget((Instruction)defaultIns);
         int objectCount = 0;

         for(int i = 0; i < types.length; ++i) {
            if (!this.isCompatible(types[i], typeCode)) {
               tabins.addTarget(tabins.getDefaultTarget());
            } else {
               tabins.addTarget(code.aload().setThis());
               if (typeCode >= 8) {
                  code.aload().setThis();
                  code.getfield().setField("objects", Object[].class);
                  JumpInstruction ifins = code.ifnonnull();
                  code.constant().setNull();
                  code.areturn();
                  ifins.setTarget(code.aload().setThis());
                  code.getfield().setField("objects", Object[].class);
                  code.constant().setValue(objectCount);
                  code.aaload();
                  code.areturn();
                  ++objectCount;
               } else {
                  code.getfield().setField("field" + i, type);
                  code.xreturn().setType(type);
               }
            }
         }

         code.calculateMaxLocals();
         code.calculateMaxStack();
      }
   }

   protected Code replaceMethod(BCClass bc, String name, Class retType, Class[] args, boolean remove) {
      bc.removeDeclaredMethod(name, args);
      BCMethod meth = bc.declareMethod(name, retType, args);
      Code code = meth.getCode(true);
      if (!remove) {
         return code;
      } else {
         code.xreturn().setType(retType);
         code.calculateMaxStack();
         code.calculateMaxLocals();
         return null;
      }
   }

   protected BCField addBeanField(BCClass bc, String name, Class type) {
      if (name == null) {
         throw new IllegalArgumentException("name == null");
      } else {
         BCField field = bc.declareField(name, type);
         field.setAccessFlags(this.getFieldAccess());
         name = StringUtils.capitalize(name);
         String prefix = type == Boolean.TYPE ? "is" : "get";
         BCMethod method = bc.declareMethod(prefix + name, type, (Class[])null);
         method.makePublic();
         Code code = method.getCode(true);
         code.aload().setThis();
         code.getfield().setField(field);
         code.xreturn().setType(type);
         code.calculateMaxStack();
         code.calculateMaxLocals();
         method = bc.declareMethod("set" + name, Void.TYPE, new Class[]{type});
         method.makePublic();
         code = method.getCode(true);
         code.aload().setThis();
         code.xload().setParam(0).setType(type);
         code.putfield().setField(field);
         code.vreturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
         return field;
      }
   }

   protected boolean isCompatible(int fieldType, int storageType) {
      if (storageType == 8) {
         return fieldType >= 8;
      } else {
         return fieldType == storageType;
      }
   }

   protected Instruction throwException(Code code, Class type) {
      Instruction ins = code.anew().setType(type);
      code.dup();
      code.invokespecial().setMethod(type, "<init>", Void.TYPE, (Class[])null);
      code.athrow();
      return ins;
   }

   protected Class forType(int type) {
      switch (type) {
         case 0:
            return Boolean.TYPE;
         case 1:
            return Byte.TYPE;
         case 2:
            return Character.TYPE;
         case 3:
            return Double.TYPE;
         case 4:
            return Float.TYPE;
         case 5:
            return Integer.TYPE;
         case 6:
            return Long.TYPE;
         case 7:
            return Short.TYPE;
         default:
            return Object.class;
      }
   }

   protected Class getWrapper(int type) {
      return this.getWrapper(this.forType(type));
   }

   protected Class getWrapper(Class c) {
      for(int i = 0; i < WRAPPERS.length; ++i) {
         if (WRAPPERS[i][0].equals(c)) {
            return WRAPPERS[i][1];
         }
      }

      return c;
   }

   static {
      WRAPPERS = new Class[][]{{Boolean.TYPE, Boolean.class}, {Byte.TYPE, Byte.class}, {Character.TYPE, Character.class}, {Integer.TYPE, Integer.class}, {Short.TYPE, Short.class}, {Long.TYPE, Long.class}, {Float.TYPE, Float.class}, {Double.TYPE, Double.class}};
      TYPES = new int[]{0, 1, 2, 5, 7, 6, 4, 3, 8};
   }
}
