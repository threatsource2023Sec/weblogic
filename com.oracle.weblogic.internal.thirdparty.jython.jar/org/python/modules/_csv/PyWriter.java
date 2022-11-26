package org.python.modules._csv;

import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_csv.writer",
   doc = "CSV writer\n\nWriter objects are responsible for generating tabular data\nin CSV format from sequence input.\n"
)
public class PyWriter extends PyObject implements Traverseproc {
   public static final String writer_doc = "CSV writer\n\nWriter objects are responsible for generating tabular data\nin CSV format from sequence input.\n";
   public static final PyType TYPE;
   public PyDialect dialect;
   private PyObject writeline;
   private StringBuffer rec;
   private int rec_len = 0;
   private int num_fields = 0;
   private boolean quoted = false;
   public static PyString __doc__writerows;
   public static PyString __doc__writerow;

   public PyWriter(PyObject writeline, PyDialect dialect) {
      this.writeline = writeline;
      this.dialect = dialect;
   }

   public void writerows(PyObject seqseq) {
      this.writer_writerows(seqseq);
   }

   final void writer_writerows(PyObject seqseq) {
      PyObject row_iter = seqseq.__iter__();
      if (row_iter == null) {
         throw _csv.Error("writerows() argument must be iterable");
      } else {
         PyObject row_obj;
         while((row_obj = row_iter.__iternext__()) != null) {
            boolean result = this.writerow(row_obj);
            if (!result) {
               break;
            }
         }

      }
   }

   public boolean writerow(PyObject seq) {
      return this.writer_writerow(seq);
   }

   final boolean writer_writerow(PyObject seq) {
      if (!seq.isSequenceType()) {
         throw _csv.Error("sequence expected");
      } else {
         int len = seq.__len__();
         if (len < 0) {
            return false;
         } else {
            this.join_reset();

            for(int i = 0; i < len; ++i) {
               this.quoted = false;
               PyObject field = seq.__getitem__(i);
               if (field == null) {
                  return false;
               }

               switch (this.dialect.quoting) {
                  case QUOTE_NONNUMERIC:
                     try {
                        field.__float__();
                     } catch (PyException var7) {
                        this.quoted = true;
                     }
                     break;
                  case QUOTE_ALL:
                     this.quoted = true;
                     break;
                  default:
                     this.quoted = false;
               }

               boolean append_ok;
               if (field instanceof PyString) {
                  append_ok = this.join_append(field.toString(), len == 1);
               } else if (field == Py.None) {
                  append_ok = this.join_append("", len == 1);
               } else {
                  PyString str;
                  if (field.getClass() == PyFloat.class) {
                     str = field.__repr__();
                  } else {
                     str = field.__str__();
                  }

                  if (str == null) {
                     return false;
                  }

                  append_ok = this.join_append(str.toString(), len == 1);
               }

               if (!append_ok) {
                  return false;
               }
            }

            if (!this.join_append_lineterminator()) {
               return false;
            } else {
               this.writeline.__call__((PyObject)(new PyString(this.rec.toString())));
               return true;
            }
         }
      }
   }

   private void join_reset() {
      this.rec_len = 0;
      this.num_fields = 0;
      this.quoted = false;
      this.rec = new StringBuffer();
   }

   private boolean join_append_lineterminator() {
      this.rec.append(this.dialect.lineterminator);
      return true;
   }

   private boolean join_append(String field, boolean quote_empty) {
      int rec_len = this.join_append_data(field, quote_empty, false);
      if (rec_len < 0) {
         return false;
      } else {
         this.rec_len = this.join_append_data(field, quote_empty, true);
         ++this.num_fields;
         return true;
      }
   }

   private int join_append_data(String field, boolean quote_empty, boolean copy_phase) {
      if (this.num_fields > 0) {
         this.addChar(this.dialect.delimiter, copy_phase);
      }

      if (copy_phase && this.quoted) {
         this.addChar(this.dialect.quotechar, copy_phase);
      }

      field = field + '\u0000';
      int i = 0;

      while(true) {
         char c = field.charAt(i);
         boolean want_escape = false;
         if (c == 0) {
            if (i == 0 && quote_empty) {
               if (this.dialect.quoting == QuoteStyle.QUOTE_NONE) {
                  throw _csv.Error("single empty field record must be quoted");
               }

               this.quoted = true;
            }

            if (this.quoted) {
               if (copy_phase) {
                  this.addChar(this.dialect.quotechar, copy_phase);
               } else {
                  this.rec_len += 2;
               }
            }

            return this.rec_len;
         }

         if (c == this.dialect.delimiter || c == this.dialect.escapechar || c == this.dialect.quotechar || this.dialect.lineterminator.indexOf(c) > -1) {
            if (this.dialect.quoting == QuoteStyle.QUOTE_NONE) {
               want_escape = true;
            } else {
               if (c == this.dialect.quotechar) {
                  if (this.dialect.doublequote) {
                     this.addChar(this.dialect.quotechar, copy_phase);
                  } else {
                     want_escape = true;
                  }
               }

               if (!want_escape) {
                  this.quoted = true;
               }
            }

            if (want_escape) {
               if (this.dialect.escapechar == 0) {
                  throw _csv.Error("need to escape, but no escapechar set");
               }

               this.addChar(this.dialect.escapechar, copy_phase);
            }
         }

         this.addChar(c, copy_phase);
         ++i;
      }
   }

   private void addChar(char c, boolean copy_phase) {
      if (copy_phase) {
         this.rec.append(c);
      }

      ++this.rec_len;
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.dialect != null) {
         int retVal = visit.visit(this.dialect, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.writeline != null ? visit.visit(this.writeline, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.dialect || ob == this.writeline);
   }

   static {
      PyType.addBuilder(PyWriter.class, new PyExposer());
      TYPE = PyType.fromClass(PyWriter.class);
      __doc__writerows = Py.newString("writerows(sequence of sequences)\n\nConstruct and write a series of sequences to a csv file.  Non-string\nelements will be converted to string.");
      __doc__writerow = Py.newString("writerow(sequence)\n\nConstruct and write a CSV record from a sequence of fields.  Non-string\nelements will be converted to string.");
   }

   private static class writer_writerows_exposer extends PyBuiltinMethodNarrow {
      public writer_writerows_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public writer_writerows_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new writer_writerows_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyWriter)this.self).writer_writerows(var1);
         return Py.None;
      }
   }

   private static class writer_writerow_exposer extends PyBuiltinMethodNarrow {
      public writer_writerow_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public writer_writerow_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new writer_writerow_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyWriter)this.self).writer_writerow(var1));
      }
   }

   private static class dialect_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public dialect_descriptor() {
         super("dialect", PyDialect.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyWriter)var1).dialect;
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

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new writer_writerows_exposer("writerows"), new writer_writerow_exposer("writerow")};
         PyDataDescr[] var2 = new PyDataDescr[]{new dialect_descriptor()};
         super("_csv.writer", PyWriter.class, Object.class, (boolean)1, "CSV writer\n\nWriter objects are responsible for generating tabular data\nin CSV format from sequence input.\n", var1, var2, (PyNewWrapper)null);
      }
   }
}
