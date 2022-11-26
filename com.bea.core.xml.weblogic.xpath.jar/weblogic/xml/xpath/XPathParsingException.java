package weblogic.xml.xpath;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;

public final class XPathParsingException extends XPathException {
   private static final boolean DEBUG = true;

   public XPathParsingException() {
   }

   public XPathParsingException(String msg) {
      super(msg);
   }

   public XPathParsingException(Throwable nested) {
      super(nested);
   }

   public XPathParsingException(String msg, Throwable nested) {
      super(msg, nested);
   }

   static XPathParsingException create(Collection errs) {
      StringWriter msg = new StringWriter();
      Iterator i = errs.iterator();
      Object o = i.next();
      if (o instanceof Throwable) {
         ((Throwable)o).printStackTrace();
         o = ((Throwable)o).getMessage();
      }

      msg.write("error: ");
      msg.write(o.toString());
      msg.write("\n");
      return new XPathParsingException(msg.toString());
   }
}
