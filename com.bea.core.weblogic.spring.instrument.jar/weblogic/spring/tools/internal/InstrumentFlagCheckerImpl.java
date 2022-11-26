package weblogic.spring.tools.internal;

import java.io.File;

public class InstrumentFlagCheckerImpl implements InstrumentFlagChecker {
   public boolean check(String dir) {
      File instrumented = new File(dir + "/META-INF/INSTRUMENTED");
      return instrumented.exists();
   }
}
