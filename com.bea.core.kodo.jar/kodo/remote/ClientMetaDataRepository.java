package kodo.remote;

import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.SequenceMetaData;

public class ClientMetaDataRepository extends MetaDataRepository {
   protected SequenceMetaData newSequenceMetaData(String name) {
      return new SequenceMetaData(name, this) {
         protected Seq instantiate(ClassLoader envLoader) {
            return new ClientSeq(this.getName());
         }
      };
   }
}
