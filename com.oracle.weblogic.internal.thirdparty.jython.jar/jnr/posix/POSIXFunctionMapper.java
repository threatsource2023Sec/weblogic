package jnr.posix;

import jnr.ffi.mapper.FunctionMapper;

/** @deprecated */
@Deprecated
final class POSIXFunctionMapper implements FunctionMapper {
   public static final FunctionMapper INSTANCE = new POSIXFunctionMapper();

   private POSIXFunctionMapper() {
   }

   public String mapFunctionName(String name, FunctionMapper.Context ctx) {
      if (ctx.getLibrary().getName().equals("msvcrt") && (name.equals("getpid") || name.equals("chmod"))) {
         name = "_" + name;
      }

      return name;
   }
}
