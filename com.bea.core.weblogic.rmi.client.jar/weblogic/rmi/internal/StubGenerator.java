package weblogic.rmi.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Map;
import javax.transaction.Transaction;
import org.omg.CORBA.Object;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.utils.Utilities;
import weblogic.utils.Debug;
import weblogic.utils.classfile.ClassFile;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.MethodInfo;
import weblogic.utils.classfile.Scope;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPInterfaceMethodref;
import weblogic.utils.classfile.cp.CPMethodref;
import weblogic.utils.classfile.expr.ArrayElementSaveStatement;
import weblogic.utils.classfile.expr.ArrayExpression;
import weblogic.utils.classfile.expr.AssignStatement;
import weblogic.utils.classfile.expr.CastExpression;
import weblogic.utils.classfile.expr.CatchExceptionExpression;
import weblogic.utils.classfile.expr.CompoundStatement;
import weblogic.utils.classfile.expr.Const;
import weblogic.utils.classfile.expr.Expression;
import weblogic.utils.classfile.expr.ExpressionStatement;
import weblogic.utils.classfile.expr.IfStatement;
import weblogic.utils.classfile.expr.InvokeExpression;
import weblogic.utils.classfile.expr.InvokeSpecialExpression;
import weblogic.utils.classfile.expr.InvokeStaticExpression;
import weblogic.utils.classfile.expr.LocalVariableExpression;
import weblogic.utils.classfile.expr.MemberVarExpression;
import weblogic.utils.classfile.expr.NewArrayExpression;
import weblogic.utils.classfile.expr.NewExpression;
import weblogic.utils.classfile.expr.ReturnStatement;
import weblogic.utils.classfile.expr.Statement;
import weblogic.utils.classfile.expr.ThrowStatement;
import weblogic.utils.classfile.expr.TryCatchStatement;
import weblogic.utils.reflect.MethodSignatureBuilder;

public final class StubGenerator extends ClassFile {
   private static final boolean DEBUG = false;
   private static final int METHOD_LIMIT = 500;
   private MemberVarExpression stubinfo;
   private MemberVarExpression ror;
   private MemberVarExpression[] md;
   private MemberVarExpression methodArray;
   private MemberVarExpression initialized;
   private CPInterfaceMethodref refInvoke;
   private final String remoteIntf;
   private final String stubBase;
   private static final Class[] STUB_CON_PARAMS = new Class[]{StubInfo.class};
   private static final Class STUB_INFO_INTF_CLASS = StubInfoIntf.class;
   private final ClientMethodDescriptor defaultCMD;
   private static final String REMOTE_RUNTIME_EXCEPTION_CLASSNAME = "weblogic/rmi/extensions/RemoteRuntimeException";
   private static final boolean isServer = KernelStatus.isServer();
   private final Map mdMap;
   private static final String LJAVA_LANG_STRING = "Ljava/lang/String;";
   private static final String LJAVA_LANG_REFLECT_METHOD = "Ljava/lang/reflect/Method;";
   private static final String LJAVA_LANG_CLASS = "Ljava/lang/Class;";
   private static final String LWEBLOGIC_RMI_STUB_INFO = "Lweblogic/rmi/internal/StubInfo;";
   private static final String LWEBLOGIC_REMOTE_REFERENCE = "Lweblogic/rmi/extensions/server/RemoteReference;";
   private static final String JAVA_LANG_BOOLEAN = "Z";
   private static final String LWEBLOGIC_RUNTIME_METHOD_DESCRIPTOR = "Lweblogic/rmi/extensions/server/RuntimeMethodDescriptor;";
   private static final String LWEBLOGIC_STUB_REFERENCE = "Lweblogic/rmi/extensions/server/StubReference;";
   private static final String LJAVA_LANG_THROWABLE = "Ljava/lang/Throwable;";
   private static final String LJAVA_RMI_REMOTE = "Ljava/rmi/Remote;";
   private static final String LJAVA_LANG_OBJECT = "Ljava/lang/Object;";

   public StubGenerator(RuntimeDescriptor rtd, String appName) {
      this(rtd.getClientRuntimeDescriptor(appName).getDefaultClientMethodDescriptor(), rtd.getClientRuntimeDescriptor(appName).getDescriptorBySignature(), rtd.getRemoteInterfaces(), rtd.getStubClassName(), rtd);
   }

   public StubGenerator(RuntimeDescriptor rtd, String stubName, String stubBaseClass) {
      this(rtd.getClientRuntimeDescriptor((String)null).getDefaultClientMethodDescriptor(), rtd.getClientRuntimeDescriptor((String)null).getDescriptorBySignature(), rtd.getRemoteInterfaces(), stubName, stubBaseClass, rtd);
   }

   private StubGenerator(StubInfo info) {
      this(info.getDefaultClientMethodDescriptor(), info.getDescriptorBySignature(), info.getInterfaces(), info.getStubName(), info.getStubBaseClassName(), (RuntimeDescriptor)null);
   }

   private StubGenerator(ClientMethodDescriptor defaultCMD, Map descriptorMap, Class[] interfaces, String stubName, RuntimeDescriptor rtd) {
      this(defaultCMD, descriptorMap, interfaces, stubName, Stub.class.getName(), rtd);
   }

   private StubGenerator(ClientMethodDescriptor defaultCMD, Map descriptorMap, Class[] interfaces, String stubName, String stubBaseClass, RuntimeDescriptor rtd) {
      if (stubBaseClass == null) {
         stubBaseClass = Stub.class.getName();
      }

      Map sigMap = Utilities.getRemoteMethodsAndSignatures(interfaces, Object.class);
      int mapSize = sigMap.size();
      String[] signatures = new String[mapSize];
      Method[] methods = new Method[mapSize];
      int j = 0;

      Map.Entry e;
      for(Iterator var12 = sigMap.entrySet().iterator(); var12.hasNext(); methods[j++] = (Method)e.getValue()) {
         e = (Map.Entry)var12.next();
         signatures[j] = (String)e.getKey();
      }

      this.setSuperClassName(stubBaseClass);
      this.stubBase = stubBaseClass.replace('.', '/');
      this.addInterface(STUB_INFO_INTF_CLASS.getName());
      Debug.assertion(interfaces.length > 0, "Class does not implement remote interface");
      this.remoteIntf = interfaces[0].getName();

      int i;
      for(i = 0; i < interfaces.length; ++i) {
         if (interfaces[i].getName().indexOf("StubInfoIntf") <= 0) {
            this.addInterface(interfaces[i].getName());
         }
      }

      this.defaultCMD = defaultCMD;
      this.mdMap = descriptorMap;
      this.setClassName(stubName);
      this.addAndInitializeMemberVariables(mapSize);
      this.addConstructor();
      this.addGetStubInfo();
      this.addEnsureIntialized(methods, signatures);

      for(i = 0; i < mapSize; ++i) {
         this.addMethodCode(methods[i], signatures[i], i);
      }

   }

   private void addAndInitializeMemberVariables(int size) {
      this.addField("stubinfo", "Lweblogic/rmi/internal/StubInfo;", 18);
      this.addField("ror", "Lweblogic/rmi/extensions/server/RemoteReference;", 18);
      this.addField("initialized", "Z", 10);
      this.addField("m", "[Ljava/lang/reflect/Method;", 10);
      this.stubinfo = new MemberVarExpression(Const.THIS, this.cp.getFieldref(this.getThisClass(), "stubinfo", "Lweblogic/rmi/internal/StubInfo;"));
      this.ror = new MemberVarExpression(Const.THIS, this.cp.getFieldref(this.getThisClass(), "ror", "Lweblogic/rmi/extensions/server/RemoteReference;"));
      this.initialized = new MemberVarExpression(this.cp.getFieldref(this.getThisClass(), "initialized", "Z"));
      this.methodArray = new MemberVarExpression(this.cp.getFieldref(this.getThisClass(), "m", "[Ljava/lang/reflect/Method;"));
      this.md = new MemberVarExpression[size];

      for(int i = 0; i < size; ++i) {
         this.addField("md" + i, "Lweblogic/rmi/extensions/server/RuntimeMethodDescriptor;", 10);
         this.md[i] = new MemberVarExpression(this.cp.getFieldref(this.getThisClass(), "md" + i, "Lweblogic/rmi/extensions/server/RuntimeMethodDescriptor;"));
      }

   }

   private void addConstructor() {
      MethodInfo mi = this.addMethod("<init>", "(Lweblogic/rmi/internal/StubInfo;)V", 1);
      Scope scope = mi.getScope();
      CodeAttribute ca = mi.getCodeAttribute();
      CompoundStatement cd = new CompoundStatement();
      CPMethodref superRef = this.cp.getMethodref(this.stubBase, "<init>", "(Lweblogic/rmi/extensions/server/StubReference;)V");
      InvokeSpecialExpression superExpr = new InvokeSpecialExpression(superRef, Const.THIS, new Expression[]{scope.getParameter(1)});
      cd.add(new ExpressionStatement(superExpr));
      AssignStatement stmt = new AssignStatement(this.stubinfo, scope.getParameter(1));
      cd.add(stmt);
      CPMethodref getRemoteRef = this.cp.getMethodref("weblogic/rmi/internal/StubInfo", "getRemoteRef", "()Lweblogic/rmi/extensions/server/RemoteReference;");
      Expression invoke = new InvokeExpression(getRemoteRef, this.stubinfo, new Expression[0]);
      stmt = new AssignStatement(this.ror, invoke);
      cd.add(stmt);
      CPMethodref ensureInitialized = this.cp.getMethodref(this.getThisClass(), "ensureInitialized", "(Lweblogic/rmi/internal/StubInfo;)V");
      Expression invoke = new InvokeStaticExpression(ensureInitialized, new Expression[]{this.stubinfo});
      cd.add(new ExpressionStatement(invoke));
      cd.add(new ReturnStatement());
      ca.setCode(cd);
   }

   private void addEnsureIntialized(Method[] methods, String[] signatures) {
      MethodInfo mi = this.addMethod("ensureInitialized", "(Lweblogic/rmi/internal/StubInfo;)V", 42);
      Scope scope = mi.getScope();
      CodeAttribute ca = mi.getCodeAttribute();
      CompoundStatement cs = new CompoundStatement();
      IfStatement ifstmt = new IfStatement(this.initialized, new ReturnStatement());
      cs.add(ifstmt);
      CPMethodref getInterfaces = this.cp.getMethodref("weblogic/rmi/internal/StubInfo", "getInterfaces", "()[Ljava/lang/Class;");
      InvokeExpression invoke = new InvokeExpression(getInterfaces, scope.getParameter(1), new Expression[0]);
      CPMethodref getRemoteMethods = this.cp.getMethodref("weblogic/rmi/utils/Utilities", "getRemoteRMIMethods", "([Ljava/lang/Class;)[Ljava/lang/reflect/Method;");
      cs.add(new AssignStatement(this.methodArray, new InvokeStaticExpression(getRemoteMethods, new Expression[]{invoke})));
      if (methods.length > 500) {
         cs.add(this.splitInitializeMethodDescriptors(scope, signatures));
      } else {
         for(int i = 0; i < methods.length; ++i) {
            cs.add(this.initializeMethodDescriptor(scope, signatures[i], i));
         }
      }

      cs.add(new AssignStatement(this.initialized, Const.get(true)));
      cs.add(new ReturnStatement());
      ca.setCode(cs);
   }

   private void addGetStubInfo() {
      MethodInfo mi = this.addMethod("getStubInfo", "()Lweblogic/rmi/internal/StubInfo;", 1);
      CodeAttribute ca = mi.getCodeAttribute();
      ca.setCode(new ReturnStatement(this.stubinfo));
   }

   private Statement splitInitializeMethodDescriptors(Scope scope, String[] signatures) {
      CompoundStatement invokeInitializersStatements = new CompoundStatement();
      int noOfInitializers = signatures.length % 500 == 0 ? signatures.length / 500 : signatures.length / 500 + 1;
      int index = 0;

      for(int i = 0; i < noOfInitializers; ++i) {
         MethodInfo initializer = this.addMethod("initializeMethodDescriptors" + i, "(Lweblogic/rmi/internal/StubInfo;)V", 10);
         Scope initializerScope = initializer.getScope();
         CompoundStatement cs = new CompoundStatement();
         int noOfMethodDescriptors = Math.min(500, signatures.length - index);

         for(int j = 0; j < noOfMethodDescriptors; ++index) {
            cs.add(this.initializeMethodDescriptor(initializerScope, signatures[index], index));
            ++j;
         }

         cs.add(new ReturnStatement());
         initializer.getCodeAttribute().setCode(cs);
         Expression invoke = new InvokeStaticExpression(this.cp.getMethodref(this.getThisClass(), initializer.getName(), initializer.getDescriptor()), new Expression[]{scope.getParameter(1)});
         invokeInitializersStatements.add(new ExpressionStatement(invoke));
      }

      return invokeInitializersStatements;
   }

   private Statement initializeMethodDescriptor(Scope scope, String signature, int index) {
      CPMethodref methodDescriptorConst = this.cp.getMethodref("weblogic/rmi/internal/MethodDescriptor", "<init>", "(Ljava/lang/reflect/Method;Ljava/lang/Class;ZZZZIIZLjava/lang/String;)V");
      Expression[] expr = this.getParameters(scope, signature, index);
      CompoundStatement cs = new CompoundStatement();
      Expression methodExpr = new NewExpression(methodDescriptorConst, expr);
      cs.add(new AssignStatement(this.md[index], methodExpr));
      return cs;
   }

   private Expression[] getParameters(Scope scope, String signature, int index) {
      CPMethodref getRemoteExceptionWrapper = this.cp.getMethodref("weblogic/rmi/internal/StubInfo", "getRemoteExceptionWrapperClassName", "(Ljava/lang/reflect/Method;)Ljava/lang/String;");
      Expression[] args = new Expression[]{new ArrayExpression(Const.get(index), this.methodArray)};
      Expression remoteExceptionWrapperName = new InvokeExpression(getRemoteExceptionWrapper, scope.getParameter(1), args);
      CPMethodref getRemoteRef = this.cp.getMethodref("weblogic/rmi/internal/StubInfo", "getRemoteRef", "()Lweblogic/rmi/extensions/server/RemoteReference;");
      CPInterfaceMethodref getObjectID = this.cp.getInterfaceMethodref("weblogic/rmi/extensions/server/RemoteReference", "getObjectID", "()I");
      Expression objectID = new InvokeExpression(getObjectID, new InvokeExpression(getRemoteRef, scope.getParameter(1), new Expression[0]), new Expression[0]);
      CPMethodref getTimeOut = this.cp.getMethodref("weblogic/rmi/internal/StubInfo", "getTimeOut", "(Ljava/lang/reflect/Method;)I");
      Expression timeOut = new InvokeExpression(getTimeOut, scope.getParameter(1), args);
      Expression[] expr = new Expression[]{new ArrayExpression(Const.get(index), this.methodArray), Const.getClass(this.remoteIntf), Const.get(this.isOneway(signature)), Const.get(this.isTransactional(signature)), Const.get(this.isOnewayTransactionalRequest(signature)), Const.get(this.isIdempotent(signature)), timeOut, objectID, Const.get(this.hasAsynchronousResult(signature)), remoteExceptionWrapperName};
      return expr;
   }

   private int isOneway(String signature) {
      boolean b = false;
      if (this.defaultCMD != null) {
         b = this.defaultCMD.isOneway();
      }

      if (this.mdMap != null) {
         ClientMethodDescriptor cmd = (ClientMethodDescriptor)this.mdMap.get(signature);
         if (cmd != null) {
            b = cmd.isOneway();
         }
      }

      return b ? 1 : 0;
   }

   private int isOnewayTransactionalRequest(String signature) {
      boolean b = false;
      if (this.defaultCMD != null) {
         b = this.defaultCMD.isOnewayTransactionalRequest();
      }

      if (this.mdMap != null) {
         ClientMethodDescriptor cmd = (ClientMethodDescriptor)this.mdMap.get(signature);
         if (cmd != null) {
            b = cmd.isOnewayTransactionalRequest();
         }
      }

      return b ? 1 : 0;
   }

   private int isIdempotent(String signature) {
      boolean b = false;
      if (this.defaultCMD != null) {
         b = this.defaultCMD.isIdempotent();
      }

      if (this.mdMap != null) {
         ClientMethodDescriptor cmd = (ClientMethodDescriptor)this.mdMap.get(signature);
         if (cmd != null) {
            b = cmd.isIdempotent();
         }
      }

      return b ? 1 : 0;
   }

   private int getTimeOut(String signature) {
      int timeout = 0;
      if (this.defaultCMD != null) {
         timeout = this.defaultCMD.getTimeOut();
      }

      if (this.mdMap != null) {
         ClientMethodDescriptor cmd = (ClientMethodDescriptor)this.mdMap.get(signature);
         if (cmd != null) {
            timeout = cmd.getTimeOut();
         }
      }

      return timeout;
   }

   private int isTransactional(String signature) {
      boolean b = false;
      if (this.defaultCMD != null) {
         b = this.defaultCMD.isTransactional();
      }

      if (this.mdMap != null) {
         ClientMethodDescriptor cmd = (ClientMethodDescriptor)this.mdMap.get(signature);
         if (cmd != null) {
            b = cmd.isTransactional();
         }
      }

      return b ? 1 : 0;
   }

   private int hasAsynchronousResult(String signature) {
      boolean b = false;
      if (this.defaultCMD != null) {
         b = this.defaultCMD.getAsynchronousResult();
      }

      if (this.mdMap != null) {
         ClientMethodDescriptor cmd = (ClientMethodDescriptor)this.mdMap.get(signature);
         if (cmd != null) {
            b = cmd.getAsynchronousResult();
         }
      }

      return b ? 1 : 0;
   }

   private void addMethodCode(Method m, String signature, int index) {
      Class[] exceptions = m.getExceptionTypes();
      if (m.getName().indexOf("getStubInfo") <= -1) {
         MethodInfo mi = this.addMethod(m, 17);
         Scope scope = mi.getScope();
         CodeAttribute ca = mi.getCodeAttribute();
         if (exceptions != null) {
            this.addThrowException(mi, exceptions);
         }

         CompoundStatement cs = new CompoundStatement();
         int transactional = this.isTransactional(signature);
         LocalVariableExpression tx = null;
         if (transactional == 0) {
            tx = scope.createLocalVar(Type.getType(Transaction.class));
            CPMethodref getTHMethod = this.cp.getMethodref("weblogic/transaction/TransactionHelper", "getTransactionHelper", "()Lweblogic/transaction/TransactionHelper;");
            CPMethodref getTMMethod = this.cp.getMethodref("weblogic/transaction/TransactionHelper", "getTransactionManager", "()Lweblogic/transaction/ClientTransactionManager;");
            CPInterfaceMethodref suspendMethod = this.cp.getInterfaceMethodref("weblogic/transaction/ClientTransactionManager", "forceSuspend", "()Ljavax/transaction/Transaction;");
            Expression forceTx = new InvokeExpression(suspendMethod, new InvokeExpression(getTMMethod, new InvokeStaticExpression(getTHMethod, new Expression[0]), new Expression[0]), new Expression[0]);
            AssignStatement stmt = new AssignStatement(tx, forceTx);
            cs.add(stmt);
         }

         cs.add(this.addTryCatchBlock(m, index, scope, exceptions, tx, transactional));
         ca.setCode(cs);
         if (tx != null) {
            tx.free();
         }

      }
   }

   private Statement addTryCatchBlock(Method m, int index, Scope scope, Class[] exceptions, LocalVariableExpression tx, int transactional) {
      boolean methodThrowsRemoteException = false;
      TryCatchStatement tcs = new TryCatchStatement();
      tcs.setBody(this.addMethodBody(m, index, scope));
      tcs.addHandler("java/lang/Error", genErrorHandler(scope));
      tcs.addHandler("java/lang/RuntimeException", genErrorHandler(scope));
      if (exceptions != null) {
         for(int i = 0; i < exceptions.length; ++i) {
            tcs.addHandler(exceptions[i].getName(), genErrorHandler(scope));
            if (RemoteException.class.isAssignableFrom(exceptions[i])) {
               methodThrowsRemoteException = true;
            }
         }
      }

      if (!methodThrowsRemoteException && this.mdMap != null) {
         ClientMethodDescriptor mDesc = (ClientMethodDescriptor)this.mdMap.get(MethodSignatureBuilder.compute(m));
         if (mDesc != null) {
            String remoteExceptionWrapperClassName = mDesc.getRemoteExceptionWrapperClassName();
            if (remoteExceptionWrapperClassName != null) {
               try {
                  Class remoteExceptionWrapper = Class.forName(remoteExceptionWrapperClassName);
                  tcs.addHandler(RemoteException.class.getName(), this.addRemoteExceptionHandler(remoteExceptionWrapperClassName, scope));
               } catch (ClassNotFoundException var12) {
               }
            }
         }
      }

      tcs.addHandler("java/lang/Throwable", this.addUnexpectedException(scope));
      CompoundStatement fcs = null;
      if (transactional == 0) {
         fcs = new CompoundStatement();
         fcs.add(this.generateResumeTx(tx));
      }

      if (fcs != null) {
         tcs.setFinally(fcs);
      }

      return tcs;
   }

   private static Statement genErrorHandler(Scope scope) {
      CompoundStatement ret = new CompoundStatement();
      LocalVariableExpression exception = scope.createLocalVar(Type.OBJECT);
      ret.add(new AssignStatement(exception, new CatchExceptionExpression()));
      ret.add(new ThrowStatement(exception));
      exception.free();
      return ret;
   }

   private Statement addUnexpectedException(Scope scope) {
      CPMethodref constructor = this.cp.getMethodref("weblogic/rmi/extensions/RemoteRuntimeException", "<init>", "(Ljava/lang/String;Ljava/lang/Throwable;)V");
      LocalVariableExpression tmp = scope.createLocalVar(Type.OBJECT);
      CompoundStatement ret = new CompoundStatement();
      ret.add(new AssignStatement(tmp, new CatchExceptionExpression()));
      ret.add(new ThrowStatement(new NewExpression(constructor, new Expression[]{Const.get("Unexpected Exception"), tmp})));
      tmp.free();
      return ret;
   }

   private Statement addRemoteExceptionHandler(String targetException, Scope scope) {
      CPMethodref constructor = this.cp.getMethodref(targetException, "<init>", "()V");
      CPMethodref initCauseMethod = this.cp.getMethodref(targetException, "initCause", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;");
      LocalVariableExpression tmp = scope.createLocalVar(Type.OBJECT);
      CompoundStatement ret = new CompoundStatement();
      ret.add(new AssignStatement(tmp, new CatchExceptionExpression()));
      ret.add(new ThrowStatement(new InvokeExpression(initCauseMethod, new NewExpression(constructor, new Expression[0]), new Expression[]{tmp})));
      scope.freeLocalVar(tmp);
      return ret;
   }

   private Statement generateResumeTx(LocalVariableExpression tx) {
      CPMethodref getTHMethod = this.cp.getMethodref("weblogic/transaction/TransactionHelper", "getTransactionHelper", "()Lweblogic/transaction/TransactionHelper;");
      CPMethodref getTMMethod = this.cp.getMethodref("weblogic/transaction/TransactionHelper", "getTransactionManager", "()Lweblogic/transaction/ClientTransactionManager;");
      CPInterfaceMethodref resumeMethod = this.cp.getInterfaceMethodref("weblogic/transaction/ClientTransactionManager", "forceResume", "(Ljavax/transaction/Transaction;)V");
      Expression forceTx = new InvokeExpression(resumeMethod, new InvokeExpression(getTMMethod, new InvokeStaticExpression(getTHMethod, new Expression[0]), new Expression[0]), new Expression[]{tx});
      return new ExpressionStatement(forceTx);
   }

   private Statement addMethodBody(Method m, int index, Scope scope) {
      Class[] paramClass = m.getParameterTypes();
      CompoundStatement cs = new CompoundStatement();
      LocalVariableExpression lve = scope.createLocalVar(Type.getType(java.lang.Object[].class));
      if (paramClass.length > 0) {
         cs.add(new AssignStatement(lve, new NewArrayExpression(java.lang.Object.class, Const.get(paramClass.length))));

         for(int i = 0; i < paramClass.length; ++i) {
            cs.add(this.wrapFormalParamInObject(lve, i, paramClass[i], scope));
         }
      } else {
         cs.add(new AssignStatement(lve, new NewArrayExpression(java.lang.Object.class, Const.get(0))));
      }

      this.refInvoke = this.cp.getInterfaceMethodref("weblogic/rmi/extensions/server/RemoteReference", "invoke", "(Ljava/rmi/Remote;Lweblogic/rmi/extensions/server/RuntimeMethodDescriptor;[Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;");
      Expression invokeExpression = new InvokeExpression(this.refInvoke, this.ror, new Expression[]{Const.THIS, this.md[index], lve, new ArrayExpression(Const.get(index), this.methodArray)});
      Class returnType = m.getReturnType();
      if (returnType == Void.TYPE) {
         cs.add(new ExpressionStatement(invokeExpression));
         cs.add(new ReturnStatement());
      } else {
         cs.add(new ReturnStatement(this.addReturnExpression(returnType, invokeExpression)));
      }

      if (lve != null) {
         lve.free();
      }

      return cs;
   }

   private Expression addReturnExpression(Class c, Expression expr) {
      if (c.isPrimitive()) {
         if (c == Integer.TYPE) {
            return new InvokeExpression(this.cp.getMethodref("java/lang/Integer", "intValue", "()I"), new CastExpression(Integer.class, expr), new Expression[0]);
         } else if (c == Long.TYPE) {
            return new InvokeExpression(this.cp.getMethodref("java/lang/Long", "longValue", "()J"), new CastExpression(Long.class, expr), new Expression[0]);
         } else if (c == Double.TYPE) {
            return new InvokeExpression(this.cp.getMethodref("java/lang/Double", "doubleValue", "()D"), new CastExpression(Double.class, expr), new Expression[0]);
         } else if (c == Boolean.TYPE) {
            return new InvokeExpression(this.cp.getMethodref("java/lang/Boolean", "booleanValue", "()Z"), new CastExpression(Boolean.class, expr), new Expression[0]);
         } else if (c == Byte.TYPE) {
            return new InvokeExpression(this.cp.getMethodref("java/lang/Byte", "byteValue", "()B"), new CastExpression(Byte.class, expr), new Expression[0]);
         } else if (c == Character.TYPE) {
            return new InvokeExpression(this.cp.getMethodref("java/lang/Character", "charValue", "()C"), new CastExpression(Character.class, expr), new Expression[0]);
         } else if (c == Float.TYPE) {
            return new InvokeExpression(this.cp.getMethodref("java/lang/Float", "floatValue", "()F"), new CastExpression(Float.class, expr), new Expression[0]);
         } else if (c == Short.TYPE) {
            return new InvokeExpression(this.cp.getMethodref("java/lang/Short", "shortValue", "()S"), new CastExpression(Short.class, expr), new Expression[0]);
         } else {
            throw new AssertionError("Unknown return type " + c);
         }
      } else {
         return new CastExpression(c, expr);
      }
   }

   private Statement wrapFormalParamInObject(Expression lhs, int index, Class param, Scope scope) {
      if (!param.isPrimitive()) {
         return new ArrayElementSaveStatement(lhs, Const.get(index), scope.getParameter(index + 1));
      } else if (param == Long.TYPE) {
         return new ArrayElementSaveStatement(lhs, Const.get(index), new NewExpression(this.cp.getMethodref("java/lang/Long", "<init>", "(J)V"), new Expression[]{scope.getParameter(index + 1)}));
      } else if (param == Double.TYPE) {
         return new ArrayElementSaveStatement(lhs, Const.get(index), new NewExpression(this.cp.getMethodref("java/lang/Double", "<init>", "(D)V"), new Expression[]{scope.getParameter(index + 1)}));
      } else if (param == Integer.TYPE) {
         return new ArrayElementSaveStatement(lhs, Const.get(index), new NewExpression(this.cp.getMethodref("java/lang/Integer", "<init>", "(I)V"), new Expression[]{scope.getParameter(index + 1)}));
      } else if (param == Byte.TYPE) {
         return new ArrayElementSaveStatement(lhs, Const.get(index), new NewExpression(this.cp.getMethodref("java/lang/Byte", "<init>", "(B)V"), new Expression[]{scope.getParameter(index + 1)}));
      } else if (param == Character.TYPE) {
         return new ArrayElementSaveStatement(lhs, Const.get(index), new NewExpression(this.cp.getMethodref("java/lang/Character", "<init>", "(C)V"), new Expression[]{scope.getParameter(index + 1)}));
      } else if (param == Float.TYPE) {
         return new ArrayElementSaveStatement(lhs, Const.get(index), new NewExpression(this.cp.getMethodref("java/lang/Float", "<init>", "(F)V"), new Expression[]{scope.getParameter(index + 1)}));
      } else if (param == Short.TYPE) {
         return new ArrayElementSaveStatement(lhs, Const.get(index), new NewExpression(this.cp.getMethodref("java/lang/Short", "<init>", "(S)V"), new Expression[]{scope.getParameter(index + 1)}));
      } else if (param == Boolean.TYPE) {
         return new ArrayElementSaveStatement(lhs, Const.get(index), new NewExpression(this.cp.getMethodref("java/lang/Boolean", "<init>", "(Z)V"), new Expression[]{scope.getParameter(index + 1)}));
      } else {
         throw new AssertionError("Unknown Type" + param);
      }
   }

   private void addThrowException(MethodInfo mi, Class[] exceptions) {
      for(int i = 0; i < exceptions.length; ++i) {
         mi.addException(this.cp.getClass(exceptions[i]));
      }

   }

   private static Class getStubClass(StubInfo info, ClassLoader cl) {
      Class c;
      try {
         c = cl.loadClass(info.getStubName());
      } catch (ClassNotFoundException var4) {
         c = hotCodeGenClass(info, cl);
      }

      return c;
   }

   private static Class hotCodeGenClass(StubInfo info, ClassLoader cl) {
      info.refreshClientRuntimeDescriptor(cl);
      return (new StubGenerator(info)).generateClass(cl);
   }

   public static java.lang.Object generateStub(StubReference info) {
      return generateStub(info, info.getDescriptor().getClassLoader());
   }

   public static java.lang.Object generateStub(StubReference info, ClassLoader cl) {
      try {
         Class c = getStubClass((StubInfo)info, cl);
         Constructor cc = c.getConstructor(STUB_CON_PARAMS);
         return cc.newInstance(info);
      } catch (Exception var4) {
         throw (Error)(new AssertionError("Failed to generate class for " + info.getStubName())).initCause(var4);
      }
   }
}
