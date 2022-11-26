package com.sun.faces.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import javax.json.stream.JsonGenerator;

public class Json {
   private static final String ERROR_INVALID_BEAN = "Cannot introspect object of type '%s' as bean.";
   private static final String ERROR_INVALID_GETTER = "Cannot invoke getter of property '%s' of bean '%s'.";

   public static String encode(Object object, Option... options) {
      StringWriter writer = new StringWriter();
      encode(object, writer, options);
      return writer.toString();
   }

   public static void encode(Object object, Writer writer, Option... options) {
      JsonGenerator generator = javax.json.Json.createGenerator(writer);
      Throwable var4 = null;

      try {
         generator.writeStartObject();
         encode("data", object, generator, options.length == 0 ? EnumSet.noneOf(Option.class) : EnumSet.copyOf(Arrays.asList(options)));
         generator.writeEnd();
      } catch (Throwable var13) {
         var4 = var13;
         throw var13;
      } finally {
         if (generator != null) {
            if (var4 != null) {
               try {
                  generator.close();
               } catch (Throwable var12) {
                  var4.addSuppressed(var12);
               }
            } else {
               generator.close();
            }
         }

      }

   }

   private static void encode(String name, Object object, JsonGenerator generator, EnumSet options) {
      if (object == null) {
         encodeNull(name, generator);
      } else if (object instanceof Boolean) {
         encodeBoolean(name, (Boolean)object, generator);
      } else if (object instanceof BigDecimal) {
         encodeBigDecimal(name, (BigDecimal)object, generator);
      } else if (object instanceof Double) {
         encodeDouble(name, (Double)object, generator);
      } else if (object instanceof BigInteger) {
         encodeBigInteger(name, (BigInteger)object, generator);
      } else if (object instanceof Integer) {
         encodeInteger(name, (Integer)object, generator);
      } else if (object instanceof Number) {
         encodeLong(name, ((Number)object).longValue(), generator);
      } else if (object instanceof Character) {
         encodeString(name, ((Character)object).toString(), generator);
      } else if (object instanceof CharSequence) {
         encodeString(name, ((CharSequence)object).toString(), generator);
      } else if (object instanceof Date) {
         encodeInstant(name, ((Date)object).toInstant().atZone(ZoneOffset.UTC).toInstant(), generator, options);
      } else if (object instanceof LocalDate) {
         encodeInstant(name, ((LocalDate)object).atStartOfDay(ZoneOffset.UTC).toInstant(), generator, options);
      } else if (object instanceof Instant) {
         encodeInstant(name, (Instant)object, generator, options);
      } else if (object.getClass().isArray()) {
         encodeArray(name, object, generator, options);
      } else if (object instanceof Collection) {
         encodeCollection(name, (Collection)object, generator, options);
      } else if (object instanceof Map) {
         encodeMap(name, (Map)object, generator, options);
      } else {
         encodeBean(name, object, generator, options);
      }

   }

   private static void encodeNull(String name, JsonGenerator generator) {
      if (name == null) {
         generator.writeNull();
      } else {
         generator.writeNull(name);
      }

   }

   private static void encodeBoolean(String name, Boolean value, JsonGenerator generator) {
      if (name == null) {
         generator.write(value);
      } else {
         generator.write(name, value);
      }

   }

   private static void encodeBigDecimal(String name, BigDecimal value, JsonGenerator generator) {
      if (name == null) {
         generator.write(value);
      } else {
         generator.write(name, value);
      }

   }

   private static void encodeDouble(String name, double value, JsonGenerator generator) {
      if (name == null) {
         generator.write(value);
      } else {
         generator.write(name, value);
      }

   }

   private static void encodeBigInteger(String name, BigInteger value, JsonGenerator generator) {
      if (name == null) {
         generator.write(value);
      } else {
         generator.write(name, value);
      }

   }

   private static void encodeInteger(String name, int value, JsonGenerator generator) {
      if (name == null) {
         generator.write(value);
      } else {
         generator.write(name, value);
      }

   }

   private static void encodeLong(String name, long value, JsonGenerator generator) {
      if (name == null) {
         generator.write(value);
      } else {
         generator.write(name, value);
      }

   }

   private static void encodeString(String name, String value, JsonGenerator generator) {
      if (name == null) {
         generator.write(value);
      } else {
         generator.write(name, value);
      }

   }

   private static void encodeInstant(String name, Instant value, JsonGenerator generator, EnumSet options) {
      encodeString(name, (options.contains(Json.Option.USE_RFC1123_DATE) ? DateTimeFormatter.RFC_1123_DATE_TIME : DateTimeFormatter.ISO_INSTANT).format(value), generator);
   }

   private static void encodeArray(String name, Object array, JsonGenerator generator, EnumSet options) {
      if (name == null) {
         generator.writeStartArray();
      } else {
         generator.writeStartArray(name);
      }

      boolean skipNullValues = options.contains(Json.Option.SKIP_NULL_VALUES);

      for(int i = 0; i < Array.getLength(array); ++i) {
         Object value = Array.get(array, i);
         if (value != null || !skipNullValues) {
            encode((String)null, value, generator, options);
         }
      }

      generator.writeEnd();
   }

   private static void encodeCollection(String name, Collection collection, JsonGenerator generator, EnumSet options) {
      if (name == null) {
         generator.writeStartArray();
      } else {
         generator.writeStartArray(name);
      }

      boolean skipNullValues = options.contains(Json.Option.SKIP_NULL_VALUES);
      Iterator var5 = collection.iterator();

      while(true) {
         Object value;
         do {
            if (!var5.hasNext()) {
               generator.writeEnd();
               return;
            }

            value = var5.next();
         } while(value == null && skipNullValues);

         encode((String)null, value, generator, options);
      }
   }

   private static void encodeMap(String name, Map map, JsonGenerator generator, EnumSet options) {
      if (name == null) {
         generator.writeStartObject();
      } else {
         generator.writeStartObject(name);
      }

      boolean skipNullValues = options.contains(Json.Option.SKIP_NULL_VALUES);
      Iterator var5 = map.entrySet().iterator();

      while(true) {
         Map.Entry entry;
         Object value;
         do {
            if (!var5.hasNext()) {
               generator.writeEnd();
               return;
            }

            entry = (Map.Entry)var5.next();
            value = entry.getValue();
         } while(value == null && skipNullValues);

         encode(String.valueOf(entry.getKey()), value, generator, options);
      }
   }

   private static void encodeBean(String name, Object bean, JsonGenerator generator, EnumSet options) {
      BeanInfo beanInfo;
      try {
         beanInfo = Introspector.getBeanInfo(bean.getClass());
      } catch (IntrospectionException var13) {
         throw new IllegalArgumentException(String.format("Cannot introspect object of type '%s' as bean.", bean.getClass()), var13);
      }

      if (name == null) {
         generator.writeStartObject();
      } else {
         generator.writeStartObject(name);
      }

      boolean skipNullValues = options.contains(Json.Option.SKIP_NULL_VALUES);
      PropertyDescriptor[] var6 = beanInfo.getPropertyDescriptors();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         PropertyDescriptor property = var6[var8];
         if (property.getReadMethod() != null && !"class".equals(property.getName())) {
            Object value;
            try {
               value = property.getReadMethod().invoke(bean);
            } catch (Exception var12) {
               throw new IllegalArgumentException(String.format("Cannot invoke getter of property '%s' of bean '%s'.", property.getName(), bean.getClass()), var12);
            }

            if (value != null || !skipNullValues) {
               encode(property.getName(), value, generator, options);
            }
         }
      }

      generator.writeEnd();
   }

   public static enum Option {
      SKIP_NULL_VALUES,
      USE_RFC1123_DATE;
   }
}
