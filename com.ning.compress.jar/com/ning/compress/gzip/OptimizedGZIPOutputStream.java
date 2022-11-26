package com.ning.compress.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class OptimizedGZIPOutputStream extends OutputStream {
   private static final int GZIP_MAGIC = 35615;
   static final byte[] DEFAULT_HEADER = new byte[]{31, -117, 8, 0, 0, 0, 0, 0, 0, -1};
   protected Deflater _deflater;
   protected final GZIPRecycler _gzipRecycler = GZIPRecycler.instance();
   protected final byte[] _eightByteBuffer = new byte[8];
   protected OutputStream _rawOut;
   protected DeflaterOutputStream _deflaterOut;
   protected CRC32 _crc;

   public OptimizedGZIPOutputStream(OutputStream out) throws IOException {
      this._rawOut = out;
      this._rawOut.write(DEFAULT_HEADER);
      this._deflater = this._gzipRecycler.allocDeflater();
      this._deflaterOut = new DeflaterOutputStream(this._rawOut, this._deflater, 4000);
      this._crc = new CRC32();
   }

   public void close() throws IOException {
      this._deflaterOut.finish();
      this._deflaterOut = null;
      this._writeTrailer(this._rawOut);
      this._rawOut.close();
      Deflater d = this._deflater;
      if (d != null) {
         this._deflater = null;
         this._gzipRecycler.releaseDeflater(d);
      }

   }

   public void flush() throws IOException {
      this._deflaterOut.flush();
   }

   public final void write(byte[] buf) throws IOException {
      this.write(buf, 0, buf.length);
   }

   public final void write(int c) throws IOException {
      this._eightByteBuffer[0] = (byte)c;
      this.write(this._eightByteBuffer, 0, 1);
   }

   public void write(byte[] buf, int off, int len) throws IOException {
      this._deflaterOut.write(buf, off, len);
      this._crc.update(buf, off, len);
   }

   private void _writeTrailer(OutputStream out) throws IOException {
      _putInt(this._eightByteBuffer, 0, (int)this._crc.getValue());
      _putInt(this._eightByteBuffer, 4, this._deflater.getTotalIn());
      out.write(this._eightByteBuffer, 0, 8);
   }

   private static final void _putInt(byte[] buf, int offset, int value) {
      buf[offset++] = (byte)value;
      buf[offset++] = (byte)(value >> 8);
      buf[offset++] = (byte)(value >> 16);
      buf[offset] = (byte)(value >> 24);
   }
}
