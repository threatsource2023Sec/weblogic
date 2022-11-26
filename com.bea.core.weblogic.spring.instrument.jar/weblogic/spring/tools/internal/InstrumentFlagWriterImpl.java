package weblogic.spring.tools.internal;

import java.io.FileOutputStream;
import java.io.IOException;
import weblogic.spring.tools.SpringInstrumentException;

public class InstrumentFlagWriterImpl implements InstrumentFlagWriter {
   public void instrumented(String dir) throws SpringInstrumentException {
      try {
         String file = dir + "/META-INF/INSTRUMENTED";
         FileOutputStream fos = new FileOutputStream(file, false);
         fos.flush();
         fos.close();
      } catch (IOException var4) {
         throw new SpringInstrumentException(var4.getMessage(), var4);
      }
   }
}
