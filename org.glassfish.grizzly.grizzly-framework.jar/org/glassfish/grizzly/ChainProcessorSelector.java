package org.glassfish.grizzly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ChainProcessorSelector implements ProcessorSelector, List {
   private final List selectorChain;

   public ChainProcessorSelector() {
      this((List)(new ArrayList()));
   }

   public ChainProcessorSelector(ProcessorSelector... selectorChain) {
      this((List)(new ArrayList(Arrays.asList(selectorChain))));
   }

   public ChainProcessorSelector(List selectorChain) {
      this.selectorChain = selectorChain;
   }

   public Processor select(IOEvent ioEvent, Connection connection) {
      Iterator var3 = this.selectorChain.iterator();

      Processor processor;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         ProcessorSelector processorSelector = (ProcessorSelector)var3.next();
         processor = processorSelector.select(ioEvent, connection);
      } while(processor == null);

      return processor;
   }

   public int size() {
      return this.selectorChain.size();
   }

   public boolean isEmpty() {
      return this.selectorChain.isEmpty();
   }

   public boolean contains(Object o) {
      return this.selectorChain.contains(o);
   }

   public Iterator iterator() {
      return this.selectorChain.iterator();
   }

   public Object[] toArray() {
      return this.selectorChain.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.selectorChain.toArray(a);
   }

   public boolean add(ProcessorSelector o) {
      return this.selectorChain.add(o);
   }

   public boolean remove(Object o) {
      return this.selectorChain.remove(o);
   }

   public boolean containsAll(Collection c) {
      return this.selectorChain.containsAll(c);
   }

   public boolean addAll(Collection c) {
      return this.selectorChain.addAll(c);
   }

   public boolean addAll(int index, Collection c) {
      return this.selectorChain.addAll(index, c);
   }

   public boolean removeAll(Collection c) {
      return this.selectorChain.removeAll(c);
   }

   public boolean retainAll(Collection c) {
      return this.selectorChain.retainAll(c);
   }

   public void clear() {
      this.selectorChain.clear();
   }

   public ProcessorSelector get(int index) {
      return (ProcessorSelector)this.selectorChain.get(index);
   }

   public ProcessorSelector set(int index, ProcessorSelector element) {
      return (ProcessorSelector)this.selectorChain.set(index, element);
   }

   public void add(int index, ProcessorSelector element) {
      this.selectorChain.add(index, element);
   }

   public ProcessorSelector remove(int index) {
      return (ProcessorSelector)this.selectorChain.remove(index);
   }

   public int indexOf(Object o) {
      return this.selectorChain.indexOf(o);
   }

   public int lastIndexOf(Object o) {
      return this.selectorChain.lastIndexOf(o);
   }

   public ListIterator listIterator() {
      return this.selectorChain.listIterator();
   }

   public ListIterator listIterator(int index) {
      return this.selectorChain.listIterator(index);
   }

   public List subList(int fromIndex, int toIndex) {
      return this.selectorChain.subList(fromIndex, toIndex);
   }
}
