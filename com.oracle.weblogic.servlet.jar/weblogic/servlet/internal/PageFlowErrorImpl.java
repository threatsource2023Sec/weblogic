package weblogic.servlet.internal;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import weblogic.management.runtime.PageFlowError;

public final class PageFlowErrorImpl implements PageFlowError, Serializable {
   private static final long serialVersionUID = 1L;
   private long _timestamp;
   private String _stackTrace;
   private String _message;

   public PageFlowErrorImpl(Throwable t) {
      assert t != null;

      this._timestamp = System.currentTimeMillis();
      this._stackTrace = stringify(t);
      this._message = t.getLocalizedMessage();
      if (this._message == null) {
         this._message = t.getMessage();
      }

   }

   public long getTimeStamp() {
      return this._timestamp;
   }

   public String getStackTraceAsString() {
      return this._stackTrace;
   }

   public String getMessage() {
      return this._message;
   }

   private static String stringify(Throwable t) {
      StringWriter sw = new StringWriter();
      t.printStackTrace(new PrintWriter(sw));
      return sw.toString();
   }
}
