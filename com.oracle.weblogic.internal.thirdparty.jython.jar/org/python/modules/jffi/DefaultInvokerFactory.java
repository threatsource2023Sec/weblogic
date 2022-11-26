package org.python.modules.jffi;

import com.kenai.jffi.HeapInvocationBuffer;
import com.kenai.jffi.MemoryIO;
import com.kenai.jffi.Platform;
import org.python.core.Py;
import org.python.core.PyNone;
import org.python.core.PyObject;

class DefaultInvokerFactory {
   public static final DefaultInvokerFactory getFactory() {
      return DefaultInvokerFactory.SingletonHolder.INSTANCE;
   }

   private DefaultInvokerFactory() {
   }

   final Invoker createInvoker(com.kenai.jffi.Function function, PyObject returnType, PyObject[] parameterTypes) {
      ParameterMarshaller[] marshallers = new ParameterMarshaller[parameterTypes.length];

      for(int i = 0; i < marshallers.length; ++i) {
         marshallers[i] = getMarshaller(parameterTypes[i]);
      }

      return this.createInvoker(function, returnType, marshallers);
   }

   final Invoker createInvoker(com.kenai.jffi.Function function, CType[] parameterTypes, CType returnType) {
      ParameterMarshaller[] marshallers = new ParameterMarshaller[parameterTypes.length];

      for(int i = 0; i < marshallers.length; ++i) {
         marshallers[i] = getMarshaller(parameterTypes[i]);
      }

      return this.createInvoker(function, (PyObject)returnType, (ParameterMarshaller[])marshallers);
   }

   final Invoker createInvoker(com.kenai.jffi.Function function, NativeType[] parameterTypes, NativeType returnType) {
      ParameterMarshaller[] marshallers = new ParameterMarshaller[parameterTypes.length];

      for(int i = 0; i < marshallers.length; ++i) {
         marshallers[i] = getMarshaller(parameterTypes[i]);
      }

      return this.createInvoker(function, returnType, marshallers);
   }

   final Invoker createInvoker(com.kenai.jffi.Function function, PyObject returnType, ParameterMarshaller[] marshallers) {
      CType cReturnType = CType.typeOf(returnType);
      if (cReturnType instanceof CType.Builtin) {
         return this.createInvoker(function, cReturnType.getNativeType(), marshallers);
      } else {
         throw Py.RuntimeError("Unsupported return type: " + returnType);
      }
   }

   final Invoker createInvoker(com.kenai.jffi.Function function, NativeType returnType, ParameterMarshaller[] marshallers) {
      switch (returnType) {
         case VOID:
            return new VoidInvoker(function, marshallers);
         case BYTE:
            return new Signed8Invoker(function, marshallers);
         case UBYTE:
            return new Unsigned8Invoker(function, marshallers);
         case SHORT:
            return new Signed16Invoker(function, marshallers);
         case USHORT:
            return new Unsigned16Invoker(function, marshallers);
         case INT:
            return new Signed32Invoker(function, marshallers);
         case UINT:
            return new Unsigned32Invoker(function, marshallers);
         case LONGLONG:
            return new Signed64Invoker(function, marshallers);
         case ULONGLONG:
            return new Unsigned64Invoker(function, marshallers);
         case LONG:
            return (Invoker)(Platform.getPlatform().longSize() == 32 ? new Signed32Invoker(function, marshallers) : new Signed64Invoker(function, marshallers));
         case ULONG:
            return (Invoker)(Platform.getPlatform().longSize() == 32 ? new Unsigned32Invoker(function, marshallers) : new Unsigned64Invoker(function, marshallers));
         case FLOAT:
            return new FloatInvoker(function, marshallers);
         case DOUBLE:
            return new DoubleInvoker(function, marshallers);
         case POINTER:
            return new PointerInvoker(function, marshallers);
         case STRING:
            return new StringInvoker(function, marshallers);
         default:
            throw Py.RuntimeError("Unsupported return type: " + returnType);
      }
   }

   static final ParameterMarshaller getMarshaller(NativeType type) {
      switch (type) {
         case BYTE:
            return DefaultInvokerFactory.Signed8Marshaller.INSTANCE;
         case UBYTE:
            return DefaultInvokerFactory.Unsigned8Marshaller.INSTANCE;
         case SHORT:
            return DefaultInvokerFactory.Signed16Marshaller.INSTANCE;
         case USHORT:
            return DefaultInvokerFactory.Unsigned16Marshaller.INSTANCE;
         case INT:
            return DefaultInvokerFactory.Signed32Marshaller.INSTANCE;
         case UINT:
            return DefaultInvokerFactory.Unsigned32Marshaller.INSTANCE;
         case LONGLONG:
            return DefaultInvokerFactory.Signed64Marshaller.INSTANCE;
         case ULONGLONG:
            return DefaultInvokerFactory.Unsigned64Marshaller.INSTANCE;
         case LONG:
            return Platform.getPlatform().longSize() == 32 ? DefaultInvokerFactory.Signed32Marshaller.INSTANCE : DefaultInvokerFactory.Signed64Marshaller.INSTANCE;
         case ULONG:
            return Platform.getPlatform().longSize() == 32 ? DefaultInvokerFactory.Unsigned32Marshaller.INSTANCE : DefaultInvokerFactory.Unsigned64Marshaller.INSTANCE;
         case FLOAT:
            return DefaultInvokerFactory.FloatMarshaller.INSTANCE;
         case DOUBLE:
            return DefaultInvokerFactory.DoubleMarshaller.INSTANCE;
         case POINTER:
            return DefaultInvokerFactory.PointerMarshaller.INSTANCE;
         case STRING:
            return DefaultInvokerFactory.StringMarshaller.INSTANCE;
         default:
            throw Py.RuntimeError("Unsupported parameter type: " + type);
      }
   }

   static final ParameterMarshaller getMarshaller(CType type) {
      if (type instanceof CType.Builtin) {
         return getMarshaller(type.getNativeType());
      } else if (type instanceof CType.Pointer) {
         return DefaultInvokerFactory.PointerMarshaller.INSTANCE;
      } else {
         throw Py.RuntimeError("Unsupported parameter type: " + type);
      }
   }

   static final ParameterMarshaller getMarshaller(PyObject type) {
      return getMarshaller(CType.typeOf(type));
   }

   // $FF: synthetic method
   DefaultInvokerFactory(Object x0) {
      this();
   }

   private static class PointerMarshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new PointerMarshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject parameter) {
         if (parameter instanceof Pointer) {
            buffer.putAddress(((Pointer)parameter).getMemory().getAddress());
         } else {
            if (parameter != Py.None) {
               throw Py.TypeError("expected pointer argument");
            }

            buffer.putAddress(0L);
         }

      }
   }

   private static class StringMarshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new StringMarshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject parameter) {
         if (parameter instanceof StringCData) {
            buffer.putAddress(((StringCData)parameter).getMemory().getAddress());
         } else if (parameter instanceof PyNone) {
            buffer.putAddress(0L);
         } else {
            byte[] bytes = parameter.toString().getBytes();
            buffer.putArray((byte[])bytes, 0, bytes.length, 5);
         }

      }
   }

   private static class DoubleMarshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new DoubleMarshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject arg) {
         buffer.putDouble(arg.asDouble());
      }
   }

   private static class FloatMarshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new FloatMarshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject arg) {
         buffer.putFloat((float)arg.asDouble());
      }
   }

   private static class Unsigned64Marshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new Unsigned64Marshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject arg) {
         buffer.putLong(Util.uint64Value(arg));
      }
   }

   private static class Signed64Marshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new Signed64Marshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject arg) {
         buffer.putLong(Util.int64Value(arg));
      }
   }

   private static class Unsigned32Marshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new Unsigned32Marshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject arg) {
         buffer.putInt(Util.uint32Value(arg));
      }
   }

   private static class Signed32Marshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new Signed32Marshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject arg) {
         buffer.putInt(Util.int32Value(arg));
      }
   }

   private static class Unsigned16Marshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new Unsigned16Marshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject arg) {
         buffer.putShort(Util.uint16Value(arg));
      }
   }

   private static class Signed16Marshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new Signed16Marshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject arg) {
         buffer.putShort(Util.int16Value(arg));
      }
   }

   private static class Unsigned8Marshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new Unsigned8Marshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject arg) {
         buffer.putByte(Util.uint8Value(arg));
      }
   }

   private static class Signed8Marshaller extends BaseMarshaller {
      public static final ParameterMarshaller INSTANCE = new Signed8Marshaller();

      public void marshal(HeapInvocationBuffer buffer, PyObject arg) {
         buffer.putByte(Util.int8Value(arg));
      }
   }

   abstract static class BaseMarshaller implements ParameterMarshaller {
   }

   private static final class StringInvoker extends BaseInvoker {
      private static final MemoryIO IO = MemoryIO.getInstance();

      public StringInvoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return JITRuntime.newString(this.jffiInvoker.invokeAddress(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class PointerInvoker extends BaseInvoker {
      public PointerInvoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return Py.newLong(this.jffiInvoker.invokeAddress(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class DoubleInvoker extends BaseInvoker {
      public DoubleInvoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return Py.newFloat(this.jffiInvoker.invokeDouble(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class FloatInvoker extends BaseInvoker {
      public FloatInvoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return Py.newFloat(this.jffiInvoker.invokeFloat(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class Unsigned64Invoker extends BaseInvoker {
      public Unsigned64Invoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return JITRuntime.newUnsigned64(this.jffiInvoker.invokeLong(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class Signed64Invoker extends BaseInvoker {
      public Signed64Invoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return JITRuntime.newSigned64(this.jffiInvoker.invokeLong(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class Unsigned32Invoker extends BaseInvoker {
      public Unsigned32Invoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return JITRuntime.newUnsigned32(this.jffiInvoker.invokeInt(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class Signed32Invoker extends BaseInvoker {
      public Signed32Invoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return JITRuntime.newSigned32(this.jffiInvoker.invokeInt(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class Unsigned16Invoker extends BaseInvoker {
      public Unsigned16Invoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return JITRuntime.newUnsigned16(this.jffiInvoker.invokeInt(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class Signed16Invoker extends BaseInvoker {
      public Signed16Invoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return JITRuntime.newSigned16(this.jffiInvoker.invokeInt(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class Unsigned8Invoker extends BaseInvoker {
      public Unsigned8Invoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return Util.newUnsigned8(this.jffiInvoker.invokeInt(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class Signed8Invoker extends BaseInvoker {
      public Signed8Invoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         return Util.newSigned8(this.jffiInvoker.invokeInt(this.jffiFunction, this.convertArguments(args)));
      }
   }

   private static final class VoidInvoker extends BaseInvoker {
      public VoidInvoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         super(function, marshallers);
      }

      public final PyObject invoke(PyObject[] args) {
         this.jffiInvoker.invokeInt(this.jffiFunction, this.convertArguments(args));
         return Py.None;
      }
   }

   private abstract static class BaseInvoker extends Invoker {
      final com.kenai.jffi.Function jffiFunction;
      final com.kenai.jffi.Invoker jffiInvoker = com.kenai.jffi.Invoker.getInstance();
      final ParameterMarshaller[] marshallers;
      final int arity;

      public BaseInvoker(com.kenai.jffi.Function function, ParameterMarshaller[] marshallers) {
         this.jffiFunction = function;
         this.marshallers = marshallers;
         this.arity = marshallers.length;
      }

      final HeapInvocationBuffer convertArguments(PyObject[] args) {
         this.checkArity(args);
         HeapInvocationBuffer buffer = new HeapInvocationBuffer(this.jffiFunction);

         for(int i = 0; i < this.marshallers.length; ++i) {
            this.marshallers[i].marshal(buffer, args[i]);
         }

         return buffer;
      }

      public final PyObject invoke() {
         return this.invoke(new PyObject[0]);
      }

      public final PyObject invoke(PyObject arg0) {
         return this.invoke(new PyObject[]{arg0});
      }

      public final PyObject invoke(PyObject arg0, PyObject arg1) {
         return this.invoke(new PyObject[]{arg0, arg1});
      }

      public final PyObject invoke(PyObject arg0, PyObject arg1, PyObject arg2) {
         return this.invoke(new PyObject[]{arg0, arg1, arg2});
      }

      public final PyObject invoke(PyObject arg0, PyObject arg1, PyObject arg2, PyObject arg3) {
         return this.invoke(new PyObject[]{arg0, arg1, arg2, arg3});
      }

      public final PyObject invoke(PyObject arg0, PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4) {
         return this.invoke(new PyObject[]{arg0, arg1, arg2, arg3, arg4});
      }

      public final PyObject invoke(PyObject arg0, PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4, PyObject arg5) {
         return this.invoke(new PyObject[]{arg0, arg1, arg2, arg3, arg4, arg5});
      }

      final void checkArity(PyObject[] args) {
         this.checkArity(args.length);
      }

      final void checkArity(int got) {
         if (got != this.arity) {
            throw Py.TypeError(String.format("expected %d args; got %d", this.arity, got));
         }
      }
   }

   interface ParameterMarshaller {
      void marshal(HeapInvocationBuffer var1, PyObject var2);
   }

   private static final class SingletonHolder {
      public static final DefaultInvokerFactory INSTANCE = new DefaultInvokerFactory();
   }
}
