package com.bea.core.repackaged.springframework.jca.cci.core;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;

public interface CciOperations {
   @Nullable
   Object execute(ConnectionCallback var1) throws DataAccessException;

   @Nullable
   Object execute(InteractionCallback var1) throws DataAccessException;

   @Nullable
   Record execute(InteractionSpec var1, Record var2) throws DataAccessException;

   void execute(InteractionSpec var1, Record var2, Record var3) throws DataAccessException;

   Record execute(InteractionSpec var1, RecordCreator var2) throws DataAccessException;

   @Nullable
   Object execute(InteractionSpec var1, Record var2, RecordExtractor var3) throws DataAccessException;

   @Nullable
   Object execute(InteractionSpec var1, RecordCreator var2, RecordExtractor var3) throws DataAccessException;
}
