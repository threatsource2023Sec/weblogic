package weblogic.admin.plugin;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class NMComponentTypeChangeList {
   private String[] componentNames;
   private final ChangeList componentTypeChanges;

   public NMComponentTypeChangeList(String[] componentNames, ChangeList componentTypeChanges) {
      assert componentNames != null;

      this.componentNames = componentNames;

      assert componentTypeChanges != null;

      this.componentTypeChanges = componentTypeChanges;
   }

   public String[] getComponentNames() {
      return this.componentNames;
   }

   public void addComponentName(String componentName) {
      int len = this.componentNames.length;
      this.componentNames = (String[])Arrays.copyOf(this.componentNames, len + 1);
      this.componentNames[len] = componentName;
   }

   public ChangeList getComponentTypeChanges() {
      return this.componentTypeChanges;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("NMComponentTypeChangeList:");
      String[] var2 = this.componentNames;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String name = var2[var4];
         sb.append(" ").append(name);
      }

      sb.append(";");
      Map changes = this.componentTypeChanges.getChanges();
      if (changes != null && changes.size() != 0) {
         Iterator var7 = changes.values().iterator();

         while(var7.hasNext()) {
            ChangeList.Change c = (ChangeList.Change)var7.next();
            sb.append(" ").append(c.getType()).append("|").append(c.getComponentName()).append("|").append(c.getRelativePath()).append("|").append(c.getVersion()).append("|").append(c.getLastModifiedTime());
         }
      } else {
         sb.append(" no change");
      }

      return sb.toString();
   }
}
