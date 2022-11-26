# WebLogic JDBC Store DDL for EDB
# Copyright (c) 2003 by BEA, Inc., All Rights Reserved

CREATE TABLE $TABLE (
  id     int      not null primary key,
  type   int      not null,
  handle int      not null,
  record blob not null
);

