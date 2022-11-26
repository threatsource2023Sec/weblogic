package org.python.core.stringlib;

import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.core.Untraversable;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "formatteriterator",
   base = PyObject.class,
   isBaseType = false
)
public class MarkupIterator extends PyObject {
   public static final PyType TYPE;
   private final String markup;
   private final boolean bytes;
   private int index;
   private final FieldNumbering numbering;

   public MarkupIterator(PyString markupObject) {
      this.markup = markupObject.getString();
      this.bytes = !(markupObject instanceof PyUnicode);
      this.numbering = new FieldNumbering();
   }

   public MarkupIterator(MarkupIterator enclosingIterator, String subMarkup) {
      this.markup = subMarkup;
      this.bytes = enclosingIterator.bytes;
      this.numbering = enclosingIterator.numbering;
   }

   public PyObject __iter__() {
      return this.formatteriterator___iter__();
   }

   final PyObject formatteriterator___iter__() {
      return this;
   }

   public PyObject __iternext__() {
      return this.formatteriterator___iternext__();
   }

   final PyObject formatteriterator___iternext__() {
      try {
         Chunk chunk = this.nextChunk();
         if (chunk == null) {
            return null;
         } else {
            PyObject[] elements = new PyObject[4];
            elements[0] = this.wrap(chunk.literalText, "");
            if (chunk.fieldName == null) {
               for(int i = 1; i < elements.length; ++i) {
                  elements[i] = Py.None;
               }
            } else {
               elements[1] = this.wrap(chunk.fieldName, "");
               elements[2] = this.wrap(chunk.formatSpec, "");
               elements[3] = this.wrap(chunk.conversion, (String)null);
            }

            return new PyTuple(elements);
         }
      } catch (IllegalArgumentException var4) {
         throw Py.ValueError(var4.getMessage());
      }
   }

   private PyObject wrap(String value, String defaultValue) {
      if (value == null) {
         value = defaultValue;
      }

      if (value == null) {
         return Py.None;
      } else if (value.length() == 0) {
         return (PyObject)(this.bytes ? Py.EmptyString : Py.EmptyUnicode);
      } else {
         return (PyObject)(this.bytes ? Py.newString(value) : Py.newUnicode(value));
      }
   }

   public Chunk nextChunk() {
      if (this.index == this.markup.length()) {
         return null;
      } else {
         Chunk result = new Chunk();
         int pos = this.index;

         while(true) {
            pos = this.indexOfFirst(this.markup, pos, '{', '}');
            if (pos < 0 || pos >= this.markup.length() - 1 || this.markup.charAt(pos + 1) != this.markup.charAt(pos)) {
               if (pos >= 0 && this.markup.charAt(pos) == '}') {
                  throw new IllegalArgumentException("Single '}' encountered in format string");
               } else {
                  if (pos < 0) {
                     result.literalText = this.unescapeBraces(this.markup.substring(this.index));
                     this.index = this.markup.length();
                  } else {
                     result.literalText = this.unescapeBraces(this.markup.substring(this.index, pos));
                     ++pos;
                     int fieldStart = pos;

                     int count;
                     for(count = 1; pos < this.markup.length(); ++pos) {
                        if (this.markup.charAt(pos) == '{') {
                           ++count;
                           result.formatSpecNeedsExpanding = true;
                        } else if (this.markup.charAt(pos) == '}') {
                           --count;
                           if (count == 0) {
                              this.parseField(result, this.markup.substring(fieldStart, pos));
                              ++pos;
                              break;
                           }
                        }
                     }

                     if (count > 0) {
                        throw new IllegalArgumentException("Single '{' encountered in format string");
                     }

                     this.index = pos;
                  }

                  return result;
               }
            }

            pos += 2;
         }
      }
   }

   public final boolean isBytes() {
      return this.bytes;
   }

   private String unescapeBraces(String substring) {
      return substring.replace("{{", "{").replace("}}", "}");
   }

   private void parseField(Chunk result, String fieldMarkup) {
      int pos = this.indexOfFirst(fieldMarkup, 0, '!', ':');
      if (pos >= 0) {
         result.fieldName = fieldMarkup.substring(0, pos);
         if (fieldMarkup.charAt(pos) == '!') {
            if (pos == fieldMarkup.length() - 1) {
               throw new IllegalArgumentException("end of format while looking for conversion specifier");
            }

            result.conversion = fieldMarkup.substring(pos + 1, pos + 2);
            pos += 2;
            if (pos < fieldMarkup.length()) {
               if (fieldMarkup.charAt(pos) != ':') {
                  throw new IllegalArgumentException("expected ':' after conversion specifier");
               }

               result.formatSpec = fieldMarkup.substring(pos + 1);
            }
         } else {
            result.formatSpec = fieldMarkup.substring(pos + 1);
         }
      } else {
         result.fieldName = fieldMarkup;
      }

      if (result.fieldName.isEmpty()) {
         result.fieldName = this.numbering.nextAutomaticFieldNumber();
      } else {
         char c = result.fieldName.charAt(0);
         if (c != '.' && c != '[') {
            if (Character.isDigit(c)) {
               this.numbering.useManualFieldNumbering();
            }

         } else {
            result.fieldName = this.numbering.nextAutomaticFieldNumber() + result.fieldName;
         }
      }
   }

   private int indexOfFirst(String s, int start, char c1, char c2) {
      int i1 = s.indexOf(c1, start);
      int i2 = s.indexOf(c2, start);
      if (i1 == -1) {
         return i2;
      } else {
         return i2 == -1 ? i1 : Math.min(i1, i2);
      }
   }

   static {
      PyType.addBuilder(MarkupIterator.class, new PyExposer());
      TYPE = PyType.fromClass(MarkupIterator.class);
   }

   public static final class Chunk {
      public String literalText;
      public String fieldName;
      public String formatSpec;
      public String conversion;
      public boolean formatSpecNeedsExpanding;
   }

   static final class FieldNumbering {
      private boolean manualFieldNumberSpecified;
      private int automaticFieldNumber = 0;

      String nextAutomaticFieldNumber() {
         if (this.manualFieldNumberSpecified) {
            throw new IllegalArgumentException("cannot switch from manual field specification to automatic field numbering");
         } else {
            return Integer.toString(this.automaticFieldNumber++);
         }
      }

      void useManualFieldNumbering() {
         if (!this.manualFieldNumberSpecified) {
            if (this.automaticFieldNumber != 0) {
               throw new IllegalArgumentException("cannot switch from automatic field numbering to manual field specification");
            } else {
               this.manualFieldNumberSpecified = true;
            }
         }
      }
   }

   private static class formatteriterator___iter___exposer extends PyBuiltinMethodNarrow {
      public formatteriterator___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public formatteriterator___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new formatteriterator___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((MarkupIterator)this.self).formatteriterator___iter__();
      }
   }

   private static class formatteriterator___iternext___exposer extends PyBuiltinMethodNarrow {
      public formatteriterator___iternext___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public formatteriterator___iternext___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new formatteriterator___iternext___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((MarkupIterator)this.self).formatteriterator___iternext__();
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new formatteriterator___iter___exposer("__iter__"), new formatteriterator___iternext___exposer("__iternext__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("formatteriterator", MarkupIterator.class, PyObject.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
