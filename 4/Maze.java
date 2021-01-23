package edu.stevens.cs570.assignments;


import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 *
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    // Turn all coordinates into a matrix, and the path accessed between them is set to 1
    private int roadVisit[][]; //Used to record whether the path between coordinates is accessed

    /** Wrapper method. */
    public boolean findMazePath() {
        ArrayList<ArrayList<PairInt>> allMazePaths = findAllMazePaths(0, 0);
        System.out.println("All paths:");
        for (ArrayList<PairInt> MazePath : allMazePaths)
            System.out.println(MazePath.toString());
        ArrayList<PairInt> minMazePath = findMazePathMin(0, 0);
        System.out.println("Shortest path：");
        System.out.println(minMazePath.toString());
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * PROBLEM 1
     * Attempts to find a path through point (x, y).
     *
     * @pre Possible path cells are in BACKGROUND color; barrier cells are in
     *      ABNORMAL color.
     * @post If a path is found, all cells on it are set to the PATH color; all
     *       cells that were visited but are not on the path are in the TEMPORARY
     *       color.
     * @param x
     *            The x-coordinate of current point
     * @param y
     *            The y-coordinate of current point
     * @return If a path through (x, y) is found, true; otherwise, false
     */
    public boolean findMazePath(int x, int y) {
        boolean found = false;
        /**
         * TO BE COMPLETED
         */
        if (x < 0 || y < 0 || x >= maze.getNCols() || y >= maze.getNRows())
            found = false;
        else if (!maze.getColor(x, y).equals(ABNORMAL))
            found = false;
        else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
            maze.recolor(x, y, PATH);
            found = true;
        } else {
            maze.recolor(x, y, PATH);
            if (findMazePath(x -1, y)
                    || findMazePath(x + 1, y)
                    || findMazePath(x, y -1)
                    || findMazePath(x, y + 1)) {
                found = true;
            } else {
                maze.recolor(x, y, TEMPORARY);
                found = false;
            }
        }
        return found;
    }

    // PROBLEM 2
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {
        ArrayList<ArrayList<PairInt>> result = new ArrayList<>();
        /**
         * TO BE COMPLETED
         */
        Stack<PairInt> trace = new Stack<>();
        // Initialize roadVisit [] [] and stack
        roadVisit = new int[maze.getNRows() * maze.getNCols()][maze.getNRows() * maze.getNCols()];
        if (!maze.getColor(0, 0).equals(ABNORMAL)) // If the initial coordinate 0, 0 is not a walkable coordinate, return directly
            return result;
        trace.push(new PairInt(0, 0));
        maze.recolor(0, 0, PATH);
        findMazePathStackBased(0, 0, result, trace);
        return result;
    }

    private boolean validCol(int col) {
        if (col < 0 || col >= maze.getNCols())
            return false;
        return true;
    }

    private boolean validRow(int row) {
        if (row < 0 || row >= maze.getNRows())
            return false;
        return true;
    }

    /**
     * Helper method for PROBLEM 2
     * @pre Possible cells are in ABNORMAL color. Barriers are in BACKGROUND color.
     * @post If a path is found, then the ArrayList result will be modified to
     *       include all possible paths to complete the maze successfully.
     * @param x
     *            x-coordinate of the current point
     * @param y
     *            y-coordinate of the current point
     * @param result
     *            The 2-D ArrayList that contains ArrayLists of PairInts
     *            corresponding to successful paths to complete the maze.
     * @param trace
     *            A stack to keep track of the current path being explored to
     *            determine a successful path.
     *
     */
    private void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
        PairInt pairInt;

        // If the current access coordinate is an exit
        if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
            // Store the result path in the collection
            ArrayList<PairInt> pairIntList = new ArrayList<>();
            pairIntList.addAll(trace);
            result.add(pairIntList);
            pairInt = trace.pop();// Pop the end point out of the stack
            // Make the endpoint accessible, but the path from the top of the current stack to the endpoint is accessed in roadVisit [] []
            maze.recolor(pairInt.getX(), pairInt.getY(), ABNORMAL);
            pairInt = trace.peek();// Current stack top coordinates
            findMazePathStackBased(pairInt.getX(), pairInt.getY(), result, trace);
        } else {
            int currentPair = x + y * maze.getNCols();//The row position of the current top of the stack in roadVisit [] []
            int xNext = 0;
            int yNext = 0;
            int nextPair;
            boolean flag = false;
            // If the coordinate is in the grid and the target coordinate is accessible, the path from the current stack top coordinate to the target coordinate is not accessible
            if (validCol(x - 1) && maze.getColor(x - 1, y).equals(ABNORMAL) && roadVisit[currentPair][x - 1 + y * maze.getNCols()] != 1) {
                xNext = x - 1;
                yNext = y;
                flag = true;
            }
            else if (validCol(x + 1) && maze.getColor(x + 1, y).equals(ABNORMAL) && roadVisit[currentPair][x + 1 + y * maze.getNCols()] != 1) {
                xNext = x + 1;
                yNext = y;
                flag = true;
            }
            else if (validRow(y - 1) && maze.getColor(x, y - 1).equals(ABNORMAL) && roadVisit[currentPair][x + (y - 1) * maze.getNCols()] != 1) {
                xNext = x;
                yNext = y - 1;
                flag = true;
            }
            else if (validRow(y + 1) && maze.getColor(x, y + 1).equals(ABNORMAL) && roadVisit[currentPair][x + (y + 1) * maze.getNCols()] != 1) {
                xNext = x;
                yNext = y + 1;
                flag = true;
            }

            // If the flag is true, it indicates that there are walking coordinates, otherwise, backtracking
            if (flag) {
                nextPair = xNext + yNext * maze.getNCols();
                maze.recolor(xNext, yNext, PATH);
                // Set the corresponding coordinates of the path from the current stack top coordinate to the next coordinate in roadVisit [] [] as accessed
                roadVisit[currentPair][nextPair] = 1;
                trace.push(new PairInt(xNext, yNext));
                findMazePathStackBased(xNext, yNext, result, trace);
            } else {
                // There are no walking coordinates. Pop up the coordinates on the top of the current stack and set the pop-up coordinates as not accessed
                pairInt = trace.pop();

                maze.recolor(pairInt.getX(), pairInt.getY(), ABNORMAL);
                //The row position of the popped coordinate in roadVisit [] []
                currentPair = pairInt.getX() + pairInt.getY() * maze.getNCols();
                // Set the path to be accessed around the pop-up coordinate (excluding the path from the current stack top coordinate to the pop-up coordinate) as not accessed
                if (validCol(x - 1) && maze.getColor(x - 1, y).equals(ABNORMAL)) {
                    roadVisit[currentPair][x - 1 + y * maze.getNCols()] = 0;
                }
                if (validCol(x + 1) && maze.getColor(x + 1, y).equals(ABNORMAL)) {
                    roadVisit[currentPair][x + 1 + y * maze.getNCols()] = 0;
                }
                if (validRow(y - 1) && maze.getColor(x, y - 1).equals(ABNORMAL)) {
                    roadVisit[currentPair][x + (y - 1) * maze.getNCols()] = 0;
                }
                if (validRow(y + 1) && maze.getColor(x, y + 1).equals(ABNORMAL)) {
                    roadVisit[currentPair][x + (y + 1) * maze.getNCols()] = 0;
                }
                // If the stack is empty, the algorithm ends
                if (trace.isEmpty()) {
                    return;
                }
                pairInt = trace.peek();
                findMazePathStackBased(pairInt.getX(), pairInt.getY(), result, trace);
            }
        }

    }

    // PROBLEM 3
    public ArrayList<PairInt> findMazePathMin(int x, int y) {
        ArrayList<PairInt> result = new ArrayList<>();
        /**
         * TO BE COMPLETED
         */
        ArrayList<ArrayList<PairInt>> allResult = findAllMazePaths(0, 0);
        if (allResult.size() > 0) {
            result = allResult.get(0);
            for(ArrayList<PairInt> pairInts : allResult) {
                if (result.size() > pairInts.size()) {
                    result = pairInts;
                }
            }
        }
        return result;
    }

    /**
     * Helper method for PROBLEM 3
     * All possible paths are explored, and the one with the shortest length is
     * added to the ArrayList.
     *
     * @param x
     *            current x-coordinate
     * @param y
     *            current y-coordinate
     * @return An ArrayList of PairInts that correspond to the shortest possible
     *         path through the maze.
     */
    private void findMazePathMinHelper(int x, int y, ArrayList<PairInt> result, Stack<PairInt> trace) {
    }

    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }

    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
}
