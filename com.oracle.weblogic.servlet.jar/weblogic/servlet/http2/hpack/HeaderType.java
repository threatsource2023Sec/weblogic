package weblogic.servlet.http2.hpack;

import java.nio.ByteBuffer;

public interface HeaderType {
   int getMask();

   int getPrefixBits();

   boolean isTheType(byte var1);

   HeaderEntry decoderHeader(ByteBuffer var1, DynamicTable var2) throws HpackException;
}
