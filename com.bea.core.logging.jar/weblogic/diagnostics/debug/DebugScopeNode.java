package weblogic.diagnostics.debug;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DebugScopeNode implements Serializable {
   private static final long serialVersionUID = -5729139200595948682L;
   private Map childNodes = new HashMap();
   private Set attributes = new HashSet();
   private String nodeName = null;
   private boolean modified = false;
   private boolean enabled = false;

   DebugScopeNode(String name) {
      this.nodeName = name;
   }

   public String getNodeName() {
      return this.nodeName;
   }

   public String toString() {
      return this.nodeName;
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof DebugScopeNode) {
         DebugScopeNode that = (DebugScopeNode)o;
         return this.nodeName.equals(that.nodeName);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.nodeName.hashCode();
   }

   public Map getChildNodesMap() {
      return this.childNodes;
   }

   public Set getChildDebugScopeNodes() {
      Set result = new HashSet(this.childNodes.values());
      return result;
   }

   public Set getDebugAttributes() {
      return this.attributes;
   }

   public boolean isChild(String scopeName) {
      return this.childNodes.containsKey(scopeName);
   }

   public DebugScopeNode getChildNode(String scopeName) {
      return (DebugScopeNode)this.childNodes.get(scopeName);
   }

   public boolean isModified() {
      return this.modified;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean value) {
      this.enabled = value;
      this.modified = true;
   }

   public Set getAllChildDebugAttributes() {
      HashSet attributes = new HashSet();
      getAllChildDebugAttributes(this, attributes);
      return attributes;
   }

   private static void getAllChildDebugAttributes(DebugScopeNode root, Set resultSet) {
      resultSet.addAll(root.getDebugAttributes());
      if (!root.getChildNodesMap().isEmpty()) {
         Iterator iter = root.getChildNodesMap().values().iterator();

         while(iter.hasNext()) {
            getAllChildDebugAttributes((DebugScopeNode)iter.next(), resultSet);
         }

      }
   }
}
