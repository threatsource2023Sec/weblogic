package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.util.List;
import java.util.Map;

public interface ISignaturePattern {
   byte PATTERN = 1;
   byte NOT = 2;
   byte OR = 3;
   byte AND = 4;

   boolean matches(Member var1, World var2, boolean var3);

   ISignaturePattern parameterizeWith(Map var1, World var2);

   ISignaturePattern resolveBindings(IScope var1, Bindings var2);

   List getExactDeclaringTypes();

   boolean isMatchOnAnyName();

   boolean couldEverMatch(ResolvedType var1);

   boolean isStarAnnotation();
}
