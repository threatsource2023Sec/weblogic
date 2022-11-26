package org.python.google.common.collect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Supplier;

@Beta
@GwtCompatible
public abstract class MultimapBuilder {
   private static final int DEFAULT_EXPECTED_KEYS = 8;

   private MultimapBuilder() {
   }

   public static MultimapBuilderWithKeys hashKeys() {
      return hashKeys(8);
   }

   public static MultimapBuilderWithKeys hashKeys(final int expectedKeys) {
      CollectPreconditions.checkNonnegative(expectedKeys, "expectedKeys");
      return new MultimapBuilderWithKeys() {
         Map createMap() {
            return Maps.newHashMapWithExpectedSize(expectedKeys);
         }
      };
   }

   public static MultimapBuilderWithKeys linkedHashKeys() {
      return linkedHashKeys(8);
   }

   public static MultimapBuilderWithKeys linkedHashKeys(final int expectedKeys) {
      CollectPreconditions.checkNonnegative(expectedKeys, "expectedKeys");
      return new MultimapBuilderWithKeys() {
         Map createMap() {
            return Maps.newLinkedHashMapWithExpectedSize(expectedKeys);
         }
      };
   }

   public static MultimapBuilderWithKeys treeKeys() {
      return treeKeys(Ordering.natural());
   }

   public static MultimapBuilderWithKeys treeKeys(final Comparator comparator) {
      Preconditions.checkNotNull(comparator);
      return new MultimapBuilderWithKeys() {
         Map createMap() {
            return new TreeMap(comparator);
         }
      };
   }

   public static MultimapBuilderWithKeys enumKeys(final Class keyClass) {
      Preconditions.checkNotNull(keyClass);
      return new MultimapBuilderWithKeys() {
         Map createMap() {
            return new EnumMap(keyClass);
         }
      };
   }

   public abstract Multimap build();

   public Multimap build(Multimap multimap) {
      Multimap result = this.build();
      result.putAll(multimap);
      return result;
   }

   // $FF: synthetic method
   MultimapBuilder(Object x0) {
      this();
   }

   public abstract static class SortedSetMultimapBuilder extends SetMultimapBuilder {
      SortedSetMultimapBuilder() {
      }

      public abstract SortedSetMultimap build();

      public SortedSetMultimap build(Multimap multimap) {
         return (SortedSetMultimap)super.build(multimap);
      }
   }

   public abstract static class SetMultimapBuilder extends MultimapBuilder {
      SetMultimapBuilder() {
         super(null);
      }

      public abstract SetMultimap build();

      public SetMultimap build(Multimap multimap) {
         return (SetMultimap)super.build(multimap);
      }
   }

   public abstract static class ListMultimapBuilder extends MultimapBuilder {
      ListMultimapBuilder() {
         super(null);
      }

      public abstract ListMultimap build();

      public ListMultimap build(Multimap multimap) {
         return (ListMultimap)super.build(multimap);
      }
   }

   public abstract static class MultimapBuilderWithKeys {
      private static final int DEFAULT_EXPECTED_VALUES_PER_KEY = 2;

      MultimapBuilderWithKeys() {
      }

      abstract Map createMap();

      public ListMultimapBuilder arrayListValues() {
         return this.arrayListValues(2);
      }

      public ListMultimapBuilder arrayListValues(final int expectedValuesPerKey) {
         CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
         return new ListMultimapBuilder() {
            public ListMultimap build() {
               return Multimaps.newListMultimap(MultimapBuilderWithKeys.this.createMap(), new ArrayListSupplier(expectedValuesPerKey));
            }
         };
      }

      public ListMultimapBuilder linkedListValues() {
         return new ListMultimapBuilder() {
            public ListMultimap build() {
               return Multimaps.newListMultimap(MultimapBuilderWithKeys.this.createMap(), MultimapBuilder.LinkedListSupplier.instance());
            }
         };
      }

      public SetMultimapBuilder hashSetValues() {
         return this.hashSetValues(2);
      }

      public SetMultimapBuilder hashSetValues(final int expectedValuesPerKey) {
         CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
         return new SetMultimapBuilder() {
            public SetMultimap build() {
               return Multimaps.newSetMultimap(MultimapBuilderWithKeys.this.createMap(), new HashSetSupplier(expectedValuesPerKey));
            }
         };
      }

      public SetMultimapBuilder linkedHashSetValues() {
         return this.linkedHashSetValues(2);
      }

      public SetMultimapBuilder linkedHashSetValues(final int expectedValuesPerKey) {
         CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
         return new SetMultimapBuilder() {
            public SetMultimap build() {
               return Multimaps.newSetMultimap(MultimapBuilderWithKeys.this.createMap(), new LinkedHashSetSupplier(expectedValuesPerKey));
            }
         };
      }

      public SortedSetMultimapBuilder treeSetValues() {
         return this.treeSetValues(Ordering.natural());
      }

      public SortedSetMultimapBuilder treeSetValues(final Comparator comparator) {
         Preconditions.checkNotNull(comparator, "comparator");
         return new SortedSetMultimapBuilder() {
            public SortedSetMultimap build() {
               return Multimaps.newSortedSetMultimap(MultimapBuilderWithKeys.this.createMap(), new TreeSetSupplier(comparator));
            }
         };
      }

      public SetMultimapBuilder enumSetValues(final Class valueClass) {
         Preconditions.checkNotNull(valueClass, "valueClass");
         return new SetMultimapBuilder() {
            public SetMultimap build() {
               Supplier factory = new EnumSetSupplier(valueClass);
               return Multimaps.newSetMultimap(MultimapBuilderWithKeys.this.createMap(), factory);
            }
         };
      }
   }

   private static final class EnumSetSupplier implements Supplier, Serializable {
      private final Class clazz;

      EnumSetSupplier(Class clazz) {
         this.clazz = (Class)Preconditions.checkNotNull(clazz);
      }

      public Set get() {
         return EnumSet.noneOf(this.clazz);
      }
   }

   private static final class TreeSetSupplier implements Supplier, Serializable {
      private final Comparator comparator;

      TreeSetSupplier(Comparator comparator) {
         this.comparator = (Comparator)Preconditions.checkNotNull(comparator);
      }

      public SortedSet get() {
         return new TreeSet(this.comparator);
      }
   }

   private static final class LinkedHashSetSupplier implements Supplier, Serializable {
      private final int expectedValuesPerKey;

      LinkedHashSetSupplier(int expectedValuesPerKey) {
         this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
      }

      public Set get() {
         return Sets.newLinkedHashSetWithExpectedSize(this.expectedValuesPerKey);
      }
   }

   private static final class HashSetSupplier implements Supplier, Serializable {
      private final int expectedValuesPerKey;

      HashSetSupplier(int expectedValuesPerKey) {
         this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
      }

      public Set get() {
         return Sets.newHashSetWithExpectedSize(this.expectedValuesPerKey);
      }
   }

   private static enum LinkedListSupplier implements Supplier {
      INSTANCE;

      public static Supplier instance() {
         Supplier result = INSTANCE;
         return result;
      }

      public List get() {
         return new LinkedList();
      }
   }

   private static final class ArrayListSupplier implements Supplier, Serializable {
      private final int expectedValuesPerKey;

      ArrayListSupplier(int expectedValuesPerKey) {
         this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
      }

      public List get() {
         return new ArrayList(this.expectedValuesPerKey);
      }
   }
}
