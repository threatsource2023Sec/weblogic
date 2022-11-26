package weblogic.diagnostics.debug;

import com.bea.logging.LoggingService;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class SimpleDebugProvider implements DebugProvider, DebugConfig {
   private static final boolean DEBUG = false;
   private static SimpleDebugProvider SINGLETON;
   private String name = SimpleDebugProvider.class.getName();
   private Map debugConfig = new HashMap();
   private PropertyChangeSupport propChangeSupport = new PropertyChangeSupport(this);

   public static SimpleDebugProvider getInstance() {
      if (SINGLETON == null) {
         SINGLETON = new SimpleDebugProvider();
      }

      return SINGLETON;
   }

   private SimpleDebugProvider() {
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getCommandLineOverridePrefix() {
      try {
         return System.getProperty("weblogic.diagnostics.debug.DefaultCommandLinePrefix", "weblogic.debug.");
      } catch (Exception var2) {
         return "weblogic.debug.";
      }
   }

   public void intializeDebugScopes() throws DebugScopeInitializationException {
   }

   public DebugScopeTree getDebugScopeTree() throws DebugScopeInitializationException {
      return null;
   }

   public Object getDebugConfiguration() throws DebugBeanConfigurationException {
      return this.debugConfig;
   }

   public Map getDebugProperties() {
      return this.debugConfig;
   }

   public void setDebugProperties(Map newValue) {
      Map oldValue = this.debugConfig;
      this.debugConfig = newValue;
      this.propChangeSupport.firePropertyChange("DebugProperties", oldValue, newValue);
   }

   public Logger getLogger() {
      return LoggingService.getInstance().getDebugDelegateLogger();
   }

   public void addPropertyChangeListener(PropertyChangeListener listener) {
      this.propChangeSupport.addPropertyChangeListener(listener);
   }

   public void removePropertyChangeListener(PropertyChangeListener listener) {
      this.propChangeSupport.removePropertyChangeListener(listener);
   }
}
