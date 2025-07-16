package com.example.chessui.chessBackend;

import java.util.HashSet;

public class King extends Piece {
    Tile initialPositionTile;
    
    public King(String colorString, Tile initalPosition, Board b){
        super(colorString, initalPosition, b);
        this.initialPositionTile=initalPosition;
        this.setMovePattern(new int[][] {{-1, 0},{1, 0},{0, 1},{0, -1},{1, 1},{1, -1},{-1, 1},{-1, -1}});
        if(colorString.equals("white")){
            this.setCharacter("♚");
        }
        if(colorString.equals("black")){
            this.setCharacter("♔");
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
                    ret.add(possibleMove);
                }
            }
        }

        return ret;
    }

    @Override
    public HashSet<Move[]> validMoves(){


        if(this.getValidMoves()==null){
            this.setValidMoves(null);
        }
        // System.out.println("finding valid moves");
        HashSet<Move[]> ret = new HashSet<>();
        HashSet<Move[]> legal = this.legalMoves();



        if(this.getPosition().equals(this.initialPositionTile) && !this.getBoard().inCheck(this.getColor())){
            //work out logic for incheck
            //alter to work for rook
            if(this.getBoard().getRooks(this.getColor())[0].getPosition()==this.getBoard().getRooks(this.getColor())[0].getInitialPosition() && this.getBoard().getRooks(this.getColor())[1].getPosition()==this.getBoard().getRooks(this.getColor())[1].getInitialPosition()){
                boolean logicBool = true;
                int rookCol = this.getBoard().getRooks(this.getColor())[0].getPosition().getPosition()[1]; //fix this stuff too...
                int kingCol = this.getPosition().getPosition()[1];
                int currentCheck = Math.min(rookCol, kingCol)+1;
                int rookAndKingRow = this.getPosition().getPosition()[0];
                while(currentCheck!=Math.max(rookCol, kingCol)){
                    if(this.getBoard().getMyBoard()[rookAndKingRow][currentCheck].getPiece()!=null){
                        logicBool=false;
                        break;
                    }
                    currentCheck++;
                }

                if(logicBool){
                    King king = this;
                    Tile kingPos = king.getPosition();
                    Rook thisRook = this.getBoard().getRooks(this.getColor())[0];
                    Tile rookPos = thisRook.getPosition();

                    int kingMoveDir = 2;

                    if(kingPos.getPosition()[1]>rookPos.getPosition()[1]){
                        kingMoveDir = -2;
                    }

                    Tile kingToMove = this.getBoard().getMyBoard()[kingPos.getPosition()[0]][kingPos.getPosition()[1]+kingMoveDir];
                    Tile rookToMove = this.getBoard().getMyBoard()[kingToMove.getPosition()[0]][kingToMove.getPosition()[1]+(kingMoveDir/-2)];

                    Move kingMove = new Move(kingPos, king, kingToMove, kingToMove.getPiece());
                    Move rookMove = new Move(rookPos, thisRook, rookToMove, rookToMove.getPiece());

                    ret.add(new Move[] {kingMove, rookMove});
                }
            }
            if(this.getBoard().getRooks(this.getColor())[1].getPosition()==this.getBoard().getRooks(this.getColor())[1].getInitialPosition()){
                boolean logicBool = true;
                int rookCol = this.getBoard().getRooks(this.getColor())[1].getPosition().getPosition()[1];
                int kingCol = this.getPosition().getPosition()[1];
                int currentCheck = Math.min(rookCol, kingCol)+1;
                int rookAndKingRow = this.getPosition().getPosition()[0];
                while(currentCheck!=Math.max(rookCol, kingCol)){
                    if(this.getBoard().getMyBoard()[rookAndKingRow][currentCheck].getPiece()!=null){
                        logicBool=false;
                        break;
                    }
                    currentCheck++;
                }

                if(logicBool){
                    King king = this;
                    Tile kingPos = king.getPosition();
                    Rook thisRook = this.getBoard().getRooks(this.getColor())[1];
                    Tile rookPos = thisRook.getPosition();

                    int kingMoveDir = 2;

                    if(kingPos.getPosition()[1]>rookPos.getPosition()[1]){
                        kingMoveDir = -2;
                    }

                    Tile kingToMove = this.getBoard().getMyBoard()[kingPos.getPosition()[0]][kingPos.getPosition()[1]+kingMoveDir];
                    Tile rookToMove = this.getBoard().getMyBoard()[kingToMove.getPosition()[0]][kingToMove.getPosition()[1]+(kingMoveDir/-2)];

                    Move kingMove = new Move(kingPos, king, kingToMove, kingToMove.getPiece());
                    Move rookMove = new Move(rookPos, thisRook, rookToMove, rookToMove.getPiece());

                    ret.add(new Move[] {kingMove, rookMove});
                }
            }
        }

        for(Move[] moves: legal){
            this.getBoard().move(moves);
            if(!this.getBoard().inCheck(this.getColor())){
                ret.add(moves);
            }
            this.getBoard().undoMoves();
        }

        this.setValidMoves(ret);
        return ret;
    }

    public Tile getInitialPosition(){
        return this.initialPositionTile;
    }

}
