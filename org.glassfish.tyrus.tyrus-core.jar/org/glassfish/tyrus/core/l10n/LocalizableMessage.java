package org.glassfish.tyrus.core.l10n;

public final class LocalizableMessage implements Localizable {
   private final String _bundlename;
   private final String _key;
   private final Object[] _args;

   public LocalizableMessage(String bundlename, String key, Object... args) {
      this._bundlename = bundlename;
      this._key = key;
      if (args == null) {
         args = new Object[0];
      }

      this._args = args;
   }

   public String getKey() {
      return this._key;
   }

   public Object[] getArguments() {
      Object[] copy = new Object[this._args.length];
      System.arraycopy(this._args, 0, copy, 0, this._args.length);
      return copy;
   }

   public String getResourceBundleName() {
      return this._bundlename;
   }
}
