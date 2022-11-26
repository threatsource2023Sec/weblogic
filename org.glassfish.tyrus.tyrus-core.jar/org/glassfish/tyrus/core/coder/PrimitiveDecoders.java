package org.glassfish.tyrus.core.coder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import org.glassfish.tyrus.core.ReflectionHelper;

public abstract class PrimitiveDecoders extends CoderAdapter implements Decoder.Text {
   public static final List ALL = Collections.unmodifiableList(Arrays.asList(BooleanDecoder.class, ByteDecoder.class, CharacterDecoder.class, DoubleDecoder.class, FloatDecoder.class, IntegerDecoder.class, LongDecoder.class, ShortDecoder.class));
   public static final Map ALL_INSTANCES = getAllInstances();

   public boolean willDecode(String s) {
      return true;
   }

   private static Map getAllInstances() {
      Map map = new HashMap();
      Iterator var1 = ALL.iterator();

      while(var1.hasNext()) {
         Class dec = (Class)var1.next();
         Class type = ReflectionHelper.getClassType(dec, Decoder.Text.class);

         try {
            map.put(type, (Decoder.Text)dec.newInstance());
         } catch (Exception var5) {
            Logger.getLogger(PrimitiveDecoders.class.getName()).log(Level.WARNING, String.format("Decoder %s could not have been instantiated.", dec));
         }
      }

      return map;
   }

   public static class ShortDecoder extends PrimitiveDecoders {
      public Short decode(String s) throws DecodeException {
         try {
            Short result = Short.valueOf(s);
            return result;
         } catch (Exception var4) {
            throw new DecodeException(s, "Decoding failed", var4);
         }
      }
   }

   public static class LongDecoder extends PrimitiveDecoders {
      public Long decode(String s) throws DecodeException {
         try {
            Long result = Long.valueOf(s);
            return result;
         } catch (Exception var4) {
            throw new DecodeException(s, "Decoding failed", var4);
         }
      }
   }

   public static class IntegerDecoder extends PrimitiveDecoders {
      public Integer decode(String s) throws DecodeException {
         try {
            Integer result = Integer.valueOf(s);
            return result;
         } catch (Exception var4) {
            throw new DecodeException(s, "Decoding failed", var4);
         }
      }
   }

   public static class FloatDecoder extends PrimitiveDecoders {
      public Float decode(String s) throws DecodeException {
         try {
            Float result = Float.valueOf(s);
            return result;
         } catch (Exception var4) {
            throw new DecodeException(s, "Decoding failed", var4);
         }
      }
   }

   public static class DoubleDecoder extends PrimitiveDecoders {
      public Double decode(String s) throws DecodeException {
         try {
            Double result = Double.valueOf(s);
            return result;
         } catch (Exception var4) {
            throw new DecodeException(s, "Decoding failed", var4);
         }
      }
   }

   public static class CharacterDecoder extends PrimitiveDecoders {
      public Character decode(String s) throws DecodeException {
         try {
            Character result = s.charAt(0);
            return result;
         } catch (Exception var4) {
            throw new DecodeException(s, "Decoding failed", var4);
         }
      }
   }

   public static class ByteDecoder extends PrimitiveDecoders {
      public Byte decode(String s) throws DecodeException {
         try {
            Byte result = Byte.valueOf(s);
            return result;
         } catch (Exception var4) {
            throw new DecodeException(s, "Decoding failed", var4);
         }
      }
   }

   public static class BooleanDecoder extends PrimitiveDecoders {
      public Boolean decode(String s) throws DecodeException {
         try {
            Boolean result = Boolean.valueOf(s);
            return result;
         } catch (Exception var4) {
            throw new DecodeException(s, "Decoding failed", var4);
         }
      }
   }
}
