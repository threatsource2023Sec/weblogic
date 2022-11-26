package jnr.ffi.provider.jffi;

import com.kenai.jffi.Function;
import jnr.ffi.CallingConvention;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;

public interface MethodGenerator {
   boolean isSupported(ResultType var1, ParameterType[] var2, CallingConvention var3);

   void generate(AsmBuilder var1, String var2, Function var3, ResultType var4, ParameterType[] var5, boolean var6);
}
