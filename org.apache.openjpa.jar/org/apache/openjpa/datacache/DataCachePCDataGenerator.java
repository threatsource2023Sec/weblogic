package org.apache.openjpa.datacache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.PCDataGenerator;
import org.apache.openjpa.kernel.AbstractPCData;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import serp.bytecode.BCClass;
import serp.bytecode.BCField;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;
import serp.bytecode.Instruction;
import serp.bytecode.JumpInstruction;

public class DataCachePCDataGenerator extends PCDataGenerator {
   public static final String POSTFIX = "datacache";
   private static final Set _synchs = new HashSet(Arrays.asList("getData", "setData", "clearData", "getImplData", "setImplData", "setIntermediate", "getIntermediate", "isLoaded", "setLoaded", "setVersion", "getVersion", "store"));

   public DataCachePCDataGenerator(OpenJPAConfiguration conf) {
      super(conf);
   }

   protected String getUniqueName(Class type) {
      return super.getUniqueName(type) + "datacache";
   }

   protected void finish(PCDataGenerator.DynamicPCData data, ClassMetaData meta) {
      int timeout = meta.getDataCacheTimeout();
      if (timeout > 0) {
         ((Timed)data).setTimeout((long)timeout + System.currentTimeMillis());
      } else {
         ((Timed)data).setTimeout(-1L);
      }

   }

   protected void decorate(BCClass bc, ClassMetaData meta) {
      this.enhanceToData(bc);
      this.enhanceToNestedData(bc);
      this.replaceNewEmbeddedPCData(bc);
      this.addSynchronization(bc);
      this.addTimeout(bc);
   }

   private void enhanceToData(BCClass bc) {
      BCMethod meth = bc.declareMethod("toData", Object.class, new Class[]{FieldMetaData.class, Object.class, StoreContext.class});
      Code code = meth.getCode(true);
      code.aload().setParam(0);
      code.invokevirtual().setMethod(FieldMetaData.class, "isLRS", Boolean.TYPE, (Class[])null);
      JumpInstruction ifins = code.ifeq();
      code.getstatic().setField(AbstractPCData.class, "NULL", Object.class);
      code.areturn();
      ifins.setTarget(code.aload().setThis());
      code.aload().setParam(0);
      code.aload().setParam(1);
      code.aload().setParam(2);
      code.invokespecial().setMethod(AbstractPCData.class, "toData", Object.class, new Class[]{FieldMetaData.class, Object.class, StoreContext.class});
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void enhanceToNestedData(BCClass bc) {
      BCMethod meth = bc.declareMethod("toNestedData", Object.class, new Class[]{ValueMetaData.class, Object.class, StoreContext.class});
      Code code = meth.getCode(true);
      code.aload().setParam(1);
      JumpInstruction ifins = code.ifnonnull();
      code.constant().setNull();
      code.areturn();
      ifins.setTarget(code.aload().setParam(0));
      code.invokeinterface().setMethod(ValueMetaData.class, "getDeclaredTypeCode", Integer.TYPE, (Class[])null);
      int local = code.getNextLocalsIndex();
      code.istore().setLocal(local);
      Collection jumps = new ArrayList(3);
      code.iload().setLocal(local);
      code.constant().setValue(12);
      jumps.add(code.ificmpeq());
      code.iload().setLocal(local);
      code.constant().setValue(13);
      jumps.add(code.ificmpeq());
      code.iload().setLocal(local);
      code.constant().setValue(11);
      jumps.add(code.ificmpeq());
      code.aload().setThis();
      code.aload().setParam(0);
      code.aload().setParam(1);
      code.aload().setParam(2);
      code.invokespecial().setMethod(AbstractPCData.class, "toNestedData", Object.class, new Class[]{ValueMetaData.class, Object.class, StoreContext.class});
      code.areturn();
      this.setTarget(code.getstatic().setField(AbstractPCData.class, "NULL", Object.class), jumps);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void replaceNewEmbeddedPCData(BCClass bc) {
      BCMethod meth = bc.declareMethod("newEmbeddedPCData", AbstractPCData.class, new Class[]{OpenJPAStateManager.class});
      Code code = meth.getCode(true);
      code.anew().setType(DataCachePCDataImpl.class);
      code.dup();
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getId", Object.class, (Class[])null);
      code.aload().setParam(0);
      code.invokeinterface().setMethod(OpenJPAStateManager.class, "getMetaData", ClassMetaData.class, (Class[])null);
      code.invokespecial().setMethod(DataCachePCDataImpl.class, "<init>", Void.TYPE, new Class[]{Object.class, ClassMetaData.class});
      code.areturn();
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private void addTimeout(BCClass bc) {
      bc.declareInterface(DataCachePCData.class);
      bc.declareInterface(Timed.class);
      BCField field = this.addBeanField(bc, "timeout", Long.TYPE);
      BCMethod meth = bc.declareMethod("isTimedOut", Boolean.TYPE, (Class[])null);
      Code code = meth.getCode(true);
      code.aload().setThis();
      code.getfield().setField(field);
      code.constant().setValue(-1L);
      code.lcmp();
      JumpInstruction ifneg = code.ifeq();
      code.aload().setThis();
      code.getfield().setField(field);
      code.invokestatic().setMethod(System.class, "currentTimeMillis", Long.TYPE, (Class[])null);
      code.lcmp();
      JumpInstruction ifnexp = code.ifge();
      code.constant().setValue(1);
      JumpInstruction go2 = code.go2();
      Instruction flse = code.constant().setValue(0);
      ifneg.setTarget(flse);
      ifnexp.setTarget(flse);
      go2.setTarget(code.ireturn());
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addSynchronization(BCClass bc) {
      BCMethod[] methods = bc.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].isPublic() && _synchs.contains(methods[i].getName())) {
            methods[i].setSynchronized(true);
         }
      }

      BCMethod method = bc.declareMethod("isLoaded", Boolean.TYPE, new Class[]{Integer.TYPE});
      method.setSynchronized(true);
      Code code = method.getCode(true);
      code.aload().setThis();
      code.iload().setParam(0);
      code.invokespecial().setMethod(AbstractPCData.class, "isLoaded", Boolean.TYPE, new Class[]{Integer.TYPE});
      code.calculateMaxLocals();
      code.calculateMaxStack();
      code.ireturn();
   }

   public interface Timed {
      void setTimeout(long var1);
   }
}
