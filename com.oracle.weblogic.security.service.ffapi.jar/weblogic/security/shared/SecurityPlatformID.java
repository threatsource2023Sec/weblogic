package weblogic.security.shared;

public interface SecurityPlatformID {
   int UNDEFINED = 0;
   int WLS = 1;
   int WLES = 2;

   int value();
}
