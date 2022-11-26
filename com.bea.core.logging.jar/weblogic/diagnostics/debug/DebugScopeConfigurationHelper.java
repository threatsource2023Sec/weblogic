package weblogic.diagnostics.debug;

import java.util.Iterator;

public final class DebugScopeConfigurationHelper {
   private static final boolean DEBUG = false;

   static void configureDebugScope(DebugScopeTree debugScopeTree, String scopeName, boolean enabled) throws InvalidDebugScopeException {
      DebugScopeNode node = debugScopeTree.findDebugScopeNode(scopeName);
      node.setEnabled(enabled);
   }

   static void configureDebugLoggers(DebugScopeNode root, DebugLoggerRepository repository) {
      if (root.isModified()) {
         boolean enabled = root.isEnabled();
         Iterator attrs = root.getAllChildDebugAttributes().iterator();

         while(attrs.hasNext()) {
            String attr = (String)attrs.next();
            repository.getDebugLogger(attr).setDebugEnabled(enabled);
         }
      }

      Iterator children = root.getChildDebugScopeNodes().iterator();

      while(children.hasNext()) {
         DebugScopeNode child = (DebugScopeNode)children.next();
         configureDebugLoggers(child, repository);
      }

   }
}
