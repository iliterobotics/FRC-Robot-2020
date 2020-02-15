package us.ilite.robot.auto.paths;

import com.team319.trajectory.Path;

public class Kate_test_path extends Path {
   // dt,x,y,left.pos,left.vel,left.acc,left.jerk,center.pos,center.vel,center.acc,center.jerk,right.pos,right.vel,right.acc,right.jerk,heading
	private static final double[][] points = {
				{0.0200,29.9960,-6.0000,0.0040,0.2000,10.0000,0.0000,0.0040,0.2000,10.0000,0.0000,0.0040,0.2000,10.0000,0.0000,-3.1416},
				{0.0200,29.9880,-6.0000,0.0120,0.3991,9.9560,-2.1988,0.0120,0.4000,10.0000,0.0000,0.0120,0.4009,10.0440,2.1988,-3.1416},
				{0.0200,29.9760,-6.0000,0.0239,0.5970,9.8960,-3.0029,0.0240,0.6000,10.0000,0.0000,0.0241,0.6030,10.1040,3.0029,-3.1415},
				{0.0200,29.9600,-6.0000,0.0398,0.7930,9.7985,-4.8737,0.0400,0.8000,10.0000,0.0000,0.0402,0.8070,10.2015,4.8737,-3.1414},
				{0.0200,29.9400,-6.0000,0.0595,0.9864,9.6701,-6.4215,0.0600,1.0000,10.0000,0.0000,0.0605,1.0136,10.3299,6.4215,-3.1411},
				{0.0200,29.9160,-6.0000,0.0830,1.1766,9.5118,-7.9134,0.0840,1.2000,10.0000,0.0000,0.0850,1.2234,10.4882,7.9134,-3.1407},
				{0.0200,29.8880,-6.0001,0.1103,1.3631,9.3249,-9.3469,0.1120,1.4000,10.0000,0.0000,0.1137,1.4369,10.6751,9.3469,-3.1400},
				{0.0200,29.8560,-6.0001,0.1412,1.5454,9.1103,-10.7261,0.1440,1.6000,10.0000,0.0000,0.1468,1.6546,10.8897,10.7260,-3.1390},
				{0.0200,29.8200,-6.0002,0.1757,1.7227,8.8691,-12.0621,0.1800,1.8000,10.0000,0.0000,0.1843,1.8773,11.1309,12.0619,-3.1376},
				{0.0200,29.7800,-6.0004,0.2136,1.8948,8.6016,-13.3733,0.2200,2.0000,10.0000,0.0000,0.2264,2.1052,11.3984,13.3729,-3.1357},
				{0.0200,29.7360,-6.0008,0.2548,2.0609,8.3079,-14.6845,0.2640,2.2000,10.0000,0.0000,0.2732,2.3391,11.6920,14.6839,-3.1331},
				{0.0200,29.6880,-6.0012,0.2992,2.2207,7.9874,-16.0272,0.3120,2.4000,10.0000,0.0000,0.3248,2.5793,12.0126,16.0262,-3.1298},
				{0.0200,29.6360,-6.0020,0.3467,2.3734,7.6386,-17.4380,0.3640,2.6000,10.0000,0.0000,0.3813,2.8266,12.3613,17.4365,-3.1256},
				{0.0200,29.5800,-6.0030,0.3970,2.5186,7.2595,-18.9580,0.4200,2.8000,10.0000,0.0000,0.4430,3.0814,12.7404,18.9557,-3.1204},
				{0.0200,29.5200,-6.0045,0.4501,2.6556,6.8469,-20.6308,0.4800,3.0000,10.0000,0.0000,0.5099,3.3444,13.1530,20.6276,-3.1141},
				{0.0200,29.4561,-6.0065,0.5058,2.7835,6.3968,-22.5005,0.5440,3.2000,10.0000,0.0000,0.5822,3.6165,13.6029,22.4959,-3.1064},
				{0.0200,29.3881,-6.0092,0.5639,2.9016,5.9047,-24.6084,0.6120,3.4000,10.0000,0.0000,0.6601,3.8984,14.0949,24.6020,-3.0972},
				{0.0200,29.3162,-6.0127,0.6240,3.0089,5.3649,-26.9874,0.6840,3.6000,10.0000,0.0000,0.7440,4.1911,14.6345,26.9787,-3.0863},
				{0.0200,29.2404,-6.0174,0.6861,3.1043,4.7718,-29.6547,0.7600,3.8000,10.0000,0.0000,0.8339,4.4956,15.2273,29.6428,-3.0735},
				{0.0200,29.1606,-6.0234,0.7499,3.1867,4.1199,-32.5983,0.8400,4.0000,10.0000,0.0000,0.9301,4.8132,15.8790,32.5823,-3.0585},
				{0.0200,29.0769,-6.0311,0.8149,3.2548,3.4047,-35.7587,0.9240,4.2000,10.0000,0.0000,1.0330,5.1451,16.5937,35.7374,-3.0411},
				{0.0200,28.9895,-6.0408,0.8811,3.3073,2.6247,-38.9998,1.0120,4.4000,10.0000,0.0000,1.1429,5.4925,17.3732,38.9719,-3.0209},
				{0.0200,28.8983,-6.0529,0.9480,3.3430,1.7833,-42.0681,1.1040,4.6000,10.0000,0.0000,1.2600,5.8568,18.2138,42.0317,-2.9978},
				{0.0200,28.8035,-6.0679,1.0152,3.3608,0.8926,-44.5366,1.2000,4.8000,10.0000,0.0000,1.3848,6.2389,19.1036,44.4900,-2.9712},
				{0.0200,28.7052,-6.0864,1.0824,3.3604,-0.0221,-45.7363,1.3000,5.0000,10.0000,0.0000,1.5176,6.6392,20.0172,45.6778,-2.9410},
				{0.0200,28.6075,-6.1079,1.1467,3.2164,-7.1989,-358.8402,1.4000,5.0000,10.0000,0.0000,1.6533,6.7831,7.1954,-641.0865,-2.9082},
				{0.0200,28.5107,-6.1327,1.2082,3.0740,-7.1223,3.8333,1.5000,5.0000,10.0000,0.0000,1.7918,6.9255,7.1185,-3.8459,-2.8727},
				{0.0200,28.4148,-6.1611,1.2669,2.9360,-6.8994,11.1441,1.6000,5.0000,10.0000,0.0000,1.9330,7.0634,6.8955,-11.1519,-2.8346},
				{0.0200,28.3201,-6.1932,1.3230,2.8061,-6.4957,20.1815,1.7000,5.0000,10.0000,0.0000,2.0769,7.1932,6.4918,-20.1825,-2.7942},
				{0.0200,28.2268,-6.2292,1.3768,2.6884,-5.8835,30.6105,1.8000,5.0000,10.0000,0.0000,2.2231,7.3108,5.8798,-30.6024,-2.7516},
				{0.0200,28.1352,-6.2693,1.4285,2.5874,-5.0478,41.7869,1.9000,5.0000,10.0000,0.0000,2.3714,7.4117,5.0444,-41.7680,-2.7071},
				{0.0200,28.0455,-6.3134,1.4787,2.5076,-3.9926,52.7618,2.0000,5.0000,10.0000,0.0000,2.5212,7.4915,3.9898,-52.7317,-2.6612},
				{0.0200,27.9579,-6.3617,1.5278,2.4527,-2.7451,62.3741,2.1000,5.0000,10.0000,0.0000,2.6721,7.5464,2.7431,-62.3334,-2.6142},
				{0.0200,27.8727,-6.4141,1.5763,2.4256,-1.3564,69.4345,2.2000,5.0000,10.0000,0.0000,2.8236,7.5735,1.3554,-69.3857,-2.5668},
				{0.0200,27.7901,-6.4704,1.6248,2.4276,0.1030,72.9704,2.3000,5.0000,10.0000,0.0000,2.9750,7.5714,-0.1029,-72.9174,-2.5193},
				{0.0200,27.7103,-6.5306,1.6740,2.4587,1.5521,72.4566,2.4000,5.0000,10.0000,0.0000,3.1258,7.5404,-1.5510,-72.4041,-2.4725},
				{0.0200,27.6333,-6.5944,1.7243,2.5169,2.9111,67.9497,2.5000,5.0000,10.0000,0.0000,3.2755,7.4822,-2.9091,-67.9023,-2.4267},
				{0.0200,27.5592,-6.6616,1.7763,2.5991,4.1126,60.0728,2.6000,5.0000,10.0000,0.0000,3.4235,7.4000,-4.1098,-60.0342,-2.3825},
				{0.0200,27.4882,-6.7319,1.8303,2.7013,5.1097,49.8570,2.7000,5.0000,10.0000,0.0000,3.5694,7.2979,-5.1063,-49.8292,-2.3401},
				{0.0200,27.4201,-6.8052,1.8867,2.8189,5.8797,38.5004,2.8000,5.0000,10.0000,0.0000,3.7130,7.1804,-5.8760,-38.4840,-2.2999},
				{0.0200,27.3549,-6.8810,1.9457,2.9474,6.4224,27.1310,2.9000,5.0000,10.0000,0.0000,3.8541,7.0520,-6.4185,-27.1250,-2.2621},
				{0.0200,27.2925,-6.9592,2.0073,3.0825,6.7551,16.6371,3.0000,5.0000,10.0000,0.0000,3.9924,6.9170,-6.7513,-16.6397,-2.2267},
				{0.0200,27.2329,-7.0394,2.0717,3.2206,6.9070,7.5938,3.1000,5.0000,10.0000,0.0000,4.1280,6.7789,-6.9034,-7.6029,-2.1939},
				{0.0200,27.1758,-7.1215,2.1389,3.3589,6.9124,0.2721,3.2000,5.0000,10.0000,0.0000,4.2608,6.6407,-6.9091,-0.2854,-2.1637},
				{0.0200,27.1211,-7.2052,2.2088,3.4950,6.8064,-5.3003,3.3000,5.0000,10.0000,0.0000,4.3909,6.5047,-6.8034,5.2844,-2.1360},
				{0.0200,27.0686,-7.2903,2.2813,3.6274,6.6212,-9.2588,3.4000,5.0000,10.0000,0.0000,4.5183,6.3723,-6.6185,9.2421,-2.1107},
				{0.0200,27.0182,-7.3767,2.3565,3.7551,6.3847,-11.8284,3.5000,5.0000,10.0000,0.0000,4.6432,6.2447,-6.3823,11.8118,-2.0877},
				{0.0200,26.9697,-7.4642,2.4340,3.8775,6.1194,-13.2649,3.6000,5.0000,10.0000,0.0000,4.7657,6.1223,-6.1173,13.2491,-2.0670},
				{0.0200,26.9229,-7.5525,2.5139,3.9944,5.8430,-13.8174,3.7000,5.0000,10.0000,0.0000,4.8858,6.0055,-5.8413,13.8028,-2.0485},
				{0.0200,26.8777,-7.6417,2.5960,4.1057,5.5689,-13.7069,3.8000,5.0000,10.0000,0.0000,5.0037,5.8941,-5.5674,13.6938,-2.0320},
				{0.0200,26.8339,-7.7316,2.6802,4.2119,5.3065,-13.1174,3.9000,5.0000,10.0000,0.0000,5.1194,5.7880,-5.3053,13.1057,-2.0175},
				{0.0200,26.7913,-7.8221,2.7665,4.3131,5.0626,-12.1949,4.0000,5.0000,10.0000,0.0000,5.2332,5.6868,-5.0616,12.1846,-2.0048},
				{0.0200,26.7497,-7.9130,2.8547,4.4100,4.8416,-11.0505,4.1000,5.0000,10.0000,0.0000,5.3450,5.5900,-4.8408,11.0414,-1.9939},
				{0.0200,26.7091,-8.0044,2.9448,4.5029,4.6463,-9.7657,4.2000,5.0000,10.0000,0.0000,5.4549,5.4971,-4.6456,9.7577,-1.9848},
				{0.0200,26.6692,-8.0961,3.0366,4.5925,4.4784,-8.3983,4.3000,5.0000,10.0000,0.0000,5.5631,5.4075,-4.4778,8.3912,-1.9773},
				{0.0200,26.6300,-8.1881,3.1302,4.6792,4.3386,-6.9869,4.4000,5.0000,10.0000,0.0000,5.6695,5.3208,-4.3382,6.9806,-1.9713},
				{0.0200,26.5912,-8.2803,3.2255,4.7638,4.2275,-5.5564,4.5000,5.0000,10.0000,0.0000,5.7742,5.2362,-4.2272,5.5507,-1.9670},
				{0.0200,26.5527,-8.3726,3.3224,4.8467,4.1451,-4.1209,4.6000,5.0000,10.0000,0.0000,5.8773,5.1533,-4.1448,4.1157,-1.9642},
				{0.0200,26.5145,-8.4650,3.4210,4.9285,4.0913,-2.6873,4.7000,5.0000,10.0000,0.0000,5.9787,5.0715,-4.0912,2.6824,-1.9629},
				{0.0200,26.4763,-8.5574,3.5212,5.0098,4.0662,-1.2574,4.8000,5.0000,10.0000,0.0000,6.0785,4.9902,-4.0661,1.2526,-1.9630},
				{0.0200,26.4380,-8.6498,3.6230,5.0912,4.0696,0.1703,4.9000,5.0000,10.0000,0.0000,6.1767,4.9088,-4.0696,-0.1750,-1.9647},
				{0.0200,26.3995,-8.7420,3.7265,5.1732,4.1015,1.5982,5.0000,5.0000,10.0000,0.0000,6.2732,4.8267,-4.1017,-1.6029,-1.9679},
				{0.0200,26.3606,-8.8342,3.8316,5.2565,4.1621,3.0288,5.1000,5.0000,10.0000,0.0000,6.3681,4.7435,-4.1624,-3.0338,-1.9726},
				{0.0200,26.3212,-8.9261,3.9384,5.3415,4.2514,4.4628,5.2000,5.0000,10.0000,0.0000,6.4613,4.6585,-4.2517,-4.4681,-1.9789},
				{0.0200,26.2812,-9.0177,4.0470,5.4289,4.3693,5.8974,5.3000,5.0000,10.0000,0.0000,6.5527,4.5711,-4.3698,-5.9033,-1.9868},
				{0.0200,26.2403,-9.1090,4.1574,5.5192,4.5158,7.3243,5.4000,5.0000,10.0000,0.0000,6.6423,4.4807,-4.5164,-7.3308,-1.9964},
				{0.0200,26.1985,-9.1999,4.2696,5.6130,4.6904,8.7270,5.5000,5.0000,10.0000,0.0000,6.7300,4.3869,-4.6911,-8.7343,-2.0077},
				{0.0200,26.1556,-9.2902,4.3839,5.7109,4.8919,10.0777,5.6000,5.0000,10.0000,0.0000,6.8158,4.2891,-4.8928,-10.0859,-2.0208},
				{0.0200,26.1115,-9.3799,4.5001,5.8132,5.1186,11.3334,5.7000,5.0000,10.0000,0.0000,6.8995,4.1867,-5.1197,-11.3428,-2.0358},
				{0.0200,26.0659,-9.4689,4.6185,5.9206,5.3672,12.4314,5.8000,5.0000,10.0000,0.0000,6.9811,4.0793,-5.3685,-12.4421,-2.0528},
				{0.0200,26.0187,-9.5571,4.7392,6.0332,5.6329,13.2834,5.9000,5.0000,10.0000,0.0000,7.0605,3.9666,-5.6344,-13.2954,-2.0718},
				{0.0200,25.9698,-9.6443,4.8622,6.1514,5.9083,13.7704,6.0000,5.0000,10.0000,0.0000,7.1374,3.8484,-5.9101,-13.7839,-2.0930},
				{0.0200,25.9189,-9.7304,4.9877,6.2751,6.1830,13.7374,6.1000,5.0000,10.0000,0.0000,7.2119,3.7247,-6.1852,-13.7523,-2.1166},
				{0.0200,25.8659,-9.8152,5.1158,6.4039,6.4429,12.9916,6.2000,5.0000,10.0000,0.0000,7.2838,3.5958,-6.4453,-13.0075,-2.1424},
				{0.0200,25.8106,-9.8985,5.2466,6.5373,6.6689,11.3039,6.3000,5.0000,10.0000,0.0000,7.3531,3.4624,-6.6717,-11.3206,-2.1708},
				{0.0200,25.7529,-9.9802,5.3800,6.6741,6.8374,8.4215,6.4000,5.0000,10.0000,0.0000,7.4196,3.3256,-6.8405,-8.4381,-2.2016},
				{0.0200,25.6926,-10.0599,5.5163,6.8124,6.9192,4.0922,6.5000,5.0000,10.0000,0.0000,7.4833,3.1871,-6.9226,-4.1075,-2.2351},
				{0.0200,25.6296,-10.1376,5.6553,6.9501,6.8814,-1.8929,6.6000,5.0000,10.0000,0.0000,7.5443,3.0494,-6.8850,1.8803,-2.2710},
				{0.0200,25.5637,-10.2128,5.7970,7.0838,6.6886,-9.6367,6.7000,5.0000,10.0000,0.0000,7.6026,2.9156,-6.6924,9.6290,-2.3094},
				{0.0200,25.4949,-10.2854,5.9412,7.2100,6.3074,-19.0595,6.8000,5.0000,10.0000,0.0000,7.6584,2.7893,-6.3113,19.0587,-2.3502},
				{0.0200,25.4231,-10.3550,6.0876,7.3242,5.7110,-29.8210,6.9000,5.0000,10.0000,0.0000,7.7119,2.6750,-5.7147,29.8294,-2.3930},
				{0.0200,25.3484,-10.4214,6.2361,7.4219,4.8856,-41.2691,7.0000,5.0000,10.0000,0.0000,7.7635,2.5773,-4.8889,41.2882,-2.4377},
				{0.0200,25.2707,-10.4843,6.3861,7.4986,3.8367,-52.4479,7.1000,5.0000,10.0000,0.0000,7.8135,2.5005,-3.8393,52.4785,-2.4838},
				{0.0200,25.1902,-10.5436,6.5371,7.5505,2.5928,-62.1953,7.2000,5.0000,10.0000,0.0000,7.8625,2.4486,-2.5946,62.2364,-2.5308},
				{0.0200,25.1069,-10.5990,6.6886,7.5746,1.2061,-69.3318,7.3000,5.0000,10.0000,0.0000,7.9109,2.4244,-1.2070,69.3809,-2.5783},
				{0.0200,25.0211,-10.6504,6.8400,7.5696,-0.2520,-72.9062,7.4000,5.0000,10.0000,0.0000,7.9595,2.4295,0.2522,72.9593,-2.6256},
				{0.0200,24.9330,-10.6976,6.9907,7.5356,-1.7005,-72.4233,7.5000,5.0000,10.0000,0.0000,8.0088,2.4635,1.7017,72.4757,-2.6724},
				{0.0200,24.8428,-10.7408,7.1402,7.4744,-3.0599,-67.9718,7.6000,5.0000,10.0000,0.0000,8.0593,2.5248,3.0621,68.0189,-2.7180},
				{0.0200,24.7508,-10.7799,7.2879,7.3891,-4.2639,-60.2013,7.7000,5.0000,10.0000,0.0000,8.1115,2.6101,4.2669,60.2397,-2.7621},
				{0.0200,24.6571,-10.8149,7.4336,7.2838,-5.2671,-50.1586,7.8000,5.0000,10.0000,0.0000,8.1658,2.7155,5.2706,50.1860,-2.8042},
				{0.0200,24.5621,-10.8461,7.5769,7.1628,-6.0480,-39.0453,7.9000,5.0000,10.0000,0.0000,8.2225,2.8365,6.0518,39.0614,-2.8440},
				{0.0200,24.4660,-10.8736,7.7175,7.0306,-6.6077,-27.9839,8.0000,5.0000,10.0000,0.0000,8.2819,2.9688,6.6116,27.9897,-2.8815},
				{0.0200,24.3689,-10.8976,7.8553,6.8914,-6.9647,-17.8532,8.1000,5.0000,10.0000,0.0000,8.3441,3.1081,6.9686,17.8504,-2.9163},
				{0.0200,24.2711,-10.9184,7.9903,6.7484,-7.1491,-9.2200,8.2000,5.0000,10.0000,0.0000,8.4091,3.2512,7.1528,9.2107,-2.9486},
				{0.0200,24.1727,-10.9361,8.1224,6.6044,-7.1962,-2.3510,8.3000,5.0000,10.0000,0.0000,8.4770,3.3952,7.1996,2.3377,-2.9782},
				{0.0200,24.0738,-10.9510,8.2516,6.4616,-7.1417,2.7228,8.4000,5.0000,10.0000,0.0000,8.5478,3.5381,7.1448,-2.7387,-3.0051},
				{0.0200,23.9746,-10.9634,8.3780,6.3212,-7.0191,6.1283,8.5000,5.0000,10.0000,0.0000,8.6213,3.6785,7.0219,-6.1450,-3.0295},
				{0.0200,23.8751,-10.9735,8.5017,6.1841,-6.8577,8.0737,8.6000,5.0000,10.0000,0.0000,8.6976,3.8157,6.8601,-8.0904,-3.0513},
				{0.0200,23.7754,-10.9815,8.6227,6.0504,-6.6818,8.7930,8.7000,5.0000,10.0000,0.0000,8.7766,3.9494,6.6839,-8.8089,-3.0707},
				{0.0200,23.6756,-10.9877,8.7411,5.9202,-6.5117,8.5062,8.8000,5.0000,10.0000,0.0000,8.8582,4.0797,6.5135,-8.5211,-3.0876},
				{0.0200,23.5757,-10.9924,8.8570,5.7929,-6.3637,7.3982,8.9000,5.0000,10.0000,0.0000,8.9424,4.2070,6.3653,-7.4119,-3.1022},
				{0.0200,23.4758,-10.9957,8.9703,5.6679,-6.2516,5.6077,9.0000,5.0000,10.0000,0.0000,9.0290,4.3320,6.2529,-5.6202,-3.1145},
				{0.0200,23.3758,-10.9978,9.0812,5.5442,-6.1871,3.2235,9.1000,5.0000,10.0000,0.0000,9.1181,4.4558,6.1882,-3.2351,-3.1246},
				{0.0200,23.2758,-10.9991,9.1896,5.4205,-6.1814,0.2849,9.2000,5.0000,10.0000,0.0000,9.2097,4.5794,6.1822,-0.2957,-3.1323},
				{0.0200,23.1758,-10.9998,9.2955,5.2956,-6.2458,-3.2194,9.3000,5.0000,10.0000,0.0000,9.3038,4.7044,6.2464,3.2088,-3.1378},
				{0.0200,23.0758,-11.0000,9.3989,5.1678,-6.3928,-7.3515,9.4000,5.0000,10.0000,0.0000,9.4004,4.8322,6.3932,7.3408,-3.1409},
				{0.0200,22.9758,-11.0000,9.4981,4.9609,-10.3406,-197.3890,9.5000,5.0000,10.0000,0.0000,9.5012,5.0391,10.3408,197.3776,-3.1402},
				{0.0200,22.8758,-11.0000,9.5981,5.0000,1.9528,614.6710,9.6000,5.0000,10.0000,0.0000,9.6012,5.0000,-1.9528,-614.6799,-3.1402},
				{0.0200,22.7758,-11.0000,9.6981,5.0000,-0.0000,-97.6410,9.7000,5.0000,10.0000,0.0000,9.7012,5.0000,-0.0000,97.6404,-3.1402},
				{0.0200,22.6758,-11.0000,9.7981,5.0000,-0.0000,-0.0000,9.8000,5.0000,10.0000,0.0000,9.8012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,22.5758,-11.0000,9.8981,5.0000,-0.0000,0.0000,9.9000,5.0000,10.0000,0.0000,9.9012,5.0000,-0.0000,0.0000,-3.1402},
				{0.0200,22.4758,-11.0000,9.9981,5.0000,0.0000,0.0000,10.0000,5.0000,10.0000,0.0000,10.0012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,22.3758,-11.0000,10.0981,5.0000,0.0000,-0.0000,10.1000,5.0000,10.0000,0.0000,10.1012,5.0000,0.0000,-0.0000,-3.1402},
				{0.0200,22.2758,-11.0000,10.1981,5.0000,-0.0000,-0.0000,10.2000,5.0000,10.0000,0.0000,10.2012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,22.1758,-11.0000,10.2981,5.0000,0.0000,0.0000,10.3000,5.0000,10.0000,0.0000,10.3012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,22.0758,-11.0000,10.3981,5.0000,-0.0000,-0.0000,10.4000,5.0000,10.0000,0.0000,10.4012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,21.9758,-11.0000,10.4981,5.0000,-0.0000,0.0000,10.5000,5.0000,10.0000,0.0000,10.5012,5.0000,-0.0000,0.0000,-3.1402},
				{0.0200,21.8758,-11.0000,10.5981,5.0000,0.0000,0.0000,10.6000,5.0000,10.0000,0.0000,10.6012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,21.7758,-11.0000,10.6981,5.0000,0.0000,0.0000,10.7000,5.0000,10.0000,0.0000,10.7012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,21.6758,-11.0000,10.7981,5.0000,-0.0000,-0.0000,10.8000,5.0000,10.0000,0.0000,10.8012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,21.5758,-11.0000,10.8981,5.0000,0.0000,0.0000,10.9000,5.0000,10.0000,0.0000,10.9012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,21.4758,-11.0000,10.9981,5.0000,0.0000,0.0000,11.0000,5.0000,10.0000,0.0000,11.0012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,21.3758,-11.0000,11.0981,5.0000,-0.0000,-0.0000,11.1000,5.0000,10.0000,0.0000,11.1012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,21.2758,-11.0000,11.1981,5.0000,0.0000,0.0000,11.2000,5.0000,10.0000,0.0000,11.2012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,21.1758,-11.0000,11.2981,5.0000,0.0000,0.0000,11.3000,5.0000,10.0000,0.0000,11.3012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,21.0758,-11.0000,11.3981,5.0000,-0.0000,-0.0000,11.4000,5.0000,10.0000,0.0000,11.4012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,20.9758,-11.0000,11.4981,5.0000,0.0000,0.0000,11.5000,5.0000,10.0000,0.0000,11.5012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,20.8758,-11.0000,11.5981,5.0000,0.0000,0.0000,11.6000,5.0000,10.0000,0.0000,11.6012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,20.7758,-11.0000,11.6981,5.0000,-0.0000,-0.0000,11.7000,5.0000,10.0000,0.0000,11.7012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,20.6758,-11.0000,11.7981,5.0000,0.0000,0.0000,11.8000,5.0000,10.0000,0.0000,11.8012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,20.5758,-11.0000,11.8981,5.0000,-0.0000,-0.0000,11.9000,5.0000,10.0000,0.0000,11.9012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,20.4758,-11.0000,11.9981,5.0000,0.0000,0.0000,12.0000,5.0000,10.0000,0.0000,12.0012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,20.3758,-11.0000,12.0981,5.0000,0.0000,0.0000,12.1000,5.0000,10.0000,0.0000,12.1012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,20.2758,-11.0000,12.1981,5.0000,-0.0000,-0.0000,12.2000,5.0000,10.0000,0.0000,12.2012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,20.1758,-11.0000,12.2981,5.0000,0.0000,0.0000,12.3000,5.0000,10.0000,0.0000,12.3012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,20.0758,-11.0000,12.3981,5.0000,0.0000,0.0000,12.4000,5.0000,10.0000,0.0000,12.4012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,19.9758,-11.0000,12.4981,5.0000,-0.0000,-0.0000,12.5000,5.0000,10.0000,0.0000,12.5012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,19.8758,-11.0000,12.5981,5.0000,0.0000,0.0000,12.6000,5.0000,10.0000,0.0000,12.6012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,19.7758,-11.0000,12.6981,5.0000,0.0000,0.0000,12.7000,5.0000,10.0000,0.0000,12.7012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,19.6758,-11.0000,12.7981,5.0000,-0.0000,-0.0000,12.8000,5.0000,10.0000,0.0000,12.8012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,19.5758,-11.0000,12.8981,5.0000,0.0000,0.0000,12.9000,5.0000,10.0000,0.0000,12.9012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,19.4758,-11.0000,12.9981,5.0000,0.0000,0.0000,13.0000,5.0000,10.0000,0.0000,13.0012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,19.3758,-11.0000,13.0981,5.0000,0.0000,0.0000,13.1000,5.0000,10.0000,0.0000,13.1012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,19.2758,-11.0000,13.1981,5.0000,0.0000,-0.0000,13.2000,5.0000,10.0000,0.0000,13.2012,5.0000,0.0000,-0.0000,-3.1402},
				{0.0200,19.1758,-11.0000,13.2981,5.0000,0.0000,-0.0000,13.3000,5.0000,10.0000,0.0000,13.3012,5.0000,0.0000,-0.0000,-3.1402},
				{0.0200,19.0758,-11.0000,13.3981,5.0000,-0.0000,-0.0000,13.4000,5.0000,10.0000,0.0000,13.4012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,18.9758,-11.0000,13.4981,5.0000,0.0000,0.0000,13.5000,5.0000,10.0000,0.0000,13.5012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,18.8758,-11.0000,13.5981,5.0000,-0.0000,-0.0000,13.6000,5.0000,10.0000,0.0000,13.6012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,18.7758,-11.0000,13.6981,5.0000,0.0000,0.0000,13.7000,5.0000,10.0000,0.0000,13.7012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,18.6758,-11.0000,13.7981,5.0000,0.0000,-0.0000,13.8000,5.0000,10.0000,0.0000,13.8012,5.0000,0.0000,-0.0000,-3.1402},
				{0.0200,18.5758,-11.0000,13.8981,5.0000,-0.0000,-0.0000,13.9000,5.0000,10.0000,0.0000,13.9012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,18.4758,-11.0000,13.9981,5.0000,0.0000,0.0000,14.0000,5.0000,10.0000,0.0000,14.0012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,18.3758,-11.0000,14.0981,5.0000,-0.0000,-0.0000,14.1000,5.0000,10.0000,0.0000,14.1012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,18.2758,-11.0000,14.1981,5.0000,0.0000,0.0000,14.2000,5.0000,10.0000,0.0000,14.2012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,18.1758,-11.0000,14.2981,5.0000,0.0000,-0.0000,14.3000,5.0000,10.0000,0.0000,14.3012,5.0000,0.0000,-0.0000,-3.1402},
				{0.0200,18.0758,-11.0000,14.3981,5.0000,-0.0000,-0.0000,14.4000,5.0000,10.0000,0.0000,14.4012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,17.9758,-11.0000,14.4981,5.0000,0.0000,0.0000,14.5000,5.0000,10.0000,0.0000,14.5012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,17.8758,-11.0000,14.5981,5.0000,-0.0000,-0.0000,14.6000,5.0000,10.0000,0.0000,14.6012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,17.7758,-11.0000,14.6981,5.0000,0.0000,0.0000,14.7000,5.0000,10.0000,0.0000,14.7012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,17.6758,-11.0000,14.7981,5.0000,0.0000,-0.0000,14.8000,5.0000,10.0000,0.0000,14.8012,5.0000,0.0000,-0.0000,-3.1402},
				{0.0200,17.5758,-11.0000,14.8981,5.0000,-0.0000,-0.0000,14.9000,5.0000,10.0000,0.0000,14.9012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,17.4758,-11.0000,14.9981,5.0000,0.0000,0.0000,15.0000,5.0000,10.0000,0.0000,15.0012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,17.3758,-11.0000,15.0981,5.0000,-0.0000,-0.0000,15.1000,5.0000,10.0000,0.0000,15.1012,5.0000,-0.0000,-0.0000,-3.1402},
				{0.0200,17.2758,-11.0000,15.1981,5.0000,0.0000,0.0000,15.2000,5.0000,10.0000,0.0000,15.2012,5.0000,0.0000,0.0000,-3.1402},
				{0.0200,17.1758,-11.0000,15.2981,5.0000,0.0000,-0.0000,15.3000,5.0000,10.0000,0.0000,15.3012,5.0000,0.0000,-0.0000,-3.1402},
				{0.0200,17.0798,-11.0000,15.3941,4.8000,-10.0000,-500.0000,15.3960,4.8000,-10.0000,0.0000,15.3972,4.8000,-10.0000,-500.0000,-3.1402},
				{0.0200,16.9878,-11.0000,15.4861,4.6000,-10.0000,0.0000,15.4880,4.6000,-10.0000,0.0000,15.4892,4.6000,-10.0000,0.0000,-3.1402},
				{0.0200,16.8998,-11.0000,15.5741,4.4000,-10.0000,0.0000,15.5760,4.4000,-10.0000,0.0000,15.5772,4.4000,-10.0000,0.0000,-3.1402},
				{0.0200,16.8158,-11.0000,15.6581,4.2000,-10.0000,0.0000,15.6600,4.2000,-10.0000,0.0000,15.6612,4.2000,-10.0000,0.0000,-3.1402},
				{0.0200,16.7358,-11.0000,15.7381,4.0000,-10.0000,0.0000,15.7400,4.0000,-10.0000,0.0000,15.7412,4.0000,-10.0000,0.0000,-3.1402},
				{0.0200,16.6598,-11.0000,15.8141,3.8000,-10.0000,-0.0000,15.8160,3.8000,-10.0000,0.0000,15.8172,3.8000,-10.0000,-0.0000,-3.1402},
				{0.0200,16.5878,-11.0000,15.8861,3.6000,-10.0000,0.0000,15.8880,3.6000,-10.0000,0.0000,15.8892,3.6000,-10.0000,0.0000,-3.1402},
				{0.0200,16.5198,-11.0000,15.9541,3.4000,-10.0000,0.0000,15.9560,3.4000,-10.0000,0.0000,15.9572,3.4000,-10.0000,0.0000,-3.1402},
				{0.0200,16.4558,-11.0000,16.0181,3.2000,-10.0000,-0.0000,16.0200,3.2000,-10.0000,0.0000,16.0212,3.2000,-10.0000,-0.0000,-3.1402},
				{0.0200,16.3958,-11.0000,16.0781,3.0000,-10.0000,0.0000,16.0800,3.0000,-10.0000,0.0000,16.0812,3.0000,-10.0000,0.0000,-3.1402},
				{0.0200,16.3398,-11.0000,16.1341,2.8000,-10.0000,0.0000,16.1360,2.8000,-10.0000,0.0000,16.1372,2.8000,-10.0000,0.0000,-3.1402},
				{0.0200,16.2878,-11.0000,16.1861,2.6000,-10.0000,-0.0000,16.1880,2.6000,-10.0000,0.0000,16.1892,2.6000,-10.0000,-0.0000,-3.1402},
				{0.0200,16.2398,-11.0000,16.2341,2.4000,-10.0000,0.0000,16.2360,2.4000,-10.0000,0.0000,16.2372,2.4000,-10.0000,0.0000,-3.1402},
				{0.0200,16.1958,-11.0000,16.2781,2.2000,-10.0000,-0.0000,16.2800,2.2000,-10.0000,0.0000,16.2812,2.2000,-10.0000,-0.0000,-3.1402},
				{0.0200,16.1558,-11.0000,16.3181,2.0000,-10.0000,0.0000,16.3200,2.0000,-10.0000,0.0000,16.3212,2.0000,-10.0000,0.0000,-3.1402},
				{0.0200,16.1198,-11.0000,16.3541,1.8000,-10.0000,-0.0000,16.3560,1.8000,-10.0000,0.0000,16.3572,1.8000,-10.0000,-0.0000,-3.1402},
				{0.0200,16.0878,-11.0000,16.3861,1.6000,-10.0000,0.0000,16.3880,1.6000,-10.0000,0.0000,16.3892,1.6000,-10.0000,0.0000,-3.1402},
				{0.0200,16.0598,-11.0000,16.4141,1.4000,-10.0000,0.0000,16.4160,1.4000,-10.0000,0.0000,16.4172,1.4000,-10.0000,0.0000,-3.1402},
				{0.0200,16.0358,-11.0000,16.4381,1.2000,-10.0000,0.0000,16.4400,1.2000,-10.0000,0.0000,16.4412,1.2000,-10.0000,0.0000,-3.1402},
				{0.0200,16.0158,-11.0000,16.4581,1.0000,-10.0000,0.0000,16.4600,1.0000,-10.0000,0.0000,16.4612,1.0000,-10.0000,0.0000,-3.1402},
				{0.0200,16.0000,-11.0000,16.4739,0.7906,-10.4688,-23.4425,16.4758,0.8000,-10.0000,0.0000,16.4770,0.7906,-10.4688,-23.4425,-3.1402},
				{0.0200,16.0000,-11.0000,16.4739,0.0000,-39.5312,-1453.1151,16.4758,0.6000,-10.0000,0.0000,16.4770,0.0000,-39.5312,-1453.1151,-3.1402},
				{0.0200,16.0000,-11.0000,16.4739,0.0000,0.0000,1976.5575,16.4758,0.4000,-10.0000,0.0000,16.4770,0.0000,0.0000,1976.5575,-3.1402},
				{0.0200,16.0000,-11.0000,16.4739,0.0000,0.0000,0.0000,16.4758,0.2000,-10.0000,0.0000,16.4770,0.0000,0.0000,0.0000,-3.1402},
				{0.0200,16.0000,-11.0000,16.4739,0.0000,0.0000,0.0000,16.4758,-0.0000,-10.0000,0.0000,16.4770,0.0000,0.0000,0.0000,-3.1402},

	    };

	@Override
	public double[][] getPath() {
	    return points;
	}
}