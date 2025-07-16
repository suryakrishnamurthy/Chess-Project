package com.example.chessui.chessBackend;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Queen extends Piece {
    
    public Queen(String colorString, Tile initalPosition, Board b){
        super(colorString, initalPosition, b);
        this.setPieceType("queen");
        this.setMovePattern(new int[][] {{-1, 0},{1, 0},{0, 1},{0, -1},{1, 1},{1, -1},{-1, 1},{-1, -1}});
        if(colorString.equals("white")){
            this.setCharacter("♛");
        }
        if(colorString.equals("black")){
            this.setCharacter("♕");
        }
    }

    @Override
    public HashSet<Move[]> legalMoves() {
        HashSet<Move[]> ret = new HashSet<>();
        
        Queue<int[]> moveChecker = new LinkedList<>();
        for(int[] baseMove: this.getMovePattern()){
            moveChecker.add(baseMove);
        }

        if(this.getPosition()==null){
            return ret;
        }

        int[] currentPos = this.getPosition().getPosition();

        while(!moveChecker.isEmpty()){
            int[] currentMove = moveChecker.remove();
            if(currentPos[0]+currentMove[0]>=0 && currentPos[0]+currentMove[0]<=7 && currentPos[1]+currentMove[1]>=0 && currentPos[1]+currentMove[1]<=7){
                
                Tile possibleTile = this.getBoard().getMyBoard()[currentPos[0]+currentMove[0]][currentPos[1]+currentMove[1]];

                if(possibleTile.getPiece()==null){
                    Move[] possibleMove = new Move[] {new Move(this.getPosition(), this, possibleTile, possibleTile.getPiece())};
                    ret.add(possibleMove);
                
                    int[] absVals = new int[] {Math.abs(currentMove[0]), Math.abs(currentMove[1])};
                    if(currentMove[0]==0){
                        absVals[0]=1;
                    }
                    if(currentMove[1]==0){
                        absVals[1]=1;
                    }

                    int[] nextMoveToCheck = new int[] {currentMove[0]/absVals[0]+currentMove[0], currentMove[1]/absVals[1]+currentMove[1]};
                    moveChecker.add(nextMoveToCheck);
                }
                
                else if(!possibleTile.getPiece().getColor().equals(this.getColor())){
                    Move[] possibleMove = new Move[] {new Move(this.getPosition(), this, possibleTile, possibleTile.getPiece())};
                    ret.add(possibleMove);
                }

            }
            
        }
        // System.out.println(ret);
        return ret;
    }

}