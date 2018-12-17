package sample;

public class Array {

    public static void main(String[] args){
        int[] data = new int[]{2, 1, 2, 3, 3, 2, 3, 1, 1, 1, 2, 3, 3, 2, 1, 2};
        int[][] coppied_data = new int[4][4];
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                coppied_data[i][j] = data[j%4+i*4];
            }
        }

        for(int i =0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                System.out.print(coppied_data[i][j] + " ");
            }
            System.out.println("");
        }

    }
}
