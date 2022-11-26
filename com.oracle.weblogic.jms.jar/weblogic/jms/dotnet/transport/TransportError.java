package weblogic.jms.dotnet.transport;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.jms.JMSException;

public final class TransportError implements MarshalWritable, MarshalReadable {
   public static final int TYPE_CODE = 20001;
   private Throwable throwable;
   private boolean isRemoteException;
   private ArrayList exceptionArray;

   public TransportError() {
   }

   public TransportError(Throwable t) {
      this.throwable = t;
      this.isRemoteException = false;
   }

   public TransportError(Throwable t, boolean isPeerGone) {
      this.throwable = t;
      this.isRemoteException = false;
   }

   public TransportError(String s, boolean isPeerGone) {
      this.throwable = new RuntimeException(s);
      this.isRemoteException = false;
   }

   public boolean isPeerGone() {
      throw new AssertionError("not implemented");
   }

   public int getMarshalTypeCode() {
      return 20001;
   }

   public ArrayList getExceptionNames() {
      return this.exceptionArray;
   }

   public boolean IsRemoteException() {
      return this.isRemoteException;
   }

   public void marshal(MarshalWriter mw) {
      mw.writeUnsignedByte(1);
      mw.writeUnsignedByte(0);
      if (this.throwable != null) {
         ArrayList exceptionNames = new ArrayList(5);
         exceptionNames.add(this.throwable.getClass().getName());
         if (this.throwable instanceof JMSException) {
            for(Throwable tCause = ((JMSException)this.throwable).getLinkedException(); tCause != null; tCause = ((Throwable)tCause).getCause()) {
               exceptionNames.add(tCause.getClass().getName());
            }
         }

         if (exceptionNames.size() == 1) {
            for(Throwable tCause = this.throwable.getCause(); tCause != null; tCause = tCause.getCause()) {
               exceptionNames.add(tCause.getClass().getName());
            }
         }

         mw.writeInt(exceptionNames.size() + 1);

         for(int index = 0; index < exceptionNames.size(); ++index) {
            mw.writeString((String)((String)exceptionNames.get(index)));
         }

         ByteArrayOutputStream ostr = new ByteArrayOutputStream();
         this.throwable.printStackTrace(new PrintStream(ostr));
         mw.writeString(ostr.toString());
      } else {
         mw.writeString("null");
      }

   }

   public void unmarshal(MarshalReader mr) {
      int version = mr.read();

      while((mr.readByte() & 1) != 0) {
      }

      this.isRemoteException = true;
      int numStrings = mr.readInt();

      for(int index = 0; index < numStrings - 1; ++index) {
         this.exceptionArray.add(mr.readString());
      }

      this.throwable = new Throwable(mr.readString());
   }

   public void printStackTrace() {
      if (this.throwable != null) {
         this.throwable.printStackTrace();
      }

   }

   public String toString() {
      return "TransportError:<" + this.throwable + ">";
   }
}
