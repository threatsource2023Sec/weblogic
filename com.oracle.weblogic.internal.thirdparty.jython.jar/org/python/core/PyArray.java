package org.python.core;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Iterator;
import org.python.core.buffer.BaseBuffer;
import org.python.core.buffer.SimpleStringBuffer;
import org.python.core.buffer.SimpleWritableBuffer;
import org.python.core.util.ByteSwapper;
import org.python.core.util.StringUtil;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.modules.gc;

@ExposedType(
   name = "array.array",
   base = PyObject.class
)
public class PyArray extends PySequence implements Cloneable, BufferProtocol, Traverseproc {
   public static final PyType TYPE;
   private Object data;
   private Class type;
   private String typecode;
   private ArrayDelegate delegate;
   private WeakReference export;

   public PyArray(PyType type) {
      super(type);
   }

   public PyArray(Class type, Object data) {
      this(TYPE);
      this.setup(type, data);
   }

   public PyArray(Class type, PyObject initial) {
      this(TYPE);
      this.type = type;
      this.typecode = class2char(type);
      this.data = Array.newInstance(type, 0);
      this.delegate = new ArrayDelegate();
      this.useInitial(initial);
   }

   public PyArray(Class type, int n) {
      this(type, Array.newInstance(type, n));
   }

   public PyArray(PyArray toCopy) {
      this(toCopy.type, toCopy.delegate.copyArray());
      this.typecode = toCopy.typecode;
   }

   private void setup(Class type, Object data) {
      this.type = type;
      this.typecode = class2char(type);
      if (data == null) {
         this.data = Array.newInstance(type, 0);
      } else {
         this.data = data;
      }

      this.delegate = new ArrayDelegate();
   }

   private void useInitial(PyObject initial) {
      if (initial != null) {
         if (initial instanceof PyList) {
            this.fromlist(initial);
         } else if (initial instanceof PyString && !(initial instanceof PyUnicode)) {
            this.fromstring(initial.toString());
         } else if ("u".equals(this.typecode)) {
            if (initial instanceof PyUnicode) {
               this.extendArray(((PyUnicode)initial).toCodePoints());
            } else {
               this.extendUnicodeIter(initial);
            }
         } else {
            this.extendInternal(initial);
         }
      }

   }

   @ExposedNew
   static final PyObject array_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      if (new_.for_type != subtype && keywords.length > 0) {
         int argc = args.length - keywords.length;
         PyObject[] justArgs = new PyObject[argc];
         System.arraycopy(args, 0, justArgs, 0, argc);
         args = justArgs;
      }

      ArgParser ap = new ArgParser("array", args, Py.NoKeywords, new String[]{"typecode", "initializer"}, 1);
      ap.noKeywords();
      PyObject obj = ap.getPyObject(0);
      String typecode;
      Class type;
      if (obj instanceof PyString && !(obj instanceof PyUnicode)) {
         if (obj.__len__() != 1) {
            throw Py.TypeError("array() argument 1 must be char, not str");
         }

         typecode = obj.toString();
         type = char2class(typecode.charAt(0));
      } else {
         if (!(obj instanceof PyJavaType)) {
            throw Py.TypeError("array() argument 1 must be char, not " + obj.getType().fastGetName());
         }

         type = ((PyJavaType)obj).getProxyType();
         typecode = type.getName();
      }

      Object self;
      if (new_.for_type == subtype) {
         self = new PyArray(subtype);
      } else {
         self = new PyArrayDerived(subtype);
      }

      class2char(type);
      ((PyArray)self).setup(type, Array.newInstance(type, 0));
      ((PyArray)self).typecode = typecode;
      ((PyArray)self).useInitial(ap.getPyObject(1, (PyObject)null));
      return (PyObject)self;
   }

   public static PyArray zeros(int n, char typecode) {
      PyArray array = zeros(n, char2class(typecode));
      array.typecode = Character.toString(typecode);
      return array;
   }

   public static PyArray zeros(int n, Class ctype) {
      PyArray array = new PyArray(ctype, n);
      array.typecode = ctype.getName();
      return array;
   }

   public static PyArray array(PyObject seq, char typecode) {
      PyArray array = array(seq, char2class(typecode));
      array.typecode = Character.toString(typecode);
      return array;
   }

   public static Class array_class(Class type) {
      return Array.newInstance(type, 0).getClass();
   }

   public static PyArray array(PyObject init, Class ctype) {
      PyArray array = new PyArray(ctype, 0);
      array.typecode = ctype.getName();
      array.extendInternal(init);
      return array;
   }

   final PyObject array___ne__(PyObject o) {
      return this.seq___ne__(o);
   }

   final PyObject array___eq__(PyObject o) {
      return this.seq___eq__(o);
   }

   public int hashCode() {
      return this.array___hash__();
   }

   final int array___hash__() {
      throw Py.TypeError(String.format("unhashable type: '%.200s'", this.getType().fastGetName()));
   }

   final PyObject array___lt__(PyObject o) {
      return this.seq___lt__(o);
   }

   final PyObject array___le__(PyObject o) {
      return this.seq___le__(o);
   }

   final PyObject array___gt__(PyObject o) {
      return this.seq___gt__(o);
   }

   final PyObject array___ge__(PyObject o) {
      return this.seq___ge__(o);
   }

   final boolean array___contains__(PyObject o) {
      return this.object___contains__(o);
   }

   final void array___delitem__(PyObject index) {
      this.seq___delitem__(index);
   }

   final void array___setitem__(PyObject o, PyObject def) {
      this.seq___setitem__(o, def);
   }

   final PyObject array___getitem__(PyObject o) {
      PyObject ret = this.seq___finditem__(o);
      if (ret == null) {
         throw Py.IndexError("index out of range: " + o);
      } else {
         return ret;
      }
   }

   final boolean array___nonzero__() {
      return this.seq___nonzero__();
   }

   public PyObject array___iter__() {
      return this.seq___iter__();
   }

   final PyObject array___getslice__(PyObject start, PyObject stop, PyObject step) {
      return this.seq___getslice__(start, stop, step);
   }

   final void array___setslice__(PyObject start, PyObject stop, PyObject step, PyObject value) {
      this.seq___setslice__(start, stop, step, value);
   }

   final void array___delslice__(PyObject start, PyObject stop, PyObject step) {
      this.seq___delslice__(start, stop, step);
   }

   public PyObject __imul__(PyObject o) {
      return this.array___imul__(o);
   }

   final PyObject array___imul__(PyObject o) {
      if (!o.isIndex()) {
         return null;
      } else {
         this.resizeCheck();
         if (this.delegate.getSize() > 0) {
            int count = o.asIndex(Py.OverflowError);
            if (count <= 0) {
               this.delegate.clear();
               return this;
            }

            Object copy = this.delegate.copyArray();
            this.delegate.ensureCapacity(this.delegate.getSize() * count);

            for(int i = 1; i < count; ++i) {
               this.delegate.appendArray(copy);
            }
         }

         return this;
      }
   }

   public PyObject __mul__(PyObject o) {
      return this.array___mul__(o);
   }

   final PyObject array___mul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
   }

   public PyObject __rmul__(PyObject o) {
      return this.array___rmul__(o);
   }

   final PyObject array___rmul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
   }

   public PyObject __iadd__(PyObject other) {
      return this.array___iadd__(other);
   }

   final PyObject array___iadd__(PyObject other) {
      if (!(other instanceof PyArray)) {
         return null;
      } else {
         PyArray otherArr = (PyArray)other;
         if (!otherArr.typecode.equals(this.typecode)) {
            throw Py.TypeError("can only append arrays of the same type, expected '" + this.type + ", found " + otherArr.type);
         } else {
            this.resizeCheck();
            this.delegate.appendArray(otherArr.delegate.copyArray());
            return this;
         }
      }
   }

   public PyObject __add__(PyObject other) {
      return this.array___add__(other);
   }

   final PyObject array___add__(PyObject other) {
      if (!(other instanceof PyArray)) {
         return null;
      } else {
         PyArray otherArr = (PyArray)other;
         if (!otherArr.typecode.equals(this.typecode)) {
            throw Py.TypeError("can only append arrays of the same type, expected '" + this.type + ", found " + otherArr.type);
         } else {
            PyArray ret = new PyArray(this);
            ret.delegate.appendArray(otherArr.delegate.copyArray());
            return ret;
         }
      }
   }

   public int __len__() {
      return this.array___len__();
   }

   final int array___len__() {
      return this.delegate.getSize();
   }

   public PyObject __reduce__() {
      return this.array___reduce__();
   }

   final PyObject array___reduce__() {
      PyObject dict = this.__findattr__("__dict__");
      if (dict == null) {
         dict = Py.None;
      }

      return this.__len__() > 0 ? new PyTuple(new PyObject[]{this.getType(), new PyTuple(new PyObject[]{Py.newString(this.typecode), Py.newString(this.tostring())}), dict}) : new PyTuple(new PyObject[]{this.getType(), new PyTuple(new PyObject[]{Py.newString(this.typecode)}), dict});
   }

   public String toString() {
      if (this.__len__() == 0) {
         return String.format("array(%s)", this.encodeTypecode(this.typecode));
      } else {
         String value;
         if ("c".equals(this.typecode)) {
            value = PyString.encode_UnicodeEscape(this.tostring(), true);
         } else if ("u".equals(this.typecode)) {
            value = (new PyUnicode(this.tounicode())).__repr__().toString();
         } else {
            value = this.tolist().toString();
         }

         return String.format("array(%s, %s)", this.encodeTypecode(this.typecode), value);
      }
   }

   private String encodeTypecode(String typecode) {
      return typecode.length() > 1 ? typecode : "'" + typecode + "'";
   }

   public Object __tojava__(Class c) {
      boolean isArray = c.isArray();
      Class componentType = c.getComponentType();
      if (c != Object.class && (!isArray || !componentType.isAssignableFrom(this.type))) {
         if (isArray && componentType == Object.class) {
            Object[] boxed = new Object[this.delegate.size];

            for(int i = 0; i < this.delegate.size; ++i) {
               boxed[i] = Array.get(this.data, i);
            }

            return boxed;
         } else {
            return c.isInstance(this) ? this : Py.NoConversion;
         }
      } else {
         return this.delegate.capacity != this.delegate.size ? this.delegate.copyArray() : this.data;
      }
   }

   public final void array_append(PyObject value) {
      this.resizeCheck();
      this.appendUnchecked(value);
   }

   private static int getCodePoint(PyObject obj) {
      if (obj instanceof PyUnicode) {
         PyUnicode u = (PyUnicode)obj;
         int[] codepoints = u.toCodePoints();
         if (codepoints.length == 1) {
            return codepoints[0];
         }
      }

      throw Py.TypeError("array item must be unicode character");
   }

   private static int getCodePointOrInt(PyObject obj) {
      if (obj instanceof PyUnicode) {
         PyUnicode u = (PyUnicode)obj;
         return u.toCodePoints()[0];
      } else if (obj instanceof PyString) {
         PyString s = (PyString)obj;
         return s.toString().charAt(0);
      } else {
         return obj.__nonzero__() ? obj.asInt() : -1;
      }
   }

   public void append(PyObject value) {
      this.resizeCheck();
      this.appendUnchecked(value);
   }

   private final void appendUnchecked(PyObject value) {
      int afterLast = this.delegate.getSize();
      if ("u".equals(this.typecode)) {
         int codepoint = getCodePoint(value);
         this.delegate.makeInsertSpace(afterLast);
         Array.setInt(this.data, afterLast, codepoint);
      } else {
         this.delegate.makeInsertSpace(afterLast);

         try {
            this.set(afterLast, value);
         } catch (PyException var4) {
            this.delegate.setSize(afterLast);
            throw new PyException(var4.type, var4.value);
         }
      }

   }

   public void array_byteswap() {
      this.byteswap();
   }

   public void byteswap() {
      if (this.getStorageSize() != 0 && !"u".equals(this.typecode)) {
         ByteSwapper.swap(this.data);
      } else {
         throw Py.RuntimeError("don't know how to byteswap this array type");
      }
   }

   public Object clone() {
      return new PyArray(this);
   }

   public static Class char2class(char type) throws PyIgnoreMethodTag {
      switch (type) {
         case 'B':
            return Short.TYPE;
         case 'H':
            return Integer.TYPE;
         case 'I':
            return Long.TYPE;
         case 'L':
            return Long.TYPE;
         case 'b':
            return Byte.TYPE;
         case 'c':
            return Character.TYPE;
         case 'd':
            return Double.TYPE;
         case 'f':
            return Float.TYPE;
         case 'h':
            return Short.TYPE;
         case 'i':
            return Integer.TYPE;
         case 'l':
            return Long.TYPE;
         case 'u':
            return Integer.TYPE;
         case 'z':
            return Boolean.TYPE;
         default:
            throw Py.ValueError("bad typecode (must be c, b, B, u, h, H, i, I, l, L, f or d)");
      }
   }

   private static String class2char(Class cls) {
      if (cls.equals(Boolean.TYPE)) {
         return "z";
      } else if (cls.equals(Character.TYPE)) {
         return "c";
      } else if (cls.equals(Byte.TYPE)) {
         return "b";
      } else if (cls.equals(Short.TYPE)) {
         return "h";
      } else if (cls.equals(Integer.TYPE)) {
         return "i";
      } else if (cls.equals(Long.TYPE)) {
         return "l";
      } else if (cls.equals(Float.TYPE)) {
         return "f";
      } else {
         return cls.equals(Double.TYPE) ? "d" : cls.getName();
      }
   }

   public final int array_count(PyObject value) {
      int iCount = 0;
      int len = this.delegate.getSize();
      int codepoint;
      if ("u".equals(this.typecode)) {
         codepoint = getCodePointOrInt(value);

         for(int i = 0; i < len; ++i) {
            if (codepoint == Array.getInt(this.data, i)) {
               ++iCount;
            }
         }
      } else {
         for(codepoint = 0; codepoint < len; ++codepoint) {
            if (value.equals(Py.java2py(Array.get(this.data, codepoint)))) {
               ++iCount;
            }
         }
      }

      return iCount;
   }

   public PyInteger count(PyObject value) {
      return Py.newInteger(this.array_count(value));
   }

   protected void del(int i) {
      this.resizeCheck();
      this.delegate.remove(i);
   }

   protected void delRange(int start, int stop) {
      this.resizeCheck();
      this.delegate.remove(start, stop);
   }

   public final void array_extend(PyObject iterable) {
      this.extendInternal(iterable);
   }

   public void extend(PyObject iterable) {
      this.extendInternal(iterable);
   }

   private void extendInternal(PyObject iterable) {
      if (iterable instanceof PyUnicode) {
         if (!"u".equals(this.typecode)) {
            if ("c".equals(this.typecode)) {
               throw Py.TypeError("array item must be char");
            }

            throw Py.TypeError("an integer is required");
         }

         this.extendUnicodeIter(iterable);
      } else if (iterable instanceof PyArray) {
         PyArray source = (PyArray)iterable;
         if (!source.typecode.equals(this.typecode)) {
            throw Py.TypeError("can only extend with array of same kind");
         }

         this.resizeCheck();
         this.delegate.appendArray(source.delegate.copyArray());
      } else {
         this.extendInternalIter(iterable);
      }

   }

   private void extendInternalIter(PyObject iterable) {
      this.resizeCheck();
      if (iterable.__findattr__("__len__") != null) {
         int last = this.delegate.getSize();
         this.delegate.ensureCapacity(last + iterable.__len__());

         for(Iterator var3 = iterable.asIterable().iterator(); var3.hasNext(); ++this.delegate.size) {
            PyObject item = (PyObject)var3.next();
            this.set(last++, item);
         }
      } else {
         Iterator var5 = iterable.asIterable().iterator();

         while(var5.hasNext()) {
            PyObject item = (PyObject)var5.next();
            this.appendUnchecked(item);
         }
      }

   }

   private void extendUnicodeIter(PyObject iterable) {
      this.resizeCheck();

      try {
         Iterator var2 = iterable.asIterable().iterator();

         while(var2.hasNext()) {
            PyObject item = (PyObject)var2.next();
            PyUnicode uitem = (PyUnicode)item;
            int[] var5 = uitem.toCodePoints();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               int codepoint = var5[var7];
               int afterLast = this.delegate.getSize();
               this.delegate.makeInsertSpace(afterLast);
               Array.setInt(this.data, afterLast, codepoint);
            }
         }

      } catch (ClassCastException var10) {
         throw Py.TypeError("Type not compatible with array type");
      }
   }

   private void extendArray(int[] items) {
      this.resizeCheck();
      int last = this.delegate.getSize();
      this.delegate.ensureCapacity(last + items.length);
      int[] var3 = items;
      int var4 = items.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int item = var3[var5];
         Array.set(this.data, last++, item);
         ++this.delegate.size;
      }

   }

   public final void array_fromfile(PyObject f, int count) {
      this.fromfile(f, count);
   }

   public void fromfile(PyObject f, int count) {
      this.resizeCheck();
      if (f instanceof PyFile) {
         PyFile file = (PyFile)f;
         if (!file.getClosed()) {
            int readbytes = count * this.getStorageSize();
            String buffer = file.read(readbytes).toString();
            this.fromstring(buffer);
            if (buffer.length() < readbytes) {
               int readcount = buffer.length() / this.getStorageSize();
               throw Py.EOFError("not enough items in file. " + Integer.toString(count) + " requested, " + Integer.toString(readcount) + " actually read");
            }
         }

      } else {
         throw Py.TypeError("arg1 must be open file");
      }
   }

   public final void array_fromlist(PyObject obj) {
      this.fromlist(obj);
   }

   public void fromlist(PyObject obj) {
      if (!(obj instanceof PyList)) {
         throw Py.TypeError("arg must be list");
      } else {
         this.resizeCheck();
         int size = this.delegate.getSize();

         try {
            this.extendInternalIter(obj);
         } catch (PyException var4) {
            this.delegate.setSize(size);
            throw new PyException(var4.type, var4.value);
         }
      }
   }

   private int fromStream(InputStream is) throws IOException, EOFException {
      return this.fromStream(is, is.available() / this.getStorageSize());
   }

   private int fromStream(InputStream is, int count) throws IOException, EOFException {
      int origsize = this.delegate.getSize();
      int n = this.fromStream(is, origsize, origsize + count, true);
      return n - origsize;
   }

   public int fillFromStream(InputStream is) throws IOException {
      return this.fromStream(is, 0, this.delegate.size, false);
   }

   private int fromStream(InputStream is, int index, int limit, boolean eofIsError) throws IOException, EOFException {
      if (limit > this.delegate.getSize()) {
         this.resizeCheck();
         this.delegate.setSize(limit);
      }

      DataInputStream dis = new DataInputStream(is);

      try {
         if (this.type.isPrimitive()) {
            switch (this.typecode.charAt(0)) {
               case 'B':
                  while(index < limit) {
                     Array.setShort(this.data, index, unsignedByte(dis.readByte()));
                     ++index;
                  }

                  return index;
               case 'H':
                  while(index < limit) {
                     Array.setInt(this.data, index, unsignedShort(dis.readShort()));
                     ++index;
                  }

                  return index;
               case 'I':
                  while(index < limit) {
                     Array.setLong(this.data, index, unsignedInt(dis.readInt()));
                     ++index;
                  }

                  return index;
               case 'L':
                  while(index < limit) {
                     Array.setLong(this.data, index, dis.readLong());
                     ++index;
                  }

                  return index;
               case 'b':
                  while(index < limit) {
                     Array.setByte(this.data, index, dis.readByte());
                     ++index;
                  }

                  return index;
               case 'c':
                  while(index < limit) {
                     Array.setChar(this.data, index, (char)(dis.readByte() & 255));
                     ++index;
                  }

                  return index;
               case 'd':
                  while(index < limit) {
                     Array.setDouble(this.data, index, dis.readDouble());
                     ++index;
                  }

                  return index;
               case 'f':
                  while(index < limit) {
                     Array.setFloat(this.data, index, dis.readFloat());
                     ++index;
                  }

                  return index;
               case 'h':
                  while(index < limit) {
                     Array.setShort(this.data, index, dis.readShort());
                     ++index;
                  }

                  return index;
               case 'i':
                  while(index < limit) {
                     Array.setInt(this.data, index, dis.readInt());
                     ++index;
                  }

                  return index;
               case 'l':
                  while(index < limit) {
                     Array.setLong(this.data, index, dis.readLong());
                     ++index;
                  }

                  return index;
               case 'u':
                  while(index < limit) {
                     Array.setInt(this.data, index, dis.readInt());
                     ++index;
                  }

                  return index;
               case 'z':
                  while(index < limit) {
                     Array.setBoolean(this.data, index, dis.readBoolean());
                     ++index;
                  }
            }
         }
      } catch (EOFException var7) {
         if (eofIsError) {
            throw var7;
         }
      }

      return index;
   }

   public void fromstring(PyObject input) {
      this.array_fromstring(input);
   }

   public void fromstring(String input) {
      this.frombytesInternal(StringUtil.toBytes(input));
   }

   final void array_fromstring(PyObject input) {
      String s;
      if (!(input instanceof BufferProtocol)) {
         s = "must be string or read-only buffer, not %s";
         throw Py.TypeError(String.format(s, input.getType().fastGetName()));
      } else {
         if (input instanceof PyUnicode) {
            s = ((PyUnicode)input).encode();
            this.frombytesInternal(StringUtil.toBytes(s));
         } else {
            PyBuffer pybuf = ((BufferProtocol)input).getBuffer(24);
            Throwable var3 = null;

            try {
               if (pybuf.getNdim() != 1) {
                  throw Py.ValueError("multi-dimensional buffer not supported");
               }

               if (pybuf.getStrides()[0] == 1) {
                  this.frombytesInternal(pybuf.getNIOByteBuffer());
               } else {
                  byte[] copy = new byte[pybuf.getLen()];
                  pybuf.copyTo(copy, 0);
                  this.frombytesInternal(ByteBuffer.wrap(copy));
               }
            } catch (Throwable var12) {
               var3 = var12;
               throw var12;
            } finally {
               if (pybuf != null) {
                  if (var3 != null) {
                     try {
                        pybuf.close();
                     } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                     }
                  } else {
                     pybuf.close();
                  }
               }

            }
         }

      }
   }

   private final void frombytesInternal(byte[] bytes) {
      this.frombytesInternal(ByteBuffer.wrap(bytes));
   }

   private final void frombytesInternal(ByteBuffer bytes) {
      int origsize = this.delegate.getSize();
      int itemsize = this.getStorageSize();
      int count = bytes.remaining();
      if (count % itemsize != 0) {
         throw Py.ValueError("string length not a multiple of item size");
      } else {
         this.resizeCheck();

         try {
            InputStream is = new ByteBufferBackedInputStream(bytes);
            this.fromStream(is);
         } catch (EOFException var6) {
            throw Py.EOFError("not enough items in string");
         } catch (IOException var7) {
            this.delegate.setSize(origsize);
            throw Py.IOError(var7);
         }
      }
   }

   public void fromunicode(PyUnicode input) {
      this.array_fromunicode(input);
   }

   final void array_fromunicode(PyObject input) {
      if (!(input instanceof PyUnicode)) {
         throw Py.ValueError("fromunicode argument must be an unicode object");
      } else if (!"u".equals(this.typecode)) {
         throw Py.ValueError("fromunicode() may only be called on type 'u' arrays");
      } else {
         this.extend(input);
      }
   }

   protected PyObject pyget(int i) {
      return (PyObject)("u".equals(this.typecode) ? new PyUnicode(Array.getInt(this.data, i)) : Py.java2py(Array.get(this.data, i)));
   }

   public Object getArray() throws PyIgnoreMethodTag {
      return this.delegate.copyArray();
   }

   public int getItemsize() {
      if (this.type.isPrimitive()) {
         if (this.type == Boolean.TYPE) {
            return 1;
         }

         if (this.type == Byte.TYPE) {
            return 1;
         }

         if (this.type == Character.TYPE) {
            return 1;
         }

         if (this.type == Short.TYPE) {
            return 2;
         }

         if (this.type == Integer.TYPE) {
            return 4;
         }

         if (this.type == Long.TYPE) {
            return 8;
         }

         if (this.type == Float.TYPE) {
            return 4;
         }

         if (this.type == Double.TYPE) {
            return 8;
         }
      }

      return 0;
   }

   public int getStorageSize() {
      if (this.type.isPrimitive()) {
         switch (this.typecode.charAt(0)) {
            case 'B':
               return 1;
            case 'H':
               return 2;
            case 'I':
               return 4;
            case 'L':
               return 8;
            case 'b':
               return 1;
            case 'c':
               return 1;
            case 'd':
               return 8;
            case 'f':
               return 4;
            case 'h':
               return 2;
            case 'i':
               return 4;
            case 'l':
               return 8;
            case 'u':
               return 4;
            case 'z':
               return 1;
            default:
               throw Py.ValueError("bad typecode (must be c, b, B, u, h, H, i, I, l, L, f or d)");
         }
      } else {
         return 0;
      }
   }

   protected PyObject getslice(int start, int stop, int step) {
      if (step > 0 && stop < start) {
         stop = start;
      }

      int n = sliceLength(start, stop, (long)step);
      PyArray ret = new PyArray(this.type, n);
      ret.typecode = this.typecode;
      if (step == 1) {
         System.arraycopy(this.data, start, ret.data, 0, n);
         return ret;
      } else {
         int i = start;

         for(int j = 0; j < n; ++j) {
            Array.set(ret.data, j, Array.get(this.data, i));
            i += step;
         }

         return ret;
      }
   }

   public String getTypecode() {
      return this.typecode;
   }

   public final int array_index(PyObject value) {
      int index = this.indexInternal(value);
      if (index != -1) {
         return index;
      } else {
         throw Py.ValueError("array.index(" + value + "): " + value + " not found in array");
      }
   }

   public PyObject index(PyObject value) {
      return Py.newInteger(this.array_index(value));
   }

   private int indexInternal(PyObject value) {
      int len = this.delegate.getSize();
      int codepoint;
      if ("u".equals(this.typecode)) {
         codepoint = getCodePointOrInt(value);

         for(int i = 0; i < len; ++i) {
            if (codepoint == Array.getInt(this.data, i)) {
               return i;
            }
         }
      } else {
         for(codepoint = 0; codepoint < len; ++codepoint) {
            if (value.equals(Py.java2py(Array.get(this.data, codepoint)))) {
               return codepoint;
            }
         }
      }

      return -1;
   }

   public final void array_insert(int index, PyObject value) {
      this.insert(index, value);
   }

   public void insert(int index, PyObject value) {
      this.resizeCheck();
      index = this.boundToSequence(index);
      if ("u".equals(this.typecode)) {
         int codepoint = getCodePoint(value);
         this.delegate.makeInsertSpace(index);
         Array.setInt(this.data, index, codepoint);
      } else {
         this.delegate.makeInsertSpace(index);
         Array.set(this.data, index, Py.tojava(value, this.type));
      }

   }

   public final PyObject array_pop(int i) {
      PyObject val = this.pop(i);
      return (PyObject)("u".equals(this.typecode) ? new PyUnicode(val.asInt()) : val);
   }

   public PyObject pop() {
      return this.pop(-1);
   }

   public PyObject pop(int index) {
      if (this.delegate.getSize() == 0) {
         throw Py.IndexError("pop from empty array");
      } else {
         index = this.delegator.fixindex(index);
         if (index == -1) {
            throw Py.IndexError("pop index out of range");
         } else {
            this.resizeCheck();
            PyObject ret = Py.java2py(Array.get(this.data, index));
            this.delegate.remove(index);
            return ret;
         }
      }
   }

   public final void array_remove(PyObject value) {
      this.remove(value);
   }

   public void remove(PyObject value) {
      int index = this.indexInternal(value);
      if (index != -1) {
         this.resizeCheck();
         this.delegate.remove(index);
      } else {
         throw Py.ValueError("array.remove(" + value + "): " + value + " not found in array");
      }
   }

   protected PyObject repeat(int count) {
      Object arraycopy = this.delegate.copyArray();
      PyArray ret = new PyArray(this.type, 0);
      ret.typecode = this.typecode;

      for(int i = 0; i < count; ++i) {
         ret.delegate.appendArray(arraycopy);
      }

      return ret;
   }

   public final void array_reverse() {
      this.reverse();
   }

   public void reverse() {
      Object array = Array.newInstance(this.type, Array.getLength(this.data));
      int i = 0;

      for(int lastIndex = this.delegate.getSize() - 1; i <= lastIndex; ++i) {
         Array.set(array, lastIndex - i, Array.get(this.data, i));
      }

      this.data = array;
   }

   public void set(int i, PyObject value) {
      this.pyset(i, value);
   }

   protected void pyset(int i, PyObject value) {
      if ("u".equals(this.typecode)) {
         Array.setInt(this.data, i, getCodePoint(value));
      } else {
         long val;
         Object o;
         if (this.type == Byte.TYPE) {
            try {
               val = (Long)value.__tojava__(Long.TYPE);
            } catch (ClassCastException var11) {
               throw Py.TypeError("Type not compatible with array type");
            }

            if (val < (long)(this.isSigned() ? 0 : -128)) {
               throw Py.OverflowError("value too small for " + this.type.getName());
            }

            if (val > 127L) {
               throw Py.OverflowError("value too large for " + this.type.getName());
            }
         } else if (this.type == Short.TYPE) {
            try {
               val = (Long)value.__tojava__(Long.TYPE);
            } catch (ClassCastException var10) {
               throw Py.TypeError("Type not compatible with array type");
            }

            if (val < (long)(this.isSigned() ? 0 : Short.MIN_VALUE)) {
               throw Py.OverflowError("value too small for " + this.type.getName());
            }

            if (val > 32767L) {
               throw Py.OverflowError("value too large for " + this.type.getName());
            }
         } else if (this.type == Integer.TYPE) {
            try {
               val = (Long)value.__tojava__(Long.TYPE);
            } catch (ClassCastException var9) {
               throw Py.TypeError("Type not compatible with array type");
            }

            if (val < (long)(this.isSigned() ? 0 : Integer.MIN_VALUE)) {
               throw Py.OverflowError("value too small for " + this.type.getName());
            }

            if (val > 2147483647L) {
               throw Py.OverflowError("value too large for " + this.type.getName());
            }
         } else if (this.type == Long.TYPE) {
            if (this.isSigned() && value instanceof PyInteger) {
               if (((PyInteger)value).getValue() < 0) {
                  throw Py.OverflowError("value too small for " + this.type.getName());
               }
            } else if (value instanceof PyLong) {
               ((PyLong)value).getLong(this.isSigned() ? 0L : Long.MIN_VALUE, Long.MAX_VALUE);
            } else {
               try {
                  o = value.__tojava__(Long.TYPE);
               } catch (ClassCastException var8) {
                  throw Py.TypeError("Type not compatible with array type");
               }

               if (o == Py.NoConversion) {
                  throw Py.TypeError("Type not compatible with array type");
               }
            }
         }

         o = Py.tojava(value, this.type);
         if (o == Py.NoConversion) {
            throw Py.TypeError("Type not compatible with array type");
         } else {
            Array.set(this.data, i, o);
         }
      }
   }

   public void set(int i, int value) {
      if (!"u".equals(this.typecode) && this.type != Integer.TYPE && this.type != Long.TYPE) {
         throw Py.TypeError("Type not compatible with array type");
      } else {
         Array.setInt(this.data, i, value);
      }
   }

   public void set(int i, char value) {
      if (!"c".equals(this.typecode) && this.type != Integer.TYPE && this.type != Long.TYPE) {
         throw Py.TypeError("Type not compatible with array type");
      } else {
         Array.setChar(this.data, i, value);
      }
   }

   private boolean isSigned() {
      return this.typecode.length() == 1 && this.typecode.equals(this.typecode.toUpperCase());
   }

   protected void setslice(int start, int stop, int step, PyObject value) {
      if (stop < start) {
         stop = start;
      }

      PyArray array;
      if (this.type == Character.TYPE && value instanceof PyString) {
         array = null;
         if (step != 1) {
            throw Py.ValueError("invalid bounds for setting from string");
         }

         char[] chars = value.toString().toCharArray();
         if (start + chars.length != stop) {
            this.resizeCheck();
         }

         this.delegate.replaceSubArray(start, stop, chars, 0, chars.length);
      } else if (value instanceof PyString && this.type == Byte.TYPE) {
         byte[] chars = ((PyString)value).toBytes();
         if (chars.length != stop - start || step != 1) {
            throw Py.ValueError("invalid bounds for setting from string");
         }

         System.arraycopy(chars, 0, this.data, start, chars.length);
      } else {
         if (!(value instanceof PyArray)) {
            throw Py.TypeError(String.format("can only assign array (not \"%.200s\") to array slice", value.getType().fastGetName()));
         }

         array = (PyArray)value;
         if (!array.typecode.equals(this.typecode)) {
            throw Py.TypeError("bad argument type for built-in operation|" + array.typecode + "|" + this.typecode);
         }

         int i;
         if (step == 1) {
            Object arrayDelegate;
            if (array == this) {
               arrayDelegate = array.delegate.copyArray();
            } else {
               arrayDelegate = array.delegate.getArray();
            }

            i = array.delegate.getSize();
            if (start + i != stop) {
               this.resizeCheck();
            }

            try {
               this.delegate.replaceSubArray(start, stop, arrayDelegate, 0, i);
            } catch (IllegalArgumentException var9) {
               throw Py.TypeError("Slice typecode '" + array.typecode + "' is not compatible with this array (typecode '" + this.typecode + "')");
            }
         } else {
            int j;
            int len;
            if (step > 1) {
               len = array.__len__();
               i = 0;

               for(j = 0; i < len; j += step) {
                  Array.set(this.data, j + start, Array.get(array.data, i));
                  ++i;
               }
            } else if (step < 0) {
               if (array == this) {
                  array = (PyArray)array.clone();
               }

               len = array.__len__();
               i = 0;

               for(j = start; i < len; j += step) {
                  Array.set(this.data, j, Array.get(array.data, i));
                  ++i;
               }
            }
         }
      }

   }

   public final void array_tofile(PyObject f) {
      this.tofile(f);
   }

   public void array_write(PyObject f) {
      this.tofile(f);
   }

   public void tofile(PyObject f) {
      if (!(f instanceof PyFile)) {
         throw Py.TypeError("arg must be open file");
      } else {
         PyFile file = (PyFile)f;
         file.write(this.tostring());
      }
   }

   public final PyObject array_tolist() {
      return this.tolist();
   }

   public PyObject tolist() {
      PyList list = new PyList();
      int len = this.delegate.getSize();
      int i;
      if ("u".equals(this.typecode)) {
         for(i = 0; i < len; ++i) {
            list.append(new PyUnicode(Array.getInt(this.data, i)));
         }
      } else {
         for(i = 0; i < len; ++i) {
            list.append(Py.java2py(Array.get(this.data, i)));
         }
      }

      return list;
   }

   public int toStream(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      int i;
      switch (this.typecode.charAt(0)) {
         case 'B':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeByte(signedByte(Array.getShort(this.data, i)));
            }

            return dos.size();
         case 'H':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeShort(signedShort(Array.getInt(this.data, i)));
            }

            return dos.size();
         case 'I':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeInt(signedInt(Array.getLong(this.data, i)));
            }

            return dos.size();
         case 'L':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeLong(Array.getLong(this.data, i));
            }

            return dos.size();
         case 'b':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeByte(Array.getByte(this.data, i));
            }

            return dos.size();
         case 'c':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeByte((byte)Array.getChar(this.data, i));
            }

            return dos.size();
         case 'd':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeDouble(Array.getDouble(this.data, i));
            }

            return dos.size();
         case 'f':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeFloat(Array.getFloat(this.data, i));
            }

            return dos.size();
         case 'h':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeShort(Array.getShort(this.data, i));
            }

            return dos.size();
         case 'i':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeInt(Array.getInt(this.data, i));
            }

            return dos.size();
         case 'l':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeLong(Array.getLong(this.data, i));
            }

            return dos.size();
         case 'u':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeInt(Array.getInt(this.data, i));
            }

            return dos.size();
         case 'z':
            for(i = 0; i < this.delegate.getSize(); ++i) {
               dos.writeBoolean(Array.getBoolean(this.data, i));
            }
      }

      return dos.size();
   }

   private static byte signedByte(short x) {
      if (x >= 128 && x < 256) {
         return (byte)(x - 256);
      } else if (x >= 0) {
         return (byte)x;
      } else {
         throw Py.ValueError("invalid storage");
      }
   }

   private static short signedShort(int x) {
      if (x >= 32768 && x < 65536) {
         return (short)(x - 65536);
      } else if (x >= 0) {
         return (short)x;
      } else {
         throw Py.ValueError("invalid storage");
      }
   }

   private static int signedInt(long x) {
      if (x >= 2147483648L && x < 4294967296L) {
         return (int)(x - 4294967296L);
      } else if (x >= 0L) {
         return (int)x;
      } else {
         throw Py.ValueError("invalid storage");
      }
   }

   private static short unsignedByte(byte x) {
      return x < 0 ? (short)(x + 256) : (short)x;
   }

   private static int unsignedShort(short x) {
      return x < 0 ? x + 65536 : x;
   }

   private static long unsignedInt(int x) {
      return x < 0 ? (long)x + 4294967296L : (long)x;
   }

   public final PyObject array_tostring() {
      return new PyString(this.tostring());
   }

   public String tostring() {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      try {
         this.toStream(bos);
      } catch (IOException var3) {
         throw Py.IOError(var3);
      }

      return StringUtil.fromBytes(bos.toByteArray());
   }

   public String tounicode() {
      if (!"u".equals(this.typecode)) {
         throw Py.ValueError("tounicode() may only be called on type 'u' arrays");
      } else {
         int len = this.delegate.getSize();
         int[] codepoints = new int[len];

         for(int i = 0; i < len; ++i) {
            codepoints[i] = Array.getInt(this.data, i);
         }

         return new String(codepoints, 0, codepoints.length);
      }
   }

   public final PyObject array_tounicode() {
      return new PyUnicode(this.tounicode());
   }

   public synchronized PyBuffer getBuffer(int flags) {
      BaseBuffer pybuf = this.getExistingBuffer(flags);
      if (pybuf == null) {
         if ("b".equals(this.typecode)) {
            byte[] storage = (byte[])((byte[])this.data);
            int size = this.delegate.getSize();
            pybuf = new SimpleWritableBuffer(flags, this, storage, 0, size);
         } else {
            if ((flags & 1) != 0) {
               throw Py.NotImplementedError("only array('b') can export a writable buffer");
            }

            pybuf = new SimpleStringBuffer(flags, this, this.tostring());
         }

         this.export = new WeakReference(pybuf);
      }

      return (PyBuffer)pybuf;
   }

   private BaseBuffer getExistingBuffer(int flags) throws PyException {
      BaseBuffer pybuf = null;
      if (this.export != null) {
         pybuf = (BaseBuffer)this.export.get();
         if (pybuf != null) {
            pybuf = pybuf.getBufferAgain(flags);
         }
      }

      return pybuf;
   }

   private void resizeCheck() throws PyException {
      if (this.export != null) {
         PyBuffer pybuf = (PyBuffer)this.export.get();
         if (pybuf != null && !pybuf.isReleased()) {
            throw Py.BufferError("cannot resize an array that is exporting buffers");
         }

         this.export = null;
      }

   }

   public int traverse(Visitproc visit, Object arg) {
      return this.data != null && gc.canLinkToPyObject(this.data.getClass(), true) ? gc.traverseByReflection(this.data, visit, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
      if (this.data != null && gc.canLinkToPyObject(this.data.getClass(), true)) {
         throw new UnsupportedOperationException();
      } else {
         return false;
      }
   }

   static {
      PyType.addBuilder(PyArray.class, new PyExposer());
      TYPE = PyType.fromClass(PyArray.class);
   }

   private class ByteBufferBackedInputStream extends InputStream {
      ByteBuffer buf;

      public ByteBufferBackedInputStream(ByteBuffer buf) {
         this.buf = buf;
      }

      public int available() throws IOException {
         return this.buf.remaining();
      }

      public int read() {
         return this.buf.hasRemaining() ? this.buf.get() & 255 : -1;
      }

      public int read(byte[] bytes, int off, int len) {
         int n = this.buf.remaining();
         if (n >= len) {
            this.buf.get(bytes, off, len);
            return len;
         } else if (n > 0) {
            this.buf.get(bytes, off, n);
            return n;
         } else {
            return -1;
         }
      }
   }

   private class ArrayDelegate extends AbstractArray {
      private ArrayDelegate() {
         super(PyArray.this.data == null ? 0 : Array.getLength(PyArray.this.data));
      }

      protected Object getArray() {
         return PyArray.this.data;
      }

      protected void setArray(Object array) {
         PyArray.this.data = array;
      }

      protected Object createArray(int size) {
         Class baseType = PyArray.this.data.getClass().getComponentType();
         return Array.newInstance(baseType, size);
      }

      // $FF: synthetic method
      ArrayDelegate(Object x1) {
         this();
      }
   }

   private static class array___ne___exposer extends PyBuiltinMethodNarrow {
      public array___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyArray)this.self).array___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class array___eq___exposer extends PyBuiltinMethodNarrow {
      public array___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyArray)this.self).array___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class array___hash___exposer extends PyBuiltinMethodNarrow {
      public array___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public array___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyArray)this.self).array___hash__());
      }
   }

   private static class array___lt___exposer extends PyBuiltinMethodNarrow {
      public array___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyArray)this.self).array___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class array___le___exposer extends PyBuiltinMethodNarrow {
      public array___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyArray)this.self).array___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class array___gt___exposer extends PyBuiltinMethodNarrow {
      public array___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyArray)this.self).array___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class array___ge___exposer extends PyBuiltinMethodNarrow {
      public array___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyArray)this.self).array___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class array___contains___exposer extends PyBuiltinMethodNarrow {
      public array___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyArray)this.self).array___contains__(var1));
      }
   }

   private static class array___delitem___exposer extends PyBuiltinMethodNarrow {
      public array___delitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___delitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___delitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyArray)this.self).array___delitem__(var1);
         return Py.None;
      }
   }

   private static class array___setitem___exposer extends PyBuiltinMethodNarrow {
      public array___setitem___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public array___setitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___setitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyArray)this.self).array___setitem__(var1, var2);
         return Py.None;
      }
   }

   private static class array___getitem___exposer extends PyBuiltinMethodNarrow {
      public array___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyArray)this.self).array___getitem__(var1);
      }
   }

   private static class array___nonzero___exposer extends PyBuiltinMethodNarrow {
      public array___nonzero___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public array___nonzero___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___nonzero___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyArray)this.self).array___nonzero__());
      }
   }

   private static class array___iter___exposer extends PyBuiltinMethodNarrow {
      public array___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public array___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyArray)this.self).array___iter__();
      }
   }

   private static class array___getslice___exposer extends PyBuiltinMethodNarrow {
      public array___getslice___exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "";
      }

      public array___getslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___getslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return ((PyArray)this.self).array___getslice__(var1, var2, var3);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyArray)this.self).array___getslice__(var1, var2, (PyObject)null);
      }
   }

   private static class array___setslice___exposer extends PyBuiltinMethodNarrow {
      public array___setslice___exposer(String var1) {
         super(var1, 4, 5);
         super.doc = "";
      }

      public array___setslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___setslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3, PyObject var4) {
         ((PyArray)this.self).array___setslice__(var1, var2, var3, var4);
         return Py.None;
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         ((PyArray)this.self).array___setslice__(var1, var2, var3, (PyObject)null);
         return Py.None;
      }
   }

   private static class array___delslice___exposer extends PyBuiltinMethodNarrow {
      public array___delslice___exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "";
      }

      public array___delslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___delslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         ((PyArray)this.self).array___delslice__(var1, var2, var3);
         return Py.None;
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyArray)this.self).array___delslice__(var1, var2, (PyObject)null);
         return Py.None;
      }
   }

   private static class array___imul___exposer extends PyBuiltinMethodNarrow {
      public array___imul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___imul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___imul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyArray)this.self).array___imul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class array___mul___exposer extends PyBuiltinMethodNarrow {
      public array___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyArray)this.self).array___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class array___rmul___exposer extends PyBuiltinMethodNarrow {
      public array___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyArray)this.self).array___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class array___iadd___exposer extends PyBuiltinMethodNarrow {
      public array___iadd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___iadd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___iadd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyArray)this.self).array___iadd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class array___add___exposer extends PyBuiltinMethodNarrow {
      public array___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyArray)this.self).array___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class array___len___exposer extends PyBuiltinMethodNarrow {
      public array___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public array___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyArray)this.self).array___len__());
      }
   }

   private static class array___reduce___exposer extends PyBuiltinMethodNarrow {
      public array___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public array___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyArray)this.self).array___reduce__();
      }
   }

   private static class array_append_exposer extends PyBuiltinMethodNarrow {
      public array_append_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array_append_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_append_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyArray)this.self).array_append(var1);
         return Py.None;
      }
   }

   private static class array_byteswap_exposer extends PyBuiltinMethodNarrow {
      public array_byteswap_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public array_byteswap_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_byteswap_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyArray)this.self).array_byteswap();
         return Py.None;
      }
   }

   private static class array_count_exposer extends PyBuiltinMethodNarrow {
      public array_count_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array_count_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_count_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyArray)this.self).array_count(var1));
      }
   }

   private static class array_extend_exposer extends PyBuiltinMethodNarrow {
      public array_extend_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array_extend_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_extend_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyArray)this.self).array_extend(var1);
         return Py.None;
      }
   }

   private static class array_fromfile_exposer extends PyBuiltinMethodNarrow {
      public array_fromfile_exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public array_fromfile_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_fromfile_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyArray)this.self).array_fromfile(var1, Py.py2int(var2));
         return Py.None;
      }
   }

   private static class array_fromlist_exposer extends PyBuiltinMethodNarrow {
      public array_fromlist_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array_fromlist_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_fromlist_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyArray)this.self).array_fromlist(var1);
         return Py.None;
      }
   }

   private static class array_fromstring_exposer extends PyBuiltinMethodNarrow {
      public array_fromstring_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array_fromstring_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_fromstring_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyArray)this.self).array_fromstring(var1);
         return Py.None;
      }
   }

   private static class array_fromunicode_exposer extends PyBuiltinMethodNarrow {
      public array_fromunicode_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array_fromunicode_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_fromunicode_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyArray)this.self).array_fromunicode(var1);
         return Py.None;
      }
   }

   private static class array_index_exposer extends PyBuiltinMethodNarrow {
      public array_index_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array_index_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_index_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyArray)this.self).array_index(var1));
      }
   }

   private static class array_insert_exposer extends PyBuiltinMethodNarrow {
      public array_insert_exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public array_insert_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_insert_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyArray)this.self).array_insert(Py.py2int(var1), var2);
         return Py.None;
      }
   }

   private static class array_pop_exposer extends PyBuiltinMethodNarrow {
      public array_pop_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public array_pop_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_pop_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyArray)this.self).array_pop(Py.py2int(var1));
      }

      public PyObject __call__() {
         return ((PyArray)this.self).array_pop(-1);
      }
   }

   private static class array_remove_exposer extends PyBuiltinMethodNarrow {
      public array_remove_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array_remove_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_remove_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyArray)this.self).array_remove(var1);
         return Py.None;
      }
   }

   private static class array_reverse_exposer extends PyBuiltinMethodNarrow {
      public array_reverse_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public array_reverse_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_reverse_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyArray)this.self).array_reverse();
         return Py.None;
      }
   }

   private static class array_tofile_exposer extends PyBuiltinMethodNarrow {
      public array_tofile_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array_tofile_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_tofile_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyArray)this.self).array_tofile(var1);
         return Py.None;
      }
   }

   private static class array_write_exposer extends PyBuiltinMethodNarrow {
      public array_write_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public array_write_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_write_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyArray)this.self).array_write(var1);
         return Py.None;
      }
   }

   private static class array_tolist_exposer extends PyBuiltinMethodNarrow {
      public array_tolist_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public array_tolist_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_tolist_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyArray)this.self).array_tolist();
      }
   }

   private static class array_tostring_exposer extends PyBuiltinMethodNarrow {
      public array_tostring_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public array_tostring_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_tostring_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyArray)this.self).array_tostring();
      }
   }

   private static class array_tounicode_exposer extends PyBuiltinMethodNarrow {
      public array_tounicode_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public array_tounicode_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new array_tounicode_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyArray)this.self).array_tounicode();
      }
   }

   private static class typecode_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public typecode_descriptor() {
         super("typecode", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyArray)var1).getTypecode();
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

   private static class itemsize_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public itemsize_descriptor() {
         super("itemsize", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyArray)var1).getItemsize());
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
         return PyArray.array_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new array___ne___exposer("__ne__"), new array___eq___exposer("__eq__"), new array___hash___exposer("__hash__"), new array___lt___exposer("__lt__"), new array___le___exposer("__le__"), new array___gt___exposer("__gt__"), new array___ge___exposer("__ge__"), new array___contains___exposer("__contains__"), new array___delitem___exposer("__delitem__"), new array___setitem___exposer("__setitem__"), new array___getitem___exposer("__getitem__"), new array___nonzero___exposer("__nonzero__"), new array___iter___exposer("__iter__"), new array___getslice___exposer("__getslice__"), new array___setslice___exposer("__setslice__"), new array___delslice___exposer("__delslice__"), new array___imul___exposer("__imul__"), new array___mul___exposer("__mul__"), new array___rmul___exposer("__rmul__"), new array___iadd___exposer("__iadd__"), new array___add___exposer("__add__"), new array___len___exposer("__len__"), new array___reduce___exposer("__reduce__"), new array_append_exposer("append"), new array_byteswap_exposer("byteswap"), new array_count_exposer("count"), new array_extend_exposer("extend"), new array_fromfile_exposer("fromfile"), new array_fromlist_exposer("fromlist"), new array_fromstring_exposer("fromstring"), new array_fromunicode_exposer("fromunicode"), new array_index_exposer("index"), new array_insert_exposer("insert"), new array_pop_exposer("pop"), new array_remove_exposer("remove"), new array_reverse_exposer("reverse"), new array_tofile_exposer("tofile"), new array_write_exposer("write"), new array_tolist_exposer("tolist"), new array_tostring_exposer("tostring"), new array_tounicode_exposer("tounicode")};
         PyDataDescr[] var2 = new PyDataDescr[]{new typecode_descriptor(), new itemsize_descriptor()};
         super("array.array", PyArray.class, PyObject.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
