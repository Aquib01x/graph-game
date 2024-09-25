package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;



public class Data_Import implements Data_Import_Interface {

    // The entire file data represented as a list of strings, where each string is a line of the file
    private final List<String> raw_data = new ArrayList<>();

    // A list of Edges obtained by parsing the data file
    private final List<Edge> edges = new ArrayList<>();

    // A list of Nodes obtained by parsing the data file
    private final List<Node> nodes = new ArrayList<>();

    // The path
//    private final Path path_notJar = Paths.get("src/Data/test_data.txt"); // Test file for JUnit testing.
    private final Path path_notJar = Paths.get("src/Data/log2023-01-14 18=13.txt");
    private final Path path = Paths.get("./log2023-01-14 18=13.txt");

    /**
     * The method read_file() will read a file line by line, and add the lines to an array list.
     * @return An array list of strings
     */
    public List<String> read_file(){

        try {
            File data_file = new File(path.toString());

            if(!data_file.isFile()){
                data_file = new File(path_notJar.toString());
            }



            Scanner scanner = new Scanner(data_file);
            while (scanner.hasNextLine()) {
                raw_data.add(scanner.nextLine());
            }
            scanner.close();

        } catch (FileNotFoundException event) {
            System.out.println("File not found. Check the path provided.");
        }

        return raw_data;
    }


    /***
     * The method parse_node() will take in an array list of strings that represent nodes and edges
     * and will parse it into a list of nodes.
     * @param data ArrayList of strings which represents nodes and edges.
     * @return ArrayList of nodes.
     */
    public List<Node> parse_node (List<String> data){
        List<Integer> all_nodes = new ArrayList<>();

        //System.out.println("console test");

        for(String elem : data){//for each string in the file
            Scanner s = new Scanner(elem);
            int first;
            int second;

            if (s.hasNextInt()) {
                first = s.nextInt();//get the first node from the text file
                all_nodes.add(first);

            }
            if (s.hasNextInt()) {
                second = s.nextInt();//get the second node from the text file
                all_nodes.add(second);
            }
            s.close();
        }

        //remove duplicates
        Set<Integer> set = new HashSet<>(all_nodes);
        all_nodes.clear();
        all_nodes.addAll(set);

        for(int n : all_nodes){
            Node node = new Node(n);
            nodes.add(node);

        }

        return nodes;

    }

    /**
     * The method parse_edge() will take in an array list of strings that represent nodes and edges,
     * and will parse it into a list of edges.
     * @param data ArrayList of strings which represents nodes and edges.
     * @return ArrayList of edges.
     */
    public List<Edge> parse_edge (List<String> data){
        for( int counter = 0; counter < data.size(); counter++){


            // Static format of an edge when it is a string is "1 15 {'weight: 8'}", so we split it into three with a space as a regex, and assign each part accordingly.
            String[] edge_data = data.get(counter).split(" ", 3);
            Edge edge = new Edge(
                    counter,
                    Integer.parseInt(edge_data[0]),
                    Integer.parseInt(edge_data[1]),
                    Integer.parseInt(edge_data[2].substring(11, edge_data[2].length()-1)));

            edges.add(edge);
        }
        return edges;
    }

}
