package weblogic.utils.wrapper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import weblogic.utils.classfile.ClassFile;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.MethodInfo;
import weblogic.utils.classfile.Scope;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPFieldref;
import weblogic.utils.classfile.cp.CPMemberType;
import weblogic.utils.classfile.cp.CPMethodref;
import weblogic.utils.classfile.expr.AssignStatement;
import weblogic.utils.classfile.expr.CastExpression;
import weblogic.utils.classfile.expr.CatchExceptionExpression;
import weblogic.utils.classfile.expr.CompoundStatement;
import weblogic.utils.classfile.expr.Const;
import weblogic.utils.classfile.expr.Expression;
import weblogic.utils.classfile.expr.ExpressionStatement;
import weblogic.utils.classfile.expr.InvokeExpression;
import weblogic.utils.classfile.expr.InvokeSpecialExpression;
import weblogic.utils.classfile.expr.LocalVariableExpression;
import weblogic.utils.classfile.expr.MemberVarExpression;
import weblogic.utils.classfile.expr.NewArrayExpression;
import weblogic.utils.classfile.expr.NewExpression;
import weblogic.utils.classfile.expr.ReturnStatement;
import weblogic.utils.classfile.expr.ThrowStatement;
import weblogic.utils.classfile.expr.TryCatchStatement;

public class WrapperClassFile extends ClassFile {
   private static final boolean DEBUG = false;
   private HashSet wrapperInterfaces = new HashSet();
   private CPFieldref vendorObjFieldRef;
   private CPMethodref preInvokeMref;
   private CPMethodref postInvokeMref;
   private CPMethodref exceptionInvokeMref;
   private CPMethodref finalizerInvokeMref;
   private CPMethodref BooleanConstructor;
   private CPMethodref CharacterConstructor;
   private CPMethodref ByteConstructor;
   private CPMethodref ShortConstructor;
   private CPMethodref IntegerConstructor;
   private CPMethodref LongConstructor;
   private CPMethodref FloatConstructor;
   private CPMethodref DoubleConstructor;
   private WrapperFactory factory;

   public WrapperClassFile(WrapperFactory factory) {
      this.factory = factory;
   }

   public void setup(Class superClass) {
      try {
         this.vendorObjFieldRef = this.cp.getFieldref(superClass, "vendorObj", "Ljava/lang/Object;");
         Method handlerMethod = superClass.getMethod("preInvocationHandler", String.class, Object[].class);
         this.preInvokeMref = this.cp.getMethodref(handlerMethod);
         handlerMethod = superClass.getMethod("postInvocationHandler", String.class, Object[].class, Object.class);
         this.postInvokeMref = this.cp.getMethodref(handlerMethod);
         handlerMethod = superClass.getMethod("invocationExceptionHandler", String.class, Object[].class, Throwable.class);
         this.exceptionInvokeMref = this.cp.getMethodref(handlerMethod);
         if (this.factory.needFinalize()) {
            try {
               handlerMethod = superClass.getMethod("finalizeInternal");
               this.finalizerInvokeMref = this.cp.getMethodref(handlerMethod);
            } catch (Exception var4) {
            }

            if (this.finalizerInvokeMref != null) {
               this.addFinalizer();
            }
         }

         this.addDefaultConstructor(superClass);
         this.BooleanConstructor = this.cp.getMethodref(Boolean.class, "<init>", "(Z)V");
         this.CharacterConstructor = this.cp.getMethodref(Character.class, "<init>", "(C)V");
         this.ByteConstructor = this.cp.getMethodref(Byte.class, "<init>", "(B)V");
         this.ShortConstructor = this.cp.getMethodref(Short.class, "<init>", "(S)V");
         this.IntegerConstructor = this.cp.getMethodref(Integer.class, "<init>", "(I)V");
         this.LongConstructor = this.cp.getMethodref(Long.class, "<init>", "(J)V");
         this.FloatConstructor = this.cp.getMethodref(Float.class, "<init>", "(F)V");
         this.DoubleConstructor = this.cp.getMethodref(Double.class, "<init>", "(D)V");
      } catch (Exception var5) {
      }

   }

   public void addMethods(Method[] m) {
      this.addMethods(m, (Object)null);
   }

   public void addMethods(Method[] m, Object vendorObj) {
      Class vendorClass = null;
      boolean pub = false;
      ClassList list = new ClassList(20);
      Class[] classArray = null;
      if (vendorObj != null) {
         vendorClass = vendorObj.getClass();
         pub = Modifier.isPublic(vendorClass.getModifiers());
         recursivelyGetInterfaces(vendorClass, list);
         classArray = list.toArray();
      }

      for(int i = 0; i < m.length; ++i) {
         if (Modifier.isPublic(m[i].getModifiers())) {
            Method method;
            if (vendorObj != null) {
               if (pub) {
                  method = m[i];
               } else {
                  method = this.getInterfaceMethod(m[i], classArray);
                  if (method == null) {
                     continue;
                  }
               }
            } else {
               method = getInterfaceMethod(m[i]);
            }

            int modifiers = method.getModifiers();
            modifiers &= 7;
            modifiers |= 16;
            MethodInfo mi = this.addMethod(method, modifiers);
            Scope scope = mi.getScope();
            Class[] params = method.getParameterTypes();
            Class[] exceptions = method.getExceptionTypes();
            Class declaringClass = method.getDeclaringClass();
            Object mref;
            if (declaringClass.isInterface()) {
               mref = this.cp.getInterfaceMethodref(method);
            } else {
               mref = this.cp.getMethodref(declaringClass, method);
            }

            Expression[] vars = mi.getScope().getArgs();
            CompoundStatement cs = new CompoundStatement();
            int var10000 = this.factory.needPreInvocationHandler(method);
            WrapperFactory var10001 = this.factory;
            Object nameExpression;
            if (var10000 != 0) {
               Expression nameExpression = Const.NULL;
               var10000 = this.factory.needPreInvocationHandler(method);
               var10001 = this.factory;
               if ((var10000 & 2) != 0) {
                  nameExpression = Const.get(method.getName());
               }

               nameExpression = Const.NULL;
               var10000 = this.factory.needPreInvocationHandler(method);
               var10001 = this.factory;
               if ((var10000 & 4) != 0) {
                  Expression[] varsCopy = this.primitiveToObject(vars);
                  nameExpression = new NewArrayExpression(Object.class, varsCopy);
               }

               ExpressionStatement preInvokeStatement = new ExpressionStatement(new InvokeExpression(this.preInvokeMref, Const.THIS, new Expression[]{(Expression)nameExpression, (Expression)nameExpression}));
               cs.add(preInvokeStatement);
            }

            Expression invokeResult = new InvokeExpression((CPMemberType)mref, new CastExpression(method.getDeclaringClass(), new MemberVarExpression(Const.THIS, this.vendorObjFieldRef)), vars);
            var10000 = this.factory.needPostInvocationHandler(method);
            var10001 = this.factory;
            Expression[] varsCopy;
            Object paramsExpression;
            if (var10000 != 0) {
               nameExpression = Const.NULL;
               var10000 = this.factory.needPostInvocationHandler(method);
               var10001 = this.factory;
               if ((var10000 & 2) != 0) {
                  nameExpression = Const.get(method.getName());
               }

               paramsExpression = Const.NULL;
               var10000 = this.factory.needPostInvocationHandler(method);
               var10001 = this.factory;
               if ((var10000 & 4) != 0) {
                  varsCopy = this.primitiveToObject(vars);
                  paramsExpression = new NewArrayExpression(Object.class, varsCopy);
               }

               Class retType = method.getReturnType();
               if (retType.isPrimitive()) {
                  LocalVariableExpression tmp = null;
                  if (retType != Void.TYPE) {
                     tmp = scope.createLocalVar(Type.getType(retType));
                     cs.add(new AssignStatement(tmp, invokeResult));
                  } else {
                     cs.add(new ExpressionStatement(invokeResult));
                  }

                  Expression resultExpression = Const.NULL;
                  if (retType != Void.TYPE) {
                     resultExpression = this.primitiveToObject(new Expression[]{tmp})[0];
                  }

                  ExpressionStatement postInvokeStatement = new ExpressionStatement(new InvokeExpression(this.postInvokeMref, Const.THIS, new Expression[]{(Expression)nameExpression, (Expression)paramsExpression, resultExpression}));
                  cs.add(postInvokeStatement);
                  if (retType != Void.TYPE) {
                     cs.add(new ReturnStatement(tmp));
                     scope.freeLocalVar(tmp);
                  } else {
                     cs.add(new ReturnStatement());
                  }
               } else {
                  Expression wrapResult = new CastExpression(retType, new InvokeExpression(this.postInvokeMref, Const.THIS, new Expression[]{(Expression)nameExpression, (Expression)paramsExpression, invokeResult}));
                  cs.add(new ReturnStatement(wrapResult));
               }
            } else {
               cs.add(new ReturnStatement(invokeResult));
            }

            var10000 = this.factory.needInvocationExceptionHandler(method);
            var10001 = this.factory;
            if (var10000 == 0) {
               CodeAttribute ca = mi.getCodeAttribute();
               ca.setCode(cs);
            } else {
               nameExpression = Const.NULL;
               var10000 = this.factory.needInvocationExceptionHandler(method);
               var10001 = this.factory;
               if ((var10000 & 2) != 0) {
                  nameExpression = Const.get(method.getName());
               }

               paramsExpression = Const.NULL;
               var10000 = this.factory.needInvocationExceptionHandler(method);
               var10001 = this.factory;
               if ((var10000 & 4) != 0) {
                  varsCopy = this.primitiveToObject(vars);
                  paramsExpression = new NewArrayExpression(Object.class, varsCopy);
               }

               TryCatchStatement tryCatch = new TryCatchStatement();
               tryCatch.setBody(cs);
               CompoundStatement exceptionHandler = new CompoundStatement();

               for(int j = 0; j < exceptions.length; ++j) {
                  LocalVariableExpression tmp = scope.createLocalVar(Type.OBJECT);
                  exceptionHandler.add(new AssignStatement(tmp, new CatchExceptionExpression()));
                  exceptionHandler.add(new ExpressionStatement(new InvokeExpression(this.exceptionInvokeMref, Const.THIS, new Expression[]{(Expression)nameExpression, (Expression)paramsExpression, tmp})));
                  exceptionHandler.add(new ThrowStatement(tmp));
                  tmp.free();
                  tryCatch.addHandler(exceptions[j].getName().replace('.', '/'), exceptionHandler);
               }

               CodeAttribute ca = mi.getCodeAttribute();
               ca.setCode(tryCatch);
            }
         }
      }

   }

   public void addInterface(String interfaceName) {
      super.addInterface(interfaceName);
   }

   public void addAllInterfaces(Class c) {
      for(Class cls = c; cls != null; cls = cls.getSuperclass()) {
         this.addInterfaces(cls.getInterfaces());
      }

   }

   private void addInterfaces(Class[] interfaces) {
      for(int i = 0; i < interfaces.length; ++i) {
         if (!Modifier.isPublic(interfaces[i].getModifiers())) {
            this.addInterfaces(interfaces[i].getInterfaces());
         } else {
            String interfaceName = interfaces[i].getName();
            if (!this.wrapperInterfaces.contains(interfaceName)) {
               super.addInterface(interfaceName);
               this.wrapperInterfaces.add(interfaceName);
            }
         }
      }

   }

   private void addDefaultConstructor(Class superClass) {
      CPMethodref superConstructor = this.cp.getMethodref(superClass, "<init>", "()V");
      Expression invokeResult = new InvokeSpecialExpression(superConstructor, Const.THIS, new Expression[0]);
      MethodInfo mi = this.addMethod("<init>", "()V", 1);
      CodeAttribute ca = mi.getCodeAttribute();
      ca.setCode(new ReturnStatement(invokeResult));
   }

   private void addFinalizer() {
      Expression invokeResult = new InvokeSpecialExpression(this.finalizerInvokeMref, Const.THIS, new Expression[0]);
      MethodInfo mi = this.addMethod("finalize", "()V", 1);
      CodeAttribute ca = mi.getCodeAttribute();
      ca.setCode(new ReturnStatement(invokeResult));
   }

   private Expression[] primitiveToObject(Expression[] vars) {
      Expression[] varsCopy = new Expression[vars.length];

      for(int j = 0; j < vars.length; ++j) {
         if (vars[j].getType() != Type.OBJECT && vars[j].getType() != Type.ARRAY) {
            if (vars[j].getType() == Type.INT) {
               varsCopy[j] = new NewExpression(this.IntegerConstructor, new Expression[]{vars[j]});
            } else if (vars[j].getType() == Type.BOOLEAN) {
               varsCopy[j] = new NewExpression(this.BooleanConstructor, new Expression[]{vars[j]});
            } else if (vars[j].getType() == Type.LONG) {
               varsCopy[j] = new NewExpression(this.LongConstructor, new Expression[]{vars[j]});
            } else if (vars[j].getType() == Type.FLOAT) {
               varsCopy[j] = new NewExpression(this.FloatConstructor, new Expression[]{vars[j]});
            } else if (vars[j].getType() == Type.DOUBLE) {
               varsCopy[j] = new NewExpression(this.DoubleConstructor, new Expression[]{vars[j]});
            } else if (vars[j].getType() == Type.SHORT) {
               varsCopy[j] = new NewExpression(this.ShortConstructor, new Expression[]{vars[j]});
            } else if (vars[j].getType() == Type.BYTE) {
               varsCopy[j] = new NewExpression(this.ByteConstructor, new Expression[]{vars[j]});
            } else if (vars[j].getType() == Type.CHARACTER) {
               varsCopy[j] = new NewExpression(this.CharacterConstructor, new Expression[]{vars[j]});
            } else {
               varsCopy[j] = vars[j];
            }
         } else {
            varsCopy[j] = vars[j];
         }
      }

      return varsCopy;
   }

   private Method getInterfaceMethod(Method m, Class[] list) {
      int i = 0;

      while(i < list.length) {
         try {
            Method foundMethod = list[i].getDeclaredMethod(m.getName(), m.getParameterTypes());
            return foundMethod;
         } catch (Exception var5) {
            ++i;
         }
      }

      return null;
   }

   public static Method getInterfaceMethod(Method m) {
      Class declaringClass = m.getDeclaringClass();
      if (Modifier.isPublic(declaringClass.getModifiers())) {
         return m;
      } else {
         Method foundMethod = recursivelyGetMethod(declaringClass, m);
         return foundMethod == null ? m : foundMethod;
      }
   }

   private static Method recursivelyGetMethod(Class decClass, Method m) {
      Method retVal = null;

      try {
         Method foundMethod = decClass.getDeclaredMethod(m.getName(), m.getParameterTypes());
         if (Modifier.isPublic(foundMethod.getDeclaringClass().getModifiers())) {
            retVal = foundMethod;
         }
      } catch (NoSuchMethodException var6) {
      } catch (SecurityException var7) {
      }

      Class[] interfaces = decClass.getInterfaces();

      for(int i = 0; interfaces != null && i < interfaces.length; ++i) {
         Method foundMethod = recursivelyGetMethod(interfaces[i], m);
         if (foundMethod != null && Modifier.isPublic(foundMethod.getDeclaringClass().getModifiers())) {
            retVal = foundMethod;
         }
      }

      if (decClass.getSuperclass() != null) {
         Method foundMethod = recursivelyGetMethod(decClass.getSuperclass(), m);
         if (foundMethod != null && Modifier.isPublic(foundMethod.getDeclaringClass().getModifiers())) {
            retVal = foundMethod;
         }
      }

      return retVal;
   }

   private static void recursivelyGetInterfaces(Class decClass, ClassList list) {
      if (decClass.getSuperclass() != null) {
         recursivelyGetInterfaces(decClass.getSuperclass(), list);
      }

      Class[] interfaces = decClass.getInterfaces();

      for(int i = 0; interfaces != null && i < interfaces.length; ++i) {
         recursivelyGetInterfaces(interfaces[i], list);
      }

      if (Modifier.isPublic(decClass.getModifiers()) && !decClass.getName().equals("java.lang.Object")) {
         list.addUnique(decClass);
      }

   }
}
