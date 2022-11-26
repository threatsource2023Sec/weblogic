package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.util.Collection;

public class CharacterAttributeBag extends GenericBag {
   public CharacterAttributeBag() {
   }

   public CharacterAttributeBag(Collection contents) {
      super(contents);
   }

   public Type getType() {
      return Type.CHARACTER_BAG;
   }
}
