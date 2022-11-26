package com.bea.core.repackaged.springframework.jca.cci.core;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.sql.SQLException;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;

@FunctionalInterface
public interface ConnectionCallback {
   @Nullable
   Object doInConnection(Connection var1, ConnectionFactory var2) throws ResourceException, SQLException, DataAccessException;
}
