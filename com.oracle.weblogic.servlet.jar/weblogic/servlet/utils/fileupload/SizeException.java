package weblogic.servlet.utils.fileupload;

import java.io.IOException;

public class SizeException extends IOException {
   public SizeException(String message) {
      super(message);
   }
}
