package weblogic.xml.util.xed;

public class Delete extends Command {
   private boolean hit = false;

   public String getName() {
      return "delete";
   }

   public Object evaluate(Context context) throws StreamEditorException {
      this.hit = true;
      return null;
   }

   public boolean wasHit() {
      if (this.hit) {
         this.hit = false;
         return true;
      } else {
         return false;
      }
   }

   public boolean isDelete() {
      return true;
   }
}
