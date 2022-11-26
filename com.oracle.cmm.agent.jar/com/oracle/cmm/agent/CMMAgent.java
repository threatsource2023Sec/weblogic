package com.oracle.cmm.agent;

import javax.management.MBeanServer;

public interface CMMAgent {
   void addCMMMBeanToServer(MBeanServer var1) throws IllegalArgumentException;

   void removeCMMMBeanFromServer(MBeanServer var1) throws IllegalArgumentException;
}
