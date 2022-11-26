package com.bea.adaptive.harvester.jmx;

import com.bea.adaptive.harvester.Harvester;
import com.bea.adaptive.mbean.typing.MBeanCategorizer;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Executor;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.PropertyHelper;

public class MBeanHarvesterManager implements MBeanHarvesterLauncher, Serializable {
   static final long serialVersionUID = 1L;
   private static HarvesterJMXTextTextFormatter mtf_base = HarvesterJMXTextTextFormatter.getInstance();
   private String name;
   private boolean stdoutDbg;
   private transient DebugLogger logDbg;
   protected boolean dbg;
   private String debugName;
   static String DBG_LABEL;

   public MBeanHarvesterManager() {
      this.name = mtf_base.getHarvesterManagerLabel();
      this.debugName = "LocJMXHarvesterManager";
      this.setUpDebugFlags();
   }

   public Harvester allocateHarvester(String potentialName, MBeanServerConnection mbeanServerConn, MBeanCategorizer categorizer, Throwable[] pollingException) throws IOException, JMException {
      return this.allocateHarvester(potentialName, (String)null, mbeanServerConn, categorizer, (String[])null, (Executor)null, pollingException);
   }

   public Harvester allocateHarvester(String potentialName, String namespace, MBeanServerConnection mbeanServerConn, MBeanCategorizer categorizer, String[] priorityMBeanPatterns, Executor executor, Throwable[] pollingException) throws IOException, JMException {
      if (this.dbg) {
         String s = "Allocating new local Harvester...";
         s = s + "  name = " + potentialName;
         s = s + "  mbeanServerConn  = " + mbeanServerConn;
         s = s + "  categorizer  = " + categorizer;
         s = s + "  polling  = " + (pollingException != null);
         this.dbg(s);
      }

      return MBeanHarvesterImpl.getMBeanHarvesterImpl(potentialName, namespace, mbeanServerConn, categorizer, priorityMBeanPatterns, executor);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void prepareHarvesterManager() {
      HarvesterJMXLogger.logServicePrepared(this.getName());
   }

   public void activateHarvesterManager() {
      HarvesterJMXLogger.logServiceActivated(this.getName());
   }

   public void deactivateHarvesterManager(boolean permanent) {
      HarvesterJMXLogger.logServiceDeactivated(this.getName());
   }

   private void setUpDebugFlags() {
      String dbgProp = "com.bea.core.debug." + this.debugName;
      this.stdoutDbg = PropertyHelper.getProperty(dbgProp + ".stdout", "false").equals("true");
      this.logDbg = DebugLogger.getDebugLogger(this.debugName);
      this.dbg = this.stdoutDbg || this.logDbg != null && this.logDbg.isDebugEnabled();
      if (this.dbg) {
         String s = "The following debug flags are set\n";
         s = s + "  stdout: " + this.stdoutDbg + "\n";
         s = s + "  log: " + (this.logDbg != null && this.logDbg.isDebugEnabled()) + "\n";
         this.dbg(s);
      }

   }

   public void dbg(Object o) {
      if (this.dbg) {
         this.logDbg.debug(this.msgLabel() + o);
      }
   }

   private String msgLabel() {
      return "[" + mtf_base.getHarvesterManagerDebugMessageLabel() + ": " + this.getName() + " - " + Thread.currentThread().getId() + "] ";
   }

   static {
      DBG_LABEL = mtf_base.getDebugMessageLabel();
   }
}
