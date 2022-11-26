package org.python.netty.handler.codec;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface Headers extends Iterable {
   Object get(Object var1);

   Object get(Object var1, Object var2);

   Object getAndRemove(Object var1);

   Object getAndRemove(Object var1, Object var2);

   List getAll(Object var1);

   List getAllAndRemove(Object var1);

   Boolean getBoolean(Object var1);

   boolean getBoolean(Object var1, boolean var2);

   Byte getByte(Object var1);

   byte getByte(Object var1, byte var2);

   Character getChar(Object var1);

   char getChar(Object var1, char var2);

   Short getShort(Object var1);

   short getShort(Object var1, short var2);

   Integer getInt(Object var1);

   int getInt(Object var1, int var2);

   Long getLong(Object var1);

   long getLong(Object var1, long var2);

   Float getFloat(Object var1);

   float getFloat(Object var1, float var2);

   Double getDouble(Object var1);

   double getDouble(Object var1, double var2);

   Long getTimeMillis(Object var1);

   long getTimeMillis(Object var1, long var2);

   Boolean getBooleanAndRemove(Object var1);

   boolean getBooleanAndRemove(Object var1, boolean var2);

   Byte getByteAndRemove(Object var1);

   byte getByteAndRemove(Object var1, byte var2);

   Character getCharAndRemove(Object var1);

   char getCharAndRemove(Object var1, char var2);

   Short getShortAndRemove(Object var1);

   short getShortAndRemove(Object var1, short var2);

   Integer getIntAndRemove(Object var1);

   int getIntAndRemove(Object var1, int var2);

   Long getLongAndRemove(Object var1);

   long getLongAndRemove(Object var1, long var2);

   Float getFloatAndRemove(Object var1);

   float getFloatAndRemove(Object var1, float var2);

   Double getDoubleAndRemove(Object var1);

   double getDoubleAndRemove(Object var1, double var2);

   Long getTimeMillisAndRemove(Object var1);

   long getTimeMillisAndRemove(Object var1, long var2);

   boolean contains(Object var1);

   boolean contains(Object var1, Object var2);

   boolean containsObject(Object var1, Object var2);

   boolean containsBoolean(Object var1, boolean var2);

   boolean containsByte(Object var1, byte var2);

   boolean containsChar(Object var1, char var2);

   boolean containsShort(Object var1, short var2);

   boolean containsInt(Object var1, int var2);

   boolean containsLong(Object var1, long var2);

   boolean containsFloat(Object var1, float var2);

   boolean containsDouble(Object var1, double var2);

   boolean containsTimeMillis(Object var1, long var2);

   int size();

   boolean isEmpty();

   Set names();

   Headers add(Object var1, Object var2);

   Headers add(Object var1, Iterable var2);

   Headers add(Object var1, Object... var2);

   Headers addObject(Object var1, Object var2);

   Headers addObject(Object var1, Iterable var2);

   Headers addObject(Object var1, Object... var2);

   Headers addBoolean(Object var1, boolean var2);

   Headers addByte(Object var1, byte var2);

   Headers addChar(Object var1, char var2);

   Headers addShort(Object var1, short var2);

   Headers addInt(Object var1, int var2);

   Headers addLong(Object var1, long var2);

   Headers addFloat(Object var1, float var2);

   Headers addDouble(Object var1, double var2);

   Headers addTimeMillis(Object var1, long var2);

   Headers add(Headers var1);

   Headers set(Object var1, Object var2);

   Headers set(Object var1, Iterable var2);

   Headers set(Object var1, Object... var2);

   Headers setObject(Object var1, Object var2);

   Headers setObject(Object var1, Iterable var2);

   Headers setObject(Object var1, Object... var2);

   Headers setBoolean(Object var1, boolean var2);

   Headers setByte(Object var1, byte var2);

   Headers setChar(Object var1, char var2);

   Headers setShort(Object var1, short var2);

   Headers setInt(Object var1, int var2);

   Headers setLong(Object var1, long var2);

   Headers setFloat(Object var1, float var2);

   Headers setDouble(Object var1, double var2);

   Headers setTimeMillis(Object var1, long var2);

   Headers set(Headers var1);

   Headers setAll(Headers var1);

   boolean remove(Object var1);

   Headers clear();

   Iterator iterator();
}
