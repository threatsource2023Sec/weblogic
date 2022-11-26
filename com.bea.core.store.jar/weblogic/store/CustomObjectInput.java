package weblogic.store;

import java.io.IOException;
import java.io.ObjectInput;
import java.nio.ByteBuffer;

public interface CustomObjectInput {
   ObjectInput getObjectInput(ByteBuffer[] var1) throws IOException;
}
