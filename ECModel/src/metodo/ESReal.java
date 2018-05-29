/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodo;

import java.util.Collections;
import java.util.Random;
import problema.Problema;
import solucao.Individuo;
import solucao.IndividuoDouble;
import solucao.PopulacaoDouble;

/**
 *
 * @author Carla
 */
public class ESReal implements Metodo{
    
    //Parametros - Problema - DeJong
    private Double minimo;    
    private Double maximo;
    private Integer nVariaveis;    
    private Problema problema;
    
    //Parametros - ES
    private Integer mu; //Tamanho da população
    private Integer lambda; //numero de descendentes
    private Integer geracoes; //criterio de parada
    private Double pMutacao; //mutação - aplicação ao descendente - variação/perturbação

    public ESReal(Double minimo, Double maximo, Integer nVariaveis, Problema problema, Integer mu, Integer lambda, Integer geracoes, Double pMutacao) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVariaveis = nVariaveis;
        this.problema = problema;
        this.mu = mu;
        this.lambda = lambda;
        this.geracoes = geracoes;
        this.pMutacao = pMutacao;
    }   
    
    private void mutacaoPorVariavel(Individuo individuo){

        Random rnd = new Random();

        for(int i = 0; i < individuo.getCromossomos().size(); i++){
            if (rnd.nextDouble() <= this.pMutacao){
               
                //Mutação aritmetica
                //Multiplicar rnd e inverter ou não o sinal
                
                Double valor = (Double) individuo.getCromossomos().get(i);
                
                //Multiplica por rnd
                valor *= rnd.nextDouble();
                
                //Inverter o sinal
                if(!rnd.nextBoolean()){
                    valor = -valor;
                }
                
                if(valor >= this.minimo && valor <= this.maximo){
                    individuo.getCromossomos().set(i, valor);
                }
            }
        }
    }    

    @Override
    public Individuo executar() {
        //Geração da população inicial - aleatoria - tamanho mu
        PopulacaoDouble populacao = new PopulacaoDouble(problema, minimo, maximo, nVariaveis, mu);
        
        populacao.criar();
        
        //Avaliar
        populacao.avaliar();
        
        //População - descendentes
        PopulacaoDouble descendentes = new PopulacaoDouble();
        
        //Critério de parada -- numero de gerações
        for(int g = 1; g <= this.geracoes; g++){
            //Para cada individuo, gerar lambda/mu descendentes
            for(int i = 0; i < populacao.getIndividuos().size(); i++){
                //Gerar lambda/mu descendentes
                for(int d = 0; d < lambda/mu; d++){
                    //Clonar individuo
                    IndividuoDouble filho = (IndividuoDouble)populacao.getIndividuos().get(i).clone();
                    
                    //Aplicar mutação
                    mutacaoPorVariavel(filho);
                    
                    //Avaliar
                    problema.calcularFuncaoObjetivo(filho);
                    
                    //Inserir na população de descendentes
                    descendentes.getIndividuos().add(filho);
                  
                }
            }
            
            //ES(mu, lambda)?  
            //Eliminar a população corrente
             //populacao.getIndividuos().clear();
            
            //ES(mu + lambda)?
            populacao.getIndividuos().addAll(descendentes.getIndividuos());
            //Ordenar Mu+Lambda
            Collections.sort(populacao.getIndividuos());
            //Definir sobreviventes
            populacao.getIndividuos().subList(this.mu, populacao.getIndividuos().size()).clear();
            
            //Limpar descendentes
            descendentes.getIndividuos().clear();

         //   System.out.println("G = " + g + "\t" + populacao.getMelhorIndividuo().getFuncaoObjetivo());
        }
        //Retornar o melhor indivíduo
        return populacao.getMelhorIndividuo();        
    }       
}
