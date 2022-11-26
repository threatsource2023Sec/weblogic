package com.bea.common.security.spi;

import weblogic.security.spi.BulkRoleMapper;
import weblogic.security.spi.RoleMapper;

public interface BulkRoleMappingProvider {
   RoleMapper getRoleMapper();

   BulkRoleMapper getBulkRoleMapper();
}
