package weblogic.jms.forwarder;

public interface RuntimeHandler {
   void disconnected(Exception var1);

   void connected();
}
