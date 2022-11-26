package weblogic.management.rest.lib.bean.utils;

import java.beans.FeatureDescriptor;

public class VersionVisibility {
   private VersionVisibility parent;
   private FeatureDescriptor fd;
   private int deprecatedVersionNumber;

   private int deprecatedVersionNumber() {
      return this.deprecatedVersionNumber;
   }

   private VersionVisibility parent() {
      return this.parent;
   }

   public static VersionVisibility getVersionVisibility(FeatureDescriptor fd) {
      return getVersionVisibility((VersionVisibility)null, fd);
   }

   public static VersionVisibility getVersionVisibility(VersionVisibility parent, FeatureDescriptor fd) {
      int deprecated = DescriptorUtils.getDeprecatedVersionNumber(fd);
      return deprecated == -1 ? parent : new VersionVisibility(parent, fd, deprecated);
   }

   private VersionVisibility(VersionVisibility parent, FeatureDescriptor fd, int deprecatedVersionNumber) {
      this.parent = parent;
      this.fd = fd;
      this.deprecatedVersionNumber = deprecatedVersionNumber;
   }

   public boolean isVisible(int versionNumber) {
      if (this.parent() != null && !this.parent().isVisible(versionNumber)) {
         return false;
      } else {
         return !this.notVisibleBecauseOfDeprecated(versionNumber);
      }
   }

   private boolean notVisibleBecauseOfDeprecated(int versionNumber) {
      if (this.deprecatedVersionNumber() == -1) {
         return false;
      } else {
         return versionNumber >= this.deprecatedVersionNumber();
      }
   }
}
