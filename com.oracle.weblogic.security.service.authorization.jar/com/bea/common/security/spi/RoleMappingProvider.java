package com.bea.common.security.spi;

import weblogic.security.spi.RoleMapper;

public interface RoleMappingProvider {
   RoleMapper getRoleMapper();
}
