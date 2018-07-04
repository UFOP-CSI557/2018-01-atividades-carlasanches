/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passeiodocavalo;

import passeiodocavalo.Individuo;
import passeiodocavalo.Populacao;
import passeiodocavalo.Problema;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Carla
 */
public class AlgoritmoGenetico {
    
    //Tamanho da população
    Integer tamanho;
    //Taxa de crossover - operador principal
    Double pCrossover;
    //Taxa de mutação - operador secundário
    Double pMutacao;
    //Critério de parada - numero de gerações
    Integer geracoes;
    
    //Dados do problema
    //Problema - DeJong
    Problema problema;
    //Precisão
    Integer precisao;
    //Minimo
    Integer minimo;
    //Maximo
    Integer maximo;
    //Variaveis
    Integer nVariaveis;

    public AlgoritmoGenetico(Integer tamanho, Double pCrossover, Double pMutacao, Integer geracoes, Problema problema, Integer precisao, Integer minimo, Integer maximo, Integer nVariaveis) {
        this.tamanho = tamanho;
        this.pCrossover = pCrossover;
        this.pMutacao = pMutacao;
        this.geracoes = geracoes;
        this.problema = problema;
        this.precisao = precisao;
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVariaveis = nVariaveis;
    }
    
    Populacao populacao;
    Populacao novaPopulacao;
    Individuo melhorSolucao;

    public Individuo getMelhorSolucao() {
        return melhorSolucao;
    }
    
    public void executar(){
        populacao = new Populacao(precisao, minimo, maximo, nVariaveis, tamanho, problema);
        novaPopulacao = new Populacao(precisao, minimo, maximo, nVariaveis, tamanho, problema);
        
        //Criar a população
        populacao.criar();
        
        //Avaliar
        populacao.avaliar();
        
        //Recuperar o melhor individuo
        
        Random rnd = new Random();
        int ind1, ind2;
        
        //Enquanto o critério de parada não for satisfeito - Gerações
        for(int g = 1; g <= geracoes; g++){
            
            for(int i = 0; i < this.tamanho; i++){
            
                //Crossover
                if(rnd.nextDouble() <= this.pCrossover){
                    //Realizar a operação

                    ind1 = rnd.nextInt(this.tamanho);

                    do{
                        ind2 = rnd.nextInt(this.tamanho);
                    }while (ind2 == ind1);

                    Individuo desc1 = new Individuo(precisao, minimo, maximo, nVariaveis);
                    Individuo desc2 = new Individuo(precisao, minimo, maximo, nVariaveis);

                    //progenitores
                    Individuo p1 = populacao.getIndividuos().get(ind1);
                    Individuo p2 = populacao.getIndividuos().get(ind2);

                    //ponto de corte
                    int corte = rnd.nextInt(p1.getCromossomos().size());

                    //Descendente 1 => ind1_1 + ind2_2
                    crossoverUmPonto(p1, p2, desc1, corte);

                    //Descendente 2 => ind2_1 + ind1_2    
                    crossoverUmPonto(p2, p1, desc2, corte);

                    //Mutação
                    //Descendente 1
                    mutacaoPorBit(desc1);

                    //Descendente2
                    mutacaoPorBit(desc2);

                    //Avaliar as novas soluções 
                    problema.calcularFuncaoObjetivo(desc1);
                    problema.calcularFuncaoObjetivo(desc2);

                    //Inserir na nova população
                    novaPopulacao.getIndividuos().add(desc1);
                    novaPopulacao.getIndividuos().add(desc2);
                }                
            }      
            
            //Definir sobreviventes => população + descendentes
            //Merge: combinar pop+desc
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());
            
            //Ordenar população
            Collections.sort(populacao.getIndividuos(), Collections.reverseOrder());
            
            //Eliminar os demais individuos - criterio: tamanho da população
            populacao.getIndividuos().subList(this.tamanho, populacao.getIndividuos().size()).clear();
            
            novaPopulacao.getIndividuos().clear();
            
            System.out.println(populacao.getIndividuos().get(0).getVariaveis().size());
            
            //Imprimir a situação atual
            System.out.println("Gen = " + g + "\tCusto = " + populacao.getIndividuos().get(0).getFuncaoObjetivo());
            //System.out.println(populacao.getIndividuos().get(0).getVariaveis());
        }
        
        System.out.println("Melhor resultado: ");
        System.out.println(populacao.getIndividuos().get(0).getVariaveis());
        //System.out.println(populacao.getIndividuos().get(0).getCromossomos());
    }
    
    private void crossoverUmPonto(Individuo ind1, Individuo ind2, Individuo descendente, int corte){
        
        //Ind_1
        descendente.getCromossomos().addAll(ind1.getCromossomos().subList(0, corte));

        //Ind1_2
        descendente.getCromossomos().addAll(ind2.getCromossomos().subList(corte, ind2.getCromossomos().size()));
        
    }
    
     private void mutacaoPorBit(Individuo individuo){

        Random rnd = new Random();

        for(int i = 0; i < individuo.getCromossomos().size(); i++){
            if (rnd.nextDouble() <= this.pMutacao){
                int bit = individuo.getCromossomos().get(i);
                if (bit == 0){
                    bit = 1;
                }
                else{
                    bit = 0;
                }
                individuo.getCromossomos().set(i, bit);
            }
        }
    }
    
}
