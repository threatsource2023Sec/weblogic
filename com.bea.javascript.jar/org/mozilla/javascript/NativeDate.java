package org.mozilla.javascript;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class NativeDate extends IdScriptable {
   private static final double HalfTimeDomain = 8.64E15;
   private static final double HoursPerDay = 24.0;
   private static final double MinutesPerHour = 60.0;
   private static final double SecondsPerMinute = 60.0;
   private static final double msPerSecond = 1000.0;
   private static final double MinutesPerDay = 1440.0;
   private static final double SecondsPerDay = 86400.0;
   private static final double SecondsPerHour = 3600.0;
   private static final double msPerDay = 8.64E7;
   private static final double msPerHour = 3600000.0;
   private static final double msPerMinute = 60000.0;
   private static final boolean TZO_WORKAROUND = false;
   private static final int MAXARGS = 7;
   private static String[] wtb = new String[]{"am", "pm", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december", "gmt", "ut", "utc", "est", "edt", "cst", "cdt", "mst", "mdt", "pst", "pdt"};
   private static int[] ttb = new int[]{-1, -2, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 10000, 10000, 10000, 10300, 10240, 10360, 10300, 10420, 10360, 10480, 10420};
   private static final int FORMATSPEC_FULL = 0;
   private static final int FORMATSPEC_DATE = 1;
   private static final int FORMATSPEC_TIME = 2;
   private static String jsFunction_NaN_date_str = "Invalid Date";
   private static String[] days = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
   private static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
   private static final int ConstructorId_UTC = -2;
   private static final int ConstructorId_parse = -1;
   private static final int Id_constructor = 1;
   private static final int Id_toString = 2;
   private static final int Id_toTimeString = 3;
   private static final int Id_toDateString = 4;
   private static final int Id_toLocaleString = 5;
   private static final int Id_toLocaleTimeString = 6;
   private static final int Id_toLocaleDateString = 7;
   private static final int Id_toUTCString = 8;
   private static final int Id_valueOf = 9;
   private static final int Id_getTime = 10;
   private static final int Id_getYear = 11;
   private static final int Id_getFullYear = 12;
   private static final int Id_getUTCFullYear = 13;
   private static final int Id_getMonth = 14;
   private static final int Id_getUTCMonth = 15;
   private static final int Id_getDate = 16;
   private static final int Id_getUTCDate = 17;
   private static final int Id_getDay = 18;
   private static final int Id_getUTCDay = 19;
   private static final int Id_getHours = 20;
   private static final int Id_getUTCHours = 21;
   private static final int Id_getMinutes = 22;
   private static final int Id_getUTCMinutes = 23;
   private static final int Id_getSeconds = 24;
   private static final int Id_getUTCSeconds = 25;
   private static final int Id_getMilliseconds = 26;
   private static final int Id_getUTCMilliseconds = 27;
   private static final int Id_getTimezoneOffset = 28;
   private static final int Id_setTime = 29;
   private static final int Id_setMilliseconds = 30;
   private static final int Id_setUTCMilliseconds = 31;
   private static final int Id_setSeconds = 32;
   private static final int Id_setUTCSeconds = 33;
   private static final int Id_setMinutes = 34;
   private static final int Id_setUTCMinutes = 35;
   private static final int Id_setHours = 36;
   private static final int Id_setUTCHours = 37;
   private static final int Id_setDate = 38;
   private static final int Id_setUTCDate = 39;
   private static final int Id_setMonth = 40;
   private static final int Id_setUTCMonth = 41;
   private static final int Id_setFullYear = 42;
   private static final int Id_setUTCFullYear = 43;
   private static final int Id_setYear = 44;
   private static final int MAX_PROTOTYPE_ID = 44;
   private static final int Id_toGMTString = 8;
   private static TimeZone thisTimeZone;
   private static double LocalTZA;
   private static DateFormat timeZoneFormatter;
   private static DateFormat localeDateTimeFormatter;
   private static DateFormat localeDateFormatter;
   private static DateFormat localeTimeFormatter;
   private double date;
   private boolean prototypeFlag;

   public NativeDate() {
      if (thisTimeZone == null) {
         thisTimeZone = TimeZone.getDefault();
         LocalTZA = (double)thisTimeZone.getRawOffset();
      }

   }

   private static int DateFromTime(double var0) {
      int var2 = DayWithinYear(var0);
      int var4 = 30;
      if (var2 <= 30) {
         return var2 + 1;
      } else {
         int var3 = var4;
         if (InLeapYear(var0)) {
            var4 += 29;
         } else {
            var4 += 28;
         }

         if (var2 <= var4) {
            return var2 - var3;
         } else {
            var3 = var4;
            var4 += 31;
            if (var2 <= var4) {
               return var2 - var3;
            } else {
               var3 = var4;
               var4 += 30;
               if (var2 <= var4) {
                  return var2 - var3;
               } else {
                  var3 = var4;
                  var4 += 31;
                  if (var2 <= var4) {
                     return var2 - var3;
                  } else {
                     var3 = var4;
                     var4 += 30;
                     if (var2 <= var4) {
                        return var2 - var3;
                     } else {
                        var3 = var4;
                        var4 += 31;
                        if (var2 <= var4) {
                           return var2 - var3;
                        } else {
                           var3 = var4;
                           var4 += 31;
                           if (var2 <= var4) {
                              return var2 - var3;
                           } else {
                              var3 = var4;
                              var4 += 30;
                              if (var2 <= var4) {
                                 return var2 - var3;
                              } else {
                                 var3 = var4;
                                 var4 += 31;
                                 if (var2 <= var4) {
                                    return var2 - var3;
                                 } else {
                                    var3 = var4;
                                    var4 += 30;
                                    return var2 <= var4 ? var2 - var3 : var2 - var4;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static double Day(double var0) {
      return Math.floor(var0 / 8.64E7);
   }

   private static double DayFromMonth(int var0, boolean var1) {
      int var2 = var0 * 30;
      if (var0 >= 7) {
         var2 += var0 / 2 - 1;
      } else if (var0 >= 2) {
         var2 += (var0 - 1) / 2 - 1;
      } else {
         var2 += var0;
      }

      if (var1 && var0 >= 2) {
         ++var2;
      }

      return (double)var2;
   }

   private static double DayFromYear(double var0) {
      return 365.0 * (var0 - 1970.0) + Math.floor((var0 - 1969.0) / 4.0) - Math.floor((var0 - 1901.0) / 100.0) + Math.floor((var0 - 1601.0) / 400.0);
   }

   private static int DayWithinYear(double var0) {
      int var2 = YearFromTime(var0);
      return (int)(Day(var0) - DayFromYear((double)var2));
   }

   private static double DaylightSavingTA(double var0) {
      Date var2 = new Date((long)var0);
      return thisTimeZone.inDaylightTime(var2) ? 3600000.0 : 0.0;
   }

   private static int DaysInYear(int var0) {
      return var0 % 4 != 0 || var0 % 100 == 0 && var0 % 400 != 0 ? 365 : 366;
   }

   private static int HourFromTime(double var0) {
      double var2 = Math.floor(var0 / 3600000.0) % 24.0;
      if (var2 < 0.0) {
         var2 += 24.0;
      }

      return (int)var2;
   }

   private static boolean InLeapYear(double var0) {
      return DaysInYear(YearFromTime(var0)) == 366;
   }

   private static double LocalTime(double var0) {
      return var0 + LocalTZA + DaylightSavingTA(var0);
   }

   private static double MakeDate(double var0, double var2) {
      return var0 * 8.64E7 + var2;
   }

   private static double MakeDay(double var0, double var2, double var4) {
      var0 += Math.floor(var2 / 12.0);
      var2 %= 12.0;
      if (var2 < 0.0) {
         var2 += 12.0;
      }

      boolean var8 = DaysInYear((int)var0) == 366;
      double var9 = Math.floor(TimeFromYear(var0) / 8.64E7);
      double var11 = DayFromMonth((int)var2, var8);
      double var6 = var9 + var11 + var4 - 1.0;
      return var6;
   }

   private static double MakeTime(double var0, double var2, double var4, double var6) {
      return ((var0 * 60.0 + var2) * 60.0 + var4) * 1000.0 + var6;
   }

   private static int MinFromTime(double var0) {
      double var2 = Math.floor(var0 / 60000.0) % 60.0;
      if (var2 < 0.0) {
         var2 += 60.0;
      }

      return (int)var2;
   }

   private static int MonthFromTime(double var0) {
      int var2 = DayWithinYear(var0);
      int var3 = 31;
      if (var2 < 31) {
         return 0;
      } else {
         if (InLeapYear(var0)) {
            var3 += 29;
         } else {
            var3 += 28;
         }

         if (var2 < var3) {
            return 1;
         } else {
            var3 += 31;
            if (var2 < var3) {
               return 2;
            } else {
               var3 += 30;
               if (var2 < var3) {
                  return 3;
               } else {
                  var3 += 31;
                  if (var2 < var3) {
                     return 4;
                  } else {
                     var3 += 30;
                     if (var2 < var3) {
                        return 5;
                     } else {
                        var3 += 31;
                        if (var2 < var3) {
                           return 6;
                        } else {
                           var3 += 31;
                           if (var2 < var3) {
                              return 7;
                           } else {
                              var3 += 30;
                              if (var2 < var3) {
                                 return 8;
                              } else {
                                 var3 += 31;
                                 if (var2 < var3) {
                                    return 9;
                                 } else {
                                    var3 += 30;
                                    return var2 < var3 ? 10 : 11;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static double Now() {
      return (double)System.currentTimeMillis();
   }

   private static int SecFromTime(double var0) {
      double var2 = Math.floor(var0 / 1000.0) % 60.0;
      if (var2 < 0.0) {
         var2 += 60.0;
      }

      return (int)var2;
   }

   private static double TimeClip(double var0) {
      if (var0 == var0 && var0 != Double.POSITIVE_INFINITY && var0 != Double.NEGATIVE_INFINITY && !(Math.abs(var0) > 8.64E15)) {
         return var0 > 0.0 ? Math.floor(var0 + 0.0) : Math.ceil(var0 + 0.0);
      } else {
         return ScriptRuntime.NaN;
      }
   }

   private static double TimeFromYear(double var0) {
      return DayFromYear(var0) * 8.64E7;
   }

   private static double TimeWithinDay(double var0) {
      double var2 = var0 % 8.64E7;
      if (var2 < 0.0) {
         var2 += 8.64E7;
      }

      return var2;
   }

   private static int WeekDay(double var0) {
      double var2 = Day(var0) + 4.0;
      var2 %= 7.0;
      if (var2 < 0.0) {
         var2 += 7.0;
      }

      return (int)var2;
   }

   private static int YearFromTime(double var0) {
      int var2 = (int)Math.floor(var0 / 8.64E7 / 366.0) + 1970;
      int var3 = (int)Math.floor(var0 / 8.64E7 / 365.0) + 1970;
      int var5;
      if (var3 < var2) {
         var5 = var2;
         var2 = var3;
         var3 = var5;
      }

      while(var3 > var2) {
         int var4 = (var3 + var2) / 2;
         if (TimeFromYear((double)var4) > var0) {
            var3 = var4 - 1;
         } else if (TimeFromYear((double)var4) <= var0) {
            var5 = var4 + 1;
            if (TimeFromYear((double)var5) > var0) {
               return var4;
            }

            var2 = var4 + 1;
         }
      }

      return var2;
   }

   private static String date_format(double var0, int var2) {
      if (var0 != var0) {
         return jsFunction_NaN_date_str;
      } else {
         StringBuffer var3 = new StringBuffer(60);
         double var4 = LocalTime(var0);
         int var6 = (int)Math.floor((LocalTZA + DaylightSavingTA(var0)) / 60000.0);
         int var7 = var6 / 60 * 100 + var6 % 60;
         String var8 = Integer.toString(DateFromTime(var4));
         String var9 = Integer.toString(HourFromTime(var4));
         String var10 = Integer.toString(MinFromTime(var4));
         String var11 = Integer.toString(SecFromTime(var4));
         String var12 = Integer.toString(var7 > 0 ? var7 : -var7);
         int var13 = YearFromTime(var4);
         String var14 = Integer.toString(var13 > 0 ? var13 : -var13);
         if (var2 != 2) {
            var3.append(days[WeekDay(var4)]);
            var3.append(' ');
            var3.append(months[MonthFromTime(var4)]);
            if (var8.length() == 1) {
               var3.append(" 0");
            } else {
               var3.append(' ');
            }

            var3.append(var8);
            var3.append(' ');
         }

         int var15;
         if (var2 != 1) {
            if (var9.length() == 1) {
               var3.append('0');
            }

            var3.append(var9);
            if (var10.length() == 1) {
               var3.append(":0");
            } else {
               var3.append(':');
            }

            var3.append(var10);
            if (var11.length() == 1) {
               var3.append(":0");
            } else {
               var3.append(':');
            }

            var3.append(var11);
            if (var7 > 0) {
               var3.append(" GMT+");
            } else {
               var3.append(" GMT-");
            }

            for(var15 = var12.length(); var15 < 4; ++var15) {
               var3.append('0');
            }

            var3.append(var12);
            if (timeZoneFormatter == null) {
               timeZoneFormatter = new SimpleDateFormat("zzz");
            }

            if (timeZoneFormatter != null) {
               var3.append(" (");
               Date var16 = new Date((long)var0);
               var3.append(timeZoneFormatter.format(var16));
               var3.append(')');
            }

            if (var2 != 2) {
               var3.append(' ');
            }
         }

         if (var2 != 2) {
            if (var13 < 0) {
               var3.append('-');
            }

            for(var15 = var14.length(); var15 < 4; ++var15) {
               var3.append('0');
            }

            var3.append(var14);
         }

         return var3.toString();
      }
   }

   private static double date_msecFromDate(double var0, double var2, double var4, double var6, double var8, double var10, double var12) {
      double var14 = MakeDay(var0, var2, var4);
      double var16 = MakeTime(var6, var8, var10, var12);
      double var18 = MakeDate(var14, var16);
      return var18;
   }

   private static double date_parseString(String var0) {
      int var3 = -1;
      int var4 = -1;
      int var5 = -1;
      int var6 = -1;
      int var7 = -1;
      int var8 = -1;
      boolean var9 = false;
      boolean var10 = false;
      int var11 = 0;
      boolean var12 = true;
      double var13 = -1.0;
      char var15 = 0;
      boolean var16 = false;
      boolean var17 = false;
      if (var0 == null) {
         return ScriptRuntime.NaN;
      } else {
         int var24 = var0.length();

         while(true) {
            while(true) {
               while(true) {
                  while(true) {
                     while(var11 < var24) {
                        char var21 = var0.charAt(var11);
                        ++var11;
                        if (var21 > ' ' && var21 != ',' && var21 != '-') {
                           int var18;
                           if (var21 != '(') {
                              if (var21 < '0' || var21 > '9') {
                                 if (var21 != '/' && var21 != ':' && var21 != '+' && var21 != '-') {
                                    for(var18 = var11 - 1; var11 < var24; ++var11) {
                                       var21 = var0.charAt(var11);
                                       if ((var21 < 'A' || var21 > 'Z') && (var21 < 'a' || var21 > 'z')) {
                                          break;
                                       }
                                    }

                                    if (var11 <= var18 + 1) {
                                       return ScriptRuntime.NaN;
                                    }

                                    int var19 = wtb.length;

                                    while(true) {
                                       --var19;
                                       if (var19 < 0) {
                                          break;
                                       }

                                       if (date_regionMatches(wtb[var19], 0, var0, var18, var11 - var18)) {
                                          int var20 = ttb[var19];
                                          if (var20 != 0) {
                                             if (var20 < 0) {
                                                if (var6 > 12 || var6 < 0) {
                                                   return ScriptRuntime.NaN;
                                                }

                                                if (var20 == -1 && var6 == 12) {
                                                   var6 = 0;
                                                } else if (var20 == -2 && var6 != 12) {
                                                   var6 += 12;
                                                }
                                             } else if (var20 <= 13) {
                                                if (var4 >= 0) {
                                                   return ScriptRuntime.NaN;
                                                }

                                                var4 = var20 - 2;
                                             } else {
                                                var13 = (double)(var20 - 10000);
                                             }
                                          }
                                          break;
                                       }
                                    }

                                    if (var19 < 0) {
                                       return ScriptRuntime.NaN;
                                    }

                                    var15 = 0;
                                 } else {
                                    var15 = var21;
                                 }
                              } else {
                                 int var23;
                                 for(var23 = var21 - 48; var11 < var24 && (var21 = var0.charAt(var11)) >= '0' && var21 <= '9'; ++var11) {
                                    var23 = var23 * 10 + var21 - 48;
                                 }

                                 if (var15 != '+' && var15 != '-') {
                                    if (var23 < 70 && (var15 != '/' || var4 < 0 || var5 < 0 || var3 >= 0)) {
                                       if (var21 == ':') {
                                          if (var6 < 0) {
                                             var6 = var23;
                                          } else {
                                             if (var7 >= 0) {
                                                return ScriptRuntime.NaN;
                                             }

                                             var7 = var23;
                                          }
                                       } else if (var21 == '/') {
                                          if (var4 < 0) {
                                             var4 = var23 - 1;
                                          } else {
                                             if (var5 >= 0) {
                                                return ScriptRuntime.NaN;
                                             }

                                             var5 = var23;
                                          }
                                       } else {
                                          if (var11 < var24 && var21 != ',' && var21 > ' ' && var21 != '-') {
                                             return ScriptRuntime.NaN;
                                          }

                                          if (var17 && var23 < 60) {
                                             if (var13 < 0.0) {
                                                var13 -= (double)var23;
                                             } else {
                                                var13 += (double)var23;
                                             }
                                          } else if (var6 >= 0 && var7 < 0) {
                                             var7 = var23;
                                          } else if (var7 >= 0 && var8 < 0) {
                                             var8 = var23;
                                          } else {
                                             if (var5 >= 0) {
                                                return ScriptRuntime.NaN;
                                             }

                                             var5 = var23;
                                          }
                                       }
                                    } else {
                                       if (var3 >= 0) {
                                          return ScriptRuntime.NaN;
                                       }

                                       if (var21 > ' ' && var21 != ',' && var21 != '/' && var11 < var24) {
                                          return ScriptRuntime.NaN;
                                       }

                                       var3 = var23 < 100 ? var23 + 1900 : var23;
                                    }
                                 } else {
                                    var17 = true;
                                    if (var23 < 24) {
                                       var23 *= 60;
                                    } else {
                                       var23 = var23 % 100 + var23 / 100 * 60;
                                    }

                                    if (var15 == '+') {
                                       var23 = -var23;
                                    }

                                    if (var13 != 0.0 && var13 != -1.0) {
                                       return ScriptRuntime.NaN;
                                    }

                                    var13 = (double)var23;
                                 }

                                 var15 = 0;
                              }
                           } else {
                              var18 = 1;

                              while(var11 < var24) {
                                 var21 = var0.charAt(var11);
                                 ++var11;
                                 if (var21 == '(') {
                                    ++var18;
                                 } else if (var21 == ')') {
                                    --var18;
                                    if (var18 <= 0) {
                                       break;
                                    }
                                 }
                              }
                           }
                        } else if (var11 < var24) {
                           char var22 = var0.charAt(var11);
                           if (var21 == '-' && var22 >= '0' && var22 <= '9') {
                              var15 = var21;
                           }
                        }
                     }

                     if (var3 >= 0 && var4 >= 0 && var5 >= 0) {
                        if (var8 < 0) {
                           var8 = 0;
                        }

                        if (var7 < 0) {
                           var7 = 0;
                        }

                        if (var6 < 0) {
                           var6 = 0;
                        }

                        if (var13 == -1.0) {
                           double var25 = date_msecFromDate((double)var3, (double)var4, (double)var5, (double)var6, (double)var7, (double)var8, 0.0);
                           return internalUTC(var25);
                        }

                        double var1 = date_msecFromDate((double)var3, (double)var4, (double)var5, (double)var6, (double)var7, (double)var8, 0.0);
                        var1 += var13 * 60000.0;
                        return var1;
                     }

                     return ScriptRuntime.NaN;
                  }
               }
            }
         }
      }
   }

   private static boolean date_regionMatches(String var0, int var1, String var2, int var3, int var4) {
      boolean var5 = false;
      int var6 = var0.length();

      for(int var7 = var2.length(); var4 > 0 && var1 < var6 && var3 < var7 && Character.toLowerCase(var0.charAt(var1)) == Character.toLowerCase(var2.charAt(var3)); --var4) {
         ++var1;
         ++var3;
      }

      if (var4 == 0) {
         var5 = true;
      }

      return var5;
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.prototypeFlag) {
         double var7;
         switch (var1) {
            case -2:
               return this.wrap_double(jsStaticFunction_UTC(var6));
            case -1:
               return this.wrap_double(jsStaticFunction_parse(ScriptRuntime.toString(var6, 0)));
            case 0:
            default:
               break;
            case 1:
               return jsConstructor(var6, var5 == null);
            case 2:
               var7 = this.realThis(var5, var2, true).date;
               return date_format(var7, 0);
            case 3:
               var7 = this.realThis(var5, var2, true).date;
               return date_format(var7, 2);
            case 4:
               var7 = this.realThis(var5, var2, true).date;
               return date_format(var7, 1);
            case 5:
               var7 = this.realThis(var5, var2, true).date;
               return jsFunction_toLocaleString(var7);
            case 6:
               var7 = this.realThis(var5, var2, true).date;
               return jsFunction_toLocaleTimeString(var7);
            case 7:
               var7 = this.realThis(var5, var2, true).date;
               return jsFunction_toLocaleDateString(var7);
            case 8:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  return jsFunction_toUTCString(var7);
               }

               return jsFunction_NaN_date_str;
            case 9:
               return this.wrap_double(this.realThis(var5, var2, true).date);
            case 10:
               return this.wrap_double(this.realThis(var5, var2, true).date);
            case 11:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = jsFunction_getYear(var3, var7);
               }

               return this.wrap_double(var7);
            case 12:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)YearFromTime(LocalTime(var7));
               }

               return this.wrap_double(var7);
            case 13:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)YearFromTime(var7);
               }

               return this.wrap_double(var7);
            case 14:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)MonthFromTime(LocalTime(var7));
               }

               return this.wrap_double(var7);
            case 15:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)MonthFromTime(var7);
               }

               return this.wrap_double(var7);
            case 16:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)DateFromTime(LocalTime(var7));
               }

               return this.wrap_double(var7);
            case 17:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)DateFromTime(var7);
               }

               return this.wrap_double(var7);
            case 18:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)WeekDay(LocalTime(var7));
               }

               return this.wrap_double(var7);
            case 19:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)WeekDay(var7);
               }

               return this.wrap_double(var7);
            case 20:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)HourFromTime(LocalTime(var7));
               }

               return this.wrap_double(var7);
            case 21:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)HourFromTime(var7);
               }

               return this.wrap_double(var7);
            case 22:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)MinFromTime(LocalTime(var7));
               }

               return this.wrap_double(var7);
            case 23:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)MinFromTime(var7);
               }

               return this.wrap_double(var7);
            case 24:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)SecFromTime(LocalTime(var7));
               }

               return this.wrap_double(var7);
            case 25:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)SecFromTime(var7);
               }

               return this.wrap_double(var7);
            case 26:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)msFromTime(LocalTime(var7));
               }

               return this.wrap_double(var7);
            case 27:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = (double)msFromTime(var7);
               }

               return this.wrap_double(var7);
            case 28:
               var7 = this.realThis(var5, var2, true).date;
               if (var7 == var7) {
                  var7 = jsFunction_getTimezoneOffset(var7);
               }

               return this.wrap_double(var7);
            case 29:
               return this.wrap_double(this.realThis(var5, var2, true).jsFunction_setTime(ScriptRuntime.toNumber(var6, 0)));
            case 30:
               return this.wrap_double(this.realThis(var5, var2, false).makeTime(var6, 1, true));
            case 31:
               return this.wrap_double(this.realThis(var5, var2, false).makeTime(var6, 1, false));
            case 32:
               return this.wrap_double(this.realThis(var5, var2, false).makeTime(var6, 2, true));
            case 33:
               return this.wrap_double(this.realThis(var5, var2, false).makeTime(var6, 2, false));
            case 34:
               return this.wrap_double(this.realThis(var5, var2, false).makeTime(var6, 3, true));
            case 35:
               return this.wrap_double(this.realThis(var5, var2, false).makeTime(var6, 3, false));
            case 36:
               return this.wrap_double(this.realThis(var5, var2, false).makeTime(var6, 4, true));
            case 37:
               return this.wrap_double(this.realThis(var5, var2, false).makeTime(var6, 4, false));
            case 38:
               return this.wrap_double(this.realThis(var5, var2, false).makeDate(var6, 1, true));
            case 39:
               return this.wrap_double(this.realThis(var5, var2, false).makeDate(var6, 1, false));
            case 40:
               return this.wrap_double(this.realThis(var5, var2, false).makeDate(var6, 2, true));
            case 41:
               return this.wrap_double(this.realThis(var5, var2, false).makeDate(var6, 2, false));
            case 42:
               return this.wrap_double(this.realThis(var5, var2, false).makeDate(var6, 3, true));
            case 43:
               return this.wrap_double(this.realThis(var5, var2, false).makeDate(var6, 3, false));
            case 44:
               return this.wrap_double(this.realThis(var5, var2, false).jsFunction_setYear(ScriptRuntime.toNumber(var6, 0)));
         }
      }

      return super.execMethod(var1, var2, var3, var4, var5, var6);
   }

   protected void fillConstructorProperties(Context var1, IdFunction var2, boolean var3) {
      this.addIdFunctionProperty(var2, -2, var3);
      this.addIdFunctionProperty(var2, -1, var3);
      super.fillConstructorProperties(var1, var2, var3);
   }

   public String getClassName() {
      return "Date";
   }

   public Object getDefaultValue(Class var1) {
      if (var1 == null) {
         var1 = ScriptRuntime.StringClass;
      }

      return super.getDefaultValue(var1);
   }

   protected String getIdName(int var1) {
      if (this.prototypeFlag) {
         switch (var1) {
            case -2:
               return "UTC";
            case -1:
               return "parse";
            case 0:
            default:
               break;
            case 1:
               return "constructor";
            case 2:
               return "toString";
            case 3:
               return "toTimeString";
            case 4:
               return "toDateString";
            case 5:
               return "toLocaleString";
            case 6:
               return "toLocaleTimeString";
            case 7:
               return "toLocaleDateString";
            case 8:
               return "toUTCString";
            case 9:
               return "valueOf";
            case 10:
               return "getTime";
            case 11:
               return "getYear";
            case 12:
               return "getFullYear";
            case 13:
               return "getUTCFullYear";
            case 14:
               return "getMonth";
            case 15:
               return "getUTCMonth";
            case 16:
               return "getDate";
            case 17:
               return "getUTCDate";
            case 18:
               return "getDay";
            case 19:
               return "getUTCDay";
            case 20:
               return "getHours";
            case 21:
               return "getUTCHours";
            case 22:
               return "getMinutes";
            case 23:
               return "getUTCMinutes";
            case 24:
               return "getSeconds";
            case 25:
               return "getUTCSeconds";
            case 26:
               return "getMilliseconds";
            case 27:
               return "getUTCMilliseconds";
            case 28:
               return "getTimezoneOffset";
            case 29:
               return "setTime";
            case 30:
               return "setMilliseconds";
            case 31:
               return "setUTCMilliseconds";
            case 32:
               return "setSeconds";
            case 33:
               return "setUTCSeconds";
            case 34:
               return "setMinutes";
            case 35:
               return "setUTCMinutes";
            case 36:
               return "setHours";
            case 37:
               return "setUTCHours";
            case 38:
               return "setDate";
            case 39:
               return "setUTCDate";
            case 40:
               return "setMonth";
            case 41:
               return "setUTCMonth";
            case 42:
               return "setFullYear";
            case 43:
               return "setUTCFullYear";
            case 44:
               return "setYear";
         }
      }

      return null;
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeDate var3 = new NativeDate();
      var3.prototypeFlag = true;
      var3.date = ScriptRuntime.NaN;
      var3.addAsPrototype(44, var0, var1, var2);
   }

   private static double internalUTC(double var0) {
      return var0 - LocalTZA - DaylightSavingTA(var0 - LocalTZA);
   }

   private static Object jsConstructor(Object[] var0, boolean var1) {
      if (!var1) {
         return date_format(Now(), 0);
      } else {
         NativeDate var2 = new NativeDate();
         if (var0.length == 0) {
            var2.date = Now();
            return var2;
         } else if (var0.length == 1) {
            if (var0[0] instanceof Scriptable) {
               var0[0] = ((Scriptable)var0[0]).getDefaultValue((Class)null);
            }

            double var11;
            if (!(var0[0] instanceof String)) {
               var11 = ScriptRuntime.toNumber(var0[0]);
            } else {
               String var12 = (String)var0[0];
               var11 = date_parseString(var12);
            }

            var2.date = TimeClip(var11);
            return var2;
         } else {
            double[] var3 = new double[7];

            for(int var4 = 0; var4 < 7; ++var4) {
               if (var4 < var0.length) {
                  double var5 = ScriptRuntime.toNumber(var0[var4]);
                  if (var5 != var5 || Double.isInfinite(var5)) {
                     var2.date = ScriptRuntime.NaN;
                     return var2;
                  }

                  var3[var4] = ScriptRuntime.toInteger(var0[var4]);
               } else {
                  var3[var4] = 0.0;
               }
            }

            if (var3[0] >= 0.0 && var3[0] <= 99.0) {
               var3[0] += 1900.0;
            }

            if (var3[2] < 1.0) {
               var3[2] = 1.0;
            }

            double var7 = MakeDay(var3[0], var3[1], var3[2]);
            double var9 = MakeTime(var3[3], var3[4], var3[5], var3[6]);
            var9 = MakeDate(var7, var9);
            var9 = internalUTC(var9);
            var2.date = TimeClip(var9);
            return var2;
         }
      }
   }

   private static double jsFunction_getTimezoneOffset(double var0) {
      return (var0 - LocalTime(var0)) / 60000.0;
   }

   private static double jsFunction_getYear(Context var0, double var1) {
      int var3 = YearFromTime(LocalTime(var1));
      if (var0.hasFeature(1)) {
         if (var3 >= 1900 && var3 < 2000) {
            var3 -= 1900;
         }
      } else {
         var3 -= 1900;
      }

      return (double)var3;
   }

   private double jsFunction_setHours(Object[] var1) {
      return this.makeTime(var1, 4, true);
   }

   private double jsFunction_setTime(double var1) {
      this.date = TimeClip(var1);
      return this.date;
   }

   private double jsFunction_setUTCHours(Object[] var1) {
      return this.makeTime(var1, 4, false);
   }

   private double jsFunction_setYear(double var1) {
      if (var1 == var1 && !Double.isInfinite(var1)) {
         if (this.date != this.date) {
            this.date = 0.0;
         } else {
            this.date = LocalTime(this.date);
         }

         if (var1 >= 0.0 && var1 <= 99.0) {
            var1 += 1900.0;
         }

         double var3 = MakeDay(var1, (double)MonthFromTime(this.date), (double)DateFromTime(this.date));
         double var5 = MakeDate(var3, TimeWithinDay(this.date));
         var5 = internalUTC(var5);
         this.date = TimeClip(var5);
         return this.date;
      } else {
         this.date = ScriptRuntime.NaN;
         return this.date;
      }
   }

   private static String jsFunction_toLocaleDateString(double var0) {
      if (localeDateFormatter == null) {
         localeDateFormatter = DateFormat.getDateInstance(1);
      }

      return toLocale_helper(var0, localeDateFormatter);
   }

   private static String jsFunction_toLocaleString(double var0) {
      if (localeDateTimeFormatter == null) {
         localeDateTimeFormatter = DateFormat.getDateTimeInstance(1, 1);
      }

      return toLocale_helper(var0, localeDateTimeFormatter);
   }

   private static String jsFunction_toLocaleTimeString(double var0) {
      if (localeTimeFormatter == null) {
         localeTimeFormatter = DateFormat.getTimeInstance(1);
      }

      return toLocale_helper(var0, localeTimeFormatter);
   }

   private static String jsFunction_toUTCString(double var0) {
      StringBuffer var2 = new StringBuffer(60);
      String var3 = Integer.toString(DateFromTime(var0));
      String var4 = Integer.toString(HourFromTime(var0));
      String var5 = Integer.toString(MinFromTime(var0));
      String var6 = Integer.toString(SecFromTime(var0));
      int var7 = YearFromTime(var0);
      String var8 = Integer.toString(var7 > 0 ? var7 : -var7);
      var2.append(days[WeekDay(var0)]);
      var2.append(", ");
      if (var3.length() == 1) {
         var2.append('0');
      }

      var2.append(var3);
      var2.append(' ');
      var2.append(months[MonthFromTime(var0)]);
      if (var7 < 0) {
         var2.append(" -");
      } else {
         var2.append(' ');
      }

      for(int var9 = var8.length(); var9 < 4; ++var9) {
         var2.append('0');
      }

      var2.append(var8);
      if (var4.length() == 1) {
         var2.append(" 0");
      } else {
         var2.append(' ');
      }

      var2.append(var4);
      if (var5.length() == 1) {
         var2.append(":0");
      } else {
         var2.append(':');
      }

      var2.append(var5);
      if (var6.length() == 1) {
         var2.append(":0");
      } else {
         var2.append(':');
      }

      var2.append(var6);
      var2.append(" GMT");
      return var2.toString();
   }

   private static double jsStaticFunction_UTC(Object[] var0) {
      double[] var1 = new double[7];

      double var3;
      for(int var2 = 0; var2 < 7; ++var2) {
         if (var2 < var0.length) {
            var3 = ScriptRuntime.toNumber(var0[var2]);
            if (var3 != var3 || Double.isInfinite(var3)) {
               return ScriptRuntime.NaN;
            }

            var1[var2] = ScriptRuntime.toInteger(var0[var2]);
         } else {
            var1[var2] = 0.0;
         }
      }

      if (var1[0] >= 0.0 && var1[0] <= 99.0) {
         var1[0] += 1900.0;
      }

      if (var1[2] < 1.0) {
         var1[2] = 1.0;
      }

      var3 = date_msecFromDate(var1[0], var1[1], var1[2], var1[3], var1[4], var1[5], var1[6]);
      var3 = TimeClip(var3);
      return var3;
   }

   private static double jsStaticFunction_parse(String var0) {
      return date_parseString(var0);
   }

   private double makeDate(Object[] var1, int var2, boolean var3) {
      double[] var5 = new double[3];
      double var16 = this.date;
      if (var1.length == 0) {
         var1 = ScriptRuntime.padArguments(var1, 1);
      }

      int var4 = 0;

      while(true) {
         if (var4 < var1.length && var4 < var2) {
            var5[var4] = ScriptRuntime.toNumber(var1[var4]);
            if (var5[var4] == var5[var4] && !Double.isInfinite(var5[var4])) {
               var5[var4] = ScriptRuntime.toInteger(var5[var4]);
               ++var4;
               continue;
            }

            this.date = ScriptRuntime.NaN;
            return this.date;
         }

         double var12;
         if (var16 != var16) {
            if (var1.length < 3) {
               return ScriptRuntime.NaN;
            }

            var12 = 0.0;
         } else if (var3) {
            var12 = LocalTime(var16);
         } else {
            var12 = var16;
         }

         var4 = 0;
         int var18 = var1.length;
         double var6;
         if (var2 >= 3 && var4 < var18) {
            var6 = var5[var4++];
         } else {
            var6 = (double)YearFromTime(var12);
         }

         double var8;
         if (var2 >= 2 && var4 < var18) {
            var8 = var5[var4++];
         } else {
            var8 = (double)MonthFromTime(var12);
         }

         double var10;
         if (var2 >= 1 && var4 < var18) {
            var10 = var5[var4++];
         } else {
            var10 = (double)DateFromTime(var12);
         }

         var10 = MakeDay(var6, var8, var10);
         double var14 = MakeDate(var10, TimeWithinDay(var12));
         if (var3) {
            var14 = internalUTC(var14);
         }

         var16 = TimeClip(var14);
         this.date = var16;
         return var16;
      }
   }

   private double makeTime(Object[] var1, int var2, boolean var3) {
      double[] var5 = new double[4];
      double var20 = this.date;
      if (var20 != var20) {
         return var20;
      } else {
         if (var1.length == 0) {
            var1 = ScriptRuntime.padArguments(var1, 1);
         }

         int var4 = 0;

         while(true) {
            if (var4 < var1.length && var4 < var2) {
               var5[var4] = ScriptRuntime.toNumber(var1[var4]);
               if (var5[var4] == var5[var4] && !Double.isInfinite(var5[var4])) {
                  var5[var4] = ScriptRuntime.toInteger(var5[var4]);
                  ++var4;
                  continue;
               }

               this.date = ScriptRuntime.NaN;
               return this.date;
            }

            double var14;
            if (var3) {
               var14 = LocalTime(var20);
            } else {
               var14 = var20;
            }

            var4 = 0;
            int var22 = var1.length;
            double var6;
            if (var2 >= 4 && var4 < var22) {
               var6 = var5[var4++];
            } else {
               var6 = (double)HourFromTime(var14);
            }

            double var8;
            if (var2 >= 3 && var4 < var22) {
               var8 = var5[var4++];
            } else {
               var8 = (double)MinFromTime(var14);
            }

            double var10;
            if (var2 >= 2 && var4 < var22) {
               var10 = var5[var4++];
            } else {
               var10 = (double)SecFromTime(var14);
            }

            double var12;
            if (var2 >= 1 && var4 < var22) {
               var12 = var5[var4++];
            } else {
               var12 = (double)msFromTime(var14);
            }

            double var16 = MakeTime(var6, var8, var10, var12);
            double var18 = MakeDate(Day(var14), var16);
            if (var3) {
               var18 = internalUTC(var18);
            }

            var20 = TimeClip(var18);
            this.date = var20;
            return var20;
         }
      }
   }

   protected int mapNameToId(String var1) {
      if (!this.prototypeFlag) {
         return 0;
      } else {
         byte var2;
         String var3;
         var2 = 0;
         var3 = null;
         char var4;
         label165:
         switch (var1.length()) {
            case 6:
               var3 = "getDay";
               var2 = 18;
               break;
            case 7:
               switch (var1.charAt(3)) {
                  case 'D':
                     var4 = var1.charAt(0);
                     if (var4 == 'g') {
                        var3 = "getDate";
                        var2 = 16;
                     } else if (var4 == 's') {
                        var3 = "setDate";
                        var2 = 38;
                     }
                     break label165;
                  case 'T':
                     var4 = var1.charAt(0);
                     if (var4 == 'g') {
                        var3 = "getTime";
                        var2 = 10;
                     } else if (var4 == 's') {
                        var3 = "setTime";
                        var2 = 29;
                     }
                     break label165;
                  case 'Y':
                     var4 = var1.charAt(0);
                     if (var4 == 'g') {
                        var3 = "getYear";
                        var2 = 11;
                     } else if (var4 == 's') {
                        var3 = "setYear";
                        var2 = 44;
                     }
                     break label165;
                  case 'u':
                     var3 = "valueOf";
                     var2 = 9;
                  default:
                     break label165;
               }
            case 8:
               var4 = var1.charAt(0);
               if (var4 == 'g') {
                  var4 = var1.charAt(7);
                  if (var4 == 'h') {
                     var3 = "getMonth";
                     var2 = 14;
                  } else if (var4 == 's') {
                     var3 = "getHours";
                     var2 = 20;
                  }
               } else if (var4 == 's') {
                  var4 = var1.charAt(7);
                  if (var4 == 'h') {
                     var3 = "setMonth";
                     var2 = 40;
                  } else if (var4 == 's') {
                     var3 = "setHours";
                     var2 = 36;
                  }
               } else if (var4 == 't') {
                  var3 = "toString";
                  var2 = 2;
               }
               break;
            case 9:
               var3 = "getUTCDay";
               var2 = 19;
               break;
            case 10:
               var4 = var1.charAt(3);
               if (var4 == 'M') {
                  var4 = var1.charAt(0);
                  if (var4 == 'g') {
                     var3 = "getMinutes";
                     var2 = 22;
                  } else if (var4 == 's') {
                     var3 = "setMinutes";
                     var2 = 34;
                  }
               } else if (var4 == 'S') {
                  var4 = var1.charAt(0);
                  if (var4 == 'g') {
                     var3 = "getSeconds";
                     var2 = 24;
                  } else if (var4 == 's') {
                     var3 = "setSeconds";
                     var2 = 32;
                  }
               } else if (var4 == 'U') {
                  var4 = var1.charAt(0);
                  if (var4 == 'g') {
                     var3 = "getUTCDate";
                     var2 = 17;
                  } else if (var4 == 's') {
                     var3 = "setUTCDate";
                     var2 = 39;
                  }
               }
               break;
            case 11:
               switch (var1.charAt(3)) {
                  case 'F':
                     var4 = var1.charAt(0);
                     if (var4 == 'g') {
                        var3 = "getFullYear";
                        var2 = 12;
                     } else if (var4 == 's') {
                        var3 = "setFullYear";
                        var2 = 42;
                     }
                     break label165;
                  case 'M':
                     var3 = "toGMTString";
                     var2 = 8;
                     break label165;
                  case 'T':
                     var3 = "toUTCString";
                     var2 = 8;
                     break label165;
                  case 'U':
                     var4 = var1.charAt(0);
                     if (var4 == 'g') {
                        var4 = var1.charAt(9);
                        if (var4 == 'r') {
                           var3 = "getUTCHours";
                           var2 = 21;
                        } else if (var4 == 't') {
                           var3 = "getUTCMonth";
                           var2 = 15;
                        }
                     } else if (var4 == 's') {
                        var4 = var1.charAt(9);
                        if (var4 == 'r') {
                           var3 = "setUTCHours";
                           var2 = 37;
                        } else if (var4 == 't') {
                           var3 = "setUTCMonth";
                           var2 = 41;
                        }
                     }
                     break label165;
                  case 's':
                     var3 = "constructor";
                     var2 = 1;
                  default:
                     break label165;
               }
            case 12:
               var4 = var1.charAt(2);
               if (var4 == 'D') {
                  var3 = "toDateString";
                  var2 = 4;
               } else if (var4 == 'T') {
                  var3 = "toTimeString";
                  var2 = 3;
               }
               break;
            case 13:
               var4 = var1.charAt(0);
               if (var4 == 'g') {
                  var4 = var1.charAt(6);
                  if (var4 == 'M') {
                     var3 = "getUTCMinutes";
                     var2 = 23;
                  } else if (var4 == 'S') {
                     var3 = "getUTCSeconds";
                     var2 = 25;
                  }
               } else if (var4 == 's') {
                  var4 = var1.charAt(6);
                  if (var4 == 'M') {
                     var3 = "setUTCMinutes";
                     var2 = 35;
                  } else if (var4 == 'S') {
                     var3 = "setUTCSeconds";
                     var2 = 33;
                  }
               }
               break;
            case 14:
               var4 = var1.charAt(0);
               if (var4 == 'g') {
                  var3 = "getUTCFullYear";
                  var2 = 13;
               } else if (var4 == 's') {
                  var3 = "setUTCFullYear";
                  var2 = 43;
               } else if (var4 == 't') {
                  var3 = "toLocaleString";
                  var2 = 5;
               }
               break;
            case 15:
               var4 = var1.charAt(0);
               if (var4 == 'g') {
                  var3 = "getMilliseconds";
                  var2 = 26;
               } else if (var4 == 's') {
                  var3 = "setMilliseconds";
                  var2 = 30;
               }
            case 16:
            default:
               break;
            case 17:
               var3 = "getTimezoneOffset";
               var2 = 28;
               break;
            case 18:
               var4 = var1.charAt(0);
               if (var4 == 'g') {
                  var3 = "getUTCMilliseconds";
                  var2 = 27;
               } else if (var4 == 's') {
                  var3 = "setUTCMilliseconds";
                  var2 = 31;
               } else if (var4 == 't') {
                  var4 = var1.charAt(8);
                  if (var4 == 'D') {
                     var3 = "toLocaleDateString";
                     var2 = 7;
                  } else if (var4 == 'T') {
                     var3 = "toLocaleTimeString";
                     var2 = 6;
                  }
               }
         }

         if (var3 != null && var3 != var1 && !var3.equals(var1)) {
            var2 = 0;
         }

         return var2;
      }
   }

   public int methodArity(int var1) {
      if (this.prototypeFlag) {
         switch (var1) {
            case -2:
               return 1;
            case -1:
               return 1;
            case 0:
            default:
               break;
            case 1:
               return 1;
            case 2:
               return 0;
            case 3:
               return 0;
            case 4:
               return 0;
            case 5:
               return 0;
            case 6:
               return 0;
            case 7:
               return 0;
            case 8:
               return 0;
            case 9:
               return 0;
            case 10:
               return 0;
            case 11:
               return 0;
            case 12:
               return 0;
            case 13:
               return 0;
            case 14:
               return 0;
            case 15:
               return 0;
            case 16:
               return 0;
            case 17:
               return 0;
            case 18:
               return 0;
            case 19:
               return 0;
            case 20:
               return 0;
            case 21:
               return 0;
            case 22:
               return 0;
            case 23:
               return 0;
            case 24:
               return 0;
            case 25:
               return 0;
            case 26:
               return 0;
            case 27:
               return 0;
            case 28:
               return 0;
            case 29:
               return 1;
            case 30:
               return 1;
            case 31:
               return 1;
            case 32:
               return 2;
            case 33:
               return 2;
            case 34:
               return 3;
            case 35:
               return 3;
            case 36:
               return 4;
            case 37:
               return 4;
            case 38:
               return 1;
            case 39:
               return 1;
            case 40:
               return 2;
            case 41:
               return 2;
            case 42:
               return 3;
            case 43:
               return 3;
            case 44:
               return 1;
         }
      }

      return super.methodArity(var1);
   }

   private static int msFromTime(double var0) {
      double var2 = var0 % 1000.0;
      if (var2 < 0.0) {
         var2 += 1000.0;
      }

      return (int)var2;
   }

   private NativeDate realThis(Scriptable var1, IdFunction var2, boolean var3) {
      while(!(var1 instanceof NativeDate)) {
         var1 = this.nextInstanceCheck(var1, var2, var3);
      }

      return (NativeDate)var1;
   }

   private static String toLocale_helper(double var0, DateFormat var2) {
      if (var0 != var0) {
         return jsFunction_NaN_date_str;
      } else {
         Date var3 = new Date((long)var0);
         return var2.format(var3);
      }
   }
}
