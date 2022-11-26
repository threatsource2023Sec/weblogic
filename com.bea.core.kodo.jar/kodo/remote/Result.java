package kodo.remote;

import org.apache.openjpa.lib.util.Closeable;

interface Result extends Closeable {
   int size();
}
