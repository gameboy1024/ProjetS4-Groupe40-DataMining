package projets4.case1.prediction;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

import projets4.case1.basic.Slider;
import projets4.case1.basic.VideoInfoVariant;
import projets4.utils.TupleHelpers;
import backtype.storm.Config;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;


/**
 * This class receive tuples with large number of elements and emit new tuple to
 * specific following bolt using only interested areas. For example, (id_video
 * id_uploader id_tag) and only this bolt will emit id_video to bolt1,
 * id_uploader to bolt2, etc.
 * 
 * @author sbt
 * 
 */
public class FilterBolt extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 632265333724495194L;
	private static final int DEFAULT_EMIT_FREQUENCY_IN_SECONDS = 1;
	private static final Logger LOG = Logger.getLogger(FilterBolt.class);
	
	private OutputCollector collector;
	private Slider slider;
	private final int emitFrequencyInSeconds;
	
	public FilterBolt() {
        this(DEFAULT_EMIT_FREQUENCY_IN_SECONDS);
        this.slider = new Slider();
    }
	
	public FilterBolt(int emitFrequencyInSeconds) {
		this.emitFrequencyInSeconds = emitFrequencyInSeconds;
		this.slider = new Slider();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		if (TupleHelpers.isTickTuple(input)) {
			//LOG.info("Received tick tuple, triggering emit of current counts");
			emit(slider.getVariantInfos());
		} else {
			process(input);
		}
	}
	
	public void process(Tuple input){
		Object obj = input.getValue(0);
		VideoInfoVariant count = (VideoInfoVariant) input.getValue(1);
        slider.updateVariantInfo(obj, count);
        collector.ack(input);
	}

	private void emit(Map<Object, VideoInfoVariant> counts) {
		for (Entry<Object, VideoInfoVariant> entry : counts.entrySet()) {
			Object obj = entry.getKey();
			VideoInfoVariant count = entry.getValue();
			collector.emit(new Values(obj, count));
			System.out.println("EMIT: "+obj+"  "+count);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("obj", "count"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		Map<String, Object> conf = new HashMap<String, Object>();
		conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, emitFrequencyInSeconds);
		return conf;
	}

}
