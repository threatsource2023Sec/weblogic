package weblogic.corba.iiop.cluster;

public class RandomSelector implements Selector {
   public static final RandomSelector SELECTOR = new RandomSelector();
   public static final String ALGORITHM = "random";

   public int select(int current, int length) {
      double dIdx = Math.random() * (double)length + 0.5;
      return (int)Math.round(dIdx) - 1;
   }
}
