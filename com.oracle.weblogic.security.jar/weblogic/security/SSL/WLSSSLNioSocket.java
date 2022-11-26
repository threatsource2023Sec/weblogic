package weblogic.security.SSL;

import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.WritableByteChannel;

public interface WLSSSLNioSocket {
   ReadableByteChannel getReadableByteChannel();

   WritableByteChannel getWritableByteChannel();

   SelectableChannel getSelectableChannel();
}
