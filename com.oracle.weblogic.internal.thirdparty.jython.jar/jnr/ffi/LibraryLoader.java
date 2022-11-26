package jnr.ffi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import jnr.ffi.mapper.CompositeFunctionMapper;
import jnr.ffi.mapper.CompositeTypeMapper;
import jnr.ffi.mapper.DataConverter;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.FunctionMapper;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.mapper.SignatureTypeMapperAdapter;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.mapper.TypeMapper;
import jnr.ffi.provider.FFIProvider;
import jnr.ffi.provider.LoadedLibrary;

public abstract class LibraryLoader {
   private final List searchPaths = new ArrayList();
   private final List libraryNames = new ArrayList();
   private final List typeMappers = new ArrayList();
   private final List functionMappers = new ArrayList();
   private final Map optionMap = new EnumMap(LibraryOption.class);
   private final TypeMapper.Builder typeMapperBuilder = new TypeMapper.Builder();
   private final FunctionMapper.Builder functionMapperBuilder = new FunctionMapper.Builder();
   private final Class interfaceClass;
   private boolean failImmediately = false;

   public static LibraryLoader create(Class interfaceClass) {
      return FFIProvider.getSystemProvider().createLibraryLoader(interfaceClass);
   }

   protected LibraryLoader(Class interfaceClass) {
      this.interfaceClass = interfaceClass;
   }

   public static boolean saveError(Map options, boolean methodHasSave, boolean methodHasIgnore) {
      boolean saveError = options.containsKey(LibraryOption.SaveError) || !options.containsKey(LibraryOption.IgnoreError);
      if (saveError) {
         if (methodHasIgnore && !methodHasSave) {
            saveError = false;
         }
      } else if (methodHasSave) {
         saveError = true;
      }

      return saveError;
   }

   public LibraryLoader library(String libraryName) {
      this.libraryNames.add(libraryName);
      return this;
   }

   public LibraryLoader search(String path) {
      this.searchPaths.add(path);
      return this;
   }

   public LibraryLoader option(LibraryOption option, Object value) {
      switch (option) {
         case TypeMapper:
            if (value instanceof SignatureTypeMapper) {
               this.mapper((SignatureTypeMapper)value);
            } else if (value instanceof TypeMapper) {
               this.mapper((TypeMapper)value);
            } else if (value != null) {
               throw new IllegalArgumentException("invalid TypeMapper: " + value.getClass());
            }
            break;
         case FunctionMapper:
            this.mapper((FunctionMapper)value);
            break;
         default:
            this.optionMap.put(option, value);
      }

      return this;
   }

   public LibraryLoader mapper(TypeMapper typeMapper) {
      this.typeMappers.add(new SignatureTypeMapperAdapter(typeMapper));
      return this;
   }

   public LibraryLoader mapper(SignatureTypeMapper typeMapper) {
      this.typeMappers.add(typeMapper);
      return this;
   }

   public LibraryLoader map(Class javaType, ToNativeConverter toNativeConverter) {
      this.typeMapperBuilder.map(javaType, toNativeConverter);
      return this;
   }

   public LibraryLoader map(Class javaType, FromNativeConverter fromNativeConverter) {
      this.typeMapperBuilder.map(javaType, fromNativeConverter);
      return this;
   }

   public LibraryLoader map(Class javaType, DataConverter dataConverter) {
      this.typeMapperBuilder.map(javaType, dataConverter);
      return this;
   }

   public LibraryLoader mapper(FunctionMapper functionMapper) {
      this.functionMappers.add(functionMapper);
      return this;
   }

   public LibraryLoader map(String javaName, String nativeFunction) {
      this.functionMapperBuilder.map(javaName, nativeFunction);
      return this;
   }

   public LibraryLoader convention(CallingConvention convention) {
      this.optionMap.put(LibraryOption.CallingConvention, convention);
      return this;
   }

   public final LibraryLoader stdcall() {
      return this.convention(CallingConvention.STDCALL);
   }

   public final LibraryLoader failImmediately() {
      this.failImmediately = true;
      return this;
   }

   public Object load(String libraryName) {
      return this.library(libraryName).load();
   }

   public Object load() {
      if (this.libraryNames.isEmpty()) {
         throw new UnsatisfiedLinkError("no library names specified");
      } else {
         this.typeMappers.add(0, new SignatureTypeMapperAdapter(this.typeMapperBuilder.build()));
         this.optionMap.put(LibraryOption.TypeMapper, this.typeMappers.size() > 1 ? new CompositeTypeMapper(this.typeMappers) : (SignatureTypeMapper)this.typeMappers.get(0));
         this.functionMappers.add(0, this.functionMapperBuilder.build());
         this.optionMap.put(LibraryOption.FunctionMapper, this.functionMappers.size() > 1 ? new CompositeFunctionMapper(this.functionMappers) : (FunctionMapper)this.functionMappers.get(0));

         try {
            return this.loadLibrary(this.interfaceClass, Collections.unmodifiableList(this.libraryNames), this.getSearchPaths(), Collections.unmodifiableMap(this.optionMap));
         } catch (LinkageError var3) {
            if (this.failImmediately) {
               throw var3;
            } else {
               return this.createErrorProxy(var3);
            }
         } catch (Exception var4) {
            RuntimeException re = var4 instanceof RuntimeException ? (RuntimeException)var4 : new RuntimeException(var4);
            if (this.failImmediately) {
               throw re;
            } else {
               return this.createErrorProxy(re);
            }
         }
      }
   }

   private Object createErrorProxy(final Throwable ex) {
      return this.interfaceClass.cast(Proxy.newProxyInstance(this.interfaceClass.getClassLoader(), new Class[]{this.interfaceClass, LoadedLibrary.class}, new InvocationHandler() {
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            throw ex;
         }
      }));
   }

   private Collection getSearchPaths() {
      List paths = new ArrayList(this.searchPaths);
      paths.addAll(LibraryLoader.StaticDataHolder.USER_LIBRARY_PATH);
      return Collections.unmodifiableList(paths);
   }

   protected abstract Object loadLibrary(Class var1, Collection var2, Collection var3, Map var4);

   private static List getPropertyPaths(String propName) {
      String value = System.getProperty(propName);
      if (value != null) {
         String[] paths = value.split(File.pathSeparator);
         return new ArrayList(Arrays.asList(paths));
      } else {
         return Collections.emptyList();
      }
   }

   private static final class StaticDataHolder {
      private static final List USER_LIBRARY_PATH;

      private static void addPaths(List paths, File file) {
         if (file.isFile() && file.exists()) {
            BufferedReader in = null;

            try {
               in = new BufferedReader(new FileReader(file));

               for(String line = in.readLine(); line != null; line = in.readLine()) {
                  if ((new File(line)).exists()) {
                     paths.add(line);
                  }
               }
            } catch (IOException var12) {
            } finally {
               if (in != null) {
                  try {
                     in.close();
                  } catch (IOException var11) {
                  }
               }

            }

         }
      }

      static {
         List paths = new ArrayList();

         try {
            paths.addAll(LibraryLoader.getPropertyPaths("jnr.ffi.library.path"));
            paths.addAll(LibraryLoader.getPropertyPaths("jaffl.library.path"));
            paths.addAll(LibraryLoader.getPropertyPaths("jna.library.path"));
            paths.addAll(LibraryLoader.getPropertyPaths("java.library.path"));
         } catch (Exception var7) {
         }

         switch (Platform.getNativePlatform().getOS()) {
            case FREEBSD:
            case OPENBSD:
            case NETBSD:
            case LINUX:
            case ZLINUX:
               File ldSoConf = new File("/etc/ld.so.conf");
               File ldSoConfD = new File("/etc/ld.so.conf.d");
               if (ldSoConf.exists()) {
                  addPaths(paths, ldSoConf);
               }

               if (ldSoConfD.isDirectory()) {
                  File[] var3 = ldSoConfD.listFiles();
                  int var4 = var3.length;

                  for(int var5 = 0; var5 < var4; ++var5) {
                     File file = var3[var5];
                     addPaths(paths, file);
                  }
               }
            default:
               USER_LIBRARY_PATH = Collections.unmodifiableList(new ArrayList(paths));
         }
      }
   }
}
