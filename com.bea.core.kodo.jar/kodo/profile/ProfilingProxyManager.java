package kodo.profile;

import com.solarmetric.profile.ProfilingAgent;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kodo.kernel.KodoStoreContext;
import kodo.manage.Management;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.ProxyManagerImpl;
import serp.bytecode.BCClass;
import serp.bytecode.BCField;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;
import serp.bytecode.Instruction;
import serp.bytecode.JumpInstruction;

public class ProfilingProxyManager extends ProxyManagerImpl implements Configurable {
   private static final Collection COLL_ACCESSED = Arrays.asList("get", "getFirst", "getLast");
   private static final Collection MAP_ACCESSED = Collections.singleton("get");
   private static final Collection COLL_ADD = new HashSet(Arrays.asList("add", "addElement", "insertElementAt", "addAll", "addFirst", "addLast"));
   private static final Collection MAP_ADD = Arrays.asList("put", "putAll");
   private static final Collection COLL_CLEAR = Arrays.asList("clear", "removeAllElements");
   private static final Collection MAP_CLEAR = Collections.singleton("clear");
   private static final Collection COLL_REMOVE = Arrays.asList("remove", "removeElementAt", "removeElement", "removeAll", "removeFirst", "removeLast");
   private static final Collection MAP_REMOVE = Collections.singleton("remove");
   private static final Collection COLL_RETAIN = Collections.singleton("retainAll");
   private static final Collection COLL_SET = Arrays.asList("set", "setElementAt");
   private static final Collection COLL_CONTAINS = Collections.singleton("contains");
   private boolean _profile = false;

   protected Class loadBuildTimeProxy(Class cls, ClassLoader loader) {
      return this._profile ? null : super.loadBuildTimeProxy(cls, loader);
   }

   protected BCClass generateProxyCollectionBytecode(Class type, boolean runtime) {
      BCClass bc = super.generateProxyCollectionBytecode(type, runtime);
      if (!this._profile) {
         return bc;
      } else {
         BCField stats = this.addProxyStats(bc);
         this.mutateSetOwnerMethod(bc, stats);
         this.wrapIterator(bc, Iterator.class, "iterator", (Class[])null);
         if (List.class.isAssignableFrom(type)) {
            this.wrapIterator(bc, ListIterator.class, "listIterator", (Class[])null);
            this.wrapIterator(bc, ListIterator.class, "listIterator", new Class[]{Integer.TYPE});
         }

         Method[] meths = type.getMethods();
         this.invokeStatsMethod("incrementAccessed", bc, type, meths, COLL_ACCESSED, (Class)null, stats);
         this.invokeStatsMethod("incrementAddCalled", bc, type, meths, COLL_ADD, Collection.class, stats);
         this.invokeStatsMethod("incrementRemoveCalled", bc, type, meths, COLL_REMOVE, Collection.class, stats);
         this.invokeStatsMethod("incrementSetCalled", bc, type, meths, COLL_SET, (Class)null, stats);
         this.invokeStatsMethod("setClearCalled", bc, type, meths, COLL_CLEAR, (Class)null, stats);
         this.invokeStatsMethod("setRetainCalled", bc, type, meths, COLL_RETAIN, (Class)null, stats);
         this.invokeStatsMethod("setContainsCalled", bc, type, meths, COLL_CONTAINS, (Class)null, stats);
         return bc;
      }
   }

   protected BCClass generateProxyMapBytecode(Class type, boolean runtime) {
      BCClass bc = super.generateProxyMapBytecode(type, runtime);
      if (!this._profile) {
         return bc;
      } else {
         BCField stats = this.addProxyStats(bc);
         this.mutateSetOwnerMethod(bc, stats);
         this.wrapEntrySet(bc);
         Method[] meths = type.getMethods();
         this.invokeStatsMethod("incrementAccessed", bc, type, meths, MAP_ACCESSED, (Class)null, stats);
         this.invokeStatsMethod("incrementAddCalled", bc, type, meths, MAP_ADD, Map.class, stats);
         this.invokeStatsMethod("incrementRemoveCalled", bc, type, meths, MAP_REMOVE, (Class)null, stats);
         this.invokeStatsMethod("setClearCalled", bc, type, meths, MAP_CLEAR, (Class)null, stats);
         return bc;
      }
   }

   private BCField addProxyStats(BCClass bc) {
      bc.declareInterface(ProfilingProxy.class);
      BCField stats = bc.declareField("stats", ProfilingProxyStats.class);
      stats.setTransient(true);
      BCMethod m = bc.declareMethod("getStats", ProfilingProxyStats.class, (Class[])null);
      m.makePublic();
      Code code = m.getCode(true);
      code.aload().setThis();
      code.getfield().setField(stats);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      return stats;
   }

   private void mutateSetOwnerMethod(BCClass bc, BCField stats) {
      BCMethod m = bc.getDeclaredMethod("setOwner", new Class[]{OpenJPAStateManager.class, Integer.TYPE});
      if (m == null) {
         throw new InternalException();
      } else {
         Code code = m.getCode(false);
         code.beforeFirst();
         code.aload().setParam(0);
         JumpInstruction ifsmnull = code.ifnull();
         code.aload().setParam(0);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "getContext", StoreContext.class, (Class[])null);
         code.checkcast().setType(KodoStoreContext.class);
         int ctx = code.getNextLocalsIndex();
         code.astore().setLocal(ctx);
         code.aload().setLocal(ctx);
         JumpInstruction ifctxnull = code.ifnull();
         code.aload().setLocal(ctx);
         code.invokeinterface().setMethod(KodoStoreContext.class, "getProfilingAgent", ProfilingAgent.class, (Class[])null);
         JumpInstruction ifprofnull = code.ifnull();
         code.aload().setThis();
         code.anew().setType(ProfilingProxyStatsImpl.class);
         code.dup();
         code.aload().setLocal(ctx);
         code.aload().setParam(0);
         code.invokeinterface().setMethod(OpenJPAStateManager.class, "getMetaData", ClassMetaData.class, (Class[])null);
         code.iload().setParam(1);
         code.invokevirtual().setMethod(ClassMetaData.class, "getField", FieldMetaData.class, new Class[]{Integer.TYPE});
         code.invokespecial().setMethod(ProfilingProxyStatsImpl.class, "<init>", Void.TYPE, new Class[]{KodoStoreContext.class, FieldMetaData.class});
         code.putfield().setField(stats);
         JumpInstruction go2 = code.go2();
         Instruction elseins = code.aload().setThis();
         ifsmnull.setTarget(elseins);
         ifctxnull.setTarget(elseins);
         ifprofnull.setTarget(elseins);
         code.anew().setType(ProfilingProxyStatsNoop.class);
         code.dup();
         code.invokespecial().setMethod(ProfilingProxyStatsNoop.class, "<init>", Void.TYPE, (Class[])null);
         code.putfield().setField(stats);
         go2.setTarget(code.next());
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }
   }

   private void invokeStatsMethod(String statsMethName, BCClass bc, Class type, Method[] meths, Collection mutateMethNames, Class multiParam, BCField stats) {
      for(int i = 0; i < meths.length; ++i) {
         if (mutateMethNames.contains(meths[i].getName())) {
            Class[] params = meths[i].getParameterTypes();
            BCMethod m = bc.getDeclaredMethod(meths[i].getName(), params);
            boolean dec;
            if (m == null) {
               m = bc.declareMethod(meths[i].getName(), meths[i].getReturnType(), params);
               m.makePublic();
               dec = false;
            } else {
               dec = true;
            }

            Code code = m.getCode(true);
            code.aload().setThis();
            code.getfield().setField(stats);
            JumpInstruction ifnull = code.ifnull();
            code.aload().setThis();
            code.getfield().setField(stats);
            Class[] args = null;
            int j;
            if (multiParam != null) {
               for(j = 0; j < params.length; ++j) {
                  if (params[j] == multiParam) {
                     code.xload().setParam(j).setType(params[j]);
                     code.invokeinterface().setMethod(params[j], "size", Integer.TYPE, (Class[])null);
                     args = new Class[]{Integer.TYPE};
                     break;
                  }
               }
            }

            code.invokeinterface().setMethod(ProfilingProxyStats.class, statsMethName, Void.TYPE, args);
            if (!dec) {
               ifnull.setTarget(code.aload().setThis());

               for(j = 0; j < params.length; ++j) {
                  code.xload().setParam(j).setType(params[j]);
               }

               code.invokespecial().setMethod(meths[i]);
               code.xreturn().setType(meths[i].getReturnType());
            } else {
               ifnull.setTarget(code.next());
            }

            code.calculateMaxStack();
            code.calculateMaxLocals();
         }
      }

   }

   private void wrapIterator(BCClass bc, Class itrType, String methName, Class[] params) {
      BCMethod m = bc.getDeclaredMethod(methName, params);
      if (m == null) {
         throw new InternalException();
      } else {
         Code code = m.getCode(false);
         code.afterLast();
         code.previous();
         int itr = code.getNextLocalsIndex();
         code.astore().setLocal(itr);
         code.aload().setThis();
         code.aload().setLocal(itr);
         code.invokestatic().setMethod(ProfilingProxies.class, methName, itrType, new Class[]{ProfilingProxy.class, itrType});
         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }
   }

   private void wrapEntrySet(BCClass bc) {
      BCMethod m = bc.getDeclaredMethod("entrySet", (Class[])null);
      if (m == null) {
         throw new InternalException();
      } else {
         Code code = m.getCode(false);
         code.afterLast();
         code.previous();
         int set = code.getNextLocalsIndex();
         code.astore().setLocal(set);
         code.aload().setThis();
         code.aload().setLocal(set);
         code.invokestatic().setMethod(ProfilingProxies.class, "entrySet", Set.class, new Class[]{ProfilingProxy.class, Set.class});
         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }
   }

   private void wrapValues(BCClass bc) {
      BCMethod m = bc.getDeclaredMethod("values", (Class[])null);
      if (m == null) {
         throw new InternalException();
      } else {
         Code code = m.getCode(false);
         code.afterLast();
         code.previous();
         int vals = code.getNextLocalsIndex();
         code.astore().setLocal(vals);
         code.aload().setThis();
         code.aload().setLocal(vals);
         code.invokestatic().setMethod(ProfilingProxies.class, "values", Collection.class, new Class[]{ProfilingProxy.class, Collection.class});
         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }
   }

   public void setConfiguration(Configuration conf) {
      Management mgmnt = Management.getInstance((OpenJPAConfiguration)conf);
      this._profile = mgmnt != null && mgmnt.getProfilingAgent() != null;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }
}
