package org.python.modules;

import java.math.BigInteger;
import java.util.Iterator;
import org.python.core.BaseSet;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyBytecode;
import org.python.core.PyComplex;
import org.python.core.PyDictionary;
import org.python.core.PyFloat;
import org.python.core.PyFrozenSet;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PySet;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;

public class _marshal implements ClassDictInit {
   private static final char TYPE_NULL = '0';
   private static final char TYPE_NONE = 'N';
   private static final char TYPE_FALSE = 'F';
   private static final char TYPE_TRUE = 'T';
   private static final char TYPE_STOPITER = 'S';
   private static final char TYPE_ELLIPSIS = '.';
   private static final char TYPE_INT = 'i';
   private static final char TYPE_INT64 = 'I';
   private static final char TYPE_FLOAT = 'f';
   private static final char TYPE_BINARY_FLOAT = 'g';
   private static final char TYPE_COMPLEX = 'x';
   private static final char TYPE_BINARY_COMPLEX = 'y';
   private static final char TYPE_LONG = 'l';
   private static final char TYPE_STRING = 's';
   private static final char TYPE_INTERNED = 't';
   private static final char TYPE_STRINGREF = 'R';
   private static final char TYPE_TUPLE = '(';
   private static final char TYPE_LIST = '[';
   private static final char TYPE_DICT = '{';
   private static final char TYPE_CODE = 'c';
   private static final char TYPE_UNICODE = 'u';
   private static final char TYPE_UNKNOWN = '?';
   private static final char TYPE_SET = '<';
   private static final char TYPE_FROZENSET = '>';
   private static final int MAX_MARSHAL_STACK_DEPTH = 2000;
   private static final int CURRENT_VERSION = 2;

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"__name__", Py.newString("_marshal"));
   }

   public static class Unmarshaller extends PyObject implements Traverseproc {
      private final PyIOFile file;
      private final PyList strings;
      private final int version;
      int depth;
      private boolean debug;

      public Unmarshaller(PyObject file) {
         this(file, 2);
      }

      public Unmarshaller(PyObject file, int version) {
         this.strings = new PyList();
         this.depth = 0;
         this.debug = false;
         this.file = PyIOFileFactory.createIOFile(file);
         this.version = version;
      }

      public void _debug() {
         this.debug = true;
      }

      public PyObject load() {
         try {
            PyObject obj = this.read_object(0);
            if (obj == null) {
               throw Py.TypeError("NULL object in marshal data");
            } else {
               return obj;
            }
         } catch (StringIndexOutOfBoundsException var2) {
            throw Py.EOFError("EOF read where object expected");
         }
      }

      private int read_byte() {
         int b = this.file.read(1).charAt(0);
         if (this.debug) {
            System.err.print("[" + b + "]");
         }

         return b;
      }

      private String read_string(int n) {
         return this.file.read(n);
      }

      private int read_short() {
         int x = this.read_byte();
         x |= this.read_byte() << 8;
         return x;
      }

      private int read_int() {
         int x = this.read_byte();
         x |= this.read_byte() << 8;
         x |= this.read_byte() << 16;
         x |= this.read_byte() << 24;
         return x;
      }

      private long read_long64() {
         long lo4 = (long)this.read_int();
         long hi4 = (long)this.read_int();
         long x = hi4 << 32 | lo4 & 4294967295L;
         return x;
      }

      private BigInteger read_long() {
         int size = this.read_int();
         int sign = 1;
         if (size < 0) {
            sign = -1;
            size = -size;
         }

         BigInteger result = BigInteger.ZERO;

         for(int i = 0; i < size; ++i) {
            String digits = String.valueOf(this.read_short());
            result = result.or((new BigInteger(digits)).shiftLeft(i * 15));
         }

         if (sign < 0) {
            result = result.negate();
         }

         return result;
      }

      private double read_float() {
         int size = this.read_byte();
         return Py.newString(this.read_string(size)).atof();
      }

      private double read_binary_float() {
         return Double.longBitsToDouble(this.read_long64());
      }

      private PyObject read_object_notnull(int depth) {
         PyObject v = this.read_object(depth);
         if (v == null) {
            throw Py.ValueError("bad marshal data");
         } else {
            return v;
         }
      }

      private String[] read_strings(int depth) {
         PyTuple t = (PyTuple)this.read_object_notnull(depth);
         String[] some_strings = new String[t.__len__()];
         int i = 0;

         PyObject item;
         for(Iterator var5 = t.asIterable().iterator(); var5.hasNext(); some_strings[i++] = item.toString().intern()) {
            item = (PyObject)var5.next();
         }

         return some_strings;
      }

      private PyObject read_object(int depth) {
         if (depth >= 2000) {
            throw Py.ValueError("Maximum marshal stack depth");
         } else {
            int type = this.read_byte();
            double real;
            double imag;
            int n;
            PyObject[] items;
            int i;
            switch (type) {
               case 40:
                  n = this.read_int();
                  if (n < 0) {
                     throw Py.ValueError("bad marshal data");
                  }

                  items = new PyObject[n];

                  for(i = 0; i < n; ++i) {
                     items[i] = this.read_object_notnull(depth + 1);
                  }

                  return new PyTuple(items);
               case 41:
               case 42:
               case 43:
               case 44:
               case 45:
               case 47:
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 54:
               case 55:
               case 56:
               case 57:
               case 58:
               case 59:
               case 61:
               case 63:
               case 64:
               case 65:
               case 66:
               case 67:
               case 68:
               case 69:
               case 71:
               case 72:
               case 74:
               case 75:
               case 76:
               case 77:
               case 79:
               case 80:
               case 81:
               case 85:
               case 86:
               case 87:
               case 88:
               case 89:
               case 90:
               case 92:
               case 93:
               case 94:
               case 95:
               case 96:
               case 97:
               case 98:
               case 100:
               case 101:
               case 104:
               case 106:
               case 107:
               case 109:
               case 110:
               case 111:
               case 112:
               case 113:
               case 114:
               case 118:
               case 119:
               case 122:
               default:
                  throw Py.ValueError("bad marshal data");
               case 46:
                  return Py.Ellipsis;
               case 48:
                  return null;
               case 60:
               case 62:
                  n = this.read_int();
                  items = new PyObject[n];

                  for(i = 0; i < n; ++i) {
                     items[i] = this.read_object(depth + 1);
                  }

                  PyTuple v = new PyTuple(items);
                  if (type == 60) {
                     return new PySet(v);
                  }

                  return new PyFrozenSet(v);
               case 70:
                  return Py.False;
               case 73:
                  return Py.newInteger(this.read_long64());
               case 78:
                  return Py.None;
               case 82:
                  n = this.read_int();
                  return this.strings.__getitem__(n);
               case 83:
                  return Py.StopIteration;
               case 84:
                  return Py.True;
               case 91:
                  n = this.read_int();
                  if (n < 0) {
                     throw Py.ValueError("bad marshal data");
                  }

                  items = new PyObject[n];

                  for(i = 0; i < n; ++i) {
                     items[i] = this.read_object_notnull(depth + 1);
                  }

                  return new PyList(items);
               case 99:
                  n = this.read_int();
                  int nlocals = this.read_int();
                  i = this.read_int();
                  int flags = this.read_int();
                  String code = this.read_object_notnull(depth + 1).toString();
                  PyObject[] consts = ((PyTuple)this.read_object_notnull(depth + 1)).getArray();
                  String[] names = this.read_strings(depth + 1);
                  String[] varnames = this.read_strings(depth + 1);
                  String[] freevars = this.read_strings(depth + 1);
                  String[] cellvars = this.read_strings(depth + 1);
                  String filename = this.read_object_notnull(depth + 1).toString();
                  String name = this.read_object_notnull(depth + 1).toString();
                  int firstlineno = this.read_int();
                  String lnotab = this.read_object_notnull(depth + 1).toString();
                  return new PyBytecode(n, nlocals, i, flags, code, consts, names, varnames, filename, name, firstlineno, lnotab, cellvars, freevars);
               case 102:
                  return Py.newFloat(this.read_float());
               case 103:
                  return Py.newFloat(this.read_binary_float());
               case 105:
                  return Py.newInteger(this.read_int());
               case 108:
                  return Py.newLong(this.read_long());
               case 115:
               case 116:
                  n = this.read_int();
                  String s = this.read_string(n);
                  if (type == 116) {
                     PyString pys = PyString.fromInterned(s.intern());
                     this.strings.append(pys);
                     return pys;
                  }

                  return Py.newString(s);
               case 117:
                  n = this.read_int();
                  PyString buffer = Py.newString(this.read_string(n));
                  return buffer.decode("utf-8");
               case 120:
                  real = this.read_float();
                  imag = this.read_float();
                  return new PyComplex(real, imag);
               case 121:
                  real = this.read_binary_float();
                  imag = this.read_binary_float();
                  return new PyComplex(real, imag);
               case 123:
                  PyDictionary d = new PyDictionary();

                  while(true) {
                     PyObject key = this.read_object(depth + 1);
                     if (key == null) {
                        return d;
                     }

                     PyObject value = this.read_object(depth + 1);
                     if (value != null) {
                        d.__setitem__(key, value);
                     }
                  }
            }
         }
      }

      public int traverse(Visitproc visit, Object arg) {
         if (this.file instanceof Traverseproc) {
            int retVal = ((Traverseproc)this.file).traverse(visit, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         return visit.visit(this.strings, arg);
      }

      public boolean refersDirectlyTo(PyObject ob) {
         if (ob == null) {
            return false;
         } else if (this.file != null && this.file instanceof Traverseproc && ((Traverseproc)this.file).refersDirectlyTo(ob)) {
            return true;
         } else {
            return ob == this.strings;
         }
      }
   }

   public static class Marshaller extends PyObject implements Traverseproc {
      private final PyIOFile file;
      private final int version;
      private boolean debug;

      public Marshaller(PyObject file) {
         this(file, 2);
      }

      public Marshaller(PyObject file, int version) {
         this.debug = false;
         this.file = PyIOFileFactory.createIOFile(file);
         this.version = version;
      }

      public void _debug() {
         this.debug = true;
      }

      public void dump(PyObject obj) {
         this.write_object(obj, 0);
      }

      private void write_byte(char c) {
         if (this.debug) {
            System.err.print("[" + c + "]");
         }

         this.file.write(c);
      }

      private void write_string(String s) {
         this.file.write(s);
      }

      private void write_strings(String[] some_strings, int depth) {
         PyObject[] items = new PyObject[some_strings.length];

         for(int i = 0; i < some_strings.length; ++i) {
            items[i] = Py.newString(some_strings[i]);
         }

         this.write_object(new PyTuple(items), depth + 1);
      }

      private void write_short(short x) {
         this.write_byte((char)(x & 255));
         this.write_byte((char)(x >> 8 & 255));
      }

      private void write_int(int x) {
         this.write_byte((char)(x & 255));
         this.write_byte((char)(x >> 8 & 255));
         this.write_byte((char)(x >> 16 & 255));
         this.write_byte((char)(x >> 24 & 255));
      }

      private void write_long64(long x) {
         this.write_int((int)(x & -1L));
         this.write_int((int)(x >> 32 & -1L));
      }

      private void write_long(BigInteger x) {
         int sign = x.signum();
         if (sign < 0) {
            x = x.negate();
         }

         int num_bits = x.bitLength();
         int num_digits = num_bits / 15 + (num_bits % 15 == 0 ? 0 : 1);
         this.write_int(sign < 0 ? -num_digits : num_digits);
         BigInteger mask = BigInteger.valueOf(32767L);

         for(int i = 0; i < num_digits; ++i) {
            this.write_short(x.and(mask).shortValue());
            x = x.shiftRight(15);
         }

      }

      private void write_float(PyFloat f) {
         this.write_string(f.__repr__().toString());
      }

      private void write_binary_float(PyFloat f) {
         this.write_long64(Double.doubleToLongBits(f.getValue()));
      }

      private void write_object(PyObject v, int depth) {
         if (depth >= 2000) {
            throw Py.ValueError("Maximum marshal stack depth");
         } else {
            if (v == null) {
               this.write_byte('0');
            } else if (v == Py.None) {
               this.write_byte('N');
            } else if (v == Py.StopIteration) {
               this.write_byte('S');
            } else if (v == Py.Ellipsis) {
               this.write_byte('.');
            } else if (v == Py.False) {
               this.write_byte('F');
            } else if (v == Py.True) {
               this.write_byte('T');
            } else if (v instanceof PyInteger) {
               this.write_byte('i');
               this.write_int(((PyInteger)v).asInt());
            } else if (v instanceof PyLong) {
               this.write_byte('l');
               this.write_long(((PyLong)v).getValue());
            } else if (v instanceof PyFloat) {
               if (this.version == 2) {
                  this.write_byte('g');
                  this.write_binary_float((PyFloat)v);
               } else {
                  this.write_byte('f');
                  this.write_float((PyFloat)v);
               }
            } else if (v instanceof PyComplex) {
               PyComplex x = (PyComplex)v;
               if (this.version == 2) {
                  this.write_byte('y');
                  this.write_binary_float(x.getReal());
                  this.write_binary_float(x.getImag());
               } else {
                  this.write_byte('x');
                  this.write_float(x.getReal());
                  this.write_float(x.getImag());
               }
            } else if (v instanceof PyUnicode) {
               this.write_byte('u');
               String buffer = ((PyUnicode)v).encode("utf-8").toString();
               this.write_int(buffer.length());
               this.write_string(buffer);
            } else if (v instanceof PyString) {
               this.write_byte('s');
               this.write_int(v.__len__());
               this.write_string(v.toString());
            } else {
               int n;
               int i;
               if (v instanceof PyTuple) {
                  this.write_byte('(');
                  PyTuple t = (PyTuple)v;
                  n = t.__len__();
                  this.write_int(n);

                  for(i = 0; i < n; ++i) {
                     this.write_object(t.__getitem__(i), depth + 1);
                  }
               } else if (v instanceof PyList) {
                  this.write_byte('[');
                  PyList list = (PyList)v;
                  n = list.__len__();
                  this.write_int(n);

                  for(i = 0; i < n; ++i) {
                     this.write_object(list.__getitem__(i), depth + 1);
                  }
               } else if (v instanceof PyDictionary) {
                  this.write_byte('{');
                  PyDictionary dict = (PyDictionary)v;
                  Iterator var11 = dict.iteritems().asIterable().iterator();

                  while(var11.hasNext()) {
                     PyObject item = (PyObject)var11.next();
                     PyTuple pair = (PyTuple)item;
                     this.write_object(pair.__getitem__(0), depth + 1);
                     this.write_object(pair.__getitem__(1), depth + 1);
                  }

                  this.write_object((PyObject)null, depth + 1);
               } else if (v instanceof BaseSet) {
                  if (v instanceof PySet) {
                     this.write_byte('<');
                  } else {
                     this.write_byte('>');
                  }

                  int n = v.__len__();
                  this.write_int(n);
                  BaseSet set = (BaseSet)v;
                  Iterator var17 = set.asIterable().iterator();

                  while(var17.hasNext()) {
                     PyObject item = (PyObject)var17.next();
                     this.write_object(item, depth + 1);
                  }
               } else if (v instanceof PyBytecode) {
                  PyBytecode code = (PyBytecode)v;
                  this.write_byte('c');
                  this.write_int(code.co_argcount);
                  this.write_int(code.co_nlocals);
                  this.write_int(code.co_stacksize);
                  this.write_int(code.co_flags.toBits());
                  this.write_object(Py.newString(new String(code.co_code)), depth + 1);
                  this.write_object(new PyTuple(code.co_consts), depth + 1);
                  this.write_strings(code.co_names, depth + 1);
                  this.write_strings(code.co_varnames, depth + 1);
                  this.write_strings(code.co_freevars, depth + 1);
                  this.write_strings(code.co_cellvars, depth + 1);
                  this.write_object(Py.newString(code.co_name), depth + 1);
                  this.write_int(code.co_firstlineno);
                  this.write_object(Py.newString(new String(code.co_lnotab)), depth + 1);
               } else {
                  this.write_byte('?');
               }
            }

            --depth;
         }
      }

      public int traverse(Visitproc visit, Object arg) {
         return this.file != null && this.file instanceof Traverseproc ? ((Traverseproc)this.file).traverse(visit, arg) : 0;
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return this.file != null && this.file instanceof Traverseproc ? ((Traverseproc)this.file).refersDirectlyTo(ob) : false;
      }
   }
}
