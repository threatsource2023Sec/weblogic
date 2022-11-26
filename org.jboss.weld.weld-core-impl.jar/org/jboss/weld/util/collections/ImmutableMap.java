package org.jboss.weld.util.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import org.jboss.weld.util.Preconditions;

public abstract class ImmutableMap extends AbstractImmutableMap {
   ImmutableMap() {
   }

   public static Map copyOf(Map map) {
      Preconditions.checkNotNull(map);
      return builder().putAll(map).build();
   }

   public static Map of(Object key, Object value) {
      return new ImmutableMapEntry(key, value);
   }

   public static ImmutableMapCollector collector(Function keyMapper, Function valueMapper) {
      return new ImmutableMapCollector(keyMapper, valueMapper);
   }

   public static Builder builder() {
      return new HashMapBuilder();
   }

   private static class ImmutableMapCollector implements Collector {
      private static final Set CHARACTERISTICS = ImmutableSet.of();
      private final Function keyMapper;
      private final Function valueMapper;

      private ImmutableMapCollector(Function keyMapper, Function valueMapper) {
         this.keyMapper = keyMapper;
         this.valueMapper = valueMapper;
      }

      public Supplier supplier() {
         return () -> {
            return new HashMapBuilder();
         };
      }

      public BiConsumer accumulator() {
         return (b, i) -> {
            b.put(this.keyMapper.apply(i), this.valueMapper.apply(i));
         };
      }

      public BinaryOperator combiner() {
         return (builder1, builder2) -> {
            return builder1.putAll(builder2);
         };
      }

      public Function finisher() {
         return HashMapBuilder::build;
      }

      public Set characteristics() {
         return CHARACTERISTICS;
      }

      // $FF: synthetic method
      ImmutableMapCollector(Function x0, Function x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class HashMapBuilder implements Builder {
      private static final int DEFAULT_INITIAL_CAPACITY = 4;
      private static final float LOAD_FACTOR = 1.2F;
      private Map map;

      private HashMapBuilder() {
         this.map = new HashMap(4, 1.2F);
      }

      public Builder put(Object key, Object value) {
         this.map.put(key, value);
         return this;
      }

      public Builder putAll(Map items) {
         this.map.putAll(items);
         return this;
      }

      HashMapBuilder putAll(HashMapBuilder items) {
         this.map.putAll(items.map);
         return this;
      }

      public Map build() {
         if (this.map.isEmpty()) {
            return Collections.emptyMap();
         } else {
            return (Map)(this.map.size() == 1 ? new ImmutableMapEntry((Map.Entry)this.map.entrySet().iterator().next()) : Collections.unmodifiableMap(this.map));
         }
      }

      // $FF: synthetic method
      HashMapBuilder(Object x0) {
         this();
      }
   }

   public interface Builder {
      Builder put(Object var1, Object var2);

      Builder putAll(Map var1);

      Map build();
   }
}
