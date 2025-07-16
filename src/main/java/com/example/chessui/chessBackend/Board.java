package com.example.chessui.chessBackend;

import java.util.ArrayList;

public class Board {

    private Tile[][] myBoard;
    private ArrayList<Piece> white;
    private ArrayList<Piece> black;
    private King blackKing;
    private King whiteKing;

    private Rook blackRooka;
    private Rook blackRookb;

    private Rook whiteRooka;
    private Rook whiteRookb;

    private ArrayList<Move[]> moveHistory;

    public Board(){
        myBoard = new Tile[8][8];
        String[] color = {"Black", "White"};

        for(int row = 0; row < 8; row++){

            for(int col = 0; col < 8; col++){

                myBoard[row][col] = new Tile(new int[] {row, col}, color[(row+col)%2]);

            }

        }

        moveHistory = new ArrayList<>();

        white = new ArrayList<>();
        black = new ArrayList<>();

        King whiteKing1 = new King("white", this.myBoard[7][4], this);
        this.whiteKing = whiteKing1;
        
        Queen whiteQueen = new Queen("white", this.myBoard[7][3], this);

        Bishop whiteBishop1 = new Bishop("white", this.myBoard[7][2], this);
        Bishop whiteBishop2 = new Bishop("white", this.myBoard[7][5], this);

        Rook whiteRook1 = new Rook("white", this.myBoard[7][0], this);
        Rook whiteRook2 = new Rook("white", this.myBoard[7][7], this);

        this.whiteRooka=whiteRook1;
        this.whiteRookb=whiteRook2;

        Knight whiteKnight1 = new Knight("white", this.myBoard[7][1], this);
        Knight whiteKnight2 = new Knight("white", this.myBoard[7][6], this);
        
        Pawn whitePawn1 = new Pawn("white", this.myBoard[6][0], this);
        Pawn whitePawn2 = new Pawn("white", this.myBoard[6][1], this);
        Pawn whitePawn3 = new Pawn("white", this.myBoard[6][2], this);
        Pawn whitePawn4 = new Pawn("white", this.myBoard[6][3], this);
        Pawn whitePawn5 = new Pawn("white", this.myBoard[6][4], this);
        Pawn whitePawn6 = new Pawn("white", this.myBoard[6][5], this);
        Pawn whitePawn7 = new Pawn("white", this.myBoard[6][6], this);
        Pawn whitePawn8 = new Pawn("white", this.myBoard[6][7], this);

        white.add(whiteKing1);
        white.add(whiteQueen);
        white.add(whiteBishop1);
        white.add(whiteBishop2);
        white.add(whiteRook1);
        white.add(whiteRook2);
        white.add(whiteKnight1);
        white.add(whiteKnight2);
        white.add(whitePawn1);
        white.add(whitePawn2);
        white.add(whitePawn3);
        white.add(whitePawn4);
        white.add(whitePawn5);
        white.add(whitePawn6);
        white.add(whitePawn7);
        white.add(whitePawn8);


        King blackKing1 = new King("black", this.myBoard[0][4], this);
        this.blackKing = blackKing1;


        Queen blackQueen = new Queen("black", this.myBoard[0][3], this);

        Bishop blackBishop1 = new Bishop("black", this.myBoard[0][2], this);
        Bishop blackBishop2 = new Bishop("black", this.myBoard[0][5], this);

        Rook blackRook1 = new Rook("black", this.myBoard[0][0], this);
        Rook blackRook2 = new Rook("black", this.myBoard[0][7], this);

        this.blackRooka=blackRook1;
        this.blackRookb=blackRook2;

        Knight blackKnight1 = new Knight("black", this.myBoard[0][1], this);
        Knight blackKnight2 = new Knight("black", this.myBoard[0][6], this);

        Pawn blackPawn1 = new Pawn("black", this.myBoard[1][0], this);
        Pawn blackPawn2 = new Pawn("black", this.myBoard[1][1], this);
        Pawn blackPawn3 = new Pawn("black", this.myBoard[1][2], this);
        Pawn blackPawn4 = new Pawn("black", this.myBoard[1][3], this);
        Pawn blackPawn5 = new Pawn("black", this.myBoard[1][4], this);
        Pawn blackPawn6 = new Pawn("black", this.myBoard[1][5], this);
        Pawn blackPawn7 = new Pawn("black", this.myBoard[1][6], this);
        Pawn blackPawn8 = new Pawn("black", this.myBoard[1][7], this);
        
        black.add(blackKing1);
        black.add(blackQueen);
        black.add(blackBishop1);
        black.add(blackBishop2);
        black.add(blackRook1);
        black.add(blackRook2);
        black.add(blackKnight1);
        black.add(blackKnight2);
        black.add(blackPawn1);
        black.add(blackPawn2);
        black.add(blackPawn3);
        black.add(blackPawn4);
        black.add(blackPawn5);
        black.add(blackPawn6);
        black.add(blackPawn7);
        black.add(blackPawn8);
    }

    public ArrayList<Move[]> getMoveHistory(){
        return moveHistory;
    }

    public ArrayList<Piece> getBlack(){
        return this.black;
    }

    public ArrayList<Piece> getWhite(){
        return this.white;
    }

    public Tile[][] getMyBoard(){
        return this.myBoard;
    }

    public boolean inCheck(String color){
        ArrayList<Piece> otherTeamPieces = new ArrayList<>();
        King king = null;
        
        if(color.equals("white")){
            otherTeamPieces=this.black;
            king = this.whiteKing;
        }
        else{
            otherTeamPieces=this.white;
            king = this.blackKing;
        }

        for(Piece b: otherTeamPieces){
            for(Move[] moves: b.legalMoves()){
                if(moves[0].getEnd().equals(king.getPosition())){ //change logic for .contains
                    return true;
                }
            }
        }
        return false;
    }

    public void move(Move[] moves){
        this.moveHistory.add(moves);
        for(Move move: moves){
            Piece mover = move.getStartPiece();
            Piece attacked = move.getEndPiece();

            Tile moverTile = move.getStart();
            Tile attackedTile = move.getEnd();


            if(mover.getPosition() == null){
                mover.changePosition(attackedTile);
            }
            if(attackedTile==null){
                moverTile.changePiece(null);
                mover.changePosition(null);
                continue;
            }
            if(attacked==null){
                if(moverTile!=null){
                    moverTile.changePiece(null);
                }
                attackedTile.changePiece(mover);
                mover.changePosition(attackedTile);
                continue;
            }
            else{
                moverTile.changePiece(null);
                attackedTile.changePiece(mover);
                attacked.changePosition(null);
                mover.changePosition(attackedTile);
            }

        }
    }

    public void undoMoves(){
        Move[] undoMoves = this.moveHistory.getLast();
        this.moveHistory.removeLast();

        for(int i = undoMoves.length - 1; i>-1; i--){
            Move move = undoMoves[i];

            Piece moved = move.getStartPiece();
            Piece attacked = move.getEndPiece();

            Tile movedFromTile = move.getStart();
            Tile attackedTile = move.getEnd();

            moved.changePosition(movedFromTile);
            movedFromTile.changePiece(moved);
            
            if(attackedTile!=null){
                attackedTile.changePiece(null);
            }

            if(attacked!=null){
                attacked.changePosition(attackedTile);
                attackedTile.changePiece(attacked);
            }

        }

    }

    @Override
    public String toString(){
        String retString = "   a  b  c  d  e  f  g  h \n";
        for(int i = 0; i<8; i++){
            retString+=8-i+"  ";
            for(int j = 0; j<8; j++){
                String filler = "";
                if(this.myBoard[i][j].getPiece()==null){
                    filler = "   ";
                }
                else{
                    filler = this.myBoard[i][j].getPiece().getCharacter()+"  ";
                }
                retString+=filler;
            }
            retString+="\n";
        }

        return retString;
    }

    public King getKing(String color){
        if(color.equals("white")){
            return this.whiteKing;
        }
        if(color.equals("black")){
            return this.blackKing;
        }
        return null;
    }

    public Rook[] getRooks(String color){
        if(color.equals("white")){
            return new Rook[] {this.whiteRooka, this.whiteRookb};
        }
        if(color.equals("black")){
            return new Rook[] {this.blackRooka, this.blackRookb};
        }
        return null;
    }


    public static void main(String[] args) {
        Board b = new Board();
        System.out.println(b);
    }
    
}
