package org.python.modules.jffi;

import com.kenai.jffi.Platform;
import org.python.core.PyObject;
import org.python.objectweb.asm.Label;

abstract class AbstractNumericMethodGenerator implements JITMethodGenerator {
   public void generate(AsmClassBuilder builder, String functionName, JITSignature signature) {
      SkinnyMethodAdapter mv = new SkinnyMethodAdapter(builder.getClassVisitor(), 17, functionName, CodegenUtils.sig(PyObject.class, CodegenUtils.params(PyObject.class, signature.getParameterCount())), (String)null, (String[])null);
      mv.start();
      this.generate(builder, mv, signature);
      mv.visitMaxs(10, 10);
      mv.visitEnd();
   }

   public void generate(AsmClassBuilder builder, SkinnyMethodAdapter mv, JITSignature signature) {
      Class nativeIntType = this.getInvokerIntType();
      int maxPointerIndex = -1;
      Label[] fallback = new Label[signature.getParameterCount()];

      for(int i = 0; i < signature.getParameterCount(); ++i) {
         fallback[i] = new Label();
      }

      mv.getstatic(CodegenUtils.p(JITInvoker.class), "jffiInvoker", CodegenUtils.ci(com.kenai.jffi.Invoker.class));
      mv.aload(0);
      mv.getfield(CodegenUtils.p(JITInvoker.class), "jffiFunction", CodegenUtils.ci(com.kenai.jffi.Function.class));
      int firstParam = true;

      int i;
      for(i = 0; i < signature.getParameterCount(); ++i) {
         if (signature.hasParameterConverter(i)) {
            mv.aload(0);
            mv.getfield(builder.getClassName(), builder.getParameterConverterFieldName(i), CodegenUtils.ci(NativeDataConverter.class));
            mv.aload(1 + i);
            mv.invokevirtual(CodegenUtils.p(NativeDataConverter.class), "toNative", CodegenUtils.sig(PyObject.class, PyObject.class));
            mv.astore(1 + i);
         }
      }

      for(i = 0; i < signature.getParameterCount(); ++i) {
         NativeType parameterType = signature.getParameterType(i);
         int paramVar = i + 1;
         mv.aload(paramVar);
         switch (parameterType) {
            case BOOL:
               this.unbox(mv, "boolValue");
               break;
            case BYTE:
               this.unbox(mv, "s8Value");
               break;
            case UBYTE:
               this.unbox(mv, "u8Value");
               break;
            case SHORT:
               this.unbox(mv, "s16Value");
               break;
            case USHORT:
               this.unbox(mv, "u16Value");
               break;
            case INT:
               this.unbox(mv, "s32Value");
               break;
            case UINT:
               this.unbox(mv, "u32Value");
               break;
            case LONG:
               if (Platform.getPlatform().longSize() == 32) {
                  this.unbox(mv, "s32Value");
               } else {
                  this.unbox(mv, "s64Value");
               }
               break;
            case ULONG:
               if (Platform.getPlatform().longSize() == 32) {
                  this.unbox(mv, "u32Value");
               } else {
                  this.unbox(mv, "u64Value");
               }
               break;
            case LONGLONG:
               this.unbox(mv, "s64Value");
               break;
            case ULONGLONG:
               this.unbox(mv, "u64Value");
               break;
            case POINTER:
               maxPointerIndex = i;
               Label direct = new Label();
               Label done = new Label();
               Label converted = new Label();
               mv.instance_of(CodegenUtils.p(Pointer.class));
               mv.iftrue(direct);
               mv.aload(paramVar);
               mv.invokestatic(CodegenUtils.p(JITRuntime.class), "other2ptr", CodegenUtils.sig(PyObject.class, PyObject.class));
               mv.label(converted);
               mv.dup();
               mv.astore(paramVar);
               mv.instance_of(CodegenUtils.p(Pointer.class));
               mv.iffalse(fallback[i]);
               mv.label(direct);
               mv.aload(paramVar);
               this.unbox(mv, "pointerValue");
               mv.label(done);
               break;
            case FLOAT:
               this.unbox(mv, "f32Value");
               break;
            case DOUBLE:
               this.unbox(mv, "f64Value");
               break;
            default:
               throw new UnsupportedOperationException("unsupported parameter type " + parameterType);
         }
      }

      mv.invokevirtual(CodegenUtils.p(com.kenai.jffi.Invoker.class), this.getInvokerMethodName(signature), this.getInvokerSignature(signature.getParameterCount()));
      this.boxResult(mv, signature.getResultType());
      this.emitResultConversion(mv, builder, signature);
      mv.areturn();
      if (maxPointerIndex >= 0) {
         for(i = maxPointerIndex; i > 0; --i) {
            mv.label(fallback[i]);
            if (Integer.TYPE == nativeIntType) {
               mv.pop();
            } else {
               mv.pop2();
            }
         }

         mv.label(fallback[0]);
         mv.pop();
         mv.pop();
         mv.aload(0);
         mv.getfield(CodegenUtils.p(JITInvoker.class), "fallbackInvoker", CodegenUtils.ci(Invoker.class));

         for(i = 0; i < signature.getParameterCount(); ++i) {
            mv.aload(1 + i);
         }

         mv.invokevirtual(CodegenUtils.p(Invoker.class), "invoke", CodegenUtils.sig(PyObject.class, CodegenUtils.params(PyObject.class, signature.getParameterCount())));
         this.emitResultConversion(mv, builder, signature);
         mv.areturn();
      }

   }

   private void emitResultConversion(SkinnyMethodAdapter mv, AsmClassBuilder builder, JITSignature signature) {
      if (signature.hasResultConverter()) {
         mv.aload(0);
         mv.getfield(builder.getClassName(), builder.getResultConverterFieldName(), CodegenUtils.ci(NativeDataConverter.class));
         mv.swap();
         mv.invokevirtual(CodegenUtils.p(NativeDataConverter.class), "fromNative", CodegenUtils.sig(PyObject.class, PyObject.class));
      }

   }

   private void boxResult(SkinnyMethodAdapter mv, String boxMethodName) {
      mv.invokestatic(CodegenUtils.p(JITRuntime.class), boxMethodName, CodegenUtils.sig(PyObject.class, this.getInvokerIntType()));
   }

   private void boxResult(SkinnyMethodAdapter mv, NativeType type) {
      switch (type) {
         case BOOL:
            this.boxResult(mv, "newBoolean");
            break;
         case BYTE:
            this.boxResult(mv, "newSigned8");
            break;
         case UBYTE:
            this.boxResult(mv, "newUnsigned8");
            break;
         case SHORT:
            this.boxResult(mv, "newSigned16");
            break;
         case USHORT:
            this.boxResult(mv, "newUnsigned16");
            break;
         case INT:
            this.boxResult(mv, "newSigned32");
            break;
         case UINT:
            this.boxResult(mv, "newUnsigned32");
            break;
         case LONG:
            if (Platform.getPlatform().longSize() == 32) {
               this.boxResult(mv, "newSigned32");
            } else {
               this.boxResult(mv, "newSigned64");
            }
            break;
         case ULONG:
            if (Platform.getPlatform().longSize() == 32) {
               this.boxResult(mv, "newUnsigned32");
            } else {
               this.boxResult(mv, "newUnsigned64");
            }
            break;
         case LONGLONG:
            this.boxResult(mv, "newSigned64");
            break;
         case ULONGLONG:
            this.boxResult(mv, "newUnsigned64");
            break;
         case POINTER:
            this.boxResult(mv, "newPointer" + Platform.getPlatform().addressSize());
            break;
         case FLOAT:
            this.boxResult(mv, "newFloat32");
            break;
         case DOUBLE:
            this.boxResult(mv, "newFloat64");
            break;
         case VOID:
            this.boxResult(mv, "newNone");
            break;
         case STRING:
            this.boxResult(mv, "newString");
            break;
         default:
            throw new UnsupportedOperationException("native return type not supported: " + type);
      }

   }

   private void unbox(SkinnyMethodAdapter mv, String method) {
      mv.invokestatic(CodegenUtils.p(JITRuntime.class), this.getRuntimeMethod(method), CodegenUtils.sig(this.getInvokerIntType(), PyObject.class));
   }

   private String getRuntimeMethod(String method) {
      return method + (Integer.TYPE == this.getInvokerIntType() ? "32" : "64");
   }

   abstract String getInvokerMethodName(JITSignature var1);

   abstract String getInvokerSignature(int var1);

   abstract Class getInvokerIntType();

   public static boolean isPrimitiveInt(Class c) {
      return Byte.TYPE == c || Character.TYPE == c || Short.TYPE == c || Integer.TYPE == c || Boolean.TYPE == c;
   }

   public static final void widen(SkinnyMethodAdapter mv, Class from, Class to) {
      if (Long.TYPE == to && Long.TYPE != from && isPrimitiveInt(from)) {
         mv.i2l();
      }

   }

   public static final void narrow(SkinnyMethodAdapter mv, Class from, Class to) {
      if (!from.equals(to) && isPrimitiveInt(to)) {
         if (Long.TYPE == from) {
            mv.l2i();
         }

         if (Byte.TYPE == to) {
            mv.i2b();
         } else if (Short.TYPE == to) {
            mv.i2s();
         } else if (Character.TYPE == to) {
            mv.i2c();
         } else if (Boolean.TYPE == to) {
            mv.iconst_1();
            mv.iand();
         }
      }

   }

   protected static String[] buildSignatures(Class nativeIntClass, int maxParameters) {
      char sigChar = Integer.TYPE == nativeIntClass ? 73 : 74;
      String[] signatures = new String[maxParameters + 1];

      for(int i = 0; i < signatures.length; ++i) {
         StringBuilder sb = new StringBuilder();
         sb.append('(').append(CodegenUtils.ci(com.kenai.jffi.Function.class));

         for(int n = 0; n < i; ++n) {
            sb.append((char)sigChar);
         }

         signatures[i] = sb.append(")").append((char)sigChar).toString();
      }

      return signatures;
   }
}
