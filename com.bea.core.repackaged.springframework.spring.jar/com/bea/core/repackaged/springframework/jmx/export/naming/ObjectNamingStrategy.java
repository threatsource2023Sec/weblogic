package com.bea.core.repackaged.springframework.jmx.export.naming;

import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

@FunctionalInterface
public interface ObjectNamingStrategy {
   ObjectName getObjectName(Object var1, @Nullable String var2) throws MalformedObjectNameException;
}
