package weblogic.common;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public interface WLObjectOutput extends ObjectOutput {
   void writeObjectWL(Object var1) throws IOException;

   void writeString(String var1) throws IOException;

   void writeDate(Date var1) throws IOException;

   void writeArrayList(ArrayList var1) throws IOException;

   void writeProperties(Properties var1) throws IOException;

   void writeBytes(byte[] var1) throws IOException;

   void writeArrayOfObjects(Object[] var1) throws IOException;

   /** @deprecated */
   @Deprecated
   void writeAbbrevString(String var1) throws IOException;

   void writeImmutable(Object var1) throws IOException;
}
