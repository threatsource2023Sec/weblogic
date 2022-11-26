package org.apache.openjpa.lib.util;

import java.util.Locale;
import java.util.ResourceBundle;

interface ResourceBundleProvider {
   ResourceBundle findResource(String var1, Locale var2, ClassLoader var3);
}
