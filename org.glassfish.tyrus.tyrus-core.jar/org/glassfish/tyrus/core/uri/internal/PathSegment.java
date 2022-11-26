package org.glassfish.tyrus.core.uri.internal;

public interface PathSegment {
   String getPath();

   MultivaluedMap getMatrixParameters();
}
