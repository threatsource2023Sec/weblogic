package org.python.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import junit.framework.TestCase;
import org.python.core.buffer.SimpleBuffer;
import org.python.util.PythonInterpreter;

public class BaseBytesTest extends TestCase {
   public static final int SMALL = 7;
   public static final int MEDIUM = 25;
   public static final int LARGE = 2000;
   public static final int HUGE = 100000;
   PythonInterpreter interp;
   Random random;

   public BaseBytesTest(String name) {
      super(name);
   }

   public static char toChar(int b) {
      return Character.toChars(255 & b)[0];
   }

   public static int[] toInts(String s) {
      int n = s.length();
      int[] r = new int[n];

      for(int i = 0; i < n; ++i) {
         int c = s.codePointAt(i);
         r[i] = 255 & c;
      }

      return r;
   }

   public static int[] randomInts(Random random, int n) {
      int[] r = new int[n];

      for(int i = 0; i < n; ++i) {
         r[i] = random.nextInt(256);
      }

      return r;
   }

   public static int[] randomInts(Random random, int n, int lo, int hi) {
      int[] r = new int[n];
      int m = hi + 1 - lo;

      for(int i = 0; i < n; ++i) {
         r[i] = lo + random.nextInt(m);
      }

      return r;
   }

   static void checkInts(int[] expected, int first, BaseBytes result, int start, int len) {
      if (len > 0) {
         int end = first + len;
         if (end > expected.length) {
            end = expected.length;
         }

         int i = first;

         for(int j = start; i < end; ++j) {
            assertEquals("element value", expected[i], result.intAt(j));
            ++i;
         }
      }

   }

   static void checkInts(int[] expected, BaseBytes result) {
      assertEquals("size", expected.length, result.size());

      for(int i = 0; i < expected.length; ++i) {
         assertEquals("element value", expected[i], result.intAt(i));
      }

   }

   static void checkInts(List expected, BaseBytes result) {
      assertEquals("size", expected.size(), result.size());

      for(int i = 0; i < result.size; ++i) {
         PyInteger res = result.pyget(i);
         PyInteger exp = (PyInteger)expected.get(i);
         assertEquals("element value", exp, res);
      }

   }

   static void checkInts(List expected, PyObject result) {
      checkInts(expected, (BaseBytes)result);
   }

   public static Iterable iterableBytes(int[] source) {
      List list = new ArrayList(source.length);
      int choose = 0;
      int[] var3 = source;
      int var4 = source.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int b = var3[var5];
         switch (choose++) {
            case 0:
               PyInteger i = new PyInteger(b);
               list.add(i);
               break;
            case 1:
               PyLong l = new PyLong((long)b);
               list.add(l);
               break;
            default:
               PyString s = new PyString(toChar(b));
               list.add(s);
               choose = 0;
         }
      }

      return list;
   }

   protected void setUp() throws Exception {
      super.setUp();
      this.random = new Random(20120310L);
   }

   public void testSize() {
      int[] aRef = toInts("Chaque coquillage incrusté");
      BaseBytes a = this.getInstance(aRef);
      System.out.println(this.toString(a));
      assertEquals(aRef.length, a.size());
      int[] var3 = new int[]{0, 1, 2, 7, 8, 9, 25, 2000, 100000};
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int n = var3[var5];
         a = this.getInstance(n);
         assertEquals("size()", n, a.size());
         assertEquals("__len__()", n, a.__len__());
      }

   }

   public void testInit_intArray() {
      int[] aRef = toInts("Dans la grotte où nous nous aimâmes");
      BaseBytes a = this.getInstance(aRef);
      BaseBytes b = this.getInstance(a);
      System.out.println(this.toString(b));
      assertEquals(a.size(), b.size());

      for(int i = 0; i < a.size(); ++i) {
         assertEquals(a.intAt(i), b.intAt(i));
      }

   }

   public void testInit_Iterable() {
      int[] aRef = toInts("A sa particularité.");
      Iterable ia = iterableBytes(aRef);
      BaseBytes a = this.getInstance(ia);
      System.out.println(this.toString(a));
      assertEquals(aRef.length, a.size());
      checkInts(aRef, a);
      BaseBytes b = this.getInstance(iterableBytes(new int[0]));
      assertEquals(0, b.size());
      int[] cRef = toInts(":-)");
      BaseBytes c = this.getInstance(iterableBytes(cRef));
      assertEquals(cRef.length, c.size());
      checkInts(cRef, c);
   }

   public void testInit_PyObject() {
      PyObject[] brantub = new PyObject[]{null, new PyInteger(5), new PyString(" ¡¢£¤"), this.getInstance(new int[]{180, 190, 200}), new PyXRange(1, 301, 50)};
      int[][] prize = new int[][]{new int[0], {0, 0, 0, 0, 0}, {160, 161, 162, 163, 164}, {180, 190, 200}, {1, 51, 101, 151, 201, 251}};

      for(int dip = 0; dip < brantub.length; ++dip) {
         int[] aRef = prize[dip];
         BaseBytes a = this.getInstance(brantub[dip]);
         assertEquals(aRef.length, a.size());
         checkInts(aRef, a);
      }

   }

   public void testInit_Exceptions() {
      this.interp = new PythonInterpreter();
      PyObject[] brantub = new PyObject[]{Py.None, new PyInteger(-1), new PyLong(2147483648L), new PyXRange(3, -2, -1), new PyXRange(250, 257)};
      PyObject[] boobyPrize = new PyObject[]{Py.TypeError, Py.ValueError, Py.OverflowError, Py.ValueError, Py.ValueError};

      for(int dip = 0; dip < brantub.length; ++dip) {
         PyObject aRef = boobyPrize[dip];

         try {
            BaseBytes a = this.getInstance(brantub[dip]);
            System.out.println(this.toString(a));
            fail("Exception not thrown for " + brantub[dip]);
         } catch (PyException var7) {
            PyObject a = var7.type;
            assertEquals(aRef, a);
         }
      }

   }

   public void testPyget() {
      this.interp = new PythonInterpreter();
      int[] aRef = randomInts(this.random, 25);
      BaseBytes a = this.getInstance(aRef);

      for(int i = 0; i < 25; ++i) {
         PyInteger r = a.pyget(i);
         assertEquals(aRef[i], r.asInt());
      }

      int[] var9 = new int[]{-1, -100, 25, 26};
      int var10 = var9.length;

      for(int var5 = 0; var5 < var10; ++var5) {
         int i = var9[var5];

         try {
            PyInteger r = a.pyget(i);
            fail("Exception not thrown for pyget(" + i + ") =" + r);
         } catch (PyException var8) {
            assertEquals(Py.IndexError, var8.type);
         }
      }

   }

   public void testGetslice() {
      String ver = "L'un a la pourpre de nos âmes";
      int L = ver.length();
      int[] aRef = toInts(ver);
      BaseBytes a = this.getInstance(aRef);
      List bList = new ArrayList(L);
      int[] posStart = new int[]{0, 1, 18, L - 8, L - 1};
      int[] negStart = new int[]{0, 3, 16, L - 10, L - 1};

      int step;
      int[] var9;
      int var10;
      int var11;
      int start;
      int stop;
      int i;
      BaseBytes b;
      for(step = 1; step < 4; ++step) {
         var9 = posStart;
         var10 = posStart.length;

         for(var11 = 0; var11 < var10; ++var11) {
            start = var9[var11];

            for(stop = start; stop <= L; ++stop) {
               bList.clear();

               for(i = start; i < stop; i += step) {
                  bList.add(new PyInteger(aRef[i]));
               }

               b = a.getslice(start, stop, step);
               checkInts((List)bList, (BaseBytes)b);
            }
         }
      }

      for(step = -1; step > -4; --step) {
         var9 = negStart;
         var10 = negStart.length;

         for(var11 = 0; var11 < var10; ++var11) {
            start = var9[var11];

            for(stop = -1; stop <= start; ++stop) {
               bList.clear();

               for(i = start; i > stop; i += step) {
                  bList.add(new PyInteger(aRef[i]));
               }

               b = a.getslice(start, stop, step);
               checkInts((List)bList, (BaseBytes)b);
            }
         }
      }

   }

   public void testRepeatInt() {
      String spam = "Spam, ";
      int maxCount = true;
      int L = spam.length();
      int[] aRef = toInts(spam);
      BaseBytes a = this.getInstance(aRef);

      for(int count = 0; count <= 10; ++count) {
         int[] bRef = new int[count * L];

         for(int i = 0; i < count; ++i) {
            for(int j = 0; j < L; ++j) {
               bRef[i * L + j] = aRef[j];
            }
         }

         BaseBytes b = a.repeat(count);
         checkInts(bRef, b);
      }

   }

   public void testPyset() {
      PyObject bRef = Py.TypeError;
      int[] aRef = toInts("This immutable type seems to allow modifications.");
      BaseBytes a = this.getInstance(aRef);
      int start = a.size() / 2;
      PyInteger x = new PyInteger(120);

      try {
         a.pyset(start, x);
         System.out.println(this.toString(a));
         fail(String.format("Exception not thrown for pyset(%d,%s)", start, x));
      } catch (PyException var8) {
         PyObject b = var8.type;
         assertEquals(bRef, b);
      }

   }

   public void testSetslice3() {
      PyObject bRef = Py.TypeError;
      int[] aRef = toInts("This immutable type seems to allow modifications.");
      BaseBytes a = this.getInstance(aRef);
      int start = a.size() / 4;
      int stop = (3 * a.size() + 3) / 4;
      int step = 3;
      BaseBytes x = new MyBytes(randomInts(this.random, 7));

      try {
         a.setslice(start, stop, step, x);
         System.out.println(this.toString(a));
         fail(String.format("Exception not thrown for setslice(%d,%d,%d,%s)", start, stop, Integer.valueOf(step), x));
      } catch (PyException var10) {
         PyObject b = var10.type;
         assertEquals(bRef, b);
      }

   }

   public BaseBytes getInstance(PyType type) {
      return new MyBytes(type);
   }

   public BaseBytes getInstance() {
      return new MyBytes();
   }

   public BaseBytes getInstance(int size) {
      return new MyBytes(size);
   }

   public BaseBytes getInstance(int[] value) {
      return new MyBytes(value);
   }

   public BaseBytes getInstance(BaseBytes value) throws PyException {
      return new MyBytes(value);
   }

   public BaseBytes getInstance(BufferProtocol value) throws PyException {
      return new MyBytes(value);
   }

   public BaseBytes getInstance(Iterable value) throws PyException {
      return new MyBytes(value);
   }

   public BaseBytes getInstance(PyString arg, PyObject encoding, PyObject errors) throws PyException {
      return new MyBytes(arg, encoding, errors);
   }

   public BaseBytes getInstance(PyString arg, String encoding, String errors) throws PyException {
      return new MyBytes(arg, encoding, errors);
   }

   public BaseBytes getInstance(PyObject arg) throws PyException {
      return new MyBytes(arg);
   }

   protected String toString(BaseBytes b) {
      Image i = new Image();
      i.showSummary(b);
      if (b.storage.length >= 0 && b.storage.length <= 70) {
         i.padTo(15);
         i.showContent(b);
      }

      return i.toString();
   }

   private static Object getItem(int n, Object buf, int[] strides, int[] suboffsets, int[] indices) {
      for(int i = 0; i < n; ++i) {
         Object[] p = (Object[])((Object[])buf);
         buf = p[indices[i] * strides[i] + suboffsets[i]];
      }

      return buf;
   }

   private static byte getByte(int ndim, Object buf, int[] strides, int[] suboffsets, int[] indices) {
      int n = ndim - 1;
      byte[] b = (byte[])((byte[])getItem(n, buf, strides, suboffsets, indices));
      return b[indices[n] + suboffsets[n]];
   }

   protected static class Image {
      private StringBuilder image = new StringBuilder(100);

      private void repeat(char c, int n) {
         for(int i = 0; i < n; ++i) {
            this.image.append((char)(i == 0 ? '|' : ' ')).append(c);
         }

      }

      private void append(byte[] s, int pos, int n) {
         if (pos >= 0 && pos + n <= s.length) {
            for(int i = 0; i < n; ++i) {
               int c = 255 & s[pos + i];
               if (c == 0) {
                  c = 46;
               } else if (Character.isISOControl(c)) {
                  c = 35;
               }

               this.image.append((char)(i == 0 ? '|' : ' ')).append(BaseBytesTest.toChar(c));
            }

         }
      }

      public void padTo(int n) {
         while(n > this.image.length()) {
            this.image.append(' ');
         }

      }

      public String showSummary(BaseBytes b) {
         this.image.append(b.offset);
         this.image.append(" [ ").append(b.size).append(" ] ");
         this.image.append(b.storage.length - (b.offset + b.size));
         return this.image.toString();
      }

      public String showExtent(BaseBytes b) {
         this.repeat('-', b.offset);
         this.repeat('x', b.size);
         int tail = b.storage.length - (b.offset + b.size);
         this.repeat('-', tail);
         this.image.append('|');
         return this.image.toString();
      }

      public String showContent(BaseBytes b) {
         this.append(b.storage, 0, b.offset);
         this.append(b.storage, b.offset, b.size);
         int tail = b.storage.length - (b.offset + b.size);
         this.append(b.storage, b.offset + b.size, tail);
         this.image.append('|');
         return this.image.toString();
      }

      public String toString() {
         return this.image.toString();
      }
   }

   public static class BufferedObject extends PyObject implements BufferProtocol {
      public static final PyType TYPE = PyType.fromClass(BufferedObject.class);
      private byte[] store;

      BufferedObject(int[] value) {
         super(TYPE);
         int n = value.length;
         this.store = new byte[n];

         for(int i = 0; i < n; ++i) {
            this.store[i] = (byte)value[i];
         }

      }

      public PyBuffer getBuffer(int flags) {
         return new SimpleBuffer(flags, this, this.store);
      }
   }

   public static class MyBytes extends BaseBytes {
      public static final PyType TYPE = PyType.fromClass(MyBytes.class);

      public MyBytes(PyType type) {
         super(type);
      }

      public MyBytes() {
         super(TYPE);
      }

      public MyBytes(int size) {
         super(TYPE, size);
      }

      MyBytes(int[] value) {
         super(TYPE, value);
      }

      public MyBytes(BaseBytes value) {
         super(TYPE);
         this.init(value);
      }

      public MyBytes(BufferProtocol value) {
         super(TYPE);
         this.init(value.getBuffer(0));
      }

      public MyBytes(Iterable value) {
         super(TYPE);
         this.init(value);
      }

      public MyBytes(PyString arg, PyObject encoding, PyObject errors) {
         super(TYPE);
         this.init(arg, encoding, errors);
      }

      public MyBytes(PyString arg, String encoding, String errors) {
         super(TYPE);
         this.init(arg, encoding, errors);
      }

      public MyBytes(PyObject arg) throws PyException {
         super(TYPE);
         this.init(arg);
      }

      protected MyBytes(int start, int stop, BaseBytes source) {
         super(TYPE);
         this.setStorage(source.storage, stop - start, start);
      }

      protected MyBytes repeat(int count) {
         MyBytes ret = new MyBytes();
         ret.setStorage(this.repeatImpl(count));
         return ret;
      }

      protected MyBytes getslice(int start, int stop, int step) {
         MyBytes r;
         if (step == 1) {
            r = new MyBytes();
            if (stop > start) {
               r.setStorage(this.storage, stop - start, start + this.offset);
            }
         } else {
            r = new MyBytes(sliceLength(start, stop, (long)step));
            int iomax = r.size + r.offset;
            int io = r.offset;

            for(int jo = start; io < iomax; ++io) {
               r.storage[io] = this.storage[jo];
               jo += step;
            }
         }

         return r;
      }

      public int __len__() {
         return this.size;
      }

      protected MyBytes(BaseBytes.Builder builder) {
         super(TYPE);
         this.setStorage(builder.getStorage(), builder.getSize());
      }

      protected BaseBytes.Builder getBuilder(int capacity) {
         return new BaseBytes.Builder(capacity) {
            MyBytes getResult() {
               return new MyBytes(this);
            }
         };
      }
   }
}
