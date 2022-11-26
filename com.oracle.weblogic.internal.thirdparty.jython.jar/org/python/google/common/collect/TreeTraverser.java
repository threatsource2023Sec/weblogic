package org.python.google.common.collect;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;

@Beta
@GwtCompatible
public abstract class TreeTraverser {
   public static TreeTraverser using(final Function nodeToChildrenFunction) {
      Preconditions.checkNotNull(nodeToChildrenFunction);
      return new TreeTraverser() {
         public Iterable children(Object root) {
            return (Iterable)nodeToChildrenFunction.apply(root);
         }
      };
   }

   public abstract Iterable children(Object var1);

   public final FluentIterable preOrderTraversal(final Object root) {
      Preconditions.checkNotNull(root);
      return new FluentIterable() {
         public UnmodifiableIterator iterator() {
            return TreeTraverser.this.preOrderIterator(root);
         }
      };
   }

   UnmodifiableIterator preOrderIterator(Object root) {
      return new PreOrderIterator(root);
   }

   public final FluentIterable postOrderTraversal(final Object root) {
      Preconditions.checkNotNull(root);
      return new FluentIterable() {
         public UnmodifiableIterator iterator() {
            return TreeTraverser.this.postOrderIterator(root);
         }
      };
   }

   UnmodifiableIterator postOrderIterator(Object root) {
      return new PostOrderIterator(root);
   }

   public final FluentIterable breadthFirstTraversal(final Object root) {
      Preconditions.checkNotNull(root);
      return new FluentIterable() {
         public UnmodifiableIterator iterator() {
            return TreeTraverser.this.new BreadthFirstIterator(root);
         }
      };
   }

   private final class BreadthFirstIterator extends UnmodifiableIterator implements PeekingIterator {
      private final Queue queue = new ArrayDeque();

      BreadthFirstIterator(Object root) {
         this.queue.add(root);
      }

      public boolean hasNext() {
         return !this.queue.isEmpty();
      }

      public Object peek() {
         return this.queue.element();
      }

      public Object next() {
         Object result = this.queue.remove();
         Iterables.addAll(this.queue, TreeTraverser.this.children(result));
         return result;
      }
   }

   private final class PostOrderIterator extends AbstractIterator {
      private final ArrayDeque stack = new ArrayDeque();

      PostOrderIterator(Object root) {
         this.stack.addLast(this.expand(root));
      }

      protected Object computeNext() {
         while(true) {
            if (!this.stack.isEmpty()) {
               PostOrderNode top = (PostOrderNode)this.stack.getLast();
               if (top.childIterator.hasNext()) {
                  Object child = top.childIterator.next();
                  this.stack.addLast(this.expand(child));
                  continue;
               }

               this.stack.removeLast();
               return top.root;
            }

            return this.endOfData();
         }
      }

      private PostOrderNode expand(Object t) {
         return new PostOrderNode(t, TreeTraverser.this.children(t).iterator());
      }
   }

   private static final class PostOrderNode {
      final Object root;
      final Iterator childIterator;

      PostOrderNode(Object root, Iterator childIterator) {
         this.root = Preconditions.checkNotNull(root);
         this.childIterator = (Iterator)Preconditions.checkNotNull(childIterator);
      }
   }

   private final class PreOrderIterator extends UnmodifiableIterator {
      private final Deque stack = new ArrayDeque();

      PreOrderIterator(Object root) {
         this.stack.addLast(Iterators.singletonIterator(Preconditions.checkNotNull(root)));
      }

      public boolean hasNext() {
         return !this.stack.isEmpty();
      }

      public Object next() {
         Iterator itr = (Iterator)this.stack.getLast();
         Object result = Preconditions.checkNotNull(itr.next());
         if (!itr.hasNext()) {
            this.stack.removeLast();
         }

         Iterator childItr = TreeTraverser.this.children(result).iterator();
         if (childItr.hasNext()) {
            this.stack.addLast(childItr);
         }

         return result;
      }
   }
}
