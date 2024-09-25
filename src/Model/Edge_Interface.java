package Model;


/** An Edge is represented by four parameters. It is a representation of a cost to move from node 1 to node 2, which
 * is equal to the weight of the edge.
 * <pre>
 *  |node 1|          weight         |node 2|
 *  |      |                         |      |
 *  |      |                         |      |
 *     ___                             ___
 *    |   |              5            |   |
 *    | 1 |===========================| 2 |
 *    |___|                           |___|
 *  </pre>
 */
public interface Edge_Interface {

    /** get_ID() will return the ID of the edge
     *
     * @return ID of the edge
     */
    int get_ID();

    /** get_Node_1() will return the ID of node 1
     *
     * @return ID of node 1
     */
    int get_Node_1();

    /** get_Node_2() will return the ID of node 2
     *
     * @return ID of node 2
     */
    int get_Node_2();

    /** get_Weight() will return the weight of the edge
     *
     * @return weight of edge
     */
    int get_Weight();
}
