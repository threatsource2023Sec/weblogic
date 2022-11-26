package kodo.jdo;

import java.util.HashSet;
import java.util.Set;
import javax.jdo.PersistenceManager;
import javax.jdo.spi.PersistenceCapable;
import javax.jdo.spi.StateManager;
import org.apache.openjpa.enhance.PCEnhancer;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.meta.ClassMetaData;
import serp.bytecode.BCClass;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;
import serp.bytecode.JumpInstruction;

public class JDOEnhancer implements PCEnhancer.AuxiliaryEnhancer {
   private static final Class JDOPC = PersistenceCapable.class;
   private static final Class JDOSM = StateManager.class;
   private static final Class OIDFS = PersistenceCapable.ObjectIdFieldSupplier.class;
   private static final Class OIDFC = PersistenceCapable.ObjectIdFieldConsumer.class;
   private static final Set UNENHANCED_METHODS = new HashSet();

   public void run(BCClass bc, ClassMetaData meta) {
      if (meta.getPCSuperclass() == null) {
         bc.declareInterface(JDOPC);
         BCMethod meth = bc.declareMethod("jdoGetPersistenceManager", PersistenceManager.class, (Class[])null);
         Code code = meth.getCode(true);
         code.aload().setThis();
         code.invokeinterface().setMethod(org.apache.openjpa.enhance.PersistenceCapable.class, "pcGetGenericContext", Object.class, (Class[])null);
         code.checkcast().setType(Broker.class);
         code.invokestatic().setMethod(KodoJDOHelper.class, "toPersistenceManager", KodoPersistenceManager.class, new Class[]{Broker.class});
         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
         this.throwException(bc, "jdoReplaceStateManager", Void.TYPE, new Class[]{JDOSM});
         this.throwException(bc, "jdoProvideField", Void.TYPE, new Class[]{Integer.TYPE});
         this.throwException(bc, "jdoProvideFields", Void.TYPE, new Class[]{int[].class});
         this.throwException(bc, "jdoReplaceField", Void.TYPE, new Class[]{Integer.TYPE});
         this.throwException(bc, "jdoReplaceFields", Void.TYPE, new Class[]{int[].class});
         this.throwException(bc, "jdoReplaceFlags", Void.TYPE, (Class[])null);
         this.throwException(bc, "jdoCopyFields", Void.TYPE, new Class[]{Object.class, int[].class});
         this.throwException(bc, "jdoNewInstance", JDOPC, new Class[]{JDOSM});
         this.throwException(bc, "jdoNewInstance", JDOPC, new Class[]{JDOSM, Object.class});
         this.throwException(bc, "jdoNewObjectIdInstance", Object.class, (Class[])null);
         this.throwException(bc, "jdoNewObjectIdInstance", Object.class, new Class[]{Object.class});
         this.throwException(bc, "jdoCopyKeyFieldsToObjectId", Void.TYPE, new Class[]{Object.class});
         this.throwException(bc, "jdoCopyKeyFieldsToObjectId", Void.TYPE, new Class[]{OIDFS, Object.class});
         this.throwException(bc, "jdoCopyKeyFieldsFromObjectId", Void.TYPE, new Class[]{OIDFC, Object.class});
         this.delegate(bc, "GetVersion", Object.class, (Class[])null);
         this.delegate(bc, "IsDirty", Boolean.TYPE, (Class[])null);
         this.delegate(bc, "IsTransactional", Boolean.TYPE, (Class[])null);
         this.delegate(bc, "IsPersistent", Boolean.TYPE, (Class[])null);
         this.delegate(bc, "IsNew", Boolean.TYPE, (Class[])null);
         this.delegate(bc, "IsDeleted", Boolean.TYPE, (Class[])null);
         meth = bc.declareMethod("jdoIsDetached", Boolean.TYPE, (Class[])null);
         code = meth.getCode(true);
         code.aload().setThis();
         code.invokevirtual().setMethod("pcIsDetached", Boolean.class, (Class[])null);
         int detached = code.getNextLocalsIndex();
         code.astore().setLocal(detached);
         code.aload().setLocal(detached);
         JumpInstruction ifnonnull = code.ifnonnull();
         code.constant().setValue(false);
         code.ireturn();
         ifnonnull.setTarget(code.aload().setLocal(detached));
         code.invokevirtual().setMethod(Boolean.class, "booleanValue", Boolean.TYPE, (Class[])null);
         code.ireturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
         meth = bc.declareMethod("jdoMakeDirty", Void.TYPE, new Class[]{String.class});
         code = meth.getCode(true);
         code.aload().setThis();
         code.aload().setParam(0);
         code.invokevirtual().setMethod("pcDirty", Void.TYPE, new Class[]{String.class});
         code.vreturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
         meth = bc.declareMethod("jdoGetObjectId", Object.class, (Class[])null);
         code = meth.getCode(true);
         code.aload().setThis();
         code.invokevirtual().setMethod("pcFetchObjectId", Object.class, (Class[])null);
         code.invokestatic().setMethod(KodoJDOHelper.class, "fromKodoObjectId", Object.class, new Class[]{Object.class});
         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
         meth = bc.declareMethod("jdoGetTransactionalObjectId", Object.class, (Class[])null);
         code = meth.getCode(true);
         code.aload().setThis();
         code.invokevirtual().setMethod("jdoGetObjectId", Object.class, (Class[])null);
         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
         meth = bc.getDeclaredMethod("pcisDetachedStateDefinitive");
         if (meth != null) {
            meth.removeCode();
            code = meth.getCode(true);
            code.constant().setValue(true);
            code.ireturn();
            code.calculateMaxStack();
            code.calculateMaxLocals();
         }

      }
   }

   private void throwException(BCClass bc, String name, Class ret, Class[] args) {
      BCMethod meth = bc.declareMethod(name, ret, args);
      Code code = meth.getCode(true);
      code.anew().setType(FatalInternalException.class);
      code.dup();
      code.invokespecial().setMethod(FatalInternalException.class, "<init>", Void.TYPE, (Class[])null);
      code.athrow();
      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void delegate(BCClass bc, String name, Class ret, Class[] args) {
      BCMethod meth = bc.declareMethod("jdo" + name, ret, args);
      Code code = meth.getCode(true);
      code.aload().setThis();
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            code.xload().setParam(i).setType(args[i]);
         }
      }

      code.invokevirtual().setMethod("pc" + name, ret, args);
      code.xreturn().setType(ret);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   public boolean skipEnhance(BCMethod method) {
      return UNENHANCED_METHODS.contains(method.getName());
   }

   static {
      UNENHANCED_METHODS.add("jdoPostLoad");
      UNENHANCED_METHODS.add("jdoPreClear");
   }
}
