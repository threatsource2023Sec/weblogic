package com.ziclix.python.sql;

import org.python.core.Py;
import org.python.core.PyArray;
import org.python.core.PyBuiltinFunctionSet;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.Untraversable;

@Untraversable
class zxJDBCFunc extends PyBuiltinFunctionSet {
   zxJDBCFunc(String name, int index, int minargs, int maxargs, String doc) {
      super(name, index, minargs, maxargs, doc);
   }

   public PyObject __call__(PyObject arg) {
      long ticks;
      switch (this.index) {
         case 4:
            ticks = ((Number)arg.__tojava__(Number.class)).longValue();
            return zxJDBC.datefactory.DateFromTicks(ticks);
         case 5:
            ticks = ((Number)arg.__tojava__(Number.class)).longValue();
            return zxJDBC.datefactory.TimeFromTicks(ticks);
         case 6:
            ticks = ((Number)arg.__tojava__(Number.class)).longValue();
            return zxJDBC.datefactory.TimestampFromTicks(ticks);
         case 7:
            if (arg instanceof PyString) {
               arg = PyArray.TYPE.__call__(Py.newString("b"), arg);
            }

            return arg;
         default:
            throw this.info.unexpectedCall(1, false);
      }
   }

   public PyObject __call__(PyObject arga, PyObject argb, PyObject argc) {
      switch (this.index) {
         case 1:
            int year = ((Number)arga.__tojava__(Number.class)).intValue();
            int month = ((Number)argb.__tojava__(Number.class)).intValue();
            int day = ((Number)argc.__tojava__(Number.class)).intValue();
            return zxJDBC.datefactory.Date(year, month, day);
         case 2:
            int hour = ((Number)arga.__tojava__(Number.class)).intValue();
            int minute = ((Number)argb.__tojava__(Number.class)).intValue();
            int second = ((Number)argc.__tojava__(Number.class)).intValue();
            return zxJDBC.datefactory.Time(hour, minute, second);
         default:
            throw this.info.unexpectedCall(3, false);
      }
   }

   public PyObject fancyCall(PyObject[] args) {
      switch (this.index) {
         case 3:
            int year = ((Number)args[0].__tojava__(Number.class)).intValue();
            int month = ((Number)args[1].__tojava__(Number.class)).intValue();
            int day = ((Number)args[2].__tojava__(Number.class)).intValue();
            int hour = ((Number)args[3].__tojava__(Number.class)).intValue();
            int minute = ((Number)args[4].__tojava__(Number.class)).intValue();
            int second = ((Number)args[5].__tojava__(Number.class)).intValue();
            return zxJDBC.datefactory.Timestamp(year, month, day, hour, minute, second);
         default:
            throw this.info.unexpectedCall(args.length, false);
      }
   }
}
