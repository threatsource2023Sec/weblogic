package com.bea.xml_.impl.jam;

import java.net.URI;

public interface JSourcePosition {
   int getColumn();

   int getLine();

   URI getSourceURI();
}
