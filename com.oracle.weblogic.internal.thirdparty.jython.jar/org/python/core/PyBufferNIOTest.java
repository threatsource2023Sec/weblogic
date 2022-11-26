package org.python.core;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.runners.Parameterized.Parameters;
import org.python.core.buffer.BaseBuffer;
import org.python.core.buffer.SimpleNIOBuffer;

public class PyBufferNIOTest extends PyBufferTest {
   public PyBufferNIOTest(PyBufferTestSupport.TestSpec spec) {
      super(spec);
   }

   @Parameters
   public static Collection genTestSpecs() {
      PyBufferTestSupport s = new PyBufferTestSupport(sliceLengths, sliceSteps);
      PyBufferTestSupport.ExporterFactory rollYourOwnExporter = new PyBufferTestSupport.WritableExporterFactory() {
         public BufferProtocol make(ByteBufferTestSupport.ByteMaterial m) {
            return new RollYourOwnExporter(m.getBuffer());
         }
      };
      s.add(rollYourOwnExporter, byteMaterial);
      s.add(rollYourOwnExporter, emptyMaterial);
      PyBufferTestSupport.ExporterFactory readonlyHeapNIOExporter = new TestNIOExporterFactory(false, false);
      s.add(readonlyHeapNIOExporter, emptyMaterial);
      s.add(readonlyHeapNIOExporter, byteMaterial);
      s.add(readonlyHeapNIOExporter, longMaterial);
      PyBufferTestSupport.ExporterFactory writableHeapNIOExporter = new TestNIOExporterFactory(true, false);
      s.add(writableHeapNIOExporter, emptyMaterial);
      s.add(writableHeapNIOExporter, byteMaterial);
      s.add(writableHeapNIOExporter, longMaterial);
      PyBufferTestSupport.ExporterFactory readonlyDirectNIOExporter = new TestNIOExporterFactory(false, true);
      s.add(readonlyDirectNIOExporter, emptyMaterial);
      s.add(readonlyDirectNIOExporter, byteMaterial);
      s.add(readonlyDirectNIOExporter, longMaterial);
      PyBufferTestSupport.ExporterFactory writableDirectNIOExporter = new TestNIOExporterFactory(true, true);
      s.add(writableDirectNIOExporter, emptyMaterial);
      s.add(writableDirectNIOExporter, byteMaterial);
      s.add(writableDirectNIOExporter, longMaterial);
      List ret = s.getTestData();
      int key = 0;
      Iterator var8 = ret.iterator();

      while(var8.hasNext()) {
         PyBufferTestSupport.TestSpec[] r = (PyBufferTestSupport.TestSpec[])var8.next();
         PyBufferTestSupport.TestSpec spec = r[0];
         System.out.printf("%6d : %s\n", key++, spec.toString());
      }

      return ret;
   }

   private static class RollYourOwnNIOBuffer extends BaseBuffer {
      static final int FEATURES = 268435457;
      final ByteBuffer storage;
      final PyBuffer root;

      public RollYourOwnNIOBuffer(int flags, BufferProtocol obj, ByteBuffer storage) {
         this(flags, (PyBuffer)null, obj, storage, storage.position(), storage.remaining(), 1);
      }

      public RollYourOwnNIOBuffer(int flags, PyBuffer root, BufferProtocol obj, ByteBuffer storage, int index0, int count, int stride) throws IndexOutOfBoundsException, NullPointerException, PyException {
         super(268435457 | (index0 == 0 && stride == 1 ? 0 : 24), index0, new int[]{count}, new int[]{stride});
         this.storage = storage.duplicate();
         if (count > 0) {
            int end = index0 + (count - 1) * stride;
            int END = storage.capacity() - 1;
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

      public PyBuffer getBufferSlice(int flags, int start, int count, int stride) {
         int newStart = this.index0 + start * this.strides[0];
         int newStride = this.strides[0] * stride;
         return new RollYourOwnNIOBuffer(flags, this.root, (BufferProtocol)null, this.storage, newStart, count, newStride);
      }

      public ByteBuffer getNIOByteBufferImpl() {
         return this.storage.duplicate();
      }

      protected byte byteAtImpl(int byteIndex) {
         return this.storage.get(byteIndex);
      }

      protected void storeAtImpl(byte value, int byteIndex) throws IndexOutOfBoundsException, PyException {
         this.storage.put(byteIndex, value);
      }
   }

   private static class RollYourOwnExporter extends PyBufferTest.TestableExporter {
      protected ByteBuffer storage;

      public RollYourOwnExporter(ByteBuffer storage) {
         this.storage = storage;
      }

      public PyBuffer getBuffer(int flags) {
         BaseBuffer pybuf = this.getExistingBuffer(flags);
         if (pybuf == null) {
            pybuf = new RollYourOwnNIOBuffer(flags, this, this.storage);
            this.export = new WeakReference(pybuf);
         }

         return (PyBuffer)pybuf;
      }
   }

   static class TestNIOExporterFactory implements PyBufferTestSupport.ExporterFactory {
      final boolean writable;
      final boolean isDirect;

      TestNIOExporterFactory(boolean writable, boolean isDirect) {
         this.writable = writable;
         this.isDirect = isDirect;
      }

      public boolean isWritable() {
         return this.writable;
      }

      public boolean isDirect() {
         return this.isDirect;
      }

      public BufferProtocol make(ByteBufferTestSupport.ByteMaterial m) {
         ByteBuffer bb = m.getBuffer();
         if (this.isDirect) {
            ByteBuffer direct = ByteBuffer.allocateDirect(bb.capacity());
            direct.put(bb).flip();
            bb = direct;
         }

         if (!this.writable) {
            bb = bb.asReadOnlyBuffer();
         }

         return new TestNIOExporter(bb);
      }

      public boolean isReadonly() {
         return !this.writable;
      }

      public boolean hasArray() {
         return !this.isDirect && this.writable;
      }
   }

   private static class TestNIOExporter extends PyBufferTest.TestableExporter {
      protected ByteBuffer storage;

      public TestNIOExporter(ByteBuffer storage) {
         this.storage = storage;
      }

      public PyBuffer getBuffer(int flags) {
         BaseBuffer pybuf = this.getExistingBuffer(flags);
         if (pybuf == null) {
            pybuf = new SimpleNIOBuffer(flags, this, this.storage) {
               protected void releaseAction() {
                  TestNIOExporter.this.export = null;
               }
            };
            this.export = new WeakReference(pybuf);
         }

         return (PyBuffer)pybuf;
      }
   }
}
