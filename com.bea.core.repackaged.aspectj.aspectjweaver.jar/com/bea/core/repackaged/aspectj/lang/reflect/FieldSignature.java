package com.bea.core.repackaged.aspectj.lang.reflect;

import java.lang.reflect.Field;

public interface FieldSignature extends MemberSignature {
   Class getFieldType();

   Field getField();
}
