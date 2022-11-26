package weblogic.rmi.internal;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.rmi.spi.InboundRequest;
import weblogic.utils.classfile.ClassFile;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.MethodInfo;
import weblogic.utils.classfile.Scope;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPClass;
import weblogic.utils.classfile.cp.CPInterfaceMethodref;
import weblogic.utils.classfile.cp.CPMemberType;
import weblogic.utils.classfile.cp.CPMethodref;
import weblogic.utils.classfile.expr.ArrayExpression;
import weblogic.utils.classfile.expr.AssignStatement;
import weblogic.utils.classfile.expr.CastExpression;
import weblogic.utils.classfile.expr.CatchExceptionExpression;
import weblogic.utils.classfile.expr.CompoundStatement;
import weblogic.utils.classfile.expr.Const;
import weblogic.utils.classfile.expr.ConstClassExpression;
import weblogic.utils.classfile.expr.ConstThisExpression;
import weblogic.utils.classfile.expr.Expression;
import weblogic.utils.classfile.expr.ExpressionStatement;
import weblogic.utils.classfile.expr.InvokeExpression;
import weblogic.utils.classfile.expr.InvokeSpecialExpression;
import weblogic.utils.classfile.expr.LHSExpression;
import weblogic.utils.classfile.expr.LocalVariableExpression;
import weblogic.utils.classfile.expr.NewExpression;
import weblogic.utils.classfile.expr.ReturnStatement;
import weblogic.utils.classfile.expr.Statement;
import weblogic.utils.classfile.expr.SwitchStatement;
import weblogic.utils.classfile.expr.ThrowStatement;
import weblogic.utils.classfile.expr.TryCatchStatement;

public class SkelGenerator extends ClassFile {
   private static final int PARAM_I = 1;
   private static final int PARAM_INBOUND_REQUEST = 2;
   private static final int PARAM_OUTBOUND_RESPONSE = 3;
   private static final int PARAM_IMPL = 4;
   private static final int METHOD_LIMIT = 100;
   private static final String INVOKE_METHOD_NAME = "invoke";
   private static final String INVOKE_METHOD_DESCRIPTOR = "(ILweblogic/rmi/spi/InboundRequest;Lweblogic/rmi/spi/OutboundResponse;Ljava/lang/Object;)Lweblogic/rmi/spi/OutboundResponse;";
   private static final String INVOKE2_METHOD_DESCRIPTOR = "(I[Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;";
   private BasicRuntimeDescriptor desc;
   private Scope scope;

   public SkelGenerator(RuntimeDescriptor desc) {
      this.desc = (BasicRuntimeDescriptor)desc;
      this.setClassName(desc.getSkeletonClassName());
      this.setSuperClassName(Skeleton.class.getName());
      this.addDefaultConstructor();
      MethodInfo invokeMethod2;
      if (this.isSplitRequired()) {
         this.splitInvokeMethod();
      } else {
         invokeMethod2 = this.addInvokeMethod();
         invokeMethod2.getCodeAttribute().setCode(this.createCode(desc.getMethodDescriptors(), desc.getRemoteMethods()));
      }

      invokeMethod2 = this.addInvokeMethod2();
      invokeMethod2.getCodeAttribute().setCode(this.createCode2(desc.getMethodDescriptors(), desc.getRemoteMethods()));
   }

   private boolean isSplitRequired() {
      Method[] methds = this.desc.getRemoteMethods();
      return methds.length > 100;
   }

   private void splitInvokeMethod() {
      MethodDescriptor[] methdDescrptrs = this.desc.getMethodDescriptors();
      Method[] methds = this.desc.getRemoteMethods();
      int noOfRemoteMethods = methds.length;
      int index = 0;
      int noOfInvokeMethods = true;
      int noOfInvokeMethods;
      if (noOfRemoteMethods % 100 == 0) {
         noOfInvokeMethods = noOfRemoteMethods / 100;
      } else {
         noOfInvokeMethods = noOfRemoteMethods / 100 + 1;
      }

      for(int j = 0; j < noOfInvokeMethods; ++j) {
         int noOfMethods = noOfInvokeMethods == 1 ? noOfRemoteMethods : (noOfRemoteMethods - index > 100 ? 100 : noOfRemoteMethods - index);
         MethodInfo invokeMethod = this.addAdditionalInvokeMethod(j);
         Method[] methodsSubset = new Method[noOfMethods];
         MethodDescriptor[] methdDescSubset = new MethodDescriptor[noOfMethods];
         int k = false;

         int k;
         for(k = 0; k < noOfMethods; ++k) {
            methodsSubset[k] = methds[index + k];
            methdDescSubset[k] = methdDescrptrs[index + k];
         }

         int nextInvoke;
         if (noOfInvokeMethods == j + 1) {
            nextInvoke = 0;
         } else {
            nextInvoke = j + 1;
         }

         invokeMethod.getCodeAttribute().setCode(this.createCode(methdDescSubset, methodsSubset, index, nextInvoke));
         index += k;
      }

   }

   private MethodInfo addAdditionalInvokeMethod(int i) {
      MethodInfo mi = null;
      if (i == 0) {
         mi = this.addMethod("invoke", "(ILweblogic/rmi/spi/InboundRequest;Lweblogic/rmi/spi/OutboundResponse;Ljava/lang/Object;)Lweblogic/rmi/spi/OutboundResponse;", 1);
      } else {
         mi = this.addMethod("internalInvoke" + i, "(ILweblogic/rmi/spi/InboundRequest;Lweblogic/rmi/spi/OutboundResponse;Ljava/lang/Object;)Lweblogic/rmi/spi/OutboundResponse;", 2);
      }

      mi.addException(this.cp.getClass("java/lang/Exception"));
      this.scope = mi.getScope();
      return mi;
   }

   public Statement createCode(MethodDescriptor[] mds, Method[] methods, int startPoint, int nextInvoke) {
      if (mds.length == 0) {
         return this.addThrowMethodIDOutOfRange(1);
      } else {
         CompoundStatement ret = new CompoundStatement();
         SwitchStatement switchStatement = new SwitchStatement(this.scope.getParameter(1));

         for(int i = 0; i < mds.length; ++i) {
            switchStatement.addCase(i + startPoint, this.addSkelMethod(mds[i], methods[i]), true);
         }

         if (nextInvoke == 0) {
            switchStatement.setDefault(this.addThrowMethodIDOutOfRange(1));
         } else {
            CompoundStatement ret2 = new CompoundStatement();
            CPMethodref m1 = this.cp.getMethodref(this.getThisClass(), "internalInvoke" + nextInvoke, "(ILweblogic/rmi/spi/InboundRequest;Lweblogic/rmi/spi/OutboundResponse;Ljava/lang/Object;)Lweblogic/rmi/spi/OutboundResponse;");
            ret2.add(new ExpressionStatement(new InvokeExpression(m1, new ConstThisExpression(), new Expression[]{this.scope.getParameter(1), this.scope.getParameter(2), this.scope.getParameter(3), this.scope.getParameter(4)})));
            switchStatement.setDefault(ret2);
         }

         ret.add(switchStatement);
         ret.add(new ReturnStatement(this.scope.getParameter(3)));
         return ret;
      }
   }

   private MethodInfo addInvokeMethod() {
      MethodInfo mi = this.addMethod("invoke", "(ILweblogic/rmi/spi/InboundRequest;Lweblogic/rmi/spi/OutboundResponse;Ljava/lang/Object;)Lweblogic/rmi/spi/OutboundResponse;", 1);
      this.scope = mi.getScope();
      mi.addException(this.cp.getClass("java/lang/Exception"));
      return mi;
   }

   private MethodInfo addInvokeMethod2() {
      MethodInfo mi = this.addMethod("invoke", "(I[Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", 1);
      this.scope = mi.getScope();
      mi.addException(this.cp.getClass("java/lang/Exception"));
      return mi;
   }

   private Statement createCode2(MethodDescriptor[] mds, Method[] methods) {
      if (mds.length == 0) {
         return this.addThrowMethodIDOutOfRange(1);
      } else {
         SwitchStatement switchStatement = new SwitchStatement(this.scope.getParameter(1));

         for(int i = 0; i < mds.length; ++i) {
            switchStatement.addCase(i, this.addSkelMethod2(mds[i], methods[i]), false);
         }

         switchStatement.setDefault(this.addThrowMethodIDOutOfRange(1));
         return switchStatement;
      }
   }

   private Statement createCode(MethodDescriptor[] mds, Method[] methods) {
      if (mds.length == 0) {
         return this.addThrowMethodIDOutOfRange(1);
      } else {
         CompoundStatement ret = new CompoundStatement();
         SwitchStatement switchStatement = new SwitchStatement(this.scope.getParameter(1));

         for(int i = 0; i < mds.length; ++i) {
            switchStatement.addCase(i, this.addSkelMethod(mds[i], methods[i]), true);
         }

         switchStatement.setDefault(this.addThrowMethodIDOutOfRange(1));
         ret.add(switchStatement);
         ret.add(new ReturnStatement(this.scope.getParameter(3)));
         return ret;
      }
   }

   private Statement addSkelMethod(MethodDescriptor md, Method m) {
      CompoundStatement stmt = new CompoundStatement();
      Class[] params = m.getParameterTypes();
      int numArgs = params.length;
      LocalVariableExpression[] vars = new LocalVariableExpression[params.length];

      for(int i = 0; i < vars.length; ++i) {
         vars[i] = this.scope.createLocalVar(Type.getType(params[i]));
      }

      if (numArgs > 0) {
         stmt.add(this.addUnmarshallingCode(params, vars, 2));
      }

      Class retType = m.getReturnType();
      LocalVariableExpression retVar = null;
      if (retType != Void.TYPE) {
         retVar = this.scope.createLocalVar(Type.getType(retType));
      }

      stmt.add(this.addInvokeImpl(retVar, md, vars, retType, m, params));
      if (!md.getImplRespondsToClient() && md.getReturnType() != Void.TYPE) {
         stmt.add(this.addMarshallReturn(retVar, retType, 3));
      }

      if (retVar != null) {
         retVar.free();
      }

      for(int i = 0; i < vars.length; ++i) {
         vars[i].free();
      }

      return stmt;
   }

   private Statement addSkelMethod2(MethodDescriptor md, Method method) {
      Class[] params = method.getParameterTypes();
      Expression[] vars = new Expression[md.getParameterTypes().length];

      for(int i = 0; i < vars.length; ++i) {
         vars[i] = new ArrayExpression(Const.get(i), this.scope.getParameter(2));
         if (params[i].isPrimitive()) {
            vars[i] = Type.toPrimitive(this.cp, Type.getType(params[i]), vars[i]);
         } else {
            vars[i] = new CastExpression(params[i], vars[i]);
         }
      }

      Class castClass = method.getDeclaringClass();
      CPMemberType implMethod = this.cp.getInterfaceMethodref(method);
      Expression invoke = new InvokeExpression(implMethod, new CastExpression(castClass, this.scope.getParameter(3)), vars);
      Type type = invoke.getType();
      if (type != Type.VOID) {
         if (type != Type.OBJECT && type != Type.ARRAY) {
            return new ReturnStatement(new NewExpression(type.getConstructor(this.cp), new Expression[]{invoke}));
         } else {
            return new ReturnStatement(invoke);
         }
      } else {
         CompoundStatement ret = new CompoundStatement();
         ret.add(new ExpressionStatement(invoke));
         ret.add(new ReturnStatement(Const.NULL));
         return ret;
      }
   }

   private Statement addInvokeImpl(LHSExpression retVar, MethodDescriptor md, Expression[] vars, Class retType, Method method, Class[] params) {
      CompoundStatement ret = new CompoundStatement();
      boolean isFuture = FutureResponse.class == md.getDispatchType();
      Method dispatchMethod = md.getDispatchMethod();
      Class castClass = method.getDeclaringClass();
      if (dispatchMethod != null) {
         method = dispatchMethod;
         castClass = this.desc.getRemoteClass();
         Expression[] tmp = new Expression[vars.length + 1];
         System.arraycopy(vars, 0, tmp, 0, vars.length);
         if (isFuture) {
            tmp[tmp.length - 1] = new CastExpression(FutureResponse.class, this.scope.getParameter(3));
            retType = Void.TYPE;
         } else if (InboundRequest.class == md.getDispatchType()) {
            tmp[tmp.length - 1] = this.scope.getParameter(2);
         }

         vars = tmp;
      }

      Object implMethod;
      if (md.getDispatchType() != null) {
         implMethod = this.cp.getMethodref(method);
      } else {
         implMethod = this.cp.getInterfaceMethodref(method);
      }

      Expression invoke = new InvokeExpression((CPMemberType)implMethod, new CastExpression(castClass, this.scope.getParameter(4)), vars);
      if (retType != Void.TYPE) {
         ret.add(new AssignStatement(retVar, invoke));
      } else {
         ret.add(new ExpressionStatement(invoke));
      }

      boolean hasAsyncArgument = false;

      for(int i = 0; i < params.length; ++i) {
         if (AsyncResult.class.isAssignableFrom(params[i])) {
            hasAsyncArgument = true;
            break;
         }
      }

      if (!isFuture && !hasAsyncArgument) {
         CPMethodref m1 = this.cp.getMethodref(this.getThisClass(), "associateResponseData", "(Lweblogic/rmi/spi/InboundRequest;Lweblogic/rmi/spi/OutboundResponse;)V");
         ret.add(new ExpressionStatement(new InvokeExpression(m1, new ConstThisExpression(), new Expression[]{this.scope.getParameter(2), this.scope.getParameter(3)})));
      }

      return ret;
   }

   private Statement addMarshallReturn(Expression retVar, Class retType, int index) {
      TryCatchStatement tryCatch = new TryCatchStatement();
      CPInterfaceMethodref getMsgOutputMethod = this.cp.getInterfaceMethodref("weblogic/rmi/spi/OutboundResponse", "getMsgOutput", "()Lweblogic/rmi/spi/MsgOutput;");
      Expression msgoutput = new InvokeExpression(getMsgOutputMethod, this.scope.getParameter(index), new Expression[0]);
      CPInterfaceMethodref writeObjectMethod;
      if (retType.isPrimitive()) {
         writeObjectMethod = this.getPrimitiveWriteMethod(retType);
         tryCatch.setBody(new ExpressionStatement(new InvokeExpression(writeObjectMethod, msgoutput, new Expression[]{retVar})));
      } else {
         writeObjectMethod = this.cp.getInterfaceMethodref("weblogic/rmi/spi/MsgOutput", "writeObject", "(Ljava/lang/Object;Ljava/lang/Class;)V");
         Object retExpr;
         ConstClassExpression retDotClass;
         if (AsyncResult.class.isAssignableFrom(retType)) {
            retExpr = new InvokeExpression(this.cp.getInterfaceMethodref(AsyncResult.class, "getObject", "()Ljava/lang/Object;"), new CastExpression(AsyncResultImpl.class, retVar), new Expression[0]);
            retDotClass = Const.get(Object.class);
         } else {
            retExpr = retVar;
            retDotClass = Const.get(retType);
         }

         tryCatch.setBody(new ExpressionStatement(new InvokeExpression(writeObjectMethod, msgoutput, new Expression[]{(Expression)retExpr, retDotClass})));
      }

      tryCatch.addHandler("java/io/IOException", this.addThrowNestedException("java/rmi/MarshalException", "error marshalling return"));
      return tryCatch;
   }

   private void addDefaultConstructor() {
      MethodInfo mi = this.addMethod("<init>", "()V", 1);
      CodeAttribute ca = mi.getCodeAttribute();
      CPMethodref skeletonConstructor = this.cp.getMethodref("weblogic/rmi/internal/Skeleton", "<init>", "()V");
      ca.setCode(new ReturnStatement(new InvokeSpecialExpression(skeletonConstructor, Const.THIS, new Expression[0])));
   }

   private Statement addUnmarshallingCode(Class[] params, LHSExpression[] vars, int index) {
      TryCatchStatement tryCatch = new TryCatchStatement();
      CPInterfaceMethodref getMsgInputMethod = this.cp.getInterfaceMethodref("weblogic/rmi/spi/InboundRequest", "getMsgInput", "()Lweblogic/rmi/spi/MsgInput;");
      LocalVariableExpression msginput = this.scope.createLocalVar(Type.OBJECT);
      CompoundStatement tryBody = new CompoundStatement();
      tryBody.add(new AssignStatement(msginput, new InvokeExpression(getMsgInputMethod, this.scope.getParameter(index), new Expression[0])));
      boolean readsObject = false;

      for(int i = 0; i < params.length; ++i) {
         if (AsyncResult.class.isAssignableFrom(params[i])) {
            CPClass asyncResultImplClass = this.cp.getClass(AsyncResultImpl.class);
            CPMethodref asyncResultImplConstructor = this.cp.getMethodref(asyncResultImplClass, "<init>", "(Lweblogic/rmi/spi/InboundRequest;Lweblogic/rmi/spi/OutboundResponse;)V");
            tryBody.add(new AssignStatement(vars[i], new NewExpression(asyncResultImplConstructor, new Expression[]{this.scope.getParameter(2), this.scope.getParameter(3)})));
         } else if (params[i].isPrimitive()) {
            tryBody.add(new AssignStatement(vars[i], new InvokeExpression(this.getPrimitiveReadMethod(params[i]), msginput, new Expression[0])));
         } else {
            readsObject = true;
            CPInterfaceMethodref readObjectMethod = this.cp.getInterfaceMethodref("weblogic/rmi/spi/MsgInput", "readObject", "(Ljava/lang/Class;)Ljava/lang/Object;");
            tryBody.add(new AssignStatement(vars[i], new CastExpression(params[i], new InvokeExpression(readObjectMethod, msginput, new Expression[]{Const.get(params[i])}))));
         }
      }

      tryCatch.setBody(tryBody);
      if (params.length > 0) {
         tryCatch.addHandler("java/io/IOException", this.addThrowNestedException("java/rmi/UnmarshalException", "error unmarshalling arguments"));
         if (readsObject) {
            tryCatch.addHandler("java/lang/ClassNotFoundException", this.addThrowNestedException("java/rmi/UnmarshalException", "error unmarshalling arguments"));
         }
      }

      return tryCatch;
   }

   private Statement addThrowNestedException(String eClass, String msg) {
      CPMethodref constructor = this.cp.getMethodref(eClass, "<init>", "(Ljava/lang/String;Ljava/lang/Exception;)V");
      LocalVariableExpression tmp = this.scope.createLocalVar(Type.OBJECT);
      CompoundStatement ret = new CompoundStatement();
      ret.add(new AssignStatement(tmp, new CatchExceptionExpression()));
      ret.add(new ThrowStatement(new NewExpression(constructor, new Expression[]{Const.get(msg), tmp})));
      tmp.free();
      return ret;
   }

   private CPInterfaceMethodref getPrimitiveReadMethod(Class c) {
      if (c == Integer.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataInput", "readInt", "()I");
      } else if (c == Byte.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataInput", "readByte", "()B");
      } else if (c == Boolean.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataInput", "readBoolean", "()Z");
      } else if (c == Short.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataInput", "readShort", "()S");
      } else if (c == Long.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataInput", "readLong", "()J");
      } else if (c == Float.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataInput", "readFloat", "()F");
      } else if (c == Character.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataInput", "readChar", "()C");
      } else if (c == Double.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataInput", "readDouble", "()D");
      } else {
         throw new AssertionError("Unknown primitive type: " + c.getName());
      }
   }

   private CPInterfaceMethodref getPrimitiveWriteMethod(Class c) {
      if (c == Integer.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataOutput", "writeInt", "(I)V");
      } else if (c == Byte.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataOutput", "writeByte", "(I)V");
      } else if (c == Boolean.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataOutput", "writeBoolean", "(Z)V");
      } else if (c == Short.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataOutput", "writeShort", "(I)V");
      } else if (c == Long.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataOutput", "writeLong", "(J)V");
      } else if (c == Float.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataOutput", "writeFloat", "(F)V");
      } else if (c == Character.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataOutput", "writeChar", "(I)V");
      } else if (c == Double.TYPE) {
         return this.cp.getInterfaceMethodref("java/io/DataOutput", "writeDouble", "(D)V");
      } else {
         throw new AssertionError("Unknown primitive type: " + c.getName());
      }
   }

   private Statement addThrowMethodIDOutOfRange(int index) {
      CPClass exceptionClass = this.cp.getClass("java/rmi/UnmarshalException");
      CPMethodref exConstructor = this.cp.getMethodref(exceptionClass, "<init>", "(Ljava/lang/String;)V");
      CPClass clsStringBuffer = this.cp.getClass(StringBuffer.class);
      CPMethodref sbConstructor = this.cp.getMethodref(clsStringBuffer, "<init>", "(Ljava/lang/String;)V");
      CPMethodref appendInt = this.cp.getMethodref(clsStringBuffer, "append", "(I)Ljava/lang/StringBuffer;");
      CPMethodref appendString = this.cp.getMethodref(clsStringBuffer, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
      CPMethodref toString = this.cp.getMethodref(clsStringBuffer, "toString", "()Ljava/lang/String;");
      return new ThrowStatement(new NewExpression(exConstructor, new Expression[]{new InvokeExpression(toString, new InvokeExpression(appendString, new InvokeExpression(appendInt, new NewExpression(sbConstructor, new Expression[]{Const.get("Method identifier [")}), new Expression[]{this.scope.getParameter(index)}), new Expression[]{Const.get("] out of range")}), new Expression[0])}));
   }

   public static void main(String[] args) {
      if (args == null || args.length != 1) {
         System.err.println("USAGE:  java weblogic.rmi.internal.SkelGenerator CLASSNAME");
         System.exit(1);
      }

      try {
         System.out.println("Using " + args[0]);
         Class myClass = Class.forName(args[0]);
         SkelGenerator skelGenerator = new SkelGenerator(DescriptorManager.getBasicRuntimeDescriptor(myClass));
         String fileName = skelGenerator.getClassName().replace('.', '/');
         skelGenerator.write(new FileOutputStream(fileName + ".class"));
      } catch (Exception var4) {
         System.err.println("ERROR: " + var4);
         var4.printStackTrace();
      }

   }
}
