/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provace;

import java.util.ArrayList;

/**
 *
 * @author Carla
 */
public class AlgoritmoAGDE {
    
    Populacao populacao;
    
    //Tamanho da população
    Integer tamanho;
    //Minimo
    Double minimo;
    //Maximo
    Double maximo;
    //Variaveis
    Integer nVariaveis;
    //Problema Schwefel
    Problema problema;
    
    public ArrayList<Individuo> executar(){
        
        this.populacao = new Populacao(this.minimo, this.maximo, this.nVariaveis, this.tamanho, this.problema);
        
        //Criar a população
        populacao.criar();
        
        //Avaliar a população
        populacao.avaliar();
        return null;
    }    
}
