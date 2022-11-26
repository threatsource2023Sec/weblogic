package com.bea.xml_.impl.jam.xml;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JamService;
import com.bea.xml_.impl.jam.JamServiceFactory;
import com.bea.xml_.impl.jam.JamServiceParams;
import com.bea.xml_.impl.jam.internal.CachedClassBuilder;
import com.bea.xml_.impl.jam.internal.JamServiceImpl;
import com.bea.xml_.impl.jam.internal.elements.ElementContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import javax.xml.stream.XMLStreamException;

public class JamXmlUtils {
   private static final JamXmlUtils INSTANCE = new JamXmlUtils();

   public static final JamXmlUtils getInstance() {
      return INSTANCE;
   }

   private JamXmlUtils() {
   }

   public JamService createService(InputStream in) throws IOException, XMLStreamException {
      if (in == null) {
         throw new IllegalArgumentException("null stream");
      } else {
         JamServiceFactory jsf = JamServiceFactory.getInstance();
         JamServiceParams params = jsf.createServiceParams();
         CachedClassBuilder cache = new CachedClassBuilder();
         params.addClassBuilder(cache);
         JamService out = jsf.createService(params);
         JamXmlReader reader = new JamXmlReader(cache, in, (ElementContext)params);
         reader.read();
         List classNames = Arrays.asList(cache.getClassNames());
         classNames.addAll(Arrays.asList(out.getClassNames()));
         String[] nameArray = new String[classNames.size()];
         classNames.toArray(nameArray);
         ((JamServiceImpl)out).setClassNames(nameArray);
         return out;
      }
   }

   public void toXml(JClass[] clazzes, Writer writer) throws IOException, XMLStreamException {
      if (clazzes == null) {
         throw new IllegalArgumentException("null classes");
      } else if (writer == null) {
         throw new IllegalArgumentException("null writer");
      } else {
         JamXmlWriter out = new JamXmlWriter(writer);
         out.begin();

         for(int i = 0; i < clazzes.length; ++i) {
            out.write(clazzes[i]);
         }

         out.end();
      }
   }
}
