package com.bea.core.repackaged.springframework.jca.cci;

import com.bea.core.repackaged.springframework.dao.DataAccessResourceFailureException;
import javax.resource.ResourceException;

public class CannotGetCciConnectionException extends DataAccessResourceFailureException {
   public CannotGetCciConnectionException(String msg, ResourceException ex) {
      super(msg, ex);
   }
}
