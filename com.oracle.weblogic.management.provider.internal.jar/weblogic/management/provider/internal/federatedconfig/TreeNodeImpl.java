package weblogic.management.provider.internal.federatedconfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeNodeImpl implements TreeNode {
   private final TreeNode parent;
   private Object value;
   private final List children = new ArrayList();

   public TreeNodeImpl(TreeNode parent, Object value) {
      this.parent = parent;
      this.value = value;
   }

   public TreeNode parent() {
      return this.parent;
   }

   public TreeNode addChild(Object value) {
      TreeNodeImpl result = new TreeNodeImpl(this, value);
      this.children.add(result);
      return result;
   }

   public Object value() {
      return this.value;
   }

   public List getChildren() {
      return this.children;
   }

   public void breadthFirstTraversal(TreeNode.BreadthFirstVisitor visitor) {
      List nodesInCurrentLevel = new ArrayList();
      nodesInCurrentLevel.add(this);

      while(!nodesInCurrentLevel.isEmpty()) {
         List nodesInNextLevel = new ArrayList();
         visitor.nextLevel();
         Iterator var4 = nodesInCurrentLevel.iterator();

         while(var4.hasNext()) {
            TreeNode node = (TreeNode)var4.next();
            visitor.nextNodeWithinLevel(node);
            nodesInNextLevel.addAll(node.getChildren());
         }

         nodesInCurrentLevel = nodesInNextLevel;
      }

      visitor.done();
   }
}
