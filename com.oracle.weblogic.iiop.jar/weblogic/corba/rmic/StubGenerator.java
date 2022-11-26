package weblogic.corba.rmic;

import java.io.Externalizable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.rmi.CORBA.Stub;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA_2_3.portable.InputStream;
import weblogic.corba.utils.RemoteInfo;
import weblogic.iiop.IDLUtils;
import weblogic.iiop.Utils;
import weblogic.kernel.Kernel;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.utils.AssertionError;
import weblogic.utils.Getopt2;
import weblogic.utils.classfile.ClassFile;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.CodeGenHelper;
import weblogic.utils.classfile.MethodInfo;
import weblogic.utils.classfile.Scope;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPFieldref;
import weblogic.utils.classfile.cp.CPMethodref;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.expr.AssignStatement;
import weblogic.utils.classfile.expr.CastExpression;
import weblogic.utils.classfile.expr.CatchExceptionExpression;
import weblogic.utils.classfile.expr.CompoundStatement;
import weblogic.utils.classfile.expr.Const;
import weblogic.utils.classfile.expr.Expression;
import weblogic.utils.classfile.expr.ExpressionStatement;
import weblogic.utils.classfile.expr.IfStatement;
import weblogic.utils.classfile.expr.InvokeSpecialExpression;
import weblogic.utils.classfile.expr.LocalVariableExpression;
import weblogic.utils.classfile.expr.MemberVarExpression;
import weblogic.utils.classfile.expr.NewArrayExpression;
import weblogic.utils.classfile.expr.NewExpression;
import weblogic.utils.classfile.expr.ReturnStatement;
import weblogic.utils.classfile.expr.Statement;
import weblogic.utils.classfile.expr.ThrowStatement;
import weblogic.utils.classfile.expr.TryCatchStatement;
import weblogic.utils.reflect.MethodSignatureBuilder;

public class StubGenerator extends ClassFile {
   private static final String STUB_PACKAGE_PREFIX = "org.omg.stub.";
   public static final String IIOP = "iiop";
   public static final String IIOP_DIRECTORY = "iiopDirectory";
   private static final String TYPE_IDS_FIELD = "_type_ids";
   private final Class remoteInterface;
   private final RuntimeDescriptor descriptor;
   private final Class[] allInterfaces;
   private static boolean initialized;
   private static Method REQUEST_METHOD;
   private static Method INVOKE_METHOD;
   private static Method RELEASE_REPLY_METHOD;
   private Scope scope;
   private static final Expression[] VOIDPARAMS = new Expression[0];
   private static Class INPUT_STREAM_23 = InputStream.class;

   public StubGenerator(Class remote) {
      init();
      this.remoteInterface = remote;
      this.descriptor = RemoteInfo.findRemoteInfo(remote).getDescriptor();
      this.setClassName(this.generateClassName());
      this.setSuperClassName(Stub.class.getName());
      this.allInterfaces = getAllInterfaces(this.remoteInterface);

      for(int i = 0; i < this.allInterfaces.length; ++i) {
         this.addInterface(this.allInterfaces[i].getName());
      }

      this.addDefaultConstructor();
      this.addIds();
      this.addGetIdsMethod();
      this.addMethods();
   }

   public static void createStubs(Class remoteClass, ClassLoader cl) {
      if (!remoteClass.isInterface()) {
         (new StubGenerator(remoteClass)).generateClass(cl);
      }

      Class[] interfaces = getAllInterfaces(remoteClass);
      Class[] var3 = interfaces;
      int var4 = interfaces.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class anInterface = var3[var5];
         StubGenerator stubGenerator = new StubGenerator(anInterface);
         stubGenerator.generateClass(cl);
      }

   }

   private String generateClassName() {
      String interfaceName = this.remoteInterface.getName();
      if (interfaceName.startsWith("javax.") || interfaceName.startsWith("java.")) {
         interfaceName = "org.omg.stub." + interfaceName;
      }

      int i = interfaceName.lastIndexOf(".");
      if (i == -1) {
         return "_" + this.remoteInterface.getName() + "_Stub";
      } else {
         String packageName = interfaceName.substring(0, i + 1);
         String className = interfaceName.substring(i + 1, interfaceName.length());
         return packageName + "_" + className + "_Stub";
      }
   }

   static synchronized void init() {
      if (!initialized) {
         try {
            Class[] params = new Class[]{String.class, Boolean.TYPE};
            REQUEST_METHOD = ObjectImpl.class.getMethod("_request", params);
            params = new Class[]{OutputStream.class};
            INVOKE_METHOD = ObjectImpl.class.getMethod("_invoke", params);
            params = new Class[]{org.omg.CORBA.portable.InputStream.class};
            RELEASE_REPLY_METHOD = ObjectImpl.class.getMethod("_releaseReply", params);
         } catch (NoSuchMethodException var1) {
            throw new AssertionError(var1);
         }

         initialized = true;
      }
   }

   private static Class[] getAllInterfaces(Class[] classes) {
      HashSet s = new HashSet();

      for(int i = 0; i < classes.length; ++i) {
         addSuperRemoteInterfaces(classes[i], s);
      }

      return (Class[])((Class[])s.toArray(new Class[s.size()]));
   }

   public static Class[] getAllInterfaces(Class start) {
      HashSet s = new HashSet();
      addSuperRemoteInterfaces(start, s);
      return (Class[])((Class[])s.toArray(new Class[s.size()]));
   }

   private static void addSuperRemoteInterfaces(Class c, HashSet s) {
      if (isRemoteInterface(c) && !s.contains(c)) {
         s.add(c);
      }

      if (c.getSuperclass() != null) {
         addSuperRemoteInterfaces(c.getSuperclass(), s);
      }

      Class[] is = c.getInterfaces();

      for(int i = 0; i < is.length; ++i) {
         if (isRemoteInterface(is[i]) && !s.contains(is[i])) {
            s.add(is[i]);
            addSuperRemoteInterfaces(is[i], s);
         }
      }

   }

   private static boolean isRemoteInterface(Class c) {
      return c.isInterface() && Remote.class.isAssignableFrom(c) && !c.equals(Remote.class);
   }

   private void addDefaultConstructor() {
      MethodInfo mi = this.addMethod("<init>", "()V", 1);
      CodeAttribute ca = mi.getCodeAttribute();
      CPMethodref stubConstructor = this.cp.getMethodref("javax/rmi/CORBA/Stub", "<init>", "()V");
      ca.setCode(new ReturnStatement(new InvokeSpecialExpression(stubConstructor, Const.THIS, VOIDPARAMS)));
   }

   private void addIds() {
      this.addField("_type_ids", "[Ljava/lang/String;", 10);
      MethodInfo mi = this.addMethod("<clinit>", "()V", 8);
      CodeAttribute ca = mi.getCodeAttribute();
      CPFieldref field = this.cp.getFieldref(this.getClassName(), "_type_ids", "[Ljava/lang/String;");
      int isaremote = isRemoteInterface(this.remoteInterface) ? 0 : 1;
      Expression[] repIds = new Expression[this.allInterfaces.length + isaremote];
      if (isaremote > 0) {
         repIds[0] = Const.get(IDLUtils.getTypeID(this.remoteInterface));
      }

      for(int i = 0; i < this.allInterfaces.length; ++i) {
         repIds[i + isaremote] = Const.get(IDLUtils.getTypeID(this.allInterfaces[i]));
      }

      CompoundStatement cs = new CompoundStatement();
      cs.add(new AssignStatement(new MemberVarExpression(field), new NewArrayExpression(String.class, repIds)));
      cs.add(new ReturnStatement());
      ca.setCode(cs);
   }

   private void addGetIdsMethod() {
      MethodInfo mi = this.addMethod("_ids", "()[Ljava/lang/String;", 1);
      CodeAttribute ca = mi.getCodeAttribute();
      ConstantPool cp = mi.getConstantPool();
      CPFieldref idsField = cp.getFieldref(this.getClassName(), "_type_ids", "[Ljava/lang/String;");
      ca.setCode(new ReturnStatement(new MemberVarExpression(idsField)));
   }

   private void addMethods() {
      int var4;
      if (this.remoteInterface.isInterface()) {
         Method[] methods = this.getMethods(this.remoteInterface);
         Method[] var2 = methods;
         int var3 = methods.length;

         for(var4 = 0; var4 < var3; ++var4) {
            Method method = var2[var4];
            this.addMethod(method);
         }
      } else {
         HashSet methset = new HashSet();
         Class[] interfaces = this.remoteInterface.getInterfaces();
         Class[] var14 = interfaces;
         var4 = interfaces.length;

         for(int var15 = 0; var15 < var4; ++var15) {
            Class anInterface = var14[var15];
            if (isRemoteInterface(anInterface)) {
               Method[] methods = this.getMethods(anInterface);
               Method[] var8 = methods;
               int var9 = methods.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  Method method = var8[var10];
                  if (!methset.contains(method)) {
                     methset.add(method);
                     this.addMethod(method);
                  }
               }
            }
         }
      }

   }

   private Method[] getMethods(Class aClass) {
      SortedSet sortedMethods = new TreeSet(new MethodComparator());
      Method[] unsortedMethods = aClass.getMethods();
      Method[] var4 = unsortedMethods;
      int var5 = unsortedMethods.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method m = var4[var6];
         sortedMethods.add(m);
      }

      return (Method[])sortedMethods.toArray(new Method[0]);
   }

   private void addMethod(Method m) {
      int modifiers = m.getModifiers();
      modifiers &= 7;
      modifiers |= 16;
      MethodInfo mi = this.addMethod(m, modifiers);
      this.scope = mi.getScope();
      mi.addException(this.cp.getClass("java/rmi/RemoteException"));
      mi.getCodeAttribute().setCode(this.getCodeForMethod(m));
   }

   private Statement getCodeForMethod(Method m) {
      CompoundStatement ret = new CompoundStatement();
      ret.add(this.generateOuterTryCatch(m));
      ret.add(new ReturnStatement());
      return ret;
   }

   private Statement generateOuterTryCatch(Method m) {
      TryCatchStatement outerTryCatch = new TryCatchStatement();
      outerTryCatch.setBody(this.generateInnerTryCatch(m));
      outerTryCatch.addHandler("org/omg/CORBA/SystemException", this.generateHandleSystemException());
      return outerTryCatch;
   }

   private Statement generateInnerTryCatch(Method m) {
      LocalVariableExpression in = this.scope.createLocalVar(Type.OBJECT);
      CompoundStatement ret = new CompoundStatement();
      ret.add(new AssignStatement(in, Const.NULL));
      TryCatchStatement innerTryCatch = new TryCatchStatement();
      innerTryCatch.setBody(this.genMarshalingCode(m, in));
      innerTryCatch.addHandler("org/omg/CORBA/portable/ApplicationException", this.generateHandleApplicationException(m, in));
      innerTryCatch.addHandler("org/omg/CORBA/portable/RemarshalException", this.generateHandleRemarshalException(m));
      innerTryCatch.setFinally(this.generateReleaseReply(in));
      ret.add(innerTryCatch);
      return ret;
   }

   private boolean isOneway(Method m) {
      return this.descriptor == null ? false : this.descriptor.getClientMethodDescriptor(MethodSignatureBuilder.compute(m)).isOneway();
   }

   private boolean hasNonPrimitiveParam(Class[] params) {
      for(int i = 0; i < params.length; ++i) {
         if (!params[i].isPrimitive()) {
            return true;
         }
      }

      return false;
   }

   private Statement genMarshalingCode(Method m, LocalVariableExpression in) {
      Class[] params = m.getParameterTypes();
      Expression[] args = this.scope.getArgs();
      CompoundStatement ret = new CompoundStatement();
      LocalVariableExpression out = this.scope.createLocalVar(Type.OBJECT);
      Expression req = this.getRequest(m);
      if (this.hasNonPrimitiveParam(params)) {
         req = new CastExpression(org.omg.CORBA_2_3.portable.OutputStream.class, (Expression)req);
      }

      ret.add(new AssignStatement(out, (Expression)req));

      for(int i = 0; i < params.length; ++i) {
         ret.add(new ExpressionStatement(this.genWriteParam(out, params[i], args[i])));
      }

      Expression invokeResult = this.getInvoke(out);
      Class retType = m.getReturnType();
      if (retType != Void.TYPE && !this.isOneway(m)) {
         if (!retType.isPrimitive()) {
            invokeResult = new CastExpression(INPUT_STREAM_23, (Expression)invokeResult);
         }

         ret.add(new AssignStatement(in, (Expression)invokeResult));
         ret.add(new ReturnStatement(this.genReadParam(in, retType)));
         return ret;
      } else {
         ret.add(new ExpressionStatement((Expression)invokeResult));
         return ret;
      }
   }

   private Expression genWriteParam(Expression out, Class type, Expression val) {
      if (type.isPrimitive()) {
         return this.genWritePrimitive(out, type, val);
      } else if (!Remote.class.isAssignableFrom(type) && !Object.class.isAssignableFrom(type)) {
         if (!type.equals(java.lang.Object.class) && !type.equals(Serializable.class) && !type.equals(Externalizable.class)) {
            return Utils.isAbstractInterface(type) ? this.genWriteAbstractInterface(out, val) : this.genWriteValue(out, type, val);
         } else {
            return this.genWriteAny(out, val);
         }
      } else {
         return this.genWriteRemote(out, val);
      }
   }

   private Expression genReadParam(Expression in, Class type) {
      if (type.isPrimitive()) {
         return this.genReadPrimitive(in, type);
      } else if (!type.equals(java.lang.Object.class) && !type.equals(Serializable.class) && !type.equals(Externalizable.class)) {
         if (!Remote.class.isAssignableFrom(type) && !Object.class.isAssignableFrom(type)) {
            return Utils.isAbstractInterface(type) ? this.genReadAbstractInterface(in, type) : this.genReadValue(in, type);
         } else {
            return this.genReadRemote(in, type);
         }
      } else {
         return this.genReadAny(in, type);
      }
   }

   private Expression genWritePrimitive(Expression out, Class type, Expression val) {
      CPMethodref writer = null;
      if (type == Integer.TYPE) {
         writer = this.cp.getMethodref(OutputStream.class, "write_long", "(I)V");
      } else if (type == Byte.TYPE) {
         writer = this.cp.getMethodref(OutputStream.class, "write_octet", "(B)V");
      } else if (type == Boolean.TYPE) {
         writer = this.cp.getMethodref(OutputStream.class, "write_boolean", "(Z)V");
      } else if (type == Short.TYPE) {
         writer = this.cp.getMethodref(OutputStream.class, "write_short", "(S)V");
      } else if (type == Long.TYPE) {
         writer = this.cp.getMethodref(OutputStream.class, "write_longlong", "(J)V");
      } else if (type == Float.TYPE) {
         writer = this.cp.getMethodref(OutputStream.class, "write_float", "(F)V");
      } else if (type == Character.TYPE) {
         writer = this.cp.getMethodref(OutputStream.class, "write_wchar", "(C)V");
      } else {
         if (type != Double.TYPE) {
            throw new AssertionError("Unknown primitive: " + type);
         }

         writer = this.cp.getMethodref(OutputStream.class, "write_double", "(D)V");
      }

      return writer.invoke(out, new Expression[]{val});
   }

   private Expression genReadPrimitive(Expression in, Class type) {
      CPMethodref reader = null;
      if (type == Integer.TYPE) {
         reader = this.cp.getMethodref(org.omg.CORBA.portable.InputStream.class, "read_long", "()I");
      } else if (type == Byte.TYPE) {
         reader = this.cp.getMethodref(org.omg.CORBA.portable.InputStream.class, "read_octet", "()B");
      } else if (type == Boolean.TYPE) {
         reader = this.cp.getMethodref(org.omg.CORBA.portable.InputStream.class, "read_boolean", "()Z");
      } else if (type == Short.TYPE) {
         reader = this.cp.getMethodref(org.omg.CORBA.portable.InputStream.class, "read_short", "()S");
      } else if (type == Long.TYPE) {
         reader = this.cp.getMethodref(org.omg.CORBA.portable.InputStream.class, "read_longlong", "()J");
      } else if (type == Float.TYPE) {
         reader = this.cp.getMethodref(org.omg.CORBA.portable.InputStream.class, "read_float", "()F");
      } else if (type == Character.TYPE) {
         reader = this.cp.getMethodref(org.omg.CORBA.portable.InputStream.class, "read_wchar", "()C");
      } else {
         if (type != Double.TYPE) {
            throw new AssertionError("Unknown primitive: " + type);
         }

         reader = this.cp.getMethodref(org.omg.CORBA.portable.InputStream.class, "read_double", "()D");
      }

      return reader.invoke(in, VOIDPARAMS);
   }

   private Expression genWriteRemote(Expression out, Expression val) {
      CPMethodref writer = this.cp.getMethodref("javax/rmi/CORBA/Util", "writeRemoteObject", "(Lorg/omg/CORBA/portable/OutputStream;Ljava/lang/Object;)V");
      return writer.invokeStatic(new Expression[]{out, val});
   }

   private Expression genReadRemote(Expression in, Class type) {
      CPMethodref narrow = this.cp.getMethodref("javax/rmi/PortableRemoteObject", "narrow", "(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
      return new CastExpression(type, narrow.invokeStatic(new Expression[]{this.genReadObject(in, type), Const.get(type)}));
   }

   private Expression genWriteAny(Expression out, Expression val) {
      CPMethodref writer = this.cp.getMethodref("javax/rmi/CORBA/Util", "writeAny", "(Lorg/omg/CORBA/portable/OutputStream;Ljava/lang/Object;)V");
      return writer.invokeStatic(new Expression[]{out, val});
   }

   private Expression genWriteAbstractInterface(Expression out, Expression val) {
      CPMethodref writer = this.cp.getMethodref("javax/rmi/CORBA/Util", "writeAbstractObject", "(Lorg/omg/CORBA/portable/OutputStream;Ljava/lang/Object;)V");
      return writer.invokeStatic(new Expression[]{out, val});
   }

   private Expression genReadAny(Expression in, Class type) {
      CPMethodref reader = this.cp.getMethodref("javax/rmi/CORBA/Util", "readAny", "(Lorg/omg/CORBA/portable/InputStream;)Ljava/lang/Object;");
      return new CastExpression(type, reader.invokeStatic(new Expression[]{in}));
   }

   private Expression genReadAbstractInterface(Expression in, Class type) {
      CPMethodref reader = this.cp.getMethodref("org/omg/CORBA_2_3/portable/InputStream", "read_abstract_interface", "()Ljava/lang/Object;");
      return new CastExpression(type, reader.invoke(in, VOIDPARAMS));
   }

   private Expression genReadObject(Expression in, Class type) {
      CPMethodref reader = this.cp.getMethodref("org/omg/CORBA/portable/InputStream", "read_Object", "()Lorg/omg/CORBA/Object;");
      return new CastExpression(type, reader.invoke(in, VOIDPARAMS));
   }

   private Expression genWriteValue(Expression out, Class type, Expression val) {
      CPMethodref writer = this.cp.getMethodref("org/omg/CORBA_2_3/portable/OutputStream", "write_value", "(Ljava/io/Serializable;Ljava/lang/Class;)V");
      return writer.invoke(out, new Expression[]{val, Const.get(type)});
   }

   private Expression genReadValue(Expression in, Class type) {
      CPMethodref reader = this.cp.getMethodref("org/omg/CORBA_2_3/portable/InputStream", "read_value", "(Ljava/lang/Class;)Ljava/io/Serializable;");
      return new CastExpression(type, reader.invoke(in, new Expression[]{Const.get(type)}));
   }

   private Statement generateHandleSystemException() {
      CPMethodref mapSystemExceptionMethod = this.cp.getMethodref("javax/rmi/CORBA/Util", "mapSystemException", "(Lorg/omg/CORBA/SystemException;)Ljava/rmi/RemoteException;");
      CompoundStatement ret = new CompoundStatement();
      LocalVariableExpression se = this.scope.createLocalVar(Type.OBJECT);
      ret.add(new AssignStatement(se, new CatchExceptionExpression()));
      ret.add(new ThrowStatement(mapSystemExceptionMethod.invokeStatic(new Expression[]{se})));
      this.scope.freeLocalVar(se);
      return ret;
   }

   private Statement generateHandleApplicationException(Method m, LocalVariableExpression in) {
      CPMethodref getISMethod = this.cp.getMethodref("org/omg/CORBA/portable/ApplicationException", "getInputStream", "()Lorg/omg/CORBA/portable/InputStream;");
      CPMethodref readStringMethod = this.cp.getMethodref("org/omg/CORBA/portable/InputStream", "read_string", "()Ljava/lang/String;");
      CPMethodref unexpectedExceptionConstructor = this.cp.getMethodref("java/rmi/UnexpectedException", "<init>", "(Ljava/lang/String;)V");
      CompoundStatement ret = new CompoundStatement();
      LocalVariableExpression ae = this.scope.createLocalVar(Type.OBJECT);
      ret.add(new AssignStatement(ae, new CatchExceptionExpression()));
      ret.add(new AssignStatement(in, new CastExpression(INPUT_STREAM_23, getISMethod.invoke(ae, VOIDPARAMS))));
      LocalVariableExpression id = this.scope.createLocalVar(Type.OBJECT);
      ret.add(new AssignStatement(id, readStringMethod.invoke(in, VOIDPARAMS)));
      Class[] exceptions = this.getCheckedExceptions(m);
      ret.add(this.genCheckedExceptions(exceptions, in, id));
      ret.add(new ThrowStatement(new NewExpression(unexpectedExceptionConstructor, new Expression[]{id})));
      this.scope.freeLocalVar(ae);
      return ret;
   }

   private Statement genCheckedExceptions(Class[] exceptions, Expression in, Expression id) {
      CompoundStatement ret = new CompoundStatement();
      CPMethodref equalsMethod = this.cp.getMethodref("java/lang/Object", "equals", "(Ljava/lang/Object;)Z");

      for(int i = 0; i < exceptions.length; ++i) {
         Expression idstr = Const.get(Utils.getIDFromException(exceptions[i]));
         ret.add(new IfStatement(equalsMethod.invoke(id, new Expression[]{idstr}), new ThrowStatement(new CastExpression(exceptions[i], this.genReadValue(in, exceptions[i])))));
      }

      return ret;
   }

   private Class[] getCheckedExceptions(Method m) {
      Class[] exceptionTypes = m.getExceptionTypes();
      int cnt = 0;

      for(int i = 0; i < exceptionTypes.length; ++i) {
         if (this.isCheckedException(exceptionTypes[i])) {
            ++cnt;
         } else {
            exceptionTypes[i] = null;
         }
      }

      Class[] checkedExceptions = new Class[cnt];
      cnt = 0;

      for(int i = 0; i < exceptionTypes.length; ++i) {
         if (exceptionTypes[i] != null) {
            checkedExceptions[cnt++] = exceptionTypes[i];
         }
      }

      return checkedExceptions;
   }

   private boolean isCheckedException(Class exception) {
      return !RemoteException.class.isAssignableFrom(exception);
   }

   private Statement generateHandleRemarshalException(Method m) {
      CPMethodref meth = this.cp.getMethodref(this.getClassName(), m.getName(), CodeGenHelper.getMethodDescriptor(m));
      CompoundStatement ret = new CompoundStatement();
      ret.add(new ExpressionStatement(new CatchExceptionExpression()));
      if (m.getReturnType() == Void.TYPE) {
         ret.add(new ExpressionStatement(meth.invoke(this.scope.getArgs())));
      } else {
         ret.add(new ReturnStatement(meth.invoke(this.scope.getArgs())));
      }

      return ret;
   }

   private Statement generateReleaseReply(LocalVariableExpression in) {
      CPMethodref releaseReplyMethod = this.cp.getMethodref(RELEASE_REPLY_METHOD);
      Expression[] params = new Expression[]{in};
      return new ExpressionStatement(releaseReplyMethod.invoke(params));
   }

   private Expression getRequest(Method m) {
      CPMethodref requestMethod = this.cp.getMethodref(REQUEST_METHOD);
      Expression[] params = new Expression[]{this.getMangledName(m), Const.get(!this.isOneway(m))};
      return requestMethod.invoke(params);
   }

   private Expression getMangledName(Method m) {
      return Const.get(IDLMangler.getMangledMethodName(m, this.remoteInterface));
   }

   private Expression getInvoke(Expression out) {
      CPMethodref invokeMethod = this.cp.getMethodref(INVOKE_METHOD);
      Expression[] params = new Expression[]{out};
      return invokeMethod.invoke(params);
   }

   public static void addOpts(Getopt2 opts) {
      opts.addFlag("iiop", "Generate iiop stubs from servers");
      opts.addOption("iiopDirectory", "directory", "Specify the directory where IIOP proxy classes will be written (overrides target directory)");
   }

   private static void ensureDirectoryExists(String outputDir, String fileName) {
      String fileDir = "";
      int i = fileName.lastIndexOf(File.separatorChar);
      if (i != -1) {
         fileDir = fileName.substring(0, i);
      }

      String dir = outputDir + File.separatorChar + fileDir;
      File f = new File(dir);
      f.mkdirs();
   }

   public static void generate(Getopt2 opts) throws Exception {
      String outputDir = opts.getOption("iiopDirectory", (String)null);
      if (outputDir == null) {
         outputDir = opts.getOption("d", (String)null);
      }

      if (outputDir == null) {
         outputDir = ".";
      }

      Kernel.ensureInitialized();
      String[] classNames = opts.args();
      Class[] classes = new Class[classNames.length];
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (null == cl) {
         cl = StubGenerator.class.getClassLoader();
      }

      for(int i = 0; i < classes.length; ++i) {
         classes[i] = Class.forName(classNames[i], true, cl);
      }

      Class[] interfaces = getAllInterfaces(classes);

      for(int i = 0; i < interfaces.length; ++i) {
         StubGenerator stubGenerator = new StubGenerator(interfaces[i]);
         String fileName = stubGenerator.getClassName().replace('.', File.separatorChar) + ".class";
         ensureDirectoryExists(outputDir, fileName);
         stubGenerator.write(new FileOutputStream(outputDir + File.separatorChar + fileName));
      }

   }

   public static void main(String[] args) {
      try {
         Getopt2 opts = new Getopt2();
         opts.grok(args);
         generate(opts);
      } catch (Exception var2) {
         System.err.println("ERROR: " + var2);
         var2.printStackTrace();
      }

   }

   private static class MethodComparator implements Comparator {
      private MethodComparator() {
      }

      public int compare(Method m1, Method m2) {
         return m1.toString().compareTo(m2.toString());
      }

      // $FF: synthetic method
      MethodComparator(java.lang.Object x0) {
         this();
      }
   }
}
