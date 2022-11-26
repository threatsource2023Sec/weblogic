package org.glassfish.grizzly.localization;

public interface Localizable {
   String NOT_LOCALIZABLE = "\u0000";

   String getKey();

   Object[] getArguments();

   String getResourceBundleName();
}
