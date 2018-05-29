/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecmodel;

import metodo.ESReal;
import problema.Problema;
import problema.ProblemaDeJong;
import solucao.Individuo;

/**
 *
 * @author Carla
 */
public class ESRealMain {
    
    public static void main(String[] args){
        
        Double minimo = -100.0;
        Double maximo = 100.0;
        Integer nVariaveis = 100;
        Problema problema = new ProblemaDeJong(nVariaveis);
        
        //Parametros - ES
        Integer mu = 20; //Tamanho da população
        Integer lambda = 100; //numero de descendentes
        Integer geracoes = 100; //criterio de parada
        Double pMutacao = 0.8; //mutação - aplicação ao descendente - variação/perturbação
        
        ESReal esReal = new ESReal(minimo, maximo, nVariaveis, problema, mu, lambda, geracoes, pMutacao);
        
        Individuo melhor = esReal.executar();
        System.out.println(melhor);
    }    
}
