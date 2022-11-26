package org.python.modules._json;

import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.core.codecs;
import org.python.expose.ExposedGet;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_json.Scanner",
   base = PyObject.class
)
public class Scanner extends PyObject implements Traverseproc {
   public static final PyType TYPE = PyType.fromClass(Scanner.class);
   @ExposedGet
   public final String __module__ = "_json";
   final String encoding;
   final boolean strict;
   final PyObject object_hook;
   final PyObject pairs_hook;
   final PyObject parse_float;
   final PyObject parse_int;
   final PyObject parse_constant;

   public Scanner(PyObject context) {
      this.encoding = _castString(context.__getattr__("encoding"), "utf-8");
      this.strict = context.__getattr__("strict").__nonzero__();
      this.object_hook = context.__getattr__("object_hook");
      this.pairs_hook = context.__getattr__("object_pairs_hook");
      this.parse_float = context.__getattr__("parse_float");
      this.parse_int = context.__getattr__("parse_int");
      this.parse_constant = context.__getattr__("parse_constant");
   }

   public PyObject __call__(PyObject string, PyObject idx) {
      return this._scan_once((PyString)string, idx.asInt());
   }

   private static boolean IS_WHITESPACE(int c) {
      return c == 32 || c == 9 || c == 10 || c == 13;
   }

   private static String _castString(PyObject pystr, String defaultValue) {
      if (pystr == Py.None) {
         return defaultValue;
      } else if (!(pystr instanceof PyString)) {
         throw Py.TypeError("encoding is not a string");
      } else {
         String s = pystr.toString();
         return codecs.PyUnicode_EncodeASCII(s, s.length(), (String)null);
      }
   }

   static PyTuple valIndex(PyObject obj, int i) {
      return new PyTuple(new PyObject[]{obj, Py.newInteger(i)});
   }

   public PyTuple _parse_object(PyString pystr, int idx) {
      PyString str = pystr;
      int end_idx = pystr.__len__() - 1;

      PyList pairs;
      for(pairs = new PyList(); idx <= end_idx && IS_WHITESPACE(str.getInt(idx)); ++idx) {
      }

      if (idx <= end_idx && str.getInt(idx) != 125) {
         while(idx <= end_idx) {
            if (str.getInt(idx) != 34) {
               _json.raise_errmsg("Expecting property name", pystr, idx);
            }

            PyTuple key_idx = _json.scanstring(pystr, idx + 1, this.encoding, this.strict);
            PyObject key = key_idx.pyget(0);

            for(idx = key_idx.pyget(1).asInt(); idx <= end_idx && IS_WHITESPACE(str.getInt(idx)); ++idx) {
            }

            if (idx > end_idx || str.getInt(idx) != 58) {
               _json.raise_errmsg("Expecting : delimiter", pystr, idx);
            }

            ++idx;

            while(idx <= end_idx && IS_WHITESPACE(str.getInt(idx))) {
               ++idx;
            }

            PyTuple val_idx = this._scan_once(pystr, idx);
            PyObject val = val_idx.pyget(0);
            idx = val_idx.pyget(1).asInt();
            pairs.append(new PyTuple(new PyObject[]{key, val}));

            while(idx <= end_idx && IS_WHITESPACE(str.getInt(idx))) {
               ++idx;
            }

            if (idx > end_idx || str.getInt(idx) == 125) {
               break;
            }

            if (str.getInt(idx) != 44) {
               _json.raise_errmsg("Expecting , delimiter", pystr, idx);
            }

            ++idx;

            while(idx <= end_idx && IS_WHITESPACE(str.getInt(idx))) {
               ++idx;
            }
         }
      }

      if (idx > end_idx || str.getInt(idx) != 125) {
         _json.raise_errmsg("Expecting object", pystr, end_idx);
      }

      if (this.pairs_hook != Py.None) {
         return valIndex(this.pairs_hook.__call__((PyObject)pairs), idx + 1);
      } else {
         PyObject rval = new PyDictionary();
         ((PyDictionary)rval).update(pairs);
         if (this.object_hook != Py.None) {
            rval = this.object_hook.__call__((PyObject)rval);
         }

         return valIndex((PyObject)rval, idx + 1);
      }
   }

   public PyTuple _parse_array(PyString pystr, int idx) {
      PyString str = pystr;
      int end_idx = pystr.__len__() - 1;

      PyList rval;
      for(rval = new PyList(); idx <= end_idx && IS_WHITESPACE(str.getInt(idx)); ++idx) {
      }

      if (idx <= end_idx && str.getInt(idx) != 93) {
         while(idx <= end_idx) {
            PyTuple val_idx = this._scan_once(pystr, idx);
            PyObject val = val_idx.pyget(0);
            idx = val_idx.pyget(1).asInt();
            rval.append(val);

            while(idx <= end_idx && IS_WHITESPACE(str.getInt(idx))) {
               ++idx;
            }

            if (idx > end_idx || str.getInt(idx) == 93) {
               break;
            }

            if (str.getInt(idx) != 44) {
               _json.raise_errmsg("Expecting , delimiter", pystr, idx);
            }

            ++idx;

            while(idx <= end_idx && IS_WHITESPACE(str.getInt(idx))) {
               ++idx;
            }
         }
      }

      if (idx > end_idx || str.getInt(idx) != 93) {
         _json.raise_errmsg("Expecting object", pystr, end_idx);
      }

      return valIndex(rval, idx + 1);
   }

   public PyTuple _scan_once(PyString pystr, int idx) {
      int length = pystr.__len__();
      if (idx >= length) {
         throw Py.StopIteration("");
      } else {
         switch (pystr.getInt(idx)) {
            case 34:
               return _json.scanstring(pystr, idx + 1, this.encoding, this.strict);
            case 45:
               if (idx + 8 < length && pystr.getInt(idx + 1) == 73 && pystr.getInt(idx + 2) == 110 && pystr.getInt(idx + 3) == 102 && pystr.getInt(idx + 4) == 105 && pystr.getInt(idx + 5) == 110 && pystr.getInt(idx + 6) == 105 && pystr.getInt(idx + 7) == 116 && pystr.getInt(idx + 8) == 121) {
                  return this._parse_constant("-Infinity", idx + 9);
               }
               break;
            case 73:
               if (idx + 7 < length && pystr.getInt(idx + 1) == 110 && pystr.getInt(idx + 2) == 102 && pystr.getInt(idx + 3) == 105 && pystr.getInt(idx + 4) == 110 && pystr.getInt(idx + 5) == 105 && pystr.getInt(idx + 6) == 116 && pystr.getInt(idx + 7) == 121) {
                  return this._parse_constant("Infinity", idx + 8);
               }
               break;
            case 78:
               if (idx + 2 < length && pystr.getInt(idx + 1) == 97 && pystr.getInt(idx + 2) == 78) {
                  return this._parse_constant("NaN", idx + 3);
               }
               break;
            case 91:
               return this._parse_array(pystr, idx + 1);
            case 102:
               if (idx + 4 < length && pystr.getInt(idx + 1) == 97 && pystr.getInt(idx + 2) == 108 && pystr.getInt(idx + 3) == 115 && pystr.getInt(idx + 4) == 101) {
                  return valIndex(Py.False, idx + 5);
               }
               break;
            case 110:
               if (idx + 3 < length && pystr.getInt(idx + 1) == 117 && pystr.getInt(idx + 2) == 108 && pystr.getInt(idx + 3) == 108) {
                  return valIndex(Py.None, idx + 4);
               }
               break;
            case 116:
               if (idx + 3 < length && pystr.getInt(idx + 1) == 114 && pystr.getInt(idx + 2) == 117 && pystr.getInt(idx + 3) == 101) {
                  return valIndex(Py.True, idx + 4);
               }
               break;
            case 123:
               return this._parse_object(pystr, idx + 1);
         }

         return this._match_number(pystr, idx);
      }
   }

   public PyTuple _parse_constant(String constant, int idx) {
      return valIndex(this.parse_constant.__call__((PyObject)Py.newString(constant)), idx);
   }

   public PyTuple _match_number(PyString pystr, int start) {
      PyString str = pystr;
      int end_idx = pystr.__len__() - 1;
      int idx = start;
      boolean is_float = false;
      if (pystr.getInt(start) == 45) {
         idx = start + 1;
         if (idx > end_idx) {
            throw Py.StopIteration("");
         }
      }

      if (pystr.getInt(idx) >= 49 && pystr.getInt(idx) <= 57) {
         ++idx;

         while(idx <= end_idx && str.getInt(idx) >= 48 && str.getInt(idx) <= 57) {
            ++idx;
         }
      } else {
         if (pystr.getInt(idx) != 48) {
            throw Py.StopIteration("");
         }

         ++idx;
      }

      if (idx < end_idx && str.getInt(idx) == 46 && str.getInt(idx + 1) >= 48 && str.getInt(idx + 1) <= 57) {
         is_float = true;

         for(idx += 2; idx <= end_idx && str.getInt(idx) >= 48 && str.getInt(idx) <= 57; ++idx) {
         }
      }

      if (idx < end_idx && (str.getInt(idx) == 101 || str.getInt(idx) == 69)) {
         int e_start = idx++;
         if (idx < end_idx && (str.getInt(idx) == 45 || str.getInt(idx) == 43)) {
            ++idx;
         }

         while(idx <= end_idx && str.getInt(idx) >= 48 && str.getInt(idx) <= 57) {
            ++idx;
         }

         if (str.getInt(idx - 1) >= 48 && str.getInt(idx - 1) <= 57) {
            is_float = true;
         } else {
            idx = e_start;
         }
      }

      PyString numstr = (PyString)str.__getslice__(Py.newInteger(start), Py.newInteger(idx));
      return is_float ? valIndex(this.parse_float.__call__((PyObject)numstr), idx) : valIndex(this.parse_int.__call__((PyObject)numstr), idx);
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.object_hook != null) {
         retVal = visit.visit(this.object_hook, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.pairs_hook != null) {
         retVal = visit.visit(this.pairs_hook, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.parse_float != null) {
         retVal = visit.visit(this.parse_float, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.parse_int != null) {
         retVal = visit.visit(this.parse_int, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.parse_constant != null ? visit.visit(this.parse_constant, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.object_hook || ob == this.pairs_hook || ob == this.parse_float || ob == this.parse_int || ob == this.parse_constant);
   }
}
