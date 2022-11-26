package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class CodecException extends NestedRuntimeException {
   public CodecException(String msg) {
      super(msg);
   }

   public CodecException(String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
