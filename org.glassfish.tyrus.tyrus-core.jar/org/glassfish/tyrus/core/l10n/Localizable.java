package org.glassfish.tyrus.core.l10n;

public interface Localizable {
   String NOT_LOCALIZABLE = "\u0000";

   String getKey();

   Object[] getArguments();

   String getResourceBundleName();
}
