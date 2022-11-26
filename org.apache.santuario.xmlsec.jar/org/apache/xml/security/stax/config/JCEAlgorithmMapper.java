package org.apache.xml.security.stax.config;

import java.util.List;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.configuration.AlgorithmType;
import org.apache.xml.security.configuration.JCEAlgorithmMappingsType;

public class JCEAlgorithmMapper extends JCEMapper {
   private JCEAlgorithmMapper() {
   }

   protected static synchronized void init(JCEAlgorithmMappingsType jceAlgorithmMappingsType) throws Exception {
      List algorithms = jceAlgorithmMappingsType.getAlgorithm();

      for(int i = 0; i < algorithms.size(); ++i) {
         AlgorithmType algorithmType = (AlgorithmType)algorithms.get(i);
         int keyLength = 0;
         if (algorithmType.getKeyLength() != null) {
            keyLength = algorithmType.getKeyLength();
         }

         int ivLength = 0;
         if (algorithmType.getIVLength() != null) {
            ivLength = algorithmType.getIVLength();
         }

         JCEMapper.Algorithm algorithm = new JCEMapper.Algorithm(algorithmType.getRequiredKey(), algorithmType.getJCEName(), algorithmType.getAlgorithmClass(), keyLength, ivLength, algorithmType.getJCEProvider());
         register(algorithmType.getURI(), algorithm);
      }

   }
}
