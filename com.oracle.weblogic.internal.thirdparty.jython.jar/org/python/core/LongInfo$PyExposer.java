package org.python.core;

import org.python.expose.BaseTypeBuilder;

public class LongInfo$PyExposer extends BaseTypeBuilder {
   public LongInfo$PyExposer() {
      PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
      PyDataDescr[] var2 = new PyDataDescr[]{new LongInfo$sizeof_digit_descriptor(), new LongInfo$bits_per_digit_descriptor()};
      super("sys.long_info", LongInfo.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
   }
}
