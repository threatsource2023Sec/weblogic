package weblogic.corba.iiop.cluster;

class PrimarySecondarySelector implements Selector {
   public static final PrimarySecondarySelector SELECTOR = new PrimarySecondarySelector();
   public static final String ALGORITHM = "primary-secondary";

   public int select(int current, int length) {
      return current % length;
   }
}
