package weblogic.iiop;

import java.io.IOException;
import org.omg.CORBA.SystemException;
import weblogic.corba.utils.CorbaUtils;
import weblogic.rmi.spi.MsgOutput;

public abstract class AbstractMsgOutput implements MsgOutput {
   protected static final boolean DEBUG = false;
   final IIOPOutputStream delegate;

   public AbstractMsgOutput(IIOPOutputStream delegate) {
      this.delegate = delegate;
   }

   public final void write(int i) throws IOException {
      this.delegate.write(i);
   }

   public final void write(byte[] b) throws IOException {
      try {
         this.delegate.write(b);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final void write(byte[] b, int i, int j) throws IOException {
      try {
         this.delegate.write(b, i, j);
      } catch (SystemException var5) {
         throw CorbaUtils.mapSystemException(var5);
      }
   }

   public final void writeBoolean(boolean b) throws IOException {
      try {
         this.delegate.writeBoolean(b);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final void writeInt(int v) throws IOException {
      try {
         this.delegate.writeInt(v);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final void writeShort(int s) throws IOException {
      try {
         this.delegate.writeShort((short)s);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final void writeLong(long l) throws IOException {
      try {
         this.delegate.writeLong(l);
      } catch (SystemException var4) {
         throw CorbaUtils.mapSystemException(var4);
      }
   }

   public final void writeDouble(double d) throws IOException {
      try {
         this.delegate.writeDouble(d);
      } catch (SystemException var4) {
         throw CorbaUtils.mapSystemException(var4);
      }
   }

   public final void writeFloat(float f) throws IOException {
      try {
         this.delegate.writeFloat(f);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final void writeByte(int b) throws IOException {
      try {
         this.delegate.writeByte((byte)b);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final void close() throws IOException {
      try {
         this.delegate.close();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public void flush() throws IOException {
      this.delegate.flush();
   }

   abstract AbstractMsgInput createMsgInput(IIOPInputStream var1);

   protected final void p(String msg) {
      System.out.println("<AbstractMsgOutput>: " + msg);
   }

   static {
      IIOPService.load();
   }
}
