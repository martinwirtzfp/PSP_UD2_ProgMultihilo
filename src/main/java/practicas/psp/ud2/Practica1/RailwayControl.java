package practicas.psp.ud2.Practica1;

import java.util.ArrayList;
import java.util.List;

public class RailwayControl {
    private boolean railwayFree;
    private List<String> trainsTransited;
    private String currentDirection;
    private int trainDirectionCounter;

    public RailwayControl(String currentDirection){
        railwayFree = true;
        trainsTransited = new ArrayList<>();
        this.currentDirection = currentDirection;
        trainDirectionCounter = 0;
    }

    public void requestRailwayPermission(String trainName, String direction) throws InterruptedException {
        // Si ya han pasado 3 trenes y la dirección actual del tren que quiere entrar es distinta
        // Cambiamos la dirección de la vía para dejar pasar a ese tren
        if(trainDirectionCounter==3 && (!direction.equals(currentDirection))){
            //Cambiamos la dirección de la vía
            currentDirection = direction;
        }

        synchronized (this) {
            // No puedes entrar si no coincide la dirección y la vía está ocupada
            while ((!(direction.equals(currentDirection))) && (!railwayFree)) {
                wait();
                System.out.println("Via ocupada. Tren " + trainName + " debe esperar: vía ocupada por trenes "
                        + this.currentDirection);
            }

            System.out.println("Tren " + trainName + " Entra en la vía " + currentDirection);
            railwayFree = false;
            trainsTransited.add(trainName);
            trainDirectionCounter++;
        }


    }

    public synchronized void trainArrival(){

    }

}
