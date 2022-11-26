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
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;
import org.python.modules.gc;

@ExposedType(
   name = "fieldnameiterator",
   base = PyObject.class,
   isBaseType = false
)
public class FieldNameIterator extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   private final String markup;
   private final boolean bytes;
   private int index;
   private Object head;

   public FieldNameIterator(String fieldName, boolean bytes) {
      this.markup = fieldName;
      this.bytes = bytes;
      this.index = this.nextDotOrBracket(fieldName);
      String headStr = fieldName.substring(0, this.index);

      try {
         this.head = Integer.parseInt(headStr);
      } catch (NumberFormatException var5) {
         this.head = headStr;
      }

   }

   public FieldNameIterator(PyString fieldNameObject) {
      this(fieldNameObject.getString(), !(fieldNameObject instanceof PyUnicode));
   }

   public PyObject __iter__() {
      return this.fieldnameiterator___iter__();
   }

   final PyObject fieldnameiterator___iter__() {
      return this;
   }

   public PyObject __iternext__() {
      return this.fieldnameiterator___iternext__();
   }

   final PyObject fieldnameiterator___iternext__() {
      Chunk chunk = this.nextChunk();
      return chunk == null ? null : new PyTuple(new PyObject[]{Py.newBoolean(chunk.is_attr), this.wrap(chunk.value)});
   }

   private PyObject wrap(Object value) {
      if (value instanceof Integer) {
         return Py.newInteger((Integer)value);
      } else {
         String s = value.toString();
         if (s.length() == 0) {
            return (PyObject)(this.bytes ? Py.EmptyString : Py.EmptyUnicode);
         } else {
            return (PyObject)(this.bytes ? Py.newString(s) : Py.newUnicode(s));
         }
      }
   }

   private int nextDotOrBracket(String markup) {
      int dotPos = markup.indexOf(46, this.index);
      if (dotPos < 0) {
         dotPos = markup.length();
      }

      int bracketPos = markup.indexOf(91, this.index);
      if (bracketPos < 0) {
         bracketPos = markup.length();
      }

      return Math.min(dotPos, bracketPos);
   }

   public Object head() {
      return this.head;
   }

   public PyObject pyHead() {
      return this.wrap(this.head());
   }

   public final boolean isBytes() {
      return this.bytes;
   }

   public Chunk nextChunk() {
      if (this.index == this.markup.length()) {
         return null;
      } else {
         Chunk chunk = new Chunk();
         if (this.markup.charAt(this.index) == '[') {
            this.parseItemChunk(chunk);
         } else {
            if (this.markup.charAt(this.index) != '.') {
               throw new IllegalArgumentException("Only '.' or '[' may follow ']' in format field specifier");
            }

            this.parseAttrChunk(chunk);
         }

         return chunk;
      }
   }

   private void parseItemChunk(Chunk chunk) {
      chunk.is_attr = false;
      int endBracket = this.markup.indexOf(93, this.index + 1);
      if (endBracket < 0) {
         throw new IllegalArgumentException("Missing ']' in format string");
      } else {
         String itemValue = this.markup.substring(this.index + 1, endBracket);
         if (itemValue.length() == 0) {
            throw new IllegalArgumentException("Empty attribute in format string");
         } else {
            try {
               chunk.value = Integer.parseInt(itemValue);
            } catch (NumberFormatException var5) {
               chunk.value = itemValue;
            }

            this.index = endBracket + 1;
         }
      }
   }

   private void parseAttrChunk(Chunk chunk) {
      ++this.index;
      chunk.is_attr = true;
      int pos = this.nextDotOrBracket(this.markup);
      if (pos == this.index) {
         throw new IllegalArgumentException("Empty attribute in format string");
      } else {
         chunk.value = this.markup.substring(this.index, pos);
         this.index = pos;
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.head != null && gc.canLinkToPyObject(this.head.getClass(), true) ? gc.traverseByReflection(this.head, visit, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
      if (ob != null && ob == this.head) {
         return true;
      } else if (!gc.canLinkToPyObject(this.head.getClass(), true)) {
         return false;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   static {
      PyType.addBuilder(FieldNameIterator.class, new PyExposer());
      TYPE = PyType.fromClass(FieldNameIterator.class);
   }

   public static class Chunk {
      public boolean is_attr;
      public Object value;
   }

   private static class fieldnameiterator___iter___exposer extends PyBuiltinMethodNarrow {
      public fieldnameiterator___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public fieldnameiterator___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new fieldnameiterator___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((FieldNameIterator)this.self).fieldnameiterator___iter__();
      }
   }

   private static class fieldnameiterator___iternext___exposer extends PyBuiltinMethodNarrow {
      public fieldnameiterator___iternext___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public fieldnameiterator___iternext___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new fieldnameiterator___iternext___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((FieldNameIterator)this.self).fieldnameiterator___iternext__();
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new fieldnameiterator___iter___exposer("__iter__"), new fieldnameiterator___iternext___exposer("__iternext__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("fieldnameiterator", FieldNameIterator.class, PyObject.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
