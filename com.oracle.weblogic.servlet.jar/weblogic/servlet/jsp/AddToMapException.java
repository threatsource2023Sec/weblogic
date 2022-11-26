package weblogic.servlet.jsp;

import javax.servlet.ServletException;
import weblogic.servlet.internal.ServletStubImpl;

public final class AddToMapException extends ServletException {
   private static final long serialVersionUID = 345782007899492834L;
   public ServletStubImpl sstub;
   public String pattern;

   public String toString() {
      return "[AddToMap: pattern=" + this.pattern + " class=" + this.sstub.getClassName() + ']';
   }

   public AddToMapException(String pattern, ServletStubImpl sstub) {
      this.pattern = pattern;
      this.sstub = sstub;
   }
}
