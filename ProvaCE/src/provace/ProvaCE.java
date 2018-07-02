/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Carla
 */
public class ProvaCE {

    /**
     * @param args the command line arguments
     */
    
    public final static int REPETICOES = 30;
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Problema problema = new Problema();
        
        //Parâmetros propostos
        Double minimo = -500.0;
        Double maximo = 500.0;        
        Integer nVariaveis = 50;
        Integer tamanho = 100;
        Integer geracoes = 300;
        
        //Parâmetros teste
        Double pCrossover = 1.0;
        Double pMutacao = 0.9;
        
        //Casos de teste
        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("REAL1", "REAL2"));
        
        for(int i = 0; i < REPETICOES; i++){
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2));
            Collections.shuffle(casos);
            
            for(int c = 0; c < casos.size(); c++){
                AlgoritmoAGDE agde;
                Individuo result;
                
                long startTime = System.currentTimeMillis();
            
                int teste = casos.get(c);
                
                switch(teste){
                    case 1:
                       pMutacao = 0.84;
                    break;
                        
                    case 2: 
                        pMutacao = 0.00001;
                    break;
                }
                
                agde = new AlgoritmoAGDE(geracoes, pCrossover, pMutacao, tamanho, minimo, maximo, nVariaveis, problema);
                result = agde.executar();
            
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                
                System.out.println(nomes.get(teste-1) + ';' + "RESULTADO" + ';' + result.getFuncaoObjetivo() + ';' + "TEMPO" 
                                + ';' + totalTime + ';' + "REPETICAO" + ';' + (i+1));
            }
        }
    }
}
