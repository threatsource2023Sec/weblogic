package com.googlecode.cqengine.query;

import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.MultiValueAttribute;
import com.googlecode.cqengine.attribute.MultiValueNullableAttribute;
import com.googlecode.cqengine.attribute.OrderMissingFirstAttribute;
import com.googlecode.cqengine.attribute.OrderMissingLastAttribute;
import com.googlecode.cqengine.attribute.SelfAttribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableMapAttribute;
import com.googlecode.cqengine.attribute.StandingQueryAttribute;
import com.googlecode.cqengine.attribute.support.FunctionalMultiValueAttribute;
import com.googlecode.cqengine.attribute.support.FunctionalMultiValueNullableAttribute;
import com.googlecode.cqengine.attribute.support.FunctionalSimpleAttribute;
import com.googlecode.cqengine.attribute.support.FunctionalSimpleNullableAttribute;
import com.googlecode.cqengine.attribute.support.MultiValueFunction;
import com.googlecode.cqengine.attribute.support.SimpleFunction;
import com.googlecode.cqengine.entity.MapEntity;
import com.googlecode.cqengine.entity.PrimaryKeyedMapEntity;
import com.googlecode.cqengine.query.logical.And;
import com.googlecode.cqengine.query.logical.Not;
import com.googlecode.cqengine.query.logical.Or;
import com.googlecode.cqengine.query.option.ArgumentValidationOption;
import com.googlecode.cqengine.query.option.ArgumentValidationStrategy;
import com.googlecode.cqengine.query.option.AttributeOrder;
import com.googlecode.cqengine.query.option.DeduplicationOption;
import com.googlecode.cqengine.query.option.DeduplicationStrategy;
import com.googlecode.cqengine.query.option.FlagsDisabled;
import com.googlecode.cqengine.query.option.FlagsEnabled;
import com.googlecode.cqengine.query.option.IsolationLevel;
import com.googlecode.cqengine.query.option.IsolationOption;
import com.googlecode.cqengine.query.option.OrderByOption;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.option.Threshold;
import com.googlecode.cqengine.query.option.Thresholds;
import com.googlecode.cqengine.query.simple.All;
import com.googlecode.cqengine.query.simple.Between;
import com.googlecode.cqengine.query.simple.Equal;
import com.googlecode.cqengine.query.simple.ExistsIn;
import com.googlecode.cqengine.query.simple.GreaterThan;
import com.googlecode.cqengine.query.simple.Has;
import com.googlecode.cqengine.query.simple.In;
import com.googlecode.cqengine.query.simple.LessThan;
import com.googlecode.cqengine.query.simple.None;
import com.googlecode.cqengine.query.simple.StringContains;
import com.googlecode.cqengine.query.simple.StringEndsWith;
import com.googlecode.cqengine.query.simple.StringIsContainedIn;
import com.googlecode.cqengine.query.simple.StringMatchesRegex;
import com.googlecode.cqengine.query.simple.StringStartsWith;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import net.jodah.typetools.TypeResolver;

public class QueryFactory {
   static final String GENERIC_TYPE_RESOLUTION_FAILURE_MESSAGE = "If the function you supplied was created from a lambda expression, then it's likely that the host JVM does not allow the generic type information to be read from lambda expressions. Alternatively, if you supplied a class-based implementation of the function, then you must ensure that you specified the generic types of the function when it was compiled. As a workaround, you can use the counterpart methods in QueryFactory which allow the generic types to be specified explicitly.";

   QueryFactory() {
   }

   public static Equal equal(Attribute attribute, Object attributeValue) {
      return new Equal(attribute, attributeValue);
   }

   public static LessThan lessThanOrEqualTo(Attribute attribute, Comparable attributeValue) {
      return new LessThan(attribute, attributeValue, true);
   }

   public static LessThan lessThan(Attribute attribute, Comparable attributeValue) {
      return new LessThan(attribute, attributeValue, false);
   }

   public static GreaterThan greaterThanOrEqualTo(Attribute attribute, Comparable attributeValue) {
      return new GreaterThan(attribute, attributeValue, true);
   }

   public static GreaterThan greaterThan(Attribute attribute, Comparable attributeValue) {
      return new GreaterThan(attribute, attributeValue, false);
   }

   public static Between between(Attribute attribute, Comparable lowerValue, boolean lowerInclusive, Comparable upperValue, boolean upperInclusive) {
      return new Between(attribute, lowerValue, lowerInclusive, upperValue, upperInclusive);
   }

   public static Between between(Attribute attribute, Comparable lowerValue, Comparable upperValue) {
      return new Between(attribute, lowerValue, true, upperValue, true);
   }

   public static Query in(Attribute attribute, Object... attributeValues) {
      return in(attribute, (Collection)Arrays.asList(attributeValues));
   }

   public static Query in(Attribute attribute, Collection attributeValues) {
      return in(attribute, attribute instanceof SimpleAttribute, attributeValues);
   }

   public static Query in(Attribute attribute, boolean disjoint, Collection attributeValues) {
      int n = attributeValues.size();
      switch (n) {
         case 0:
            return none(attribute.getObjectType());
         case 1:
            Object singleValue = attributeValues.iterator().next();
            return equal(attribute, singleValue);
         default:
            Set values = attributeValues instanceof Set ? (Set)attributeValues : new HashSet(attributeValues);
            return new In(attribute, disjoint, (Set)values);
      }
   }

   public static StringStartsWith startsWith(Attribute attribute, CharSequence attributeValue) {
      return new StringStartsWith(attribute, attributeValue);
   }

   public static StringEndsWith endsWith(Attribute attribute, CharSequence attributeValue) {
      return new StringEndsWith(attribute, attributeValue);
   }

   public static StringContains contains(Attribute attribute, CharSequence attributeValue) {
      return new StringContains(attribute, attributeValue);
   }

   public static StringIsContainedIn isContainedIn(Attribute attribute, CharSequence attributeValue) {
      return new StringIsContainedIn(attribute, attributeValue);
   }

   public static StringMatchesRegex matchesRegex(Attribute attribute, Pattern regexPattern) {
      return new StringMatchesRegex(attribute, regexPattern);
   }

   public static StringMatchesRegex matchesRegex(Attribute attribute, String regex) {
      return new StringMatchesRegex(attribute, Pattern.compile(regex));
   }

   public static Has has(Attribute attribute) {
      return new Has(attribute);
   }

   public static And and(Query query1, Query query2) {
      Collection queries = Arrays.asList(query1, query2);
      return new And(queries);
   }

   public static And and(Query query1, Query query2, Query... additionalQueries) {
      Collection queries = new ArrayList(2 + additionalQueries.length);
      queries.add(query1);
      queries.add(query2);
      Collections.addAll(queries, additionalQueries);
      return new And(queries);
   }

   public static And and(Query query1, Query query2, Collection additionalQueries) {
      Collection queries = new ArrayList(2 + additionalQueries.size());
      queries.add(query1);
      queries.add(query2);
      queries.addAll(additionalQueries);
      return new And(queries);
   }

   public static Or or(Query query1, Query query2) {
      Collection queries = Arrays.asList(query1, query2);
      return new Or(queries);
   }

   public static Or or(Query query1, Query query2, Query... additionalQueries) {
      Collection queries = new ArrayList(2 + additionalQueries.length);
      queries.add(query1);
      queries.add(query2);
      Collections.addAll(queries, additionalQueries);
      return new Or(queries);
   }

   public static Or or(Query query1, Query query2, Collection additionalQueries) {
      Collection queries = new ArrayList(2 + additionalQueries.size());
      queries.add(query1);
      queries.add(query2);
      queries.addAll(additionalQueries);
      return new Or(queries);
   }

   public static Not not(Query query) {
      return new Not(query);
   }

   public static Query existsIn(IndexedCollection foreignCollection, Attribute localKeyAttribute, Attribute foreignKeyAttribute) {
      return new ExistsIn(foreignCollection, localKeyAttribute, foreignKeyAttribute);
   }

   public static Query existsIn(IndexedCollection foreignCollection, Attribute localKeyAttribute, Attribute foreignKeyAttribute, Query foreignRestrictions) {
      return new ExistsIn(foreignCollection, localKeyAttribute, foreignKeyAttribute, foreignRestrictions);
   }

   public static Query all(Class objectType) {
      return new All(objectType);
   }

   public static Query none(Class objectType) {
      return new None(objectType);
   }

   public static OrderByOption orderBy(List attributeOrders) {
      return new OrderByOption(attributeOrders);
   }

   public static OrderByOption orderBy(AttributeOrder... attributeOrders) {
      return new OrderByOption(Arrays.asList(attributeOrders));
   }

   public static AttributeOrder ascending(Attribute attribute) {
      return new AttributeOrder(attribute, false);
   }

   public static AttributeOrder descending(Attribute attribute) {
      return new AttributeOrder(attribute, true);
   }

   public static DeduplicationOption deduplicate(DeduplicationStrategy deduplicationStrategy) {
      return new DeduplicationOption(deduplicationStrategy);
   }

   public static IsolationOption isolationLevel(IsolationLevel isolationLevel) {
      return new IsolationOption(isolationLevel);
   }

   public static ArgumentValidationOption argumentValidation(ArgumentValidationStrategy strategy) {
      return new ArgumentValidationOption(strategy);
   }

   public static QueryOptions queryOptions(Object... queryOptions) {
      return queryOptions((Collection)Arrays.asList(queryOptions));
   }

   public static QueryOptions queryOptions(Collection queryOptions) {
      QueryOptions resultOptions = new QueryOptions();
      Iterator var2 = queryOptions.iterator();

      while(var2.hasNext()) {
         Object queryOption = var2.next();
         resultOptions.put(queryOption.getClass(), queryOption);
      }

      return resultOptions;
   }

   public static QueryOptions noQueryOptions() {
      return new QueryOptions();
   }

   public static FlagsEnabled enableFlags(Object... flags) {
      FlagsEnabled result = new FlagsEnabled();
      Object[] var2 = flags;
      int var3 = flags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object flag = var2[var4];
         result.add(flag);
      }

      return result;
   }

   public static FlagsDisabled disableFlags(Object... flags) {
      FlagsDisabled result = new FlagsDisabled();
      Object[] var2 = flags;
      int var3 = flags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object flag = var2[var4];
         result.add(flag);
      }

      return result;
   }

   public static Thresholds applyThresholds(Threshold... thresholds) {
      return new Thresholds(Arrays.asList(thresholds));
   }

   public static Threshold threshold(Object key, Double value) {
      return new Threshold(key, value);
   }

   public static SelfAttribute selfAttribute(Class objectType) {
      return new SelfAttribute(objectType);
   }

   public static Attribute mapAttribute(Object mapKey, Class mapValueType) {
      return new SimpleNullableMapAttribute(mapKey, mapValueType);
   }

   public static Map mapEntity(Map map) {
      return (Map)(map instanceof MapEntity ? map : new MapEntity(map));
   }

   public static Map primaryKeyedMapEntity(Map map, Object primaryKey) {
      return (Map)(map instanceof PrimaryKeyedMapEntity ? map : new PrimaryKeyedMapEntity(map, primaryKey));
   }

   public static OrderMissingLastAttribute missingLast(Attribute delegateAttribute) {
      return new OrderMissingLastAttribute(delegateAttribute);
   }

   public static OrderMissingFirstAttribute missingFirst(Attribute delegateAttribute) {
      return new OrderMissingFirstAttribute(delegateAttribute);
   }

   public static StandingQueryAttribute forStandingQuery(Query standingQuery) {
      return new StandingQueryAttribute(standingQuery);
   }

   public static StandingQueryAttribute forObjectsMissing(Attribute attribute) {
      return forStandingQuery(not(has(attribute)));
   }

   public static SimpleAttribute attribute(SimpleFunction function) {
      return attribute(function.getClass().getName(), function);
   }

   public static SimpleAttribute attribute(String attributeName, SimpleFunction function) {
      FunctionGenericTypes resolved = resolveSimpleFunctionGenericTypes(function.getClass());
      return attribute(resolved.objectType, resolved.attributeType, attributeName, function);
   }

   public static SimpleAttribute attribute(Class objectType, Class attributeType, String attributeName, SimpleFunction function) {
      return new FunctionalSimpleAttribute(objectType, attributeType, attributeName, function);
   }

   public static SimpleNullableAttribute nullableAttribute(SimpleFunction function) {
      return nullableAttribute(function.getClass().getName(), function);
   }

   public static SimpleNullableAttribute nullableAttribute(String attributeName, SimpleFunction function) {
      FunctionGenericTypes resolved = resolveSimpleFunctionGenericTypes(function.getClass());
      return nullableAttribute(resolved.objectType, resolved.attributeType, attributeName, function);
   }

   public static SimpleNullableAttribute nullableAttribute(Class objectType, Class attributeType, String attributeName, SimpleFunction function) {
      return new FunctionalSimpleNullableAttribute(objectType, attributeType, attributeName, function);
   }

   public static MultiValueAttribute attribute(Class attributeType, MultiValueFunction function) {
      return attribute(attributeType, function.getClass().getName(), function);
   }

   public static MultiValueAttribute attribute(Class attributeType, String attributeName, MultiValueFunction function) {
      Class resolvedObjectType = resolveMultiValueFunctionGenericObjectType(function.getClass());
      return attribute(resolvedObjectType, attributeType, attributeName, function);
   }

   public static MultiValueAttribute attribute(Class objectType, Class attributeType, String attributeName, MultiValueFunction function) {
      return new FunctionalMultiValueAttribute(objectType, attributeType, attributeName, function);
   }

   public static MultiValueNullableAttribute nullableAttribute(Class attributeType, MultiValueFunction function) {
      return nullableAttribute(attributeType, function.getClass().getName(), function);
   }

   public static MultiValueNullableAttribute nullableAttribute(Class attributeType, String attributeName, MultiValueFunction function) {
      Class resolvedObjectType = resolveMultiValueFunctionGenericObjectType(function.getClass());
      return nullableAttribute(resolvedObjectType, attributeType, attributeName, function);
   }

   public static MultiValueNullableAttribute nullableAttribute(Class objectType, Class attributeType, String attributeName, MultiValueFunction function) {
      return new FunctionalMultiValueNullableAttribute(objectType, attributeType, attributeName, true, function);
   }

   static FunctionGenericTypes resolveSimpleFunctionGenericTypes(Class subType) {
      Class[] typeArgs = TypeResolver.resolveRawArguments(SimpleFunction.class, subType);
      validateSimpleFunctionGenericTypes(typeArgs, subType);
      Class objectType = typeArgs[0];
      Class attributeType = typeArgs[1];
      return new FunctionGenericTypes(objectType, attributeType);
   }

   static void validateSimpleFunctionGenericTypes(Class[] typeArgs, Class subType) {
      if (typeArgs == null) {
         throw new IllegalStateException("Could not resolve any generic type information from the given function of type: " + subType.getName() + ". " + "If the function you supplied was created from a lambda expression, then it's likely that the host JVM does not allow the generic type information to be read from lambda expressions. Alternatively, if you supplied a class-based implementation of the function, then you must ensure that you specified the generic types of the function when it was compiled. As a workaround, you can use the counterpart methods in QueryFactory which allow the generic types to be specified explicitly.");
      } else if (typeArgs.length != 2 || typeArgs[0] == TypeResolver.Unknown.class || typeArgs[1] == TypeResolver.Unknown.class) {
         throw new IllegalStateException("Could not resolve sufficient generic type information from the given function of type: " + subType.getName() + ", resolved: " + Arrays.toString(typeArgs) + ". " + "If the function you supplied was created from a lambda expression, then it's likely that the host JVM does not allow the generic type information to be read from lambda expressions. Alternatively, if you supplied a class-based implementation of the function, then you must ensure that you specified the generic types of the function when it was compiled. As a workaround, you can use the counterpart methods in QueryFactory which allow the generic types to be specified explicitly.");
      }
   }

   static Class resolveMultiValueFunctionGenericObjectType(Class subType) {
      Class[] typeArgs = TypeResolver.resolveRawArguments(MultiValueFunction.class, subType);
      validateMultiValueFunctionGenericTypes(typeArgs, subType);
      Class objectType = typeArgs[0];
      return objectType;
   }

   static void validateMultiValueFunctionGenericTypes(Class[] typeArgs, Class subType) {
      if (typeArgs == null) {
         throw new IllegalStateException("Could not resolve any generic type information from the given function of type: " + subType.getName() + ". " + "If the function you supplied was created from a lambda expression, then it's likely that the host JVM does not allow the generic type information to be read from lambda expressions. Alternatively, if you supplied a class-based implementation of the function, then you must ensure that you specified the generic types of the function when it was compiled. As a workaround, you can use the counterpart methods in QueryFactory which allow the generic types to be specified explicitly.");
      } else if (typeArgs.length != 3 || typeArgs[0] == TypeResolver.Unknown.class) {
         throw new IllegalStateException("Could not resolve sufficient generic type information from the given function of type: " + subType.getName() + ", resolved: " + Arrays.toString(typeArgs) + ". " + "If the function you supplied was created from a lambda expression, then it's likely that the host JVM does not allow the generic type information to be read from lambda expressions. Alternatively, if you supplied a class-based implementation of the function, then you must ensure that you specified the generic types of the function when it was compiled. As a workaround, you can use the counterpart methods in QueryFactory which allow the generic types to be specified explicitly.");
      }
   }

   public static And and(Query query1, Query query2, Query query3) {
      Collection queries = Arrays.asList(query1, query2, query3);
      return new And(queries);
   }

   public static And and(Query query1, Query query2, Query query3, Query query4) {
      Collection queries = Arrays.asList(query1, query2, query3, query4);
      return new And(queries);
   }

   public static And and(Query query1, Query query2, Query query3, Query query4, Query query5) {
      Collection queries = Arrays.asList(query1, query2, query3, query4, query5);
      return new And(queries);
   }

   public static Or or(Query query1, Query query2, Query query3) {
      Collection queries = Arrays.asList(query1, query2, query3);
      return new Or(queries);
   }

   public static Or or(Query query1, Query query2, Query query3, Query query4) {
      Collection queries = Arrays.asList(query1, query2, query3, query4);
      return new Or(queries);
   }

   public static Or or(Query query1, Query query2, Query query3, Query query4, Query query5) {
      Collection queries = Arrays.asList(query1, query2, query3, query4, query5);
      return new Or(queries);
   }

   public static OrderByOption orderBy(AttributeOrder attributeOrder) {
      List attributeOrders = Collections.singletonList(attributeOrder);
      return new OrderByOption(attributeOrders);
   }

   public static OrderByOption orderBy(AttributeOrder attributeOrder1, AttributeOrder attributeOrder2) {
      List attributeOrders = Arrays.asList(attributeOrder1, attributeOrder2);
      return new OrderByOption(attributeOrders);
   }

   public static OrderByOption orderBy(AttributeOrder attributeOrder1, AttributeOrder attributeOrder2, AttributeOrder attributeOrder3) {
      List attributeOrders = Arrays.asList(attributeOrder1, attributeOrder2, attributeOrder3);
      return new OrderByOption(attributeOrders);
   }

   public static OrderByOption orderBy(AttributeOrder attributeOrder1, AttributeOrder attributeOrder2, AttributeOrder attributeOrder3, AttributeOrder attributeOrder4) {
      List attributeOrders = Arrays.asList(attributeOrder1, attributeOrder2, attributeOrder3, attributeOrder4);
      return new OrderByOption(attributeOrders);
   }

   public static OrderByOption orderBy(AttributeOrder attributeOrder1, AttributeOrder attributeOrder2, AttributeOrder attributeOrder3, AttributeOrder attributeOrder4, AttributeOrder attributeOrder5) {
      List attributeOrders = Arrays.asList(attributeOrder1, attributeOrder2, attributeOrder3, attributeOrder4, attributeOrder5);
      return new OrderByOption(attributeOrders);
   }

   public static Predicate predicate(Query query) {
      return predicate(query, (QueryOptions)null);
   }

   public static Predicate predicate(Query query, QueryOptions queryOptions) {
      return (object) -> {
         return query.matches(object, queryOptions);
      };
   }

   public static QueryOptions queryOptions(Object queryOption) {
      return queryOptions((Collection)Collections.singleton(queryOption));
   }

   public static QueryOptions queryOptions(Object queryOption1, Object queryOption2) {
      List queryOptions = Arrays.asList(queryOption1, queryOption2);
      return queryOptions((Collection)queryOptions);
   }

   static class FunctionGenericTypes {
      final Class objectType;
      final Class attributeType;

      FunctionGenericTypes(Class objectType, Class attributeType) {
         this.objectType = objectType;
         this.attributeType = attributeType;
      }
   }
}
