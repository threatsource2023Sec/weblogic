package org.python.bouncycastle.math.field;

public interface ExtensionField extends FiniteField {
   FiniteField getSubfield();

   int getDegree();
}
