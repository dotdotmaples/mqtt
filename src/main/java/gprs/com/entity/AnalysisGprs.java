package gprs.com.entity;

import gprs.tool.Hex;

/**
 * 命令解析
 *
 */
public class AnalysisGprs {
	
	private int head; //头
	private int order; //命令
	private int V; //电压
	private int I; //电流
	private int P; //电能
	private int D; //运行时间
	private int LK; //漏电流
	private int PF; //功率回数
	private int T; //温度
	private int SWITCH;
	private int fault; //故障显示
	private int LK_SET; //额定电压设置
	private int RC_SET; //额定电流设置
	private int UV_SET; //欠压
	private int OV_SET; //过压
	private int trip; //功能
	private int AS_R; //波形
	private int PK_R; //
	private int LR_F; //是否开启学习模式 1 开 0 不开
	private int LR_1; // 下面4个学习波形
	private int LR_2; //
	private int LR_3; //
	private int LR_4; //
	private int HF; //高频指数
	private int SW_CODE; //软件代码
	private int ARC_CODE; //
	private int signal; // gprs信号强度
	
	public static void main(String[] args) {
		
		//
//		String a = "640cef00000000001100000a01001920b40004010f0064000000000000001a002000000000000000000000000000000084f3eb0cbb36e2070b1d000122050000";
//		System.out.println(Integer.parseInt(msg.substring(2, 4),16));
//		System.out.println(msg.substring(2, 4));
		
//		String a = "640ce600d6020b030000050901021e20b400fe000000640a1501020304055c0732352f3836303334343034363432303330342f323031392f372f31382f32332f35372f32392f342f3236";
//		String a = "640cec00000000000100000a01001920be0004010f005a000000000000001a3333392f3836353337333034343537353937302f323031392f372f32352f31332f32352f35392f342f3331";
//		String a = "640e0500000000000000000a00011920b400040107005a000000000000001a0033372f3836353337333034343537363030322f323031392f382f31352f31312f34312f35312f342f3236";
		String a = "640ced00000000000000000a01001920b40004010f005a000000000000001a0033302f3836353337333034343537353937302f323031392f392f322f382f32302f35352f312f3237";
		System.out.println(a.length());
		AnalysisGprs analysisGprs = new AnalysisGprs(a);
	    System.out.println(analysisGprs.toString());
	    System.out.println(analysisGprs.getFault());
	    
	}
	
	public AnalysisGprs(String msg){
	    // 前62位数据信息
		System.out.println("msg = " + msg);
		this.head = Integer.parseInt(msg.substring(0, 2),16);
		this.order = Integer.parseInt(msg.substring(2, 4),16);
		this.V = handleHex(msg.substring(4,8));
		this.I = handleHex(msg.substring(8,12));
		this.P = handleHex(msg.substring(12,16));
		this.D = handleHex(msg.substring(16,20));
		this.LK = Integer.parseInt(msg.substring(20, 22),16);
		this.PF = Integer.parseInt(msg.substring(22, 24),16);
		this.SWITCH = Integer.parseInt(msg.substring(24, 26),16);
		this.fault = Integer.parseInt(msg.substring(26, 28),16);
		this.LK_SET = Integer.parseInt(msg.substring(28, 30),16);
		this.RC_SET = Integer.parseInt(msg.substring(30, 32),16);
		this.UV_SET = handleHex(msg.substring(32,36));
		this.OV_SET = handleHex(msg.substring(36,40));
		this.trip = Integer.parseInt(msg.substring(40, 42),16);
	    this.AS_R = Integer.parseInt(msg.substring(42, 44),16);
	    this.PK_R = Integer.parseInt(msg.substring(44, 46),16);
		this.LR_F = Integer.parseInt(msg.substring(46, 48),16);
		this.LR_1 = Integer.parseInt(msg.substring(48, 50),16);
		this.LR_2 = Integer.parseInt(msg.substring(50, 52),16);
		this.LR_3 = Integer.parseInt(msg.substring(52, 54),16);
		this.LR_4 = Integer.parseInt(msg.substring(54, 56),16);
		this.HF = Integer.parseInt(msg.substring(56, 58),16);
		this.SW_CODE = Integer.parseInt(msg.substring(58, 60),16);
		this.ARC_CODE = Integer.parseInt(msg.substring(60, 62),16);
		/*
		// 判断数据分隔符‘\’的位置 5c
		int index = msg.indexOf("5c");
		// 获取string数据，分隔符后面有乱码，占2位
		System.out.println("index = " + index);
		String substring = msg.substring(index + 4);
		System.out.println("subString = " + substring);
		String hexStringToString2 = Hex.hexStringToString(substring);
		
		System.out.println("hexStringToString2 = " + hexStringToString2);
		System.out.println("test = " + Hex.hexStringToString(msg.substring(64)));
		*/
		
		String data[] = Hex.hexStringToString(msg.substring(64)).split("/");
		this.T = Integer.parseInt(data[0]);
		this.signal = Integer.parseInt(data[data.length - 1]);
		
		/*
		// 温度
		this.T = Integer.parseInt(hexStringToString2.substring(0, hexStringToString2.indexOf("/")));
		//gprs信号强度
		this.signal = Integer.parseInt(hexStringToString2.substring(hexStringToString2.lastIndexOf("/")+1));
		*/
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

	public int getSWITCH() {
        return SWITCH;
    }

    public void setSWITCH(int sWITCH) {
        SWITCH = sWITCH;
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

	public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
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

//	public int getOFFSET() {
//		return OFFSET;
//	}
//
//	public void setOFFSET(int oFFSET) {
//		OFFSET = oFFSET;
//	}

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

    @Override
    public String toString() {
        return "AnalysisGprs [head=" + head + ", order=" + order + ", V=" + V + ", I=" + I + ", P=" + P + ", D=" + D
                + ", LK=" + LK + ", PF=" + PF + ", T=" + T + ", SWITCH=" + SWITCH + ", fault=" + fault + ", LK_SET="
                + LK_SET + ", RC_SET=" + RC_SET + ", UV_SET=" + UV_SET + ", OV_SET=" + OV_SET + ", trip=" + trip
                + ", AS_R=" + AS_R + ", PK_R=" + PK_R + ", LR_F=" + LR_F + ", LR_1=" + LR_1 + ", LR_2=" + LR_2
                + ", LR_3=" + LR_3 + ", LR_4=" + LR_4 + ", HF=" + HF + ", SW_CODE=" + SW_CODE + ", ARC_CODE=" + ARC_CODE
                + ", signal=" + signal + "]";
    }
    
	
}
