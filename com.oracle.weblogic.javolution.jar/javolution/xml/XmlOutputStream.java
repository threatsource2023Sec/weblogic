package javolution.xml;

import java.io.IOException;
import java.io.OutputStream;
import javolution.lang.Reusable;

public class XmlOutputStream extends OutputStream implements Reusable {
   static final byte END_XML = -2;
   private OutputStream _outputStream;
   private final ObjectWriter _objectWriter;
   private final OutputStreamProxy _outputStreamProxy;

   public XmlOutputStream() {
      this(new ObjectWriter());
   }

   public XmlOutputStream(ObjectWriter var1) {
      this._outputStreamProxy = new OutputStreamProxy();
      this._objectWriter = var1;
   }

   public XmlOutputStream setOutputStream(OutputStream var1) {
      if (this._outputStream != null) {
         throw new IllegalStateException("Stream not closed or reset");
      } else {
         this._outputStream = var1;
         return this;
      }
   }

   public void writeObject(Object var1) throws IOException {
      if (this._outputStream == null) {
         throw new IOException("Stream closed");
      } else {
         this._objectWriter.write(var1, (OutputStream)this._outputStreamProxy);
         this._outputStream.write(-2);
         this._outputStream.flush();
      }
   }

   public void write(int var1) throws IOException {
      if (this._outputStream == null) {
         throw new IOException("Stream closed");
      } else {
         this._outputStream.write(var1);
      }
   }

   public void flush() throws IOException {
      if (this._outputStream == null) {
         throw new IOException("Stream closed");
      } else {
         this._outputStream.flush();
      }
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      if (this._outputStream == null) {
         throw new IOException("Stream closed");
      } else {
         this._outputStream.write(var1, var2, var3);
      }
   }

   public void close() throws IOException {
      if (this._outputStream != null) {
         this._outputStream.close();
         this.reset();
      }

   }

   public void reset() {
      this._objectWriter.reset();
      this._outputStream = null;
   }

   static OutputStream access$100(XmlOutputStream var0) {
      return var0._outputStream;
   }

   private final class OutputStreamProxy extends OutputStream {
      private OutputStreamProxy() {
      }

      public void flush() throws IOException {
         XmlOutputStream.access$100(XmlOutputStream.this).flush();
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         XmlOutputStream.access$100(XmlOutputStream.this).write(var1, var2, var3);
      }

      public void write(int var1) throws IOException {
         XmlOutputStream.access$100(XmlOutputStream.this).write(var1);
      }

      public void close() throws IOException {
      }

      OutputStreamProxy(Object var2) {
         this();
      }
   }
}
