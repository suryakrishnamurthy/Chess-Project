package com.example.chessui.chessBackend;

import java.util.Arrays;

public class Tile {
    int[] position;
    Piece current;
    String color;

    public Tile(int[] pos, String col){
        this.position = pos;
        this.color = col;
    }

    public Piece getPiece(){
        return this.current;
    }

    public void changePiece(Piece newPiece){
        this.current = newPiece;
    }

    public int[] getPosition(){
        return position;
    }

    @Override
    public boolean equals(Object o){
        Tile b = (Tile) o;
        return Arrays.equals(this.getPosition(), b.getPosition());
    }

    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }

    @Override
    public String toString(){
        String col = "abcdefgh".split("")[position[1]];
        return col+(8-position[0]);
    }
}
