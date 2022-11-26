package weblogic.jms.bridge;

public interface GenericMessage {
   void acknowledge();

   String getIdentifer();
}
