package org.apache.openjpa.slice.jdbc;

import java.util.concurrent.ExecutorService;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.slice.DistributedConfiguration;
import org.apache.openjpa.slice.Slice;

public interface DistributedJDBCConfiguration extends JDBCConfiguration, DistributedConfiguration {
   Slice getMaster();

   String getExecutorService();

   ExecutorService getExecutorServiceInstance();
}
