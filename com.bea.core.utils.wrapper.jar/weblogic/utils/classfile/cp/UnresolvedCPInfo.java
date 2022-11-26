package weblogic.utils.classfile.cp;

public class UnresolvedCPInfo extends CPInfo {
   public int idx1 = -1;
   public int idx2 = -1;

   public UnresolvedCPInfo() {
   }

   public UnresolvedCPInfo(int tag, int idx1) {
      this.setTag(tag);
      this.idx1 = idx1;
   }

   public UnresolvedCPInfo(int tag, int idx1, int idx2) {
      this.setTag(tag);
      this.idx1 = idx1;
      this.idx2 = idx2;
   }

   public void init(Object o) {
   }
}
