package weblogic.diagnostics.debug;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import weblogic.diagnostics.context.CorrelationHelper;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ServerDebugMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;

public class ServerDebugService extends KernelDebugService implements PropertyChangeListener, DebugScopeUtil {
   private static ServerDebugService singleton = null;
   private static final boolean DEBUG = false;
   private ServerDebugMBean serverDebug = null;
   private DebugScopeTree debugScopeTree = null;
   private boolean initialized = false;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static ServerDebugService getInstance() {
      if (singleton == null) {
         singleton = new ServerDebugService();
      }

      return singleton;
   }

   private ServerDebugService() {
      try {
         this.debugScopeTree = DebugScopeTree.initializeFromPersistence();
      } catch (RuntimeException var2) {
         throw var2;
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public synchronized String[] getChildDebugScopes(String parent) throws InvalidDebugScopeException {
      DebugScopeNode node = this.debugScopeTree.findDebugScopeNode(parent);
      Iterator iter = node.getChildDebugScopeNodes().iterator();
      ArrayList result = new ArrayList();

      while(iter.hasNext()) {
         String nodeName = ((DebugScopeNode)iter.next()).getNodeName();
         result.add(nodeName);
      }

      String[] values = new String[result.size()];
      result.toArray(values);
      return values;
   }

   public synchronized String[] getChildDebugAttributes(String parent) throws InvalidDebugScopeException {
      DebugScopeNode node = this.debugScopeTree.findDebugScopeNode(parent);
      Iterator iter = node.getDebugAttributes().iterator();
      ArrayList result = new ArrayList();

      while(iter.hasNext()) {
         String attrName = (String)iter.next();
         result.add(attrName);
      }

      String[] values = new String[result.size()];
      result.toArray(values);
      return values;
   }

   public void initializeServerDebug(Logger serverLogger) throws DebugProviderRegistrationException {
      if (!this.initialized) {
         this.serverDebug = ManagementService.getRuntimeAccess(kernelId).getServer().getServerDebug();
         ServerDebugProvider sdp = new ServerDebugProvider();
         DebugProviderRegistration.registerDebugProvider(sdp, DebugLogger.getDefaultDebugLoggerRepository());
         this.serverDebug.addPropertyChangeListener(this);
         this.initDebugContextMode();
         DebugLogger.setDebugContext(new DebugContextImpl());
         this.initializeDebugLogging(serverLogger);
         this.initializeDebugLoggersFromOldDebugCategoryCommandLineProperties();
         this.initialized = true;
      }
   }

   public void initializeDebugLogging(Logger logger) {
   }

   private void initDebugContextMode() {
      if (this.serverDebug.getDiagnosticContextDebugMode().equals("And")) {
         DebugLogger.setContextMode(1);
      } else if (this.serverDebug.getDiagnosticContextDebugMode().equals("Or")) {
         DebugLogger.setContextMode(2);
      } else {
         DebugLogger.setContextMode(0);
      }

      DebugLogger.setDebugMask(CorrelationHelper.parseDyeMask(this.serverDebug.getDebugMaskCriterias()));
   }

   public void propertyChange(PropertyChangeEvent evt) {
      String sourceName = ((WebLogicMBean)evt.getSource()).getName();
      String sourceType = ((WebLogicMBean)evt.getSource()).getType();
      this.attributeValueChanged(sourceName, sourceType, evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
   }

   private void attributeValueChanged(String sourceName, String sourceType, String attrName, Object oldValue, Object newValue) {
      if (attrName.equals("DiagnosticContextDebugMode")) {
         this.initDebugContextMode();
      } else if (attrName.equals("DebugMaskCriterias")) {
         this.initDebugContextMode();
      } else if (attrName.equals("DebugParameters")) {
         DebugLogger.getDefaultDebugLoggerRepository().setDebugLoggerParameters(this.serverDebug.getDebugParameters());
      }

   }

   private void initializeDebugLoggersFromOldDebugCategoryCommandLineProperties() {
      InputStream inStr = null;

      try {
         Properties props = new Properties();
         inStr = ServerDebugService.class.getResourceAsStream("OldDebugCategory.properties");
         if (inStr != null) {
            props.load(inStr);
            Set keys = props.keySet();
            Iterator i = keys.iterator();

            while(i.hasNext()) {
               String debugLoggerName = (String)i.next();
               String debugCategoryNames = props.getProperty(debugLoggerName);
               StringTokenizer tk = new StringTokenizer(debugCategoryNames, ",");

               while(tk.hasMoreTokens()) {
                  String debugCategoryName = tk.nextToken();
                  if (Debug.getCategory(debugCategoryName).isEnabled()) {
                     DebugLogger.getDebugLogger(debugLoggerName).setDebugEnabled(true);
                  }
               }
            }
         }
      } catch (Exception var17) {
         DebugModuleLogger.logErrorInitializingDebugCategories(var17);
      } finally {
         if (inStr != null) {
            try {
               inStr.close();
            } catch (IOException var16) {
            }
         }

      }

   }

   void testDebugScopeName(String debugScopeName) throws InvalidDebugScopeException {
      this.debugScopeTree.findDebugScopeNode(debugScopeName);
   }
}
