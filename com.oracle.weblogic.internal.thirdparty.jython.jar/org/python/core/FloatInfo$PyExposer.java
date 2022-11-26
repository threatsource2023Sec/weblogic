package org.python.core;

import org.python.expose.BaseTypeBuilder;

public class FloatInfo$PyExposer extends BaseTypeBuilder {
   public FloatInfo$PyExposer() {
      PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
      PyDataDescr[] var2 = new PyDataDescr[]{new FloatInfo$epsilon_descriptor(), new FloatInfo$radix_descriptor(), new FloatInfo$min_descriptor(), new FloatInfo$dig_descriptor(), new FloatInfo$max_descriptor(), new FloatInfo$max_10_exp_descriptor(), new FloatInfo$max_exp_descriptor(), new FloatInfo$min_10_exp_descriptor(), new FloatInfo$mant_dig_descriptor(), new FloatInfo$min_exp_descriptor(), new FloatInfo$rounds_descriptor()};
      super("sys.float_info", FloatInfo.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
   }
}
