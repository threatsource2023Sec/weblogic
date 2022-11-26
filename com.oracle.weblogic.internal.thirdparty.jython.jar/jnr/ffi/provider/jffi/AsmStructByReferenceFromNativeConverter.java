package jnr.ffi.provider.jffi;

import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import org.python.objectweb.asm.ClassReader;
import org.python.objectweb.asm.ClassVisitor;
import org.python.objectweb.asm.ClassWriter;
import org.python.objectweb.asm.Label;

@FromNativeConverter.NoContext
@FromNativeConverter.Cacheable
public abstract class AsmStructByReferenceFromNativeConverter implements FromNativeConverter {
   private final Runtime runtime;
   private final int flags;
   static final Map converterClasses = new ConcurrentHashMap();
   private static final AtomicLong nextClassID = new AtomicLong(0L);

   protected AsmStructByReferenceFromNativeConverter(Runtime runtime, int flags) {
      this.runtime = runtime;
      this.flags = flags;
   }

   public final Class nativeType() {
      return Pointer.class;
   }

   protected final Runtime getRuntime() {
      return this.runtime;
   }

   static AsmStructByReferenceFromNativeConverter newStructByReferenceConverter(Runtime runtime, Class structClass, int flags, AsmClassLoader classLoader) {
      try {
         return (AsmStructByReferenceFromNativeConverter)newStructByReferenceClass(structClass, classLoader).getConstructor(Runtime.class, Integer.TYPE).newInstance(runtime, flags);
      } catch (NoSuchMethodException var5) {
         throw new RuntimeException(var5);
      } catch (IllegalAccessException var6) {
         throw new RuntimeException(var6);
      } catch (InstantiationException var7) {
         throw new RuntimeException(var7);
      } catch (InvocationTargetException var8) {
         throw new RuntimeException(var8);
      }
   }

   static Class newStructByReferenceClass(Class structClass, AsmClassLoader classLoader) {
      try {
         Constructor cons = structClass.asSubclass(Struct.class).getConstructor(Runtime.class);
         if (!Modifier.isPublic(cons.getModifiers())) {
            throw new RuntimeException(structClass.getName() + " constructor is not public");
         }
      } catch (NoSuchMethodException var11) {
         throw new RuntimeException("struct subclass " + structClass.getName() + " has no constructor that takes a " + Runtime.class.getName(), var11);
      }

      ClassWriter cw = new ClassWriter(2);
      ClassVisitor cv = AsmLibraryLoader.DEBUG ? AsmUtil.newCheckClassAdapter(cw) : cw;
      String className = CodegenUtils.p(structClass) + "$$jnr$$StructByReferenceFromNativeConverter$$" + nextClassID.getAndIncrement();
      ((ClassVisitor)cv).visit(49, 17, className, (String)null, CodegenUtils.p(AsmStructByReferenceFromNativeConverter.class), new String[0]);
      ((ClassVisitor)cv).visitAnnotation(CodegenUtils.ci(FromNativeConverter.NoContext.class), true);
      SkinnyMethodAdapter init = new SkinnyMethodAdapter((ClassVisitor)cv, 1, "<init>", CodegenUtils.sig(Void.TYPE, Runtime.class, Integer.TYPE), (String)null, (String[])null);
      init.start();
      init.aload(0);
      init.aload(1);
      init.iload(2);
      init.invokespecial(CodegenUtils.p(AsmStructByReferenceFromNativeConverter.class), "<init>", CodegenUtils.sig(Void.TYPE, Runtime.class, Integer.TYPE));
      init.voidreturn();
      init.visitMaxs(10, 10);
      init.visitEnd();
      SkinnyMethodAdapter fromNative = new SkinnyMethodAdapter((ClassVisitor)cv, 17, "fromNative", CodegenUtils.sig(structClass, Pointer.class, FromNativeContext.class), (String)null, (String[])null);
      fromNative.start();
      Label nullPointer = new Label();
      fromNative.aload(1);
      fromNative.ifnull(nullPointer);
      fromNative.newobj(CodegenUtils.p(structClass));
      fromNative.dup();
      fromNative.aload(0);
      fromNative.invokevirtual(CodegenUtils.p(AsmStructByReferenceFromNativeConverter.class), "getRuntime", CodegenUtils.sig(Runtime.class));
      fromNative.invokespecial(structClass, "<init>", Void.TYPE, Runtime.class);
      fromNative.dup();
      fromNative.aload(1);
      fromNative.invokevirtual(structClass, "useMemory", Void.TYPE, Pointer.class);
      fromNative.areturn();
      fromNative.label(nullPointer);
      fromNative.aconst_null();
      fromNative.areturn();
      fromNative.visitAnnotation(CodegenUtils.ci(FromNativeConverter.NoContext.class), true);
      fromNative.visitMaxs(10, 10);
      fromNative.visitEnd();
      fromNative = new SkinnyMethodAdapter((ClassVisitor)cv, 17, "fromNative", CodegenUtils.sig(Object.class, Object.class, FromNativeContext.class), (String)null, (String[])null);
      fromNative.start();
      fromNative.aload(0);
      fromNative.aload(1);
      fromNative.checkcast(Pointer.class);
      fromNative.aload(2);
      fromNative.invokevirtual(className, "fromNative", CodegenUtils.sig(structClass, Pointer.class, FromNativeContext.class));
      fromNative.areturn();
      fromNative.visitAnnotation(CodegenUtils.ci(FromNativeConverter.NoContext.class), true);
      fromNative.visitMaxs(10, 10);
      fromNative.visitEnd();
      ((ClassVisitor)cv).visitEnd();

      try {
         byte[] bytes = cw.toByteArray();
         if (AsmLibraryLoader.DEBUG) {
            ClassVisitor trace = AsmUtil.newTraceClassVisitor(new PrintWriter(System.err));
            (new ClassReader(bytes)).accept(trace, 0);
         }

         return classLoader.defineClass(className.replace("/", "."), bytes);
      } catch (Throwable var10) {
         throw new RuntimeException(var10);
      }
   }
}
