package com.ning.compress.gzip;

import java.lang.ref.SoftReference;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class GZIPRecycler {
   protected static final ThreadLocal _recyclerRef = new ThreadLocal();
   protected Inflater _inflater;
   protected Deflater _deflater;

   public static GZIPRecycler instance() {
      SoftReference ref = (SoftReference)_recyclerRef.get();
      GZIPRecycler br = ref == null ? null : (GZIPRecycler)ref.get();
      if (br == null) {
         br = new GZIPRecycler();
         _recyclerRef.set(new SoftReference(br));
      }

      return br;
   }

   public Deflater allocDeflater() {
      Deflater d = this._deflater;
      if (d == null) {
         d = new Deflater(-1, true);
      } else {
         this._deflater = null;
      }

      return d;
   }

   public void releaseDeflater(Deflater d) {
      if (d != null) {
         d.reset();
         this._deflater = d;
      }

   }

   public Inflater allocInflater() {
      Inflater i = this._inflater;
      if (i == null) {
         i = new Inflater(true);
      } else {
         this._inflater = null;
      }

      return i;
   }

   public void releaseInflater(Inflater i) {
      if (i != null) {
         i.reset();
         this._inflater = i;
      }

   }
}
