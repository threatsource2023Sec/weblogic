package weblogic.security.principal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.security.spi.WLSGroup;

public final class WLSGroupImpl extends WLSAbstractPrincipal implements WLSGroup {
   private static final long serialVersionUID = -8923536011547762759L;
   private static boolean intern;

   public WLSGroupImpl(String groupName) {
      this.setName(groupName);
   }

   public WLSGroupImpl(String groupName, boolean createSalt) {
      super(createSalt);
      this.setName(groupName);
   }

   public boolean equals(Object another) {
      return another instanceof WLSGroupImpl && super.equals(another);
   }

   protected void setName(String name) {
      if (intern && name != null) {
         super.setName(name.intern());
      } else {
         super.setName(name);
      }

   }

   protected void setGuid(String guid) {
      if (intern && guid != null) {
         super.setGuid(guid.intern());
      } else {
         super.setGuid(guid);
      }

   }

   protected void setDn(String dn) {
      if (intern && dn != null) {
         super.setDn(dn.intern());
      } else {
         super.setDn(dn);
      }

   }

   static {
      try {
         GetInternProperty getUseIntern = new GetInternProperty();
         intern = (Boolean)AccessController.doPrivileged(getUseIntern);
      } catch (SecurityException var1) {
         intern = true;
      }

   }

   private static class GetInternProperty implements PrivilegedAction {
      private GetInternProperty() {
      }

      public Object run() {
         String useIntern = System.getProperty("weblogic.security.UseStringInternForGroups", "true");
         return Boolean.valueOf(useIntern);
      }

      // $FF: synthetic method
      GetInternProperty(Object x0) {
         this();
      }
   }
}
