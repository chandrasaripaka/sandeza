package pour.streams.catalog;

import java.net.InetAddress;

import scala.Tuple2;

public class CatalogSubscription {

    String streamName;

    InetAddress inetAddress;

    Tuple2 lastInputTuple;

    int maxMissingTuples;

}
