package com.example.chessui.chessBackend;

public class Move {
    private Tile start;
    private Tile end;
    private Piece startPiece;
    private Piece endPiece;

    public Move(Tile s, Piece sp, Tile e, Piece ep){
        this.start = s;
        this.startPiece = sp;
        this.end = e;
        this.endPiece = ep;
    }

    public Tile getStart(){
        return this.start;
    }

    public Tile getEnd(){
        return this.end;
    }

    public Piece getStartPiece(){
        return this.startPiece;
    }

    public Piece getEndPiece(){
        return this.endPiece;
    }

    @Override
    public String toString(){
        return this.start.toString() + " " + this.end.toString();
    }

    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        Move b = (Move) o;
        return this.start.equals(b.start) && this.end.equals(b.end);
    }
}