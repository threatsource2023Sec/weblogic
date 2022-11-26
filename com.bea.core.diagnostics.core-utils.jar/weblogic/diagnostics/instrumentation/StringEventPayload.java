package weblogic.diagnostics.instrumentation;

public class StringEventPayload implements EventPayload {
   static final long serialVersionUID = -665469141712332428L;
   protected String payload;

   public StringEventPayload(String payload) {
      this.payload = payload;
   }

   public String getPayload() {
      return this.payload;
   }

   public String toString() {
      return this.payload;
   }
}
