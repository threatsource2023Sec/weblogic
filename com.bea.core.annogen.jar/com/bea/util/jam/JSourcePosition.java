package com.bea.util.jam;

import java.net.URI;

public interface JSourcePosition {
   int getColumn();

   int getLine();

   int getOffset();

   URI getSourceURI();
}
