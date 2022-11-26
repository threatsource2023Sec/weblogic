package weblogic.jms.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class GZIPCompressionFactoryImpl extends CompressionFactory {
   public static final byte GZIP_COMPRESSION = 0;

   public OutputStream createCompressionOutputStream(OutputStream out, Properties options) throws IOException {
      return new GZIPOutputStream(out, DeflaterDispenser.getDeflater(options.getProperty("weblogic.jms.common.gzip.level")));
   }

   public InputStream createDecompressionInputStream(InputStream in, int size, Properties options) throws IOException {
      return new GZIPInputStream(in, size, InflaterDispenser.getInflater());
   }

   public byte getCompressionTag() {
      return 0;
   }
}
