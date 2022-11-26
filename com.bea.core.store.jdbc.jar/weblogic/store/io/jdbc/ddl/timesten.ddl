
# WebLogic JDBC Store DDL for TimesTen
# Copyright (c) 2003 by BEA, Inc., All Rights Reserved

CREATE TABLE $TABLE (
  id     int                not null primary key,
  type   int                not null,
  handle int                not null,
  record varbinary(1000000) not null
);

