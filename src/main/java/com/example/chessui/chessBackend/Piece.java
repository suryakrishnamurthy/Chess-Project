package com.example.chessui.chessBackend;// Move Pattern (matrix transformations)
// Adjacency Tiles
// Initialized with color, position, and type
// Abstract class with specific implementations by piece

import java.util.*;

public class Piece{
    private int[][] movePattern;
    private HashSet<Move[]> validMoves;
    private String color;
    private Tile position;
    private String pieceType;
    private Board board;
    private String character;

    public Piece(String colorString, Tile initialPosition, Board b){
        this.color = colorString;
        this.position = initialPosition;
        this.board = b;
        if(initialPosition!=null){
            initialPosition.changePiece(this);
        }
    }

    public HashSet<Move[]> legalMoves(){
        HashSet<Move[]> ret = new HashSet<>();
        return ret;
    }
    //returns list of tiles to which a piece can move legally: not off the board, and not on another piece of the same color, given piece's move type
    //Does not check if move results in a check on its own king

    public HashSet<Move[]> validMoves(){
        if(this.validMoves!=null){
            this.validMoves.clear();
        }
        // System.out.println("finding valid moves");
        HashSet<Move[]> ret = new HashSet<>();
        HashSet<Move[]> legal = this.legalMoves();

        for(Move[] moves: legal){
            this.getBoard().move(moves);
            if(!this.board.inCheck(this.color)){
                ret.add(moves);
            }
            this.getBoard().undoMoves();
        }

        this.validMoves=ret;
        return ret;
    }

    public String getColor(){
        return this.color;        
    }

    public Tile getPosition(){
        return this.position;        
    }
    //gets position of current piece

    public void changePosition(Tile newPos){
        this.position = newPos;        
    }
    
    public String getCharacter(){
        return this.character;        
    }

    public void setCharacter(String setCharacter){
        this.character = setCharacter;        
    }

    public int[][] getMovePattern(){
        return this.movePattern;        
    }

    public void setMovePattern(int[][] mp){
        this.movePattern = mp;        
    }

    public HashSet<Move[]> getValidMoves(){
        return this.validMoves;        
    }

    public void setValidMoves(HashSet<Move[]> moves){
        this.validMoves = moves;        
    }

    public Board getBoard(){
        return this.board;
    }

    public String getPieceType(){
        return this.pieceType;        
    }

    public void setPieceType(String pt){
        this.pieceType = pt;        
    }

}