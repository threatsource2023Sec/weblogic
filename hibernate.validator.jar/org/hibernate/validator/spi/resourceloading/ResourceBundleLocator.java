package org.hibernate.validator.spi.resourceloading;

import java.util.Locale;
import java.util.ResourceBundle;

public interface ResourceBundleLocator {
   ResourceBundle getResourceBundle(Locale var1);
}
