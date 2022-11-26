package weblogic.security.providers.authorization;

public interface RangePredicateArgument extends PredicateArgument {
   Comparable getMinValue();

   Comparable getMaxValue();
}
