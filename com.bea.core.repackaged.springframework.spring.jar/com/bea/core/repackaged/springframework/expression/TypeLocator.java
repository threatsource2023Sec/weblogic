package com.bea.core.repackaged.springframework.expression;

@FunctionalInterface
public interface TypeLocator {
   Class findType(String var1) throws EvaluationException;
}
