// Emma Flynn
// RO0221673
// Non-Linear Assignment 2- Part 2
// Bottom-up approach

public class RobotMoving {
    private static final double[][] costMatrix = {{1.1, 1.3, 2.5}, {1.5, 1.2, 2.3}};

    private static void findBestPath(int n, double[] costList, String costName){
        double[][] minCostMatrix = new double[n][n]; // holds the smallest amount of energy used to reach each square
        int[][] directionsTaken = new int[n][n];

        minCostMatrix[0][0] = 0; // start point is 0 because robot is already there so no cost

        // left col and top row can only be reached by moving in one direction from start
        for (int i = 1; i < n; i++){ // 1 because we already initialized [0][0]
            minCostMatrix[i][0] = minCostMatrix[i - 1][0] + costList[1]; // initialize first column - only using cost of moving down
            minCostMatrix[0][i] = minCostMatrix[0][i - 1] + costList[0]; // initialize first row - cost of moving right

            directionsTaken[0][i] = 1;
            directionsTaken[i][0] = 2;
        }
        // for remaining squares: check how much energy it would take to move there from neighboring
        // square and store the smallest value in the matrix
        for (int i = 1; i < n; i++){
            for (int j = 1; j < n; j++) {
                double moveRight = minCostMatrix[i][j - 1] + costList[0];
                double moveDown = minCostMatrix[i - 1][j] + costList[1];
                double moveDiag = minCostMatrix[i - 1][j - 1] + costList[2];

                // Store the minimum cost and the direction taken to reach the current cell
                if (moveDiag <= moveRight && moveDiag <= moveDown) {
                    minCostMatrix[i][j] = moveDiag;
                    directionsTaken[i][j] = 3;
                } else if (moveRight <= moveDown) {
                    minCostMatrix[i][j] = moveRight;
                    directionsTaken[i][j] = 1;
                } else {
                    minCostMatrix[i][j] = moveDown;
                    directionsTaken[i][j] = 2;

                }
            }
        }
        System.out.println(costName + ": minimum cost = " + String.format("%.2f", minCostMatrix[n - 1][n - 1]));
        showPathTaken(directionsTaken, n);
    }
    private static void showPathTaken(int[][] directions, int n){
        // look at the bottom right square and work backwards to print out the route taken
        int i = n - 1;
        int j = n - 1;

        StringBuilder path = new StringBuilder();

        while (i > 0 || j > 0){
            int direction = directions[i][j];
            switch (direction) {
                case 1 -> {
                    path.append("Right, ");
                    //path.append("(").append(i).append(",").append(j).append("), ");
                    j--;
                }
                case 2 -> {
                    path.append("Down, ");
                    i--;
                }
                case 3 -> {
                    path.append("Right-Down, ");
                    i--;
                    j--;
                }
            }
        }
        System.out.println("Path taken: " + path);
    }
    public static void main(String[] args) {
        int n = 8; // Set the size of the matrix
        findBestPath(n, costMatrix[0],"cost1" );
        findBestPath(n, costMatrix[1], "cost2");
    }
}
