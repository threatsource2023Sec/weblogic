package org.glassfish.grizzly.filterchain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FilterChainBuilder {
   protected final List patternFilterChain;

   private FilterChainBuilder() {
      this.patternFilterChain = new ArrayList();
   }

   public static FilterChainBuilder stateless() {
      return new StatelessFilterChainBuilder();
   }

   public static FilterChainBuilder stateful() {
      throw new UnsupportedOperationException("Not implemented yet");
   }

   public abstract FilterChain build();

   public FilterChainBuilder add(Filter filter) {
      return this.addLast(filter);
   }

   public FilterChainBuilder addFirst(Filter filter) {
      this.patternFilterChain.add(0, filter);
      return this;
   }

   public FilterChainBuilder addLast(Filter filter) {
      this.patternFilterChain.add(filter);
      return this;
   }

   public FilterChainBuilder add(int index, Filter filter) {
      this.patternFilterChain.add(index, filter);
      return this;
   }

   public FilterChainBuilder set(int index, Filter filter) {
      this.patternFilterChain.set(index, filter);
      return this;
   }

   public Filter get(int index) {
      return (Filter)this.patternFilterChain.get(index);
   }

   public FilterChainBuilder remove(int index) {
      this.patternFilterChain.remove(index);
      return this;
   }

   public FilterChainBuilder remove(Filter filter) {
      this.patternFilterChain.remove(filter);
      return this;
   }

   public FilterChainBuilder addAll(Filter[] array) {
      this.patternFilterChain.addAll(this.patternFilterChain.size(), Arrays.asList(array));
      return this;
   }

   public FilterChainBuilder addAll(int filterIndex, Filter[] array) {
      this.patternFilterChain.addAll(filterIndex, Arrays.asList(array));
      return this;
   }

   public FilterChainBuilder addAll(List list) {
      return this.addAll(this.patternFilterChain.size(), list);
   }

   public FilterChainBuilder addAll(int filterIndex, List list) {
      this.patternFilterChain.addAll(filterIndex, list);
      return this;
   }

   public FilterChainBuilder addAll(FilterChainBuilder source) {
      this.patternFilterChain.addAll(source.patternFilterChain);
      return this;
   }

   public int indexOf(Filter filter) {
      return this.patternFilterChain.indexOf(filter);
   }

   public int indexOfType(Class filterType) {
      int size = this.patternFilterChain.size();

      for(int i = 0; i < size; ++i) {
         Filter filter = this.get(i);
         if (filterType.isAssignableFrom(filter.getClass())) {
            return i;
         }
      }

      return -1;
   }

   // $FF: synthetic method
   FilterChainBuilder(Object x0) {
      this();
   }

   public static class StatelessFilterChainBuilder extends FilterChainBuilder {
      public StatelessFilterChainBuilder() {
         super(null);
      }

      public FilterChain build() {
         FilterChain fc = new DefaultFilterChain();
         fc.addAll(this.patternFilterChain);
         return fc;
      }
   }
}
