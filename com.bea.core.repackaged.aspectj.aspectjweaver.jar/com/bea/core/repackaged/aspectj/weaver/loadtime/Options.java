package com.bea.core.repackaged.aspectj.weaver.loadtime;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.util.LangUtil;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Options {
   private static final String OPTION_15 = "-1.5";
   private static final String OPTION_lazyTjp = "-XlazyTjp";
   private static final String OPTION_noWarn = "-nowarn";
   private static final String OPTION_noWarnNone = "-warn:none";
   private static final String OPTION_proceedOnError = "-proceedOnError";
   private static final String OPTION_verbose = "-verbose";
   private static final String OPTION_debug = "-debug";
   private static final String OPTION_reweavable = "-Xreweavable";
   private static final String OPTION_noinline = "-Xnoinline";
   private static final String OPTION_addSerialVersionUID = "-XaddSerialVersionUID";
   private static final String OPTION_hasMember = "-XhasMember";
   private static final String OPTION_pinpoint = "-Xdev:pinpoint";
   private static final String OPTION_showWeaveInfo = "-showWeaveInfo";
   private static final String OPTIONVALUED_messageHandler = "-XmessageHandlerClass:";
   private static final String OPTIONVALUED_Xlintfile = "-Xlintfile:";
   private static final String OPTIONVALUED_Xlint = "-Xlint:";
   private static final String OPTIONVALUED_joinpoints = "-Xjoinpoints:";
   private static final String OPTIONVALUED_Xset = "-Xset:";
   private static final String OPTION_timers = "-timers";
   private static final String OPTIONVALUED_loadersToSkip = "-loadersToSkip:";

   public static WeaverOption parse(String options, ClassLoader laoder, IMessageHandler imh) {
      WeaverOption weaverOption = new WeaverOption(imh);
      if (LangUtil.isEmpty(options)) {
         return weaverOption;
      } else {
         List flags = LangUtil.anySplit(options, " ");
         Collections.reverse(flags);
         Iterator iterator = flags.iterator();

         String arg;
         String value;
         while(iterator.hasNext()) {
            arg = (String)iterator.next();
            if (arg.startsWith("-XmessageHandlerClass:") && arg.length() > "-XmessageHandlerClass:".length()) {
               value = arg.substring("-XmessageHandlerClass:".length()).trim();

               try {
                  Class handler = Class.forName(value, false, laoder);
                  weaverOption.messageHandler = (IMessageHandler)handler.newInstance();
               } catch (Throwable var9) {
                  weaverOption.messageHandler.handleMessage(new Message("Cannot instantiate message handler " + value, IMessage.ERROR, var9, (ISourceLocation)null));
               }
            }
         }

         iterator = flags.iterator();

         while(true) {
            while(iterator.hasNext()) {
               arg = (String)iterator.next();
               if (arg.equals("-1.5")) {
                  weaverOption.java5 = true;
               } else if (arg.equalsIgnoreCase("-XlazyTjp")) {
                  weaverOption.lazyTjp = true;
               } else if (arg.equalsIgnoreCase("-Xnoinline")) {
                  weaverOption.noInline = true;
               } else if (arg.equalsIgnoreCase("-XaddSerialVersionUID")) {
                  weaverOption.addSerialVersionUID = true;
               } else if (!arg.equalsIgnoreCase("-nowarn") && !arg.equalsIgnoreCase("-warn:none")) {
                  if (arg.equalsIgnoreCase("-proceedOnError")) {
                     weaverOption.proceedOnError = true;
                  } else if (arg.equalsIgnoreCase("-Xreweavable")) {
                     weaverOption.notReWeavable = false;
                  } else if (arg.equalsIgnoreCase("-showWeaveInfo")) {
                     weaverOption.showWeaveInfo = true;
                  } else if (arg.equalsIgnoreCase("-XhasMember")) {
                     weaverOption.hasMember = true;
                  } else if (arg.startsWith("-Xjoinpoints:")) {
                     if (arg.length() > "-Xjoinpoints:".length()) {
                        weaverOption.optionalJoinpoints = arg.substring("-Xjoinpoints:".length()).trim();
                     }
                  } else if (arg.equalsIgnoreCase("-verbose")) {
                     weaverOption.verbose = true;
                  } else if (arg.equalsIgnoreCase("-debug")) {
                     weaverOption.debug = true;
                  } else if (arg.equalsIgnoreCase("-Xdev:pinpoint")) {
                     weaverOption.pinpoint = true;
                  } else if (!arg.startsWith("-XmessageHandlerClass:")) {
                     if (arg.startsWith("-Xlintfile:")) {
                        if (arg.length() > "-Xlintfile:".length()) {
                           weaverOption.lintFile = arg.substring("-Xlintfile:".length()).trim();
                        }
                     } else if (arg.startsWith("-Xlint:")) {
                        if (arg.length() > "-Xlint:".length()) {
                           weaverOption.lint = arg.substring("-Xlint:".length()).trim();
                        }
                     } else if (arg.startsWith("-Xset:")) {
                        if (arg.length() > "-Xlint:".length()) {
                           weaverOption.xSet = arg.substring("-Xset:".length()).trim();
                        }
                     } else if (arg.equalsIgnoreCase("-timers")) {
                        weaverOption.timers = true;
                     } else if (arg.startsWith("-loadersToSkip:")) {
                        if (arg.length() > "-loadersToSkip:".length()) {
                           value = arg.substring("-loadersToSkip:".length()).trim();
                           weaverOption.loadersToSkip = value;
                        }
                     } else {
                        weaverOption.messageHandler.handleMessage(new Message("Cannot configure weaver with option '" + arg + "': unknown option", IMessage.WARNING, (Throwable)null, (ISourceLocation)null));
                     }
                  }
               } else {
                  weaverOption.noWarn = true;
               }
            }

            if (weaverOption.noWarn) {
               weaverOption.messageHandler.ignore(IMessage.WARNING);
            }

            if (weaverOption.verbose) {
               weaverOption.messageHandler.dontIgnore(IMessage.INFO);
            }

            if (weaverOption.debug) {
               weaverOption.messageHandler.dontIgnore(IMessage.DEBUG);
            }

            if (weaverOption.showWeaveInfo) {
               weaverOption.messageHandler.dontIgnore(IMessage.WEAVEINFO);
            }

            return weaverOption;
         }
      }
   }

   public static class WeaverOption {
      boolean java5;
      boolean lazyTjp;
      boolean hasMember;
      boolean timers = false;
      String optionalJoinpoints;
      boolean noWarn;
      boolean proceedOnError;
      boolean verbose;
      boolean debug;
      boolean notReWeavable = true;
      boolean noInline;
      boolean addSerialVersionUID;
      boolean showWeaveInfo;
      boolean pinpoint;
      IMessageHandler messageHandler;
      String lint;
      String lintFile;
      String xSet;
      String loadersToSkip;

      public WeaverOption(IMessageHandler imh) {
         this.messageHandler = imh;
      }
   }
}
