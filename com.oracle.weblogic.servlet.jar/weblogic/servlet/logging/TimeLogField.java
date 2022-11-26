package weblogic.servlet.logging;

public final class TimeLogField implements LogField {
   private int type;

   TimeLogField(String id) {
      if ("time".equals(id)) {
         this.type = 1;
      } else if ("time-taken".equals(id)) {
         this.type = 2;
      } else if ("date".equals(id)) {
         this.type = 3;
      } else if ("bytes".equals(id)) {
         this.type = 4;
      } else {
         this.type = 0;
      }

   }

   public void logField(HttpAccountingInfo metrics, FormatStringBuffer buff) {
      switch (this.type) {
         case 0:
            buff.appendValueOrDash((String)null);
            return;
         case 1:
            buff.appendTime();
            return;
         case 2:
            long milsec = System.currentTimeMillis() - metrics.getInvokeTime();
            Float sec = new Float((double)milsec / 1000.0);
            buff.append(sec.toString());
            return;
         case 3:
            buff.appendDate();
            return;
         case 4:
            long contentLength = metrics.getResponseContentLengthLong();
            if (contentLength > 2147483647L) {
               buff.append(contentLength);
            } else {
               buff.append((int)contentLength);
            }

            return;
         default:
      }
   }
}
