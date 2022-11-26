package org.python.core;

import org.python.expose.BaseTypeBuilder;

public class WinVersion$PyExposer extends BaseTypeBuilder {
   public WinVersion$PyExposer() {
      PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
      PyDataDescr[] var2 = new PyDataDescr[]{new WinVersion$major_descriptor(), new WinVersion$minor_descriptor(), new WinVersion$build_descriptor(), new WinVersion$service_pack_descriptor(), new WinVersion$platform_descriptor()};
      super("sys.getwindowsversion", WinVersion.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
   }
}
