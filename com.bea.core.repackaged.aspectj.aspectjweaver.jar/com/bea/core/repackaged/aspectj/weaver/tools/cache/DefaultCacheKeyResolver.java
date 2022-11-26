package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.CRC32;

public class DefaultCacheKeyResolver implements CacheKeyResolver {
   public static final String GENERATED_SUFFIX = ".generated";
   public static final String WEAVED_SUFFIX = ".weaved";

   public String createClassLoaderScope(ClassLoader cl, List aspects) {
      String name = cl != null ? cl.getClass().getSimpleName() : "unknown";
      List hashableStrings = new LinkedList();
      StringBuilder hashable = new StringBuilder(256);
      URL[] urls;
      if (cl != null && cl instanceof URLClassLoader) {
         urls = ((URLClassLoader)cl).getURLs();

         for(int i = 0; i < urls.length; ++i) {
            hashableStrings.add(urls[i].toString());
         }
      }

      hashableStrings.addAll(aspects);
      Collections.sort(hashableStrings);
      Iterator it = hashableStrings.iterator();

      while(it.hasNext()) {
         String url = (String)it.next();
         hashable.append(url);
      }

      urls = null;
      byte[] bytes = hashable.toString().getBytes();
      String hash = this.crc(bytes);
      return name + '.' + hash;
   }

   private String crc(byte[] input) {
      CRC32 crc32 = new CRC32();
      crc32.update(input);
      return String.valueOf(crc32.getValue());
   }

   public String getGeneratedRegex() {
      return ".*.generated";
   }

   public String getWeavedRegex() {
      return ".*.weaved";
   }

   public String keyToClass(String key) {
      if (key.endsWith(".generated")) {
         return key.replaceAll(".generated$", "");
      } else {
         return key.endsWith(".weaved") ? key.replaceAll("\\.[^.]+.weaved", "") : key;
      }
   }

   public CachedClassReference weavedKey(String className, byte[] original_bytes) {
      String hash = this.crc(original_bytes);
      return new CachedClassReference(className + "." + hash + ".weaved", className);
   }

   public CachedClassReference generatedKey(String className) {
      return new CachedClassReference(className + ".generated", className);
   }
}
