package com.bea.core.repackaged.springframework.jca.cci.core;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import javax.resource.ResourceException;
import javax.resource.cci.Record;
import javax.resource.cci.RecordFactory;

@FunctionalInterface
public interface RecordCreator {
   Record createRecord(RecordFactory var1) throws ResourceException, DataAccessException;
}
