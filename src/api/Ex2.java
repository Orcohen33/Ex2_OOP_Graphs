package api;


import GUI.GraphFrame;

/**
 * This class is the main class for api.Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = new DirectWeightGraph();
        // ****** Add your code here ******
        DirectedWeightedGraphAlgorithms dwga = new DirectWeightGraphAlgo();
        dwga.init(ans);
        dwga.load(json_file);
        ans = dwga.getGraph();
        // ********************************
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new DirectWeightGraphAlgo();
        // ****** Add your code here ******
        DirectedWeightedGraph dwg = new DirectWeightGraph();
        ans.init(dwg);
        ans.load(json_file);
        // ********************************
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraph dwg = new DirectWeightGraph();
        DirectedWeightedGraphAlgorithms dwga1 = new DirectWeightGraphAlgo();
        dwga1.init(dwg);
        dwga1.load(json_file);
        // ****** Add your code here ******
      GraphFrame app = new GraphFrame(dwga1);
//        GraphApp app = new GraphApp(dwga1);
        // ********************************
    }

    public static void main(String[] args) {
        getGrapg(args[0]);
        getGrapgAlgo(args[0]);
        runGUI(args[0]);
    }
}