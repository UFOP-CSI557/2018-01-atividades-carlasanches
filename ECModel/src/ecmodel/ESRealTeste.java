/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import metodo.ESReal;
import problema.Problema;
import problema.ProblemaDeJong;
import solucao.Individuo;

/**
 *
 * @author Carla
 */
public class ESRealTeste {
    
    public final static int REPETICOES = 30;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Parâmetros propostos
        Double minimo = -5.12;
        Double maximo = 5.12;
        Integer nVariaveis = 100;
        Integer geracoes = 300;
        
        Problema problema = new ProblemaDeJong(nVariaveis);
        
        //Parâmetros não modificados
        Double pMutacao = 0.065;
        Integer lambda = 100; //numero de descendentes
        
        //Casos de teste
        //1 - Variar mutação; 2 - variar crossover
        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("REAL1", "REAL2"));
        
        
        
        for(int i = 0; i < REPETICOES; i++){
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2));
            Collections.shuffle(casos);
            
            for(int c = 0; c < casos.size(); c++){
                
                Integer mu = 300; //Tamanho da popula��o
                
                ESReal esReal;
                
                Individuo result;
                
                long startTime = System.currentTimeMillis();
                
                int teste = casos.get(c);
                
                switch(teste){
                    case 1:
                       mu = 50;
                    break;
                        
                    case 2: 
                        mu = 100;
                    break;
                }
                
                esReal = new ESReal(minimo, maximo, nVariaveis, problema, mu, lambda, geracoes, pMutacao);
                result = esReal.executar();
                                
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                
                System.out.println(nomes.get(teste-1) + ';' + "RESULTADO" + ';' + result.getFuncaoObjetivo() + ';' + "TEMPO" 
                                + ';' + totalTime + ';' + "REPETICAO" + ';' + (i+1));
            }                         
        }
    }    
}
