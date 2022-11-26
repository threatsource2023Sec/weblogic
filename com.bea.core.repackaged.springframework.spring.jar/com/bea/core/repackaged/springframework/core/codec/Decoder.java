package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import java.util.List;
import java.util.Map;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Decoder {
   boolean canDecode(ResolvableType var1, @Nullable MimeType var2);

   Flux decode(Publisher var1, ResolvableType var2, @Nullable MimeType var3, @Nullable Map var4);

   Mono decodeToMono(Publisher var1, ResolvableType var2, @Nullable MimeType var3, @Nullable Map var4);

   List getDecodableMimeTypes();
}
