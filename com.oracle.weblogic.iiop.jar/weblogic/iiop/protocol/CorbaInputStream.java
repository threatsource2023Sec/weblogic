package weblogic.iiop.protocol;

import java.io.ObjectInput;
import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA_2_3.portable.InputStream;
import weblogic.corba.utils.RepositoryId;
import weblogic.rmi.spi.MsgInput;

public abstract class CorbaInputStream extends InputStream implements MsgInput, ObjectInput, CorbaStream {
   public abstract int peek_long();

   public abstract Any read_any(TypeCode var1);

   public abstract long startEncapsulation();

   public abstract boolean consumeEndian();

   public abstract void endEncapsulation(long var1);

   public abstract int bytesLeft(long var1);

   public abstract byte[] read_octet_sequence(int var1);

   public abstract void reset();

   public abstract void setLittleEndian(boolean var1);

   public abstract byte[] read_octet_sequence();

   public abstract IDLEntity read_IDLEntity(Class var1);

   public abstract RepositoryId read_repository_id();

   public abstract void setPossibleCodebase(String var1);

   public abstract void setMinorVersion(int var1);

   public abstract void addChunks(CorbaInputStream var1);

   public abstract void setCodeSets(int var1, int var2);

   public abstract int getCharCodeset();

   public abstract int getWcharCodeset();

   public abstract boolean isSecure();

   public abstract int read_unsigned_short();

   public void close() {
   }
}
