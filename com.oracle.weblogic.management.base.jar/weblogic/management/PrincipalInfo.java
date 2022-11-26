package weblogic.management;

import java.io.Serializable;

public final class PrincipalInfo implements Serializable {
   private static final long serialVersionUID = 7266902478036561415L;
   private String name;
   private boolean is_group;

   public PrincipalInfo(String name, boolean is_group) {
      this.name = name;
      this.is_group = is_group;
   }

   public String getName() {
      return this.name;
   }

   public boolean isGroup() {
      return this.is_group;
   }
}
