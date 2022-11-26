
# WebLogic JDBC Store DDL for Oracle
# Copyright (c) 2003,2013, Oracle and/or its affiliates. All rights reserved.

CREATE TABLE $TABLE (
  id     int  not null primary key,
  type   int  not null,
  handle int  not null,
  record blob not null
) 
lob (record) store as securefile(
  ENABLE STORAGE IN ROW NOCACHE);

