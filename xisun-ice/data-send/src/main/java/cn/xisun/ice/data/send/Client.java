package cn.xisun.ice.data.send;

import cn.xisun.ice.data.send.demo.PrinterPrx;
import cn.xisun.ice.data.send.demo.PrinterPrxHelper;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/5/17 17:12
 * @description 数据发送服务
 */
public class Client {
    public static void main(String[] args) {
        // 初使化
        Ice.Communicator ice = Ice.Util.initialize(args);
        // 传入远程服务单元的名称、网络协议、IP及端口，获取Printer的远程代理，这里使用的stringToProxy方式
        Ice.ObjectPrx base = ice.stringToProxy("SimplePrinter:default -p 10003");
        // 通过checkedCast向下转换，获取Printer接口的远程，并同时检测根据传入的名称获取的服务单元是否Printer的代理接口，如果不是则返回null对象
        PrinterPrx printer = PrinterPrxHelper.checkedCast(base);
        if (printer == null) {
            throw new Error("Invalid proxy");
        }
        // 把Hello World传给服务端，让服务端打印出来，因为这个方法最终会在服务端上执行
        printer.printString("Hello World!");
        // 销毁客户端
        ice.destroy();
    }
}
