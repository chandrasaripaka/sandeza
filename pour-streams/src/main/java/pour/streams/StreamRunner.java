package pour.streams;

import java.util.Properties;

import org.apache.beam.runners.flink.FlinkPipelineOptions;
import org.apache.beam.runners.flink.FlinkRunner;
import org.apache.beam.runners.flink.examples.WordCount;
import org.apache.beam.runners.flink.examples.streaming.KafkaWindowedWordCountExample.FormatAsStringFn;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.windowing.AfterWatermark;
import org.apache.beam.sdk.transforms.windowing.SlidingWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.table.StreamTableEnvironment;
import org.apache.flink.api.table.Types;
import org.apache.flink.api.table.sources.BatchTableSource;
import org.apache.flink.api.table.sources.CsvTableSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.Kafka08TableSource;
import org.joda.time.Duration;

public class StreamRunner {
  
  static final long WINDOW_SIZE = 10;  // Default window duration in seconds
  static final long SLIDE_SIZE = 5;  // Default window slide in seconds
  
  public static void main(String [] args){
     String top = "With Window 10 SECONDS Slide every 5 SECONDS Checkpoint 1000 SECONDS";
     String query = "SELECT STREAM FROM TEXT FILE PROCESS EXTRACTWORD STAT COUNT TO TEXT FILE";
     create1(args);
  }
  
  public static void create1(String [] args){
    StreamExecutionEnvironment execEnvStream = StreamExecutionEnvironment.getExecutionEnvironment();
    StreamTableEnvironment tableEnvStream = StreamTableEnvironment.getTableEnvironment(execEnvStream);
    
    Properties kafkaProps = new Properties();
    kafkaProps.setProperty("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
    kafkaProps.setProperty("zookeeper.connect", "localhost:2181");
    kafkaProps.setProperty("group.id", "lbsconsumer1");
    
    BatchTableSource lbs = new CsvTableSource("file:///Users/chandras/Downloads/lbs_100_000", new String[] { "date", "imsi", "msisdn", "celltower","event","long","lat","imei" }, new TypeInformation<?>[] {
      Types.DATE(),
      Types.STRING(),
      Types.STRING(),
      Types.STRING(),
      Types.INT(),
      Types.DOUBLE(),
      Types.DOUBLE(),
      Types.STRING()
    }, "|", "\n", null, true,  "%", false);
    
    //Kafka08TableSource kf8ts = new Kafka08TableSource("LBSStream06",kafkaProps,new TypeInform);
    
  }
  
  
  public static void create(String [] args){
    PipelineOptionsFactory.register(StreamingCountOptions.class);
    StreamingCountOptions options = PipelineOptionsFactory.fromArgs(args).as(StreamingCountOptions.class);
    options.setAppName("FlinkWordCount");
    options.setStreaming(true);
    options.setWindowSize(10L);
    options.setSlide(5L);
    options.setCheckpointingInterval(1000L);
    options.setNumberOfExecutionRetries(5);
    options.setExecutionRetryDelay(3000L);
    options.setParallelism(8);
    options.setRunner(FlinkRunner.class);
    options.setOutput("./outputWordCount.txt");
    Pipeline pipeline = Pipeline.create(options);
    PCollection<String> words = pipeline
        .apply("StreamingWordCount",TextIO.Read.from("file:///Users/chandras/Downloads/lbs_100_000"))
        .apply(ParDo.of(new WordCount.ExtractWordsFn()))
        .apply(Window.<String>into(SlidingWindows.of(
            Duration.standardSeconds(options.getWindowSize()))
            .every(Duration.standardSeconds(options.getSlide())))
            .triggering(AfterWatermark.pastEndOfWindow()).withAllowedLateness(Duration.ZERO)
            .discardingFiredPanes());
    PCollection<KV<String, Long>> wordCounts =
        words.apply(Count.<String>perElement());

    wordCounts.apply(ParDo.of(new FormatAsStringFn()))
        .apply(TextIO.Write.to("./outputWordCount.txt"));
    pipeline.run();
  }


  /**
   * Pipeline options.
   */
  public interface StreamingCountOptions
      extends Options {
    @Description("Sliding window duration, in seconds")
    @Default.Long(WINDOW_SIZE)
    Long getWindowSize();

    void setWindowSize(Long value);

    @Description("Window slide, in seconds")
    @Default.Long(SLIDE_SIZE)
    Long getSlide();

    void setSlide(Long value);
  }
  
  /**
   * Options supported by {@link WordCount}.
   *
   * <p>Inherits standard configuration options.
   */
  public interface Options extends PipelineOptions, FlinkPipelineOptions {
    @Description("Path of the file to read from")
    String getInput();
    void setInput(String value);

    @Description("Path of the file to write to")
    //@Validation.Required
    String getOutput();
    void setOutput(String value);
  }
}