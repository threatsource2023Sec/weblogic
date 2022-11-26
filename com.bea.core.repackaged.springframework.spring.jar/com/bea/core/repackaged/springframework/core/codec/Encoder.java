package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.io.buffer.DataBufferFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import java.util.List;
import java.util.Map;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public interface Encoder {
   boolean canEncode(ResolvableType var1, @Nullable MimeType var2);

   Flux encode(Publisher var1, DataBufferFactory var2, ResolvableType var3, @Nullable MimeType var4, @Nullable Map var5);

   List getEncodableMimeTypes();
}
