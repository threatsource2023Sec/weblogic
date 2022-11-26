package weblogic.entitlement.engine.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.security.spi.Resource;

public class ResourceTreeMap {
   private Map treeMap = new HashMap();

   public void insertKey(Resource resource, Key k) {
      synchronized(this.treeMap) {
         this.getNode(resource).getKeys().add(k);
      }
   }

   private TreeNode getNode(Resource resource) {
      String res = resource != null ? resource.toString() : null;
      TreeNode tn = (TreeNode)this.treeMap.get(res);
      if (tn == null) {
         if (resource != null) {
            TreeNode parent = this.getNode(resource.getParentResource());
            tn = new TreeNode(parent, res);
            parent.getChildren().add(tn);
         } else {
            tn = new TreeNode((TreeNode)null, res);
         }

         this.treeMap.put(res, tn);
      }

      return tn;
   }

   public void removeKey(Key k) {
      if (k != null) {
         Resource res = k.getResource();
         synchronized(this.treeMap) {
            TreeNode tn = (TreeNode)this.treeMap.get(res != null ? res.toString() : null);
            if (tn != null && tn.hasKeys()) {
               tn.getKeys().remove(k);
            }
         }
      }

   }

   public Set resourceChanged(String resource) {
      Set oustedKeys = new HashSet();
      synchronized(this.treeMap) {
         TreeNode tn = (TreeNode)this.treeMap.remove(resource);
         if (tn != null) {
            TreeNode parent = tn.getParent();
            if (parent != null) {
               parent.getChildren().remove(tn);
            }

            this.removeNode(tn, oustedKeys);
         }

         return oustedKeys;
      }
   }

   private void removeNode(TreeNode tn, Set oustedKeys) {
      if (tn.hasKeys()) {
         oustedKeys.addAll(tn.getKeys());
      }

      if (tn.hasChildren()) {
         Iterator it = tn.getChildren().iterator();

         while(it.hasNext()) {
            TreeNode child = (TreeNode)it.next();
            this.treeMap.remove(child.getResource());
            this.removeNode(child, oustedKeys);
         }
      }

   }

   private static class TreeNode {
      private String resource;
      private TreeNode parent;
      private Collection children;
      private Set keys;

      public TreeNode(TreeNode parent, String resource) {
         this.parent = parent;
         this.resource = resource;
      }

      public String getResource() {
         return this.resource;
      }

      public TreeNode getParent() {
         return this.parent;
      }

      public boolean hasChildren() {
         return this.children != null && !this.children.isEmpty();
      }

      public Collection getChildren() {
         if (this.children == null) {
            this.children = new ArrayList();
         }

         return this.children;
      }

      public boolean hasKeys() {
         return this.keys != null && !this.keys.isEmpty();
      }

      public Set getKeys() {
         if (this.keys == null) {
            this.keys = new HashSet();
         }

         return this.keys;
      }
   }
}
