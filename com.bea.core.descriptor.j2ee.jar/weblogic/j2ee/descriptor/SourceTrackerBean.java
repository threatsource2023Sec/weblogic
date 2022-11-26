package weblogic.j2ee.descriptor;

public interface SourceTrackerBean {
   int SOURCE_I_DESCRIPTOR = 0;
   int SOURCE_I_ANNOTATION = 1;

   int getBeanSource();

   void setBeanSource(int var1);
}
