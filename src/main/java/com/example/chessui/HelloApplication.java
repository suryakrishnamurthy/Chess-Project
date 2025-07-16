package com.example.chessui;

import com.example.chessui.chessBackend.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private static void selectMove(Board b, int turnNum, Scanner s) {
        Scanner inputReader = s;
        String[] teams = {"black","white"};
        Piece toMove = null;
        Tile target = null;

        Move[] moveToDo = null;

        // System.out.println("Valid moves for white");
        // for(Piece p: b.getWhite()){
        //     System.out.println(p.getPosition()+"");
        //     if(p.getValidMoves()!=null){
        //         for(Move[] moves : p.getValidMoves()){
        //             System.out.println(Arrays.asList(moves)+" ");
        //         }
        //     }
        // }

        // System.out.println("Valid moves for black");
        // for(Piece p: b.getBlack()){
        //     System.out.println(p.getPosition()+"");
        //     if(p.getValidMoves()!=null){
        //         for(Move[] moves : p.getValidMoves()){
        //             System.out.println(Arrays.asList(moves)+" ");
        //         }
        //     }
        // }

        String turnTeam = teams[turnNum%2];
        System.out.println(turnTeam + ", select move");

        boolean userInputCorrect = false;

        outerLoop:
        do{
            String input = inputReader.nextLine();
            if(input.length()==5){
                String[] move = input.split(" ");
                if(move.length==2){
                    if(move[0].length()==2 && move[1].length()==2){
                        int colOfPiece = (move[0].split("")[0]).charAt(0)-97;
                        int rowOfPiece = 8-Integer.parseInt(move[0].split("")[1]);

                        int colOfTarget = (move[1].split("")[0]).charAt(0)-97;
                        int rowOfTarget = 8-Integer.parseInt(move[1].split("")[1]);
                        //check if above are between 0 and 7

                        if(rowOfPiece>=0 && rowOfPiece<=7 && colOfPiece>=0 && colOfPiece<=7 && rowOfTarget>=0 && rowOfTarget<=7 && colOfTarget>=0 && colOfTarget<=7){
                            toMove = b.getMyBoard()[rowOfPiece][colOfPiece].getPiece();
                            target = b.getMyBoard()[rowOfTarget][colOfTarget];
                            //check if correct color and is in valid moves
                            if(toMove!=null){
                                if(toMove.getColor().equals(turnTeam)){

                                    for(Move[] moves : toMove.getValidMoves()){

                                        if(moves[0].getStart()==toMove.getPosition() && moves[0].getEnd()==target){
                                            moveToDo = moves;
                                            break outerLoop;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("Improper input: try again");


        } while(!userInputCorrect);

        b.move(moveToDo);


        //promotion check


        if(moveToDo[0].getStartPiece().getPieceType().equals("pawn") && ((turnTeam.equals("white") && moveToDo[0].getEnd().getPosition()[0]==0) || (turnTeam.equals("black") && moveToDo[0].getEnd().getPosition()[0]==7))){
            boolean userInput2Correct = false;
            do{
                System.out.println("What would you like to promote your pawn to?");
                String promotion = inputReader.nextLine();
                ArrayList<Piece> teamPieces = null;

                if(turnTeam.equals("white")){
                    teamPieces = b.getWhite();
                }
                else{
                    teamPieces = b.getBlack();
                }

                Move pawnOff = new Move(moveToDo[0].getStart(), moveToDo[0].getStartPiece(), null, null);

                if(promotion.equals("knight")){
                    teamPieces.add(new Knight(turnTeam, null, b));
                    b.move(new Move[] {pawnOff, new Move(null, teamPieces.getLast(), moveToDo[0].getEnd(), null)});
                    System.out.println(teamPieces);
                    teamPieces.remove(moveToDo[0].getStartPiece());
                    System.out.println(teamPieces);
                    break;
                }
                if(promotion.equals("queen")){
                    teamPieces.add(new Queen(turnTeam, null, b));
                    b.move(new Move[] {pawnOff, new Move(null, teamPieces.getLast(), moveToDo[0].getEnd(), null)});
                    System.out.println(teamPieces);
                    teamPieces.remove(moveToDo[0].getStartPiece());
                    System.out.println(teamPieces);
                    break;
                }
                if(promotion.equals("rook")){
                    teamPieces.add(new Rook(turnTeam, null, b));
                    b.move(new Move[] {pawnOff, new Move(null, teamPieces.getLast(), moveToDo[0].getEnd(), null)});
                    System.out.println(teamPieces);
                    teamPieces.remove(moveToDo[0].getStartPiece());
                    System.out.println(teamPieces);
                    break;
                }
                if(promotion.equals("bishop")){
                    teamPieces.add(new Bishop(turnTeam, null, b));
                    b.move(new Move[] {pawnOff, new Move(null, teamPieces.getLast(), moveToDo[0].getEnd(), null)});
                    System.out.println(teamPieces);
                    teamPieces.remove(moveToDo[0].getStartPiece());
                    System.out.println(teamPieces);
                    break;
                }

                System.out.println("Improper input: try again");

            } while(!userInput2Correct);
        }
        // System.out.println(b);
        // inputReader.close();
    }

    public static void main(String[] args) {
//        launch();
        System.out.println("hello world!");
        boolean gameOver=false;

        Scanner s = new Scanner(System.in);

        Board b = new Board();

        ArrayList<Piece> teamsPieces = new ArrayList<>();

        System.out.println(b);

        int turn = 1;

        while(true){
            gameOver=true;

            if(turn%2==0){
                teamsPieces = b.getBlack();
            }
            else{
                teamsPieces = b.getWhite();
            }

            for(Piece a: teamsPieces){
                if(a.getPosition()==null){
                    continue;
                }
                if(!a.validMoves().isEmpty()){
                    gameOver=false;
                }
            }

            if(gameOver==true){
                break;
            }

            selectMove(b, turn, s);
            System.out.println(b);
            turn++;
        }

        if(b.inCheck("white")){
            System.out.println("Black wins");
        }
        else if(b.inCheck("black")){
            System.out.println("White wins");
        }
        else{
            System.out.println("Stalemate");
        }
        s.close();

    }
}