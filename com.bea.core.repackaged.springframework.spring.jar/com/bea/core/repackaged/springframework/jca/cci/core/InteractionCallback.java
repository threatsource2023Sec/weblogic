package com.bea.core.repackaged.springframework.jca.cci.core;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.sql.SQLException;
import javax.resource.ResourceException;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;

@FunctionalInterface
public interface InteractionCallback {
   @Nullable
   Object doInInteraction(Interaction var1, ConnectionFactory var2) throws ResourceException, SQLException, DataAccessException;
}
