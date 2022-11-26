package oracle.jrockit.jfr;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;

public interface ProducerDescriptor {
   int getId();

   String getDescription();

   String getName();

   URI getURI();

   Collection getEvents();

   ByteBuffer getBinaryDescriptor();

   long writeCheckPoint(FileChannel var1, long var2) throws IOException;
}
