package com.bea.core.repackaged.springframework.jmx.export.naming;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public interface SelfNaming {
   ObjectName getObjectName() throws MalformedObjectNameException;
}
