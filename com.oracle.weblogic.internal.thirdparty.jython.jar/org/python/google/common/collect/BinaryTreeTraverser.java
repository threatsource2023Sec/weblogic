package org.python.google.common.collect;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import java.util.Iterator;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Optional;
import org.python.google.common.base.Preconditions;

@Beta
@GwtCompatible
public abstract class BinaryTreeTraverser extends TreeTraverser {
   public abstract Optional leftChild(Object var1);

   public abstract Optional rightChild(Object var1);

   public final Iterable children(final Object root) {
      Preconditions.checkNotNull(root);
      return new FluentIterable() {
         public Iterator iterator() {
            return new AbstractIterator() {
               boolean doneLeft;
               boolean doneRight;

               protected Object computeNext() {
                  Optional right;
                  if (!this.doneLeft) {
                     this.doneLeft = true;
                     right = BinaryTreeTraverser.this.leftChild(root);
                     if (right.isPresent()) {
                        return right.get();
                     }
                  }

                  if (!this.doneRight) {
                     this.doneRight = true;
                     right = BinaryTreeTraverser.this.rightChild(root);
                     if (right.isPresent()) {
                        return right.get();
                     }
                  }

                  return this.endOfData();
               }
            };
         }
      };
   }

   UnmodifiableIterator preOrderIterator(Object root) {
      return new PreOrderIterator(root);
   }

   UnmodifiableIterator postOrderIterator(Object root) {
      return new PostOrderIterator(root);
   }

   public final FluentIterable inOrderTraversal(final Object root) {
      Preconditions.checkNotNull(root);
      return new FluentIterable() {
         public UnmodifiableIterator iterator() {
            return BinaryTreeTraverser.this.new InOrderIterator(root);
         }
      };
   }

   private static void pushIfPresent(Deque stack, Optional node) {
      if (node.isPresent()) {
         stack.addLast(node.get());
      }

   }

   private final class InOrderIterator extends AbstractIterator {
      private final Deque stack = new ArrayDeque(8);
      private final BitSet hasExpandedLeft = new BitSet();

      InOrderIterator(Object root) {
         this.stack.addLast(root);
      }

      protected Object computeNext() {
         while(!this.stack.isEmpty()) {
            Object node = this.stack.getLast();
            if (this.hasExpandedLeft.get(this.stack.size() - 1)) {
               this.stack.removeLast();
               this.hasExpandedLeft.clear(this.stack.size());
               BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.rightChild(node));
               return node;
            }

            this.hasExpandedLeft.set(this.stack.size() - 1);
            BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.leftChild(node));
         }

         return this.endOfData();
      }
   }

   private final class PostOrderIterator extends UnmodifiableIterator {
      private final Deque stack = new ArrayDeque(8);
      private final BitSet hasExpanded;

      PostOrderIterator(Object root) {
         this.stack.addLast(root);
         this.hasExpanded = new BitSet();
      }

      public boolean hasNext() {
         return !this.stack.isEmpty();
      }

      public Object next() {
         while(true) {
            Object node = this.stack.getLast();
            boolean expandedNode = this.hasExpanded.get(this.stack.size() - 1);
            if (expandedNode) {
               this.stack.removeLast();
               this.hasExpanded.clear(this.stack.size());
               return node;
            }

            this.hasExpanded.set(this.stack.size() - 1);
            BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.rightChild(node));
            BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.leftChild(node));
         }
      }
   }

   private final class PreOrderIterator extends UnmodifiableIterator implements PeekingIterator {
      private final Deque stack = new ArrayDeque(8);

      PreOrderIterator(Object root) {
         this.stack.addLast(root);
      }

      public boolean hasNext() {
         return !this.stack.isEmpty();
      }

      public Object next() {
         Object result = this.stack.removeLast();
         BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.rightChild(result));
         BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.leftChild(result));
         return result;
      }

      public Object peek() {
         return this.stack.getLast();
      }
   }
}
