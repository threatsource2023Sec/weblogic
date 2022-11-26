package org.glassfish.grizzly.streams;

import java.io.Closeable;
import org.glassfish.grizzly.Connection;

public interface Stream extends Closeable {
   Connection getConnection();
}
