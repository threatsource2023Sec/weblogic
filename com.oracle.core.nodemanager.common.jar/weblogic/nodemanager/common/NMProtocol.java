package weblogic.nodemanager.common;

public enum NMProtocol {
   v2,
   v2_5,
   v2_6;

   public static NMProtocol getLatestVersion() {
      NMProtocol[] values = values();
      return values[values.length - 1];
   }
}
