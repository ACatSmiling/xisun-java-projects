package cn.xisun.ice.data.reception;

import cn.xisun.ice.data.reception.demo._PrinterDisp;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/5/17 17:08
 * @description 处理接收到的数据
 */
public class Printer extends _PrinterDisp {
    @Override
    public void printString(String s, Ice.Current current) {
        System.out.println("接收到的数据：" + s);
    }
}
