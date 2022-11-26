package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.springframework.core.io.Resource;
import java.io.IOException;

public interface MetadataReaderFactory {
   MetadataReader getMetadataReader(String var1) throws IOException;

   MetadataReader getMetadataReader(Resource var1) throws IOException;
}
