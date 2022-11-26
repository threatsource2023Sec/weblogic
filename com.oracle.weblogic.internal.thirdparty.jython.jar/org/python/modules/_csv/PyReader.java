package org.python.modules._csv;

import org.python.core.Py;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyIterator;
import org.python.core.PyList;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_csv.reader",
   doc = "CSV reader\n\nReader objects are responsible for reading and parsing tabular data\nin CSV format.\n"
)
public class PyReader extends PyIterator {
   public static final PyType TYPE;
   public static final String reader_doc = "CSV reader\n\nReader objects are responsible for reading and parsing tabular data\nin CSV format.\n";
   public PyDialect dialect;
   public int line_num = 0;
   private PyObject input_iter;
   private ParserState state;
   private PyList fields;
   private StringBuffer field;
   private boolean numeric_field;
   private static final int INITIAL_BUILDER_CAPACITY = 4096;

   public PyReader(PyObject input_iter, PyDialect dialect) {
      this.state = PyReader.ParserState.START_RECORD;
      this.fields = new PyList();
      this.field = new StringBuffer(4096);
      this.numeric_field = false;
      this.input_iter = input_iter;
      this.dialect = dialect;
   }

   public PyObject __iternext__() {
      this.parse_reset();

      do {
         PyObject lineobj = this.input_iter.__iternext__();
         if (lineobj == null) {
            if (this.field.length() == 0 && this.state != PyReader.ParserState.IN_QUOTED_FIELD) {
               return null;
            }

            if (this.dialect.strict) {
               throw _csv.Error("unexpected end of data");
            }

            this.parse_save_field();
            break;
         }

         ++this.line_num;
         String line = lineobj.toString();
         int linelen = line.length();

         for(int i = 0; i < linelen; ++i) {
            char c = line.charAt(i);
            if (c == 0) {
               throw _csv.Error("line contains NULL byte");
            }

            this.parse_process_char(c);
         }

         this.parse_process_char('\u0000');
      } while(this.state != PyReader.ParserState.START_RECORD);

      PyObject fields = this.fields;
      this.fields = new PyList();
      return fields;
   }

   private void parse_process_char(char c) {
      switch (this.state) {
         case START_RECORD:
            if (c == 0) {
               break;
            }

            if (c == '\n' || c == '\r') {
               this.state = PyReader.ParserState.EAT_CRNL;
               break;
            } else {
               this.state = PyReader.ParserState.START_FIELD;
            }
         case START_FIELD:
            if (c != '\n' && c != '\r' && c != 0) {
               if (c == this.dialect.quotechar && this.dialect.quoting != QuoteStyle.QUOTE_NONE) {
                  this.state = PyReader.ParserState.IN_QUOTED_FIELD;
               } else if (c == this.dialect.escapechar) {
                  this.state = PyReader.ParserState.ESCAPED_CHAR;
               } else if (c != ' ' || !this.dialect.skipinitialspace) {
                  if (c == this.dialect.delimiter) {
                     this.parse_save_field();
                  } else {
                     if (this.dialect.quoting == QuoteStyle.QUOTE_NONNUMERIC) {
                        this.numeric_field = true;
                     }

                     this.parse_add_char(c);
                     this.state = PyReader.ParserState.IN_FIELD;
                  }
               }
            } else {
               this.parse_save_field();
               this.state = c == 0 ? PyReader.ParserState.START_RECORD : PyReader.ParserState.EAT_CRNL;
            }
            break;
         case ESCAPED_CHAR:
            if (c == 0) {
               c = '\n';
            }

            this.parse_add_char(c);
            this.state = PyReader.ParserState.IN_FIELD;
            break;
         case IN_FIELD:
            if (c != '\n' && c != '\r' && c != 0) {
               if (c == this.dialect.escapechar) {
                  this.state = PyReader.ParserState.ESCAPED_CHAR;
               } else if (c == this.dialect.delimiter) {
                  this.parse_save_field();
                  this.state = PyReader.ParserState.START_FIELD;
               } else {
                  this.parse_add_char(c);
               }
            } else {
               this.parse_save_field();
               this.state = c == 0 ? PyReader.ParserState.START_RECORD : PyReader.ParserState.EAT_CRNL;
            }
            break;
         case IN_QUOTED_FIELD:
            if (c != 0) {
               if (c == this.dialect.escapechar) {
                  this.state = PyReader.ParserState.ESCAPE_IN_QUOTED_FIELD;
               } else if (c == this.dialect.quotechar && this.dialect.quoting != QuoteStyle.QUOTE_NONE) {
                  if (this.dialect.doublequote) {
                     this.state = PyReader.ParserState.QUOTE_IN_QUOTED_FIELD;
                  } else {
                     this.state = PyReader.ParserState.IN_FIELD;
                  }
               } else {
                  this.parse_add_char(c);
               }
            }
            break;
         case ESCAPE_IN_QUOTED_FIELD:
            if (c == 0) {
               c = '\n';
            }

            this.parse_add_char(c);
            this.state = PyReader.ParserState.IN_QUOTED_FIELD;
            break;
         case QUOTE_IN_QUOTED_FIELD:
            if (this.dialect.quoting != QuoteStyle.QUOTE_NONE && c == this.dialect.quotechar) {
               this.parse_add_char(c);
               this.state = PyReader.ParserState.IN_QUOTED_FIELD;
            } else if (c == this.dialect.delimiter) {
               this.parse_save_field();
               this.state = PyReader.ParserState.START_FIELD;
            } else if (c != '\n' && c != '\r' && c != 0) {
               if (this.dialect.strict) {
                  throw _csv.Error(String.format("'%c' expected after '%c'", this.dialect.delimiter, this.dialect.quotechar));
               }

               this.parse_add_char(c);
               this.state = PyReader.ParserState.IN_FIELD;
            } else {
               this.parse_save_field();
               this.state = c == 0 ? PyReader.ParserState.START_RECORD : PyReader.ParserState.EAT_CRNL;
            }
            break;
         case EAT_CRNL:
            if (c != '\n' && c != '\r') {
               if (c != 0) {
                  String err = "new-line character seen in unquoted field - do you need to open the file in universal-newline mode?";
                  throw _csv.Error(err);
               }

               this.state = PyReader.ParserState.START_RECORD;
            }
      }

   }

   private void parse_reset() {
      this.fields = new PyList();
      this.state = PyReader.ParserState.START_RECORD;
      this.numeric_field = false;
   }

   private void parse_save_field() {
      PyObject field = new PyString(this.field.toString());
      if (this.numeric_field) {
         this.numeric_field = false;
         field = ((PyObject)field).__float__();
      }

      this.fields.append((PyObject)field);
      this.field = new StringBuffer(4096);
   }

   private void parse_add_char(char c) {
      int field_len = this.field.length();
      if (field_len >= _csv.field_limit) {
         throw _csv.Error(String.format("field larger than field limit (%d)", _csv.field_limit));
      } else {
         this.field.append(c);
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         if (this.dialect != null) {
            retVal = visit.visit(this.dialect, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (this.input_iter != null) {
            retVal = visit.visit(this.input_iter, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         return this.fields != null ? visit.visit(this.fields, arg) : 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob == null && (ob == this.fields || ob == this.dialect || ob == this.input_iter || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(PyReader.class, new PyExposer());
      TYPE = PyType.fromClass(PyReader.class);
   }

   private static enum ParserState {
      START_RECORD,
      START_FIELD,
      ESCAPED_CHAR,
      IN_FIELD,
      IN_QUOTED_FIELD,
      ESCAPE_IN_QUOTED_FIELD,
      QUOTE_IN_QUOTED_FIELD,
      EAT_CRNL;
   }

   private static class dialect_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public dialect_descriptor() {
         super("dialect", PyDialect.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyReader)var1).dialect;
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

   private static class line_num_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public line_num_descriptor() {
         super("line_num", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyReader)var1).line_num);
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
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[]{new dialect_descriptor(), new line_num_descriptor()};
         super("_csv.reader", PyReader.class, Object.class, (boolean)1, "CSV reader\n\nReader objects are responsible for reading and parsing tabular data\nin CSV format.\n", var1, var2, (PyNewWrapper)null);
      }
   }
}
