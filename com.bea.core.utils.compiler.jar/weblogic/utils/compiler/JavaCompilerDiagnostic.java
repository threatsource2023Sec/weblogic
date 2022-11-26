package weblogic.utils.compiler;

public interface JavaCompilerDiagnostic {
   String getMessage();

   String getOriginatingFileName();

   int getSourceStart();

   int getSourceEnd();

   boolean isError();
}
