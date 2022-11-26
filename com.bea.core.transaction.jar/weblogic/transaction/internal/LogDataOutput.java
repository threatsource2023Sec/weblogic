package weblogic.transaction.internal;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

public interface LogDataOutput extends DataOutput {
   void writeNonNegativeInt(int var1) throws IOException;

   void writeString(String var1) throws IOException;

   void writeAbbrevString(String var1) throws IOException;

   void writeByteArray(byte[] var1) throws IOException;

   void writeProperties(Map var1) throws IOException;
}
