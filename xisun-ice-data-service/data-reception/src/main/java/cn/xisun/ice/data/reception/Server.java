package cn.xisun.ice.data.reception;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/5/17 17:12
 * @description 数据接受服务
 */
public class Server {
    public static void main(String[] args) {
        // 初使化连接，args可以传一些初使化参数，如连接超时时间，初使化客户连接池的数量等
        Ice.Communicator ice = Ice.Util.initialize(args);
        Runtime.getRuntime().addShutdownHook(new Thread(ice::destroy));
        // 创建名为SimplePrinterAdapter的适配器，并要求适配器使用缺省的协议(TCP/IP侦听端口为10003的请求)
        Ice.ObjectAdapter adapter = ice.createObjectAdapterWithEndpoints("SimplePrinterAdapter", "default -p 10003");
        // 实例化一个PrinterI对象，为Printer接口创建一个服务对象
        Ice.Object object = new Printer();
        // 将服务单元增加到适配器中，并给服务对象指定名称为SimplePrinter，该名称用于唯一确定一个服务单元
        adapter.add(object, Ice.Util.stringToIdentity("SimplePrinter"));
        // 激活适配器，这样做的好处是可以等到所有资源就位后再触发
        adapter.activate();
        // 让服务在退出之前，一直持续对请求的监听
        ice.waitForShutdown();
    }
}
