package weblogic.management.jmx.modelmbean;

public class WLSModelMBeanInstanceContext {
   private int arrayIndex = -1;

   public WLSModelMBeanInstanceContext(int arrayIndex) {
      this.arrayIndex = arrayIndex;
   }

   public int getArrayIndex() {
      return this.arrayIndex;
   }
}
