package jnr.ffi.provider.converters;

import java.lang.annotation.Annotation;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import jnr.ffi.annotations.Encoding;
import jnr.ffi.mapper.MethodParameterContext;
import jnr.ffi.mapper.ToNativeContext;

final class StringUtil {
   private static final Charset UTF8 = Charset.forName("UTF-8");
   private static final Charset USASCII = Charset.forName("US-ASCII");
   private static final Charset ISO8859_1 = Charset.forName("ISO-8859-1");
   private static final Charset UTF16 = Charset.forName("UTF-16");
   private static final Charset UTF16LE = Charset.forName("UTF-16LE");
   private static final Charset UTF16BE = Charset.forName("UTF-16BE");

   private StringUtil() {
   }

   static CharsetEncoder getEncoder(Charset charset, ThreadLocal localEncoder) {
      Reference ref = (Reference)localEncoder.get();
      CharsetEncoder encoder;
      return ref != null && (encoder = (CharsetEncoder)ref.get()) != null && encoder.charset() == charset ? encoder : initEncoder(charset, localEncoder);
   }

   static CharsetDecoder getDecoder(Charset charset, ThreadLocal localDecoder) {
      Reference ref = (Reference)localDecoder.get();
      CharsetDecoder decoder;
      return ref != null && (decoder = (CharsetDecoder)ref.get()) != null && decoder.charset() == charset ? decoder : initDecoder(charset, localDecoder);
   }

   private static CharsetEncoder initEncoder(Charset charset, ThreadLocal localEncoder) {
      CharsetEncoder encoder = charset.newEncoder();
      encoder.onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
      localEncoder.set(new SoftReference(encoder));
      return encoder;
   }

   private static CharsetDecoder initDecoder(Charset charset, ThreadLocal localDecoder) {
      CharsetDecoder decoder = charset.newDecoder();
      decoder.onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
      localDecoder.set(new SoftReference(decoder));
      return decoder;
   }

   static Charset getCharset(ToNativeContext toNativeContext) {
      Charset charset = Charset.defaultCharset();
      Charset cs;
      if (toNativeContext instanceof MethodParameterContext) {
         cs = getEncodingCharset(Arrays.asList(((MethodParameterContext)toNativeContext).getMethod().getDeclaringClass().getAnnotations()));
         if (cs != null) {
            charset = cs;
         }

         cs = getEncodingCharset(Arrays.asList(((MethodParameterContext)toNativeContext).getMethod().getAnnotations()));
         if (cs != null) {
            charset = cs;
         }
      }

      cs = getEncodingCharset(toNativeContext.getAnnotations());
      if (cs != null) {
         charset = cs;
      }

      return charset;
   }

   private static Charset getEncodingCharset(Collection annotations) {
      Iterator var1 = annotations.iterator();

      Annotation a;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         a = (Annotation)var1.next();
      } while(!(a instanceof Encoding));

      return Charset.forName(((Encoding)a).value());
   }

   static void throwException(CoderResult result) {
      try {
         result.throwException();
      } catch (RuntimeException var2) {
         throw var2;
      } catch (CharacterCodingException var3) {
         throw new RuntimeException(var3);
      }
   }

   static int terminatorWidth(Charset charset) {
      if (!charset.equals(UTF8) && !charset.equals(USASCII) && !charset.equals(ISO8859_1)) {
         return !charset.equals(UTF16) && !charset.equals(UTF16LE) && !charset.equals(UTF16BE) ? 4 : 2;
      } else {
         return 1;
      }
   }

   static int stringLength(ByteBuffer in, int terminatorWidth) {
      int end;
      int tcount;
      int idx;
      if (in.hasArray()) {
         byte[] array = in.array();
         end = in.arrayOffset() + in.limit();
         tcount = 0;
         idx = in.arrayOffset() + in.position();

         while(idx < end) {
            if (array[idx++] == 0) {
               ++tcount;
            } else {
               tcount = 0;
            }

            if (tcount == terminatorWidth) {
               return idx - terminatorWidth;
            }
         }
      } else {
         int begin = in.position();
         end = in.limit();
         tcount = 0;
         idx = begin;

         while(idx < end) {
            if (in.get(idx++) == 0) {
               ++tcount;
            } else {
               tcount = 0;
            }

            if (tcount == terminatorWidth) {
               return idx - terminatorWidth;
            }
         }
      }

      return -1;
   }
}
