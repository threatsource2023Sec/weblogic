package org.python.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PyBufferTestSupport {
   protected int verbosity;
   private final int[] sliceLengths;
   private final int[] sliceSteps;
   private List testSpecList;

   PyBufferTestSupport(int[] sliceLengths, int[] sliceSteps) {
      this(0, sliceLengths, sliceSteps);
   }

   PyBufferTestSupport(int verbosity, int[] sliceLengths, int[] sliceSteps) {
      this.testSpecList = new LinkedList();
      this.verbosity = verbosity;
      this.sliceLengths = sliceLengths;
      this.sliceSteps = sliceSteps;
   }

   void add(ExporterFactory factory, ByteBufferTestSupport.ByteMaterial material) {
      TestSpec original = new TestSpec(factory, material);
      this.queue(original);
      int N = original.ref.length;
      int M = (N + 4) / 4;

      for(int start = 0; start <= N; start += M) {
         int[] var7 = this.sliceLengths;
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            int length = var7[var9];
            if (length == 0) {
               this.queue(original, start, 0, 1);
               this.queue(original, start, 0, 2);
            } else if (length == 1 && start < N) {
               this.queue(original, start, 1, 1);
               this.queue(original, start, 1, 2);
            } else if (start < N) {
               int[] var11 = this.sliceSteps;
               int var12 = var11.length;

               int var13;
               int step;
               for(var13 = 0; var13 < var12; ++var13) {
                  step = var11[var13];
                  if (start + (length - 1) * step < N) {
                     this.queue(original, start, length, step);
                  }
               }

               var11 = this.sliceSteps;
               var12 = var11.length;

               for(var13 = 0; var13 < var12; ++var13) {
                  step = var11[var13];
                  if (start - (length - 1) * step >= 0) {
                     this.queue(original, start, length, -step);
                  }
               }
            }
         }
      }

   }

   private void queue(TestSpec spec) {
      if (this.verbosity > 2) {
         System.out.printf("queue non-slice: length=%d, readonly=%s\n", spec.ref.length, spec.readonly);
      }

      this.testSpecList.add(spec);
   }

   private void queue(TestSpec original, int start, int length, int step) {
      try {
         if (this.verbosity > 2) {
            System.out.printf("  queue slice: start=%4d, length=%4d, step=%4d\n", start, length, step);
         }

         TestSpec spec = new SlicedTestSpec(original, 1, start, length, step);
         this.testSpecList.add(spec);
      } catch (Exception var6) {
         if (this.verbosity > 2) {
            System.out.printf("*** SKIP %s\n", var6);
         }
      }

   }

   List getTestData() {
      List r = new ArrayList(this.testSpecList.size());
      Iterator var2 = this.testSpecList.iterator();

      while(var2.hasNext()) {
         TestSpec spec = (TestSpec)var2.next();
         r.add(new TestSpec[]{spec});
      }

      return r;
   }

   static byte[] bytesFromByteAt(PyBuffer v) {
      int N = v.getLen();
      byte[] a = new byte[N];

      for(int i = 0; i < N; ++i) {
         a[i] = v.byteAt(i);
      }

      return a;
   }

   static class SlicedTestSpec extends TestSpec {
      final int itemsize;
      final int first;
      final int count;
      final int step;
      final int start;
      static final int[] strided1DFlags = new int[]{24, 280, 284};
      static final int[] strided1DTassles = new int[]{0, 4};

      SlicedTestSpec(TestSpec parent, int itemsize, int first, int count, int step) {
         super(parent, parent.ref.slice(itemsize, first, count, step), new int[]{count}, new int[1], strided1DFlags, strided1DTassles);
         if (parent.getItemsize() != 1) {
            throw new IllegalArgumentException("Only byte-array parent supported");
         } else {
            this.itemsize = itemsize;
            this.first = first;
            this.count = count;
            this.step = step;
            this.start = parent.getStart() + first * parent.getStride();
            this.strides[0] = step * parent.getStride();
         }
      }

      int getItemsize() {
         return this.itemsize;
      }

      int getStart() {
         return this.start;
      }

      public TestSpec.ObjectAndView makePair() {
         TestSpec.ObjectAndView pair = this.parent.makePair();
         PyBuffer view = pair.view.getBufferSlice(this.flags, this.first, this.count, this.step);
         pair.view.release();
         return new TestSpec.ObjectAndView(pair.obj, view);
      }
   }

   static class TestSpec {
      final ExporterFactory factory;
      final ByteBufferTestSupport.ByteMaterial ref;
      final boolean readonly;
      final boolean hasArray;
      final TestSpec parent;
      final int[] shape;
      final int[] strides;
      final int flags;
      final int[] validFlags;
      final int[] validTassles;
      static final int[] simpleFlags = new int[]{0, 8, 24, 280, 284};
      static final int[] simpleTassles = new int[]{0, 4, 56, 88, 152};

      TestSpec(ExporterFactory factory, ByteBufferTestSupport.ByteMaterial ref) {
         this((TestSpec)null, factory, ref, new int[]{ref.length}, new int[]{1}, simpleFlags, simpleTassles);
      }

      protected TestSpec(TestSpec parent, ByteBufferTestSupport.ByteMaterial ref, int[] shape, int[] strides, int[] validFlags, int[] validTassles) {
         this(parent, parent.getOriginal().factory, ref, shape, strides, validFlags, validTassles);
      }

      protected TestSpec(TestSpec parent, ExporterFactory factory, ByteBufferTestSupport.ByteMaterial ref, int[] shape, int[] strides, int[] validFlags, int[] validTassles) {
         this.parent = parent;
         this.factory = factory;
         this.readonly = factory.isReadonly();
         this.hasArray = factory.hasArray();
         this.flags = (this.readonly ? 284 : 285) | (this.hasArray ? 268435456 : 0);
         this.ref = ref;
         this.shape = shape;
         this.strides = strides;
         this.validFlags = validFlags;
         this.validTassles = validTassles;
      }

      final TestSpec getParent() {
         return this.parent;
      }

      final boolean isOriginal() {
         return this.parent == null;
      }

      final TestSpec getOriginal() {
         TestSpec p;
         for(p = this; !p.isOriginal(); p = p.getParent()) {
         }

         return p;
      }

      int getItemsize() {
         return 1;
      }

      int getStride() {
         return this.strides[0];
      }

      int getStart() {
         return 0;
      }

      public ObjectAndView makePair() {
         BufferProtocol obj = this.factory.make(this.ref);
         PyBuffer view = obj.getBuffer(this.flags);
         return new ObjectAndView(obj, view);
      }

      public String toString() {
         ObjectAndView pair = this.makePair();
         BufferProtocol obj = pair.obj;
         PyBuffer view = pair.view;
         StringBuilder sb = new StringBuilder(100);
         sb.append(obj.getClass().getSimpleName()).append('[');
         int stride = this.getStride();
         int offset;
         if (view.hasArray()) {
            offset = view.getBuf().offset;
         } else {
            offset = view.getNIOByteBuffer().position();
         }

         if (offset > 0) {
            sb.append(offset);
         }

         String plus = offset == 0 ? "" : "+";
         if (stride == 1) {
            sb.append(plus).append("k]");
         } else if (stride == -1) {
            sb.append("-k]");
         } else if (stride < 0) {
            sb.append("-").append(-stride).append("*k]");
         } else {
            sb.append(plus).append(stride).append("*k]");
         }

         while(sb.length() < 30) {
            sb.append(' ');
         }

         sb.append(view.isReadonly() ? "R " : "W ");
         sb.append("ref = ").append(this.ref.toString());
         return sb.toString();
      }

      static class ObjectAndView {
         final BufferProtocol obj;
         final PyBuffer view;

         ObjectAndView(BufferProtocol obj, PyBuffer view) {
            this.obj = obj;
            this.view = view;
         }
      }
   }

   abstract static class WritableExporterFactory implements ExporterFactory {
      public boolean isReadonly() {
         return false;
      }

      public boolean hasArray() {
         return true;
      }
   }

   abstract static class ReadonlyExporterFactory implements ExporterFactory {
      public boolean isReadonly() {
         return true;
      }

      public boolean hasArray() {
         return true;
      }
   }

   interface ExporterFactory {
      BufferProtocol make(ByteBufferTestSupport.ByteMaterial var1);

      boolean isReadonly();

      boolean hasArray();
   }
}
