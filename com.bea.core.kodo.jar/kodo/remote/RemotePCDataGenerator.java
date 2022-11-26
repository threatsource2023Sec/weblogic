package kodo.remote;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.BitSet;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.PCDataGenerator;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.LockManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCData;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.ValueMetaData;
import serp.bytecode.BCClass;
import serp.bytecode.BCField;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;
import serp.bytecode.Instruction;
import serp.bytecode.JumpInstruction;
import serp.util.Strings;

class RemotePCDataGenerator extends PCDataGenerator {
   public static final String POSTFIX = "remote";
   private static final String[] EXTERN_NAMES = new String[]{"id", "version", "objects", "loaded", "remoteFlush", "lockLevel", "newInstance"};
   private static final Class[] EXTERN_TYPES;

   public RemotePCDataGenerator(OpenJPAConfiguration conf) {
      super(conf);
   }

   protected String getUniqueName(Class type) {
      return super.getUniqueName(type) + "remote";
   }

   protected void declareClasses(BCClass bc) {
      super.declareClasses(bc);
      bc.declareInterface(RemotePCData.class);
      bc.declareInterface(Externalizable.class);
   }

   protected void decorate(BCClass bc, ClassMetaData meta) {
      this.addRemoteFields(bc);
      this.enhanceLoadVersionMethod(bc);
      this.enhanceStoreMethod(bc, true);
      this.enhanceStoreMethod(bc, false);
      this.addLoadLockLevel(bc);
      this.enhanceLoad(bc, meta);
      this.enhanceLoadWithFields(bc);
      this.enhanceNewEmbeddedPCData(bc);
      this.overrideToRelationField(bc);
      this.overrideToRelationData(bc);
      this.addExternalization(bc, meta);
   }

   protected boolean usesIntermediate(FieldMetaData fmd) {
      return false;
   }

   protected boolean usesImplData(ClassMetaData meta) {
      return false;
   }

   protected boolean usesImplData(FieldMetaData fmd) {
      return false;
   }

   private void addRemoteFields(BCClass bc) {
      this.addBeanField(bc, "remoteFlush", Boolean.TYPE);
      this.addBeanField(bc, "lockLevel", Integer.TYPE);
      this.addBeanField(bc, "newInstance", Boolean.TYPE);
   }

   private void enhanceLoadVersionMethod(BCClass bc) {
      BCMethod meth = bc.getDeclaredMethod("loadVersion", new Class[]{OpenJPAStateManager.class});
      Code code = meth.getCode(false);
      code.beforeFirst();
      code.aload().setThis();
      code.getfield().setField("newInstance", Boolean.TYPE);
      JumpInstruction notNew = code.ifeq();
      code.aload().setThis();
      code.getfield().setField("remoteFlush", Boolean.TYPE);
      JumpInstruction remote = code.ifne();
      code.aload().setParam(0);
      code.aload().setThis();
      code.getfield().setField("version", Object.class);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "setNextVersion", Void.TYPE, new Class[]{Object.class});
      code.vreturn();
      Instruction ins = code.aload().setThis();
      notNew.setTarget(ins);
      remote.setTarget(ins);
      code.getfield().setField("remoteFlush", Boolean.TYPE);
      JumpInstruction notRemote = code.ifeq();
      code.aload().setParam(0);
      code.aload().setThis();
      code.getfield().setField("version", Object.class);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "setVersion", Void.TYPE, new Class[]{Object.class});
      code.vreturn();
      notRemote.setTarget(code.next());
      code.calculateMaxStack();
   }

   private void enhanceStoreMethod(BCClass bc, boolean fields) {
      Class[] args = null;
      if (fields) {
         args = new Class[]{OpenJPAStateManager.class, BitSet.class};
      } else {
         args = new Class[]{OpenJPAStateManager.class};
      }

      BCMethod meth = bc.getDeclaredMethod("store", args);
      Code code = meth.getCode(false);
      code.beforeFirst();
      code.aload().setThis();
      code.getfield().setField("remoteFlush", Boolean.TYPE);
      JumpInstruction ifins = code.ifne();
      code.aload().setThis();
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getContext", StoreContext.class, (Class[])null);
      code.invokeinterface().setMethod(StoreContext.class, "getLockManager", LockManager.class, (Class[])null);
      code.aload().setParam(0);
      code.invokeinterface().setMethod(LockManager.class, "getLockLevel", Integer.TYPE, new Class[]{OpenJPAStateManager.class});
      code.putfield().setField("lockLevel", Integer.TYPE);
      ifins.setTarget(code.next());
      code.calculateMaxStack();
   }

   private void addLoadLockLevel(BCClass bc) {
      BCMethod meth = bc.declareMethod("loadLockLevel", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      Code code = meth.getCode(true);
      int local = code.getNextLocalsIndex();
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getContext", StoreContext.class, (Class[])null);
      code.invokeinterface().setMethod(StoreContext.class, "getLockManager", LockManager.class, (Class[])null);
      code.astore().setLocal(local);
      code.aload().setLocal(local);
      code.isinstance().setType(ClientLockManager.class);
      JumpInstruction ifins = code.ifeq();
      code.aload().setLocal(local);
      code.checkcast().setType(ClientLockManager.class);
      code.aload().setParam(0);
      code.aload().setThis();
      code.getfield().setField("lockLevel", Integer.TYPE);
      code.invokevirtual().setMethod(ClientLockManager.class, "serverLocked", Void.TYPE, new Class[]{OpenJPAStateManager.class, Integer.TYPE});
      ifins.setTarget(code.vreturn());
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void enhanceLoad(BCClass bc, ClassMetaData meta) {
      BCMethod meth = bc.getDeclaredMethod("load", new Class[]{OpenJPAStateManager.class, FetchConfiguration.class, Object.class});
      Code code = meth.getCode(false);
      code.beforeFirst();
      code.aload().setThis();
      code.getfield().setField("remoteFlush", Boolean.TYPE);
      JumpInstruction ifins = code.ifeq();
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getVersion", Object.class, (Class[])null);
      JumpInstruction ifins2 = code.ifnull();
      code.aload().setThis();
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getVersion", Object.class, (Class[])null);
      code.putfield().setField("version", Object.class);
      FieldMetaData[] fmds = meta.getFields();
      BCField loaded = bc.getDeclaredField("loaded");
      int objectCount = 0;
      int local = code.getNextLocalsIndex();
      ifins2.setTarget(code.aload().setParam(0));
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getMetaData", ClassMetaData.class, (Class[])null);
      code.astore().setLocal(local);
      ifins2 = null;

      for(int i = 0; i < fmds.length; ++i) {
         int type = this.replaceType(fmds[i]);
         Instruction ins = code.aload().setThis();
         if (ifins2 != null) {
            ifins2.setTarget(ins);
         }

         code.getfield().setField(loaded);
         code.constant().setValue(i);
         code.invokevirtual().setMethod(BitSet.class, "get", Boolean.TYPE, new Class[]{Integer.TYPE});
         ifins2 = code.ifeq();
         code.aload().setParam(0);
         if (type >= 8) {
            code.constant().setValue(i);
            code.aload().setThis();
            code.aload().setParam(0);
            code.aload().setLocal(local);
            code.constant().setValue(i);
            code.invokevirtual().setMethod(ClassMetaData.class, "getField", FieldMetaData.class, new Class[]{Integer.TYPE});
            code.aload().setThis();
            code.getfield().setField("objects", Object[].class);
            code.constant().setValue(objectCount);
            code.aaload();
            code.aload().setParam(1);
            code.aload().setParam(2);
            code.invokevirtual().setMethod("toField", Object.class, new Class[]{OpenJPAStateManager.class, FieldMetaData.class, Object.class, FetchConfiguration.class, Object.class});
            code.invokeinterface().setMethod(OpenJPAStateManager.class, "setRemote", Void.TYPE, new Class[]{Integer.TYPE, Object.class});
            ++objectCount;
         } else {
            code.aload().setParam(0);
            code.invokeinterface().setMethod(OpenJPAStateManager.class, "getPersistenceCapable", PersistenceCapable.class, (Class[])null);
            code.constant().setValue(i);
            switch (type) {
               case 3:
                  code.constant().setValue(0.0);
                  break;
               case 4:
                  code.constant().setValue(0.0F);
                  break;
               case 5:
               default:
                  code.constant().setValue(0);
                  break;
               case 6:
                  code.constant().setValue(0L);
            }

            Class fieldType = this.forType(type);
            code.aload().setThis();
            code.getfield().setField(this.getFieldName(i), fieldType);
            code.constant().setValue(1);
            code.invokeinterface().setMethod(StateManager.class, "setting" + StringUtils.capitalise(fieldType.getName()) + "Field", Void.TYPE, new Class[]{PersistenceCapable.class, Integer.TYPE, fieldType, fieldType, Integer.TYPE});
         }
      }

      Instruction ins = code.vreturn();
      if (ifins2 != null) {
         ifins2.setTarget(ins);
      }

      ifins.setTarget(code.aload().setThis());
      code.aload().setParam(0);
      code.invokevirtual().setMethod("loadLockLevel", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void enhanceLoadWithFields(BCClass bc) {
      BCMethod meth = bc.getDeclaredMethod("load", new Class[]{OpenJPAStateManager.class, BitSet.class, FetchConfiguration.class, Object.class});
      Code code = meth.getCode(false);
      code.beforeFirst();
      code.aload().setThis();
      code.getfield().setField("remoteFlush", Boolean.TYPE);
      JumpInstruction ifins = code.ifeq();
      code.aload().setThis();
      code.aload().setParam(0);
      code.aload().setParam(2);
      code.aload().setParam(3);
      code.invokevirtual().setMethod("load", Void.TYPE, new Class[]{OpenJPAStateManager.class, FetchConfiguration.class, Object.class});
      code.vreturn();
      ifins.setTarget(code.aload().setThis());
      code.aload().setParam(0);
      code.invokevirtual().setMethod("loadLockLevel", Void.TYPE, new Class[]{OpenJPAStateManager.class});
      code.calculateMaxStack();
   }

   private void enhanceNewEmbeddedPCData(BCClass bc) {
      BCMethod meth = bc.getDeclaredMethod("newEmbeddedPCData", new Class[]{OpenJPAStateManager.class});
      Code code = meth.getCode(false);
      code.afterLast();
      code.previous();
      int local = code.getNextLocalsIndex();
      code.astore().setLocal(local);
      code.aload().setLocal(local);
      code.checkcast().setType(RemotePCData.class);
      code.aload().setThis();
      code.getfield().setField("remoteFlush", Boolean.TYPE);
      code.invokeinterface().setMethod(RemotePCData.class, "setRemoteFlush", Void.TYPE, new Class[]{Boolean.TYPE});
      code.aload().setLocal(local);
      code.checkcast().setType(RemotePCData.class);
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "isNew", Boolean.TYPE, (Class[])null);
      JumpInstruction ifeq = code.ifeq();
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getObjectId", Object.class, (Class[])null);
      JumpInstruction ifnonnull = code.ifnonnull();
      code.constant().setValue(1);
      JumpInstruction go2 = code.go2();
      Instruction flse = code.constant().setValue(0);
      ifeq.setTarget(flse);
      ifnonnull.setTarget(flse);
      go2.setTarget(code.invokeinterface().setMethod(RemotePCData.class, "setNewInstance", Void.TYPE, new Class[]{Boolean.TYPE}));
      code.aload().setLocal(local);
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private void overrideToRelationField(BCClass bc) {
      BCMethod meth = bc.declareMethod("toRelationField", Object.class, new Class[]{OpenJPAStateManager.class, ValueMetaData.class, Object.class, FetchConfiguration.class, Object.class});
      Code code = meth.getCode(true);
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getContext", StoreContext.class, (Class[])null);
      int ctx = code.getNextLocalsIndex();
      code.astore().setLocal(ctx);
      code.aload().setLocal(ctx);
      code.aload().setParam(2);
      code.aload().setParam(3);
      code.constant().setNull();
      code.aload().setParam(4);
      code.aload().setThis();
      code.getfield().setField("remoteFlush", Boolean.TYPE);
      JumpInstruction ifeq = code.ifeq();
      code.constant().setValue(16);
      JumpInstruction go2 = code.go2();
      ifeq.setTarget(code.constant().setValue(0));
      go2.setTarget(code.invokeinterface().setMethod(StoreContext.class, "find", Object.class, new Class[]{Object.class, FetchConfiguration.class, BitSet.class, Object.class, Integer.TYPE}));
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void overrideToRelationData(BCClass bc) {
      BCMethod meth = bc.declareMethod("toRelationData", Object.class, new Class[]{Object.class, StoreContext.class});
      Code code = meth.getCode(true);
      code.aload().setThis();
      code.getfield().setField("remoteFlush", Boolean.TYPE);
      JumpInstruction ifRemoteFlush = code.ifne();
      code.aload().setParam(1);
      code.aload().setParam(0);
      code.invokeinterface().setMethod(StoreContext.class, "getObjectId", Object.class, new Class[]{Object.class});
      code.areturn();
      ifRemoteFlush.setTarget(code.aload().setParam(1));
      code.aload().setParam(0);
      code.invokeinterface().setMethod(StoreContext.class, "getStateManager", OpenJPAStateManager.class, new Class[]{Object.class});
      int sm = code.getNextLocalsIndex();
      code.astore().setLocal(sm);
      code.aload().setLocal(sm);
      JumpInstruction ifnonnull = code.ifnonnull();
      code.constant().setNull();
      code.areturn();
      ifnonnull.setTarget(code.aload().setLocal(sm));
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getObjectId", Object.class, (Class[])null);
      JumpInstruction ifObjectId = code.ifnonnull();
      code.aload().setLocal(sm);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getId", Object.class, (Class[])null);
      code.areturn();
      ifObjectId.setTarget(code.aload().setLocal(sm));
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getObjectId", Object.class, (Class[])null);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addExternalization(BCClass bc, ClassMetaData meta) {
      this.addWriteReplace(bc);
      this.addExternalizationRead(bc, meta);
      this.addExternalizationWrite(bc, meta);
   }

   private void addWriteReplace(BCClass bc) {
      BCMethod method = bc.declareMethod("writeReplace", Object.class, (Class[])null);
      Code code = method.getCode(true);
      code.anew().setType(PCDataTemplate.class);
      code.dup();
      code.aload().setThis();
      code.aload().setThis();
      code.invokevirtual().setMethod("getType", Class.class, (Class[])null);
      code.invokespecial().setMethod(PCDataTemplate.class, "<init>", Void.TYPE, new Class[]{PCData.class, Class.class});
      code.areturn();
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private void addExternalizationRead(BCClass bc, ClassMetaData meta) {
      BCMethod method = bc.declareMethod("readExternal", Void.TYPE, new Class[]{ObjectInput.class});
      method.getExceptions(true).addException(IOException.class);
      method.makePublic();
      Code code = method.getCode(true);

      for(int i = 0; i < EXTERN_NAMES.length; ++i) {
         this.addRead(code, EXTERN_NAMES[i], EXTERN_TYPES[i]);
      }

      FieldMetaData[] fmds = meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         int type = this.replaceType(fmds[i]);
         if (type < 8) {
            this.addRead(code, this.getFieldName(i), this.forType(type));
         }
      }

      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addRead(Code code, String name, Class type) {
      Class orig = type;
      code.aload().setThis();
      code.aload().setParam(0);
      if (!type.isPrimitive()) {
         type = Object.class;
      }

      String typeName = type.isPrimitive() ? Strings.getClassName(type) : "Object";
      code.invokeinterface().setMethod(ObjectInput.class, "read" + StringUtils.capitalise(typeName), type, (Class[])null);
      if (!orig.isPrimitive() && !Object.class.equals(orig)) {
         code.checkcast().setType(orig);
      }

      code.putfield().setField(name, orig);
   }

   private void addExternalizationWrite(BCClass bc, ClassMetaData meta) {
      BCMethod method = bc.declareMethod("writeExternal", Void.TYPE, new Class[]{ObjectOutput.class});
      method.getExceptions(true).addException(IOException.class);
      method.makePublic();
      Code code = method.getCode(true);

      for(int i = 0; i < EXTERN_NAMES.length; ++i) {
         this.addWrite(code, EXTERN_NAMES[i], EXTERN_TYPES[i]);
      }

      FieldMetaData[] fmds = meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         int type = this.replaceType(fmds[i]);
         if (type < 8) {
            this.addWrite(code, this.getFieldName(i), this.forType(type));
         }
      }

      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addWrite(Code code, String name, Class type) {
      Class orig = type;
      if (!type.isPrimitive()) {
         type = Object.class;
      }

      Class arg = type;
      if (Byte.TYPE.equals(type) || Short.TYPE.equals(type) || Character.TYPE.equals(type)) {
         arg = Integer.TYPE;
      }

      code.aload().setParam(0);
      code.aload().setThis();
      code.getfield().setField(name, orig);
      String typeName = type.isPrimitive() ? Strings.getClassName(type) : "Object";
      code.invokeinterface().setMethod(ObjectOutput.class, "write" + StringUtils.capitalise(typeName), Void.TYPE, new Class[]{arg});
   }

   static {
      EXTERN_TYPES = new Class[]{Object.class, Object.class, Object[].class, BitSet.class, Boolean.TYPE, Integer.TYPE, Boolean.TYPE};
   }

   public static class PCDataTemplate implements Externalizable, Serializable {
      PCData data;
      Class cls;

      public PCDataTemplate() {
      }

      public PCDataTemplate(PCData data, Class cls) {
         this.data = data;
         this.cls = cls;
      }

      public void writeExternal(ObjectOutput os) throws IOException {
         os.writeObject(this.cls);
         ((Externalizable)this.data).writeExternal(os);
      }

      public void readExternal(ObjectInput is) throws IOException, ClassNotFoundException {
         this.cls = (Class)is.readObject();
         PCDataGenerator gen = ((PCDataGeneratorObjectInputStream)is).getPCDataGenerator();
         OpenJPAConfiguration conf = gen.getConfiguration();
         MetaDataRepository repos = conf.getMetaDataRepositoryInstance();
         ClassMetaData meta = repos.getMetaData(this.cls, (ClassLoader)null, true);
         this.data = gen.generatePCData((Object)null, meta);
         ((Externalizable)this.data).readExternal(is);
         ((PCDataGenerator.DynamicPCData)this.data).setStorageGenerator(gen);
      }

      public Object readResolve() {
         return this.data;
      }
   }
}
