package weblogic.management.provider.internal.federatedconfig;

import java.util.List;

public interface TreeNode {
   TreeNode parent();

   Object value();

   TreeNode addChild(Object var1);

   List getChildren();

   void breadthFirstTraversal(BreadthFirstVisitor var1);

   public interface BreadthFirstVisitor {
      void nextLevel();

      void nextNodeWithinLevel(TreeNode var1);

      void done();
   }
}
