package weblogic.cluster.replication;

public final class ROInfo {
   public static final int INITIAL_VERSION_NUMBER = 0;
   public static final int RO_NOT_FOUND = -1;
   private final ROID roid;
   private Object secondaryROInfo;
   private int secondaryROVersion = 0;

   ROInfo(ROID roid) {
      this.roid = roid;
   }

   public final Object getSecondaryROInfo() {
      return this.secondaryROInfo;
   }

   public final void setSecondaryROInfo(Object object) {
      this.secondaryROInfo = object;
   }

   public final ROID getROID() {
      return this.roid;
   }

   public int getSecondaryROVersion() {
      return this.secondaryROVersion;
   }

   public void setSecondaryROVersion(int v) {
      this.secondaryROVersion = v;
   }
}
