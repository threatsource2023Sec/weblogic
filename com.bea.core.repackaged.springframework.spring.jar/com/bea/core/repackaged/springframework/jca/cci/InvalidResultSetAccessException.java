package com.bea.core.repackaged.springframework.jca.cci;

import com.bea.core.repackaged.springframework.dao.InvalidDataAccessResourceUsageException;
import java.sql.SQLException;

public class InvalidResultSetAccessException extends InvalidDataAccessResourceUsageException {
   public InvalidResultSetAccessException(String msg, SQLException ex) {
      super(ex.getMessage(), ex);
   }
}
