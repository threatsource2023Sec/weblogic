package weblogic.iiop.protocol;

import java.io.ObjectOutput;
import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.spi.MessageHolder;
import weblogic.protocol.AsyncOutgoingMessage;
import weblogic.rmi.spi.MsgOutput;
import weblogic.utils.io.Chunk;

public abstract class CorbaOutputStream extends OutputStream implements AsyncOutgoingMessage, CorbaStream, ObjectOutput, MessageHolder, MsgOutput {
   public abstract void setMaxStreamFormatVersion(byte var1);

   public abstract byte getMaxStreamFormatVersion();

   public abstract long startEncapsulation();

   public abstract void endEncapsulation(long var1);

   public abstract void write_repository_id(RepositoryId var1);

   public abstract boolean isSecure();

   public abstract void write_any(Any var1, TypeCode var2);

   public abstract void write_octet_sequence(byte[] var1);

   public abstract boolean supportsTLS();

   public abstract void write_unsigned_short(int var1);

   public abstract void putEndian();

   public abstract byte[] getBuffer();

   public void close() {
   }

   public abstract void startUnboundedEncapsulation();

   public abstract void setCodeSets(int var1, int var2);

   public abstract Chunk getChunks();

   public abstract void setLittleEndian(boolean var1);
}
