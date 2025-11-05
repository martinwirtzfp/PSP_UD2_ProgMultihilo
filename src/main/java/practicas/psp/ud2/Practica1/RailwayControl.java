package practicas.psp.ud2.Practica1;

import java.util.ArrayList;
import java.util.List;

public class RailwayControl {

    private int trainsCurrentlyInRailway;
    private boolean railwayFree;
    private List<String> trainsTransited;
    private String currentDirection;
    private int trainDirectionCounter;

    private final int MAX_TRAINS_IN_SAME_DIRECTION = 3;

    public RailwayControl(String initialDirection) {
        trainsCurrentlyInRailway = 0;
        trainsTransited = new ArrayList<>();
        this.currentDirection = initialDirection;
        trainDirectionCounter = 0;
    }

    public void requestRailwayPermission(String trainName, String direction) throws InterruptedException {
        synchronized (this) {

            if (trainDirectionCounter == MAX_TRAINS_IN_SAME_DIRECTION && (!direction.equals(currentDirection))) {
                // Si el contador llegó a 3 y el tren que pide entrar tiene dirección opuesta, cambiamos la vía.
                System.out.println("\n Han pasado " + MAX_TRAINS_IN_SAME_DIRECTION + "trenes (" + currentDirection + "). Cediendo el paso a " + direction + ".");
                currentDirection = direction;
                trainDirectionCounter = 0;
            }
            // Condición de Espera (Espera no activa)
            // El tren espera si:
            // a) La vía está ocupada (trainsCurrentlyInRailway > 0)
            // b) La dirección solicitada no coincide con la dirección actual permitida.
                while (trainsCurrentlyInRailway > 0 || !direction.equals(currentDirection)){
                    System.out.println("Vía ocupada. Tren " + trainName + " debe esperar. Ocupada por: "
                            + currentDirection + " (Trenes: " + trainsCurrentlyInRailway + ")");
                    wait();


            }
            System.out.println("Tren " + trainName + " entra en la vía " + currentDirection);
            trainsCurrentlyInRailway++;
            trainsTransited.add(trainName);
            trainDirectionCounter++;
        }


    }

    public synchronized void trainArrival() {
        trainsCurrentlyInRailway--;
        System.out.println("Tren ha salido de la vía. Trenes restantes en vía: " + trainsCurrentlyInRailway);

        if(trainsCurrentlyInRailway == 0){
            System.out.println("Vía libre. Notificando a los trenes en espera...");
            notifyAll();
        }
    }

}
