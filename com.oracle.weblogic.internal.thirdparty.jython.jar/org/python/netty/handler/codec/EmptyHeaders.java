package org.python.netty.handler.codec;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EmptyHeaders implements Headers {
   public Object get(Object name) {
      return null;
   }

   public Object get(Object name, Object defaultValue) {
      return null;
   }

   public Object getAndRemove(Object name) {
      return null;
   }

   public Object getAndRemove(Object name, Object defaultValue) {
      return null;
   }

   public List getAll(Object name) {
      return Collections.emptyList();
   }

   public List getAllAndRemove(Object name) {
      return Collections.emptyList();
   }

   public Boolean getBoolean(Object name) {
      return null;
   }

   public boolean getBoolean(Object name, boolean defaultValue) {
      return defaultValue;
   }

   public Byte getByte(Object name) {
      return null;
   }

   public byte getByte(Object name, byte defaultValue) {
      return defaultValue;
   }

   public Character getChar(Object name) {
      return null;
   }

   public char getChar(Object name, char defaultValue) {
      return defaultValue;
   }

   public Short getShort(Object name) {
      return null;
   }

   public short getShort(Object name, short defaultValue) {
      return defaultValue;
   }

   public Integer getInt(Object name) {
      return null;
   }

   public int getInt(Object name, int defaultValue) {
      return defaultValue;
   }

   public Long getLong(Object name) {
      return null;
   }

   public long getLong(Object name, long defaultValue) {
      return defaultValue;
   }

   public Float getFloat(Object name) {
      return null;
   }

   public float getFloat(Object name, float defaultValue) {
      return defaultValue;
   }

   public Double getDouble(Object name) {
      return null;
   }

   public double getDouble(Object name, double defaultValue) {
      return defaultValue;
   }

   public Long getTimeMillis(Object name) {
      return null;
   }

   public long getTimeMillis(Object name, long defaultValue) {
      return defaultValue;
   }

   public Boolean getBooleanAndRemove(Object name) {
      return null;
   }

   public boolean getBooleanAndRemove(Object name, boolean defaultValue) {
      return defaultValue;
   }

   public Byte getByteAndRemove(Object name) {
      return null;
   }

   public byte getByteAndRemove(Object name, byte defaultValue) {
      return defaultValue;
   }

   public Character getCharAndRemove(Object name) {
      return null;
   }

   public char getCharAndRemove(Object name, char defaultValue) {
      return defaultValue;
   }

   public Short getShortAndRemove(Object name) {
      return null;
   }

   public short getShortAndRemove(Object name, short defaultValue) {
      return defaultValue;
   }

   public Integer getIntAndRemove(Object name) {
      return null;
   }

   public int getIntAndRemove(Object name, int defaultValue) {
      return defaultValue;
   }

   public Long getLongAndRemove(Object name) {
      return null;
   }

   public long getLongAndRemove(Object name, long defaultValue) {
      return defaultValue;
   }

   public Float getFloatAndRemove(Object name) {
      return null;
   }

   public float getFloatAndRemove(Object name, float defaultValue) {
      return defaultValue;
   }

   public Double getDoubleAndRemove(Object name) {
      return null;
   }

   public double getDoubleAndRemove(Object name, double defaultValue) {
      return defaultValue;
   }

   public Long getTimeMillisAndRemove(Object name) {
      return null;
   }

   public long getTimeMillisAndRemove(Object name, long defaultValue) {
      return defaultValue;
   }

   public boolean contains(Object name) {
      return false;
   }

   public boolean contains(Object name, Object value) {
      return false;
   }

   public boolean containsObject(Object name, Object value) {
      return false;
   }

   public boolean containsBoolean(Object name, boolean value) {
      return false;
   }

   public boolean containsByte(Object name, byte value) {
      return false;
   }

   public boolean containsChar(Object name, char value) {
      return false;
   }

   public boolean containsShort(Object name, short value) {
      return false;
   }

   public boolean containsInt(Object name, int value) {
      return false;
   }

   public boolean containsLong(Object name, long value) {
      return false;
   }

   public boolean containsFloat(Object name, float value) {
      return false;
   }

   public boolean containsDouble(Object name, double value) {
      return false;
   }

   public boolean containsTimeMillis(Object name, long value) {
      return false;
   }

   public int size() {
      return 0;
   }

   public boolean isEmpty() {
      return true;
   }

   public Set names() {
      return Collections.emptySet();
   }

   public Headers add(Object name, Object value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers add(Object name, Iterable values) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers add(Object name, Object... values) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addObject(Object name, Object value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addObject(Object name, Iterable values) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addObject(Object name, Object... values) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addBoolean(Object name, boolean value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addByte(Object name, byte value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addChar(Object name, char value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addShort(Object name, short value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addInt(Object name, int value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addLong(Object name, long value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addFloat(Object name, float value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addDouble(Object name, double value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers addTimeMillis(Object name, long value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers add(Headers headers) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers set(Object name, Object value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers set(Object name, Iterable values) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers set(Object name, Object... values) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setObject(Object name, Object value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setObject(Object name, Iterable values) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setObject(Object name, Object... values) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setBoolean(Object name, boolean value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setByte(Object name, byte value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setChar(Object name, char value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setShort(Object name, short value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setInt(Object name, int value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setLong(Object name, long value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setFloat(Object name, float value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setDouble(Object name, double value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setTimeMillis(Object name, long value) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers set(Headers headers) {
      throw new UnsupportedOperationException("read only");
   }

   public Headers setAll(Headers headers) {
      throw new UnsupportedOperationException("read only");
   }

   public boolean remove(Object name) {
      return false;
   }

   public Headers clear() {
      return this.thisT();
   }

   public Iterator iterator() {
      List empty = Collections.emptyList();
      return empty.iterator();
   }

   public boolean equals(Object o) {
      if (!(o instanceof Headers)) {
         return false;
      } else {
         Headers rhs = (Headers)o;
         return this.isEmpty() && rhs.isEmpty();
      }
   }

   public int hashCode() {
      return -1028477387;
   }

   public String toString() {
      return this.getClass().getSimpleName() + '[' + ']';
   }

   private Headers thisT() {
      return this;
   }
}
