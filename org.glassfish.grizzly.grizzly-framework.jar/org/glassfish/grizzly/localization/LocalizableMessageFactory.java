package org.glassfish.grizzly.localization;

public class LocalizableMessageFactory {
   private final String _bundlename;

   public LocalizableMessageFactory(String bundlename) {
      this._bundlename = bundlename;
   }

   public Localizable getMessage(String key, Object... args) {
      return new LocalizableMessage(this._bundlename, key, args);
   }
}
