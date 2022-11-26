package jnr.ffi;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import jnr.ffi.provider.FFIProvider;
import jnr.ffi.provider.LoadedLibrary;

/** @deprecated */
public final class Library {
   private static final Map customSearchPaths = new ConcurrentHashMap();
   private final String name;

   private Library(String libraryName) {
      this.name = libraryName;
   }

   /** @deprecated */
   public static Runtime getRuntime(Object library) {
      return ((LoadedLibrary)library).getRuntime();
   }

   /** @deprecated */
   public static Object loadLibrary(String libraryName, Class interfaceClass) {
      return loadLibrary(interfaceClass, libraryName);
   }

   /** @deprecated */
   public static Object loadLibrary(Class interfaceClass, String... libraryNames) {
      Map options = Collections.emptyMap();
      return loadLibrary(interfaceClass, options, libraryNames);
   }

   /** @deprecated */
   public static Object loadLibrary(String libraryName, Class interfaceClass, Map libraryOptions) {
      return loadLibrary(interfaceClass, libraryOptions, libraryName);
   }

   /** @deprecated */
   public static Object loadLibrary(Class interfaceClass, Map libraryOptions, String... libraryNames) {
      LibraryLoader loader = FFIProvider.getSystemProvider().createLibraryLoader(interfaceClass);
      String[] var4 = libraryNames;
      int var5 = libraryNames.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String libraryName = var4[var6];
         loader.library(libraryName);
         Iterator var8 = getLibraryPath(libraryName).iterator();

         while(var8.hasNext()) {
            String path = (String)var8.next();
            loader.search(path);
         }
      }

      Iterator var10 = libraryOptions.entrySet().iterator();

      while(var10.hasNext()) {
         Map.Entry option = (Map.Entry)var10.next();
         loader.option((LibraryOption)option.getKey(), option.getValue());
      }

      return loader.failImmediately().load();
   }

   /** @deprecated */
   public static synchronized void addLibraryPath(String libraryName, File path) {
      List customPaths = (List)customSearchPaths.get(libraryName);
      if (customPaths == null) {
         customPaths = new CopyOnWriteArrayList();
         customSearchPaths.put(libraryName, customPaths);
      }

      ((List)customPaths).add(path.getAbsolutePath());
   }

   /** @deprecated */
   public static List getLibraryPath(String libraryName) {
      List customPaths = (List)customSearchPaths.get(libraryName);
      return customPaths != null ? customPaths : Collections.emptyList();
   }

   /** @deprecated */
   @Deprecated
   public static Library getInstance(String libraryName) {
      return new Library(libraryName);
   }

   /** @deprecated */
   @Deprecated
   public String getName() {
      return this.name;
   }
}
