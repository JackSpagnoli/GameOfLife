class GameOfLife {
    private final boolean sizeCapped;
    private char[][] board; //[x][y]
    private final char ALIVE = 'O',
            DEAD = '.';
    GameOfLife(boolean sizeCapped) {
        this.sizeCapped = sizeCapped;
    }
    void setBoard(int x, int y) {
        this.board = new char[x][y];
        innit();
    }
    private void innit() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                this.board[i][j] = DEAD;
            }
        }
    }
    void toggleCell(int x, int y) {
        if (this.board[x][y] == ALIVE) {
            this.board[x][y] = DEAD;
        } else {
            this.board[x][y] = ALIVE;
        }
    }
    private void trimBoard() {
        if (!this.sizeCapped) {
            int minx = Integer.MAX_VALUE,
                    miny = Integer.MAX_VALUE,
                    maxx = 0,
                    maxy = 0;
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[i].length; j++) {
                    if (this.board[i][j] == ALIVE) {
                        if (i > maxx) {
                            maxx = i;
                        } else if (i < minx) {
                            minx = i;
                        } else if (j > maxy) {
                            maxy = j;
                        } else if (j < miny) {
                            miny = j;
                        }
                    }
                }
            }
            char[][] temp = this.board; //Copies board
            this.board = new char[(maxx - minx) + 2][(maxy - miny) + 2]; //New board with size +2 for new rows and columns
            for (int i = 0; i < this.board[0].length; i++) { //Setting the first and last columns
                this.board[0][i] = DEAD;
                this.board[this.board.length - 1][i] = DEAD;
            }
            for (int i = 0; i < temp.length; i++) { //Looping through the copied array
                this.board[i][0] = DEAD; //Setting the top value to dead
                for (int j = 0; j < temp[i].length; j++) { //Looping through each of the copied array values
                    this.board[i + 1][j + 1] = temp[i][j]; //And copying it into the new array
                }
                this.board[i + 1][this.board[i + 1].length - 1] = DEAD; //Setting the bottom value to dead
            }
        }
    }
    void simulate() {
        int[][] number = getNumber();
        char[][] temp=this.board;
        for (int i=0;i<number.length;i++){
            for (int j=0;j<number[i].length;j++){
                if (this.board[i][j]==ALIVE){
                    if (number[i][j]<2){
                        temp[i][j]=DEAD;
                    }else if (number[i][j]>3){
                        temp[i][j]=DEAD;
                    }else{
                        temp[i][j]=ALIVE;
                    }
                }else {
                    if (number[i][j]==3){
                        temp[i][j]=ALIVE;
                    }
                }
            }
        }
        this.board=temp;
        trimBoard();
    }
    void simulate(int generations) {
        for (int i=0;i<generations;i++){
            simulate();
        }
    }
    /**
     * surround is a 3D int array storing what modifications need to be done to x and y to catch the surrounding cells
     * surround[0][0][0] is the first modification to y before a loop
     * (y+surround[0][0][0])+i gets the y coordinate
     * surround[1] is a 2D array storing the list of modifications done to each y row of the board.
     * x+surround[1][i][j] gets the x coordinate
     **/
    private int[][] getNumber() {
        int[][] number = new int[this.board.length][this.board[0].length];
        int[][][] surround = new int[][][]{{{-1}},
                {{-1, 0, 1},
                        {-1, 1},
                        {-1, 0, 1}}};
        for (int x = 0; x < this.board.length; x++) {
            for (int y = 0; y < this.board[x].length; y++) {
                for (int i = 0; i < surround[1].length; i++) {
                    for (int j = 0; j < surround[1][i].length; j++) {
                        try {
                            if (this.board[x + surround[1][i][j]][surround[0][0][0] + y + i] == ALIVE) {
                                number[x][y]++;
                            }
                        }catch (Exception ignored){}
                    }
                }
            }
        }
        return number;
    }
}