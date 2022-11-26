package weblogic.entitlement.util;

import java.io.Serializable;

public class Version implements Serializable {
   private String who;
   private int major;
   private int minor;
   private String build;
   private String rcsId;

   public Version() {
      this.who = this.getClass().getName();
      this.build = "";
      this.rcsId = "$Id: Version.java,v 1.3 2001/11/19 14:20:28 nboegholm Exp $";
   }

   public Version(String who, int major, int minor, String build, String rcsId) {
      this.who = who;
      this.major = major;
      this.minor = minor;
      this.build = build;
      this.rcsId = rcsId;
   }

   public String getVersionString() {
      int index = this.who.lastIndexOf(".");
      String s = index < 0 ? this.who : this.who.substring(index);
      return s + ": Version " + this.major + "." + this.minor + " - build " + this.build;
   }

   public String getInfoString() {
      return "\nModule: " + this.who + "\nVersion: " + this.major + "." + this.minor + "\nBuild: " + this.build + "\nId: " + this.rcsId;
   }

   public int getMajorNumber() {
      return this.major;
   }

   public int getMinorNumber() {
      return this.minor;
   }

   public String getBuildString() {
      return this.build;
   }

   public String toString() {
      return this.getInfoString();
   }
}
