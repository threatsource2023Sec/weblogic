package com.bea.core.repackaged.springframework.jca.cci;

import com.bea.core.repackaged.springframework.dao.InvalidDataAccessResourceUsageException;
import javax.resource.ResourceException;

public class RecordTypeNotSupportedException extends InvalidDataAccessResourceUsageException {
   public RecordTypeNotSupportedException(String msg, ResourceException ex) {
      super(msg, ex);
   }
}
