package javax.transaction;

public class HeuristicMixedException extends Exception {
   private static final long serialVersionUID = 2345014349685956666L;

   public HeuristicMixedException() {
   }

   public HeuristicMixedException(String msg) {
      super(msg);
   }
}
