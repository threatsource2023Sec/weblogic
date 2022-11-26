package weblogic.diagnostics.debug;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Set;
import java.util.StringTokenizer;

public final class DebugScopeTree implements Serializable {
   static final long serialVersionUID = -912275493627677911L;
   public static final String ROOT_NODE_NAME = "";
   public static final String DEFAULT_NODE_NAME = "default";
   private static final String DELIMITER = ".";
   private static boolean verbose = false;
   private DebugScopeNode rootNode = null;
   private DebugScopeNode defaultNode = null;

   static void setVerbose(boolean verboseEnabled) {
      verbose = verboseEnabled;
   }

   public static DebugScopeTree initializeFromPersistence() throws IOException, ClassNotFoundException {
      InputStream is = DebugScopeNode.class.getResourceAsStream("DebugScopeTree.ser");
      return initializeFromPersistence(is);
   }

   public static DebugScopeTree initializeFromPersistence(InputStream is) throws IOException, ClassNotFoundException {
      ObjectInputStream ois = null;

      DebugScopeTree var2;
      try {
         ois = new ObjectInputStream(is);
         var2 = (DebugScopeTree)ois.readObject();
      } finally {
         if (ois != null) {
            ois.close();
         }

      }

      return var2;
   }

   DebugScopeTree() {
      this.rootNode = new DebugScopeNode("");
      this.defaultNode = new DebugScopeNode("default");
      this.rootNode.getChildNodesMap().put("default", this.defaultNode);
   }

   public DebugScopeNode getRootNode() {
      return this.rootNode;
   }

   public DebugScopeNode getDefaultNode() {
      return this.defaultNode;
   }

   public DebugScopeNode findDebugScopeNode(String fqNodeName) throws InvalidDebugScopeException {
      DebugScopeNode currentNode = this.rootNode;

      String token;
      for(StringTokenizer tokenizer = new StringTokenizer(fqNodeName, "."); tokenizer.hasMoreTokens(); currentNode = currentNode.getChildNode(token)) {
         token = tokenizer.nextToken();
         if (!currentNode.isChild(token)) {
            throw new InvalidDebugScopeException(fqNodeName);
         }
      }

      return currentNode;
   }

   void addDebugAttributeToDebugScopeTree(String scopeName, String attribute, Set processedAttributes) throws InvalidDebugScopeException {
      if (scopeName.equals("default")) {
         if (!processedAttributes.contains(attribute)) {
            if (verbose) {
               System.out.println("Adding attribute " + attribute + " to default scope");
            }

            this.defaultNode.getDebugAttributes().add(attribute);
         } else {
            if (verbose) {
               System.out.println("Ignoring attribute " + attribute + " as it is already processed");
            }

         }
      } else {
         if (this.defaultNode.getDebugAttributes().contains(attribute)) {
            if (verbose) {
               System.out.println("Removing attribute " + attribute + " from default scope");
            }

            this.defaultNode.getDebugAttributes().remove(attribute);
         }

         if (verbose) {
            System.out.println("Adding scope " + scopeName + " to root");
         }

         this.addDebugAttributeToDebugScopeNode(this.rootNode, scopeName, attribute);
         processedAttributes.add(attribute);
      }
   }

   private void addDebugAttributeToDebugScopeNode(DebugScopeNode parent, String childScopeName, String attribute) throws InvalidDebugScopeException {
      if (childScopeName != null && childScopeName.trim().length() != 0) {
         if (verbose) {
            System.out.println("Adding attribute " + attribute + " to parent scope " + parent.getNodeName() + " and child scope " + childScopeName);
         }

         int delimiterIndex = childScopeName.indexOf(".");
         if (delimiterIndex == -1) {
            DebugScopeNode node = (DebugScopeNode)parent.getChildNodesMap().get(childScopeName);
            if (node == null) {
               if (verbose) {
                  System.out.println("Creating a node for scope named " + childScopeName + " as a child of " + parent.getNodeName());
               }

               node = new DebugScopeNode(childScopeName);
               parent.getChildNodesMap().put(childScopeName, node);
            }

            node.getDebugAttributes().add(attribute);
         } else {
            String newParentNodeName = childScopeName.substring(0, delimiterIndex);
            String newChildScopeName = childScopeName.substring(delimiterIndex + 1);
            DebugScopeNode newParent = (DebugScopeNode)parent.getChildNodesMap().get(newParentNodeName);
            if (verbose) {
               System.out.println("Creating a node for scope named " + newParentNodeName + " as a child of " + parent.getNodeName());
            }

            if (newParent == null) {
               newParent = new DebugScopeNode(newParentNodeName);
               parent.getChildNodesMap().put(newParentNodeName, newParent);
            }

            this.addDebugAttributeToDebugScopeNode(newParent, newChildScopeName, attribute);
         }

      } else {
         throw new InvalidDebugScopeException("DebugScope name cannot be an empty string for parent node = " + parent.getNodeName() + " and attribute " + attribute);
      }
   }

   public Set getAllChildDebugAttributes(String fqNodeName) throws InvalidDebugScopeException {
      DebugScopeNode node = this.findDebugScopeNode(fqNodeName);
      return node.getAllChildDebugAttributes();
   }
}
