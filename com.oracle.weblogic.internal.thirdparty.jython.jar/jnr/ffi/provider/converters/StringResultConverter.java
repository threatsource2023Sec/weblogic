package jnr.ffi.provider.converters;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import jnr.ffi.Pointer;
import jnr.ffi.annotations.Encoding;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.MethodResultContext;

@FromNativeConverter.NoContext
@FromNativeConverter.Cacheable
public class StringResultConverter implements FromNativeConverter {
   private static final FromNativeConverter DEFAULT = new StringResultConverter(Charset.defaultCharset());
   private final ThreadLocal localDecoder = new ThreadLocal();
   private final Charset charset;
   private final int terminatorWidth;

   private StringResultConverter(Charset charset) {
      this.charset = charset;
      this.terminatorWidth = StringUtil.terminatorWidth(charset);
   }

   public static FromNativeConverter getInstance(Charset cs) {
      return (FromNativeConverter)(Charset.defaultCharset().equals(cs) ? DEFAULT : new StringResultConverter(cs));
   }

   public static FromNativeConverter getInstance(FromNativeContext fromNativeContext) {
      Charset charset = Charset.defaultCharset();
      Encoding e;
      if (fromNativeContext instanceof MethodResultContext) {
         e = getEncoding(Arrays.asList(((MethodResultContext)fromNativeContext).getMethod().getDeclaringClass().getAnnotations()));
         if (e != null) {
            charset = Charset.forName(e.value());
         }
      }

      e = getEncoding(fromNativeContext.getAnnotations());
      if (e != null) {
         charset = Charset.forName(e.value());
      }

      return getInstance(charset);
   }

   public String fromNative(Pointer pointer, FromNativeContext context) {
      if (pointer == null) {
         return null;
      } else {
         int idx = 0;

         label30:
         while(true) {
            idx += pointer.indexOf((long)idx, (byte)0);

            for(int tcount = 1; tcount < this.terminatorWidth; ++tcount) {
               if (pointer.getByte((long)(idx + tcount)) != 0) {
                  idx += tcount;
                  continue label30;
               }
            }

            byte[] bytes = new byte[idx];
            pointer.get(0L, (byte[])bytes, 0, bytes.length);

            try {
               return StringUtil.getDecoder(this.charset, this.localDecoder).reset().decode(ByteBuffer.wrap(bytes)).toString();
            } catch (CharacterCodingException var6) {
               throw new RuntimeException(var6);
            }
         }
      }
   }

   public Class nativeType() {
      return Pointer.class;
   }

   private static Encoding getEncoding(Collection annotations) {
      Iterator var1 = annotations.iterator();

      Annotation a;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         a = (Annotation)var1.next();
      } while(!(a instanceof Encoding));

      return (Encoding)a;
   }
}
