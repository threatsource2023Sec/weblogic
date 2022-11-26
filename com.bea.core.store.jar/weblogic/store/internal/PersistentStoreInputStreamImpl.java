package weblogic.store.internal;

import java.io.IOException;
import java.nio.ByteBuffer;
import weblogic.utils.io.ByteBufferObjectInputStream;

public final class PersistentStoreInputStreamImpl extends ByteBufferObjectInputStream {
   PersistentStoreInputStreamImpl(ByteBuffer[] data) throws IOException {
      super(data);
   }
}
