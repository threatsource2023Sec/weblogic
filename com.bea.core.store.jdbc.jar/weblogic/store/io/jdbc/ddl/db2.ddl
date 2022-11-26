
# WebLogic JDBC Store DDL for DB2
# Copyright (c) 2003 by BEA, Inc., All Rights Reserved

CREATE TABLE $TABLE (
  id     int       not null primary key,
  type   int       not null,
  handle int       not null,
  record blob(10M) not null
);

