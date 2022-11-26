package javolution.lang;

public final class MathLib {
   private static long Seed = System.currentTimeMillis() + 8682522807148012L;
   public static final double E = Math.E;
   public static final double PI = Math.PI;
   public static final double HALF_PI = 1.5707963267948966;
   public static final double TWO_PI = 6.283185307179586;
   public static final double FOUR_PI = 12.566370614359172;
   public static final double PI_SQUARE = 9.869604401089358;
   public static final double LOG2 = 0.6931471805599453;
   public static final double LOG10 = 2.302585092994046;
   public static final double SQRT2 = 1.4142135623730951;
   public static final double NaN = Double.NaN;
   public static final double Infinity = Double.POSITIVE_INFINITY;
   private static double INV_LOG10 = 0.4342944819032518;
   private static final double INV_MAX_VALUE = 4.656612875245797E-10;
   private static final double[] POW_10_POS = new double[]{1.0, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, 1.0E7, 1.0E8, 1.0E9, 1.0E10, 1.0E11, 1.0E12, 1.0E13, 1.0E14, 1.0E15, 1.0E16, 1.0E17, 1.0E18, 1.0E19, 1.0E20, 1.0E21, 1.0E22, 9.999999999999999E22, 1.0E24, 1.0E25, 1.0E26, 1.0E27, 1.0E28, 1.0E29, 1.0E30, 1.0E31, 1.0E32, 1.0E33, 1.0E34, 1.0E35, 1.0E36, 1.0E37, 1.0E38, 1.0E39, 1.0E40, 1.0E41, 1.0E42, 1.0E43, 1.0E44, 1.0E45, 1.0E46, 1.0E47, 1.0E48, 1.0E49, 1.0E50, 1.0E51, 1.0E52, 1.0E53, 1.0E54, 1.0E55, 1.0E56, 1.0E57, 1.0E58, 1.0E59, 1.0E60, 1.0E61, 1.0E62, 1.0E63, 1.0E64, 1.0E65, 1.0E66, 1.0E67, 1.0E68, 1.0E69, 1.0E70, 1.0E71, 1.0E72, 1.0E73, 1.0E74, 1.0E75, 1.0E76, 1.0E77, 1.0E78, 1.0E79, 1.0E80, 1.0E81, 1.0E82, 1.0E83, 1.0E84, 1.0E85, 1.0E86, 1.0E87, 1.0E88, 1.0E89, 1.0E90, 1.0E91, 1.0E92, 1.0E93, 1.0E94, 1.0E95, 1.0E96, 1.0E97, 1.0E98, 1.0E99, 1.0E100, 1.0E101, 1.0E102, 1.0E103, 1.0E104, 1.0E105, 1.0E106, 1.0E107, 1.0E108, 1.0E109, 1.0E110, 1.0E111, 1.0E112, 1.0E113, 1.0E114, 1.0E115, 1.0E116, 1.0E117, 1.0E118, 1.0E119, 1.0E120, 1.0E121, 1.0E122, 1.0E123, 1.0E124, 1.0E125, 1.0E126, 1.0E127, 1.0E128, 1.0E129, 1.0E130, 1.0E131, 1.0E132, 1.0E133, 1.0E134, 1.0E135, 1.0E136, 1.0E137, 1.0E138, 1.0E139, 1.0E140, 1.0E141, 1.0E142, 1.0E143, 1.0E144, 1.0E145, 1.0E146, 1.0E147, 1.0E148, 1.0E149, 1.0E150, 1.0E151, 1.0E152, 1.0E153, 1.0E154, 1.0E155, 1.0E156, 1.0E157, 1.0E158, 1.0E159, 1.0E160, 1.0E161, 1.0E162, 1.0E163, 1.0E164, 1.0E165, 1.0E166, 1.0E167, 1.0E168, 1.0E169, 1.0E170, 1.0E171, 1.0E172, 1.0E173, 1.0E174, 1.0E175, 1.0E176, 1.0E177, 1.0E178, 1.0E179, 1.0E180, 1.0E181, 1.0E182, 1.0E183, 1.0E184, 1.0E185, 1.0E186, 1.0E187, 1.0E188, 1.0E189, 1.0E190, 1.0E191, 1.0E192, 1.0E193, 1.0E194, 1.0E195, 1.0E196, 1.0E197, 1.0E198, 1.0E199, 1.0E200, 1.0E201, 1.0E202, 1.0E203, 1.0E204, 1.0E205, 1.0E206, 1.0E207, 1.0E208, 1.0E209, 1.0E210, 1.0E211, 1.0E212, 1.0E213, 1.0E214, 1.0E215, 1.0E216, 1.0E217, 1.0E218, 1.0E219, 1.0E220, 1.0E221, 1.0E222, 1.0E223, 1.0E224, 1.0E225, 1.0E226, 1.0E227, 1.0E228, 1.0E229, 1.0E230, 1.0E231, 1.0E232, 1.0E233, 1.0E234, 1.0E235, 1.0E236, 1.0E237, 1.0E238, 1.0E239, 1.0E240, 1.0E241, 1.0E242, 1.0E243, 1.0E244, 1.0E245, 1.0E246, 1.0E247, 1.0E248, 1.0E249, 1.0E250, 1.0E251, 1.0E252, 1.0E253, 1.0E254, 1.0E255, 1.0E256, 1.0E257, 1.0E258, 1.0E259, 1.0E260, 1.0E261, 1.0E262, 1.0E263, 1.0E264, 1.0E265, 1.0E266, 1.0E267, 1.0E268, 1.0E269, 1.0E270, 1.0E271, 1.0E272, 1.0E273, 1.0E274, 1.0E275, 1.0E276, 1.0E277, 1.0E278, 1.0E279, 1.0E280, 1.0E281, 1.0E282, 1.0E283, 1.0E284, 1.0E285, 1.0E286, 1.0E287, 1.0E288, 1.0E289, 1.0E290, 1.0E291, 1.0E292, 1.0E293, 1.0E294, 1.0E295, 1.0E296, 1.0E297, 1.0E298, 1.0E299, 1.0E300, 1.0E301, 1.0E302, 1.0E303, 1.0E304, 1.0E305, 1.0E306, 1.0E307, 1.0E308};
   private static final double[] POW_10_NEG = new double[]{1.0, 0.1, 0.01, 0.001, 1.0E-4, 1.0E-5, 1.0E-6, 1.0E-7, 1.0E-8, 1.0E-9, 1.0E-10, 1.0E-11, 1.0E-12, 1.0E-13, 1.0E-14, 1.0E-15, 1.0E-16, 1.0E-17, 1.0E-18, 1.0E-19, 1.0E-20, 1.0E-21, 1.0E-22, 1.0E-23, 1.0E-24, 1.0E-25, 1.0E-26, 1.0E-27, 1.0E-28, 1.0E-29, 1.0E-30, 1.0E-31, 1.0E-32, 1.0E-33, 1.0E-34, 1.0E-35, 1.0E-36, 1.0E-37, 1.0E-38, 1.0E-39, 1.0E-40, 1.0E-41, 1.0E-42, 1.0E-43, 1.0E-44, 1.0E-45, 1.0E-46, 1.0E-47, 1.0E-48, 1.0E-49, 1.0E-50, 1.0E-51, 1.0E-52, 1.0E-53, 1.0E-54, 1.0E-55, 1.0E-56, 1.0E-57, 1.0E-58, 1.0E-59, 1.0E-60, 1.0E-61, 1.0E-62, 1.0E-63, 1.0E-64, 1.0E-65, 1.0E-66, 1.0E-67, 1.0E-68, 1.0E-69, 1.0E-70, 1.0E-71, 1.0E-72, 1.0E-73, 1.0E-74, 1.0E-75, 1.0E-76, 1.0E-77, 1.0E-78, 1.0E-79, 1.0E-80, 1.0E-81, 1.0E-82, 1.0E-83, 1.0E-84, 1.0E-85, 1.0E-86, 1.0E-87, 1.0E-88, 1.0E-89, 1.0E-90, 1.0E-91, 1.0E-92, 1.0E-93, 1.0E-94, 1.0E-95, 1.0E-96, 1.0E-97, 1.0E-98, 1.0E-99, 1.0E-100, 1.0E-101, 1.0E-102, 1.0E-103, 1.0E-104, 1.0E-105, 1.0E-106, 1.0E-107, 1.0E-108, 1.0E-109, 1.0E-110, 1.0E-111, 1.0E-112, 1.0E-113, 1.0E-114, 1.0E-115, 1.0E-116, 1.0E-117, 1.0E-118, 1.0E-119, 1.0E-120, 1.0E-121, 1.0E-122, 1.0E-123, 1.0E-124, 1.0E-125, 1.0E-126, 1.0E-127, 1.0E-128, 1.0E-129, 1.0E-130, 1.0E-131, 1.0E-132, 1.0E-133, 1.0E-134, 1.0E-135, 1.0E-136, 1.0E-137, 1.0E-138, 1.0E-139, 1.0E-140, 1.0E-141, 1.0E-142, 1.0E-143, 1.0E-144, 1.0E-145, 1.0E-146, 1.0E-147, 1.0E-148, 1.0E-149, 1.0E-150, 1.0E-151, 1.0E-152, 1.0E-153, 1.0E-154, 1.0E-155, 1.0E-156, 1.0E-157, 1.0E-158, 1.0E-159, 1.0E-160, 1.0E-161, 1.0E-162, 1.0E-163, 1.0E-164, 1.0E-165, 1.0E-166, 1.0E-167, 1.0E-168, 1.0E-169, 1.0E-170, 1.0E-171, 1.0E-172, 1.0E-173, 1.0E-174, 1.0E-175, 1.0E-176, 1.0E-177, 1.0E-178, 1.0E-179, 1.0E-180, 1.0E-181, 1.0E-182, 1.0E-183, 1.0E-184, 1.0E-185, 1.0E-186, 1.0E-187, 1.0E-188, 1.0E-189, 1.0E-190, 1.0E-191, 1.0E-192, 1.0E-193, 1.0E-194, 1.0E-195, 1.0E-196, 1.0E-197, 1.0E-198, 1.0E-199, 1.0E-200, 1.0E-201, 1.0E-202, 1.0E-203, 1.0E-204, 1.0E-205, 1.0E-206, 1.0E-207, 1.0E-208, 1.0E-209, 1.0E-210, 1.0E-211, 1.0E-212, 1.0E-213, 1.0E-214, 1.0E-215, 1.0E-216, 1.0E-217, 1.0E-218, 1.0E-219, 1.0E-220, 1.0E-221, 1.0E-222, 1.0E-223, 1.0E-224, 1.0E-225, 1.0E-226, 1.0E-227, 1.0E-228, 1.0E-229, 1.0E-230, 1.0E-231, 1.0E-232, 1.0E-233, 1.0E-234, 1.0E-235, 1.0E-236, 1.0E-237, 1.0E-238, 1.0E-239, 1.0E-240, 1.0E-241, 1.0E-242, 1.0E-243, 1.0E-244, 1.0E-245, 1.0E-246, 1.0E-247, 1.0E-248, 1.0E-249, 1.0E-250, 1.0E-251, 1.0E-252, 1.0E-253, 1.0E-254, 1.0E-255, 1.0E-256, 1.0E-257, 1.0E-258, 1.0E-259, 1.0E-260, 1.0E-261, 1.0E-262, 1.0E-263, 1.0E-264, 1.0E-265, 1.0E-266, 1.0E-267, 1.0E-268, 1.0E-269, 1.0E-270, 1.0E-271, 1.0E-272, 1.0E-273, 1.0E-274, 1.0E-275, 1.0E-276, 1.0E-277, 1.0E-278, 1.0E-279, 1.0E-280, 1.0E-281, 1.0E-282, 1.0E-283, 1.0E-284, 1.0E-285, 1.0E-286, 1.0E-287, 1.0E-288, 1.0E-289, 1.0E-290, 1.0E-291, 1.0E-292, 1.0E-293, 1.0E-294, 1.0E-295, 1.0E-296, 1.0E-297, 1.0E-298, 1.0E-299, 1.0E-300, 1.0E-301, 1.0E-302, 1.0E-303, 1.0E-304, 1.0E-305, 1.0E-306, 1.0E-307, 1.0E-308, 1.0E-309, 1.0E-310, 1.0E-311, 1.0E-312, 1.0E-313, 1.0E-314, 1.0E-315, 1.0E-316, 1.0E-317, 1.0E-318, 1.0E-319, 1.0E-320, 1.0E-321, 1.0E-322, 1.0E-323};
   static final double[] atanhi = new double[]{0.4636476090008061, 0.7853981633974483, 0.982793723247329, 1.5707963267948966};
   static final double[] atanlo = new double[]{2.2698777452961687E-17, 3.061616997868383E-17, 1.3903311031230998E-17, 6.123233995736766E-17};
   static final double[] aT = new double[]{0.3333333333333293, -0.19999999999876483, 0.14285714272503466, -0.11111110405462356, 0.09090887133436507, -0.0769187620504483, 0.06661073137387531, -0.058335701337905735, 0.049768779946159324, -0.036531572744216916, 0.016285820115365782};
   static final double one = 1.0;
   static final double huge = 1.0E300;
   static final double ln2_hi = 0.6931471803691238;
   static final double ln2_lo = 1.9082149292705877E-10;
   static final double two54 = 1.8014398509481984E16;
   static final double Lg1 = 0.6666666666666735;
   static final double Lg2 = 0.3999999999940942;
   static final double Lg3 = 0.2857142874366239;
   static final double Lg4 = 0.22222198432149784;
   static final double Lg5 = 0.1818357216161805;
   static final double Lg6 = 0.15313837699209373;
   static final double Lg7 = 0.14798198605116586;
   static final double zero = 0.0;
   static final double[] halF = new double[]{0.5, -0.5};
   static final double twom1000 = 9.332636185032189E-302;
   static final double o_threshold = 709.782712893384;
   static final double u_threshold = -745.1332191019411;
   static final double[] ln2HI = new double[]{0.6931471803691238, -0.6931471803691238};
   static final double[] ln2LO = new double[]{1.9082149292705877E-10, -1.9082149292705877E-10};
   static final double invln2 = 1.4426950408889634;
   static final double P1 = 0.16666666666666602;
   static final double P2 = -0.0027777777777015593;
   static final double P3 = 6.613756321437934E-5;
   static final double P4 = -1.6533902205465252E-6;
   static final double P5 = 4.1381367970572385E-8;

   private MathLib() {
   }

   public static synchronized int randomInt(int var0, int var1) {
      Seed = Seed * 25214903917L + 11L & 281474976710655L;
      int var2 = (int)(Seed >>> 16);
      return var2 < 0 ? var0 - var2 % (var1 - var0 + 1) : var0 + var2 % (var1 - var0 + 1);
   }

   public static double toRadians(double var0) {
      return var0 * 0.017453292519943295;
   }

   public static double toDegrees(double var0) {
      return var0 * 57.29577951308232;
   }

   public static double sqrt(double var0) {
      return Math.sqrt(var0);
   }

   public static double rem(double var0, double var2) {
      double var4 = var0 / var2;
      return abs(var4) <= 9.223372036854776E18 ? var0 - (double)round(var4) * var2 : Double.NaN;
   }

   public static double ceil(double var0) {
      return Math.ceil(var0);
   }

   public static double floor(double var0) {
      return Math.floor(var0);
   }

   public static double sin(double var0) {
      return Math.sin(var0);
   }

   public static double cos(double var0) {
      return Math.cos(var0);
   }

   public static double tan(double var0) {
      return Math.tan(var0);
   }

   public static double asin(double var0) {
      if (!(var0 < -1.0) && !(var0 > 1.0)) {
         if (var0 == -1.0) {
            return -1.5707963267948966;
         } else {
            return var0 == 1.0 ? 1.5707963267948966 : atan(var0 / sqrt(1.0 - var0 * var0));
         }
      } else {
         return Double.NaN;
      }
   }

   public static double acos(double var0) {
      return 1.5707963267948966 - asin(var0);
   }

   public static double atan(double var0) {
      return _atan(var0);
   }

   public static double atan2(double var0, double var2) {
      if (abs(var2) > 1.0E-128) {
         double var6 = atan(abs(var0) / abs(var2));
         if (var2 < 0.0) {
            var6 = Math.PI - var6;
         }

         if (var0 < 0.0) {
            var6 = 6.283185307179586 - var6;
         }

         return var6;
      } else if (var0 > 1.0E-128) {
         return 1.5707963267948966;
      } else {
         return var0 < -1.0E-128 ? 4.71238898038469 : 0.0;
      }
   }

   public static double sinh(double var0) {
      return (exp(var0) - exp(-var0)) * 0.5;
   }

   public static double cosh(double var0) {
      return (exp(var0) + exp(-var0)) * 0.5;
   }

   public static double tanh(double var0) {
      return (exp(2.0 * var0) - 1.0) / (exp(2.0 * var0) + 1.0);
   }

   public static double exp(double var0) {
      return _ieee754_exp(var0);
   }

   public static double log(double var0) {
      return _ieee754_log(var0);
   }

   public static double log10(double var0) {
      return log(var0) * INV_LOG10;
   }

   public static double pow(double var0, double var2) {
      return exp(var2 * log(var0));
   }

   public static int round(float var0) {
      return (int)floor((double)(var0 + 0.5F));
   }

   public static long round(double var0) {
      return (long)floor(var0 + 0.5);
   }

   public static double random() {
      return (double)randomInt(0, Integer.MAX_VALUE) * 4.656612875245797E-10;
   }

   public static int abs(int var0) {
      return var0 < 0 ? -var0 : var0;
   }

   public static long abs(long var0) {
      return var0 < 0L ? -var0 : var0;
   }

   public static float abs(float var0) {
      return var0 < 0.0F ? -var0 : var0;
   }

   public static double abs(double var0) {
      return var0 < 0.0 ? -var0 : var0;
   }

   public static int max(int var0, int var1) {
      return var0 >= var1 ? var0 : var1;
   }

   public static long max(long var0, long var2) {
      return var0 >= var2 ? var0 : var2;
   }

   public static float max(float var0, float var1) {
      return var0 >= var1 ? var0 : var1;
   }

   public static double max(double var0, double var2) {
      return var0 >= var2 ? var0 : var2;
   }

   public static int min(int var0, int var1) {
      return var0 < var1 ? var0 : var1;
   }

   public static long min(long var0, long var2) {
      return var0 < var2 ? var0 : var2;
   }

   public static float min(float var0, float var1) {
      return var0 < var1 ? var0 : var1;
   }

   public static double min(double var0, double var2) {
      return var0 < var2 ? var0 : var2;
   }

   public static double toDouble(long var0, int var2) {
      return var2 < 0 ? (var2 >= -323 ? (double)var0 * POW_10_NEG[-var2] : (double)var0 * 0.0) : (var2 <= 308 ? (double)var0 * POW_10_POS[var2] : (double)var0 * Double.POSITIVE_INFINITY);
   }

   static double _atan(double var0) {
      long var13 = Double.doubleToLongBits(var0);
      int var15 = (int)(var13 >> 32);
      int var16 = (int)var13;
      int var10 = var15 & Integer.MAX_VALUE;
      if (var10 < 1141899264) {
         byte var12;
         if (var10 < 1071382528) {
            if (var10 < 1042284544 && 1.0E300 + var0 > 1.0) {
               return var0;
            }

            var12 = -1;
         } else {
            var0 = abs(var0);
            if (var10 < 1072889856) {
               if (var10 < 1072037888) {
                  var12 = 0;
                  var0 = (2.0 * var0 - 1.0) / (2.0 + var0);
               } else {
                  var12 = 1;
                  var0 = (var0 - 1.0) / (var0 + 1.0);
               }
            } else if (var10 < 1073971200) {
               var12 = 2;
               var0 = (var0 - 1.5) / (1.0 + 1.5 * var0);
            } else {
               var12 = 3;
               var0 = -1.0 / var0;
            }
         }

         double var8 = var0 * var0;
         double var2 = var8 * var8;
         double var4 = var8 * (aT[0] + var2 * (aT[2] + var2 * (aT[4] + var2 * (aT[6] + var2 * (aT[8] + var2 * aT[10])))));
         double var6 = var2 * (aT[1] + var2 * (aT[3] + var2 * (aT[5] + var2 * (aT[7] + var2 * aT[9]))));
         if (var12 < 0) {
            return var0 - var0 * (var4 + var6);
         } else {
            var8 = atanhi[var12] - (var0 * (var4 + var6) - atanlo[var12] - var0);
            return var15 < 0 ? -var8 : var8;
         }
      } else if (var10 <= 2146435072 && (var10 != 2146435072 || var16 == 0)) {
         return var15 > 0 ? atanhi[3] + atanlo[3] : -atanhi[3] - atanlo[3];
      } else {
         return var0 + var0;
      }
   }

   static double _ieee754_log(double var0) {
      long var25 = Double.doubleToLongBits(var0);
      int var21 = (int)(var25 >> 32);
      int var24 = (int)var25;
      int var20 = 0;
      if (var21 < 1048576) {
         if ((var21 & Integer.MAX_VALUE | var24) == 0) {
            return Double.NEGATIVE_INFINITY;
         }

         if (var21 < 0) {
            return (var0 - var0) / 0.0;
         }

         var20 -= 54;
         var0 *= 1.8014398509481984E16;
         var25 = Double.doubleToLongBits(var0);
         var21 = (int)(var25 >> 32);
      }

      if (var21 >= 2146435072) {
         return var0 + var0;
      } else {
         var20 += (var21 >> 20) - 1023;
         var21 &= 1048575;
         int var22 = var21 + 614244 & 1048576;
         var25 = Double.doubleToLongBits(var0);
         int var27 = var21 | var22 ^ 1072693248;
         var25 = ((long)var27 & 4294967295L) << 32 | var25 & 4294967295L;
         var0 = Double.longBitsToDouble(var25);
         var20 += var22 >> 20;
         double var4 = var0 - 1.0;
         double var10;
         double var18;
         if ((1048575 & 2 + var21) < 3) {
            if (var4 == 0.0) {
               if (var20 == 0) {
                  return 0.0;
               } else {
                  var18 = (double)var20;
                  return var18 * 0.6931471803691238 + var18 * 1.9082149292705877E-10;
               }
            } else {
               var10 = var4 * var4 * (0.5 - 0.3333333333333333 * var4);
               if (var20 == 0) {
                  return var4 - var10;
               } else {
                  var18 = (double)var20;
                  return var18 * 0.6931471803691238 - (var10 - var18 * 1.9082149292705877E-10 - var4);
               }
            }
         } else {
            double var6 = var4 / (2.0 + var4);
            var18 = (double)var20;
            double var8 = var6 * var6;
            var22 = var21 - 398458;
            double var12 = var8 * var8;
            int var23 = 440401 - var21;
            double var14 = var12 * (0.3999999999940942 + var12 * (0.22222198432149784 + var12 * 0.15313837699209373));
            double var16 = var8 * (0.6666666666666735 + var12 * (0.2857142874366239 + var12 * (0.1818357216161805 + var12 * 0.14798198605116586)));
            var22 |= var23;
            var10 = var16 + var14;
            if (var22 > 0) {
               double var2 = 0.5 * var4 * var4;
               return var20 == 0 ? var4 - (var2 - var6 * (var2 + var10)) : var18 * 0.6931471803691238 - (var2 - (var6 * (var2 + var10) + var18 * 1.9082149292705877E-10) - var4);
            } else {
               return var20 == 0 ? var4 - var6 * (var4 - var10) : var18 * 0.6931471803691238 - (var6 * (var4 - var10) - var18 * 1.9082149292705877E-10 - var4);
            }
         }
      }
   }

   static double _ieee754_exp(double var0) {
      double var4 = 0.0;
      double var6 = 0.0;
      int var12 = 0;
      long var15 = Double.doubleToLongBits(var0);
      int var17 = (int)(var15 >> 32);
      int var18 = (int)var15;
      int var13 = var17 >> 31 & 1;
      int var14 = var17 & Integer.MAX_VALUE;
      if (var14 >= 1082535490) {
         if (var14 >= 2146435072) {
            if ((var14 & 1048575 | var18) != 0) {
               return var0 + var0;
            }

            return var13 == 0 ? var0 : 0.0;
         }

         if (var0 > 709.782712893384) {
            return Double.POSITIVE_INFINITY;
         }

         if (var0 < -745.1332191019411) {
            return 0.0;
         }
      }

      double var10;
      if (var14 > 1071001154) {
         if (var14 < 1072734898) {
            var4 = var0 - ln2HI[var13];
            var6 = ln2LO[var13];
            var12 = 1 - var13 - var13;
         } else {
            var12 = (int)(1.4426950408889634 * var0 + halF[var13]);
            var10 = (double)var12;
            var4 = var0 - var10 * ln2HI[0];
            var6 = var10 * ln2LO[0];
         }

         var0 = var4 - var6;
      } else if (var14 < 1043333120) {
         if (1.0E300 + var0 > 1.0) {
            return 1.0 + var0;
         }
      } else {
         var12 = 0;
      }

      var10 = var0 * var0;
      double var8 = var0 - var10 * (0.16666666666666602 + var10 * (-0.0027777777777015593 + var10 * (6.613756321437934E-5 + var10 * (-1.6533902205465252E-6 + var10 * 4.1381367970572385E-8))));
      if (var12 == 0) {
         return 1.0 - (var0 * var8 / (var8 - 2.0) - var0);
      } else {
         double var2 = 1.0 - (var6 - var0 * var8 / (2.0 - var8) - var4);
         long var19 = Double.doubleToLongBits(var2);
         int var21 = (int)(var19 >> 32);
         if (var12 >= -1021) {
            var21 += var12 << 20;
            var19 = ((long)var21 & 4294967295L) << 32 | var19 & 4294967295L;
            var2 = Double.longBitsToDouble(var19);
            return var2;
         } else {
            var21 += var12 + 1000 << 20;
            var19 = ((long)var21 & 4294967295L) << 32 | var19 & 4294967295L;
            var2 = Double.longBitsToDouble(var19);
            return var2 * 9.332636185032189E-302;
         }
      }
   }
}
