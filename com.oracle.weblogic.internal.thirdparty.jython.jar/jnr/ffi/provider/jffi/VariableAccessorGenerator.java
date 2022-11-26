package jnr.ffi.provider.jffi;

import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import jnr.ffi.NativeType;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.Variable;
import jnr.ffi.mapper.DefaultSignatureType;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.FromNativeType;
import jnr.ffi.mapper.SignatureType;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.mapper.ToNativeType;
import org.python.objectweb.asm.ClassReader;
import org.python.objectweb.asm.ClassVisitor;
import org.python.objectweb.asm.ClassWriter;

public class VariableAccessorGenerator {
   private final AtomicLong nextClassID = new AtomicLong(0L);
   private final Runtime runtime;
   static final Map pointerOperations;
   private static final PointerOp POINTER_OP_POINTER;

   public VariableAccessorGenerator(Runtime runtime) {
      this.runtime = runtime;
   }

   public void generate(AsmBuilder builder, Class interfaceClass, String variableName, long address, Class javaType, Collection annotations, SignatureTypeMapper typeMapper, AsmClassLoader classLoader) {
      if (!NativeLibraryLoader.ASM_ENABLED) {
         throw new UnsupportedOperationException("asm bytecode generation not supported");
      } else {
         SimpleNativeContext context = new SimpleNativeContext(builder.getRuntime(), annotations);
         SignatureType signatureType = DefaultSignatureType.create(javaType, (FromNativeContext)context);
         FromNativeType fromNativeType = typeMapper.getFromNativeType(signatureType, context);
         FromNativeConverter fromNativeConverter = fromNativeType != null ? fromNativeType.getFromNativeConverter() : null;
         ToNativeType toNativeType = typeMapper.getToNativeType(signatureType, context);
         ToNativeConverter toNativeConverter = toNativeType != null ? toNativeType.getToNativeConverter() : null;
         Variable variableAccessor = this.buildVariableAccessor(builder.getRuntime(), address, interfaceClass, javaType, annotations, toNativeConverter, fromNativeConverter, classLoader);
         SkinnyMethodAdapter mv = new SkinnyMethodAdapter(builder.getClassVisitor(), 17, variableName, CodegenUtils.sig(Variable.class), (String)null, (String[])null);
         mv.start();
         mv.aload(0);
         mv.getfield(builder.getClassNamePath(), builder.getVariableName(variableAccessor), CodegenUtils.ci(Variable.class));
         mv.areturn();
         mv.visitMaxs(10, 10);
         mv.visitEnd();
      }
   }

   Variable buildVariableAccessor(Runtime runtime, long address, Class interfaceClass, Class javaType, Collection annotations, ToNativeConverter toNativeConverter, FromNativeConverter fromNativeConverter, AsmClassLoader classLoader) {
      boolean debug = AsmLibraryLoader.DEBUG && !InvokerUtil.hasAnnotation(annotations, NoTrace.class);
      ClassWriter cw = new ClassWriter(2);
      ClassVisitor cv = debug ? AsmUtil.newCheckClassAdapter(cw) : cw;
      AsmBuilder builder = new AsmBuilder(runtime, CodegenUtils.p(interfaceClass) + "$VariableAccessor$$" + this.nextClassID.getAndIncrement(), (ClassVisitor)cv, classLoader);
      ((ClassVisitor)cv).visit(50, 17, builder.getClassNamePath(), (String)null, CodegenUtils.p(Object.class), new String[]{CodegenUtils.p(Variable.class)});
      SkinnyMethodAdapter set = new SkinnyMethodAdapter(builder.getClassVisitor(), 17, "set", CodegenUtils.sig(Void.TYPE, Object.class), (String)null, (String[])null);
      Class boxedType = toNativeConverter != null ? toNativeConverter.nativeType() : javaType;
      NativeType nativeType = Types.getType(runtime, boxedType, annotations).getNativeType();
      jnr.ffi.provider.ToNativeType toNativeType = new jnr.ffi.provider.ToNativeType(javaType, nativeType, annotations, toNativeConverter, (ToNativeContext)null);
      jnr.ffi.provider.FromNativeType fromNativeType = new jnr.ffi.provider.FromNativeType(javaType, nativeType, annotations, fromNativeConverter, (FromNativeContext)null);
      PointerOp pointerOp = (PointerOp)pointerOperations.get(nativeType);
      if (pointerOp == null) {
         throw new IllegalArgumentException("global variable type not supported: " + javaType);
      } else {
         set.start();
         set.aload(0);
         Pointer pointer = DirectMemoryIO.wrap(runtime, address);
         set.getfield(builder.getClassNamePath(), builder.getObjectFieldName(pointer, Pointer.class), CodegenUtils.ci(Pointer.class));
         set.lconst_0();
         set.aload(1);
         set.checkcast(javaType);
         AsmUtil.emitToNativeConversion(builder, set, toNativeType);
         ToNativeOp toNativeOp = ToNativeOp.get(toNativeType);
         if (toNativeOp != null && toNativeOp.isPrimitive()) {
            toNativeOp.emitPrimitive(set, pointerOp.nativeIntClass, toNativeType.getNativeType());
         } else {
            if (!Pointer.class.isAssignableFrom(toNativeType.effectiveJavaType())) {
               throw new IllegalArgumentException("global variable type not supported: " + javaType);
            }

            pointerOp = POINTER_OP_POINTER;
         }

         pointerOp.put(set);
         set.voidreturn();
         set.visitMaxs(10, 10);
         set.visitEnd();
         SkinnyMethodAdapter get = new SkinnyMethodAdapter(builder.getClassVisitor(), 17, "get", CodegenUtils.sig(Object.class), (String)null, (String[])null);
         get.start();
         get.aload(0);
         get.getfield(builder.getClassNamePath(), builder.getObjectFieldName(pointer, Pointer.class), CodegenUtils.ci(Pointer.class));
         get.lconst_0();
         pointerOp.get(get);
         AsmUtil.emitFromNativeConversion(builder, get, fromNativeType, pointerOp.nativeIntClass);
         get.areturn();
         get.visitMaxs(10, 10);
         get.visitEnd();
         SkinnyMethodAdapter init = new SkinnyMethodAdapter((ClassVisitor)cv, 1, "<init>", CodegenUtils.sig(Void.TYPE, Object[].class), (String)null, (String[])null);
         init.start();
         init.aload(0);
         init.invokespecial(CodegenUtils.p(Object.class), "<init>", CodegenUtils.sig(Void.TYPE));
         builder.emitFieldInitialization(init, 1);
         init.voidreturn();
         init.visitMaxs(10, 10);
         init.visitEnd();
         ((ClassVisitor)cv).visitEnd();

         try {
            byte[] bytes = cw.toByteArray();
            if (debug) {
               ClassVisitor trace = AsmUtil.newTraceClassVisitor(new PrintWriter(System.err));
               (new ClassReader(bytes)).accept(trace, 0);
            }

            Class implClass = classLoader.defineClass(builder.getClassNamePath().replace("/", "."), bytes);
            Constructor cons = implClass.getDeclaredConstructor(Object[].class);
            return (Variable)cons.newInstance((Object)builder.getObjectFieldValues());
         } catch (Throwable var27) {
            throw new RuntimeException(var27);
         }
      }
   }

   private static void op(Map ops, NativeType type, String name, Class nativeIntType) {
      ops.put(type, new PointerOp(name, nativeIntType));
   }

   static {
      Map ops = new EnumMap(NativeType.class);
      op(ops, NativeType.SCHAR, "Byte", Byte.TYPE);
      op(ops, NativeType.UCHAR, "Byte", Byte.TYPE);
      op(ops, NativeType.SSHORT, "Short", Short.TYPE);
      op(ops, NativeType.USHORT, "Short", Short.TYPE);
      op(ops, NativeType.SINT, "Int", Integer.TYPE);
      op(ops, NativeType.UINT, "Int", Integer.TYPE);
      op(ops, NativeType.SLONG, "Long", Long.TYPE);
      op(ops, NativeType.ULONG, "Long", Long.TYPE);
      op(ops, NativeType.SLONGLONG, "LongLong", Long.TYPE);
      op(ops, NativeType.ULONGLONG, "LongLong", Long.TYPE);
      op(ops, NativeType.FLOAT, "Float", Float.TYPE);
      op(ops, NativeType.DOUBLE, "Double", Double.TYPE);
      op(ops, NativeType.ADDRESS, "Address", Long.TYPE);
      pointerOperations = Collections.unmodifiableMap(ops);
      POINTER_OP_POINTER = new PointerOp("Pointer", Pointer.class);
   }

   private static final class PointerOp {
      private final String getMethodName;
      private final String putMethodName;
      final Class nativeIntClass;

      private PointerOp(String name, Class nativeIntClass) {
         this.getMethodName = "get" + name;
         this.putMethodName = "put" + name;
         this.nativeIntClass = nativeIntClass;
      }

      void put(SkinnyMethodAdapter mv) {
         mv.invokevirtual(Pointer.class, this.putMethodName, Void.TYPE, Long.TYPE, this.nativeIntClass);
      }

      void get(SkinnyMethodAdapter mv) {
         mv.invokevirtual(Pointer.class, this.getMethodName, this.nativeIntClass, Long.TYPE);
      }

      // $FF: synthetic method
      PointerOp(String x0, Class x1, Object x2) {
         this(x0, x1);
      }
   }
}
