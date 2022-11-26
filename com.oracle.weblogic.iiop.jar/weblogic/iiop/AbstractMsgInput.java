package weblogic.iiop;

import java.io.IOException;
import org.omg.CORBA.SystemException;
import weblogic.corba.utils.CorbaUtils;
import weblogic.corba.utils.RemoteInfo;
import weblogic.iiop.ior.IOR;
import weblogic.rmi.spi.MsgInput;

public abstract class AbstractMsgInput implements MsgInput {
   protected static final boolean DEBUG = false;
   protected final IIOPInputStream delegate;

   public AbstractMsgInput(IIOPInputStream delegate) {
      this.delegate = delegate;
   }

   public final void readFully(byte[] b) throws IOException {
      try {
         this.delegate.readFully(b);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final void readFully(byte[] b, int i, int j) throws IOException {
      try {
         this.delegate.readFully(b, i, j);
      } catch (SystemException var5) {
         throw CorbaUtils.mapSystemException(var5);
      }
   }

   public final int skipBytes(int i) throws IOException {
      try {
         return this.delegate.skipBytes(i);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final boolean readBoolean() throws IOException {
      try {
         return this.delegate.readBoolean();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public final int readInt() throws IOException {
      try {
         return this.delegate.readInt();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public final short readShort() throws IOException {
      try {
         return this.delegate.readShort();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public final int readUnsignedShort() throws IOException {
      try {
         return this.delegate.readUnsignedShort();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public final long readLong() throws IOException {
      try {
         return this.delegate.readLong();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public final double readDouble() throws IOException {
      try {
         return this.delegate.readDouble();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public final float readFloat() throws IOException {
      try {
         return this.delegate.readFloat();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public final byte readByte() throws IOException {
      try {
         return this.delegate.readByte();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public final int readUnsignedByte() throws IOException {
      try {
         return this.delegate.readUnsignedByte();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   final Object readRemote(Class c) throws IOException {
      try {
         IOR ior = new IOR(this.delegate);
         if (c != null && CorbaUtils.isARemote(c)) {
            RemoteInfo rinfo = RemoteInfo.findRemoteInfo(c);
            IIOPReplacer.getIIOPReplacer();
            return IIOPReplacer.resolveObject(ior, rinfo);
         } else {
            IIOPReplacer.getIIOPReplacer();
            return IIOPReplacer.resolveObject(ior);
         }
      } catch (SystemException var4) {
         throw CorbaUtils.mapSystemException(var4);
      }
   }

   public void close() throws IOException {
      try {
         this.delegate.close();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public Object readObject() throws ClassNotFoundException, IOException {
      return this.delegate.readObject();
   }

   public final int available() throws IOException {
      return this.delegate.available();
   }

   public final long skip(long l) {
      return this.delegate.skip(l);
   }

   public final int read(byte[] b, int i, int j) throws IOException {
      try {
         return this.delegate.read(b, i, j);
      } catch (SystemException var5) {
         throw CorbaUtils.mapSystemException(var5);
      }
   }

   public final int read(byte[] b) throws IOException {
      try {
         return this.delegate.read(b);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final int read() throws IOException {
      try {
         return this.delegate.read();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   abstract AbstractMsgOutput createMsgOutput(IIOPOutputStream var1);

   protected final void p(String msg) {
      System.out.println("<AbstractMsgInput>: " + msg);
   }

   static {
      IIOPService.load();
   }
}
