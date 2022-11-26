package weblogic.xml.xpath;

public final class XPathUnsupportedException extends XPathException {
   public XPathUnsupportedException(String msg) {
      super(msg);
   }

   public XPathUnsupportedException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
