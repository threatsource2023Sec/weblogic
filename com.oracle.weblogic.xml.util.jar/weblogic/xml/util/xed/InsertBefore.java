package weblogic.xml.util.xed;

public class InsertBefore extends InsertCommand {
   public String getName() {
      return "insert-before";
   }

   public boolean isInsertBefore() {
      return true;
   }
}
