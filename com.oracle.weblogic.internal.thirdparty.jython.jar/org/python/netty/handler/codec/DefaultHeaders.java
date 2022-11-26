package org.python.netty.handler.codec;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.python.netty.util.HashingStrategy;
import org.python.netty.util.internal.MathUtil;
import org.python.netty.util.internal.ObjectUtil;

public class DefaultHeaders implements Headers {
   static final int HASH_CODE_SEED = -1028477387;
   private final HeaderEntry[] entries;
   protected final HeaderEntry head;
   private final byte hashMask;
   private final ValueConverter valueConverter;
   private final NameValidator nameValidator;
   private final HashingStrategy hashingStrategy;
   int size;

   public DefaultHeaders(ValueConverter valueConverter) {
      this(HashingStrategy.JAVA_HASHER, valueConverter);
   }

   public DefaultHeaders(ValueConverter valueConverter, NameValidator nameValidator) {
      this(HashingStrategy.JAVA_HASHER, valueConverter, nameValidator);
   }

   public DefaultHeaders(HashingStrategy nameHashingStrategy, ValueConverter valueConverter) {
      this(nameHashingStrategy, valueConverter, DefaultHeaders.NameValidator.NOT_NULL);
   }

   public DefaultHeaders(HashingStrategy nameHashingStrategy, ValueConverter valueConverter, NameValidator nameValidator) {
      this(nameHashingStrategy, valueConverter, nameValidator, 16);
   }

   public DefaultHeaders(HashingStrategy nameHashingStrategy, ValueConverter valueConverter, NameValidator nameValidator, int arraySizeHint) {
      this.valueConverter = (ValueConverter)ObjectUtil.checkNotNull(valueConverter, "valueConverter");
      this.nameValidator = (NameValidator)ObjectUtil.checkNotNull(nameValidator, "nameValidator");
      this.hashingStrategy = (HashingStrategy)ObjectUtil.checkNotNull(nameHashingStrategy, "nameHashingStrategy");
      this.entries = new HeaderEntry[MathUtil.findNextPositivePowerOfTwo(Math.max(2, Math.min(arraySizeHint, 128)))];
      this.hashMask = (byte)(this.entries.length - 1);
      this.head = new HeaderEntry();
   }

   public Object get(Object name) {
      ObjectUtil.checkNotNull(name, "name");
      int h = this.hashingStrategy.hashCode(name);
      int i = this.index(h);
      HeaderEntry e = this.entries[i];

      Object value;
      for(value = null; e != null; e = e.next) {
         if (e.hash == h && this.hashingStrategy.equals(name, e.key)) {
            value = e.value;
         }
      }

      return value;
   }

   public Object get(Object name, Object defaultValue) {
      Object value = this.get(name);
      return value == null ? defaultValue : value;
   }

   public Object getAndRemove(Object name) {
      int h = this.hashingStrategy.hashCode(name);
      return this.remove0(h, this.index(h), ObjectUtil.checkNotNull(name, "name"));
   }

   public Object getAndRemove(Object name, Object defaultValue) {
      Object value = this.getAndRemove(name);
      return value == null ? defaultValue : value;
   }

   public List getAll(Object name) {
      ObjectUtil.checkNotNull(name, "name");
      LinkedList values = new LinkedList();
      int h = this.hashingStrategy.hashCode(name);
      int i = this.index(h);

      for(HeaderEntry e = this.entries[i]; e != null; e = e.next) {
         if (e.hash == h && this.hashingStrategy.equals(name, e.key)) {
            values.addFirst(e.getValue());
         }
      }

      return values;
   }

   public List getAllAndRemove(Object name) {
      List all = this.getAll(name);
      this.remove(name);
      return all;
   }

   public boolean contains(Object name) {
      return this.get(name) != null;
   }

   public boolean containsObject(Object name, Object value) {
      return this.contains(name, this.valueConverter.convertObject(ObjectUtil.checkNotNull(value, "value")));
   }

   public boolean containsBoolean(Object name, boolean value) {
      return this.contains(name, this.valueConverter.convertBoolean(value));
   }

   public boolean containsByte(Object name, byte value) {
      return this.contains(name, this.valueConverter.convertByte(value));
   }

   public boolean containsChar(Object name, char value) {
      return this.contains(name, this.valueConverter.convertChar(value));
   }

   public boolean containsShort(Object name, short value) {
      return this.contains(name, this.valueConverter.convertShort(value));
   }

   public boolean containsInt(Object name, int value) {
      return this.contains(name, this.valueConverter.convertInt(value));
   }

   public boolean containsLong(Object name, long value) {
      return this.contains(name, this.valueConverter.convertLong(value));
   }

   public boolean containsFloat(Object name, float value) {
      return this.contains(name, this.valueConverter.convertFloat(value));
   }

   public boolean containsDouble(Object name, double value) {
      return this.contains(name, this.valueConverter.convertDouble(value));
   }

   public boolean containsTimeMillis(Object name, long value) {
      return this.contains(name, this.valueConverter.convertTimeMillis(value));
   }

   public boolean contains(Object name, Object value) {
      return this.contains(name, value, HashingStrategy.JAVA_HASHER);
   }

   public final boolean contains(Object name, Object value, HashingStrategy valueHashingStrategy) {
      ObjectUtil.checkNotNull(name, "name");
      int h = this.hashingStrategy.hashCode(name);
      int i = this.index(h);

      for(HeaderEntry e = this.entries[i]; e != null; e = e.next) {
         if (e.hash == h && this.hashingStrategy.equals(name, e.key) && valueHashingStrategy.equals(value, e.value)) {
            return true;
         }
      }

      return false;
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.head == this.head.after;
   }

   public Set names() {
      if (this.isEmpty()) {
         return Collections.emptySet();
      } else {
         Set names = new LinkedHashSet(this.size());

         for(HeaderEntry e = this.head.after; e != this.head; e = e.after) {
            names.add(e.getKey());
         }

         return names;
      }
   }

   public Headers add(Object name, Object value) {
      this.nameValidator.validateName(name);
      ObjectUtil.checkNotNull(value, "value");
      int h = this.hashingStrategy.hashCode(name);
      int i = this.index(h);
      this.add0(h, i, name, value);
      return this.thisT();
   }

   public Headers add(Object name, Iterable values) {
      this.nameValidator.validateName(name);
      int h = this.hashingStrategy.hashCode(name);
      int i = this.index(h);
      Iterator var5 = values.iterator();

      while(var5.hasNext()) {
         Object v = var5.next();
         this.add0(h, i, name, v);
      }

      return this.thisT();
   }

   public Headers add(Object name, Object... values) {
      this.nameValidator.validateName(name);
      int h = this.hashingStrategy.hashCode(name);
      int i = this.index(h);
      Object[] var5 = values;
      int var6 = values.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Object v = var5[var7];
         this.add0(h, i, name, v);
      }

      return this.thisT();
   }

   public Headers addObject(Object name, Object value) {
      return this.add(name, this.valueConverter.convertObject(ObjectUtil.checkNotNull(value, "value")));
   }

   public Headers addObject(Object name, Iterable values) {
      Iterator var3 = values.iterator();

      while(var3.hasNext()) {
         Object value = var3.next();
         this.addObject(name, value);
      }

      return this.thisT();
   }

   public Headers addObject(Object name, Object... values) {
      Object[] var3 = values;
      int var4 = values.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object value = var3[var5];
         this.addObject(name, value);
      }

      return this.thisT();
   }

   public Headers addInt(Object name, int value) {
      return this.add(name, this.valueConverter.convertInt(value));
   }

   public Headers addLong(Object name, long value) {
      return this.add(name, this.valueConverter.convertLong(value));
   }

   public Headers addDouble(Object name, double value) {
      return this.add(name, this.valueConverter.convertDouble(value));
   }

   public Headers addTimeMillis(Object name, long value) {
      return this.add(name, this.valueConverter.convertTimeMillis(value));
   }

   public Headers addChar(Object name, char value) {
      return this.add(name, this.valueConverter.convertChar(value));
   }

   public Headers addBoolean(Object name, boolean value) {
      return this.add(name, this.valueConverter.convertBoolean(value));
   }

   public Headers addFloat(Object name, float value) {
      return this.add(name, this.valueConverter.convertFloat(value));
   }

   public Headers addByte(Object name, byte value) {
      return this.add(name, this.valueConverter.convertByte(value));
   }

   public Headers addShort(Object name, short value) {
      return this.add(name, this.valueConverter.convertShort(value));
   }

   public Headers add(Headers headers) {
      if (headers == this) {
         throw new IllegalArgumentException("can't add to itself.");
      } else {
         this.addImpl(headers);
         return this.thisT();
      }
   }

   protected void addImpl(Headers headers) {
      if (headers instanceof DefaultHeaders) {
         DefaultHeaders defaultHeaders = (DefaultHeaders)headers;
         HeaderEntry e = defaultHeaders.head.after;
         if (defaultHeaders.hashingStrategy == this.hashingStrategy && defaultHeaders.nameValidator == this.nameValidator) {
            while(e != defaultHeaders.head) {
               this.add0(e.hash, this.index(e.hash), e.key, e.value);
               e = e.after;
            }
         } else {
            while(e != defaultHeaders.head) {
               this.add(e.key, e.value);
               e = e.after;
            }
         }
      } else {
         Iterator var4 = headers.iterator();

         while(var4.hasNext()) {
            Map.Entry header = (Map.Entry)var4.next();
            this.add(header.getKey(), header.getValue());
         }
      }

   }

   public Headers set(Object name, Object value) {
      this.nameValidator.validateName(name);
      ObjectUtil.checkNotNull(value, "value");
      int h = this.hashingStrategy.hashCode(name);
      int i = this.index(h);
      this.remove0(h, i, name);
      this.add0(h, i, name, value);
      return this.thisT();
   }

   public Headers set(Object name, Iterable values) {
      this.nameValidator.validateName(name);
      ObjectUtil.checkNotNull(values, "values");
      int h = this.hashingStrategy.hashCode(name);
      int i = this.index(h);
      this.remove0(h, i, name);
      Iterator var5 = values.iterator();

      while(var5.hasNext()) {
         Object v = var5.next();
         if (v == null) {
            break;
         }

         this.add0(h, i, name, v);
      }

      return this.thisT();
   }

   public Headers set(Object name, Object... values) {
      this.nameValidator.validateName(name);
      ObjectUtil.checkNotNull(values, "values");
      int h = this.hashingStrategy.hashCode(name);
      int i = this.index(h);
      this.remove0(h, i, name);
      Object[] var5 = values;
      int var6 = values.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Object v = var5[var7];
         if (v == null) {
            break;
         }

         this.add0(h, i, name, v);
      }

      return this.thisT();
   }

   public Headers setObject(Object name, Object value) {
      ObjectUtil.checkNotNull(value, "value");
      Object convertedValue = ObjectUtil.checkNotNull(this.valueConverter.convertObject(value), "convertedValue");
      return this.set(name, convertedValue);
   }

   public Headers setObject(Object name, Iterable values) {
      this.nameValidator.validateName(name);
      int h = this.hashingStrategy.hashCode(name);
      int i = this.index(h);
      this.remove0(h, i, name);
      Iterator var5 = values.iterator();

      while(var5.hasNext()) {
         Object v = var5.next();
         if (v == null) {
            break;
         }

         this.add0(h, i, name, this.valueConverter.convertObject(v));
      }

      return this.thisT();
   }

   public Headers setObject(Object name, Object... values) {
      this.nameValidator.validateName(name);
      int h = this.hashingStrategy.hashCode(name);
      int i = this.index(h);
      this.remove0(h, i, name);
      Object[] var5 = values;
      int var6 = values.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Object v = var5[var7];
         if (v == null) {
            break;
         }

         this.add0(h, i, name, this.valueConverter.convertObject(v));
      }

      return this.thisT();
   }

   public Headers setInt(Object name, int value) {
      return this.set(name, this.valueConverter.convertInt(value));
   }

   public Headers setLong(Object name, long value) {
      return this.set(name, this.valueConverter.convertLong(value));
   }

   public Headers setDouble(Object name, double value) {
      return this.set(name, this.valueConverter.convertDouble(value));
   }

   public Headers setTimeMillis(Object name, long value) {
      return this.set(name, this.valueConverter.convertTimeMillis(value));
   }

   public Headers setFloat(Object name, float value) {
      return this.set(name, this.valueConverter.convertFloat(value));
   }

   public Headers setChar(Object name, char value) {
      return this.set(name, this.valueConverter.convertChar(value));
   }

   public Headers setBoolean(Object name, boolean value) {
      return this.set(name, this.valueConverter.convertBoolean(value));
   }

   public Headers setByte(Object name, byte value) {
      return this.set(name, this.valueConverter.convertByte(value));
   }

   public Headers setShort(Object name, short value) {
      return this.set(name, this.valueConverter.convertShort(value));
   }

   public Headers set(Headers headers) {
      if (headers != this) {
         this.clear();
         this.addImpl(headers);
      }

      return this.thisT();
   }

   public Headers setAll(Headers headers) {
      if (headers != this) {
         Iterator var2 = headers.names().iterator();

         while(var2.hasNext()) {
            Object key = var2.next();
            this.remove(key);
         }

         this.addImpl(headers);
      }

      return this.thisT();
   }

   public boolean remove(Object name) {
      return this.getAndRemove(name) != null;
   }

   public Headers clear() {
      Arrays.fill(this.entries, (Object)null);
      this.head.before = this.head.after = this.head;
      this.size = 0;
      return this.thisT();
   }

   public Iterator iterator() {
      return new HeaderIterator();
   }

   public Boolean getBoolean(Object name) {
      Object v = this.get(name);
      return v != null ? this.valueConverter.convertToBoolean(v) : null;
   }

   public boolean getBoolean(Object name, boolean defaultValue) {
      Boolean v = this.getBoolean(name);
      return v != null ? v : defaultValue;
   }

   public Byte getByte(Object name) {
      Object v = this.get(name);
      return v != null ? this.valueConverter.convertToByte(v) : null;
   }

   public byte getByte(Object name, byte defaultValue) {
      Byte v = this.getByte(name);
      return v != null ? v : defaultValue;
   }

   public Character getChar(Object name) {
      Object v = this.get(name);
      return v != null ? this.valueConverter.convertToChar(v) : null;
   }

   public char getChar(Object name, char defaultValue) {
      Character v = this.getChar(name);
      return v != null ? v : defaultValue;
   }

   public Short getShort(Object name) {
      Object v = this.get(name);
      return v != null ? this.valueConverter.convertToShort(v) : null;
   }

   public short getShort(Object name, short defaultValue) {
      Short v = this.getShort(name);
      return v != null ? v : defaultValue;
   }

   public Integer getInt(Object name) {
      Object v = this.get(name);
      return v != null ? this.valueConverter.convertToInt(v) : null;
   }

   public int getInt(Object name, int defaultValue) {
      Integer v = this.getInt(name);
      return v != null ? v : defaultValue;
   }

   public Long getLong(Object name) {
      Object v = this.get(name);
      return v != null ? this.valueConverter.convertToLong(v) : null;
   }

   public long getLong(Object name, long defaultValue) {
      Long v = this.getLong(name);
      return v != null ? v : defaultValue;
   }

   public Float getFloat(Object name) {
      Object v = this.get(name);
      return v != null ? this.valueConverter.convertToFloat(v) : null;
   }

   public float getFloat(Object name, float defaultValue) {
      Float v = this.getFloat(name);
      return v != null ? v : defaultValue;
   }

   public Double getDouble(Object name) {
      Object v = this.get(name);
      return v != null ? this.valueConverter.convertToDouble(v) : null;
   }

   public double getDouble(Object name, double defaultValue) {
      Double v = this.getDouble(name);
      return v != null ? v : defaultValue;
   }

   public Long getTimeMillis(Object name) {
      Object v = this.get(name);
      return v != null ? this.valueConverter.convertToTimeMillis(v) : null;
   }

   public long getTimeMillis(Object name, long defaultValue) {
      Long v = this.getTimeMillis(name);
      return v != null ? v : defaultValue;
   }

   public Boolean getBooleanAndRemove(Object name) {
      Object v = this.getAndRemove(name);
      return v != null ? this.valueConverter.convertToBoolean(v) : null;
   }

   public boolean getBooleanAndRemove(Object name, boolean defaultValue) {
      Boolean v = this.getBooleanAndRemove(name);
      return v != null ? v : defaultValue;
   }

   public Byte getByteAndRemove(Object name) {
      Object v = this.getAndRemove(name);
      return v != null ? this.valueConverter.convertToByte(v) : null;
   }

   public byte getByteAndRemove(Object name, byte defaultValue) {
      Byte v = this.getByteAndRemove(name);
      return v != null ? v : defaultValue;
   }

   public Character getCharAndRemove(Object name) {
      Object v = this.getAndRemove(name);
      if (v == null) {
         return null;
      } else {
         try {
            return this.valueConverter.convertToChar(v);
         } catch (Throwable var4) {
            return null;
         }
      }
   }

   public char getCharAndRemove(Object name, char defaultValue) {
      Character v = this.getCharAndRemove(name);
      return v != null ? v : defaultValue;
   }

   public Short getShortAndRemove(Object name) {
      Object v = this.getAndRemove(name);
      return v != null ? this.valueConverter.convertToShort(v) : null;
   }

   public short getShortAndRemove(Object name, short defaultValue) {
      Short v = this.getShortAndRemove(name);
      return v != null ? v : defaultValue;
   }

   public Integer getIntAndRemove(Object name) {
      Object v = this.getAndRemove(name);
      return v != null ? this.valueConverter.convertToInt(v) : null;
   }

   public int getIntAndRemove(Object name, int defaultValue) {
      Integer v = this.getIntAndRemove(name);
      return v != null ? v : defaultValue;
   }

   public Long getLongAndRemove(Object name) {
      Object v = this.getAndRemove(name);
      return v != null ? this.valueConverter.convertToLong(v) : null;
   }

   public long getLongAndRemove(Object name, long defaultValue) {
      Long v = this.getLongAndRemove(name);
      return v != null ? v : defaultValue;
   }

   public Float getFloatAndRemove(Object name) {
      Object v = this.getAndRemove(name);
      return v != null ? this.valueConverter.convertToFloat(v) : null;
   }

   public float getFloatAndRemove(Object name, float defaultValue) {
      Float v = this.getFloatAndRemove(name);
      return v != null ? v : defaultValue;
   }

   public Double getDoubleAndRemove(Object name) {
      Object v = this.getAndRemove(name);
      return v != null ? this.valueConverter.convertToDouble(v) : null;
   }

   public double getDoubleAndRemove(Object name, double defaultValue) {
      Double v = this.getDoubleAndRemove(name);
      return v != null ? v : defaultValue;
   }

   public Long getTimeMillisAndRemove(Object name) {
      Object v = this.getAndRemove(name);
      return v != null ? this.valueConverter.convertToTimeMillis(v) : null;
   }

   public long getTimeMillisAndRemove(Object name, long defaultValue) {
      Long v = this.getTimeMillisAndRemove(name);
      return v != null ? v : defaultValue;
   }

   public boolean equals(Object o) {
      return !(o instanceof Headers) ? false : this.equals((Headers)o, HashingStrategy.JAVA_HASHER);
   }

   public int hashCode() {
      return this.hashCode(HashingStrategy.JAVA_HASHER);
   }

   public final boolean equals(Headers h2, HashingStrategy valueHashingStrategy) {
      if (h2.size() != this.size()) {
         return false;
      } else if (this == h2) {
         return true;
      } else {
         Iterator var3 = this.names().iterator();

         while(var3.hasNext()) {
            Object name = var3.next();
            List otherValues = h2.getAll(name);
            List values = this.getAll(name);
            if (otherValues.size() != values.size()) {
               return false;
            }

            for(int i = 0; i < otherValues.size(); ++i) {
               if (!valueHashingStrategy.equals(otherValues.get(i), values.get(i))) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public final int hashCode(HashingStrategy valueHashingStrategy) {
      int result = -1028477387;
      Iterator var3 = this.names().iterator();

      while(var3.hasNext()) {
         Object name = var3.next();
         result = 31 * result + this.hashingStrategy.hashCode(name);
         List values = this.getAll(name);

         for(int i = 0; i < values.size(); ++i) {
            result = 31 * result + valueHashingStrategy.hashCode(values.get(i));
         }
      }

      return result;
   }

   public String toString() {
      StringBuilder builder = (new StringBuilder(this.getClass().getSimpleName())).append('[');
      String separator = "";
      Iterator var3 = this.names().iterator();

      while(var3.hasNext()) {
         Object name = var3.next();
         List values = this.getAll(name);

         for(int i = 0; i < values.size(); ++i) {
            builder.append(separator);
            builder.append(name).append(": ").append(values.get(i));
            separator = ", ";
         }
      }

      return builder.append(']').toString();
   }

   protected HeaderEntry newHeaderEntry(int h, Object name, Object value, HeaderEntry next) {
      return new HeaderEntry(h, name, value, next, this.head);
   }

   protected ValueConverter valueConverter() {
      return this.valueConverter;
   }

   private int index(int hash) {
      return hash & this.hashMask;
   }

   private void add0(int h, int i, Object name, Object value) {
      this.entries[i] = this.newHeaderEntry(h, name, value, this.entries[i]);
      ++this.size;
   }

   private Object remove0(int h, int i, Object name) {
      HeaderEntry e = this.entries[i];
      if (e == null) {
         return null;
      } else {
         Object value = null;

         for(HeaderEntry next = e.next; next != null; next = e.next) {
            if (next.hash == h && this.hashingStrategy.equals(name, next.key)) {
               value = next.value;
               e.next = next.next;
               next.remove();
               --this.size;
            } else {
               e = next;
            }
         }

         e = this.entries[i];
         if (e.hash == h && this.hashingStrategy.equals(name, e.key)) {
            if (value == null) {
               value = e.value;
            }

            this.entries[i] = e.next;
            e.remove();
            --this.size;
         }

         return value;
      }
   }

   private Headers thisT() {
      return this;
   }

   protected static class HeaderEntry implements Map.Entry {
      protected final int hash;
      protected final Object key;
      protected Object value;
      protected HeaderEntry next;
      protected HeaderEntry before;
      protected HeaderEntry after;

      protected HeaderEntry(int hash, Object key) {
         this.hash = hash;
         this.key = key;
      }

      HeaderEntry(int hash, Object key, Object value, HeaderEntry next, HeaderEntry head) {
         this.hash = hash;
         this.key = key;
         this.value = value;
         this.next = next;
         this.after = head;
         this.before = head.before;
         this.pointNeighborsToThis();
      }

      HeaderEntry() {
         this.hash = -1;
         this.key = null;
         this.before = this.after = this;
      }

      protected final void pointNeighborsToThis() {
         this.before.after = this;
         this.after.before = this;
      }

      public final HeaderEntry before() {
         return this.before;
      }

      public final HeaderEntry after() {
         return this.after;
      }

      protected void remove() {
         this.before.after = this.after;
         this.after.before = this.before;
      }

      public final Object getKey() {
         return this.key;
      }

      public final Object getValue() {
         return this.value;
      }

      public final Object setValue(Object value) {
         ObjectUtil.checkNotNull(value, "value");
         Object oldValue = this.value;
         this.value = value;
         return oldValue;
      }

      public final String toString() {
         return this.key.toString() + '=' + this.value.toString();
      }
   }

   private final class HeaderIterator implements Iterator {
      private HeaderEntry current;

      private HeaderIterator() {
         this.current = DefaultHeaders.this.head;
      }

      public boolean hasNext() {
         return this.current.after != DefaultHeaders.this.head;
      }

      public Map.Entry next() {
         this.current = this.current.after;
         if (this.current == DefaultHeaders.this.head) {
            throw new NoSuchElementException();
         } else {
            return this.current;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException("read only");
      }

      // $FF: synthetic method
      HeaderIterator(Object x1) {
         this();
      }
   }

   public interface NameValidator {
      NameValidator NOT_NULL = new NameValidator() {
         public void validateName(Object name) {
            ObjectUtil.checkNotNull(name, "name");
         }
      };

      void validateName(Object var1);
   }
}
