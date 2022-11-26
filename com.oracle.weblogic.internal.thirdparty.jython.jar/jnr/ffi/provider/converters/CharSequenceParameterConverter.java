package jnr.ffi.provider.converters;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import jnr.ffi.annotations.Encoding;
import jnr.ffi.annotations.In;
import jnr.ffi.annotations.NulTerminate;
import jnr.ffi.mapper.MethodParameterContext;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;

@ToNativeConverter.NoContext
@ToNativeConverter.Cacheable
public class CharSequenceParameterConverter implements ToNativeConverter {
   private static final ToNativeConverter DEFAULT = new CharSequenceParameterConverter(Charset.defaultCharset());
   private final ThreadLocal localEncoder = new ThreadLocal();
   private final Charset charset;

   public static ToNativeConverter getInstance(Charset charset, ToNativeContext toNativeContext) {
      return (ToNativeConverter)(Charset.defaultCharset().equals(charset) ? DEFAULT : new CharSequenceParameterConverter(charset));
   }

   public static ToNativeConverter getInstance(ToNativeContext toNativeContext) {
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

      return getInstance(charset, toNativeContext);
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

   private CharSequenceParameterConverter(Charset charset) {
      this.charset = charset;
   }

   public ByteBuffer toNative(CharSequence string, ToNativeContext context) {
      if (string == null) {
         return null;
      } else {
         CharsetEncoder encoder = StringUtil.getEncoder(this.charset, this.localEncoder);
         ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[(int)((float)string.length() * encoder.averageBytesPerChar()) + 4]);
         CharBuffer charBuffer = CharBuffer.wrap(string);
         encoder.reset();

         while(charBuffer.hasRemaining()) {
            CoderResult result = encoder.encode(charBuffer, byteBuffer, true);
            if (result.isUnderflow() && (result = encoder.flush(byteBuffer)).isUnderflow()) {
               break;
            }

            if (result.isOverflow()) {
               byteBuffer = grow(byteBuffer);
            } else {
               StringUtil.throwException(result);
            }
         }

         if (byteBuffer.remaining() <= 4) {
            byteBuffer = grow(byteBuffer);
         }

         byteBuffer.position(byteBuffer.position() + 4);
         byteBuffer.flip();
         return byteBuffer;
      }
   }

   private static ByteBuffer grow(ByteBuffer oldBuffer) {
      ByteBuffer buf = ByteBuffer.wrap(new byte[oldBuffer.capacity() * 2]);
      oldBuffer.flip();
      buf.put(oldBuffer);
      return buf;
   }

   @In
   @NulTerminate
   public Class nativeType() {
      return ByteBuffer.class;
   }
}
