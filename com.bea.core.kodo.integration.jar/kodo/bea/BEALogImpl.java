package kodo.bea;

import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import weblogic.i18n.logging.LogMessage;
import weblogic.i18n.logging.MessageDispatcher;
import weblogic.i18n.logging.MessageLogger;

public class BEALogImpl implements Log {
   private final String channel;
   private final MessageLogger ml;
   private final MessageDispatcher md;
   private final String subsystem;
   private final String bundleBaseName;
   private final BEALogFactory factory;
   private TraceEnabledHook traceEnabledHook;

   protected BEALogImpl(String channel, MessageLogger ml, MessageDispatcher md, String subsystem, String bundleBaseName, BEALogFactory factory) {
      this.channel = channel;
      this.ml = ml;
      this.md = md;
      this.subsystem = subsystem;
      this.bundleBaseName = bundleBaseName;
      this.factory = factory;
   }

   public String getChannel() {
      return this.channel;
   }

   public String getSubsystem() {
      return this.channel;
   }

   public BEALogFactory getFactory() {
      return this.factory;
   }

   public String getBundleBaseName() {
      return this.bundleBaseName;
   }

   public void setTraceEnabledHook(TraceEnabledHook val) {
      this.traceEnabledHook = val;
   }

   public TraceEnabledHook getTraceEnabledHook() {
      return this.traceEnabledHook;
   }

   protected LogMessage toLogMessage(int severity, Object o, Throwable t) {
      LogMessage lm = null;
      if (o instanceof Localizer.Message && BEALogFactory.toMessageId((Localizer.Message)o) != null) {
         lm = KodoCatalogMessage.newInstance((Localizer.Message)o, t, severity, this);
      }

      if (lm == null) {
         lm = new LogMessage("2004002", "BEA", this.subsystem, severity, o == null ? null : o.toString(), t);
      }

      return (LogMessage)lm;
   }

   public void error(Object o) {
      this.md.log(this.toLogMessage(8, o, (Throwable)null));
   }

   public void error(Object o, Throwable t) {
      this.md.log(this.toLogMessage(8, o, t));
   }

   public void fatal(Object o) {
      this.md.log(this.toLogMessage(1, o, (Throwable)null));
   }

   public void fatal(Object o, Throwable t) {
      this.md.log(this.toLogMessage(1, o, t));
   }

   public void info(Object o) {
      this.md.log(this.toLogMessage(64, o, (Throwable)null));
   }

   public void info(Object o, Throwable t) {
      this.md.log(this.toLogMessage(64, o, t));
   }

   public void trace(Object o) {
      this.md.log(this.toLogMessage(256, o, (Throwable)null));
   }

   public void trace(Object o, Throwable t) {
      this.md.log(this.toLogMessage(256, o, t));
   }

   public void warn(Object o) {
      this.md.log(this.toLogMessage(16, o, (Throwable)null));
   }

   public void warn(Object o, Throwable t) {
      this.md.log(this.toLogMessage(16, o, t));
   }

   public boolean isErrorEnabled() {
      return this.ml.isSeverityEnabled(this.subsystem, 8);
   }

   public boolean isFatalEnabled() {
      return this.ml.isSeverityEnabled(this.subsystem, 1);
   }

   public boolean isInfoEnabled() {
      return this.ml.isSeverityEnabled(this.subsystem, 64);
   }

   public boolean isTraceEnabled() {
      return this.traceEnabledHook != null ? this.traceEnabledHook.isTraceEnabled() : this.ml.isSeverityEnabled(this.subsystem, 256);
   }

   public boolean isWarnEnabled() {
      return this.ml.isSeverityEnabled(this.subsystem, 16);
   }

   public interface TraceEnabledHook {
      boolean isTraceEnabled();
   }
}
