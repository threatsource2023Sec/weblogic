package org.glassfish.grizzly.utils;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.glassfish.grizzly.ThreadCache;

public final class Charsets {
   public static final String DEFAULT_CHARACTER_ENCODING;
   private static final ConcurrentMap charsetAliasMap;
   public static final Charset ASCII_CHARSET;
   public static final Charset UTF8_CHARSET;
   public static final Charset DEFAULT_CHARSET;
   public static final int CODECS_CACHE_SIZE = 4;
   private static final CharsetCodecResolver DECODER_RESOLVER;
   private static final CharsetCodecResolver ENCODER_RESOLVER;
   private static final ThreadCache.CachedTypeIndex CODECS_CACHE;
   private static volatile boolean areCharsetsPreloaded;

   public static Charset lookupCharset(String charsetName) {
      String charsetLowerCase = charsetName.toLowerCase(Locale.US);
      Charset charset = (Charset)charsetAliasMap.get(charsetLowerCase);
      if (charset == null) {
         if (areCharsetsPreloaded) {
            throw new UnsupportedCharsetException(charsetName);
         }

         Charset newCharset = Charset.forName(charsetLowerCase);
         Charset prevCharset = (Charset)charsetAliasMap.putIfAbsent(charsetLowerCase, newCharset);
         charset = prevCharset == null ? newCharset : prevCharset;
      }

      return charset;
   }

   public static void preloadAllCharsets() {
      synchronized(charsetAliasMap) {
         Map charsetsMap = Charset.availableCharsets();
         Iterator var2 = charsetsMap.values().iterator();

         while(var2.hasNext()) {
            Charset charset = (Charset)var2.next();
            charsetAliasMap.put(charset.name().toLowerCase(Locale.US), charset);
            Iterator var4 = charset.aliases().iterator();

            while(var4.hasNext()) {
               String alias = (String)var4.next();
               charsetAliasMap.put(alias.toLowerCase(Locale.US), charset);
            }
         }

         areCharsetsPreloaded = true;
      }
   }

   public static void drainAllCharsets() {
      synchronized(charsetAliasMap) {
         areCharsetsPreloaded = false;
         charsetAliasMap.clear();
      }
   }

   public static CharsetDecoder getCharsetDecoder(Charset charset) {
      if (charset == null) {
         throw new IllegalArgumentException("Charset can not be null");
      } else {
         CharsetDecoder decoder = obtainCodecsCache().getDecoder(charset);
         decoder.reset();
         return decoder;
      }
   }

   public static CharsetEncoder getCharsetEncoder(Charset charset) {
      if (charset == null) {
         throw new IllegalArgumentException("Charset can not be null");
      } else {
         CharsetEncoder encoder = obtainCodecsCache().getEncoder(charset);
         encoder.reset();
         return encoder;
      }
   }

   private static CodecsCache obtainCodecsCache() {
      CodecsCache cache = (CodecsCache)ThreadCache.getFromCache(CODECS_CACHE);
      if (cache == null) {
         cache = new CodecsCache();
         ThreadCache.putToCache(CODECS_CACHE, cache);
      }

      return cache;
   }

   static {
      if (Boolean.getBoolean(Charsets.class.getName() + ".preloadAllCharsets")) {
         preloadAllCharsets();
      }

      DEFAULT_CHARACTER_ENCODING = Charset.defaultCharset().name();
      charsetAliasMap = new ConcurrentHashMap(8);
      ASCII_CHARSET = lookupCharset("ASCII");
      UTF8_CHARSET = lookupCharset("UTF-8");
      DEFAULT_CHARSET = Charset.defaultCharset();
      DECODER_RESOLVER = new DecoderResolver();
      ENCODER_RESOLVER = new EncoderResolver();
      CODECS_CACHE = ThreadCache.obtainIndex(CodecsCache.class, 1);
   }

   private static final class EncoderResolver implements CharsetCodecResolver {
      private EncoderResolver() {
      }

      public Charset charset(Object element) {
         return ((CharsetEncoder)element).charset();
      }

      public Object newElement(Charset charset) {
         return charset.newEncoder();
      }

      // $FF: synthetic method
      EncoderResolver(Object x0) {
         this();
      }
   }

   private static final class DecoderResolver implements CharsetCodecResolver {
      private DecoderResolver() {
      }

      public Charset charset(Object element) {
         return ((CharsetDecoder)element).charset();
      }

      public Object newElement(Charset charset) {
         return charset.newDecoder();
      }

      // $FF: synthetic method
      DecoderResolver(Object x0) {
         this();
      }
   }

   private interface CharsetCodecResolver {
      Charset charset(Object var1);

      Object newElement(Charset var1);
   }

   private static final class CodecsCache {
      private final Object[] decoders;
      private final Object[] encoders;

      private CodecsCache() {
         this.decoders = new Object[4];
         this.encoders = new Object[4];
      }

      public CharsetDecoder getDecoder(Charset charset) {
         return (CharsetDecoder)obtainElementByCharset(this.decoders, charset, Charsets.DECODER_RESOLVER);
      }

      public CharsetEncoder getEncoder(Charset charset) {
         return (CharsetEncoder)obtainElementByCharset(this.encoders, charset, Charsets.ENCODER_RESOLVER);
      }

      private static Object obtainElementByCharset(Object[] array, Charset charset, CharsetCodecResolver resolver) {
         int i;
         Object currentElement;
         for(i = 0; i < array.length; ++i) {
            currentElement = array[i];
            if (currentElement == null) {
               ++i;
               break;
            }

            if (charset.equals(resolver.charset(currentElement))) {
               makeFirst(array, i, currentElement);
               return currentElement;
            }
         }

         currentElement = resolver.newElement(charset);
         makeFirst(array, i - 1, currentElement);
         return currentElement;
      }

      private static void makeFirst(Object[] array, int offs, Object element) {
         System.arraycopy(array, 0, array, 1, offs - 1 + 1);
         array[0] = element;
      }

      // $FF: synthetic method
      CodecsCache(Object x0) {
         this();
      }
   }
}
