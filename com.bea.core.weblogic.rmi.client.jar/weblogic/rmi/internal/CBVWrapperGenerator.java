package weblogic.rmi.internal;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Future;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.utils.classfile.ClassFile;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.FieldInfo;
import weblogic.utils.classfile.MethodInfo;
import weblogic.utils.classfile.Scope;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPFieldref;
import weblogic.utils.classfile.cp.CPMethodref;
import weblogic.utils.classfile.expr.AssignStatement;
import weblogic.utils.classfile.expr.CastExpression;
import weblogic.utils.classfile.expr.CompoundStatement;
import weblogic.utils.classfile.expr.Const;
import weblogic.utils.classfile.expr.Expression;
import weblogic.utils.classfile.expr.ExpressionStatement;
import weblogic.utils.classfile.expr.InvokeExpression;
import weblogic.utils.classfile.expr.InvokeSpecialExpression;
import weblogic.utils.classfile.expr.LocalVariableExpression;
import weblogic.utils.classfile.expr.MemberVarExpression;
import weblogic.utils.classfile.expr.NotNullCondExpression;
import weblogic.utils.classfile.expr.ReturnStatement;
import weblogic.utils.classfile.expr.TernaryExpression;

public final class CBVWrapperGenerator extends ClassFile {
   private final RuntimeDescriptor desc;
   private final Class implClass;
   private FieldInfo delegateField;
   private CPFieldref delegateFieldRef;
   private Scope scope;

   public CBVWrapperGenerator(BasicRuntimeDescriptor desc) {
      this.desc = desc;
      this.implClass = desc.getRemoteClass();
      this.setClassName(desc.getRemoteClassName() + "_CBV");
      this.setSuperClassName(CBVWrapper.class.getName());
      this.addDelegateField();
      this.addDefaultConstructor();
      this.addMethods();
      this.addGetDelegateMethod();
      String[] interfaces = desc.getRemoteInterfacesClassNames();

      for(int i = 0; i < interfaces.length; ++i) {
         if (!interfaces[i].equals(StubInfoIntf.class.getName())) {
            this.addInterface(interfaces[i]);
         }
      }

   }

   private void addDelegateField() {
      this.delegateField = this.addField("delegate", "L" + this.desc.getRemoteClassName().replace('.', '/') + ";", 18);
      this.delegateFieldRef = this.cp.getFieldref(this.desc.getRemoteClassName().replace('.', '/') + "_CBV", "delegate", "L" + this.desc.getRemoteClassName().replace('.', '/') + ";");
   }

   private void addDefaultConstructor() {
      MethodInfo mi = this.addMethod("<init>", "(L" + this.desc.getRemoteClassName().replace('.', '/') + ";)V", 1);
      CodeAttribute ca = mi.getCodeAttribute();
      CPMethodref cbvWrapperConstructor = this.cp.getMethodref("weblogic/rmi/internal/CBVWrapper", "<init>", "()V");
      CompoundStatement cs = new CompoundStatement();
      cs.add(new ExpressionStatement(new InvokeSpecialExpression(cbvWrapperConstructor, Const.THIS, new Expression[0])));
      cs.add(new AssignStatement(new MemberVarExpression(Const.THIS, this.delegateFieldRef), mi.getScope().getParameter(1)));
      cs.add(new ReturnStatement());
      ca.setCode(cs);
   }

   private void addGetDelegateMethod() {
      MethodInfo mi = this.addMethod("getDelegate", "()Ljava/rmi/Remote;", 1);
      CodeAttribute ca = mi.getCodeAttribute();
      ca.setCode(new ReturnStatement(new MemberVarExpression(Const.THIS, this.delegateFieldRef)));
   }

   private void addMethods() {
      Method[] m = this.desc.getRemoteMethods();
      Method[] var2 = m;
      int var3 = m.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method aM = var2[var4];
         int modifiers = aM.getModifiers();
         modifiers &= 7;
         modifiers |= 16;
         MethodInfo mi = this.addMethod(aM, modifiers);
         this.scope = mi.getScope();
         Class[] params = aM.getParameterTypes();
         CPMethodref mref = this.cp.getMethodref(this.implClass, aM);
         Expression[] vars = mi.getScope().getArgs();

         for(int j = 0; j < params.length; ++j) {
            Class c = params[j];
            if (this.shouldClone(c)) {
               vars[j] = this.generateClone(c, vars[j]);
            } else if (this.shouldCopy(c)) {
               vars[j] = this.generateCopy(c, vars[j]);
            }
         }

         Expression invokeResult = new InvokeExpression(mref, new MemberVarExpression(Const.THIS, this.delegateFieldRef), vars);
         CodeAttribute ca = mi.getCodeAttribute();
         Class c = aM.getReturnType();
         boolean haveInvocationResult = false;
         if (c.isAssignableFrom(Future.class)) {
            MethodDescriptor md = this.findMethodDescriptor(this.desc.getMethodDescriptors(), aM.getName());
            if (md != null && md.hasAsyncResponse()) {
               invokeResult = this.generateCBVFutureWrapper((Expression)invokeResult);
               haveInvocationResult = true;
            }
         }

         if (!haveInvocationResult && this.shouldClone(c)) {
            CompoundStatement ret = new CompoundStatement();
            LocalVariableExpression tmp = this.scope.createLocalVar(Type.getType(c));
            ret.add(new AssignStatement(tmp, (Expression)invokeResult));
            ret.add(new ReturnStatement(this.generateClone(c, tmp)));
            this.scope.freeLocalVar(tmp);
            ca.setCode(ret);
         } else {
            if (!haveInvocationResult && this.shouldCopy(c)) {
               invokeResult = this.generateCopy(c, (Expression)invokeResult);
            }

            ca.setCode(new ReturnStatement((Expression)invokeResult));
         }
      }

   }

   private MethodDescriptor findMethodDescriptor(MethodDescriptor[] methodDescriptors, String name) {
      MethodDescriptor[] var3 = methodDescriptors;
      int var4 = methodDescriptors.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MethodDescriptor methodDescriptor = var3[var5];
         if (methodDescriptor.getMethod().getName().equals(name)) {
            return methodDescriptor;
         }
      }

      return null;
   }

   private CPMethodref getCloneMethod(Class type) {
      return type.isArray() ? this.cp.getMethodref(Object.class, "clone", "()Ljava/lang/Object;") : this.cp.getMethodref(type, "clone", "()Ljava/lang/Object;");
   }

   private CPMethodref getCopyMethod() {
      return this.cp.getMethodref("weblogic/rmi/internal/CBVWrapper", "copy", "(Ljava/lang/Object;)Ljava/lang/Object;");
   }

   private boolean shouldClone(Class c) {
      if (c == Date.class) {
         return true;
      } else if (c == java.sql.Date.class) {
         return true;
      } else if (c.isArray()) {
         return !this.shouldCopy(c.getComponentType());
      } else {
         return false;
      }
   }

   private boolean shouldCopy(Class c) {
      if (c.isPrimitive()) {
         return false;
      } else if (c == String.class) {
         return false;
      } else if (c == Boolean.class) {
         return false;
      } else if (c == Byte.class) {
         return false;
      } else if (c == Character.class) {
         return false;
      } else if (c == Double.class) {
         return false;
      } else if (c == Float.class) {
         return false;
      } else if (c == Integer.class) {
         return false;
      } else if (c == Long.class) {
         return false;
      } else if (c == Short.class) {
         return false;
      } else {
         return c != Void.class;
      }
   }

   private Expression generateClone(Class type, Expression expr) {
      CPMethodref cloneMethod = this.getCloneMethod(type);
      return new TernaryExpression(new NotNullCondExpression(expr), new CastExpression(type, new InvokeExpression(cloneMethod, expr, new Expression[0])), Const.NULL);
   }

   private Expression generateCBVFutureWrapper(Expression expr) {
      CPMethodref cbvFutureMethod = this.cp.getMethodref("weblogic/rmi/internal/CBVWrapper", "getFutureWrapper", "(Ljava/util/concurrent/Future;)Ljava/util/concurrent/Future;");
      return new InvokeSpecialExpression(cbvFutureMethod, Const.THIS, new Expression[]{expr});
   }

   private Expression generateCopy(Class type, Expression expr) {
      CPMethodref copyMethod = this.getCopyMethod();
      return new CastExpression(type, new InvokeSpecialExpression(copyMethod, Const.THIS, new Expression[]{expr}));
   }

   public static void main(String[] args) {
      if (args == null || args.length != 1) {
         System.err.println("USAGE:  java weblogic.rmi.internal.CBVWrapperGenerator CLASSNAME");
         System.exit(1);
      }

      RJVMEnvironment.getEnvironment().ensureInitialized();

      try {
         Class myClass = Class.forName(args[0]);
         CBVWrapperGenerator cbvGenerator = new CBVWrapperGenerator(new BasicRuntimeDescriptor(myClass));
         String fileName = cbvGenerator.getClassName().replace('.', '/');
         cbvGenerator.write(new FileOutputStream(fileName + ".class"));
      } catch (Exception var4) {
         System.err.println("ERROR: " + var4);
         var4.printStackTrace();
      }

   }
}
