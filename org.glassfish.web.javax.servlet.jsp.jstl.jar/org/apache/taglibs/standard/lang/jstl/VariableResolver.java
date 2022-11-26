package org.apache.taglibs.standard.lang.jstl;

public interface VariableResolver {
   Object resolveVariable(String var1, Object var2) throws ELException;
}
