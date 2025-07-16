package com.example.chessui.chessBackend;

import java.util.HashSet;

public class Pawn extends Piece {
    
    Tile initialPositionTile;

    public Pawn(String colorString, Tile initalPosition, Board b){
        super(colorString, initalPosition, b);
        this.initialPositionTile=initalPosition;
        this.setPieceType("pawn");
        if(colorString=="white"){
            this.setCharacter("♟");
            this.setMovePattern(new int[][] {{-1, 0}, {-1, 1}, {-1, -1}});
        }
        if(colorString=="black"){
            this.setCharacter("♙");
            this.setMovePattern(new int[][] {{1, 0}, {1, 1}, {1, -1}});
        }
    }

    @Override
    public HashSet<Move[]> legalMoves() {
        
        HashSet<Move[]> ret = new HashSet<>();

        if(this.getPosition()==null){
            return ret;
        }

        int[] currentPos = this.getPosition().getPosition();

        for(int i = 0; i<this.getMovePattern().length; i++){
            int[] currentMove = this.getMovePattern()[i];
            if(currentPos[0]+currentMove[0]>=0 && currentPos[0]+currentMove[0]<=7 && currentPos[1]+currentMove[1]>=0 && currentPos[1]+currentMove[1]<=7){
                
                Tile possibleTile = this.getBoard().getMyBoard()[currentPos[0]+currentMove[0]][currentPos[1]+currentMove[1]];
                

                if(possibleTile.getPiece()==null && i==0){
                    Move[] possibleMove = new Move[] {new Move(this.getPosition(), this, possibleTile, possibleTile.getPiece())};
                    ret.add(possibleMove);
                }
                else if(!this.getBoard().getMoveHistory().isEmpty()){ 
                    Move enPassantCheck = this.getBoard().getMoveHistory().getLast()[0];
                    if(enPassantCheck.getEnd()!=null){
                        if(enPassantCheck.getStartPiece().getPieceType()=="pawn" && 2==Math.abs(enPassantCheck.getStart().getPosition()[0]-enPassantCheck.getEnd().getPosition()[0]) && enPassantCheck.getStartPiece().getPosition()==this.getBoard().getMyBoard()[this.getPosition().getPosition()[0]][this.getPosition().getPosition()[1]+currentMove[1]]){ //enPassantCheck.getStartPiece().getPosition()==this.getBoard().getMyBoard()[this.getPosition().getPosition()[0]][this.getPosition().getPosition()[1]+currentMove[1]]  &&
                            Move[] possibleMove = new Move[] {new Move(this.getPosition(), this, possibleTile, possibleTile.getPiece()), new Move(enPassantCheck.getStartPiece().getPosition(), enPassantCheck.getStartPiece(), null, null)};
                            ret.add(possibleMove);
                        }
                    }
                }
                if(possibleTile.getPiece()!=null){
                    if((possibleTile.getPiece().getColor()!=this.getColor()) && i!=0){
                        Move[] possibleMove = new Move[] {new Move(this.getPosition(), this, possibleTile, possibleTile.getPiece())};
                        ret.add(possibleMove);
                    }
                } 
            }
        }

        if(this.getPosition()==this.initialPositionTile){
            int[] leapMove = new int[] {2*this.getMovePattern()[0][0], 0};
            int[] inBetween = new int[] {this.getMovePattern()[0][0], 0};

            if(currentPos[0]+leapMove[0]>=0 && currentPos[0]+leapMove[0]<=7 && currentPos[1]+leapMove[1]>=0 && currentPos[1]+leapMove[1]<=7){
                
                Tile possibleTile = this.getBoard().getMyBoard()[currentPos[0]+leapMove[0]][currentPos[1]+leapMove[1]];
                Tile possibleTile1 = this.getBoard().getMyBoard()[currentPos[0]+inBetween[0]][currentPos[1]+inBetween[1]];

                if(possibleTile.getPiece()==null && possibleTile1.getPiece()==null){
                    Move[] possibleMove = new Move[] {new Move(this.getPosition(), this, possibleTile, possibleTile.getPiece())};
                    ret.add(possibleMove);
                }
            }
        }

        return ret;
    }
}
