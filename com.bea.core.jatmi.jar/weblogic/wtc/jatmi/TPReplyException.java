package weblogic.wtc.jatmi;

public final class TPReplyException extends TPException {
   private static final long serialVersionUID = -7645324238210497753L;

   public TPReplyException(int err, Reply aRplyRtn) throws TPException {
      super(err, 0, 0, 0, (Reply)aRplyRtn);
      if (err != 10 && err != 11) {
         throw new TPException(4);
      }
   }

   public TPReplyException(int err, int uerr, int urcode, int detail, Reply aRplyRtn) {
      super(err, uerr, urcode, detail, aRplyRtn);
   }

   public TPReplyException(int err, int uerr, int urcode, int detail, int diagnostic, int revent, Reply aRplyRtn) {
      super(err, uerr, urcode, detail, diagnostic, revent, aRplyRtn);
   }

   public Reply getExceptionReply() {
      return this.getReplyRtn();
   }
}
