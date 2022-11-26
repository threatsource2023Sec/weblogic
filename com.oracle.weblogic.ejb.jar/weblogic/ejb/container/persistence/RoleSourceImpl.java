package weblogic.ejb.container.persistence;

import java.util.Arrays;
import weblogic.ejb.container.persistence.spi.RoleSource;

public final class RoleSourceImpl implements RoleSource {
   protected String[] description;
   protected String ejbName;

   public void setDescriptions(String[] d) {
      this.description = d;
   }

   public String[] getDescriptions() {
      return this.description;
   }

   public void setEjbName(String ejbName) {
      this.ejbName = ejbName;
   }

   public String getEjbName() {
      return this.ejbName;
   }

   public String toString() {
      return "[RoleSourceImpl ejbName: " + this.ejbName + " description: " + Arrays.toString(this.description) + "]";
   }
}
