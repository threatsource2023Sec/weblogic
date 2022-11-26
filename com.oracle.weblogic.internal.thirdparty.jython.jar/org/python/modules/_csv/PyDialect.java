package org.python.modules._csv;

import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBaseString;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyInteger;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.Untraversable;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "_csv.Dialect",
   doc = "CSV dialect\n\nThe Dialect type records CSV parsing and generation options.\n"
)
public class PyDialect extends PyObject {
   public static final PyType TYPE;
   public static final String Dialect_doc = "CSV dialect\n\nThe Dialect type records CSV parsing and generation options.\n";
   public boolean doublequote;
   public char delimiter;
   public char quotechar;
   public char escapechar;
   public boolean skipinitialspace;
   public String lineterminator;
   public QuoteStyle quoting;
   public boolean strict;

   public PyDialect() {
      super(TYPE);
   }

   public PyDialect(PyType subType) {
      super(subType);
   }

   @ExposedNew
   static final PyObject Dialect___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("__new__", args, keywords, new String[]{"dialect", "delimiter", "doublequote", "escapechar", "lineterminator", "quotechar", "quoting", "skipinitialspace", "strict"});
      PyObject dialect = ap.getPyObject(0, (PyObject)null);
      PyObject delimiter = ap.getPyObject(1, (PyObject)null);
      PyObject doublequote = ap.getPyObject(2, (PyObject)null);
      PyObject escapechar = ap.getPyObject(3, (PyObject)null);
      PyObject lineterminator = ap.getPyObject(4, (PyObject)null);
      PyObject quotechar = ap.getPyObject(5, (PyObject)null);
      PyObject quoting = ap.getPyObject(6, (PyObject)null);
      PyObject skipinitialspace = ap.getPyObject(7, (PyObject)null);
      PyObject strict = ap.getPyObject(8, (PyObject)null);
      if (dialect instanceof PyString) {
         dialect = _csv.get_dialect_from_registry(dialect);
      }

      if (dialect instanceof PyDialect && delimiter == null && doublequote == null && escapechar == null && lineterminator == null && quotechar == null && quoting == null && skipinitialspace == null && strict == null) {
         return dialect;
      } else {
         if (dialect != null) {
            delimiter = delimiter != null ? delimiter : dialect.__findattr__("delimiter");
            doublequote = doublequote != null ? doublequote : dialect.__findattr__("doublequote");
            escapechar = escapechar != null ? escapechar : dialect.__findattr__("escapechar");
            lineterminator = lineterminator != null ? lineterminator : dialect.__findattr__("lineterminator");
            quotechar = quotechar != null ? quotechar : dialect.__findattr__("quotechar");
            quoting = quoting != null ? quoting : dialect.__findattr__("quoting");
            skipinitialspace = skipinitialspace != null ? skipinitialspace : dialect.__findattr__("skipinitialspace");
            strict = strict != null ? strict : dialect.__findattr__("strict");
         }

         Object self;
         if (new_.for_type == subtype) {
            self = new PyDialect();
         } else {
            self = new PyDialectDerived(subtype);
         }

         ((PyDialect)self).delimiter = toChar("delimiter", delimiter, ',');
         ((PyDialect)self).doublequote = toBool("doublequote", doublequote, true);
         ((PyDialect)self).escapechar = toChar("escapechar", escapechar, '\u0000');
         ((PyDialect)self).lineterminator = toStr("lineterminator", lineterminator, "\r\n");
         ((PyDialect)self).quotechar = toChar("quotechar", quotechar, '"');
         int quotingOrdinal = toInt("quoting", quoting, QuoteStyle.QUOTE_MINIMAL.ordinal());
         ((PyDialect)self).skipinitialspace = toBool("skipinitialspace", skipinitialspace, false);
         ((PyDialect)self).strict = toBool("strict", strict, false);
         ((PyDialect)self).quoting = QuoteStyle.fromOrdinal(quotingOrdinal);
         if (((PyDialect)self).quoting == null) {
            throw Py.TypeError("bad \"quoting\" value");
         } else if (((PyDialect)self).delimiter == 0) {
            throw Py.TypeError("delimiter must be set");
         } else {
            if (quotechar == Py.None && quoting == null) {
               ((PyDialect)self).quoting = QuoteStyle.QUOTE_NONE;
            }

            if (((PyDialect)self).quoting != QuoteStyle.QUOTE_NONE && ((PyDialect)self).quotechar == 0) {
               throw Py.TypeError("quotechar must be set if quoting enabled");
            } else if (((PyDialect)self).lineterminator == null) {
               throw Py.TypeError("lineterminator must be set");
            } else {
               return (PyObject)self;
            }
         }
      }
   }

   private static boolean toBool(String name, PyObject src, boolean dflt) {
      return src == null ? dflt : src.__nonzero__();
   }

   private static char toChar(String name, PyObject src, char dflt) {
      if (src == null) {
         return dflt;
      } else {
         boolean isStr = Py.isInstance(src, PyString.TYPE);
         if (src == Py.None || isStr && src.__len__() == 0) {
            return '\u0000';
         } else if (isStr && src.__len__() == 1) {
            return src.toString().charAt(0);
         } else {
            throw Py.TypeError(String.format("\"%s\" must be an 1-character string", name));
         }
      }
   }

   private static int toInt(String name, PyObject src, int dflt) {
      if (src == null) {
         return dflt;
      } else if (!(src instanceof PyInteger)) {
         throw Py.TypeError(String.format("\"%s\" must be an integer", name));
      } else {
         return src.asInt();
      }
   }

   private static String toStr(String name, PyObject src, String dflt) {
      if (src == null) {
         return dflt;
      } else if (src == Py.None) {
         return null;
      } else if (!(src instanceof PyBaseString)) {
         throw Py.TypeError(String.format("\"%s\" must be an string", name));
      } else {
         return src.toString();
      }
   }

   public PyObject getEscapechar() {
      return (PyObject)(this.escapechar == 0 ? Py.None : Py.newString(this.escapechar));
   }

   public PyObject getQuotechar() {
      return (PyObject)(this.quotechar == 0 ? Py.None : Py.newString(this.quotechar));
   }

   public PyObject getQuoting() {
      return Py.newInteger(this.quoting.ordinal());
   }

   public void setQuoting(PyObject obj) {
      throw Py.AttributeError(String.format("attribute '%s' of '%s' objects is not writable", "quoting", this.getType().fastGetName()));
   }

   public void delQuoting() {
      throw Py.AttributeError(String.format("attribute '%s' of '%s' objects is not writable", "quoting", this.getType().fastGetName()));
   }

   static {
      PyType.addBuilder(PyDialect.class, new PyExposer());
      TYPE = PyType.fromClass(PyDialect.class);
   }

   private static class lineterminator_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public lineterminator_descriptor() {
         super("lineterminator", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyDialect)var1).lineterminator;
         return var10000 == null ? Py.None : Py.newString(var10000);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class quotechar_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public quotechar_descriptor() {
         super("quotechar", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyDialect)var1).getQuotechar();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class quoting_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public quoting_descriptor() {
         super("quoting", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyDialect)var1).getQuoting();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyDialect)var1).setQuoting((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyDialect)var1).delQuoting();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class escapechar_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public escapechar_descriptor() {
         super("escapechar", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyDialect)var1).getEscapechar();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class delimiter_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public delimiter_descriptor() {
         super("delimiter", Character.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.makeCharacter(((PyDialect)var1).delimiter);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class skipinitialspace_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public skipinitialspace_descriptor() {
         super("skipinitialspace", Boolean.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newBoolean(((PyDialect)var1).skipinitialspace);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class doublequote_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public doublequote_descriptor() {
         super("doublequote", Boolean.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newBoolean(((PyDialect)var1).doublequote);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class strict_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public strict_descriptor() {
         super("strict", Boolean.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newBoolean(((PyDialect)var1).strict);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyDialect.Dialect___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[]{new lineterminator_descriptor(), new quotechar_descriptor(), new quoting_descriptor(), new escapechar_descriptor(), new delimiter_descriptor(), new skipinitialspace_descriptor(), new doublequote_descriptor(), new strict_descriptor()};
         super("_csv.Dialect", PyDialect.class, Object.class, (boolean)1, "CSV dialect\n\nThe Dialect type records CSV parsing and generation options.\n", var1, var2, new exposed___new__());
      }
   }
}
