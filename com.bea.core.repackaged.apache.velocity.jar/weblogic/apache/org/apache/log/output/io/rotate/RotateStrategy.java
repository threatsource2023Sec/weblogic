package weblogic.apache.org.apache.log.output.io.rotate;

import java.io.File;

public interface RotateStrategy {
   void reset();

   boolean isRotationNeeded(String var1, File var2);
}
