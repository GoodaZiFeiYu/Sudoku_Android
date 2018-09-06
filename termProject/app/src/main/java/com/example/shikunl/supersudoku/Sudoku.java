/**
 * Author: chnmagnus
 * First version Date: 03/19/2016
 * This is Sudoku logical class which can generator the Sudoku table and
 * slove the sudoku.
 * Last modified date: 07/21/2018 Shikun Lin
 * You can find the source code from: https://segmentfault.com/a/1190000004641936#articleHeader1
 *
 */

package com.example.shikunl.supersudoku;


public class Sudoku {

    private int[][] data = new int[9][9]; //muti_array
    private int lef; //the number of zero in array
    private int tip; //the number of nozero_digit in array


    public Sudoku(){
        lef = 0;
        for(int i=0;i<9;++i){
            for(int j=0;j<9;++j){
                data[i][j] = 0;
            }
        }
    }

    public void genSudo(){


        lef = 81 - 9;
        for(int i=0;i<9;++i){
            data[0][i] = i+1;
        }
        for(int i=0;i<9;++i){
            int ta = (int)(Math.random()*10)%9;
            int tb = (int)(Math.random()*10)%9;
            int tem = data[0][ta];
            data[0][ta] = data[0][tb];
            data[0][tb] = tem;
        }
        for(int i=0;i<9;++i){
            int ta = (int)(Math.random()*10)%9;
            int tb = (int)(Math.random()*10)%9;
            int tem = data[0][i];
            data[0][i] = data[ta][tb];
            data[ta][tb] = tem;
        }

        solveSudo();
        lef = 81 - tip;
        for(int i=0;i<lef;++i){
            int ta = (int)(Math.random()*10)%9;
            int tb = (int)(Math.random()*10)%9;
            if(data[ta][tb]!=0)
                data[ta][tb] = 0;
            else
                i--;
        }
    }

    public boolean solveSudo(){
        if(dfs()){
            System.out.println("Solve completed.");
            return true;
        }else{
            System.out.println("Error:There are no solution.");
            return false;
        }
    }


    private int calcount(int r,int c,int[] mark){
        for(int ti=0;ti<10;++ti)
            mark[ti] = 0;
        for(int i=0;i<9;++i){
            mark[data[i][c]] = 1;
            mark[data[r][i]] = 1;
        }
        int rs = (r/3)*3;
        int cs = (c/3)*3;
        for(int i=0;i<3;++i){
            for(int j=0;j<3;++j){
                mark[data[rs+i][cs+j]] = 1;
            }
        }
        int count = 0;
        for(int i=1;i<=9;++i){
            if(mark[i]==0)
                count++;
        }
        return count;
    }

    private boolean dfs(){
        if(lef==0) return true;
        int mincount = 10;
        int mini = 0,minj = 0;
        int[] mark = new int[10];

        for(int i=0;i<9;++i){
            for(int j=0;j<9;++j){
                if(data[i][j]!=0) continue;

                int count = calcount(i,j,mark);
                if(count==0) return false;
                if(count<mincount){
                    mincount = count;
                    mini = i;
                    minj = j;
                }
            }
        }

        calcount(mini,minj,mark);
        for(int i=1;i<=9;++i){
            if(mark[i]==0){
                data[mini][minj] = i;
                lef--;
                dfs();
                if(lef==0) return true;
                data[mini][minj] = 0;
                lef++;
            }
        }
        return true;
    }

    public int getTip() {
        return tip;
    }
    public void setTip(int t){
        this.tip = t;
    }

    public int[][] getData() {
        return data;
    }

}