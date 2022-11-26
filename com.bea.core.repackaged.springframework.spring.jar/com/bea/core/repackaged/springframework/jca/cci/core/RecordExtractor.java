package com.bea.core.repackaged.springframework.jca.cci.core;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.sql.SQLException;
import javax.resource.ResourceException;
import javax.resource.cci.Record;

@FunctionalInterface
public interface RecordExtractor {
   @Nullable
   Object extractData(Record var1) throws ResourceException, SQLException, DataAccessException;
}
