package org.python.google.common.util.concurrent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Optional;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableCollection;
import org.python.google.common.collect.ImmutableList;
import org.python.google.common.collect.Lists;

@GwtCompatible(
   emulated = true
)
abstract class CollectionFuture extends AggregateFuture {
   static final class ListFuture extends CollectionFuture {
      ListFuture(ImmutableCollection futures, boolean allMustSucceed) {
         this.init(new ListFutureRunningState(futures, allMustSucceed));
      }

      private final class ListFutureRunningState extends CollectionFutureRunningState {
         ListFutureRunningState(ImmutableCollection futures, boolean allMustSucceed) {
            super(futures, allMustSucceed);
         }

         public List combine(List values) {
            List result = Lists.newArrayListWithCapacity(values.size());
            Iterator var3 = values.iterator();

            while(var3.hasNext()) {
               Optional element = (Optional)var3.next();
               result.add(element != null ? element.orNull() : null);
            }

            return Collections.unmodifiableList(result);
         }
      }
   }

   abstract class CollectionFutureRunningState extends AggregateFuture.RunningState {
      private List values;

      CollectionFutureRunningState(ImmutableCollection futures, boolean allMustSucceed) {
         super(futures, allMustSucceed, true);
         this.values = (List)(futures.isEmpty() ? ImmutableList.of() : Lists.newArrayListWithCapacity(futures.size()));

         for(int i = 0; i < futures.size(); ++i) {
            this.values.add((Object)null);
         }

      }

      final void collectOneValue(boolean allMustSucceed, int index, @Nullable Object returnValue) {
         List localValues = this.values;
         if (localValues != null) {
            localValues.set(index, Optional.fromNullable(returnValue));
         } else {
            Preconditions.checkState(allMustSucceed || CollectionFuture.this.isCancelled(), "Future was done before all dependencies completed");
         }

      }

      final void handleAllCompleted() {
         List localValues = this.values;
         if (localValues != null) {
            CollectionFuture.this.set(this.combine(localValues));
         } else {
            Preconditions.checkState(CollectionFuture.this.isDone());
         }

      }

      void releaseResourcesAfterFailure() {
         super.releaseResourcesAfterFailure();
         this.values = null;
      }

      abstract Object combine(List var1);
   }
}
