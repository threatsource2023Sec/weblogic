package weblogic.j2ee.dd.xml;

import java.util.HashSet;
import java.util.Set;

public abstract class RecordingAnnotationProcessor extends FilteringAnnotationProcessor {
   private Set recordedClassNames = null;

   public RecordingAnnotationProcessor(Set supportedAnnotations) {
      super(supportedAnnotations);
   }

   public final void beginRecording() {
      this.recordedClassNames = new HashSet();
   }

   public final Set endRecording() {
      Set completedRecording = new HashSet(this.recordedClassNames);
      this.recordedClassNames = null;
      return completedRecording;
   }

   public void recordComponentClass(Class clz) {
      if (this.recordedClassNames != null) {
         String clzName = clz.getName();
         if (!this.recordedClassNames.contains(clzName)) {
            this.recordedClassNames.add(clzName);
            Class superClass = clz.getSuperclass();
            boolean continueRecursing = true;

            while(superClass != null && superClass != Object.class && continueRecursing) {
               if (continueRecursing = !this.recordedClassNames.contains(superClass.getName())) {
                  this.recordedClassNames.add(superClass.getName());
                  superClass = superClass.getSuperclass();
               }
            }
         }
      }

   }
}
