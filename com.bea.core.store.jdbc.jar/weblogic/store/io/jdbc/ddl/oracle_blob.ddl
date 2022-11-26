
# WebLogic JDBC Store DDL for Oracle
# Copyright (c) 2003 by BEA, Inc., All Rights Reserved
#
# Important:  For notes on using this file, refer to the documentation
# for the "JDBCStore.CreateTableDDLFile" attribute.

CREATE TABLE $TABLE (
  id     int  not null primary key,
  type   int  not null,
  handle int  not null,
  record blob not null
);

