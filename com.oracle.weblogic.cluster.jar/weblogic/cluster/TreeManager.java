package weblogic.cluster;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.utils.AssertionError;

public final class TreeManager {
   private static TreeManager theTreeManager = null;
   private static Object theLock = new Object();
   private Map nodes;
   private Context initialCtx;
   private ConflictHandler conHandler;
   private long ageThreshold;

   static TreeManager theOne() {
      return theTreeManager;
   }

   static void initialize(long ageThreshold) {
      if (theTreeManager == null) {
         synchronized(theLock) {
            if (theTreeManager == null) {
               theTreeManager = new TreeManager(ageThreshold);
            }
         }
      }

   }

   private TreeManager(long ageThreshold) {
      try {
         Hashtable env = new Hashtable();
         env.put("weblogic.jndi.replicateBindings", "false");
         env.put("weblogic.jndi.createIntermediateContexts", "true");
         this.initialCtx = new InitialContext(env);
      } catch (NamingException var4) {
         throw new AssertionError("Failed to create initial context", var4);
      }

      this.conHandler = new BasicConflictHandler();
      this.nodes = new HashMap();
      this.ageThreshold = ageThreshold;
   }

   void install(ServiceOffer offer, boolean isLocal) {
      NodeInfo node = this.find(offer.name());
      node.install(offer, isLocal);
      this.done(node);
   }

   void retract(ServiceOffer offer, boolean isLocal) {
      NodeInfo node = this.find(offer.name());
      node.retract(offer, isLocal);
      this.doneRemove(node, offer.name());
   }

   void update(ServiceOffer offer, boolean isLocal) {
      NodeInfo node = this.find(offer.name());
      node.update(offer, isLocal);
      this.done(node);
   }

   private synchronized NodeInfo find(String name) {
      NodeInfo node = (NodeInfo)this.nodes.get(name);
      if (node == null) {
         node = new NodeInfo(this.initialCtx, this.conHandler, name, this.ageThreshold);
         this.nodes.put(name, node);
         node.numUnprocessedRequests = 1;
      } else {
         ++node.numUnprocessedRequests;
      }

      return node;
   }

   private synchronized void done(NodeInfo node) {
      --node.numUnprocessedRequests;
   }

   private synchronized void doneRemove(NodeInfo node, String name) {
      --node.numUnprocessedRequests;
      if (node.numUnprocessedRequests == 0 && node.isEmpty()) {
         this.nodes.remove(name);
      }

   }
}
