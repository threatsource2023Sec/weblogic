package com.bea.common.security.internal.utils.database;

public interface NamedSQLConnection {
   String getName();

   ASIDBPoolConnection getASIConnection();
}
