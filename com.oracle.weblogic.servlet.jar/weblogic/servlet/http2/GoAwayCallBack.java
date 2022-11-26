package weblogic.servlet.http2;

public class GoAwayCallBack {
   private HTTP2Connection http2;

   public GoAwayCallBack(HTTP2Connection http2) {
      this.http2 = http2;
   }

   public void finished() {
      this.http2.tryTeminateConnection();
   }
}
