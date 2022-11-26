package org.python.core;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@Untraversable
public abstract class BaseBytes extends PySequence implements List {
   protected static final byte[] emptyStorage = new byte[0];
   protected byte[] storage;
   protected int size;
   protected int offset;
   private static PyString PICKLE_ENCODING;
   private static final int SWAP_CASE = 32;
   private static final byte UPPER = 1;
   private static final byte LOWER = 2;
   private static final byte DIGIT = 4;
   private static final byte SPACE = 8;
   private static final byte ALPHA = 3;
   private static final byte ALNUM = 7;
   private static final byte[] ctype = new byte[256];
   protected final List listDelegate;

   public BaseBytes(PyType type) {
      super(type, (SequenceIndexDelegate)null);
      this.storage = emptyStorage;
      this.size = 0;
      this.offset = 0;
      this.listDelegate = new AbstractList() {
         public PyInteger get(int index) {
            BaseBytes.this.indexCheck(index);
            return BaseBytes.this.pyget(index);
         }

         public int size() {
            return BaseBytes.this.size;
         }

         public PyInteger set(int index, PyInteger element) throws PyException {
            BaseBytes.this.indexCheck(index);
            PyInteger result = BaseBytes.this.pyget(index);
            BaseBytes.this.pyset(index, element);
            return result;
         }

         public void add(int index, PyInteger element) throws PyException {
            BaseBytes.this.indexCheck(index);
            BaseBytes.this.pyinsert(index, element);
         }

         public PyInteger remove(int index) {
            BaseBytes.this.indexCheck(index);
            PyInteger result = BaseBytes.this.pyget(index);
            BaseBytes.this.del(index);
            return result;
         }
      };
      this.delegator = new IndexDelegate();
      this.setStorage(emptyStorage);
   }

   public BaseBytes(PyType type, int size) {
      super(type, (SequenceIndexDelegate)null);
      this.storage = emptyStorage;
      this.size = 0;
      this.offset = 0;
      this.listDelegate = new AbstractList() {
         public PyInteger get(int index) {
            BaseBytes.this.indexCheck(index);
            return BaseBytes.this.pyget(index);
         }

         public int size() {
            return BaseBytes.this.size;
         }

         public PyInteger set(int index, PyInteger element) throws PyException {
            BaseBytes.this.indexCheck(index);
            PyInteger result = BaseBytes.this.pyget(index);
            BaseBytes.this.pyset(index, element);
            return result;
         }

         public void add(int index, PyInteger element) throws PyException {
            BaseBytes.this.indexCheck(index);
            BaseBytes.this.pyinsert(index, element);
         }

         public PyInteger remove(int index) {
            BaseBytes.this.indexCheck(index);
            PyInteger result = BaseBytes.this.pyget(index);
            BaseBytes.this.del(index);
            return result;
         }
      };
      this.delegator = new IndexDelegate();
      this.newStorage(size);
   }

   public BaseBytes(PyType type, int[] value) {
      super(type, (SequenceIndexDelegate)null);
      this.storage = emptyStorage;
      this.size = 0;
      this.offset = 0;
      this.listDelegate = new AbstractList() {
         public PyInteger get(int index) {
            BaseBytes.this.indexCheck(index);
            return BaseBytes.this.pyget(index);
         }

         public int size() {
            return BaseBytes.this.size;
         }

         public PyInteger set(int index, PyInteger element) throws PyException {
            BaseBytes.this.indexCheck(index);
            PyInteger result = BaseBytes.this.pyget(index);
            BaseBytes.this.pyset(index, element);
            return result;
         }

         public void add(int index, PyInteger element) throws PyException {
            BaseBytes.this.indexCheck(index);
            BaseBytes.this.pyinsert(index, element);
         }

         public PyInteger remove(int index) {
            BaseBytes.this.indexCheck(index);
            PyInteger result = BaseBytes.this.pyget(index);
            BaseBytes.this.del(index);
            return result;
         }
      };
      this.delegator = new IndexDelegate();
      int n = value.length;
      this.newStorage(n);
      int i = this.offset;

      for(int j = 0; j < n; ++j) {
         this.storage[i] = byteCheck(value[j]);
         ++i;
      }

   }

   protected BaseBytes(PyType type, String value) throws PyException {
      super(type, (SequenceIndexDelegate)null);
      this.storage = emptyStorage;
      this.size = 0;
      this.offset = 0;
      this.listDelegate = new AbstractList() {
         public PyInteger get(int index) {
            BaseBytes.this.indexCheck(index);
            return BaseBytes.this.pyget(index);
         }

         public int size() {
            return BaseBytes.this.size;
         }

         public PyInteger set(int index, PyInteger element) throws PyException {
            BaseBytes.this.indexCheck(index);
            PyInteger result = BaseBytes.this.pyget(index);
            BaseBytes.this.pyset(index, element);
            return result;
         }

         public void add(int index, PyInteger element) throws PyException {
            BaseBytes.this.indexCheck(index);
            BaseBytes.this.pyinsert(index, element);
         }

         public PyInteger remove(int index) {
            BaseBytes.this.indexCheck(index);
            PyInteger result = BaseBytes.this.pyget(index);
            BaseBytes.this.del(index);
            return result;
         }
      };
      this.delegator = new IndexDelegate();
      int n = value.length();
      this.newStorage(n);
      int i = this.offset;

      for(int j = 0; j < n; ++j) {
         this.storage[i++] = byteCheck(value.charAt(j));
      }

   }

   protected void setStorage(byte[] storage, int size, int offset) throws IllegalArgumentException {
      if (size >= 0 && offset >= 0 && offset + size <= storage.length) {
         this.storage = storage;
         this.size = size;
         this.offset = offset;
      } else {
         throw new IllegalArgumentException();
      }
   }

   protected void setStorage(byte[] storage, int size) throws IllegalArgumentException {
      if (size >= 0 && size <= storage.length) {
         this.storage = storage;
         this.size = size;
         this.offset = 0;
      } else {
         throw new IllegalArgumentException();
      }
   }

   protected void setStorage(byte[] storage) {
      this.storage = storage;
      this.size = storage.length;
      this.offset = 0;
   }

   protected void init(PyObject arg) {
      if (arg == null) {
         this.setStorage(emptyStorage);
      } else if (arg instanceof PyString) {
         this.init((PyString)arg, (String)null, (String)null);
      } else if (arg.isIndex()) {
         this.init(arg.asIndex(Py.OverflowError));
      } else if (arg instanceof BaseBytes) {
         this.init((BaseBytes)arg);
      } else if (arg instanceof BufferProtocol) {
         this.init((BufferProtocol)arg);
      } else {
         this.init(arg.asIterable());
      }

   }

   protected void init(PyString arg, PyObject encoding, PyObject errors) {
      String enc = encoding == null ? null : encoding.asString();
      String err = errors == null ? null : errors.asString();
      this.init(arg, enc, err);
   }

   protected void init(PyString arg, String encoding, String errors) {
      String encoded = encode(arg, encoding, errors);
      this.newStorage(encoded.length());
      this.setBytes(0, encoded);
   }

   protected static String encode(PyString arg, String encoding, String errors) throws PyException {
      String encoded;
      if (arg instanceof PyUnicode) {
         if (encoding == null) {
            throw Py.TypeError("unicode argument without an encoding");
         }

         encoded = codecs.encode(arg, encoding, errors);
      } else if (encoding != null) {
         encoded = codecs.encode(arg, encoding, errors);
      } else {
         encoded = arg.getString();
      }

      return encoded;
   }

   protected void setBytes(int start, String value) throws PyException {
      int n = value.length();
      int io = this.offset + start;

      for(int j = 0; j < n; ++j) {
         this.storage[io++] = byteCheck(value.charAt(j));
      }

   }

   protected void setBytes(int start, int step, String value) throws PyException {
      int n = value.length();
      int io = this.offset + start;

      for(int j = 0; j < n; ++j) {
         this.storage[io] = byteCheck(value.charAt(j));
         io += step;
      }

   }

   protected void init(int n) {
      if (n < 0) {
         throw Py.ValueError("negative count");
      } else {
         this.newStorage(n);
      }
   }

   protected void init(BufferProtocol value) throws PyException {
      PyBuffer view = value.getBuffer(284);
      Throwable var3 = null;

      try {
         this.newStorage(view.getLen());
         view.copyTo(this.storage, this.offset);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (view != null) {
            if (var3 != null) {
               try {
                  view.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               view.close();
            }
         }

      }

   }

   protected void init(BaseBytes source) {
      this.newStorage(source.size);
      System.arraycopy(source.storage, source.offset, this.storage, this.offset, this.size);
   }

   protected void init(Iterable iter) {
      FragmentList fragList = new FragmentList();
      fragList.loadFrom(iter);
      if (fragList.totalCount > 0) {
         if (fragList.size() == 1) {
            Fragment frag = (Fragment)fragList.getFirst();
            this.setStorage(frag.storage, frag.count);
         } else {
            this.newStorage(fragList.totalCount);
            fragList.emptyInto(this.storage, this.offset);
         }
      } else {
         this.setStorage(emptyStorage);
      }

   }

   protected final void indexCheck(int index) throws PyException {
      if (index < 0 || index >= this.size) {
         throw Py.IndexError(this.getType().fastGetName() + " index out of range");
      }
   }

   protected void newStorage(int needed) {
      if (needed > 0) {
         try {
            this.setStorage(new byte[needed]);
         } catch (OutOfMemoryError var3) {
            throw Py.MemoryError(var3.getMessage());
         }
      } else {
         this.setStorage(emptyStorage);
      }

   }

   protected static final byte byteCheck(int value) throws PyException {
      if (value >= 0 && value <= 255) {
         return (byte)value;
      } else {
         throw Py.ValueError("byte must be in range(0, 256)");
      }
   }

   protected static final byte byteCheck(PyInteger value) throws PyException {
      return byteCheck(value.asInt());
   }

   protected static final byte byteCheck(PyObject value) throws PyException {
      if (value.isIndex()) {
         return byteCheck(value.asIndex());
      } else if (value.getType() == PyString.TYPE) {
         String strValue = ((PyString)value).getString();
         if (strValue.length() != 1) {
            throw Py.ValueError("string must be of size 1");
         } else {
            return byteCheck(strValue.charAt(0));
         }
      } else {
         throw Py.TypeError("an integer or string of size 1 is required");
      }
   }

   protected static PyBuffer getView(PyObject b) {
      if (b == null) {
         return null;
      } else if (b instanceof PyUnicode) {
         return null;
      } else {
         return b instanceof BufferProtocol ? ((BufferProtocol)b).getBuffer(284) : null;
      }
   }

   protected static PyBuffer getViewOrError(PyObject b) {
      PyBuffer buffer = getView(b);
      if (buffer != null) {
         return buffer;
      } else {
         String fmt = "Type %s doesn't support the buffer API";
         throw Py.TypeError(String.format(fmt, b.getType().fastGetName()));
      }
   }

   protected PyInteger pyget(int index) {
      return new PyInteger(this.intAt(index));
   }

   protected abstract BaseBytes getslice(int var1, int var2, int var3);

   protected abstract BaseBytes repeat(int var1);

   public void pyinsert(int index, PyObject element) {
      this.pyset(index, element);
   }

   protected BaseBytes getslice(int start, int stop) {
      return this.getslice(start, stop, 1);
   }

   public int __len__() {
      return this.size;
   }

   private static int compare(BaseBytes a, PyBuffer b) {
      int ap = a.offset;
      int aEnd = ap + a.size;
      int bp = 0;
      int bEnd = b.getLen();

      int diff;
      do {
         if (ap >= aEnd) {
            if (bp < bEnd) {
               return -1;
            }

            return 0;
         }

         if (bp >= bEnd) {
            return 1;
         }

         int aVal = 255 & a.storage[ap++];
         int bVal = b.intAt(bp++);
         diff = aVal - bVal;
      } while(diff == 0);

      return diff < 0 ? -1 : 1;
   }

   private synchronized int basebytes_cmp(PyObject b) {
      if (this == b) {
         return 0;
      } else {
         PyBuffer bv = getView(b);
         Throwable var3 = null;

         int var4;
         try {
            if (bv != null) {
               var4 = compare(this, bv);
               return var4;
            }

            var4 = -2;
         } catch (Throwable var14) {
            var3 = var14;
            throw var14;
         } finally {
            if (bv != null) {
               if (var3 != null) {
                  try {
                     bv.close();
                  } catch (Throwable var13) {
                     var3.addSuppressed(var13);
                  }
               } else {
                  bv.close();
               }
            }

         }

         return var4;
      }
   }

   private synchronized int basebytes_cmpeq(PyObject b) {
      if (this == b) {
         return 0;
      } else {
         PyBuffer bv = getView(b);
         Throwable var3 = null;

         int var4;
         try {
            if (bv == null) {
               byte var18 = -2;
               return var18;
            }

            if (bv.getLen() != this.size) {
               byte var17 = 1;
               return var17;
            }

            var4 = compare(this, bv);
         } catch (Throwable var15) {
            var3 = var15;
            throw var15;
         } finally {
            if (bv != null) {
               if (var3 != null) {
                  try {
                     bv.close();
                  } catch (Throwable var14) {
                     var3.addSuppressed(var14);
                  }
               } else {
                  bv.close();
               }
            }

         }

         return var4;
      }
   }

   final PyObject basebytes___eq__(PyObject other) {
      int cmp = this.basebytes_cmpeq(other);
      if (cmp == 0) {
         return Py.True;
      } else {
         return cmp > -2 ? Py.False : null;
      }
   }

   final PyObject basebytes___ne__(PyObject other) {
      int cmp = this.basebytes_cmpeq(other);
      if (cmp == 0) {
         return Py.False;
      } else {
         return cmp > -2 ? Py.True : null;
      }
   }

   final PyObject basebytes___lt__(PyObject other) {
      int cmp = this.basebytes_cmp(other);
      if (cmp >= 0) {
         return Py.False;
      } else {
         return cmp > -2 ? Py.True : null;
      }
   }

   final PyObject basebytes___le__(PyObject other) {
      int cmp = this.basebytes_cmp(other);
      if (cmp > 0) {
         return Py.False;
      } else {
         return cmp > -2 ? Py.True : null;
      }
   }

   final PyObject basebytes___ge__(PyObject other) {
      int cmp = this.basebytes_cmp(other);
      if (cmp >= 0) {
         return Py.True;
      } else {
         return cmp > -2 ? Py.False : null;
      }
   }

   final PyObject basebytes___gt__(PyObject other) {
      int cmp = this.basebytes_cmp(other);
      if (cmp > 0) {
         return Py.True;
      } else {
         return cmp > -2 ? Py.False : null;
      }
   }

   protected final synchronized boolean basebytes___contains__(PyObject target) {
      if (target.isIndex()) {
         byte b = byteCheck(target.asIndex());
         return this.index(b) >= 0;
      } else {
         PyBuffer targetView = getViewOrError(target);
         Throwable var3 = null;

         boolean var5;
         try {
            Finder finder = new Finder(targetView);
            finder.setText(this);
            var5 = finder.nextIndex() >= 0;
         } catch (Throwable var14) {
            var3 = var14;
            throw var14;
         } finally {
            if (targetView != null) {
               if (var3 != null) {
                  try {
                     targetView.close();
                  } catch (Throwable var13) {
                     var3.addSuppressed(var13);
                  }
               } else {
                  targetView.close();
               }
            }

         }

         return var5;
      }
   }

   protected final synchronized boolean basebytes_starts_or_endswith(PyObject target, PyObject ostart, PyObject oend, boolean endswith) {
      int[] index = this.indicesEx(ostart, oend);
      if (target instanceof PyTuple) {
         Iterator var6 = ((PyTuple)target).getList().iterator();

         PyObject t;
         do {
            if (!var6.hasNext()) {
               return false;
            }

            t = (PyObject)var6.next();
         } while(!this.match(t, index[0], index[3], endswith));

         return true;
      } else {
         return this.match(target, index[0], index[3], endswith);
      }
   }

   private boolean match(PyObject target, int pos, int n, boolean endswith) {
      PyBuffer vt = getViewOrError(target);
      Throwable var6 = null;

      try {
         int j = 0;
         int len = vt.getLen();
         boolean var9;
         if (!endswith) {
            if (len > n) {
               var9 = false;
               return var9;
            }
         } else {
            j = n - len;
            if (j < 0) {
               var9 = false;
               return var9;
            }
         }

         j += this.offset + pos;

         for(int i = 0; i < len; ++i) {
            if (this.storage[j++] != vt.byteAt(i)) {
               boolean var10 = false;
               return var10;
            }
         }

         var9 = true;
         return var9;
      } catch (Throwable var22) {
         var6 = var22;
         throw var22;
      } finally {
         if (vt != null) {
            if (var6 != null) {
               try {
                  vt.close();
               } catch (Throwable var21) {
                  var6.addSuppressed(var21);
               }
            } else {
               vt.close();
            }
         }

      }
   }

   private int[] indicesEx(PyObject ostart, PyObject oend) {
      PySlice s = new PySlice(ostart, oend, (PyObject)null);
      return s.indicesEx(this.size);
   }

   public synchronized String asString() {
      char[] buf = new char[this.size];
      int j = this.offset + this.size;
      int i = this.size;

      while(true) {
         --i;
         if (i < 0) {
            return new String(buf);
         }

         --j;
         buf[i] = (char)(255 & this.storage[j]);
      }
   }

   public PyObject decode() {
      return this.decode((String)null, (String)null);
   }

   public PyObject decode(String encoding) {
      return this.decode(encoding, (String)null);
   }

   public PyObject decode(String encoding, String errors) {
      PyString this_ = new PyString(this.asString());
      return codecs.decode(this_, encoding, errors);
   }

   protected final PyObject basebytes_decode(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("decode", args, keywords, "encoding", "errors");
      String encoding = ap.getString(0, (String)null);
      String errors = ap.getString(1, (String)null);
      return this.decode(encoding, errors);
   }

   static PyException ConcatenationTypeError(PyType type, PyType toType) {
      String fmt = "can't concat %s to %s";
      return Py.TypeError(String.format(fmt, type.fastGetName(), toType.fastGetName()));
   }

   public PyObject __reduce__() {
      return this.basebytes___reduce__();
   }

   final PyTuple basebytes___reduce__() {
      PyUnicode encoded = new PyUnicode(this.asString());
      PyObject args = new PyTuple(new PyObject[]{encoded, getPickleEncoding()});
      PyObject dict = this.__findattr__("__dict__");
      return new PyTuple(new PyObject[]{this.getType(), args, dict != null ? dict : Py.None});
   }

   private static final PyString getPickleEncoding() {
      if (PICKLE_ENCODING == null) {
         PICKLE_ENCODING = new PyString("latin-1");
      }

      return PICKLE_ENCODING;
   }

   protected int index(byte b) {
      int limit = this.offset + this.size;

      for(int p = this.offset; p < limit; ++p) {
         if (this.storage[p] == b) {
            return p - this.offset;
         }
      }

      return -1;
   }

   protected static final int checkForEmptySeparator(PyBuffer separator) throws PyException {
      int n = separator.getLen();
      if (n == 0) {
         throw Py.ValueError("empty separator");
      } else {
         return n;
      }
   }

   protected int lstripIndex(ByteSet byteSet) {
      int limit = this.offset + this.size;

      for(int left = this.offset; left < limit; ++left) {
         if (!byteSet.contains(this.storage[left])) {
            return left - this.offset;
         }
      }

      return this.size;
   }

   protected int lstripIndex() {
      int limit = this.offset + this.size;

      for(int left = this.offset; left < limit; ++left) {
         if (!isspace(this.storage[left])) {
            return left - this.offset;
         }
      }

      return this.size;
   }

   protected int rstripIndex(ByteSet byteSet) {
      for(int right = this.offset + this.size; right > this.offset; --right) {
         if (!byteSet.contains(this.storage[right - 1])) {
            return right - this.offset;
         }
      }

      return 0;
   }

   protected int rstripIndex() {
      for(int right = this.offset + this.size; right > this.offset; --right) {
         if (!isspace(this.storage[right - 1])) {
            return right - this.offset;
         }
      }

      return this.size;
   }

   final int basebytes_count(PyObject sub, PyObject ostart, PyObject oend) {
      PyBuffer vsub = getViewOrError(sub);
      Throwable var5 = null;

      int var8;
      try {
         Finder finder = new Finder(vsub);
         int[] index = this.indicesEx(ostart, oend);
         var8 = finder.count(this.storage, this.offset + index[0], index[3]);
      } catch (Throwable var17) {
         var5 = var17;
         throw var17;
      } finally {
         if (vsub != null) {
            if (var5 != null) {
               try {
                  vsub.close();
               } catch (Throwable var16) {
                  var5.addSuppressed(var16);
               }
            } else {
               vsub.close();
            }
         }

      }

      return var8;
   }

   final int basebytes_find(PyObject sub, PyObject ostart, PyObject oend) {
      PyBuffer vsub = getViewOrError(sub);
      Throwable var5 = null;

      int var7;
      try {
         Finder finder = new Finder(vsub);
         var7 = this.find(finder, ostart, oend);
      } catch (Throwable var16) {
         var5 = var16;
         throw var16;
      } finally {
         if (vsub != null) {
            if (var5 != null) {
               try {
                  vsub.close();
               } catch (Throwable var15) {
                  var5.addSuppressed(var15);
               }
            } else {
               vsub.close();
            }
         }

      }

      return var7;
   }

   static void basebytes_fromhex(BaseBytes result, String hex) throws PyException {
      int hexlen = hex.length();
      result.newStorage(hexlen / 2);
      String fmt = "non-hexadecimal number found in fromhex() arg at position %d";
      byte[] r = result.storage;
      int p = result.offset;
      int i = 0;

      while(i < hexlen) {
         char c = hex.charAt(i++);
         if (c != ' ') {
            try {
               int value = hexDigit(c);
               c = hex.charAt(i++);
               value = (value << 4) + hexDigit(c);
               r[p++] = (byte)value;
            } catch (IllegalArgumentException var9) {
               throw Py.ValueError(String.format(fmt, i - 1));
            } catch (IndexOutOfBoundsException var10) {
               throw Py.ValueError(String.format(fmt, i - 2));
            }
         }
      }

      result.size = p - result.offset;
   }

   private static int hexDigit(char c) throws IllegalArgumentException {
      int result = c - 48;
      if (result >= 0) {
         if (result < 10) {
            return result;
         }

         result = (c & 223) - 65;
         if (result >= 0 && result < 6) {
            return result + 10;
         }
      }

      throw new IllegalArgumentException();
   }

   final synchronized PyByteArray basebytes_join(Iterable iter) {
      List iterList = new LinkedList();
      long mysize = (long)this.size;
      long totalSize = 0L;
      boolean first = true;
      boolean var17 = false;

      PyByteArray var22;
      try {
         var17 = true;
         Iterator var8 = iter.iterator();

         while(var8.hasNext()) {
            PyObject o = (PyObject)var8.next();
            PyBuffer v = getView(o);
            if (v == null) {
               String fmt = "can only join an iterable of bytes (item %d has type '%.80s')";
               throw Py.TypeError(String.format(fmt, iterList.size(), o.getType().fastGetName()));
            }

            iterList.add(v);
            totalSize += (long)v.getLen();
            if (!first) {
               totalSize += mysize;
            } else {
               first = false;
            }

            if (totalSize > 2147483647L) {
               throw Py.OverflowError("join() result would be too long");
            }
         }

         PyByteArray result = new PyByteArray((int)totalSize);
         int p = result.offset;
         first = true;

         PyBuffer v;
         for(Iterator var21 = iterList.iterator(); var21.hasNext(); p += v.getLen()) {
            v = (PyBuffer)var21.next();
            if (!first) {
               System.arraycopy(this.storage, this.offset, result.storage, p, this.size);
               p += this.size;
            } else {
               first = false;
            }

            v.copyTo(result.storage, p);
         }

         var22 = result;
         var17 = false;
      } finally {
         if (var17) {
            Iterator var14 = iterList.iterator();

            while(var14.hasNext()) {
               PyBuffer v = (PyBuffer)var14.next();
               v.release();
            }

         }
      }

      Iterator var24 = iterList.iterator();

      while(var24.hasNext()) {
         PyBuffer v = (PyBuffer)var24.next();
         v.release();
      }

      return var22;
   }

   public PyTuple partition(PyObject sep) {
      return this.basebytes_partition(sep);
   }

   final synchronized PyTuple basebytes_partition(PyObject sep) {
      PyBuffer separator = getViewOrError(sep);
      Throwable var3 = null;

      PyTuple var7;
      try {
         int n = checkForEmptySeparator(separator);
         Finder finder = new Finder(separator);
         finder.setText(this);
         int p = finder.nextIndex() - this.offset;
         if (p < 0) {
            var7 = this.partition(this.size, this.size);
            return var7;
         }

         var7 = this.partition(p, p + n);
      } catch (Throwable var17) {
         var3 = var17;
         throw var17;
      } finally {
         if (separator != null) {
            if (var3 != null) {
               try {
                  separator.close();
               } catch (Throwable var16) {
                  var3.addSuppressed(var16);
               }
            } else {
               separator.close();
            }
         }

      }

      return var7;
   }

   private PyTuple partition(int p, int q) {
      BaseBytes head = this.getslice(0, p);
      BaseBytes sep = this.getslice(p, q);
      BaseBytes tail = this.getslice(q, this.size);
      return new PyTuple(new PyObject[]{head, sep, tail});
   }

   final int basebytes_rfind(PyObject sub, PyObject ostart, PyObject oend) {
      PyBuffer vsub = getViewOrError(sub);
      Throwable var5 = null;

      int var7;
      try {
         Finder finder = new ReverseFinder(vsub);
         var7 = this.find(finder, ostart, oend);
      } catch (Throwable var16) {
         var5 = var16;
         throw var16;
      } finally {
         if (vsub != null) {
            if (var5 != null) {
               try {
                  vsub.close();
               } catch (Throwable var15) {
                  var5.addSuppressed(var15);
               }
            } else {
               vsub.close();
            }
         }

      }

      return var7;
   }

   private final int find(Finder finder, PyObject ostart, PyObject oend) {
      int[] index = this.indicesEx(ostart, oend);
      finder.setText(this.storage, this.offset + index[0], index[3]);
      int result = finder.nextIndex();
      return result < 0 ? -1 : result - this.offset;
   }

   final synchronized PyByteArray basebytes_replace(PyObject oldB, PyObject newB, int maxcount) {
      PyBuffer to = getViewOrError(newB);
      Throwable var5 = null;

      PyByteArray var10;
      try {
         PyBuffer from = getViewOrError(oldB);
         Throwable var7 = null;

         try {
            Object from_len;
            try {
               if (maxcount < 0) {
                  maxcount = Integer.MAX_VALUE;
               } else if (maxcount == 0 || this.size == 0) {
                  from_len = new PyByteArray(this);
                  return (PyByteArray)from_len;
               }

               from_len = from.getLen();
               int to_len = to.getLen();
               if (maxcount == 0 || from_len == false && to_len == 0) {
                  var10 = new PyByteArray(this);
                  return var10;
               }

               if (from_len == false) {
                  var10 = this.replace_interleave(to, maxcount);
                  return var10;
               }

               if (this.size == 0) {
                  var10 = new PyByteArray(to);
                  return var10;
               }

               if (to_len == 0) {
                  var10 = this.replace_delete_substring(from, maxcount);
                  return var10;
               }

               if (from_len != to_len) {
                  var10 = this.replace_substring(from, to, maxcount);
                  return var10;
               }

               var10 = this.replace_substring_in_place(from, to, maxcount);
            } catch (Throwable var51) {
               from_len = var51;
               var7 = var51;
               throw var51;
            }
         } finally {
            if (from != null) {
               if (var7 != null) {
                  try {
                     from.close();
                  } catch (Throwable var50) {
                     var7.addSuppressed(var50);
                  }
               } else {
                  from.close();
               }
            }

         }
      } catch (Throwable var53) {
         var5 = var53;
         throw var53;
      } finally {
         if (to != null) {
            if (var5 != null) {
               try {
                  to.close();
               } catch (Throwable var49) {
                  var5.addSuppressed(var49);
               }
            } else {
               to.close();
            }
         }

      }

      return var10;
   }

   private PyByteArray replace_substring(PyBuffer from, PyBuffer to, int maxcount) {
      Finder finder = new Finder(from);
      int count = finder.count(this.storage, this.offset, this.size, maxcount);
      if (count == 0) {
         return new PyByteArray(this);
      } else {
         int from_len = from.getLen();
         int to_len = to.getLen();
         long result_len = (long)(this.size + count * (to_len - from_len));

         byte[] r;
         try {
            r = new byte[(int)result_len];
         } catch (OutOfMemoryError var15) {
            throw Py.OverflowError("replace bytes is too long");
         }

         int p = this.offset;
         int rp = 0;
         finder.setText(this.storage, p, this.size);

         int q;
         while(count-- > 0) {
            q = finder.nextIndex();
            if (q < 0) {
               break;
            }

            int length = q - p;
            if (length > 0) {
               System.arraycopy(this.storage, p, r, rp, length);
               rp += length;
            }

            p = q + from_len;
            to.copyTo(r, rp);
            rp += to_len;
         }

         q = this.size + this.offset - p;
         if (q > 0) {
            System.arraycopy(this.storage, p, r, rp, q);
            int var10000 = rp + q;
         }

         return new PyByteArray(r);
      }
   }

   private PyByteArray replace_interleave(PyBuffer to, int maxcount) {
      int count = this.size + 1;
      if (maxcount < count) {
         count = maxcount;
      }

      int to_len = to.getLen();
      long result_len = (long)count * (long)to_len + (long)this.size;

      byte[] r;
      try {
         r = new byte[(int)result_len];
      } catch (OutOfMemoryError var11) {
         throw Py.OverflowError("replace bytes is too long");
      }

      int p = this.offset;
      int rp = 0;
      to.copyTo(r, rp);
      rp += to_len;

      int length;
      for(length = 1; length < count; ++length) {
         r[rp++] = this.storage[p++];
         to.copyTo(r, rp);
         rp += to_len;
      }

      length = this.size + this.offset - p;
      if (length > 0) {
         System.arraycopy(this.storage, p, r, rp, length);
         int var10000 = rp + length;
      }

      return new PyByteArray(r);
   }

   private PyByteArray replace_delete_substring(PyBuffer from, int maxcount) {
      Finder finder = new Finder(from);
      int count = finder.count(this.storage, this.offset, this.size, maxcount);
      if (count == 0) {
         return new PyByteArray(this);
      } else {
         int from_len = from.getLen();
         long result_len = (long)(this.size - count * from_len);

         assert result_len >= 0L;

         byte[] r;
         try {
            r = new byte[(int)result_len];
         } catch (OutOfMemoryError var13) {
            throw Py.OverflowError("replace bytes is too long");
         }

         int p = this.offset;
         int rp = 0;
         finder.setText(this.storage, this.offset, this.size);

         int q;
         for(; count-- > 0; p = q + from_len) {
            q = finder.nextIndex();
            if (q < 0) {
               break;
            }

            int length = q - p;
            if (length > 0) {
               System.arraycopy(this.storage, p, r, rp, length);
               rp += length;
            }
         }

         q = this.size + this.offset - p;
         if (q > 0) {
            System.arraycopy(this.storage, p, r, rp, q);
            int var10000 = rp + q;
         }

         return new PyByteArray(r);
      }
   }

   private PyByteArray replace_substring_in_place(PyBuffer from, PyBuffer to, int maxcount) {
      Finder finder = new Finder(from);
      int count = maxcount;

      byte[] r;
      try {
         r = new byte[this.size];
      } catch (OutOfMemoryError var8) {
         throw Py.OverflowError("replace bytes is too long");
      }

      System.arraycopy(this.storage, this.offset, r, 0, this.size);
      finder.setText(r);

      while(count-- > 0) {
         int q = finder.nextIndex();
         if (q < 0) {
            break;
         }

         to.copyTo(r, q);
      }

      return new PyByteArray(r);
   }

   public PyTuple rpartition(PyObject sep) {
      return this.basebytes_rpartition(sep);
   }

   final synchronized PyTuple basebytes_rpartition(PyObject sep) {
      PyBuffer separator = getViewOrError(sep);
      Throwable var3 = null;

      PyTuple var7;
      try {
         int n = checkForEmptySeparator(separator);
         Finder finder = new ReverseFinder(separator);
         finder.setText(this);
         int p = finder.nextIndex() - this.offset;
         if (p >= 0) {
            var7 = this.partition(p, p + n);
            return var7;
         }

         var7 = this.partition(0, 0);
      } catch (Throwable var17) {
         var3 = var17;
         throw var17;
      } finally {
         if (separator != null) {
            if (var3 != null) {
               try {
                  separator.close();
               } catch (Throwable var16) {
                  var3.addSuppressed(var16);
               }
            } else {
               separator.close();
            }
         }

      }

      return var7;
   }

   public PyList rsplit() {
      return this.basebytes_rsplit_whitespace(-1);
   }

   public PyList rsplit(PyObject sep) {
      return this.basebytes_rsplit(sep, -1);
   }

   public PyList rsplit(PyObject sep, int maxsplit) {
      return this.basebytes_rsplit(sep, maxsplit);
   }

   final PyList basebytes_rsplit(PyObject sep, int maxsplit) {
      return sep != null && sep != Py.None ? this.basebytes_rsplit_explicit(sep, maxsplit) : this.basebytes_rsplit_whitespace(maxsplit);
   }

   final synchronized PyList basebytes_rsplit_explicit(PyObject sep, int maxsplit) {
      PyBuffer separator = getViewOrError(sep);
      Throwable var4 = null;

      PyList var22;
      try {
         int n = checkForEmptySeparator(separator);
         PyList result = new PyList();
         Finder finder = new ReverseFinder(separator);
         finder.setText(this);
         int q = this.offset + this.size;

         while(q > this.offset && maxsplit-- != 0) {
            int r = q;
            q = finder.nextIndex();
            int p;
            if (q < 0) {
               p = this.offset;
            } else {
               p = q + n;
            }

            BaseBytes word = this.getslice(p - this.offset, r - this.offset);
            result.add(0, word);
         }

         if (q >= this.offset) {
            BaseBytes word = this.getslice(0, q - this.offset);
            result.add(0, word);
         }

         var22 = result;
      } catch (Throwable var19) {
         var4 = var19;
         throw var19;
      } finally {
         if (separator != null) {
            if (var4 != null) {
               try {
                  separator.close();
               } catch (Throwable var18) {
                  var4.addSuppressed(var18);
               }
            } else {
               separator.close();
            }
         }

      }

      return var22;
   }

   final synchronized PyList basebytes_rsplit_whitespace(int maxsplit) {
      PyList result = new PyList();

      int q;
      for(q = this.offset + this.size; q > this.offset && isspace(this.storage[q - 1]); --q) {
      }

      BaseBytes word;
      while(q > this.offset && maxsplit-- != 0) {
         int p;
         for(p = q; p > this.offset && !isspace(this.storage[p - 1]); --p) {
         }

         word = this.getslice(p - this.offset, q - this.offset);
         result.add(0, word);

         for(q = p; q > this.offset && isspace(this.storage[q - 1]); --q) {
         }
      }

      if (q > this.offset) {
         word = this.getslice(0, q - this.offset);
         result.add(0, word);
      }

      return result;
   }

   public PyList split() {
      return this.basebytes_split_whitespace(-1);
   }

   public PyList split(PyObject sep) {
      return this.basebytes_split(sep, -1);
   }

   public PyList split(PyObject sep, int maxsplit) {
      return this.basebytes_split(sep, maxsplit);
   }

   final PyList basebytes_split(PyObject sep, int maxsplit) {
      return sep != null && sep != Py.None ? this.basebytes_split_explicit(sep, maxsplit) : this.basebytes_split_whitespace(maxsplit);
   }

   final synchronized PyList basebytes_split_explicit(PyObject sep, int maxsplit) {
      PyBuffer separator = getViewOrError(sep);
      Throwable var4 = null;

      try {
         checkForEmptySeparator(separator);
         PyList result = new PyList();
         Finder finder = new Finder(separator);
         finder.setText(this);
         int p = finder.currIndex();

         for(int q = finder.nextIndex(); q >= 0 && maxsplit-- != 0; q = finder.nextIndex()) {
            result.append(this.getslice(p - this.offset, q - this.offset));
            p = finder.currIndex();
         }

         result.append(this.getslice(p - this.offset, this.size));
         PyList var9 = result;
         return var9;
      } catch (Throwable var18) {
         var4 = var18;
         throw var18;
      } finally {
         if (separator != null) {
            if (var4 != null) {
               try {
                  separator.close();
               } catch (Throwable var17) {
                  var4.addSuppressed(var17);
               }
            } else {
               separator.close();
            }
         }

      }
   }

   final synchronized PyList basebytes_split_whitespace(int maxsplit) {
      PyList result = new PyList();
      int limit = this.offset + this.size;

      int p;
      for(p = this.offset; p < limit && isspace(this.storage[p]); ++p) {
      }

      while(p < limit && maxsplit-- != 0) {
         int q;
         for(q = p; q < limit && !isspace(this.storage[q]); ++q) {
         }

         result.append(this.getslice(p - this.offset, q - this.offset));

         for(p = q; p < limit && isspace(this.storage[p]); ++p) {
         }
      }

      if (p < limit) {
         result.append(this.getslice(p - this.offset, this.size));
      }

      return result;
   }

   public PyList splitlines() {
      return this.basebytes_splitlines(false);
   }

   public PyList splitlines(boolean keepends) {
      return this.basebytes_splitlines(keepends);
   }

   protected final synchronized PyList basebytes_splitlines(boolean keepends) {
      PyList list = new PyList();
      int limit = this.offset + this.size;

      int lenEOL;
      int q;
      for(int p = this.offset; p < limit; p = q + lenEOL) {
         lenEOL = 0;

         for(q = p; q < limit; ++q) {
            byte b = this.storage[q];
            if (b == 13) {
               lenEOL = this.storage[q + 1] == 10 ? 2 : 1;
               break;
            }

            if (b == 10) {
               lenEOL = 1;
               break;
            }
         }

         if (keepends) {
            list.append(this.getslice(p - this.offset, q + lenEOL - this.offset));
         } else {
            list.append(this.getslice(p - this.offset, q - this.offset));
         }
      }

      return list;
   }

   protected static byte fillByteCheck(String function, String fillchar) {
      if (fillchar == null) {
         return 32;
      } else if (fillchar.length() == 1) {
         return (byte)fillchar.charAt(0);
      } else {
         throw Py.TypeError(function + "() argument 2 must be char, not str");
      }
   }

   private BaseBytes padHelper(byte pad, int left, int right) {
      if (left + right <= 0) {
         return this.getslice(0, this.size);
      } else {
         Builder builder = this.getBuilder(left + this.size + right);
         builder.repeat(pad, left);
         builder.append(this);
         builder.repeat(pad, right);
         return builder.getResult();
      }
   }

   final BaseBytes basebytes_center(int width, String fillchar) {
      byte pad = fillByteCheck("center", fillchar);
      int fill = width - this.size;
      int left = fill / 2 + (fill & width & 1);
      return this.padHelper(pad, left, fill - left);
   }

   final BaseBytes basebytes_ljust(int width, String fillchar) {
      byte pad = fillByteCheck("rjust", fillchar);
      int fill = width - this.size;
      return this.padHelper(pad, 0, fill);
   }

   final BaseBytes basebytes_rjust(int width, String fillchar) {
      byte pad = fillByteCheck("rjust", fillchar);
      int fill = width - this.size;
      return this.padHelper(pad, fill, 0);
   }

   final BaseBytes basebytes_expandtabs(int tabsize) {
      int estimatedSize = this.size + this.size / 8;
      Builder builder = this.getBuilder(estimatedSize);
      int carriagePosition = 0;
      int limit = this.offset + this.size;

      for(int i = this.offset; i < limit; ++i) {
         byte c = this.storage[i];
         if (c == 9) {
            int spaces = tabsize - carriagePosition % tabsize;
            builder.repeat((byte)32, spaces);
            carriagePosition += spaces;
         } else {
            builder.append(c);
            carriagePosition = c != 10 && c != 13 ? carriagePosition + 1 : 0;
         }
      }

      return builder.getResult();
   }

   final BaseBytes basebytes_zfill(int width) {
      int fill = width - this.size;
      Builder builder = this.getBuilder(fill > 0 ? width : this.size);
      if (fill <= 0) {
         builder.append(this);
      } else {
         int p = 0;
         if (this.size > 0) {
            byte sign = this.storage[this.offset];
            if (sign == 45 || sign == 43) {
               builder.append(sign);
               p = 1;
            }
         }

         builder.repeat((byte)48, fill);
         if (this.size > p) {
            builder.append(this, p, this.size);
         }
      }

      return builder.getResult();
   }

   static final boolean isupper(byte b) {
      return (ctype[128 + b] & 1) != 0;
   }

   static final boolean islower(byte b) {
      return (ctype[128 + b] & 2) != 0;
   }

   static final boolean isalpha(byte b) {
      return (ctype[128 + b] & 3) != 0;
   }

   static final boolean isdigit(byte b) {
      return (ctype[128 + b] & 4) != 0;
   }

   static final boolean isalnum(byte b) {
      return (ctype[128 + b] & 7) != 0;
   }

   static final boolean isspace(byte b) {
      return (ctype[128 + b] & 8) != 0;
   }

   public boolean isalnum() {
      return this.basebytes_isalnum();
   }

   final boolean basebytes_isalnum() {
      if (this.size == 1) {
         return isalnum(this.storage[this.offset]);
      } else {
         for(int i = 0; i < this.size; ++i) {
            if (!isalnum(this.storage[this.offset + i])) {
               return false;
            }
         }

         return this.size > 0;
      }
   }

   public boolean isalpha() {
      return this.basebytes_isalpha();
   }

   final boolean basebytes_isalpha() {
      if (this.size == 1) {
         return isalpha(this.storage[this.offset]);
      } else {
         for(int i = 0; i < this.size; ++i) {
            if (!isalpha(this.storage[this.offset + i])) {
               return false;
            }
         }

         return this.size > 0;
      }
   }

   public boolean isdigit() {
      return this.basebytes_isdigit();
   }

   final boolean basebytes_isdigit() {
      if (this.size == 1) {
         return isdigit(this.storage[this.offset]);
      } else {
         for(int i = 0; i < this.size; ++i) {
            if (!isdigit(this.storage[this.offset + i])) {
               return false;
            }
         }

         return this.size > 0;
      }
   }

   public boolean islower() {
      return this.basebytes_islower();
   }

   final boolean basebytes_islower() {
      if (this.size == 1) {
         return islower(this.storage[this.offset]);
      } else {
         byte c = 0;

         int i;
         for(i = 0; i < this.size && !isalpha(c = this.storage[this.offset + i]); ++i) {
         }

         if (i != this.size && !isupper(c)) {
            ++i;

            while(i < this.size) {
               if (isupper(this.storage[this.offset + i])) {
                  return false;
               }

               ++i;
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public boolean isspace() {
      return this.basebytes_isspace();
   }

   final boolean basebytes_isspace() {
      if (this.size == 1) {
         return isspace(this.storage[this.offset]);
      } else {
         for(int i = 0; i < this.size; ++i) {
            if (!isspace(this.storage[this.offset + i])) {
               return false;
            }
         }

         return this.size > 0;
      }
   }

   public boolean istitle() {
      return this.basebytes_istitle();
   }

   final boolean basebytes_istitle() {
      int state = 0;

      for(int i = 0; i < this.size; ++i) {
         byte c = this.storage[this.offset + i];
         if (isupper(c)) {
            if (state == 2) {
               return false;
            }

            state = 2;
         } else if (islower(c)) {
            if (state != 2) {
               return false;
            }
         } else if (state == 2) {
            state = 1;
         }
      }

      return state != 0;
   }

   public boolean isupper() {
      return this.basebytes_isupper();
   }

   final boolean basebytes_isupper() {
      if (this.size == 1) {
         return isupper(this.storage[this.offset]);
      } else {
         byte c = 0;

         int i;
         for(i = 0; i < this.size && !isalpha(c = this.storage[this.offset + i]); ++i) {
         }

         if (i != this.size && !islower(c)) {
            ++i;

            while(i < this.size) {
               if (islower(this.storage[this.offset + i])) {
                  return false;
               }

               ++i;
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public BaseBytes capitalize() {
      return this.basebytes_capitalize();
   }

   final BaseBytes basebytes_capitalize() {
      Builder builder = this.getBuilder(this.size);
      if (this.size > 0) {
         byte c = this.storage[this.offset];
         if (islower(c)) {
            c = (byte)(c ^ 32);
         }

         builder.append(c);

         for(int i = 1; i < this.size; ++i) {
            c = this.storage[this.offset + i];
            if (isupper(c)) {
               c = (byte)(c ^ 32);
            }

            builder.append(c);
         }
      }

      return builder.getResult();
   }

   public BaseBytes lower() {
      return this.basebytes_lower();
   }

   final BaseBytes basebytes_lower() {
      Builder builder = this.getBuilder(this.size);

      for(int i = 0; i < this.size; ++i) {
         byte c = this.storage[this.offset + i];
         if (isupper(c)) {
            c = (byte)(c ^ 32);
         }

         builder.append(c);
      }

      return builder.getResult();
   }

   public BaseBytes swapcase() {
      return this.basebytes_swapcase();
   }

   final BaseBytes basebytes_swapcase() {
      Builder builder = this.getBuilder(this.size);

      for(int i = 0; i < this.size; ++i) {
         byte c = this.storage[this.offset + i];
         if (isalpha(c)) {
            c = (byte)(c ^ 32);
         }

         builder.append(c);
      }

      return builder.getResult();
   }

   public BaseBytes title() {
      return this.basebytes_title();
   }

   final BaseBytes basebytes_title() {
      Builder builder = this.getBuilder(this.size);
      boolean inWord = false;

      for(int i = 0; i < this.size; ++i) {
         byte c = this.storage[this.offset + i];
         if (!inWord) {
            if (islower(c)) {
               c = (byte)(c ^ 32);
               inWord = true;
            } else if (isupper(c)) {
               inWord = true;
            }
         } else if (isupper(c)) {
            c = (byte)(c ^ 32);
         } else if (!islower(c)) {
            inWord = false;
         }

         builder.append(c);
      }

      return builder.getResult();
   }

   public BaseBytes upper() {
      return this.basebytes_upper();
   }

   final BaseBytes basebytes_upper() {
      Builder builder = this.getBuilder(this.size);

      for(int i = 0; i < this.size; ++i) {
         byte c = this.storage[this.offset + i];
         if (islower(c)) {
            c = (byte)(c ^ 32);
         }

         builder.append(c);
      }

      return builder.getResult();
   }

   private final synchronized byte byteAt(int index) {
      return this.storage[index + this.offset];
   }

   public synchronized int intAt(int index) throws PyException {
      this.indexCheck(index);
      return 255 & this.byteAt(index);
   }

   protected synchronized byte[] repeatImpl(int count) {
      if (count <= 0) {
         return emptyStorage;
      } else {
         long newSize = (long)count * (long)this.size;

         byte[] dst;
         try {
            dst = new byte[(int)newSize];
         } catch (OutOfMemoryError var7) {
            throw Py.MemoryError(var7.getMessage());
         }

         int i = 0;

         for(int p = 0; i < count; p += this.size) {
            System.arraycopy(this.storage, this.offset, dst, p, this.size);
            ++i;
         }

         return dst;
      }
   }

   private static final void appendHexEscape(StringBuilder buf, int c) {
      buf.append("\\x").append(Character.forDigit((c & 240) >> 4, 16)).append(Character.forDigit(c & 15, 16));
   }

   final synchronized String basebytes_repr(String before, String after) {
      if (before == null) {
         before = "";
      }

      if (after == null) {
         after = "";
      }

      int guess = this.size + (this.size >> 2) + before.length() + after.length() + 10;
      StringBuilder buf = new StringBuilder(guess);
      buf.append(before).append('\'');
      int jmax = this.offset + this.size;

      for(int j = this.offset; j < jmax; ++j) {
         int c = 255 & this.storage[j];
         if (c >= 127) {
            appendHexEscape(buf, c);
         } else if (c < 32) {
            if (c == 9) {
               buf.append("\\t");
            } else if (c == 10) {
               buf.append("\\n");
            } else if (c == 13) {
               buf.append("\\r");
            } else {
               appendHexEscape(buf, c);
            }
         } else {
            if (c == 92 || c == 39) {
               buf.append('\\');
            }

            buf.append((char)c);
         }
      }

      buf.append('\'').append(after);
      return buf.toString();
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public boolean contains(Object o) {
      return this.listDelegate.contains(o);
   }

   public Iterator iterator() {
      return this.listDelegate.iterator();
   }

   public Object[] toArray() {
      return this.listDelegate.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.listDelegate.toArray(a);
   }

   public boolean add(PyInteger o) {
      return this.listDelegate.add(o);
   }

   public boolean remove(Object o) {
      return this.listDelegate.remove(o);
   }

   public boolean containsAll(Collection c) {
      return this.listDelegate.containsAll(c);
   }

   public boolean addAll(Collection c) {
      return this.listDelegate.addAll(c);
   }

   public boolean addAll(int index, Collection c) {
      return this.listDelegate.addAll(index, c);
   }

   public boolean removeAll(Collection c) {
      return this.listDelegate.removeAll(c);
   }

   public boolean retainAll(Collection c) {
      return this.listDelegate.retainAll(c);
   }

   public void clear() {
      this.listDelegate.clear();
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else {
         return other instanceof PyObject ? super.equals(other) : this.listDelegate.equals(other);
      }
   }

   public int hashCode() {
      return this.listDelegate.hashCode();
   }

   public PyInteger get(int index) {
      return (PyInteger)this.listDelegate.get(index);
   }

   public PyInteger set(int index, PyInteger element) {
      return (PyInteger)this.listDelegate.set(index, element);
   }

   public void add(int index, PyInteger element) {
      this.listDelegate.add(index, element);
   }

   public PyInteger remove(int index) {
      return (PyInteger)this.listDelegate.remove(index);
   }

   public int indexOf(Object o) {
      return this.listDelegate.indexOf(o);
   }

   public int lastIndexOf(Object o) {
      return this.listDelegate.lastIndexOf(o);
   }

   public ListIterator listIterator() {
      return this.listDelegate.listIterator();
   }

   public ListIterator listIterator(int index) {
      return this.listDelegate.listIterator(index);
   }

   public List subList(int fromIndex, int toIndex) {
      return this.listDelegate.subList(fromIndex, toIndex);
   }

   protected static final int roundUp(int size) {
      int ALLOC = true;
      int SIZE2 = true;
      if (size >= 10) {
         return size + (size >> 3) + 21 & -16;
      } else {
         return size > 0 ? 16 : 0;
      }
   }

   protected abstract Builder getBuilder(int var1);

   static {
      int c;
      for(c = 65; c <= 90; ++c) {
         ctype[128 + c] = 1;
         ctype[160 + c] = 2;
      }

      for(c = 48; c <= 57; ++c) {
         ctype[128 + c] = 4;
      }

      char[] var4 = " \t\n\u000b\f\r".toCharArray();
      int var1 = var4.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         char c = var4[var2];
         ctype[128 + c] = 8;
      }

   }

   protected abstract static class Builder {
      private byte[] storage;
      private int size;

      abstract BaseBytes getResult();

      Builder(int capacity) {
         this.storage = BaseBytes.emptyStorage;
         this.size = 0;
         this.makeRoomFor(capacity);
      }

      byte[] getStorage() {
         byte[] s = this.storage;
         this.storage = BaseBytes.emptyStorage;
         return s;
      }

      final int getSize() {
         return this.size;
      }

      void append(byte b) {
         this.makeRoomFor(1);
         this.storage[this.size++] = b;
      }

      void repeat(byte b, int n) {
         if (n > 0) {
            this.makeRoomFor(n);

            while(n-- > 0) {
               this.storage[this.size++] = b;
            }
         }

      }

      void append(BaseBytes b) {
         this.append(b, 0, b.size);
      }

      void append(BaseBytes b, int start, int end) {
         int n = end - start;
         this.makeRoomFor(n);
         System.arraycopy(b.storage, b.offset + start, this.storage, this.size, n);
         this.size += n;
      }

      void append(PyBuffer v) {
         int n = v.getLen();
         this.makeRoomFor(n);
         v.copyTo(this.storage, this.size);
         this.size += n;
      }

      void makeRoomFor(int n) throws PyException {
         int needed = this.size + n;
         if (needed > this.storage.length) {
            try {
               if (this.storage == BaseBytes.emptyStorage) {
                  this.size = 0;
                  if (n > 0) {
                     this.storage = new byte[n];
                  }
               } else {
                  byte[] old = this.storage;
                  this.storage = new byte[BaseBytes.roundUp(needed)];
                  System.arraycopy(old, 0, this.storage, 0, this.size);
               }
            } catch (OutOfMemoryError var4) {
               throw Py.MemoryError(var4.getMessage());
            }
         }

      }
   }

   protected static class ByteSet {
      protected final long[] map = new long[4];

      public ByteSet(PyBuffer bytes) {
         int n = bytes.getLen();

         for(int i = 0; i < n; ++i) {
            int c = bytes.intAt(i);
            long mask = 1L << c;
            int word = c >> 6;
            long[] var10000 = this.map;
            var10000[word] |= mask;
         }

      }

      public boolean contains(byte b) {
         int word = (b & 255) >> 6;
         long mask = 1L << b;
         return (this.map[word] & mask) != 0L;
      }

      public boolean contains(int b) {
         int word = b >> 6;
         long mask = 1L << b;
         return (this.map[word] & mask) != 0L;
      }
   }

   protected static class ReverseFinder extends Finder {
      private static final byte MASK = 31;

      public ReverseFinder(PyBuffer pattern) {
         super(pattern);
      }

      protected int[] calculateSkipTable() {
         int[] skipTable = new int[32];
         int m = this.pattern.getLen();
         Arrays.fill(skipTable, m);
         int i = m;

         while(true) {
            --i;
            if (i < 0) {
               return skipTable;
            }

            skipTable[31 & this.pattern.byteAt(i)] = i;
         }
      }

      public int currIndex() {
         return this.right + this.pattern.getLen() - 1;
      }

      public int nextIndex() {
         int m = this.pattern.getLen();
         int i;
         int i;
         if (this.skipTable != null) {
            i = this.right - 1;

            while(true) {
               while(i >= this.left) {
                  i = this.skipTable[31 & this.text[i]];
                  if (i == 0) {
                     int k = i;

                     int j;
                     for(j = 0; j < m && this.text[k++] == this.pattern.byteAt(j); ++j) {
                     }

                     if (j == m) {
                        this.right = i - m + 1;
                        return i;
                     }

                     --i;
                  } else {
                     i -= i;
                  }
               }

               return -1;
            }
         } else if (m == 1) {
            byte b = this.pattern.byteAt(0);
            i = this.right;

            while(true) {
               --i;
               if (i < this.left) {
                  break;
               }

               if (this.text[i] == b) {
                  this.right = i;
                  return i;
               }
            }
         } else {
            i = this.right;
            --i;
            if (i >= this.left) {
               this.right = i;
               return i;
            }
         }

         return -1;
      }
   }

   protected static class Finder {
      private static final byte MASK = 31;
      protected int[] skipTable = null;
      protected final PyBuffer pattern;
      protected byte[] text;
      protected int left;
      protected int right;

      public Finder(PyBuffer pattern) {
         this.text = BaseBytes.emptyStorage;
         this.left = 0;
         this.right = 0;
         this.pattern = pattern;
      }

      protected int[] calculateSkipTable() {
         int[] skipTable = new int[32];
         int m = this.pattern.getLen();
         Arrays.fill(skipTable, m);

         for(int i = 0; i < m; ++i) {
            skipTable[31 & this.pattern.byteAt(i)] = m - i - 1;
         }

         return skipTable;
      }

      public void setText(byte[] text) {
         this.setText(text, 0, text.length);
      }

      public void setText(BaseBytes text) {
         this.setText(text.storage, text.offset, text.size);
      }

      public void setText(byte[] text, int start, int size) {
         this.text = text;
         this.left = start;
         this.right = start + size - this.pattern.getLen() + 1;
         if (this.pattern.getLen() > 1 && this.skipTable == null) {
            this.skipTable = this.calculateSkipTable();
         }

      }

      public int currIndex() {
         return this.left;
      }

      public int nextIndex() {
         int m = this.pattern.getLen();
         int i;
         int i;
         if (this.skipTable != null) {
            i = this.left;

            while(true) {
               while(i < this.right) {
                  i = this.skipTable[31 & this.text[i + (m - 1)]];
                  if (i == 0) {
                     int k = i;

                     int j;
                     for(j = 0; j < m && this.text[k++] == this.pattern.byteAt(j); ++j) {
                     }

                     if (j == m) {
                        this.left = k;
                        return i;
                     }

                     ++i;
                  } else {
                     i += i;
                  }
               }

               return -1;
            }
         } else if (m == 1) {
            byte b = this.pattern.byteAt(0);

            for(i = this.left; i < this.right; ++i) {
               if (this.text[i] == b) {
                  this.left = i + 1;
                  return i;
               }
            }
         } else {
            i = this.left;
            if (i <= this.right) {
               this.left = i + 1;
               return i;
            }
         }

         return -1;
      }

      public int count(byte[] text) {
         return this.count(text, 0, text.length, Integer.MAX_VALUE);
      }

      public int count(byte[] text, int start, int size) {
         return this.count(text, start, size, Integer.MAX_VALUE);
      }

      public int count(byte[] text, int start, int size, int maxcount) {
         this.setText(text, start, size);

         int count;
         for(count = 0; count < maxcount && this.nextIndex() >= 0; ++count) {
         }

         return count;
      }
   }

   private class IndexDelegate extends PySequence.DefaultIndexDelegate {
      private IndexDelegate() {
         super();
      }

      public void checkIdxAndSetSlice(PySlice slice, PyObject value) {
         if (value.__len__() != 0) {
            super.checkIdxAndSetSlice(slice, value);
         } else {
            this.checkIdxAndDelItem(slice);
         }

      }

      protected void delSlice(int[] indices) {
         BaseBytes.this.delslice(indices[0], indices[1], indices[2], indices[3]);
      }

      // $FF: synthetic method
      IndexDelegate(Object x1) {
         this();
      }
   }

   protected static class FragmentList extends LinkedList {
      int totalCount = 0;

      void loadFrom(Iterable iter) throws PyException {
         int fragSize = 8;
         Fragment curr = null;

         try {
            Iterator var4 = iter.iterator();

            while(var4.hasNext()) {
               PyObject value = (PyObject)var4.next();
               if (curr == null) {
                  curr = new Fragment(fragSize);
                  this.add(curr);
                  if (fragSize < 1024) {
                     fragSize <<= 1;
                  }
               }

               if (curr.isFilledBy(value)) {
                  this.totalCount += curr.count;
                  curr = null;
               }
            }
         } catch (OutOfMemoryError var6) {
            throw Py.MemoryError(var6.getMessage());
         }

         if (curr != null) {
            this.totalCount += curr.count;
         }

      }

      void emptyInto(byte[] target, int p) {
         Fragment frag;
         for(Iterator var3 = this.iterator(); var3.hasNext(); p += frag.count) {
            frag = (Fragment)var3.next();
            System.arraycopy(frag.storage, 0, target, p, frag.count);
         }

         this.clear();
         this.totalCount = 0;
      }

      void emptyInto(byte[] target, int start, int step) {
         int p = start;
         Iterator var5 = this.iterator();

         while(var5.hasNext()) {
            Fragment frag = (Fragment)var5.next();

            for(int i = 0; i < frag.count; ++i) {
               target[p] = frag.storage[i];
               p += step;
            }
         }

         this.clear();
         this.totalCount = 0;
      }
   }

   protected static class Fragment {
      static final int MINSIZE = 8;
      static final int MAXSIZE = 1024;
      byte[] storage;
      int count = 0;

      Fragment(int size) {
         this.storage = new byte[size];
      }

      boolean isFilledBy(PyObject value) {
         this.storage[this.count++] = BaseBytes.byteCheck(value);
         return this.count == this.storage.length;
      }
   }
}
