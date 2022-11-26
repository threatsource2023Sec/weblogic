package org.python.modules;

public class math_erf {
   static final double erx = 0.8450629115104675;
   static final double efx = 0.1283791670955126;
   static final double efx8 = 1.0270333367641007;
   static final double pp0 = 0.12837916709551256;
   static final double pp1 = -0.3250421072470015;
   static final double pp2 = -0.02848174957559851;
   static final double pp3 = -0.005770270296489442;
   static final double pp4 = -2.3763016656650163E-5;
   static final double qq1 = 0.39791722395915535;
   static final double qq2 = 0.0650222499887673;
   static final double qq3 = 0.005081306281875766;
   static final double qq4 = 1.3249473800432164E-4;
   static final double qq5 = -3.960228278775368E-6;
   static final double pa0 = -0.0023621185607526594;
   static final double pa1 = 0.41485611868374833;
   static final double pa2 = -0.3722078760357013;
   static final double pa3 = 0.31834661990116175;
   static final double pa4 = -0.11089469428239668;
   static final double pa5 = 0.035478304325618236;
   static final double pa6 = -0.002166375594868791;
   static final double qa1 = 0.10642088040084423;
   static final double qa2 = 0.540397917702171;
   static final double qa3 = 0.07182865441419627;
   static final double qa4 = 0.12617121980876164;
   static final double qa5 = 0.01363708391202905;
   static final double qa6 = 0.011984499846799107;
   static final double ra0 = -0.009864944034847148;
   static final double ra1 = -0.6938585727071818;
   static final double ra2 = -10.558626225323291;
   static final double ra3 = -62.375332450326006;
   static final double ra4 = -162.39666946257347;
   static final double ra5 = -184.60509290671104;
   static final double ra6 = -81.2874355063066;
   static final double ra7 = -9.814329344169145;
   static final double sa1 = 19.651271667439257;
   static final double sa2 = 137.65775414351904;
   static final double sa3 = 434.56587747522923;
   static final double sa4 = 645.3872717332679;
   static final double sa5 = 429.00814002756783;
   static final double sa6 = 108.63500554177944;
   static final double sa7 = 6.570249770319282;
   static final double sa8 = -0.0604244152148581;
   static final double rb0 = -0.0098649429247001;
   static final double rb1 = -0.799283237680523;
   static final double rb2 = -17.757954917754752;
   static final double rb3 = -160.63638485582192;
   static final double rb4 = -637.5664433683896;
   static final double rb5 = -1025.0951316110772;
   static final double rb6 = -483.5191916086514;
   static final double sb1 = 30.33806074348246;
   static final double sb2 = 325.7925129965739;
   static final double sb3 = 1536.729586084437;
   static final double sb4 = 3199.8582195085955;
   static final double sb5 = 2553.0504064331644;
   static final double sb6 = 474.52854120695537;
   static final double sb7 = -22.44095244658582;

   public static double erf(double x) {
      if (Double.isNaN(x)) {
         return x;
      } else if (Double.POSITIVE_INFINITY == x) {
         return 1.0;
      } else if (Double.NEGATIVE_INFINITY == x) {
         return -1.0;
      } else {
         double veryTiny = 2.8480945388892178E-306;
         double small = 3.725290298461914E-9;
         boolean sign = false;
         if (x < 0.0) {
            x = -x;
            sign = true;
         }

         double s;
         double R;
         double S;
         double z;
         if (x < 0.84375) {
            if (x < 3.725290298461914E-9) {
               if (x < 2.8480945388892178E-306) {
                  s = 0.125 * (8.0 * x + 1.0270333367641007 * x);
               } else {
                  s = x + 0.1283791670955126 * x;
               }
            } else {
               R = x * x;
               S = 0.12837916709551256 + R * (-0.3250421072470015 + R * (-0.02848174957559851 + R * (-0.005770270296489442 + R * -2.3763016656650163E-5)));
               double s = 1.0 + R * (0.39791722395915535 + R * (0.0650222499887673 + R * (0.005081306281875766 + R * (1.3249473800432164E-4 + R * -3.960228278775368E-6))));
               z = S / s;
               s = x + x * z;
            }

            return sign ? -s : s;
         } else if (x < 1.25) {
            s = x - 1.0;
            R = -0.0023621185607526594 + s * (0.41485611868374833 + s * (-0.3722078760357013 + s * (0.31834661990116175 + s * (-0.11089469428239668 + s * (0.035478304325618236 + s * -0.002166375594868791)))));
            S = 1.0 + s * (0.10642088040084423 + s * (0.540397917702171 + s * (0.07182865441419627 + s * (0.12617121980876164 + s * (0.01363708391202905 + s * 0.011984499846799107)))));
            return sign ? -0.8450629115104675 - R / S : 0.8450629115104675 + R / S;
         } else if (x >= 6.0) {
            return sign ? -1.0 : 1.0;
         } else {
            s = 1.0 / (x * x);
            if (x < 2.857142857142857) {
               R = -0.009864944034847148 + s * (-0.6938585727071818 + s * (-10.558626225323291 + s * (-62.375332450326006 + s * (-162.39666946257347 + s * (-184.60509290671104 + s * (-81.2874355063066 + s * -9.814329344169145))))));
               S = 1.0 + s * (19.651271667439257 + s * (137.65775414351904 + s * (434.56587747522923 + s * (645.3872717332679 + s * (429.00814002756783 + s * (108.63500554177944 + s * (6.570249770319282 + s * -0.0604244152148581)))))));
            } else {
               R = -0.0098649429247001 + s * (-0.799283237680523 + s * (-17.757954917754752 + s * (-160.63638485582192 + s * (-637.5664433683896 + s * (-1025.0951316110772 + s * -483.5191916086514)))));
               S = 1.0 + s * (30.33806074348246 + s * (325.7925129965739 + s * (1536.729586084437 + s * (3199.8582195085955 + s * (2553.0504064331644 + s * (474.52854120695537 + s * -22.44095244658582))))));
            }

            long t20 = Double.doubleToLongBits(x) & -4294967296L;
            z = Double.longBitsToDouble(t20);
            double r = Math.exp(-z * z - 0.5625) * Math.exp((z - x) * (z + x) + R / S);
            return sign ? r / x - 1.0 : 1.0 - r / x;
         }
      }
   }

   public static double erfc(double x) {
      if (Double.isNaN(x)) {
         return x;
      } else if (Double.POSITIVE_INFINITY == x) {
         return 0.0;
      } else if (Double.NEGATIVE_INFINITY == x) {
         return 2.0;
      } else {
         double tiny = 5.9604644775390625E-8;
         boolean sign = false;
         if (x < 0.0) {
            x = -x;
            sign = true;
         }

         double s;
         double R;
         double S;
         double z;
         if (x < 0.84375) {
            if (x < 5.9604644775390625E-8) {
               s = x;
            } else {
               R = x * x;
               S = 0.12837916709551256 + R * (-0.3250421072470015 + R * (-0.02848174957559851 + R * (-0.005770270296489442 + R * -2.3763016656650163E-5)));
               double s = 1.0 + R * (0.39791722395915535 + R * (0.0650222499887673 + R * (0.005081306281875766 + R * (1.3249473800432164E-4 + R * -3.960228278775368E-6))));
               z = S / s;
               if (x < 0.25) {
                  s = x + x * z;
               } else {
                  s = 0.5 + x * z + (x - 0.5);
               }
            }

            return sign ? 1.0 + s : 1.0 - s;
         } else if (x < 1.25) {
            s = x - 1.0;
            R = -0.0023621185607526594 + s * (0.41485611868374833 + s * (-0.3722078760357013 + s * (0.31834661990116175 + s * (-0.11089469428239668 + s * (0.035478304325618236 + s * -0.002166375594868791)))));
            S = 1.0 + s * (0.10642088040084423 + s * (0.540397917702171 + s * (0.07182865441419627 + s * (0.12617121980876164 + s * (0.01363708391202905 + s * 0.011984499846799107)))));
            return sign ? 1.8450629115104675 + R / S : 0.15493708848953247 - R / S;
         } else if (x < 28.0) {
            s = 1.0 / (x * x);
            if (x < 2.857142857142857) {
               R = -0.009864944034847148 + s * (-0.6938585727071818 + s * (-10.558626225323291 + s * (-62.375332450326006 + s * (-162.39666946257347 + s * (-184.60509290671104 + s * (-81.2874355063066 + s * -9.814329344169145))))));
               S = 1.0 + s * (19.651271667439257 + s * (137.65775414351904 + s * (434.56587747522923 + s * (645.3872717332679 + s * (429.00814002756783 + s * (108.63500554177944 + s * (6.570249770319282 + s * -0.0604244152148581)))))));
            } else {
               if (sign && x > 6.0) {
                  return 2.0;
               }

               R = -0.0098649429247001 + s * (-0.799283237680523 + s * (-17.757954917754752 + s * (-160.63638485582192 + s * (-637.5664433683896 + s * (-1025.0951316110772 + s * -483.5191916086514)))));
               S = 1.0 + s * (30.33806074348246 + s * (325.7925129965739 + s * (1536.729586084437 + s * (3199.8582195085955 + s * (2553.0504064331644 + s * (474.52854120695537 + s * -22.44095244658582))))));
            }

            long t20 = Double.doubleToLongBits(x) & -4294967296L;
            z = Double.longBitsToDouble(t20);
            double r = Math.exp(-z * z - 0.5625) * Math.exp((z - x) * (z + x) + R / S);
            return sign ? 2.0 - r / x : r / x;
         } else {
            return sign ? 2.0 : 0.0;
         }
      }
   }
}
