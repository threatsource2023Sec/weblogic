package org.python.core;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.python.core.buffer.BaseBuffer;
import org.python.core.buffer.SimpleBuffer;
import org.python.core.buffer.SimpleStringBuffer;
import org.python.core.buffer.SimpleWritableBuffer;
import org.python.util.PythonInterpreter;

@RunWith(Parameterized.class)
public class PyBufferTest {
   protected int verbosity = 0;
   protected static final boolean PRINT_KEY = true;
   static final int LONG = 1000;
   protected static final int[] sliceLengths = new int[]{1, 2, 5, 0, 250};
   protected static final int[] sliceSteps = new int[]{1, 2, 3, 7};
   protected static PythonInterpreter interp = new PythonInterpreter();
   protected PyBufferTestSupport.TestSpec spec;
   protected ByteBufferTestSupport.ByteMaterial ref;
   protected BufferProtocol obj;
   protected PyBuffer view;
   protected static final ByteBufferTestSupport.ByteMaterial byteMaterial = new ByteBufferTestSupport.ByteMaterial(10, 16, 3);
   protected static final ByteBufferTestSupport.ByteMaterial abcMaterial = new ByteBufferTestSupport.ByteMaterial("abcdefgh");
   protected static final ByteBufferTestSupport.ByteMaterial stringMaterial = new ByteBufferTestSupport.ByteMaterial("Mon côté fâcheux");
   protected static final ByteBufferTestSupport.ByteMaterial emptyMaterial = new ByteBufferTestSupport.ByteMaterial(new byte[0]);
   protected static final ByteBufferTestSupport.ByteMaterial longMaterial = new ByteBufferTestSupport.ByteMaterial(0, 1000, 5);
   private static final String[] validOrders = new String[]{"C-contiguous test fail", "F-contiguous test fail", "Any-contiguous test fail"};

   public PyBufferTest(PyBufferTestSupport.TestSpec spec) {
      this.spec = spec;
      this.ref = spec.ref;
      this.createObjAndView();
   }

   protected void createObjAndView() {
      PyBufferTestSupport.TestSpec.ObjectAndView pair = this.spec.makePair();
      this.obj = pair.obj;
      this.view = pair.view;
   }

   @Parameters
   public static Collection genTestSpecs() {
      PyBufferTestSupport s = new PyBufferTestSupport(sliceLengths, sliceSteps);
      PyBufferTestSupport.ExporterFactory simpleExporter = new SimpleExporterFactory();
      s.add(simpleExporter, byteMaterial);
      PyBufferTestSupport.ExporterFactory simpleWritableExporter = new PyBufferTestSupport.WritableExporterFactory() {
         public BufferProtocol make(ByteBufferTestSupport.ByteMaterial m) {
            return new SimpleWritableExporter(m.getBytes());
         }
      };
      s.add(simpleWritableExporter, abcMaterial);
      s.add(simpleWritableExporter, emptyMaterial);
      PyBufferTestSupport.ExporterFactory stringExporter = new PyBufferTestSupport.ReadonlyExporterFactory() {
         public BufferProtocol make(ByteBufferTestSupport.ByteMaterial m) {
            return new StringExporter(m.string);
         }
      };
      s.add(stringExporter, stringMaterial);
      PyBufferTestSupport.ExporterFactory rollYourOwnExporter = new PyBufferTestSupport.WritableExporterFactory() {
         public BufferProtocol make(ByteBufferTestSupport.ByteMaterial m) {
            return new RollYourOwnExporter(m.getBytes());
         }
      };
      s.add(rollYourOwnExporter, byteMaterial);
      s.add(rollYourOwnExporter, emptyMaterial);
      PyBufferTestSupport.ExporterFactory pyByteArrayExporter = new PyBufferTestSupport.WritableExporterFactory() {
         public BufferProtocol make(ByteBufferTestSupport.ByteMaterial m) {
            return new PyByteArray(m.getBytes());
         }
      };
      s.add(pyByteArrayExporter, byteMaterial);
      s.add(pyByteArrayExporter, longMaterial);
      s.add(pyByteArrayExporter, emptyMaterial);
      PyBufferTestSupport.ExporterFactory pyStringExporter = new PyBufferTestSupport.ReadonlyExporterFactory() {
         public BufferProtocol make(ByteBufferTestSupport.ByteMaterial m) {
            return new PyString(m.string);
         }
      };
      s.add(pyStringExporter, abcMaterial);
      s.add(pyStringExporter, emptyMaterial);
      PyBufferTestSupport.ExporterFactory offsetPyByteArrayExporter = new PyBufferTestSupport.WritableExporterFactory() {
         public BufferProtocol make(ByteBufferTestSupport.ByteMaterial m) {
            int OFFSET = true;
            byte[] b = m.getBytes();
            byte[] data = new byte[4 + b.length];
            System.arraycopy(b, 0, data, 4, b.length);
            PyByteArray a = new PyByteArray(data);
            a.delRange(0, 4);

            assert a.__alloc__() > b.length;

            return a;
         }
      };
      s.add(offsetPyByteArrayExporter, byteMaterial);
      s.add(offsetPyByteArrayExporter, longMaterial);
      List ret = s.getTestData();
      int key = 0;
      Iterator var10 = ret.iterator();

      while(var10.hasNext()) {
         PyBufferTestSupport.TestSpec[] r = (PyBufferTestSupport.TestSpec[])var10.next();
         PyBufferTestSupport.TestSpec spec = r[0];
         System.out.printf("%6d : %s\n", key++, spec.toString());
      }

      return ret;
   }

   protected void announce(String api) {
      if (this.verbosity > 0) {
         System.out.printf("%-30s %s\n", api + ":", this.spec.toString());
      }

   }

   @Test
   public void testIsReadonly() {
      this.announce("isReadonly");
      Assert.assertTrue(this.view.isReadonly() == this.spec.readonly);
   }

   @Test
   public void testGetNdim() {
      this.announce("getNdim");
      Assert.assertEquals("unexpected ndim", (long)this.spec.shape.length, (long)this.view.getNdim());
   }

   @Test
   public void testGetShape() {
      this.announce("getShape");
      int[] shape = this.view.getShape();
      Assert.assertNotNull("shape[] should always be provided", shape);
      ByteBufferTestSupport.assertIntsEqual("unexpected shape", this.spec.shape, shape);
   }

   @Test
   public void testGetLen() {
      this.announce("getLen");
      Assert.assertEquals("unexpected length", (long)this.ref.length, (long)this.view.getLen());
   }

   @Test
   public void testGetObj() {
      this.announce("getObj");
      Assert.assertEquals("unexpected exporting object", this.obj, this.view.getObj());
   }

   @Test
   public void testByteAt() {
      this.announce("byteAt");

      for(int i = 0; i < this.ref.length; ++i) {
         Assert.assertEquals((long)this.ref.bytes[i], (long)this.view.byteAt(i));
      }

   }

   @Test
   public void testByteAtNdim() {
      this.announce("byteAt (n-dim)");
      int[] index = new int[1];
      if (this.view.getShape().length != 1) {
         Assert.fail("Test not implemented if dimensions != 1");
      }

      for(int i = 0; i < this.ref.length; ++i) {
         index[0] = i;
         Assert.assertEquals((long)this.ref.bytes[i], (long)this.view.byteAt(index));
      }

      try {
         this.view.byteAt(0, 0);
         Assert.fail("Use of 2D index did not raise exception");
      } catch (PyException var3) {
         Assert.assertEquals(Py.BufferError, var3.type);
      }

   }

   @Test
   public void testIntAt() {
      this.announce("intAt");

      for(int i = 0; i < this.ref.length; ++i) {
         Assert.assertEquals((long)this.ref.ints[i], (long)this.view.intAt(i));
      }

   }

   @Test
   public void testIntAtNdim() {
      this.announce("intAt (n-dim)");
      int[] index = new int[1];
      if (this.view.getShape().length != 1) {
         Assert.fail("Test not implemented for dimensions != 1");
      }

      for(int i = 0; i < this.ref.length; ++i) {
         index[0] = i;
         Assert.assertEquals((long)this.ref.ints[i], (long)this.view.intAt(index));
      }

      try {
         this.view.intAt(0, 0);
         Assert.fail("Use of 2D index did not raise exception");
      } catch (PyException var3) {
         Assert.assertEquals(Py.BufferError, var3.type);
      }

   }

   @Test
   public void testStoreAt() {
      this.announce("storeAt");
      int n = this.ref.length;
      int[] exp = (int[])this.ref.ints.clone();
      int i;
      if (!this.spec.readonly) {
         for(i = 0; i < n; ++i) {
            byte v = (byte)(exp[i] ^ 3);
            this.view.storeAt(v, i);
         }

         for(i = 0; i < n; ++i) {
            Assert.assertEquals((long)(exp[i] ^ 3), (long)this.view.intAt(i));
         }
      } else {
         for(i = 0; i < n; ++i) {
            try {
               this.view.storeAt((byte)3, i);
               Assert.fail("Write access not prevented: " + this.spec);
            } catch (PyException var5) {
               Assert.assertEquals(Py.TypeError, var5.type);
            }
         }
      }

   }

   @Test
   public void testStoreAtNdim() {
      this.announce("storeAt (n-dim)");
      int[] index = new int[1];
      int n = this.ref.length;
      int[] exp = (int[])this.ref.ints.clone();
      int i;
      if (!this.spec.readonly) {
         for(i = 0; i < n; ++i) {
            index[0] = i;
            byte v = (byte)(exp[i] ^ 3);
            this.view.storeAt(v, index);
         }

         for(i = 0; i < n; ++i) {
            index[0] = i;
            Assert.assertEquals((long)(exp[i] ^ 3), (long)this.view.intAt(index));
         }

         if (this.spec.shape.length == 1) {
            try {
               this.view.storeAt((byte)1, 0, 0);
               Assert.fail("Use of 2D index did not raise exception");
            } catch (PyException var7) {
               Assert.assertEquals(Py.BufferError, var7.type);
            }
         }
      } else {
         for(i = 0; i < n; ++i) {
            index[0] = i;

            try {
               this.view.storeAt((byte)3, index);
               Assert.fail("Write access not prevented: " + this.spec);
            } catch (PyException var6) {
               Assert.assertEquals(Py.TypeError, var6.type);
            }
         }
      }

   }

   @Test
   public void testCopyTo() {
      int OFFSET = true;
      this.announce("copyTo");
      int n = this.ref.length;
      byte[] actual = new byte[n];
      this.view.copyTo(actual, 0);
      ByteBufferTestSupport.assertBytesEqual("copyTo() incorrect", this.ref.bytes, (byte[])actual, 0);
      actual = new byte[n + 10];
      this.view.copyTo(actual, 5);
      ByteBufferTestSupport.assertBytesEqual("copyTo(offset) incorrect", this.ref.bytes, (byte[])actual, 5);
      Assert.assertEquals("data before destination", 0L, (long)actual[4]);
      Assert.assertEquals("data after destination", 0L, (long)actual[5 + n]);
   }

   @Test
   public void testSliceCopyTo() {
      this.announce("copyTo (from slice)");
      int OFFSET = true;
      int n = this.ref.length;
      byte[] before = new byte[n + 6];
      byte BLANK = true;
      Arrays.fill(before, (byte)7);

      for(int destPos = 0; destPos <= 3; destPos += 3) {
         for(int srcIndex = 0; srcIndex <= 3; srcIndex += 3) {
            int trim;
            for(trim = 0; srcIndex + trim <= n; trim = 2 * trim + 1) {
               this.doTestSliceCopyTo(srcIndex, before, destPos, trim, n);
            }

            for(trim = 0; srcIndex + trim <= n; trim = 2 * trim + 1) {
               int length = n - srcIndex - trim;
               this.doTestSliceCopyTo(srcIndex, before, destPos, length, n);
            }
         }
      }

   }

   private void doTestSliceCopyTo(int srcIndex, byte[] before, int destPos, int length, int n) {
      if (this.verbosity > 1) {
         System.out.printf("  copy src[%d:%d] (%d) to dst[%d:%d] (%d)\n", srcIndex, srcIndex + length, n, destPos, destPos + length, before.length);
      }

      byte[] dest = (byte[])before.clone();
      this.view.copyTo(srcIndex, dest, destPos, length);
      byte[] viewBytes = PyBufferTestSupport.bytesFromByteAt(this.view);
      ByteBufferTestSupport.checkReadCorrect(this.ref.bytes, viewBytes, dest, destPos, length, 1, srcIndex, 1);
   }

   @Test
   public void testCopyFrom() {
      this.announce("copyFrom");
      int OFFSET = true;
      int L = this.ref.length;
      byte[] src = (new ByteBufferTestSupport.ByteMaterial(48, this.ref.length + 3, 1)).bytes;
      PyBufferTestSupport.TestSpec underlying = this.spec.getOriginal();
      int start = this.spec.getStart();
      int stride = this.spec.getStride();

      for(int srcPos = 0; srcPos <= 3; srcPos += 3) {
         for(int destIndex = 0; destIndex <= 3; destIndex += 3) {
            int trim;
            for(trim = 0; destIndex + trim <= L; trim = 2 * trim + 1) {
               this.doTestCopyFrom(src, srcPos, underlying, start, trim, stride, destIndex);
            }

            for(trim = 0; destIndex + trim <= L; trim = 2 * trim + 1) {
               int length = this.ref.length - destIndex - trim;
               this.doTestCopyFrom(src, srcPos, underlying, start, length, stride, destIndex);
            }
         }
      }

   }

   private void doTestCopyFrom(byte[] src, int srcPos, PyBufferTestSupport.TestSpec underlying, int start, int length, int stride, int destIndex) {
      if (this.verbosity > 1) {
         System.out.printf("  copy src[%d:%d] (%d) to dst[%d:%d]\n", srcPos, srcPos + length, this.ref.length, destIndex, destIndex + length);
      }

      this.createObjAndView();
      PyBuffer underlyingView = this.obj.getBuffer(underlying.flags & -2);
      byte[] before = PyBufferTestSupport.bytesFromByteAt(underlyingView);
      if (!this.spec.readonly) {
         this.view.copyFrom(src, srcPos, destIndex, length);
         byte[] after = PyBufferTestSupport.bytesFromByteAt(underlyingView);
         int underlyingDestIndex = start + destIndex * stride;
         ByteBufferTestSupport.checkWriteCorrect(before, after, src, srcPos, length, 1, underlyingDestIndex, stride);
      } else {
         try {
            this.view.copyFrom(src, srcPos, destIndex, length);
            Assert.fail("Write access not prevented: " + this.spec);
         } catch (PyException var12) {
            Assert.assertEquals(Py.TypeError, var12.type);
         }
      }

   }

   @Test
   public void testCopyFromPyBuffer() {
      this.announce("copyFrom (PyBuffer)");
      int n = this.spec.ref.length;
      int p = this.spec.getStride();
      int[] srcStrides;
      if (n < 2) {
         srcStrides = new int[]{1};
      } else if (p <= 2 && p >= -2) {
         if (p != 2 && p != -2) {
            srcStrides = new int[]{1, 2, -1, -2};
         } else {
            srcStrides = new int[]{1, 2, 3, -1, -2, -3};
         }
      } else {
         srcStrides = new int[]{1, p - 1, p, p + 1, -p + 1, -p, -p - 1};
      }

      int maxStride = 0;
      int[] var5 = srcStrides;
      int var6 = srcStrides.length;

      int srcMaterialSize;
      for(srcMaterialSize = 0; srcMaterialSize < var6; ++srcMaterialSize) {
         int stride = var5[srcMaterialSize];
         if (stride > maxStride) {
            maxStride = stride;
         } else if (-stride > maxStride) {
            maxStride = -stride;
         }
      }

      int maxOffset = n + 1;
      int[] srcOffsets = new int[]{0, (maxOffset + 1) / 3, maxOffset};
      srcMaterialSize = n * maxStride + maxOffset;
      ByteBufferTestSupport.ByteMaterial srcMaterial = new ByteBufferTestSupport.ByteMaterial(48, srcMaterialSize, 1);
      PyBufferTestSupport.ExporterFactory[] factories = new PyBufferTestSupport.ExporterFactory[]{this.spec.factory, new SimpleExporterFactory()};
      PyBufferTestSupport.ExporterFactory[] var10 = factories;
      int var11 = factories.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         PyBufferTestSupport.ExporterFactory factory = var10[var12];
         PyBufferTestSupport.TestSpec original = new PyBufferTestSupport.TestSpec(factory, srcMaterial);
         int[] var15 = srcStrides;
         int var16 = srcStrides.length;

         for(int var17 = 0; var17 < var16; ++var17) {
            int stride = var15[var17];
            int[] var19 = srcOffsets;
            int var20 = srcOffsets.length;

            for(int var21 = 0; var21 < var20; ++var21) {
               int offset = var19[var21];
               int start = stride > 0 ? offset : srcMaterialSize - offset - 1;
               this.doTestCopyFrom(original, start, n, stride);
            }
         }
      }

   }

   private void doTestCopyFrom(PyBufferTestSupport.TestSpec original, int start, int n, int stride) {
      PyBufferTestSupport.TestSpec srcSpec = new PyBufferTestSupport.SlicedTestSpec(original, 1, start, n, stride);
      PyBufferTestSupport.TestSpec.ObjectAndView pair = srcSpec.makePair();
      PyBuffer src = pair.view;
      byte[] srcBytes = srcSpec.ref.bytes;
      int s = this.spec.getStart();
      int p = this.spec.getStride();
      String srcName = pair.obj.getClass().getSimpleName();
      if (this.verbosity > 1) {
         int end = start + (n - 1) * stride + (stride > 0 ? 1 : -1);
         int e = s + (n - 1) * p + (p > 0 ? 1 : -1);
         System.out.printf("  copy from src[%d:%d:%d] %s(%d) to obj[%d:%d:%d]\n", start, end, stride, srcName, n, s, e, p);
      }

      this.createObjAndView();
      PyBufferTestSupport.TestSpec underlying = this.spec.getOriginal();
      PyBuffer underlyingView = this.obj.getBuffer(underlying.flags & -2);
      byte[] before = PyBufferTestSupport.bytesFromByteAt(underlyingView);
      if (!this.spec.readonly) {
         this.view.copyFrom(src);
         byte[] after = PyBufferTestSupport.bytesFromByteAt(underlyingView);
         ByteBufferTestSupport.checkWriteCorrect(before, after, srcBytes, 0, n, 1, s, p);
      } else {
         try {
            this.view.copyFrom(src);
            Assert.fail("Write access not prevented: " + this.spec);
         } catch (PyException var16) {
            Assert.assertEquals(Py.TypeError, var16.type);
         }
      }

   }

   @Test
   public void testCopyFromSelf() {
      this.announce("copyFrom (self)");
      int n = this.ref.length;
      PyBufferTestSupport.TestSpec original = this.spec.getOriginal();
      if (!this.spec.readonly && this.spec != original && n >= 1) {
         int p = this.spec.getStride();
         int L = original.ref.length;
         int[] srcStrides;
         if (n < 2) {
            srcStrides = new int[]{1};
         } else if (p <= 2 && p >= -2) {
            if (p != 2 && p != -2) {
               srcStrides = new int[]{1, 2, -1, -2};
            } else {
               srcStrides = new int[]{1, 2, 3, -1, -2, -3};
            }
         } else {
            srcStrides = new int[]{1, p - 1, p, p + 1, -p + 1, -p, -p - 1};
         }

         int[] var6 = srcStrides;
         int var7 = srcStrides.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            int srcStride = var6[var8];
            int minOffset;
            int incOffset;
            int srcOffset;
            if (srcStride > 0) {
               minOffset = L - 1 - srcStride * (n - 1);
               if (minOffset >= 0) {
                  incOffset = 1 + minOffset / 4;

                  for(srcOffset = 0; srcOffset <= minOffset; srcOffset += incOffset) {
                     this.doTestCopyFromSelf(srcOffset, srcStride, n);
                  }
               }
            } else {
               int absStride = -srcStride;
               minOffset = absStride * (n - 1) + 1;
               if (minOffset < L) {
                  incOffset = 1 + (L - 1 - minOffset) / 4;

                  for(srcOffset = L - 1; srcOffset > minOffset; srcOffset -= incOffset) {
                     this.doTestCopyFromSelf(srcOffset, srcStride, n);
                  }
               }
            }
         }

      }
   }

   private void doTestCopyFromSelf(int srcStart, int srcStride, int n) {
      this.createObjAndView();
      int dstStart = this.spec.getStart();
      int dstStride = this.spec.getStride();
      String srcName = this.obj.getClass().getSimpleName();
      if (this.verbosity > 1) {
         int srcEnd = srcStart + (n - 1) * srcStride + (srcStride > 0 ? 1 : -1);
         int dstEnd = dstStart + (n - 1) * dstStride + (dstStride > 0 ? 1 : -1);
         System.out.printf("  copy from obj[%d:%d:%d] %s(%d) to obj[%d:%d:%d]\n", srcStart, srcEnd, srcStride, srcName, n, dstStart, dstEnd, dstStride);
      }

      assert !this.spec.readonly;

      PyBuffer underlying = this.obj.getBuffer(284);
      Throwable var23 = null;

      try {
         byte[] before = PyBufferTestSupport.bytesFromByteAt(underlying);
         PyBuffer src = underlying.getBufferSlice(284, srcStart, n, srcStride);
         byte[] srcBytes = PyBufferTestSupport.bytesFromByteAt(src);
         this.view.copyFrom(src);
         byte[] after = PyBufferTestSupport.bytesFromByteAt(underlying);
         ByteBufferTestSupport.checkWriteCorrect(before, after, srcBytes, 0, n, 1, dstStart, dstStride);
      } catch (Throwable var20) {
         var23 = var20;
         throw var20;
      } finally {
         if (underlying != null) {
            if (var23 != null) {
               try {
                  underlying.close();
               } catch (Throwable var19) {
                  var23.addSuppressed(var19);
               }
            } else {
               underlying.close();
            }
         }

      }

   }

   public void testGetBufferForRead() {
      this.announce("getBuffer(READ): ");
      int[] var1 = this.spec.validFlags;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         int flags = var1[var3];
         int[] var5 = this.spec.validTassles;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            int tassles = var5[var7];
            PyBuffer view2 = this.view.getBuffer(flags | tassles);
            Assert.assertNotNull(view2);
         }
      }

   }

   public void testGetBufferForWrite() {
      this.announce("getBuffer(WRITE): ");
      int[] var1;
      int var2;
      int var3;
      int flags;
      if (!this.spec.readonly) {
         var1 = this.spec.validFlags;
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            flags = var1[var3];
            int[] var5 = this.spec.validTassles;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               int tassles = var5[var7];
               PyBuffer view2 = this.view.getBuffer(flags | tassles | 1);
               Assert.assertNotNull(view2);
            }
         }
      } else {
         var1 = this.spec.validFlags;
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            flags = var1[var3];

            try {
               this.view.getBuffer(flags | 1);
               Assert.fail("Write access not prevented: " + this.spec);
            } catch (PyException var10) {
               Assert.assertEquals(Py.BufferError, var10.type);
            }
         }
      }

   }

   @Test
   public void testReleaseTryWithResources() {
      this.announce("release (via try)");
      int flags = 28;

      try {
         PyBuffer c = this.obj.getBuffer(flags);
         Throwable var3 = null;

         try {
            PyBuffer b = this.obj.getBuffer(284);
            Throwable var5 = null;

            try {
               PyBuffer d = c.getBuffer(flags);
               Throwable var7 = null;

               try {
                  this.maybeCheckExporting(this.obj);
               } catch (Throwable var53) {
                  var7 = var53;
                  throw var53;
               } finally {
                  if (d != null) {
                     if (var7 != null) {
                        try {
                           d.close();
                        } catch (Throwable var52) {
                           var7.addSuppressed(var52);
                        }
                     } else {
                        d.close();
                     }
                  }

               }
            } catch (Throwable var55) {
               var5 = var55;
               throw var55;
            } finally {
               if (b != null) {
                  if (var5 != null) {
                     try {
                        b.close();
                     } catch (Throwable var51) {
                        var5.addSuppressed(var51);
                     }
                  } else {
                     b.close();
                  }
               }

            }

            this.maybeCheckExporting(this.obj);
            throw new Throwable("test");
         } catch (Throwable var57) {
            var3 = var57;
            throw var57;
         } finally {
            if (c != null) {
               if (var3 != null) {
                  try {
                     c.close();
                  } catch (Throwable var50) {
                     var3.addSuppressed(var50);
                  }
               } else {
                  c.close();
               }
            }

         }
      } catch (Throwable var59) {
         this.maybeCheckExporting(this.obj);
         this.view.release();
         this.maybeCheckNotExporting(this.obj);
      }
   }

   @Test
   public void testRelease() {
      this.announce("release");
      int flags = 28;
      PyBuffer a = this.view;
      PyBuffer b = this.obj.getBuffer(284);
      PyBuffer c = this.obj.getBuffer(flags);
      this.maybeCheckExporting(this.obj);
      b.release();
      a.release();
      this.maybeCheckExporting(this.obj);
      PyBuffer d = c.getBuffer(flags);
      c.release();
      this.maybeCheckExporting(this.obj);
      d.release();

      try {
         this.view.release();
         Assert.fail("excess release not detected");
      } catch (Exception var7) {
      }

   }

   @Test
   public void testGetAfterRelease() {
      this.announce("getBuffer (after release)");
      this.view.release();
      this.maybeCheckNotExporting(this.obj);
      this.maybeCheckNotExporting(this.view);

      try {
         this.view.getBuffer(284);
         Assert.fail("PyBuffer.getBuffer after final release not detected");
      } catch (Exception var3) {
         this.maybeCheckNotExporting(this.obj);
      }

      try {
         this.view.getBufferSlice(284, 0, 0);
         Assert.fail("PyBuffer.getBufferSlice after final release not detected");
      } catch (Exception var2) {
         this.maybeCheckNotExporting(this.obj);
      }

      PyBuffer b = this.obj.getBuffer(284);
      this.maybeCheckExporting(this.obj);
      b.release();
      this.maybeCheckNotExporting(this.obj);
   }

   private void maybeCheckExporting(BufferProtocol subject) {
      if (subject instanceof TestableExporter) {
         Assert.assertTrue("exports not being counted", ((TestableExporter)subject).isExporting());
      } else if (subject instanceof PyBuffer) {
         Assert.assertFalse("exports not being counted (PyBuffer)", ((PyBuffer)subject).isReleased());
      } else if (subject instanceof PyByteArray) {
         try {
            ((PyByteArray)subject).bytearray_append(Py.One);
            Assert.fail("bytearray_append with exports should fail");
         } catch (Exception var3) {
         }
      }

   }

   private void maybeCheckNotExporting(BufferProtocol subject) {
      if (subject instanceof TestableExporter) {
         Assert.assertFalse("exports counted incorrectly", ((TestableExporter)subject).isExporting());
      } else if (subject instanceof PyBuffer) {
         Assert.assertTrue("exports counted incorrectly (PyBuffer)", ((PyBuffer)subject).isReleased());
      } else if (subject instanceof PyByteArray) {
         try {
            PyByteArray sub = (PyByteArray)subject;
            sub.bytearray_append(Py.Zero);
            sub.del(sub.__len__() - 1);
         } catch (Exception var3) {
            Assert.fail("bytearray unexpectedly locked");
         }
      }

   }

   @Test
   public void testGetBufferSliceWithStride() {
      this.announce("getBuffer (slice & stride)");
      int N = this.ref.length;
      int M = (N + 4) / 4;

      for(int start = 0; start <= N; start += M) {
         int[] var4 = sliceLengths;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            int length = var4[var6];
            if (length == 0) {
               this.doTestGetBufferSliceWithStride(start, 0, 1);
               this.doTestGetBufferSliceWithStride(start, 0, 2);
            } else if (length == 1 && start < N) {
               this.doTestGetBufferSliceWithStride(start, 1, 1);
               this.doTestGetBufferSliceWithStride(start, 1, 2);
            } else if (start < N) {
               int[] var8 = sliceSteps;
               int var9 = var8.length;

               int var10;
               int step;
               for(var10 = 0; var10 < var9; ++var10) {
                  step = var8[var10];
                  if (start + (length - 1) * step < N) {
                     this.doTestGetBufferSliceWithStride(start, length, step);
                  }
               }

               var8 = sliceSteps;
               var9 = var8.length;

               for(var10 = 0; var10 < var9; ++var10) {
                  step = var8[var10];
                  if (start - (length - 1) * step >= 0) {
                     this.doTestGetBufferSliceWithStride(start, length, -step);
                  }
               }
            }
         }
      }

   }

   private void doTestGetBufferSliceWithStride(int first, int count, int step) {
      PyBufferTestSupport.TestSpec slicedSpec = new PyBufferTestSupport.SlicedTestSpec(this.spec, this.spec.getItemsize(), first, count, step);
      if (this.verbosity > 1) {
         System.out.printf("  slice first=%4d, count=%4d, step=%4d -> underlying start=%4d, stride=%4d\n", first, count, step, slicedSpec.getStart(), slicedSpec.getStride());
      }

      PyBuffer slicedView = this.view.getBufferSlice(this.spec.flags, first, count, step);
      byte[] slice = PyBufferTestSupport.bytesFromByteAt(slicedView);
      ByteBufferTestSupport.assertBytesEqual("slice incorrect", slicedSpec.ref.bytes, slice);
   }

   @Test
   public void testGetNIOByteBuffer() {
      this.announce("getNIOByteBuffer");
      int stride = this.spec.getStride();
      ByteBuffer bb = this.view.getNIOByteBuffer();
      ByteBufferTestSupport.assertBytesEqual("buffer does not match reference", this.ref.bytes, bb, stride);
      if (this.spec.readonly) {
         Assert.assertTrue("ByteBuffer should be read-only", bb.isReadOnly());
      } else {
         Assert.assertFalse("ByteBuffer should not be read-only", bb.isReadOnly());
      }

   }

   @Test
   public void testHasArray() {
      this.announce("hasArray");
      if (this.spec.hasArray) {
         Assert.assertTrue("a backing array was expected", this.view.hasArray());
      } else {
         Assert.assertFalse("no backing array was expected", this.view.hasArray());
      }

   }

   @Test
   public void testGetBuf() {
      this.announce("getBuf");
      if (this.spec.hasArray) {
         int stride = this.spec.getStride();
         PyBuffer.Pointer bp = this.view.getBuf();
         assertBytesEqual("buffer does not match reference", this.ref.bytes, bp, stride);
      }

   }

   @Test
   public void testGetPointer() {
      this.announce("getPointer");
      if (this.spec.hasArray) {
         int itemsize = this.spec.getItemsize();
         byte[] exp = new byte[itemsize];
         byte[] bytes = this.ref.bytes;

         for(int i = 0; i <= this.ref.length - itemsize; ++i) {
            int p = i * itemsize;

            for(int j = 0; j < itemsize; ++j) {
               exp[j] = bytes[p + j];
            }

            PyBuffer.Pointer bp = this.view.getPointer(i);
            assertBytesEqual("getPointer value", exp, bp);
         }
      }

   }

   @Test
   public void testGetPointerNdim() {
      int[] index = new int[1];
      this.announce("getPointer(array)");
      if (this.spec.hasArray) {
         int n = this.ref.length;
         int itemsize = this.view.getItemsize();
         byte[] exp = new byte[itemsize];
         byte[] bytes = this.ref.bytes;

         for(int i = 0; i < n; ++i) {
            int p = i * itemsize;

            for(int j = 0; j < itemsize; ++j) {
               exp[j] = bytes[p + j];
            }

            index[0] = i;
            PyBuffer.Pointer bp = this.view.getPointer(index);
            assertBytesEqual("getPointer value", exp, bp);
         }

         try {
            this.view.getPointer(0, 0);
            Assert.fail("Use of 2D index did not raise exception");
         } catch (PyException var9) {
            Assert.assertEquals(Py.BufferError, var9.type);
         }
      }

   }

   @Test
   public void testGetStrides() {
      this.announce("getStrides");
      int[] var1 = this.spec.validFlags;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         int flags = var1[var3];
         PyBuffer view = this.view.getBuffer(flags);
         int[] strides = view.getStrides();
         Assert.assertNotNull("strides[] should always be provided", strides);
         if (this.ref.bytes.length > 1) {
            ByteBufferTestSupport.assertIntsEqual("unexpected strides", this.spec.strides, strides);
         }
      }

   }

   @Test
   public void testGetSuboffsets() {
      this.announce("getSuboffsets");
      Assert.assertNull(this.view.getSuboffsets());
   }

   @Test
   public void testIsContiguous() {
      this.announce("isContiguous");
      int ndim = this.spec.shape[0];
      int stride = this.spec.getStride();
      int itemsize = this.spec.getItemsize();
      boolean contig = ndim < 2 || stride == itemsize;
      String[] var5 = validOrders;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String orderMsg = var5[var7];
         char order = orderMsg.charAt(0);
         Assert.assertEquals(orderMsg, this.view.isContiguous(order), contig);
      }

   }

   @Test
   public void testGetFormat() {
      this.announce("getFormat");
      PyBufferTestSupport.TestSpec spec = this.spec;
      int[] var2 = spec.validFlags;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int flags = var2[var4];
         PyBuffer view = this.view.getBuffer(flags);
         Assert.assertNotNull("format should always be provided", view.getFormat());
         Assert.assertEquals("B", view.getFormat());
         view = this.view.getBuffer(flags | 4);
         Assert.assertEquals("B", view.getFormat());
      }

   }

   @Test
   public void testGetItemsize() {
      this.announce("getItemsize");
      Assert.assertEquals(1L, (long)this.view.getItemsize());
   }

   @Test
   public void testToString() {
      this.announce("toString");
      String r = this.view.toString();
      Assert.assertEquals("buffer does not match reference", this.ref.string, r);
   }

   private static void assertBytesEqual(String message, byte[] expected, PyBuffer.Pointer bp) {
      assertBytesEqual(message, expected, bp, 1);
   }

   private static void assertBytesEqual(String message, byte[] expected, PyBuffer.Pointer bp, int stride) {
      ByteBufferTestSupport.assertBytesEqual(message, expected, 0, expected.length, bp.storage, bp.offset, stride);
   }

   private static class RollYourOwnArrayBuffer extends BaseBuffer {
      static final int FEATURES = 268435457;
      final byte[] storage;
      final PyBuffer root;

      public RollYourOwnArrayBuffer(int flags, BufferProtocol obj, byte[] storage) {
         this(flags, (PyBuffer)null, obj, storage, 0, storage.length, 1);
      }

      public RollYourOwnArrayBuffer(int flags, PyBuffer root, BufferProtocol obj, byte[] storage, int index0, int count, int stride) throws IndexOutOfBoundsException, NullPointerException, PyException {
         super(268435457 | (index0 == 0 && stride == 1 ? 0 : 24), index0, new int[]{count}, new int[]{stride});
         this.storage = storage;
         if (count > 0) {
            int end = index0 + (count - 1) * stride;
            int END = storage.length - 1;
            if (index0 < 0 || index0 > END || end < 0 || end > END) {
               throw new IndexOutOfBoundsException();
            }
         }

         this.checkRequestFlags(flags);
         if (root == null) {
            this.root = this;
            this.obj = obj;
         } else {
            this.root = root.getBuffer(284);
            this.obj = root.getObj();
         }

      }

      protected PyBuffer getRoot() {
         return this.root;
      }

      public PyBuffer getBufferSlice(int flags, int start, int length, int stride) {
         int newStart = this.index0 + start * this.strides[0];
         int newStride = this.strides[0] * stride;
         return new RollYourOwnArrayBuffer(flags, this.root, (BufferProtocol)null, this.storage, newStart, length, newStride);
      }

      public ByteBuffer getNIOByteBufferImpl() {
         return ByteBuffer.wrap(this.storage);
      }

      protected byte byteAtImpl(int byteIndex) {
         return this.storage[byteIndex];
      }

      protected void storeAtImpl(byte value, int byteIndex) throws IndexOutOfBoundsException, PyException {
         this.storage[byteIndex] = value;
      }
   }

   private static class RollYourOwnExporter extends TestableExporter {
      protected byte[] storage;

      public RollYourOwnExporter(byte[] storage) {
         this.storage = storage;
      }

      public PyBuffer getBuffer(int flags) {
         BaseBuffer pybuf = this.getExistingBuffer(flags);
         if (pybuf == null) {
            pybuf = new RollYourOwnArrayBuffer(flags, this, this.storage);
            this.export = new WeakReference(pybuf);
         }

         return (PyBuffer)pybuf;
      }
   }

   private static class SimpleWritableExporter extends TestableExporter {
      protected byte[] storage;

      public SimpleWritableExporter(byte[] storage) {
         this.storage = storage;
      }

      public PyBuffer getBuffer(int flags) {
         BaseBuffer pybuf = this.getExistingBuffer(flags);
         if (pybuf == null) {
            pybuf = new SimpleWritableBuffer(flags, this, this.storage) {
               protected void releaseAction() {
                  SimpleWritableExporter.this.export = null;
               }
            };
            this.export = new WeakReference(pybuf);
         }

         return (PyBuffer)pybuf;
      }
   }

   static class StringExporter extends TestableExporter {
      String storage;

      public StringExporter(String s) {
         this.storage = s;
      }

      public PyBuffer getBuffer(int flags) {
         BaseBuffer pybuf = this.getExistingBuffer(flags);
         if (pybuf == null) {
            pybuf = new SimpleStringBuffer(flags, this, this.storage);
            this.export = new SoftReference(pybuf);
         }

         return (PyBuffer)pybuf;
      }
   }

   abstract static class TestableExporter implements BufferProtocol {
      protected Reference export;

      protected BaseBuffer getExistingBuffer(int flags) {
         BaseBuffer pybuf = null;
         if (this.export != null) {
            pybuf = (BaseBuffer)this.export.get();
            if (pybuf != null) {
               pybuf = pybuf.getBufferAgain(flags);
            }
         }

         return pybuf;
      }

      public boolean isExporting() {
         if (this.export != null) {
            PyBuffer pybuf = (PyBuffer)this.export.get();
            if (pybuf != null) {
               return !pybuf.isReleased();
            }

            this.export = null;
         }

         return false;
      }
   }

   private static class SimpleExporterFactory extends PyBufferTestSupport.ReadonlyExporterFactory {
      private SimpleExporterFactory() {
      }

      public BufferProtocol make(ByteBufferTestSupport.ByteMaterial m) {
         return new SimpleExporter(m.getBytes());
      }

      // $FF: synthetic method
      SimpleExporterFactory(Object x0) {
         this();
      }
   }

   static class SimpleExporter implements BufferProtocol {
      protected byte[] storage;

      public SimpleExporter(byte[] storage) {
         this.storage = storage;
      }

      public PyBuffer getBuffer(int flags) {
         return new SimpleBuffer(flags, this, this.storage);
      }
   }
}
