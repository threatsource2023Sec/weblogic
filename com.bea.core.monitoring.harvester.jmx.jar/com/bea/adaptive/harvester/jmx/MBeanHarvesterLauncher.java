package com.bea.adaptive.harvester.jmx;

import com.bea.adaptive.harvester.Harvester;
import com.bea.adaptive.mbean.typing.MBeanCategorizer;
import java.io.IOException;
import java.util.concurrent.Executor;
import javax.management.JMException;
import javax.management.MBeanServerConnection;

public interface MBeanHarvesterLauncher {
   Harvester allocateHarvester(String var1, MBeanServerConnection var2, MBeanCategorizer var3, Throwable[] var4) throws IOException, JMException;

   Harvester allocateHarvester(String var1, String var2, MBeanServerConnection var3, MBeanCategorizer var4, String[] var5, Executor var6, Throwable[] var7) throws IOException, JMException;
}
