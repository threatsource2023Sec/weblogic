package jnr.ffi.provider;

import java.lang.reflect.Method;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import jnr.ffi.CallingConvention;
import jnr.ffi.Variable;
import jnr.ffi.annotations.StdCall;
import jnr.ffi.mapper.SignatureTypeMapper;

public class InterfaceScanner {
   private final Class interfaceClass;
   private final SignatureTypeMapper typeMapper;
   private final CallingConvention callingConvention;
   private final Method[] methods;
   private static final Method methodIsDefault;

   public InterfaceScanner(Class interfaceClass, SignatureTypeMapper typeMapper, CallingConvention callingConvention) {
      this.interfaceClass = interfaceClass;
      this.typeMapper = typeMapper;
      this.methods = interfaceClass.getMethods();
      this.callingConvention = interfaceClass.isAnnotationPresent(StdCall.class) ? CallingConvention.STDCALL : callingConvention;
   }

   public Collection functions() {
      return new AbstractCollection() {
         public Iterator iterator() {
            return InterfaceScanner.this.new FunctionsIterator(InterfaceScanner.this.methods);
         }

         public int size() {
            return 0;
         }
      };
   }

   public Collection variables() {
      return new AbstractCollection() {
         public Iterator iterator() {
            return InterfaceScanner.this.new VariablesIterator(InterfaceScanner.this.methods);
         }

         public int size() {
            return 0;
         }
      };
   }

   private static boolean isDefault(Method method) {
      if (methodIsDefault == null) {
         return false;
      } else {
         try {
            return Boolean.TRUE.equals(methodIsDefault.invoke(method));
         } catch (Exception var2) {
            throw new RuntimeException("Unexpected error attempting to call isDefault method", var2);
         }
      }
   }

   static {
      Method isDefault = null;

      try {
         isDefault = Method.class.getMethod("isDefault", (Class[])null);
      } catch (NoSuchMethodException var2) {
      }

      methodIsDefault = isDefault;
   }

   private final class VariablesIterator implements Iterator {
      private final Method[] methods;
      private int nextIndex;

      private VariablesIterator(Method[] methods) {
         this.methods = methods;
         this.nextIndex = 0;
      }

      public boolean hasNext() {
         while(this.nextIndex < this.methods.length) {
            if (Variable.class == this.methods[this.nextIndex].getReturnType()) {
               return true;
            }

            ++this.nextIndex;
         }

         return false;
      }

      public NativeVariable next() {
         return new NativeVariable(this.methods[this.nextIndex++]);
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      VariablesIterator(Method[] x1, Object x2) {
         this(x1);
      }
   }

   private final class FunctionsIterator implements Iterator {
      private final Method[] methods;
      private int nextIndex;

      private FunctionsIterator(Method[] methods) {
         this.methods = methods;
         this.nextIndex = 0;
      }

      public boolean hasNext() {
         while(this.nextIndex < this.methods.length) {
            if (!Variable.class.isAssignableFrom(this.methods[this.nextIndex].getReturnType()) && !InterfaceScanner.isDefault(this.methods[this.nextIndex])) {
               return true;
            }

            ++this.nextIndex;
         }

         return false;
      }

      public NativeFunction next() {
         CallingConvention callingConvention = this.methods[this.nextIndex].isAnnotationPresent(StdCall.class) ? CallingConvention.STDCALL : InterfaceScanner.this.callingConvention;
         return new NativeFunction(this.methods[this.nextIndex++], callingConvention);
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      FunctionsIterator(Method[] x1, Object x2) {
         this(x1);
      }
   }
}
