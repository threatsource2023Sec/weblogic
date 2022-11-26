package weblogic.xml.stream.events;

/** @deprecated */
@Deprecated
public class NullEvent extends ElementEvent {
   public NullEvent() {
      this.init();
   }

   protected void init() {
      this.type = 128;
   }

   public String toString() {
      return "<?Null?>";
   }
}
