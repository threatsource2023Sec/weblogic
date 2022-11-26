package org.cryptacular.spec;

public interface Spec {
   String getAlgorithm();

   Object newInstance();
}
