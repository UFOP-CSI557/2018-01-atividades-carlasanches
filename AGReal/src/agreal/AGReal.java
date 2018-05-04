/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agreal;

/**
 *
 * @author Carla
 */
public class AGReal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
         Problema problema = new Problema();

        Integer tamanho = 50; //Numero baixo gera queda no desempenho
                              //Populações grandes gastam mais recursos computacionais
                              
        Double pCrossover = 1.5; //Deve ser maior que a taxa de mutação
                                //Quanto mais alto o valor, mais rápido a população será substituída.
                                //Individuos com boa aptidão poderão ser descartados.
                                //Um valor muito baixo deixa o algoritmo mais lento.
                                
        Double pMutacao = 1.0; //Uma taxa baixa previne a estagnação de um valor.
                                //Uma taxa alta torna a busca mais aleatória.
        Integer geracoes = 300; //espepcificado na atividade 1
        //1000 e 100
        
        Double minimo = -5.12; //especificado na atividade 1
        Double maximo = 5.12; //especificado na atividade 1
        Integer nVariaveis = 100; //especificado na atividade 1
        
        AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanho, pCrossover, pMutacao, geracoes, problema, minimo, maximo, nVariaveis);
        
        ag.executar();               
      
    }   
}

//gerar dois cenários para cada algoritmo
//fazer 10 execuções e anotar os dados
