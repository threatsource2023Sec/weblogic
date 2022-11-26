package org.python.apache.xerces.util;

import javax.xml.stream.Location;
import org.python.apache.xerces.xni.XMLLocator;

public final class StAXLocationWrapper implements XMLLocator {
   private Location fLocation = null;

   public void setLocation(Location var1) {
      this.fLocation = var1;
   }

   public Location getLocation() {
      return this.fLocation;
   }

   public String getPublicId() {
      return this.fLocation != null ? this.fLocation.getPublicId() : null;
   }

   public String getLiteralSystemId() {
      return this.fLocation != null ? this.fLocation.getSystemId() : null;
   }

   public String getBaseSystemId() {
      return null;
   }

   public String getExpandedSystemId() {
      return this.getLiteralSystemId();
   }

   public int getLineNumber() {
      return this.fLocation != null ? this.fLocation.getLineNumber() : -1;
   }

   public int getColumnNumber() {
      return this.fLocation != null ? this.fLocation.getColumnNumber() : -1;
   }

   public int getCharacterOffset() {
      return this.fLocation != null ? this.fLocation.getCharacterOffset() : -1;
   }

   public String getEncoding() {
      return null;
   }

   public String getXMLVersion() {
      return null;
   }
}
