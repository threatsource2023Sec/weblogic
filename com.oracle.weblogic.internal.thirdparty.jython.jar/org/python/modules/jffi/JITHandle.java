package org.python.modules.jffi;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicInteger;

final class JITHandle {
   private static final int THRESHOLD = Integer.getInteger("jython.ctypes.compile.threshold", 100);
   private final JITSignature jitSignature;
   private volatile boolean compilationFailed = false;
   private final AtomicInteger counter = new AtomicInteger(0);
   private final JITCompiler compiler;
   private WeakReference compiledClassRef = null;

   JITHandle(JITCompiler compiler, JITSignature signature, boolean compilationFailed) {
      this.compiler = compiler;
      this.jitSignature = signature;
      this.compilationFailed = compilationFailed;
   }

   final boolean compilationFailed() {
      return this.compilationFailed;
   }

   final Invoker compile(com.kenai.jffi.Function function, NativeDataConverter resultConverter, NativeDataConverter[] parameterConverters) {
      if (!this.compilationFailed && this.counter.incrementAndGet() >= THRESHOLD) {
         Class compiledClass;
         synchronized(this) {
            if (this.compiledClassRef == null || (compiledClass = (Class)this.compiledClassRef.get()) == null) {
               compiledClass = this.newInvokerClass(this.jitSignature);
               if (compiledClass == null) {
                  this.compilationFailed = true;
                  return null;
               }

               this.compiler.registerClass(this, compiledClass);
               this.compiledClassRef = new WeakReference(compiledClass);
            }
         }

         try {
            Constructor cons = compiledClass.getDeclaredConstructor(com.kenai.jffi.Function.class, NativeDataConverter.class, NativeDataConverter[].class, Invoker.class);
            return (Invoker)cons.newInstance(function, resultConverter, parameterConverters, createFallbackInvoker(function, this.jitSignature));
         } catch (Throwable var7) {
            var7.printStackTrace();
            return null;
         }
      } else {
         return null;
      }
   }

   Class newInvokerClass(JITSignature jitSignature) {
      JITMethodGenerator generator = null;
      JITMethodGenerator[] generators = new JITMethodGenerator[]{new FastIntMethodGenerator(), new FastLongMethodGenerator(), new FastNumericMethodGenerator()};

      for(int i = 0; i < generators.length; ++i) {
         if (generators[i].isSupported(jitSignature)) {
            generator = generators[i];
            break;
         }
      }

      return generator == null ? null : (new AsmClassBuilder(generator, jitSignature)).build();
   }

   static Invoker createFallbackInvoker(com.kenai.jffi.Function function, JITSignature signature) {
      NativeType[] parameterTypes = new NativeType[signature.getParameterCount()];

      for(int i = 0; i < parameterTypes.length; ++i) {
         parameterTypes[i] = signature.getParameterType(i);
      }

      return DefaultInvokerFactory.getFactory().createInvoker(function, parameterTypes, signature.getResultType());
   }
}
