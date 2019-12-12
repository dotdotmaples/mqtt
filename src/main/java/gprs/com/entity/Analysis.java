package gprs.com.entity;

import java.util.Date;

import javax.crypto.Mac;

/**
 * 命令解析
 * @author yvdedu.com
 *
 */
public class Analysis {
	
	private int head; //头
	private int order; //命令
	private int V; //电压
	private int I; //电流
	private int P; //电能
	private int D; //运行时间
	private int LK; //漏电流
	private int PF; //功率回数
	private int T; //温度
	private int fault; //故障显示
	private int LK_SET; //额定电压设置
	private int RC_SET; //额定电流设置
	private int UV_SET; //欠压
	private int OV_SET; //过压
	private int trip; //功能
	private int LR_F; //是否开启学习模式 1 开 0 不开
	private int OFFSET; //偏移量(校对值)
	private int PK_R; //
	private int AS_R; //波形
	private int LR_1; // 下面4个学习波形
	private int LR_2; //
	private int LR_3; //
	private int LR_4; //
	private int HF; //高频指数
	private int SW_CODE; //软件代码
	private int ARC_CODE; //
//	private int AS_R; //as显示 波形
//	private int PK_R; //
//	private int LR_F; //是否开启学习模式 1 开 0 不开
//	private int LR_1; // 下面4个学习波形
//	private int LR_2; //
//	private int LR_3; //
//	private int LR_4; //
//	private int HF; //高频指数
//	private int SW_CODE; //软件代码
//	private int X3; //
//	private int X4; //
	private int X5; //
	private String X6; //
	private String MAC; //mac地址
	private String Y; //年
	private String MON; //月
	private String DS; //日
	private String H; //时
	private String M; //分
	private String S; //秒
	private int WD; //星期
	private int T_CODE; //计数器
	private String date;
	  
	public static void main(String[] args) {
		
//		String msg = "640cef00000000001100000a01001920b40004010f0064000000000000001a002000000000000000000000000000000084f3eb0cbb36e2070b1d000122050000";
//		String msg = "640cec00000000000300000a01001920b40004010f004c000000000000001a003000000000000000000000000000000084f3eb0c04f9e30707180d2637041801";
//		String msg = "640ced00000000000c00000a01001920b40004017f014c000000000000001a002f00000000000000000000000000000084f3eb0c04f9e3070805100a2b027c56";
		String msg = "640cee00000000000000000a00001920b40004010f005a000000000000001a002a00000000000000000000000000000084f3eb9267b2e30708090d2110061e01";
//		String msg = "640cee00000000000000000a00001920b40004010f005a000000000000001a002a00000000000000000000000000000084f3eb9267b2e30708090d2110061e01";
//		String msg = "640e0500000000000000000a00011920b400040107005a000000000000001a0033372f3836353337333034343537363030322f323031392f382f31352f31312f34312f35312f342f3236";
		System.out.println(msg.length());
		System.out.println(Integer.parseInt(msg.substring(2, 4),16));
		System.out.println(msg.substring(2, 4));
		Analysis analysis = new Analysis(msg);
		System.out.println(analysis.toString());
		
	}
	
	public Analysis(String msg){
		this.head = Integer.parseInt(msg.substring(0, 2),16);
		this.order = Integer.parseInt(msg.substring(2, 4),16);
		this.V = handleHex(msg.substring(4,8));
		this.I = handleHex(msg.substring(8,12));
		this.P = handleHex(msg.substring(12,16));
		this.D = handleHex(msg.substring(16,20));
		this.LK = Integer.parseInt(msg.substring(20, 22),16);
		this.PF = Integer.parseInt(msg.substring(22, 24),16);
		this.T = Integer.parseInt(msg.substring(24, 26),16);
		this.fault = Integer.parseInt(msg.substring(26, 28),16);
		this.LK_SET = Integer.parseInt(msg.substring(28, 30),16);
		this.RC_SET = Integer.parseInt(msg.substring(30, 32),16);
		this.UV_SET = handleHex(msg.substring(32,36));
		this.OV_SET = handleHex(msg.substring(36,40));
		this.trip = Integer.parseInt(msg.substring(40, 42),16);
		this.LR_F = Integer.parseInt(msg.substring(42, 44),16);
		this.OFFSET = Integer.parseInt(msg.substring(44, 46),16);
		this.PK_R = Integer.parseInt(msg.substring(46, 48),16);
		this.AS_R = Integer.parseInt(msg.substring(48, 50),16);
		this.LR_1 = Integer.parseInt(msg.substring(50, 52),16);
		this.LR_2 = Integer.parseInt(msg.substring(52, 54),16);
		this.LR_3 = Integer.parseInt(msg.substring(54, 56),16);
		this.LR_4 = Integer.parseInt(msg.substring(56, 58),16);
		this.HF = Integer.parseInt(msg.substring(58, 60),16);
		this.SW_CODE = Integer.parseInt(msg.substring(60, 62),16);
		this.ARC_CODE = Integer.parseInt(msg.substring(62, 64),16);
		this.X5 = Integer.parseInt(msg.substring(64, 66),16);
		this.X6 = msg.substring(66, 96);
		this.MAC = huoqumac(msg.substring(96, 108));
		this.Y = handleHex(msg.substring(108,112))+"";
		this.MON = buwei(Integer.parseInt(msg.substring(112, 114),16));
		this.DS = buwei(Integer.parseInt(msg.substring(114, 116),16));
		this.H = buwei(Integer.parseInt(msg.substring(116, 118),16));
		this.M = buwei(Integer.parseInt(msg.substring(118, 120),16));
		this.S = buwei(Integer.parseInt(msg.substring(120, 122),16));
		this.WD = Integer.parseInt(msg.substring(122, 124),16);
		this.T_CODE = handleHex(msg.substring(124, 128));
		this.date = Y+"-"+MON+"-"+DS+" "+H+":"+M+":"+S;
	}
	
	public String buwei(int n){
		if(n<10){
			return "0"+n;
		}else{
			return n+"";
		}
	}
	

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getV() {
		return V;
	}

	public void setV(int v) {
		V = v;
	}

	public int getI() {
		return I;
	}

	public void setI(int i) {
		I = i;
	}

	public int getP() {
		return P;
	}

	public void setP(int p) {
		P = p;
	}

	public int getD() {
		return D;
	}

	public void setD(int d) {
		D = d;
	}

	public int getLK() {
		return LK;
	}

	public void setLK(int lK) {
		LK = lK;
	}

	public int getPF() {
		return PF;
	}

	public void setPF(int pF) {
		PF = pF;
	}

	public int getT() {
		return T;
	}

	public void setT(int t) {
		T = t;
	}

	public int getFault() {
		return fault;
	}

	public void setFault(int fault) {
		this.fault = fault;
	}

	public int getLK_SET() {
		return LK_SET;
	}

	public void setLK_SET(int lK_SET) {
		LK_SET = lK_SET;
	}

	public int getRC_SET() {
		return RC_SET;
	}

	public void setRC_SET(int rC_SET) {
		RC_SET = rC_SET;
	}

	public int getUV_SET() {
		return UV_SET;
	}

	public void setUV_SET(int uV_SET) {
		UV_SET = uV_SET;
	}

	public int getOV_SET() {
		return OV_SET;
	}

	public void setOV_SET(int oV_SET) {
		OV_SET = oV_SET;
	}

	public int getTrip() {
		return trip;
	}

	public void setTrip(int trip) {
		this.trip = trip;
	}

	public int getLR_F() {
		return LR_F;
	}

	public void setLR_F(int lR_F) {
		LR_F = lR_F;
	}

	public int getOFFSET() {
		return OFFSET;
	}

	public void setOFFSET(int oFFSET) {
		OFFSET = oFFSET;
	}

	public int getPK_R() {
		return PK_R;
	}

	public void setPK_R(int pK_R) {
		PK_R = pK_R;
	}

	public int getAS_R() {
		return AS_R;
	}

	public void setAS_R(int aS_R) {
		AS_R = aS_R;
	}

	public int getLR_1() {
		return LR_1;
	}

	public void setLR_1(int lR_1) {
		LR_1 = lR_1;
	}

	public int getLR_2() {
		return LR_2;
	}

	public void setLR_2(int lR_2) {
		LR_2 = lR_2;
	}

	public int getLR_3() {
		return LR_3;
	}

	public void setLR_3(int lR_3) {
		LR_3 = lR_3;
	}

	public int getLR_4() {
		return LR_4;
	}

	public void setLR_4(int lR_4) {
		LR_4 = lR_4;
	}

	public int getHF() {
		return HF;
	}

	public void setHF(int hF) {
		HF = hF;
	}

	public int getSW_CODE() {
		return SW_CODE;
	}

	public void setSW_CODE(int sW_CODE) {
		SW_CODE = sW_CODE;
	}

	public int getARC_CODE() {
		return ARC_CODE;
	}

	public void setARC_CODE(int aRC_CODE) {
		ARC_CODE = aRC_CODE;
	}

	public int getX5() {
		return X5;
	}

	public void setX5(int x5) {
		X5 = x5;
	}
	public String getX6() {
		return X6;
	}

	public void setX6(String x6) {
		X6 = x6;
	}
	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}

	public String getY() {
		return Y;
	}

	public void setY(String y) {
		Y = y;
	}

	public String getMON() {
		return MON;
	}

	public void setMON(String mON) {
		MON = mON;
	}

	public String getDS() {
		return DS;
	}

	public void setDS(String dS) {
		DS = dS;
	}

	public String getH() {
		return H;
	}

	public void setH(String h) {
		H = h;
	}

	public String getM() {
		return M;
	}

	public void setM(String m) {
		M = m;
	}

	public String getS() {
		return S;
	}

	public void setS(String s) {
		S = s;
	}

	public int getWD() {
		return WD;
	}

	public void setWD(int wD) {
		WD = wD;
	}

	public int getT_CODE() {
		return T_CODE;
	}

	public void setT_CODE(int t_CODE) {
		T_CODE = t_CODE;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public static int handleHex(String hex){
		String front = hex.substring(0,2);
		String after = hex.substring(2,4);
		String newHex = after+front;//16位进行前后换位后转10进制
		return Integer.parseInt(newHex,16);
	}
	
	public static String huoqumac(String hex){
		int mac1 = Integer.parseInt(hex.substring(0, 2),16);
		int mac2 = Integer.parseInt(hex.substring(2, 4),16);
		int mac3 = Integer.parseInt(hex.substring(4, 6),16);
		int mac4 = Integer.parseInt(hex.substring(6, 8),16);
		int mac5 = Integer.parseInt(hex.substring(8, 10),16);
		int mac6 = Integer.parseInt(hex.substring(10, 12),16);
		return ""+mac1+mac2+mac3+mac4+mac5+mac6;
	}
	
//	public static void main(String[] args) {
//		String xx = "640CEC00000000000200000A01001920B40004010F0064000000000000001A0026000000000000000000000000000000ECFABC1A2035E20709110E212B022C01";
//		System.out.println(new Date().getTime());
//		System.out.println(new Analysis(xx));
//		System.out.println(new Date().getTime());
//	}

	

	public String getDate() {
		return date;
	}


	@Override
	public String toString() {
		return "Analysis [head=" + head + ", order=" + order + ", V=" + V + ", I=" + I + ", P=" + P + ", D=" + D
				+ ", LK=" + LK + ", PF=" + PF + ", T=" + T + ", fault=" + fault + ", LK_SET=" + LK_SET + ", RC_SET="
				+ RC_SET + ", UV_SET=" + UV_SET + ", OV_SET=" + OV_SET + ", trip=" + trip + ", LR_F=" + LR_F
				+ ", OFFSET=" + OFFSET + ", PK_R=" + PK_R + ", AS_R=" + AS_R + ", LR_1=" + LR_1 + ", LR_2=" + LR_2
				+ ", LR_3=" + LR_3 + ", LR_4=" + LR_4 + ", HF=" + HF + ", SW_CODE=" + SW_CODE + ", ARC_CODE=" + ARC_CODE
				+ ", X5=" + X5 + ", X6=" + X6 + ", MAC=" + MAC + ", Y=" + Y + ", MON=" + MON + ", DS=" + DS + ", H=" + H
				+ ", M=" + M + ", S=" + S + ", WD=" + WD + ", T_CODE=" + T_CODE + ", date=" + date + "]";
	}





	
	
}
