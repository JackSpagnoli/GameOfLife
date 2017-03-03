class GameOfLife {
    private final boolean sizeCapped;
    private char[][] board; //[x][y]
    private final char ALIVE='O',
                       DEAD='.';
    GameOfLife(boolean sizeCapped) {
        this.sizeCapped=sizeCapped;
    }
    void setBoard(int x, int y){
        this.board=new char[x][y];
        innit();
    }
    private void innit(){
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board[i].length;j++){
                this.board[i][j]=DEAD;
            }
        }
    }
    void toggleCell(int x, int y){
        if (this.board[x][y]==ALIVE){
            this.board[x][y]=DEAD;
        }else {
            this.board[x][y]=ALIVE;
        }
    }
    void trimBoard(){
        if (this.sizeCapped){
            return;
        }else {
            int minx=Integer.MAX_VALUE,
                    miny=Integer.MAX_VALUE,
                    maxx=0,
                    maxy=0;
            for (int i=0;i<this.board.length;i++){
                for (int j=0;j<this.board[i].length;j++){
                    if (this.board[i][j]==ALIVE){
                        if (i>maxx){
                            maxx=i;
                        }else if (i<minx){
                            minx=i;
                        }else if (j>maxy){
                            maxy=j;
                        }else if (j<miny){
                            miny=j;
                        }
                    }
                }
            }
            //TODO Extend the board to fit the extra cell
            this.board=Functions.subSet(this.board,minx-1,maxx+1);
            for (int i=0;i<this.board.length;i++){
                this.board[i]=Functions.subSet(this.board[i],miny-1,maxy+1);
            }
        }
    }
}class Functions{
    static char[][] append(char[][] array, char[] addition){
        char[][] temp=new char[array.length+1][array[0].length];
        for(int i=0;i<array.length;i++){
            temp[i]=array[i];
        }
        temp[temp.length-1]=addition;
        return temp;
    }
    static char[][] subSet(char[][] array, int s, int n){
        char[][] temp=new char[(n-s)+1][];
        for (int i=s, p=0; i<n+1;i++,p++){
            temp[p]=array[i];
        }
        return temp;
    }
    static char[] subSet(char[] array, int s, int n){
        char[] temp=new char[(n-s)+1];
        for (int i=s, p=0; i<n+1;i++,p++){
            temp[p]=array[i];
        }
        return temp;
    }
}