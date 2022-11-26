package weblogic.corba.iiop.cluster;

public class RoundRobinSelector implements Selector {
   public static final RoundRobinSelector SELECTOR = new RoundRobinSelector();
   public static final String ALGORITHM = "round-robin";

   public int select(int current, int length) {
      return (current + 1) % length;
   }
}
