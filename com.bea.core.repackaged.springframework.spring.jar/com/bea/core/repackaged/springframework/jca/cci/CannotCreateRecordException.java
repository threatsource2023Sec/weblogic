package com.bea.core.repackaged.springframework.jca.cci;

import com.bea.core.repackaged.springframework.dao.DataAccessResourceFailureException;
import javax.resource.ResourceException;

public class CannotCreateRecordException extends DataAccessResourceFailureException {
   public CannotCreateRecordException(String msg, ResourceException ex) {
      super(msg, ex);
   }
}
