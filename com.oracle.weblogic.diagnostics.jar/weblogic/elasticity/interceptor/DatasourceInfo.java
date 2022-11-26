package weblogic.elasticity.interceptor;

public class DatasourceInfo {
   private String name;
   private String url;
   private int maxCapacity;
   private String[] targets;

   public DatasourceInfo(String name, String url, int maxCapacity, String[] targets) {
      this.name = name;
      this.url = url;
      this.maxCapacity = maxCapacity;
      this.targets = targets;
   }

   public String getName() {
      return this.name;
   }

   public String getUrl() {
      return this.url;
   }

   public int getMaxCapacity() {
      return this.maxCapacity;
   }

   public String[] getTargets() {
      return this.targets;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.name).append("{").append(this.url).append(", ").append(this.maxCapacity).append(", ").append("[");
      if (this.targets != null) {
         String[] var2 = this.targets;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            buf.append(s).append(" ");
         }
      }

      buf.append("]}");
      return buf.toString();
   }
}
