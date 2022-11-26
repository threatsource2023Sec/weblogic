package weblogic.management.jmx;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public class CompositeTypeThrowable {
   public static final CompositeType THROWABLE;
   public static final String THROWABLE_TYPE_NAME = CompositeType.class.getName() + ".THROWABLE";
   public static final String MESSAGE_KEY = "Message";
   public static final String STACKTRACE_KEY = "StackTrace";
   public static final String CLASSNAME_KEY = "ClassName";
   private static final String[] KEY_NAME_ARRAY = new String[]{"Message", "StackTrace", "ClassName"};
   private static final Class[] ONE_STRING_ARG = new Class[]{String.class};
   private static final Class[] ONE_OBJECT_ARG = new Class[]{Object.class};

   public static final CompositeData newThrowableInstance(Throwable value) throws OpenDataException {
      if (value == null) {
         return null;
      } else {
         String message = value.getMessage();
         StringWriter stringWriter = new StringWriter();
         value.printStackTrace(new PrintWriter(stringWriter));
         String stackTrace = stringWriter.toString();
         String className = value.getClass().getName();
         return new CompositeDataSupport(THROWABLE, KEY_NAME_ARRAY, new Object[]{message, stackTrace, className});
      }
   }

   public static final Throwable reconstitute(CompositeData composite) {
      Throwable result = null;
      String message = (String)composite.get("Message");
      String stackTraceString = (String)composite.get("StackTrace");
      boolean substituted = false;

      try {
         String throwableClassName = (String)composite.get("ClassName");
         Class throwableClass = null;

         try {
            throwableClass = Class.forName(throwableClassName);
         } catch (ClassNotFoundException var14) {
            substituted = true;
            throwableClass = RuntimeException.class;
         }

         Constructor constructor = null;

         try {
            constructor = throwableClass.getConstructor(ONE_STRING_ARG);
         } catch (NoSuchMethodException var13) {
            substituted = true;
         }

         if (constructor == null) {
            result = new RuntimeException("Substituted for the exception " + throwableClassName + " which lacks a String contructor, original message - " + message);
         } else {
            if (substituted) {
               message = "Substituted for the missing exception class " + throwableClassName + ", original message - " + message;
            }

            result = (Throwable)constructor.newInstance(message);
         }

         Throwable currentThrowable = result;
         PreviewStringTokenizer tokenizer = new PreviewStringTokenizer(stackTraceString, "\n");
         Vector elementVector = new Vector();
         String msgLine = tokenizer.nextToken();

         for(msgLine = tokenizer.previewToken(); msgLine != null && !msgLine.startsWith("Caused by:") && !msgLine.trim().startsWith("at "); msgLine = tokenizer.previewToken()) {
            tokenizer.nextElement();
         }

         while(tokenizer.hasMoreElements()) {
            String nextLine = tokenizer.nextToken();
            if (nextLine.trim().length() != 0 && !nextLine.trim().startsWith("...")) {
               if (nextLine.startsWith("Caused by:")) {
                  currentThrowable = handleCausedBy(nextLine, tokenizer, (Throwable)currentThrowable, elementVector);
               } else {
                  handleStackTraceElement(nextLine, elementVector);
               }
            }
         }

         if (!elementVector.isEmpty()) {
            ((Throwable)currentThrowable).setStackTrace((StackTraceElement[])((StackTraceElement[])elementVector.toArray(new StackTraceElement[elementVector.size()])));
         }

         return (Throwable)result;
      } catch (IllegalAccessException var15) {
         throw new RuntimeException(var15);
      } catch (InvocationTargetException var16) {
         throw new RuntimeException(var16);
      } catch (InstantiationException var17) {
         throw new RuntimeException(var17);
      }
   }

   private static void handleStackTraceElement(String nextLine, Vector elementVector) {
      int locationPosition = nextLine.lastIndexOf(40);
      if (locationPosition != -1) {
         String methodPart = nextLine.substring(0, locationPosition);
         int classPosition = methodPart.lastIndexOf(32);
         if (classPosition != -1) {
            methodPart = methodPart.substring(classPosition + 1);
         }

         int parenPosition = methodPart.indexOf(40);
         String argumentsPart = null;
         if (parenPosition != -1) {
            argumentsPart = methodPart.substring(parenPosition);
            methodPart = methodPart.substring(0, parenPosition);
         }

         int methodPosition = methodPart.lastIndexOf(46);
         if (methodPosition != -1) {
            String className = methodPart.substring(0, methodPosition);
            String methodName = methodPart.substring(methodPosition + 1);
            if (argumentsPart != null) {
               methodName = methodName + argumentsPart;
            }

            String locationPart = nextLine.substring(locationPosition + 1);
            parenPosition = locationPart.indexOf(41);
            locationPart = locationPart.substring(0, parenPosition);
            String[] locationParts = locationPart.split(":");
            String fileName = locationParts[0];
            int lineNumber = 0;
            if (fileName.equals("Native Method")) {
               lineNumber = -2;
            } else if (fileName.equals("Unknown Source")) {
               fileName = null;
            } else if (locationParts.length == 2) {
               try {
                  lineNumber = Integer.parseInt(locationParts[1]);
               } catch (NumberFormatException var15) {
                  lineNumber = -1;
               }
            }

            StackTraceElement element = new StackTraceElement(className, methodName, fileName, lineNumber);
            elementVector.add(element);
         }
      }
   }

   private static Throwable handleCausedBy(String causedByLine, PreviewStringTokenizer tokenizer, Throwable currentThrowable, Vector traceElementVector) throws InstantiationException, IllegalAccessException, InvocationTargetException {
      boolean substituted = false;
      String[] lineParts = causedByLine.split(":", 3);
      Class causedByClass = null;
      String causedByClassName = lineParts[1].trim();

      try {
         causedByClass = Class.forName(causedByClassName);
      } catch (ClassNotFoundException var15) {
         causedByClass = Throwable.class;
         substituted = true;
      }

      Constructor causedByConstructor = null;

      try {
         if (causedByClass == AssertionError.class) {
            causedByConstructor = causedByClass.getConstructor(ONE_OBJECT_ARG);
         } else {
            causedByConstructor = causedByClass.getConstructor(ONE_STRING_ARG);
         }
      } catch (NoSuchMethodException var14) {
         substituted = true;
      }

      String messageText = "";
      if (lineParts.length == 3 && lineParts[2] != null && lineParts[2].length() > 1) {
         messageText = lineParts[2].substring(1);
      }

      for(String msgLine = tokenizer.previewToken(); msgLine != null && !msgLine.startsWith("Caused by:") && !msgLine.trim().startsWith("at "); msgLine = tokenizer.previewToken()) {
         messageText = messageText + "\n" + tokenizer.nextElement();
      }

      Throwable causedBy;
      if (causedByConstructor == null) {
         causedBy = new Throwable("Substituted for the exception " + causedByClassName + " which lacks a String contructor, original message - " + messageText);
      } else {
         if (substituted) {
            messageText = "Substituted for missing class " + causedByClassName + " - " + messageText;
         }

         causedBy = (Throwable)causedByConstructor.newInstance(messageText);
      }

      if (currentThrowable instanceof RemoteException) {
         ((RemoteException)currentThrowable).detail = causedBy;
      } else {
         try {
            currentThrowable.initCause(causedBy);
         } catch (IllegalStateException var13) {
         }
      }

      currentThrowable.setStackTrace((StackTraceElement[])((StackTraceElement[])traceElementVector.toArray(new StackTraceElement[traceElementVector.size()])));
      traceElementVector.clear();
      return causedBy;
   }

   static {
      try {
         THROWABLE = new CompositeType(THROWABLE_TYPE_NAME, "Represents a value which can be any of the SimpleTypes", KEY_NAME_ARRAY, new String[]{"The message text of the exception", "The stack trace", "The class name fo the exception"}, new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.STRING});
      } catch (OpenDataException var1) {
         throw new RuntimeException(var1);
      }
   }
}
