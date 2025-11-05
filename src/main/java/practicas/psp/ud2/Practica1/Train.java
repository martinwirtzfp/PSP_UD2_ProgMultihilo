package practicas.psp.ud2.Practica1;

public class Train implements Runnable{

    private String name;
    private String direction;
    private RailwayControl railwayControl;

    public Train(String name, String direction, RailwayControl railwayControl){
        this.name = name;
        this.direction = direction;
        this.railwayControl = railwayControl;
    }


    @Override
    public void run() {
        // 1. Intenta entrar en la vía
        System.out.println("Tren " + name + " intenta entrar en la vía (" + direction + ")");
        try {
            railwayControl.requestRailwayPermission(name, direction);

            // 2. Simula el tiempo que el tren está en la vía
            Thread.sleep((long) (Math.random() * 2000) + 1000);

            // 3. Sale de la vía
            railwayControl.trainArrival(name, direction);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El tren " + name + " ha sido interrumpido.");
        } catch (Exception e) {
            System.err.println("Error inesperado en el tren " + name + ": " + e.getMessage());
        }
    }
}
