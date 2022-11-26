package org.python.antlr.runtime;

public interface TokenSource {
   Token nextToken();

   String getSourceName();
}
