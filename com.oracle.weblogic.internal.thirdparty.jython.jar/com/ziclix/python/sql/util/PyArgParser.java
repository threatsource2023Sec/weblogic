package com.ziclix.python.sql.util;

import java.util.HashMap;
import java.util.Map;
import org.python.core.Py;
import org.python.core.PyObject;

public class PyArgParser {
   protected Map keywords = new HashMap();
   protected PyObject[] arguments = null;

   public PyArgParser(PyObject[] args, String[] kws) {
      this.parse(args, kws);
   }

   protected void parse(PyObject[] args, String[] kws) {
      int largs = args.length;
      if (kws != null) {
         for(int i = kws.length - 1; i >= 0; --i) {
            String var10001 = kws[i];
            --largs;
            this.keywords.put(var10001, args[largs]);
         }
      }

      this.arguments = new PyObject[largs];
      System.arraycopy(args, 0, this.arguments, 0, largs);
   }

   public int numKw() {
      return this.keywords.keySet().size();
   }

   public boolean hasKw(String kw) {
      return this.keywords.containsKey(kw);
   }

   public PyObject kw(String kw) {
      if (!this.hasKw(kw)) {
         throw Py.KeyError(kw);
      } else {
         return (PyObject)this.keywords.get(kw);
      }
   }

   public PyObject kw(String kw, PyObject def) {
      return !this.hasKw(kw) ? def : (PyObject)this.keywords.get(kw);
   }

   public String[] kws() {
      return (String[])this.keywords.keySet().toArray(new String[0]);
   }

   public int numArg() {
      return this.arguments.length;
   }

   public PyObject arg(int index) {
      if (index >= 0 && index <= this.arguments.length - 1) {
         return this.arguments[index];
      } else {
         throw Py.IndexError("index out of range");
      }
   }

   public PyObject arg(int index, PyObject def) {
      return index >= 0 && index <= this.arguments.length - 1 ? this.arguments[index] : def;
   }
}
