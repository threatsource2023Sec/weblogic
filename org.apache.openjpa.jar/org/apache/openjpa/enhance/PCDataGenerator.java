package org.apache.openjpa.enhance;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.AbstractPCData;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCData;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.InternalException;
import serp.bytecode.BCClass;
import serp.bytecode.BCField;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;
import serp.bytecode.ExceptionHandler;
import serp.bytecode.IfInstruction;
import serp.bytecode.Instruction;
import serp.bytecode.JumpInstruction;
import serp.bytecode.LocalVariableInstruction;
import serp.bytecode.LookupSwitchInstruction;

public class PCDataGenerator extends DynamicStorageGenerator {
   private static final Localizer _loc = Localizer.forPackage(PCDataGenerator.class);
   protected static final String POSTFIX = "$openjpapcdata";
   private final Map _generated = new ConcurrentHashMap();
   private final OpenJPAConfiguration _conf;
   private final Log _log;

   public PCDataGenerator(OpenJPAConfiguration conf) {
      this._conf = conf;
      this._log = this._conf.getLogFactory().getLog("openjpa.Enhance");
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   public PCData generatePCData(Object oid, ClassMetaData meta) {
      if (meta == null) {
         return null;
      } else {
         Class type = meta.getDescribedType();
         DynamicStorage storage = (DynamicStorage)this._generated.get(type);
         if (storage == null) {
            storage = this.generateStorage(meta);
            this._generated.put(type, storage);
            if (this._log.isTraceEnabled()) {
               this._log.trace(_loc.get("pcdata-created", type.getName(), meta));
            }
         }

         DynamicPCData data = (DynamicPCData)storage.newInstance();
         data.setId(oid);
         data.setStorageGenerator(this);
         this.finish(data, meta);
         return data;
      }
   }

   private DynamicStorage generateStorage(ClassMetaData meta) {
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("pcdata-generate", (Object)meta));
      }

      FieldMetaData[] fields = meta.getFields();
      int[] types = new int[fields.length];

      for(int i = 0; i < types.length; ++i) {
         types[i] = this.replaceType(fields[i]);
      }

      return this.generateStorage(types, meta);
   }

   protected void finish(DynamicPCData data, ClassMetaData meta) {
   }

   protected int getCreateFieldMethods(int typeCode) {
      return typeCode >= 8 ? 2 : 1;
   }

   protected void declareClasses(BCClass bc) {
      super.declareClasses(bc);
      bc.declareInterface(DynamicPCData.class);
      bc.setSuperclass(AbstractPCData.class);
   }

   protected final String getClassName(Object obj) {
      return this.getUniqueName(((ClassMetaData)obj).getDescribedType());
   }

   protected String getUniqueName(Class type) {
      return type.getName() + "$" + System.identityHashCode(type) + "$openjpapcdata";
   }

   protected final void decorate(Object obj, BCClass bc, int[] types) {
      super.decorate(obj, bc, types);
      ClassMetaData meta = (ClassMetaData)obj;
      this.enhanceConstructor(bc);
      this.addBaseFields(bc);
      this.addImplDataMethods(bc, meta);
      this.addFieldImplDataMethods(bc, meta);
      this.addVersionMethods(bc);
      this.addGetType(bc, meta);
      this.addLoadMethod(bc, meta);
      this.addLoadWithFieldsMethod(bc, meta);
      this.addStoreMethods(bc, meta);
      this.addNewEmbedded(bc);
      this.addGetData(bc);
      this.decorate(bc, meta);
   }

   protected void decorate(BCClass bc, ClassMetaData meta) {
   }

   private void enhanceConstructor(BCClass bc) {
      BCMethod cons = bc.getDeclaredMethod("<init>", (String[])null);
      Code code = cons.getCode(false);
      code.afterLast();
      code.previous();
      BCField loaded = this.addBeanField(bc, "loaded", BitSet.class);
      loaded.setFinal(true);
      code.aload().setThis();
      code.anew().setType(BitSet.class);
      code.dup();
      code.constant().setValue(bc.getFields().length);
      code.invokespecial().setMethod(BitSet.class, "<init>", Void.TYPE, new Class[]{Integer.TYPE});
      code.putfield().setField(loaded);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addGetType(BCClass bc, ClassMetaData meta) {
      BCField type = bc.declareField("type", Class.class);
      type.setStatic(true);
      type.makePrivate();
      BCMethod getter = bc.declareMethod("getType", Class.class, (Class[])null);
      getter.makePublic();
      Code code = getter.getCode(true);
      code.getstatic().setField(type);
      Collection jumps = new LinkedList();
      jumps.add(code.ifnonnull());
      ExceptionHandler handler = code.addExceptionHandler();
      handler.setTryStart(code.constant().setValue(meta.getDescribedType().getName()));
      code.constant().setValue(true);
      code.invokestatic().setMethod(Thread.class, "currentThread", Thread.class, (Class[])null);
      code.invokevirtual().setMethod(Thread.class, "getContextClassLoader", ClassLoader.class, (Class[])null);
      code.invokestatic().setMethod(Class.class, "forName", Class.class, new Class[]{String.class, Boolean.TYPE, ClassLoader.class});
      code.putstatic().setField(type);
      Instruction go2 = code.go2();
      jumps.add(go2);
      handler.setTryEnd(go2);
      handler.setCatch(ClassNotFoundException.class);
      handler.setHandlerStart(this.throwException(code, InternalException.class));
      this.setTarget(code.getstatic().setField(type), jumps);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addBaseFields(BCClass bc) {
      this.addBeanField(bc, "id", Object.class);
      BCField field = this.addBeanField(bc, "storageGenerator", PCDataGenerator.class);
      field.setAccessFlags(field.getAccessFlags() | 128);
   }

   private void addImplDataMethods(BCClass bc, ClassMetaData meta) {
      BCMethod meth = bc.declareMethod("storeImplData", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      Code code = meth.getCode(true);
      BCField impl = null;
      IfInstruction ifins;
      if (!this.usesImplData(meta)) {
         code.vreturn();
      } else {
         impl = this.addBeanField(bc, "implData", Object.class);
         code.aload().setParam(0);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "isImplDataCacheable", Boolean.TYPE, (Class[])null);
         ifins = code.ifeq();
         code.aload().setThis();
         code.aload().setParam(0);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "getImplData", Object.class, (Class[])null);
         code.invokevirtual().setMethod("setImplData", Void.TYPE, new Class[]{Object.class});
         ifins.setTarget(code.vreturn());
      }

      code.calculateMaxStack();
      code.calculateMaxLocals();
      meth = bc.declareMethod("loadImplData", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      code = meth.getCode(true);
      if (!this.usesImplData(meta)) {
         code.vreturn();
      } else {
         code.aload().setParam(0);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "getImplData", Object.class, (Class[])null);
         ifins = code.ifnonnull();
         code.aload().setThis();
         code.getfield().setField(impl);
         JumpInstruction ifins2 = code.ifnull();
         code.aload().setParam(0);
         code.aload().setThis();
         code.getfield().setField(impl);
         code.constant().setValue(true);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "setImplData", Void.TYPE, new Class[]{Object.class, Boolean.TYPE});
         Instruction ins = code.vreturn();
         ifins.setTarget(ins);
         ifins2.setTarget(ins);
      }

      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addFieldImplDataMethods(BCClass bc, ClassMetaData meta) {
      BCMethod meth = bc.declareMethod("loadImplData", Void.TYPE, new Class[]{OpenJPAStateManager.class, Integer.TYPE});
      meth.makePrivate();
      Code code = meth.getCode(true);
      int count = this.countImplDataFields(meta);
      BCField impl = null;
      LocalVariableInstruction switchTarget;
      int i;
      if (count == 0) {
         code.vreturn();
      } else {
         impl = bc.declareField("fieldImpl", Object[].class);
         impl.makePrivate();
         code.aload().setThis();
         code.getfield().setField(impl);
         JumpInstruction ifins = code.ifnonnull();
         code.vreturn();
         int obj = code.getNextLocalsIndex();
         ifins.setTarget(code.constant().setNull());
         code.astore().setLocal(obj);
         switchTarget = code.aload().setLocal(obj);
         code.previous();
         code.iload().setParam(1);
         LookupSwitchInstruction lswitch = code.lookupswitch();
         FieldMetaData[] fields = meta.getFields();
         i = 0;

         for(int i = 0; i < fields.length; ++i) {
            if (this.usesImplData(fields[i])) {
               lswitch.addCase(i, code.aload().setThis());
               code.getfield().setField(impl);
               code.constant().setValue(i++);
               code.aaload();
               code.astore().setLocal(obj);
               code.go2().setTarget(switchTarget);
            }
         }

         lswitch.setDefaultTarget(switchTarget);
         code.next();
         ifins = code.ifnonnull();
         code.vreturn();
         ifins.setTarget(code.aload().setParam(0));
         code.iload().setParam(1);
         code.aload().setLocal(obj);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "setImplData", Void.TYPE, new Class[]{Integer.TYPE, Object.class});
         code.vreturn();
      }

      code.calculateMaxLocals();
      code.calculateMaxStack();
      meth = bc.declareMethod("storeImplData", Void.TYPE, new Class[]{OpenJPAStateManager.class, Integer.TYPE, Boolean.TYPE});
      code = meth.getCode(true);
      if (count == 0) {
         code.vreturn();
      } else {
         int arrIdx = code.getNextLocalsIndex();
         code.constant().setValue(-1);
         code.istore().setLocal(arrIdx);
         code.iload().setParam(1);
         LookupSwitchInstruction lswitch = code.lookupswitch();
         switchTarget = code.iload().setLocal(arrIdx);
         code.previous();
         FieldMetaData[] fields = meta.getFields();
         int cacheable = 0;

         for(i = 0; i < fields.length; ++i) {
            if (this.usesImplData(fields[i])) {
               lswitch.addCase(i, code.constant().setValue(cacheable++));
               code.istore().setLocal(arrIdx);
               code.go2().setTarget(switchTarget);
            }
         }

         lswitch.setDefaultTarget(switchTarget);
         code.next();
         code.constant().setValue(-1);
         JumpInstruction ifins = code.ificmpne();
         code.vreturn();
         Instruction nullTarget = code.aload().setThis();
         code.previous();
         ifins.setTarget(code.iload().setParam(2));
         code.ifeq().setTarget(nullTarget);
         int obj = code.getNextLocalsIndex();
         code.aload().setParam(0);
         code.iload().setParam(1);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "getImplData", Object.class, new Class[]{Integer.TYPE});
         code.astore().setLocal(obj);
         code.aload().setLocal(obj);
         code.ifnull().setTarget(nullTarget);
         code.aload().setThis();
         code.getfield().setField(impl);
         ifins = code.ifnonnull();
         code.aload().setThis();
         code.constant().setValue(count);
         code.anewarray().setType(Object.class);
         code.putfield().setField(impl);
         ifins.setTarget(code.aload().setThis());
         code.getfield().setField(impl);
         code.iload().setLocal(arrIdx);
         code.aload().setLocal(obj);
         code.aastore();
         code.vreturn();
         code.next();
         code.getfield().setField(impl);
         ifins = code.ifnonnull();
         code.vreturn();
         ifins.setTarget(code.aload().setThis());
         code.getfield().setField(impl);
         code.iload().setLocal(arrIdx);
         code.constant().setNull();
         code.aastore();
         code.vreturn();
      }

      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   protected void addVersionMethods(BCClass bc) {
      this.addBeanField(bc, "version", Object.class);
      BCMethod meth = bc.declareMethod("storeVersion", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      Code code = meth.getCode(true);
      code.aload().setThis();
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getVersion", Object.class, (Class[])null);
      code.putfield().setField("version", Object.class);
      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      meth = bc.declareMethod("loadVersion", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      code = meth.getCode(true);
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getVersion", Object.class, (Class[])null);
      JumpInstruction ifins = code.ifnonnull();
      code.aload().setParam(0);
      code.aload().setThis();
      code.getfield().setField("version", Object.class);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "setVersion", Void.TYPE, new Class[]{Object.class});
      ifins.setTarget(code.vreturn());
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addLoadMethod(BCClass bc, ClassMetaData meta) {
      Code code = this.addLoadMethod(bc, false);
      FieldMetaData[] fmds = meta.getFields();
      Collection jumps = new LinkedList();
      int local = code.getNextLocalsIndex();
      code.constant().setNull();
      code.astore().setLocal(local);
      int inter = code.getNextLocalsIndex();
      code.constant().setNull();
      code.astore().setLocal(inter);
      int objectCount = 0;

      for(int i = 0; i < fmds.length; ++i) {
         Collection jumps2 = new LinkedList();
         boolean intermediate = this.usesIntermediate(fmds[i]);
         this.setTarget(code.aload().setThis(), jumps);
         code.getfield().setField("loaded", BitSet.class);
         code.constant().setValue(i);
         code.invokevirtual().setMethod(BitSet.class, "get", Boolean.TYPE, new Class[]{Integer.TYPE});
         jumps.add(code.ifne());
         if (intermediate) {
            this.addLoadIntermediate(code, i, objectCount, jumps2, inter);
         }

         jumps2.add(code.go2());
         this.setTarget(code.aload().setParam(1), jumps);
         code.aload().setParam(0);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "getMetaData", ClassMetaData.class, (Class[])null);
         code.constant().setValue(fmds[i].getIndex());
         code.invokevirtual().setMethod(ClassMetaData.class, "getField", FieldMetaData.class, new Class[]{Integer.TYPE});
         code.invokeinterface().setMethod(FetchConfiguration.class, "requiresFetch", Integer.TYPE, new Class[]{FieldMetaData.class});
         code.constant().setValue(0);
         jumps2.add(code.ificmpeq());
         this.addLoad(bc, code, fmds[i], objectCount, local, false);
         jumps = jumps2;
         if (this.replaceType(fmds[i]) >= 8) {
            ++objectCount;
         }
      }

      this.setTarget(code.vreturn(), jumps);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addLoadWithFieldsMethod(BCClass bc, ClassMetaData meta) {
      Code code = this.addLoadMethod(bc, true);
      FieldMetaData[] fmds = meta.getFields();
      Collection jumps = new LinkedList();
      int objectCount = 0;
      int local = code.getNextLocalsIndex();
      code.constant().setNull();
      code.astore().setLocal(local);
      int inter = code.getNextLocalsIndex();
      code.constant().setNull();
      code.astore().setLocal(inter);

      for(int i = 0; i < fmds.length; ++i) {
         Collection jumps2 = new LinkedList();
         boolean intermediate = this.usesIntermediate(fmds[i]);
         this.setTarget(code.aload().setParam(1), jumps);
         code.constant().setValue(i);
         code.invokevirtual().setMethod(BitSet.class, "get", Boolean.TYPE, new Class[]{Integer.TYPE});
         jumps2.add(code.ifeq());
         code.aload().setThis();
         code.getfield().setField("loaded", BitSet.class);
         code.constant().setValue(i);
         code.invokevirtual().setMethod(BitSet.class, "get", Boolean.TYPE, new Class[]{Integer.TYPE});
         if (intermediate) {
            jumps.add(code.ifeq());
         } else {
            jumps2.add(code.ifeq());
         }

         this.addLoad(bc, code, fmds[i], objectCount, local, true);
         if (this.usesImplData(fmds[i])) {
            code.aload().setThis();
            code.aload().setParam(0);
            code.constant().setValue(i);
            code.invokevirtual().setMethod("loadImplData", Void.TYPE, new Class[]{OpenJPAStateManager.class, Integer.TYPE});
         }

         code.aload().setParam(1);
         code.constant().setValue(i);
         code.invokevirtual().setMethod(BitSet.class, "clear", Void.TYPE, new Class[]{Integer.TYPE});
         jumps2.add(code.go2());
         if (intermediate) {
            this.setTarget(this.addLoadIntermediate(code, i, objectCount, jumps2, inter), jumps);
         }

         jumps = jumps2;
         if (this.replaceType(fmds[i]) >= 8) {
            ++objectCount;
         }
      }

      this.setTarget(code.vreturn(), jumps);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private Code addLoadMethod(BCClass bc, boolean fields) {
      Class[] args = null;
      if (fields) {
         args = new Class[]{OpenJPAStateManager.class, BitSet.class, FetchConfiguration.class, Object.class};
      } else {
         args = new Class[]{OpenJPAStateManager.class, FetchConfiguration.class, Object.class};
      }

      BCMethod load = bc.declareMethod("load", Void.TYPE, args);
      Code code = load.getCode(true);
      code.aload().setThis();
      code.aload().setParam(0);
      code.invokevirtual().setMethod("loadVersion", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      code.aload().setThis();
      code.aload().setParam(0);
      code.invokevirtual().setMethod("loadImplData", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      return code;
   }

   private Instruction addLoad(BCClass bc, Code code, FieldMetaData fmd, int objectCount, int local, boolean fields) {
      int index = fmd.getIndex();
      int typeCode = this.replaceType(fmd);
      LocalVariableInstruction first;
      if (typeCode < 8) {
         Class type = this.forType(fmd.getTypeCode());
         first = code.aload().setParam(0);
         code.constant().setValue(index);
         code.aload().setThis();
         code.getfield().setField(this.getFieldName(index), type);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "store" + StringUtils.capitalize(type.getName()), Void.TYPE, new Class[]{Integer.TYPE, type});
      } else {
         int offset = fields ? 1 : 0;
         first = code.aload().setParam(0);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "getMetaData", ClassMetaData.class, (Class[])null);
         code.constant().setValue(fmd.getIndex());
         code.invokevirtual().setMethod(ClassMetaData.class, "getField", FieldMetaData.class, new Class[]{Integer.TYPE});
         code.astore().setLocal(local);
         code.aload().setParam(0);
         code.constant().setValue(index);
         code.aload().setThis();
         code.aload().setParam(0);
         code.aload().setLocal(local);
         code.aload().setThis();
         code.getfield().setField("objects", Object[].class);
         code.constant().setValue(objectCount);
         code.aaload();
         code.aload().setParam(1 + offset);
         code.aload().setParam(2 + offset);
         code.invokevirtual().setMethod(bc.getName(), "toField", Object.class.getName(), toStrings(new Class[]{OpenJPAStateManager.class, FieldMetaData.class, Object.class, FetchConfiguration.class, Object.class}));
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "storeField", Void.TYPE, new Class[]{Integer.TYPE, Object.class});
      }

      return first;
   }

   private Instruction addLoadIntermediate(Code code, int index, int objectCount, Collection jumps2, int inter) {
      Instruction first = code.aload().setThis();
      code.getfield().setField("objects", Object[].class);
      code.constant().setValue(objectCount);
      code.aaload();
      code.astore().setLocal(inter);
      code.aload().setLocal(inter);
      jumps2.add(code.ifnull());
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getLoaded", BitSet.class, (Class[])null);
      code.constant().setValue(index);
      code.invokevirtual().setMethod(BitSet.class, "get", Boolean.TYPE, new Class[]{Integer.TYPE});
      jumps2.add(code.ifne());
      code.aload().setParam(0);
      code.constant().setValue(index);
      code.aload().setLocal(inter);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "setIntermediate", Void.TYPE, new Class[]{Integer.TYPE, Object.class});
      return first;
   }

   private void addStoreMethods(BCClass bc, ClassMetaData meta) {
      this.addStoreMethod(bc, meta, true);
      this.addStoreMethod(bc, meta, false);
   }

   private void addStoreMethod(BCClass bc, ClassMetaData meta, boolean fields) {
      BCMethod store;
      if (fields) {
         store = bc.declareMethod("store", Void.TYPE, new Class[]{OpenJPAStateManager.class, BitSet.class});
      } else {
         store = bc.declareMethod("store", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      }

      Code code = store.getCode(true);
      code.aload().setThis();
      code.invokevirtual().setMethod("initialize", Void.TYPE, (Class[])null);
      code.aload().setThis();
      code.aload().setParam(0);
      code.invokevirtual().setMethod("storeVersion", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      code.aload().setThis();
      code.aload().setParam(0);
      code.invokevirtual().setMethod("storeImplData", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      FieldMetaData[] fmds = meta.getFields();
      Collection jumps = new LinkedList();
      int objectCount = 0;

      for(int i = 0; i < fmds.length; ++i) {
         if (fields) {
            this.setTarget(code.aload().setParam(1), jumps);
            jumps.add(code.ifnull());
            code.aload().setParam(1);
            code.constant().setValue(i);
            code.invokevirtual().setMethod(BitSet.class, "get", Boolean.TYPE, new Class[]{Integer.TYPE});
            jumps.add(code.ifeq());
         } else {
            this.setTarget(code.aload().setParam(0), jumps);
            code.invokeinterface().setMethod(OpenJPAStateManager.class, "getLoaded", BitSet.class, (Class[])null);
            code.constant().setValue(i);
            code.invokevirtual().setMethod(BitSet.class, "get", Boolean.TYPE, new Class[]{Integer.TYPE});
            jumps.add(code.ifeq());
         }

         this.addStore(bc, code, fmds[i], objectCount);
         if (this.usesIntermediate(fmds[i])) {
            JumpInstruction elseIns = code.go2();
            this.setTarget(code.aload().setThis(), jumps);
            jumps.add(elseIns);
            code.getfield().setField("loaded", BitSet.class);
            code.constant().setValue(i);
            code.invokevirtual().setMethod(BitSet.class, "get", Boolean.TYPE, new Class[]{Integer.TYPE});
            jumps.add(code.ifne());
            code.aload().setParam(0);
            code.constant().setValue(i);
            code.invokeinterface().setMethod(OpenJPAStateManager.class, "getIntermediate", Object.class, new Class[]{Integer.TYPE});
            int local = code.getNextLocalsIndex();
            code.astore().setLocal(local);
            code.aload().setLocal(local);
            jumps.add(code.ifnull());
            code.aload().setThis();
            code.getfield().setField("objects", Object[].class);
            code.constant().setValue(objectCount);
            code.aload().setLocal(local);
            code.aastore();
         }

         if (this.replaceType(fmds[i]) >= 8) {
            ++objectCount;
         }
      }

      this.setTarget(code.vreturn(), jumps);
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private void addStore(BCClass bc, Code code, FieldMetaData fmd, int objectCount) {
      int typeCode = this.replaceType(fmd);
      int index = fmd.getIndex();
      if (typeCode < 8) {
         Class type = this.forType(typeCode);
         code.aload().setThis();
         code.aload().setParam(0);
         code.constant().setValue(index);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "fetch" + StringUtils.capitalize(type.getName()), type, new Class[]{Integer.TYPE});
         code.putfield().setField(this.getFieldName(index), type);
         code.aload().setThis();
         code.getfield().setField("loaded", BitSet.class);
         code.constant().setValue(index);
         code.invokevirtual().setMethod(BitSet.class, "set", Void.TYPE, new Class[]{Integer.TYPE});
      } else {
         int local = code.getNextLocalsIndex();
         code.aload().setThis();
         code.aload().setParam(0);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "getMetaData", ClassMetaData.class, (Class[])null);
         code.constant().setValue(fmd.getIndex());
         code.invokevirtual().setMethod(ClassMetaData.class, "getField", FieldMetaData.class, new Class[]{Integer.TYPE});
         code.aload().setParam(0);
         code.constant().setValue(fmd.getIndex());
         code.constant().setValue(false);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "fetchField", Object.class, new Class[]{Integer.TYPE, Boolean.TYPE});
         code.aload().setParam(0);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "getContext", StoreContext.class, (Class[])null);
         code.invokevirtual().setMethod(bc.getName(), "toData", Object.class.getName(), toStrings(new Class[]{FieldMetaData.class, Object.class, StoreContext.class}));
         code.astore().setLocal(local);
         code.aload().setLocal(local);
         code.getstatic().setField(AbstractPCData.class, "NULL", Object.class);
         JumpInstruction ifins = code.ifacmpne();
         code.constant().setNull();
         code.astore().setLocal(local);
         code.aload().setThis();
         code.getfield().setField("loaded", BitSet.class);
         code.constant().setValue(index);
         code.invokevirtual().setMethod(BitSet.class, "clear", Void.TYPE, new Class[]{Integer.TYPE});
         JumpInstruction go2 = code.go2();
         ifins.setTarget(code.aload().setThis());
         code.getfield().setField("loaded", BitSet.class);
         code.constant().setValue(index);
         code.invokevirtual().setMethod(BitSet.class, "set", Void.TYPE, new Class[]{Integer.TYPE});
         go2.setTarget(code.aload().setThis());
         code.getfield().setField("objects", Object[].class);
         code.constant().setValue(objectCount);
         code.aload().setLocal(local);
         code.aastore();
      }

      if (this.usesImplData(fmd)) {
         code.aload().setThis();
         code.aload().setParam(0);
         code.constant().setValue(index);
         code.aload().setThis();
         code.getfield().setField("loaded", BitSet.class);
         code.constant().setValue(index);
         code.invokevirtual().setMethod(BitSet.class, "get", Boolean.TYPE, new Class[]{Integer.TYPE});
         code.invokevirtual().setMethod("storeImplData", Void.TYPE, new Class[]{OpenJPAStateManager.class, Integer.TYPE, Boolean.TYPE});
      }
   }

   private void addNewEmbedded(BCClass bc) {
      BCMethod meth = bc.declareMethod("newEmbeddedPCData", PCData.class, new Class[]{OpenJPAStateManager.class});
      Code code = meth.getCode(true);
      code.aload().setThis();
      code.getfield().setField("storageGenerator", PCDataGenerator.class);
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getId", Object.class, (Class[])null);
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getMetaData", ClassMetaData.class, (Class[])null);
      code.invokevirtual().setMethod(PCDataGenerator.class, "generatePCData", PCData.class, new Class[]{Object.class, ClassMetaData.class});
      code.areturn();
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private void addGetData(BCClass bc) {
      BCMethod method = bc.declareMethod("getData", Object.class, new Class[]{Integer.TYPE});
      Code code = method.getCode(true);
      code.aload().setThis();
      code.iload().setParam(0);
      code.invokevirtual().setMethod("getObject", Object.class, new Class[]{Integer.TYPE});
      code.areturn();
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   protected int replaceType(FieldMetaData fmd) {
      return this.usesIntermediate(fmd) ? 8 : fmd.getTypeCode();
   }

   protected boolean usesIntermediate(FieldMetaData fmd) {
      return fmd.usesIntermediate();
   }

   protected boolean usesImplData(ClassMetaData meta) {
      return true;
   }

   protected boolean usesImplData(FieldMetaData fmd) {
      return fmd.usesImplData() == null;
   }

   private int countImplDataFields(ClassMetaData meta) {
      FieldMetaData[] fmds = meta.getFields();
      int count = 0;

      for(int i = 0; i < fmds.length; ++i) {
         if (this.usesImplData(fmds[i])) {
            ++count;
         }
      }

      return count;
   }

   protected void callAbstractPCData(BCClass bc, String name, Class retType, Class[] args) {
      BCMethod meth = bc.declareMethod(name, retType, args);
      Code code = meth.getCode(true);
      code.aload().setThis();

      for(int i = 0; i < args.length; ++i) {
         code.xload().setParam(i).setType(args[i]);
      }

      code.invokevirtual().setMethod(AbstractPCData.class, name, retType, args);
      if (!Void.TYPE.equals(retType)) {
         code.xreturn().setType(retType);
      }

      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   protected void setTarget(Instruction ins, Collection jumps) {
      Iterator it = jumps.iterator();

      while(it.hasNext()) {
         ((JumpInstruction)it.next()).setTarget(ins);
      }

      jumps.clear();
   }

   private static String[] toStrings(Class[] cls) {
      String[] strings = new String[cls.length];

      for(int i = 0; i < strings.length; ++i) {
         strings[i] = cls[i].getName();
      }

      return strings;
   }

   public interface DynamicPCData extends PCData {
      void setId(Object var1);

      PCDataGenerator getStorageGenerator();

      void setStorageGenerator(PCDataGenerator var1);
   }
}
