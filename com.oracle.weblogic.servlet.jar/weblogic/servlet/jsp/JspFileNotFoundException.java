package weblogic.servlet.jsp;

import java.io.FileNotFoundException;

public final class JspFileNotFoundException extends FileNotFoundException {
   public JspFileNotFoundException(String message) {
      super(message);
   }
}
