package com.ziclix.python.sql;

import org.python.core.PyObject;

public interface DateFactory {
   PyObject Date(int var1, int var2, int var3);

   PyObject Time(int var1, int var2, int var3);

   PyObject Timestamp(int var1, int var2, int var3, int var4, int var5, int var6);

   PyObject DateFromTicks(long var1);

   PyObject TimeFromTicks(long var1);

   PyObject TimestampFromTicks(long var1);
}
