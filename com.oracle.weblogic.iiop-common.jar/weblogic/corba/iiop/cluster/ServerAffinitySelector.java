package weblogic.corba.iiop.cluster;

class ServerAffinitySelector implements Selector {
   public static final ServerAffinitySelector SELECTOR = new ServerAffinitySelector();
   public static final String ALGORITHM = "-affinity";

   public int select(int current, int length) {
      return current;
   }
}
