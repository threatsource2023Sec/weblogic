package weblogic.apache.org.apache.log.output;

public class NullOutputLogTarget extends AbstractOutputTarget {
   public NullOutputLogTarget() {
      this.open();
   }

   protected void write(String data) {
   }
}
