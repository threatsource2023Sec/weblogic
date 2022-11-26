package weblogic.jms.common;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.OutputStream;

public abstract class BufferOutputStream extends OutputStream implements DataOutput, JMSOutputStream, Payload {
   abstract boolean isJMSStoreOutputStream();

   abstract void setIsJMSStoreOutputStream();

   abstract void setIsBypassOutputStream();

   public abstract void setIsJMSMulticastOutputStream();

   public abstract void reset();

   public abstract ObjectOutput getObjectOutput() throws IOException;

   abstract void copyBuffer() throws JMSException;

   abstract void writeUTF32(String var1) throws IOException;

   public abstract int size();

   abstract Payload moveToPayload();

   abstract PayloadStream copyPayloadWithoutSharedStream() throws JMSException;
}
