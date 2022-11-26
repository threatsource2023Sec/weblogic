package jnr.ffi.provider.jffi;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import jnr.ffi.CallingConvention;
import jnr.ffi.LibraryOption;
import jnr.ffi.Runtime;
import jnr.ffi.Variable;
import jnr.ffi.annotations.Synchronized;
import jnr.ffi.mapper.CachingTypeMapper;
import jnr.ffi.mapper.CompositeTypeMapper;
import jnr.ffi.mapper.FunctionMapper;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.mapper.SignatureTypeMapperAdapter;
import jnr.ffi.mapper.TypeMapper;
import jnr.ffi.provider.IdentityFunctionMapper;
import jnr.ffi.provider.Invoker;
import jnr.ffi.provider.LoadedLibrary;
import jnr.ffi.provider.NativeInvocationHandler;
import jnr.ffi.provider.NullTypeMapper;
import jnr.ffi.util.Annotations;

class ReflectionLibraryLoader extends LibraryLoader {
   Object loadLibrary(NativeLibrary library, Class interfaceClass, Map libraryOptions) {
      return interfaceClass.cast(Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass, LoadedLibrary.class}, new NativeInvocationHandler(new LazyLoader(library, interfaceClass, libraryOptions))));
   }

   private static final class LazyLoader extends AbstractMap {
      private final DefaultInvokerFactory invokerFactory;
      private final Runtime runtime;
      private final AsmClassLoader classLoader;
      private final SignatureTypeMapper typeMapper;
      private final FunctionMapper functionMapper;
      private final CallingConvention libraryCallingConvention;
      private final boolean libraryIsSynchronized;
      private final NativeLibrary library;
      private final Class interfaceClass;
      private final Map libraryOptions;

      private LazyLoader(NativeLibrary library, Class interfaceClass, Map libraryOptions) {
         this.runtime = NativeRuntime.getInstance();
         this.classLoader = new AsmClassLoader();
         this.library = library;
         this.interfaceClass = interfaceClass;
         this.libraryOptions = libraryOptions;
         this.functionMapper = libraryOptions.containsKey(LibraryOption.FunctionMapper) ? (FunctionMapper)libraryOptions.get(LibraryOption.FunctionMapper) : IdentityFunctionMapper.getInstance();
         Object typeMapper;
         if (libraryOptions.containsKey(LibraryOption.TypeMapper)) {
            Object tm = libraryOptions.get(LibraryOption.TypeMapper);
            if (tm instanceof SignatureTypeMapper) {
               typeMapper = (SignatureTypeMapper)tm;
            } else {
               if (!(tm instanceof TypeMapper)) {
                  throw new IllegalArgumentException("TypeMapper option is not a valid TypeMapper instance");
               }

               typeMapper = new SignatureTypeMapperAdapter((TypeMapper)tm);
            }
         } else {
            typeMapper = new NullTypeMapper();
         }

         this.typeMapper = new CompositeTypeMapper(new SignatureTypeMapper[]{(SignatureTypeMapper)typeMapper, new CachingTypeMapper(new InvokerTypeMapper(new NativeClosureManager(this.runtime, (SignatureTypeMapper)typeMapper, this.classLoader), this.classLoader, NativeLibraryLoader.ASM_ENABLED))});
         this.libraryCallingConvention = InvokerUtil.getCallingConvention(interfaceClass, libraryOptions);
         this.libraryIsSynchronized = interfaceClass.isAnnotationPresent(Synchronized.class);
         this.invokerFactory = new DefaultInvokerFactory(this.runtime, library, this.typeMapper, this.functionMapper, this.libraryCallingConvention, libraryOptions, this.libraryIsSynchronized);
      }

      public Set entrySet() {
         throw new UnsupportedOperationException("not implemented");
      }

      public synchronized Invoker get(Object key) {
         if (!(key instanceof Method)) {
            throw new IllegalArgumentException("key not instance of Method");
         } else {
            Method method = (Method)key;
            if (Variable.class.isAssignableFrom(method.getReturnType())) {
               return this.getVariableAccessor(method);
            } else {
               return (Invoker)(method.getName().equals("getRuntime") && method.getReturnType().isAssignableFrom(NativeRuntime.class) ? new GetRuntimeInvoker(this.runtime) : this.invokerFactory.createInvoker(method));
            }
         }
      }

      private Invoker getVariableAccessor(Method method) {
         Collection annotations = Annotations.sortedAnnotationCollection(method.getAnnotations());
         String functionName = this.functionMapper.mapFunctionName(method.getName(), new NativeFunctionMapperContext(this.library, annotations));
         long symbolAddress = this.library.getSymbolAddress(functionName);
         if (symbolAddress == 0L) {
            return new FunctionNotFoundInvoker(method, functionName);
         } else {
            Variable variable = ReflectionVariableAccessorGenerator.createVariableAccessor(this.runtime, method, symbolAddress, this.typeMapper, annotations);
            return new VariableAcccessorInvoker(variable);
         }
      }

      // $FF: synthetic method
      LazyLoader(NativeLibrary x0, Class x1, Map x2, Object x3) {
         this(x0, x1, x2);
      }

      private static final class VariableAcccessorInvoker implements Invoker {
         private final Variable variable;

         private VariableAcccessorInvoker(Variable variable) {
            this.variable = variable;
         }

         public Object invoke(Object self, Object[] parameters) {
            return this.variable;
         }

         // $FF: synthetic method
         VariableAcccessorInvoker(Variable x0, Object x1) {
            this(x0);
         }
      }
   }

   private static final class GetRuntimeInvoker implements Invoker {
      private final Runtime runtime;

      private GetRuntimeInvoker(Runtime runtime) {
         this.runtime = runtime;
      }

      public Object invoke(Object self, Object[] parameters) {
         return this.runtime;
      }

      // $FF: synthetic method
      GetRuntimeInvoker(Runtime x0, Object x1) {
         this(x0);
      }
   }

   private static final class FunctionNotFoundInvoker implements Invoker {
      private final Method method;
      private final String functionName;

      private FunctionNotFoundInvoker(Method method, String functionName) {
         this.method = method;
         this.functionName = functionName;
      }

      public Object invoke(Object self, Object[] parameters) {
         throw new UnsatisfiedLinkError(String.format("native method '%s' not found for method %s", this.functionName, this.method));
      }

      // $FF: synthetic method
      FunctionNotFoundInvoker(Method x0, String x1, Object x2) {
         this(x0, x1);
      }
   }
}
