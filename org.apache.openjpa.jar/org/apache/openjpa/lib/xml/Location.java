package org.apache.openjpa.lib.xml;

import java.text.MessageFormat;
import org.apache.openjpa.lib.util.Localizer;
import org.xml.sax.Locator;
import serp.util.Numbers;

public class Location {
   private static final Localizer _loc = Localizer.forPackage(Location.class);
   private boolean _nullOnNoLocator;
   private Locator _locator;

   public Location() {
      this(false);
   }

   public Location(boolean nullOnNoLocator) {
      this._nullOnNoLocator = false;
      this._locator = null;
      this._nullOnNoLocator = nullOnNoLocator;
   }

   public String getLocation(String format) {
      if (this._locator == null) {
         return this._nullOnNoLocator ? null : _loc.get("no-locator").getMessage();
      } else {
         return MessageFormat.format(format, Numbers.valueOf(this._locator.getLineNumber()), Numbers.valueOf(this._locator.getColumnNumber()), this._locator.getPublicId(), this._locator.getSystemId());
      }
   }

   public String getLocation() {
      return this.getLocation(_loc.get("location-format").getMessage());
   }

   public void setLocator(Locator locator) {
      this._locator = locator;
   }

   public Locator getLocator() {
      return this._locator;
   }

   public void setNullOnNoLocator(boolean val) {
      this._nullOnNoLocator = val;
   }

   public boolean isNullOnNoLocator() {
      return this._nullOnNoLocator;
   }
}
