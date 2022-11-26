package org.python.core;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import org.python.core.buffer.BaseBuffer;
import org.python.core.buffer.SimpleWritableBuffer;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "bytearray",
   base = PyObject.class,
   doc = "bytearray(iterable_of_ints) -> bytearray.\nbytearray(string, encoding[, errors]) -> bytearray.\nbytearray(bytes_or_bytearray) -> mutable copy of bytes_or_bytearray.\nbytearray(memory_view) -> bytearray.\n\nConstruct an mutable bytearray object from:\n  - an iterable yielding integers in range(256)\n  - a text string encoded using the specified encoding\n  - a bytes or a bytearray object\n  - any object implementing the buffer API.\n\nbytearray(int) -> bytearray.\n\nConstruct a zero-initialized bytearray of the given length."
)
public class PyByteArray extends BaseBytes implements BufferProtocol {
   public static final PyType TYPE;
   private WeakReference export;

   public PyByteArray(PyType type) {
      super(type);
   }

   public PyByteArray() {
      super(TYPE);
   }

   public PyByteArray(int size) {
      super(TYPE);
      this.init(size);
   }

   public PyByteArray(int[] value) {
      super(TYPE, value);
   }

   public PyByteArray(BaseBytes value) {
      super(TYPE);
      this.init(value);
   }

   PyByteArray(PyBuffer value) {
      super(TYPE);
      this.init(value);
   }

   public PyByteArray(BufferProtocol value) {
      super(TYPE);
      this.init(value);
   }

   public PyByteArray(Iterable value) {
      super(TYPE);
      this.init(value);
   }

   public PyByteArray(PyString arg, PyObject encoding, PyObject errors) {
      super(TYPE);
      this.init(arg, encoding, errors);
   }

   public PyByteArray(PyString arg, String encoding, String errors) {
      super(TYPE);
      this.init(arg, encoding, errors);
   }

   public PyByteArray(PyString arg) {
      super(TYPE);
      this.init(arg, (String)null, (String)null);
   }

   public PyByteArray(byte[] storage) {
      super(TYPE);
      this.setStorage(storage);
   }

   public PyByteArray(byte[] storage, int size) {
      super(TYPE);
      this.setStorage(storage, size);
   }

   public PyByteArray(PyObject arg) throws PyException {
      super(TYPE);
      this.init(arg);
   }

   public synchronized PyBuffer getBuffer(int flags) {
      BaseBuffer pybuf = this.getExistingBuffer(flags);
      if (pybuf == null) {
         pybuf = new SimpleWritableBuffer(flags, this, this.storage, this.offset, this.size);
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

   protected void resizeCheck() throws PyException {
      if (this.export != null) {
         PyBuffer pybuf = (PyBuffer)this.export.get();
         if (pybuf != null && !pybuf.isReleased()) {
            throw Py.BufferError("Existing exports of data: object cannot be re-sized");
         }

         this.export = null;
      }

   }

   protected synchronized PyByteArray getslice(int start, int stop, int step) {
      if (step == 1) {
         return this.getslice(start, stop);
      } else {
         int n = sliceLength(start, stop, (long)step);
         PyByteArray ret = new PyByteArray(n);
         n += ret.offset;
         byte[] dst = ret.storage;
         int io = start + this.offset;

         for(int jo = ret.offset; jo < n; ++jo) {
            dst[jo] = this.storage[io];
            io += step;
         }

         return ret;
      }
   }

   protected synchronized PyByteArray getslice(int start, int stop) {
      int n = stop - start;
      if (n <= 0) {
         return new PyByteArray();
      } else {
         PyByteArray ret = new PyByteArray(n);
         System.arraycopy(this.storage, this.offset + start, ret.storage, ret.offset, n);
         return ret;
      }
   }

   protected synchronized PyByteArray repeat(int count) {
      PyByteArray ret = new PyByteArray();
      ret.setStorage(this.repeatImpl(count));
      return ret;
   }

   protected synchronized void irepeat(int count) {
      if (this.size != 0 && count != 1) {
         if (count <= 0) {
            this.resizeCheck();
            this.setStorage(emptyStorage);
         } else {
            int orginalSize = this.size;
            this.storageExtend(orginalSize * (count - 1));
            int b;
            int p;
            if (orginalSize == 1) {
               b = this.storage[this.offset];
               p = 1;

               for(int p = this.offset + 1; p < count; ++p) {
                  this.storage[p++] = (byte)b;
               }
            } else {
               b = 1;

               for(p = this.offset + orginalSize; b < count; p += orginalSize) {
                  System.arraycopy(this.storage, this.offset, this.storage, p, orginalSize);
                  ++b;
               }
            }
         }
      }

   }

   public synchronized void pyset(int index, PyObject value) throws PyException {
      this.storage[index + this.offset] = byteCheck(value);
   }

   public synchronized void pyinsert(int index, PyObject element) {
      this.storageReplace(index, 0, 1);
      this.storage[this.offset + index] = byteCheck(element);
   }

   protected synchronized void setslice(int start, int stop, int step, PyObject value) {
      if (step == 1 && stop < start) {
         stop = start;
      }

      if (value instanceof PyString) {
         this.setslice(start, stop, step, (PyString)value);
      } else if (value.isIndex()) {
         this.setslice(start, stop, step, value.asIndex(Py.OverflowError));
      } else if (value instanceof BaseBytes) {
         this.setslice(start, stop, step, (BaseBytes)value);
      } else if (value instanceof BufferProtocol) {
         this.setslice(start, stop, step, (BufferProtocol)value);
      } else {
         this.setslice(start, stop, step, value.asIterable());
      }

   }

   private void setslice(int start, int stop, int step, int len) throws PyException {
      if (step == 1) {
         this.storageReplace(start, stop - start, len);
         Arrays.fill(this.storage, start + this.offset, start + this.offset + len, (byte)0);
      } else {
         int n = sliceLength(start, stop, (long)step);
         if (n != len) {
            throw SliceSizeError("bytes", len, n);
         }

         for(int io = start + this.offset; n > 0; --n) {
            this.storage[io] = 0;
            io += step;
         }
      }

   }

   private void setslice(int start, int stop, int step, PyString value) throws PyException {
      if (value instanceof PyUnicode) {
         throw Py.TypeError("can't set bytearray slice from unicode");
      } else {
         String v = value.asString();
         int len = v.length();
         if (step == 1) {
            this.storageReplace(start, stop - start, len);
            this.setBytes(start, v);
         } else {
            int n = sliceLength(start, stop, (long)step);
            if (n != len) {
               throw SliceSizeError("bytes", len, n);
            }

            this.setBytes(start, step, v);
         }

      }
   }

   private void setslice(int start, int stop, int step, BufferProtocol value) throws PyException {
      PyBuffer view = value.getBuffer(284);
      Throwable var6 = null;

      try {
         int len = view.getLen();
         if (step == 1) {
            this.storageReplace(start, stop - start, len);
            view.copyTo(this.storage, start + this.offset);
         } else {
            int n = sliceLength(start, stop, (long)step);
            if (n != len) {
               throw SliceSizeError("bytes", len, n);
            }

            int io = start + this.offset;

            for(int j = 0; j < n; ++j) {
               this.storage[io] = view.byteAt(j);
               io += step;
            }
         }
      } catch (Throwable var18) {
         var6 = var18;
         throw var18;
      } finally {
         if (view != null) {
            if (var6 != null) {
               try {
                  view.close();
               } catch (Throwable var17) {
                  var6.addSuppressed(var17);
               }
            } else {
               view.close();
            }
         }

      }

   }

   private void setslice(int start, int stop, int step, BaseBytes value) throws PyException {
      if (value == this) {
         value = new PyByteArray((BaseBytes)value);
      }

      int len = ((BaseBytes)value).size;
      if (step == 1) {
         this.storageReplace(start, stop - start, len);
         System.arraycopy(((BaseBytes)value).storage, ((BaseBytes)value).offset, this.storage, start + this.offset, len);
      } else {
         int n = sliceLength(start, stop, (long)step);
         if (n != len) {
            throw SliceSizeError("bytes", len, n);
         }

         int no = n + ((BaseBytes)value).offset;
         int io = start + this.offset;

         for(int jo = ((BaseBytes)value).offset; jo < no; ++jo) {
            this.storage[io] = ((BaseBytes)value).storage[jo];
            io += step;
         }
      }

   }

   private void setslice(int start, int stop, int step, Iterable iter) {
      BaseBytes.FragmentList fragList = new BaseBytes.FragmentList();
      fragList.loadFrom(iter);
      if (step == 1) {
         this.storageReplace(start, stop - start, fragList.totalCount);
         if (fragList.totalCount > 0) {
            fragList.emptyInto(this.storage, start + this.offset);
         }
      } else {
         int n = sliceLength(start, stop, (long)step);
         if (n != fragList.totalCount) {
            throw SliceSizeError("bytes", fragList.totalCount, n);
         }

         fragList.emptyInto(this.storage, start + this.offset, step);
      }

   }

   protected synchronized void del(int index) {
      this.storageDelete(index, 1);
   }

   protected synchronized void delRange(int start, int stop) {
      this.storageDelete(start, stop - start);
   }

   protected synchronized void delslice(int start, int stop, int step, int n) {
      this.resizeCheck();
      if (step == 1) {
         this.storageDelete(start, n);
      } else if (step == -1) {
         this.storageDelete(stop + 1, n);
      } else {
         int p;
         int m;
         if (step > 1) {
            p = start;
            m = step - 1;
         } else {
            p = start + (n - 1) * step;
            m = -1 - step;
         }

         p += this.offset;

         for(int i = 1; i < n; ++i) {
            int q = p + i;

            for(int j = 0; j < m; ++j) {
               this.storage[p++] = this.storage[q++];
            }
         }

         this.storageDelete(p - this.offset, n);
      }

   }

   public static PyException SliceSizeError(String valueType, int valueSize, int sliceSize) {
      String fmt = "attempt to assign %s of size %d to extended slice of size %d";
      return Py.ValueError(String.format(fmt, valueType, valueSize, sliceSize));
   }

   @ExposedNew
   final synchronized void bytearray___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("bytearray", args, kwds, "source", "encoding", "errors");
      PyObject arg = ap.getPyObject(0, (PyObject)null);
      PyObject encoding = ap.getPyObjectByType(1, PyBaseString.TYPE, (PyObject)null);
      PyObject errors = ap.getPyObjectByType(2, PyBaseString.TYPE, (PyObject)null);
      if (encoding == null && errors == null) {
         this.init(arg);
      } else {
         if (arg == null || !(arg instanceof PyString)) {
            throw Py.TypeError("encoding or errors without sequence argument");
         }

         this.init((PyString)arg, encoding, errors);
      }

   }

   protected BaseBytes.Builder getBuilder(int capacity) {
      return new BaseBytes.Builder(capacity) {
         PyByteArray getResult() {
            return new PyByteArray(this.getStorage(), this.getSize());
         }
      };
   }

   public PyObject __eq__(PyObject other) {
      return this.basebytes___eq__(other);
   }

   public PyObject __ne__(PyObject other) {
      return this.basebytes___ne__(other);
   }

   public PyObject __lt__(PyObject other) {
      return this.basebytes___lt__(other);
   }

   public PyObject __le__(PyObject other) {
      return this.basebytes___le__(other);
   }

   public PyObject __ge__(PyObject other) {
      return this.basebytes___ge__(other);
   }

   public PyObject __gt__(PyObject other) {
      return this.basebytes___gt__(other);
   }

   final synchronized PyObject bytearray___eq__(PyObject other) {
      return this.basebytes___eq__(other);
   }

   final synchronized PyObject bytearray___ne__(PyObject other) {
      return this.basebytes___ne__(other);
   }

   final synchronized PyObject bytearray___lt__(PyObject other) {
      return this.basebytes___lt__(other);
   }

   final synchronized PyObject bytearray___le__(PyObject other) {
      return this.basebytes___le__(other);
   }

   final synchronized PyObject bytearray___ge__(PyObject other) {
      return this.basebytes___ge__(other);
   }

   final synchronized PyObject bytearray___gt__(PyObject other) {
      return this.basebytes___gt__(other);
   }

   public PyObject __add__(PyObject o) {
      return this.bytearray___add__(o);
   }

   final synchronized PyObject bytearray___add__(PyObject o) {
      PyByteArray sum = null;
      if (o instanceof BaseBytes) {
         BaseBytes ob = (BaseBytes)o;
         sum = new PyByteArray(this.size + ob.size);
         System.arraycopy(this.storage, this.offset, sum.storage, sum.offset, this.size);
         System.arraycopy(ob.storage, ob.offset, sum.storage, sum.offset + this.size, ob.size);
      } else {
         if (o.getType() != PyString.TYPE) {
            throw ConcatenationTypeError(TYPE, o.getType());
         }

         PyString os = (PyString)o;
         sum = new PyByteArray(this.size + os.__len__());
         System.arraycopy(this.storage, this.offset, sum.storage, sum.offset, this.size);
         sum.setslice(this.size, sum.size, 1, (PyString)os);
      }

      return sum;
   }

   public int __alloc__() {
      return this.bytearray___alloc__();
   }

   final int bytearray___alloc__() {
      return this.storage.length;
   }

   public PyObject __imul__(PyObject n) {
      return this.bytearray___imul__(n);
   }

   final PyObject bytearray___imul__(PyObject n) {
      if (!n.isIndex()) {
         return null;
      } else {
         this.irepeat(n.asIndex(Py.OverflowError));
         return this;
      }
   }

   public PyObject __mul__(PyObject n) {
      return this.bytearray___mul__(n);
   }

   final PyObject bytearray___mul__(PyObject n) {
      return !n.isIndex() ? null : this.repeat(n.asIndex(Py.OverflowError));
   }

   public PyObject __rmul__(PyObject n) {
      return this.bytearray___rmul__(n);
   }

   final PyObject bytearray___rmul__(PyObject n) {
      return !n.isIndex() ? null : this.repeat(n.asIndex(Py.OverflowError));
   }

   public void append(byte element) {
      this.storageExtend(1);
      this.storage[this.offset + this.size - 1] = element;
   }

   public void append(PyObject element) {
      this.bytearray_append(element);
   }

   final synchronized void bytearray_append(PyObject element) {
      this.storageExtend(1);
      this.storage[this.offset + this.size - 1] = byteCheck(element);
   }

   public boolean __contains__(PyObject o) {
      return this.basebytes___contains__(o);
   }

   final boolean bytearray___contains__(PyObject o) {
      return this.basebytes___contains__(o);
   }

   final PyObject bytearray_decode(PyObject[] args, String[] keywords) {
      return this.basebytes_decode(args, keywords);
   }

   public PyByteArray center(int width) {
      return (PyByteArray)this.basebytes_center(width, " ");
   }

   public PyByteArray center(int width, String fillchar) {
      return (PyByteArray)this.basebytes_center(width, fillchar);
   }

   final PyByteArray bytearray_center(int width, String fillchar) {
      return (PyByteArray)this.basebytes_center(width, fillchar);
   }

   public int count(PyObject sub) {
      return this.basebytes_count(sub, (PyObject)null, (PyObject)null);
   }

   public int count(PyObject sub, PyObject start) {
      return this.basebytes_count(sub, start, (PyObject)null);
   }

   public int count(PyObject sub, PyObject start, PyObject end) {
      return this.basebytes_count(sub, start, end);
   }

   final int bytearray_count(PyObject sub, PyObject start, PyObject end) {
      return this.basebytes_count(sub, start, end);
   }

   public boolean endswith(PyObject suffix) {
      return this.basebytes_starts_or_endswith(suffix, (PyObject)null, (PyObject)null, true);
   }

   public boolean endswith(PyObject suffix, PyObject start) {
      return this.basebytes_starts_or_endswith(suffix, start, (PyObject)null, true);
   }

   public boolean endswith(PyObject suffix, PyObject start, PyObject end) {
      return this.basebytes_starts_or_endswith(suffix, start, end, true);
   }

   final boolean bytearray_endswith(PyObject suffix, PyObject start, PyObject end) {
      return this.basebytes_starts_or_endswith(suffix, start, end, true);
   }

   public PyByteArray expandtabs() {
      return (PyByteArray)this.basebytes_expandtabs(8);
   }

   public PyByteArray expandtabs(int tabsize) {
      return (PyByteArray)this.basebytes_expandtabs(tabsize);
   }

   final PyByteArray bytearray_expandtabs(int tabsize) {
      return (PyByteArray)this.basebytes_expandtabs(tabsize);
   }

   public void extend(PyObject o) {
      this.bytearray_extend(o);
   }

   final synchronized void bytearray_extend(PyObject o) {
      o.__iter__();
      this.setslice(this.size, this.size, 1, (PyObject)o);
   }

   public int find(PyObject sub) {
      return this.basebytes_find(sub, (PyObject)null, (PyObject)null);
   }

   public int find(PyObject sub, PyObject start) {
      return this.basebytes_find(sub, start, (PyObject)null);
   }

   public int find(PyObject sub, PyObject start, PyObject end) {
      return this.basebytes_find(sub, start, end);
   }

   final int bytearray_find(PyObject sub, PyObject start, PyObject end) {
      return this.basebytes_find(sub, start, end);
   }

   static PyByteArray fromhex(String hex) throws PyException {
      return bytearray_fromhex(TYPE, hex);
   }

   static PyByteArray bytearray_fromhex(PyType type, String hex) {
      PyByteArray result = new PyByteArray();
      basebytes_fromhex(result, hex);
      return result;
   }

   final synchronized PyObject bytearray___getitem__(PyObject index) {
      return this.delegator.checkIdxAndGetItem(index);
   }

   public PyObject __iadd__(PyObject o) {
      return this.bytearray___iadd__(o);
   }

   final synchronized PyObject bytearray___iadd__(PyObject o) {
      PyType oType = o.getType();
      if (oType == TYPE) {
         this.setslice(this.size, this.size, 1, (BaseBytes)((BaseBytes)o));
      } else {
         if (oType != PyString.TYPE) {
            throw ConcatenationTypeError(oType, TYPE);
         }

         this.setslice(this.size, this.size, 1, (PyString)((PyString)o));
      }

      return this;
   }

   public int index(PyObject sub) {
      return this.bytearray_index(sub, (PyObject)null, (PyObject)null);
   }

   public int index(PyObject sub, PyObject start) {
      return this.bytearray_index(sub, start, (PyObject)null);
   }

   public int hashCode() throws PyException {
      return this.bytearray___hash__();
   }

   final int bytearray___hash__() {
      throw Py.TypeError(String.format("unhashable type: '%.200s'", this.getType().fastGetName()));
   }

   public int index(PyObject sub, PyObject start, PyObject end) throws PyException {
      return this.bytearray_index(sub, start, end);
   }

   final int bytearray_index(PyObject sub, PyObject start, PyObject end) {
      int pos = this.basebytes_find(sub, start, end);
      if (pos < 0) {
         throw Py.ValueError("subsection not found");
      } else {
         return pos;
      }
   }

   public void insert(PyObject index, PyObject value) {
      this.bytearray_insert(index, value);
   }

   final synchronized void bytearray_insert(PyObject index, PyObject value) {
      this.pyinsert(this.boundToSequence(index.asIndex()), value);
   }

   final boolean bytearray_isalnum() {
      return this.basebytes_isalnum();
   }

   final boolean bytearray_isalpha() {
      return this.basebytes_isalpha();
   }

   final boolean bytearray_isdigit() {
      return this.basebytes_isdigit();
   }

   final boolean bytearray_islower() {
      return this.basebytes_islower();
   }

   final boolean bytearray_isspace() {
      return this.basebytes_isspace();
   }

   final boolean bytearray_istitle() {
      return this.basebytes_istitle();
   }

   final boolean bytearray_isupper() {
      return this.basebytes_isupper();
   }

   final PyByteArray bytearray_capitalize() {
      return (PyByteArray)this.basebytes_capitalize();
   }

   final PyByteArray bytearray_lower() {
      return (PyByteArray)this.basebytes_lower();
   }

   final PyByteArray bytearray_swapcase() {
      return (PyByteArray)this.basebytes_swapcase();
   }

   final PyByteArray bytearray_title() {
      return (PyByteArray)this.basebytes_title();
   }

   final PyByteArray bytearray_upper() {
      return (PyByteArray)this.basebytes_upper();
   }

   public PyByteArray join(PyObject iterable) {
      return this.bytearray_join(iterable);
   }

   final PyByteArray bytearray_join(PyObject iterable) {
      return this.basebytes_join(iterable.asIterable());
   }

   final int bytearray___len__() {
      return super.__len__();
   }

   public PyByteArray ljust(int width) {
      return (PyByteArray)this.basebytes_ljust(width, " ");
   }

   public PyByteArray ljust(int width, String fillchar) {
      return (PyByteArray)this.basebytes_ljust(width, fillchar);
   }

   final PyByteArray bytearray_ljust(int width, String fillchar) {
      return (PyByteArray)this.basebytes_ljust(width, fillchar);
   }

   public PyByteArray lstrip() {
      return this.bytearray_lstrip((PyObject)null);
   }

   public PyByteArray lstrip(PyObject bytes) {
      return this.bytearray_lstrip(bytes);
   }

   final synchronized PyByteArray bytearray_lstrip(PyObject bytes) {
      int left;
      if (bytes != null && bytes != Py.None) {
         BaseBytes.ByteSet byteSet = new BaseBytes.ByteSet(getViewOrError(bytes));
         left = this.lstripIndex(byteSet);
      } else {
         left = this.lstripIndex();
      }

      return this.getslice(left, this.size);
   }

   final PyTuple bytearray_partition(PyObject sep) {
      return this.basebytes_partition(sep);
   }

   public PyInteger pop() {
      return this.bytearray_pop(-1);
   }

   public PyInteger pop(int i) {
      return this.bytearray_pop(i);
   }

   final synchronized PyInteger bytearray_pop(int i) {
      if (this.size == 0) {
         throw Py.IndexError("pop from empty list");
      } else {
         if (i < 0) {
            i += this.size;
         }

         return this.remove(i);
      }
   }

   final PyObject bytearray___reduce__() {
      return this.basebytes___reduce__();
   }

   public void remove(PyObject o) throws PyException {
      this.bytearray_remove(o);
   }

   final synchronized void bytearray_remove(PyObject o) {
      byte b = byteCheck(o);
      int pos = this.index(b);
      if (pos < 0) {
         throw Py.ValueError("value not found in bytearray");
      } else {
         this.storageDelete(pos, 1);
      }
   }

   public PyByteArray replace(PyObject oldB, PyObject newB) {
      return this.basebytes_replace(oldB, newB, -1);
   }

   public PyByteArray replace(PyObject oldB, PyObject newB, int maxcount) {
      return this.basebytes_replace(oldB, newB, maxcount);
   }

   final PyByteArray bytearray_replace(PyObject oldB, PyObject newB, PyObject count) {
      int maxcount = count == null ? -1 : count.asInt();
      return this.basebytes_replace(oldB, newB, maxcount);
   }

   public void reverse() {
      this.bytearray_reverse();
   }

   final synchronized void bytearray_reverse() {
      int a = this.offset;
      int b = this.offset + this.size;

      while(true) {
         --b;
         if (b <= a) {
            return;
         }

         byte t = this.storage[b];
         this.storage[b] = this.storage[a];
         this.storage[a++] = t;
      }
   }

   public int rfind(PyObject sub) {
      return this.basebytes_rfind(sub, (PyObject)null, (PyObject)null);
   }

   public int rfind(PyObject sub, PyObject start) {
      return this.basebytes_rfind(sub, start, (PyObject)null);
   }

   public int rfind(PyObject sub, PyObject start, PyObject end) {
      return this.basebytes_rfind(sub, start, end);
   }

   final int bytearray_rfind(PyObject sub, PyObject start, PyObject end) {
      return this.basebytes_rfind(sub, start, end);
   }

   public int rindex(PyObject sub) {
      return this.bytearray_rindex(sub, (PyObject)null, (PyObject)null);
   }

   public int rindex(PyObject sub, PyObject start) {
      return this.bytearray_rindex(sub, start, (PyObject)null);
   }

   public PyByteArray rjust(int width) {
      return (PyByteArray)this.basebytes_rjust(width, " ");
   }

   public PyByteArray rjust(int width, String fillchar) {
      return (PyByteArray)this.basebytes_rjust(width, fillchar);
   }

   final PyByteArray bytearray_rjust(int width, String fillchar) {
      return (PyByteArray)this.basebytes_rjust(width, fillchar);
   }

   public int rindex(PyObject sub, PyObject start, PyObject end) {
      return this.bytearray_rindex(sub, start, end);
   }

   final int bytearray_rindex(PyObject sub, PyObject start, PyObject end) {
      int pos = this.basebytes_rfind(sub, start, end);
      if (pos < 0) {
         throw Py.ValueError("subsection not found");
      } else {
         return pos;
      }
   }

   final PyTuple bytearray_rpartition(PyObject sep) {
      return this.basebytes_rpartition(sep);
   }

   final PyList bytearray_rsplit(PyObject sep, int maxsplit) {
      return this.basebytes_rsplit(sep, maxsplit);
   }

   public PyByteArray rstrip() {
      return this.bytearray_rstrip((PyObject)null);
   }

   public PyByteArray rstrip(PyObject bytes) {
      return this.bytearray_rstrip(bytes);
   }

   final synchronized PyByteArray bytearray_rstrip(PyObject bytes) {
      int right;
      if (bytes != null && bytes != Py.None) {
         BaseBytes.ByteSet byteSet = new BaseBytes.ByteSet(getViewOrError(bytes));
         right = this.rstripIndex(byteSet);
      } else {
         right = this.rstripIndex();
      }

      return this.getslice(0, right);
   }

   final PyList bytearray_split(PyObject sep, int maxsplit) {
      return this.basebytes_split(sep, maxsplit);
   }

   final PyList bytearray_splitlines(boolean keepends) {
      return this.basebytes_splitlines(keepends);
   }

   public boolean startswith(PyObject prefix) {
      return this.basebytes_starts_or_endswith(prefix, (PyObject)null, (PyObject)null, false);
   }

   public boolean startswith(PyObject prefix, PyObject start) {
      return this.basebytes_starts_or_endswith(prefix, start, (PyObject)null, false);
   }

   public boolean startswith(PyObject prefix, PyObject start, PyObject end) {
      return this.basebytes_starts_or_endswith(prefix, start, end, false);
   }

   final boolean bytearray_startswith(PyObject prefix, PyObject start, PyObject end) {
      return this.basebytes_starts_or_endswith(prefix, start, end, false);
   }

   public PyByteArray strip() {
      return this.bytearray_strip((PyObject)null);
   }

   public PyByteArray strip(PyObject bytes) {
      return this.bytearray_strip(bytes);
   }

   final synchronized PyByteArray bytearray_strip(PyObject bytes) {
      int left;
      int right;
      if (bytes != null && bytes != Py.None) {
         BaseBytes.ByteSet byteSet = new BaseBytes.ByteSet(getViewOrError(bytes));
         left = this.lstripIndex(byteSet);
         right = left == this.size ? this.size : this.rstripIndex(byteSet);
      } else {
         left = this.lstripIndex();
         right = left == this.size ? this.size : this.rstripIndex();
      }

      return this.getslice(left, right);
   }

   final synchronized void bytearray___setitem__(PyObject index, PyObject value) {
      this.delegator.checkIdxAndSetItem(index, value);
   }

   public String toString() {
      return this.bytearray_repr();
   }

   final synchronized String bytearray_repr() {
      return this.basebytes_repr("bytearray(b", ")");
   }

   public PyString __str__() {
      return this.bytearray_str();
   }

   final PyString bytearray_str() {
      return new PyString(this.asString());
   }

   public PyByteArray translate(PyObject table) {
      return this.bytearray_translate(table, (PyObject)null);
   }

   public PyByteArray translate(PyObject table, PyObject deletechars) {
      return this.bytearray_translate(table, deletechars);
   }

   final PyByteArray bytearray_translate(PyObject table, PyObject deletechars) {
      PyBuffer tab = this.getTranslationTable(table);
      Throwable var4 = null;

      PyByteArray var38;
      try {
         PyByteArray result = new PyByteArray();
         if (deletechars != null) {
            PyBuffer d = getViewOrError(deletechars);
            Throwable var39 = null;

            try {
               BaseBytes.ByteSet del = new BaseBytes.ByteSet(d);
               int limit = this.offset + this.size;
               int i;
               int b;
               if (tab == null) {
                  for(i = this.offset; i < limit; ++i) {
                     b = this.storage[i] & 255;
                     if (!del.contains(b)) {
                        result.append((byte)b);
                     }
                  }
               } else {
                  for(i = this.offset; i < limit; ++i) {
                     b = this.storage[i] & 255;
                     if (!del.contains(b)) {
                        result.append(tab.byteAt(b));
                     }
                  }
               }
            } catch (Throwable var33) {
               var39 = var33;
               throw var33;
            } finally {
               if (d != null) {
                  if (var39 != null) {
                     try {
                        d.close();
                     } catch (Throwable var32) {
                        var39.addSuppressed(var32);
                     }
                  } else {
                     d.close();
                  }
               }

            }
         } else if (tab == null) {
            result.extend(this);
         } else {
            int limit = this.offset + this.size;

            for(int i = this.offset; i < limit; ++i) {
               int b = this.storage[i] & 255;
               result.append(tab.byteAt(b));
            }
         }

         var38 = result;
      } catch (Throwable var35) {
         var4 = var35;
         throw var35;
      } finally {
         if (tab != null) {
            if (var4 != null) {
               try {
                  tab.close();
               } catch (Throwable var31) {
                  var4.addSuppressed(var31);
               }
            } else {
               tab.close();
            }
         }

      }

      return var38;
   }

   private PyBuffer getTranslationTable(PyObject table) throws PyException {
      PyBuffer tab = null;
      if (table != null && table != Py.None) {
         tab = getViewOrError(table);
         if (tab.getLen() != 256) {
            throw Py.ValueError("translation table must be 256 bytes long");
         }
      }

      return tab;
   }

   public PyByteArray zfill(int width) {
      return (PyByteArray)this.basebytes_zfill(width);
   }

   final PyByteArray bytearray_zfill(int width) {
      return (PyByteArray)this.basebytes_zfill(width);
   }

   private final int recLength(int needed) {
      int L = this.storage.length;
      return needed <= L && needed * 2 >= L ? L : roundUp(needed);
   }

   protected void newStorage(int needed) {
      if (needed > 0) {
         int L = this.recLength(needed);

         try {
            byte[] s = new byte[L];
            this.setStorage(s, needed, (L - needed) / 2);
         } catch (OutOfMemoryError var4) {
            throw Py.MemoryError(var4.getMessage());
         }
      } else {
         this.setStorage(emptyStorage);
      }

   }

   private void storageReplace(int a, int d, int e) {
      int b = this.size - (a + d);
      int c = e - d;
      if (c != 0) {
         if (c > 0 && b == 0) {
            this.storageExtend(c);
         } else {
            this.resizeCheck();
            int L = this.storage.length;
            int f = this.offset;
            int s2 = a + e + b;
            int L2 = this.recLength(s2);
            if (L2 == L) {
               int f2;
               if (a <= b) {
                  f2 = f - c;
                  if (f2 >= 0) {
                     if (a > 0) {
                        System.arraycopy(this.storage, f, this.storage, f2, a);
                     }

                     this.offset = f2;
                     this.size = s2;
                  } else {
                     this.newStorageAvoided(a, d, b, e);
                  }
               } else {
                  f2 = f + a + e;
                  if (f2 + b <= L) {
                     if (b > 0) {
                        System.arraycopy(this.storage, f2 - c, this.storage, f2, b);
                     }

                     this.size = s2;
                  } else {
                     this.newStorageAvoided(a, d, b, e);
                  }
               }
            } else if (L2 > 0) {
               this.newStorage(L2, a, d, b, e);
            } else {
               this.setStorage(emptyStorage);
            }

         }
      }
   }

   private void newStorageAvoided(int a, int d, int b, int e) {
      int f = this.offset;
      int g = f + a + d;
      int s2 = a + e + b;
      int f2;
      if (a == b) {
         f2 = (this.storage.length - s2) / 2;
      } else {
         long spare = (long)(this.storage.length - s2);
         f2 = (int)(spare * (long)b / (long)(a + b));
      }

      int g2 = f2 + a + e;
      if (f2 + a > g) {
         if (b > 0) {
            System.arraycopy(this.storage, g, this.storage, g2, b);
         }

         if (a > 0) {
            System.arraycopy(this.storage, f, this.storage, f2, a);
         }
      } else {
         if (a > 0) {
            System.arraycopy(this.storage, f, this.storage, f2, a);
         }

         if (b > 0) {
            System.arraycopy(this.storage, g, this.storage, g2, b);
         }
      }

      this.size = s2;
      this.offset = f2;
   }

   private void newStorage(int L2, int a, int d, int b, int e) {
      int f = this.offset;
      int g = f + a + d;
      int s2 = a + e + b;
      byte[] newStorage = new byte[L2];
      int f2;
      if (a == b) {
         f2 = (L2 - s2) / 2;
      } else {
         long spare = (long)(L2 - s2);
         f2 = (int)(spare * (long)b / (long)(a + b));
      }

      if (a > 0) {
         System.arraycopy(this.storage, f, newStorage, f2, a);
      }

      if (b > 0) {
         System.arraycopy(this.storage, g, newStorage, f2 + a + e, b);
      }

      this.setStorage(newStorage, s2, f2);
   }

   private void storageExtend(int e) {
      if (e != 0) {
         this.resizeCheck();
         int L = this.storage.length;
         int f = this.offset;
         int s2 = this.size + e;
         int L2 = this.recLength(s2);
         if (L2 <= L) {
            int g2 = f + s2;
            if (g2 > L) {
               if (this.size > 0) {
                  System.arraycopy(this.storage, this.offset, this.storage, 0, this.size);
               }

               this.offset = 0;
            }

            this.size = s2;
         } else {
            byte[] newStorage = new byte[L2];
            if (this.size > 0) {
               System.arraycopy(this.storage, f, newStorage, 0, this.size);
            }

            this.setStorage(newStorage, s2);
         }

      }
   }

   private void storageDelete(int a, int d) {
      if (d != 0) {
         this.resizeCheck();
         int L = this.storage.length;
         int f = this.offset;
         int b = this.size - (a + d);
         int s2 = a + b;
         int L2 = this.recLength(s2);
         int g;
         if (L2 == L) {
            if (a <= b) {
               g = f + d;
               if (a > 0) {
                  System.arraycopy(this.storage, f, this.storage, g, a);
               }

               this.offset = g;
               this.size = s2;
            } else {
               g = f + a;
               if (b > 0) {
                  System.arraycopy(this.storage, g + d, this.storage, g, b);
               }

               this.size = s2;
            }
         } else if (L2 > 0) {
            g = f + a + d;
            int f2 = (L2 - s2) / 2;
            byte[] newStorage = new byte[L2];
            if (a > 0) {
               System.arraycopy(this.storage, f, newStorage, f2, a);
            }

            if (b > 0) {
               System.arraycopy(this.storage, g, newStorage, f2 + a, b);
            }

            this.setStorage(newStorage, s2, f2);
         } else {
            this.setStorage(emptyStorage);
         }

      }
   }

   static {
      PyType.addBuilder(PyByteArray.class, new PyExposer());
      TYPE = PyType.fromClass(PyByteArray.class);
   }

   private static class bytearray___init___exposer extends PyBuiltinMethod {
      public bytearray___init___exposer(String var1) {
         super(var1);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public bytearray___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyByteArray)this.self).bytearray___init__(var1, var2);
         return Py.None;
      }
   }

   private static class bytearray___eq___exposer extends PyBuiltinMethodNarrow {
      public bytearray___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public bytearray___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyByteArray)this.self).bytearray___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bytearray___ne___exposer extends PyBuiltinMethodNarrow {
      public bytearray___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public bytearray___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyByteArray)this.self).bytearray___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bytearray___lt___exposer extends PyBuiltinMethodNarrow {
      public bytearray___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public bytearray___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyByteArray)this.self).bytearray___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bytearray___le___exposer extends PyBuiltinMethodNarrow {
      public bytearray___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public bytearray___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyByteArray)this.self).bytearray___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bytearray___ge___exposer extends PyBuiltinMethodNarrow {
      public bytearray___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public bytearray___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyByteArray)this.self).bytearray___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bytearray___gt___exposer extends PyBuiltinMethodNarrow {
      public bytearray___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public bytearray___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyByteArray)this.self).bytearray___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bytearray___add___exposer extends PyBuiltinMethodNarrow {
      public bytearray___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public bytearray___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyByteArray)this.self).bytearray___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bytearray___alloc___exposer extends PyBuiltinMethodNarrow {
      public bytearray___alloc___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.__alloc__() -> int\n\nReturns the number of bytes actually allocated.";
      }

      public bytearray___alloc___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.__alloc__() -> int\n\nReturns the number of bytes actually allocated.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___alloc___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyByteArray)this.self).bytearray___alloc__());
      }
   }

   private static class bytearray___imul___exposer extends PyBuiltinMethodNarrow {
      public bytearray___imul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__imul__(y) <==> x*=y";
      }

      public bytearray___imul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__imul__(y) <==> x*=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___imul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyByteArray)this.self).bytearray___imul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bytearray___mul___exposer extends PyBuiltinMethodNarrow {
      public bytearray___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mul__(n) <==> x*n";
      }

      public bytearray___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mul__(n) <==> x*n";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyByteArray)this.self).bytearray___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bytearray___rmul___exposer extends PyBuiltinMethodNarrow {
      public bytearray___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmul__(n) <==> n*x";
      }

      public bytearray___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmul__(n) <==> n*x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyByteArray)this.self).bytearray___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bytearray_append_exposer extends PyBuiltinMethodNarrow {
      public bytearray_append_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "B.append(int) -> None\n\nAppend a single item to the end of B.";
      }

      public bytearray_append_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.append(int) -> None\n\nAppend a single item to the end of B.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_append_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyByteArray)this.self).bytearray_append(var1);
         return Py.None;
      }
   }

   private static class bytearray___contains___exposer extends PyBuiltinMethodNarrow {
      public bytearray___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__contains__(y) <==> y in x";
      }

      public bytearray___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__contains__(y) <==> y in x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyByteArray)this.self).bytearray___contains__(var1));
      }
   }

   private static class bytearray_decode_exposer extends PyBuiltinMethod {
      public bytearray_decode_exposer(String var1) {
         super(var1);
         super.doc = "B.decode([encoding[, errors]]) -> unicode object.\n\nDecodes B using the codec registered for encoding. encoding defaults\nto the default encoding. errors may be given to set a different error\nhandling scheme.  Default is 'strict' meaning that encoding errors raise\na UnicodeDecodeError.  Other possible values are 'ignore' and 'replace'\nas well as any other name registered with codecs.register_error that is\nable to handle UnicodeDecodeErrors.";
      }

      public bytearray_decode_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.decode([encoding[, errors]]) -> unicode object.\n\nDecodes B using the codec registered for encoding. encoding defaults\nto the default encoding. errors may be given to set a different error\nhandling scheme.  Default is 'strict' meaning that encoding errors raise\na UnicodeDecodeError.  Other possible values are 'ignore' and 'replace'\nas well as any other name registered with codecs.register_error that is\nable to handle UnicodeDecodeErrors.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_decode_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyByteArray)this.self).bytearray_decode(var1, var2);
      }
   }

   private static class bytearray_center_exposer extends PyBuiltinMethodNarrow {
      public bytearray_center_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "B.center(width[, fillchar]) -> copy of B\n\nReturn B centered in a string of length width.  Padding is\ndone using the specified fill character (default is a space).";
      }

      public bytearray_center_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.center(width[, fillchar]) -> copy of B\n\nReturn B centered in a string of length width.  Padding is\ndone using the specified fill character (default is a space).";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_center_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyByteArray)this.self).bytearray_center(Py.py2int(var1), var2.asStringOrNull());
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_center(Py.py2int(var1), (String)null);
      }
   }

   private static class bytearray_count_exposer extends PyBuiltinMethodNarrow {
      public bytearray_count_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "B.count(sub [,start [,end]]) -> int\n\nReturn the number of non-overlapping occurrences of subsection sub in\nbytes B[start:end].  Optional arguments start and end are interpreted\nas in slice notation.";
      }

      public bytearray_count_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.count(sub [,start [,end]]) -> int\n\nReturn the number of non-overlapping occurrences of subsection sub in\nbytes B[start:end].  Optional arguments start and end are interpreted\nas in slice notation.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_count_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_count(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_count(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_count(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class bytearray_endswith_exposer extends PyBuiltinMethodNarrow {
      public bytearray_endswith_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "B.endswith(suffix [,start [,end]]) -> bool\n\nReturn True if B ends with the specified suffix, False otherwise.\nWith optional start, test B beginning at that position.\nWith optional end, stop comparing B at that position.\nsuffix can also be a tuple of strings to try.";
      }

      public bytearray_endswith_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.endswith(suffix [,start [,end]]) -> bool\n\nReturn True if B ends with the specified suffix, False otherwise.\nWith optional start, test B beginning at that position.\nWith optional end, stop comparing B at that position.\nsuffix can also be a tuple of strings to try.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_endswith_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_endswith(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_endswith(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_endswith(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class bytearray_expandtabs_exposer extends PyBuiltinMethodNarrow {
      public bytearray_expandtabs_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "B.expandtabs([tabsize]) -> copy of B\n\nReturn a copy of B where all tab characters are expanded using spaces.\nIf tabsize is not given, a tab size of 8 characters is assumed.";
      }

      public bytearray_expandtabs_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.expandtabs([tabsize]) -> copy of B\n\nReturn a copy of B where all tab characters are expanded using spaces.\nIf tabsize is not given, a tab size of 8 characters is assumed.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_expandtabs_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_expandtabs(Py.py2int(var1));
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_expandtabs(8);
      }
   }

   private static class bytearray_extend_exposer extends PyBuiltinMethodNarrow {
      public bytearray_extend_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "B.extend(iterable int) -> None\n\nAppend all the elements from the iterator or sequence to the\nend of B.";
      }

      public bytearray_extend_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.extend(iterable int) -> None\n\nAppend all the elements from the iterator or sequence to the\nend of B.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_extend_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyByteArray)this.self).bytearray_extend(var1);
         return Py.None;
      }
   }

   private static class bytearray_find_exposer extends PyBuiltinMethodNarrow {
      public bytearray_find_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "B.find(sub [,start [,end]]) -> int\n\nReturn the lowest index in B where subsection sub is found,\nsuch that sub is contained within s[start,end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public bytearray_find_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.find(sub [,start [,end]]) -> int\n\nReturn the lowest index in B where subsection sub is found,\nsuch that sub is contained within s[start,end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_find_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_find(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_find(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_find(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class bytearray_fromhex_exposer extends PyBuiltinClassMethodNarrow {
      public bytearray_fromhex_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "bytearray.fromhex(string) -> bytearray\n\nCreate a bytearray object from a string of hexadecimal numbers.\nSpaces between two numbers are accepted.\nExample: bytearray.fromhex('B9 01EF') -> bytearray(b'\\xb9\\x01\\xef').";
      }

      public bytearray_fromhex_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "bytearray.fromhex(string) -> bytearray\n\nCreate a bytearray object from a string of hexadecimal numbers.\nSpaces between two numbers are accepted.\nExample: bytearray.fromhex('B9 01EF') -> bytearray(b'\\xb9\\x01\\xef').";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_fromhex_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return PyByteArray.bytearray_fromhex((PyType)this.self, var1.asString());
      }
   }

   private static class bytearray___getitem___exposer extends PyBuiltinMethodNarrow {
      public bytearray___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public bytearray___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray___getitem__(var1);
      }
   }

   private static class bytearray___iadd___exposer extends PyBuiltinMethodNarrow {
      public bytearray___iadd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__iadd__(y) <==> x+=y";
      }

      public bytearray___iadd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iadd__(y) <==> x+=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___iadd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyByteArray)this.self).bytearray___iadd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class bytearray___hash___exposer extends PyBuiltinMethodNarrow {
      public bytearray___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public bytearray___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyByteArray)this.self).bytearray___hash__());
      }
   }

   private static class bytearray_index_exposer extends PyBuiltinMethodNarrow {
      public bytearray_index_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "B.index(sub [,start [,end]]) -> int\n\nLike B.find() but raise ValueError when the subsection is not found.";
      }

      public bytearray_index_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.index(sub [,start [,end]]) -> int\n\nLike B.find() but raise ValueError when the subsection is not found.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_index_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_index(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_index(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_index(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class bytearray_insert_exposer extends PyBuiltinMethodNarrow {
      public bytearray_insert_exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "B.insert(index, int) -> None\n\nInsert a single item into the bytearray before the given index.";
      }

      public bytearray_insert_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.insert(index, int) -> None\n\nInsert a single item into the bytearray before the given index.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_insert_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyByteArray)this.self).bytearray_insert(var1, var2);
         return Py.None;
      }
   }

   private static class bytearray_isalnum_exposer extends PyBuiltinMethodNarrow {
      public bytearray_isalnum_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.isalnum() -> bool\n\nReturn True if all characters in B are alphanumeric\nand there is at least one character in B, False otherwise.";
      }

      public bytearray_isalnum_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.isalnum() -> bool\n\nReturn True if all characters in B are alphanumeric\nand there is at least one character in B, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_isalnum_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_isalnum());
      }
   }

   private static class bytearray_isalpha_exposer extends PyBuiltinMethodNarrow {
      public bytearray_isalpha_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.isalpha() -> bool\n\nReturn True if all characters in B are alphabetic\nand there is at least one character in B, False otherwise.";
      }

      public bytearray_isalpha_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.isalpha() -> bool\n\nReturn True if all characters in B are alphabetic\nand there is at least one character in B, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_isalpha_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_isalpha());
      }
   }

   private static class bytearray_isdigit_exposer extends PyBuiltinMethodNarrow {
      public bytearray_isdigit_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.isdigit() -> bool\n\nReturn True if all characters in B are digits\nand there is at least one character in B, False otherwise.";
      }

      public bytearray_isdigit_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.isdigit() -> bool\n\nReturn True if all characters in B are digits\nand there is at least one character in B, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_isdigit_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_isdigit());
      }
   }

   private static class bytearray_islower_exposer extends PyBuiltinMethodNarrow {
      public bytearray_islower_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.islower() -> bool\n\nReturn True if all cased characters in B are lowercase and there is\nat least one cased character in B, False otherwise.";
      }

      public bytearray_islower_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.islower() -> bool\n\nReturn True if all cased characters in B are lowercase and there is\nat least one cased character in B, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_islower_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_islower());
      }
   }

   private static class bytearray_isspace_exposer extends PyBuiltinMethodNarrow {
      public bytearray_isspace_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.isspace() -> bool\n\nReturn True if all characters in B are whitespace\nand there is at least one character in B, False otherwise.";
      }

      public bytearray_isspace_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.isspace() -> bool\n\nReturn True if all characters in B are whitespace\nand there is at least one character in B, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_isspace_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_isspace());
      }
   }

   private static class bytearray_istitle_exposer extends PyBuiltinMethodNarrow {
      public bytearray_istitle_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.istitle() -> bool\n\nReturn True if B is a titlecased string and there is at least one\ncharacter in B, i.e. uppercase characters may only follow uncased\ncharacters and lowercase characters only cased ones. Return False\notherwise.";
      }

      public bytearray_istitle_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.istitle() -> bool\n\nReturn True if B is a titlecased string and there is at least one\ncharacter in B, i.e. uppercase characters may only follow uncased\ncharacters and lowercase characters only cased ones. Return False\notherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_istitle_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_istitle());
      }
   }

   private static class bytearray_isupper_exposer extends PyBuiltinMethodNarrow {
      public bytearray_isupper_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.isupper() -> bool\n\nReturn True if all cased characters in B are uppercase and there is\nat least one cased character in B, False otherwise.";
      }

      public bytearray_isupper_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.isupper() -> bool\n\nReturn True if all cased characters in B are uppercase and there is\nat least one cased character in B, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_isupper_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_isupper());
      }
   }

   private static class bytearray_capitalize_exposer extends PyBuiltinMethodNarrow {
      public bytearray_capitalize_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.capitalize() -> copy of B\n\nReturn a copy of B with only its first character capitalized (ASCII)\nand the rest lower-cased.";
      }

      public bytearray_capitalize_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.capitalize() -> copy of B\n\nReturn a copy of B with only its first character capitalized (ASCII)\nand the rest lower-cased.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_capitalize_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_capitalize();
      }
   }

   private static class bytearray_lower_exposer extends PyBuiltinMethodNarrow {
      public bytearray_lower_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.lower() -> copy of B\n\nReturn a copy of B with all ASCII characters converted to lowercase.";
      }

      public bytearray_lower_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.lower() -> copy of B\n\nReturn a copy of B with all ASCII characters converted to lowercase.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_lower_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_lower();
      }
   }

   private static class bytearray_swapcase_exposer extends PyBuiltinMethodNarrow {
      public bytearray_swapcase_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.swapcase() -> copy of B\n\nReturn a copy of B with uppercase ASCII characters converted\nto lowercase ASCII and vice versa.";
      }

      public bytearray_swapcase_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.swapcase() -> copy of B\n\nReturn a copy of B with uppercase ASCII characters converted\nto lowercase ASCII and vice versa.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_swapcase_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_swapcase();
      }
   }

   private static class bytearray_title_exposer extends PyBuiltinMethodNarrow {
      public bytearray_title_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.title() -> copy of B\n\nReturn a titlecased version of B, i.e. ASCII words start with uppercase\ncharacters, all remaining cased characters have lowercase.";
      }

      public bytearray_title_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.title() -> copy of B\n\nReturn a titlecased version of B, i.e. ASCII words start with uppercase\ncharacters, all remaining cased characters have lowercase.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_title_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_title();
      }
   }

   private static class bytearray_upper_exposer extends PyBuiltinMethodNarrow {
      public bytearray_upper_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.upper() -> copy of B\n\nReturn a copy of B with all ASCII characters converted to uppercase.";
      }

      public bytearray_upper_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.upper() -> copy of B\n\nReturn a copy of B with all ASCII characters converted to uppercase.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_upper_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_upper();
      }
   }

   private static class bytearray_join_exposer extends PyBuiltinMethodNarrow {
      public bytearray_join_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "B.join(iterable_of_bytes) -> bytes\n\nConcatenates any number of bytearray objects, with B in between each pair.";
      }

      public bytearray_join_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.join(iterable_of_bytes) -> bytes\n\nConcatenates any number of bytearray objects, with B in between each pair.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_join_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_join(var1);
      }
   }

   private static class bytearray___len___exposer extends PyBuiltinMethodNarrow {
      public bytearray___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__len__() <==> len(x)";
      }

      public bytearray___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__len__() <==> len(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyByteArray)this.self).bytearray___len__());
      }
   }

   private static class bytearray_ljust_exposer extends PyBuiltinMethodNarrow {
      public bytearray_ljust_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "B.ljust(width[, fillchar]) -> copy of B\n\nReturn B left justified in a string of length width. Padding is\ndone using the specified fill character (default is a space).";
      }

      public bytearray_ljust_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.ljust(width[, fillchar]) -> copy of B\n\nReturn B left justified in a string of length width. Padding is\ndone using the specified fill character (default is a space).";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_ljust_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyByteArray)this.self).bytearray_ljust(Py.py2int(var1), var2.asStringOrNull());
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_ljust(Py.py2int(var1), (String)null);
      }
   }

   private static class bytearray_lstrip_exposer extends PyBuiltinMethodNarrow {
      public bytearray_lstrip_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "B.lstrip([bytes]) -> bytearray\n\nStrip leading bytes contained in the argument.\nIf the argument is omitted, strip leading ASCII whitespace.";
      }

      public bytearray_lstrip_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.lstrip([bytes]) -> bytearray\n\nStrip leading bytes contained in the argument.\nIf the argument is omitted, strip leading ASCII whitespace.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_lstrip_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_lstrip(var1);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_lstrip((PyObject)null);
      }
   }

   private static class bytearray_partition_exposer extends PyBuiltinMethodNarrow {
      public bytearray_partition_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "B.partition(sep) -> (head, sep, tail)\n\nSearches for the separator sep in B, and returns the part before it,\nthe separator itself, and the part after it.  If the separator is not\nfound, returns B and two empty bytearray objects.";
      }

      public bytearray_partition_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.partition(sep) -> (head, sep, tail)\n\nSearches for the separator sep in B, and returns the part before it,\nthe separator itself, and the part after it.  If the separator is not\nfound, returns B and two empty bytearray objects.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_partition_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_partition(var1);
      }
   }

   private static class bytearray_pop_exposer extends PyBuiltinMethodNarrow {
      public bytearray_pop_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "B.pop([index]) -> int\n\nRemove and return a single item from B. If no index\nargument is given, will pop the last value.";
      }

      public bytearray_pop_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.pop([index]) -> int\n\nRemove and return a single item from B. If no index\nargument is given, will pop the last value.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_pop_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_pop(Py.py2int(var1));
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_pop(-1);
      }
   }

   private static class bytearray___reduce___exposer extends PyBuiltinMethodNarrow {
      public bytearray___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return state information for pickling.";
      }

      public bytearray___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return state information for pickling.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray___reduce__();
      }
   }

   private static class bytearray_remove_exposer extends PyBuiltinMethodNarrow {
      public bytearray_remove_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "B.remove(int) -> None\n\nRemove the first occurance of a value in B.";
      }

      public bytearray_remove_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.remove(int) -> None\n\nRemove the first occurance of a value in B.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_remove_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyByteArray)this.self).bytearray_remove(var1);
         return Py.None;
      }
   }

   private static class bytearray_replace_exposer extends PyBuiltinMethodNarrow {
      public bytearray_replace_exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "B.replace(old, new[, count]) -> bytes\n\nReturn a copy of B with all occurrences of subsection\nold replaced by new.  If the optional argument count is\ngiven, only the first count occurrences are replaced.";
      }

      public bytearray_replace_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.replace(old, new[, count]) -> bytes\n\nReturn a copy of B with all occurrences of subsection\nold replaced by new.  If the optional argument count is\ngiven, only the first count occurrences are replaced.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_replace_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return ((PyByteArray)this.self).bytearray_replace(var1, var2, var3);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyByteArray)this.self).bytearray_replace(var1, var2, (PyObject)null);
      }
   }

   private static class bytearray_reverse_exposer extends PyBuiltinMethodNarrow {
      public bytearray_reverse_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "B.reverse() -> None\n\nReverse the order of the values in B in place.";
      }

      public bytearray_reverse_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.reverse() -> None\n\nReverse the order of the values in B in place.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_reverse_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyByteArray)this.self).bytearray_reverse();
         return Py.None;
      }
   }

   private static class bytearray_rfind_exposer extends PyBuiltinMethodNarrow {
      public bytearray_rfind_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "B.rfind(sub [,start [,end]]) -> int\n\nReturn the highest index in B where subsection sub is found,\nsuch that sub is contained within s[start,end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public bytearray_rfind_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.rfind(sub [,start [,end]]) -> int\n\nReturn the highest index in B where subsection sub is found,\nsuch that sub is contained within s[start,end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_rfind_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_rfind(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_rfind(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_rfind(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class bytearray_rjust_exposer extends PyBuiltinMethodNarrow {
      public bytearray_rjust_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "B.rjust(width[, fillchar]) -> copy of B\n\nReturn B right justified in a string of length width. Padding is\ndone using the specified fill character (default is a space)";
      }

      public bytearray_rjust_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.rjust(width[, fillchar]) -> copy of B\n\nReturn B right justified in a string of length width. Padding is\ndone using the specified fill character (default is a space)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_rjust_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyByteArray)this.self).bytearray_rjust(Py.py2int(var1), var2.asStringOrNull());
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_rjust(Py.py2int(var1), (String)null);
      }
   }

   private static class bytearray_rindex_exposer extends PyBuiltinMethodNarrow {
      public bytearray_rindex_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "B.rindex(sub [,start [,end]]) -> int\n\nLike B.rfind() but raise ValueError when the subsection is not found.";
      }

      public bytearray_rindex_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.rindex(sub [,start [,end]]) -> int\n\nLike B.rfind() but raise ValueError when the subsection is not found.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_rindex_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_rindex(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_rindex(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyByteArray)this.self).bytearray_rindex(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class bytearray_rpartition_exposer extends PyBuiltinMethodNarrow {
      public bytearray_rpartition_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "B.rpartition(sep) -> (head, sep, tail)\n\nSearches for the separator sep in B, starting at the end of B,\nand returns the part before it, the separator itself, and the\npart after it.  If the separator is not found, returns two empty\nbytearray objects and B.";
      }

      public bytearray_rpartition_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.rpartition(sep) -> (head, sep, tail)\n\nSearches for the separator sep in B, starting at the end of B,\nand returns the part before it, the separator itself, and the\npart after it.  If the separator is not found, returns two empty\nbytearray objects and B.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_rpartition_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_rpartition(var1);
      }
   }

   private static class bytearray_rsplit_exposer extends PyBuiltinMethodNarrow {
      public bytearray_rsplit_exposer(String var1) {
         super(var1, 1, 3);
         super.doc = "B.rsplit(sep[, maxsplit]) -> list of bytearray\n\nReturn a list of the sections in B, using sep as the delimiter,\nstarting at the end of B and working to the front.\nIf sep is not given, B is split on ASCII whitespace characters\n(space, tab, return, newline, formfeed, vertical tab).\nIf maxsplit is given, at most maxsplit splits are done.";
      }

      public bytearray_rsplit_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.rsplit(sep[, maxsplit]) -> list of bytearray\n\nReturn a list of the sections in B, using sep as the delimiter,\nstarting at the end of B and working to the front.\nIf sep is not given, B is split on ASCII whitespace characters\n(space, tab, return, newline, formfeed, vertical tab).\nIf maxsplit is given, at most maxsplit splits are done.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_rsplit_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyByteArray)this.self).bytearray_rsplit(var1, Py.py2int(var2));
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_rsplit(var1, -1);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_rsplit((PyObject)null, -1);
      }
   }

   private static class bytearray_rstrip_exposer extends PyBuiltinMethodNarrow {
      public bytearray_rstrip_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "B.rstrip([bytes]) -> bytearray\n\nStrip trailing bytes contained in the argument.\nIf the argument is omitted, strip trailing ASCII whitespace.";
      }

      public bytearray_rstrip_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.rstrip([bytes]) -> bytearray\n\nStrip trailing bytes contained in the argument.\nIf the argument is omitted, strip trailing ASCII whitespace.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_rstrip_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_rstrip(var1);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_rstrip((PyObject)null);
      }
   }

   private static class bytearray_split_exposer extends PyBuiltinMethodNarrow {
      public bytearray_split_exposer(String var1) {
         super(var1, 1, 3);
         super.doc = "B.split([sep[, maxsplit]]) -> list of bytearray\n\nReturn a list of the sections in B, using sep as the delimiter.\nIf sep is not given, B is split on ASCII whitespace characters\n(space, tab, return, newline, formfeed, vertical tab).\nIf maxsplit is given, at most maxsplit splits are done.";
      }

      public bytearray_split_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.split([sep[, maxsplit]]) -> list of bytearray\n\nReturn a list of the sections in B, using sep as the delimiter.\nIf sep is not given, B is split on ASCII whitespace characters\n(space, tab, return, newline, formfeed, vertical tab).\nIf maxsplit is given, at most maxsplit splits are done.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_split_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyByteArray)this.self).bytearray_split(var1, Py.py2int(var2));
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_split(var1, -1);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_split((PyObject)null, -1);
      }
   }

   private static class bytearray_splitlines_exposer extends PyBuiltinMethodNarrow {
      public bytearray_splitlines_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "B.splitlines([keepends]) -> list of lines\n\nReturn a list of the lines in B, breaking at line boundaries.\nLine breaks are not included in the resulting list unless keepends\nis given and true.";
      }

      public bytearray_splitlines_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.splitlines([keepends]) -> list of lines\n\nReturn a list of the lines in B, breaking at line boundaries.\nLine breaks are not included in the resulting list unless keepends\nis given and true.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_splitlines_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_splitlines(Py.py2boolean(var1));
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_splitlines((boolean)0);
      }
   }

   private static class bytearray_startswith_exposer extends PyBuiltinMethodNarrow {
      public bytearray_startswith_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "B.startswith(prefix [,start [,end]]) -> bool\n\nReturn True if B starts with the specified prefix, False otherwise.\nWith optional start, test B beginning at that position.\nWith optional end, stop comparing B at that position.\nprefix can also be a tuple of strings to try.";
      }

      public bytearray_startswith_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.startswith(prefix [,start [,end]]) -> bool\n\nReturn True if B starts with the specified prefix, False otherwise.\nWith optional start, test B beginning at that position.\nWith optional end, stop comparing B at that position.\nprefix can also be a tuple of strings to try.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_startswith_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_startswith(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_startswith(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyByteArray)this.self).bytearray_startswith(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class bytearray_strip_exposer extends PyBuiltinMethodNarrow {
      public bytearray_strip_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "B.strip([bytes]) -> bytearray\n\nStrip leading and trailing bytes contained in the argument.\nIf the argument is omitted, strip ASCII whitespace.";
      }

      public bytearray_strip_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.strip([bytes]) -> bytearray\n\nStrip leading and trailing bytes contained in the argument.\nIf the argument is omitted, strip ASCII whitespace.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_strip_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_strip(var1);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_strip((PyObject)null);
      }
   }

   private static class bytearray___setitem___exposer extends PyBuiltinMethodNarrow {
      public bytearray___setitem___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "x.__setitem__(i, y) <==> x[i]=y";
      }

      public bytearray___setitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__setitem__(i, y) <==> x[i]=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray___setitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyByteArray)this.self).bytearray___setitem__(var1, var2);
         return Py.None;
      }
   }

   private static class bytearray_repr_exposer extends PyBuiltinMethodNarrow {
      public bytearray_repr_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public bytearray_repr_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_repr_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyByteArray)this.self).bytearray_repr();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class bytearray_str_exposer extends PyBuiltinMethodNarrow {
      public bytearray_str_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public bytearray_str_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_str_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyByteArray)this.self).bytearray_str();
      }
   }

   private static class bytearray_translate_exposer extends PyBuiltinMethodNarrow {
      public bytearray_translate_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "B.translate(table[, deletechars]) -> bytearray\n\nReturn a copy of B, where all characters occurring in the\noptional argument deletechars are removed, and the remaining\ncharacters have been mapped through the given translation\ntable, which must be a bytes object of length 256.";
      }

      public bytearray_translate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.translate(table[, deletechars]) -> bytearray\n\nReturn a copy of B, where all characters occurring in the\noptional argument deletechars are removed, and the remaining\ncharacters have been mapped through the given translation\ntable, which must be a bytes object of length 256.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_translate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyByteArray)this.self).bytearray_translate(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_translate(var1, (PyObject)null);
      }
   }

   private static class bytearray_zfill_exposer extends PyBuiltinMethodNarrow {
      public bytearray_zfill_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "B.zfill(width) -> copy of B\n\nPad a numeric string B with zeros on the left, to fill a field\nof the specified width.  B is never truncated.";
      }

      public bytearray_zfill_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "B.zfill(width) -> copy of B\n\nPad a numeric string B with zeros on the left, to fill a field\nof the specified width.  B is never truncated.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new bytearray_zfill_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyByteArray)this.self).bytearray_zfill(Py.py2int(var1));
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PyByteArray var4 = new PyByteArray(this.for_type);
         if (var1) {
            var4.bytearray___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyByteArrayDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new bytearray___init___exposer("__init__"), new bytearray___eq___exposer("__eq__"), new bytearray___ne___exposer("__ne__"), new bytearray___lt___exposer("__lt__"), new bytearray___le___exposer("__le__"), new bytearray___ge___exposer("__ge__"), new bytearray___gt___exposer("__gt__"), new bytearray___add___exposer("__add__"), new bytearray___alloc___exposer("__alloc__"), new bytearray___imul___exposer("__imul__"), new bytearray___mul___exposer("__mul__"), new bytearray___rmul___exposer("__rmul__"), new bytearray_append_exposer("append"), new bytearray___contains___exposer("__contains__"), new bytearray_decode_exposer("decode"), new bytearray_center_exposer("center"), new bytearray_count_exposer("count"), new bytearray_endswith_exposer("endswith"), new bytearray_expandtabs_exposer("expandtabs"), new bytearray_extend_exposer("extend"), new bytearray_find_exposer("find"), new bytearray_fromhex_exposer("fromhex"), new bytearray___getitem___exposer("__getitem__"), new bytearray___iadd___exposer("__iadd__"), new bytearray___hash___exposer("__hash__"), new bytearray_index_exposer("index"), new bytearray_insert_exposer("insert"), new bytearray_isalnum_exposer("isalnum"), new bytearray_isalpha_exposer("isalpha"), new bytearray_isdigit_exposer("isdigit"), new bytearray_islower_exposer("islower"), new bytearray_isspace_exposer("isspace"), new bytearray_istitle_exposer("istitle"), new bytearray_isupper_exposer("isupper"), new bytearray_capitalize_exposer("capitalize"), new bytearray_lower_exposer("lower"), new bytearray_swapcase_exposer("swapcase"), new bytearray_title_exposer("title"), new bytearray_upper_exposer("upper"), new bytearray_join_exposer("join"), new bytearray___len___exposer("__len__"), new bytearray_ljust_exposer("ljust"), new bytearray_lstrip_exposer("lstrip"), new bytearray_partition_exposer("partition"), new bytearray_pop_exposer("pop"), new bytearray___reduce___exposer("__reduce__"), new bytearray_remove_exposer("remove"), new bytearray_replace_exposer("replace"), new bytearray_reverse_exposer("reverse"), new bytearray_rfind_exposer("rfind"), new bytearray_rjust_exposer("rjust"), new bytearray_rindex_exposer("rindex"), new bytearray_rpartition_exposer("rpartition"), new bytearray_rsplit_exposer("rsplit"), new bytearray_rstrip_exposer("rstrip"), new bytearray_split_exposer("split"), new bytearray_splitlines_exposer("splitlines"), new bytearray_startswith_exposer("startswith"), new bytearray_strip_exposer("strip"), new bytearray___setitem___exposer("__setitem__"), new bytearray_repr_exposer("__repr__"), new bytearray_str_exposer("__str__"), new bytearray_translate_exposer("translate"), new bytearray_zfill_exposer("zfill")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("bytearray", PyByteArray.class, PyObject.class, (boolean)1, "bytearray(iterable_of_ints) -> bytearray.\nbytearray(string, encoding[, errors]) -> bytearray.\nbytearray(bytes_or_bytearray) -> mutable copy of bytes_or_bytearray.\nbytearray(memory_view) -> bytearray.\n\nConstruct an mutable bytearray object from:\n  - an iterable yielding integers in range(256)\n  - a text string encoded using the specified encoding\n  - a bytes or a bytearray object\n  - any object implementing the buffer API.\n\nbytearray(int) -> bytearray.\n\nConstruct a zero-initialized bytearray of the given length.", var1, var2, new exposed___new__());
      }
   }
}
