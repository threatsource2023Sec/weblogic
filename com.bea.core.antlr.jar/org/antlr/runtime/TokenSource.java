package org.antlr.runtime;

public interface TokenSource {
   Token nextToken();

   String getSourceName();
}
