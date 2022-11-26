package weblogic.transaction.internal;

import java.io.DataInput;
import java.io.IOException;
import java.util.Map;

public interface LogDataInput extends DataInput {
   int readNonNegativeInt() throws IOException;

   String readString() throws IOException;

   String readAbbrevString() throws IOException;

   byte[] readByteArray() throws IOException;

   Map readProperties() throws IOException;
}
