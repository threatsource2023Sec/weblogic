package weblogic.jms.common;

import com.ning.compress.lzf.LZFInputStream;
import com.ning.compress.lzf.LZFOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class LZFCompressionFactoryImpl extends CompressionFactory {
   public static final byte LZF_COMPRESSION = 1;

   public OutputStream createCompressionOutputStream(OutputStream out, Properties options) throws IOException {
      return new LZFOutputStream(out);
   }

   public InputStream createDecompressionInputStream(InputStream in, int size, Properties options) throws IOException {
      return new LZFInputStream(in);
   }

   public byte getCompressionTag() {
      return 1;
   }
}
