/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import metodo.DEReal;

import metodo.ESReal;
import problema.Problema;
import problema.ProblemaDeJong;
import solucao.Individuo;

/**
 *
 * @author Carla
 */
public class DERealTeste {
    
    public final static int REPETICOES = 30;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Par√¢metros propostos
        Double minimo = -5.12;
        Double maximo = 5.12;
        Integer D = 100;
        Integer gmax = 300;
        
        Problema problema = new ProblemaDeJong(D);
        
        //Par√¢metros n√£o modificados        
        Double F = 0.000035; //Taxa de mutaÁ„o
        Double Cr = 1.5; //Taxa de crossover
        
        //Casos de teste
        //1 - Variar muta√ß√£o; 2 - variar crossover
        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("REAL1", "REAL2"));
        
        for(int i = 0; i < REPETICOES; i++){
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2));
            Collections.shuffle(casos);
            
            for(int c = 0; c < casos.size(); c++){
                
                Integer Np = 3; //Tamanho da populaÁ„o
                
                DEReal deReal;
                
                Individuo result;
                
                long startTime = System.currentTimeMillis();
                
                int teste = casos.get(c);
                
                switch(teste){
                    case 1:
                       Np = 100;
                    break;
                        
                    case 2: 
                        Np = 200;
                    break;
                }
                
                deReal = new DEReal(minimo, maximo, problema, gmax, D, Np, F, Cr);
                result = deReal.executar();
                                
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                
                System.out.println(nomes.get(teste-1) + ';' + "RESULTADO" + ';' + result.getFuncaoObjetivo() + ';' + "TEMPO" 
                                + ';' + totalTime + ';' + "REPETICAO" + ';' + (i+1));
            }                         
        }
    }    
}
