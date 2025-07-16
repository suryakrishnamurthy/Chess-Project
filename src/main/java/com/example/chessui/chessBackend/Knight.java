package com.example.chessui.chessBackend;

import java.util.HashSet;

public class Knight extends Piece {
    
    public Knight(String colorString, Tile initalPosition, Board b){
        super(colorString, initalPosition, b);
        this.setMovePattern(new int[][] {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}});
        if(colorString.equals("white")){
            this.setCharacter("♞");
        }
        if(colorString.equals("black")){
            this.setCharacter("♘");
        }
    }

    @Override
    public HashSet<Move[]> legalMoves() {
        HashSet<Move[]> ret = new HashSet<>();

        if(this.getPosition()==null){
            return ret;
        }

        int[] currentPos = this.getPosition().getPosition();

        for(int[] currentMove: this.getMovePattern()){
            if(currentPos[0]+currentMove[0]>=0 && currentPos[0]+currentMove[0]<=7 && currentPos[1]+currentMove[1]>=0 && currentPos[1]+currentMove[1]<=7){
                
                Tile possibleTile = this.getBoard().getMyBoard()[currentPos[0]+currentMove[0]][currentPos[1]+currentMove[1]];

                if(possibleTile.getPiece()==null || !possibleTile.getPiece().getColor().equals(this.getColor())){
                    Move[] possibleMove = new Move[] {new Move(this.getPosition(), this, possibleTile, possibleTile.getPiece())};
                    ret.add(possibleMove);                }
            }
        }

        return ret;
    }

}
