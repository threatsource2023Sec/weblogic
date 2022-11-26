package org.python.modules._json;

import java.util.Iterator;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.ExposedGet;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_json.encoder",
   base = PyObject.class
)
public class Encoder extends PyObject implements Traverseproc {
   public static final PyType TYPE = PyType.fromClass(Encoder.class);
   @ExposedGet
   public final String __module__ = "_json";
   final PyDictionary markers;
   final PyObject defaultfn;
   final PyObject encoder;
   final PyObject indent;
   final PyObject key_separator;
   final PyObject item_separator;
   final PyObject sort_keys;
   final boolean skipkeys;
   final boolean allow_nan;

   public Encoder(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("encoder", args, kwds, new String[]{"markers", "default", "encoder", "indent", "key_separator", "item_separator", "sort_keys", "skipkeys", "allow_nan"});
      ap.noKeywords();
      PyObject m = ap.getPyObject(0);
      this.markers = m == Py.None ? null : (PyDictionary)m;
      this.defaultfn = ap.getPyObject(1);
      this.encoder = ap.getPyObject(2);
      this.indent = ap.getPyObject(3);
      this.key_separator = ap.getPyObject(4);
      this.item_separator = ap.getPyObject(5);
      this.sort_keys = ap.getPyObject(6);
      this.skipkeys = ap.getPyObject(7).__nonzero__();
      this.allow_nan = ap.getPyObject(8).__nonzero__();
   }

   public PyObject __call__(PyObject obj) {
      return this.__call__(obj, Py.Zero);
   }

   public PyObject __call__(PyObject obj, PyObject indent_level) {
      PyList rval = new PyList();
      this.encode_obj(rval, obj, 0);
      return rval;
   }

   private PyString encode_float(PyObject obj) {
      double i = obj.asDouble();
      if (!Double.isInfinite(i) && !Double.isNaN(i)) {
         return obj.__repr__();
      } else if (!this.allow_nan) {
         throw Py.ValueError("Out of range float values are not JSON compliant");
      } else if (i == Double.POSITIVE_INFINITY) {
         return new PyString("Infinity");
      } else {
         return i == Double.NEGATIVE_INFINITY ? new PyString("-Infinity") : new PyString("NaN");
      }
   }

   private PyString encode_string(PyObject obj) {
      return (PyString)this.encoder.__call__(obj);
   }

   private PyObject checkCircularReference(PyObject obj) {
      PyObject ident = null;
      if (this.markers != null) {
         ident = Py.newInteger(Py.id(obj));
         if (this.markers.__contains__(ident)) {
            throw Py.ValueError("Circular reference detected");
         }

         this.markers.__setitem__(ident, obj);
      }

      return ident;
   }

   private void encode_obj(PyList rval, PyObject obj, int indent_level) {
      if (obj == Py.None) {
         rval.append(new PyString("null"));
      } else if (obj == Py.True) {
         rval.append(new PyString("true"));
      } else if (obj == Py.False) {
         rval.append(new PyString("false"));
      } else if (obj instanceof PyString) {
         rval.append(this.encode_string(obj));
      } else if (!(obj instanceof PyInteger) && !(obj instanceof PyLong)) {
         if (obj instanceof PyFloat) {
            rval.append(this.encode_float(obj));
         } else if (!(obj instanceof PyList) && !(obj instanceof PyTuple)) {
            if (obj instanceof PyDictionary) {
               this.encode_dict(rval, (PyDictionary)obj, indent_level);
            } else {
               PyObject ident = this.checkCircularReference(obj);
               if (this.defaultfn == Py.None) {
                  throw Py.TypeError(String.format(".80s is not JSON serializable", obj.__repr__()));
               }

               PyObject newobj = this.defaultfn.__call__(obj);
               this.encode_obj(rval, newobj, indent_level);
               if (ident != null) {
                  this.markers.__delitem__(ident);
               }
            }
         } else {
            this.encode_list(rval, obj, indent_level);
         }
      } else {
         rval.append(obj.__str__());
      }

   }

   private void encode_dict(PyList rval, PyDictionary dct, int indent_level) {
      if (dct.__len__() == 0) {
         rval.append(new PyString("{}"));
      } else {
         PyObject ident = this.checkCircularReference(dct);
         rval.append(new PyString("{"));
         int idx = 0;
         Iterator var6 = dct.asIterable().iterator();

         PyObject key;
         label57:
         do {
            while(var6.hasNext()) {
               key = (PyObject)var6.next();
               PyString kstr;
               if (!(key instanceof PyString) && !(key instanceof PyUnicode)) {
                  if (key instanceof PyFloat) {
                     kstr = this.encode_float(key);
                  } else if (!(key instanceof PyInteger) && !(key instanceof PyLong)) {
                     if (key == Py.True) {
                        kstr = new PyString("true");
                     } else if (key == Py.False) {
                        kstr = new PyString("false");
                     } else {
                        if (key != Py.None) {
                           continue label57;
                        }

                        kstr = new PyString("null");
                     }
                  } else {
                     kstr = key.__str__();
                  }
               } else {
                  kstr = (PyString)key;
               }

               if (idx > 0) {
                  rval.append(this.item_separator);
               }

               PyObject value = dct.__getitem__(key);
               PyString encoded = this.encode_string(kstr);
               rval.append(encoded);
               rval.append(this.key_separator);
               this.encode_obj(rval, value, indent_level);
               ++idx;
            }

            if (ident != null) {
               this.markers.__delitem__(ident);
            }

            rval.append(new PyString("}"));
            return;
         } while(this.skipkeys);

         throw Py.TypeError(String.format("keys must be a string: %.80s", key.__repr__()));
      }
   }

   private void encode_list(PyList rval, PyObject seq, int indent_level) {
      PyObject ident = this.checkCircularReference(seq);
      rval.append(new PyString("["));
      int i = 0;

      for(Iterator var6 = seq.asIterable().iterator(); var6.hasNext(); ++i) {
         PyObject obj = (PyObject)var6.next();
         if (i > 0) {
            rval.append(this.item_separator);
         }

         this.encode_obj(rval, obj, indent_level);
      }

      if (ident != null) {
         this.markers.__delitem__(ident);
      }

      rval.append(new PyString("]"));
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.markers != null) {
         retVal = visit.visit(this.markers, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.defaultfn != null) {
         retVal = visit.visit(this.defaultfn, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.encoder != null) {
         retVal = visit.visit(this.encoder, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.indent != null) {
         retVal = visit.visit(this.indent, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.key_separator != null) {
         retVal = visit.visit(this.key_separator, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.item_separator != null) {
         retVal = visit.visit(this.item_separator, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.sort_keys != null ? visit.visit(this.sort_keys, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.markers || ob == this.defaultfn || ob == this.encoder || ob == this.indent || ob == this.key_separator || ob == this.item_separator || ob == this.sort_keys);
   }
}
