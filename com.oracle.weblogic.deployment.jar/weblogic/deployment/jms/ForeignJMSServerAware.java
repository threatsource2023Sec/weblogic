package weblogic.deployment.jms;

public interface ForeignJMSServerAware {
   boolean isReferencedByFS();

   void setReferencedByFS(boolean var1);
}
