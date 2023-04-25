package app.nmm.Logic.Actor;

import app.nmm.Controller.GameController;
import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Observer.Observer;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class Player extends Actor implements Observer {

    private Integer selectedNodeID = null;
    private Action selectedAction =  null;


    public Player(String tokenColour, String playerName) {
        super(tokenColour, playerName);
    }

    @Override
    public CompletableFuture<Action>  playTurn(Map<Integer, List<Action>> allowableActions, GameController controller) {
        return null;
    }



    @Override
    public void sendUserAction(Integer nodeID) {
        this.selectedNodeID = nodeID;
    }

    @Override
    public CompletableFuture<Action> playTurn(ArrayList<Action> allowableActions, GameController controller) {
        CompletableFuture<Action> completableFuture = new CompletableFuture<>();

        Service<Void> backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        // Perform background processing for player input
                        while (selectedNodeID == null) {
                            // Wait for selectedNodeID to be set by another thread
                            Thread.sleep(100); // Add a small delay to avoid excessive resource consumption
                        }
                        Action finalAction = null;
                        while (finalAction == null) {
                            // Get finalAction from selectedNodeID and update game state
                            System.out.println("Execute! " + selectedNodeID);
                            // Set finalAction based on selectedNodeID or other logic
                            finalAction = allowableActions.get(selectedNodeID);
                        }
                        // Complete the CompletableFuture with the finalAction value
                        completableFuture.complete(finalAction);
                        return null;
                    }
                };
            }
        };

        backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                System.out.println("Done!");
            }
        });

        backgroundThread.restart();

        // Return the CompletableFuture that will eventually complete with the finalAction value
        return completableFuture;
    }





}
