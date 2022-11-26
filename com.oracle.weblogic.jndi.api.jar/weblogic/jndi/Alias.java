package weblogic.jndi;

import java.io.Serializable;

public class Alias implements Serializable {
   private String realName;

   public Alias(String realName) {
      this.realName = realName;
   }

   public String getRealName() {
      return this.realName;
   }
}
