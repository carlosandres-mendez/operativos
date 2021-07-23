
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author carlosandres.mendez
 */
public class ExCovid19 {
    private final static int NUM_PACIENTES = 1000;
    private static AtomicInteger contHosp1 = new AtomicInteger(0);
    private static AtomicInteger contHosp2 = new AtomicInteger(0);
    private static Semaphore semaphore1 = new Semaphore(100);//Semaforo con 100 permisos
    private static Semaphore semaphore2 = new Semaphore(100);//Semaforo con 100 permisos

    public void iniciar() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(1000);//los pacientes 

        for (int i = 0; i < NUM_PACIENTES; i++) {//veces al azar que repite el proceso.
            //ArrayList<Integer> hospMasCercanos = desordenarAleatorio(); //hospitales mas cercanos en orden 

            int hospMasCercano = (int) ((Math.random() * 2) + 1); //retorna 1 o 2
            int hospSgteMasCercano = hospMasCercano == 1 ? 2 : 1;
            String[] nombreHospitales = {"Este", "Oeste"};
            String nombreHospMasCercano1 = nombreHospitales[hospMasCercano - 1];
            String nombreHospMasCercano2 = nombreHospitales[hospSgteMasCercano - 1];

            if (hospMasCercano == 1) { //1 = Este 
                //Padre lanza el hilo i seAtiendeEnHospital1
                Future<Boolean> result = executor.submit(new IngresarHospitalEste());
                if(result.get()==false)
                    executor.submit(new IngresarHospitalOeste());
                
            } else if (hospMasCercano == 2) { //2= Oeste
                //Padre lanza el hilo i seAtiendeEnHospital2
                Future<Boolean> result = executor.submit(new IngresarHospitalOeste());
                if(result.get()==false)
                    executor.submit(new IngresarHospitalEste());
            } else {
                System.out.println("no se atiende en ningun hospital");
            }
        }

        ConcurrentUtils.stop(executor);
    }


    class IngresarHospitalEste implements Callable<Boolean> {

        public Boolean call() {
            boolean resultado = false;
            if (semaphore1.tryAcquire(1)) {
                resultado = true;
                contHosp1.addAndGet(1);
                System.out.println("Hospital Este (" + contHosp1.toString() + "): paciente " + Thread.currentThread().getName()
                        +" Cant. pacientes: " +contHosp1.toString());
                try {
                    TimeUnit.MILLISECONDS.sleep(15);//espera 1 segundo
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExCovid19.class.getName()).log(Level.SEVERE, null, ex);
                }
                semaphore1.release();
                //contHosp1.addAndGet(-1);
            }
            return new Boolean(resultado);
        }
    }

    class IngresarHospitalOeste implements Callable<Boolean> {

        public Boolean call() {

            boolean resultado = false;
            if (semaphore2.tryAcquire(1)) {
                resultado = true;
                contHosp2.addAndGet(1);
                System.out.println("Hospital Oeste (" + contHosp2.toString() + "): paciente " 
                        + Thread.currentThread().getName() 
                        +" Cant. pacientes: " +contHosp1.toString());
                try {
                    TimeUnit.MILLISECONDS.sleep(15);//espera 1 segundo
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExCovid19.class.getName()).log(Level.SEVERE, null, ex);
                }
                semaphore2.release();
                //contHosp2.addAndGet(-1);
            }

            return new Boolean(resultado);
        }
    }
    
  
}


class ProgramaControlIngresoHospitalesCR {

    public static void main(String[] args) {
        try {
            ExCovid19 c = new ExCovid19();
            c.iniciar();
        } catch (Exception ex) {
            Logger.getLogger(ProgramaControlIngresoHospitalesCR.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}