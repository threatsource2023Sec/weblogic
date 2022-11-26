package org.python.core;

import java.util.Properties;
import org.python.core.adapter.ExtensiblePyObjectAdapter;

public interface JythonInitializer {
   void initialize(Properties var1, Properties var2, String[] var3, ClassLoader var4, ExtensiblePyObjectAdapter var5);
}
