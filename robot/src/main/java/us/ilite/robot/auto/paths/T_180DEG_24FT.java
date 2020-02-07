package us.ilite.robot.auto.paths;

import com.team319.trajectory.Path;

public class T_180DEG_24FT extends Path {
   // dt,x,y,left.pos,left.vel,left.acc,left.jerk,center.pos,center.vel,center.acc,center.jerk,right.pos,right.vel,right.acc,right.jerk,heading
	private static final double[][] points = {
				{0.0200,0.0113,-12.0000,0.0113,0.5640,28.2000,0.0000,0.0113,0.5640,28.2000,0.0000,0.0113,0.5640,28.2000,0.0000,0.0000},
				{0.0200,0.0338,-12.0000,0.0338,1.1277,28.1856,-0.7183,0.0338,1.1280,28.2000,0.0000,0.0338,1.1283,28.2144,0.7183,0.0000},
				{0.0200,0.0677,-12.0000,0.0677,1.6910,28.1659,-0.9849,0.0677,1.6920,28.2000,0.0000,0.0677,1.6930,28.2341,0.9849,0.0000},
				{0.0200,0.1128,-12.0000,0.1127,2.2537,28.1338,-1.6063,0.1128,2.2560,28.2000,0.0000,0.1129,2.2583,28.2662,1.6063,0.0001},
				{0.0200,0.1692,-12.0000,0.1690,2.8155,28.0912,-2.1314,0.1692,2.8200,28.2000,0.0000,0.1694,2.8245,28.3088,2.1314,0.0001},
				{0.0200,0.2369,-12.0000,0.2366,3.3763,28.0382,-2.6492,0.2369,3.3840,28.2000,0.0000,0.2372,3.3917,28.3618,2.6492,0.0003},
				{0.0200,0.3158,-11.9999,0.3153,3.9358,27.9750,-3.1596,0.3158,3.9480,28.2000,0.0000,0.3164,3.9602,28.4250,3.1596,0.0005},
				{0.0200,0.4061,-11.9999,0.4052,4.4938,27.9017,-3.6636,0.4061,4.5120,28.2000,0.0000,0.4070,4.5302,28.4983,3.6636,0.0009},
				{0.0200,0.5076,-11.9998,0.5062,5.0502,27.8185,-4.1635,0.5076,5.0760,28.2000,0.0000,0.5090,5.1018,28.5815,4.1635,0.0013},
				{0.0200,0.6204,-11.9996,0.6183,5.6047,27.7252,-4.6631,0.6204,5.6400,28.2000,0.0000,0.6225,5.6753,28.6748,4.6630,0.0020},
				{0.0200,0.7445,-11.9993,0.7414,6.1571,27.6218,-5.1678,0.7445,6.2040,28.2000,0.0000,0.7476,6.2509,28.7781,5.1675,0.0028},
				{0.0200,0.8798,-11.9988,0.8755,6.7073,27.5082,-5.6844,0.8798,6.7680,28.2000,0.0000,0.8841,6.8287,28.8918,5.6841,0.0040},
				{0.0200,1.0265,-11.9982,1.0206,7.2550,27.3837,-6.2220,1.0265,7.3320,28.2000,0.0000,1.0323,7.4090,29.0163,6.2214,0.0054},
				{0.0200,1.1844,-11.9972,1.1766,7.7999,27.2479,-6.7914,1.1844,7.8960,28.2000,0.0000,1.1922,7.9921,29.1521,6.7906,0.0071},
				{0.0200,1.3536,-11.9958,1.3435,8.3419,27.0998,-7.4058,1.3536,8.4600,28.2000,0.0000,1.3637,8.5781,29.3002,7.4047,0.0093},
				{0.0200,1.5341,-11.9939,1.5211,8.8807,26.9382,-8.0809,1.5341,9.0240,28.2000,0.0000,1.5471,9.1673,29.4617,8.0793,0.0120},
				{0.0200,1.7258,-11.9913,1.7094,9.4159,26.7615,-8.8351,1.7258,9.5880,28.2000,0.0000,1.7423,9.7601,29.6384,8.8329,0.0151},
				{0.0200,1.9288,-11.9878,1.9084,9.9473,26.5676,-9.6904,1.9289,10.1520,28.2000,0.0000,1.9494,10.3567,29.8322,9.6874,0.0189},
				{0.0200,2.1431,-11.9833,2.1178,10.4744,26.3542,-10.6724,2.1432,10.7160,28.2000,0.0000,2.1686,10.9576,30.0455,10.6682,0.0234},
				{0.0200,2.3686,-11.9774,2.3378,10.9967,26.1180,-11.8112,2.3688,11.2800,28.2000,0.0000,2.3998,11.5633,30.2816,11.8057,0.0286},
				{0.0200,2.6054,-11.9700,2.5681,11.5138,25.8551,-13.1429,2.6057,11.8440,28.2000,0.0000,2.6433,12.1741,30.5443,13.1353,0.0347},
				{0.0200,2.8492,-11.9607,2.8045,11.8238,15.4979,-517.8592,2.8497,12.2000,28.2000,0.0000,2.8948,12.5762,20.1015,-522.1442,0.0416},
				{0.0200,3.0929,-11.9496,3.0403,11.7859,-1.8962,-869.7081,3.0937,12.2000,28.2000,0.0000,3.1471,12.6141,1.8957,-910.2870,0.0492},
				{0.0200,3.3366,-11.9366,3.2752,11.7465,-1.9697,-3.6729,3.3377,12.2000,28.2000,0.0000,3.4002,12.6535,1.9691,3.6694,0.0576},
				{0.0200,3.5801,-11.9215,3.5093,11.7054,-2.0527,-4.1492,3.5817,12.2000,28.2000,0.0000,3.6541,12.6945,2.0520,4.1451,0.0667},
				{0.0200,3.8235,-11.9040,3.7425,11.6625,-2.1455,-4.6425,3.8257,12.2000,28.2000,0.0000,3.9088,12.7374,2.1448,4.6379,0.0766},
				{0.0200,4.0667,-11.8840,3.9749,11.6175,-2.2486,-5.1531,4.0697,12.2000,28.2000,0.0000,4.1645,12.7824,2.2477,5.1478,0.0874},
				{0.0200,4.3096,-11.8614,4.2063,11.5703,-2.3622,-5.6797,4.3137,12.2000,28.2000,0.0000,4.4211,12.8296,2.3612,5.6737,0.0990},
				{0.0200,4.5523,-11.8357,4.4367,11.5206,-2.4866,-6.2196,4.5577,12.2000,28.2000,0.0000,4.6786,12.8793,2.4854,6.2128,0.1115},
				{0.0200,4.7946,-11.8070,4.6661,11.4681,-2.6219,-6.7678,4.8017,12.2000,28.2000,0.0000,4.9373,12.9317,2.6206,6.7602,0.1250},
				{0.0200,5.0364,-11.7748,4.8943,11.4127,-2.7683,-7.3166,5.0457,12.2000,28.2000,0.0000,5.1970,12.9870,2.7668,7.3078,0.1395},
				{0.0200,5.2778,-11.7390,5.1214,11.3542,-2.9253,-7.8544,5.2897,12.2000,28.2000,0.0000,5.4579,13.0455,2.9237,7.8446,0.1551},
				{0.0200,5.5185,-11.6994,5.3473,11.2924,-3.0927,-8.3654,5.5337,12.2000,28.2000,0.0000,5.7201,13.1073,3.0908,8.3544,0.1718},
				{0.0200,5.7586,-11.6555,5.5718,11.2270,-3.2692,-8.8280,5.7777,12.2000,28.2000,0.0000,5.9835,13.1727,3.2671,8.8154,0.1897},
				{0.0200,5.9977,-11.6072,5.7950,11.1579,-3.4535,-9.2130,6.0217,12.2000,28.2000,0.0000,6.2484,13.2417,3.4511,9.1990,0.2089},
				{0.0200,6.2359,-11.5542,6.0167,11.0851,-3.6431,-9.4834,6.2657,12.2000,28.2000,0.0000,6.5146,13.3145,3.6404,9.4680,0.2295},
				{0.0200,6.4729,-11.4961,6.2368,11.0084,-3.8350,-9.5924,6.5097,12.2000,28.2000,0.0000,6.7825,13.3911,3.8319,9.5752,0.2515},
				{0.0200,6.7085,-11.4327,6.4554,10.9279,-4.0246,-9.4817,6.7537,12.2000,28.2000,0.0000,7.0519,13.4716,4.0212,9.4633,0.2749},
				{0.0200,6.9425,-11.3635,6.6723,10.8438,-4.2063,-9.0825,6.9977,12.2000,28.2000,0.0000,7.3230,13.5556,4.2025,9.0628,0.2999},
				{0.0200,7.1746,-11.2884,6.8874,10.7563,-4.3726,-8.3150,7.2417,12.2000,28.2000,0.0000,7.5959,13.6430,4.3684,8.2946,0.3265},
				{0.0200,7.4046,-11.2069,7.1007,10.6660,-4.5144,-7.0916,7.4857,12.2000,28.2000,0.0000,7.8705,13.7332,4.5098,7.0708,0.3548},
				{0.0200,7.6321,-11.1188,7.3122,10.5736,-4.6208,-5.3214,7.7297,12.2000,28.2000,0.0000,8.1470,13.8255,4.6158,5.3015,0.3847},
				{0.0200,7.8568,-11.0237,7.5218,10.4800,-4.6792,-2.9199,7.9737,12.2000,28.2000,0.0000,8.4254,13.9190,4.6738,2.9016,0.4164},
				{0.0200,8.0783,-10.9213,7.7295,10.3865,-4.6756,0.1799,8.2177,12.2000,28.2000,0.0000,8.7057,14.0124,4.6699,-0.1948,0.4499},
				{0.0200,8.2961,-10.8114,7.9354,10.2946,-4.5954,4.0094,8.4617,12.2000,28.2000,0.0000,8.9878,14.1042,4.5895,-4.0192,0.4850},
				{0.0200,8.5098,-10.6937,8.1395,10.2061,-4.4245,8.5455,8.7057,12.2000,28.2000,0.0000,9.2716,14.1925,4.4186,-8.5484,0.5217},
				{0.0200,8.7190,-10.5681,8.3420,10.1231,-4.1507,13.6908,8.9497,12.2000,28.2000,0.0000,9.5571,14.2754,4.1449,-13.6847,0.5600},
				{0.0200,8.9231,-10.4345,8.5429,10.0478,-3.7656,19.2580,9.1937,12.2000,28.2000,0.0000,9.8441,14.3506,3.7601,-19.2413,0.5996},
				{0.0200,9.1217,-10.2927,8.7426,9.9824,-3.2663,24.9652,9.4377,12.2000,28.2000,0.0000,10.1324,14.4159,3.2613,-24.9366,0.6405},
				{0.0200,9.3143,-10.1429,8.9412,9.9293,-2.6573,30.4468,9.6817,12.2000,28.2000,0.0000,10.4218,14.4689,2.6532,-30.4063,0.6824},
				{0.0200,9.5003,-9.9850,9.1390,9.8903,-1.9516,35.2855,9.9257,12.2000,28.2000,0.0000,10.7120,14.5079,1.9485,-35.2340,0.7249},
				{0.0200,9.6794,-9.8194,9.3363,9.8669,-1.1704,39.0628,10.1697,12.2000,28.2000,0.0000,11.0026,14.5313,1.1685,-39.0023,0.7679},
				{0.0200,9.8512,-9.6461,9.5335,9.8600,-0.3419,41.4209,10.4137,12.2000,28.2000,0.0000,11.2934,14.5381,0.3414,-41.3547,0.8110},
				{0.0200,10.0154,-9.4657,9.7309,9.8700,0.5005,42.1232,10.6577,12.2000,28.2000,0.0000,11.5839,14.5281,-0.4997,-42.0552,0.8540},
				{0.0200,10.1718,-9.2784,9.9288,9.8965,1.3225,41.0973,10.9017,12.2000,28.2000,0.0000,11.8740,14.5017,-1.3204,-41.0318,0.8964},
				{0.0200,10.3201,-9.0847,10.1276,9.9383,2.0914,38.4486,11.1457,12.2000,28.2000,0.0000,12.1632,14.4599,-2.0881,-38.3895,0.9381},
				{0.0200,10.4603,-8.8850,10.3275,9.9939,2.7803,34.4407,11.3897,12.2000,28.2000,0.0000,12.4512,14.4044,-2.7760,-34.3910,0.9787},
				{0.0200,10.5925,-8.6799,10.5287,10.0613,3.3692,29.4476,11.6337,12.2000,28.2000,0.0000,12.7380,14.3371,-3.3642,-29.4094,1.0181},
				{0.0200,10.7165,-8.4698,10.7315,10.1382,3.8471,23.8924,11.8777,12.2000,28.2000,0.0000,13.0232,14.2603,-3.8415,-23.8662,1.0561},
				{0.0200,10.8327,-8.2553,10.9359,10.2225,4.2108,18.1860,12.1217,12.2000,28.2000,0.0000,13.3067,14.1762,-4.2049,-18.1713,1.0926},
				{0.0200,10.9412,-8.0367,11.1422,10.3117,4.4644,12.6797,12.3657,12.2000,28.2000,0.0000,13.5885,14.0870,-4.4584,-12.6753,1.1274},
				{0.0200,11.0422,-7.8146,11.3502,10.4041,4.6171,7.6379,12.6097,12.2000,28.2000,0.0000,13.8684,13.9948,-4.6113,-7.6423,1.1605},
				{0.0200,11.1359,-7.5893,11.5602,10.4977,4.6817,3.2304,12.8537,12.2000,28.2000,0.0000,14.1464,13.9013,-4.6761,-3.2412,1.1918},
				{0.0200,11.2228,-7.3613,11.7720,10.5912,4.6725,-0.4611,13.0977,12.2000,28.2000,0.0000,14.4225,13.8079,-4.6672,0.4455,1.2215},
				{0.0200,11.3031,-7.1309,11.9857,10.6833,4.6040,-3.4253,13.3417,12.2000,28.2000,0.0000,14.6969,13.7160,-4.5990,3.4066,1.2494},
				{0.0200,11.3770,-6.8984,12.2011,10.7731,4.4900,-5.7018,13.5857,12.2000,28.2000,0.0000,14.9694,13.6262,-4.4854,5.6815,1.2757},
				{0.0200,11.4451,-6.6641,12.4183,10.8599,4.3427,-7.3618,13.8297,12.2000,28.2000,0.0000,15.2402,13.5395,-4.3386,7.3411,1.3004},
				{0.0200,11.5075,-6.4282,12.6372,10.9434,4.1729,-8.4921,14.0737,12.2000,28.2000,0.0000,15.5093,13.4561,-4.1692,8.4718,1.3236},
				{0.0200,11.5646,-6.1910,12.8577,11.0232,3.9892,-9.1836,14.3177,12.2000,28.2000,0.0000,15.7768,13.3764,-3.9859,9.1641,1.3453},
				{0.0200,11.6167,-5.9526,13.0797,11.0991,3.7988,-9.5225,14.5617,12.2000,28.2000,0.0000,16.0428,13.3005,-3.7958,9.5042,1.3656},
				{0.0200,11.6641,-5.7133,13.3031,11.1713,3.6070,-9.5866,14.8057,12.2000,28.2000,0.0000,16.3074,13.2284,-3.6044,9.5700,1.3845},
				{0.0200,11.7072,-5.4731,13.5279,11.2396,3.4182,-9.4433,15.0497,12.2000,28.2000,0.0000,16.5706,13.1601,-3.4158,9.4279,1.4022},
				{0.0200,11.7461,-5.2323,13.7540,11.3043,3.2352,-9.1478,15.2937,12.2000,28.2000,0.0000,16.8325,13.0954,-3.2331,9.1341,1.4187},
				{0.0200,11.7812,-4.9908,13.9813,11.3655,3.0603,-8.7456,15.5377,12.2000,28.2000,0.0000,17.0932,13.0342,-3.0585,8.7332,1.4341},
				{0.0200,11.8127,-4.7488,14.2097,11.4234,2.8949,-8.2719,15.7817,12.2000,28.2000,0.0000,17.3527,12.9764,-2.8933,8.2611,1.4484},
				{0.0200,11.8408,-4.5065,14.4393,11.4782,2.7398,-7.7543,16.0257,12.2000,28.2000,0.0000,17.6112,12.9216,-2.7384,7.7447,1.4617},
				{0.0200,11.8659,-4.2638,14.6699,11.5301,2.5955,-7.2134,16.2697,12.2000,28.2000,0.0000,17.8685,12.8697,-2.5943,7.2048,1.4741},
				{0.0200,11.8880,-4.0208,14.9015,11.5794,2.4622,-6.6639,16.5137,12.2000,28.2000,0.0000,18.1250,12.8205,-2.4611,6.6565,1.4855},
				{0.0200,11.9075,-3.7775,15.1340,11.6262,2.3399,-6.1168,16.7577,12.2000,28.2000,0.0000,18.3804,12.7737,-2.3389,6.1102,1.4961},
				{0.0200,11.9245,-3.5341,15.3674,11.6708,2.2283,-5.5791,17.0017,12.2000,28.2000,0.0000,18.6350,12.7291,-2.2275,5.5733,1.5059},
				{0.0200,11.9392,-3.2906,15.6017,11.7133,2.1272,-5.0554,17.2457,12.2000,28.2000,0.0000,18.8887,12.6866,-2.1265,5.0502,1.5148},
				{0.0200,11.9518,-3.0469,15.8368,11.7540,2.0363,-4.5480,17.4897,12.2000,28.2000,0.0000,19.1417,12.6459,-2.0356,4.5434,1.5231},
				{0.0200,11.9626,-2.8031,16.0726,11.7931,1.9551,-4.0579,17.7337,12.2000,28.2000,0.0000,19.3938,12.6068,-1.9545,4.0539,1.5305},
				{0.0200,11.9715,-2.5593,16.3093,11.8308,1.8834,-3.5849,17.9777,12.2000,28.2000,0.0000,19.6452,12.5692,-1.8829,3.5813,1.5374},
				{0.0200,11.9789,-2.3154,16.5466,11.8672,1.8208,-3.1276,18.2217,12.2000,28.2000,0.0000,19.8958,12.5328,-1.8204,3.1244,1.5435},
				{0.0200,11.9849,-2.0715,16.7847,11.9026,1.7672,-2.6842,18.4657,12.2000,28.2000,0.0000,20.1458,12.4974,-1.7668,2.6814,1.5490},
				{0.0200,11.9896,-1.8275,17.0234,11.9370,1.7221,-2.2524,18.7097,12.2000,28.2000,0.0000,20.3950,12.4630,-1.7218,2.2498,1.5538},
				{0.0200,11.9932,-1.5836,17.2628,11.9707,1.6855,-1.8292,18.9537,12.2000,28.2000,0.0000,20.6436,12.4293,-1.6852,1.8268,1.5580},
				{0.0200,11.9959,-1.3396,17.5029,12.0039,1.6573,-1.4113,19.1977,12.2000,28.2000,0.0000,20.8916,12.3961,-1.6571,1.4092,1.5617},
				{0.0200,11.9978,-1.0956,17.7436,12.0366,1.6374,-0.9952,19.4417,12.2000,28.2000,0.0000,21.1388,12.3634,-1.6372,0.9932,1.5647},
				{0.0200,11.9989,-0.8516,17.9850,12.0691,1.6259,-0.5769,19.6857,12.2000,28.2000,0.0000,21.3854,12.3309,-1.6257,0.5750,1.5671},
				{0.0200,11.9996,-0.6076,18.2270,12.1016,1.6228,-0.1520,19.9297,12.2000,28.2000,0.0000,21.6314,12.2984,-1.6227,0.1502,1.5689},
				{0.0200,11.9999,-0.3636,18.4697,12.1341,1.6285,0.2843,20.1737,12.2000,28.2000,0.0000,21.8767,12.2659,-1.6284,-0.2861,1.5701},
				{0.0200,12.0000,-0.1196,18.7131,12.1670,1.6433,0.7374,20.4177,12.2000,28.2000,0.0000,22.1214,12.2330,-1.6432,-0.7393,1.5707},
				{0.0200,12.0000,0.1244,18.9569,12.1916,1.2287,-20.7290,20.6617,12.2000,28.2000,0.0000,22.3656,12.2084,-1.2287,20.7271,1.5709},
				{0.0200,11.9999,0.3684,19.2002,12.1661,-1.2727,-125.0671,20.9057,12.2000,28.2000,0.0000,22.6102,12.2339,1.2726,125.0653,1.5715},
				{0.0200,11.9996,0.6124,19.4429,12.1331,-1.6535,-19.0431,21.1497,12.2000,28.2000,0.0000,22.8556,12.2669,1.6535,19.0412,1.5727},
				{0.0200,11.9989,0.8564,19.6849,12.1003,-1.6384,0.7557,21.3937,12.2000,28.2000,0.0000,23.1016,12.2997,1.6383,-0.7576,1.5746},
				{0.0200,11.9977,1.1004,19.9262,12.0676,-1.6325,0.2971,21.6377,12.2000,28.2000,0.0000,23.3482,12.3324,1.6323,-0.2989,1.5770},
				{0.0200,11.9958,1.3444,20.1669,12.0349,-1.6354,-0.1442,21.8817,12.2000,28.2000,0.0000,23.5955,12.3651,1.6352,0.1423,1.5801},
				{0.0200,11.9931,1.5884,20.4070,12.0020,-1.6468,-0.5736,22.1257,12.2000,28.2000,0.0000,23.8435,12.3980,1.6466,0.5717,1.5837},
				{0.0200,11.9895,1.8323,20.6463,11.9687,-1.6667,-0.9961,22.3697,12.2000,28.2000,0.0000,24.0921,12.4313,1.6665,0.9940,1.5880},
				{0.0200,11.9847,2.0763,20.8850,11.9348,-1.6951,-1.4160,22.6137,12.2000,28.2000,0.0000,24.3414,12.4652,1.6948,1.4138,1.5929},
				{0.0200,11.9787,2.3202,21.1230,11.9001,-1.7318,-1.8373,22.8577,12.2000,28.2000,0.0000,24.5914,12.4998,1.7315,1.8350,1.5984},
				{0.0200,11.9712,2.5641,21.3603,11.8646,-1.7771,-2.2640,23.1017,12.2000,28.2000,0.0000,24.8421,12.5354,1.7767,2.2613,1.6046},
				{0.0200,11.9621,2.8079,21.5969,11.8280,-1.8311,-2.6988,23.3457,12.2000,28.2000,0.0000,25.0935,12.5720,1.8306,2.6960,1.6114},
				{0.0200,11.9513,3.0517,21.8327,11.7901,-1.8940,-3.1451,23.5897,12.2000,28.2000,0.0000,25.3457,12.6099,1.8935,3.1419,1.6190},
				{0.0200,11.9386,3.2954,22.0677,11.7508,-1.9661,-3.6050,23.8337,12.2000,28.2000,0.0000,25.5987,12.6492,1.9655,3.6015,1.6273},
				{0.0200,11.9237,3.5389,22.3019,11.7098,-2.0477,-4.0805,24.0777,12.2000,28.2000,0.0000,25.8525,12.6901,2.0470,4.0764,1.6363},
				{0.0200,11.9066,3.7823,22.5352,11.6670,-2.1391,-4.5725,24.3217,12.2000,28.2000,0.0000,26.1072,12.7329,2.1384,4.5679,1.6461},
				{0.0200,11.8869,4.0255,22.7677,11.6222,-2.2408,-5.0814,24.5657,12.2000,28.2000,0.0000,26.3627,12.7777,2.2399,5.0763,1.6568},
				{0.0200,11.8646,4.2685,22.9992,11.5752,-2.3529,-5.6061,24.8097,12.2000,28.2000,0.0000,26.6192,12.8247,2.3519,5.6002,1.6683},
				{0.0200,11.8394,4.5112,23.2297,11.5256,-2.4758,-6.1439,25.0537,12.2000,28.2000,0.0000,26.8767,12.8742,2.4746,6.1372,1.6807},
				{0.0200,11.8110,4.7535,23.4592,11.4734,-2.6096,-6.6900,25.2977,12.2000,28.2000,0.0000,27.1352,12.9264,2.6083,6.6826,1.6941},
				{0.0200,11.7793,4.9955,23.6875,11.4184,-2.7543,-7.2372,25.5417,12.2000,28.2000,0.0000,27.3949,12.9814,2.7529,7.2286,1.7085},
				{0.0200,11.7439,5.2369,23.9147,11.3602,-2.9098,-7.7742,25.7857,12.2000,28.2000,0.0000,27.6557,13.0396,2.9082,7.7644,1.7240},
				{0.0200,11.7047,5.4777,24.1407,11.2987,-3.0755,-8.2855,26.0297,12.2000,28.2000,0.0000,27.9177,13.1011,3.0737,8.2744,1.7406},
				{0.0200,11.6613,5.7178,24.3654,11.2336,-3.2505,-8.7499,26.2737,12.2000,28.2000,0.0000,28.1810,13.1660,3.2484,8.7376,1.7584},
				{0.0200,11.6136,5.9571,24.5887,11.1650,-3.4333,-9.1395,26.5177,12.2000,28.2000,0.0000,28.4457,13.2347,3.4309,9.1256,1.7775},
				{0.0200,11.5611,6.1954,24.8105,11.0925,-3.6216,-9.4178,26.7617,12.2000,28.2000,0.0000,28.7118,13.3070,3.6190,9.4025,1.7979},
				{0.0200,11.5036,6.4325,25.0309,11.0163,-3.8124,-9.5390,27.0057,12.2000,28.2000,0.0000,28.9795,13.3832,3.8094,9.5222,1.8197},
				{0.0200,11.4408,6.6683,25.2496,10.9363,-4.0014,-9.4466,27.2497,12.2000,28.2000,0.0000,29.2488,13.4632,3.9980,9.4283,1.8430},
				{0.0200,11.3723,6.9025,25.4666,10.8526,-4.1828,-9.0727,27.4937,12.2000,28.2000,0.0000,29.5197,13.5468,4.1790,9.0532,1.8678},
				{0.0200,11.2978,7.1348,25.6820,10.7656,-4.3496,-8.3390,27.7377,12.2000,28.2000,0.0000,29.7924,13.6337,4.3454,8.3187,1.8943},
				{0.0200,11.2171,7.3650,25.8955,10.6758,-4.4928,-7.1591,27.9817,12.2000,28.2000,0.0000,30.0668,13.7234,4.4882,7.1386,1.9224},
				{0.0200,11.1297,7.5928,26.1071,10.5837,-4.6016,-5.4431,28.2257,12.2000,28.2000,0.0000,30.3432,13.8154,4.5966,5.4231,1.9521},
				{0.0200,11.0353,7.8179,26.3170,10.4905,-4.6638,-3.1060,28.4697,12.2000,28.2000,0.0000,30.6213,13.9085,4.6584,3.0877,1.9837},
				{0.0200,10.9338,8.0397,26.5249,10.3972,-4.6654,-0.0799,28.7137,12.2000,28.2000,0.0000,30.9014,14.0017,4.6597,0.0648,2.0169},
				{0.0200,10.8247,8.2580,26.7310,10.3053,-4.5920,3.6697,28.9577,12.2000,28.2000,0.0000,31.1832,14.0934,4.5861,-3.6800,2.0518},
				{0.0200,10.7079,8.4722,26.9353,10.2167,-4.4295,8.1251,29.2017,12.2000,28.2000,0.0000,31.4669,14.1819,4.4235,-8.1285,2.0883},
				{0.0200,10.5832,8.6819,27.1380,10.1334,-4.1655,13.1961,29.4457,12.2000,28.2000,0.0000,31.7522,14.2651,4.1597,-13.1909,2.1264},
				{0.0200,10.4505,8.8866,27.3392,10.0576,-3.7914,18.7048,29.6897,12.2000,28.2000,0.0000,32.0390,14.3408,3.7859,-18.6891,2.1659},
				{0.0200,10.3097,9.0858,27.5390,9.9915,-3.3039,24.3793,29.9337,12.2000,28.2000,0.0000,32.3271,14.4068,3.2989,-24.3519,2.2066},
				{0.0200,10.1607,9.2791,27.7377,9.9374,-2.7066,29.8632,30.1777,12.2000,28.2000,0.0000,32.6163,14.4609,2.7024,-29.8240,2.2483},
				{0.0200,10.0038,9.4659,27.9357,9.8971,-2.0117,34.7458,30.4217,12.2000,28.2000,0.0000,32.9064,14.5010,2.0085,-34.6955,2.2907},
				{0.0200,9.8390,9.6459,28.1331,9.8724,-1.2395,38.6098,30.6657,12.2000,28.2000,0.0000,33.1969,14.5258,1.2375,-38.5503,2.3336},
				{0.0200,9.6667,9.8186,28.3304,9.8640,-0.4176,41.0926,30.9097,12.2000,28.2000,0.0000,33.4876,14.5341,0.4169,-41.0272,2.3767},
				{0.0200,9.4871,9.9837,28.5279,9.8724,0.4213,41.9469,31.1537,12.2000,28.2000,0.0000,33.7781,14.5257,-0.4206,-41.8794,2.4196},
				{0.0200,9.3006,10.1410,28.7258,9.8973,1.2430,41.0849,31.3977,12.2000,28.2000,0.0000,34.0681,14.5009,-1.2410,-41.0194,2.4620},
				{0.0200,9.1077,10.2904,28.9246,9.9376,2.0149,38.5950,31.6417,12.2000,28.2000,0.0000,34.3573,14.4606,-2.0117,-38.5356,2.5037},
				{0.0200,8.9087,10.4316,29.1244,9.9918,2.7094,34.7254,31.8857,12.2000,28.2000,0.0000,34.6454,14.4065,-2.7052,-34.6751,2.5444},
				{0.0200,8.7043,10.5648,29.3255,10.0579,3.3062,29.8389,32.1297,12.2000,28.2000,0.0000,34.9322,14.3405,-3.3012,-29.7996,2.5838},
				{0.0200,8.4949,10.6899,29.5282,10.1338,3.7933,24.3526,32.3737,12.2000,28.2000,0.0000,35.2175,14.2648,-3.7877,-24.3254,2.6219},
				{0.0200,8.2809,10.8072,29.7326,10.2171,4.1668,18.6778,32.6177,12.2000,28.2000,0.0000,35.5012,14.1815,-4.1610,-18.6620,2.6585},
				{0.0200,8.0629,10.9167,29.9387,10.3057,4.4302,13.1699,32.8617,12.2000,28.2000,0.0000,35.7830,14.0931,-4.4243,-13.1646,2.6934},
				{0.0200,7.8412,11.0187,30.1466,10.3975,4.5922,8.1007,33.1057,12.2000,28.2000,0.0000,36.0631,14.0013,-4.5864,-8.1042,2.7266},
				{0.0200,7.6164,11.1136,30.3564,10.4908,4.6652,3.6478,33.3497,12.2000,28.2000,0.0000,36.3412,13.9081,-4.6595,-3.6579,2.7581},
				{0.0200,7.3888,11.2014,30.5681,10.5841,4.6632,-0.0993,33.5937,12.2000,28.2000,0.0000,36.6175,13.8150,-4.6578,0.0842,2.7879},
				{0.0200,7.1588,11.2827,30.7817,10.6761,4.6007,-3.1228,33.8377,12.2000,28.2000,0.0000,36.8920,13.7231,-4.5957,3.1044,2.8159},
				{0.0200,6.9266,11.3577,30.9970,10.7660,4.4916,-5.4575,34.0817,12.2000,28.2000,0.0000,37.1646,13.6333,-4.4870,5.4376,2.8424},
				{0.0200,6.6925,11.4267,31.2140,10.8529,4.3482,-7.1716,34.3257,12.2000,28.2000,0.0000,37.4356,13.5465,-4.3440,7.1509,2.8672},
				{0.0200,6.4569,11.4900,31.4328,10.9365,4.1812,-8.3498,34.5697,12.2000,28.2000,0.0000,37.7048,13.4629,-4.1774,8.3296,2.8905},
				{0.0200,6.2199,11.5480,31.6531,11.0165,3.9995,-9.0824,34.8137,12.2000,28.2000,0.0000,37.9725,13.3830,-3.9961,9.0628,2.9123},
				{0.0200,5.9817,11.6010,31.8749,11.0927,3.8104,-9.4554,35.0577,12.2000,28.2000,0.0000,38.2386,13.3068,-3.8074,9.4372,2.9327},
				{0.0200,5.7426,11.6493,32.0982,11.1651,3.6194,-9.5475,35.3017,12.2000,28.2000,0.0000,38.5033,13.2345,-3.6168,9.5306,2.9518},
				{0.0200,5.5025,11.6932,32.3229,11.2338,3.4309,-9.4262,35.5457,12.2000,28.2000,0.0000,38.7666,13.1659,-3.4286,9.4109,2.9696},
				{0.0200,5.2618,11.7330,32.5489,11.2987,3.2480,-9.1480,35.7897,12.2000,28.2000,0.0000,39.0287,13.1010,-3.2459,9.1343,2.9862},
				{0.0200,5.0205,11.7688,32.7761,11.3602,3.0728,-8.7590,36.0337,12.2000,28.2000,0.0000,39.2895,13.0396,-3.0709,8.7466,3.0017},
				{0.0200,4.7786,11.8011,33.0045,11.4183,2.9069,-8.2952,36.2777,12.2000,28.2000,0.0000,39.5491,12.9815,-2.9052,8.2843,3.0161},
				{0.0200,4.5363,11.8300,33.2339,11.4733,2.7512,-7.7849,36.5217,12.2000,28.2000,0.0000,39.8076,12.9265,-2.7497,7.7752,3.0295},
				{0.0200,4.2937,11.8558,33.4644,11.5255,2.6062,-7.2492,36.7657,12.2000,28.2000,0.0000,40.0651,12.8744,-2.6049,7.2405,3.0419},
				{0.0200,4.0508,11.8786,33.6959,11.5749,2.4721,-6.7034,37.0097,12.2000,28.2000,0.0000,40.3216,12.8250,-2.4710,6.6959,3.0534},
				{0.0200,3.8076,11.8988,33.9284,11.6219,2.3489,-6.1589,37.2537,12.2000,28.2000,0.0000,40.5772,12.7780,-2.3480,6.1521,3.0641},
				{0.0200,3.5642,11.9165,34.1617,11.6666,2.2365,-5.6228,37.4977,12.2000,28.2000,0.0000,40.8318,12.7333,-2.2356,5.6171,3.0739},
				{0.0200,3.3207,11.9319,34.3959,11.7093,2.1345,-5.1004,37.7417,12.2000,28.2000,0.0000,41.0856,12.6906,-2.1337,5.0951,3.0830},
				{0.0200,3.0771,11.9452,34.6309,11.7501,2.0426,-4.5938,37.9857,12.2000,28.2000,0.0000,41.3386,12.6498,-2.0419,4.5893,3.0912},
				{0.0200,2.8334,11.9565,34.8667,11.7894,1.9605,-4.1045,38.2297,12.2000,28.2000,0.0000,41.5908,12.6106,-1.9599,4.1004,3.0988},
				{0.0200,2.5895,11.9661,35.1032,11.8271,1.8879,-3.6322,38.4737,12.2000,28.2000,0.0000,41.8423,12.5728,-1.8874,3.6285,3.1057},
				{0.0200,2.3570,11.9737,35.3295,11.3144,-25.6374,-1376.2627,38.7064,11.6360,-28.2000,0.0000,42.0815,11.9576,-30.7619,-1443.7280,3.1116},
				{0.0200,2.1356,11.9798,35.5454,10.7959,-25.9232,-14.2920,38.9278,11.0720,-28.2000,0.0000,42.3084,11.3481,-30.4763,14.2825,3.1167},
				{0.0200,1.9255,11.9846,35.7509,10.2723,-26.1789,-12.7836,39.1380,10.5080,-28.2000,0.0000,42.5233,10.7437,-30.2207,12.7766,3.1211},
				{0.0200,1.7266,11.9883,35.9458,9.7441,-26.4088,-11.4979,39.3369,9.9440,-28.2000,0.0000,42.7262,10.1438,-29.9909,11.4926,3.1247},
				{0.0200,1.5391,11.9911,36.1300,9.2118,-26.6167,-10.3946,39.5245,9.3800,-28.2000,0.0000,42.9171,9.5482,-29.7831,10.3908,3.1278},
				{0.0200,1.3628,11.9933,36.3035,8.6757,-26.8055,-9.4399,39.7008,8.8160,-28.2000,0.0000,43.0963,8.9563,-29.5943,9.4371,3.1304},
				{0.0200,1.1977,11.9950,36.4662,8.1361,-26.9776,-8.6048,39.8658,8.2520,-28.2000,0.0000,43.2636,8.3678,-29.4223,8.6027,3.1326},
				{0.0200,1.0440,11.9962,36.6181,7.5934,-27.1349,-7.8649,40.0196,7.6880,-28.2000,0.0000,43.4193,7.7825,-29.2650,7.8634,3.1343},
				{0.0200,0.9015,11.9972,36.7591,7.0479,-27.2789,-7.1994,40.1621,7.1240,-28.2000,0.0000,43.5633,7.2001,-29.1210,7.1984,3.1357},
				{0.0200,0.7703,11.9979,36.8891,6.4997,-27.4107,-6.5908,40.2933,6.5600,-28.2000,0.0000,43.6957,6.6203,-28.9892,6.5900,3.1368},
				{0.0200,0.6504,11.9984,37.0080,5.9490,-27.5312,-6.0240,40.4132,5.9960,-28.2000,0.0000,43.8165,6.0430,-28.8688,6.0236,3.1377},
				{0.0200,0.5417,11.9988,37.1160,5.3962,-27.6410,-5.4870,40.5218,5.4320,-28.2000,0.0000,43.9259,5.4678,-28.7590,5.4867,3.1383},
				{0.0200,0.4444,11.9991,37.2128,4.8414,-27.7403,-4.9692,40.6192,4.8680,-28.2000,0.0000,44.0238,4.8946,-28.6597,4.9690,3.1388},
				{0.0200,0.3583,11.9993,37.2985,4.2848,-27.8296,-4.4625,40.7053,4.3040,-28.2000,0.0000,44.1102,4.3232,-28.5704,4.4624,3.1392},
				{0.0200,0.2835,11.9995,37.3730,3.7266,-27.9088,-3.9605,40.7801,3.7400,-28.2000,0.0000,44.1853,3.7534,-28.4912,3.9604,3.1394},
				{0.0200,0.2200,11.9996,37.4364,3.1671,-27.9780,-3.4581,40.8436,3.1760,-28.2000,0.0000,44.2490,3.1849,-28.4220,3.4581,3.1396},
				{0.0200,0.1677,11.9997,37.4885,2.6063,-28.0370,-2.9522,40.8958,2.6120,-28.2000,0.0000,44.3014,2.6177,-28.3630,2.9522,3.1397},
				{0.0200,0.1268,11.9998,37.5294,2.0446,-28.0858,-2.4407,40.9368,2.0480,-28.2000,0.0000,44.3424,2.0514,-28.3142,2.4407,3.1398},
				{0.0200,0.0971,11.9998,37.5590,1.4821,-28.1243,-1.9228,40.9665,1.4840,-28.2000,0.0000,44.3721,1.4859,-28.2757,1.9228,3.1398},
				{0.0200,0.0787,11.9999,37.5774,0.9191,-28.1522,-1.3987,40.9849,0.9200,-28.2000,0.0000,44.3905,0.9209,-28.2478,1.3987,3.1398},
				{0.0200,0.0716,11.9999,37.5845,0.3557,-28.1696,-0.8696,40.9920,0.3560,-28.2000,0.0000,44.3977,0.3563,-28.2304,0.8696,3.1398},
				{0.0200,0.0757,11.9999,37.5887,0.2078,-7.3936,1038.8027,40.9878,-0.2080,-28.2000,0.0000,44.4018,0.2082,-7.4064,1041.1973,3.1398},

	    };

	@Override
	public double[][] getPath() {
	    return points;
	}
}