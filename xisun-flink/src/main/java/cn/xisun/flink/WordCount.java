package cn.xisun.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/26 13:06
 * @description 批处理实现word count
 */
public class WordCount {
    /**
     * 自定义类，实现FlatMapFunction接口
     * 参数说明：
     * String：传入数据类型
     * Tuple2<String, Integer>：传出数据类型
     * Tuple2<T0, T1>：Flink自身实现的元组，注意不要用scala的
     */
    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String line, Collector<Tuple2<String, Integer>> out) throws Exception {
            // 按空格分词
            String[] words = line.split(" ");
            // 遍历所有word，包装成二元组输出
            for (String str : words) {
                // 每一个word，都包装成一个二元组对象，并计数为1，然后用out收集
                out.collect(new Tuple2<>(str, 1));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 1.创建批处理执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 2.从resources路径下的文件中单线程读取数据，是按照一行一行读取的，此处设置读取时并行度为1
        String inputPath = "src/main/resources/hello.txt";
        DataSet<String> inputDataSet = env.readTextFile(inputPath).setParallelism(1);

        // 3.对数据集进行处理，按空格分词展开，转换成(word, 1)这样的二元组进行统计
        DataSet<Tuple2<String, Integer>> resultSet = inputDataSet.flatMap(new MyFlatMapper())
                .groupBy(0)// 按照元组第一个位置的word分组
                .sum(1);// 按照元组第二个位置上的数据求和

        // 4.打印输出
        resultSet.print();
    }
}
